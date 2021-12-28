package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;

import static com.valinor.game.world.entity.AttributeKey.NEXT_ATTACK;
import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 27, 2021
 */
public class Nightmare extends Npc {

    public static final int NO_TELEPORT = 0, CENTER = 1, EDGE = 2;

    private int stage = -1, specialDelta = 60, stageDelta = -1, sleepwalkerCount = 0, parasiteDelta = -1, flowerRotary = -1, flyDirection = -1;

    private TotemPlugin[] totems;

    private final Tile base;

    private boolean shield;

    public Nightmare(int id, Tile base) {
        super(id, base);
        this.base = base;
    }

    @Override
    public int maxHp() {
        return shield ? 80 * playersInRegion() : 2400;
    }

    public ArrayList<Tile> getSleepwalkerTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();
        int[][] spots = new int[][]{{26, 24}, {28, 24}, {36, 24}, {38, 24}, {41, 21}, {41, 19}, {41, 11}, {41, 9}, {38, 6}, {36, 6}, {28, 6}, {26, 6}, {23, 9}, {23, 11}, {23, 19}, {23, 21}, {23, 14}, {23, 16}, {41, 16}, {41, 14}, {31, 5}, {33, 5}, {31, 25}, {33, 25}};
        for (int[] spot : spots) {
            tiles.add(getBase().transform(spot[0], spot[1], 0));
        }
        return tiles;
    }

    @Override
    public Hit manipulateHit(Hit hit) {
        boolean dead = false;

        Mob attacker = hit.getAttacker();

        if ((attacker instanceof TotemPlugin) && stageDelta == -1 && stage < 2) {
            stageDelta = 6;
            toggleShield();
        } else if ((attacker instanceof TotemPlugin) && stage < 2) {

        } else if (attacker instanceof TotemPlugin) {
            if (stage >= 2) {
                dead = true;
            }
        }

        if (attacker instanceof Parasite) {
            hit.splatType = SplatType.NPC_HEALING_HITSPLAT;
            hit.setDamage(World.getWorld().random(100));
        }

        //attacker.forceChat(stage + ": " + damage + "/" + hp());

        if (shield && 40 >= hp() && stage <= 2) {
            toggleShield();
            World.getWorld().getPlayers().forEachInRegion(this.tile.region(), p -> p.message("<col=ff0000>As the Nightmare's shield fails, the totems in the area are activated!"));
        }

        if (hp() <= 0) {
            for (TotemPlugin t : totems) {
                t.setChargeable(false);
            }
        }
        if (dead && hp() > 0) {
            super.hit(this, hp());
        }
        return hit;
    }

    public NightmareCombat getCombatMethod() {
        return new NightmareCombat();
    }

    @Override
    public void sequence() {
        if (stage < 0) {
            super.sequence();
            System.out.println("dont do shit "+stage);
            return;
        }
        if (stageDelta > 0 && --stageDelta == 0 && stage < 2) {
            stageDelta = -1;
            getCombat().reset();
            transmog(9431);
            animate(-1);
            getCombatMethod().setSpecial(SpecialAttacks.SLEEPWALKERS);
            getCombatMethod().prepareAttack(this, null);
            specialDelta = 30;
            stage++;
        }

        if (stageDelta == 4) {
            teleportAction(spawnTile());
        }
        if (stageDelta != -1) {
            super.sequence();
            return;
        }

        if (isAttackable() && !getCombat().inCombat() && getCombat().getTarget() == null) {
            for (Mob mob : getPossibleTargets()) {
                getCombat().setTarget(mob);
                break;
            }
        }

        if (specialDelta == 0 && stage < 3) {
            SpecialAttacks[][] attacks = {
                {
                    SpecialAttacks.GRASPING_CLAWS,
                    SpecialAttacks.FLOWER_POWER,
                    SpecialAttacks.HUSKS,
                    SpecialAttacks.GRASPING_CLAWS,
                    SpecialAttacks.FLOWER_POWER,
                    SpecialAttacks.HUSKS,
                    SpecialAttacks.GRASPING_CLAWS,
                    SpecialAttacks.FLOWER_POWER,
                    SpecialAttacks.HUSKS,
                    SpecialAttacks.GRASPING_CLAWS,
                    SpecialAttacks.FLOWER_POWER,
                    SpecialAttacks.HUSKS,
                },
                {
                    SpecialAttacks.GRASPING_CLAWS,
                    SpecialAttacks.CURSE,
                    SpecialAttacks.PARASITES,
                    SpecialAttacks.GRASPING_CLAWS,
                    SpecialAttacks.CURSE,
                    SpecialAttacks.PARASITES,
                    SpecialAttacks.GRASPING_CLAWS,
                    SpecialAttacks.CURSE,
                    SpecialAttacks.PARASITES,
                },
                {
                    SpecialAttacks.GRASPING_CLAWS,
                    SpecialAttacks.GRASPING_CLAWS,
                    SpecialAttacks.GRASPING_CLAWS,
//							SpecialAttacks.SURGE,
                    SpecialAttacks.SPORES,
                    SpecialAttacks.SPORES,
                    SpecialAttacks.SPORES
                }};
            SpecialAttacks nextAttack = attacks[stage][World.getWorld().random(attacks[stage].length - 1)];
//			nextAttack = SpecialAttacks.SPORES;
            if (nextAttack.teleportOption != NO_TELEPORT) {
                int diffX = 0, diffY = 0;
                if (nextAttack.teleportOption == EDGE) {
                    setFlyDirection(0);
                    switch (getFlyDirection()) {
                        case 0 -> diffY = -10;
                        case 1 -> diffX = 10;
                        case 2 -> diffY = -10;
                        case 3 -> diffX = -10;
                    }
                }
                Tile dest = spawnTile().transform(diffX, diffY, this.tile.level);
                teleportAction(dest);
            }
            putAttrib(NEXT_ATTACK, nextAttack);
            getCombat().delayAttack(12);
        }
        if (specialDelta == -4) {
            getCombatMethod().setSpecial(getAttrib(NEXT_ATTACK));
            getCombatMethod().prepareAttack(this, null);
        }
        if (--specialDelta == -10) {
            getCombatMethod().setSpecial(null);
            specialDelta = 60;
        }
        super.sequence();
    }

    public ArrayList<Mob> getPossibleTargets() {
        return getPossibleTargets(14, true, false);
    }

    public ArrayList<Mob> getPossibleTargets(int ratio, boolean players, boolean npcs) {
        ArrayList<Mob> possibleTargets = new ArrayList<>();
        if (players) {
            for (Player player : World.getWorld().getPlayers()) {
                if (player == null || player.dead() || player.tile().distance(this.getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(player);
            }
        }
        if (npcs) {
            for (Npc npc : World.getWorld().getNpcs()) {
                if (npc == null || npc == this || npc.dead() || npc.getCentrePosition().distance(this.getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(npc);
            }
        }
        return possibleTargets;
    }

    public boolean isAttackable() {
        return stage > -1 && id() < THE_NIGHTMARE_9430 && stageDelta == -1;
    }

    private void teleportAction(Tile dest) {
        animate(8607);
        getCombat().reset();
        Chain.bound(null).runFn(1, () -> this.teleport(dest)).then(1, () -> {
            transmog(stageDelta == -1 ? THE_NIGHTMARE + getStage() : THE_NIGHTMARE_9431);
            animate(8609);
        });
    }

    public void toggleShield() {
        shield = !shield;
        setHitpoints(shield ? 200 + (220 * playersInRegion()) : 2400 - (800 * stage));
        for (TotemPlugin totem : totems) {
            totem.setChargeable(!isShield());
        }
    }

    public int playersInRegion() {
        int count = 0;
        for (Player p : World.getWorld().getPlayers()) {
            if (p != null && p.tile().region() == tile().region())
                count++;
        }
        return count;
    }

    @Override
    public void animate(int id) {
        if (id() == 9431 && id != 8604 && id > 0) {
            super.animate(0);
        }
        super.animate(id);
    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public TotemPlugin[] getTotems() {
        return totems;
    }

    public void setTotems(TotemPlugin[] totems) {
        this.totems = totems;
    }

    public int getFlowerRotary() {
        return flowerRotary;
    }

    public void setFlowerRotary(int flowerRotary) {
        this.flowerRotary = flowerRotary;
    }

    public int getFlyDirection() {
        return flyDirection;
    }

    public void setFlyDirection(int flyDirection) {
        this.flyDirection = flyDirection;
    }

    public int getSleepwalkerCount() {
        return sleepwalkerCount;
    }

    public void setSleepwalkerCount(int sleepwalkerCount) {
        this.sleepwalkerCount = sleepwalkerCount;
    }

    public int getParasiteDelta() {
        return parasiteDelta;
    }

    public void setParasiteDelta(int parasiteDelta) {
        this.parasiteDelta = parasiteDelta;
    }

    public Tile getBase() {
        return base;
    }
}

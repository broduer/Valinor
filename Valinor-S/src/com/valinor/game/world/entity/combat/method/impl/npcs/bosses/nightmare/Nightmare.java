package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.method.CombatMethod;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;

import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 27, 2021
 */
public class Nightmare extends Npc {

    public static final int NO_TELEPORT = 0, CENTER = 1, EDGE = 2;

    private int stage = -1, specialDelta = 60, stageDelta = -1, sleepwalkerCount = 0, parasiteDelta = -1, flowerRotary = -1, flyDirection = -1;

    private TotemPlugin[] totems;

    private Tile base;

    private boolean shield;

    public Nightmare(int id, Tile base) {
        super(id);
        this.base = base;
    }

    @Override
    public int maxHp() {//TODO
        return 2400;//shield ? 80 * getPosition().getRegion().players.size() : 2400
    }

    @Override
    public CombatMethod getCombatMethod() {
        return new NightmareCombat();
    }

    @Override
    public void sequence() {
        if (stage < 0) {
            super.sequence();
            return;
        }
        if (stageDelta > 0 && --stageDelta == 0 && stage < 2) {
            stageDelta = -1;
            getCombat().reset();
            transmog(9431);
            animate(-1);
            //TODO
            //getCombatMethod().setSpecial(SpecialAttacks.SLEEPWALKERS);
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
            //TODO
            //set("next_attack", nextAttack);
            getCombat().delayAttack(12);
        }
        if (specialDelta == -4) {
            //TODO
            //getCombatMethod().setSpecial(remove("next_attack"));
            getCombatMethod().prepareAttack(this, null);
        }
        if (--specialDelta == -10) {
            //TODO
            //getCombatMethod().setSpecial(null);
            specialDelta = 60;
        }
        super.sequence();
    }

    public ArrayList<Mob> getPossibleTargets() {
        return getPossibleTargets(14, true, false);
    }

    public ArrayList<Mob> getPossibleTargets(int ratio, boolean players, boolean npcs) {
        ArrayList<Mob> possibleTargets = new ArrayList<Mob>();
        if (players) {
            for (Player player : World.getWorld().getPlayers()) {
                if (player == null) {
                    continue;
                }
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
        Chain.bound(null).runFn(1, () -> {
            this.teleport(dest);
        }).then(1, () -> {
            transmog(stageDelta == -1 ? THE_NIGHTMARE + getStage() : THE_NIGHTMARE_9431);
            animate(8609);
        });
    }

    public void toggleShield() {
        shield = !shield;
        //TODO
        setHitpoints(2400 - (800 * stage));
        //getCombat().getStat(StatType.Hitpoints).alter(shield ? 200 + (220 * tile().getRegion().players.size()) : 2400 - (800 * stage));
        for (TotemPlugin totem : totems) {
            //TODO
            //totem.setChargeable(!isShield());
        }
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

package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.LockType;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.skull.Skulling;
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
            tiles.add(getBase().transform(spot[0], spot[1]));
        }
        return tiles;
    }

    public int overrideSubmit(Hit... hits) {
        int damage = 0;
        boolean process = true;
        boolean dead = false;
        for(Hit hit : hits) {
            Mob attacker = hit.getAttacker();

            if ((attacker instanceof TotemPlugin) && stageDelta == -1 && stage < 2) {
                System.out.println("huh");
                stageDelta = 6;
                toggleShield();
                process = false;
            } else if ((attacker instanceof TotemPlugin) && stage < 2) {
                process = false;
                CombatFactory.addPendingHit(hit);
                System.out.println("huh1");
            } else if (attacker instanceof TotemPlugin) {
                System.out.println("huh2");
                if (stage >= 2) {
                    dead = true;
                    System.out.println("huh3");
                }
            }

            if (attacker instanceof Parasite) {
                System.out.println("huh4");
                hit.splatType = SplatType.NPC_HEALING_HITSPLAT;
                hit.setDamage(World.getWorld().random(100));
            }

            if (attacker.isPlayer() && !isShield()) {
                process = false;
            }

            if(process) {

                if (!isNullifyDamageLock())
                    CombatFactory.addPendingHit(hit);
                damage += hit.getDamage();
            }

            attacker.forceChat(stage + ": " + hit.getDamage() + "/" + hp());

            if (shield && 40 >= hp() && stage <= 2) {
                System.out.println("huh5");
                toggleShield();
                World.getWorld().getPlayers().forEachInRegion(this.tile.region(), p -> p.message("<col=ff0000>As the Nightmare's shield fails, the totems in the area are activated!"));
            }

        }

        Hit baseHit = hits[0];
        if (process) {
            System.out.println("huh6");
            if(baseHit.splatType == SplatType.HITSPLAT || baseHit.splatType == SplatType.BLOCK_HITSPLAT) {
                if(baseHit.getAttacker().isPlayer())
                    baseHit.getAttacker().getAsPlayer().stopActions(false);
                else
                    baseHit.getAttacker().getAsNpc().stopActions(false);
            }

            if(baseHit.getAttacker() != null) {
                if(baseHit.getAttacker().isPlayer() && baseHit.getAttacker().getAsPlayer() != null && baseHit.getCombatType() != null) {
                    if(baseHit.getAttacker().getAsPlayer() != null) //important that this happens here for things that hit multiple targets
                    if(baseHit.spell == null)
                        CombatFactory.addCombatXp(baseHit.getAttacker().getAsPlayer(), baseHit.getTarget(), Math.min(baseHit.getDamage(), baseHit.getTarget().hp()), baseHit.getCombatType(), baseHit.getAttacker().getAsPlayer().getCombat().getFightType().getStyle());
                }
            }
        }

        if (hp() <= 0) {
            for (TotemPlugin t : totems) {
                t.setChargeable(false);
            }
        }
        if (dead && hp() > 0) {
            super.hit(this, hp());
        }
        return damage;
    }

    public NightmareCombat getCombatMethod() {
        return new NightmareCombat();
    }

    @Override
    public void sequence() {
        if (stage < 0) {
            super.sequence();
            System.out.println("dont do shit stage:"+stage);
            return;
        }
        //So somehow the variables that are used to determine stages
        //E.g: stageDelta, stage and specialStage or w/e don't seem to properly update
        //It always stays in the same stages
        //Somehow this stageDelta is ALWAYS -1
        System.out.println("stageDelta: "+stageDelta+" "+stage+" "+specialDelta);
        // so 6 >5>4>3>2>1 then -1
        if (stageDelta > 0 && --stageDelta == 0 && stage < 2) {//Also stage is never updated so once its gonna do its teleport metohod nightmare will change into a wrong npc cuz stage is always 0
            //Not a single stage var is changed
            System.out.println("enter sleepwalkers");
            stageDelta = -1;
            getCombat().reset();
            transmog(THE_NIGHTMARE_9431);
            animate(-1);
            getCombatMethod().setSpecial(SpecialAttacks.SLEEPWALKERS);
            getCombatMethod().prepareAttack(this, null);
            specialDelta = 30;
            stage++;
        }

        if (stageDelta == 4) {
            System.out.println("enter teleport move");
            teleportAction(spawnTile());
        }
        if (stageDelta != -1) {
            System.out.println("keep processing");
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
            System.out.println("special delta 0 and stage below 2");
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
                        case 0, 2 -> diffY = -10;
                        case 1 -> diffX = 10;
                        case 3 -> diffX = -10;
                    }
                }
                Tile dest = spawnTile().transform(diffX, diffY);
                teleportAction(dest);
                System.out.println("this teleport");
            }
            putAttrib(NEXT_ATTACK, nextAttack);
            getCombat().delayAttack(12);
        }
        if (specialDelta == -4) {
            System.out.println("special delta -4");
            getCombatMethod().setSpecial(getAttrib(NEXT_ATTACK));
            getCombatMethod().prepareAttack(this, null);
        }
        if (--specialDelta == -10) {
            System.out.println("special delta -10");
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
        Chain.bound(null).runFn(1, () -> {
            this.teleport(dest);
            System.out.println("teleport to dest: "+dest);
        }).then(1, () -> {
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
        if (id() == THE_NIGHTMARE_9431 && id != 8604 && id > 0) {
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

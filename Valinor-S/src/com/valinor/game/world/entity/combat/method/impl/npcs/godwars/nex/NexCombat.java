package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.ForceMovementTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.Direction;
import com.valinor.game.world.entity.mob.Flag;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.NpcCombatInfo;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.DumbRoute;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;

import static com.valinor.util.NpcIdentifiers.NEX_11282;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 12, 2022
 */
public class NexCombat extends CommonCombatMethod {

    /*~32 Magic
~60 Ranged
~29 Melee
50 Smoke Bullet special
50 Shadow Smash special
80 Blood Sacrifice special
60 Containment special
75 Ice Prison special*/

    private int attackCount;

    private static final int MELEE_ATTACK_ANIM = 9180;
    private static final int VIRUS_ATTACK_ANIM = 9189;
    private static final int MAGIC_ATTACK_ANIM = 9189;//Shared animation

    public static final Area NEX_AREA = new Area(2910, 5189, 2939, 5217);

    public static Tile[] NO_ESCAPE_TELEPORTS = { new Tile(2924, 5213, 0), // north
        new Tile(2934, 5202, 0), // east,
        new Tile(2924, 5192, 0), // south
        new Tile(2913, 5202, 0), }; // west

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if(mob.isNpc()) {
            Npc npc = mob.getAsNpc();
            final Nex nex = (Nex) npc;
            attackCount += 1;
            if (nex.getAttacksStage() == 0) {
                System.out.println("Attack stage 0");
                //First attack
                if (nex.getLastVirus() < Utils.currentTimeMillis() && attackCount == 1) {
                    virusAttack(nex);
                    System.out.println("Virus attack");
                //After 3 attacks shadow bullet
                } else if(attackCount == 4) {
                    dragAttack(nex);
                    System.out.println("Drag attack");
                //After 5 attacks shadow bullet
                } else if(attackCount == 6) {
                    attackCount = 0; // reset attack count
                    System.out.println("reset count "+attackCount);
                    smokeBulletAttack(nex, target);
                    System.out.println("Smoke bullet attack");
                } else {
                    System.out.println("Normal attack");
                    //Otherwise normal attacks
                    if (CombatFactory.canReach(nex, CombatFactory.MELEE_COMBAT, target)) {
                        meleeAttack(nex, target);
                    } else {
                        smokeRushAttack(nex);
                    }
                }
            }
            System.out.println("attk count "+attackCount);
        }
    }

    private void smokeBulletAttack(Nex nex, Mob target) {
        nex.forceChat("There is...");
        nex.cantInteract(true);
        nex.getCombat().reset();
        System.out.println("cmb reset 1212122");
        final int idx = Utils.random(NO_ESCAPE_TELEPORTS.length);
        final Tile selectedTile = NO_ESCAPE_TELEPORTS[idx];
        final Tile center = new Tile(2924, 5202, 0);

        //https://youtu.be/dTBsVyNLRFo?t=261

        //04:23 there is
        //04:26 face direction
        //04:27 animation + teleport to tile (arrived at 04:29)
        //04:29 forcemovement to center + no escape force chat
        //04:32 arrives at center
        //04:33 selects new target
        Chain.bound(null).runFn(4, () -> {
            nex.animate(9178);
            nex.face(selectedTile);
        }).then(6, () -> {
            nex.teleport(selectedTile);
            nex.faceEntity(null);
        }).then(1, () -> { // lands
            nex.forceChat("NO ESCAPE!");
            //Look for potential victims
            for (Mob p : nex.calculatePossibleTargets(center, selectedTile, idx == 0 || idx == 2)) {
                if (p instanceof Player) {
                    p.hit(nex, Utils.getRandom(50));
                    Chain.bound(null).runFn(1, () -> {
                        p.lock();
                        p.animate(1157);
                        p.graphic(245, 5, 124);
                        p.hit(p, 3);
                        p.stun(2, true);
                        int diffX = center.x - p.getAbsX();
                        int diffY = center.y - p.getAbsY();
                        TaskManager.submit(new ForceMovementTask(p.getAsPlayer(), 3, new ForceMovement(p.tile().clone(), new Tile(diffX, diffY), 10, 60, idx == 3 ? 3 : idx == 2 ? 2 : idx == 1 ? 1 : 0)));
                        p.unlock();
                    });
                }
            }
            nex.animate(9178);
            Tile dif = new Tile( center.x-selectedTile.x, center.y - selectedTile.y);
            final ForceMovement fm = new ForceMovement(selectedTile, dif, 50, 80, 3);
            nex.setForceMovement(fm);
            nex.getUpdateFlag().flag(Flag.FORCED_MOVEMENT);
            Chain.bound(null).then(3, () -> nex.teleport(center)); //Update nex on map
        }).then(8, () -> {
            nex.getCombat().setTarget(Utils.randomElement(getPossibleTargets(nex)));
            nex.cantInteract(false);
        });
    }

    private void dragAttack(Nex nex) {
        Mob target = Utils.randomElement(getPossibleTargets(nex));
        if(target.isPlayer()) {

            int vecX = (nex.getAbsX() - Utils.getClosestX(nex, target.tile()));
            int vecY = (nex.getAbsY() - Utils.getClosestY(nex, target.tile()));
            int endX = nex.getAbsX();
            int endY = nex.getAbsY();
            for (int i = 0; i < 4; i++) {
                if (DumbRoute.getDirection(endX, endY, nex.getZ(), target.getSize(), endX + vecX, endY + vecY) != null) { // we can take this step!
                    endX += vecX;
                    endY += vecY;
                } else
                    break; // cant take the step, stop here
            }
            Direction dir;
            if (vecX == -1)
                dir = Direction.EAST;
            else if (vecX == 1)
                dir = Direction.WEST;
            else if (vecY == -1)
                dir = Direction.NORTH;
            else
                dir = Direction.SOUTH;

            int finalEndX = endX;
            int finalEndY = endY;
            Chain.bound(null).runFn(1, () -> {
                final Player p = target.getAsPlayer();
                p.lock();
                p.animate(1157);
                p.graphic(1998, 5, 124);
                p.hit(mob, World.getWorld().random(30));
                p.stun(2, true);
                int diffX = finalEndX - p.getAbsX();
                int diffY = finalEndY - p.getAbsY();
                TaskManager.submit(new ForceMovementTask(p, 3, new ForceMovement(p.tile().clone(), new Tile(diffX, diffY), 10, 60, dir.toInteger())));
                p.unlock();
            });
        }
    }

    private void virusAttack(Nex nex) {
        nex.animate(VIRUS_ATTACK_ANIM);
        nex.setLastVirus(Utils.currentTimeMillis() + 60000);
        nex.forceChat("Let the virus flow through you.");
        sendVirusAttack(nex, new ArrayList<>(), getPossibleTargets(nex), target);
    }

    public void sendVirusAttack(Nex nex, ArrayList<Mob> hit, ArrayList<Mob> possibleTargets, Mob infected) {
        for (Mob t : possibleTargets) {
            if (hit.contains(t))
                continue;
            if (Utils.getDistance(t.getX(), t.getY(), infected.getX(), infected.getY()) <= 1) {
                t.forceChat("*Cough*");
                t.hit(nex, World.getWorld().random(10));
                t.skills().alterSkill(Skills.PRAYER,-2);
                hit.add(t);
                sendVirusAttack(nex, hit, possibleTargets, infected);
            }
        }
    }

    private void smokeRushAttack(Nex nex) {
        nex.putAttrib(AttributeKey.MAXHIT_OVERRIDE, 32);
        nex.animate(MAGIC_ATTACK_ANIM);
        for(Mob t : getPossibleTargets(nex)) {
            Projectile projectile = new Projectile(nex, t, 384, 0, 100, 43, 31, 0);
            projectile.sendProjectile();
            if (World.getWorld().rollDie(100, 25)) {
                t.poison(2);
            }
            t.hit(nex, World.getWorld().random(13),3, CombatType.MAGIC).graphic(new Graphic(385)).checkAccuracy().submit();
        }
        nex.putAttrib(AttributeKey.MAXHIT_OVERRIDE, -1);
    }

    private void meleeAttack(Nex nex, Mob target) {
        nex.putAttrib(AttributeKey.MAXHIT_OVERRIDE, 29);
        nex.animate(MELEE_ATTACK_ANIM);
        target.hit(nex, CombatFactory.calcDamageFromType(nex, target, CombatType.MELEE),1, CombatType.MELEE).checkAccuracy().submit();
        nex.putAttrib(AttributeKey.MAXHIT_OVERRIDE, -1);
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 20;
    }

    @Override
    public boolean customOnDeath(Mob mob) {
        if(mob.isNpc()) {
            Npc npc = mob.getAsNpc();
            npc.transmog(NEX_11282);
            final NpcCombatInfo combatInfo = npc.combatInfo();
            TaskManager.submit(new Task("NexCombatDeathTask1",1) {
                int ticks;
                @Override
                public void execute() {
                    if (ticks == 0) {
                        npc.animate(combatInfo.animations.death);
                    } else if (ticks >= combatInfo.deathlen) {
                        npc.remove();
                        ZarosGodwars.end();
                        stop();
                    }
                    ticks++;
                }
            });
            npc.forceChat("Taste my wrath!");
            TaskManager.submit(new Task("NexCombatDeathTask2",5) {
                @Override
                public void execute() {
                    npc.graphic(2013);
                    ArrayList<Mob> possibleTargets = getPossibleTargets(mob);
                    if (possibleTargets != null) {
                        for (Mob mob : possibleTargets) {
                            if (mob == null || mob.dead() || !mob.isRegistered() || !mob.tile().isWithinDistance(npc.tile(), 10))
                                continue;
                            Projectile projectile = new Projectile(target.tile(), new Tile(npc.getX() + 2, npc.getY() + 2, npc.getZ()), -1, 2014, 41, 35, 24, 0, 0);
                            projectile.sendProjectile();
                            Projectile projectile2 = new Projectile(target.tile(), new Tile(npc.getX() + 2, npc.getY(), npc.getZ()),-1, 2014, 41, 35, 24, 0, 0);
                            projectile2.sendProjectile();
                            Projectile projectile3 = new Projectile(target.tile(), new Tile(npc.getX() + 2, npc.getY() - 2, npc.getZ()),-1, 2014, 41, 35, 24, 0, 0);
                            projectile3.sendProjectile();
                            Projectile projectile4 = new Projectile(target.tile(), new Tile(npc.getX() - 2, npc.getY() + 2, npc.getZ()),-1, 2014, 41, 35, 24, 0, 0);
                            projectile4.sendProjectile();
                            Projectile projectile5 = new Projectile(target.tile(), new Tile(npc.getX() - 2, npc.getY(), npc.getZ()), -1, 2014, 41, 35, 24, 0, 0);
                            projectile5.sendProjectile();
                            Projectile projectile6 = new Projectile(target.tile(), new Tile(npc.getX() - 2, npc.getY() - 2, npc.getZ()),-1, 2014, 41, 35, 24, 0, 0);
                            projectile6.sendProjectile();
                            Projectile projectile7 = new Projectile(target.tile(), new Tile(npc.getX(), npc.getY() + 2, npc.getZ()),-1, 2014, 41, 35, 24, 0, 0);
                            projectile7.sendProjectile();
                            Projectile projectile8 = new Projectile(target.tile(), new Tile(npc.getX(), npc.getY() - 2, npc.getZ()), -1, 2014, 41, 35, 24, 0, 0);
                            projectile8.sendProjectile();
                            mob.hit(npc, World.getWorld().random(40));
                        }
                    }
                }
            });
        }
        return true;
    }


}

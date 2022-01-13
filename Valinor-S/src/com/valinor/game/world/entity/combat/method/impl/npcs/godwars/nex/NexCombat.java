package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.NpcCombatInfo;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;

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

    private static final int MELEE_ATTACK_ANIM = 9180;
    private static final int VIRUS_ATTACK_ANIM = 9189;
    private static final int MAGIC_ATTACK_ANIM = 9189;//Shared animation

    public static final Area NEX_AREA = new Area(2910, 5189, 2939, 5217);

    public Tile[] NO_ESCAPE_TELEPORTS = { new Tile(2924, 5213, 0), // north
        new Tile(2934, 5202, 0), // east,
        new Tile(2924, 5192, 0), // south
        new Tile(2913, 5202, 0), }; // west

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if(mob.isNpc()) {
            Npc npc = mob.getAsNpc();
            final Nex nex = (Nex) npc;

            if (nex.getAttacksStage() == 0) {
                if (nex.getLastVirus() < Utils.currentTimeMillis()) {
                    virusAttack(nex);
                }

                if (CombatFactory.canReach(nex, CombatFactory.MELEE_COMBAT, target)) {
                    meleeAttack(nex, target);
                } else {
                    smokeRushAttack(nex);
                }
            }
        }
    }

    private void virusAttack(Nex nex) {
        nex.animate(VIRUS_ATTACK_ANIM);
        nex.setLastVirus(Utils.currentTimeMillis() + 60000);
        nex.forceChat("Let the virus flow through you.");
        sendVirusAttack(nex, new ArrayList<>(), getPossibleTargets(), target);
    }

    public void sendVirusAttack(Nex nex, ArrayList<Mob> hit, ArrayList<Mob> possibleTargets, Mob infected) {
        for (Mob t : possibleTargets) {
            if (hit.contains(t))
                continue;
            if (Utils.getDistance(t.getX(), t.getY(), infected.getX(), infected.getY()) <= 1) {
                t.forceChat("*Cough*");
                t.hit(nex, World.getWorld().random(10));
                hit.add(t);
                sendVirusAttack(nex, hit, possibleTargets, infected);
            }
        }
    }

    private void smokeRushAttack(Nex nex) {
        nex.putAttrib(AttributeKey.MAXHIT_OVERRIDE, 32);
        nex.animate(MAGIC_ATTACK_ANIM);
        for(Mob t : getPossibleTargets()) {
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
                    ArrayList<Mob> possibleTargets = getPossibleTargets();
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

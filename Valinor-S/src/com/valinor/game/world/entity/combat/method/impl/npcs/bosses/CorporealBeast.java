package com.valinor.game.world.entity.combat.method.impl.npcs.bosses;

import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

public class CorporealBeast extends CommonCombatMethod {

    public static final Area CORPOREAL_BEAST_AREA = new Area(2974, 4371, 2998, 4395);

    private Task stompTask;
    private void checkStompTask(Mob mob, Mob target) {
        if (stompTask == null) {
            stompTask = new Task("checkStompTask",7) {
                @Override
                public void execute() {
                    if (mob.dead() || !mob.isRegistered() || !target.tile().inArea(CORPOREAL_BEAST_AREA)) {
                        stop();
                        return;
                    }
                    World.getWorld().getPlayers().forEachInArea(CORPOREAL_BEAST_AREA, p -> {
                        if (p.boundaryBounds().inside(mob.tile(), mob.getSize())) {
                            stompAttack(mob.getAsNpc(), p);
                        }
                    });
                }

                @Override
                public void onStop() {
                    mob.getCombat().reset();
                }
            }.bind(mob);
            TaskManager.submit(stompTask);
        }
    }

    /**
     * If the player steps under the Corporeal Beast, it may perform a stomp attack that will always deal 30–51 damage.
     * This attack is on a timer that checks if any players are under the Corporeal Beast every 7 ticks (4.2 seconds). OK
     */
    public void stompAttack(Npc corp, Player player) {
        int maxHit = Utils.random(31, 51);

        corp.animate(1686);
        player.hit(corp, maxHit, 1, CombatType.MELEE).checkAccuracy().submit();
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        checkStompTask(mob, target);
        if (World.getWorld().rollDie(10, 6)) {
            if (withinDistance(1) && World.getWorld().rollDie(10, 6)) {
                meleeAttack(mob, target);
            } else {
                fireBasic(mob, target);
            }
        } else if (World.getWorld().rollDie(3, 2)) {
            firePowered(mob, target);
        } else {
            fireSplit(mob, target);
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 20;
    }

    private void stat_draining_magic_attack(Mob target) {
        Player player = (Player) target;
        //Check to see if we apply the 'special' effect.
        if (Utils.rollDie(3, 1)) {
            int reduction = Utils.random(3);
            //Decide if we reduce their magic or prayer.
            if (Utils.rollDie(2, 1)) {
                if (player.skills().level(Skills.MAGIC) < reduction) {
                    target.skills().setLevel(Skills.MAGIC, 0);
                } else {
                    player.skills().setLevel(Skills.MAGIC, player.skills().level(Skills.MAGIC) - reduction);
                }
                player.message("Your Magic has been slightly drained.");
            } else if (player.skills().level(Skills.PRAYER) > reduction) {
                if (player.skills().level(Skills.PRAYER) < reduction) {
                    player.skills().setLevel(Skills.PRAYER, 0);
                } else {
                    player.skills().setLevel(Skills.PRAYER, player.skills().level(Skills.PRAYER) - reduction);
                }
                player.message("Your Prayer has been slightly drained.");
            }
        }
    }

    private void meleeAttack(Mob mob, Mob target) {
        mob.animate(1682);
        mob.getAsNpc().combatInfo().maxhit = 33;
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target,CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
    }

    private void fireBasic(Mob mob, Mob target) {
        mob.animate(1680);
        mob.getAsNpc().combatInfo().maxhit = 55;
        Projectile  projectile = new Projectile(mob, target, 314, 15, 66, 65, 31, 0);
        projectile.sendProjectile();
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target,CombatType.MAGIC), 2, CombatType.MAGIC).checkAccuracy().submit();
        stat_draining_magic_attack(target);
    }

    private void firePowered(Mob mob, Mob target) {
        mob.animate(1681);
        Projectile  projectile = new Projectile(mob, target, 316, 15, 66, 65, 31, 0);
        projectile.sendProjectile();

        if (Prayers.usingPrayer(target, Prayers.PROTECT_FROM_MAGIC)) {
            target.hit(mob, World.getWorld().random(10, 35), 2);
        } else {
            mob.getAsNpc().combatInfo().maxhit = 65;
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), 2, CombatType.MAGIC).checkAccuracy().submit();
        }
    }

    private void fireSplit(Mob mob, Mob target) {
        mob.animate(1681);
        Projectile  projectile = new Projectile(mob.getCentrePosition(), target.tile(), 1,315, 66, 15, 65, 31, 0);
        projectile.sendProjectile();

        mob.getAsNpc().combatInfo().maxhit = 42;
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), 2, CombatType.MAGIC).checkAccuracy().submit();

        final Mob t = target;
        Tile[] targets = new Tile[5];
        Chain.bound(null).runFn(2, () -> {
            Tile src = t.tile();
            for (int i = 0; i < targets.length; i++) {
                targets[i] = new Tile(src.getX() + World.getWorld().get(-2, 2), src.getY() + World.getWorld().get(-2, 2), src.getZ());
                Projectile split = new Projectile(mob.getCentrePosition(), target.tile(), 1,315, 60, 0, 0, 0, 0);
                split.sendProjectile();
            }
        }).then(2, () -> {
            for (Tile tileGraphic : targets) {
                World.getWorld().tileGraphic(317, tileGraphic, 0, 0);
            }

            World.getWorld().getPlayers().forEachInArea(CORPOREAL_BEAST_AREA, player -> {
                if (!CombatFactory.canAttack(mob, player)) {
                    return;
                }
                for (Tile tile : targets) {
                    if (player.isAt(tile.getX(), tile.getY())) {
                        target.hit(mob, World.getWorld().random(15, 35));
                    }
                }
            });
        });
    }
}

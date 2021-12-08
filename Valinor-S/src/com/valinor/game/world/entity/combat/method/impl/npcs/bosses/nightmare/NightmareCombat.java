package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.ProjectileRoute;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 28, 2021
 */
public class NightmareCombat extends CommonCombatMethod {

    private static final int MAGIC_ANIMATION = 8595, RANGE_ANIMATION = 8596, MAGIC_PROJECTILE = 1764, RANGED_PROJECTILE = 1766;

    private SpecialAttacks special;

    private Task restoreTask;
    private void checkRestoreTask() {
        if (restoreTask == null) {
            restoreTask = new Task("checkRestoreTask",3) {
                @Override
                public void execute() {
                    if (mob.dead() || !mob.isRegistered() && (mob.getLocalPlayers().isEmpty() || mob.getLocalPlayers().stream().noneMatch(p -> ProjectileRoute.allow(mob, p)))) {
                        stop();
                        restore();
                    }
                }

                @Override
                public void onStop() {
                    mob.getCombat().reset();
                }
            }.bind(mob);
            TaskManager.submit(restoreTask);
        }
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        checkRestoreTask();

        Nightmare nightmare = (Nightmare) mob;

        /*
         * Special attack.
         */
        if (special != null) {
            nightmare.animate(special.animation);
            special.run(nightmare);
            special = null;
            return;
        }

        /*
         * Default attack.
         */
        if (World.getWorld().random(10) > 5) {
            for (Mob victim : nightmare.getPossibleTargets(64, true, false)) {
                nightmare.animate(MAGIC_ANIMATION);
                nightmare.getCombat().setTarget(victim);
                nightmare.runFn(1, () -> {
                    Projectile pr = new Projectile(nightmare, victim, MAGIC_PROJECTILE, 30, 66, 110, 90, 0);
                    pr.sendProjectile();
                });
                final Tile dest = victim.tile();
                int delay = 60;
                if (nightmare.getCentrePosition().distance(dest) == 1) {
                    delay = 60;
                } else if (nightmare.getCentrePosition().distance(dest) <= 5) {
                    delay = 80;
                } else if (nightmare.getCentrePosition().distance(dest) <= 8) {
                    delay = 100;
                } else {
                    delay = 120;
                }
                victim.hit(nightmare, CombatFactory.calcDamageFromType(nightmare, victim, CombatType.MAGIC), (delay / 20) - 1, CombatType.MAGIC).checkAccuracy().submit();
            }
        } else {
            for (Mob victim : nightmare.getPossibleTargets(64, true, false)) {
                int delay = 60;
                nightmare.animate(RANGE_ANIMATION);
                nightmare.getCombat().setTarget(victim);
                nightmare.runFn(1, () -> {
                    Projectile pr = new Projectile(nightmare, victim, RANGED_PROJECTILE, 30, 66, 110, 90, 0);
                    pr.sendProjectile();
                });
                final Tile dest = victim.tile();
                if (nightmare.getCentrePosition().distance(dest) == 1) {
                    delay = 60;
                } else if (nightmare.getCentrePosition().distance(dest) <= 5) {
                    delay = 80;
                } else if (nightmare.getCentrePosition().distance(dest) <= 8) {
                    delay = 100;
                } else {
                    delay = 120;
                }
                victim.hit(nightmare, CombatFactory.calcDamageFromType(nightmare, victim, CombatType.RANGED), (delay / 20) - 1, CombatType.RANGED).checkAccuracy().submit();
            }
        }
    }

    public void setSpecial(SpecialAttacks attack) {
        special = attack;
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;
    }

    @Override
    public void onDeath() {
        target.message("You have defeated The Nightmare!");
        target.message("You can leave by using the energy barrier.");
    }
}

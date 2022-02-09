package com.valinor.game.content.seasonal_events.winter.boss;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.util.chainedwork.Chain;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 09, 2022
 */
public class SnowflakeCombatScript extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if(World.getWorld().rollDie(5, 1)) {
            rangeAttack(mob, target);
        } else {
            magicAttack(mob, target);
        }
    }

    private void magicAttack(Mob mob, Mob target) {
        mob.animate(8158);
        mob.resetFaceTile(); // Stop facing the target
        World.getWorld().getPlayers().forEach(p -> {
            if(p != null && target.tile().inSqRadius(p.tile(),12)) {
                var delay = mob.getProjectileHitDelay(target);
                new Projectile(mob, p, 1223, 32, mob.projectileSpeed(target), 30, 30, 0).sendProjectile();
                p.hit(mob, CombatFactory.calcDamageFromType(mob, p, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
            }
        });

        mob.face(target.tile()); // Go back to facing the target.
    }

    private void rangeAttack(Mob mob, Mob target) {
        mob.animate(8159);
        mob.resetFaceTile(); // Stop facing the target
        Chain.bound(null).runFn(2, () -> {
            World.getWorld().getPlayers().forEach(p -> {
                if(p != null && target.tile().inSqRadius(p.tile(),12)) {
                    var delay = mob.getProjectileHitDelay(target);
                    new Projectile(mob, p, 856, 32, mob.projectileSpeed(target), 30, 30, 0).sendProjectile();
                    p.hit(mob, CombatFactory.calcDamageFromType(mob, p, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().submit();
                }
            });

            mob.face(target.tile()); // Go back to facing the target.
        });
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 10;
    }
}

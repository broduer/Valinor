package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.custom;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 07, 2022
 */
public class AragogWilderness extends CommonCombatMethod {

    private void webAttack(Mob mob, Mob target) {
        //mob.forceChat("WEB -> RANGED ATTACK");

        var tileDist = mob.tile().transform(3, 3, 0).distance(target.tile());
        new Projectile(mob, target, 1254, 20, 12 * tileDist, 30, 30, 0, 14, 5).sendProjectile();
        var delay = Math.max(1, (20 + (tileDist * 12)) / 30);
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.RANGED).checkAccuracy().submit();
    }

    private void fireSkullAttack(Mob mob, Mob target) {
        //mob.forceChat("FIRE SKULL -> MAGIC ATTACK");

        var tileDist = mob.tile().transform(3, 3, 0).distance(target.tile());
        var delay = Math.max(1, (20 + (tileDist * 12)) / 30);
        new Projectile(mob, target, 88, 20, 12 * tileDist, 30, 30, 0, 14, 5).sendProjectile();
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
    }

    private void meleeAttack(Mob mob, Mob target) {
        //mob.forceChat("MELEE ATTACK");
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
        mob.animate(mob.attackAnimation());
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        // Determine if we're going to melee or mage
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            int chance = World.getWorld().random(6);
            if (chance == 1) {
                fireSkullAttack(mob, target);
            } else if (chance == 2) {
                webAttack(mob, target);
            } else {
                meleeAttack(mob, target);
            }
        } else {
            int chance = World.getWorld().random(3);
            if (chance == 1) {
                fireSkullAttack(mob, target);
            } else {
                webAttack(mob, target);
            }
            // Do an animation..
            mob.animate(5322);
        }
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

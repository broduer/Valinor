package com.valinor.game.content.seasonal_events.winter.boss;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 09, 2021
 */
public class WampaCombatScript extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (withinDistance(2) && World.getWorld().rollDie(5, 1)) {
            meleeAttack(mob, target);
        } else {
            magicAttack(mob, target);
        }
    }

    private void meleeAttack(Mob mob, Mob target) {
        if(World.getWorld().rollDie(3, 1)) {
            target.hit(mob, World.getWorld().random(5, 12));
            mob.animate(5724);
        } else {
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
            mob.animate(5725);
        }
    }

    private void magicAttack(Mob mob, Mob target) {
        mob.animate(8533);
        var delay = mob.getProjectileHitDelay(target);
        new Projectile(mob, target, 2006, 32, mob.projectileSpeed(target), 30, 30, 0).sendProjectile();
        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().graphic(new Graphic(2005));
        hit.submit();
        if(hit.isAccurate()) {
            target.freeze(10, mob);
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

package com.valinor.game.world.entity.combat.method.impl.npcs.barrows;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;

public class Karils extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        var tileDist = mob.tile().transform(3, 3, 0).distance(target.tile());

        new Projectile(mob, target, 27, 41, 12 * tileDist, 30, 30, 0, 10, 15).sendProjectile();
        var delay = Math.max(1, 20 + tileDist * 12 / 30);
        if (delay > 2)
            delay = 2;
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().submit();
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getAsNpc().getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 6;
    }
}

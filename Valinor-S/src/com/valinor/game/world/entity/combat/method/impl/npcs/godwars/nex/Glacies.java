package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 13, 2022
 */
public class Glacies extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
        var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
        Projectile projectile = new Projectile(mob, target, 368, 0, 66, 43, 0, 0);
        projectile.sendProjectile();
        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC);
        hit.checkAccuracy().submit();
        if(hit.isAccurate()) {
            target.graphic(369);
            target.freeze(33, mob);
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 8;
    }
}

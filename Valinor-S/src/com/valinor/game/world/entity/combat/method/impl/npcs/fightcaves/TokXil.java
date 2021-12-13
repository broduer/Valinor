package com.valinor.game.world.entity.combat.method.impl.npcs.fightcaves;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 13, 2021
 */
public class TokXil extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            if (World.getWorld().rollDie(2, 1))
                meleeAttack(mob, target);
            else rangedAttack(mob, target);
        } else {
            rangedAttack(mob, target);
        }
    }

    private void meleeAttack(Mob mob, Mob target) {
        mob.getAsNpc().combatInfo().maxhit = 14;
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
        mob.animate(mob.attackAnimation());
    }

    private void rangedAttack(Mob mob, Mob target) {
        mob.getAsNpc().combatInfo().maxhit = 14;

        var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
        var delay = Math.max(1, (50 + (tileDist * 12)) / 30);

        mob.face(target.tile());
        mob.animate(2633);
        Projectile projectile = new Projectile(mob, target, 443, 30, 12 * tileDist, 56, 36, 0);
        projectile.sendProjectile();

        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().submit();
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 14;
    }
}

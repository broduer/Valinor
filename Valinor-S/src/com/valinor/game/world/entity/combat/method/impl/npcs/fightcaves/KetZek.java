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
public class KetZek extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            if (World.getWorld().rollDie(2, 1))
                meleeAttack(mob, target);
            else magicAttack(mob, target);
        } else {
            magicAttack(mob, target);
        }
    }

    private void meleeAttack(Mob mob, Mob target) {
        mob.getAsNpc().combatInfo().maxhit = 54;
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
        mob.animate(mob.attackAnimation());
    }

    private void magicAttack(Mob mob, Mob target) {
        mob.getAsNpc().combatInfo().maxhit = 49;

        var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
        var delay = Math.max(1, (50 + (tileDist * 12)) / 30);

        mob.face(target.tile());
        mob.animate(2647);
        Projectile projectile = new Projectile(mob.tile().transform(2, 2), target, 445, 30, 12 * tileDist, 125, 36, 0);
        projectile.sendProjectile();

        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
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

package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cos;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;

/**
 * @author Patrick van Elderen | May, 11, 2021, 12:37
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class Dementor extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if(CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            meleeAttack(mob, target);//Melee attack ignores prayer
        } else if(World.getWorld().rollDie(2,1)) {
            magicAttack(mob);
        } else if(World.getWorld().rollDie(10,1) && !target.dead()) {
            stealGoodMemories(mob, target);
        }
    }

    private void meleeAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
    }

    private void magicAttack(Mob mob) {
        //mob.forceChat("MAGIC ATTACK");
        for (Mob t : getPossibleTargets(mob, 20, true,false)) {
            var tileDist = mob.tile().transform(3, 3, 0).distance(t.tile());
            var delay = Math.max(1, (20 + (tileDist * 12)) / 30);
            mob.graphic(194);
            new Projectile(mob, t, 195, 20, 12 * tileDist, 35, 30, 0).sendProjectile();
            t.hit(mob, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
            t.delayedGraphics(196, 0, delay);
        }
        mob.animate(mob.attackAnimation());
    }

    private void stealGoodMemories(Mob mob, Mob target) {
        mob.forceChat("STEAL MEMORIES!");
        mob.animate(5543);

        var tileDist = mob.tile().transform(3, 3, 0).distance(target.tile());
        var delay = Math.max(1, (20 + (tileDist * 12)) / 30);
        new Projectile(mob, target, 1382, 20, 12 * tileDist, 35, 30, 0).sendProjectile();

        final Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC);
        hit.checkAccuracy().submit();

        mob.heal(hit.getDamage());
        mob.graphic(1423);

        target.animate(2046);
        target.graphic(1433);
        target.message("You feel all your memories fade away.");
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

package com.valinor.game.world.entity.combat.method.impl.npcs.fightcaves;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 13, 2021
 */
public class YtMejkot extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            if (World.getWorld().rollDie(3, 1)) {
                attemptHeal(mob);
            } else {
                target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
                mob.animate(mob.attackAnimation());
            }
        }
    }

    private void attemptHeal(Mob mob) {
        var currentHP = mob.hp();

        //If our monster is below 50% hitpoints we heal ourselves and those around us.
        if (currentHP < 80) {
            mob.heal(World.getWorld().random(10));
            mob.graphic(444, 150, 5);
            mob.animate(2639);
            World.getWorld().getNpcs().forEachInArea(mob.bounds(3), monster -> {
                monster.heal(World.getWorld().random(10));
            });
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;
    }
}

package com.valinor.game.world.entity.combat.method.impl.npcs.bosses;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;

public class Barrelchest extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        meleeAttack(mob, target);
    }

    private void meleeAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        if(World.getWorld().rollDie(2,1)) {
            target.hit(mob, World.getWorld().random(1, mob.getAsNpc().combatInfo().maxhit / 2));
        } else {
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
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

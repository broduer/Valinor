package com.valinor.game.world.entity.combat.method.impl.npcs.fightcaves;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.player.Skills;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 13, 2021
 */
public class TzKih extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
            mob.animate(mob.attackAnimation());
            target.skills().alterSkill(Skills.PRAYER, -1);
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

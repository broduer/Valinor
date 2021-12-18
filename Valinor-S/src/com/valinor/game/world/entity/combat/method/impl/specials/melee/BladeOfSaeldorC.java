package com.valinor.game.world.entity.combat.method.impl.specials.melee;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.player.Skills;

public class BladeOfSaeldorC extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.animate(7515);
        mob.graphic(1695);
        target.graphic(1732);
        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE),1, CombatType.MELEE).checkAccuracy();
        hit.submit();

        if(target.isPlayer()) {
            var heal = hit.getDamage() * 25 / 100;
            mob.heal(heal);
            mob.skills().alterSkill(Skills.PRAYER, heal);
            mob.message("Your hitpoints and prayer points have been healed by: "+heal+"!");
        }

        CombatSpecial.drain(mob, CombatSpecial.BLADE_OF_SAELDOR_C.getDrainAmount());
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

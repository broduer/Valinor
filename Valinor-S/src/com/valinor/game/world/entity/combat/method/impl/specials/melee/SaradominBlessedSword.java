package com.valinor.game.world.entity.combat.method.impl.specials.melee;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;

public class SaradominBlessedSword extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.animate(1133);
        mob.graphic(1213, 100, 0);

        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC),1, CombatType.MAGIC).checkAccuracy();
        hit.submit();

        target.graphic(1196, 30, 0);
        CombatSpecial.drain(mob, CombatSpecial.BLESSED_SARADOMIN_SWORD.getDrainAmount());
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

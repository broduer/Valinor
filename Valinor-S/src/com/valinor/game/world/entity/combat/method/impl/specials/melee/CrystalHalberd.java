package com.valinor.game.world.entity.combat.method.impl.specials.melee;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;

public class CrystalHalberd extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.animate(1203);
        mob.graphic(1235, 80, 0);

        int h1 = CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE);
        int h2 = CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE);

        if(target.getSize() == 1) {
            Hit hit = target.hit(mob, h1,1, CombatType.MELEE).checkAccuracy();
            hit.submit();
        } else {
            Hit hit = target.hit(mob, h1,1, CombatType.MELEE).checkAccuracy();
            hit.submit();
            Hit hit2 = target.hit(mob, h2,1, CombatType.MELEE).checkAccuracy();
            hit2.submit();
        }
        CombatSpecial.drain(mob, CombatSpecial.CRYSTAL_HALBERD.getDrainAmount());
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 3;
    }
}

package com.valinor.game.world.entity.combat.method.impl.specials.magic;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.magic.CombatSpells;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 06, 2022
 */
public class CursedNMS extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.graphic(5008);
        mob.animate(8532);

        mob.getCombat().setCastSpell(CombatSpells.CURSED_NIGHTMARE_STAFF.getSpell());
        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), 3, CombatType.MAGIC).checkAccuracy();
        hit.submit();
        if(hit.isAccurate()) {
            target.graphic(5007);
        }
        //Reset spell
        mob.getCombat().setCastSpell(null);

        //Drain spec after the attack
        CombatSpecial.drain(mob, CombatSpecial.CURSED_NIGHTMARE_STAFF.getDrainAmount());
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 10;
    }
}

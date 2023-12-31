package com.valinor.game.world.entity.combat.method.impl.specials.melee;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;

/**
 * @author Patrick van Elderen | November, 28, 2020, 19:19
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class GraniteHammer extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.animate(1378);
        mob.graphic(1450);

        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE) + 5,1, CombatType.MELEE).checkAccuracy();
        hit.submit();
        CombatSpecial.drain(mob, CombatSpecial.GRANITE_HAMMER.getDrainAmount());
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

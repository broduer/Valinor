package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cos.aragog;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;

/**
 * @author Patrick van Elderen | April, 07, 2021, 18:54
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class AragogMinion extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
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

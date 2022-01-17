package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.player.Skills;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 13, 2022
 */
public class Umbra extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        if (target.isPlayer()) {
            Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), 2, CombatType.MAGIC);
            hit.checkAccuracy().submit();
            if (hit.isAccurate()) {
                target.graphic(383);

                if (target.skills().level(Skills.ATTACK) < target.skills().xpLevel(Skills.ATTACK)) {
                    return;
                }

                int decrease = (int) (0.15 * (target.skills().level(Skills.ATTACK)));
                target.skills().setLevel(Skills.ATTACK, target.skills().level(Skills.ATTACK) - decrease);
                target.skills().update(Skills.ATTACK);
            }
        }
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

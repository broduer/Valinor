package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.custom;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 21, 2022
 */
public class BloodFuryHespori extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {

    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return 0;
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 0;
    }
}

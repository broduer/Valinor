package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 28, 2021
 */
public class NightmareCombat extends CommonCombatMethod {

    private static final int MAGIC_ANIMATION = 8595, RANGE_ANIMATION = 8596, MAGIC_PROJECTILE = 1764, RANGED_PROJECTILE = 1766;

    private SpecialAttacks special;

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

    public void setSpecial(SpecialAttacks attack) {
        special = attack;
    }
}

package com.valinor.game.world.entity.combat.formula;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public enum MagicMaxitCaps {

    EMPTY(51, 51),

    ;

    public final int maxHit, maxHitSpec;

    MagicMaxitCaps(int maxHit, int maxHitSpec) {
        this.maxHit = maxHit;
        this.maxHitSpec = maxHitSpec;
    }
}

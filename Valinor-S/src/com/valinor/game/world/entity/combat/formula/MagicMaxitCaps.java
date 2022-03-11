package com.valinor.game.world.entity.combat.formula;

import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public enum MagicMaxitCaps {

    ELDRITCH_NIGHTMARE_STAFF(ItemIdentifiers.ELDRITCH_NIGHTMARE_STAFF, 60),
    VOLATILE_NIGHTMARE_STAFF(ItemIdentifiers.VOLATILE_NIGHTMARE_STAFF, 80),
    CURSED_NIGHTMARE_STAFF(CustomItemIdentifiers.CURSED_NIGHTMARE_STAFF, 82)
    ;

    public final int itemId, maxHitSpec;

    MagicMaxitCaps(int itemId, int maxHitSpec) {
        this.itemId = itemId;
        this.maxHitSpec = maxHitSpec;
    }

    public static MagicMaxitCaps forWeapon(int weapon) {
        for (MagicMaxitCaps cap : MagicMaxitCaps.values()) {
            if(cap.itemId == weapon) {
                return cap;
            }
        }
        return null;
    }
}

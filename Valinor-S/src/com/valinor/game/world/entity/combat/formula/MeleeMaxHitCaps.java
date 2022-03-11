package com.valinor.game.world.entity.combat.formula;

import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public enum MeleeMaxHitCaps {

    ABYSSAL_WHIP(ItemIdentifiers.ABYSSAL_WHIP,51, 51),
    ARMADYL_GODSWORD(ItemIdentifiers.ARMADYL_GODSWORD,63, 86),
    ARMADYL_GODSWORD_OR(ItemIdentifiers.ARMADYL_GODSWORD_OR,63, 87),
    ANCIENT_GODSWORD(ItemIdentifiers.ANCIENT_GODSWORD,63, 69),
    BANDOS_GODSWORD(ItemIdentifiers.BANDOS_GODSWORD,63, 75),
    SARADOMIN_GODSWORD(ItemIdentifiers.SARADOMIN_GODSWORD,63, 69),
    ZAMORAK_GODSWORD(ItemIdentifiers.ZAMORAK_GODSWORD,63, 69),
    BANDOS_GODSWORD_OR(ItemIdentifiers.BANDOS_GODSWORD_OR,63, 76),
    SARADOMIN_GODSWORD_OR(ItemIdentifiers.SARADOMIN_GODSWORD_OR,63, 70),
    ZAMORAK_GODSWORD_OR(ItemIdentifiers.ZAMORAK_GODSWORD_OR,63, 70),
    DRAGON_CLAWS(ItemIdentifiers.DRAGON_CLAWS,44, 85),
    DRAGON_CLAWS_OR(CustomItemIdentifiers.DRAGON_CLAWS_OR,44, 86),
    DRAGON_DAGGER(ItemIdentifiers.DRAGON_DAGGER,48, 48),
    DRAGON_DAGGERP_5698(ItemIdentifiers.DRAGON_DAGGERP_5698,48, 48),
    DRAGON_DAGGERP_5680(ItemIdentifiers.DRAGON_DAGGERP_5680,48, 48),
    DRAGON_MACE(ItemIdentifiers.DRAGON_MACE,46, 69),
    DRAGON_LONGSWORD(ItemIdentifiers.DRAGON_LONGSWORD,50, 62),
    DRAGON_SCIMITAR(ItemIdentifiers.DRAGON_SCIMITAR,49, 62),
    DRAGON_2H_SWORD(ItemIdentifiers.DRAGON_2H_SWORD,53, 53),
    GHRAZI_RAPIER(ItemIdentifiers.GHRAZI_RAPIER,54, 54),
    ELDER_MAUL(ItemIdentifiers.ELDER_MAUL,66, 66),
    DARK_ELDER_MAUL(CustomItemIdentifiers.DARK_ELDER_MAUL,67, 67),
    GRANITE_MAUL_24225(ItemIdentifiers.GRANITE_MAUL_24225,50, 50),
    GRANITE_MAUL_OR(ItemIdentifiers.GRANITE_MAUL_12848,51, 51),
    INQUISITORS_MACE(ItemIdentifiers.INQUISITORS_MACE,54, 54),
    DRAGON_WARHAMMER(ItemIdentifiers.DRAGON_WARHAMMER,53, 79),
    STATIUSS_WARHAMMER(ItemIdentifiers.STATIUSS_WARHAMMER,60, 75),
    VESTAS_LONGSWORD(ItemIdentifiers.VESTAS_LONGSWORD,61, 73),
    VESTAS_SPEAR(ItemIdentifiers.VESTAS_SPEAR,60, 60),
    NEW_CRYSTAL_HALBERD_FULL(ItemIdentifiers.NEW_CRYSTAL_HALBERD_FULL,59, 64),
    DRAGON_HALBERD(ItemIdentifiers.DRAGON_HALBERD,52, 57),
    DRAGON_HASTA(ItemIdentifiers.DRAGON_HASTA,47, 70),
    KORASI_SWORD(CustomItemIdentifiers.KORASI_SWORD,49, 80),
    ANCIENT_WARRIOR_MAUL(CustomItemIdentifiers.ANCIENT_WARRIOR_MAUL,47, 86),
    ANCIENT_WARRIOR_AXE(CustomItemIdentifiers.ANCIENT_WARRIOR_AXE,60, 86),
    ANCIENT_WARRIOR_MAUL_C(CustomItemIdentifiers.ANCIENT_WARRIOR_MAUL_C,67, 87),
    ANCIENT_WARRIOR_AXE_C(CustomItemIdentifiers.ANCIENT_WARRIOR_AXE_C,60, 87),
    ;

    public final int itemId, maxHit, maxHitSpec;

    MeleeMaxHitCaps(int itemId, int maxHit, int maxHitSpec) {
        this.itemId = itemId;
        this.maxHit = maxHit;
        this.maxHitSpec = maxHitSpec;
    }

    public static MeleeMaxHitCaps forWeapon(int weapon) {
        for (MeleeMaxHitCaps cap : MeleeMaxHitCaps.values()) {
            if(cap.itemId == weapon) {
                return cap;
            }
        }
        return null;
    }
}

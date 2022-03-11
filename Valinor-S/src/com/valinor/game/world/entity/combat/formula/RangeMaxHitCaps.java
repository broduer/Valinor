package com.valinor.game.world.entity.combat.formula;

import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public enum RangeMaxHitCaps {

    RUNE_CROSSBOW(ItemIdentifiers.RUNE_CROSSBOW,51, 73),
    RUNE_CROSSBOW_TIER_1(CustomItemIdentifiers.RUNE_CROSSBOW_TIER_1,51, 73),
    RUNE_CROSSBOW_TIER_2(CustomItemIdentifiers.RUNE_CROSSBOW_TIER_2,51, 73),
    RUNE_CROSSBOW_TIER_3(CustomItemIdentifiers.RUNE_CROSSBOW_TIER_3,51, 73),
    RUNE_CROSSBOW_TIER_4(CustomItemIdentifiers.RUNE_CROSSBOW_TIER_4,51, 73),
    RUNE_CROSSBOW_TIER_5_1(CustomItemIdentifiers.RUNE_CROSSBOW_TIER_5_1,51, 73),
    RUNE_CROSSBOW_TIER_5_2(CustomItemIdentifiers.RUNE_CROSSBOW_TIER_5_2,51, 73),
    RUNE_CROSSBOW_TIER_5_3(CustomItemIdentifiers.RUNE_CROSSBOW_TIER_5_3,51, 73),
    RUNE_CROSSBOW_TIER_5_4(CustomItemIdentifiers.RUNE_CROSSBOW_TIER_5_4,51, 73),
    RUNE_CROSSBOW_TIER_5_5(CustomItemIdentifiers.RUNE_CROSSBOW_TIER_5_5,51, 73),
    ARMADYL_CROSSBOW(ItemIdentifiers.ARMADYL_CROSSBOW,53, 75),
    ZARYTE_CROSSBOW(ItemIdentifiers.ZARYTE_CROSSBOW,53, 75),
    DRAGON_CROSSBOW(ItemIdentifiers.DRAGON_CROSSBOW,53, 75),
    DRAGON_HUNTER_CROSSBOW(ItemIdentifiers.DRAGON_HUNTER_CROSSBOW,53, 75),
    DARK_BOW(ItemIdentifiers.DARK_BOW,37, 48),
    DARK_BOW_TIER_1(CustomItemIdentifiers.DARK_BOW_TIER_1,37, 48),
    DARK_BOW_TIER_2(CustomItemIdentifiers.DARK_BOW_TIER_2,37, 48),
    DARK_BOW_TIER_3(CustomItemIdentifiers.DARK_BOW_TIER_3,37, 48),
    DARK_BOW_TIER_4(CustomItemIdentifiers.DARK_BOW_TIER_4,37, 48),
    DARK_BOW_TIER_5_1(CustomItemIdentifiers.DARK_BOW_TIER_5_1,37, 48),
    DARK_BOW_TIER_5_2(CustomItemIdentifiers.DARK_BOW_TIER_5_2,37, 48),
    DARK_BOW_TIER_5_3(CustomItemIdentifiers.DARK_BOW_TIER_5_3,37, 48),
    DARK_BOW_TIER_5_4(CustomItemIdentifiers.DARK_BOW_TIER_5_4,37, 48),
    DARK_BOW_TIER_5_5(CustomItemIdentifiers.DARK_BOW_TIER_5_5,37, 48),
    TOXIC_BLOWPIPE(ItemIdentifiers.TOXIC_BLOWPIPE,35, 52),
    MAGIC_SHORTBOW(ItemIdentifiers.MAGIC_SHORTBOW,35, 27),
    MAGIC_SHORTBOW_I(ItemIdentifiers.MAGIC_SHORTBOW_I,35, 27),
    MAGIC_SHORTBOW_TIER_1(CustomItemIdentifiers.MAGIC_SHORTBOW_TIER_1,35, 27),
    MAGIC_SHORTBOW_TIER_2(CustomItemIdentifiers.MAGIC_SHORTBOW_TIER_2,35, 27),
    MAGIC_SHORTBOW_TIER_3(CustomItemIdentifiers.MAGIC_SHORTBOW_TIER_3,35, 27),
    MAGIC_SHORTBOW_TIER_4(CustomItemIdentifiers.MAGIC_SHORTBOW_TIER_4,35, 27),
    MAGIC_SHORTBOW_TIER_5_1(CustomItemIdentifiers.MAGIC_SHORTBOW_TIER_5_1,35, 27),
    MAGIC_SHORTBOW_TIER_5_2(CustomItemIdentifiers.MAGIC_SHORTBOW_TIER_5_2,35, 27),
    MAGIC_SHORTBOW_TIER_5_3(CustomItemIdentifiers.MAGIC_SHORTBOW_TIER_5_3,35, 27),
    MAGIC_SHORTBOW_TIER_5_4(CustomItemIdentifiers.MAGIC_SHORTBOW_TIER_5_4,35, 27),
    MAGIC_SHORTBOW_TIER_5_5(CustomItemIdentifiers.MAGIC_SHORTBOW_TIER_5_5,35, 27),
    BOW_OF_FAERDHINEN_C(ItemIdentifiers.BOW_OF_FAERDHINEN_C,49, 49),
    BOW_OF_FAERDHINEN_C_25884(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25884,49, 49),
    BOW_OF_FAERDHINEN_C_25886(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25886,49, 49),
    BOW_OF_FAERDHINEN_C_25888(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25888,49, 49),
    BOW_OF_FAERDHINEN_C_25890(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25890,49, 49),
    BOW_OF_FAERDHINEN_C_25892(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25892,49, 49),
    BOW_OF_FAERDHINEN_C_25894(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25894,49, 49),
    BOW_OF_FAERDHINEN_C_25896(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25896,49, 49),
    BOW_OF_FAERDHINEN(ItemIdentifiers.BOW_OF_FAERDHINEN,49, 49),
    BOW_OF_FAERDHINEN_INACTIVE(ItemIdentifiers.BOW_OF_FAERDHINEN_INACTIVE,49, 49),
    LIGHT_BALLISTA(ItemIdentifiers.LIGHT_BALLISTA,60, 75),
    HEAVY_BALLISTA(ItemIdentifiers.HEAVY_BALLISTA,64, 80),
    DRAGON_KNIFE(ItemIdentifiers.DRAGON_KNIFE,29, 29),
    DRAGON_KNIFE_22812(ItemIdentifiers.DRAGON_KNIFE_22812,29, 29),
    DRAGON_KNIFE_22814(ItemIdentifiers.DRAGON_KNIFE_22814,29, 29),
    DRAGON_KNIFEP_22810(ItemIdentifiers.DRAGON_KNIFEP_22810,29, 29),
    DRAGON_KNIFEP_22808(ItemIdentifiers.DRAGON_KNIFEP_22808,29, 29),
    DRAGON_THROWNAXE(ItemIdentifiers.DRAGON_THROWNAXE,29, 29),
    NEW_CRYSTAL_BOW(ItemIdentifiers.NEW_CRYSTAL_BOW,41, 41),
    ;

    public final int itemId, maxHit, maxHitSpec;

    RangeMaxHitCaps(int itemId, int maxHit, int maxHitSpec) {
        this.itemId = itemId;
        this.maxHit = maxHit;
        this.maxHitSpec = maxHitSpec;
    }

    public static RangeMaxHitCaps forWeapon(int weapon) {
        for (RangeMaxHitCaps cap : RangeMaxHitCaps.values()) {
            if(cap.itemId == weapon) {
                return cap;
            }
        }
        return null;
    }
}

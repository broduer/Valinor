package com.valinor.game.world.entity.combat.formula;

import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public enum MeleeMaxHitCaps {

    ABYSSAL_WHIP(ItemIdentifiers.ABYSSAL_WHIP,51, 51),
    ABYSSAL_TENTACLE(ItemIdentifiers.ABYSSAL_TENTACLE,52, 52),
    ABYSSAL_WHIP_TIER_1(CustomItemIdentifiers.ABYSSAL_WHIP_TIER_1,51, 51),
    ABYSSAL_WHIP_TIER_2(CustomItemIdentifiers.ABYSSAL_WHIP_TIER_2,51, 51),
    ABYSSAL_WHIP_TIER_3(CustomItemIdentifiers.ABYSSAL_WHIP_TIER_3,51, 51),
    ABYSSAL_WHIP_TIER_4(CustomItemIdentifiers.ABYSSAL_WHIP_TIER_4,51, 51),
    ABYSSAL_WHIP_TIER_5_1(CustomItemIdentifiers.ABYSSAL_WHIP_TIER_5_1,51, 51),
    ABYSSAL_WHIP_TIER_5_2(CustomItemIdentifiers.ABYSSAL_WHIP_TIER_5_2,51, 51),
    ABYSSAL_WHIP_TIER_5_3(CustomItemIdentifiers.ABYSSAL_WHIP_TIER_5_3,51, 51),
    ABYSSAL_WHIP_TIER_5_4(CustomItemIdentifiers.ABYSSAL_WHIP_TIER_5_4,51, 51),
    ABYSSAL_WHIP_TIER_5_5(CustomItemIdentifiers.ABYSSAL_WHIP_TIER_5_5,51, 51),
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
    DRAGON_DAGGER_TIER_1(CustomItemIdentifiers.DRAGON_DAGGER_TIER_1,48, 48),
    DRAGON_DAGGER_TIER_2(CustomItemIdentifiers.DRAGON_DAGGER_TIER_2,48, 48),
    DRAGON_DAGGER_TIER_4(CustomItemIdentifiers.DRAGON_DAGGER_TIER_4,48, 48),
    DRAGON_DAGGER_TIER_5_1(CustomItemIdentifiers.DRAGON_DAGGER_TIER_5_1,48, 48),
    DRAGON_DAGGER_TIER_5_2(CustomItemIdentifiers.DRAGON_DAGGER_TIER_5_2,48, 48),
    DRAGON_DAGGER_TIER_5_3(CustomItemIdentifiers.DRAGON_DAGGER_TIER_5_3,48, 48),
    DRAGON_DAGGER_TIER_5_4(CustomItemIdentifiers.DRAGON_DAGGER_TIER_5_4,48, 48),
    DRAGON_DAGGER_TIER_5_5(CustomItemIdentifiers.DRAGON_DAGGER_TIER_5_5,48, 48),
    DRAGON_MACE(ItemIdentifiers.DRAGON_MACE,46, 69),
    DRAGON_MACE_TIER_1(CustomItemIdentifiers.DRAGON_MACE_TIER_1,46, 69),
    DRAGON_MACE_TIER_2(CustomItemIdentifiers.DRAGON_MACE_TIER_2,46, 69),
    DRAGON_MACE_TIER_3(CustomItemIdentifiers.DRAGON_MACE_TIER_3,46, 69),
    DRAGON_MACE_TIER_4(CustomItemIdentifiers.DRAGON_MACE_TIER_4,46, 69),
    DRAGON_MACE_TIER_5_1(CustomItemIdentifiers.DRAGON_MACE_TIER_5_1,46, 69),
    DRAGON_MACE_TIER_5_2(CustomItemIdentifiers.DRAGON_MACE_TIER_5_2,46, 69),
    DRAGON_MACE_TIER_5_3(CustomItemIdentifiers.DRAGON_MACE_TIER_5_3,46, 69),
    DRAGON_MACE_TIER_5_4(CustomItemIdentifiers.DRAGON_MACE_TIER_5_4,46, 69),
    DRAGON_MACE_TIER_5_5(CustomItemIdentifiers.DRAGON_MACE_TIER_5_5,46, 69),
    DRAGON_LONGSWORD(ItemIdentifiers.DRAGON_LONGSWORD,50, 62),
    DRAGON_LONGSWORD_TIER_1(CustomItemIdentifiers.DRAGON_LONGSWORD_TIER_1,50, 62),
    DRAGON_LONGSWORD_TIER_2(CustomItemIdentifiers.DRAGON_LONGSWORD_TIER_2,50, 62),
    DRAGON_LONGSWORD_TIER_3(CustomItemIdentifiers.DRAGON_LONGSWORD_TIER_3,50, 62),
    DRAGON_LONGSWORD_TIER_4(CustomItemIdentifiers.DRAGON_LONGSWORD_TIER_4,50, 62),
    DRAGON_LONGSWORD_TIER_5_1(CustomItemIdentifiers.DRAGON_LONGSWORD_TIER_5_1,50, 62),
    DRAGON_LONGSWORD_TIER_5_2(CustomItemIdentifiers.DRAGON_LONGSWORD_TIER_5_2,50, 62),
    DRAGON_LONGSWORD_TIER_5_3(CustomItemIdentifiers.DRAGON_LONGSWORD_TIER_5_3,50, 62),
    DRAGON_LONGSWORD_TIER_5_4(CustomItemIdentifiers.DRAGON_LONGSWORD_TIER_5_4,50, 62),
    DRAGON_LONGSWORD_TIER_5_5(CustomItemIdentifiers.DRAGON_LONGSWORD_TIER_5_5,50, 62),
    DRAGON_SCIMITAR(ItemIdentifiers.DRAGON_SCIMITAR,49, 62),
    DRAGON_SCIMITAR_TIER_1(CustomItemIdentifiers.DRAGON_SCIMITAR_TIER_1,49, 62),
    DRAGON_SCIMITAR_TIER_2(CustomItemIdentifiers.DRAGON_SCIMITAR_TIER_2,49, 62),
    DRAGON_SCIMITAR_TIER_3(CustomItemIdentifiers.DRAGON_SCIMITAR_TIER_3,49, 62),
    DRAGON_SCIMITAR_TIER_4(CustomItemIdentifiers.DRAGON_SCIMITAR_TIER_4,49, 62),
    DRAGON_SCIMITAR_TIER_5_1(CustomItemIdentifiers.DRAGON_SCIMITAR_TIER_5_1,49, 62),
    DRAGON_SCIMITAR_TIER_5_2(CustomItemIdentifiers.DRAGON_SCIMITAR_TIER_5_2,49, 62),
    DRAGON_SCIMITAR_TIER_5_3(CustomItemIdentifiers.DRAGON_SCIMITAR_TIER_5_3,49, 62),
    DRAGON_SCIMITAR_TIER_5_4(CustomItemIdentifiers.DRAGON_SCIMITAR_TIER_5_4,49, 62),
    DRAGON_SCIMITAR_TIER_5_5(CustomItemIdentifiers.DRAGON_SCIMITAR_TIER_5_5,49, 62),
    DRAGON_2H_SWORD(ItemIdentifiers.DRAGON_2H_SWORD,53, 53),
    GHRAZI_RAPIER(ItemIdentifiers.GHRAZI_RAPIER,54, 54),
    ELDER_MAUL(ItemIdentifiers.ELDER_MAUL,67, 67),
    DARK_ELDER_MAUL(CustomItemIdentifiers.DARK_ELDER_MAUL,67, 67),
    GRANITE_MAUL_24225(ItemIdentifiers.GRANITE_MAUL_24225,50, 50),
    GRANITE_MAUL_TIER_1(CustomItemIdentifiers.GRANITE_MAUL_TIER_1,50, 50),
    GRANITE_MAUL_TIER_2(CustomItemIdentifiers.GRANITE_MAUL_TIER_2,50, 50),
    GRANITE_MAUL_TIER_3(CustomItemIdentifiers.GRANITE_MAUL_TIER_3,50, 50),
    GRANITE_MAUL_TIER_4(CustomItemIdentifiers.GRANITE_MAUL_TIER_4,50, 50),
    GRANITE_MAUL_TIER_5_1(CustomItemIdentifiers.GRANITE_MAUL_TIER_5_1,50, 50),
    GRANITE_MAUL_TIER_5_2(CustomItemIdentifiers.GRANITE_MAUL_TIER_5_2,50, 50),
    GRANITE_MAUL_TIER_5_3(CustomItemIdentifiers.GRANITE_MAUL_TIER_5_3,50, 50),
    GRANITE_MAUL_TIER_5_4(CustomItemIdentifiers.GRANITE_MAUL_TIER_5_4,50, 50),
    GRANITE_MAUL_TIER_5_5(CustomItemIdentifiers.GRANITE_MAUL_TIER_5_5,50, 50),
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
    ANCIENT_WARRIOR_MAUL(CustomItemIdentifiers.ANCIENT_WARRIOR_MAUL,68, 86),
    ANCIENT_WARRIOR_AXE(CustomItemIdentifiers.ANCIENT_WARRIOR_AXE,58, 86),
    ANCIENT_WARRIOR_SWORD(CustomItemIdentifiers.ANCIENT_WARRIOR_SWORD,58, 86),
    ANCIENT_WARRIOR_MAUL_C(CustomItemIdentifiers.ANCIENT_WARRIOR_MAUL_C,69, 87),
    ANCIENT_WARRIOR_AXE_C(CustomItemIdentifiers.ANCIENT_WARRIOR_AXE_C,59, 87),
    ANCIENT_WARRIOR_SWORD_C(CustomItemIdentifiers.ANCIENT_WARRIOR_SWORD_C,59, 87),
    SWORD_OF_GRYFFINDOR(CustomItemIdentifiers.SWORD_OF_GRYFFINDOR,67, 82),
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

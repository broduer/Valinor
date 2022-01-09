package com.valinor.game.content.items_kept_on_death;

import com.valinor.game.world.items.Item;
import com.valinor.util.ItemIdentifiers;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * juni 22, 2020
 */
public enum Conversions {

    DRAG_CHAIN_G(new Item(DRAGON_CHAINBODY_G), new Item[]{new Item(DRAGON_CHAINBODY_3140), new Item(DRAGON_CHAINBODY_ORNAMENT_KIT)}),
    DRAG_LEGS_G(new Item(DRAGON_PLATELEGS_G), new Item[]{new Item(DRAGON_PLATELEGS), new Item(DRAGON_LEGSSKIRT_ORNAMENT_KIT)}),
    DRAG_SKIRT_G(new Item(DRAGON_PLATESKIRT_G), new Item[]{new Item(DRAGON_PLATESKIRT), new Item(DRAGON_LEGSSKIRT_ORNAMENT_KIT)}),
    DRAG_FULLHELM_G(new Item(DRAGON_FULL_HELM_G), new Item[]{new Item(DRAGON_FULL_HELM), new Item(DRAGON_FULL_HELM_ORNAMENT_KIT)}),
    DRAG_SQ_G(new Item(DRAGON_SQ_SHIELD_G), new Item[]{new Item(DRAGON_SQ_SHIELD), new Item(DRAGON_SQ_SHIELD_ORNAMENT_KIT)}),
    AGS(new Item(ARMADYL_GODSWORD_OR), new Item[]{new Item(ARMADYL_GODSWORD), new Item(ARMADYL_GODSWORD_ORNAMENT_KIT)}),
    BGS(new Item(BANDOS_GODSWORD_OR), new Item[]{new Item(BANDOS_GODSWORD), new Item(BANDOS_GODSWORD_ORNAMENT_KIT)}),
    SGS(new Item(SARADOMIN_GODSWORD_OR), new Item[]{new Item(SARADOMIN_GODSWORD), new Item(SARADOMIN_GODSWORD_ORNAMENT_KIT)}),
    ZGS(new Item(ZAMORAK_GODSWORD_OR), new Item[]{new Item(ZAMORAK_GODSWORD), new Item(ZAMORAK_GODSWORD_ORNAMENT_KIT)}),
    TORTURE(new Item(AMULET_OF_TORTURE_OR), new Item[]{new Item(AMULET_OF_TORTURE), new Item(TORTURE_ORNAMENT_KIT)}),
    DSCIM(new Item(DRAGON_SCIMITAR_OR), new Item[]{new Item(DRAGON_SCIMITAR), new Item(DRAGON_SCIMITAR_ORNAMENT_KIT)}),
    FURY(new Item(AMULET_OF_FURY_OR), new Item[]{new Item(AMULET_OF_FURY), new Item(FURY_ORNAMENT_KIT)}),
    OCCULT(new Item(OCCULT_NECKLACE_OR), new Item[]{new Item(OCCULT_NECKLACE), new Item(OCCULT_ORNAMENT_KIT)}),
    GRANITE_CLAMP(new Item(GRANITE_MAUL_12848), new Item[]{new Item(GRANITE_MAUL_24225), new Item(ItemIdentifiers.GRANITE_CLAMP)}),
    ODIUM_ORN(new Item(ODIUM_WARD_12807), new Item[]{new Item(ODIUM_WARD), new Item(WARD_UPGRADE_KIT)}),
    MALEDICTION_ORN(new Item(MALEDICTION_WARD_12806), new Item[]{new Item(MALEDICTION_WARD), new Item(WARD_UPGRADE_KIT)}),
    STEAM_STAFF_ORN(new Item(MYSTIC_STEAM_STAFF_12796), new Item[]{new Item(MYSTIC_STEAM_STAFF), new Item(STEAM_STAFF_UPGRADE_KIT)}),
    FROZEN_WHIP(new Item(FROZEN_ABYSSAL_WHIP), new Item[]{new Item(ABYSSAL_WHIP), new Item(FROZEN_WHIP_MIX)}),
    VOLCANIC_WHIP(new Item(VOLCANIC_ABYSSAL_WHIP), new Item[]{new Item(ABYSSAL_WHIP), new Item(VOLCANIC_WHIP_MIX)}),
    BLUE_DARKBOW(new Item(DARK_BOW_12765), new Item[]{new Item(DARK_BOW), new Item(BLUE_DARK_BOW_PAINT)}),
    GREEN_DARKBOW(new Item(DARK_BOW_12766), new Item[]{new Item(DARK_BOW), new Item(GREEN_DARK_BOW_PAINT)}),
    YELLOW_DARKBOW(new Item(DARK_BOW_12767), new Item[]{new Item(DARK_BOW), new Item(YELLOW_DARK_BOW_PAINT)}),
    WHITE_DARKBOW(new Item(DARK_BOW_12768), new Item[]{new Item(DARK_BOW), new Item(WHITE_DARK_BOW_PAINT)}),
    LIGHT_INFINITY_HAT(new Item(ItemIdentifiers.LIGHT_INFINITY_HAT), new Item[]{new Item(INFINITY_HAT), new Item(LIGHT_INFINITY_COLOUR_KIT)}),
    LIGHT_INFINITY_TOP(new Item(ItemIdentifiers.LIGHT_INFINITY_TOP), new Item[]{new Item(INFINITY_TOP), new Item(LIGHT_INFINITY_COLOUR_KIT)}),
    LIGHT_INFINITY_SKIRT(new Item(ItemIdentifiers.LIGHT_INFINITY_BOTTOMS), new Item[]{new Item(INFINITY_BOTTOMS), new Item(LIGHT_INFINITY_COLOUR_KIT)}),
    DARK_INFINITY_HAT(new Item(ItemIdentifiers.DARK_INFINITY_HAT), new Item[]{new Item(INFINITY_HAT), new Item(DARK_INFINITY_COLOUR_KIT)}),
    DARK_INFINITY_TOP(new Item(ItemIdentifiers.DARK_INFINITY_TOP), new Item[]{new Item(INFINITY_TOP), new Item(DARK_INFINITY_COLOUR_KIT)}),
    DARK_INFINITY_SKIRT(new Item(DARK_INFINITY_BOTTOMS), new Item[]{new Item(INFINITY_BOTTOMS), new Item(DARK_INFINITY_COLOUR_KIT)}),
    MAGMA_HELMET(new Item(MAGMA_HELM), new Item[]{new Item(SERPENTINE_HELM), new Item(MAGMA_MUTAGEN)}),
    TANZANITE_HELMET(new Item(TANZANITE_HELM), new Item[]{new Item(SERPENTINE_HELM), new Item(TANZANITE_MUTAGEN)}),
    SARADOMINS_BLESSED_SWORD(new Item(ItemIdentifiers.SARADOMINS_BLESSED_SWORD), new Item[]{new Item(SARADOMINS_TEAR), new Item(SARADOMIN_SWORD)}),
    DRAGON_PLATEBODY(new Item(DRAGON_PLATEBODY_G), new Item[] {new Item(ItemIdentifiers.DRAGON_PLATEBODY), new Item(DRAGON_PLATEBODY_ORNAMENT_KIT)}),
    DRAGON_KITESHIELD(new Item(DRAGON_KITESHIELD_G), new Item[] {new Item(ItemIdentifiers.DRAGON_KITESHIELD), new Item(DRAGON_KITESHIELD_ORNAMENT_KIT)}),
    DRAGON_BOOTS(new Item(DRAGON_BOOTS_G), new Item[] { new Item(ItemIdentifiers.DRAGON_BOOTS), new Item(DRAGON_BOOTS_ORNAMENT_KIT)}),
    ANGUISH(new Item(NECKLACE_OF_ANGUISH_OR), new Item[]{new Item(NECKLACE_OF_ANGUISH), new Item(ANGUISH_ORNAMENT_KIT)}),
    STAFF_OF_LIGHT(new Item(ItemIdentifiers.STAFF_OF_LIGHT), new Item[] { new Item(STAFF_OF_THE_DEAD), new Item(SARADOMINS_LIGHT)}),
    R_SCIM_GUTHIX(new Item(RUNE_SCIMITAR_23330), new Item[] { new Item(RUNE_SCIMITAR), new Item(RUNE_SCIMITAR_ORNAMENT_KIT_GUTHIX)}),
    R_SCIM_SARADOMIN(new Item(RUNE_SCIMITAR_23332), new Item[] { new Item(RUNE_SCIMITAR), new Item(RUNE_SCIMITAR_ORNAMENT_KIT_SARADOMIN)}),
    R_SCIM_ZAMORAK(new Item(RUNE_SCIMITAR_23334), new Item[] { new Item(RUNE_SCIMITAR), new Item(RUNE_SCIMITAR_ORNAMENT_KIT_ZAMORAK)}),
    RUNE_DEFENDER_T(new Item(ItemIdentifiers.RUNE_DEFENDER_T), new Item[] { new Item(RUNE_DEFENDER), new Item(RUNE_DEFENDER_ORNAMENT_KIT)}),
    DRAGON_DEFENDER_T(new Item(ItemIdentifiers.DRAGON_DEFENDER_T), new Item[] { new Item(DRAGON_DEFENDER), new Item(DRAGON_DEFENDER_ORNAMENT_KIT)}),
    TZHAAR_KET_OM_T(new Item(TZHAARKETOM_T), new Item[] { new Item(TZHAARKETOM), new Item(TZHAARKETOM_ORNAMENT_KIT)}),
    BERSERKER_NECKLACE_OR(new Item(ItemIdentifiers.BERSERKER_NECKLACE_OR), new Item[] { new Item(BERSERKER_NECKLACE), new Item(BERSERKER_NECKLACE_ORNAMENT_KIT)}),
    TORMENTED_BRACELET_OR(new Item(ItemIdentifiers.TORMENTED_BRACELET_OR), new Item[] { new Item(TORMENTED_BRACELET), new Item(TORMENTED_ORNAMENT_KIT)}),
    TWISTED_ANCESTRAL_HAT(new Item(ItemIdentifiers.TWISTED_ANCESTRAL_HAT), new Item[] { new Item(ANCESTRAL_HAT), new Item(TWISTED_ANCESTRAL_COLOUR_KIT)}),
    TWISTED_ANCESTRAL_ROBE_TOP(new Item(ItemIdentifiers.TWISTED_ANCESTRAL_ROBE_TOP), new Item[] { new Item(ANCESTRAL_ROBE_TOP), new Item(TWISTED_ANCESTRAL_COLOUR_KIT)}),
    TWISTED_ANCESTRAL_ROBE_BOTTOM(new Item(ItemIdentifiers.TWISTED_ANCESTRAL_ROBE_BOTTOM), new Item[] { new Item(ANCESTRAL_ROBE_BOTTOM), new Item(TWISTED_ANCESTRAL_COLOUR_KIT)}),
    HOLY_GHRAZI_RAPIER(new Item(ItemIdentifiers.HOLY_GHRAZI_RAPIER), new Item[] { new Item(GHRAZI_RAPIER), new Item(HOLY_ORNAMENT_KIT)}),
    HOLY_SANGUINESTI_STAFF(new Item(ItemIdentifiers.HOLY_SANGUINESTI_STAFF), new Item[] { new Item(SANGUINESTI_STAFF), new Item(HOLY_ORNAMENT_KIT)}),
    HOLY_SCYTHE_OF_VITUR(new Item(ItemIdentifiers.HOLY_SCYTHE_OF_VITUR), new Item[] { new Item(SCYTHE_OF_VITUR), new Item(HOLY_ORNAMENT_KIT)}),
    SANGUINE_SCYTHE_OF_VITUR(new Item(ItemIdentifiers.SANGUINE_SCYTHE_OF_VITUR), new Item[] { new Item(SCYTHE_OF_VITUR), new Item(SANGUINE_ORNAMENT_KIT)}),
    GRACEFUL_HOOD(new Item(GRACEFUL_HOOD_24743), new Item[] { new Item(ItemIdentifiers.GRACEFUL_HOOD), new Item(DARK_DYE)}),
    GRACEFUL_TOP(new Item(GRACEFUL_TOP_24749), new Item[] { new Item(ItemIdentifiers.GRACEFUL_TOP), new Item(DARK_DYE)}),
    GRACEFUL_LEGS(new Item(GRACEFUL_LEGS_24752), new Item[] { new Item(ItemIdentifiers.GRACEFUL_LEGS), new Item(DARK_DYE)}),
    GRACEFUL_GLOVES(new Item(GRACEFUL_GLOVES_24755), new Item[] { new Item(ItemIdentifiers.GRACEFUL_GLOVES), new Item(DARK_DYE)}),
    GRACEFUL_BOOTS(new Item(GRACEFUL_BOOTS_24758), new Item[] { new Item(ItemIdentifiers.GRACEFUL_BOOTS), new Item(DARK_DYE)}),
    GRACEFUL_CAPE(new Item(GRACEFUL_CAPE_24746), new Item[] { new Item(ItemIdentifiers.GRACEFUL_CAPE), new Item(DARK_DYE)}),
    GRACEFUL_HOOD_TRAILBLAZER(new Item(GRACEFUL_HOOD_25069), new Item[] { new Item(ItemIdentifiers.GRACEFUL_HOOD), new Item(TRAILBLAZER_GRACEFUL_ORNAMENT_KIT)}),
    GRACEFUL_TOP_TRAILBLAZER(new Item(GRACEFUL_TOP_25075), new Item[] { new Item(ItemIdentifiers.GRACEFUL_TOP), new Item(TRAILBLAZER_GRACEFUL_ORNAMENT_KIT)}),
    GRACEFUL_LEGS_TRAILBLAZER(new Item(GRACEFUL_LEGS_25078), new Item[] { new Item(ItemIdentifiers.GRACEFUL_LEGS), new Item(TRAILBLAZER_GRACEFUL_ORNAMENT_KIT)}),
    GRACEFUL_GLOVES_TRAILBLAZER(new Item(GRACEFUL_GLOVES_25081), new Item[] { new Item(ItemIdentifiers.GRACEFUL_GLOVES), new Item(TRAILBLAZER_GRACEFUL_ORNAMENT_KIT)}),
    GRACEFUL_BOOTS_TRAILBLAZER(new Item(GRACEFUL_BOOTS_25084), new Item[] { new Item(ItemIdentifiers.GRACEFUL_BOOTS), new Item(TRAILBLAZER_GRACEFUL_ORNAMENT_KIT)}),
    GRACEFUL_CAPE_TRAILBLAZER(new Item(GRACEFUL_CAPE_25072), new Item[] { new Item(ItemIdentifiers.GRACEFUL_CAPE), new Item(TRAILBLAZER_GRACEFUL_ORNAMENT_KIT)}),
    DRAGON_AXE_TRAILBLAZER(new Item(DRAGON_AXE_OR), new Item[] { new Item(DRAGON_AXE), new Item(TRAILBLAZER_TOOL_ORNAMENT_KIT)}),
    INFERNAL_AXE_TRAILBLAZER(new Item(INFERNAL_AXE_OR), new Item[] { new Item(INFERNAL_AXE), new Item(TRAILBLAZER_TOOL_ORNAMENT_KIT)}),
    DRAGON_HARPOON_TRAILBLAZER(new Item(DRAGON_HARPOON_OR), new Item[] { new Item(DRAGON_HARPOON), new Item(TRAILBLAZER_TOOL_ORNAMENT_KIT)}),
    INFERNAL_HARPOON_TRAILBLAZER(new Item(INFERNAL_HARPOON_OR), new Item[] { new Item(INFERNAL_HARPOON), new Item(TRAILBLAZER_TOOL_ORNAMENT_KIT)}),
    DRAGON_PICKAXE_TRAILBLAZER(new Item(DRAGON_PICKAXE_OR_25376), new Item[] { new Item(ItemIdentifiers.DRAGON_PICKAXE), new Item(TRAILBLAZER_TOOL_ORNAMENT_KIT)}),
    INFERNAL_PICKAXE_TRAILBLAZER(new Item(INFERNAL_PICKAXE_OR), new Item[] { new Item(INFERNAL_PICKAXE), new Item(TRAILBLAZER_TOOL_ORNAMENT_KIT)}),
    DRAGON_PICKAXE_ZALCANO(new Item(DRAGON_PICKAXE_OR), new Item[] { new Item(ItemIdentifiers.DRAGON_PICKAXE), new Item(ZALCANO_SHARD)}),
    DRAGON_PICKAXE(new Item(DRAGON_PICKAXE_12797), new Item[] { new Item(ItemIdentifiers.DRAGON_PICKAXE), new Item(DRAGON_PICKAXE_UPGRADE_KIT)}),
    STEAM_STAFF(new Item(STEAM_BATTLESTAFF_12795), new Item[] { new Item(STEAM_BATTLESTAFF), new Item(STEAM_STAFF_UPGRADE_KIT)}),
    LAVA_STAFF(new Item(LAVA_BATTLESTAFF_21198), new Item[] { new Item(LAVA_BATTLESTAFF), new Item(LAVA_STAFF_UPGRADE_KIT)}),
    DRAGON_HUNTER_CROSSBOW_B(new Item(ItemIdentifiers.DRAGON_HUNTER_CROSSBOW_B), new Item[] { new Item(DRAGON_HUNTER_CROSSBOW), new Item(KBD_HEADS)}),
    DRAGON_HUNTER_CROSSBOW_T(new Item(ItemIdentifiers.DRAGON_HUNTER_CROSSBOW_T), new Item[] { new Item(DRAGON_HUNTER_CROSSBOW), new Item(VORKATHS_HEAD_21907)}),
    CRYSTAL_OF_IORWERTH_CROWN(new Item(CRYSTAL_CROWN_23913), new Item[] { new Item(CRYSTAL_CROWN), new Item(CRYSTAL_OF_IORWERTH)}),
    CRYSTAL_OF_IORWERTH_BLADE(new Item(BLADE_OF_SAELDOR_C_25872), new Item[] { new Item(BLADE_OF_SAELDOR_C), new Item(CRYSTAL_OF_IORWERTH)}),
    CRYSTAL_OF_IORWERTH_BOW(new Item(BOW_OF_FAERDHINEN_C_25886), new Item[] { new Item(BOW_OF_FAERDHINEN_C), new Item(CRYSTAL_OF_IORWERTH)}),
    CRYSTAL_OF_TRAHAEARN_CROWN(new Item(ItemIdentifiers.CRYSTAL_CROWN_23915), new Item[] { new Item(CRYSTAL_CROWN), new Item(CRYSTAL_OF_TRAHAEARN)}),
    CRYSTAL_OF_TRAHAEARN_BLADE(new Item(ItemIdentifiers.BLADE_OF_SAELDOR_C_25874), new Item[] { new Item(BLADE_OF_SAELDOR_C), new Item(CRYSTAL_OF_TRAHAEARN)}),
    CRYSTAL_OF_TRAHAEARN_BOW(new Item(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25888), new Item[] { new Item(BOW_OF_FAERDHINEN_C), new Item(CRYSTAL_OF_TRAHAEARN)}),
    CRYSTAL_OF_CADARN_CROWN(new Item(ItemIdentifiers.CRYSTAL_CROWN_23917), new Item[] { new Item(CRYSTAL_CROWN), new Item(CRYSTAL_OF_CADARN)}),
    CRYSTAL_OF_CADARN_BLADE(new Item(ItemIdentifiers.BLADE_OF_SAELDOR_C_25876), new Item[] { new Item(BLADE_OF_SAELDOR_C), new Item(CRYSTAL_OF_CADARN)}),
    CRYSTAL_OF_CADARN_BOW(new Item(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25890), new Item[] { new Item(BOW_OF_FAERDHINEN_C), new Item(CRYSTAL_OF_CADARN)}),
    CRYSTAL_OF_CRWYS_CROWN(new Item(ItemIdentifiers.CRYSTAL_CROWN_23919), new Item[] { new Item(CRYSTAL_CROWN), new Item(CRYSTAL_OF_CRWYS)}),
    CRYSTAL_OF_CRWYS_BLADE(new Item(ItemIdentifiers.BLADE_OF_SAELDOR_C_25878), new Item[] { new Item(BLADE_OF_SAELDOR_C), new Item(CRYSTAL_OF_CRWYS)}),
    CRYSTAL_OF_CRWYS_BOW(new Item(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25892), new Item[] { new Item(BOW_OF_FAERDHINEN_C), new Item(CRYSTAL_OF_CRWYS)}),
    CRYSTAL_OF_MEILYR_CROWN(new Item(ItemIdentifiers.CRYSTAL_CROWN_23921), new Item[] { new Item(CRYSTAL_CROWN), new Item(CRYSTAL_OF_MEILYR)}),
    CRYSTAL_OF_MEILYR_BLADE(new Item(ItemIdentifiers.BLADE_OF_SAELDOR_C_25880), new Item[] { new Item(BLADE_OF_SAELDOR_C), new Item(CRYSTAL_OF_MEILYR)}),
    CRYSTAL_OF_MEILYR_BOW(new Item(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25894), new Item[] { new Item(BOW_OF_FAERDHINEN_C), new Item(CRYSTAL_OF_MEILYR)}),
    CRYSTAL_OF_HEFIN_CROWN(new Item(ItemIdentifiers.CRYSTAL_CROWN_23923), new Item[] { new Item(CRYSTAL_CROWN), new Item(CRYSTAL_OF_HEFIN)}),
    CRYSTAL_OF_HEFIN_BLADE(new Item(ItemIdentifiers.BLADE_OF_SAELDOR_C), new Item[] { new Item(BLADE_OF_SAELDOR_C), new Item(CRYSTAL_OF_HEFIN)}),
    CRYSTAL_OF_HEFIN_BOW(new Item(ItemIdentifiers.BOW_OF_FAERDHINEN_C), new Item[] { new Item(BOW_OF_FAERDHINEN_C), new Item(CRYSTAL_OF_HEFIN)}),
    CRYSTAL_OF_AMLODD_CROWN(new Item(ItemIdentifiers.CRYSTAL_CROWN_23925), new Item[] { new Item(CRYSTAL_CROWN), new Item(CRYSTAL_OF_AMLODD)}),
    CRYSTAL_OF_AMLODD_BLADE(new Item(ItemIdentifiers.BLADE_OF_SAELDOR_C_25882), new Item[] { new Item(BLADE_OF_SAELDOR_C), new Item(CRYSTAL_OF_AMLODD)}),
    CRYSTAL_OF_AMLODDL_BOW(new Item(BOW_OF_FAERDHINEN_C_25896), new Item[] { new Item(BOW_OF_FAERDHINEN_C), new Item(CRYSTAL_OF_AMLODD)}),

    ;//end of enum

    public Item src;
    public Item[] out;

    Conversions(Item src, Item[] output) {
        this.src = src;
        this.out = output;
    }
}

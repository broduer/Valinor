package com.valinor.game.content.mechanics.item_dispenser;

import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;

import java.util.Optional;

/**
 * This enum will hold all the prices and data for the item dispenser.
 *
 * @author Patrick van Elderen | February, 14, 2021, 23:12
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public enum Cart {

    /**
     * Item ID, Coin value
     */
    RING_OF_ELYSIAN(CustomItemIdentifiers.RING_OF_ELYSIAN,500),
    JAWA_PET(CustomItemIdentifiers.JAWA_PET,600),
    CENTAUR_MALE(CustomItemIdentifiers.CENTAUR_MALE,800),
    CENTAUR_FEMALE(CustomItemIdentifiers.CENTAUR_FEMALE,800),
    DEMENTOR_PET(CustomItemIdentifiers.DEMENTOR_PET,1250),
    FENRIR_GREYBACK_JR(CustomItemIdentifiers.FENRIR_GREYBACK_JR,350),
    FLUFFY_JR(CustomItemIdentifiers.FLUFFY_JR,350),
    ANCIENT_BARRELCHEST_PET(CustomItemIdentifiers.ANCIENT_BARRELCHEST_PET,1050),
    ANCIENT_CHAOS_ELEMENTAL_PET(CustomItemIdentifiers.ANCIENT_CHAOS_ELEMENTAL_PET,1050),
    ANCIENT_KING_BLACK_DRAGON_PET(CustomItemIdentifiers.ANCIENT_KING_BLACK_DRAGON_PET,1050),
    ZRIAWK(CustomItemIdentifiers.ZRIAWK,2000),
    FAWKES(CustomItemIdentifiers.FAWKES,1500),
    BARRELCHEST_PET(CustomItemIdentifiers.BARRELCHEST_PET,500),
    ICELORD(CustomItemIdentifiers.WAMPA_PET,1000),
    NIFFLER(CustomItemIdentifiers.NIFFLER,750),
    BABY_ARAGOG(CustomItemIdentifiers.BABY_ARAGOG,1250),
    FOUNDER_IMP(CustomItemIdentifiers.FOUNDER_IMP,850),
    BABY_LAVA_DRAGON(CustomItemIdentifiers.BABY_LAVA_DRAGON,800),
    MINI_NECROMANCER(CustomItemIdentifiers.MINI_NECROMANCER,1500),
    PET_CORRUPTED_NECHRYARCH(CustomItemIdentifiers.PET_CORRUPTED_NECHRYARCH,1500),
    TZREKZUK(ItemIdentifiers.TZREKZUK,500),
    GRIM_REAPER_PET(CustomItemIdentifiers.GRIM_REAPER_PET,750),
    GENIE_PET(CustomItemIdentifiers.GENIE_PET,500),
    DHAROK_PET(CustomItemIdentifiers.DHAROK_PET,350),
    PET_ZOMBIES_CHAMPION(CustomItemIdentifiers.ZOMBIES_CHAMPION_PET,350),
    BABY_ABYSSAL_DEMON(CustomItemIdentifiers.BABY_ABYSSAL_DEMON,500),
    BABY_SQUIRT(CustomItemIdentifiers.BABY_SQUIRT,500),
    BABY_DARK_BEAST_EGG(CustomItemIdentifiers.BABY_DARK_BEAST_EGG,500),
    ELEMENTAL_BOW(CustomItemIdentifiers.ELEMENTAL_BOW,4000),
    HEAVY_BALLISTA(ItemIdentifiers.HEAVY_BALLISTA,500),
    DINHS_BULWARK(ItemIdentifiers.DINHS_BULWARK,250),
    ARMADYL_GODSWORD(ItemIdentifiers.ARMADYL_GODSWORD,150),
    BLESSED_SARADOMIN_SWORD(ItemIdentifiers.SARADOMINS_BLESSED_SWORD,250),
    FEROCIOUS_GLOVES(ItemIdentifiers.FEROCIOUS_GLOVES, 1000),
    INQUISITORS_MACE(ItemIdentifiers.INQUISITORS_MACE, 2500),
    AMULET_OF_TORTURE_OR(ItemIdentifiers.AMULET_OF_TORTURE_OR, 1500),
    NECKLACE_OF_ANGUISH_OR(ItemIdentifiers.NECKLACE_OF_ANGUISH_OR, 1500),
    OCCULT_NECKLACE_OR(ItemIdentifiers.OCCULT_NECKLACE_OR, 750),
    TORMENTED_BRACELET_OR(ItemIdentifiers.TORMENTED_BRACELET_OR, 1500),
    TOXIC_STAFF_OF_THE_DEAD_C(CustomItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD_C, 1500),
    CRAWS_BOW_C(CustomItemIdentifiers.CRAWS_BOW_C, 1750),
    VIGGORAS_CHAINMACE_C(CustomItemIdentifiers.VIGGORAS_CHAINMACE_C, 1750),
    THAMMARONS_STAFF_C(CustomItemIdentifiers.THAMMARONS_STAFF_C, 1750),
    ANCESTRAL_HAT_I(CustomItemIdentifiers.ANCESTRAL_HAT_I, 2000),
    ANCESTRAL_ROBE_TOP_I(CustomItemIdentifiers.ANCESTRAL_ROBE_TOP_I, 3500),
    ANCESTRAL_ROBE_BOTTOM_I(CustomItemIdentifiers.ANCESTRAL_ROBE_BOTTOM_I, 3500),
    TALONHAWK_CROSSBOW(CustomItemIdentifiers.TALONHAWK_CROSSBOW, 3500),
    LAVA_DHIDE_COIF(CustomItemIdentifiers.LAVA_DHIDE_COIF, 1500),
    LAVA_DHIDE_BODY(CustomItemIdentifiers.LAVA_DHIDE_BODY, 4500),
    LAVA_DHIDE_CHAPS(CustomItemIdentifiers.LAVA_DHIDE_CHAPS, 3000),
    ANCIENT_VESTAS_LONGSWORD(CustomItemIdentifiers.ANCIENT_VESTAS_LONGSWORD,3500),
    ANCIENT_STATIUSS_WARHAMMER(CustomItemIdentifiers.ANCIENT_STATIUS_WARHAMMER,3500),
    ANCIENT_WARRIOR_AXE_C(CustomItemIdentifiers.ANCIENT_WARRIOR_AXE_C,3000),
    ANCIENT_WARRIOR_MAUL_C(CustomItemIdentifiers.ANCIENT_WARRIOR_MAUL_C,3000),
    ANCIENT_WARRIOR_SWORD_C(CustomItemIdentifiers.ANCIENT_WARRIOR_SWORD_C,3000),
    ANCIENT_FACEGAURD(CustomItemIdentifiers.ANCIENT_FACEGAURD,3250),
    NEITIZNOT_FACEGUARD(ItemIdentifiers.NEITIZNOT_FACEGUARD,500),
    ANCIENT_WARRIOR_AXE(CustomItemIdentifiers.ANCIENT_WARRIOR_AXE,1000),
    ANCIENT_WARRIOR_MAUL(CustomItemIdentifiers.ANCIENT_WARRIOR_MAUL,1000),
    ANCIENT_WARRIOR_SWORD(CustomItemIdentifiers.ANCIENT_WARRIOR_SWORD,1000),
    DRAGON_HUNTER_CROSSBOW(ItemIdentifiers.DRAGON_HUNTER_CROSSBOW,500),
    ELDER_MAUL(ItemIdentifiers.ELDER_MAUL,350),
    MAGMA_HELM(ItemIdentifiers.MAGMA_HELM,700),
    TANZANITE_HELM(ItemIdentifiers.TANZANITE_HELM,700),
    ANCIENT_WYVERN_SHIELD(ItemIdentifiers.ANCIENT_WYVERN_SHIELD,150),
    DRAGONFRIE_WARD(ItemIdentifiers.DRAGONFIRE_WARD,150),
    SERPENTINE_HELM(ItemIdentifiers.SERPENTINE_HELM,500),
    TRIDENT_OF_THE_SWAMP(ItemIdentifiers.TRIDENT_OF_THE_SWAMP,250),
    TOXIC_BLOWPIPE(ItemIdentifiers.TOXIC_BLOWPIPE,500),
    TOXIC_STAFF_OF_THE_DEAD(ItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD,800),
    ANKOU_GLOVES(ItemIdentifiers.ANKOU_GLOVES,1000),
    ANKOU_MASK(ItemIdentifiers.ANKOU_MASK,1000),
    ANKOU_SOCKS(ItemIdentifiers.ANKOU_SOCKS,1000),
    ANKOU_TOP(ItemIdentifiers.ANKOU_TOP,1000),
    ANKOUS_LEGGINGS(ItemIdentifiers.ANKOUS_LEGGINGS,1000),
    DEXTEROUS_PRAYER_SCROLL(ItemIdentifiers.DEXTEROUS_PRAYER_SCROLL,1250),
    ARCANE_PRAYER_SCROLL(ItemIdentifiers.ARCANE_PRAYER_SCROLL,1250),
    INQUISITORS_GREAT_HELM(ItemIdentifiers.INQUISITORS_GREAT_HELM,1000),
    INQUISITORS_HAUBERK(ItemIdentifiers.INQUISITORS_HAUBERK,1500),
    INQUISITORS_PLATESKIRT(ItemIdentifiers.INQUISITORS_PLATESKIRT,1500),
    SPECTRAL_SPIRIT_SHIELD(ItemIdentifiers.SPECTRAL_SPIRIT_SHIELD,750),
    NECKLACE_OF_ANGUISH(ItemIdentifiers.NECKLACE_OF_ANGUISH,500),
    AMULET_OF_TORTURE(ItemIdentifiers.AMULET_OF_TORTURE,500),
    TORMENTED_BRACELET(ItemIdentifiers.TORMENTED_BRACELET,500),
    RING_OF_SUFFERING(ItemIdentifiers.RING_OF_SUFFERING,500),
    DRAGON_WARHAMMER(ItemIdentifiers.DRAGON_WARHAMMER,1000),
    BLADE_OF_SAELDOR(ItemIdentifiers.BLADE_OF_SAELDOR,3000),
    DRAGON_HUNTER_LANCE(ItemIdentifiers.DRAGON_HUNTER_LANCE,1000),
    ARMADYL_GODSWORD_OR(ItemIdentifiers.ARMADYL_GODSWORD_OR,1000),
    BANDOS_GODSWORD_OR(ItemIdentifiers.BANDOS_GODSWORD_OR,500),
    SARADOMIN_GODSWORD_OR(ItemIdentifiers.SARADOMIN_GODSWORD_OR,500),
    ZAMORAK_GODSWORD_OR(ItemIdentifiers.ZAMORAK_GODSWORD_OR,500),
    DRAGON_CLAWS(ItemIdentifiers.DRAGON_CLAWS,500),
    DRAGON_CLAWS_OR(CustomItemIdentifiers.DRAGON_CLAWS_OR,1000),
    TWISTED_BOW(ItemIdentifiers.TWISTED_BOW,10000),
    SCYTHE_OF_VITUR(ItemIdentifiers.SCYTHE_OF_VITUR,10000),
    ELYSIAN_SPIRIT_SHIELD(ItemIdentifiers.ELYSIAN_SPIRIT_SHIELD,7500),
    VESTAS_CHAINBODY(ItemIdentifiers.VESTAS_CHAINBODY,500),
    VESTAS_PLATESKIRT(ItemIdentifiers.VESTAS_PLATESKIRT,500),
    VESTAS_LONGSWORD(ItemIdentifiers.VESTAS_LONGSWORD,1000),
    VESTAS_SPEAR(ItemIdentifiers.VESTAS_SPEAR,600),
    STATIUSS_FULL_HELM(ItemIdentifiers.STATIUSS_FULL_HELM,350),
    STATIUSS_PLATEBODY(ItemIdentifiers.STATIUSS_PLATEBODY,350),
    STATIUSS_PLATELEGS(ItemIdentifiers.STATIUSS_PLATELEGS,350),
    STATIUSS_WARHAMMER(ItemIdentifiers.STATIUSS_WARHAMMER,600),
    MORRIGANS_COIF(ItemIdentifiers.MORRIGANS_COIF,350),
    MORRIGANS_LEATHER_BODY(ItemIdentifiers.MORRIGANS_LEATHER_BODY,350),
    MORRIGANS_LEATHER_CHAPS(ItemIdentifiers.MORRIGANS_LEATHER_CHAPS,350),
    ZURIEL_STAFF(ItemIdentifiers.ZURIELS_STAFF,450),
    ZURIELS_HOOD(ItemIdentifiers.ZURIELS_HOOD,350),
    ZURIELS_ROBE_TOP(ItemIdentifiers.ZURIELS_ROBE_TOP,350),
    ZURIELS_ROBE_BOTTOM(ItemIdentifiers.ZURIELS_ROBE_BOTTOM,350),
    AVERNIC_DEFENDER(ItemIdentifiers.AVERNIC_DEFENDER,1250),
    AVERNIC_DEFENDER_HILT(ItemIdentifiers.AVERNIC_DEFENDER_HILT,1250),
    INFERNAL_CAPE(ItemIdentifiers.INFERNAL_CAPE,3000),
    ARCANE_SPIRIT_SHIELD(ItemIdentifiers.ARCANE_SPIRIT_SHIELD,1850),
    ANCESTRAL_HAT(ItemIdentifiers.ANCESTRAL_HAT,800),
    ANCESTRAL_ROBE_TOP(ItemIdentifiers.ANCESTRAL_ROBE_TOP,1000),
    ANCESTRAL_ROBE_BOTTOM(ItemIdentifiers.ANCESTRAL_ROBE_BOTTOM,1000),
    TWISTED_ANCESTRAL_HAT(ItemIdentifiers.TWISTED_ANCESTRAL_HAT,1200),
    TWISTED_ANCESTRAL_ROBE_TOP(ItemIdentifiers.TWISTED_ANCESTRAL_ROBE_TOP,1500),
    TWISTED_ANCESTRAL_ROBE_BOTTOM(ItemIdentifiers.TWISTED_ANCESTRAL_ROBE_BOTTOM,1500),
    JUSTICIAR_FACEGUARD(ItemIdentifiers.JUSTICIAR_FACEGUARD,1500),
    JUSTICIAR_CHESTGUARD(ItemIdentifiers.JUSTICIAR_CHESTGUARD,1500),
    JUSTICIAR_LEGGUARDS(ItemIdentifiers.JUSTICIAR_LEGGUARDS,1500),
    VOLATILE_ORB(ItemIdentifiers.VOLATILE_ORB,1000),
    CURSED_ORB(CustomItemIdentifiers.CURSED_ORB,3200),
    HARMONISED_ORB(ItemIdentifiers.HARMONISED_ORB,1000),
    ELDRITCH_ORB(ItemIdentifiers.ELDRITCH_ORB,1000),
    NIGHTMARE_STAFF(ItemIdentifiers.NIGHTMARE_STAFF,500),
    VIGGORAS_CHAINMACE(ItemIdentifiers.VIGGORAS_CHAINMACE,250),
    CRAWS_BOW(ItemIdentifiers.CRAWS_BOW,250),
    THAMMARONS_SCEPTRE(ItemIdentifiers.THAMMARONS_SCEPTRE,250),
    AMULET_OF_AVARICE(ItemIdentifiers.AMULET_OF_AVARICE,200),
    KODAI_WAND(ItemIdentifiers.KODAI_WAND,1500),
    GHRAZI_RAPIER(ItemIdentifiers.GHRAZI_RAPIER,1500),
    ODIUM_WARD(ItemIdentifiers.ODIUM_WARD,45),
    MALEDICTION_WARD(ItemIdentifiers.MALEDICTION_WARD,45),
    BANDOS_GODSWORD(ItemIdentifiers.BANDOS_GODSWORD,50),
    ZAMORAK_GODSWORD(ItemIdentifiers.ZAMORAK_GODSWORD,50),
    SARADOMIN_GODSWORD(ItemIdentifiers.SARADOMIN_GODSWORD,50),
    INFINITY_HAT(ItemIdentifiers.INFINITY_HAT,10),
    INFINITY_TOP(ItemIdentifiers.INFINITY_TOP,10),
    INFINITY_BOTTOMS(ItemIdentifiers.INFINITY_BOTTOMS,10),
    INFINITY_BOOTS(ItemIdentifiers.INFINITY_BOOTS,10),
    INFINITY_GLOVES(ItemIdentifiers.INFINITY_GLOVES,10),
    MASTER_WAND(ItemIdentifiers.MASTER_WAND,10),
    MAGES_BOOK(ItemIdentifiers.MAGES_BOOK,10),
    ARMADYL_HELMET(ItemIdentifiers.ARMADYL_HELMET,75),
    ARMADYL_CHESTPLATE(ItemIdentifiers.ARMADYL_CHESTPLATE,120),
    ARMADYL_CHAINSKIRT(ItemIdentifiers.ARMADYL_CHAINSKIRT,120),
    BANDOS_CHESTPLATE(ItemIdentifiers.BANDOS_CHESTPLATE,120),
    BANDOS_TASSETS(ItemIdentifiers.BANDOS_TASSETS,120),
    BANDOS_BOOTS(ItemIdentifiers.BANDOS_BOOTS,75),
    STAFF_OF_THE_DEAD(ItemIdentifiers.STAFF_OF_THE_DEAD,50),
    ARMADYL_CROSSBOW(ItemIdentifiers.ARMADYL_CROSSBOW,120),
    ZAMORAKIAN_SPEAR(ItemIdentifiers.ZAMORAKIAN_SPEAR,120),
    ZAMORAKIAN_HASTA(ItemIdentifiers.ZAMORAKIAN_HASTA,120),
    KRAKEN_TENTACLE(ItemIdentifiers.KRAKEN_TENTACLE,15),
    ABYSSAL_TENTACLE(ItemIdentifiers.ABYSSAL_TENTACLE,15),
    TORVA_FULL_HELM(ItemIdentifiers.TORVA_FULL_HELM,3000),
    TORVA_PLATELEGS(ItemIdentifiers.TORVA_PLATELEGS,3000),
    TORVA_PLATEBODY(ItemIdentifiers.TORVA_PLATEBODY,3000),
    BERSERKER_RING_I(ItemIdentifiers.BERSERKER_RING_I,15),
    WARRIOR_RING_I(ItemIdentifiers.WARRIOR_RING_I,25),
    SEERS_RING_I(ItemIdentifiers.SEERS_RING_I,25),
    ARCHERS_RING_I(ItemIdentifiers.ARCHERS_RING_I,25),
    SWORD_OF_GRYFFINDOR(CustomItemIdentifiers.SWORD_OF_GRYFFINDOR,4000),
    ANCIENT_GODSWORD(ItemIdentifiers.ANCIENT_GODSWORD,4000),
    RED_PARTYHAT(ItemIdentifiers.RED_PARTYHAT,750),
    YELLOW_PARTYHAT(ItemIdentifiers.YELLOW_PARTYHAT,750),
    BLUE_PARTYHAT(ItemIdentifiers.BLUE_PARTYHAT,750),
    GREEN_PARTYHAT(ItemIdentifiers.GREEN_PARTYHAT,750),
    PURPLE_PARTYHAT(ItemIdentifiers.PURPLE_PARTYHAT,750),
    WHITE_PARTYHAT(ItemIdentifiers.WHITE_PARTYHAT,750),
    GREEN_HALLOWEEN_MASK(ItemIdentifiers.GREEN_HALLOWEEN_MASK,350),
    BLUE_HALLOWEEN_MASK(ItemIdentifiers.BLUE_HALLOWEEN_MASK,350),
    RED_HALLOWEEN_MASK(ItemIdentifiers.RED_HALLOWEEN_MASK,350),
    DHAROKS_HELM(ItemIdentifiers.DHAROKS_HELM,25),
    DHAROKS_GREATAXE(ItemIdentifiers.DHAROKS_GREATAXE,25),
    DHAROKS_PLATEBODY(ItemIdentifiers.DHAROKS_PLATEBODY,25),
    DHAROKS_PLATELEGS(ItemIdentifiers.DHAROKS_PLATELEGS,25),
    AHRIMS_HOOD(ItemIdentifiers.AHRIMS_HOOD,15),
    AHRIMS_STAFF(ItemIdentifiers.AHRIMS_STAFF,15),
    AHRIMS_ROBETOP(ItemIdentifiers.AHRIMS_ROBETOP,15),
    AHRIMS_ROBESKIRT(ItemIdentifiers.AHRIMS_ROBESKIRT,15),
    GUTHANS_HELM(ItemIdentifiers.GUTHANS_HELM,15),
    GUTHANS_WARSPEAR(ItemIdentifiers.GUTHANS_WARSPEAR,15),
    GUTHANS_PLATEBODY(ItemIdentifiers.GUTHANS_PLATEBODY,15),
    GUTHANS_CHAINSKIRT(ItemIdentifiers.GUTHANS_CHAINSKIRT,15),
    KARILS_COIF(ItemIdentifiers.KARILS_COIF,15),
    KARILS_CROSSBOW(ItemIdentifiers.KARILS_CROSSBOW,15),
    KARILS_LEATHERTOP(ItemIdentifiers.KARILS_LEATHERTOP,15),
    KARILS_LEATHERSKIRT(ItemIdentifiers.KARILS_LEATHERSKIRT,15),
    TORAGS_HELM(ItemIdentifiers.TORAGS_HELM,15),
    TORAGS_HAMMERS(ItemIdentifiers.TORAGS_HAMMERS,15),
    TORAGS_PLATEBODY(ItemIdentifiers.TORAGS_PLATEBODY,15),
    TORAGS_PLATELEGS(ItemIdentifiers.TORAGS_PLATELEGS,15),
    VERACS_HELM(ItemIdentifiers.VERACS_HELM,15),
    VERACS_FLAIL(ItemIdentifiers.VERACS_FLAIL,15),
    VERACS_BRASSARD(ItemIdentifiers.VERACS_BRASSARD,15),
    VERACS_PLATESKIRT(ItemIdentifiers.VERACS_PLATESKIRT,15),
    SAMURAI_BOOTS(ItemIdentifiers.SAMURAI_BOOTS,500),
    SAMURAI_GLOVES(ItemIdentifiers.SAMURAI_GLOVES,500),
    SAMURAI_GREAVES(ItemIdentifiers.SAMURAI_GREAVES,500),
    SAMURAI_KASA(ItemIdentifiers.SAMURAI_KASA,500),
    SAMURAI_SHIRT(ItemIdentifiers.SAMURAI_SHIRT,500),
    MUMMYS_BODY(ItemIdentifiers.MUMMYS_BODY,500),
    MUMMYS_FEET(ItemIdentifiers.MUMMYS_FEET,500),
    MUMMYS_HANDS(ItemIdentifiers.MUMMYS_HANDS,500),
    MUMMYS_HEAD(ItemIdentifiers.MUMMYS_HEAD,500),
    MUMMYS_LEGS(ItemIdentifiers.MUMMYS_LEGS,500),
    TOM_RIDDLE_DIARY(CustomItemIdentifiers.TOM_RIDDLE_DIARY,2350),
    _3RD_AGE_AMULET(ItemIdentifiers._3RD_AGE_AMULET,1000),
    RANGER_BOOTS(ItemIdentifiers.RANGER_BOOTS,20),
    SMOKE_BATTLESTAFF(ItemIdentifiers.SMOKE_BATTLESTAFF,25),
    ABYSSAL_DAGGER(ItemIdentifiers.ABYSSAL_DAGGER,100),
    ABYSSAL_DAGGER_P_13271(ItemIdentifiers.ABYSSAL_DAGGER_P_13271,100),
    ABYSSAL_DAGGER_P_13269(ItemIdentifiers.ABYSSAL_DAGGER_P_13269,100),
    ABYSSAL_DAGGER_P(ItemIdentifiers.ABYSSAL_DAGGER_P,100),
    DRAGON_CHAINBODY(ItemIdentifiers.DRAGON_CHAINBODY_3140,50),
    DARK_BOW(ItemIdentifiers.DARK_BOW,10),
    ABYSSAL_WHIP(ItemIdentifiers.ABYSSAL_WHIP,10),
    _3RD_AGE_RANGE_TOP(ItemIdentifiers._3RD_AGE_RANGE_TOP,1500),
    _3RD_AGE_RANGE_LEGS(ItemIdentifiers._3RD_AGE_RANGE_LEGS,1500),
    _3RD_AGE_RANGE_COIF(ItemIdentifiers._3RD_AGE_RANGE_COIF,1000),
    _3RD_AGE_VAMBRACES(ItemIdentifiers._3RD_AGE_VAMBRACES,1500),
    _3RD_AGE_ROBE_TOP(ItemIdentifiers._3RD_AGE_ROBE_TOP,1500),
    _3RD_AGE_ROBE(ItemIdentifiers._3RD_AGE_ROBE,1500),
    _3RD_AGE_MAGE_HAT(ItemIdentifiers._3RD_AGE_MAGE_HAT,1000),
    _3RD_AGE_PLATELEGS(ItemIdentifiers._3RD_AGE_PLATELEGS,1500),
    _3RD_AGE_PLATEBODY(ItemIdentifiers._3RD_AGE_PLATEBODY,1500),
    _3RD_AGE_FULL_HELMET(ItemIdentifiers._3RD_AGE_FULL_HELMET,1000),
    _3RD_AGE_KITESHIELD(ItemIdentifiers._3RD_AGE_KITESHIELD,1500),
    SCYTHE(ItemIdentifiers.SCYTHE,50),
    BANDANA_EYEPATCH_8927(ItemIdentifiers.BANDANA_EYEPATCH_8927,50),
    BLACK_CAVALIER(ItemIdentifiers.BLACK_CAVALIER,50),
    BLUE_HEADBAND(ItemIdentifiers.BLUE_HEADBAND,50),
    AFRO(ItemIdentifiers.AFRO,50),
    BEANIE(ItemIdentifiers.BEANIE,50),
    DECORATIVE_ARMOUR_11899(ItemIdentifiers.DECORATIVE_ARMOUR_11899,50),
    DECORATIVE_ARMOUR_11900(ItemIdentifiers.DECORATIVE_ARMOUR_11900,50),
    DECORATIVE_ARMOUR_11898(ItemIdentifiers.DECORATIVE_ARMOUR_11898,50),
    DECORATIVE_ARMOUR_11896(ItemIdentifiers.DECORATIVE_ARMOUR_11896,50),
    DECORATIVE_ARMOUR_11897(ItemIdentifiers.DECORATIVE_ARMOUR_11897,50),
    BLACK_CANE(ItemIdentifiers.BLACK_CANE,50),
    ADAMANT_CANE(ItemIdentifiers.ADAMANT_CANE,50),
    IRON_DRAGON_MASK(ItemIdentifiers.IRON_DRAGON_MASK,50),
    STEEL_DRAGON_MASK(ItemIdentifiers.STEEL_DRAGON_MASK,50),
    MITHRIL_DRAGON_MASK(ItemIdentifiers.MITHRIL_DRAGON_MASK,50),
    GREEN_DRAGON_MASK(ItemIdentifiers.GREEN_DRAGON_MASK,50),
    RED_DRAGON_MASK(ItemIdentifiers.RED_DRAGON_MASK,50),
    BLACK_DRAGON_MASK(ItemIdentifiers.BLACK_DRAGON_MASK,50),
    GRANITE_MAUL_24225(ItemIdentifiers.GRANITE_MAUL_24225,50),
    BERSERKER_NECKLACE(ItemIdentifiers.BERSERKER_NECKLACE,50),
    FLIPPERS(ItemIdentifiers.FLIPPERS,50),
    RUBBER_CHICKEN(ItemIdentifiers.RUBBER_CHICKEN,50),
    MUDSKIPPER_HAT(ItemIdentifiers.MUDSKIPPER_HAT,50),
    COW_MASK(ItemIdentifiers.COW_MASK,50),
    COW_TOP(ItemIdentifiers.COW_TOP,50),
    COW_TROUSERS(ItemIdentifiers.COW_TROUSERS,50),
    COW_GLOVES(ItemIdentifiers.COW_GLOVES,50),
    COW_SHOES(ItemIdentifiers.COW_SHOES,50),
    RUNE_CANE(ItemIdentifiers.RUNE_CANE,50),
    DRAGON_CANE(ItemIdentifiers.DRAGON_CANE,50),
    BRONZE_DRAGON_MASK(ItemIdentifiers.BRONZE_DRAGON_MASK,50),
    ELDER_CHAOS_TOP(ItemIdentifiers.ELDER_CHAOS_TOP,50),
    ELDER_CHAOS_ROBE(ItemIdentifiers.ELDER_CHAOS_ROBE,50),
    ELDER_CHAOS_HOOD(ItemIdentifiers.ELDER_CHAOS_HOOD,50),
    FANCY_TIARA(ItemIdentifiers.FANCY_TIARA,50),
    BOWL_WIG(ItemIdentifiers.BOWL_WIG,50),
    LESSER_DEMON_MASK(ItemIdentifiers.LESSER_DEMON_MASK,50),
    GREATER_DEMON_MASK(ItemIdentifiers.GREATER_DEMON_MASK,50),
    BLACK_DEMON_MASK(ItemIdentifiers.BLACK_DEMON_MASK,50),
    OLD_DEMON_MASK(ItemIdentifiers.OLD_DEMON_MASK,50),
    JUNGLE_DEMON_MASK(ItemIdentifiers.JUNGLE_DEMON_MASK,50),
    ROYAL_CROWN(ItemIdentifiers.ROYAL_CROWN,100),
    ROYAL_GOWN_BOTTOM(ItemIdentifiers.ROYAL_GOWN_BOTTOM,100),
    ROYAL_SCEPTRE(ItemIdentifiers.ROYAL_SCEPTRE,100),
    ROYAL_GOWN_TOP(ItemIdentifiers.ROYAL_GOWN_TOP,100),
    _3RD_AGE_WAND(ItemIdentifiers._3RD_AGE_WAND,1500),
    _3RD_AGE_BOW(ItemIdentifiers._3RD_AGE_BOW,1500),
    _3RD_AGE_LONGSWORD(ItemIdentifiers._3RD_AGE_LONGSWORD,1500),
    GLOVES_OF_DARKNESS(ItemIdentifiers.GLOVES_OF_DARKNESS,500),
    HOOD_OF_DARKNESS(ItemIdentifiers.HOOD_OF_DARKNESS,500),
    ROBE_BOTTOM_OF_DARKNESS(ItemIdentifiers.ROBE_BOTTOM_OF_DARKNESS,500),
    ROBE_TOP_OF_DARKNESS(ItemIdentifiers.ROBE_TOP_OF_DARKNESS,500),
    MONKS_ROBE_G(ItemIdentifiers.MONKS_ROBE_G,50),
    MONKS_ROBE_TOP_G(ItemIdentifiers.MONKS_ROBE_TOP_G,50),
    DRAGON_BOOTS(ItemIdentifiers.DRAGON_BOOTS,50),
    WIZARD_BOOTS(ItemIdentifiers.WIZARD_BOOTS,10),
    TEAM_CAPE_X(ItemIdentifiers.TEAM_CAPE_X,450),
    RUNE_POUCH_I(CustomItemIdentifiers.RUNE_POUCH_I,500),
    LAVA_WHIP(CustomItemIdentifiers.LAVA_WHIP,50),
    FROST_WHIP(CustomItemIdentifiers.FROST_WHIP,50),
    NEW_CRYSTAL_HALBERD_FULL(ItemIdentifiers.NEW_CRYSTAL_HALBERD_FULL,200),
    NEW_CRYSTAL_BOW(ItemIdentifiers.NEW_CRYSTAL_BOW,25),
    NEW_CRYSTAL_SHIELD(ItemIdentifiers.NEW_CRYSTAL_SHIELD,25),
    PEGASIAN_CRYSTAL(ItemIdentifiers.PEGASIAN_CRYSTAL,80),
    PRIMORDIAL_CRYSTAL(ItemIdentifiers.PRIMORDIAL_CRYSTAL,80),
    ETERNAL_CRYSTAL(ItemIdentifiers.ETERNAL_CRYSTAL,80),
    PEGASIAN_BOOTS(ItemIdentifiers.PEGASIAN_BOOTS,80),
    PRIMORDIAL_BOOTS(ItemIdentifiers.PRIMORDIAL_BOOTS,80),
    ETERNAL_BOOTS(ItemIdentifiers.ETERNAL_BOOTS,80),
    GILDED_2H_SWORD(ItemIdentifiers.GILDED_2H_SWORD,200),
    GILDED_CHAINBODY(ItemIdentifiers.GILDED_CHAINBODY,200),
    GILDED_HASTA(ItemIdentifiers.GILDED_HASTA,200),
    GILDED_SPEAR(ItemIdentifiers.GILDED_SPEAR,200),
    GILDED_MED_HELM(ItemIdentifiers.GILDED_MED_HELM,200),
    GILDED_SQ_SHIELD(ItemIdentifiers.GILDED_SQ_SHIELD,200),
    GILDED_BOOTS(ItemIdentifiers.GILDED_BOOTS,200),
    GILDED_FULL_HELM(ItemIdentifiers.GILDED_FULL_HELM,200),
    GILDED_PLATEBODY(ItemIdentifiers.GILDED_PLATEBODY,200),
    GILDED_PLATELEGS(ItemIdentifiers.GILDED_PLATELEGS,200),
    GILDED_PLATESKIRT(ItemIdentifiers.GILDED_PLATESKIRT,200),
    GILDED_KITESHIELD(ItemIdentifiers.GILDED_KITESHIELD,200),
    ;

    Cart(int item, int value) {
        this.item = item;
        this.value = value;
    }

    /**
     * The item that will be dispensed into Valinor coins.
     */
    public final int item;

    /**
     * The amount of coins we get in return.
     */
    public final int value;

    /**
     * A getter to gather enum info for specific items such as the value.
     * @param itemId The item to check
     * @return The info
     */
    public static Optional<Cart> get(int itemId) {
        for (Cart cart : Cart.values()) {
            if (cart.item == itemId) {
                return Optional.of(cart);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "Cart{" +
            "item=" + item +
            ", value=" + value +
            '}';
    }
}

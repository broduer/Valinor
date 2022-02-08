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
    HEAVY_BALLISTA(ItemIdentifiers.HEAVY_BALLISTA,700),
    DINHS_BULWARK(ItemIdentifiers.DINHS_BULWARK,700),
    ARMADYL_GODSWORD(ItemIdentifiers.ARMADYL_GODSWORD,500),
    BLESSED_SARADOMIN_SWORD(ItemIdentifiers.SARADOMINS_BLESSED_SWORD,250),
    FEROCIOUS_GLOVES(ItemIdentifiers.FEROCIOUS_GLOVES, 1750),
    INQUISITORS_MACE(ItemIdentifiers.INQUISITORS_MACE, 1000),
    AMULET_OF_TORTURE_OR(ItemIdentifiers.AMULET_OF_TORTURE_OR, 1200),
    NECKLACE_OF_ANGUISH_OR(ItemIdentifiers.NECKLACE_OF_ANGUISH_OR, 1200),
    OCCULT_NECKLACE_OR(ItemIdentifiers.OCCULT_NECKLACE_OR, 450),
    TORMENTED_BRACELET_OR(ItemIdentifiers.TORMENTED_BRACELET_OR, 1200),
    TOXIC_STAFF_OF_THE_DEAD_C(CustomItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD_C, 1000),
    CRAWS_BOW_C(CustomItemIdentifiers.CRAWS_BOW_C, 3200),
    VIGGORAS_CHAINMACE_C(CustomItemIdentifiers.VIGGORAS_CHAINMACE_C, 3200),
    THAMMARONS_STAFF_C(CustomItemIdentifiers.THAMMARONS_STAFF_C, 3200),
    ANCESTRAL_HAT_I(CustomItemIdentifiers.ANCESTRAL_HAT_I, 1200),
    ANCESTRAL_ROBE_TOP_I(CustomItemIdentifiers.ANCESTRAL_ROBE_TOP_I, 1500),
    ANCESTRAL_ROBE_BOTTOM_I(CustomItemIdentifiers.ANCESTRAL_ROBE_BOTTOM_I, 1500),
    TALONHAWK_CROSSBOW(CustomItemIdentifiers.TALONHAWK_CROSSBOW, 4500),
    LAVA_DHIDE_COIF(CustomItemIdentifiers.LAVA_DHIDE_COIF, 1000),
    LAVA_DHIDE_BODY(CustomItemIdentifiers.LAVA_DHIDE_BODY, 1000),
    LAVA_DHIDE_CHAPS(CustomItemIdentifiers.LAVA_DHIDE_CHAPS, 1000),
    ANCIENT_VESTAS_LONGSWORD(CustomItemIdentifiers.ANCIENT_VESTAS_LONGSWORD,1750),
    ANCIENT_STATIUSS_WARHAMMER(CustomItemIdentifiers.ANCIENT_STATIUSS_WARHAMMER,1750),
    ANCIENT_WARRIOR_AXE_C(CustomItemIdentifiers.ANCIENT_WARRIOR_AXE_C,3500),
    ANCIENT_WARRIOR_MAUL_C(CustomItemIdentifiers.ANCIENT_WARRIOR_MAUL_C,4500),
    ANCIENT_WARRIOR_SWORD_C(CustomItemIdentifiers.ANCIENT_WARRIOR_SWORD_C,4500),
    ANCIENT_FACEGAURD(CustomItemIdentifiers.ANCIENT_FACEGAURD,3250),
    NEITIZNOT_FACEGUARD(ItemIdentifiers.NEITIZNOT_FACEGUARD,800),
    ANCIENT_WARRIOR_AXE(CustomItemIdentifiers.ANCIENT_WARRIOR_AXE,2050),
    ANCIENT_WARRIOR_MAUL(CustomItemIdentifiers.ANCIENT_WARRIOR_MAUL,2050),
    ANCIENT_WARRIOR_SWORD(CustomItemIdentifiers.ANCIENT_WARRIOR_SWORD,2050),
    DRAGON_HUNTER_CROSSBOW(ItemIdentifiers.DRAGON_HUNTER_CROSSBOW,825),
    ELDER_MAUL(ItemIdentifiers.ELDER_MAUL,2750),
    MAGMA_HELM(ItemIdentifiers.MAGMA_HELM,700),
    TANZANITE_HELM(ItemIdentifiers.TANZANITE_HELM,700),
    ANCIENT_WYVERN_SHIELD(ItemIdentifiers.ANCIENT_WYVERN_SHIELD,350),
    DRAGONFRIE_WARD(ItemIdentifiers.DRAGONFIRE_WARD,250),
    SERPENTINE_HELM(ItemIdentifiers.SERPENTINE_HELM,500),
    TRIDENT_OF_THE_SWAMP(ItemIdentifiers.TRIDENT_OF_THE_SWAMP,550),
    TOXIC_BLOWPIPE(ItemIdentifiers.TOXIC_BLOWPIPE,500),
    TOXIC_STAFF_OF_THE_DEAD(ItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD,800),
    ANKOU_GLOVES(ItemIdentifiers.ANKOU_GLOVES,1000),
    ANKOU_MASK(ItemIdentifiers.ANKOU_MASK,1000),
    ANKOU_SOCKS(ItemIdentifiers.ANKOU_SOCKS,1000),
    ANKOU_TOP(ItemIdentifiers.ANKOU_TOP,1000),
    ANKOUS_LEGGINGS(ItemIdentifiers.ANKOUS_LEGGINGS,1000),
    DEXTEROUS_PRAYER_SCROLL(ItemIdentifiers.DEXTEROUS_PRAYER_SCROLL,1250),
    ARCANE_PRAYER_SCROLL(ItemIdentifiers.ARCANE_PRAYER_SCROLL,1250),
    INQUISITORS_GREAT_HELM(ItemIdentifiers.INQUISITORS_GREAT_HELM,400),
    INQUISITORS_HAUBERK(ItemIdentifiers.INQUISITORS_HAUBERK,1000),
    INQUISITORS_PLATESKIRT(ItemIdentifiers.INQUISITORS_PLATESKIRT,1000),
    SPECTRAL_SPIRIT_SHIELD(ItemIdentifiers.SPECTRAL_SPIRIT_SHIELD,75),
    NECKLACE_OF_ANGUISH(ItemIdentifiers.NECKLACE_OF_ANGUISH,1250),
    AMULET_OF_TORTURE(ItemIdentifiers.AMULET_OF_TORTURE,1250),
    TORMENTED_BRACELET(ItemIdentifiers.TORMENTED_BRACELET,1250),
    RING_OF_SUFFERING(ItemIdentifiers.RING_OF_SUFFERING,800),
    DRAGON_WARHAMMER(ItemIdentifiers.DRAGON_WARHAMMER,1025),
    BLADE_OF_SAELDOR(ItemIdentifiers.BLADE_OF_SAELDOR,3000),
    DRAGON_HUNTER_LANCE(ItemIdentifiers.DRAGON_HUNTER_LANCE,1025),
    ARMADYL_GODSWORD_OR(ItemIdentifiers.ARMADYL_GODSWORD_OR,1000),
    BANDOS_GODSWORD_OR(ItemIdentifiers.BANDOS_GODSWORD_OR,500),
    SARADOMIN_GODSWORD_OR(ItemIdentifiers.SARADOMIN_GODSWORD_OR,500),
    ZAMORAK_GODSWORD_OR(ItemIdentifiers.ZAMORAK_GODSWORD_OR,500),
    DRAGON_CLAWS(ItemIdentifiers.DRAGON_CLAWS,500),
    DRAGON_CLAWS_OR(CustomItemIdentifiers.DRAGON_CLAWS_OR,1000),
    TWISTED_BOW(ItemIdentifiers.TWISTED_BOW,5000),
    SCYTHE_OF_VITUR(ItemIdentifiers.SCYTHE_OF_VITUR,5000),
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
    INFERNAL_CAPE(ItemIdentifiers.INFERNAL_CAPE,3000),
    ARCANE_SPIRIT_SHIELD(ItemIdentifiers.ARCANE_SPIRIT_SHIELD,1850),
    ANCESTRAL_HAT(ItemIdentifiers.ANCESTRAL_HAT,800),
    ANCESTRAL_ROBE_TOP(ItemIdentifiers.ANCESTRAL_ROBE_TOP,1000),
    ANCESTRAL_ROBE_BOTTOM(ItemIdentifiers.ANCESTRAL_ROBE_BOTTOM,1000),
    TWISTED_ANCESTRAL_HAT(ItemIdentifiers.TWISTED_ANCESTRAL_HAT,1200),
    TWISTED_ANCESTRAL_ROBE_TOP(ItemIdentifiers.TWISTED_ANCESTRAL_ROBE_TOP,1500),
    TWISTED_ANCESTRAL_ROBE_BOTTOM(ItemIdentifiers.TWISTED_ANCESTRAL_ROBE_BOTTOM,1500),
    JUSTICIAR_FACEGUARD(ItemIdentifiers.JUSTICIAR_FACEGUARD,2000),
    JUSTICIAR_CHESTGUARD(ItemIdentifiers.JUSTICIAR_CHESTGUARD,2000),
    JUSTICIAR_LEGGUARDS(ItemIdentifiers.JUSTICIAR_LEGGUARDS,2000),
    VOLATILE_ORB(ItemIdentifiers.VOLATILE_ORB,2200),
    HARMONISED_ORB(ItemIdentifiers.HARMONISED_ORB,1400),
    ELDRITCH_ORB(ItemIdentifiers.ELDRITCH_ORB,1000),
    NIGHTMARE_STAFF(ItemIdentifiers.NIGHTMARE_STAFF,1500),
    VIGGORAS_CHAINMACE(ItemIdentifiers.VIGGORAS_CHAINMACE,850),
    CRAWS_BOW(ItemIdentifiers.CRAWS_BOW,800),
    THAMMARONS_SCEPTRE(ItemIdentifiers.THAMMARONS_SCEPTRE,550),
    AMULET_OF_AVARICE(ItemIdentifiers.AMULET_OF_AVARICE,550),
    KODAI_WAND(ItemIdentifiers.KODAI_WAND,2500),
    GHRAZI_RAPIER(ItemIdentifiers.GHRAZI_RAPIER,2500),
    ODIUM_WARD(ItemIdentifiers.ODIUM_WARD,100),
    MALEDICTION_WARD(ItemIdentifiers.MALEDICTION_WARD,100),
    BANDOS_GODSWORD(ItemIdentifiers.BANDOS_GODSWORD,200),
    ZAMORAK_GODSWORD(ItemIdentifiers.ZAMORAK_GODSWORD,200),
    SARADOMIN_GODSWORD(ItemIdentifiers.SARADOMIN_GODSWORD,200),
    INFINITY_HAT(ItemIdentifiers.INFINITY_HAT,10),
    INFINITY_TOP(ItemIdentifiers.INFINITY_TOP,10),
    INFINITY_BOTTOMS(ItemIdentifiers.INFINITY_BOTTOMS,10),
    INFINITY_BOOTS(ItemIdentifiers.INFINITY_BOOTS,10),
    INFINITY_GLOVES(ItemIdentifiers.INFINITY_GLOVES,10),
    MASTER_WAND(ItemIdentifiers.MASTER_WAND,10),
    MAGES_BOOK(ItemIdentifiers.MAGES_BOOK,10),
    ARMADYL_HELMET(ItemIdentifiers.ARMADYL_HELMET,200),
    ARMADYL_CHESTPLATE(ItemIdentifiers.ARMADYL_CHESTPLATE,200),
    ARMADYL_CHAINSKIRT(ItemIdentifiers.ARMADYL_CHAINSKIRT,200),
    BANDOS_CHESTPLATE(ItemIdentifiers.BANDOS_CHESTPLATE,200),
    BANDOS_TASSETS(ItemIdentifiers.BANDOS_TASSETS,200),
    BANDOS_BOOTS(ItemIdentifiers.BANDOS_BOOTS,100),
    STAFF_OF_THE_DEAD(ItemIdentifiers.STAFF_OF_THE_DEAD,350),
    ARMADYL_CROSSBOW(ItemIdentifiers.ARMADYL_CROSSBOW,400),
    ZAMORAKIAN_SPEAR(ItemIdentifiers.ZAMORAKIAN_SPEAR,200),
    ZAMORAKIAN_HASTA(ItemIdentifiers.ZAMORAKIAN_HASTA,200),
    KRAKEN_TENTACLE(ItemIdentifiers.KRAKEN_TENTACLE,50),
    ABYSSAL_TENTACLE(ItemIdentifiers.ABYSSAL_TENTACLE,100),
    TORVA_FULL_HELM(ItemIdentifiers.TORVA_FULL_HELM,3000),
    TORVA_PLATELEGS(ItemIdentifiers.TORVA_PLATELEGS,3000),
    TORVA_PLATEBODY(ItemIdentifiers.TORVA_PLATEBODY,3000),
    BERSERKER_RING_I(ItemIdentifiers.BERSERKER_RING_I,50),
    WARRIOR_RING_I(ItemIdentifiers.WARRIOR_RING_I,50),
    SEERS_RING_I(ItemIdentifiers.SEERS_RING_I,50),
    ARCHERS_RING_I(ItemIdentifiers.ARCHERS_RING_I,50),
    SWORD_OF_GRYFFINDOR(CustomItemIdentifiers.SWORD_OF_GRYFFINDOR,4000),
    ANCIENT_GODSWORD(ItemIdentifiers.ANCIENT_GODSWORD,4000),
    RED_PARTYHAT(ItemIdentifiers.RED_PARTYHAT,550),
    YELLOW_PARTYHAT(ItemIdentifiers.YELLOW_PARTYHAT,550),
    BLUE_PARTYHAT(ItemIdentifiers.BLUE_PARTYHAT,550),
    GREEN_PARTYHAT(ItemIdentifiers.GREEN_PARTYHAT,550),
    PURPLE_PARTYHAT(ItemIdentifiers.PURPLE_PARTYHAT,550),
    WHITE_PARTYHAT(ItemIdentifiers.WHITE_PARTYHAT,550),
    GREEN_HALLOWEEN_MASK(ItemIdentifiers.GREEN_HALLOWEEN_MASK,150),
    BLUE_HALLOWEEN_MASK(ItemIdentifiers.BLUE_HALLOWEEN_MASK,150),
    RED_HALLOWEEN_MASK(ItemIdentifiers.RED_HALLOWEEN_MASK,150),
    DHAROKS_HELM(ItemIdentifiers.DHAROKS_HELM,50),
    DHAROKS_GREATAXE(ItemIdentifiers.DHAROKS_GREATAXE,50),
    DHAROKS_PLATEBODY(ItemIdentifiers.DHAROKS_PLATEBODY,50),
    DHAROKS_PLATELEGS(ItemIdentifiers.DHAROKS_PLATELEGS,50),
    AHRIMS_HOOD(ItemIdentifiers.AHRIMS_HOOD,50),
    AHRIMS_STAFF(ItemIdentifiers.AHRIMS_STAFF,50),
    AHRIMS_ROBETOP(ItemIdentifiers.AHRIMS_ROBETOP,50),
    AHRIMS_ROBESKIRT(ItemIdentifiers.AHRIMS_ROBESKIRT,50),
    GUTHANS_HELM(ItemIdentifiers.GUTHANS_HELM,50),
    GUTHANS_WARSPEAR(ItemIdentifiers.GUTHANS_WARSPEAR,50),
    GUTHANS_PLATEBODY(ItemIdentifiers.GUTHANS_PLATEBODY,50),
    GUTHANS_CHAINSKIRT(ItemIdentifiers.GUTHANS_CHAINSKIRT,50),
    KARILS_COIF(ItemIdentifiers.KARILS_COIF,50),
    KARILS_CROSSBOW(ItemIdentifiers.KARILS_CROSSBOW,50),
    KARILS_LEATHERTOP(ItemIdentifiers.KARILS_LEATHERTOP,50),
    KARILS_LEATHERSKIRT(ItemIdentifiers.KARILS_LEATHERSKIRT,50),
    TORAGS_HELM(ItemIdentifiers.TORAGS_HELM,50),
    TORAGS_HAMMERS(ItemIdentifiers.TORAGS_HAMMERS,50),
    TORAGS_PLATEBODY(ItemIdentifiers.TORAGS_PLATEBODY,50),
    TORAGS_PLATELEGS(ItemIdentifiers.TORAGS_PLATELEGS,50),
    VERACS_HELM(ItemIdentifiers.VERACS_HELM,50),
    VERACS_FLAIL(ItemIdentifiers.VERACS_FLAIL,50),
    VERACS_BRASSARD(ItemIdentifiers.VERACS_BRASSARD,50),
    VERACS_PLATESKIRT(ItemIdentifiers.VERACS_PLATESKIRT,50),
    SAMURAI_BOOTS(ItemIdentifiers.SAMURAI_BOOTS,1000),
    SAMURAI_GLOVES(ItemIdentifiers.SAMURAI_GLOVES,1000),
    SAMURAI_GREAVES(ItemIdentifiers.SAMURAI_GREAVES,1000),
    SAMURAI_KASA(ItemIdentifiers.SAMURAI_KASA,1000),
    SAMURAI_SHIRT(ItemIdentifiers.SAMURAI_SHIRT,1000),
    MUMMYS_BODY(ItemIdentifiers.MUMMYS_BODY,1000),
    MUMMYS_FEET(ItemIdentifiers.MUMMYS_FEET,1000),
    MUMMYS_HANDS(ItemIdentifiers.MUMMYS_HANDS,1000),
    MUMMYS_HEAD(ItemIdentifiers.MUMMYS_HEAD,1000),
    MUMMYS_LEGS(ItemIdentifiers.MUMMYS_LEGS,1000),
    TOM_RIDDLE_DIARY(CustomItemIdentifiers.TOM_RIDDLE_DIARY,2350),
    _3RD_AGE_AMULET(ItemIdentifiers._3RD_AGE_AMULET,1000),
    RANGER_BOOTS(ItemIdentifiers.RANGER_BOOTS,100),
    SMOKE_BATTLESTAFF(ItemIdentifiers.SMOKE_BATTLESTAFF,25),
    ABYSSAL_DAGGER(ItemIdentifiers.ABYSSAL_DAGGER,100),
    ABYSSAL_DAGGER_P_13271(ItemIdentifiers.ABYSSAL_DAGGER_P_13271,100),
    ABYSSAL_DAGGER_P_13269(ItemIdentifiers.ABYSSAL_DAGGER_P_13269,100),
    ABYSSAL_DAGGER_P(ItemIdentifiers.ABYSSAL_DAGGER_P,100),
    DRAGON_CHAINBODY(ItemIdentifiers.DRAGON_CHAINBODY_3140,50),
    DARK_BOW(ItemIdentifiers.DARK_BOW,50),
    ABYSSAL_WHIP(ItemIdentifiers.ABYSSAL_WHIP,50),
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
    SPIRIT_SHIELD(ItemIdentifiers.SPIRIT_SHIELD,50),
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

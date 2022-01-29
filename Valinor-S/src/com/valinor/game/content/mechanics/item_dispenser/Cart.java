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
    ELEMENTAL_BOW(CustomItemIdentifiers.ELEMENTAL_BOW,1000),
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
    CRAWS_BOW_C(CustomItemIdentifiers.CRAWS_BOW_C, 1200),
    VIGGORAS_CHAINMACE_C(CustomItemIdentifiers.VIGGORAS_CHAINMACE_C, 2200),
    THAMMARONS_STAFF_C(CustomItemIdentifiers.THAMMARONS_STAFF_C, 1200),
    ANCESTRAL_HAT_I(CustomItemIdentifiers.ANCESTRAL_HAT_I, 1200),
    ANCESTRAL_ROBE_TOP_I(CustomItemIdentifiers.ANCESTRAL_ROBE_TOP_I, 1500),
    ANCESTRAL_ROBE_BOTTOM_I(CustomItemIdentifiers.ANCESTRAL_ROBE_BOTTOM_I, 1500),
    TALONHAWK_CROSSBOW(CustomItemIdentifiers.TALONHAWK_CROSSBOW, 3500),
    LAVA_DHIDE_COIF(CustomItemIdentifiers.LAVA_DHIDE_COIF, 100),
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
    DRAGON_HUNTER_CROSSBOW(ItemIdentifiers.DRAGON_HUNTER_CROSSBOW,125),
    ELDER_MAUL(ItemIdentifiers.ELDER_MAUL,1750),
    MAGMA_HELM(ItemIdentifiers.MAGMA_HELM,700),
    TANZANITE_HELM(ItemIdentifiers.TANZANITE_HELM,700),
    ANCIENT_WYVERN_SHIELD(ItemIdentifiers.ANCIENT_WYVERN_SHIELD,350),
    DRAGONFRIE_WARD(ItemIdentifiers.DRAGONFIRE_WARD,250),
    SERPENTINE_HELM(ItemIdentifiers.SERPENTINE_HELM,500),
    TRIDENT_OF_THE_SWAMP(ItemIdentifiers.TRIDENT_OF_THE_SWAMP,550),
    TOXIC_BLOWPIPE(ItemIdentifiers.TOXIC_BLOWPIPE,1000),
    TOXIC_STAFF_OF_THE_DEAD(ItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD,1000),
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
    VESTAS_CHAINBODY(ItemIdentifiers.VESTAS_CHAINBODY,400),
    VESTAS_PLATESKIRT(ItemIdentifiers.VESTAS_PLATESKIRT,400),
    VESTAS_LONGSWORD(ItemIdentifiers.VESTAS_LONGSWORD,1000),
    VESTAS_SPEAR(ItemIdentifiers.VESTAS_SPEAR,600),
    STATIUSS_FULL_HELM(ItemIdentifiers.STATIUSS_FULL_HELM,250),
    STATIUSS_PLATEBODY(ItemIdentifiers.STATIUSS_PLATEBODY,250),
    STATIUSS_PLATELEGS(ItemIdentifiers.STATIUSS_PLATELEGS,250),
    STATIUSS_WARHAMMER(ItemIdentifiers.STATIUSS_WARHAMMER,550),
    MORRIGANS_COIF(ItemIdentifiers.MORRIGANS_COIF,250),
    MORRIGANS_LEATHER_BODY(ItemIdentifiers.MORRIGANS_LEATHER_BODY,250),
    MORRIGANS_LEATHER_CHAPS(ItemIdentifiers.MORRIGANS_LEATHER_CHAPS,250),
    ZURIEL_STAFF(ItemIdentifiers.ZURIELS_STAFF,550),
    ZURIELS_HOOD(ItemIdentifiers.ZURIELS_HOOD,250),
    ZURIELS_ROBE_TOP(ItemIdentifiers.ZURIELS_ROBE_TOP,250),
    ZURIELS_ROBE_BOTTOM(ItemIdentifiers.ZURIELS_ROBE_BOTTOM,250),
    AVERNIC_DEFENDER(ItemIdentifiers.AVERNIC_DEFENDER,1250),
    INFERNAL_CAPE(ItemIdentifiers.INFERNAL_CAPE,2000),
    ARCANE_SPIRIT_SHIELD(ItemIdentifiers.ARCANE_SPIRIT_SHIELD,850),
    ANCESTRAL_HAT(ItemIdentifiers.ANCESTRAL_HAT,600),
    ANCESTRAL_ROBE_TOP(ItemIdentifiers.ANCESTRAL_ROBE_TOP,1000),
    ANCESTRAL_ROBE_BOTTOM(ItemIdentifiers.ANCESTRAL_ROBE_BOTTOM,1000),
    TWISTED_ANCESTRAL_HAT(ItemIdentifiers.TWISTED_ANCESTRAL_HAT,800),
    TWISTED_ANCESTRAL_ROBE_TOP(ItemIdentifiers.TWISTED_ANCESTRAL_ROBE_TOP,1500),
    TWISTED_ANCESTRAL_ROBE_BOTTOM(ItemIdentifiers.TWISTED_ANCESTRAL_ROBE_BOTTOM,1500),
    JUSTICIAR_FACEGUARD(ItemIdentifiers.JUSTICIAR_FACEGUARD,500),
    JUSTICIAR_CHESTGUARD(ItemIdentifiers.JUSTICIAR_CHESTGUARD,700),
    JUSTICIAR_LEGGUARDS(ItemIdentifiers.JUSTICIAR_LEGGUARDS,700),
    VOLATILE_ORB(ItemIdentifiers.VOLATILE_ORB,1200),
    HARMONISED_ORB(ItemIdentifiers.HARMONISED_ORB,1200),
    ELDRITCH_ORB(ItemIdentifiers.ELDRITCH_ORB,400),
    NIGHTMARE_STAFF(ItemIdentifiers.NIGHTMARE_STAFF,1500),
    VIGGORAS_CHAINMACE(ItemIdentifiers.VIGGORAS_CHAINMACE,850),
    CRAWS_BOW(ItemIdentifiers.CRAWS_BOW,800),
    THAMMARONS_SCEPTRE(ItemIdentifiers.THAMMARONS_SCEPTRE,550),
    AMULET_OF_AVARICE(ItemIdentifiers.AMULET_OF_AVARICE,550),
    KODAI_WAND(ItemIdentifiers.KODAI_WAND,2500),
    GHRAZI_RAPIER(ItemIdentifiers.GHRAZI_RAPIER,1900),
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

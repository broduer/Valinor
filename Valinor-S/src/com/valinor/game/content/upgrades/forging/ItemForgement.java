package com.valinor.game.content.upgrades.forging;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.items.Item;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.valinor.game.content.upgrades.forging.ItemForgingTable.START_OF_FORGE_LIST;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * utility enum which writes all data to interface.
 *
 * @author Patrick van Elderen | Zerikoth (PVE) | 16 okt. 2019 : 10:04
 * @see <a href="https://github.com/Patrick9-10-1995">Github profile</a>
 */
public enum ItemForgement {

    //Weapons
    ARMADYL_GODSWORD("Armadyl godsword (or)", ItemForgingCategory.WEAPON, 10, new Item(ItemIdentifiers.ARMADYL_GODSWORD_OR), new Item(ItemIdentifiers.ARMADYL_GODSWORD, 1), AttributeKey.ARMADYL_GODSWORD_OR_ATTEMPTS),
    BANDOS_GODSWORD("Bandos godsword (or)", ItemForgingCategory.WEAPON, 25, new Item(BANDOS_GODSWORD_OR), new Item(ItemIdentifiers.BANDOS_GODSWORD, 1), AttributeKey.BANDOS_GODSWORD_OR_ATTEMPTS),
    SARADOMIN_GODSWORD("Saradomin godsword (or)", ItemForgingCategory.WEAPON, 25, new Item(SARADOMIN_GODSWORD_OR), new Item(ItemIdentifiers.SARADOMIN_GODSWORD, 1), AttributeKey.SARADOMIN_GODSWORD_OR_ATTEMPTS),
    ZAMORAK_GODSWORD("Zamorak godsword (or)", ItemForgingCategory.WEAPON, 25, new Item(ZAMORAK_GODSWORD_OR), new Item(ItemIdentifiers.ZAMORAK_GODSWORD, 1), AttributeKey.ZAMORAK_GODSWORD_OR_ATTEMPTS),
    GRANITE_MAUL("Granite maul (or)", ItemForgingCategory.WEAPON, 25, new Item(GRANITE_MAUL_12848), new Item(GRANITE_MAUL_24225, 5), AttributeKey.GRANITE_MAUL_OR_ATTEMPTS),
    DRAGON_CLAWS("Dragon claws (or)", ItemForgingCategory.WEAPON, 5, new Item(DRAGON_CLAWS_OR), new Item(ItemIdentifiers.DRAGON_CLAWS, 1), AttributeKey.DRAGON_CLAWS_OR_ATTEMPTS),
    ELDER_MAUL("Dark elder maul", ItemForgingCategory.WEAPON, 10, new Item(DARK_ELDER_MAUL), new Item(ItemIdentifiers.ELDER_MAUL, 1), AttributeKey.DARK_ELDER_MAUL_ATTEMPTS),
    MAGMA_BLOWPIPE("Magma blowpipe", ItemForgingCategory.WEAPON, 5, new Item(CustomItemIdentifiers.MAGMA_BLOWPIPE), new Item(TOXIC_BLOWPIPE,1), AttributeKey.MAGMA_BLOWPIPE_ATTEMPTS),
    HOLY_SANGUINESTI_STAFF("Holy sanguinesti staff", ItemForgingCategory.WEAPON, 20, new Item(ItemIdentifiers.HOLY_SANGUINESTI_STAFF), new Item(SANGUINESTI_STAFF,1), AttributeKey.HOLY_SANGUINESTI_STAFF_ATTEMPTS),
    HOLY_SCYHTE_OF_VITUR("Holy scythe of vitur", ItemForgingCategory.WEAPON, 30, new Item(HOLY_SCYTHE_OF_VITUR), new Item(SCYTHE_OF_VITUR,1), AttributeKey.HOLY_SCYTHE_OF_VITUR_ATTEMPTS),
    HOLY_GHRAZI_RAPIER("Holy ghrazi rapier", ItemForgingCategory.WEAPON, 20, new Item(ItemIdentifiers.HOLY_GHRAZI_RAPIER), new Item(GHRAZI_RAPIER,1), AttributeKey.HOLY_GHRAZI_RAPIER_ATTEMPTS),
    SANGUINE_SCYTHE_OF_VITUR("Sanguine scythe of vitur", ItemForgingCategory.WEAPON, 15, new Item(ItemIdentifiers.SANGUINE_SCYTHE_OF_VITUR), new Item(ItemIdentifiers.SCYTHE_OF_VITUR,1), AttributeKey.SANGUINE_SCYTHE_OF_VITUR_ATTEMPTS),
    VIGGORAS_CHAINMACE_C("Viggora's chainmace (c)", ItemForgingCategory.WEAPON, 10, new Item(CustomItemIdentifiers.VIGGORAS_CHAINMACE_C), new Item(VIGGORAS_CHAINMACE, 1), AttributeKey.CORRUPTED_VIGGORAS_CHAINMACE_ATTEMPTS),
    CRAWS_BOW_C("Craw's bow (c)", ItemForgingCategory.WEAPON, 10, new Item(CustomItemIdentifiers.CRAWS_BOW_C), new Item(CRAWS_BOW, 1), AttributeKey.CORRUPTED_CRAWS_BOW_ATTEMPTS),
    THAMMARONS_SCEPTRE_C("Thammaron's sceptre (c)", ItemForgingCategory.WEAPON, 5, new Item(CustomItemIdentifiers.THAMMARONS_STAFF_C), new Item(THAMMARONS_SCEPTRE,1), AttributeKey.CORRUPTED_THAMMARONS_STAFF_ATTEMPTS),
    SANGUINE_TWISTED_BOW("Sanguine twisted bow", ItemForgingCategory.WEAPON, 15, new Item(CustomItemIdentifiers.SANGUINE_TWISTED_BOW), new Item(ItemIdentifiers.TWISTED_BOW, 1), AttributeKey.SANGUINE_TWISTED_BOW_ATTEMTPS),
    TOXIC_STAFF_C("Toxic staff of the dead (c)", ItemForgingCategory.WEAPON, 25, new Item(TOXIC_STAFF_OF_THE_DEAD_C), new Item(TOXIC_STAFF_OF_THE_DEAD,4), AttributeKey.TOXIC_STAFF_OF_THE_DEAD_C_ATTEMPTS),

    //Armour
    AMULET_OF_FURY("Amulet of fury (or)", ItemForgingCategory.ARMOUR, 35, new Item(AMULET_OF_FURY_OR), new Item(ItemIdentifiers.AMULET_OF_FURY, 5), AttributeKey.FURY_OR_ATTEMPTS),
    OCCULT_NECKLACE("Occult necklace (or)", ItemForgingCategory.ARMOUR, 25, new Item(OCCULT_NECKLACE_OR), new Item(ItemIdentifiers.OCCULT_NECKLACE, 5), AttributeKey.OCCULT_OR_ATTEMPTS),
    AMULET_OF_TORTURE("Amulet of torture (or)", ItemForgingCategory.ARMOUR, 25, new Item(AMULET_OF_TORTURE_OR), new Item(ItemIdentifiers.AMULET_OF_TORTURE, 3), AttributeKey.TORTURE_OR_ATTEMPTS),
    NECKLACE_OF_ANGUISH("Necklace of anguish (or)", ItemForgingCategory.ARMOUR, 25, new Item(NECKLACE_OF_ANGUISH_OR), new Item(ItemIdentifiers.NECKLACE_OF_ANGUISH, 3), AttributeKey.ANGUISH_OR_ATTEMPTS),
    BERSERKER_NECKLACE("Berserker necklace (or)", ItemForgingCategory.ARMOUR, 35, new Item(BERSERKER_NECKLACE_OR), new Item(ItemIdentifiers.BERSERKER_NECKLACE, 5), AttributeKey.BERSERKER_NECKLACE_OR_ATTEMPTS),
    TORMENTED_BRACELET("Tormented bracelet (or)", ItemForgingCategory.ARMOUR, 25, new Item(TORMENTED_BRACELET_OR), new Item(ItemIdentifiers.TORMENTED_BRACELET, 3), AttributeKey.TORMENTED_BRACELET_OR_ATTEMPTS),
    ANCESTRAL_HAT_I("Ancestral hat (i)", ItemForgingCategory.ARMOUR, 30, new Item(CustomItemIdentifiers.ANCESTRAL_HAT_I), new Item(ANCESTRAL_HAT, 3), AttributeKey.ANCESTRAL_HAT_I_ATTEMPTS),
    ANCESTRAL_ROBE_TOP_I("Ancestral robe top (i)", ItemForgingCategory.ARMOUR, 25, new Item(CustomItemIdentifiers.ANCESTRAL_ROBE_TOP_I), new Item(ANCESTRAL_ROBE_TOP, 3), AttributeKey.ANCESTRAL_ROBE_TOP_I_ATTEMPTS),
    ANCESTRAL_ROBE_BOTTOM_I("Ancestral robe bottom (i)", ItemForgingCategory.ARMOUR, 25, new Item(CustomItemIdentifiers.ANCESTRAL_ROBE_BOTTOM_I), new Item(ANCESTRAL_ROBE_BOTTOM, 3), AttributeKey.ANCESTRAL_ROBE_BOTTOM_I_ATTEMPTS),
    PRIMORDIAL_BOOTS_OR("Primordial boots (or)", ItemForgingCategory.ARMOUR, 25, new Item(CustomItemIdentifiers.PRIMORDIAL_BOOTS_OR), new Item(PRIMORDIAL_BOOTS, 3), AttributeKey.PRIMORDIAL_BOOTS_OR_ATTEMPTS),
    PEGASIAN_BOOTS_OR("Pegasian boots (or)", ItemForgingCategory.ARMOUR, 25, new Item(CustomItemIdentifiers.PEGASIAN_BOOTS_OR), new Item(PEGASIAN_BOOTS, 3), AttributeKey.PEGASIAN_BOOTS_OR_ATTEMPTS),
    ETERNAL_BOOTS_OR("Eternal boots (or)", ItemForgingCategory.ARMOUR, 25, new Item(CustomItemIdentifiers.ETERNAL_BOOTS_OR), new Item(ETERNAL_BOOTS, 3), AttributeKey.ETERNAL_BOOTS_OR_ATTEMPTS),
    ANCIENT_FACEGUARD("Ancient faceguard", ItemForgingCategory.ARMOUR, 25, new Item(CustomItemIdentifiers.ANCIENT_FACEGAURD), new Item(NEITIZNOT_FACEGUARD,3), AttributeKey.ANCIENT_FACEGUARD_ATTEMPTS),

    //Misc
    RUNE_POUCH("Rune pouch (i)", ItemForgingCategory.MISC, 25, new Item(RUNE_POUCH_23650), new Item(ItemIdentifiers.RUNE_POUCH, 5), AttributeKey.RUNE_POUCH_I_ATTEMPTS),
    ;

    /**
     * forged item name.
     */
    public final String name;

    /**
     * item difficulty
     */
    public final ItemForgingCategory tier;

    /**
     * chance of successfully enchanting the item.
     */
    public final int successRate;

    /**
     * enchanted item reward.
     */
    public final Item enchantedItem;

    /**
     * items required to have a chance at upgrading.
     */
    public final Item requiredItem;

    /**
     * This key determines how many times we're tried to enchant this item.
     */
    public final AttributeKey attempts;

    ItemForgement(String name, ItemForgingCategory tier, int successRate, Item enchantedItem, Item requiredItem, AttributeKey attempts) {
        this.name = name;
        this.tier = tier;
        this.successRate = successRate;
        this.enchantedItem = enchantedItem;
        this.requiredItem = requiredItem;
        this.attempts = attempts;
    }

    public int button() {
        return START_OF_FORGE_LIST + ordinal();
    }

    public static List<ItemForgement> sortByTier(ItemForgingCategory tier) {
        return Arrays.stream(values()).filter(a -> a.tier == tier).sorted(Comparator.comparing(Enum::name)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ItemForgement{" +
            "name='" + name + '\'' +
            ", tier=" + tier +
            ", successRate=" + successRate +
            ", enchantedItem=" + enchantedItem +
            ", requiredItems=" + requiredItem +
            ", attempts=" + attempts +
            '}';
    }
}

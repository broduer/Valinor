package com.valinor.game.content.seasonal_events.rewards;

import com.valinor.game.world.items.Item;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since October 14, 2021
 */
public enum EventRewards {

    EVENT_REWARD_1(new Item(ItemIdentifiers.CRYSTAL_KEY), 210),
    EVENT_REWARD_2(new Item(CustomItemIdentifiers.SLAYER_KEY), 205),
    EVENT_REWARD_3(new Item(CustomItemIdentifiers.DOUBLE_DROPS_LAMP), 200),
    EVENT_REWARD_4(new Item(CustomItemIdentifiers.IMBUEMENT_SCROLL),195),
    EVENT_REWARD_5(new Item(ItemIdentifiers.MYSTIC_LAVA_STAFF), 190),
    EVENT_REWARD_6(new Item(ItemIdentifiers.BERSERKER_NECKLACE), 185),
    EVENT_REWARD_7(new Item(ItemIdentifiers.BRYOPHYTAS_ESSENCE), 180),
    EVENT_REWARD_8(new Item(ItemIdentifiers.DHAROKS_ARMOUR_SET), 175),
    EVENT_REWARD_9(new Item(ItemIdentifiers.GUTHANS_ARMOUR_SET), 170),
    EVENT_REWARD_10(new Item(ItemIdentifiers.ZAMORAK_HALO), 165),
    EVENT_REWARD_11(new Item(ItemIdentifiers.SARADOMIN_HALO), 160),
    EVENT_REWARD_12(new Item(ItemIdentifiers.GUTHIX_HALO), 155),
    EVENT_REWARD_13(new Item(ItemIdentifiers.ARCHERS_RING), 150),
    EVENT_REWARD_14(new Item(ItemIdentifiers.SEERS_RING), 145),
    EVENT_REWARD_15(new Item(ItemIdentifiers.ABYSSAL_WHIP), 140),
    EVENT_REWARD_16(new Item(ItemIdentifiers.SPIKED_MANACLES), 135),
    EVENT_REWARD_17(new Item(ItemIdentifiers.FREMENNIK_KILT), 130),
    EVENT_REWARD_18(new Item(ItemIdentifiers.LARRANS_KEY), 125),
    EVENT_REWARD_19(new Item(ItemIdentifiers.AMULET_OF_FURY), 120),
    EVENT_REWARD_20(new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), 115),
    EVENT_REWARD_21(new Item(CustomItemIdentifiers.MYSTERY_TICKET), 110),
    EVENT_REWARD_22(new Item(CustomItemIdentifiers.WILDERNESS_KEY), 105),
    EVENT_REWARD_23(new Item(CustomItemIdentifiers.POINTS_MYSTERY_BOX), 100),
    EVENT_REWARD_24(new Item(ItemIdentifiers.DRAGON_BOOTS), 95),
    EVENT_REWARD_25(new Item(ItemIdentifiers.ANCIENT_TOTEM), 90),
    EVENT_REWARD_26(new Item(CustomItemIdentifiers.FROST_WHIP), 85),
    EVENT_REWARD_27(new Item(CustomItemIdentifiers.LAVA_WHIP), 80),
    EVENT_REWARD_28(new Item(CustomItemIdentifiers.POINTS_MYSTERY_CHEST), 75),
    EVENT_REWARD_29(new Item(ItemIdentifiers.OBSIDIAN_CAPE_R), 70),
    EVENT_REWARD_30(new Item(ItemIdentifiers.BUCKET_HELM_G), 65),
    EVENT_REWARD_31(new Item(ItemIdentifiers.MALEDICTION_WARD), 60),
    EVENT_REWARD_32(new Item(ItemIdentifiers.DRAGON_FULL_HELM), 55),
    EVENT_REWARD_33(new Item(ItemIdentifiers.DRAGON_PLATEBODY), 50),
    EVENT_REWARD_34(new Item(ItemIdentifiers.DRAGON_SCIMITAR_OR), 45),
    EVENT_REWARD_35(new Item(ItemIdentifiers.LAVA_DRAGON_MASK), 40),
    EVENT_REWARD_36(new Item(ItemIdentifiers.BLACK_MASK), 35),
    EVENT_REWARD_37(new Item(CustomItemIdentifiers.ORANGE_PARTYHAT, 10), 30),
    EVENT_REWARD_38(new Item(ItemIdentifiers.DRACONIC_VISAGE), 25),
    EVENT_REWARD_39(new Item(CustomItemIdentifiers.FIVE_DOLLAR_BOND), 20),
    EVENT_REWARD_40(new Item(ItemIdentifiers.EXTRA_SUPPLY_CRATE), 15),
    EVENT_REWARD_41(new Item(CustomItemIdentifiers.KEY_OF_DROPS), 10),
    EVENT_REWARD_42(new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX), 5),
    EVENT_REWARD_43(new Item(ItemIdentifiers.INVERTED_SANTA_HAT), 2),
    EVENT_REWARD_44(new Item(CustomItemIdentifiers.SNOW_IMP_COSTUME), 0) // cos theres no amount on these items
    ;

    public final Item reward;
    public final int chance;

    EventRewards(Item reward, int chance) {
        this.reward = reward;
        this.chance = chance;
    }
}

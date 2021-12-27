package com.valinor.game.content.mechanics.break_items;

import com.valinor.util.ItemIdentifiers;

import java.util.HashMap;
import java.util.Map;

public enum BrokenItem {

    FIRE_CAPE(ItemIdentifiers.FIRE_CAPE, ItemIdentifiers.FIRE_CAPE_BROKEN, 10_500),
    INFERNAL_CAPE(ItemIdentifiers.INFERNAL_CAPE, ItemIdentifiers.INFERNAL_CAPE_BROKEN, 50_000),
    AVAS_ASSEMBLER(ItemIdentifiers.AVAS_ASSEMBLER, ItemIdentifiers.AVAS_ASSEMBLER_BROKEN, 75_000),
    IMBUED_GUTHIX_CAPE(ItemIdentifiers.IMBUED_GUTHIX_CAPE, ItemIdentifiers.IMBUED_GUTHIX_CAPE_BROKEN, 75_000),
    IMBUED_SARADOMIN_CAPE(ItemIdentifiers.IMBUED_SARADOMIN_CAPE, ItemIdentifiers.IMBUED_SARADOMIN_CAPE_BROKEN, 75_000),
    IMBUED_ZAMORAK_CAPE(ItemIdentifiers.IMBUED_ZAMORAK_CAPE, ItemIdentifiers.IMBUED_ZAMORAK_CAPE_BROKEN, 75_000),
    FIRE_MAX_CAPE(ItemIdentifiers.FIRE_MAX_CAPE, ItemIdentifiers.FIRE_MAX_CAPE_BROKEN, 99_000),
    INFERNAL_MAX_CAPE(ItemIdentifiers.INFERNAL_MAX_CAPE, ItemIdentifiers.INFERNAL_MAX_CAPE_BROKEN, 99_000),
    ASSEMBLER_MAX_CAPE(ItemIdentifiers.ASSEMBLER_MAX_CAPE, ItemIdentifiers.ASSEMBLER_MAX_CAPE_BROKEN, 99_000),
    IMBUED_GUTHIX_MAX_CAPE(ItemIdentifiers.IMBUED_GUTHIX_MAX_CAPE, ItemIdentifiers.IMBUED_GUTHIX_MAX_CAPE_BROKEN, 99_000),
    IMBUED_SARADOMIN_MAX_CAPE(ItemIdentifiers.IMBUED_SARADOMIN_MAX_CAPE, ItemIdentifiers.IMBUED_SARADOMIN_MAX_CAPE_BROKEN, 99_000),
    IMBUED_ZAMORAK_MAX_CAPE(ItemIdentifiers.IMBUED_ZAMORAK_MAX_CAPE, ItemIdentifiers.IMBUED_ZAMORAK_MAX_CAPE_BROKEN, 99_000),
    BRONZE_DEFENDER(ItemIdentifiers.BRONZE_DEFENDER, ItemIdentifiers.BRONZE_DEFENDER_BROKEN, 1_000),
    IRON_DEFENDER(ItemIdentifiers.IRON_DEFENDER, ItemIdentifiers.IRON_DEFENDER_BROKEN, 2_000),
    STEEL_DEFENDER(ItemIdentifiers.STEEL_DEFENDER, ItemIdentifiers.STEEL_DEFENDER_BROKEN, 2_500),
    BLACK_DEFENDER(ItemIdentifiers.BLACK_DEFENDER, ItemIdentifiers.BLACK_DEFENDER_BROKEN, 5_000),
    MITHRIL_DEFENDER(ItemIdentifiers.MITHRIL_DEFENDER, ItemIdentifiers.MITHRIL_DEFENDER_BROKEN, 15_000),
    ADAMANT_DEFENDER(ItemIdentifiers.ADAMANT_DEFENDER, ItemIdentifiers.ADAMANT_DEFENDER_BROKEN, 25_000),
    RUNE_DEFENDER(ItemIdentifiers.RUNE_DEFENDER, ItemIdentifiers.RUNE_DEFENDER_BROKEN, 35_000),
    DRAGON_DEFENDER(ItemIdentifiers.DRAGON_DEFENDER, ItemIdentifiers.DRAGON_DEFENDER_BROKEN, 40_000),
    AVERNIC_DEFENDER(ItemIdentifiers.AVERNIC_DEFENDER, ItemIdentifiers.AVERNIC_DEFENDER_BROKEN, 1_000_000),
    VOID_MELEE_HELM(ItemIdentifiers.VOID_MELEE_HELM, ItemIdentifiers.VOID_MELEE_HELM_BROKEN, 40_000),
    VOID_MAGE_HELM(ItemIdentifiers.VOID_MAGE_HELM, ItemIdentifiers.VOID_MAGE_HELM_BROKEN, 40_000),
    VOID_RANGER_HELM(ItemIdentifiers.VOID_RANGER_HELM, ItemIdentifiers.VOID_RANGER_HELM_BROKEN, 40_000),
    VOID_KNIGHT_TOP(ItemIdentifiers.VOID_KNIGHT_TOP, ItemIdentifiers.VOID_KNIGHT_TOP_BROKEN, 45_000),
    ELITE_VOID_TOP(ItemIdentifiers.ELITE_VOID_TOP, ItemIdentifiers.ELITE_VOID_TOP_BROKEN, 50_000),
    VOID_KNIGHT_ROBE(ItemIdentifiers.VOID_KNIGHT_ROBE, ItemIdentifiers.VOID_KNIGHT_ROBE_BROKEN, 45_000),
    ELITE_VOID_ROBE(ItemIdentifiers.ELITE_VOID_ROBE, ItemIdentifiers.ELITE_VOID_ROBE_BROKEN, 50_000),
    VOID_KNIGHT_GLOVES(ItemIdentifiers.VOID_KNIGHT_GLOVES, ItemIdentifiers.VOID_KNIGHT_GLOVES_BROKEN, 30_000),
    FIGHTER_HAT(ItemIdentifiers.FIGHTER_HAT, ItemIdentifiers.FIGHTER_HAT_BROKEN, 45_000),
    FIGHTER_TORSO(ItemIdentifiers.FIGHTER_TORSO, ItemIdentifiers.FIGHTER_TORSO_BROKEN, 50_000),

    ;

    BrokenItem(int originalItem, int brokenItem, int costToRepair) {
        this.originalItem = originalItem;
        this.brokenItem = brokenItem;
        this.costToRepair = costToRepair;
    }

    public final int originalItem;
    public final int brokenItem;
    public final int costToRepair;

    private static final Map<Integer, BrokenItem> brokenItems = new HashMap<>();

    public static BrokenItem get(int originalId) {
        return brokenItems.get(originalId);
    }

    static {
        for (BrokenItem brokenItem : BrokenItem.values()) {
            brokenItems.put(brokenItem.originalItem, brokenItem);
        }
    }
}

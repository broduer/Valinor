package com.valinor.game.content.skill.impl.farming.impl;

import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.world.items.Item;
import com.valinor.util.ItemIdentifiers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The impl for all the seeds that can be planted.
 */
public enum Seeds {

    HERB_GUAM(FarmingPatchType.HERB_PATCH, 9, 12, 5291, 199, 249, 0x04, 0x08, 5, 7, 1),
    HERB_MARRENTILL(FarmingPatchType.HERB_PATCH, 14, 15, 5292, 201, 251, 0x0b, 0x0f, 5, 7, 10),
    HERB_TARROMIN(FarmingPatchType.HERB_PATCH, 19, 18, 5293, 203, 253, 0x12, 0x16, 5, 7, 10),
    HERB_HARRALANDER(FarmingPatchType.HERB_PATCH, 26, 24, 5294, 205, 255, 0x19, 0x1d, 5, 7, 10),
    HERB_GOUT_TUBER(FarmingPatchType.HERB_PATCH, 29, 105, 6311, 3261, 3261, 0xc0, 0xc4, 5, 7, 10),
    HERB_RANARR(FarmingPatchType.HERB_PATCH, 32, 30, 5295, 207, 257, 0x20, 0x24, 5, 7, 10),
    HERB_TOADFLAX(FarmingPatchType.HERB_PATCH, 38, 38, 5296, 3050, 2998, 0x27, 0x2b, 5, 7, 10),
    HERB_IRIT(FarmingPatchType.HERB_PATCH, 44, 48, 5297, 209, 259, 0x2e, 0x32, 5, 7, 10),
    HERB_AVANTOE(FarmingPatchType.HERB_PATCH, 50, 61, 5298, 211, 261, 0x35, 0x39, 5, 7, 10),
    HERB_KWUARM(FarmingPatchType.HERB_PATCH, 56, 78, 5299, 213, 263, 0x44, 0x48, 5, 7, 10),
    HERB_SNAPDRAGON(FarmingPatchType.HERB_PATCH, 62, 99, 5300, 3051, 3000, 0x4b, 0x4f, 5, 7, 10),
    HERB_CADANTINE(FarmingPatchType.HERB_PATCH, 67, 120, 5301, 215, 265, 0x52, 0x56, 5, 7, 10),
    HERB_LANTADYME(FarmingPatchType.HERB_PATCH, 73, 152, 5302, 2485, 2481, 0x59, 0x5d, 5, 7, 10),
    HERB_DWARF_WEED(FarmingPatchType.HERB_PATCH, 79, 192, 5303, 217, 267, 0x60, 0x64, 5, 7, 10),
    HERB_TORSTOL(FarmingPatchType.HERB_PATCH, 85, 225, 5304, 219, 269, 0x67, 0x6b, 5, 7, 10),

    FLOWER_MARIGOLDS(FarmingPatchType.FLOWER_PATCH, 2, 47, 5096, 6010, 6010, 0x08, 0x0c, 1, 1, 5),
    FLOWER_ROSEMARY(FarmingPatchType.FLOWER_PATCH, 11, 67, 5097, 6014, 6014, 0x0d, 0x11, 1, 1, 5),
    FLOWER_NASTURTIUM(FarmingPatchType.FLOWER_PATCH, 24, 111, 5098, 6012, 6012, 0x12, 0x16, 1, 1, 5),
    FLOWER_WOAD(FarmingPatchType.FLOWER_PATCH, 25, 116, 5099, 1793, 1793, 0x17, 0x1b, 1, 1, 5),
    FLOWER_LIMPWURT(FarmingPatchType.FLOWER_PATCH, 26, 120, 5100, 225, 225, 0x1c, 0x20, 5, 7, 5),

    ALLOTMENT_POTATOES(FarmingPatchType.ALLOTMENT, 1, 9, 5318, 1942, 1942, 0x06, 0x0a, 5, 7, 10, new Item(ItemIdentifiers.COMPOST, 2)),
    ALLOTMENT_ONIONS(FarmingPatchType.ALLOTMENT, 5, 11, 5319, 1957, 1957, 0x0d, 0x12, 5, 7, 10, new Item(ItemIdentifiers.POTATO, 10), new Item(ItemIdentifiers.POTATOES10, 1)),
    ALLOTMENT_CABBAGES(FarmingPatchType.ALLOTMENT, 7, 12, 5324, 1965, 1965, 0x14, 0x18, 5, 7, 10, new Item(ItemIdentifiers.ONION, 10), new Item(ItemIdentifiers.ONIONS10, 1)),
    ALLOTMENT_TOMATOES(FarmingPatchType.ALLOTMENT, 12, 14, 5322, 1982, 1982, 0x1b, 0x1f, 5, 7, 10, new Item(ItemIdentifiers.CABBAGE, 20), new Item(ItemIdentifiers.CABBAGES10, 2)),
    ALLOTMENT_SWEETCORN(FarmingPatchType.ALLOTMENT, 20, 19, 5320, 5986, 5986, 0x22, 0x28, 5, 7, 10, new Item(ItemIdentifiers.JUTE_FIBRE, 10)),
    ALLOTMENT_STRAWBERRY(FarmingPatchType.ALLOTMENT, 31, 29, 5323, 5504, 5504, 0x2b, 0x32, 5, 7, 10, new Item(ItemIdentifiers.COOKING_APPLE, 5), new Item(ItemIdentifiers.APPLES5,	1)),
    ALLOTMENT_WATERMELON(FarmingPatchType.ALLOTMENT, 47, 55, 5321, 5982, 5982, 0x34, 0x3e, 5, 7, 10, new Item(ItemIdentifiers.CURRY_LEAF, 10)),

    FRUIT_TREE_APPLE(FarmingPatchType.FRUIT_TREE_PATCH, 27, 22, 5496, 1955, 1955, 0x08, 0x14, 5, 7, 160, new Item(ItemIdentifiers.SWEETCORN, 9)),
    FRUIT_TREE_BANANA(FarmingPatchType.FRUIT_TREE_PATCH, 33, 28, 5497, 1963, 1963, 0x23, 0x2f, 5, 7, 160, new Item(ItemIdentifiers.COOKING_APPLE, 20), new Item(ItemIdentifiers.APPLES5, 4)),
    FRUIT_TREE_ORANGE(FarmingPatchType.FRUIT_TREE_PATCH, 39, 36, 5498, 2108, 2108, 0x48, 0x54, 5, 7, 160, new Item(ItemIdentifiers.STRAWBERRY, 15), new Item(ItemIdentifiers.STRAWBERRIES5, 3)),
    FRUIT_TREE_CURRY(FarmingPatchType.FRUIT_TREE_PATCH, 42, 40, 5499, 5970, 5970, 0x63, 0x6f, 5, 7, 160, new Item(ItemIdentifiers.BANANAS5, 5)),
    FRUIT_TREE_PINEAPPLE(FarmingPatchType.FRUIT_TREE_PATCH, 51, 57, 5500, 2114, 2114, 0x88, 0x94, 5, 7, 160, new Item(ItemIdentifiers.WATERMELON, 10)),
    FRUIT_TREE_PAPAYA(FarmingPatchType.FRUIT_TREE_PATCH, 57, 72, 5501, 5972, 5972, 0xa3, 0xaf, 5, 7, 160, new Item(ItemIdentifiers.PINEAPPLE, 10)),
    FRUIT_TREE_PALM(FarmingPatchType.FRUIT_TREE_PATCH, 68, 171, 5502, 5974, 5974, 0xc8, 0xd4, 5, 7, 160, new Item(ItemIdentifiers.PAPAYA_FRUIT, 15)),

    TREE_OAK(FarmingPatchType.TREE_PATCH, 15, 14, 5370, 6043, 6043, 0x08, 0x0c, 5, 7, 40, new Item(ItemIdentifiers.TOMATO, 5), new Item(ItemIdentifiers.TOMATOES5, 1)),
    TREE_WILLOW(FarmingPatchType.TREE_PATCH, 30, 25, 5371, 6045, 6045, 0x0f, 0x15, 5, 7, 40, new Item(ItemIdentifiers.COOKING_APPLE, 5), new Item(ItemIdentifiers.APPLES5, 1)),
    TREE_MAPLE(FarmingPatchType.TREE_PATCH, 45, 45, 5372, 6047, 6047, 0x18, 0x20, 5, 7, 40, new Item(ItemIdentifiers.ORANGE, 5), new Item(ItemIdentifiers.ORANGES5, 1)),
    TREE_YEW(FarmingPatchType.TREE_PATCH, 60, 81, 5373, 6049, 6049, 0x23, 0x2d, 5, 7, 40, new Item(ItemIdentifiers.CACTUS_SPINE, 10)),
    TREE_MAGIC(FarmingPatchType.TREE_PATCH, 75, 146, 5374, 6051, 6051, 0x30, 0x3c, 5, 7, 40, new Item(ItemIdentifiers.COCONUT, 25), new Item(ItemIdentifiers.COCONUT, 25)),

    HOP_BARLEY(FarmingPatchType.HOP_PATCH, 3, 9, 5305, 6006, 6006, 0x31, 0x35, 5, 7, 10, new Item(ItemIdentifiers.COMPOST, 3)),
    HOP_HAMMERSTONE(FarmingPatchType.HOP_PATCH, 4, 10, 5307, 5994, 5994, 0x04, 0x08, 5, 7, 10, new Item(ItemIdentifiers.MARIGOLDS, 1)),
    HOP_ASGARNIAN(FarmingPatchType.HOP_PATCH, 8, 11, 5308, 5996, 5996, 0x0b, 0x10, 5, 7, 10, new Item(ItemIdentifiers.ONION, 10), new Item(ItemIdentifiers.ONIONS10, 1)),
    HOP_JUTE(FarmingPatchType.HOP_PATCH, 13, 15, 5306, 5931, 5931, 0x38, 0x3d, 5, 7, 10, new Item(ItemIdentifiers.BARLEY_MALT, 6)),
    HOP_YANILLIAN(FarmingPatchType.HOP_PATCH, 16, 15, 5309, 5998, 5998, 0x13, 0x19, 5, 7, 10, new Item(ItemIdentifiers.TOMATO, 5), new Item(ItemIdentifiers.TOMATOES5, 1)),
    HOP_KRANDORIAN(FarmingPatchType.HOP_PATCH, 21, 18, 5310, 6000, 6000, 0x1c, 0x23, 5, 7, 10, new Item(ItemIdentifiers.CABBAGE, 30), new Item(ItemIdentifiers.CABBAGES10, 3)),
    HOP_WILDBLOOD(FarmingPatchType.HOP_PATCH, 28, 23, 5311, 6002, 6002, 0x26, 0x2e, 5, 7, 10, new Item(ItemIdentifiers.NASTURTIUMS, 1)),;

    /**
     * The patch type that can hold this seed..
     */
    private final FarmingPatchType type;

    /**
     * The level required to plant this seed.
     */
    private final int levelReq;

    /**
     * The experience given by harvesting this seed.
     */
    private final int experience;


    /**
     * The seed's item ID.
     */
    private final int seedItemId;

    /**
     * The "dirty" product item ID.
     */
    private final int grimyItemId;

    /**
     * The clean product item ID.
     */
    private final int cleanItemId;

    /**
     * The bit of minimum growth.
     */
    private final int minGrowth;

    /**
     * The bit of max growth.
     */
    private final int maxGrowth;

    /**
     * The minimum amount of products on harvest.
     */
    private final int minYield;

    /**
     * The maximum amount of products on harvest.
     */
    private final int maxYield;

    /**
     * The seed's stage growth time in minutes.
     */
    private final int stageGrowthTime;

    /**
     * The cost for the farmer to protect this seed.
     */
    private final List<Item> protectionCost;

    Seeds(FarmingPatchType type, int levelReq, int experience, int seedItemId, int grimyItemId, int clean, int
        minGrowth, int maxGrowth, int minYield, int maxYield, int durationInMinutes, Item... protectionCost) {
        this.type = checkNotNull(type, "type == null");
        this.levelReq = levelReq;
        this.experience = experience;
        this.seedItemId = seedItemId;
        this.grimyItemId = grimyItemId;
        this.cleanItemId = clean;
        this.minGrowth = minGrowth;
        this.maxGrowth = maxGrowth;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.stageGrowthTime = durationInMinutes;
        this.protectionCost = protectionCost != null
            ? List.of(protectionCost)
            : Collections.emptyList();
    }

    /**
     * Returns the patch's type that can hold this seed.
     *
     * @return the type
     */
    public FarmingPatchType getType() {
        return type;
    }

    /**
     * Returns the farming level required to plant this seed.
     *
     * @return the level
     */
    public int getLevelReq() {
        return levelReq;
    }

    /**
     * Returns the experience rewarded when harveting the products.
     *
     * @return the experience
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Returns the seed's item ID.
     *
     * @return the ID
     */
    public int getSeedItemId() {
        return seedItemId;
    }

    /**
     * Returns the product's item ID.
     *
     * @return the ID
     */
    public int getProduct() {
        return grimyItemId;
    }

    /**
     * Returns the clean product's item ID.
     *
     * @return the ID
     */
    public int getCleanProduct() {
        return cleanItemId;
    }

    /**
     * Returns the bit of minimum growth.
     *
     * @return the bit
     */
    public int getMinGrowth() {
        return minGrowth;
    }

    /**
     * Returns the bit of maximum growth.
     *
     * @return the bit
     */
    public int getMaxGrowth() {
        return maxGrowth;
    }

    /**
     * Returns the minimum amount of products when harvesting.
     *
     * @return the amount
     */
    public int getMinYield() {
        return minYield;
    }

    /**
     * Returns the maximum amount of products when harvesting.
     *
     * @return the amount
     */
    public int getMaxYield() {
        return maxYield;
    }

    /**
     * Returns the time between each growth stage.
     *
     * @return the time in minutes
     */
    public int getStageGrowthTime() {
        return (int) (stageGrowthTime * FarmingConstants.STAGE_GROWTH_MULTIPLIER);
    }


    /**
     * Returns the protection cost for this seed.
     *
     * @return the cost
     */
    public List<Item> getProtectionCost() {
        return protectionCost;
    }

    /**
     * Returns the seed impl by its seed item ID.
     *
     * @param itemId
     * @return the impl
     */
    public static Seeds get(int itemId) {
        return Arrays.stream(values()).filter(p -> p.getSeedItemId() == itemId).findAny().orElse(null);
    }

    /**
     * Returns the seed impl by its product item ID.
     *
     * @param itemId
     * @return the impl
     */
    public static Seeds forProductID(int itemId) {
        return Arrays.stream(values()).filter(p -> p.getProduct() == itemId).findAny().orElse(null);
    }

}

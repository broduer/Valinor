package com.valinor.game.content.skill.impl.farming.impl;

import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.world.position.Tile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The information for all farming patches.
 */
public enum Patches {

    HERB_PATCH_CAMELOT(FarmingPatchType.HERB_PATCH, 8151, 529, 24, new Tile[]{new Tile(2813, 3463)}, 2324),
    HERB_PATCH_ARDOUGNE(FarmingPatchType.HERB_PATCH, 8152, 529, 24, new Tile[]{new Tile(2670, 3374)}, 2325),
    HERB_PATCH_CANIFIS(FarmingPatchType.HERB_PATCH, 8153, 529, 24, new Tile[]{new Tile(3605, 3529)}, 2326),
    HERB_PATCH_FALADOR(FarmingPatchType.HERB_PATCH, 8150, 529, 24, new Tile[]{new Tile(3058, 3311)}, 2323),

    ALLOTMENT_NORTH_CAMELOT(FarmingPatchType.ALLOTMENT, 8552, 529, 0, new Tile[]{new Tile(2805, 3466), new Tile(2806, 3468), new Tile(2805, 3467), new Tile(2814, 3468)}, 2324),
    ALLOTMENT_SOUTH_CAMELOT(FarmingPatchType.ALLOTMENT, 8553, 529, 8, new Tile[]{new Tile(2805, 3459), new Tile(2806, 3461), new Tile(2802, 3459), new Tile(2814, 3460)}, 2324),
    ALLOTMENT_NORTH_ARDOUGNE(FarmingPatchType.ALLOTMENT, 8552, 529, 0, new Tile[]{new Tile(2662, 3377), new Tile(2663, 3379), new Tile(2662, 3378), new Tile(2671, 3379)}, 2325),
    ALLOTMENT_SOUTH_ARDOUGNE(FarmingPatchType.ALLOTMENT, 8553, 529, 8, new Tile[]{new Tile(2662, 3370), new Tile(2663, 3372), new Tile(2662, 3370), new Tile(2671, 3371)}, 2325),
    ALLOTMENT_SOUTH_EAST_CANIFIS(FarmingPatchType.ALLOTMENT, 8552, 529, 8, new Tile[]{new Tile(3602, 3521), new Tile(3606, 3522), new Tile(3605, 3521), new Tile(3606, 3526)}, 2326),
    ALLOTMENT_NORTH_WEST_CANIFIS(FarmingPatchType.ALLOTMENT, 8553, 529, 0, new Tile[]{new Tile(3597, 3525), new Tile(3598, 3530), new Tile(3597, 3529), new Tile(3601, 3530)}, 2326),
    ALLOTMENT_NORTH_WEST_FALADOR(FarmingPatchType.ALLOTMENT, 8552, 529, 0, new Tile[]{new Tile(3050, 3307), new Tile(3051, 3312), new Tile(3050, 3311), new Tile(3054, 3312)}, 2323),
    ALLOTMENT_SOUTH_EAST_FALADOR(FarmingPatchType.ALLOTMENT, 8553, 529, 8, new Tile[]{new Tile(3055, 3303), new Tile(3059, 3304), new Tile(3058, 3303), new Tile(3059, 3308)}, 2323),

    FLOWER_PATCH_CAMELOT(FarmingPatchType.FLOWER_PATCH, 7848, 529, 16, new Tile[]{new Tile(2809, 3463)}, 2324),
    FLOWER_PATCH_ARDOUGNE(FarmingPatchType.FLOWER_PATCH, 7849, 529, 16, new Tile[]{new Tile(2666, 3374)}, 2325),
    FLOWER_PATCH_CANIFIS(FarmingPatchType.FLOWER_PATCH, 7850, 529, 16, new Tile[]{new Tile(3601, 3525)}, 2326),
    FLOWER_PATCH_FALADOR(FarmingPatchType.FLOWER_PATCH, 7847, 529, 16, new Tile[]{new Tile(3054, 3307)}, 2323);

    /**
     * The patch's information.
     */
    private final FarmingPatchType type;

    /**
     * The patch's object ID.
     */
    private final int plotId;

    /**
     * The patch's config ID.
     */
    private final int configId;

    /**
     * The patch's config bit offset.
     */
    private final int configBitOffset;

    /**
     * The farmer that takes care of the patch.
     */
    private final int farmerBelonging;

    /**
     * The position for the patches.
     */
    private final Tile[] allotmentTile;

    /**
     * The list of patches.
     */
    private static Map<String, Patches> allotment_list = new HashMap<>();

    static {
        Arrays.stream(Patches.values()).forEach(data -> allotment_list.put(data.name(), data));
    }

    Patches(final FarmingPatchType type, int plotId, int configId, int configBitOffset, Tile[] allotmentTile, int farmerBelonging) {
        this.type = type;
        this.plotId = plotId;
        this.configId = configId;
        this.configBitOffset = configBitOffset;
        this.allotmentTile = allotmentTile;
        this.farmerBelonging = farmerBelonging;
    }

    /**
     * Returns the patch's information by searching its coordinates.
     *
     * @param x
     * @param y
     * @return the info
     */
    public static Patches get(int x, int y) {
        for (Patches allotmentFieldsData : Patches.values()) {
            Tile pos = allotmentFieldsData.getAllotmentTile()[0];
            if (pos.getX() == x && pos.getY() == y) {
                return allotmentFieldsData;
            }
        }
        for (Patches allotmentFieldsData : Patches.values()) {
            if (allotmentFieldsData.getAllotmentTile().length >= 4) {
                if (FarmingConstants.inRangeArea(allotmentFieldsData.getAllotmentTile()[0], allotmentFieldsData.getAllotmentTile()[1], x, y)
                    || FarmingConstants.inRangeArea(allotmentFieldsData.getAllotmentTile()[2], allotmentFieldsData.getAllotmentTile()[3], x, y)) {
                    return allotmentFieldsData;
                }
            }
        }
        return null;
    }

    /**
     * Returns the allotment's information by searching its ID.
     *
     * @param objectId
     * @return the info
     */
    public static Patches get(int objectId) {
        return Arrays.stream(Patches.values()).filter(data -> data.getPlotId() == objectId).findAny()
            .orElse(null);
    }

    /**
     * Returns the allotment's information by searching its name.
     *
     * @param name
     * @return the info
     */
    public static Patches get(String name) {
        return allotment_list.get(name);
    }

    /**
     * Returns the patch's object ID.
     *
     * @return the ID
     */
    public int getPlotId() {
        return plotId;
    }

    /**
     * Returns the patch's type.
     *
     * @return the type
     */
    public FarmingPatchType getPatchType() {
        return type;
    }

    /**
     * Returns the patch's config ID.
     *
     * @return the ID
     */
    public int getConfigId() {
        return configId;
    }

    /**
     * Returns the patch's config bit offset.
     *
     * @return the bit offset
     */
    public int getPatchBitOffset() {
        return configBitOffset;
    }

    /**
     * Returns the patch's positions on map.
     *
     * @return the positions
     */
    public Tile[] getAllotmentTile() {
        return allotmentTile;
    }

    /**
     * Returns the farmer that takes care of the patches.
     *
     * @return the farmer's NPC ID
     */
    public int getFarmerBelonging() {
        return farmerBelonging;
    }
}

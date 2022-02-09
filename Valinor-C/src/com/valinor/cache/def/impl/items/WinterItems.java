package com.valinor.cache.def.impl.items;

import com.valinor.cache.def.ItemDefinition;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.CASKET;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 09, 2022
 */
public class WinterItems {

    public static void unpack(int id) {
        ItemDefinition def = ItemDefinition.get(id);

        if (id == WINTER_TOKENS) {
            ItemDefinition.copyInventory(def, 13204);
            def.name = "<col=65280>Winter token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{127, 155, 374770};
            def.countco = new int[]{2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
            def.countobj = new int[]{30192, 30193, 30194, 30194, 0, 0, 0, 0, 0, 0};
        }

        if (id == WINTER_CASKET) {
            ItemDefinition.copyInventory(def, CASKET);
            def.name = "<col=65280>Winter casket";
            def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
            def.modelCustomColor4 = 33785;
        }

        if (id == WAMPA_PET) {
            def.name = "<col=65280>Wampa pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 21802;
            def.zoom2d = 4380;
        }

        if (id == ICE_IMP) {
            def.name = "<col=65280>Ice imp";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 58916;
            def.xOffset2d = 11;
            def.yOffset2d = -1;
            def.xan2d = 116;
            def.yan2d = 1778;
            def.zoom2d = 1424;
            def.modelCustomColor4 = 31575;
        }

        if (id == SNOWFLAKE_PET) {
            def.name = "<col=65280>Snowflake pet";
            def.inventoryModel = 35835;
            def.xan2d = 83;
            def.yan2d = 1826;
            def.zoom2d = 4541;
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
        }

        if (id == SNOW_IMP_COSTUME) {
            ItemDefinition.copyInventory(def, 11158);
            def.name = "<col=65280>Snow imp costume";
            def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
        }
    }
}

package com.valinor.cache.def.impl.items;

import com.valinor.cache.def.ItemDefinition;

import static com.valinor.util.CustomItemIdentifiers.KORASI_SWORD;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 25, 2022
 */
public class PreEocItems {

    public static void unpack(int id) {
        ItemDefinition definition = ItemDefinition.get(id);
        if (id == KORASI_SWORD) {
            definition.name = "<col=65280>Korasi's sword";
            definition.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            definition.inventoryModel = 60831;
            definition.maleModel = 57780;
            definition.femaleModel = 57784;
            definition.zoom2d = 1744;
            definition.xan2d = 330;
            definition.yan2d = 1505;
            definition.xOffset2d = 0;
            definition.yOffset2d = 0;
            definition.description = "The sword of a Void Knight.";
            definition.groundActions = new String[]{null, null, "Take", null, null};
        }
    }
}

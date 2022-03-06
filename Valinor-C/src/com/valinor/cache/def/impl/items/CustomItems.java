package com.valinor.cache.def.impl.items;

import com.valinor.ClientConstants;
import com.valinor.cache.def.ItemDefinition;
import com.valinor.cache.def.NpcDefinition;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.NpcIdentifiers;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.CustomItemIdentifiers.DARK_ANCIENT_EFFIGY;
import static com.valinor.util.CustomNpcIdentifiers.BLOOD_FURY_HESPORI;
import static com.valinor.util.ItemIdentifiers.*;

public class CustomItems {

    public static void unpack(int id) {
        ItemDefinition def = ItemDefinition.get(id);

        if(id == CURSED_ORB) {
            def.name = "<col=65280>Cursed orb";
            def.groundActions = new String[]{null, null, "Take", null, null};
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.animateInventory = true;
            def.ambient = -50;
            def.inventoryModel = 55626;
            def.zoom2d = 1614;
            def.yan2d = 225;
            def.xan2d = 114;
        }

        if(id == CURSED_NIGHTMARE_STAFF) {
            def.name = "<col=65280>Cursed nightmare staff";
            def.femaleModel = 55628;
            def.inventoryActions = new String[] {null, "Wear", null, "Dismantle", "Drop"};
            def.groundActions = new String[]{null, null, "Take", null, null};
            def.animateInventory = true;
            def.inventoryModel = 55627;
            def.maleModel = 55629;
            def.xOffset2d = -5;
            def.yOffset2d = 13;
            def.yan2d = 1050;
            def.xan2d = 404;
            def.zoom2d = 2064;
            def.modelCustomColor4 = 100;
        }

        if(id == YELL_TAG_SCROLL) {
            def.name = "<col=65280>Yell tag scroll";
            def.inventoryActions = new String[]{"Read", null, null, null, "Destroy"};
            def.inventoryModel = 3374;
            def.xan2d = 360;
            def.yan2d = 672;
            def.xOffset2d = 1;
            def.yOffset2d = 0;
            def.zoom2d = 1010;
            def.modelCustomColor4 = 175;
        }

        if(id == LITTLE_CHAOTIC_NIGHTMARE) {
            ItemDefinition.copyInventory(def, LITTLE_NIGHTMARE);
            def.name = "<col=65280>Little chaotic nightmare";
            def.modelCustomColor4 = 100;
        }

        if(id == PKP_TICKET) {
            ItemDefinition.copyInventory(def, CASTLE_WARS_TICKET);
            def.name = "<col=65280>PkP ticket";
            def.modelCustomColor4 = 22222;
        }

       if(id == INFERNAL_TRIDENT) {
           def.name = "<col=65280>Infernal trident";
           def.femaleModel = 55623;
           def.femaleOffset = 6;
           def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
           def.inventoryModel = 55624;
           def.maleModel = 55623;
           def.xOffset2d = -7;
           def.yOffset2d = 8;
           def.cost = 68500;
           def.xan2d = 1535;
           def.yan2d = 1778;
           def.zan2d = 108;
           def.zoom2d = 2421;
       }

        if(id == WILDY_ACTIVITY_CASKET) {
            ItemDefinition.copyInventory(def, CASKET);
            def.name = "<col=65280>Wildy activity casket";
            def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
            def.modelCustomColor4 = 124;
        }

        if(id == BIG_CHEST) {
            def.name = "<col=65280>Big chest";
            ItemDefinition.copyInventory(def, 8151);
            def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
            def.modelCustomColor4 = 124;
        }

        if (id == SKILLING_SCROLL) {
            def.name = "<col=65280>Skilling scroll";
            def.inventoryActions = new String[]{"Read", null, null, null, "Destroy"};
            def.inventoryModel = 3374;
            def.xan2d = 360;
            def.yan2d = 672;
            def.xOffset2d = 1;
            def.yOffset2d = 0;
            def.zoom2d = 1010;
            def.modelCustomColor4 = 22222;
        }

        if (id == PVP_SCROLL) {
            def.name = "<col=65280>PvP scroll";
            def.inventoryActions = new String[]{"Read", null, null, null, "Destroy"};
            def.inventoryModel = 3374;
            def.xan2d = 360;
            def.yan2d = 672;
            def.xOffset2d = 1;
            def.yOffset2d = 0;
            def.zoom2d = 1010;
            def.recolorFrom = new int[]{6464, 6608, 22305, 22034, 6740, 22422, 6583, 6587, 6604};
            def.recolorTo = new int[]{933, 926, 926, 926, 933, 926, 926, 926, 933};
        }

        if (id == PVMING_SCROLL) {
            def.name = "<col=65280>Pvming scroll";
            def.recolorFrom = new int[]{6583, 6604, 6608, 6587, 6464, 6740};
            def.recolorTo = new int[]{933, 926, 926, 926, 933, 926};
            def.inventoryActions = new String[]{"Read", null, null, null, "Destroy"};
            def.inventoryModel = 3374;
            def.xan2d = 360;
            def.yan2d = 672;
            def.xOffset2d = 1;
            def.yOffset2d = 0;
            def.zoom2d = 1010;
            def.modelCustomColor4 = 12222;
        }

        if (id == TASK_BOTTLE_SKILLING) {
            def.name = "<col=65280>Task bottle (skilling)";
            def.zoom2d = 1114;
            def.xan2d = 0;
            def.yan2d = 1093;
            def.xOffset2d = 0;
            def.yOffset2d = 0;
            def.groundActions = new String[]{null, null, "Take", null, null};
            def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
            def.inventoryModel = 31044;
            def.modelCustomColor4 = 22222;
        }

        if (id == TASK_BOTTLE_PVMING) {
            def.name = "<col=65280>Task bottle (pvming)";
            def.zoom2d = 1114;
            def.xan2d = 0;
            def.yan2d = 1093;
            def.xOffset2d = 0;
            def.yOffset2d = 0;
            def.groundActions = new String[]{null, null, "Take", null, null};
            def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
            def.inventoryModel = 31044;
            def.modelCustomColor4 = 12222;
        }

        if (id == TASK_BOTTLE_PVP) {
            def.name = "<col=65280>Task bottle (pvp)";
            def.zoom2d = 1114;
            def.xan2d = 0;
            def.yan2d = 1093;
            def.xOffset2d = 0;
            def.yOffset2d = 0;
            def.groundActions = new String[]{null, null, "Take", null, null};
            def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
            def.inventoryModel = 31044;
            def.recolorFrom = new int[]{22422};
            def.recolorTo = new int[]{933};
        }

        if (id == CLOAK_OF_INVISIBILITY) {
            def.name = "<col=65280>Cloak of invisibility";
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.femaleModel = 37051;
            def.inventoryModel = 37197;
            def.maleModel = 37019;
            def.xOffset2d = 3;
            def.yOffset2d = 12;
            def.xan2d = 361;
            def.yan2d = 47;
            def.zoom2d = 2130;
            def.modelCustomColor4 = 490;
        }

        if (id == MARVOLO_GAUNTS_RING) {
            def.name = "<col=65280>Marvolo Gaunts Ring";
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.inventoryModel = 29141;
            def.xOffset2d = -1;
            def.yOffset2d = -1;
            def.xan2d = 550;
            def.yan2d = 1800;
            def.zoom2d = 550;
            def.modelCustomColor3 = 3020;
        }

        if (id == TOM_RIDDLE_DIARY) {
            def.name = "<col=65280>Tom Riddle's Diary";
            def.ambient = 15;
            def.femaleModel = 10699;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 10573;
            def.maleModel = 10698;
            def.xOffset2d = 2;
            def.yOffset2d = 10;
            def.xan2d = 260;
            def.yan2d = 1948;
            def.zoom2d = 950;
            def.modelCustomColor4 = 490;
        }

        if (id == NAGINI) {
            def.name = "<col=65280>Nagini";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 13556;
            def.xOffset2d = 3;
            def.yOffset2d = 6;
            def.xan2d = 429;
            def.yan2d = 1985;
            def.zoom2d = 2128;
        }

        if (id == HWEEN_TOKENS) {
            ItemDefinition.copyInventory(def, 13204);
            def.name = "<col=65280>H'ween token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{25, 26, 27};
            def.countco = new int[]{2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
            def.countobj = new int[]{30236, 30237, 30238, 30238, 0, 0, 0, 0, 0, 0};
        }

        if (id == 30236) {
            ItemDefinition.copyInventory(def, 3985);
            def.name = "<col=65280>H'ween Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{25, 26, 27};
        }

        if (id == 30237) {
            ItemDefinition.copyInventory(def, 3987);
            def.name = "<col=65280>H'ween Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{25, 26, 27};
        }

        if (id == 30238) {
            ItemDefinition.copyInventory(def, 3989);
            def.name = "<col=65280>H'ween Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{25, 26, 27};
        }

        if (id == MYSTERY_TICKET) {
            def.animateInventory = true;
            def.name = "<col=65280>Mystery ticket";
            def.inventoryActions = new String[]{"Tear", null, null, null, null};
            def.recolorFrom = new int[]{7364, 11078, -327, -329, 7496, 7500};
            def.recolorTo = new int[]{374770, 374770, -327, -329, 374770, 374770};
            def.inventoryModel = 55601;
            def.stackable = 1;
            def.xan2d = 308;
            def.yan2d = 1888;
            def.zoom2d = 1160;
        }

        if (id == 23759 || id == 22319 || id == 24491) {
            def.name = "<col=65280>" + def.name + " pet";
        }

        if (id == DARKLORD_BOW) {
            def.name = "<col=65280>Darklord bow";
            def.femaleModel = 59109;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 59108;
            def.maleModel = 59109;
            def.xOffset2d = 9;
            def.yOffset2d = 9;
            def.yan2d = 183;
            def.xan2d = 320;
            def.zoom2d = 2061;
        }

        if (id == DARKLORD_SWORD) {
            def.name = "<col=65280>Darklord sword";
            def.femaleModel = 59113;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 59112;
            def.maleModel = 59113;
            def.xOffset2d = 1;
            def.yOffset2d = 1;
            def.yan2d = 270;
            def.xan2d = 548;
            def.zoom2d = 2061;
        }

        if (id == DARKLORD_STAFF) {
            def.name = "<col=65280>Darklord staff";
            def.femaleModel = 59111;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 59110;
            def.maleModel = 59111;
            def.xOffset2d = 9;
            def.yOffset2d = 9;
            def.yan2d = 183;
            def.xan2d = 1713;
            def.zoom2d = 2061;
        }

        if (id == ANCESTRAL_HAT_I) {
            def.name = "<col=65280>Ancestral hat (i)";
            def.recolorFrom = new int[]{6323, 6331, 6340, 6348, 6356, 6364, -21992, -22235};
            def.recolorTo = new int[]{15, 17, 19, 23, 25, 27, 29, 524};
            def.femaleHeadModel = 34263;
            def.femaleModel = 32663;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.inventoryModel = 32794;
            def.maleHeadModel = 32774;
            def.maleModel = 32655;
            def.yOffset2d = -12;
            def.xan2d = 118;
            def.yan2d = 10;
            def.zoom2d = 1236;
        }

        if (id == ANCESTRAL_ROBE_TOP_I) {
            def.name = "<col=65280>Ancestral robe top (i)";
            def.recolorFrom = new int[]{6348, -16318, 6331, -22225, 7108, -22235, -16327, -22231, -16339, 6323};
            def.recolorTo = new int[]{12, 19, 14, 534, 27, 536, 15, 538, 17, 8};
            def.femaleModel = 32664;
            def.femaleModel1 = 32665;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.inventoryModel = 32790;
            def.maleModel = 32657;
            def.maleModel1 = 32658;
            def.yOffset2d = -3;
            def.xan2d = 514;
            def.yan2d = 2041;
            def.zoom2d = 1358;
        }

        if (id == ANCESTRAL_ROBE_BOTTOM_I) {
            def.name = "<col=65280>Ancestral robe bottom (i)";
            def.ambient = 30;
            def.recolorFrom = new int[]{-16339, 6348, -16327, 6331, -16318, -22225, -22235, 6323, -22231};
            def.recolorTo = new int[]{17, 12, 15, 14, 25, 534, 537, 13, 532};
            def.contrast = 20;
            def.femaleModel = 32662;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.inventoryModel = 32787;
            def.maleModel = 32653;
            def.xOffset2d = -1;
            def.yOffset2d = 7;
            def.xan2d = 435;
            def.yan2d = 9;
            def.zoom2d = 1690;
        }

        if (id == TWISTED_BOW_I) {
            def.name = "<col=65280>Twisted bow";
            def.recolorFrom = new int[]{10318, 10334, 14236, 13223};
            def.recolorTo = new int[]{524, 527, 524, 527};
            def.femaleModel = 32674;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 32799;
            def.maleModel = 32674;
            def.xOffset2d = -3;
            def.yOffset2d = 1;
            def.xan2d = 720;
            def.yan2d = 1500;
        }

        if (id == MAGMA_BLOWPIPE) {
            def.name = "<col=65280>Magma blowpipe";
            def.femaleModel = 58976;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 58975;
            def.maleModel = 58976;
            def.xOffset2d = -7;
            def.yOffset2d = 4;
            def.xan2d = 768;
            def.yan2d = 189;
            def.zoom2d = 1158;
        }

        if (id == RING_OF_MANHUNTING) {
            def.name = "<col=65280>Ring of manhunting";
            ItemDefinition.copyInventory(def, TYRANNICAL_RING);
            ItemDefinition.copyInventory(def, TYRANNICAL_RING);
            def.modelCustomColor4 = 233333;
        }

        if (id == KERBEROS_PET) {
            ItemDefinition.copyInventory(def, HELLPUPPY);
            def.name = "<col=65280>Kerberos pet";
            def.modelCustomColor4 = 125;
        }

        if (id == SKORPIOS_PET) {
            ItemDefinition.copyInventory(def, SCORPIAS_OFFSPRING);
            def.name = "<col=65280>Skorpios pet";
            def.modelCustomColor4 = 125;
        }

        if (id == ARACHNE_PET) {
            ItemDefinition.copyInventory(def, VENENATIS_SPIDERLING);
            def.name = "<col=65280>Arachne pet";
            def.modelCustomColor4 = 125;
        }

        if (id == ARTIO_PET) {
            ItemDefinition.copyInventory(def, CALLISTO_CUB);
            def.name = "<col=65280>Artio pet";
            def.modelCustomColor4 = 125;
        }

        if (id == RING_OF_ELYSIAN) {
            def.name = "<col=65280>Ring of elysian";
            ItemDefinition.copyInventory(def, 23185);
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[]{7378, 8289, 8282, 7244};
            def.recolorTo = new int[]{-29116, -29019, -29125, -29110};
        }

        if (id == TOXIC_STAFF_OF_THE_DEAD_C) {
            def.name = "<col=65280>Toxic staff of the dead (c)";
            ItemDefinition.copyInventory(def, TOXIC_STAFF_OF_THE_DEAD);
            ItemDefinition.copyEquipment(def, TOXIC_STAFF_OF_THE_DEAD);
            def.modelCustomColor4 = 222200;
        }

        if (id == RING_OF_VIGOUR) {
            def.name = "<col=65280>Ring of vigour";
            ItemDefinition.copyInventory(def, LUNAR_RING);
            ItemDefinition.copyEquipment(def, LUNAR_RING);
            def.modelCustomColor4 = 222200;
        }

        if (id == DARK_BANDOS_CHESTPLATE) {
            def.name = "<col=65280>Dark bandos chestplate";
            def.inventoryModel = 28042;
            def.zoom2d = 984;
            def.xan2d = 501;
            def.yan2d = 6;
            def.xOffset2d = 1;
            def.yOffset2d = 4;
            def.stackable = 0;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.maleModel = 27636;
            def.maleModel1 = 28826;
            def.femaleModel = 27644;
            def.femaleModel1 = 28827;
            def.recolorFrom = new int[]{8384, 163, 10275, 10502, 4550, 9515, 8076, 142, 8367, 9523, 22, 8375, 10266, 9403, 8379};
            def.recolorTo = new int[]{16, 272, 0, 274, 20, 4, 8, 142, 968, 272, 0, 274, 20, 968, 968};
        }

        if (id == DARK_BANDOS_TASSETS) {
            def.name = "<col=65280>Dark bandos tassets";
            def.inventoryModel = 28047;
            def.zoom2d = 854;
            def.xan2d = 540;
            def.yan2d = 2039;
            def.xOffset2d = 3;
            def.yOffset2d = 3;
            def.stackable = 0;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.maleModel = 27625;
            def.femaleModel = 27640;
            def.recolorFrom = new int[]{163, 9523, 4550, 22, 8390, 39, 154};
            def.recolorTo = new int[]{16, 968, 0, 274, 20, 4, 8};
        }

        if (id == DARK_ARMADYL_HELMET) {
            def.name = "<col=65280>Dark armadyl helmet";
            def.inventoryModel = 28043;
            def.zoom2d = 789;
            def.xan2d = 66;
            def.yan2d = 372;
            def.xOffset2d = 9;
            def.yOffset2d = 0;
            def.stackable = 0;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.maleModel = 27623;
            def.femaleModel = 27639;
            def.maleHeadModel = 27699;
            def.femaleHeadModel = 27700;
            def.recolorFrom = new int[]{0, -22452, 4550, -22456, -22506, 8650, -22460, -22448};
            def.recolorTo = new int[]{16, 968, 0, 274, 20, 4, 8, 20};
        }

        if (id == DARK_ARMADYL_CHESTPLATE) {
            def.name = "<col=65280>Dark armadyl chestplate";
            def.inventoryModel = 28039;
            def.zoom2d = 854;
            def.xan2d = 453;
            def.yan2d = 0;
            def.xOffset2d = 1;
            def.yOffset2d = -5;
            def.stackable = 0;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.maleModel = 27633;
            def.maleModel1 = 27629;
            def.femaleModel = 27645;
            def.femaleModel1 = 28828;
            def.recolorFrom = new int[]{8658, 4550, -22440, -22489, 8650, -22448};
            def.recolorTo = new int[]{16, 968, 0, 274, 968, 968};
        }

        if (id == DARK_ARMADYL_CHAINSKIRT) {
            def.name = "<col=65280>Dark armadyl chainskirt";
            def.inventoryModel = 28046;
            def.zoom2d = 1957;
            def.xan2d = 555;
            def.yan2d = 2036;
            def.yOffset2d = -3;
            def.stackable = 0;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.maleModel = 27627;
            def.femaleModel = 27641;
            def.recolorFrom = new int[]{-22485, -22440, -22456, -22489, -22473, 8650, -22448, -22464};
            def.recolorTo = new int[]{16, 968, 0, 274, 20, 4, 8, 20};
        }

        if(id == FAWKES) {
            def.name = "<col=65280>Fawkes pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.ambient = 40;
            def.inventoryModel = 26852;
            def.xOffset2d = -8;
            def.yOffset2d = -13;
            def.xan2d = 141;
            def.yan2d = 1790;
            def.zoom2d = 2768;
        }

        if (id == FAWKES_32937) {
            def.name = "<col=65280>Fawkes pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.modelCustomColor4 = 222200;
            def.ambient = 40;
            def.inventoryModel = 26852;
            def.xOffset2d = -8;
            def.yOffset2d = -13;
            def.xan2d = 141;
            def.yan2d = 1790;
            def.zoom2d = 2768;
        }

        if (id == CYAN_PARTYHAT) {
            def.name = "<col=65280>Cyan partyhat";
            def.inventoryModel = 2635;
            def.maleModel = 187;
            def.femaleModel = 363;
            def.zoom2d = 440;
            def.yan2d = 1852;
            def.xan2d = 76;
            def.xOffset2d = 1;
            def.yOffset2d = 1;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[1];
            def.recolorFrom[0] = 926;
            def.recolorTo = new int[1];
            def.recolorTo[0] = 34770;
        }

        if (id == LIME_PARTYHAT) {
            def.name = "<col=65280>Lime partyhat";
            def.inventoryModel = 2635;
            def.maleModel = 187;
            def.femaleModel = 363;
            def.zoom2d = 440;
            def.yan2d = 1852;
            def.xan2d = 76;
            def.xOffset2d = 1;
            def.yOffset2d = 1;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[1];
            def.recolorFrom[0] = 926;
            def.recolorTo = new int[1];
            def.recolorTo[0] = 17350;
        }

        if (id == ORANGE_PARTYHAT) {
            def.name = "<col=65280>Orange partyhat";
            def.inventoryModel = 2635;
            def.maleModel = 187;
            def.femaleModel = 363;
            def.zoom2d = 440;
            def.yan2d = 1852;
            def.xan2d = 76;
            def.xOffset2d = 1;
            def.yOffset2d = 1;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[1];
            def.recolorFrom[0] = 926;
            def.recolorTo = new int[1];
            def.recolorTo[0] = 6073;
        }

        if (id == WHITE_HWEEN_MASK) {
            def.name = "<col=65280>White h'ween mask";
            def.inventoryModel = 2438;
            def.maleModel = 3188;
            def.femaleModel = 3192;
            def.zoom2d = 730;
            def.xan2d = 516;
            def.yan2d = 0;
            def.yOffset2d = -10;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[2];
            def.recolorFrom[0] = 926;
            def.recolorFrom[1] = 0;
            def.recolorTo = new int[2];
            def.recolorTo[0] = 124; // Background colour
            def.recolorTo[1] = 933; // Eyes colour
        }

        if (id == PURPLE_HWEEN_MASK) {
            def.name = "<col=65280>Purple h'ween mask";
            def.inventoryModel = 2438;
            def.maleModel = 3188;
            def.femaleModel = 3192;
            def.zoom2d = 730;
            def.xan2d = 516;
            def.yan2d = 0;
            def.yOffset2d = -10;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[2];
            def.recolorFrom[0] = 926;
            def.recolorFrom[1] = 0;
            def.recolorTo = new int[2];
            def.recolorTo[0] = 51136; // Background colour
            def.recolorTo[1] = 0; // Eyes colour
        }

        if (id == LIME_GREEN_HWEEN_MASK) {
            def.name = "<col=65280>Lime green h'ween mask";
            def.inventoryModel = 2438;
            def.maleModel = 3188;
            def.femaleModel = 3192;
            def.zoom2d = 730;
            def.xan2d = 516;
            def.yan2d = 0;
            def.yOffset2d = -10;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[2];
            def.recolorFrom[0] = 926;
            def.recolorFrom[1] = 0;
            def.recolorTo = new int[2];
            def.recolorTo[0] = 17350; // Background colour
            def.recolorTo[1] = 0; // Eyes colour
        }

        if (id == DARK_ELDER_MAUL) {
            ItemDefinition.copyInventory(def, ELDER_MAUL);
            ItemDefinition.copyEquipment(def, ELDER_MAUL);
            def.name = "<col=65280>Dark elder maul";
            def.modelCustomColor4 = 222200;
        }

        if (id == SANGUINE_TWISTED_BOW) {
            ItemDefinition.copyInventory(def, TWISTED_BOW);
            ItemDefinition.copyEquipment(def, TWISTED_BOW);
            def.inventoryActions = new String[]{null, "Wield", null, null, null};
            def.name = "<col=65280>Sanguine twisted bow";
            def.modelCustomColor4 = 235;
            def.stackable = 0;
        }

        if (id == SANGUINE_SCYTHE_OF_VITUR) {
            def.inventoryActions = new String[]{null, "Wield", null, null, null};
        }

        if (id == HOLY_SANGUINESTI_STAFF) {
            def.inventoryActions = new String[]{null, "Wield", null, null, "Dismantle"};
        }

        if (id == ANCIENT_WARRIOR_AXE_C) {
            ItemDefinition.copyInventory(def, 7807);
            ItemDefinition.copyEquipment(def, 7807);
            def.inventoryActions = new String[]{null, "Wield", null, null, "Dismantle"};
            def.name = "<col=65280>Ancient warrior axe (c)";
            def.modelCustomColor3 = 23532;
        }

        if (id == ANCIENT_WARRIOR_MAUL_C) {
            ItemDefinition.copyInventory(def, 7808);
            ItemDefinition.copyEquipment(def, 7808);
            def.inventoryActions = new String[]{null, "Wield", null, null, "Dismantle"};
            def.name = "<col=65280>Ancient warrior maul (c)";
            def.modelCustomColor3 = 23532;
        }

        if (id == ANCIENT_WARRIOR_SWORD_C) {
            ItemDefinition.copyInventory(def, 7806);
            ItemDefinition.copyEquipment(def, 7806);
            def.inventoryActions = new String[]{null, "Wield", null, null, "Dismantle"};
            def.name = "<col=65280>Ancient warrior sword (c)";
            def.modelCustomColor3 = 23532;
        }

        if (id == ANCIENT_FACEGAURD) {
            ItemDefinition.copyInventory(def, NEITIZNOT_FACEGUARD);
            ItemDefinition.copyEquipment(def, NEITIZNOT_FACEGUARD);
            def.name = "<col=65280>Ancient faceguard";
            def.modelCustomColor4 = 222200;
        }

        if (id == ANCIENT_WARRIOR_CLAMP) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Ancient warrior clamp";
            def.inventoryModel = 55580;
            def.zoom2d = 1280;
            def.xOffset2d = -22;
            def.yOffset2d = -7;
            def.yan2d = 0;
            def.zan2d = 1818;
            def.xan2d = 0;
            def.stackable = 0;
            def.animateInventory = true;
        }

        if (id == GAMBLER_SCROLL) {
            def.name = "<col=65280>Gambler scroll";
            def.inventoryActions = new String[]{"Redeem", null, null, null, "Drop"};
        }

        if (id == ANCIENT_KING_BLACK_DRAGON_PET) {
            ItemDefinition.copyInventory(def, PRINCE_BLACK_DRAGON);
            def.name = "<col=65280>Ancient king black dragon pet";
            def.modelCustomColor4 = 235;
        }

        if (id == ANCIENT_CHAOS_ELEMENTAL_PET) {
            ItemDefinition.copyInventory(def, CHAOS_ELEMENTAL);
            def.name = "<col=65280>Ancient chaos elemental pet";
            def.modelCustomColor4 = 235;
        }

        if (id == ANCIENT_BARRELCHEST_PET) {
            ItemDefinition.copyInventory(def, BARRELCHEST_PET);
            def.name = "<col=65280>Ancient barrelchest pet";
            def.modelCustomColor4 = 235;
        }

        if (id == DARK_ANCIENT_EMBLEM) {
            ItemDefinition.copyInventory(def, ANCIENT_EMBLEM);
            def.name = "<col=65280>Dark ancient emblem";
            def.modelCustomColor4 = 235;
        }
        if (id == DARK_ANCIENT_TOTEM) {
            ItemDefinition.copyInventory(def, ANCIENT_TOTEM);
            def.name = "<col=65280>Dark ancient totem";
            def.modelCustomColor4 = 235;
        }
        if (id == DARK_ANCIENT_STATUETTE) {
            ItemDefinition.copyInventory(def, ANCIENT_STATUETTE);
            def.name = "<col=65280>Dark ancient statuette";
            def.modelCustomColor4 = 235;
        }
        if (id == DARK_ANCIENT_MEDALLION) {
            ItemDefinition.copyInventory(def, ANCIENT_MEDALLION);
            def.name = "<col=65280>Dark ancient medallion";
            def.modelCustomColor4 = 235;
        }
        if (id == DARK_ANCIENT_EFFIGY) {
            ItemDefinition.copyInventory(def, ANCIENT_EFFIGY);
            def.name = "<col=65280>Dark ancient effigy";
            def.modelCustomColor4 = 235;
        }
        if (id == DARK_ANCIENT_RELIC) {
            ItemDefinition.copyInventory(def, ANCIENT_RELIC);
            def.name = "<col=65280>Dark ancient relic";
            def.modelCustomColor4 = 235;
        }
        if (id == ANCIENT_VESTAS_LONGSWORD) {
            ItemDefinition.copyInventory(def, VESTAS_LONGSWORD);
            ItemDefinition.copyEquipment(def, VESTAS_LONGSWORD);
            def.name = "<col=65280>Ancient vesta's longsword";
            def.modelCustomColor4 = 235;
        }
        if (id == ANCIENT_STATIUSS_WARHAMMER) {
            ItemDefinition.copyInventory(def, STATIUSS_WARHAMMER);
            ItemDefinition.copyEquipment(def, STATIUSS_WARHAMMER);
            def.name = "<col=65280>Ancient statius's warhammer";
            def.modelCustomColor4 = 235;
        }

        if (id == TASK_BOTTLE_CASKET) {
            def.name = "<col=65280>Task bottle casket";
            def.inventoryModel = 55568;
            def.zoom2d = 2640;
            def.xan2d = 114;
            def.yan2d = 1883;
            def.animateInventory = true;
            def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
            def.recolorFrom = new int[]{24, 49, 4510, 4502, 8128, 7093};
            def.recolorTo = new int[]{933, 933, 933, 933, 933, 933};
        }

        if (id == BLOOD_FIREBIRD) {
            def.name = "<col=65280>Blood firebird pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 26853;
            def.zoom2d = 2340;
            def.yan2d = 210;
            def.xan2d = 10;
            def.xOffset2d = -2;
            def.yOffset2d = 9;
            def.recolorFrom = new int[]{10176, 2880, 6082, 6084, 23492, 2983, 4391, 8, 29867, 4011, 4013, 2733, 2735, 4399, 914, 20, 8150, 10167, 1946, 23483, 28, 5053};
            def.recolorTo = new int[]{933, 933, 933, 933, 933, 933, 4391, 8, 29867, 4011, 4013, 2733, 2735, 4399, 914, 20, 8150, 10167, 1946, 23483, 28, 5053};
        }

        if (id == SHADOW_MACE) {
            def.ambient = 15;
            def.contrast = 35;
            def.femaleModel = 55555;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 55557;
            def.maleModel = 55555;
            def.xOffset2d = 1;
            def.yOffset2d = 16;
            def.cost = 5000000;
            def.xan2d = 348;
            def.zoom2d = 1642;
            def.name = "<col=65280>Shadow mace";
            def.modelCustomColor4 = 235;
        }

        if (id == SHADOW_GREAT_HELM) {
            ItemDefinition.copyInventory(def, INQUISITORS_GREAT_HELM);
            ItemDefinition.copyEquipment(def, INQUISITORS_GREAT_HELM);
            def.name = "<col=65280>Shadow great helm";
            def.modelCustomColor4 = 235;
        }

        if (id == SHADOW_HAUBERK) {
            ItemDefinition.copyInventory(def, INQUISITORS_HAUBERK);
            ItemDefinition.copyEquipment(def, INQUISITORS_HAUBERK);
            def.name = "<col=65280>Shadow hauberk";
            def.modelCustomColor4 = 235;
        }

        if (id == SHADOW_PLATESKIRT) {
            ItemDefinition.copyInventory(def, INQUISITORS_PLATESKIRT);
            ItemDefinition.copyEquipment(def, INQUISITORS_PLATESKIRT);
            def.name = "<col=65280>Shadow plateskirt";
            def.modelCustomColor4 = 235;
        }

        if (id == ETHEREAL_PARTYHAT) {
            def.name = "<col=65280>Ethereal partyhat";
            def.inventoryModel = 2635;
            def.maleModel = 187;
            def.femaleModel = 363;
            def.zoom2d = 440;
            def.yan2d = 1852;
            def.xan2d = 76;
            def.xOffset2d = 1;
            def.yOffset2d = 1;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[1];
            def.recolorFrom[0] = 926;
            def.recolorTo = new int[1];
            def.recolorTo[0] = 374770;
        }

        if (id == ETHEREAL_HWEEN_MASK) {
            def.name = "<col=65280>Ethereal h'ween mask";
            def.inventoryModel = 2438;
            def.maleModel = 3188;
            def.femaleModel = 3192;
            def.zoom2d = 730;
            def.xan2d = 516;
            def.yan2d = 0;
            def.yOffset2d = -10;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[2];
            def.recolorFrom[0] = 926;
            def.recolorFrom[1] = 0;
            def.recolorTo = new int[2];
            def.recolorTo[0] = 374770; // Background colour
            def.recolorTo[1] = 933; // Eyes colour
        }

        if (id == ETHEREAL_SANTA_HAT) {
            def.name = "<col=65280>Ethereal santa hat";
            def.inventoryModel = 2537;
            def.zoom2d = 540;
            def.xan2d = 72;
            def.yan2d = 136;
            def.xOffset2d = 0;
            def.yOffset2d = -3;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.maleModel = 189;
            def.femaleModel = 366;
            def.maleHeadModel = 69;
            def.femaleHeadModel = 127;
            def.recolorFrom = new int[]{933, 10351};
            def.recolorTo = new int[]{374770, 10351};
        }

        if (id == BEGINNER_WEAPON_PACK) {
            def.name = "<col=65280>Beginner weapon pack";
            def.inventoryModel = 20587;
            def.xOffset2d = 0;
            def.yOffset2d = 12;
            def.yan2d = 456;
            def.xan2d = 318;
            def.zoom2d = 2216;
            def.inventoryActions = new String[]{"Open", null, null, null, "Destroy"};
        }

        if (id == BEGINNER_DRAGON_CLAWS) {
            def.name = "<col=65280>Beginner dragon claws";
            def.femaleModel = 29191;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
            def.inventoryModel = 32784;
            def.maleModel = 29191;
            def.xOffset2d = -1;
            def.yOffset2d = 8;
            def.xan2d = 349;
            def.yan2d = 15;
            def.zoom2d = 886;
            def.recolorFrom = new int[]{929, 914, 918, 922};
            def.recolorTo = new int[]{34770, 34770, 34770, 34770};
        }

        if (id == BEGINNER_AGS) {
            def.name = "<col=65280>Beginner AGS";
            def.femaleModel = 27649;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
            def.inventoryModel = 28075;
            def.maleModel = 27649;
            def.xOffset2d = -1;
            def.yOffset2d = -1;
            def.xan2d = 498;
            def.yan2d = 484;
            def.zoom2d = 1957;
            def.recolorFrom = new int[]{-22419, -24279, -22423, -22444, -22477, -24271, -22415, -22208, -22464};
            def.recolorTo = new int[]{34770, 34770, 34770, 34770, 34770, 34770, 34770, 34770, 34770};
        }

        if (id == BEGINNER_CHAINMACE) {
            def.name = "<col=65280>Beginner chainmace";
            def.femaleModel = 35771;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
            def.inventoryModel = 35780;
            def.maleModel = 35771;
            def.xOffset2d = 11;
            def.yOffset2d = -8;
            def.xan2d = 1495;
            def.yan2d = 256;
            def.zoom2d = 1488;
            def.recolorFrom = new int[]{16, -11234, -11238, -11242, -11246, -10719};
            def.recolorTo = new int[]{34770, 34770, 34770, 34770, 34770, 34770};
        }

        if (id == BEGINNER_CRAWS_BOW) {
            def.name = "<col=65280>Beginner craw's bow";
            def.femaleModel = 35769;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
            def.inventoryModel = 35775;
            def.maleModel = 35769;
            def.xOffset2d = -1;
            def.yOffset2d = -3;
            def.xan2d = 1463;
            def.yan2d = 510;
            def.zan2d = 835;
            def.zoom2d = 1979;
            def.recolorFrom = new int[]{-22242, -6087, -22440, 6602, 6699, -22448, 6736, -22225, 3346, -6099, 7124, 6709, -22431};
            def.recolorTo = new int[]{34770, 34770, 34770, 34770, 34770, 34770, 34770, 34770, 34770, 34770, 34770, 34770, 34770};
        }

        if (id == VETERAN_PARTYHAT) {
            def.name = "<col=65280>Veteran partyhat";
            def.inventoryModel = 2635;
            def.maleModel = 187;
            def.femaleModel = 363;
            def.zoom2d = 440;
            def.yan2d = 1852;
            def.xan2d = 76;
            def.xOffset2d = 1;
            def.yOffset2d = 1;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[1];
            def.recolorFrom[0] = 926;
            def.recolorTo = new int[1];
            def.recolorTo[0] = 614770;
        }

        if (id == VETERAN_HWEEN_MASK) {
            def.name = "<col=65280>Veteran h'ween mask";
            def.inventoryModel = 2438;
            def.maleModel = 3188;
            def.femaleModel = 3192;
            def.zoom2d = 730;
            def.xan2d = 516;
            def.yan2d = 0;
            def.yOffset2d = -10;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[2];
            def.recolorFrom[0] = 926;
            def.recolorFrom[1] = 0;
            def.recolorTo = new int[2];
            def.recolorTo[0] = 614770; // Background colour
            def.recolorTo[1] = 933; // Eyes colour
        }

        if (id == VETERAN_SANTA_HAT) {
            def.name = "<col=65280>Veteran santa hat";
            def.inventoryModel = 2537;
            def.zoom2d = 540;
            def.xan2d = 72;
            def.yan2d = 136;
            def.xOffset2d = 0;
            def.yOffset2d = -3;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.maleModel = 189;
            def.femaleModel = 366;
            def.maleHeadModel = 69;
            def.femaleHeadModel = 127;
            def.recolorFrom = new int[]{933, 10351};
            def.recolorTo = new int[]{614770, 10351};
        }

        if (id == PEGASIAN_BOOTS_OR) {
            ItemDefinition.copyInventory(def, PEGASIAN_BOOTS);
            ItemDefinition.copyEquipment(def, PEGASIAN_BOOTS);
            def.name = "<col=65280>Pegasian boots (or)";
            def.recolorFrom = new int[]{8481, 17746, 15252, 16549, 8493, 17294};
            def.recolorTo = new int[]{7114, 7114, 15252, 7114, 7114, 17294};
        }

        if (id == ETERNAL_BOOTS_OR) {
            ItemDefinition.copyInventory(def, ETERNAL_BOOTS);
            ItemDefinition.copyEquipment(def, ETERNAL_BOOTS);
            def.name = "<col=65280>Eternal boots (or)";
            def.recolorFrom = new int[]{9152, -22242, -22326, -10839, -22248, 695, -22361, -22510};
            def.recolorTo = new int[]{9152, -22242, 7114, 7114, 7114, 695, 7114, -22510};
        }

        if (id == VIGGORAS_CHAINMACE_C) {
            ItemDefinition.copyInventory(def, VIGGORAS_CHAINMACE);
            ItemDefinition.copyEquipment(def, VIGGORAS_CHAINMACE);
            def.name = "<col=65280>Viggora's chainmace (c)";
            def.recolorFrom = new int[]{16, -11234, -11238, -11242, -11246, -10719};
            def.recolorTo = new int[]{16, 1255, 1255, 5, 5, 1255};
        }

        if (id == CRAWS_BOW_C) {
            ItemDefinition.copyInventory(def, CRAWS_BOW);
            ItemDefinition.copyEquipment(def, CRAWS_BOW);
            def.name = "<col=65280>Craw's bow (c)";
            def.recolorFrom = new int[]{-22242, -6087, -22440, 6602, 6699, -22448, 6736, -22225, 3346, -6099, 7124, 6709, -22431};
            def.recolorTo = new int[]{7, 1, 7, 1255, 10, 1255, 1255, 10, 5, 1255, 1255, 1255, 10};
        }

        if (id == THAMMARONS_STAFF_C) {
            ItemDefinition.copyInventory(def, THAMMARONS_SCEPTRE);
            ItemDefinition.copyEquipment(def, THAMMARONS_SCEPTRE);
            def.name = "<col=65280>Thammaron's sceptre (c)";
            def.recolorFrom = new int[]{960, 33, 417, 20, 53, 555, 939, 12, 28, 284};
            def.recolorTo = new int[]{10, 15, 1, 15, 1400, 5, 1, 10, 1400, 1400};
        }

        if (id == ItemIdentifiers.TOXIC_BLOWPIPE || id == ItemIdentifiers.SERPENTINE_HELM || id == ItemIdentifiers.TRIDENT_OF_THE_SWAMP || id == ItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD
            || id == ItemIdentifiers.TOME_OF_FIRE || id == ItemIdentifiers.SCYTHE_OF_VITUR || id == ItemIdentifiers.SANGUINESTI_STAFF || id == ItemIdentifiers.CRAWS_BOW
            || id == ItemIdentifiers.VIGGORAS_CHAINMACE || id == ItemIdentifiers.THAMMARONS_SCEPTRE || id == ItemIdentifiers.TRIDENT_OF_THE_SEAS || id == ItemIdentifiers.MAGMA_HELM
            || id == ItemIdentifiers.TANZANITE_HELM || id == ItemIdentifiers.DRAGONFIRE_SHIELD || id == ItemIdentifiers.DRAGONFIRE_WARD || id == ItemIdentifiers.ANCIENT_WYVERN_SHIELD
            || id == ItemIdentifiers.ABYSSAL_TENTACLE || id == ItemIdentifiers.BARRELCHEST_ANCHOR || id == ItemIdentifiers.SARADOMINS_BLESSED_SWORD) {
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
        }

        if (id == RING_OF_SORCERY) {
            ItemDefinition.copyInventory(def, 24731);
            ItemDefinition.copyEquipment(def, 24731);
            def.name = "<col=65280>Ring of sorcery";
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.modelCustomColor4 = 235;
        }

        if (id == RING_OF_PRECISION) {
            def.name = "<col=65280>Ring of Precision";
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            ItemDefinition.copyInventory(def, 12601);
            ItemDefinition.copyEquipment(def, 12601);
            def.modelCustomColor4 = 33785;
        }

        if (id == RING_OF_TRINITY) {
            def.name = "<col=65280>Ring of Trinity";
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            ItemDefinition.copyInventory(def, 12603);
            ItemDefinition.copyEquipment(def, 12603);
            def.modelCustomColor4 = 3433;
        }

        if (id == BABY_ARAGOG) {
            def.name = "<col=65280>Baby Aragog";
            ItemDefinition.copyInventory(def, VENENATIS_SPIDERLING);
            def.recolorFrom = new int[]{912, 0, 916, 103, 138, 794, 107, 908};
            def.recolorTo = new int[]{138, 908, 4769, 4769, 4769, 0, 0, 0};
        }

        if (id == JAWA_PET) {
            def.name = "<col=65280>Jawa pet";
            ItemDefinition.copyInventory(def, ATTACK_HOOD);
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.modelCustomColor = 4769;
        }

        if (id == DHAROK_PET) {
            def.name = "<col=65280>Dharok pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.zoom2d = 1000;
            def.xan2d = 100;
            def.yan2d = 100;
            def.xOffset2d = 0;
            def.yOffset2d = 100;
            NpcDefinition npcInstance = NpcDefinition.get(NpcIdentifiers.DHAROK_THE_WRETCHED);
            def.inventoryModel = npcInstance.modelId[0];
        }

        if(id == TREASURE_CASKET) {
            ItemDefinition.copyInventory(def, REWARD_CASKET_MASTER);
            def.name = "<col=65280>Treasure casket";
            def.stackable = 1;
        }

        if(id == VOID_SET) {
            ItemDefinition.copyInventory(def, VERACS_ARMOUR_SET);
            def.name = "<col=65280>Regular void set";
            def.modelCustomColor4 = 155;
        }

        if (id == ItemIdentifiers.ATTACKER_ICON || id == ItemIdentifiers.COLLECTOR_ICON || id == ItemIdentifiers.DEFENDER_ICON || id == ItemIdentifiers.HEALER_ICON) {
            def.inventoryActions = new String[]{null, "Wear", null, null, "Destroy"};
        }

        if (id == ItemIdentifiers.BANK_KEY) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
        }

        if (id == IMBUEMENT_SCROLL) {
            def.name = "<col=65280>Imbuement scroll";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.description = "Use this scroll to imbue certain items.";
        }

        if (id == NIFFLER) {
            ItemDefinition.copyInventory(def, BABY_MOLE);
            def.modelCustomColor4 = 33785;
            def.name = "<col=65280>Niffler pet";
        }

        if (id == ZRIAWK) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Zriawk pet";
            def.modelCustomColor4 = 33235;
            def.ambient = 40;
            def.inventoryModel = 26852;
            def.xOffset2d = -8;
            def.yOffset2d = -13;
            def.xan2d = 141;
            def.yan2d = 1790;
            def.zoom2d = 2768;
        }

        if (id == ELDER_WAND_HANDLE) {
            def.name = "<col=65280>Elder wand handle";
            def.recolorFrom = new int[]{-19153, 33, -19145, -19500};
            def.recolorTo = new int[]{530, 540, 529, 10};
            def.inventoryActions = new String[]{"Inspect", null, null, null, "Drop"};
            def.inventoryModel = 32805;
            def.xOffset2d = -1;
            def.yOffset2d = -1;
            def.xan2d = 606;
            def.yan2d = 498;
            def.zoom2d = 716;
        }

        if (id == ELDER_WAND_STICK) {
            def.name = "<col=65280>Elder wand stick";
            def.recolorFrom = new int[]{9024, 9009, 5652, 8070, 9015, 7050, 4634, -22413, 8877, 3614};
            def.recolorTo = new int[]{530, 540, 529, 10, 13, 16, 19, 22, 25, 28};
            def.inventoryActions = new String[]{"Inspect", null, null, null, "Drop"};
            def.inventoryModel = 10565;
            def.xOffset2d = 6;
            def.yOffset2d = -7;
            def.xan2d = 68;
            def.yan2d = 1092;
            def.zoom2d = 540;
        }

        if (id == TALONHAWK_CROSSBOW) {
            def.name = "<col=65280>Talonhawk crossbow";
            def.recolorFrom = new int[]{49, 10471, 10475};
            def.recolorTo = new int[]{-32518, 25, -32518};
            def.femaleModel = 15472;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 15493;
            def.maleModel = 15472;
            def.xOffset2d = 2;
            def.yOffset2d = 8;
            def.xan2d = 444;
            def.yan2d = 1658;
            def.zoom2d = 1104;
        }

        if (id == FLUFFY_JR) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Fluffy Jr pet";
            def.recolorFrom = new int[]{0, 11200, 929, 931, 9542, 902, 262, 906, 910, 914, 918, 922, 955, 9149, 7101, 8125, 6077, 4029, 957, 1981, 926};
            def.recolorTo = new int[]{4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 0, 4769, 0, 0, 4769, 4769};
            def.inventoryModel = 29240;
            def.xan2d = 3;
            def.yan2d = 213;
            def.zoom2d = 1616;
        }

        if (id == CENTAUR_MALE) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Centaur pet";
            def.inventoryModel = 16213;
            def.zoom2d = 3500;
            def.xan2d = 25;
            def.yan2d = 25;
            def.yOffset2d = -10;
        }

        if (id == FOUNDER_IMP) {
            def.name = "<col=65280>Founder imp";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 58916;
            def.xOffset2d = 11;
            def.yOffset2d = -1;
            def.xan2d = 116;
            def.yan2d = 1778;
            def.zoom2d = 1424;
        }

        if (id == PET_CORRUPTED_NECHRYARCH) {
            def.name = "<col=65280>Corrupted nechryarch pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 58922;
            def.zoom2d = 7000;
            def.xan2d = 25;
            def.yan2d = 25;
            def.yOffset2d = -10;
        }

        if (id == MINI_NECROMANCER) {
            def.name = "<col=65280>Mini necromancer";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 58979;
            def.yOffset2d = -12;
            def.xan2d = 118;
            def.yan2d = 10;
            def.zoom2d = 1136;
        }

        if (id == CORRUPTED_RANGER_GAUNTLETS) {
            def.name = "<col=65280>Corrupted ranger gauntlets";
            def.femaleModel = 36335;
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.inventoryModel = 36141;
            def.maleModel = 36325;
            def.xOffset2d = -1;
            def.xan2d = 420;
            def.yan2d = 1082;
            def.zoom2d = 917;
            def.recolorFrom = new int[]{16, 30643, 12484, 13493, -32630, 8, 24, 10411, 12};
            def.recolorTo = new int[]{350780, 30643, 347770, 13493, -32630, 8, 24, 350700, 12};
        }

        if (id == JALNIBREK) {
            def.name = "<col=65280>Jal-nib-rek pet";
        }

        if (id == YOUNGLLEF) {
            def.name = "<col=65280>Yougnleff pet";
        }

        if (id == CORRUPTED_YOUNGLLEF) {
            def.name = "<col=65280>Corrupted yougnleff pet";
        }

        if (id == CORRUPTING_STONE) {
            def.name = "<col=65280>Corrupting stone";
            def.recolorFrom = new int[]{-22297, 127};
            def.recolorTo = new int[]{945, 582};
        }

        if (id == JALTOK_JAD) {
            def.name = "<col=65280>Jaltok-jad pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 33012;
            def.xOffset2d = -3;
            def.yOffset2d = -30;
            def.yan2d = 553;
            def.zoom2d = 12000;
        }

        if (id == BABY_LAVA_DRAGON) {
            def.name = "<col=65280>Baby lava dragon";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 58995;
            def.xOffset2d = -97;
            def.yOffset2d = 9;
            def.xan2d = 83;
            def.yan2d = 1826;
            def.zoom2d = 2541;
        }

        if (id == CENTAUR_FEMALE) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Centaur pet";
            def.inventoryModel = 16212;
            def.zoom2d = 2300;
            def.xan2d = 25;
            def.yan2d = 25;
            def.yOffset2d = -10;
        }

        if (id == DEMENTOR_PET) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Dementor pet";
            def.recolorFrom = new int[]{3346, 6371};
            def.recolorTo = new int[]{0, 0};
            def.inventoryModel = 14992;
            def.xOffset2d = -1;
            def.yOffset2d = -10;
            def.xan2d = 160;
            def.yan2d = 64;
            def.zoom2d = 530;
        }

        if (id == FENRIR_GREYBACK_JR) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Fenrir Greyback Jr";
            def.recolorFrom = new int[]{0, 11200, 929, 931, 9542, 902, 262, 906, 910, 914, 918, 922, 955, 9149, 7101, 8125, 6077, 4029, 957, 1981, 926};
            def.recolorTo = new int[]{4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 0, 4769, 0, 0, 4769, 4769};
            def.inventoryModel = 26177;
            def.yOffset2d = 100;
            def.xan2d = 100;
            def.yan2d = 100;
            def.zoom2d = 1000;
        }

        if (id == 12873 || id == 12875 || id == 12877 || id == 12879 || id == 12881 || id == 12883 || id == 6759 || id == DWARF_CANNON_SET) {
            def.inventoryActions = new String[5];
            def.inventoryActions[0] = "Open";
        }

        if (id == DRAGON_CLAWS_OR) {
            ItemDefinition.copyInventory(def, 13652);
            def.name = "<col=65280>Dragon claws (or)";
            def.recolorFrom = new int[]{929, 914, 918, 922};
            def.recolorTo = new int[]{929, 7114, 7114, 7114};
        }

        if (id == 12791) {
            def.inventoryActions = new String[]{"Open", null, null, "Empty", "Drop"};
        }

        if (id == RUNE_POUCH_I) {
            ItemDefinition.copyInventory(def, 12791);
            def.inventoryActions = new String[]{"Open", null, null, "Empty", "Destroy"};
            def.name = "<col=65280>Rune pouch (i)";
            def.ambient = 120;
        }

        if (id == DOUBLE_DROPS_LAMP) {
            def.name = "<col=65280>Double drops lamp";
            def.description = "Receive double drops when killing bosses. (for 1 hour)";
        }

        if (id == WILDERNESS_KEY) {
            def.name = "<col=65280><col=65280>Wilderness key</col>";
        }

        if (id == POINTS_MYSTERY_CHEST) {
            def.name = "<col=65280>Points mystery chest";
            def.inventoryActions = new String[]{"Open", null, null, null, null};
            def.recolorFrom = new int[]{49, 6466, 24, 5545};
            def.recolorTo = new int[]{49, 302770, 24, 296770};
            def.inventoryModel = 12146;
            def.xan2d = 114;
            def.yan2d = 1883;
            def.zoom2d = 2640;
        }

        if (id == RANGING_SCROLL) {
            def.name = "<col=65280>Ranging scroll";
            def.inventoryActions = new String[]{"Read", null, null, null, "Drop"};
            def.stackable = 0;
            def.inventoryModel = 10347;
            def.xOffset2d = -1;
            def.yOffset2d = 11;
            def.xan2d = 344;
            def.yan2d = 656;
            def.zoom2d = 1020;
            def.modelCustomColor4 = 32222;
        }

        if (id == MELEE_SCROLL) {
            def.name = "<col=65280>Melee scroll";
            def.inventoryActions = new String[]{"Read", null, null, null, "Drop"};
            def.stackable = 0;
            def.inventoryModel = 10347;
            def.xOffset2d = -1;
            def.yOffset2d = 11;
            def.xan2d = 344;
            def.yan2d = 656;
            def.zoom2d = 1020;
            def.modelCustomColor4 = 42222;
        }

        if (id == DOUBLE_DROPS_SCROLL) {
            def.name = "<col=65280>Double drop scroll";
            def.inventoryActions = new String[]{null, null, null, null, null};
            def.stackable = 1;
            def.inventoryModel = 10347;
            def.xOffset2d = -1;
            def.yOffset2d = 11;
            def.xan2d = 344;
            def.yan2d = 656;
            def.zoom2d = 1020;
            def.modelCustomColor4 = 22222;
        }

        if(id == SAPPHIRE_MEMBER_RANK) {
            ItemDefinition.copyInventory(def, SCROLL);
            def.name = "<col=65280>Sapphire member rank scroll";
            def.modelCustomColor4 = 31575;
        }

        if(id == FIFTY_TOTAL_DONATED_SCROLL) {
            ItemDefinition.copyInventory(def, SCROLL);
            def.name = "<col=65280>$50 Scroll";
            def.modelCustomColor4 = 12222;
        }

        if(id == ONE_HUNDRED_TOTAL_DONATED_SCROLL) {
            ItemDefinition.copyInventory(def, SCROLL);
            def.name = "<col=65280>$100 Scroll";
            def.modelCustomColor4 = 13133;
        }

        if(id == HUNDRED_FIFTY_TOTAL_DONATED_SCROLL) {
            ItemDefinition.copyInventory(def, SCROLL);
            def.name = "<col=65280>$150 Scroll";
            def.modelCustomColor4 = 235;
        }

        if (id == 30192) {
            ItemDefinition.copyInventory(def, 3985);
            def.name = "<col=65280>Winter Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{127, 155, 374770};
        }

        if (id == 30193) {
            ItemDefinition.copyInventory(def, 3987);
            def.name = "<col=65280>Winter Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{127, 155, 374770};
        }

        if (id == 30194) {
            ItemDefinition.copyInventory(def, 3989);
            def.name = "<col=65280>Winter Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{127, 155, 374770};
        }

        if (id == SLAYER_TELEPORT_SCROLL) {
            def.name = "<col=65280>Slayer teleport scroll";
            def.inventoryActions = new String[]{"Tear", null, null, null, null};
            def.stackable = 1;
            def.inventoryModel = 10347;
            def.xOffset2d = -1;
            def.yOffset2d = 11;
            def.xan2d = 344;
            def.yan2d = 656;
            def.zoom2d = 1020;
            def.modelCustomColor4 = 12222;
        }

        if (id == GIANT_KEY_OF_DROPS) {
            def.name = "<col=65280>Giant key of drops";
            def.inventoryActions = new String[]{null, null, null, null, null};
            def.inventoryModel = 55611;
            def.xOffset2d = -2;
            def.yOffset2d = -2;
            def.xan2d = 512;
            def.yan2d = 40;
            def.zoom2d = 740;
            def.animateInventory = true;
        }

        if(id == COLLECTION_KEY) {
            def.name = "<col=65280>Collection key";
            def.inventoryActions = new String[]{null, null, null, null, null};
            def.inventoryModel = 55612;
            def.xOffset2d = 1;
            def.yOffset2d = 5;
            def.xan2d = 471;
            def.yan2d = 20;
            def.zoom2d = 1296;
        }

        if(id == CURSED_AMULET_OF_THE_DAMNED) {
            def.name = "<col=65280>Cursed amulet of the damned";
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.inventoryModel = 55613;
            def.femaleModel = 55614;
            def.maleModel = 55614;
            def.xOffset2d = -1;
            def.yOffset2d = 2;
            def.xan2d = 512;
            def.yan2d = 108;
            def.zan2d = 2020;
            def.zoom2d = 480;
        }

        if(id == FRAGMENT_OF_SEREN_PET) {
            def.name = "<col=65280>Fragment of seren pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.zoom2d = 5000;
            def.yan2d = 100;
            def.xan2d = 100;
            def.xOffset2d = 0;
            def.yOffset2d = 10;
            def.inventoryModel = 38608;
        }

        if(id == BLOOD_FURY_HESPORI_PET) {
            def.name = "<col=65280>Bloodfury hespori pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.zoom2d = 5000;
            def.yan2d = 100;
            def.xan2d = 100;
            def.xOffset2d = 0;
            def.yOffset2d = 10;
            NpcDefinition npcInstance = NpcDefinition.get(BLOOD_FURY_HESPORI);
            def.inventoryModel = npcInstance.modelId[0];
        }

        if(id == INFERNAL_SPIDER_PET) {
            def.name = "<col=65280>Infernal spider pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.animate_inv_sprite = true;
            def.inventoryModel = 55622;
            def.xOffset2d = 9;
            def.yOffset2d = -4;
            def.xan2d = 67;
            def.yan2d = 67;
            def.zoom2d = 1380;
        }

        if (id == DONATOR_MYSTERY_BOX || id == POINTS_MYSTERY_BOX || id == POINTS_MYSTERY_BOX +1 || id == BARROWS_MYSTERY_BOX || id == RAIDS_MYSTERY_BOX || id == SUPER_MYSTERY_BOX || id == PETS_MYSTERY_BOX
            || id == BRONZE_MYSTERY_BOX || id == SILVER_MYSTERY_BOX || id == GOLD_MYSTERY_BOX || id == PLATINUM_MYSTERY_BOX || id == YOUTUBE_MYSTERY_BOX || id == LEGENDARY_MYSTERY_BOX || id == PROMO_MYSTERY_BOX
            || id == PVP_MYSTERY_BOX || id == WEAPON_MYSTERY_BOX || id == ARMOUR_MYSTERY_BOX || id == GRAND_MYSTERY_BOX) {
            ItemDefinition.copyInventory(def, 6199);
            switch (id) {
                case DONATOR_MYSTERY_BOX:
                    def.name = "<col=65280>Donator mystery box";
                    def.inventoryModel = 55566;
                    def.animateInventory = true;
                    def.recolorFrom = new int[]{2999, 22410};
                    def.recolorTo = new int[]{524, 13};
                    break;

                case SUPER_MYSTERY_BOX:
                    def.name = "<col=65280>Super mystery box";
                    def.inventoryModel = 55615;
                    def.animateInventory = true;
                    def.recolorFrom = new int[]{2999, 22410};
                    def.recolorTo = new int[]{524, 13};
                    break;

                case PETS_MYSTERY_BOX:
                    def.name = "<col=65280>Pets mystery box";
                    def.inventoryModel = 55616;
                    def.animateInventory = true;
                    def.recolorFrom = new int[]{2999, 22410};
                    def.recolorTo = new int[]{524, 13};
                    break;

                case RAIDS_MYSTERY_BOX:
                    def.name = "<col=65280>Raids mystery box";
                    def.inventoryModel = 55610;
                    def.animateInventory = true;
                    def.recolorFrom = new int[]{2999, 22410};
                    def.recolorTo = new int[]{524, 13};
                    break;

                case POINTS_MYSTERY_BOX:
                    def.name = "<col=65280>Point mystery box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{302770, 296770};
                    break;

                case 30187:
                    def.note = 30186;
                    def.notedTemplate = 799;
                    def.stackable = 1;
                    break;

                case BARROWS_MYSTERY_BOX:
                    def.name = "<col=65280>Barrows Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{356770, 127};
                    break;

                case BRONZE_MYSTERY_BOX:
                    def.name = "<col=65280>Bronze Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{5652, 127};
                    break;

                case SILVER_MYSTERY_BOX:
                    def.name = "<col=65280>Silver Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{82, 127};
                    break;

                case GOLD_MYSTERY_BOX:
                    def.name = "<col=65280>Gold Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{8128, 127};
                    break;

                case PLATINUM_MYSTERY_BOX:
                    def.name = "<col=65280>Platinum Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{37982, 127};
                    break;

                case YOUTUBE_MYSTERY_BOX:
                    def.name = "<col=65280>Youtube Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{43331, 127};
                    break;

                case LEGENDARY_MYSTERY_BOX:
                    def.name = "<col=65280>Legendary mystery box";
                    def.inventoryModel = 55617;
                    def.animateInventory = true;
                    def.recolorFrom = new int[]{2999, 22410};
                    def.recolorTo = new int[]{524, 13};
                    break;

                case PROMO_MYSTERY_BOX:
                    def.name = "<col=65280>Promo mystery box";
                    def.inventoryModel = 55618;
                    def.animateInventory = true;
                    def.recolorFrom = new int[]{2999, 22410};
                    def.recolorTo = new int[]{524, 13};
                    break;

                case PVP_MYSTERY_BOX:
                    def.name = "<col=65280>PvP Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{54433, 127};
                    break;

                case WEAPON_MYSTERY_BOX:
                    def.name = "<col=65280>Weapon Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{-21568, 127};
                    break;

                case ARMOUR_MYSTERY_BOX:
                    def.name = "<col=65280>Armour Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{22464, 127};
                    break;

                case GRAND_MYSTERY_BOX:
                    def.name = "<col=65280>Grand Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{0, 9152};
                    break;
            }
        }

        if (id == SWORD_OF_GRYFFINDOR) {
            def.name = "<col=65280>Sword of gryffindor";
            def.recolorFrom = new int[]{10258, 10291, 10275, 10262, 10266, 10283};
            def.recolorTo = new int[]{82, 125, 125, 121, 125, 125};
        }

        if (id == CHRISTMAS_CRACKER) {
            def.inventoryActions = new String[]{"Open", null, null, null, null};
        }

        if (id == ZOMBIES_CHAMPION_PET) {
            def.name = "<col=65280>Zombies champion pet";
            def.inventoryActions = new String[]{null, null, null, null, null};
        }

        if (id == BARRELCHEST_PET) {
            def.name = "<col=65280>Barrelchest pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 22790;
            def.zoom2d = 5980;
            def.yan2d = 0;
            def.xan2d = 100;
            def.xOffset2d = 2;
            def.yOffset2d = 153;
            def.stackable = 0;
        }

        switch (id) {

            case LAVA_DHIDE_COIF:
                def.name = "<col=65280>Lava dhide coif";
                def.femaleModel = 58987;
                def.femaleModel1 = 403;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 58990;
                def.maleModel = 59000;
                def.maleModel1 = 230;
                def.yOffset2d = -35;
                def.xan2d = 194;
                def.yan2d = 1784;
                def.zoom2d = 789;
                break;

            case LAVA_DHIDE_BODY:
                def.name = "<col=65280>Lava dhide body";
                def.femaleModel = 58986;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 58991;
                def.maleModel = 58999;
                def.yOffset2d = -4;
                def.yan2d = 0;
                def.xan2d = 548;
                def.zoom2d = 1180;
                break;

            case LAVA_DHIDE_CHAPS:
                def.name = "<col=65280>Lava dhide chaps";
                def.femaleModel = 58988;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 58992;
                def.maleModel = 59001;
                def.yOffset2d = -1;
                def.xan2d = 444;
                def.yan2d = 0;
                def.zoom2d = 1827;
                break;

            case PRIMORDIAL_BOOTS_OR:
                def.name = "<col=65280>Primordial boots (or)";
                def.femaleModel = 58967;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 58966;
                def.maleModel = 58968;
                def.xOffset2d = 5;
                def.yOffset2d = -5;
                def.xan2d = 147;
                def.yan2d = 279;
                def.zoom2d = 976;
                break;

            case CORRUPTED_BOOTS:
                def.name = "<col=65280>Corrupted boots";
                def.femaleModel = 59096;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59095;
                def.maleModel = 59097;
                def.xOffset2d = 5;
                def.yOffset2d = -5;
                def.xan2d = 147;
                def.yan2d = 279;
                def.zoom2d = 976;
                break;

            case ELDER_WAND:
            case ELDER_WAND_RAIDS:
                def.name = "<col=65280>Elder wand";
                def.recolorFrom = new int[]{-19153, -19500, -19145, 37, -16339, -16331};
                def.recolorTo = new int[]{530, 540, 529, 10, 13, 16};
                def.femaleModel = 32669;
                def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
                def.inventoryModel = 32789;
                def.maleModel = 32669;
                def.xOffset2d = 2;
                def.yOffset2d = -4;
                def.xan2d = 140;
                def.yan2d = 1416;
                def.zoom2d = 668;
                break;

            case BABY_DARK_BEAST_EGG:
                def.name = "<col=65280>Baby Dark Beast";
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                def.zoom2d = 550;
                def.xan2d = 76;
                def.yan2d = 16;
                def.xOffset2d = 0;
                def.yOffset2d = 0;
                def.inventoryModel = 7171;
                def.recolorFrom = new int[]{476};
                def.recolorTo = new int[]{54444};
                break;

            case PET_KREE_ARRA_WHITE:
                ItemDefinition.copyInventory(def, 12649);
                def.name = "<col=65280>Pet kree'arra (white)";
                def.inventoryActions = new String[]{null, null, null, "Wipe-off paint", null};
                def.modelCustomColor4 = 31575;
                break;

            case PET_ZILYANA_WHITE:
                ItemDefinition.copyInventory(def, 12651);
                def.name = "<col=65280>Pet zilyana (white)";
                def.inventoryActions = new String[]{null, null, null, "Wipe-off paint", null};
                def.modelCustomColor4 = 33785;
                break;

            case PET_GENERAL_GRAARDOR_BLACK:
                ItemDefinition.copyInventory(def, 12650);
                def.name = "<col=65280>Pet general graardor (black)";
                def.inventoryActions = new String[]{null, null, null, "Wipe-off paint", null};
                def.modelCustomColor4 = 235;
                break;

            case PET_KRIL_TSUTSAROTH_BLACK:
                ItemDefinition.copyInventory(def, 12652);
                def.name = "<col=65280>Pet k'ril tsutsaroth (black)";
                def.inventoryActions = new String[]{null, null, null, "Wipe-off paint", null};
                def.modelCustomColor4 = 235;
                break;

            case BABY_ABYSSAL_DEMON:
                def.name = "<col=65280>Baby Abyssal demon";
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                def.zoom2d = 550;
                def.xan2d = 76;
                def.yan2d = 16;
                def.xOffset2d = 0;
                def.yOffset2d = 0;
                def.inventoryModel = 7171;
                def.modelCustomColor3 = 1343;
                break;

            case VENGEANCE_SKULL:
                def.inventoryActions = new String[]{"Cast", null, null, null, "Destroy"};
                def.name = "<col=65280>Vengeance";
                def.description = "Rebound damage to an opponent.";
                def.recolorFrom = new int[]{5231};
                def.recolorTo = new int[]{130770};
                break;

            case MYSTERY_CHEST:
                def.inventoryModel = 55570;
                def.zoom2d = 2640;
                def.xan2d = 114;
                def.yan2d = 1883;
                def.animateInventory = true;
                def.name = "<col=65280>Mystery chest";
                def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
                def.recolorFrom = new int[]{24, 49, 4510, 4502, 8128, 7093};
                def.recolorTo = new int[]{24, 49, 184770, 184770, 87770, 87770};
                break;

            case BABY_SQUIRT:
                def.name = "<col=65280>Baby Squirt pet";
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                def.zoom2d = 550;
                def.xan2d = 76;
                def.yan2d = 16;
                def.xOffset2d = 0;
                def.yOffset2d = 0;
                def.inventoryModel = 7171;
                def.recolorFrom = new int[]{476};
                def.recolorTo = new int[]{246770};
                break;

            case PET_PAINT_BLACK:
                def.modelCustomColor4 = 155;
                def.name = "<col=65280>Pet paint (black)";
                def.description = "Changes color of baby K'ril and baby Graardor.";
                break;

            case PET_PAINT_WHITE:
                def.modelCustomColor4 = 2;
                def.name = "<col=65280>Pet paint (white)";
                def.description = "Changes color of baby Kree'Arra and baby Zilyana.";
                break;

            case FIVE_DOLLAR_BOND:
                def.name = "<col=65280>$5 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.modelCustomColor4 = 975;
                break;

            case TEN_DOLLAR_BOND:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$10 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.modelCustomColor4 = 13133;
                break;

            case TWENTY_DOLLAR_BOND:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$20 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.modelCustomColor4 = 233333;
                break;

            case THIRTY_DOLLAR_BOND:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$30 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.modelCustomColor4 = 8999;
                break;

            case FORTY_DOLLAR_BOND:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$40 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.modelCustomColor4 = 490;
                break;

            case FIFTY_DOLLAR_BOND:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$50 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.modelCustomColor4 = 32222;
                break;

            case SEVENTY_FIVE_DOLLAR_BOND:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$75 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.modelCustomColor4 = 22222;
                break;

            case ONE_HUNDRED_DOLLAR_BOND:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$100 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.modelCustomColor4 = 964;
                break;

            case EARTH_ARROWS:
                def.name = "<col=65280>Earth arrows";
                break;

            case FIRE_ARROWS:
                def.name = "<col=65280>Fire arrows";
                def.recolorFrom = new int[]{57, 61, 5012, 926};
                def.recolorTo = new int[]{57, 61, 5012, 926};
                break;

            case ANCIENT_WARRIOR_SWORD:
                def.name = "<col=65280>Ancient warrior sword";
                def.recolorFrom = new int[]{920, 0, 103};
                def.recolorTo = new int[]{391770, 0, 110};
                break;

            case ANCIENT_WARRIOR_MAUL:
                def.name = "<col=65280>Ancient warrior maul";
                def.recolorFrom = new int[]{78, 103, 920};
                def.recolorTo = new int[]{391470, 391470, 100, 100};
                break;

            case ANCIENT_WARRIOR_AXE:
                def.name = "<col=65280>Ancient warrior axe";
                def.recolorFrom = new int[]{0, 78, 920};
                def.recolorTo = new int[]{191770, 191770, 110};
                break;

            case VALINOR_COINS:
                ItemDefinition.copyInventory(def, 995);
                def.countco = new int[]{2, 3, 4, 5, 25, 100, 250, 1000, 10000, 0};
                def.countobj = new int[]{17001, 17002, 17003, 17004, 17005, 17006, 17007, 17008, 17009, 0};
                def.name = ClientConstants.CLIENT_NAME + " coins";
                def.recolorFrom = new int[]{8128};
                def.recolorTo = new int[]{5706};
                break;
            case 17001:
                ItemDefinition.copyInventory(def, 996);
                def.name = ClientConstants.CLIENT_NAME + " coins";
                def.recolorFrom = new int[]{8128};
                def.recolorTo = new int[]{5706};
                break;
            case 17002:
                ItemDefinition.copyInventory(def, 997);
                def.name = ClientConstants.CLIENT_NAME + " coins";
                def.recolorFrom = new int[]{8128};
                def.recolorTo = new int[]{5706};
                break;
            case 17003:
                ItemDefinition.copyInventory(def, 998);
                def.name = ClientConstants.CLIENT_NAME + " coins";
                def.recolorFrom = new int[]{8128};
                def.recolorTo = new int[]{5706};
                break;
            case 17004:
                ItemDefinition.copyInventory(def, 999);
                def.name = ClientConstants.CLIENT_NAME + " coins";
                def.recolorFrom = new int[]{8128};
                def.recolorTo = new int[]{5706};
                break;
            case 17005:
                ItemDefinition.copyInventory(def, 1000);
                def.name = ClientConstants.CLIENT_NAME + " coins";
                def.recolorFrom = new int[]{8128};
                def.recolorTo = new int[]{5706};
                break;
            case 17006:
                ItemDefinition.copyInventory(def, 1001);
                def.name = ClientConstants.CLIENT_NAME + " coins";
                def.recolorFrom = new int[]{8128};
                def.recolorTo = new int[]{5706};
                break;
            case 17007:
                ItemDefinition.copyInventory(def, 1002);
                def.name = ClientConstants.CLIENT_NAME + " coins";
                def.recolorFrom = new int[]{8128};
                def.recolorTo = new int[]{5706};
                break;
            case 17008:
                ItemDefinition.copyInventory(def, 1003);
                def.name = ClientConstants.CLIENT_NAME + " coins";
                def.recolorFrom = new int[]{8128};
                def.recolorTo = new int[]{5706};
                break;
            case 17009:
                ItemDefinition.copyInventory(def, 1004);
                def.name = ClientConstants.CLIENT_NAME + " coins";
                def.recolorFrom = new int[]{8128};
                def.recolorTo = new int[]{5706};
                break;

            case KEY_OF_DROPS:
                def.name = "<col=65280>Key of drops";
                def.modelCustomColor3 = 54325;
                break;

            case LAVA_WHIP:
                def.name = "<col=65280>Lava whip";
                break;

            case FROST_WHIP:
                def.name = "<col=65280>Frost whip";
                break;

            case GENIE_PET:
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                def.name = "<col=65280>Genie pet";
                break;

            case GRIM_REAPER_PET:
                def.name = "<col=65280>Grim Reaper pet";
                def.zoom2d = 1010;
                def.xan2d = 0;
                def.yan2d = 0;
                def.xOffset2d = 1;
                def.yOffset2d = 79;
                def.inventoryModel = 5100;
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                break;

            case BLOOD_REAPER:
                def.name = "<col=65280>Blood Reaper pet";
                def.zoom2d = 1010;
                def.xan2d = 0;
                def.yan2d = 0;
                def.xOffset2d = 1;
                def.yOffset2d = 79;
                def.inventoryModel = 5100;
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                def.modelCustomColor4 = 964;
                break;

            case ELEMENTAL_BOW:
                def.name = "<col=65280>Elemental bow";
                def.zoom2d = 1862;
                def.xan2d = 456;
                def.yan2d = 1232;
                def.xOffset2d = 13;
                def.yOffset2d = -7;
                def.recolorTo = new int[]{311770, 74, 10283, 374770, 311770, 311770, 10299, 66, 127};
                def.recolorFrom = new int[]{26772, 74, 10283, 41, 26780, 26776, 10299, 66, 127};
                def.groundActions = new String[]{null, null, "Take", null, null};
                def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
                def.inventoryModel = 28678;
                def.maleModel = 28622;
                def.femaleModel = 28622;
                break;

            case DONATOR_TICKET:
                def.name = "<col=65280>Donator ticket";
                break;

            case VOTE_TICKET:
                def.name = "<col=65280>Vote ticket";
                def.stackable = 1;
                def.inventoryActions = new String[]{"Convert to Points", null, null, null, "Drop"};
                break;

            case LAVA_PARTYHAT:
                def.name = "<col=65280>Lava Party hat";
                def.inventoryActions = new String[5];
                def.inventoryActions[1] = "Wear";
                def.zoom2d = 440;
                def.inventoryModel = 55571;
                def.animateInventory = true;
                def.xOffset2d = 1;
                def.yOffset2d = 1;
                def.yan2d = 1852;
                def.xan2d = 76;
                def.maleModel = 55572;
                def.femaleModel = 55573;
                def.recolorFrom = new int[]{926};
                def.recolorTo = new int[]{926};
                def.retextureFrom = new short[]{926};
                def.retextureTo = new short[]{31};
                break;

            case 23975:
            case 23971:
            case 23979:
            case 24668:
            case 24664:
            case 24666:
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                break;

            case SAELDOR_SHARD:
                def.name = "<col=65280>Saeldor shard";
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                break;

            case SALAZAR_SLYTHERINS_LOCKET:
                def.name = "<col=65280>Salazar Slytherin's locket";
                def.inventoryModel = 55562;
                def.zoom2d = 590;
                def.xan2d = 500;
                def.yan2d = 0;
                def.xOffset2d = 0;
                def.yOffset2d = 7;
                def.stackable = 0;
                def.groundActions = new String[]{null, null, "Take", null, null};
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.maleModel = 55563;
                def.femaleModel = 55564;
                break;

            case SLAYER_KEY:
                def.name = "<col=65280>Slayer key";
                def.inventoryModel = 55565;
                def.animateInventory = true;
                def.recolorFrom = new int[]{57, 49};
                def.recolorTo = new int[]{6453, 6569};
                def.zoom2d = 860;
                def.xan2d = 460;
                def.yan2d = 20;
                def.xOffset2d = -9;
                def.yOffset2d = -2;
                def.stackable = 1;
                break;

            case TWISTED_ANCESTRAL_COLOUR_KIT:
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                break;

            case TOTEMIC_HELMET:
                def.name = "<col=65280>Totemic helmet";
                def.femaleModel = 59029;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59030;
                def.maleModel = 59031;
                def.yOffset2d = -12;
                def.xan2d = 118;
                def.yan2d = 10;
                def.zoom2d = 1236;
                break;

            case TOTEMIC_PLATEBODY:
                def.name = "<col=65280>Totemic platebody";
                def.femaleModel = 59028;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59025;
                def.maleModel = 59035;
                def.yOffset2d = -3;
                def.xan2d = 514;
                def.yan2d = 2041;
                def.zoom2d = 1358;
                break;

            case TOTEMIC_PLATELEGS:
                def.name = "<col=65280>Totemic platelegs";
                def.ambient = 30;
                def.contrast = 20;
                def.femaleModel = 59033;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59032;
                def.maleModel = 59034;
                def.xan2d = 496;
                def.yan2d = 9;
                def.zoom2d = 2061;
                break;

            case DARK_SAGE_HAT:
                def.name = "<col=65280>Dark sage hat";
                def.femaleModel = 58915;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 58914;
                def.maleModel = 58913;
                def.yOffset2d = -12;
                def.xan2d = 118;
                def.yan2d = 10;
                def.zoom2d = 1236;
                break;

            case DARK_SAGE_ROBE_TOP:
                def.name = "<col=65280>Dark sage robe top";
                def.femaleModel = 58911;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 58919;
                def.maleModel = 58912;
                def.yOffset2d = -3;
                def.xan2d = 514;
                def.yan2d = 2041;
                def.zoom2d = 1358;
                break;

            case DARK_SAGE_ROBE_BOTTOM:
                def.name = "<col=65280>Dark sage robe bottoms";
                def.ambient = 30;
                def.contrast = 20;
                def.femaleModel = 58918;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 58917;
                def.maleModel = 58909;
                def.xOffset2d = -1;
                def.yOffset2d = 7;
                def.xan2d = 435;
                def.yan2d = 9;
                def.zoom2d = 1690;
                break;

            case TWISTED_BOW_ORANGE:
                def.name = "<col=65280>Twisted bow";
                def.femaleModel = 58973;
                def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
                def.inventoryModel = 58974;
                def.maleModel = 58973;
                def.xOffset2d = -3;
                def.yOffset2d = 1;
                def.xan2d = 720;
                def.yan2d = 1500;
                break;

            case TWISTED_BOW_PURPLE:
                def.name = "<col=65280>Twisted bow";
                def.femaleModel = 58965;
                def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
                def.inventoryModel = 58964;
                def.maleModel = 58965;
                def.xOffset2d = -3;
                def.yOffset2d = 1;
                def.xan2d = 720;
                def.yan2d = 1500;
                break;

            case SARKIS_DARK_COIF:
                def.name = "<col=65280>Sarkis dark coif";
                def.femaleModel = 58945;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 58947;
                def.maleModel = 58908;
                def.yOffset2d = -12;
                def.xan2d = 118;
                def.yan2d = 10;
                def.zoom2d = 1236;
                break;

            case SARKIS_DARK_BODY:
                def.name = "<col=65280>Sarkis dark body";
                def.femaleModel = 58952;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 58954;
                def.maleModel = 58953;
                def.yOffset2d = -3;
                def.xan2d = 514;
                def.yan2d = 2041;
                def.zoom2d = 1358;
                break;

            case SARKIS_DARK_LEGS:
                def.name = "<col=65280>Sarkis dark legs";
                def.ambient = 30;
                def.contrast = 20;
                def.femaleModel = 58946;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 58951;
                def.maleModel = 58950;
                def.yOffset2d = 3;
                def.xan2d = 1646;
                def.yan2d = 9;
                def.zoom2d = 1979;
                break;

            case RESOURCE_PACK:
                def.name = "<col=65280>Resource pack";
                def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
                def.inventoryModel = 59006;
                def.stackable = 1;
                def.xOffset2d = -3;
                def.yOffset2d = -3;
                def.yan2d = 2047;
                def.xan2d = 0;
                def.zoom2d = 951;
                break;

            case SUMMER_TOKEN:
                def.name = "<col=65280>Summer token";
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                def.inventoryModel = 59047;
                def.xan2d = 468;
                def.yan2d = 56;
                def.yOffset2d = 6;
                def.zoom2d = 450;
                break;

            case AGILITY_MASTER_CAPE:
                def.name = "<col=65280>Agility master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{677, 801, -21996, -21993, -21990, -21987, -21986, -21984, -21982, -21978, -21978, -21978};
                def.femaleModel = 59050;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59049;
                def.maleModel = 59050;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case ATTACK_MASTER_CAPE:
                def.name = "<col=65280>Attack master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 37, 22, 20};
                def.recolorTo = new int[]{7104, 9151, 911, 914, 917, 920, 921, 923, 925, 925, 925, 929};
                def.femaleModel = 59052;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59051;
                def.maleModel = 59052;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case CONSTRUCCTION_MASTER_CAPE:
                def.name = "<col=65280>Construction master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{6061, 5945, 6327, 6330, 6333, 6336, 6337, 6339, 6341, 6345, 6345, 6345};
                def.femaleModel = 59054;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59053;
                def.maleModel = 59054;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case COOKING_MASTER_CAPE:
                def.name = "<col=65280>Cooking master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{920, 920, -13680, -13677, -13674, -13671, -13670, -13668, -13666, -13662, -13662, -13662};
                def.femaleModel = 59056;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59055;
                def.maleModel = 59056;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case CRAFTING_MASTER_CAPE:
                def.name = "<col=65280>Crafting master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{9142, 9152, 4511, 4514, 4517, 4520, 4521, 4523, 4525, 4529, 4529, 4529};
                def.femaleModel = 59058;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59057;
                def.maleModel = 59058;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case DEFENCE_MASTER_CAPE:
                def.name = "<col=65280>Defence master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{10460, 10473, -24126, -24123, -24120, -24117, -24116, -24114, -24112, -24108, -24108, -24108};
                def.femaleModel = 59060;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59059;
                def.maleModel = 59060;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case FARMING_MASTER_CAPE:
                def.name = "<col=65280>Farming master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{14775, 14792, 22026, 22029, 22032, 22035, 22036, 22038, 22040, 22044, 22044, 22044};
                def.femaleModel = 59062;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59061;
                def.maleModel = 59062;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case FIREMAKING_MASTER_CAPE:
                def.name = "<col=65280>Firemaking master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{8125, 9152, 4015, 4018, 4021, 4024, 4025, 4027, 4029, 4033, 4033, 4033};
                def.femaleModel = 59064;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59063;
                def.maleModel = 59064;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case FISHING_MASTER_CAPE:
                def.name = "<col=65280>Fishing master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{9144, 9152, -27334, -27331, -27328, -27325, -27324, -27322, -27316, -27314, -27314, -27314};
                def.femaleModel = 59066;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59065;
                def.maleModel = 59066;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case FLETCHING_MASTER_CAPE:
                def.name = "<col=65280>Fletching master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{6067, 9152, -31866, -31863, -31860, -31857, -31856, -31854, -31852, -31848, -31848, -31848};
                def.femaleModel = 59068;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59067;
                def.maleModel = 59068;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case HERBLORE_MASTER_CAPE:
                def.name = "<col=65280>Herblore master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{9145, 9156, 22414, 22417, 22420, 22423, 22424, 22426, 22428, 22432, 22432, 22432};
                def.femaleModel = 59070;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59069;
                def.maleModel = 59070;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case HITPOINTS_MASTER_CAPE:
                def.name = "<col=65280>Hitpoints master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{818, 951, 8291, 8294, 8297, 8300, 8301, 8303, 8305, 8309, 8309, 8309};
                def.femaleModel = 59072;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59071;
                def.maleModel = 59072;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case HUNTER_MASTER_CAPE:
                def.name = "<col=65280>Hunter master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{5262, 6020, 8472, 8475, 8478, 8481, 8482, 8484, 8486, 8490, 8490, 8490};
                def.femaleModel = 59074;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59073;
                def.maleModel = 59074;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case MAGIC_MASTER_CAPE:
                def.name = "<col=65280>Magic master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{-21967, -21951, 6336, 6339, 6342, 6345, 6346, 6348, 6350, 6354, 6354, 6354};
                def.femaleModel = 59076;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59075;
                def.maleModel = 59076;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case MINING_MASTER_CAPE:
                def.name = "<col=65280>Mining master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{-29240, -29257, 10386, 10389, 10392, 10395, 10396, 10398, 10400, 10404, 10404, 10404};
                def.femaleModel = 59078;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59077;
                def.maleModel = 59078;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case PRAYER_MASTER_CAPE:
                def.name = "<col=65280>Prayer master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{9163, 9168, 117, 120, 123, 126, 127, 127, 127, 127, 127, 127};
                def.femaleModel = 59080;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59079;
                def.maleModel = 59080;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case RANGE_MASTER_CAPE:
                def.name = "<col=65280>Range master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{3755, 3998, 15122, 15125, 15128, 15131, 15132, 15134, 15136, 15140, 15140, 15140};
                def.femaleModel = 59082;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59081;
                def.maleModel = 59082;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case RUNECRAFTING_MASTER_CAPE:
                def.name = "<col=65280>Runecrafting master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{9152, 8128, 10318, 10321, 10324, 10327, 10328, 10330, 10332, 10336, 10336, 10336};
                def.femaleModel = 59084;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59083;
                def.maleModel = 59084;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case SLAYER_MASTER_CAPE:
                def.name = "<col=65280>Slayer master cape";
                def.recolorFrom = new int[]{-8514, -16725};
                def.recolorTo = new int[]{912, 920};
                def.femaleModel = 59048;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59085;
                def.maleModel = 59048;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case SMITHING_MASTER_CAPE:
                def.name = "<col=65280>Smithing master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{8115, 9148, 10380, 10389, 10392, 10395, 10396, 10398, 10400, 10406, 10406, 10406};
                def.femaleModel = 59093;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59086;
                def.maleModel = 59093;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case STRENGTH_MASTER_CAPE:
                def.name = "<col=65280>Strength master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{935, 931, 27538, 27541, 27544, 27547, 27548, 27550, 27552, 27556, 27556, 27556};
                def.femaleModel = 59088;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59087;
                def.maleModel = 59088;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case THIEVING_MASTER_CAPE:
                def.name = "<col=65280>Thieving master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{11, 1, -6757, -6754, -6751, -6748, -6747, -6745, -6743, -6739, -6739, -6739};
                def.femaleModel = 59090;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59089;
                def.maleModel = 59090;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;

            case WOODCUTTING_MASTER_CAPE:
                def.name = "<col=65280>Woodcutting master cape";
                def.recolorFrom = new int[]{-8514, -16725, 2, 1029, 1032, 11, 12, 14, 16, 20, 37, 22};
                def.recolorTo = new int[]{25109, 24088, 6693, 6696, 6699, 6702, 6703, 6705, 6707, 6711, 6711, 6711};
                def.femaleModel = 59092;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.inventoryModel = 59091;
                def.maleModel = 59092;
                def.xOffset2d = -26;
                def.yOffset2d = 9;
                def.xan2d = 617;
                def.yan2d = 1017;
                def.zoom2d = 2461;
                break;
        }
    }
}

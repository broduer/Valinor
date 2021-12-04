package com.valinor.cache.def.impl.items;

import com.valinor.ClientConstants;
import com.valinor.cache.def.ItemDefinition;
import com.valinor.cache.def.NpcDefinition;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.NpcIdentifiers;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

public class CustomItems {

    public static void unpack(int id) {
        ItemDefinition def = ItemDefinition.get(id);

        if (id == 30383) {
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

        if (id == 30384) {
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

        if (id == 30385) {
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

        if (id == 30386) {
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

        if (id == 30253) {
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

        if (id == 30252) {
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

        if (id == 30251) {
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

        if (id == 30250) {
            def.name = "<col=65280>Nagini";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 13556;
            def.xOffset2d = 3;
            def.yOffset2d = 6;
            def.xan2d = 429;
            def.yan2d = 1985;
            def.zoom2d = 2128;
        }

        if (id == 30224) {
            def.name = "<col=65280>Grim h'ween mask";
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
            def.recolorTo[0] = 28; // Background colour
            def.recolorTo[1] = 9152; // Eyes colour
        }

        if (id == 30225) {
            def.name = "<col=65280>Grim partyhat";
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
            def.recolorTo[0] = 28;
        }

        if (id == 30226) {
            def.name = "<col=65280>Grim scythe";
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 2511;
            def.maleModel = 507;
            def.femaleModel = 507;
            def.xOffset2d = 1;
            def.yOffset2d = 17;
            def.xan2d = 336;
            def.yan2d = 20;
            def.zoom2d = 1930;
            def.recolorFrom = new int[]{7073, 61};
            def.recolorTo = new int[]{28, 61};
        }

        if (id == 30227) {
            def.animateInventory = true;
            def.name = "<col=65280>H'ween mystery chest";
            def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
            def.inventoryModel = 55606;
            def.xOffset2d = 0;
            def.yOffset2d = 0;
            def.xan2d = 114;
            def.yan2d = 1883;
            def.zoom2d = 2640;
            def.recolorFrom = new int[]{24, 49, 4510, 4502, 8128, 7093};
            def.recolorTo = new int[]{24, 49, 374770, 374770, 87770, 87770};
        }

        if (id == 30228) {
            def.name = "<col=65280>Haunted hellhound pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 26262;
            def.zoom2d = 3100;
            def.xan2d = 250;
            def.yan2d = 280;
            def.xOffset2d = -7;
            def.yOffset2d = -329;
        }

        if (id == 30229) {
            def.name = "<col=65280>H'ween armadyl godsword";
            def.femaleModel = 27649;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 28075;
            def.maleModel = 27649;
            def.xOffset2d = -1;
            def.yOffset2d = -1;
            def.xan2d = 498;
            def.yan2d = 484;
            def.zoom2d = 1957;
            def.modelCustomColor3 = 24;
        }

        if (id == 30230) {
            def.name = "<col=65280>H'ween blowpipe";
            def.femaleModel = 14403;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 19219;
            def.maleModel = 14403;
            def.xOffset2d = -7;
            def.yOffset2d = 4;
            def.xan2d = 768;
            def.yan2d = 189;
            def.zoom2d = 1158;
            def.modelCustomColor3 = 24;
        }

        if (id == 30231) {
            def.name = "<col=65280>H'ween dragon claws";
            def.femaleModel = 29191;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 32784;
            def.maleModel = 29191;
            def.xOffset2d = -1;
            def.yOffset2d = 8;
            def.xan2d = 349;
            def.yan2d = 15;
            def.zoom2d = 886;
            def.modelCustomColor3 = 24;
        }

        if (id == 30232) {
            def.name = "<col=65280>H'ween craw's bow";
            def.femaleModel = 35769;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 35775;
            def.maleModel = 35769;
            def.xOffset2d = -1;
            def.yOffset2d = -3;
            def.xan2d = 1463;
            def.yan2d = 510;
            def.zan2d = 835;
            def.zoom2d = 1979;
            def.modelCustomColor3 = 24;
        }

        if (id == 30233) {
            def.name = "<col=65280>H'ween chainmace";
            def.femaleModel = 35771;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 35780;
            def.maleModel = 35771;
            def.xOffset2d = 11;
            def.yOffset2d = -8;
            def.xan2d = 1495;
            def.yan2d = 256;
            def.zoom2d = 1488;
            def.modelCustomColor3 = 24;
        }

        if (id == 30234) {
            ItemDefinition.copyInventory(def, GRANITE_MAUL_24225);
            ItemDefinition.copyEquipment(def, GRANITE_MAUL_24225);
            def.name = "<col=65280>H'ween granite maul";
            def.modelCustomColor3 = 24;
        }

        if (id == 30239) {
            def.name = "<col=65280>Haunted sled";
            def.inventoryActions = new String[]{null, "Ride", null, null, "Drop"};
            def.femaleModel = 4946;
            def.inventoryModel = 4937;
            def.maleModel = 4946;
            def.xan2d = 136;
            def.yan2d = 160;
            def.zoom2d = 1960;
            def.modelCustomColor3 = 24;
        }

        if (id == 30240) {
            def.name = "<col=65280>Haunted crossbow";
            def.femaleModel = 15472;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 15493;
            def.maleModel = 15472;
            def.xOffset2d = 2;
            def.yOffset2d = 8;
            def.xan2d = 444;
            def.yan2d = 1658;
            def.zoom2d = 1104;
            def.modelCustomColor3 = 24;
        }

        if (id == 30241) {
            def.name = "<col=65280>Haunted dragonfire shield";
            def.ambient = 15;
            def.contrast = 15;
            def.femaleModel = 26423;
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
            def.inventoryModel = 26457;
            def.maleModel = 26423;
            def.xan2d = 540;
            def.yan2d = 123;
            def.zoom2d = 2022;
            def.modelCustomColor3 = 24;
        }

        if (id == 30242) {
            def.name = "<col=65280>H'ween item casket";
            def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
            def.inventoryModel = 2450;
            def.xOffset2d = 3;
            def.yOffset2d = -6;
            def.xan2d = 164;
            def.yan2d = 1592;
            def.zoom2d = 1410;
            def.modelCustomColor3 = 24;
        }

        if (id == 30235) {
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

        if (id == 30222) {
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

        if (id == 30122) {
            def.inventoryActions = new String[]{null, null, null, null, null};
            def.name = "<col=65280>Corrupt totem base";
            def.inventoryModel = 31621;
            def.yOffset2d = -4;
            def.xan2d = 188;
            def.yan2d = 108;
            def.zoom2d = 530;
            def.recolorFrom = new int[]{22290, -26664};
            def.recolorTo = new int[]{945, 582};
        }

        if (id == 30123) {
            def.inventoryActions = new String[]{null, null, null, null, null};
            def.name = "<col=65280>Corrupt totem middle";
            def.inventoryModel = 31622;
            def.yOffset2d = -1;
            def.xan2d = 188;
            def.yan2d = 664;
            def.zoom2d = 480;
            def.recolorFrom = new int[]{-26664, -27417};
            def.recolorTo = new int[]{945, 582};
        }

        if (id == 30124) {
            def.inventoryActions = new String[]{null, null, null, null, null};
            def.name = "<col=65280>Corrupt totem top";
            def.inventoryModel = 31623;
            def.xOffset2d = -3;
            def.yOffset2d = -1;
            def.xan2d = 111;
            def.yan2d = 194;
            def.zoom2d = 724;
            def.recolorFrom = new int[]{-26808, -26664, -26713, -26825};
            def.recolorTo = new int[]{945, 582, 712, 728};
        }

        if (id == 30125) {
            def.inventoryActions = new String[]{null, null, null, null, null};
            def.name = "<col=65280>Corrupt totem";
            def.inventoryModel = 31620;
            def.xOffset2d = 2;
            def.yOffset2d = -3;
            def.xan2d = 152;
            def.yan2d = 111;
            def.zoom2d = 1150;
            def.recolorFrom = new int[]{22290, -26808, -26664, -26713, -26825, -27417};
            def.recolorTo = new int[]{945, 582, 152, 712, 728, -77};
        }

        if (id == 23759 || id == 22319 || id == 24491) {
            def.name = "<col=65280>" + def.name + " pet";
        }

        if (id == 25731) {
            def.femaleModel = 42276;
            def.inventoryActions = new String[]{null, "Wield", null, null, null};
            def.inventoryModel = 42292;
            def.maleModel = 42271;
            def.name = "<col=65280>Holy sanguinesti staff";
            def.xOffset2d = -5;
            def.yOffset2d = 3;
            def.cost = 5000000;
            def.xan2d = 552;
            def.yan2d = 1558;
            def.zoom2d = 2258;
        }

        if (id == 25734) {
            def.recolorFrom = new int[]{90};
            def.recolorTo = new int[]{-9762};
            def.femaleModel = 42278;
            def.inventoryActions = new String[]{null, "Wield", null, null, null};
            def.inventoryModel = 42294;
            def.maleModel = 42273;
            def.name = "<col=65280>Holy ghrazi rapier";
            def.xOffset2d = 5;
            def.yOffset2d = -18;
            def.cost = 5000000;
            def.xan2d = 1603;
            def.yan2d = 552;
            def.zoom2d = 2064;
        }

        if (id == 25736) {
            def.femaleModel = 42270;
            def.inventoryActions = new String[]{null, "Wield", null, null, null};
            def.inventoryModel = 42293;
            def.maleModel = 42277;
            def.name = "<col=65280>Holy scythe of vitur";
            def.xOffset2d = 1;
            def.yOffset2d = 17;
            def.cost = 4000000;
            def.xan2d = 327;
            def.yan2d = 23;
            def.zoom2d = 2105;
        }

        if (id == 25739) {
            def.femaleModel = 42272;
            def.inventoryActions = new String[]{null, "Wield", null, null, null};
            def.inventoryModel = 42295;
            def.maleModel = 42279;
            def.name = "<col=65280>Sanguine scythe of vitur";
            def.xOffset2d = 1;
            def.yOffset2d = 17;
            def.cost = 4000000;
            def.xan2d = 327;
            def.yan2d = 23;
            def.zoom2d = 2105;
        }

        if (id == 25753) {
            def.recolorFrom = new int[]{11191, 11183};
            def.recolorTo = new int[]{580, 557};
            def.inventoryActions = new String[]{"Rub", null, null, null, "Drop"};
            def.inventoryModel = 3348;
            def.name = "<col=65280>99 lamp";
            def.xOffset2d = 2;
            def.yOffset2d = -2;
            def.xan2d = 28;
            def.yan2d = 228;
            def.zoom2d = 840;
        }

        if (id == 30315) {
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

        if (id == 30309) {
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

        if (id == 30312) {
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

        if (id == 30175) {
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

        if (id == 30177) {
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

        if (id == 30179) {
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

        if (id == 30183) {
            def.name = "<col=65280>Twisted bow (i)";
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

        if (id == 30049) {
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

        if (id == 15331) {
            def.name = "<col=65280>Ring of manhunting";
            ItemDefinition.copyInventory(def, TYRANNICAL_RING);
            ItemDefinition.copyInventory(def, TYRANNICAL_RING);
            def.modelCustomColor4 = 233333;
        }

        if (id == 15300) {
            def.name = "<col=65280>Recover special (4)";
            ItemDefinition.copyInventory(def, 2436);
            def.recolorTo = new int[]{38222};
            def.recolorFrom = new int[]{61};
        }

        if (id == 15301) {
            def.name = "<col=65280>Recover special (3)";
            ItemDefinition.copyInventory(def, 145);
            def.recolorTo = new int[]{38222};
            def.recolorFrom = new int[]{61};
        }

        if (id == 15302) {
            def.name = "<col=65280>Recover special (2)";
            ItemDefinition.copyInventory(def, 147);
            def.recolorTo = new int[]{38222};
            def.recolorFrom = new int[]{61};
        }

        if (id == 15303) {
            def.name = "<col=65280>Recover special (1)";
            ItemDefinition.copyInventory(def, 149);
            def.recolorTo = new int[]{38222};
            def.recolorFrom = new int[]{61};
        }

        if (id == 27000) {
            ItemDefinition.copyInventory(def, HELLPUPPY);
            def.name = "<col=65280>Kerberos pet";
            def.modelCustomColor4 = 125;
        }

        if (id == 27001) {
            ItemDefinition.copyInventory(def, SCORPIAS_OFFSPRING);
            def.name = "<col=65280>Skorpios pet";
            def.modelCustomColor4 = 125;
        }

        if (id == 27002) {
            ItemDefinition.copyInventory(def, VENENATIS_SPIDERLING);
            def.name = "<col=65280>Arachne pet";
            def.modelCustomColor4 = 125;
        }

        if (id == 27003) {
            ItemDefinition.copyInventory(def, CALLISTO_CUB);
            def.name = "<col=65280>Artio pet";
            def.modelCustomColor4 = 125;
        }

        if (id == 27004) {
            def.name = "<col=65280>Blood money pet";
            ItemDefinition.copyInventory(def, 13316);
            def.stackable = 0;
            def.recolorFrom = new int[]{8128};
            def.recolorTo = new int[]{947};
        }

        if (id == 27005) {
            def.name = "<col=65280>Ring of elysian";
            ItemDefinition.copyInventory(def, 23185);
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.recolorFrom = new int[]{7378, 8289, 8282, 7244};
            def.recolorTo = new int[]{-29116, -29019, -29125, -29110};
        }

        if (id == 27006) {
            def.name = "<col=65280>Toxic staff of the dead (c)";
            ItemDefinition.copyInventory(def, TOXIC_STAFF_OF_THE_DEAD);
            ItemDefinition.copyEquipment(def, TOXIC_STAFF_OF_THE_DEAD);
            def.modelCustomColor4 = 222200;
        }

        if (id == 15304) {
            def.name = "<col=65280>Ring of vigour";
            ItemDefinition.copyInventory(def, LUNAR_RING);
            ItemDefinition.copyEquipment(def, LUNAR_RING);
            def.modelCustomColor4 = 222200;
        }

        if (id == 26500) {
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

        if (id == 26501) {
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

        if (id == 26502) {
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

        if (id == 26503) {
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

        if (id == 26504) {
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

        if (id == 24937) {
            ItemDefinition.copyInventory(def, FAWKES);
            def.name = "<col=65280>Fawkes pet";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24938) {
            ItemDefinition.copyInventory(def, VOID_KNIGHT_GLOVES);
            ItemDefinition.copyEquipment(def, VOID_KNIGHT_GLOVES);
            def.name = "<col=65280>Void knight gloves";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24939) {
            ItemDefinition.copyInventory(def, VOID_RANGER_HELM);
            ItemDefinition.copyEquipment(def, VOID_RANGER_HELM);
            def.name = "<col=65280>Void ranger helm";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24940) {
            ItemDefinition.copyInventory(def, VOID_MAGE_HELM);
            ItemDefinition.copyEquipment(def, VOID_MAGE_HELM);
            def.name = "<col=65280>Void mage helm";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24941) {
            ItemDefinition.copyInventory(def, VOID_MELEE_HELM);
            ItemDefinition.copyEquipment(def, VOID_MELEE_HELM);
            def.name = "<col=65280>Void melee helm";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24942) {
            ItemDefinition.copyInventory(def, ELITE_VOID_ROBE);
            ItemDefinition.copyEquipment(def, ELITE_VOID_ROBE);
            def.name = "<col=65280>Elite void robe";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24943) {
            ItemDefinition.copyInventory(def, ELITE_VOID_TOP);
            ItemDefinition.copyEquipment(def, ELITE_VOID_TOP);
            def.name = "<col=65280>Elite void top";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24944) {
            ItemDefinition.copyInventory(def, GRANITE_MAUL_24225);
            ItemDefinition.copyEquipment(def, GRANITE_MAUL_24225);
            def.name = "<col=65280>Granite maul";
            def.modelCustomColor4 = 23523;
        }

        if (id == 24945) {
            ItemDefinition.copyInventory(def, PARTYHAT__SPECS);
            ItemDefinition.copyEquipment(def, PARTYHAT__SPECS);
            def.name = "<col=65280>Partyhat & specs";
            def.modelCustomColor4 = 235;
        }

        if (id == 24946) {
            ItemDefinition.copyInventory(def, FREMENNIK_KILT);
            ItemDefinition.copyEquipment(def, FREMENNIK_KILT);
            def.name = "<col=65280>Fremennik kilt";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24947) {
            ItemDefinition.copyInventory(def, SPIKED_MANACLES);
            ItemDefinition.copyEquipment(def, SPIKED_MANACLES);
            def.name = "<col=65280>Spiked manacles";
            def.modelCustomColor4 = 222222;
        }

        if (id == 24948) {
            ItemDefinition.copyInventory(def, ABYSSAL_TENTACLE);
            ItemDefinition.copyEquipment(def, ABYSSAL_TENTACLE);
            def.name = "<col=65280>Abyssal tentacle";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24949) {
            def.name = "<col=65280>Dragon dagger(p++)";
            ItemDefinition.copyInventory(def, DRAGON_DAGGERP_5698);
            ItemDefinition.copyEquipment(def, DRAGON_DAGGERP_5698);
            def.modelCustomColor4 = 22459;
        }

        if (id == 18336) {
            def.name = "<col=65280>Dragon's partyhat";
            def.inventoryActions = new String[5];
            def.inventoryActions[1] = "Wear";
            def.zoom2d = 440;
            def.inventoryModel = 55602;
            def.animateInventory = true;
            def.xOffset2d = 1;
            def.yOffset2d = 1;
            def.yan2d = 1852;
            def.xan2d = 76;
            def.maleModel = 55603;
            def.femaleModel = 55604;
            def.recolorFrom = new int[]{926};
            def.recolorTo = new int[]{926};
            def.retextureFrom = new short[]{926};
            def.retextureTo = new short[]{54};
        }

        if (id == 24950) {
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

        if (id == 24951) {
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

        if (id == 24952) {
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

        if (id == 24953) {
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

        if (id == 24954) {
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

        if (id == 24955) {
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

        if (id == 24956) {
            ItemDefinition.copyInventory(def, ELDER_MAUL);
            ItemDefinition.copyEquipment(def, ELDER_MAUL);
            def.name = "<col=65280>Dark elder maul";
            def.modelCustomColor4 = 222200;
        }

        if (id == 28957) {
            ItemDefinition.copyInventory(def, TWISTED_BOW);
            ItemDefinition.copyEquipment(def, TWISTED_BOW);
            def.name = "<col=65280>Sanguine twisted bow";
            def.modelCustomColor4 = 235;
            def.stackable = 0;
        }

        if (id == 24958) {
            ItemDefinition.copyInventory(def, ELDER_MAUL);
            ItemDefinition.copyEquipment(def, ELDER_MAUL);
            def.name = "<col=65280>Dark elder maul";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24981) {
            ItemDefinition.copyInventory(def, 7807);
            ItemDefinition.copyEquipment(def, 7807);
            def.inventoryActions = new String[]{null, "Wield", null, "Dismantle", "Drop"};
            def.name = "<col=65280>Ancient warrior axe (c)";
            def.modelCustomColor3 = 23532;
        }

        if (id == 24982) {
            ItemDefinition.copyInventory(def, 7808);
            ItemDefinition.copyEquipment(def, 7808);
            def.inventoryActions = new String[]{null, "Wield", null, "Dismantle", "Drop"};
            def.name = "<col=65280>Ancient warrior maul (c)";
            def.modelCustomColor3 = 23532;
        }

        if (id == 24983) {
            ItemDefinition.copyInventory(def, 7806);
            ItemDefinition.copyEquipment(def, 7806);
            def.inventoryActions = new String[]{null, "Wield", null, "Dismantle", "Drop"};
            def.name = "<col=65280>Ancient warrior sword (c)";
            def.modelCustomColor3 = 23532;
        }

        if (id == 24984) {
            ItemDefinition.copyInventory(def, NEITIZNOT_FACEGUARD);
            ItemDefinition.copyEquipment(def, NEITIZNOT_FACEGUARD);
            def.name = "<col=65280>Ancient faceguard";
            def.modelCustomColor4 = 222200;
        }

        if (id == 24985) {
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

        if (id == 786) {
            def.name = "<col=65280>Gambler scroll";
            def.inventoryActions = new String[]{"Redeem", null, null, null, "Drop"};
        }

        if (id == 24986) {
            ItemDefinition.copyInventory(def, PRINCE_BLACK_DRAGON);
            def.name = "<col=65280>Ancient king black dragon pet";
            def.modelCustomColor4 = 235;
        }

        if (id == 24987) {
            ItemDefinition.copyInventory(def, CHAOS_ELEMENTAL);
            def.name = "<col=65280>Ancient chaos elemental pet";
            def.modelCustomColor4 = 235;
        }

        if (id == 24988) {
            ItemDefinition.copyInventory(def, BARRELCHEST_PET);
            def.name = "<col=65280>Ancient barrelchest pet";
            def.modelCustomColor4 = 235;
        }

        if (id == 24989) {
            ItemDefinition.copyInventory(def, ANCIENT_EMBLEM);
            def.name = "<col=65280>Dark ancient emblem";
            def.modelCustomColor4 = 235;
        }
        if (id == 24990) {
            ItemDefinition.copyInventory(def, ANCIENT_TOTEM);
            def.name = "<col=65280>Dark ancient totem";
            def.modelCustomColor4 = 235;
        }
        if (id == 24991) {
            ItemDefinition.copyInventory(def, ANCIENT_STATUETTE);
            def.name = "<col=65280>Dark ancient statuette";
            def.modelCustomColor4 = 235;
        }
        if (id == 24992) {
            ItemDefinition.copyInventory(def, ANCIENT_MEDALLION);
            def.name = "<col=65280>Dark ancient medallion";
            def.modelCustomColor4 = 235;
        }
        if (id == 24993) {
            ItemDefinition.copyInventory(def, ANCIENT_EFFIGY);
            def.name = "<col=65280>Dark ancient effigy";
            def.modelCustomColor4 = 235;
        }
        if (id == 24994) {
            ItemDefinition.copyInventory(def, ANCIENT_RELIC);
            def.name = "<col=65280>Dark ancient relic";
            def.modelCustomColor4 = 235;
        }
        if (id == 24995) {
            ItemDefinition.copyInventory(def, VESTAS_LONGSWORD);
            ItemDefinition.copyEquipment(def, VESTAS_LONGSWORD);
            def.name = "<col=65280>Ancient vesta's longsword";
            def.modelCustomColor4 = 235;
        }
        if (id == 24996) {
            ItemDefinition.copyInventory(def, STATIUSS_WARHAMMER);
            ItemDefinition.copyEquipment(def, STATIUSS_WARHAMMER);
            def.name = "<col=65280>Ancient statius's warhammer";
            def.modelCustomColor4 = 235;
        }

        if (id == 24999) {
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

        if (id == 28000) {
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

        if (id == 28001) {
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

        if (id == 28002) {
            ItemDefinition.copyInventory(def, INQUISITORS_GREAT_HELM);
            ItemDefinition.copyEquipment(def, INQUISITORS_GREAT_HELM);
            def.name = "<col=65280>Shadow great helm";
            def.modelCustomColor4 = 235;
        }

        if (id == 28003) {
            ItemDefinition.copyInventory(def, INQUISITORS_HAUBERK);
            ItemDefinition.copyEquipment(def, INQUISITORS_HAUBERK);
            def.name = "<col=65280>Shadow hauberk";
            def.modelCustomColor4 = 235;
        }

        if (id == 28004) {
            ItemDefinition.copyInventory(def, INQUISITORS_PLATESKIRT);
            ItemDefinition.copyEquipment(def, INQUISITORS_PLATESKIRT);
            def.name = "<col=65280>Shadow plateskirt";
            def.modelCustomColor4 = 235;
        }

        if (id == 28005) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Shadow inquisitor ornament kit";
            def.zoom2d = 1616;
            def.yan2d = 1943;
            def.xan2d = 564;
            def.xOffset2d = -10;
            def.yOffset2d = 20;
            def.inventoryModel = 31973;
            def.recolorTo = new int[]{10, 0, 1, 1, 1, 1, 1, 1, 1, 1};
            def.recolorFrom = new int[]{7607, 0, 908, 54162, 41137, 41149, 41143, 6998, 40107, 14734};
        }

        if (id == 28006) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Inquisitor's mace ornament kit";
            def.zoom2d = 1616;
            def.yan2d = 1943;
            def.xan2d = 564;
            def.xOffset2d = -10;
            def.yOffset2d = 20;
            def.inventoryModel = 55556;
            def.animateInventory = true;
            def.recolorTo = new int[]{10, 0, 1, 1, 1, 1, 1, 1, 1, 1};
            def.recolorFrom = new int[]{7607, 0, 908, 54162, 41137, 41149, 41143, 6998, 40107, 14734};
        }

        if (id == 28007) {
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

        if (id == 28008) {
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

        if (id == 28009) {
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

        if (id == 14479) {
            def.name = "<col=65280>Beginner weapon pack";
            def.inventoryModel = 20587;
            def.xOffset2d = 0;
            def.yOffset2d = 12;
            def.yan2d = 456;
            def.xan2d = 318;
            def.zoom2d = 2216;
            def.inventoryActions = new String[]{"Open", null, null, null, "Destroy"};
        }

        if (id == 14486) {
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

        if (id == 14487) {
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

        if (id == 14488) {
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

        if (id == 14489) {
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

        if (id == 28013) {
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

        if (id == 28014) {
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

        if (id == 28015) {
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

        if (id == 30180) {
            ItemDefinition.copyInventory(def, PEGASIAN_BOOTS);
            ItemDefinition.copyEquipment(def, PEGASIAN_BOOTS);
            def.name = "<col=65280>Pegasian boots (or)";
            def.recolorFrom = new int[]{8481, 17746, 15252, 16549, 8493, 17294};
            def.recolorTo = new int[]{7114, 7114, 15252, 7114, 7114, 17294};
        }

        if (id == 30182) {
            ItemDefinition.copyInventory(def, ETERNAL_BOOTS);
            ItemDefinition.copyEquipment(def, ETERNAL_BOOTS);
            def.name = "<col=65280>Eternal boots (or)";
            def.recolorFrom = new int[]{9152, -22242, -22326, -10839, -22248, 695, -22361, -22510};
            def.recolorTo = new int[]{9152, -22242, 7114, 7114, 7114, 695, 7114, -22510};
        }

        if (id == 29000) {
            ItemDefinition.copyInventory(def, VIGGORAS_CHAINMACE);
            ItemDefinition.copyEquipment(def, VIGGORAS_CHAINMACE);
            def.name = "<col=65280>Viggora's chainmace (c)";
            def.recolorFrom = new int[]{16, -11234, -11238, -11242, -11246, -10719};
            def.recolorTo = new int[]{16, 1255, 1255, 5, 5, 1255};
        }

        if (id == 29001) {
            ItemDefinition.copyInventory(def, CRAWS_BOW);
            ItemDefinition.copyEquipment(def, CRAWS_BOW);
            def.name = "<col=65280>Craw's bow (c)";
            def.recolorFrom = new int[]{-22242, -6087, -22440, 6602, 6699, -22448, 6736, -22225, 3346, -6099, 7124, 6709, -22431};
            def.recolorTo = new int[]{7, 1, 7, 1255, 10, 1255, 1255, 10, 5, 1255, 1255, 1255, 10};
        }

        if (id == 29002) {
            ItemDefinition.copyInventory(def, THAMMARONS_SCEPTRE);
            ItemDefinition.copyEquipment(def, THAMMARONS_SCEPTRE);
            def.name = "<col=65280>Thammaron's sceptre (c)";
            def.recolorFrom = new int[]{960, 33, 417, 20, 53, 555, 939, 12, 28, 284};
            def.recolorTo = new int[]{10, 15, 1, 15, 1400, 5, 1, 10, 1400, 1400};
        }

        if (id == 2396) {
            def.name = "<col=65280>3% drop rate boost scroll";
        }

        if (id == 13215) {
            ItemDefinition.copyInventory(def, 13204);
            def.name = "<col=65280>Bloody Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{947, 948, 949};
            def.countco = new int[]{2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
            def.countobj = new int[]{13216, 13217, 13218, 13218, 0, 0, 0, 0, 0, 0};
        }

        if (id == 13216) {
            ItemDefinition.copyInventory(def, 3985);
            def.name = "<col=65280>Bloody Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{947, 948, 949};
        }

        if (id == 13217) {
            ItemDefinition.copyInventory(def, 3987);
            def.name = "<col=65280>Bloody Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{947, 948, 949};
        }

        if (id == 13218) {
            ItemDefinition.copyInventory(def, 3989);
            def.name = "<col=65280>Bloody Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{947, 948, 949};
        }

        if (id == ItemIdentifiers.TOXIC_BLOWPIPE || id == ItemIdentifiers.SERPENTINE_HELM || id == ItemIdentifiers.TRIDENT_OF_THE_SWAMP || id == ItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD
            || id == ItemIdentifiers.TOME_OF_FIRE || id == ItemIdentifiers.SCYTHE_OF_VITUR || id == ItemIdentifiers.SANGUINESTI_STAFF || id == ItemIdentifiers.CRAWS_BOW
            || id == ItemIdentifiers.VIGGORAS_CHAINMACE || id == ItemIdentifiers.THAMMARONS_SCEPTRE || id == ItemIdentifiers.TRIDENT_OF_THE_SEAS || id == ItemIdentifiers.MAGMA_HELM
            || id == ItemIdentifiers.TANZANITE_HELM || id == ItemIdentifiers.DRAGONFIRE_SHIELD || id == ItemIdentifiers.DRAGONFIRE_WARD || id == ItemIdentifiers.ANCIENT_WYVERN_SHIELD
            || id == ItemIdentifiers.ABYSSAL_TENTACLE || id == ItemIdentifiers.BARRELCHEST_ANCHOR || id == ItemIdentifiers.SARADOMINS_BLESSED_SWORD) {
            def.inventoryActions = new String[]{null, "Wield", null, null, "Drop"};
        }

        if (id == 16167) {
            ItemDefinition.copyInventory(def, 24731);
            ItemDefinition.copyEquipment(def, 24731);
            def.name = "<col=65280>Ring of sorcery";
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            def.modelCustomColor4 = 235;
        }

        if (id == 16168) {
            def.name = "<col=65280>Ring of Precision";
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            ItemDefinition.copyInventory(def, 12601);
            ItemDefinition.copyEquipment(def, 12601);
            def.modelCustomColor4 = 33785;
        }

        if (id == 16169) {
            def.name = "<col=65280>Ring of Trinity";
            def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
            ItemDefinition.copyInventory(def, 12603);
            ItemDefinition.copyEquipment(def, 12603);
            def.modelCustomColor4 = 3433;
        }

        if (id == 16172) {
            def.name = "<col=65280>Baby Aragog";
            ItemDefinition.copyInventory(def, VENENATIS_SPIDERLING);
            def.recolorFrom = new int[]{912, 0, 916, 103, 138, 794, 107, 908};
            def.recolorTo = new int[]{138, 908, 4769, 4769, 4769, 0, 0, 0};
        }

        if (id == 16173) {
            def.name = "<col=65280>Jawa pet";
            ItemDefinition.copyInventory(def, ATTACK_HOOD);
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.modelCustomColor = 4769;
        }

        if (id == 21205) {
            ItemDefinition.copyInventory(def, 21003);
            ItemDefinition.copyEquipment(def, 21003);
            def.name = "<col=65280>Elder maul";
            def.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
            def.recolorFrom = new int[]{5056, 8125};
            def.recolorTo = new int[]{685, 924};
        }

        if (id == 16020) {
            def.name = "<col=65280>Dharok pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.zoom2d = 1000;
            def.xan2d = 100;
            def.yan2d = 100;
            def.xOffset2d = 0;
            def.yOffset2d = 100;
            NpcDefinition npcInstance = NpcDefinition.get(NpcIdentifiers.DHAROK_THE_WRETCHED);
            def.inventoryModel = npcInstance.models[0];
        }

        if (id == ItemIdentifiers.ATTACKER_ICON || id == ItemIdentifiers.COLLECTOR_ICON || id == ItemIdentifiers.DEFENDER_ICON || id == ItemIdentifiers.HEALER_ICON || id == ItemIdentifiers.AMULET_OF_FURY_OR || id == ItemIdentifiers.OCCULT_NECKLACE_OR || id == ItemIdentifiers.NECKLACE_OF_ANGUISH_OR || id == ItemIdentifiers.AMULET_OF_TORTURE_OR || id == ItemIdentifiers.BERSERKER_NECKLACE_OR || id == ItemIdentifiers.TORMENTED_BRACELET_OR || id == ItemIdentifiers.DRAGON_DEFENDER_T || id == ItemIdentifiers.DRAGON_BOOTS_G) {
            def.inventoryActions = new String[]{null, "Wear", null, null, "Destroy"};
        }

        if (id == 2944) {
            def.name = "<col=65280>Key of drops";
        }

        if (id == 24445) {
            def.name = "<col=65280>Twisted slayer helmet (i)";
            def.femaleHeadModel = 38997;
            def.femaleModel = 38970;
            def.inventoryActions = new String[]{null, "Wear", null, "Disassemble", "Drop"};
            def.inventoryModel = 38958;
            def.maleHeadModel = 38997;
            def.maleModel = 38960;
            def.xOffset2d = -4;
            def.yOffset2d = -3;
            def.xan2d = 30;
            def.yan2d = 1773;
            def.zoom2d = 779;
            def.recolorFrom = new int[]{16, 14272, 33, 10306, 37, 4550, 10343, 24, 10312, 12, 10334, 10318};
            def.recolorTo = new int[]{8, 6073, 8, 10306, 8, 4550, 10343, 8, 10312, 8, 10334, 10318};
        }

        if (id == ItemIdentifiers.BANK_KEY) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
        }

        if (id == 20238) {
            def.name = "<col=65280>Imbuement scroll";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.description = "Use this scroll to imbue certain items.";
        }

        if (id == 12646) {
            def.modelCustomColor4 = 33785;
            def.name = "<col=65280>Niffler pet";
        }

        if (id == 20693) {
            def.name = "<col=65280>Fawkes pet";
        }

        if (id == 28663) {
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

        if (id == 28639) {
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

        if (id == 28640) {
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

        if (id == 28641) {
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

        if (id == 28642) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Fluffy Jr pet";
            def.recolorFrom = new int[]{0, 11200, 929, 931, 9542, 902, 262, 906, 910, 914, 918, 922, 955, 9149, 7101, 8125, 6077, 4029, 957, 1981, 926};
            def.recolorTo = new int[]{4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 0, 4769, 0, 0, 4769, 4769};
            def.inventoryModel = 29240;
            def.xan2d = 3;
            def.yan2d = 213;
            def.zoom2d = 1616;
        }

        if (id == 30338) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Centaur pet";
            def.inventoryModel = 16213;
            def.zoom2d = 3500;
            def.xan2d = 25;
            def.yan2d = 25;
            def.yOffset2d = -10;
        }

        if (id == 30016) {
            def.name = "<col=65280>Founder imp";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 58916;
            def.xOffset2d = 11;
            def.yOffset2d = -1;
            def.xan2d = 116;
            def.yan2d = 1778;
            def.zoom2d = 1424;
        }

        if (id == 30018) {
            def.name = "<col=65280>Corrupted nechryarch pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 58922;
            def.zoom2d = 7000;
            def.xan2d = 25;
            def.yan2d = 25;
            def.yOffset2d = -10;
        }

        if (id == 30033) {
            def.name = "<col=65280>Mini necromancer";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 58979;
            def.yOffset2d = -12;
            def.xan2d = 118;
            def.yan2d = 10;
            def.zoom2d = 1136;
        }

        if (id == 30048) {
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

        if (id == 21291) {
            def.name = "<col=65280>Jal-nib-rek pet";
        }

        if (id == 23757) {
            def.name = "<col=65280>Yougnleff pet";
        }

        if (id == 23759) {
            def.name = "<col=65280>Corrupted yougnleff pet";
        }

        if (id == 8788) {
            def.name = "<col=65280>Corrupting stone";
            def.recolorFrom = new int[]{-22297, 127};
            def.recolorTo = new int[]{945, 582};
        }

        if (id == 30044) {
            def.name = "<col=65280>Jaltok-jad pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 33012;
            def.xOffset2d = -3;
            def.yOffset2d = -30;
            def.yan2d = 553;
            def.zoom2d = 12000;
        }

        if (id == 30131) {
            def.name = "<col=65280>Baby lava dragon";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 58995;
            def.xOffset2d = -97;
            def.yOffset2d = 9;
            def.xan2d = 83;
            def.yan2d = 1826;
            def.zoom2d = 2541;
        }

        if (id == 16171) {
            def.name = "<col=65280>Wampa pet";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 21802;
            def.zoom2d = 4380;
        }

        if (id == 30340) {
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.name = "<col=65280>Centaur pet";
            def.inventoryModel = 16212;
            def.zoom2d = 2300;
            def.xan2d = 25;
            def.yan2d = 25;
            def.yOffset2d = -10;
        }

        if (id == 30342) {
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

        if (id == 28643) {
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

        if (id == 10858) {
            def.name = "<col=65280>Sword of gryffindor";
        }

        if (id == 12873 || id == 12875 || id == 12877 || id == 12879 || id == 12881 || id == 12883 || id == 6759 || id == DWARF_CANNON_SET) {
            def.inventoryActions = new String[5];
            def.inventoryActions[0] = "Open";
        }

        if (id == 13188) {
            ItemDefinition.copyInventory(def, 13652);
            def.name = "<col=65280>Dragon claws (or)";
            def.recolorFrom = new int[]{929, 914, 918, 922};
            def.recolorTo = new int[]{929, 7114, 7114, 7114};
        }

        if (id == 12791) {
            def.inventoryActions = new String[]{"Open", null, null, "Empty", "Drop"};
        }

        if (id == 23650) {
            ItemDefinition.copyInventory(def, 12791);
            def.inventoryActions = new String[]{"Open", null, null, "Empty", "Destroy"};
            def.name = "<col=65280>Rune pouch (i)";
            def.ambient = 120;
        }

        if (id == 14500) {
            ItemDefinition.copyInventory(def, 23650);
            def.name = "<col=65280>Rune pouch (i) (broken)";
            def.ambient = -25;
            def.inventoryActions = new String[]{null, null, null, null, "Destroy"};
        }

        if (id == 14501) {
            ItemDefinition.copyInventory(def, 12436);
            def.name = "<col=65280>Amulet of fury (or) (broken)";
            def.ambient = -25;
            def.inventoryActions = new String[]{null, null, null, null, "Destroy"};
        }

        if (id == 14502) {
            ItemDefinition.copyInventory(def, 19720);
            def.name = "<col=65280>Occult necklace (or) (broken)";
            def.ambient = -25;
            def.inventoryActions = new String[]{null, null, null, null, "Destroy"};
        }

        if (id == 14503) {
            ItemDefinition.copyInventory(def, 20366);
            def.name = "<col=65280>Amulet of torture (or) (broken)";
            def.ambient = -25;
            def.inventoryActions = new String[]{null, null, null, null, "Destroy"};
        }

        if (id == 14504) {
            ItemDefinition.copyInventory(def, 22249);
            def.name = "<col=65280>Necklace of anguish (or) (broken)";
            def.ambient = -25;
            def.inventoryActions = new String[]{null, null, null, null, "Destroy"};
        }

        if (id == 14505) {
            ItemDefinition.copyInventory(def, 23444);
            def.name = "<col=65280>Tormented bracelet (or) (broken)";
            def.ambient = -25;
            def.inventoryActions = new String[]{null, null, null, null, "Destroy"};
        }

        if (id == 14506) {
            ItemDefinition.copyInventory(def, 19722);
            def.name = "<col=65280>Dragon defender (t) (broken)";
            def.ambient = -25;
            def.inventoryActions = new String[]{null, null, null, null, "Destroy"};
        }

        if (id == 14507) {
            ItemDefinition.copyInventory(def, 22234);
            def.name = "<col=65280>Dragon boots (g) (broken)";
            def.ambient = -25;
            def.inventoryActions = new String[]{null, null, null, null, "Destroy"};
        }

        if (id == 4447) {
            def.name = "<col=65280>Double drops lamp";
            def.description = "Receive double drops when killing bosses. (for 1 hour)";
        }

        if (id == 7956) {
            ItemDefinition.copyInventory(def, ItemIdentifiers.CASKET);
            def.name = "<col=65280>Small blood money casket";
            def.recolorFrom = new int[]{13248, 7062, -22477};
            def.recolorTo = new int[]{7114, 929, 929};
        }

        if (id == 13302) {
            def.name = "<col=65280><col=65280>Wilderness key</col>";
        }

        if (id == 6542) {
            def.name = "<col=65280>Present Mystery Box";
        }

        if (id == 30189) {
            def.name = "<col=65280>Points mystery chest";
            def.inventoryActions = new String[]{"Open", null, null, null, null};
            def.recolorFrom = new int[]{49, 6466, 24, 5545};
            def.recolorTo = new int[]{49, 302770, 24, 296770};
            def.inventoryModel = 12146;
            def.xan2d = 114;
            def.yan2d = 1883;
            def.zoom2d = 2640;
        }

        if (id == 30190) {
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

        if (id == 30191) {
            ItemDefinition.copyInventory(def, 13204);
            def.name = "<col=65280>X'mas token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{127, 155, 374770};
            def.countco = new int[]{2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
            def.countobj = new int[]{30192, 30193, 30194, 30194, 0, 0, 0, 0, 0, 0};
        }

        if (id == 30192) {
            ItemDefinition.copyInventory(def, 3985);
            def.name = "<col=65280>X'mas Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{127, 155, 374770};
        }

        if (id == 30193) {
            ItemDefinition.copyInventory(def, 3987);
            def.name = "<col=65280>X'mas Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{127, 155, 374770};
        }

        if (id == 30194) {
            ItemDefinition.copyInventory(def, 3989);
            def.name = "<col=65280>X'mas Token";
            def.recolorFrom = new int[]{5813, 9139, 26006};
            def.recolorTo = new int[]{127, 155, 374770};
        }

        if (id == 30195) {
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

        if (id == 30201) {
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

        if(id == 30210) {
            def.name = "<col=65280>Collection key";
            def.inventoryActions = new String[]{null, null, null, null, null};
            def.inventoryModel = 55612;
            def.xOffset2d = 1;
            def.yOffset2d = 5;
            def.xan2d = 471;
            def.yan2d = 20;
            def.zoom2d = 1296;
        }

        if(id == 30211) {
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

        if (id == 30185 || id == 30186 || id == 30187 || id == 30188 || id == 30200) {
            ItemDefinition.copyInventory(def, 6199);
            switch (id) {
                case 30185:
                    def.name = "<col=65280>Donator mystery box";
                    def.inventoryModel = 55566;
                    def.animateInventory = true;
                    def.recolorFrom = new int[]{2999, 22410};
                    def.recolorTo = new int[]{524, 13};
                    break;

                case 30200:
                    def.name = "<col=65280>Raids mystery box";
                    def.inventoryModel = 55610;
                    def.animateInventory = true;
                    def.recolorFrom = new int[]{2999, 22410};
                    def.recolorTo = new int[]{524, 13};
                    break;

                case 30186:
                    def.name = "<col=65280>Point mystery box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{302770, 296770};
                    break;

                case 30187:
                    def.note = 30186;
                    def.notedTemplate = 799;
                    def.stackable = 1;
                    break;

                case 30188:
                    def.name = "<col=65280>Barrows Mystery Box";
                    def.recolorFrom = new int[]{22410, 2999};
                    def.recolorTo = new int[]{356770, 266770};
                    break;
            }
        }

        if (id == 10858) {
            def.recolorFrom = new int[]{10258, 10291, 10275, 10262, 10266, 10283};
            def.recolorTo = new int[]{82, 125, 125, 121, 125, 125};
        }

        if (id == 3269) {
            def.recolorFrom = new int[]{57, 49};
            def.recolorTo = new int[]{926, 9152};
            def.animateInventory = true;
        }

        if (id == 962) {
            def.inventoryActions = new String[]{"Open", null, null, null, null};
        }

        if (id == 6722) {
            def.name = "<col=65280>Zombies champion pet";
            def.inventoryActions = new String[]{null, null, null, null, null};
        }

        if (id == 23818) {
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

        if (id == 29102) {
            def.name = "<col=65280>Scythe of vitur kit";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 12841;
            def.xOffset2d = -10;
            def.yOffset2d = 20;
            def.xan2d = 564;
            def.yan2d = 1943;
            def.zoom2d = 1616;
            def.modelCustomColor4 = 235;
        }

        if (id == 29103) {
            def.name = "<col=65280>Twisted bow kit";
            def.inventoryActions = new String[]{null, null, null, null, "Drop"};
            def.inventoryModel = 12841;
            def.xOffset2d = -10;
            def.yOffset2d = 20;
            def.xan2d = 564;
            def.yan2d = 1943;
            def.zoom2d = 1616;
            def.modelCustomColor4 = 31575;
        }

        switch (id) {

            case 30074:
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

            case 30077:
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

            case 30080:
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

            case 30038:
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

            case 30297:
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

            case 30181:
            case 30184:
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

            case 2572:
                ItemDefinition.copyInventory(def, RING_OF_WEALTH_1);
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                break;

            case 16012:
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

            case 16013:
                ItemDefinition.copyInventory(def, 12649);
                def.name = "<col=65280>Pet kree'arra (white)";
                def.inventoryActions = new String[]{null, null, null, "Wipe-off paint", null};
                def.modelCustomColor4 = 31575;
                break;

            case 16014:
                ItemDefinition.copyInventory(def, 12651);
                def.name = "<col=65280>Pet zilyana (white)";
                def.inventoryActions = new String[]{null, null, null, "Wipe-off paint", null};
                def.modelCustomColor4 = 33785;
                break;

            case 16015:
                ItemDefinition.copyInventory(def, 12650);
                def.name = "<col=65280>Pet general graardor (black)";
                def.inventoryActions = new String[]{null, null, null, "Wipe-off paint", null};
                def.modelCustomColor4 = 235;
                break;

            case 16016:
                ItemDefinition.copyInventory(def, 12652);
                def.name = "<col=65280>Pet k'ril tsutsaroth (black)";
                def.inventoryActions = new String[]{null, null, null, "Wipe-off paint", null};
                def.modelCustomColor4 = 235;
                break;

            case 16024:
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

            case 964:
                def.inventoryActions = new String[]{"Cast", null, null, null, "Destroy"};
                def.name = "<col=65280>Vengeance";
                def.description = "Rebound damage to an opponent.";
                def.recolorFrom = new int[]{5231};
                def.recolorTo = new int[]{130770};
                break;

            case 2685:
                def.name = "<col=65280>PvP task scroll";
                def.inventoryActions = new String[]{"Read", null, "Skip task", null, "Destroy"};
                def.recolorFrom = new int[]{6464, 6608, 22305, 22034, 6740, 22422, 6583, 6587, 6604};
                def.recolorTo = new int[]{933, 926, 926, 926, 933, 926, 926, 926, 933};
                break;

            case 16755:
                ItemDefinition.copyInventory(def, 13648);
                def.name = "<col=65280>PvP task bottle";
                def.ambient = 20;
                def.recolorFrom = new int[]{22422};
                def.recolorTo = new int[]{933};
                def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
                break;

            case 14524:
                ItemDefinition.copyInventory(def, 8151);
                def.name = "<col=65280>Blood money chest";
                def.description = "Opens for 10.000 blood money.";
                def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
                def.recolorFrom = new int[]{24, 49, 4510, 4502, 8128, 7093};
                def.recolorTo = new int[]{926, 926, 926, 926, 926, 926};
                break;

            case 14525:
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

            case 7237:
                def.name = "<col=65280>PvP reward casket";
                def.recolorFrom = new int[]{13248, 7062, -22477};
                def.recolorTo = new int[]{13248, 563, -22477};
                def.inventoryActions = new String[]{"Open", null, null, null, "Drop"};
                break;

            case 16005:
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

            case 7999:
                def.modelCustomColor4 = 155;
                def.name = "<col=65280>Pet paint (black)";
                def.description = "Changes color of baby K'ril and baby Graardor.";
                break;

            case 8000:
                def.modelCustomColor4 = 2;
                def.name = "<col=65280>Pet paint (white)";
                def.description = "Changes color of baby Kree'Arra and baby Zilyana.";
                break;

            case 14222:
                def.name = "<col=65280>X2 PK Points (+30)";
                def.zoom2d = 1020;
                def.xan2d = 344;
                def.yan2d = 656;
                def.xOffset2d = -1;
                def.yOffset2d = 11;
                def.inventoryModel = 10347;
                def.inventoryActions[1] = "Claim";
                def.stackable = 1;
                def.description = "+30 minutes of 2x PkP.";
                break;

            case 13190:
                def.name = "<col=65280>$5 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                break;

            case 16278:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$10 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.recolorFrom = new int[]{20416, 22451, 11224, 22181, 22449, 22305, 21435, 9164, 11092, 9152, 7087, 32821, 32846, 7997, 8117, 32829, 32838, 22464};
                def.recolorTo = new int[]{32416, 34451, 23224, 34181, 34449, 34305, 33435, 21164, 23092, 21152, 19087, 44821, 44846, 19997, 20117, 44829, 44838, 34464};
                break;

            case 16263:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$20 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.recolorFrom = new int[]{20416, 22451, 11224, 22181, 22449, 22305, 21435, 9164, 11092, 9152, 7087, 32821, 32846, 7997, 8117, 32829, 32838, 22464};
                def.recolorTo = new int[]{63316, 65351, 54124, 65081, 65349, 65205, 64335, 52064, 53992, 52052, 49987, 75721, 75746, 50897, 51017, 75729, 75738, 65364};
                break;

            case 16264:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$40 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.recolorFrom = new int[]{20416, 22451, 11224, 22181, 22449, 22305, 21435, 9164, 11092, 9152, 7087, 32821, 32846, 7997, 8117, 32829, 32838, 22464};
                def.recolorTo = new int[]{25416, 27451, 16224, 27181, 27449, 27305, 26435, 14164, 16092, 14152, 12087, 37821, 37846, 12997, 13117, 37829, 37838, 27464};
                break;

            case 16265:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$50 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.recolorFrom = new int[]{20416, 22451, 11224, 22181, 22449, 22305, 21435, 9164, 11092, 9152, 7087, 32821, 32846, 7997, 8117, 32829, 32838, 22464};
                def.recolorTo = new int[]{35416, 37451, 26224, 37181, 37449, 37305, 36435, 24164, 26092, 24152, 22087, 47821, 47846, 22997, 23117, 47829, 47838, 37464};
                break;

            case 16266:
                ItemDefinition.copyInventory(def, 13190);
                def.name = "<col=65280>$100 bond";
                def.inventoryActions = new String[]{"Redeem", null, null, null, null};
                def.recolorFrom = new int[]{20416, 22451, 11224, 22181, 22449, 22305, 21435, 9164, 11092, 9152, 7087, 32821, 32846, 7997, 8117, 32829, 32838, 22464};
                def.recolorTo = new int[]{77316, 79351, 68124, 79081, 79349, 79205, 78335, 66064, 67992, 66052, 63987, 89721, 89746, 64897, 65017, 89729, 89738, 79364};
                break;

            case 2866:
                def.name = "<col=65280>Earth arrows";
                break;

            case 4160:
                def.name = "<col=65280>Fire arrows";
                def.recolorFrom = new int[]{57, 61, 5012, 926};
                def.recolorTo = new int[]{57, 61, 5012, 926};
                break;

            case 7806:
                def.name = "<col=65280>Ancient warrior sword";
                def.recolorFrom = new int[]{920, 0, 103};
                def.recolorTo = new int[]{391770, 0, 110};
                break;

            case 7808:
                def.name = "<col=65280>Ancient warrior maul";
                def.recolorFrom = new int[]{78, 103, 920};
                def.recolorTo = new int[]{391470, 391470, 100, 100};
                break;

            case 7807:
                def.name = "<col=65280>Ancient warrior axe";
                def.recolorFrom = new int[]{0, 78, 920};
                def.recolorTo = new int[]{191770, 191770, 110};
                break;

            case 17000:
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

            case 2944:
                def.modelCustomColor3 = 54325;
                break;

            case 12773:
                def.name = "<col=65280>Lava whip";
                break;

            case 12774:
                def.name = "<col=65280>Frost whip";
                break;

            case 10586:
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                def.name = "<col=65280>Genie pet";
                break;

            case 12102:
                def.name = "<col=65280>Grim Reaper pet";
                def.zoom2d = 1010;
                def.xan2d = 0;
                def.yan2d = 0;
                def.xOffset2d = 1;
                def.yOffset2d = 79;
                def.inventoryModel = 5100;
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                break;

            case 32102:
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

            case 12081:
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

            case 12082:
                ItemDefinition.copyInventory(def, 12373);
                ItemDefinition.copyEquipment(def, 12373);
                def.name = "<col=65280>Elemental staff";
                def.recolorTo = new int[]{311770, 347770, 347770, 37};
                def.recolorFrom = new int[]{924, 0, 43164, 37};
                break;

            case 4067:
                def.name = "<col=65280>Donator ticket";
                break;

            case 619:
                def.name = "<col=65280>Vote ticket";
                def.stackable = 1;
                def.inventoryActions = new String[]{"Convert to Points", null, null, null, "Drop"};
                break;

            case 18335:
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

            case 22517:
                def.name = "<col=65280>Saeldor shard";
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                break;

            case 25600:
                def.name = "<col=65280>Ranger gloves";
                def.inventoryModel = 24569;
                def.recolorFrom = new int[]{14658, 14649, 14645, 14637, 16536, 13716};
                def.recolorTo = new int[]{43059, 43055, 43051, 43047, 43030, 43030};
                def.zoom2d = 917;
                def.xan2d = 408;
                def.yan2d = 150;
                def.xOffset2d = 0;
                def.stackable = 0;
                def.inventoryActions = new String[]{null, "Wear", null, null, "Drop"};
                def.maleModel = 55558;
                def.femaleModel = 29056;
                break;

            case 27644:
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

            case 3269:
                def.name = "<col=65280>Slayer key";
                def.inventoryModel = 55565;
                def.recolorFrom = new int[]{57, 49};
                def.recolorTo = new int[]{6453, 6569};
                def.zoom2d = 860;
                def.xan2d = 460;
                def.yan2d = 20;
                def.xOffset2d = -9;
                def.yOffset2d = -2;
                def.stackable = 1;
                break;
            case 24670:
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                break;

            case 30196:
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

            case 30199:
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

            case 30202:
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

            case 30000:
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

            case 30002:
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

            case 30004:
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

            case 30036:
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

            case 30037:
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

            case 30020:
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

            case 30021:
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

            case 30022:
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

            case 30104:
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

            case 30219:
                def.name = "<col=65280>Summer token";
                def.inventoryActions = new String[]{null, null, null, null, "Drop"};
                def.inventoryModel = 59047;
                def.xan2d = 468;
                def.yan2d = 56;
                def.yOffset2d = 6;
                def.zoom2d = 450;
                break;

            case 30280:
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

            case 30282:
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

            case 30284:
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

            case 30286:
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

            case 30288:
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

            case 30290:
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

            case 30292:
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

            case 30294:
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

            case 30246:
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

            case 30248:
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

            case 30298:
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

            case 30296:
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

            case 30254:
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

            case 30256:
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

            case 30258:
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

            case 30260:
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

            case 30262:
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

            case 30264:
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

            case 30266:
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

            case 30268:
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

            case 30270:
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

            case 30272:
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

            case 30274:
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

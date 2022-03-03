package com.valinor.cache.def.impl.npcs;

import com.valinor.cache.def.NpcDefinition;
import com.valinor.util.NpcIdentifiers;

import static com.valinor.util.CustomNpcIdentifiers.*;
import static com.valinor.util.NpcIdentifiers.ICELORD;

/**
 * @author Patrick van Elderen | April, 07, 2021, 15) {49
 * @see <a href="https) {//www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class CustomBosses {

    public static void unpack(int id) {
        NpcDefinition definition = NpcDefinition.get(id);

        if(id == CHAOTIC_NIGHTMARE) {
            NpcDefinition.copy(definition, NpcIdentifiers.THE_NIGHTMARE_9425);
            //definition.modelId = new int[] {55625};
            definition.modelCustomColor4 = 100;
        }

        if(id == ENRAGED_GORILLA_MAGIC) {
            NpcDefinition.copy(definition, NpcIdentifiers.DEMONIC_GORILLA_7146);
            definition.name = "Enraged gorilla";
            definition.modelCustomColor4 = 100;
        }

        if(id == ENRAGED_GORILLA_RANGE) {
            NpcDefinition.copy(definition, NpcIdentifiers.DEMONIC_GORILLA_7145);
            definition.name = "Enraged gorilla";
            definition.modelCustomColor4 = 100;
        }

        if(id == ENRAGED_GORILLA_MELEE) {
            NpcDefinition.copy(definition, NpcIdentifiers.DEMONIC_GORILLA);
            definition.name = "Enraged gorilla";
            definition.modelCustomColor4 = 100;
        }

        if(id == BLOOD_FURY_HESPORI || id == BLOOD_FURY_HESPORI_15022) {
            definition.name = "Bloodfury hespori";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.combatLevel = 833;
            definition.modelId = new int[] {55621};
            definition.widthScale = 128;
            definition.heightScale = 128;
            definition.standingAnimation = 8222;
            definition.size = 3;
            definition.walkingAnimation = 8222;
        }

        if(id == INFERNAL_SPIDER || id == INFERNAL_SPIDER_15031) {
            definition.name = "Infernal spider";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.combatLevel = 655;
            definition.modelId = new int[] {55619, 55620};
            definition.widthScale = 190;
            definition.heightScale = 190;
            definition.standingAnimation = 5318;
            definition.size = 4;
            definition.walkingAnimation = 5317;
        }

        if(id == SNOWFLAKE_BOSS) {
            definition.name = "Snowflake";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.combatLevel = 1027;
            definition.modelId = new int[] {35835};
            definition.additionalModels = new int[] {35839};
            definition.widthScale = 180;
            definition.heightScale = 180;
            definition.standingAnimation = 8154;
            definition.size = 2;
            definition.walkingAnimation = 8151;
        }

        if(id == ICELORD) {
            definition.name = "Wampa";
            definition.combatLevel = 981;
            definition.modelId = new int[] {21804,21801};
            definition.standingAnimation = 5722;
            definition.size = 2;
            definition.drawMapDot = true;
            definition.walkingAnimation = 5721;
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.widthScale = 180;
            definition.heightScale = 180;
        }

        if (id == GRIM) {
            definition.name = "Grim";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.combatLevel = 1322;
            definition.recolorFrom = new int[] {10004,25238,8741,4550,908,7073};
            definition.recolorTo = new int[] {5231,0,0,5353,0,8084};
            definition.modelId = new int[] {5100,292,170,179,256,507};
            definition.drawMapDot = true;
            definition.rotate180Animation = 820;
            definition.rotate90LeftAnimation = 822;
            definition.rotate90RightAnimation = 821;
            definition.standingAnimation = 847;
            definition.size = 3;
            definition.walkingAnimation = 819;
            definition.widthScale = 250;
            definition.heightScale = 250;
        }

        if (id == BRUTAL_LAVA_DRAGON_FLYING) {
            definition.name = "Brutal Lava Dragon";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.combatLevel = 420;
            definition.modelId = new int[]{58995, 58995, 58994, 58996};
            definition.widthScale = 170;
            definition.heightScale = 170;
            definition.standingAnimation = 7870;
            definition.size = 7;
            definition.walkingAnimation = 7870;
        }

        if (id == BRUTAL_LAVA_DRAGON) {
            definition.name = "Brutal Lava Dragon";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.combatLevel = 420;
            definition.modelId = new int[]{58995, 58995, 58994, 58996};
            definition.widthScale = 170;
            definition.heightScale = 170;
            definition.standingAnimation = 90;
            definition.size = 7;
            definition.walkingAnimation = 79;
        }

        if (id == CORRUPTED_NECHRYARCH) {
            definition.name = "Corrupted Nechryarch";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.combatLevel = 300;
            definition.modelId = new int[]{58922};
            definition.standingAnimation = 4650;
            definition.size = 2;
            definition.walkingAnimation = 6372;
        }

        if (id == NECROMANCER) {
            definition.name = "Necromancer";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.recolorFrom = new int[]{-26527, -24618, -26073, 5018, 61, 10351, 33, 24};
            definition.recolorTo = new int[]{-19054, 12, 12, -16870, 11177, 61, 16, 12};
            definition.combatLevel = 300;
            definition.modelId = new int[]{4953, 4955, 556, 58948, 58907, 58950, 58953, 58956};
            definition.widthScale = 160;
            definition.heightScale = 160;
            definition.standingAnimation = 808;
            definition.size = 2;
            definition.walkingAnimation = 819;
        }

        if (id == ARAGOG) {
            definition.name = "Aragog";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.recolorFrom = new int[]{138, 908, 794, 912, 916, 0, 103, 107};
            definition.recolorTo = new int[]{138, 908, 4769, 4769, 4769, 0, 0, 0};
            definition.combatLevel = 1123;
            definition.modelId = new int[]{28294, 28295};
            definition.widthScale = 190;
            definition.heightScale = 190;
            definition.standingAnimation = 5318;
            definition.size = 4;
            definition.walkingAnimation = 5317;
        }

        if (id == FLUFFY) {
            definition.name = "Fluffy";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.recolorFrom = new int[]{929, 960, 1981, 0, 931, 4029, 926, 902, 922, 918, 924, 904, 916, 912, 935, 939, 906, 920, 955, 910, 914, 7101, 11200, 957, 9149, 908, 4, 5053, 8125, 6069};
            definition.recolorTo = new int[]{4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 0, 4769, 0, 4769, 0, 0, 4769, 4769};
            definition.combatLevel = 636;
            definition.modelId = new int[]{29270};
            definition.standingAnimation = 4484;
            definition.size = 5;
            definition.walkingAnimation = 4488;
            definition.widthScale = 100;
            definition.heightScale = 100;
        }

        if (id == DEMENTOR) {
            definition.name = "Dementor";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.ambient = 20;
            definition.recolorFrom = new int[]{10343, -22250, -22365, -22361, -22353, -22464, -22477, -22456, -22473, -22452};
            definition.recolorTo = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            definition.combatLevel = 126;
            definition.contrast = 20;
            definition.modelId = new int[]{21154};
            definition.additionalModels = new int[]{21394};
            definition.standingAnimation = 5538;
            definition.walkingAnimation = 5539;
        }

        if (id == MALE_CENTAUR) {
            definition.name = "Centaur";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.combatLevel = 126;
            definition.modelId = new int[]{16196, 16202, 16199, 16200};
            definition.additionalModels = new int[]{16213};
            definition.standingAnimation = 4311;
            definition.size = 2;
            definition.walkingAnimation = 4310;
        }

        if (id == FEMALE_CENTAUR) {
            definition.name = "Centaur";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.combatLevel = 126;
            definition.modelId = new int[]{16195, 16201, 16198, 16197, 16200};
            definition.additionalModels = new int[]{16212, 16211};
            definition.standingAnimation = 4311;
            definition.size = 2;
            definition.walkingAnimation = 4310;
        }

        if (id == HUNGARIAN_HORNTAIL) {
            definition.name = "Hungarian horntail";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.ambient = 30;
            definition.recolorFrom = new int[]{0, 30635, 29390, 29526, 31271, 31393, 31151, 32200, 31192, 127};
            definition.recolorTo = new int[]{5662, 127, 5662, 5662, 5662, 5662, 5662, 5662, 127, 5662};
            definition.combatLevel = 172;
            definition.modelId = new int[]{38610};
            definition.widthScale = 110;
            definition.heightScale = 110;
            definition.standingAnimation = 90;
            definition.size = 4;
            definition.walkingAnimation = 79;
        }

        if (id == FENRIR_GREYBACK) {
            definition.name = "Fenrir greyback";
            definition.actions = new String[]{null, "Attack", null, null, null};
            definition.combatLevel = 655;
            definition.modelId = new int[]{26177, 26188, 26181};
            definition.additionalModels = new int[]{26113};
            definition.standingAnimation = 6539;
            definition.walkingAnimation = 6541;
        }

        if (id == KERBEROS) {
            NpcDefinition.copy(definition, NpcIdentifiers.CERBERUS);
            definition.name = "Kerberos";
            definition.modelCustomColor4 = 125;
        }

        if (id == SKORPIOS) {
            NpcDefinition.copy(definition, NpcIdentifiers.SCORPIA);
            definition.name = "Skorpios";
            definition.modelCustomColor4 = 125;
        }

        if (id == ARACHNE) {
            NpcDefinition.copy(definition, NpcIdentifiers.VENENATIS);
            definition.name = "Arachne";
            definition.modelCustomColor4 = 125;
        }

        if (id == ARTIO) {
            NpcDefinition.copy(definition, NpcIdentifiers.CALLISTO);
            definition.name = "Artio";
            definition.modelCustomColor4 = 115;
        }
    }
}

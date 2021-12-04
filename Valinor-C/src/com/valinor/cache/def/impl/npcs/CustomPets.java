package com.valinor.cache.def.impl.npcs;

import com.valinor.cache.def.NpcDefinition;

import static com.valinor.util.CustomNpcIdentifiers.*;
import static com.valinor.util.CustomNpcIdentifiers.ZOMBIES_CHAMPION;
import static com.valinor.util.NpcIdentifiers.*;

public class CustomPets {

    private static final int[] pets = {
        SKELETON_HELLHOUND_PET,
        FAWKES_10981,
        LITTLE_NIGHTMARE_9399,
        ELYSIAN_PET,
        BLOOD_MONEY_PET,
        KERBEROS_PET,
        SKORPIOS_PET,
        ARACHNE_PET,
        ARTIO_PET,
        ANCIENT_KING_BLACK_DRAGON_PET,
        ANCIENT_CHAOS_ELEMENTAL_PET,
        ANCIENT_BARRELCHEST_PET,
        BLOOD_FIREBIRD,
        ZRIAWK,
        FENRIR_GREYBACK_JR,
        FLUFFY_JR,
        DEMENTOR_PET,
        CENTAUR_MALE_PET,
        CENTAUR_FEMALE_PET,
        JALNIBREK_7675,
        TZREKZUK_8011,
        FOUNDER_IMP,
        PET_CORRUPTED_NECHRYARCH,
        MINI_NECROMANCER,
        JALTOK_JAD,
        BABY_LAVA_DRAGON,
        JAWA,
        BABY_ARAGOG,
        WAMPA,
        FAWKES,
        BABY_SQUIRT,
        GRIM_REAPER,
        BABY_DARK_BEAST,
        BABY_ABYSSAL_DEMON,
        ZOMBIES_CHAMPION,
        BARRELCHEST_PET,
        NIFFLER,
        DHAROK_PET,
        GENIE_PET,
        PET_GENERAL_GRAARDOR_BLACK,
        PET_KRIL_TSUTSAROTH_BLACK,
        PET_ZILYANA_WHITE,
        PET_KREEARRA_WHITE,
        ABYSSAL_ORPHAN_5884,
        CALLISTO_CUB_5558,
        HELLPUPPY_3099,
        KALPHITE_PRINCESS_6638,
        KALPHITE_PRINCESS,
        KALPHITE_PRINCESS,
        KALPHITE_PRINCESS_6638,
        CHAOS_ELEMENTAL_JR,
        DAGANNOTH_PRIME_JR_6629,
        DAGANNOTH_REX_JR,
        DAGANNOTH_SUPREME_JR_6628,
        DARK_CORE,
        CORPOREAL_CRITTER_8010,
        GENERAL_GRAARDOR_JR,
        KRIL_TSUTSAROTH_JR,
        KREEARRA_JR,
        ZILYANA_JR,
        KRAKEN_6640,
        PENANCE_PET_6674,
        SMOKE_DEVIL_6639,
        SNAKELING_2130,
        SNAKELING_2128,
        SNAKELING_2131,
        SNAKELING_2129,
        SNAKELING_2132,
        SNAKELING_2127,
        PRINCE_BLACK_DRAGON,
        SCORPIAS_OFFSPRING_5561,
        TZREKJAD_5893,
        VENENATIS_SPIDERLING_5557,
        VETION_JR_5559,
        VETION_JR_5560,
        MIDNIGHT_7893,
        NOON_7892,
        SKOTOS_7671,
        VORKI_8029,
        OLMLET_7520,
        PUPPADILE_8201,
        TEKTINY_8202,
        VANGUARD_8203,
        VASA_MINIRIO_8204,
        VESPINA_8205,
        IKKLE_HYDRA,
        IKKLE_HYDRA_8493,
        IKKLE_HYDRA_8494,
        IKKLE_HYDRA_8495,
        BABY_CHINCHOMPA_6756,
        BABY_CHINCHOMPA_6757,
        BABY_CHINCHOMPA_6758,
        BABY_CHINCHOMPA_6759,
        BEAVER_6724,
        HERON_6722,
        ROCK_GOLEM_7439,
        ROCK_GOLEM_7440,
        ROCK_GOLEM_7441,
        ROCK_GOLEM_7442,
        ROCK_GOLEM_7443,
        ROCK_GOLEM_7444,
        ROCK_GOLEM_7445,
        ROCK_GOLEM_7737,
        ROCK_GOLEM_7446,
        ROCK_GOLEM_7448,
        ROCK_GOLEM_7447,
        ROCK_GOLEM_7736,
        ROCK_GOLEM_7449,
        ROCK_GOLEM_7450,
        ROCK_GOLEM_7711,
        GIANT_SQUIRREL_7351,
        TANGLEROOT_7352,
        ROCKY_7353,
        RIFT_GUARDIAN_7354,
        RIFT_GUARDIAN_7355,
        RIFT_GUARDIAN_7356,
        RIFT_GUARDIAN_7357,
        RIFT_GUARDIAN_7358,
        RIFT_GUARDIAN_7359,
        RIFT_GUARDIAN_7360,
        RIFT_GUARDIAN_7361,
        RIFT_GUARDIAN_7362,
        RIFT_GUARDIAN_7363,
        RIFT_GUARDIAN_7364,
        RIFT_GUARDIAN_7365,
        RIFT_GUARDIAN_7366,
        RIFT_GUARDIAN_7367,
        RIFT_GUARDIAN_8024,
        HERBI_7760,
        BLOODHOUND_7232,
        CHOMPY_CHICK_4002,
        YOUNGLLEF,
        CORRUPTED_YOUNGLLEF,
        BLOOD_REAPER,
        NAGINI,
        SRARACHA
    };

    private static boolean isPet(int id) {
        for (int pet : pets) {
            if (pet == id) {
                return true;
            }
        }
        return false;
    }

    private static final int[] MORPH_PETS = new int[]{KALPHITE_PRINCESS, KALPHITE_PRINCESS_6638, DARK_CORE, CORPOREAL_CRITTER_8010, SNAKELING_2128, SNAKELING_2130, SNAKELING_2131, SNAKELING_2132, VETION_JR_5559,
        VETION_JR_5560, NOON_7892, MIDNIGHT_7893, IKKLE_HYDRA, IKKLE_HYDRA_8493, IKKLE_HYDRA_8494, IKKLE_HYDRA_8495, BABY_CHINCHOMPA_6757, BABY_CHINCHOMPA_6758, BABY_CHINCHOMPA_6758, BABY_CHINCHOMPA_6759};

    private static boolean morphPets(int id) {
        for (int pet : MORPH_PETS) {
            if (pet == id) {
                return true;
            }
        }
        return false;
    }

    public static void unpack(int id) {
        NpcDefinition definition = NpcDefinition.get(id);

        if(id == 12025) {
            definition.name = "Nagini";
            definition.ambient = 10;
            definition.models = new int[] {13910};
            definition.widthScale = 45;
            definition.heightScale = 45;
            definition.standingAnimation = 3535;
            definition.walkingAnimation = 3537;
        }

        if (id == 9330) {
            definition.name = "Ancient king black dragon";
            definition.modelCustomColor4 = 235;
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{17414, 17415, 17429, 17422, 17423};
            definition.additionalModels = new int[]{28865};
            definition.heightScale = 40;
            definition.widthScale = 40;
            definition.standingAnimation = 90;
            definition.walkingAnimation = 4635;
        }

        if (id == 9331) {
            definition.name = "Ancient chaos elemental";
            definition.modelCustomColor4 = 235;
            definition.models = new int[]{28256};
            definition.additionalModels = new int[]{5805};
            definition.standingAnimation = 3144;
            definition.walkingAnimation = 3145;
        }

        if (id == 9332) {
            definition.name = "Ancient barrelchest";
            definition.modelCustomColor4 = 235;
            definition.heightScale = 40;
            definition.widthScale = 40;
            definition.models = new int[]{22790};
            definition.standingAnimation = 5893;
            definition.walkingAnimation = 5892;
        }

        if (id == 9340) {
            definition.modelCustomColor4 = 33235;
        }

        if (id == 6635) {
            definition.modelCustomColor4 = 33785;
            definition.name = "Niffler";
        }

        if (id == 4927) {
            definition.name = "Fawkes";
        }

        if (id == 10981) {
            definition.name = "Fawkes";
            definition.ambient = 40;
            definition.models = new int[]{26853};
            definition.standingAnimation = 6809;
            definition.walkingAnimation = 6808;
            definition.modelCustomColor4 = 222200;
        }

        if (id == 7370) {
            definition.name = "Blood firebird";
            definition.recolorFrom = new int[]{8, 10176, 10167, 5010, 4894, 914, 29867, 6084, 2880, 4011, 8150, 4399, 4391, 20, 5053, 5066, 4647, 23492, 23483, 6053, 5669, 6622, 1587, 28};
            definition.recolorTo = new int[]{910, 933, 910, 933, 910, 910, 910, 933, 910, 933, 910, 933, 910, 910, 910, 933, 910, 933, 910, 933, 910, 910, 910, 933};
        }

        if (id == 1217) {
            definition.name = "Dharok the Wretched";
            definition.widthScale = 80;
            definition.heightScale = 80;
            definition.ambient = 50;
            definition.contrast = 50;
            definition.models = new int[]{6652, 6671, 6640, 6661, 6703, 6679};
            definition.standingAnimation = 2065;
            definition.walkingAnimation = 2064;
        }

        if (id == 1219) {
            definition.name = "Zombies champion";
            definition.widthScale = 63;
            definition.heightScale = 63;
            definition.models = new int[]{20949};
            definition.standingAnimation = 5573;
            definition.walkingAnimation = 5582;
            definition.rotate180Animation = 8634;
            definition.rotate90LeftAnimation = 8634;
            definition.rotate90RightAnimation = 8634;
        }

        if (id == 1220) {
            definition.name = "Wampa";
            definition.widthScale = 63;
            definition.heightScale = 63;
            definition.models = new int[]{21802, 21801, 21806};
            definition.standingAnimation = 5722;
            definition.walkingAnimation = 5721;
        }

        if (id == 1221) {
            definition.name = "Zilyana Jr.";
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{27989, 27937, 27985, 27968, 27990};
            definition.additionalModels = new int[]{28864};
            definition.widthScale = 60;
            definition.heightScale = 60;
            definition.standingAnimation = 6966;
            definition.walkingAnimation = 6965;
            definition.rotate180Animation = 6965;
            definition.rotate90LeftAnimation = 6965;
            definition.rotate90RightAnimation = 6965;
            definition.modelCustomColor4 = 33785;
        }

        if (id == 1222) {
            definition.name = "General Graardor Jr.";
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{27660, 27665};
            definition.additionalModels = new int[]{28860};
            definition.widthScale = 30;
            definition.heightScale = 30;
            definition.standingAnimation = 7017;
            definition.walkingAnimation = 7016;
            definition.rotate180Animation = 7016;
            definition.rotate90LeftAnimation = 7016;
            definition.rotate90RightAnimation = 7016;
            definition.modelCustomColor4 = 235;
        }

        if (id == 1223) {
            definition.name = "Kree'arra Jr.";
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{28019, 28021, 28020};
            definition.additionalModels = new int[]{28859};
            definition.widthScale = 30;
            definition.heightScale = 30;
            definition.standingAnimation = 7166;
            definition.walkingAnimation = 7167;
            definition.rotate180Animation = 7166;
            definition.rotate90LeftAnimation = 7166;
            definition.rotate90RightAnimation = 7166;
            definition.modelCustomColor4 = 31575;
        }

        if (id == 1224) {
            definition.name = "K'ril Tsutsaroth Jr.";
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{27683, 27681, 27692, 27682, 27690};
            definition.additionalModels = new int[]{28858};
            definition.widthScale = 20;
            definition.heightScale = 20;
            definition.standingAnimation = 6935;
            definition.walkingAnimation = 4070;
            definition.rotate180Animation = 4070;
            definition.rotate90LeftAnimation = 4070;
            definition.rotate90RightAnimation = 4070;
            definition.modelCustomColor4 = 235;
        }

        if (id == 1225) {
            definition.name = "Baby Squirt";
            definition.models = new int[]{25000, 25006, 25001, 25002, 25003, 25004, 25005};
            definition.standingAnimation = 6317;
            definition.walkingAnimation = 6317;
            definition.rotate180Animation = 6317;
            definition.rotate90LeftAnimation = 6317;
            definition.rotate90RightAnimation = 6317;
            definition.heightScale = 30;
            definition.widthScale = 30;
        }

        if (id == 1213) {
            definition.name = "Jawa";
            definition.models = new int[]{28285, 181, 249, 28286, 28226, 176, 28223};
            definition.modelCustomColor = 4769;
            definition.heightScale = 80;
            definition.widthScale = 80;
            definition.rotate180Animation = 820;
            definition.rotate90LeftAnimation = 822;
            definition.rotate90RightAnimation = 821;
            definition.standingAnimation = 808;
            definition.walkingAnimation = 819;
        }

        if (id == 1214) {
            definition.name = "Baby Aragog";
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{28294, 28295};
            definition.additionalModels = new int[]{29186};
            definition.widthScale = 60;
            definition.heightScale = 60;
            definition.standingAnimation = 5326;
            definition.walkingAnimation = 5325;
            definition.rotate180Animation = NpcDefinition.getHalfTurnAnimation(VENENATIS_SPIDERLING_5557);
            definition.rotate90RightAnimation = NpcDefinition.getQuarterAnticlockwiseTurnAnimation(VENENATIS_SPIDERLING_5557);
            definition.rotate90LeftAnimation = NpcDefinition.getQuarterAnticlockwiseTurnAnimation(VENENATIS_SPIDERLING_5557);
            definition.recolorFrom = new int[]{138, 908, 794, 912, 916, 0, 103, 107};
            definition.recolorTo = new int[]{138, 908, 4769, 4769, 4769, 0, 0, 0};
        }

        if (id == 1182) {
            definition.name = "Baby Barrelchest";
            definition.heightScale = 40;
            definition.widthScale = 40;
            definition.models = new int[]{22790};
            definition.standingAnimation = 5893;
            definition.walkingAnimation = 5892;
        }

        if (id == 1228) {
            definition.name = "Grim Reaper";
            definition.heightScale = 90;
            definition.widthScale = 90;
            definition.recolorFrom = new int[]{10004, 25238, 8741, 4550, 908, 7073};
            definition.recolorTo = new int[]{5231, 0, 0, 5353, 0, 8084};
            definition.models = new int[]{5100, 292, 170, 179, 256, 507};
            definition.rotate180Animation = 820;
            definition.rotate90LeftAnimation = 822;
            definition.rotate90RightAnimation = 821;
            definition.standingAnimation = 847;
            definition.walkingAnimation = 819;
        }

        if (id == BLOOD_REAPER) {
            definition.name = "Blood Reaper";
            definition.heightScale = 90;
            definition.widthScale = 90;
            definition.recolorFrom = new int[]{10004, 25238, 8741, 4550, 908, 7073};
            definition.recolorTo = new int[]{5231, 0, 0, 5353, 0, 8084};
            definition.models = new int[]{5100, 292, 170, 179, 256, 507};
            definition.rotate180Animation = 820;
            definition.rotate90LeftAnimation = 822;
            definition.rotate90RightAnimation = 821;
            definition.standingAnimation = 847;
            definition.walkingAnimation = 819;
            definition.modelCustomColor4 = 964;
        }

        if (id == 1216) {
            definition.name = "Baby Dark Beast";
            definition.models = new int[]{26395};
            definition.standingAnimation = 2730;
            definition.walkingAnimation = 2729;
            definition.heightScale = 40;
            definition.widthScale = 40;
        }

        if (id == 1218) {
            definition.name = "Baby Abyssal Demon";
            definition.recolorFrom = new int[] {4015};
            definition.recolorTo = new int[] {528};
            definition.models = new int[] {5062};
            definition.standingAnimation = 1536;
            definition.walkingAnimation = 1534;
            definition.heightScale = 40;
            definition.widthScale = 40;
        }

        if (id == 6849) {
            definition.name = "Genie";
            definition.standingAnimation = 792;
            definition.walkingAnimation = 792;
            definition.rotate180Animation = 65535;
            definition.rotate90LeftAnimation = 65535;
            definition.rotate90RightAnimation = 65535;
            definition.heightScale = 128;
            definition.widthScale = 128;
            definition.models = new int[]{231, 241, 252, 315, 173, 176, 264, 270};
            definition.recolorFrom = new int[]{4550, 6798, 926, 43072, 0, 25238};
            definition.recolorTo = new int[]{39888, 40627, 43924, 13243, 957, 54177};
        }

        if (id == 15035) {
            definition.name = "Kerberos";
            definition.models = new int[]{29240};
            definition.additionalModels = new int[]{29392};
            definition.standingAnimation = 6561;
            definition.walkingAnimation = 6560;
            definition.modelCustomColor4 = 125;
        }

        if (id == 15036) {
            definition.name = "Skorpios";
            definition.ambient = 30;
            definition.recolorFrom = new int[]{142, 4525, 4636, 4884, 4645};
            definition.recolorTo = new int[]{28, 16, 16, 16, 16};
            definition.contrast = 30;
            definition.models = new int[]{29193};
            definition.additionalModels = new int[]{29185};
            definition.widthScale = 280;
            definition.heightScale = 280;
            definition.standingAnimation = 6258;
            definition.walkingAnimation = 6257;
            definition.modelCustomColor4 = 125;
        }

        if (id == 15037) {
            definition.name = "Arachne";
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{28294, 28295};
            definition.additionalModels = new int[]{29186};
            definition.widthScale = 60;
            definition.heightScale = 60;
            definition.standingAnimation = 5326;
            definition.walkingAnimation = 5325;
            definition.modelCustomColor4 = 125;
        }

        if (id == 15038) {
            definition.name = "Artio";
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{28298};
            definition.additionalModels = new int[]{29187};
            definition.widthScale = 35;
            definition.heightScale = 35;
            definition.standingAnimation = 4919;
            definition.walkingAnimation = 4923;
            definition.modelCustomColor4 = 115;
        }

        if (id == 7315) {
            definition.name = "Blood money";
            definition.recolorFrom = new int[1];
            definition.recolorFrom[0] = 8128;
            definition.recolorTo = new int[1];
            definition.recolorTo[0] = 940;
        }

        if (id == 336) {
            definition.name = "Elysian";
        }

        if (id == 15040) {
            definition.name = "Centaur";
            definition.models = new int[]{16196, 16202, 16199, 16200};
            definition.additionalModels = new int[]{16213};
            definition.widthScale = 78;
            definition.heightScale = 78;
            definition.standingAnimation = 4311;
            definition.walkingAnimation = 4310;
        }

        if (id == 15042) {
            definition.name = "Centaur";
            definition.models = new int[]{16195, 16201, 16198, 16197, 16200};
            definition.additionalModels = new int[]{16212, 16211};
            definition.widthScale = 78;
            definition.heightScale = 78;
            definition.standingAnimation = 4311;
            definition.walkingAnimation = 4310;
        }

        if (id == 15044) {
            definition.name = "Dementor";
            definition.ambient = 20;
            definition.recolorFrom = new int[]{10343, -22250, -22365, -22361, -22353, -22464, -22477, -22456, -22473, -22452};
            definition.recolorTo = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            definition.contrast = 20;
            definition.models = new int[]{21154};
            definition.additionalModels = new int[]{21394};
            definition.widthScale = 78;
            definition.heightScale = 78;
            definition.standingAnimation = 5538;
            definition.walkingAnimation = 5539;
        }

        if (id == 15017) {
            definition.name = "Baby Lava Dragon";
            definition.models = new int[]{58995, 58995, 58994, 58996};
            definition.widthScale = 40;
            definition.heightScale = 40;
            definition.standingAnimation = 7870;
            definition.walkingAnimation = 7870;
        }

        if (id == 15005) {
            definition.name = "Mini Necromancer";
            definition.recolorFrom = new int[]{-26527, -24618, -26073, 5018, 61, 10351, 33, 24};
            definition.recolorTo = new int[]{-19054, 12, 12, -16870, 11177, 61, 16, 12};
            definition.models = new int[]{4953, 4955, 556, 58948, 58907, 58950, 58953, 58956};
            definition.widthScale = 90;
            definition.heightScale = 90;
            definition.standingAnimation = 808;
            definition.walkingAnimation = 819;
        }

        if (id == 15002) {
            definition.name = "Corrupted nechryarch";
            definition.models = new int[]{58922};
            definition.widthScale = 35;
            definition.heightScale = 35;
            definition.standingAnimation = 4650;
            definition.walkingAnimation = 6372;
        }

        if (id == 15000) {
            definition.name = "Founder Imp";
            definition.ambient = 30;
            definition.recolorFrom = new int[]{10306, 10297, -25326, 7461, 7469};
            definition.recolorTo = new int[]{10549, 10421, -24698, 7952, 7704};
            definition.models = new int[]{58916};
            definition.widthScale = 132;
            definition.heightScale = 132;
            definition.standingAnimation = 171;
            definition.walkingAnimation = 168;
        }

        if (id == 15008) {
            definition.name = "JalTok-Jad";
            definition.models = new int[]{33012};
            definition.widthScale = 20;
            definition.heightScale = 20;
            definition.standingAnimation = 7589;
            definition.walkingAnimation = 7588;
        }

        if (id == 9338) {
            definition.name = "Fluffy Jr";
            definition.recolorFrom = new int[]{929, 960, 1981, 0, 931, 4029, 926, 902, 922, 918, 924, 904, 916, 912, 935, 939, 906, 920, 955, 910, 914, 7101, 11200, 957, 9149, 908, 4, 5053, 8125, 6069};
            definition.recolorTo = new int[]{4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 0, 4769, 0, 4769, 0, 0, 4769, 4769};
            definition.models = new int[]{29270};
            definition.widthScale = 28;
            definition.heightScale = 28;
            definition.standingAnimation = 4484;
            definition.walkingAnimation = 4488;
        }

        if (id == 9339) {
            definition.name = "Fenrir Greyback Jr";
            definition.models = new int[]{26177, 26188, 26181};
            definition.additionalModels = new int[]{26113};
            definition.widthScale = 78;
            definition.heightScale = 78;
            definition.standingAnimation = 6539;
            definition.walkingAnimation = 6541;
        }

        if (id == 9340) {
            definition.name = "Zriawk";
            definition.models = new int[]{6458};
            definition.standingAnimation = 2017;
            definition.walkingAnimation = 2016;
        }

        if(id == 12022) {
            definition.models = new int[]{26262};
            definition.name = "Skeleton hellhound pet";
            definition.standingAnimation = 6580;
            definition.walkingAnimation = (definition.rotate180Animation = definition.rotate90RightAnimation = definition.rotate90LeftAnimation = 6577);
            definition.widthScale = 85;
            definition.heightScale = 85;
        }

        //Default definitions for pets
        if (isPet(id)) {
            definition.name = definition.name + " pet";
            if (morphPets(id)) {
                definition.actions = new String[]{"Pick-up", null, "Metamorphosis", null, null};
            } else if (id == NIFFLER) {
                definition.actions = new String[]{"Pick-up", null, "Tickle", null, null};
            } else {
                definition.actions = new String[]{"Pick-up", null, null, null, null};
            }
            definition.pet = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.combatLevel = 0;
            definition.description = "Tiny but deadly!";
        }
    }
}

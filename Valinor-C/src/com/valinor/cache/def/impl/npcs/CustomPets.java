package com.valinor.cache.def.impl.npcs;

import com.valinor.cache.def.NpcDefinition;

import static com.valinor.util.CustomNpcIdentifiers.*;
import static com.valinor.util.CustomNpcIdentifiers.ZOMBIES_CHAMPION;
import static com.valinor.util.NpcIdentifiers.*;

public class CustomPets {

    public static void unpack(int id) {
        NpcDefinition definition = NpcDefinition.get(id);

        if(id == NAGINI) {
            definition.name = "Nagini pet";
            definition.ambient = 10;
            definition.models = new int[] {13910};
            definition.widthScale = 45;
            definition.heightScale = 45;
            definition.standingAnimation = 3535;
            definition.walkingAnimation = 3537;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == ANCIENT_KING_BLACK_DRAGON_PET) {
            definition.name = "Ancient king black dragon pet";
            definition.modelCustomColor4 = 235;
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{17414, 17415, 17429, 17422, 17423};
            definition.additionalModels = new int[]{28865};
            definition.heightScale = 40;
            definition.widthScale = 40;
            definition.standingAnimation = 90;
            definition.walkingAnimation = 4635;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == ANCIENT_CHAOS_ELEMENTAL_PET) {
            definition.name = "Ancient chaos elemental pet";
            definition.modelCustomColor4 = 235;
            definition.models = new int[]{28256};
            definition.additionalModels = new int[]{5805};
            definition.standingAnimation = 3144;
            definition.walkingAnimation = 3145;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == ANCIENT_BARRELCHEST_PET) {
            definition.name = "Ancient barrelchest pet";
            definition.modelCustomColor4 = 235;
            definition.heightScale = 40;
            definition.widthScale = 40;
            definition.models = new int[]{22790};
            definition.standingAnimation = 5893;
            definition.walkingAnimation = 5892;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == NIFFLER) {
            definition.modelCustomColor4 = 33785;
            definition.name = "Niffler pet";
            definition.actions = new String[]{"Pick-up", null, "Tickle", null, null};
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[] {12073};
            definition.additionalModels = new int[] {28863};
            definition.heightScale = 80;
            definition.widthScale = 80;
            definition.standingAnimation = 3309;
            definition.walkingAnimation = 3313;
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == FAWKES) {
            definition.name = "Fawkes pet";
            definition.ambient = 40;
            definition.models = new int[] {26853};
            definition.standingAnimation = 6809;
            definition.walkingAnimation = 6808;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == FAWKES_15981) {
            definition.name = "Fawkes pet";
            definition.ambient = 40;
            definition.models = new int[]{26853};
            definition.standingAnimation = 6809;
            definition.walkingAnimation = 6808;
            definition.modelCustomColor4 = 222200;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == BLOOD_FIREBIRD) {
            definition.name = "Blood firebird pet";
            definition.recolorFrom = new int[]{8, 10176, 10167, 5010, 4894, 914, 29867, 6084, 2880, 4011, 8150, 4399, 4391, 20, 5053, 5066, 4647, 23492, 23483, 6053, 5669, 6622, 1587, 28};
            definition.recolorTo = new int[]{910, 933, 910, 933, 910, 910, 910, 933, 910, 933, 910, 933, 910, 910, 910, 933, 910, 933, 910, 933, 910, 910, 910, 933};
            definition.ambient = 40;
            definition.models = new int[] {26852};
            definition.additionalModels = new int[] {32290};
            definition.widthScale = 80;
            definition.heightScale = 80;
            definition.standingAnimation = 6809;
            definition.walkingAnimation = 6808;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == DHAROK_PET) {
            definition.name = "Dharok the Wretched pet";
            definition.widthScale = 80;
            definition.heightScale = 80;
            definition.ambient = 50;
            definition.contrast = 50;
            definition.models = new int[]{6652, 6671, 6640, 6661, 6703, 6679};
            definition.standingAnimation = 2065;
            definition.walkingAnimation = 2064;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == ZOMBIES_CHAMPION) {
            definition.name = "Zombies champion pet";
            definition.widthScale = 63;
            definition.heightScale = 63;
            definition.models = new int[]{20949};
            definition.standingAnimation = 5573;
            definition.walkingAnimation = 5582;
            definition.rotate180Animation = 8634;
            definition.rotate90LeftAnimation = 8634;
            definition.rotate90RightAnimation = 8634;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == ICELORD_PET) {
            definition.name = "Icelord pet";
            definition.widthScale = 63;
            definition.heightScale = 63;
            definition.models = new int[]{21802, 21801, 21806};
            definition.standingAnimation = 5722;
            definition.walkingAnimation = 5721;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if(id == 15121) {
            NpcDefinition.copy(definition, IMP);
            definition.name = "Ice imp pet";
            definition.combatLevel = 188;
            definition.widthScale = 120;
            definition.heightScale = 120;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
            definition.modelCustomColor4 = 31575;
        }

        if (id == PET_ZILYANA_WHITE) {
            definition.name = "Zilyana Jr. pet";
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
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == PET_GENERAL_GRAARDOR_BLACK) {
            definition.name = "General Graardor Jr. pet";
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
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == PET_KREEARRA_WHITE) {
            definition.name = "Kree'arra Jr. pet";
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
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == PET_KRIL_TSUTSAROTH_BLACK) {
            definition.name = "K'ril Tsutsaroth Jr. pet";
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
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == BABY_SQUIRT) {
            definition.name = "Baby Squirt pet";
            definition.models = new int[]{25000, 25006, 25001, 25002, 25003, 25004, 25005};
            definition.standingAnimation = 6317;
            definition.walkingAnimation = 6317;
            definition.rotate180Animation = 6317;
            definition.rotate90LeftAnimation = 6317;
            definition.rotate90RightAnimation = 6317;
            definition.heightScale = 30;
            definition.widthScale = 30;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == JAWA) {
            definition.name = "Jawa pet";
            definition.models = new int[]{28285, 181, 249, 28286, 28226, 176, 28223};
            definition.modelCustomColor = 4769;
            definition.heightScale = 80;
            definition.widthScale = 80;
            definition.rotate180Animation = 820;
            definition.rotate90LeftAnimation = 822;
            definition.rotate90RightAnimation = 821;
            definition.standingAnimation = 808;
            definition.walkingAnimation = 819;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == BABY_ARAGOG) {
            definition.name = "Baby Aragog pet";
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
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == BARRELCHEST_PET) {
            definition.name = "Baby Barrelchest pet";
            definition.heightScale = 40;
            definition.widthScale = 40;
            definition.models = new int[]{22790};
            definition.standingAnimation = 5893;
            definition.walkingAnimation = 5892;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == GRIM_REAPER) {
            definition.name = "Grim Reaper pet";
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
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == BLOOD_REAPER) {
            definition.name = "Blood Reaper pet";
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
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == BABY_DARK_BEAST) {
            definition.name = "Baby Dark Beast pet";
            definition.models = new int[]{26395};
            definition.standingAnimation = 2730;
            definition.walkingAnimation = 2729;
            definition.heightScale = 40;
            definition.widthScale = 40;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == BABY_ABYSSAL_DEMON) {
            definition.name = "Baby Abyssal Demon pet";
            definition.recolorFrom = new int[] {4015};
            definition.recolorTo = new int[] {528};
            definition.models = new int[] {5062};
            definition.standingAnimation = 1536;
            definition.walkingAnimation = 1534;
            definition.heightScale = 40;
            definition.widthScale = 40;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == GENIE_PET) {
            definition.name = "Genie pet";
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
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == KERBEROS_PET) {
            definition.name = "Kerberos pet";
            definition.models = new int[]{29240};
            definition.additionalModels = new int[]{29392};
            definition.standingAnimation = 6561;
            definition.walkingAnimation = 6560;
            definition.modelCustomColor4 = 125;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == SKORPIOS_PET) {
            definition.name = "Skorpios pet";
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
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == ARACHNE_PET) {
            definition.name = "Arachne pet";
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{28294, 28295};
            definition.additionalModels = new int[]{29186};
            definition.widthScale = 60;
            definition.heightScale = 60;
            definition.standingAnimation = 5326;
            definition.walkingAnimation = 5325;
            definition.modelCustomColor4 = 125;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == ARTIO_PET) {
            definition.name = "Artio pet";
            definition.ambient = 30;
            definition.contrast = 30;
            definition.models = new int[]{28298};
            definition.additionalModels = new int[]{29187};
            definition.widthScale = 35;
            definition.heightScale = 35;
            definition.standingAnimation = 4919;
            definition.walkingAnimation = 4923;
            definition.modelCustomColor4 = 115;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == ELYSIAN_PET) {
            definition.name = "Elysian pet";
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == CENTAUR_MALE_PET) {
            definition.name = "Centaur pet";
            definition.models = new int[]{16196, 16202, 16199, 16200};
            definition.additionalModels = new int[]{16213};
            definition.widthScale = 78;
            definition.heightScale = 78;
            definition.standingAnimation = 4311;
            definition.walkingAnimation = 4310;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == CENTAUR_FEMALE_PET) {
            definition.name = "Centaur pet";
            definition.models = new int[]{16195, 16201, 16198, 16197, 16200};
            definition.additionalModels = new int[]{16212, 16211};
            definition.widthScale = 78;
            definition.heightScale = 78;
            definition.standingAnimation = 4311;
            definition.walkingAnimation = 4310;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == DEMENTOR_PET) {
            definition.name = "Dementor pet";
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
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == BABY_LAVA_DRAGON) {
            definition.name = "Baby Lava Dragon pet";
            definition.models = new int[]{58995, 58995, 58994, 58996};
            definition.widthScale = 40;
            definition.heightScale = 40;
            definition.standingAnimation = 7870;
            definition.walkingAnimation = 7870;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == MINI_NECROMANCER) {
            definition.name = "Mini Necromancer pet";
            definition.recolorFrom = new int[]{-26527, -24618, -26073, 5018, 61, 10351, 33, 24};
            definition.recolorTo = new int[]{-19054, 12, 12, -16870, 11177, 61, 16, 12};
            definition.models = new int[]{4953, 4955, 556, 58948, 58907, 58950, 58953, 58956};
            definition.widthScale = 90;
            definition.heightScale = 90;
            definition.standingAnimation = 808;
            definition.walkingAnimation = 819;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == PET_CORRUPTED_NECHRYARCH) {
            definition.name = "Corrupted nechryarch pet";
            definition.models = new int[]{58922};
            definition.widthScale = 35;
            definition.heightScale = 35;
            definition.standingAnimation = 4650;
            definition.walkingAnimation = 6372;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == FOUNDER_IMP) {
            definition.name = "Founder Imp pet";
            definition.ambient = 30;
            definition.recolorFrom = new int[]{10306, 10297, -25326, 7461, 7469};
            definition.recolorTo = new int[]{10549, 10421, -24698, 7952, 7704};
            definition.models = new int[]{58916};
            definition.widthScale = 132;
            definition.heightScale = 132;
            definition.standingAnimation = 171;
            definition.walkingAnimation = 168;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == JALTOK_JAD) {
            definition.name = "JalTok-Jad pet";
            definition.models = new int[]{33012};
            definition.widthScale = 20;
            definition.heightScale = 20;
            definition.standingAnimation = 7589;
            definition.walkingAnimation = 7588;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == FLUFFY_JR) {
            definition.name = "Fluffy Jr pet";
            definition.recolorFrom = new int[]{929, 960, 1981, 0, 931, 4029, 926, 902, 922, 918, 924, 904, 916, 912, 935, 939, 906, 920, 955, 910, 914, 7101, 11200, 957, 9149, 908, 4, 5053, 8125, 6069};
            definition.recolorTo = new int[]{4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 4769, 0, 4769, 0, 4769, 0, 0, 4769, 4769};
            definition.models = new int[]{29270};
            definition.widthScale = 28;
            definition.heightScale = 28;
            definition.standingAnimation = 4484;
            definition.walkingAnimation = 4488;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == FENRIR_GREYBACK_JR) {
            definition.name = "Fenrir Greyback Jr pet";
            definition.models = new int[]{26177, 26188, 26181};
            definition.additionalModels = new int[]{26113};
            definition.widthScale = 78;
            definition.heightScale = 78;
            definition.standingAnimation = 6539;
            definition.walkingAnimation = 6541;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if (id == ZRIAWK) {
            definition.name = "Zriawk pet";
            definition.models = new int[]{6458};
            definition.standingAnimation = 2017;
            definition.walkingAnimation = 2016;
            definition.modelCustomColor4 = 33235;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }

        if(id == SKELETON_HELLHOUND_PET) {
            definition.models = new int[]{26262};
            definition.name = "Skeleton hellhound pet";
            definition.standingAnimation = 6580;
            definition.walkingAnimation = (definition.rotate180Animation = definition.rotate90RightAnimation = definition.rotate90LeftAnimation = 6577);
            definition.widthScale = 85;
            definition.heightScale = 85;
            definition.actions = new String[]{"Pick-up", null, null, null, null};
            definition.isFollower = true;
            definition.drawMapDot = false;
            definition.size = 1;
            definition.description = "Tiny but deadly!";
        }
    }
}

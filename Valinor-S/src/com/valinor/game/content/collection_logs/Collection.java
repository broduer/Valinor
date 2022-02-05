package com.valinor.game.content.collection_logs;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.items.Item;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.CustomNpcIdentifiers;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.NpcIdentifiers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.valinor.game.content.collection_logs.CollectionLog.*;
import static com.valinor.util.CustomItemIdentifiers.BABY_LAVA_DRAGON;
import static com.valinor.util.CustomItemIdentifiers.BARRELCHEST_PET;
import static com.valinor.util.CustomItemIdentifiers.DEMENTOR_PET;
import static com.valinor.util.CustomItemIdentifiers.FENRIR_GREYBACK_JR;
import static com.valinor.util.CustomItemIdentifiers.FLUFFY_JR;
import static com.valinor.util.CustomItemIdentifiers.NIFFLER;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.CustomNpcIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.NpcIdentifiers.THE_NIGHTMARE_9430;

/**
 * @author PVE
 * @Since juli 15, 2020
 */
public enum Collection {

    // OSRS bosses
    ABYSSAL_SIRE(AttributeKey.KC_ABYSSALSIRE, LogType.BOSSES,"Abyssal Sire", new int[]{NpcIdentifiers.ABYSSAL_SIRE, NpcIdentifiers.ABYSSAL_SIRE_5887, NpcIdentifiers.ABYSSAL_SIRE_5888, NpcIdentifiers.ABYSSAL_SIRE_5889, NpcIdentifiers.ABYSSAL_SIRE_5890, NpcIdentifiers.ABYSSAL_SIRE_5891, NpcIdentifiers.ABYSSAL_SIRE_5908}, AttributeKey.ABYSSALSIRE_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3)},
        //Drops
        new Item(ABYSSAL_ORPHAN), new Item(UNSIRED), new Item(ABYSSAL_HEAD), new Item(BLUDGEON_SPINE), new Item(BLUDGEON_CLAW), new Item(BLUDGEON_AXON), new Item(JAR_OF_MIASMA), new Item(ABYSSAL_DAGGER), new Item(ABYSSAL_WHIP)),

    BARROWS(AttributeKey.BARROWS_CHESTS_OPENED, LogType.BOSSES, "Barrows", new int[]{BARROWS_KEY},  AttributeKey.BARROWS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS), new Item(BARROWS_MYSTERY_BOX, 10), new Item(CustomItemIdentifiers.CURSED_AMULET_OF_THE_DAMNED)},
        //Drops
        new Item(KARILS_COIF), new Item(AHRIMS_HOOD), new Item(DHAROKS_HELM),new Item(GUTHANS_HELM), new Item(TORAGS_HELM), new Item(VERACS_HELM), new Item(KARILS_LEATHERTOP), new Item(AHRIMS_ROBETOP), new Item(DHAROKS_PLATEBODY), new Item(GUTHANS_PLATEBODY), new Item(TORAGS_PLATEBODY), new Item(VERACS_BRASSARD), new Item(KARILS_LEATHERSKIRT), new Item(AHRIMS_ROBESKIRT), new Item(DHAROKS_PLATELEGS), new Item(GUTHANS_CHAINSKIRT), new Item(TORAGS_PLATELEGS), new Item(VERACS_PLATESKIRT), new Item(KARILS_CROSSBOW), new Item(AHRIMS_STAFF), new Item(DHAROKS_GREATAXE), new Item(GUTHANS_WARSPEAR), new Item(TORAGS_HAMMERS), new Item(VERACS_FLAIL), new Item(BOLT_RACK)),

    BRYOPHYTA(AttributeKey.KC_BRYOPHYTA, LogType.BOSSES, "Bryophyta", new int[]{NpcIdentifiers.BRYOPHYTA}, AttributeKey.BRYOPHYTA_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2)},
        //Drops
        new Item(BRYOPHYTAS_ESSENCE)),//TODO pet?

    CALLISTO(AttributeKey.CALLISTOS_KILLED, LogType.BOSSES, "Callisto", new int[]{NpcIdentifiers.CALLISTO, NpcIdentifiers.CALLISTO_6609}, AttributeKey.CALLISTO_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2)},
        //Drops
        new Item(CALLISTO_CUB), new Item(TYRANNICAL_RING), new Item(DRAGON_PICKAXE), new Item(DRAGON_2H_SWORD)),

    CERBERUS(AttributeKey.CERBERUS_KILLED, LogType.BOSSES, "Cerberus", new int[]{NpcIdentifiers.CERBERUS}, AttributeKey.CERBERUS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(PRIMORDIAL_BOOTS_OR), new Item(PEGASIAN_BOOTS_OR), new Item(ETERNAL_BOOTS_OR)},
        //Drops
        new Item(HELLPUPPY), new Item(ETERNAL_CRYSTAL), new Item(PEGASIAN_CRYSTAL), new Item(PRIMORDIAL_CRYSTAL), new Item(JAR_OF_SOULS), new Item(SMOULDERING_STONE), new Item(KEY_MASTER_TELEPORT)),

    CHAOS_ELEMENTAL(AttributeKey.CHAOS_ELEMENTALS_KILLED, LogType.BOSSES, "Chaos Elemental", new int[]{NpcIdentifiers.CHAOS_ELEMENTAL}, AttributeKey.CHAOS_ELEMENTAL_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(PET_CHAOS_ELEMENTAL), new Item(DRAGON_PICKAXE), new Item(DRAGON_2H_SWORD)),

    CHAOS_FANATIC(AttributeKey.CHAOS_FANATICS_KILLED, LogType.BOSSES, "Chaos Fanatic", new int[]{NpcIdentifiers.CHAOS_FANATIC}, AttributeKey.CHAOS_FANATIC_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(PET_CHAOS_ELEMENTAL), new Item(ODIUM_SHARD_1), new Item(MALEDICTION_SHARD_1)),

    COMMANDER_ZILYANA(AttributeKey.COMMANDER_ZILYANA_KILLED, LogType.BOSSES, "Commander Zilyana", new int[]{NpcIdentifiers.COMMANDER_ZILYANA}, AttributeKey.COMMANDER_ZILYANA_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS,2), new Item(SARADOMIN_GODSWORD_ORNAMENT_KIT, 1)},
        //Drops
        new Item(PET_ZILYANA), new Item(ARMADYL_CROSSBOW), new Item(SARADOMIN_HILT), new Item(SARADOMIN_SWORD), new Item(SARADOMINS_LIGHT), new Item(GODSWORD_SHARD_1), new Item(GODSWORD_SHARD_2), new Item(GODSWORD_SHARD_3)),

    CORPOREAL_BEAST(AttributeKey.CORPOREAL_BEASTS_KILLED, LogType.BOSSES, "Corporeal Beast", new int[]{NpcIdentifiers.CORPOREAL_BEAST}, AttributeKey.CORPOREAL_BEAST_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS, 3), new Item(RING_OF_ELYSIAN, 1)},
        //Drops
        new Item(PET_DARK_CORE), new Item(ELYSIAN_SIGIL), new Item(SPECTRAL_SIGIL), new Item(ARCANE_SIGIL), new Item(HOLY_ELIXIR), new Item(SPIRIT_SHIELD)),

    CRAZY_ARCHAEOLOGIST(AttributeKey.CRAZY_ARCHAEOLOGISTS_KILLED, LogType.BOSSES, "Crazy Archaeologist", new int[]{NpcIdentifiers.CRAZY_ARCHAEOLOGIST}, AttributeKey.CRAZY_ARCHAEOLOGIST_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2)},
        //Drops
        new Item(ODIUM_SHARD_2), new Item(MALEDICTION_SHARD_2), new Item(FEDORA)),

    DAGANNOTH_KINGS(null, LogType.BOSSES, "Dagannoth Kings", new int[]{NpcIdentifiers.DAGANNOTH_REX, NpcIdentifiers.DAGANNOTH_PRIME, NpcIdentifiers.DAGANNOTH_SUPREME}, AttributeKey.DAGANNOTH_KINGS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3)},
        //Drops
        new Item(PET_DAGANNOTH_PRIME), new Item(PET_DAGANNOTH_SUPREME), new Item(PET_DAGANNOTH_REX), new Item(BERSERKER_RING), new Item(ARCHERS_RING), new Item(SEERS_RING), new Item(WARRIOR_RING), new Item(DRAGON_AXE), new Item(SEERCULL), new Item(MUD_BATTLESTAFF)),

    DEMONIC_GORILLA(AttributeKey.DEMONIC_GORILLAS_KILLED, LogType.BOSSES, "Demonic Gorilla", new int[]{NpcIdentifiers.DEMONIC_GORILLA, NpcIdentifiers.DEMONIC_GORILLA_7145, NpcIdentifiers.DEMONIC_GORILLA_7146, NpcIdentifiers.DEMONIC_GORILLA_7147, NpcIdentifiers.DEMONIC_GORILLA_7148, NpcIdentifiers.DEMONIC_GORILLA_7149}, AttributeKey.DEMONIC_GORILLA_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(DRAGON_JAVELIN), new Item(ZENYTE_SHARD), new Item(BALLISTA_LIMBS), new Item(BALLISTA_SPRING), new Item(LIGHT_FRAME), new Item(HEAVY_FRAME), new Item(MONKEY_TAIL)),

    GENERAL_GRAARDOR(AttributeKey.GENERAL_GRAARDOR_KILLED, LogType.BOSSES, "General Graardor", new int[]{NpcIdentifiers.GENERAL_GRAARDOR}, AttributeKey.GENERAL_GRAARDOR_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS,2), new Item(BANDOS_GODSWORD_ORNAMENT_KIT, 1)},
        //Drops
        new Item(PET_GENERAL_GRAARDOR), new Item(BANDOS_CHESTPLATE), new Item(BANDOS_TASSETS), new Item(BANDOS_BOOTS), new Item(BANDOS_HILT), new Item(GODSWORD_SHARD_1), new Item(GODSWORD_SHARD_2), new Item(GODSWORD_SHARD_3)),

    GIANT_MOLE(AttributeKey.KC_GIANTMOLE, LogType.BOSSES, "Giant Mole", new int[]{NpcIdentifiers.GIANT_MOLE}, AttributeKey.GIANT_MOLE_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(NIFFLER), new Item(MOLE_SKIN), new Item(MOLE_CLAW)),

    /*TODO
    GROTESQUE_GUARDIANS(AttributeKey.GROTESQUE_GUARDIANS, LogType.BOSSES, "Grotesque Guardians", new int[]{NpcIdentifiers.DUSK, NpcIdentifiers.DAWN},  AttributeKey.GROTESQUE_GUARDIANS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(NOON), new Item(BLACK_TOURMALINE_CORE), new Item(GRANITE_GLOVES), new Item(GRANITE_RING), new Item(GRANITE_HAMMER), new Item(JAR_OF_STONE), new Item(GRANITE_DUST)),
    */

    KRIL_TSUTSAROTH(AttributeKey.KRIL_TSUTSAROTHS_KILLED, LogType.BOSSES, "K'ril Tsutsaroth", new int[]{NpcIdentifiers.KRIL_TSUTSAROTH}, AttributeKey.KRIL_TSUTSAROTH_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS,2), new Item(ZAMORAK_GODSWORD_ORNAMENT_KIT, 1)},
        //Drops
        new Item(PET_KRIL_TSUTSAROTH), new Item(STAFF_OF_THE_DEAD), new Item(ZAMORAKIAN_SPEAR), new Item(STEAM_BATTLESTAFF), new Item(ZAMORAK_HILT), new Item(GODSWORD_SHARD_1), new Item(GODSWORD_SHARD_2), new Item(GODSWORD_SHARD_3)),

    KALPHITE_QUEEN(AttributeKey.KC_KQ, LogType.BOSSES, "Kalphite Queen", new int[]{NpcIdentifiers.KALPHITE_QUEEN_6501}, AttributeKey.KALPHITE_QUEEN_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(KALPHITE_PRINCESS), new Item(KQ_HEAD), new Item(JAR_OF_SAND), new Item(DRAGON_2H_SWORD), new Item(DRAGON_CHAINBODY)),

    KING_BLACK_DRAGON(AttributeKey.KING_BLACK_DRAGONS_KILLED, LogType.BOSSES, "King Black Dragon", new int[]{NpcIdentifiers.KING_BLACK_DRAGON}, AttributeKey.KING_BLACK_DRAGON_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS, 2)},
        //Drops
        new Item(PRINCE_BLACK_DRAGON), new Item(KBD_HEADS), new Item(DRAGON_PICKAXE), new Item(DRACONIC_VISAGE)),

    KRAKEN(AttributeKey.KRAKENS_KILLED, LogType.BOSSES, "Kraken", new int[]{NpcIdentifiers.KRAKEN, NpcIdentifiers.WHIRLPOOL_496}, AttributeKey.KRAKEN_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(PET_KRAKEN), new Item(KRAKEN_TENTACLE), new Item(TRIDENT_OF_THE_SEAS), new Item(JAR_OF_DIRT)),

    KREEARRA(AttributeKey.KREE_ARRA_KILLED, LogType.BOSSES, "Kree'Arra", new int[]{NpcIdentifiers.KREEARRA}, AttributeKey.KREEARRA_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS,2), new Item(ARMADYL_GODSWORD_ORNAMENT_KIT, 1)},
        //Drops
        new Item(PET_KREEARRA), new Item(ARMADYL_HELMET), new Item(ARMADYL_CHESTPLATE), new Item(ARMADYL_CHAINSKIRT), new Item(ARMADYL_HILT), new Item(GODSWORD_SHARD_1), new Item(GODSWORD_SHARD_2), new Item(GODSWORD_SHARD_3)),

    LAVA_DRAGON(AttributeKey.LAVA_DRAGONS_KILLED, LogType.BOSSES, "Lava Dragon", new int[]{NpcIdentifiers.LAVA_DRAGON}, AttributeKey.LAVA_DRAGON_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3)},
        //Drops
        new Item(DRAGONFIRE_SHIELD), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(KEY_OF_DROPS)),

    LIZARDMAN_SHAMAN(AttributeKey.LIZARDMAN_SHAMANS_KILLED, LogType.BOSSES, "Lizardman Shaman", new int[]{NpcIdentifiers.LIZARDMAN_SHAMAN, NpcIdentifiers.LIZARDMAN_SHAMAN_6767}, AttributeKey.LIZARDMAN_SHAMAN_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 5)},
        //Drops
        new Item(DRAGON_WARHAMMER)),

    SCORPIA(AttributeKey.SCORPIAS_KILLED, LogType.BOSSES, "Scorpia", new int[]{NpcIdentifiers.SCORPIA}, AttributeKey.SCORPIA_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(SCORPIAS_OFFSPRING), new Item(ODIUM_SHARD_3), new Item(MALEDICTION_SHARD_3)),

    SKOTIZO(AttributeKey.SKOTIZOS_KILLED, LogType.BOSSES, "Skotizo", new int[]{NpcIdentifiers.SKOTIZO}, AttributeKey.SKOTIZO_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3)},
        //Drops
        new Item(SKOTOS), new Item(JAR_OF_DARKNESS), new Item(DARK_CLAW), new Item(DARK_TOTEM), new Item(UNCUT_ONYX), new Item(ANCIENT_SHARD)),

    THERMONUCLEAR_SMOKE_DEVIL(AttributeKey.THERMONUCLEAR_SMOKE_DEVILS_KILLED, LogType.BOSSES, "Thermonuclear Smoke Devil", new int[]{NpcIdentifiers.THERMONUCLEAR_SMOKE_DEVIL}, AttributeKey.THERMONUCLEAR_SMOKE_DEVIL_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS), new Item(OCCULT_ORNAMENT_KIT)},
        //Drops
        new Item(PET_SMOKE_DEVIL), new Item(OCCULT_NECKLACE), new Item(SMOKE_BATTLESTAFF), new Item(DRAGON_CHAINBODY)),

    TZTOK_JAD(AttributeKey.JADS_KILLED, LogType.BOSSES, "Tztok-Jad", new int[]{NpcIdentifiers.TZTOKJAD}, AttributeKey.TZTOK_JAD_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(TZREKJAD), new Item(FIRE_CAPE)),

    VENENATIS(AttributeKey.VENENATIS_KILLED, LogType.BOSSES, "Venenatis", new int[]{NpcIdentifiers.VENENATIS, NpcIdentifiers.VENENATIS_6610}, AttributeKey.VENENATIS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(VENENATIS_SPIDERLING), new Item(TREASONOUS_RING), new Item(DRAGON_PICKAXE), new Item(DRAGON_2H_SWORD)),

    VETION(AttributeKey.VETIONS_KILLED, LogType.BOSSES, "Vet'ion", new int[]{NpcIdentifiers.VETION_REBORN}, AttributeKey.VETION_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(VETION_JR), new Item(RING_OF_THE_GODS), new Item(DRAGON_PICKAXE), new Item(DRAGON_2H_SWORD)),

    VORKATH(AttributeKey.VORKATHS_KILLED, LogType.BOSSES, "Vorkath", new int[]{NpcIdentifiers.VORKATH}, AttributeKey.VORKATH_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX, 1), new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(DIAMOND_DRAGON_BOLTS_E, 5000)},
        //Drops
        new Item(VORKI), new Item(VORKATHS_HEAD_21907), new Item(DRACONIC_VISAGE), new Item(SKELETAL_VISAGE), new Item(JAR_OF_DECAY), new Item(DRAGONBONE_NECKLACE)),

    ZULRAH(AttributeKey.ZULRAHS_KILLED, LogType.BOSSES, "Zulrah", new int[]{NpcIdentifiers.ZULRAH, NpcIdentifiers.ZULRAH_2044, NpcIdentifiers.ZULRAH_2043}, AttributeKey.ZULRAH_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX), new Item(CustomItemIdentifiers.MYSTERY_TICKET,2), new Item(MAGMA_BLOWPIPE, 1)},
        //Drops
        new Item(PET_SNAKELING), new Item(TANZANITE_MUTAGEN), new Item(MAGMA_MUTAGEN), new Item(JAR_OF_SWAMP), new Item(MAGIC_FANG), new Item(SERPENTINE_VISAGE), new Item(TANZANITE_FANG), new Item(UNCUT_ONYX), new Item(ZULRAHS_SCALES)),

    ALCHEMICAL_HYDRA(AttributeKey.ALCHY_KILLED, LogType.BOSSES, "Alchemical Hydra", new int[]{NpcIdentifiers.ALCHEMICAL_HYDRA, NpcIdentifiers.ALCHEMICAL_HYDRA_8616, NpcIdentifiers.ALCHEMICAL_HYDRA_8617, NpcIdentifiers.ALCHEMICAL_HYDRA_8618, NpcIdentifiers.ALCHEMICAL_HYDRA_8619, NpcIdentifiers.ALCHEMICAL_HYDRA_8620, NpcIdentifiers.ALCHEMICAL_HYDRA_8621, NpcIdentifiers.ALCHEMICAL_HYDRA_8622}, AttributeKey.ALCHEMICAL_HYDRA_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3)},
        //Drops
        new Item(IKKLE_HYDRA), new Item(HYDRAS_CLAW), new Item(HYDRA_TAIL), new Item(HYDRA_LEATHER), new Item(HYDRAS_FANG), new Item(HYDRAS_EYE), new Item(HYDRAS_HEART), new Item(DRAGON_KNIFE), new Item(DRAGON_THROWNAXE), new Item(JAR_OF_CHEMICALS), new Item(ALCHEMICAL_HYDRA_HEADS)),

    THE_NIGHTMARE(AttributeKey.THE_NIGHTMARE_KC, LogType.BOSSES, "The nightmare", new int[]{THE_NIGHTMARE_9430}, AttributeKey.THE_NIGTHMARE_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX, 3), new Item(CustomItemIdentifiers.MYSTERY_TICKET,3), new Item(COINS_995, 500_000_000), new Item(WRATH_RUNE,10_000)},
        //Drops
        new Item(LITTLE_NIGHTMARE), new Item(INQUISITORS_MACE), new Item(INQUISITORS_GREAT_HELM), new Item(INQUISITORS_HAUBERK), new Item(INQUISITORS_PLATESKIRT), new Item(NIGHTMARE_STAFF), new Item(VOLATILE_ORB), new Item(HARMONISED_ORB), new Item(ELDRITCH_ORB), new Item(JAR_OF_DREAMS)),

    NEX(AttributeKey.NEX_KC, LogType.BOSSES, "Nex", new int[]{NpcIdentifiers.NEX, NpcIdentifiers.NEX_11279, NpcIdentifiers.NEX_11280, NpcIdentifiers.NEX_11281, NpcIdentifiers.NEX_11282}, AttributeKey.NEX_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX, 1), new Item(CustomItemIdentifiers.MYSTERY_TICKET,5)},
        //Drops
        new Item(NEXLING), new Item(ANCIENT_HILT), new Item(NIHIL_HORN), new Item(ZARYTE_VAMBRACES), new Item(TORVA_FULL_HELM), new Item(TORVA_PLATEBODY), new Item(TORVA_PLATELEGS), new Item(NIHIL_SHARD)),

    SARACHNIS(AttributeKey.SARACHNIS, LogType.BOSSES, "Sarachnis", new int[]{NpcIdentifiers.SARACHNIS}, AttributeKey.SARACHNIS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3)},
        //Drops
        new Item(SRARACHA), new Item(JAR_OF_EYES), new Item(GIANT_EGG_SACFULL), new Item(SARACHNIS_CUDGEL)),

    // Custom bosses
    BARRELCHEST(AttributeKey.BARRELCHESTS_KILLED, LogType.BOSSES, "Barrelchests", new int[]{NpcIdentifiers.BARRELCHEST_6342}, AttributeKey.BARRELCHEST_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 1), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(BARRELCHEST_PET), new Item(ANCIENT_WARRIOR_SWORD), new Item(ANCIENT_WARRIOR_AXE), new Item(ANCIENT_WARRIOR_MAUL)),

    ZOMBIES_CHAMPION(AttributeKey.ZOMBIES_CHAMPIONS_KILLED, LogType.BOSSES, "Zombies Champion", new int[]{NpcIdentifiers.ZOMBIES_CHAMPION}, AttributeKey.ZOMBIES_CHAMPION_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX, 1), new Item(CustomItemIdentifiers.MYSTERY_TICKET,2), new Item(KEY_OF_DROPS,3)},
        //Drops
        new Item(CustomItemIdentifiers.ZOMBIES_CHAMPION_PET), new Item(VESTAS_LONGSWORD), new Item(VESTAS_CHAINBODY), new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_FULL_HELM), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(MORRIGANS_COIF), new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS), new Item(ZURIELS_STAFF), new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM)),

    BRUTAL_LAVA_DRAGON(AttributeKey.BRUTAL_LAVA_DRAGONS_KILLED, LogType.BOSSES, "Brutal Lava Dragon", new int[]{BRUTAL_LAVA_DRAGON_FLYING}, AttributeKey.BRUTAL_LAVA_DRAGON_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS,2)},
        //Drops
        new Item(BABY_LAVA_DRAGON), new Item(LAVA_DHIDE_COIF), new Item(LAVA_DHIDE_BODY), new Item(LAVA_DHIDE_CHAPS), new Item(KEY_OF_DROPS), new Item(LAVA_PARTYHAT), new Item(INFERNAL_CAPE)),

    CORRUPTED_NECHRYARCH(AttributeKey.CORRUPTED_NECHRYARCHS_KILLED, LogType.BOSSES, "Corrupted Nechryarch", new int[]{CustomNpcIdentifiers.CORRUPTED_NECHRYARCH}, AttributeKey.CORRUPTED_NECHRYARCH_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX, 1)},
        //Drops
        new Item(CustomItemIdentifiers.PET_CORRUPTED_NECHRYARCH), new Item(KEY_OF_DROPS), new Item(CORRUPTED_BOOTS)),

    ANCIENT_CHAOS_ELEMENTAL(AttributeKey.ANCIENT_CHAOS_ELEMENTALS_KILLED, LogType.BOSSES, "Ancient Chaos Elemental", new int[]{CustomNpcIdentifiers.ANCIENT_CHAOS_ELEMENTAL}, AttributeKey.ANCIENT_CHAOS_ELEMENTAL_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS,2)},
        //Drops
        new Item(CustomItemIdentifiers.ANCIENT_CHAOS_ELEMENTAL_PET), new Item(RING_OF_VIGOUR)
    ),

    ANCIENT_BARRELCHEST(AttributeKey.ANCIENT_BARRELCHESTS_KILLED, LogType.BOSSES, "Ancient Barrelchest", new int[]{CustomNpcIdentifiers.ANCIENT_BARRELCHEST}, AttributeKey.ANCIENT_BARRELCHEST_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS,3), new Item(TASK_BOTTLE_CASKET,3)},
        //Drops
        new Item(CustomItemIdentifiers.ANCIENT_BARRELCHEST_PET), new Item(ANCIENT_WARRIOR_CLAMP)
    ),

    ANCIENT_KING_BLACK_DRAGON(AttributeKey.ANCIENT_KING_BLACK_DRAGONS_KILLED, LogType.BOSSES, "Ancient King Black Dragon", new int[]{CustomNpcIdentifiers.ANCIENT_KING_BLACK_DRAGON}, AttributeKey.ANCIENT_KING_BLACK_DRAGON_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS,3), new Item(TASK_BOTTLE_CASKET,3)},
        //Drops
        new Item(CustomItemIdentifiers.ANCIENT_KING_BLACK_DRAGON_PET), new Item(ANCIENT_FACEGAURD)
    ),

    KERBEROS(AttributeKey.KERBEROS_KILLED, LogType.BOSSES, "Kerberos", new int[]{CustomNpcIdentifiers.KERBEROS}, AttributeKey.KERBEROS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS,1), new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX,1), new Item(SAELDOR_SHARD, 500)},
        //Drops
        new Item(CustomItemIdentifiers.KERBEROS_PET), new Item(ETERNAL_CRYSTAL), new Item(PEGASIAN_CRYSTAL), new Item(PRIMORDIAL_CRYSTAL), new Item(JAR_OF_SOULS), new Item(SMOULDERING_STONE), new Item(DARK_ARMADYL_CHAINSKIRT)
    ),

    SKORPIOS(AttributeKey.SKORPIOS_KILLED, LogType.BOSSES, "Skorpios", new int[]{CustomNpcIdentifiers.SKORPIOS}, AttributeKey.SKORPIOS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 3), new Item(KEY_OF_DROPS,1), new Item(SAELDOR_SHARD, 250)},
        //Drops
        new Item(CustomItemIdentifiers.SKORPIOS_PET), new Item(KEY_OF_DROPS), new Item(SAELDOR_SHARD)
    ),

    ARACHNE(AttributeKey.ARACHNE_KILLED, LogType.BOSSES, "Arachne", new int[]{CustomNpcIdentifiers.ARACHNE}, AttributeKey.ARACHNE_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS,2)},
        //Drops
        new Item(CustomItemIdentifiers.ARACHNE_PET), new Item(DARK_ARMADYL_HELMET)
    ),

    ARTIO(AttributeKey.ARTIO_KILLED, LogType.BOSSES, "Artio", new int[]{CustomNpcIdentifiers.ARTIO}, AttributeKey.ARTIO_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS,2)},
        //Drops
        new Item(CustomItemIdentifiers.ARTIO_PET), new Item(DARK_ARMADYL_CHESTPLATE)
    ),

    CORRUPTED_HUNLEFF(AttributeKey.CORRUPTED_HUNLEFFS_KILLED, LogType.BOSSES, "Corrupted Hunleff", new int[]{NpcIdentifiers.CORRUPTED_HUNLLEF, NpcIdentifiers.CORRUPTED_HUNLLEF_9036, NpcIdentifiers.CORRUPTED_HUNLLEF_9037}, AttributeKey.CORRUPTED_HUNLEFF_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX, 2), new Item(KEY_OF_DROPS,2)},
        //Drops
        new Item(YOUNGLLEF), new Item(CRYSTAL_HELM), new Item(CRYSTAL_BODY), new Item(CRYSTAL_LEGS), new Item(KEY_OF_DROPS), new Item(CORRUPTING_STONE)
    ),

    REVENANTS(AttributeKey.REVENANTS_KILLED, LogType.OTHER, "Revenants", new int[]{NpcIdentifiers.REVENANT_IMP, NpcIdentifiers.REVENANT_CYCLOPS, NpcIdentifiers.REVENANT_DARK_BEAST, NpcIdentifiers.REVENANT_DEMON, NpcIdentifiers.REVENANT_DRAGON, NpcIdentifiers.REVENANT_GOBLIN, NpcIdentifiers.REVENANT_HELLHOUND, NpcIdentifiers.REVENANT_HOBGOBLIN, NpcIdentifiers.REVENANT_KNIGHT, NpcIdentifiers.REVENANT_ORK, NpcIdentifiers.REVENANT_PYREFIEND}, AttributeKey.REVENANTS_LOG_CLAIMED, new Item[]{new Item(VIGGORAS_CHAINMACE_C), new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX), new Item(CustomItemIdentifiers.MYSTERY_TICKET), new Item(KEY_OF_DROPS)},
        //Drops
        new Item(VESTAS_SPEAR), new Item(VESTAS_LONGSWORD), new Item(VESTAS_CHAINBODY), new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_FULL_HELM), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(ZURIELS_STAFF), new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM), new Item(MORRIGANS_COIF), new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS), new Item(MORRIGANS_JAVELIN), new Item(MORRIGANS_THROWING_AXE), new Item(VIGGORAS_CHAINMACE), new Item(CRAWS_BOW), new Item(THAMMARONS_SCEPTRE), new Item(AMULET_OF_AVARICE), new Item(BRACELET_OF_ETHEREUM), new Item(ANCIENT_RELIC), new Item(ANCIENT_EFFIGY), new Item(ANCIENT_MEDALLION), new Item(ItemIdentifiers.ANCIENT_STATUETTE), new Item(ANCIENT_TOTEM), new Item(ANCIENT_EMBLEM), new Item(REVENANT_CAVE_TELEPORT), new Item(REVENANT_ETHER)
    ),

    ANCIENT_REVENANTS(AttributeKey.ANCIENT_REVENANTS_KILLED, LogType.OTHER, "Ancient Revenants", new int[]{ANCIENT_REVENANT_DARK_BEAST, ANCIENT_REVENANT_ORK, ANCIENT_REVENANT_CYCLOPS, ANCIENT_REVENANT_DRAGON, ANCIENT_REVENANT_KNIGHT}, AttributeKey.ANCIENT_REVENANTS_LOG_CLAIMED, new Item[]{new Item(VIGGORAS_CHAINMACE_C), new Item(ANCIENT_VESTAS_LONGSWORD), new Item(ANCIENT_STATIUSS_WARHAMMER), new Item(ItemIdentifiers.LARRANS_KEY,20)},
        //Drops
        new Item(VESTAS_SPEAR), new Item(VESTAS_LONGSWORD), new Item(VESTAS_CHAINBODY), new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_FULL_HELM), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(ZURIELS_STAFF), new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM), new Item(MORRIGANS_COIF), new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS), new Item(MORRIGANS_JAVELIN), new Item(MORRIGANS_THROWING_AXE), new Item(VIGGORAS_CHAINMACE), new Item(CRAWS_BOW), new Item(THAMMARONS_SCEPTRE), new Item(AMULET_OF_AVARICE), new Item(DARK_ANCIENT_EMBLEM), new Item(DARK_ANCIENT_RELIC), new Item(DARK_ANCIENT_EFFIGY), new Item(DARK_ANCIENT_MEDALLION), new Item(DARK_ANCIENT_STATUETTE), new Item(DARK_ANCIENT_TOTEM), new Item(DARK_ANCIENT_EMBLEM), new Item(ANCIENT_VESTAS_LONGSWORD), new Item(ANCIENT_STATIUSS_WARHAMMER)
    ),

    CHAMBER_OF_SECRETS(AttributeKey.CHAMBER_OF_SECRET_RUNS_COMPLETED, LogType.BOSSES, "Chamber Of Secrets", new int[]{COS_RAIDS_KEY}, AttributeKey.CHAMBER_OF_SECRETS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX, 1), new Item(CustomItemIdentifiers.MYSTERY_TICKET,2), new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX,3)},
        //Drops
        new Item(CustomItemIdentifiers.NAGINI), new Item(FENRIR_GREYBACK_JR), new Item(FLUFFY_JR), new Item(CENTAUR_MALE), new Item(CENTAUR_FEMALE), new Item(DEMENTOR_PET),
        new Item(TOM_RIDDLE_DIARY), new Item(MARVOLO_GAUNTS_RING), new Item(CLOAK_OF_INVISIBILITY), new Item(ELDER_WAND_HANDLE), new Item(ELDER_WAND_STICK), new Item(SWORD_OF_GRYFFINDOR), new Item(TALONHAWK_CROSSBOW), new Item(SALAZAR_SLYTHERINS_LOCKET)),

    CHAMBER_OF_XERIC(AttributeKey.CHAMBER_OF_SECRET_RUNS_COMPLETED, LogType.BOSSES, "Chambers of Xeric", new int[]{COX_RAIDS_KEY}, AttributeKey.CHAMBER_OF_XERIC_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX,5), new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX, 1), new Item(CustomItemIdentifiers.RAIDS_MYSTERY_BOX,5)},
        //Drops
        new Item(OLMLET), new Item(METAMORPHIC_DUST), new Item(TWISTED_BOW), new Item(ELDER_MAUL), new Item(KODAI_INSIGNIA), new Item(DRAGON_CLAWS), new Item(ANCESTRAL_HAT), new Item(ANCESTRAL_ROBE_TOP), new Item(ANCESTRAL_ROBE_BOTTOM), new Item(DINHS_BULWARK), new Item(DEXTEROUS_PRAYER_SCROLL), new Item(ARCANE_PRAYER_SCROLL), new Item(DRAGON_HUNTER_CROSSBOW), new Item(TWISTED_BUCKLER), new Item(TORN_PRAYER_SCROLL), new Item(DARK_RELIC), new Item(ONYX), new Item(TWISTED_ANCESTRAL_COLOUR_KIT)),

    THEATRE_OF_BLOOD(AttributeKey.THEATRE_OF_BLOOD_RUNS_COMPLETED, LogType.BOSSES, "Theatre of Blood", new int[]{TOB_RAIDS_KEY}, AttributeKey.THEATRE_OF_BLOOD_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX,1), new Item(CustomItemIdentifiers.RAIDS_MYSTERY_BOX,3), new Item(CustomItemIdentifiers.MYSTERY_TICKET,2)},
        //Drops
        new Item(LIL_ZIK), new Item(SCYTHE_OF_VITUR), new Item(GHRAZI_RAPIER), new Item(SANGUINESTI_STAFF), new Item(JUSTICIAR_FACEGUARD), new Item(JUSTICIAR_CHESTGUARD), new Item(JUSTICIAR_LEGGUARDS), new Item(AVERNIC_DEFENDER_HILT)),

    //Mystery box
    DONATOR_MYSTERY_BOX(AttributeKey.DONATOR_MYSTERY_BOXES_OPENED, LogType.MYSTERY_BOX, "Donator Mystery Box", new int[]{CustomItemIdentifiers.DONATOR_MYSTERY_BOX}, AttributeKey.DONATOR_MYSTERY_BOX_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX, 10), new Item(KEY_OF_DROPS,5)},
        //Drops
        new Item(GREEN_HALLOWEEN_MASK), new Item(BLUE_HALLOWEEN_MASK), new Item(RED_HALLOWEEN_MASK), new Item(BLACK_HWEEN_MASK), new Item(SANTA_HAT),
        new Item(BLACK_SANTA_HAT), new Item(INVERTED_SANTA_HAT), new Item(RED_PARTYHAT), new Item(YELLOW_PARTYHAT), new Item(BLUE_PARTYHAT),
        new Item(GREEN_PARTYHAT), new Item(PURPLE_PARTYHAT), new Item(WHITE_PARTYHAT), new Item(BLACK_PARTYHAT), new Item(RAINBOW_PARTYHAT),
        new Item(PARTYHAT__SPECS), new Item(CHRISTMAS_CRACKER), new Item(_3RD_AGE_WAND), new Item(_3RD_AGE_BOW), new Item(_3RD_AGE_LONGSWORD),
        new Item(_3RD_AGE_CLOAK), new Item(_3RD_AGE_RANGE_TOP), new Item(_3RD_AGE_RANGE_LEGS), new Item(_3RD_AGE_RANGE_COIF), new Item(_3RD_AGE_VAMBRACES),
        new Item(_3RD_AGE_ROBE_TOP), new Item(_3RD_AGE_ROBE), new Item(_3RD_AGE_MAGE_HAT), new Item(_3RD_AGE_AMULET), new Item(_3RD_AGE_PLATELEGS),
        new Item(_3RD_AGE_PLATEBODY), new Item(_3RD_AGE_FULL_HELMET), new Item(_3RD_AGE_KITESHIELD)),

    //Mystery ticket
    MYSTERY_TICKET(AttributeKey.MYSTERY_TICKETS_OPENED, LogType.MYSTERY_BOX, "Mystery Ticket", new int[]{CustomItemIdentifiers.MYSTERY_TICKET}, AttributeKey.MYSTERY_TICKET_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.MYSTERY_TICKET, 5), new Item(KEY_OF_DROPS,5)},
        //Drops
        new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(CustomItemIdentifiers.MYSTERY_CHEST), new Item(BANDOS_TASSETS), new Item(BANDOS_CHESTPLATE),
        new Item(ARMADYL_CHESTPLATE), new Item(ARMADYL_CHAINSKIRT), new Item(SARADOMIN_GODSWORD), new Item(ARMADYL_GODSWORD), new Item(DRAGON_WARHAMMER),
        new Item(DRAGON_HUNTER_LANCE), new Item(SPECTRAL_SPIRIT_SHIELD)),

    //Mystery chest
    MYSTERY_CHEST(AttributeKey.MYSTERY_CHESTS_OPENED, LogType.MYSTERY_BOX, "Mystery Chest", new int[]{CustomItemIdentifiers.MYSTERY_CHEST}, AttributeKey.MYSTERY_CHEST_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.MYSTERY_CHEST, 1), new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX)},
        //Drops
        new Item(ELYSIAN_SPIRIT_SHIELD), new Item(TWISTED_BOW), new Item(SCYTHE_OF_VITUR), new Item(DARK_BANDOS_CHESTPLATE), new Item(DARK_BANDOS_TASSETS), new Item(BLADE_OF_SAELDOR_C_25882),
        new Item(ANCESTRAL_ROBE_BOTTOM_I), new Item(ANCESTRAL_ROBE_TOP_I), new Item(SALAZAR_SLYTHERINS_LOCKET)),

    //Pets mystery box
    PETS_MYSTERY_BOX(AttributeKey.PETS_MYSTERY_BOXES_OPENED, LogType.MYSTERY_BOX, "Pets Mystery Box", new int[]{CustomItemIdentifiers.PETS_MYSTERY_BOX}, AttributeKey.PETS_MYSTERY_BOX_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX, 2), new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX)},
        //Drops
        new Item(CustomItemIdentifiers.JAWA_PET),
        new Item(CustomItemIdentifiers.ZRIAWK),
        new Item(CustomItemIdentifiers.FAWKES),
        new Item(CustomItemIdentifiers.BABY_ARAGOG),
        new Item(CustomItemIdentifiers.BLOOD_REAPER),
        new Item(ItemIdentifiers.YOUNGLLEF),
        new Item(CustomItemIdentifiers.MINI_NECROMANCER),
        new Item(CustomItemIdentifiers.ARTIO_PET),
        new Item(CustomItemIdentifiers.ANCIENT_BARRELCHEST_PET),
        new Item(CustomItemIdentifiers.PET_CORRUPTED_NECHRYARCH),
        new Item(CustomItemIdentifiers.GRIM_REAPER_PET),
        new Item(CustomItemIdentifiers.NIFFLER),
        new Item(CustomItemIdentifiers.WAMPA_PET),
        new Item(CustomItemIdentifiers.KERBEROS_PET),
        new Item(CustomItemIdentifiers.SKORPIOS_PET),
        new Item(CustomItemIdentifiers.ARACHNE_PET),
        new Item(CustomItemIdentifiers.DEMENTOR_PET),
        new Item(CustomItemIdentifiers.FENRIR_GREYBACK_JR),
        new Item(CustomItemIdentifiers.FLUFFY_JR),
        new Item(CustomItemIdentifiers.ANCIENT_KING_BLACK_DRAGON_PET),
        new Item(CustomItemIdentifiers.ANCIENT_CHAOS_ELEMENTAL_PET),
        new Item(CustomItemIdentifiers.FOUNDER_IMP),
        new Item(CustomItemIdentifiers.BABY_LAVA_DRAGON),
        new Item(CustomItemIdentifiers.JALTOK_JAD),
        new Item(CustomItemIdentifiers.RING_OF_ELYSIAN),
        new Item(CustomItemIdentifiers.GENIE_PET),
        new Item(CustomItemIdentifiers.DHAROK_PET),
        new Item(CustomItemIdentifiers.ZOMBIES_CHAMPION_PET),
        new Item(CustomItemIdentifiers.BABY_ABYSSAL_DEMON),
        new Item(CustomItemIdentifiers.BABY_DARK_BEAST_EGG),
        new Item(CustomItemIdentifiers.BABY_SQUIRT),
        new Item(CustomItemIdentifiers.CENTAUR_FEMALE),
        new Item(CustomItemIdentifiers.CENTAUR_MALE)),

    //Other
    SLAYER(null, LogType.OTHER, "Slayer",
        //Npcs that drop these items
        new int[]{NpcIdentifiers.CRAWLING_HAND_448, NpcIdentifiers.CRAWLING_HAND_449, NpcIdentifiers.CRAWLING_HAND_450, NpcIdentifiers.CRAWLING_HAND_451, NpcIdentifiers.CRAWLING_HAND_452, NpcIdentifiers.CRAWLING_HAND_453, NpcIdentifiers.CRAWLING_HAND_454, NpcIdentifiers.CRAWLING_HAND_455, NpcIdentifiers.CRAWLING_HAND_456, NpcIdentifiers.CRAWLING_HAND_457, NpcIdentifiers.CRUSHING_HAND,
            NpcIdentifiers.COCKATRICE_419, NpcIdentifiers.COCKATRICE_420, NpcIdentifiers.COCKATHRICE, NpcIdentifiers.BASILISK_417, NpcIdentifiers.BASILISK_418, NpcIdentifiers.BASILISK_9283, NpcIdentifiers.BASILISK_9284, NpcIdentifiers.BASILISK_9285, NpcIdentifiers.BASILISK_9286, NpcIdentifiers.BASILISK_KNIGHT, NpcIdentifiers.BASILISK_SENTINEL, NpcIdentifiers.BASILISK_YOUNGLING, NpcIdentifiers.MONSTROUS_BASILISK, NpcIdentifiers.MONSTROUS_BASILISK_9287, NpcIdentifiers.MONSTROUS_BASILISK_9288,
            NpcIdentifiers.KURASK_410, NpcIdentifiers.KURASK_411, NpcIdentifiers.KING_KURASK, NpcIdentifiers.ABYSSAL_DEMON_415, NpcIdentifiers.ABYSSAL_DEMON_416, NpcIdentifiers.ABYSSAL_DEMON_7241, NpcIdentifiers.GREATER_ABYSSAL_DEMON, NpcIdentifiers.ABYSSAL_SIRE, NpcIdentifiers.ABYSSAL_SIRE_5887, NpcIdentifiers.ABYSSAL_SIRE_5888, NpcIdentifiers.ABYSSAL_SIRE_5889, NpcIdentifiers.ABYSSAL_SIRE_5890, NpcIdentifiers.ABYSSAL_SIRE_5891, NpcIdentifiers.ABYSSAL_SIRE_5908,
            NpcIdentifiers.GARGOYLE, NpcIdentifiers.GARGOYLE_1543, NpcIdentifiers.MARBLE_GARGOYLE_7408, NpcIdentifiers.TUROTH, NpcIdentifiers.TUROTH_427, NpcIdentifiers.TUROTH_428, NpcIdentifiers.TUROTH_429, NpcIdentifiers.TUROTH_430, NpcIdentifiers.TUROTH_431, NpcIdentifiers.TUROTH_432, NpcIdentifiers.CAVE_HORROR, NpcIdentifiers.CAVE_HORROR_1048, NpcIdentifiers.CAVE_HORROR_1049, NpcIdentifiers.CAVE_HORROR_1050, NpcIdentifiers.CAVE_HORROR_1051, NpcIdentifiers.CAVE_ABOMINATION,
            NpcIdentifiers.TALONED_WYVERN, NpcIdentifiers.SPITTING_WYVERN, NpcIdentifiers.LONGTAILED_WYVERN, NpcIdentifiers.ANCIENT_WYVERN, NpcIdentifiers.KING_BLACK_DRAGON, NpcIdentifiers.KING_BLACK_DRAGON_6502, NpcIdentifiers.BLACK_DRAGON, NpcIdentifiers.BLACK_DRAGON_253, NpcIdentifiers.BLACK_DRAGON_254, NpcIdentifiers.BLACK_DRAGON_255, NpcIdentifiers.BLACK_DRAGON_256, NpcIdentifiers.BLACK_DRAGON_257, NpcIdentifiers.BLACK_DRAGON_258, NpcIdentifiers.BLACK_DRAGON_259, NpcIdentifiers.BLACK_DRAGON_7861, NpcIdentifiers.BLACK_DRAGON_7862, NpcIdentifiers.BLACK_DRAGON_7863, NpcIdentifiers.BLACK_DRAGON_8084, NpcIdentifiers.BLACK_DRAGON_8085, NpcIdentifiers.BRUTAL_BLACK_DRAGON, NpcIdentifiers.BRUTAL_BLACK_DRAGON_8092, NpcIdentifiers.BRUTAL_BLACK_DRAGON_8092,
            NpcIdentifiers.VORKATH_8061, NpcIdentifiers.ADAMANT_DRAGON, NpcIdentifiers.ADAMANT_DRAGON_8090, NpcIdentifiers.RUNE_DRAGON, NpcIdentifiers.RUNE_DRAGON_8031, NpcIdentifiers.RUNE_DRAGON_8091, NpcIdentifiers.LAVA_DRAGON, NpcIdentifiers.MITHRIL_DRAGON, NpcIdentifiers.MITHRIL_DRAGON_8088, NpcIdentifiers.MITHRIL_DRAGON_8089, NpcIdentifiers.SKELETAL_WYVERN_466, NpcIdentifiers.SKELETAL_WYVERN_467, NpcIdentifiers.SKELETAL_WYVERN_468, NpcIdentifiers.SPIRITUAL_MAGE, NpcIdentifiers.SPIRITUAL_MAGE_2244, NpcIdentifiers.SPIRITUAL_MAGE_3161, NpcIdentifiers.SPIRITUAL_MAGE_3168,
            NpcIdentifiers.WHIRLPOOL, NpcIdentifiers.WHIRLPOOL_496, NpcIdentifiers.CAVE_KRAKEN, NpcIdentifiers.KRAKEN, NpcIdentifiers.DARK_BEAST, NpcIdentifiers.DARK_BEAST_7250, NpcIdentifiers.NIGHT_BEAST, NpcIdentifiers.SMOKE_DEVIL, NpcIdentifiers.SMOKE_DEVIL_6639, NpcIdentifiers.SMOKE_DEVIL_6655, NpcIdentifiers.SMOKE_DEVIL_8482, NpcIdentifiers.SMOKE_DEVIL_8483, NpcIdentifiers.NUCLEAR_SMOKE_DEVIL, NpcIdentifiers.THERMONUCLEAR_SMOKE_DEVIL, NpcIdentifiers.KALPHITE_QUEEN_6500, NpcIdentifiers.KALPHITE_QUEEN_6501, NpcIdentifiers.WYRM, NpcIdentifiers.WYRM_8611, NpcIdentifiers.DRAKE_8612, NpcIdentifiers.DRAKE_8613, NpcIdentifiers.HYDRA, NpcIdentifiers.ALCHEMICAL_HYDRA, NpcIdentifiers.ALCHEMICAL_HYDRA_8616, NpcIdentifiers.ALCHEMICAL_HYDRA_8617, NpcIdentifiers.ALCHEMICAL_HYDRA_8618, NpcIdentifiers.ALCHEMICAL_HYDRA_8619, NpcIdentifiers.ALCHEMICAL_HYDRA_8620, NpcIdentifiers.ALCHEMICAL_HYDRA_8621, NpcIdentifiers.ALCHEMICAL_HYDRA_8622, NpcIdentifiers.ALCHEMICAL_HYDRA_8634,
            NpcIdentifiers.BASILISK_KNIGHT, NpcIdentifiers.BASILISK_SENTINEL}, AttributeKey.SLAYER_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SLAYER_KEY, 100), new Item(CustomItemIdentifiers.PETS_MYSTERY_BOX,1), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX,5)},
        //Drops
        new Item(CRAWLING_HAND_7975), new Item(COCKATRICE_HEAD), new Item(BASILISK_HEAD), new Item(KURASK_HEAD), new Item(ABYSSAL_HEAD), new Item(IMBUED_HEART), new Item(ETERNAL_GEM), new Item(DUST_BATTLESTAFF), new Item(MIST_BATTLESTAFF), new Item(ABYSSAL_WHIP), new Item(GRANITE_MAUL_24225), new Item(LEAFBLADED_SWORD), new Item(LEAFBLADED_BATTLEAXE), new Item(BLACK_MASK), new Item(GRANITE_LONGSWORD), new Item(WYVERN_VISAGE), new Item(DRACONIC_VISAGE),
        new Item(DRAGON_BOOTS), new Item(ABYSSAL_DAGGER), new Item(TRIDENT_OF_THE_SEAS), new Item(KRAKEN_TENTACLE), new Item(DARK_BOW), new Item(OCCULT_NECKLACE), new Item(DRAGON_CHAINBODY_3140), new Item(DRAGON_THROWNAXE), new Item(DRAGON_HARPOON), new Item(DRAGON_SWORD), new Item(DRAGON_KNIFE), new Item(DRAKES_TOOTH), new Item(DRAKES_CLAW), new Item(HYDRA_TAIL), new Item(HYDRAS_FANG), new Item(HYDRAS_EYE), new Item(HYDRAS_HEART), new Item(BASILISK_JAW),
        new Item(MOSSY_KEY), new Item(DRAGON_METAL_SLICE), new Item(DRAGON_METAL_LUMP), new Item(DRAGON_LIMBS)),

    //Keys
    LARRANS_KEY(AttributeKey.LARRANS_KEYS_OPENED, LogType.KEYS, "Larrans key", new int[]{ItemIdentifiers.LARRANS_KEY}, AttributeKey.LARRANS_KEY_LOG_CLAIMED, new Item[]{new Item(ItemIdentifiers.LARRANS_KEY, 10), new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX)},
        //Drops
        new Item(DAGONHAI_HAT), new Item(DAGONHAI_ROBE_TOP), new Item(DAGONHAI_ROBE_BOTTOM), new Item(VESTAS_LONGSWORD), new Item(VESTAS_SPEAR), new Item(VESTAS_CHAINBODY),
        new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_FULL_HELM), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(ZURIELS_STAFF),
        new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM), new Item(MORRIGANS_JAVELIN), new Item(MORRIGANS_THROWING_AXE), new Item(MORRIGANS_COIF),
        new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS)),

    CRYSTAL_KEY(AttributeKey.CRYSTAL_KEYS_OPENED, LogType.KEYS, "Crystal key", new int[]{ItemIdentifiers.CRYSTAL_KEY}, AttributeKey.CRYSTAL_KEY_LOG_CLAIMED, new Item[]{new Item(ItemIdentifiers.CRYSTAL_KEY, 5), new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX)},
        //Drops
        new Item(NEW_CRYSTAL_BOW), new Item(NEW_CRYSTAL_SHIELD), new Item(NEW_CRYSTAL_HALBERD_FULL)),

    SLAYER_KEY(AttributeKey.SLAYER_KEYS_OPENED, LogType.KEYS, "Slayer key", new int[]{CustomItemIdentifiers.SLAYER_KEY}, AttributeKey.SLAYER_KEY_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.SLAYER_KEY, 10), new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX)},
        //Drops
        new Item(DRAGONSTONE_FULL_HELM), new Item(DRAGONSTONE_PLATEBODY), new Item(DRAGONSTONE_PLATELEGS), new Item(DRAGONSTONE_GAUNTLETS), new Item(DRAGONSTONE_BOOTS), new Item(UNCUT_ONYX)),

    BRIMSTONE_KEY(AttributeKey.BRIMSTONE_KEYS_OPENED, LogType.KEYS, "Brimstone key", new int[]{ItemIdentifiers.BRIMSTONE_KEY}, AttributeKey.BRIMSTONE_KEY_LOG_CLAIMED, new Item[]{new Item(ItemIdentifiers.BRIMSTONE_KEY, 10), new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX)},
        //Drops
        new Item(MYSTIC_HAT_DUSK), new Item(MYSTIC_ROBE_TOP_DUSK), new Item(MYSTIC_ROBE_BOTTOM_DUSK), new Item(MYSTIC_BOOTS_DUSK), new Item(MYSTIC_GLOVES_DUSK), new Item(DRAGON_HASTA), new Item(HYDRAS_HEART), new Item(HYDRAS_FANG), new Item(HYDRAS_EYE), new Item(BOOTS_OF_BRIMSTONE)),

    COLLECTION_KEY(AttributeKey.COLLECTION_LOG_KEYS_OPENED, LogType.KEYS, "Collection key", new int[]{CustomItemIdentifiers.COLLECTION_KEY}, AttributeKey.COLLECTION_KEY_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.COLLECTION_KEY, 1), new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX)},
        //Drops
        new Item(SARADOMIN_HILT), new Item(ARMADYL_HILT), new Item(ARMADYL_HELMET), new Item(ARMADYL_CHESTPLATE), new Item(ARMADYL_CHAINSKIRT), new Item(BANDOS_BOOTS), new Item(BANDOS_TASSETS), new Item(BANDOS_CHESTPLATE), new Item(BLADE_OF_SAELDOR)),

    WILDERNESS_KEY(AttributeKey.WILDY_KEYS_OPENED, LogType.KEYS, "Wilderness key", new int[]{CustomItemIdentifiers.WILDERNESS_KEY}, AttributeKey.WILDERNESS_KEY_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.WILDERNESS_KEY, 5), new Item(CustomItemIdentifiers.SUPER_MYSTERY_BOX)},
        //Drops
        new Item(VESTAS_LONGSWORD), new Item(VESTAS_SPEAR), new Item(VESTAS_CHAINBODY), new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(STATIUSS_FULL_HELM), new Item(MORRIGANS_COIF), new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS), new Item(ZURIELS_STAFF), new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM),
        new Item(AMULET_OF_TORTURE), new Item(NECKLACE_OF_ANGUISH), new Item(HEAVY_BALLISTA), new Item(LIGHT_BALLISTA), new Item(NEITIZNOT_FACEGUARD), new Item(PRIMORDIAL_BOOTS), new Item(PEGASIAN_BOOTS), new Item(ETERNAL_BOOTS), new Item(IMBUED_HEART), new Item(DRAGON_CLAWS), new Item(ARMADYL_GODSWORD),
        new Item(STAFF_OF_THE_DEAD), new Item(STAFF_OF_LIGHT), new Item(TOXIC_STAFF_OF_THE_DEAD)),

    ;

    private final AttributeKey attributeKey;
    private final LogType logType;
    private final String name;
    private final int[] key;
    private final AttributeKey rewardClaimed;
    private final Item[] reward;
    private final Item[] obtainables;

    Collection(AttributeKey attributeKey, LogType logType, String name, int[] key, AttributeKey rewardClaimed, Item[] reward, Item... obtainables) {
        this.attributeKey = attributeKey;
        this.logType = logType;
        this.name = name;
        this.key = key;
        this.rewardClaimed = rewardClaimed;
        this.reward = reward;
        this.obtainables = obtainables;
    }

    public AttributeKey getAttributeKey() {
        return attributeKey;
    }

    public String getName() {
        return name;
    }

    public int[] getKey() {
        return key;
    }

    public AttributeKey getRewardClaimedKey() {
        return rewardClaimed;
    }

    public Item[] getReward() {
        return reward;
    }

    public Item[] getObtainables() {
        return obtainables;
    }

    public LogType getLogType() {
        return logType;
    }

    /**
     * The amount of items we can obtain.
     */
    public int totalCollectables() {
        return obtainables.length;
    }

    /**
     * Gets all the data for a specific type.
     *
     * @param logType the log type that is being sorted at alphabetical order
     */
    public static List<Collection> getAsList(LogType logType) {
        return Arrays.stream(values()).filter(type -> type.getLogType() == logType).sorted(Comparator.comparing(Enum::name)).collect(Collectors.toList());
    }
}

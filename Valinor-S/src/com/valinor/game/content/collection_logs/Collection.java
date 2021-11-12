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

import static com.valinor.game.content.collection_logs.CollectionLog.RAIDS_KEY;
import static com.valinor.util.CustomItemIdentifiers.BABY_LAVA_DRAGON;
import static com.valinor.util.CustomItemIdentifiers.BARRELCHEST_PET;
import static com.valinor.util.CustomItemIdentifiers.DEMENTOR_PET;
import static com.valinor.util.CustomItemIdentifiers.FENRIR_GREYBACK_JR;
import static com.valinor.util.CustomItemIdentifiers.FLUFFY_JR;
import static com.valinor.util.CustomItemIdentifiers.NIFFLER;
import static com.valinor.util.CustomItemIdentifiers.SKELETON_HELLHOUND_PET;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.CustomNpcIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.NpcIdentifiers.THE_NIGHTMARE_9430;

/**
 * @author PVE
 * @Since juli 15, 2020
 */
public enum Collection {
    // bosses
    BARRELCHEST(AttributeKey.BARRELCHESTS_KILLED, LogType.BOSSES, "Barrelchests", new int[]{NpcIdentifiers.BARRELCHEST_6342}, AttributeKey.BARRELCHEST_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX)},
        //Drops
        new Item(BARRELCHEST_PET), new Item(ANCIENT_WARRIOR_SWORD), new Item(ANCIENT_WARRIOR_AXE), new Item(ANCIENT_WARRIOR_MAUL), new Item(KEY_OF_DROPS)),

    CALLISTO(AttributeKey.CALLISTOS_KILLED, LogType.BOSSES, "Callisto", new int[]{NpcIdentifiers.CALLISTO, NpcIdentifiers.CALLISTO_6609}, AttributeKey.CALLISTO_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX, 3)},
        //Drops
        new Item(CALLISTO_CUB), new Item(DRAGON_PICKAXE), new Item(TYRANNICAL_RING), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.WEAPON_MYSTERY_BOX), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(KEY_OF_DROPS)),

    CERBERUS(AttributeKey.CERBERUS_KILLED, LogType.BOSSES, "Cerberus", new int[]{NpcIdentifiers.CERBERUS}, AttributeKey.CERBERUS_LOG_CLAIMED, new Item[]{new Item(PRIMORDIAL_BOOTS), new Item(PEGASIAN_BOOTS), new Item(ETERNAL_BOOTS)},
        //Drops
        new Item(HELLPUPPY), new Item(PRIMORDIAL_CRYSTAL), new Item(PEGASIAN_CRYSTAL), new Item(ETERNAL_CRYSTAL), new Item(SMOULDERING_STONE), new Item(JAR_OF_SOULS)),

    CHAOS_ELEMENTAL(AttributeKey.CHAOS_ELEMENTALS_KILLED, LogType.BOSSES, "Chaos Elemental", new int[]{NpcIdentifiers.CHAOS_ELEMENTAL}, AttributeKey.CHAOS_ELEMENTAL_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX)},
        //Drops
        new Item(PET_CHAOS_ELEMENTAL), new Item(DRAGON_2H_SWORD), new Item(DRAGON_PICKAXE), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.WEAPON_MYSTERY_BOX), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(KEY_OF_DROPS), new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX), new Item(ELEMENTAL_BOW)),

    CHAOS_FANATIC(AttributeKey.CHAOS_FANATICS_KILLED, LogType.BOSSES, "Chaos Fanatic", new int[]{NpcIdentifiers.CHAOS_FANATIC}, AttributeKey.CHAOS_FANATIC_LOG_CLAIMED, new Item[]{new Item(BLOOD_MONEY_CASKET, 2)},
        //Drops
        new Item(PET_CHAOS_ELEMENTAL), new Item(ODIUM_WARD_12807), new Item(MALEDICTION_WARD_12806), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.WEAPON_MYSTERY_BOX)),

    CORPOREAL_BEAST(AttributeKey.CORPOREAL_BEASTS_KILLED, LogType.BOSSES, "Corporeal Beast", new int[]{NpcIdentifiers.CORPOREAL_BEAST}, AttributeKey.CORPOREAL_BEAST_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX, 3), new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX, 2)},
        //Drops
        new Item(PET_DARK_CORE), new Item(ELYSIAN_SPIRIT_SHIELD), new Item(SPECTRAL_SPIRIT_SHIELD), new Item(ARCANE_SPIRIT_SHIELD), new Item(HOLY_ELIXIR)),

    CRAZY_ARCHAEOLOGIST(AttributeKey.CRAZY_ARCHAEOLOGISTS_KILLED, LogType.BOSSES, "Crazy Archaeologist", new int[]{NpcIdentifiers.CRAZY_ARCHAEOLOGIST}, AttributeKey.CRAZY_ARCHAEOLOGIST_LOG_CLAIMED, new Item[]{new Item(ODIUM_WARD), new Item(MALEDICTION_WARD)},
        //Drops
        new Item(ODIUM_SHARD_2), new Item(MALEDICTION_SHARD_2), new Item(FEDORA), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX)),

    DEMONIC_GORILLA(AttributeKey.DEMONIC_GORILLAS_KILLED, LogType.BOSSES, "Demonic Gorilla", new int[]{NpcIdentifiers.DEMONIC_GORILLA, NpcIdentifiers.DEMONIC_GORILLA_7145, NpcIdentifiers.DEMONIC_GORILLA_7146, NpcIdentifiers.DEMONIC_GORILLA_7147, NpcIdentifiers.DEMONIC_GORILLA_7148, NpcIdentifiers.DEMONIC_GORILLA_7149}, AttributeKey.DEMONIC_GORILLA_LOG_CLAIMED, new Item[]{new Item(HEAVY_BALLISTA), new Item(DRAGON_JAVELIN, 4000)},
        //Drops
        new Item(DRAGON_JAVELIN), new Item(LIGHT_BALLISTA), new Item(HEAVY_BALLISTA), new Item(NECKLACE_OF_ANGUISH), new Item(TORMENTED_BRACELET), new Item(RING_OF_SUFFERING), new Item(AMULET_OF_TORTURE)),

    KING_BLACK_DRAGON(AttributeKey.KING_BLACK_DRAGONS_KILLED, LogType.BOSSES, "King Black Dragon", new int[]{NpcIdentifiers.KING_BLACK_DRAGON}, AttributeKey.KING_BLACK_DRAGON_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX, 3), new Item(DRAGONFIRE_SHIELD, 2)},
        //Drops
        new Item(PRINCE_BLACK_DRAGON), new Item(KBD_HEADS), new Item(DRAGON_PICKAXE), new Item(DRAGONFIRE_SHIELD), new Item(ARMADYL_GODSWORD), new Item(BANDOS_GODSWORD), new Item(SARADOMIN_GODSWORD), new Item(ZAMORAK_GODSWORD), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.WEAPON_MYSTERY_BOX), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(KEY_OF_DROPS)),

    KRAKEN(AttributeKey.KRAKENS_KILLED, LogType.BOSSES, "Kraken", new int[]{NpcIdentifiers.KRAKEN}, AttributeKey.KRAKEN_LOG_CLAIMED, new Item[]{new Item(ANCIENT_WYVERN_SHIELD), new Item(DRAGONFIRE_WARD)},
        //Drops
        new Item(PET_KRAKEN), new Item(ABYSSAL_TENTACLE), new Item(TRIDENT_OF_THE_SEAS), new Item(JAR_OF_DIRT), new Item(ANCIENT_WYVERN_SHIELD), new Item(DRAGONFIRE_WARD), new Item(NIGHTMARE_STAFF)),

    LAVA_DRAGON(AttributeKey.LAVA_DRAGONS_KILLED, LogType.BOSSES, "Lava Dragon", new int[]{NpcIdentifiers.LAVA_DRAGON}, AttributeKey.LAVA_DRAGON_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX, 3)},
        //Drops
        new Item(DRAGONFIRE_SHIELD), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.WEAPON_MYSTERY_BOX), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(KEY_OF_DROPS)),

    LIZARDMAN_SHAMAN(AttributeKey.LIZARDMAN_SHAMANS_KILLED, LogType.BOSSES, "Lizardman Shaman", new int[]{NpcIdentifiers.LIZARDMAN_SHAMAN, NpcIdentifiers.LIZARDMAN_SHAMAN_6767}, AttributeKey.LIZARDMAN_SHAMAN_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX, 5)},
        //Drops
        new Item(DRAGON_WARHAMMER)),

    SCORPIA(AttributeKey.SCORPIAS_KILLED, LogType.BOSSES, "Scorpia", new int[]{NpcIdentifiers.SCORPIA}, AttributeKey.SCORPIA_LOG_CLAIMED, new Item[]{new Item(OCCULT_NECKLACE_OR)},
        //Drops
        new Item(SCORPIAS_OFFSPRING), new Item(ODIUM_WARD_12807), new Item(MALEDICTION_WARD_12806), new Item(OCCULT_NECKLACE)),

    THERMONUCLEAR_SMOKE_DEVIL(AttributeKey.THERMONUCLEAR_SMOKE_DEVILS_KILLED, LogType.BOSSES, "Thermonuclear Smoke Devil", new int[]{NpcIdentifiers.THERMONUCLEAR_SMOKE_DEVIL}, AttributeKey.THERMONUCLEAR_SMOKE_DEVIL_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX, 3)},
        //Drops
        new Item(PET_SMOKE_DEVIL), new Item(MYSTERY_BOX)),

    TZTOK_JAD(AttributeKey.JADS_KILLED, LogType.BOSSES, "Tztok-Jad", new int[]{NpcIdentifiers.TZTOKJAD}, AttributeKey.TZTOK_JAD_LOG_CLAIMED, new Item[]{new Item(DRAGON_CLAWS)},
        //Drops
        new Item(TZREKJAD), new Item(DRAGON_CLAWS), new Item(INFERNAL_CAPE)),

    VENENATIS(AttributeKey.VENENATIS_KILLED, LogType.BOSSES, "Venenatis", new int[]{NpcIdentifiers.VENENATIS, NpcIdentifiers.VENENATIS_6610}, AttributeKey.VENENATIS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX, 5)},
        //Drops
        new Item(VENENATIS_SPIDERLING), new Item(TREASONOUS_RING), new Item(DRAGON_PICKAXE), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.WEAPON_MYSTERY_BOX), new Item(KEY_OF_DROPS)),

    VETION(AttributeKey.VETIONS_KILLED, LogType.BOSSES, "Vet'ion", new int[]{NpcIdentifiers.VETION_REBORN}, AttributeKey.VETION_LOG_CLAIMED, new Item[]{new Item(KEY_OF_DROPS)},
        //Drops
        new Item(VETION_JR), new Item(DRAGON_PICKAXE), new Item(RING_OF_THE_GODS), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(KEY_OF_DROPS)),

    VORKATH(AttributeKey.VORKATHS_KILLED, LogType.BOSSES, "Vorkath", new int[]{NpcIdentifiers.VORKATH}, AttributeKey.VORKATH_LOG_CLAIMED, new Item[]{new Item(VORKATHS_HEAD_21907), new Item(DRAGON_HUNTER_CROSSBOW_T)},
        //Drops
        new Item(VORKI), new Item(VORKATHS_HEAD), new Item(DRAGONFIRE_SHIELD), new Item(DRAGON_CROSSBOW), new Item(DRAGONFIRE_WARD), new Item(DRAGON_HUNTER_CROSSBOW), new Item(JAR_OF_DECAY), new Item(DRAGONBONE_NECKLACE)),

    TEKTON(AttributeKey.TEKTONS_KILLED, LogType.BOSSES, "Tekton", new int[]{NpcIdentifiers.TEKTON_7542}, AttributeKey.TEKTON_LOG_CLAIMED, new Item[]{new Item(ELDER_MAUL)},
        //Drops
        new Item(TEKTINY), new Item(DRAGON_SWORD), new Item(DRAGON_HARPOON), new Item(DINHS_BULWARK), new Item(KODAI_WAND), new Item(ELDER_MAUL), new Item(ANCESTRAL_HAT), new Item(ANCESTRAL_ROBE_TOP), new Item(ANCESTRAL_ROBE_BOTTOM), new Item(GHRAZI_RAPIER)),

    SKOTIZO(AttributeKey.SKOTIZOS_KILLED, LogType.BOSSES, "Skotizo", new int[]{NpcIdentifiers.SKOTIZO}, AttributeKey.SKOTIZO_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX)},
        //Dropscustom
        new Item(SKOTOS), new Item(DARK_CLAW), new Item(JAR_OF_DARKNESS), new Item(JUSTICIAR_FACEGUARD), new Item(JUSTICIAR_CHESTGUARD), new Item(JUSTICIAR_LEGGUARDS)),

    ZOMBIES_CHAMPION(AttributeKey.ZOMBIES_CHAMPIONS_KILLED, LogType.BOSSES, "Zombies Champion", new int[]{NpcIdentifiers.ZOMBIES_CHAMPION}, AttributeKey.ZOMBIES_CHAMPION_LOG_CLAIMED, new Item[]{new Item(VESTAS_LONGSWORD), new Item(STATIUSS_WARHAMMER), new Item(ZURIELS_STAFF)},
        //Drops
        new Item(PET_ZOMBIES_CHAMPION), new Item(ARMADYL_CROSSBOW), new Item(ARMADYL_GODSWORD), new Item(VESTAS_LONGSWORD), new Item(VESTAS_CHAINBODY), new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_FULL_HELM), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(MORRIGANS_COIF), new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS), new Item(ZURIELS_STAFF), new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM)),

    BRUTAL_LAVA_DRAGON(AttributeKey.BRUTAL_LAVA_DRAGONS_KILLED, LogType.BOSSES, "Brutal Lava Dragon", new int[]{BRUTAL_LAVA_DRAGON_FLYING}, AttributeKey.BRUTAL_LAVA_DRAGON_LOG_CLAIMED, new Item[]{new Item(LAVA_DHIDE_BODY), new Item(LAVA_DHIDE_CHAPS)},
        //Drops
        new Item(BABY_LAVA_DRAGON), new Item(DAGONHAI_HAT), new Item(DAGONHAI_ROBE_TOP), new Item(DAGONHAI_ROBE_BOTTOM), new Item(LAVA_DHIDE_COIF), new Item(LAVA_DHIDE_BODY), new Item(LAVA_DHIDE_CHAPS), new Item(LAVA_WHIP), new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX), new Item(KEY_OF_DROPS), new Item(LAVA_PARTYHAT), new Item(INFERNAL_CAPE), new Item(EPIC_PET_BOX)),

    ZULRAH(AttributeKey.ZULRAHS_KILLED, LogType.BOSSES, "Zulrah", new int[]{NpcIdentifiers.ZULRAH, NpcIdentifiers.ZULRAH_2044, NpcIdentifiers.ZULRAH_2043}, AttributeKey.ZULRAH_LOG_CLAIMED, new Item[]{new Item(TOXIC_BLOWPIPE), new Item(TRIDENT_OF_THE_SWAMP), new Item(TOXIC_STAFF_OF_THE_DEAD)},
        //Drops
        new Item(PET_SNAKELING), new Item(TANZANITE_HELM), new Item(MAGMA_HELM), new Item(JAR_OF_SWAMP), new Item(TRIDENT_OF_THE_SWAMP), new Item(SERPENTINE_HELM), new Item(TOXIC_STAFF_OF_THE_DEAD), new Item(TOXIC_BLOWPIPE), new Item(ZULANDRA_TELEPORT), new Item(UNCUT_ONYX), new Item(ZULRAHS_SCALES)),

    ALCHEMICAL_HYDRA(AttributeKey.ALCHY_KILLED, LogType.BOSSES, "Alchemical Hydra", new int[]{NpcIdentifiers.ALCHEMICAL_HYDRA, NpcIdentifiers.ALCHEMICAL_HYDRA_8616, NpcIdentifiers.ALCHEMICAL_HYDRA_8617, NpcIdentifiers.ALCHEMICAL_HYDRA_8618, NpcIdentifiers.ALCHEMICAL_HYDRA_8619, NpcIdentifiers.ALCHEMICAL_HYDRA_8620, NpcIdentifiers.ALCHEMICAL_HYDRA_8621, NpcIdentifiers.ALCHEMICAL_HYDRA_8622}, AttributeKey.ALCHEMICAL_HYDRA_LOG_CLAIMED, new Item[]{new Item(KEY_OF_DROPS)},
        //Drops
        new Item(IKKLE_HYDRA), new Item(HYDRAS_CLAW), new Item(HYDRA_TAIL), new Item(FEROCIOUS_GLOVES), new Item(BRIMSTONE_RING), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(DRAGON_KNIFE), new Item(DRAGON_THROWNAXE), new Item(JAR_OF_CHEMICALS), new Item(ALCHEMICAL_HYDRA_HEADS)),

    GIANT_MOLE(AttributeKey.KC_GIANTMOLE, LogType.BOSSES, "Giant Mole", new int[]{NpcIdentifiers.GIANT_MOLE}, AttributeKey.GIANT_MOLE_LOG_CLAIMED, new Item[]{new Item(DRAGON_CLAWS)},
        //Drops
        new Item(NIFFLER), new Item(MOLE_SKIN), new Item(MOLE_CLAW), new Item(DRAGON_CLAWS), new Item(KEY_OF_DROPS)),

    CORRUPTED_NECHRYARCH(AttributeKey.CORRUPTED_NECHRYARCHS_KILLED, LogType.BOSSES, "Corrupted Nechryarch", new int[]{CustomNpcIdentifiers.CORRUPTED_NECHRYARCH}, AttributeKey.CORRUPTED_NECHRYARCH_LOG_CLAIMED, new Item[]{new Item(BLOOD_MONEY_CASKET, 4)},
        //Drops
        new Item(CORRUPTED_BOOTS)),

    ANCIENT_CHAOS_ELEMENTAL(AttributeKey.ANCIENT_CHAOS_ELEMENTALS_KILLED, LogType.BOSSES, "Ancient Chaos Elemental", new int[]{CustomNpcIdentifiers.ANCIENT_CHAOS_ELEMENTAL}, AttributeKey.ANCIENT_CHAOS_ELEMENTAL_LOG_CLAIMED, new Item[]{new Item(KEY_OF_DROPS)},
        //Drops
        new Item(CustomItemIdentifiers.ANCIENT_CHAOS_ELEMENTAL_PET), new Item(BLOOD_MONEY_CASKET), new Item(CustomItemIdentifiers.WEAPON_MYSTERY_BOX), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(KEY_OF_DROPS), new Item(EPIC_PET_BOX)
    ),

    ANCIENT_BARRELCHEST(AttributeKey.ANCIENT_BARRELCHESTS_KILLED, LogType.BOSSES, "Ancient Barrelchest", new int[]{CustomNpcIdentifiers.ANCIENT_BARRELCHEST}, AttributeKey.ANCIENT_BARRELCHEST_LOG_CLAIMED, new Item[]{new Item(ANCIENT_WARRIOR_CLAMP)},
        //Drops
        new Item(CustomItemIdentifiers.ANCIENT_BARRELCHEST_PET), new Item(BLOOD_MONEY_CASKET), new Item(CustomItemIdentifiers.WEAPON_MYSTERY_BOX), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(KEY_OF_DROPS), new Item(EPIC_PET_BOX), new Item(ANCIENT_WARRIOR_CLAMP)
    ),

    ANCIENT_KING_BLACK_DRAGON(AttributeKey.ANCIENT_KING_BLACK_DRAGONS_KILLED, LogType.BOSSES, "Ancient King Black Dragon", new int[]{CustomNpcIdentifiers.ANCIENT_KING_BLACK_DRAGON}, AttributeKey.ANCIENT_KING_BLACK_DRAGON_LOG_CLAIMED, new Item[]{new Item(KEY_OF_DROPS)},
        //Drops
        new Item(CustomItemIdentifiers.ANCIENT_KING_BLACK_DRAGON_PET), new Item(DRAGONFIRE_SHIELD), new Item(CustomItemIdentifiers.WEAPON_MYSTERY_BOX), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(KEY_OF_DROPS), new Item(EPIC_PET_BOX), new Item(ANCIENT_FACEGAURD)
    ),

    KERBEROS(AttributeKey.KERBEROS_KILLED, LogType.BOSSES, "Kerberos", new int[]{CustomNpcIdentifiers.KERBEROS}, AttributeKey.KERBEROS_LOG_CLAIMED, new Item[]{new Item(SAELDOR_SHARD, 100)},
        //Drops
        new Item(CustomItemIdentifiers.KERBEROS_PET), new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX), new Item(SAELDOR_SHARD), new Item(LARRANS_KEY_TIER_III), new Item(VAMPYRE_DUST), new Item(KEY_OF_DROPS), new Item(EPIC_PET_BOX), new Item(PHARAOHS_HILT)
    ),

    SKORPIOS(AttributeKey.SKORPIOS_KILLED, LogType.BOSSES, "Skorpios", new int[]{CustomNpcIdentifiers.SKORPIOS}, AttributeKey.SKORPIOS_LOG_CLAIMED, new Item[]{new Item(SAELDOR_SHARD, 100)},
        //Drops
        new Item(CustomItemIdentifiers.SKORPIOS_PET), new Item(KEY_OF_DROPS), new Item(EPIC_PET_BOX), new Item(LARRANS_KEY_TIER_III), new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX), new Item(SAELDOR_SHARD), new Item(DARK_ARMADYL_HELMET), new Item(BOW_OF_FAERDHINEN_3)
    ),

    ARACHNE(AttributeKey.ARACHNE_KILLED, LogType.BOSSES, "Arachne", new int[]{CustomNpcIdentifiers.ARACHNE}, AttributeKey.ARACHNE_LOG_CLAIMED, new Item[]{new Item(SAELDOR_SHARD, 100)},
        //Drops
        new Item(CustomItemIdentifiers.ARACHNE_PET), new Item(KEY_OF_DROPS), new Item(EPIC_PET_BOX), new Item(LARRANS_KEY_TIER_III), new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX), new Item(SAELDOR_SHARD), new Item(DARK_ARMADYL_CHAINSKIRT), new Item(BOW_OF_FAERDHINEN_3)
    ),

    ARTIO(AttributeKey.ARTIO_KILLED, LogType.BOSSES, "Artio", new int[]{CustomNpcIdentifiers.ARTIO}, AttributeKey.ARTIO_LOG_CLAIMED, new Item[]{new Item(SAELDOR_SHARD, 100)},
        //Drops
        new Item(CustomItemIdentifiers.ARTIO_PET), new Item(KEY_OF_DROPS), new Item(EPIC_PET_BOX), new Item(LARRANS_KEY_TIER_III), new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX), new Item(SAELDOR_SHARD), new Item(DARK_ARMADYL_CHESTPLATE), new Item(BOW_OF_FAERDHINEN_3)
    ),

    THE_NIGHTMARE(AttributeKey.THE_NIGHTMARE_KC, LogType.BOSSES, "The nightmare", new int[]{THE_NIGHTMARE_9430}, AttributeKey.THE_NIGTHMARE_LOG_CLAIMED, new Item[]{new Item(SHADOW_INQUISITOR_ORNAMENT_KIT), new Item(INQUISITORS_MACE_ORNAMENT_KIT)},
        //Drops
        new Item(LITTLE_NIGHTMARE), new Item(ItemIdentifiers.CRYSTAL_KEY), new Item(CustomItemIdentifiers.WEAPON_MYSTERY_BOX), new Item(CustomItemIdentifiers.ARMOUR_MYSTERY_BOX), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX), new Item(BLOOD_MONEY_CASKET), new Item(ABYSSAL_BLUDGEON), new Item(ARMADYL_GODSWORD), new Item(DRAGON_CLAWS), new Item(DRAGON_WARHAMMER), new Item(KEY_OF_DROPS), new Item(CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX), new Item(EPIC_PET_BOX), new Item(INQUISITORS_MACE), new Item(INQUISITORS_GREAT_HELM), new Item(INQUISITORS_HAUBERK), new Item(INQUISITORS_PLATESKIRT), new Item(NIGHTMARE_STAFF), new Item(ELDRITCH_ORB), new Item(HARMONISED_ORB), new Item(VOLATILE_ORB), new Item(SHADOW_INQUISITOR_ORNAMENT_KIT), new Item(INQUISITORS_MACE_ORNAMENT_KIT)),

    CORRUPTED_HUNLEFF(AttributeKey.CORRUPTED_HUNLEFFS_KILLED, LogType.BOSSES, "Corrupted Hunleff", new int[]{NpcIdentifiers.CORRUPTED_HUNLLEF, NpcIdentifiers.CORRUPTED_HUNLLEF_9036, NpcIdentifiers.CORRUPTED_HUNLLEF_9037}, AttributeKey.CORRUPTED_HUNLEFF_LOG_CLAIMED, new Item[]{new Item(KEY_OF_DROPS)},
        //Drops
        new Item(YOUNGLLEF), new Item(CRYSTAL_HELM), new Item(CRYSTAL_BODY), new Item(CRYSTAL_LEGS), new Item(BLOOD_MONEY_CASKET), new Item(BANDOS_GODSWORD_OR), new Item(SARADOMIN_GODSWORD_OR), new Item(ZAMORAK_GODSWORD_OR), new Item(EPIC_PET_BOX), new Item(KEY_OF_DROPS), new Item(CORRUPTING_STONE)
    ),

    MEN_IN_BLACK(AttributeKey.MEN_IN_BLACK_KILLED, LogType.OTHER, "Men in Black", new int[]{NpcIdentifiers.KILLER}, AttributeKey.MEN_IN_BLACK_LOG_CLAIMED, new Item[]{new Item(HWEEN_MYSTERY_CHEST)},
        //Drops
        new Item(SKELETON_HELLHOUND_PET), new Item(PINK_SWEETS), new Item(HWEEN_MYSTERY_BOX), new Item(MYSTERY_TICKET), new Item(KEY_OF_DROPS), new Item(HWEEN_MYSTERY_CHEST)
    ),

    REVENANTS(AttributeKey.REVENANTS_KILLED, LogType.OTHER, "Revenants", new int[]{NpcIdentifiers.REVENANT_IMP, NpcIdentifiers.REVENANT_CYCLOPS, NpcIdentifiers.REVENANT_DARK_BEAST, NpcIdentifiers.REVENANT_DEMON, NpcIdentifiers.REVENANT_DRAGON, NpcIdentifiers.REVENANT_GOBLIN, NpcIdentifiers.REVENANT_HELLHOUND, NpcIdentifiers.REVENANT_HOBGOBLIN, NpcIdentifiers.REVENANT_KNIGHT, NpcIdentifiers.REVENANT_ORK, NpcIdentifiers.REVENANT_PYREFIEND}, AttributeKey.REVENANTS_LOG_CLAIMED, new Item[]{new Item(CRAWS_BOW), new Item(VIGGORAS_CHAINMACE), new Item(THAMMARONS_SCEPTRE)},
        //Drops
        new Item(VESTAS_SPEAR), new Item(VESTAS_LONGSWORD), new Item(VESTAS_CHAINBODY), new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_FULL_HELM), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(ZURIELS_STAFF), new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM), new Item(MORRIGANS_COIF), new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS), new Item(MORRIGANS_JAVELIN), new Item(MORRIGANS_THROWING_AXE), new Item(VIGGORAS_CHAINMACE), new Item(CRAWS_BOW), new Item(THAMMARONS_SCEPTRE), new Item(AMULET_OF_AVARICE), new Item(BRACELET_OF_ETHEREUM_UNCHARGED), new Item(ANCIENT_RELIC), new Item(ANCIENT_EFFIGY), new Item(ANCIENT_MEDALLION), new Item(ItemIdentifiers.ANCIENT_STATUETTE), new Item(ANCIENT_TOTEM), new Item(ANCIENT_EMBLEM), new Item(REVENANT_CAVE_TELEPORT), new Item(REVENANT_ETHER)
    ),

    ANCIENT_REVENANTS(AttributeKey.ANCIENT_REVENANTS_KILLED, LogType.OTHER, "Ancient Revenants", new int[]{ANCIENT_REVENANT_DARK_BEAST, ANCIENT_REVENANT_ORK, ANCIENT_REVENANT_CYCLOPS, ANCIENT_REVENANT_DRAGON, ANCIENT_REVENANT_KNIGHT}, AttributeKey.ANCIENT_REVENANTS_LOG_CLAIMED, new Item[]{new Item(ANCIENT_VESTAS_LONGSWORD), new Item(ANCIENT_STATIUSS_WARHAMMER)},
        //Drops
        new Item(VESTAS_SPEAR), new Item(VESTAS_LONGSWORD), new Item(VESTAS_CHAINBODY), new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_FULL_HELM), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(ZURIELS_STAFF), new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM), new Item(MORRIGANS_COIF), new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS), new Item(MORRIGANS_JAVELIN), new Item(MORRIGANS_THROWING_AXE), new Item(VIGGORAS_CHAINMACE), new Item(CRAWS_BOW), new Item(THAMMARONS_SCEPTRE), new Item(AMULET_OF_AVARICE), new Item(DARK_ANCIENT_EMBLEM), new Item(DARK_ANCIENT_RELIC), new Item(DARK_ANCIENT_EFFIGY), new Item(DARK_ANCIENT_MEDALLION), new Item(DARK_ANCIENT_STATUETTE), new Item(DARK_ANCIENT_TOTEM), new Item(DARK_ANCIENT_EMBLEM), new Item(ANCIENT_VESTAS_LONGSWORD), new Item(ANCIENT_STATIUSS_WARHAMMER)
    ),

    SLAYER(null, LogType.OTHER, "Slayer",
        //Npcs that drop these items
        new int[]{NpcIdentifiers.CRAWLING_HAND_448, NpcIdentifiers.CRAWLING_HAND_449, NpcIdentifiers.CRAWLING_HAND_450, NpcIdentifiers.CRAWLING_HAND_451, NpcIdentifiers.CRAWLING_HAND_452, NpcIdentifiers.CRAWLING_HAND_453, NpcIdentifiers.CRAWLING_HAND_454, NpcIdentifiers.CRAWLING_HAND_455, NpcIdentifiers.CRAWLING_HAND_456, NpcIdentifiers.CRAWLING_HAND_457, NpcIdentifiers.CRUSHING_HAND,
            NpcIdentifiers.COCKATRICE_419, NpcIdentifiers.COCKATRICE_420, NpcIdentifiers.COCKATHRICE, NpcIdentifiers.BASILISK_417, NpcIdentifiers.BASILISK_418, NpcIdentifiers.BASILISK_9283, NpcIdentifiers.BASILISK_9284, NpcIdentifiers.BASILISK_9285, NpcIdentifiers.BASILISK_9286, NpcIdentifiers.BASILISK_KNIGHT, NpcIdentifiers.BASILISK_SENTINEL, NpcIdentifiers.BASILISK_YOUNGLING, NpcIdentifiers.MONSTROUS_BASILISK, NpcIdentifiers.MONSTROUS_BASILISK_9287, NpcIdentifiers.MONSTROUS_BASILISK_9288,
            NpcIdentifiers.KURASK_410, NpcIdentifiers.KURASK_411, NpcIdentifiers.KING_KURASK, NpcIdentifiers.ABYSSAL_DEMON_415, NpcIdentifiers.ABYSSAL_DEMON_416, NpcIdentifiers.ABYSSAL_DEMON_7241, NpcIdentifiers.GREATER_ABYSSAL_DEMON, NpcIdentifiers.ABYSSAL_SIRE, NpcIdentifiers.ABYSSAL_SIRE_5887, NpcIdentifiers.ABYSSAL_SIRE_5888, NpcIdentifiers.ABYSSAL_SIRE_5889, NpcIdentifiers.ABYSSAL_SIRE_5890, NpcIdentifiers.ABYSSAL_SIRE_5891, NpcIdentifiers.ABYSSAL_SIRE_5908,
            NpcIdentifiers.GARGOYLE, NpcIdentifiers.GARGOYLE_1543, NpcIdentifiers.MARBLE_GARGOYLE_7408, NpcIdentifiers.TUROTH, NpcIdentifiers.TUROTH_427, NpcIdentifiers.TUROTH_428, NpcIdentifiers.TUROTH_429, NpcIdentifiers.TUROTH_430, NpcIdentifiers.TUROTH_431, NpcIdentifiers.TUROTH_432, NpcIdentifiers.CAVE_HORROR, NpcIdentifiers.CAVE_HORROR_1048, NpcIdentifiers.CAVE_HORROR_1049, NpcIdentifiers.CAVE_HORROR_1050, NpcIdentifiers.CAVE_HORROR_1051, NpcIdentifiers.CAVE_ABOMINATION,
            NpcIdentifiers.TALONED_WYVERN, NpcIdentifiers.SPITTING_WYVERN, NpcIdentifiers.LONGTAILED_WYVERN, NpcIdentifiers.ANCIENT_WYVERN, NpcIdentifiers.KING_BLACK_DRAGON, NpcIdentifiers.KING_BLACK_DRAGON_6502, NpcIdentifiers.BLACK_DRAGON, NpcIdentifiers.BLACK_DRAGON_253, NpcIdentifiers.BLACK_DRAGON_254, NpcIdentifiers.BLACK_DRAGON_255, NpcIdentifiers.BLACK_DRAGON_256, NpcIdentifiers.BLACK_DRAGON_257, NpcIdentifiers.BLACK_DRAGON_258, NpcIdentifiers.BLACK_DRAGON_259, NpcIdentifiers.BLACK_DRAGON_7861, NpcIdentifiers.BLACK_DRAGON_7862, NpcIdentifiers.BLACK_DRAGON_7863, NpcIdentifiers.BLACK_DRAGON_8084, NpcIdentifiers.BLACK_DRAGON_8085, NpcIdentifiers.BRUTAL_BLACK_DRAGON, NpcIdentifiers.BRUTAL_BLACK_DRAGON_8092, NpcIdentifiers.BRUTAL_BLACK_DRAGON_8092,
            NpcIdentifiers.VORKATH_8061, NpcIdentifiers.ADAMANT_DRAGON, NpcIdentifiers.ADAMANT_DRAGON_8090, NpcIdentifiers.RUNE_DRAGON, NpcIdentifiers.RUNE_DRAGON_8031, NpcIdentifiers.RUNE_DRAGON_8091, NpcIdentifiers.LAVA_DRAGON, NpcIdentifiers.MITHRIL_DRAGON, NpcIdentifiers.MITHRIL_DRAGON_8088, NpcIdentifiers.MITHRIL_DRAGON_8089, NpcIdentifiers.SKELETAL_WYVERN_466, NpcIdentifiers.SKELETAL_WYVERN_467, NpcIdentifiers.SKELETAL_WYVERN_468, NpcIdentifiers.SPIRITUAL_MAGE, NpcIdentifiers.SPIRITUAL_MAGE_2244, NpcIdentifiers.SPIRITUAL_MAGE_3161, NpcIdentifiers.SPIRITUAL_MAGE_3168,
            NpcIdentifiers.KRAKEN, NpcIdentifiers.DARK_BEAST, NpcIdentifiers.DARK_BEAST_7250, NpcIdentifiers.NIGHT_BEAST, NpcIdentifiers.SMOKE_DEVIL, NpcIdentifiers.SMOKE_DEVIL_6639, NpcIdentifiers.SMOKE_DEVIL_6655, NpcIdentifiers.SMOKE_DEVIL_8482, NpcIdentifiers.SMOKE_DEVIL_8483, NpcIdentifiers.NUCLEAR_SMOKE_DEVIL, NpcIdentifiers.THERMONUCLEAR_SMOKE_DEVIL, NpcIdentifiers.KALPHITE_QUEEN_6500, NpcIdentifiers.KALPHITE_QUEEN_6501, NpcIdentifiers.WYRM, NpcIdentifiers.WYRM_8611, NpcIdentifiers.DRAKE_8612, NpcIdentifiers.DRAKE_8613, NpcIdentifiers.HYDRA, NpcIdentifiers.ALCHEMICAL_HYDRA, NpcIdentifiers.ALCHEMICAL_HYDRA_8616, NpcIdentifiers.ALCHEMICAL_HYDRA_8617, NpcIdentifiers.ALCHEMICAL_HYDRA_8618, NpcIdentifiers.ALCHEMICAL_HYDRA_8619, NpcIdentifiers.ALCHEMICAL_HYDRA_8620, NpcIdentifiers.ALCHEMICAL_HYDRA_8621, NpcIdentifiers.ALCHEMICAL_HYDRA_8622, NpcIdentifiers.ALCHEMICAL_HYDRA_8634,
            NpcIdentifiers.BASILISK_KNIGHT, NpcIdentifiers.BASILISK_SENTINEL}, AttributeKey.SLAYER_LOG_CLAIMED, new Item[]{new Item(ABYSSAL_TENTACLE_24948), new Item(GRANITE_MAUL_24944)},
        //Drops
        new Item(CRAWLING_HAND_7975), new Item(COCKATRICE_HEAD), new Item(BASILISK_HEAD), new Item(KURASK_HEAD), new Item(ABYSSAL_HEAD), new Item(IMBUED_HEART), new Item(ETERNAL_GEM), new Item(DUST_BATTLESTAFF), new Item(MIST_BATTLESTAFF), new Item(ABYSSAL_WHIP), new Item(GRANITE_MAUL_24225), new Item(LEAFBLADED_SWORD), new Item(LEAFBLADED_BATTLEAXE), new Item(BLACK_MASK), new Item(GRANITE_LONGSWORD), new Item(WYVERN_VISAGE), new Item(DRACONIC_VISAGE),
        new Item(DRAGON_BOOTS), new Item(ABYSSAL_DAGGER), new Item(TRIDENT_OF_THE_SEAS), new Item(KRAKEN_TENTACLE), new Item(DARK_BOW), new Item(OCCULT_NECKLACE), new Item(DRAGON_CHAINBODY_3140), new Item(DRAGON_THROWNAXE), new Item(DRAGON_HARPOON), new Item(DRAGON_SWORD), new Item(DRAGON_KNIFE), new Item(DRAKES_TOOTH), new Item(DRAKES_CLAW), new Item(HYDRA_TAIL), new Item(HYDRAS_FANG), new Item(HYDRAS_EYE), new Item(HYDRAS_HEART), new Item(BASILISK_JAW)),

    CHAMBER_OF_SECRETS(AttributeKey.CHAMBER_OF_SECRET_RUNS_COMPLETED, LogType.OTHER, "Chamber Of Secrets", new int[]{RAIDS_KEY}, AttributeKey.CHAMBER_OF_SECRETS_LOG_CLAIMED, new Item[]{new Item(CustomItemIdentifiers.RAIDS_MYSTERY_BOX, 3)},
        //Custom
        new Item(CustomItemIdentifiers.NAGINI), new Item(FENRIR_GREYBACK_JR), new Item(FLUFFY_JR), new Item(CENTAUR_MALE), new Item(CENTAUR_FEMALE), new Item(DEMENTOR_PET),
        new Item(TOM_RIDDLE_DIARY), new Item(MARVOLO_GAUNTS_RING), new Item(CLOAK_OF_INVISIBILITY), new Item(ELDER_WAND_HANDLE), new Item(ELDER_WAND_STICK), new Item(SWORD_OF_GRYFFINDOR), new Item(TALONHAWK_CROSSBOW), new Item(SALAZAR_SLYTHERINS_LOCKET)),
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

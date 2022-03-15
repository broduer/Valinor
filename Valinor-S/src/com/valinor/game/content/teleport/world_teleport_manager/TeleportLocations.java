package com.valinor.game.content.teleport.world_teleport_manager;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.CustomNpcIdentifiers;
import com.valinor.util.NpcIdentifiers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * The teleport locations stored in an enum.
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 04, 2022
 */
public enum TeleportLocations {

    /* Bosses */
    BARROWS(NpcIdentifiers.DHAROK_THE_WRETCHED, 2150, AttributeKey.BARROWS_CHESTS_OPENED, new Tile(3565, 3316, 0), Category.BOSSES, false, true, true, new Item(KARILS_COIF), new Item(AHRIMS_HOOD), new Item(DHAROKS_HELM),new Item(GUTHANS_HELM), new Item(TORAGS_HELM), new Item(VERACS_HELM), new Item(KARILS_LEATHERTOP), new Item(AHRIMS_ROBETOP), new Item(DHAROKS_PLATEBODY), new Item(GUTHANS_PLATEBODY), new Item(TORAGS_PLATEBODY), new Item(VERACS_BRASSARD), new Item(KARILS_LEATHERSKIRT), new Item(AHRIMS_ROBESKIRT), new Item(DHAROKS_PLATELEGS), new Item(GUTHANS_CHAINSKIRT), new Item(TORAGS_PLATELEGS), new Item(VERACS_PLATESKIRT), new Item(KARILS_CROSSBOW), new Item(AHRIMS_STAFF), new Item(DHAROKS_GREATAXE), new Item(GUTHANS_WARSPEAR), new Item(TORAGS_HAMMERS), new Item(VERACS_FLAIL), new Item(BOLT_RACK)),
    CORPOREAL_BEAST(NpcIdentifiers.CORPOREAL_BEAST, 2150, AttributeKey.CORPOREAL_BEASTS_KILLED, new Tile(2969, 4382, 2), Category.BOSSES, false, true, true, new Item(PET_DARK_CORE), new Item(SPIRIT_SHIELD), new Item(BLESSED_SPIRIT_SHIELD), new Item(SPECTRAL_SPIRIT_SHIELD), new Item(ARCANE_SPIRIT_SHIELD), new Item(ELYSIAN_SPIRIT_SHIELD)),
    CHAOS_ELEMENTAL(NpcIdentifiers.CHAOS_ELEMENTAL, 2800, AttributeKey.CHAOS_ELEMENTALS_KILLED, new Tile(3307, 3916, 0), Category.BOSSES, true, true, true, new Item(PET_CHAOS_ELEMENTAL), new Item(KORASI_SWORD), new Item(ELEMENTAL_BOW)),
    ABYSSAL_SIRE(NpcIdentifiers.ABYSSAL_SIRE, 4500, AttributeKey.KC_ABYSSALSIRE, new Tile(3039, 4772, 0), Category.BOSSES, false, true, true, new Item(UNSIRED)),
    CERBERUS(NpcIdentifiers.CERBERUS, 3000, AttributeKey.CERBERUS_KILLED, new Tile(1292,1252, 0), Category.BOSSES, false, false, true, new Item(HELLPUPPY), new Item(SMOULDERING_STONE), new Item(ETERNAL_BOOTS), new Item(PEGASIAN_BOOTS), new Item(PRIMORDIAL_BOOTS), new Item(JAR_OF_SOULS)),
    GODWARS_DUNGEON(NpcIdentifiers.KREEARRA, 5500, null, null, Category.BOSSES, false, false, true, new Item(BANDOS_CHESTPLATE), new Item(BANDOS_TASSETS), new Item(BANDOS_GODSWORD), new Item(SARADOMIN_GODSWORD), new Item(ARMADYL_CROSSBOW), new Item(ARMADYL_HELMET), new Item(ARMADYL_CHESTPLATE), new Item(ARMADYL_CHAINSKIRT), new Item(ARMADYL_GODSWORD), new Item(ZAMORAKIAN_SPEAR), new Item(ZAMORAK_GODSWORD)),
    GIANT_MOLE(NpcIdentifiers.GIANT_MOLE, 3000, AttributeKey.KC_GIANTMOLE, new Tile(1760, 5163, 0), Category.BOSSES, false, true, true, new Item(NIFFLER)),
    KALPHITE_QUEEN(NpcIdentifiers.KALPHITE_QUEEN_6500, 3000, AttributeKey.KC_KQ, new Tile(3484, 9510, 2), Category.BOSSES, false, true, true, new Item(KALPHITE_PRINCESS), new Item(DRAGON_CHAINBODY_3140), new Item(DRAGON_2H_SWORD)),
    DAGANNOTH_KINGS(NpcIdentifiers.DAGANNOTH_PRIME, 3000, AttributeKey.KC_PRIME, new Tile(1910, 4367, 0), Category.BOSSES, false, true, true, new Item(BERSERKER_RING), new Item(SEERS_RING), new Item(ARCHERS_RING), new Item(WARRIOR_RING)),
    KING_BLACK_DRAGON(NpcIdentifiers.KING_BLACK_DRAGON, 2800, AttributeKey.KING_BLACK_DRAGONS_KILLED, new Tile(3001, 3849, 0), Category.BOSSES, true, false, true, new Item(PRINCE_BLACK_DRAGON), new Item(KBD_HEADS), new Item(DRAGONFIRE_SHIELD), new Item(DRAGON_HUNTER_CROSSBOW), new Item(ARMADYL_GODSWORD), new Item(DRAGON_CLAWS)),
    KRAKEN(NpcIdentifiers.KRAKEN, 5000, AttributeKey.KRAKENS_KILLED, new Tile(2280, 10016,0), Category.BOSSES, false, true, true, new Item(ABYSSAL_TENTACLE), new Item(TRIDENT_OF_THE_SEAS), new Item(TOXIC_STAFF_OF_THE_DEAD), new Item(MYSTERY_BOX), new Item(DONATOR_MYSTERY_BOX), new Item(JAR_OF_DIRT)),
    THERMY_SMOKE_DEVIL(NpcIdentifiers.THERMONUCLEAR_SMOKE_DEVIL, 2800, AttributeKey.THERMONUCLEAR_SMOKE_DEVILS_KILLED, new Tile(2379, 9452, 0), Category.BOSSES, false, true, true, new Item(SMOKE_BATTLESTAFF), new Item(OCCULT_NECKLACE), new Item(TOXIC_STAFF_OF_THE_DEAD)),
    ZULRAH(NpcIdentifiers.ZULRAH, 6000, AttributeKey.ZULRAHS_KILLED, new Tile(2200, 3055, 0), Category.BOSSES, false, true, true, new Item(PET_SNAKELING), new Item(SERPENTINE_HELM), new Item(TANZANITE_HELM), new Item(MAGMA_HELM), new Item(TRIDENT_OF_THE_SWAMP), new Item(TOXIC_STAFF_OF_THE_DEAD), new Item(TOXIC_BLOWPIPE), new Item(MYSTERY_BOX), new Item(ARMOUR_MYSTERY_BOX), new Item(WEAPON_MYSTERY_BOX),new Item(DONATOR_MYSTERY_BOX), new Item(LEGENDARY_MYSTERY_BOX), new Item(JAR_OF_SWAMP)),
    CALLISTO(NpcIdentifiers.CALLISTO_6609, 2800, AttributeKey.CALLISTOS_KILLED, new Tile(3307, 3837, 0), Category.BOSSES, true, true, true, new Item(CALLISTO_CUB), new Item(TYRANNICAL_RING), new Item(DRAGON_PICKAXE), new Item(DRAGON_2H_SWORD), new Item(DINHS_BULWARK), new Item(ELDER_MAUL)),
    CHAOS_FANATIC(NpcIdentifiers.CHAOS_FANATIC, 1500, AttributeKey.CHAOS_FANATICS_KILLED, new Tile(2992, 3851, 0), Category.BOSSES, true, true, true, new Item(PET_CHAOS_ELEMENTAL), new Item(ODIUM_SHARD_1), new Item(MALEDICTION_SHARD_1)),
    CRAZY_ARCHAEOLOGIST(NpcIdentifiers.CRAZY_ARCHAEOLOGIST, 1500, AttributeKey.CRAZY_ARCHAEOLOGISTS_KILLED, new Tile(2975,3696,0), Category.BOSSES, true, true, true, new Item(ODIUM_SHARD_2), new Item(MALEDICTION_SHARD_2), new Item(FEDORA)),
    DEMONIC_GORILLA(NpcIdentifiers.DEMONIC_GORILLA, 2800, AttributeKey.DEMONIC_GORILLAS_KILLED, new Tile(3238, 3771,0), Category.BOSSES, true, true, true,  new Item(RING_OF_SUFFERING), new Item(TORMENTED_BRACELET), new Item(NECKLACE_OF_ANGUISH), new Item(AMULET_OF_TORTURE), new Item(LIGHT_BALLISTA), new Item(HEAVY_BALLISTA)),
    LIZARDMAN_SHAMAN(NpcIdentifiers.LIZARDMAN_SHAMAN_6767, 3200, AttributeKey.LIZARDMAN_SHAMANS_KILLED, new Tile(3169,3751,0), Category.BOSSES,true,true, true, new Item(DRAGON_WARHAMMER)),
    VENENATIS(NpcIdentifiers.VENENATIS_6610, 2800, AttributeKey.VENENATIS_KILLED, new Tile(3319, 3745, 0), Category.BOSSES, true, true, true, new Item(SARADOMIN_GODSWORD), new Item(ZAMORAK_GODSWORD), new Item(ZAMORAK_GODSWORD), new Item(JUSTICIAR_CHESTGUARD)),
    VETION(NpcIdentifiers.VETION, 2150, AttributeKey.VETIONS_KILLED, new Tile(3224, 3806, 0), Category.BOSSES, true, true, true, new Item(VETION_JR), new Item(TYRANNICAL_RING_I), new Item(TREASONOUS_RING_I), new Item(RING_OF_THE_GODS_I), new Item(ABYSSAL_DAGGER), new Item(NEITIZNOT_FACEGUARD), new Item(ARMADYL_GODSWORD)),
    SCORPIA(NpcIdentifiers.SCORPIA, 2800, AttributeKey.SCORPIAS_KILLED, new Tile(3232, 3950, 0), Category.BOSSES, true, true, true, new Item(SCORPIAS_OFFSPRING), new Item(ODIUM_SHARD_3), new Item(MALEDICTION_SHARD_3), new Item(DRAGON_CLAWS), new Item(DRAGON_HUNTER_LANCE)),
    VORKATH(NpcIdentifiers.VORKATH_8061, 4700, AttributeKey.VORKATHS_KILLED, new Tile(2273, 4049, 0), Category.BOSSES, false, true, true, new Item(VORKI), new Item(VORKATHS_HEAD_21907), new Item(DRACONIC_VISAGE), new Item(SKELETAL_VISAGE), new Item(JAR_OF_DECAY), new Item(DRAGONBONE_NECKLACE)),
    WORLD_BOSS(NpcIdentifiers.ZOMBIES_CHAMPION, 3500, null, null, Category.BOSSES, true, true, true, new Item(CustomItemIdentifiers.ZOMBIES_CHAMPION_PET), new Item(VESTAS_LONGSWORD), new Item(VESTAS_CHAINBODY), new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_FULL_HELM), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(MORRIGANS_COIF), new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS), new Item(ZURIELS_STAFF), new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM),
        new Item(CustomItemIdentifiers.BABY_LAVA_DRAGON), new Item(LAVA_DHIDE_COIF), new Item(LAVA_DHIDE_BODY), new Item(LAVA_DHIDE_CHAPS), new Item(KEY_OF_DROPS), new Item(LAVA_PARTYHAT), new Item(INFERNAL_CAPE)),
    ALCHY_HYDRA(NpcIdentifiers.ALCHEMICAL_HYDRA, 5500, AttributeKey.ALCHY_KILLED, new Tile(1354, 10258,0), Category.BOSSES, false, true, true, new Item(IKKLE_HYDRA), new Item(HYDRAS_CLAW), new Item(HYDRA_TAIL), new Item(HYDRA_LEATHER), new Item(HYDRAS_FANG), new Item(HYDRAS_EYE), new Item(HYDRAS_HEART), new Item(JAR_OF_CHEMICALS), new Item(ALCHEMICAL_HYDRA_HEADS)),
    BARRELCHEST(NpcIdentifiers.BARRELCHEST_6342, 2800, AttributeKey.BARRELCHESTS_KILLED, new Tile(3286,3881,0), Category.BOSSES, true, true, true, new Item(BARRELCHEST_PET), new Item(ANCIENT_WARRIOR_SWORD), new Item(ANCIENT_WARRIOR_AXE), new Item(ANCIENT_WARRIOR_MAUL), new Item(KEY_OF_DROPS)),
    CORRUPTED_NECHRYARCH(CustomNpcIdentifiers.CORRUPTED_NECHRYARCH, 4500, AttributeKey.CORRUPTED_NECHRYARCHS_KILLED, new Tile(1885, 3865,0), Category.BOSSES, false, true, true, new Item(PET_CORRUPTED_NECHRYARCH), new Item(CORRUPTED_BOOTS), new Item(KEY_OF_DROPS)),
    SARACHNIS(NpcIdentifiers.SARACHNIS, 2800, AttributeKey.SARACHNIS, new Tile(1847,9922,0), Category.BOSSES, false, true, true, new Item(SRARACHA), new Item(JAR_OF_EYES), new Item(GIANT_EGG_SACFULL), new Item(SARACHNIS_CUDGEL)),
    BRYOPHYTA(NpcIdentifiers.BRYOPHYTA, 2800, AttributeKey.KC_BRYOPHYTA, new Tile(3174, 9897,0), Category.BOSSES, false, true, true, new Item(BRYOPHYTAS_ESSENCE)),
    THE_NIGHTMARE(NpcIdentifiers.THE_NIGHTMARE_9430, 2800, AttributeKey.THE_NIGHTMARE_KC, new Tile(3808, 9777,1), Category.BOSSES, false, true, true, new Item(LITTLE_NIGHTMARE), new Item(INQUISITORS_MACE), new Item(INQUISITORS_GREAT_HELM), new Item(INQUISITORS_HAUBERK), new Item(INQUISITORS_PLATESKIRT), new Item(NIGHTMARE_STAFF), new Item(VOLATILE_ORB), new Item(HARMONISED_ORB), new Item(ELDRITCH_ORB), new Item(JAR_OF_DREAMS)),
    NEX(NpcIdentifiers.NEX, 2800, AttributeKey.NEX_KC, new Tile(2904, 5203,0), Category.BOSSES, false, true, true, new Item(NEXLING), new Item(ANCIENT_GODSWORD), new Item(ZARYTE_CROSSBOW), new Item(ZARYTE_VAMBRACES), new Item(TORVA_FULL_HELM), new Item(TORVA_PLATEBODY), new Item(TORVA_PLATELEGS)),
    ENRAGED_GORILLA(CustomNpcIdentifiers.ENRAGED_GORILLA_MELEE, 2800, AttributeKey.ENRAGED_GORILLA_KILLS, new Tile(3247, 3777, 0), Category.BOSSES, true, true, true, new Item(AMULET_OF_TORTURE), new Item(NECKLACE_OF_ANGUISH), new Item(LIGHT_BALLISTA), new Item(HEAVY_BALLISTA), new Item(TORMENTED_BRACELET), new Item(RING_OF_SUFFERING), new Item(TORMENTED_ORNAMENT_KIT), new Item(ANGUISH_ORNAMENT_KIT), new Item(TORTURE_ORNAMENT_KIT)),
    LAVA_DRAGON(NpcIdentifiers.LAVA_DRAGON, 2800, AttributeKey.LAVA_DRAGONS_KILLED, new Tile(3202, 3842, 0), Category.BOSSES, true, true, true, new Item(DRAGONFIRE_SHIELD)),
    TZTOK_JAD(NpcIdentifiers.TZTOKJAD, 5500, AttributeKey.JADS_KILLED, new Tile(2440, 5172), Category.BOSSES, false, false, true, new Item(FIRE_CAPE)),
    ARAGOG(CustomNpcIdentifiers.ARAGOG_15048, 2800, AttributeKey.KC_ARAGOG, new Tile(3283, 3823), Category.BOSSES, true, true, true, new Item(ARMADYL_GODSWORD), new Item(DRAGON_CLAWS), new Item(DONATOR_MYSTERY_BOX), new Item(PETS_MYSTERY_BOX)),

    /* PVM && Training places */
    ROCK_CRABS(NpcIdentifiers.ROCK_CRAB, 1000, new Tile(2706, 3713, 0), Category.PVM, false, false, true),
    YAK(NpcIdentifiers.YAK, 2000, new Tile(2325, 3800, 0), Category.PVM, false, false, true),
    STRONGHOLD_OF_SECURITY(NpcIdentifiers.MINOTAUR, 1500, new Tile(1859, 5243, 0), Category.PVM, false, false, true),
    SUQAH(NpcIdentifiers.SUQAH, 2000, new Tile(2113, 3915, 0), Category.PVM, false, false, true),
    BASILISK_KNIGHT(NpcIdentifiers.BASILISK_KNIGHT, 3500, new Tile(2437, 10397, 0), Category.PVM, false, false, true),
    REVENANTS(NpcIdentifiers.REVENANT_DRAGON, 3500, new Tile(3127, 3832, 0), Category.PVM, true, false, true),
    DAGANNOTHS(NpcIdentifiers.DAGANNOTH_971, 3000, new Tile(2442, 10147, 0), Category.PVM, false, false, true),
    SMOKE_DUNGEON(NpcIdentifiers.DUST_DEVIL, 2000, new Tile(3206, 9379, 0), Category.PVM, false, false, true),
    SAND_CRABS(NpcIdentifiers.SAND_CRAB, 1000, new Tile(1728, 3463, 0), Category.PVM, false, false, true),
    FOSSIL_WYVERN(NpcIdentifiers.ANCIENT_WYVERN, 3200, new Tile(3609, 10278, 0), Category.PVM, false, false, true),
    EXPERIMENTS(NpcIdentifiers.EXPERIMENT, 2000, new Tile(3577, 9927, 0), Category.PVM, false, false, true),
    ANCIENT_CAVERN(NpcIdentifiers.MITHRIL_DRAGON, 3500, new Tile(1746, 5327, 0), Category.PVM, false, false, true),
    CAVE_HORROR(NpcIdentifiers.CAVE_HORROR, 2000, new Tile(3826, 9425, 0), Category.PVM, false, false, true),
    BRINE_RAT(NpcIdentifiers.BRINE_RAT, 1000, new Tile(2692, 10124, 0), Category.PVM, false, false, true),
    LITHKREN(NpcIdentifiers.ADAMANT_DRAGON, 2800, AttributeKey.ADAMANT_DRAGONS_KILLED, new Tile(3565, 3998, 0), Category.PVM, false, false, true),
    KARUULM_DUNGEON(NpcIdentifiers.HYDRA, 5500, new Tile(1311, 3812, 0), Category.PVM, false, false, true),
    CATACOMBS(NpcIdentifiers.WARPED_JELLY, 2000, new Tile(1666, 10048, 0), Category.PVM, false, false, true),
    LUMBRIDGE_SWAMP(NpcIdentifiers.CAVE_CRAWLER, 1500, new Tile(3168, 9572, 0), Category.PVM, false, false, true),
    ASGARNIAN_ICE_DUNG(NpcIdentifiers.SKELETAL_WYVERN_466, 3500, new Tile(3007, 9550, 0), Category.PVM, false, false, true),
    MOURNER_TUNNELS(NpcIdentifiers.DARK_BEAST, 2500, new Tile(2029, 4636, 0), Category.PVM, false, false, true),
    EDGEVILLE_DUNGEON(NpcIdentifiers.BLACK_DEMON_1432, 3000, new Tile(3097, 9868, 0), Category.PVM, false, false, true),
    SLAYER_TOWER(NpcIdentifiers.ABYSSAL_DEMON_415, 2150, new Tile(3420, 3535, 0), Category.PVM, false, false, true),
    BRIMHAVEN_DUNGEON(NpcIdentifiers.FIRE_GIANT, 2150, new Tile(2709, 9564, 0), Category.PVM, false, false, true),
    TAVERLEY_DUNGEON(NpcIdentifiers.BLUE_DRAGON, 2150, new Tile(2884, 9799, 0), Category.PVM, false, false, true),
    SLAYER_STRONGHOLD(NpcIdentifiers.CAVE_KRAKEN, 4500, new Tile(2431, 3421, 0), Category.PVM, false, false, true),
    RELLEKKA_SLAYER(NpcIdentifiers.CAVE_HORROR, 2150, new Tile(2803, 9998, 0), Category.PVM, false, false, true),

    /* Pking */
    BANDIT_CAMP(NpcIdentifiers.BANDIT, 1500, new Tile(3034, 3690), Category.PKING,true, true, false, "- Level 18-23 multi wilderness"),
    CHAOS_TEMPLE(NpcIdentifiers.ELDER_CHAOS_DRUID, 1500, new Tile(3235, 3643), Category.PKING, true, true, false, "- Level 16-20 multi wilderness"),
    EAST_DRAGONS(NpcIdentifiers.GREEN_DRAGON, 2150, new Tile(3343, 3664), Category.PKING, true, false, false, "- Level 19-20 single wilderness"),
    WEST_DRAGONS(NpcIdentifiers.GREEN_DRAGON, 2150, new Tile(2978, 3598), Category.PKING, true, false, false, "- Level 19-20 single wilderness"),
    GRAVEYARD(NpcIdentifiers.SKELETON, 2150, new Tile(3161, 3670), Category.PKING, true, false, false, "- Level 19-20 single wilderness"),
    MAGE_BANK(NpcIdentifiers.MAGE_OF_ZAMORAK, 2150, new Tile(2540, 4717), Category.PKING, false, false, true, "You will be teleported to the", "mage area lobby.", "<br>This is the location for the", Color.CYAN.tag()+"Mage Arena minigame!<br>", "Mage arena points can be earned,", "by killing battle mages. You can", "spend your earned points, at the", "shop."),
    THE_GATE(NpcIdentifiers.WOLF, 2150, new Tile(3225, 3903), Category.PKING, true, true, false, "- Level 48-49 multi wilderness"),
    RISK_ARENA(NpcIdentifiers.COW31337KILLER, 1500, new Tile(3327,4754), Category.PKING, false, false, true, "- PJ is disallowed in this area and", "results in a ban!<br>", "- "+ Color.RED.tag()+"ALL ITEMS ARE LOST ON DEATH!");

    /**
     * The npc id that will be send to the model viewer,
     * and is also used to gather info about this boss.
     */
    private final int npcId;

    /**
     * This number determines the size of the model.
     */
    private final int modelZoom;

    /**
     * The key which will be used to grab the killcount.
     */
    private final AttributeKey killcountKey;

    /**
     * The location to teleport the player to.
     */
    private final Tile tile;

    /**
     * The teleport category.
     */
    private final Category category;

    /**
     * A flag that opens up a warning dialogue.
     */
    private final boolean warning;

    /**
     * A flag to determine if the teleport location is in a multi area.
     */
    private final boolean multiArea;

    /**
     * A flag to determine if we should send the configs.
     */
    private final boolean loadConfigs;

    private final String[] description;

    private final Item[] bestPossibleDrops;

    public int id() {
        return npcId;
    }

    public int zoom() {
        return modelZoom;
    }

    public AttributeKey killcountKey() {
        return killcountKey;
    }

    public Tile tile() {
        return tile;
    }

    public Category category() {
        return category;
    }

    public boolean warning() {
        return warning;
    }

    public boolean multiArea() {
        return multiArea;
    }

    public boolean loadConfigs() {
        return loadConfigs;
    }

    public String[] description() {
        return description;
    }

    public Item[] bestPossibleDrops() {
        return bestPossibleDrops;
    }

    /**
     * This constructs the enum.
     */
    TeleportLocations(int npcId, int modelZoom, Tile tile, Category category, boolean warning, boolean multiArea, boolean loadConfigs, String... description) {
        this.npcId = npcId;
        this.modelZoom = modelZoom;
        this.killcountKey = null;
        this.tile = tile;
        this.category = category;
        this.warning = warning;
        this.multiArea = multiArea;
        this.loadConfigs = loadConfigs;
        this.description = description;
        this.bestPossibleDrops = new Item[] {};
    }

    TeleportLocations(int npcId, int modelZoom, AttributeKey killcountKey, Tile tile, Category category, boolean warning, boolean multiArea, boolean loadConfigs, Item... bestPossibleDrops) {
        this.npcId = npcId;
        this.modelZoom = modelZoom;
        this.killcountKey = killcountKey;
        this.tile = tile;
        this.category = category;
        this.warning = warning;
        this.multiArea = multiArea;
        this.loadConfigs = loadConfigs;
        this.description = new String[] {};
        this.bestPossibleDrops = bestPossibleDrops;
    }

    TeleportLocations(int npcId, int modelZoom, Tile tile, Category category, boolean warning, boolean multiArea, boolean loadConfigs) {
        this.npcId = npcId;
        this.modelZoom = modelZoom;
        this.killcountKey = null;
        this.tile = tile;
        this.category = category;
        this.warning = warning;
        this.multiArea = multiArea;
        this.loadConfigs = loadConfigs;
        this.description = new String[] {};
        this.bestPossibleDrops = new Item[] {};
    }

    /**
     * Filters the locations by category.
     *
     * @param category the category to filter.
     * @return returns a list of teleports in that category.
     */
    public static List<TeleportLocations> filterByCategory(Category category) {
        return Arrays.stream(values()).filter(c -> c.category == category).sorted(Comparator.comparing(Enum::name)).collect(Collectors.toList());
    }
}

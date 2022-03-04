package com.valinor.game.content.teleport.world_teleport_manager;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;
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
    BARRELCHEST(NpcIdentifiers.BARRELCHEST_6342, 2800, AttributeKey.BARRELCHESTS_KILLED, new Tile(3286,3881,0), Category.PVM, true, true, true, new Item(BARRELCHEST_PET), new Item(7806), new Item(7807), new Item(7808), new Item(2944)),
    CERBERUS(NpcIdentifiers.CERBERUS, 3000, AttributeKey.CERBERUS_KILLED, new Tile(1240, 1237,0), Category.PVM, false, false, true, new Item(HELLPUPPY), new Item(SMOULDERING_STONE), new Item(ETERNAL_BOOTS), new Item(PEGASIAN_BOOTS), new Item(PRIMORDIAL_BOOTS), new Item(GHRAZI_RAPIER), new Item(NIGHTMARE_STAFF), new Item(JAR_OF_SOULS)),
    CHAOS_ELEMENTAL(NpcIdentifiers.CHAOS_ELEMENTAL, 2800, AttributeKey.CHAOS_ELEMENTALS_KILLED, new Tile(3304,3913,0), Category.PVM, true, true, true, new Item(PET_CHAOS_ELEMENTAL), new Item(KORASI_SWORD), new Item(ELEMENTAL_BOW)),
    CORPOREAL_BEAST(NpcIdentifiers.CORPOREAL_BEAST, 2150, AttributeKey.CORPOREAL_BEASTS_KILLED, new Tile(2969, 4382, 2), Category.PVM, false, true, true, new Item(PET_DARK_CORE), new Item(SPIRIT_SHIELD), new Item(BLESSED_SPIRIT_SHIELD), new Item(SPECTRAL_SPIRIT_SHIELD), new Item(ARCANE_SPIRIT_SHIELD), new Item(ELYSIAN_SPIRIT_SHIELD)),
    CRAZY_ARCHAEOLOGIST(NpcIdentifiers.CRAZY_ARCHAEOLOGIST, 1500, AttributeKey.CRAZY_ARCHAEOLOGISTS_KILLED, new Tile(2975,3696,0), Category.PVM, true, true, true, new Item(ODIUM_WARD), new Item(MALEDICTION_WARD), new Item(MYSTERY_BOX), new Item(ARMOUR_MYSTERY_BOX), new Item(WEAPON_MYSTERY_BOX), new Item(DONATOR_MYSTERY_BOX), new Item(LEGENDARY_MYSTERY_BOX)),
    DEMONIC_GORILLA(NpcIdentifiers.DEMONIC_GORILLA, 2800, AttributeKey.DEMONIC_GORILLAS_KILLED, new Tile(3238, 3771,0), Category.PVM, true, true, true,  new Item(RING_OF_SUFFERING), new Item(TORMENTED_BRACELET), new Item(NECKLACE_OF_ANGUISH), new Item(AMULET_OF_TORTURE), new Item(LIGHT_BALLISTA), new Item(HEAVY_BALLISTA)),
    KING_BLACK_DRAGON(NpcIdentifiers.KING_BLACK_DRAGON, 2800, AttributeKey.KING_BLACK_DRAGONS_KILLED, new Tile(3001, 3849, 0), Category.PVM, true, false, true, new Item(PRINCE_BLACK_DRAGON), new Item(KBD_HEADS), new Item(DRAGONFIRE_SHIELD), new Item(DRAGON_HUNTER_CROSSBOW), new Item(ARMADYL_GODSWORD), new Item(DRAGON_CLAWS)),
    KRAKEN(NpcIdentifiers.KRAKEN, 5000, AttributeKey.KRAKENS_KILLED, new Tile(2280, 10016,0), Category.PVM, false, true, true, new Item(ABYSSAL_TENTACLE), new Item(TRIDENT_OF_THE_SEAS), new Item(TOXIC_STAFF_OF_THE_DEAD), new Item(MYSTERY_BOX), new Item(DONATOR_MYSTERY_BOX), new Item(JAR_OF_DIRT)),
    LIZARDMAN_SHAMAN(NpcIdentifiers.LIZARDMAN_SHAMAN_6767, 3200, AttributeKey.LIZARDMAN_SHAMANS_KILLED, new Tile(3169,3751,0), Category.PVM,true,true, true, new Item(BANDOS_CHESTPLATE), new Item(BANDOS_TASSETS), new Item(MYSTERY_BOX), new Item(NEITIZNOT_FACEGUARD), new Item(DRAGON_WARHAMMER)),
    VENENATIS(NpcIdentifiers.VENENATIS_6610, 2800, AttributeKey.VENENATIS_KILLED, new Tile(3319, 3745, 0), Category.PVM, true, true, true, new Item(SARADOMIN_GODSWORD), new Item(ZAMORAK_GODSWORD), new Item(ZAMORAK_GODSWORD), new Item(JUSTICIAR_CHESTGUARD)),
    VETION(NpcIdentifiers.VETION, 2150, AttributeKey.VETIONS_KILLED, new Tile(3224, 3806, 0), Category.PVM, true, true, true, new Item(VETION_JR), new Item(TYRANNICAL_RING_I), new Item(TREASONOUS_RING_I), new Item(RING_OF_THE_GODS_I), new Item(ABYSSAL_DAGGER), new Item(NEITIZNOT_FACEGUARD), new Item(ARMADYL_GODSWORD)),
    VORKATH(NpcIdentifiers.VORKATH_8061, 4700, AttributeKey.VORKATHS_KILLED, new Tile(2273, 4049, 0), Category.PVM, false, true, true, new Item(VORKI), new Item(VORKATHS_HEAD), new Item(DRAGONFIRE_WARD), new Item(ANCIENT_WYVERN_SHIELD), new Item(DONATOR_MYSTERY_BOX), new Item(SCYTHE_OF_VITUR), new Item(JAR_OF_DECAY)),
    ZULRAH(NpcIdentifiers.ZULRAH, 6000, AttributeKey.ZULRAHS_KILLED, new Tile(2200, 3055, 0), Category.PVM, false, true, true, new Item(PET_SNAKELING), new Item(SERPENTINE_HELM), new Item(TANZANITE_HELM), new Item(MAGMA_HELM), new Item(TRIDENT_OF_THE_SWAMP), new Item(TOXIC_STAFF_OF_THE_DEAD), new Item(TOXIC_BLOWPIPE), new Item(MYSTERY_BOX), new Item(ARMOUR_MYSTERY_BOX), new Item(WEAPON_MYSTERY_BOX),new Item(DONATOR_MYSTERY_BOX), new Item(LEGENDARY_MYSTERY_BOX), new Item(JAR_OF_SWAMP)),
    CALLISTO(NpcIdentifiers.CALLISTO_6609, 2800, AttributeKey.CALLISTOS_KILLED, new Tile(3307, 3837, 0), Category.PVM, true, true, true, new Item(DRAGON_PICKAXE), new Item(ABYSSAL_BLUDGEON), new Item(TYRANNICAL_RING), new Item(STAFF_OF_LIGHT), new Item(DRAGON_CROSSBOW), new Item(JUSTICIAR_FACEGUARD)),
    SCORPIA(NpcIdentifiers.SCORPIA, 2800, AttributeKey.SCORPIAS_KILLED, new Tile(3232, 3950, 0), Category.PVM, true, true, true, new Item(ODIUM_WARD_12807), new Item(MALEDICTION_WARD_12806), new Item(STAFF_OF_THE_DEAD), new Item(ABYSSAL_DAGGER), new Item(JUSTICIAR_LEGGUARDS)),
    CHAOS_FANATIC(NpcIdentifiers.CHAOS_FANATIC, 1500, AttributeKey.CHAOS_FANATICS_KILLED, new Tile(2992, 3851, 0), Category.PVM, true, true, true, new Item(PET_CHAOS_ELEMENTAL), new Item(BLESSED_SPIRIT_SHIELD), new Item(ODIUM_WARD_12807), new Item(MALEDICTION_WARD_12806), new Item(SANGUINESTI_STAFF)),
    THERMY_SMOKE_DEVIL(NpcIdentifiers.THERMONUCLEAR_SMOKE_DEVIL, 2800, AttributeKey.THERMONUCLEAR_SMOKE_DEVILS_KILLED, new Tile(2379, 9452, 0), Category.PVM, false, true, true, new Item(SMOKE_BATTLESTAFF), new Item(OCCULT_NECKLACE), new Item(TOXIC_STAFF_OF_THE_DEAD)),
    ADAMANT_DRAGON(NpcIdentifiers.ADAMANT_DRAGON, 2800, AttributeKey.ADAMANT_DRAGONS_KILLED, new Tile(3021, 3823, 0), Category.PVM, true, false, true, new Item(DRAGONFIRE_WARD), new Item(ANCIENT_WYVERN_SHIELD), new Item(DRAGON_CROSSBOW), new Item(DRAGON_METAL_SLICE)),
    RUNE_DRAGON(NpcIdentifiers.RUNE_DRAGON, 2800, AttributeKey.RUNE_DRAGONS_KILLED, new Tile(3081, 3875, 0), Category.PVM, true, false, true, new Item(DRAGONFIRE_WARD), new Item(ANCIENT_WYVERN_SHIELD), new Item(DRAGONFIRE_SHIELD), new Item(DRAGON_CROSSBOW), new Item(DRAGON_METAL_SLICE)),
    LAVA_DRAGON(NpcIdentifiers.LAVA_DRAGON, 2800, AttributeKey.LAVA_DRAGONS_KILLED, new Tile(3202, 3842, 0), Category.PVM, true, true, true, new Item(DRAGONFIRE_SHIELD)),
    ANCIENT_WYVERN(NpcIdentifiers.ANCIENT_WYVERN, 2800, AttributeKey.FOSSIL_WYVERN, new Tile(2963, 3828, 0), Category.PVM, true, false, true, new Item(ANTIQUE_EMBLEM_TIER_1), new Item(GRANITE_LONGSWORD), new Item(ANCIENT_WYVERN_SHIELD), new Item(DRAGONFIRE_SHIELD)),
    DRAKE(NpcIdentifiers.DRAKE_8612, 2800, AttributeKey.DRAKE, new Tile(3263, 3886, 0), Category.PVM, true, true, true, new Item(BLADE_OF_SAELDOR)),
    WYRM(NpcIdentifiers.WYRM_8611, 3500, AttributeKey.WYRM, new Tile(2983, 3789, 0), Category.PVM, true, false, true, new Item(DRAGON_HUNTER_CROSSBOW)),
    HYDRA(NpcIdentifiers.HYDRA, 5500, AttributeKey.HYDRA, new Tile(3029, 3907, 0), Category.PVM, true, false, true, new Item(DRAGON_HUNTER_LANCE)),
    WORLD_BOSS(NpcIdentifiers.SKOTIZO, 5500, null, null, Category.PVM, true, true, true, new Item(DRAGON_SWORD), new Item(DRAGON_HARPOON), new Item(DINHS_BULWARK), new Item(KODAI_WAND), new Item(ELDER_MAUL), new Item(ANCESTRAL_HAT), new Item(ANCESTRAL_ROBE_TOP), new Item(ANCESTRAL_ROBE_BOTTOM), new Item(GHRAZI_RAPIER), new Item(ARMADYL_CROSSBOW), new Item(ARMADYL_GODSWORD), new Item(VESTAS_LONGSWORD), new Item(VESTAS_CHAINBODY), new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_FULL_HELM), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(MORRIGANS_COIF), new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS), new Item(ZURIELS_STAFF), new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM), new Item(DARK_CLAW), new Item(JAR_OF_DARKNESS), new Item(DRAGONFIRE_WARD), new Item(ANCIENT_WYVERN_SHIELD), new Item(DRAGON_HUNTER_CROSSBOW), new Item(DRAGONFIRE_SHIELD), new Item(JUSTICIAR_FACEGUARD), new Item(JUSTICIAR_CHESTGUARD), new Item(JUSTICIAR_LEGGUARDS)),
    REVENANT_CAVES(NpcIdentifiers.REVENANT_DRAGON, 2150, AttributeKey.REVENANTS_KILLED, new Tile(3076, 3651, 0), Category.PVM, true, true, true, new Item(VIGGORAS_CHAINMACE), new Item(CRAWS_BOW), new Item(THAMMARONS_SCEPTRE), new Item(AMULET_OF_AVARICE)),
    TZTOK_JAD(NpcIdentifiers.TZTOKJAD, 5500, AttributeKey.JADS_KILLED, new Tile(2440, 5172), Category.PVM, false, false, true, new Item(DRAGON_CLAWS), new Item(INFERNAL_CAPE)),
    GODWARS_DUNGEON(NpcIdentifiers.KREEARRA, 5500, null, null, Category.PVM, false, false, true, new Item(BANDOS_CHESTPLATE), new Item(BANDOS_TASSETS), new Item(BANDOS_GODSWORD), new Item(SARADOMIN_GODSWORD), new Item(ARMADYL_CROSSBOW), new Item(ARMADYL_HELMET), new Item(ARMADYL_CHESTPLATE), new Item(ARMADYL_CHAINSKIRT), new Item(ARMADYL_GODSWORD), new Item(ZAMORAKIAN_SPEAR), new Item(ZAMORAK_GODSWORD)),

    /* PVM && Training places */
    AREA_FOR_SKILLING(NpcIdentifiers.BLACK_CHINCHOMPA, 1000, new Tile(2805,2785, 0), Category.TRAINING, false, false, false, "An area containing many different<br>skilling resources. It also makes a<br>great hangout!"),
    ROCK_CRABS(NpcIdentifiers.ROCK_CRAB, 1000, new Tile(2706, 3713, 0), Category.TRAINING, false, false, false),
    SLAYER_TOWER(NpcIdentifiers.ABYSSAL_DEMON_415, 2150, new Tile(3420, 3535, 0), Category.TRAINING, false, false, false),
    BRIMHAVEN_DUNGEON(NpcIdentifiers.FIRE_GIANT, 2150, new Tile(2709, 9564, 0), Category.TRAINING, false, false, false),
    TAVERLEY_DUNGEON(NpcIdentifiers.BLUE_DRAGON, 2150, new Tile(2884, 9799, 0), Category.TRAINING, false, false, false),
    SLAYER_STRONGHOLD(NpcIdentifiers.CAVE_KRAKEN, 4500, new Tile(2431, 3421, 0), Category.TRAINING, false, false, false),
    RELLEKKA_SLAYER(NpcIdentifiers.CAVE_HORROR, 2150, new Tile(2803, 9998, 0), Category.TRAINING, false, false, false),

    /* Pking */
    BANDIT_CAMP(NpcIdentifiers.BANDIT, 1500, new Tile(3034, 3690), Category.PKING,true, true, false, "- Level 18-23 multi wilderness"),
    CHAOS_TEMPLE(NpcIdentifiers.ELDER_CHAOS_DRUID, 1500, new Tile(3235, 3643), Category.PKING, true, true, false, "- Level 16-20 multi wilderness"),
    EAST_DRAGONS(NpcIdentifiers.GREEN_DRAGON, 2150, new Tile(3343, 3664), Category.PKING, true, false, false, "- Level 19-20 single wilderness"),
    WEST_DRAGONS(NpcIdentifiers.GREEN_DRAGON, 2150, new Tile(2978, 3598), Category.PKING, true, false, false, "- Level 19-20 single wilderness"),
    GRAVEYARD(NpcIdentifiers.SKELETON, 2150, new Tile(3161, 3670), Category.PKING, true, false, false, "- Level 19-20 single wilderness"),
    MAGE_BANK(NpcIdentifiers.MAGE_OF_ZAMORAK, 2150, new Tile(2540, 4717), Category.PKING, false, false, false, "You will be teleported to the", "mage area lobby.", "<br>This is the location for the", Color.CYAN.tag()+"Mage Arena minigame!<br>", "Mage arena points can be earned,", "by killing battle mages. You can", "spend your earned points, at the", "shop."),
    THE_GATE(NpcIdentifiers.WOLF, 2150, new Tile(3225, 3903), Category.PKING, true, true, false, "- Level 48-49 multi wilderness"),
    RISK_ARENA(NpcIdentifiers.COW31337KILLER, 1500, new Tile(3327,4754), Category.PKING, false, false, false, "- PJ is disallowed in this area and", "results in a ban!<br>", "- "+ Color.RED.tag()+"ALL ITEMS ARE LOST ON DEATH!");

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

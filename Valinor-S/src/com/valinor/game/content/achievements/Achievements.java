package com.valinor.game.content.achievements;

import com.valinor.game.GameConstants;
import com.valinor.game.world.items.Item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | April, 14, 2021, 16:26
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public enum Achievements {

    /*
     * ROMAN NUMBERS:
     * I
     * II
     * III
     * IV
     * V
     * VI
     * VII
     * VIII
     * IX
     * X
     */
    //Misc
    COMPLETIONIST("Completionist", "Complete all Achievements besides this one.", 1, Difficulty.HARD, new Item(COINS_995, 250_000_000), new Item(DONATOR_MYSTERY_BOX, 3), new Item(MYSTERY_CHEST)),
    VOTE_FOR_US_I("Vote I", "Claim at least 100 vote points.<br>Type ::vote to support "+ GameConstants.SERVER_NAME,100, Difficulty.EASY, new Item(VOTE_TICKET, 5), new Item(CRYSTAL_KEY+1, 2)),
    VOTE_FOR_US_II("Vote II", "Claim at least 250 vote points.<br>Type ::vote to support "+GameConstants.SERVER_NAME,250, Difficulty.MED, new Item(VOTE_TICKET, 25), new Item(DONATOR_MYSTERY_BOX, 1)),
    VOTE_FOR_US_III("Vote III", "Claim at least 500 vote points.<br>Type ::vote to support "+GameConstants.SERVER_NAME,500, Difficulty.HARD, new Item(VOTE_TICKET, 50), new Item(MYSTERY_TICKET, 1), new Item(MYSTERY_CHEST)),
    CRYSTAL_LOOTER_I("Crystal looter I", "Open the crystal chest 10 times.", 10, Difficulty.EASY, new Item(COINS_995, 500_000), new Item(CRYSTAL_KEY+1, 1)),
    CRYSTAL_LOOTER_II("Crystal looter II", "Open the crystal chest 50 times.", 50, Difficulty.MED, new Item(COINS_995, 5_000_000), new Item(CRYSTAL_KEY+1, 10)),
    CRYSTAL_LOOTER_III("Crystal looter III", "Open the crystal chest 100 times.", 100, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(CRYSTAL_KEY+1, 25)),
    BRIMSTONE_LOOTER_I("Brimstone looter I", "Open the brimstone chest 10 times.", 10, Difficulty.EASY, new Item(COINS_995, 500_000), new Item(BRIMSTONE_KEY, 1)),
    BRIMSTONE_LOOTER_II("Brimstone looter II", "Open the brimstone chest 50 times.", 50, Difficulty.MED, new Item(COINS_995, 5_000_000), new Item(BRIMSTONE_KEY, 5)),
    BRIMSTONE_LOOTER_III("Brimstone looter III", "Open the brimstone chest 100 times.", 100, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(BRIMSTONE_KEY, 10)),
    LARRANS_LOOTER_I("Larran's looter I", "Open the Larran's chest 10 times.", 10, Difficulty.EASY, new Item(LARRANS_KEY, 2)),
    LARRANS_LOOTER_II("Larran's looter II", "Open the Larran's chest 50 times.", 50, Difficulty.MED, new Item(LARRANS_KEY, 10)),
    LARRANS_LOOTER_III("Larran's looter III", "Open the Larran's chest 100 times.", 100, Difficulty.HARD, new Item(LARRANS_KEY, 20)),

    //Pvm
    YAK_HUNTER("Yak hunter", "Kill 50 Yaks.", 50, Difficulty.EASY, new Item(COINS_995, 500_000), new Item(DRAGON_SCIMITAR, 1), new Item(CRYSTAL_KEY+1, 2)),
    ROCK_CRAB_HUNTER("Rock crab hunter", "Kill 50 Rock crabs.", 50, Difficulty.EASY, new Item(SUPER_COMBAT_POTION4+1,10), new Item(CRYSTAL_KEY+1,1), new Item(PRAYER_POTION4+1,10), new Item(MONKFISH+1,50)),
    SAND_CRAB_HUNTER("Sand crab hunter", "Kill 50 Sand crabs.", 50, Difficulty.EASY, new Item(RANGING_POTION4+1,10), new Item(CRYSTAL_KEY+1,1), new Item(SANFEW_SERUM4+1,10), new Item(MONKFISH+1,50)),
    EXPERIMENTS_HUNTER("Experiments hunter", "Kill 50 Experiments.", 50, Difficulty.EASY, new Item(SUPER_COMBAT_POTION4+1,10), new Item(CRYSTAL_KEY+1,1), new Item(PRAYER_POTION4+1,10)),
    DRAGON_SLAYER_I("Dragon slayer I", "Kill 250 dragons.", 250, Difficulty.EASY, new Item(DRAGON_BONES+1,100)),
    DRAGON_SLAYER_II("Dragon slayer II", "Kill 100 King black dragons.", 100, Difficulty.HARD, new Item(COINS_995,10_000_000), new Item(DRAGON_BONES+1,200), new Item(BLACK_DRAGONHIDE+1,500), new Item(DONATOR_MYSTERY_BOX,1)),
    DRAGON_SLAYER_III("Dragon slayer III", "Kill 100 adamant or rune dragons.", 100, Difficulty.HARD, new Item(DRAGON_BONES+1,100), new Item(RUNITE_BAR+1,200), new Item(ADAMANTITE_BAR+1,400), new Item(DONATOR_MYSTERY_BOX,1)),
    FLUFFY("Fluffy", "Kill Cerberus 350 times.", 350, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(DONATOR_MYSTERY_BOX)),
    ULTIMATE_CHAOS("Ultimate chaos", "Kill 500 Chaos elementals.", 500, Difficulty.HARD, new Item(COINS_995, 50_000_000), new Item(DONATOR_MYSTERY_BOX,2), new Item(DRAGON_PICKAXE)),
    LORD_OF_THE_RINGS("Lord of the rings", "Kill 250 dagannoth kings.", 250, Difficulty.MED, new Item(COINS_995, 7_500_000), new Item(CRYSTAL_KEY+1, 15)),
    SQUIDWARD("Squidward", "Kill 500 Krakens", 500, Difficulty.MED, new Item(DONATOR_MYSTERY_BOX), new Item(COINS_995, 10_000_000)),
    DR_CURT_CONNORS("Dr. Curt Connors", "Kill 300 Lizardman shaman.", 300, Difficulty.MED, new Item(COINS_995, 7_500_000), new Item(DONATOR_MYSTERY_BOX, 1)),
    TSJERNOBYL("Tsjernobyl", "Kill 500 Thermonuclear smoke devil.", 500, Difficulty.MED, new Item(COINS_995, 10_000_000), new Item(SMOKE_BATTLESTAFF, 1), new Item(OCCULT_NECKLACE, 1)),
    VETION("Vet'ion", "Kill 150 Vet'ions.", 150, Difficulty.HARD, new Item(COINS_995, 50_000_000), new Item(DONATOR_MYSTERY_BOX, 3), new Item(DOUBLE_DROPS_LAMP, 1)),
    VENENATIS("Venenatis", "Kill Venenatis 350 times.", 350, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(DONATOR_MYSTERY_BOX, 1), new Item(DOUBLE_DROPS_LAMP, 1)),
    BARK_SCORPION("Bark scorpion", "Kill scorpia 500 times.", 500, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(DONATOR_MYSTERY_BOX, 1), new Item(LARRANS_KEY, 5)),
    BEAR_GRYLLS("Bear Grylls", "Kill 250 Callisto.", 250, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(DONATOR_MYSTERY_BOX, 1), new Item(LARRANS_KEY, 5)),
    SNAKE_CHARMER("Snake charmer", "Kill 1000 Zulrah.", 1000, Difficulty.HARD, new Item(DOUBLE_DROPS_LAMP, 2), new Item(MYSTERY_TICKET, 1), new Item(MYSTERY_CHEST, 1)),
    VORKY("Vorky", "Kill 100 Vorkaths.", 100, Difficulty.HARD, new Item(COINS_995, 50_000_000), new Item(MYSTERY_TICKET), new Item(VORKATHS_HEAD_21907)),
    REVENANT_HUNTER("Revenant hunter", "Kill 10000 revenants.", 10000, Difficulty.HARD, new Item(MYSTERY_TICKET, 1), new Item(DONATOR_MYSTERY_BOX, 3), new Item(CRAWS_BOW_C, 1)),
    GODWAR("Godwar", "Kill 500 Godwars Dungeon Bosses", 500, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(DONATOR_MYSTERY_BOX, 2), new Item(GODSWORD_BLADE, 1)),
    CORPOREAL_CRITTER("Corporeal Critter", "Kill 100 Corporeal beasts.", 100, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(BLESSED_SPIRIT_SHIELD, 1), new Item(MYSTERY_TICKET, 1)),

    SKILLER_I("Skiller I", "Earn a total level of 750.", 1, Difficulty.EASY, new Item(COINS_995, 500_000), new Item(ANTIQUE_LAMP_11137, 1)),
    SKILLER_II("Skiller II", "Earn a total level of 1000.", 1, Difficulty.MED, new Item(COINS_995, 5_000_000), new Item(ANTIQUE_LAMP_11137, 2)),
    SKILLER_III("Skiller III", "Earn a total level of 1500.", 1, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(ANTIQUE_LAMP_11137, 2), new Item(DONATOR_MYSTERY_BOX, 1)),
    SKILLER_IV("Skiller IV", "Earn level 99 in all skills.", 1, Difficulty.HARD, new Item(COINS_995, 100_000_000), new Item(PETS_MYSTERY_BOX, 1), new Item(DOUBLE_DROPS_LAMP, 2), new Item(MYSTERY_TICKET, 1)),

    TASK_MASTER_I("Task master I", "Complete 10 tasks.", 10, Difficulty.EASY, new Item(SLAYER_KEY,10)),
    TASK_MASTER_II("Task master II", "Complete 25 tasks.", 25, Difficulty.MED, new Item(SLAYER_KEY,20)),
    TASK_MASTER_III("Task master III", "Complete 50 tasks.", 50, Difficulty.HARD, new Item(SLAYER_KEY,50), new Item(DONATOR_MYSTERY_BOX,1)),

    //Minigames
    BARROWS_I("Barrows I", "Complete 10 barrows runs.", 10, Difficulty.EASY, new Item(COINS_995, 2_500_000), new Item(BARROWS_MYSTERY_BOX, 1)),
    BARROWS_II("Barrows II", "Complete 30 barrows runs.", 30, Difficulty.MED, new Item(COINS_995, 5_000_000), new Item(BARROWS_MYSTERY_BOX, 5)),
    BARROWS_III("Barrows III", "Complete 50 barrows runs.", 50, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(BARROWS_MYSTERY_BOX, 10), new Item(DHAROKS_PLATELEGS, 1)),
    DEFENDER_HUNT_I("Defender hunter I", "Obtain the bronze defender.", 1, Difficulty.EASY, new Item(WARRIOR_GUILD_TOKEN, 25)),
    DEFENDER_HUNT_II("Defender hunter II", "Obtain the iron defender.", 1, Difficulty.EASY, new Item(WARRIOR_GUILD_TOKEN, 50)),
    DEFENDER_HUNT_III("Defender hunter III", "Obtain the steel defender.", 1, Difficulty.EASY, new Item(WARRIOR_GUILD_TOKEN, 75)),
    DEFENDER_HUNT_IV("Defender hunter IV", "Obtain the black defender.", 1, Difficulty.EASY, new Item(WARRIOR_GUILD_TOKEN, 100)),
    DEFENDER_HUNT_V("Defender hunter V", "Obtain the mithril defender.", 1, Difficulty.EASY, new Item(WARRIOR_GUILD_TOKEN, 125)),
    DEFENDER_HUNT_VI("Defender hunter VI", "Obtain the adamant defender.", 1, Difficulty.EASY, new Item(WARRIOR_GUILD_TOKEN, 175)),
    DEFENDER_HUNT_VII("Defender hunter VII", "Obtain the rune defender.", 1, Difficulty.MED, new Item(WARRIOR_GUILD_TOKEN, 225)),
    DEFENDER_HUNT_IX("Defender hunter IX", "Obtain the dragon defender.", 1, Difficulty.MED, new Item(SUPER_COMBAT_POTION4+1, 50), new Item(PRAYER_POTION4+1, 50), new Item(BIG_BONES+1, 100)),
    FIGHT_CAVES_I("Fight caves I", "Defeat TzTok-Jad.", 1, Difficulty.HARD, new Item(COINS_995, 5_000_000), new Item(DONATOR_MYSTERY_BOX, 1)),
    FIGHT_CAVES_II("Fight caves II", "Defeat TzTok-Jad three times.", 3, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(MYSTERY_TICKET, 1), new Item(SUPER_COMBAT_POTION4+1, 10), new Item(PRAYER_POTION4+1, 10)),

    //Skilling

    /* Runecrafting */
    RUNE_MYSTERIES("Rune Mysteries", "Craft 15000 runes.", 15000, Difficulty.HARD, new Item(DONATOR_MYSTERY_BOX, 1), new Item(COINS_995, 10_000_000), new Item(WRATH_RUNE, 5000)),

    /* Construction *///TODO
    //OWN_A_HOUSE("Own a house", "Purchase your first house.", 1, Difficulty.EASY, new Item(COINS_995, 1_500_000)),
    //CONSTRUCTION_WORKER("Construction worker", "Reach level 75 construction.", 1, Difficulty.MED, new Item(COINS_995, 2_500_000), new Item(OAK_PLANK+1, 200)),
    //MASTER_IN_CONSTRUCTION("Master in construction", "Reach level 99 construction.", 1, Difficulty.HARD, new Item(COINS_995, 7_500_000), new Item(MAHOGANY_PLANK+1, 200)),

    /* Agility */
    PARKOUR("Parkour", "Complete 150 laps of agility courses.", 150, Difficulty.MED, new Item(COINS_995, 10_000_000), new Item(MARK_OF_GRACE, 100), new Item(ROYAL_SEED_POD, 1), new Item(STAMINA_POTION4+1, 50)),

    /* Herblore */
    BREWING("Brewing", "Make 500 potions.", 500, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(SNAPDRAGON_POTION_UNF+1, 150)),

    /* Thieving */
    MASTER_THIEF("Master thief", "Steal 1000 supplies from any stall.", 1000, Difficulty.HARD, new Item(COINS_995, 2_500_000), new Item(ROGUE_TOP), new Item(ROGUE_MASK), new Item(ROGUE_TROUSERS), new Item(ROGUE_GLOVES), new Item(ROGUE_BOOTS)),

    /* Crafting */
    LAPIDARIST("Lapidarist", "Cut 1000 gems of any kind.", 1000, Difficulty.MED, new Item(UNCUT_ONYX, 1), new Item(UNCUT_DRAGONSTONE+1, 50), new Item(UNCUT_RUBY+1, 100)),

    /* Fletching */
    FLETCHER("Fletcher", "Carve 1000 bows of any kind.", 1000, Difficulty.MED, new Item(COINS_995, 2_500_000), new Item(MAGIC_LONGBOW_U+1, 500), new Item(YEW_LONGBOW_U+1, 1000)),

    /* Hunter */

    /* Mining */
    MINER("Miner", "Mine 1000 ores of any kind.", 1000, Difficulty.MED, new Item(PROSPECTOR_HELMET), new Item(PROSPECTOR_JACKET), new Item(PROSPECTOR_LEGS), new Item(PROSPECTOR_BOOTS), new Item(COAL+1, 1000), new Item(MITHRIL_ORE+1, 500)),

    /* Smithing */
    GOLDSMITH("Goldsmith", "Make 1000 bars of any kind.", 1000, Difficulty.MED, new Item(RUNITE_BAR+1, 100), new Item(ADAMANTITE_BAR+1, 250), new Item(MITHRIL_BAR+1, 300)),

    /* Fishing */
    FISHERMAN("Fisherman", "Catch 1000 fish of any kind.", 1000, Difficulty.MED, new Item(ANGLER_HAT), new Item(ANGLER_BOOTS), new Item(ANGLER_TOP), new Item(ANGLER_WADERS), new Item(COINS_995, 500_000)),

    /* Cooking */
    BAKER("Baker", "Bake 10 breads or cakes.", 10, Difficulty.EASY, new Item(RAW_TROUT+1, 100)),
    COOK("Cook", "Cook 500 fish, meat or potato's.", 500, Difficulty.MED, new Item(COINS_995, 1_000_000), new Item(RAW_SHARK+1, 250), new Item(RAW_MONKFISH+1, 300)),
    CHEF("Chef", "Complete all cooking achievements to become a chef!", 1, Difficulty.HARD, new Item(COINS_995, 10_000_000), new Item(GOLDEN_CHEFS_HAT), new Item(GOLDEN_APRON)),

    /*Firemaking */
    PYROMANCER("Pyromancer", "Burn 1000 logs.", 1000, Difficulty.EASY, new Item(PYROMANCER_BOOTS), new Item(PYROMANCER_HOOD), new Item(PYROMANCER_GARB), new Item(PYROMANCER_ROBE), new Item(WARM_GLOVES), new Item(BRUMA_TORCH), new Item(MAGIC_LOGS+1, 100), new Item(YEW_LOGS+1, 100)),

    /* Woodcutting */
    LUMBERJACK("Lumberjack", "Cut 1000 logs.", 1000, Difficulty.EASY, new Item(LUMBERJACK_BOOTS), new Item(LUMBERJACK_HAT), new Item(LUMBERJACK_TOP), new Item(LUMBERJACK_LEGS), new Item(MAGIC_LOGS+1, 100), new Item(YEW_LOGS+1, 100)),

    /* Farming */
    FARMER("Farmer", "Harvest 100 seeds.", 100, Difficulty.HARD, new Item(FARMERS_STRAWHAT), new Item(FARMERS_BOOTS), new Item(FARMERS_JACKET), new Item(FARMERS_BORO_TROUSERS), new Item(SNAPDRAGON_SEED,100)),

    /* PvP */
    PVP_I("PVP I", "Kill 100 players in the wilderness.",100, Difficulty.EASY, new Item(DONATOR_MYSTERY_BOX)),
    PVP_II("PVP II", "Kill 500 players in the wilderness.",500, Difficulty.MED, new Item(MYSTERY_TICKET, 1)),
    PVP_III("PVP III", "Kill 1.000 players in the wilderness.",1000, Difficulty.HARD, new Item(MYSTERY_CHEST)),

    BOUNTY_HUNTER_I("Bounty hunter I", "Kill 10 targets.", 10, Difficulty.EASY, new Item(DONATOR_MYSTERY_BOX)),
    BOUNTY_HUNTER_II("Bounty hunter II", "Kill 50 targets.", 50, Difficulty.MED, new Item(VESTAS_LONGSWORD)),
    BOUNTY_HUNTER_III("Bounty hunter III", "Kill 150 targets.", 150, Difficulty.HARD, new Item(MYSTERY_TICKET,2)),

    DEEP_WILD_I("Deep wild I", "Kill 25 players in level 30+ wilderness.", 25, Difficulty.EASY, new Item(LARRANS_KEY, 5)),
    DEEP_WILD_II("Deep wild II", "Kill 50 players in level 30+ wilderness.", 50, Difficulty.MED, new Item(DONATOR_MYSTERY_BOX, 2)),
    DEEP_WILD_III("Deep wild III", "Kill 100 players in level 30+ wilderness.", 100, Difficulty.HARD, new Item(MYSTERY_TICKET, 1)),

    EXTREME_DEEP_WILD_I("Extreme deep wild I", "Kill 25 players in level 50+ wilderness.", 25, Difficulty.EASY, new Item(LARRANS_KEY, 5)),
    EXTREME_DEEP_WILD_II("Extreme deep wild II", "Kill 50 players in level 50+ wilderness.", 50, Difficulty.MED, new Item(DONATOR_MYSTERY_BOX, 2)),
    EXTREME_DEEP_WILD_III("Extreme deep wild III", "Kill 100 players in level 50+ wilderness.", 100, Difficulty.HARD, new Item(MYSTERY_TICKET, 1)),

    BLOODTHIRSTY_I("Bloodthirsty I", "Get a killstreak of 25.", 1, Difficulty.MED, new Item(DONATOR_MYSTERY_BOX)),
    BLOODTHIRSTY_II("Bloodthirsty II", "Get a killstreak of 50.", 1, Difficulty.HARD, new Item(DRAGON_CLAWS)),
    BLOODTHIRSTY_III("Bloodthirsty III", "Kill someone that is on a killstreak of +50.", 1, Difficulty.HARD, new Item(DONATOR_MYSTERY_BOX)),

    SURVIVOR_I("Survivor I", "Get a wilderness killstreak of 5.", 5, Difficulty.MED, new Item(LARRANS_KEY, 5)),
    SURVIVOR_II("Survivor II", "Get a wilderness killstreak of above 10.", 10, Difficulty.HARD, new Item(DONATOR_MYSTERY_BOX)),

    PURE_I("Pure I", "Get 25 player kills with a defence level of 1.<br>You must have a CB level of at least 80!", 25, Difficulty.EASY, new Item(ELDER_CHAOS_HOOD), new Item(ELDER_CHAOS_TOP), new Item(ELDER_CHAOS_ROBE)),
    PURE_II("Pure II", "Get 50 player kills with a defence level of 1.<br>You must have a CB level of at least 80!", 50, Difficulty.MED, new Item(DONATOR_MYSTERY_BOX)),
    PURE_III("Pure III", "Get 75 player kills with a defence level of 1.<br>You must have a CB level of at least 80!", 75, Difficulty.HARD, new Item(MYSTERY_TICKET)),
    PURE_IV("Pure IV", "Get 100 player kills with a defence level of 1.<br>You must have a CB level of at least 80!", 100, Difficulty.HARD, new Item(ZURIELS_STAFF)),

    ZERKER_I("Zerker I", "Get 25 player kills with a defence level of 45.<br>You mast have a CB level of at least 95!", 25, Difficulty.EASY, new Item(FIGHTER_HAT), new Item(FIGHTER_TORSO)),
    ZERKER_II("Zerker II", "Get 50 player kills with a defence level of 45.<br>You mast have a CB level of at least 95!", 50, Difficulty.MED, new Item(DONATOR_MYSTERY_BOX)),
    ZERKER_III("Zerker III", "Get 75 player kills with a defence level of 45.<br>You mast have a CB level of at least 95!", 75, Difficulty.HARD, new Item(MYSTERY_TICKET)),
    ZERKER_IV("Zerker IV", "Get 100 player kills with a defence level of 45.<br>You mast have a CB level of at least 95!", 100, Difficulty.HARD, new Item(STATIUSS_WARHAMMER)),

    DHAROK_BOMBER_I("Dharok bomber I","Kill 15 players wearing full dharok.<br>Your hitpoints must be below 25.",15, Difficulty.EASY, new Item(DONATOR_MYSTERY_BOX), new Item(DWARVEN_ROCK_CAKE)),
    DHAROK_BOMBER_II("Dharok bomber II","Kill 25 players wearing full dharok.<br>Your hitpoints must be below 15.",25, Difficulty.MED, new Item(MYSTERY_TICKET)),
    DHAROK_BOMBER_III("Dharok bomber III","Kill 15 players wearing full dharok.<br>Your hitpoints must be below 5.",15, Difficulty.HARD, new Item(DHAROK_PET)),

    ;

    public static List<Achievements> asList(Difficulty difficulty) {
        return Arrays.stream(values()).filter(a -> a.difficulty == difficulty).sorted(Comparator.comparing(Enum::name)).collect(Collectors.toList());
    }

    private final String name;
    private final String description;
    private final int completeAmount;
    private final String rewardString;
    private final Difficulty difficulty;
    private final Item[] reward;

    Achievements(String name, String description, int completeAmount, Difficulty difficulty, Item... reward) {
        this.name = name;
        this.description = description;
        this.completeAmount = completeAmount;
        this.rewardString = "";
        this.difficulty = difficulty;
        this.reward = reward;
    }

    Achievements(String name, String description, int completeAmount, String rewardString, Difficulty difficulty, Item... reward) {
        this.name = name;
        this.description = description;
        this.completeAmount = completeAmount;
        this.rewardString = rewardString;
        this.difficulty = difficulty;
        this.reward = reward;
    }

    public int getCompleteAmount() {
        return completeAmount;
    }

    public String getDescription() {
        return description;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Item[] getReward() {
        return reward;
    }

    public String getName() {
        return name;
    }

    public String otherRewardString() {
        return rewardString;
    }

    public int points() {
        return difficulty == Difficulty.EASY ? 2 : difficulty == Difficulty.MED ? 4 : 6;
    }

    public static int getTotal() {
        return values().length;
    }
}

package com.valinor.game;

import com.valinor.GameServer;
import com.valinor.game.world.InterfaceConstants;
import com.valinor.game.world.items.Item;
import com.valinor.util.ItemIdentifiers;

import static com.valinor.util.CustomItemIdentifiers.BEGINNER_WEAPON_PACK;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * A class containing different attributes
 * which affect the game in different ways.
 *
 * @author Professor Oak
 */
public class GameConstants {

    public static final String VOTE_URL = "https://valinorpk.com/voting/";
    public static final String STORE_URL = "https://valinorpk.com/store/";
    public static final String DONATOR_FEATURES_URL = "https://valinorpk.com/features/";
    public static final String WEBSITE_URL = "https://valinorpk.com/";
    public static final String RULES_URL = "";
    public static final String DISCORD_URL = "https://discord.gg/hESDzJVkz4";

    /**
     * Starter items for game mode.
     */
    public static final Item[] STARTER_ITEMS = {
        //Iron armour
        new Item(IRON_FULL_HELM), new Item(IRON_PLATEBODY), new Item(IRON_PLATELEGS), new Item(IRON_KITESHIELD),
        //Iron scimitar and rune scimitar
        new Item(IRON_SCIMITAR), new Item(RUNE_SCIMITAR),
        //Regular bow and bronze arrows
        new Item(SHORTBOW), new Item(BRONZE_ARROW, 1000),
        //Power ammy and climbing boots
        new Item(AMULET_OF_POWER), new Item(CLIMBING_BOOTS),
        //Standard runes
        new Item(FIRE_RUNE, 1000), new Item(WATER_RUNE, 1000), new Item(AIR_RUNE, 1000), new Item(EARTH_RUNE, 1000), new Item(MIND_RUNE, 1000),
        //Food (Lobsters, noted)
        new Item(LOBSTER + 1, 50),
        //Beginner weapon
        new Item(BEGINNER_WEAPON_PACK)
    };

    public final static int[] TAB_AMOUNT = {48, 60, 0, 0, 0, 0, 0, 0, 0, 0};
    public final static Item[] BANK_ITEMS = {
        new Item(4587, 20000), // Scim
        new Item(1215, 20000), // Dagger
        new Item(4089, 20000), // Mystic
        new Item(4109, 20000), // Mystic
        new Item(4099, 20000), // Mystic
        new Item(7400, 20000), // Enchanted
        new Item(3755, 20000), // farseer helm
        new Item(1163, 20000), // rune full helm
        new Item(1305, 20000), // d long
        new Item(4675, 20000), // ancient staff
        new Item(4091, 20000), // Mystic
        new Item(4111, 20000), // Mystic
        new Item(4101, 20000), // Mystic
        new Item(7399, 20000), // enchanted
        new Item(3751, 20000), // hat
        new Item(1127, 20000), // rune
        new Item(1434, 20000), // mace
        new Item(9185, 20000), // crossbow
        new Item(4093, 20000), // Mystic
        new Item(4113, 20000), // Mystic
        new Item(4103, 20000), // Mystic
        new Item(7398, 20000), // enchanted
        new Item(3753, 20000), // helm
        new Item(1079, 20000), // rune
        new Item(5698, 20000), // dagger
        new Item(10499, 20000), // avas
        new Item(4097, 20000), // Mystic
        new Item(4117, 20000), // Mystic
        new Item(4107, 20000), // Mystic
        new Item(2579, 20000), // wiz boots
        new Item(3749, 20000), // helm
        new Item(4131, 20000), // rune boots
        new Item(2503, 20000), // hides
        new Item(2497, 20000), // hides
        new Item(12492, 20000), // hides
        new Item(12508, 20000), // hides
        new Item(12500, 20000), // hides
        new Item(3105, 20000), // climbers
        new Item(1093, 20000), // rune
        new Item(1201, 20000), // rune
        new Item(3842, 20000), // god book
        new Item(12612, 20000), // god book
        new Item(12494, 20000), // hides
        new Item(12510, 20000), // hides
        new Item(12502, 20000), // hides
        new Item(6108, 20000), // ghostly
        new Item(6107, 20000), // ghostly
        new Item(6109, 20000), // ghostly

        // END OF TAB 1
        new Item(2436, 20000), // pots
        new Item(2440, 20000), // pots
        new Item(2442, 20000), // pots
        new Item(2444, 20000), // pots
        new Item(3040, 20000), // pots
        new Item(10925, 20000), // pots
        new Item(3024, 20000), // pots
        new Item(6685, 20000), // pots
        new Item(145, 20000), // pots
        new Item(157, 20000), // pots
        new Item(163, 20000), // pots
        new Item(169, 20000), // pots
        new Item(3042, 20000), // pots
        new Item(10927, 20000), // pots
        new Item(3026, 20000), // pots
        new Item(6689, 20000), // pots
        new Item(147, 20000), // pots
        new Item(159, 20000), // pots
        new Item(165, 20000), // pots
        new Item(171, 20000), // pots
        new Item(3044, 20000), // pots
        new Item(10929, 20000), // pots
        new Item(3028, 20000), // pots
        new Item(6687, 20000), // pots
        new Item(149, 20000), // pots
        new Item(161, 20000), // pots
        new Item(167, 20000), // pots
        new Item(173, 20000), // pots
        new Item(3046, 20000), // pots
        new Item(10931, 20000), // pots
        new Item(3030, 20000), // pots
        new Item(6691, 20000), // pots
        new Item(385, 20000), // sharks
        new Item(3144, 20000), // karambwan
        new Item(560, 20000000), // runes
        new Item(565, 20000000), // runes
        new Item(555, 20000000), // runes
        new Item(562, 20000000), // runes
        new Item(557, 20000000), // runes
        new Item(559, 20000000), // runes
        new Item(564, 20000000), // runes
        new Item(554, 20000000), // runes
        new Item(9075, 20000000), // runes
        new Item(556, 20000000), // runes
        new Item(563, 20000000), // runes
        new Item(559, 20000000), // runes
        new Item(566, 20000000), // runes
        new Item(561, 20000000), // runes
        new Item(9241, 20000), // bolts
        new Item(9244, 20000), // bolts
        new Item(9245, 20000), // bolts
        new Item(9243, 20000), // bolts
        new Item(9242, 20000), // bolts
        new Item(892, 20000), // rune arrows
        new Item(10828, 20000), // neit helm
        new Item(2412, 20000), // sara god cape
        new Item(8013, 20000), // home teleport tab
        new Item(7458, 20000), // mithril gloves for pures
        new Item(7462, 20000), // gloves
        new Item(11978, 20000), // glory (6)
    };

    public static final String SERVER_NAME = "ValinorPK";

    /**
     * Holds the array of all the side-bar identification and their
     * corresponding itemcontainer identification.
     */
    public static final int[][] SIDEBAR_INTERFACE =
        {
            {GameConstants.ATTACK_TAB, 2423}, {GameConstants.SKILL_TAB, 10000}, {GameConstants.QUEST_TAB, 53400}, {GameConstants.INVENTORY_TAB, 3213}, {GameConstants.EQUIPMENT_TAB, 1644}, {GameConstants.PRAYER_TAB, 5608}, {GameConstants.MAGIC_TAB, 938}, //Row 1

            {GameConstants.CLAN_TAB, InterfaceConstants.CLAN_CHAT}, {GameConstants.FRIENDS_TAB, 5065}, {GameConstants.IGNORE_TAB, 5715}, {GameConstants.LOGOUT_TAB, 2449}, {GameConstants.WRENCH_TAB, 42500}, {GameConstants.EMOTE_TAB, 147}, {GameConstants.MUSIC_TAB, 72150} //Row 2
        };

    /**
     * All the tab identifications
     */
    public static final int ATTACK_TAB = 0, SKILL_TAB = 1, QUEST_TAB = 2, INVENTORY_TAB = 3, EQUIPMENT_TAB = 4,
        PRAYER_TAB = 5, MAGIC_TAB = 6, CLAN_TAB = 7, FRIENDS_TAB = 8, IGNORE_TAB = 9, LOGOUT_TAB = 10,
        WRENCH_TAB = 11, EMOTE_TAB = 12, MUSIC_TAB = 13;

    //Capped at 1000, we wont ever reach more then 1000 players as RSPS anyways
    public static final int PLAYERS_LIMIT = 1000; //This must be capped to 2047 because 11 bits - 1
    public static final int NPCS_LIMIT = 16383; //This must be capped to 16384 because 14 bits - 1

    /**
     * Strings that are classified as bad
     */
    public static final String[] BAD_STRINGS = {};
}

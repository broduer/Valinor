package com.valinor.game;

import com.valinor.GameServer;
import com.valinor.game.world.InterfaceConstants;
import com.valinor.game.world.items.Item;
import com.valinor.util.ItemIdentifiers;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * A class containing different attributes
 * which affect the game in different ways.
 *
 * @author Professor Oak
 */
public class GameConstants {

    public static final String VOTE_URL = "https://valinor-os.com/voting/";
    public static final String STORE_URL = "https://valinor-os.com/store/";
    public static final String DONATOR_FEATURES_URL = "https://valinor-os.com/features/";
    public static final String WEBSITE_URL = "https://valinor-os.com/";
    public static final String RULES_URL = "";

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
        //Glory (6) and climbing boots
        new Item(AMULET_OF_GLORY6), new Item(CLIMBING_BOOTS),
        //Standard runes
        new Item(FIRE_RUNE, 1000), new Item(WATER_RUNE, 1000), new Item(AIR_RUNE, 1000), new Item(EARTH_RUNE, 1000), new Item(MIND_RUNE, 1000),
        //Food (Lobsters, noted)
        new Item(LOBSTER+1, 50)
    };

    public static final String SERVER_NAME = "Valinor";

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
    public static final String[] BAD_STRINGS = { };
}

package com.valinor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.valinor.game.world.position.Tile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lare96 <http://github.com/lare96>
 */
public final class ServerProperties {

    public static final Gson SERIALIZE = new GsonBuilder().setPrettyPrinting().create();

    private static final Logger logger = LogManager.getLogger(ServerProperties.class);

    static final ServerProperties current;
    public static final LocalProperties localProperties;

    static {
        Path filePathLocal = Paths.get("./data/local-properties.json");
        try {
            if (filePathLocal.toFile().exists()) {
                byte[] readBytes = Files.readAllBytes(filePathLocal);
                LocalProperties loaded = SERIALIZE.fromJson(new String(readBytes), LocalProperties.class);
                if (loaded == null) {
                    logger.info("Server properties file (./data/LocalProperties.json) empty, using default settings.");
                    localProperties = new LocalProperties();
                } else {
                    localProperties = loaded;
                    logger.info("Loaded server properties file (./data/LocalProperties.json) successfully. "+ localProperties);
                }
            } else {
                localProperties = new LocalProperties();
                logger.info("Server properties file (./data/LocalProperties.json) was not found, loaded with default settings.");
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
        try {
            Path filePath = Paths.get("./data/properties.json");
            if (filePath.toFile().exists()) {
                byte[] readBytes = Files.readAllBytes(filePath);
                ServerProperties loaded = SERIALIZE.fromJson(new String(readBytes), ServerProperties.class);
                if (loaded == null) {
                    current = new ServerProperties(localProperties.sqlOn, localProperties.discordLoggingOn);
                    logger.info("Server properties file (./data/properties.json) empty, using default settings.");
                } else {
                    current = loaded;
                    // overwrite with local
                    if (filePathLocal.toFile().exists()) {
                        current.enableDiscordLogging = localProperties.discordLoggingOn;
                        current.enableSql = localProperties.sqlOn;
                    }
                    logger.info("Loaded server properties file (./data/properties.json) successfully.");
                }
            } else {
                current = new ServerProperties(localProperties.sqlOn, localProperties.discordLoggingOn);
                logger.info("Server properties file (./data/properties.json) was not found, loaded with default settings.");
            }
            //Since the static initializer is called after the constructor, we can set the game port here if we don't want to override the game port.
            if (!current.overrideGamePort) {
                current.gamePort = 43594;
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static class LocalProperties {
        public final String db1;
        public final boolean sqlOn, discordLoggingOn;

        public LocalProperties() {
            db1 = "default";
            discordLoggingOn = true;
            sqlOn = true;
        }

        @Override
        public String toString() {
            return "LocalProperties{" +
                "db1='" + db1 + '\'' +
                '}';
        }
    }

    private ServerProperties() {
        this(false, false);
    }

    private ServerProperties(boolean sqlOn, boolean discordLoggingOn) {
        // Default property values. If the server properties file exists, these will be ignored.
        lowercaseCaptcha = true;
        disableCaptchaEveryLogin = true;
        staffOnlyLogins = false;
        gameVersion = "2";
        definitionsDirectory = "./data/def/";
        dumpDefinitionsDirectory = "./data/dump/";
        clippingDirectory = "./data/clipping/";
        debugMode = true;
        autoRefreshQuestTab = true;
        enableLeakDetection = false;
        concurrency = (Runtime.getRuntime().availableProcessors() > 1);
        queuedLoopThreshold = 45;
        packetProcessLimit = 25;
        defaultTile = new Tile(3094, 3503, 0);
        duelTile = new Tile(3369, 3266);
        defaultClanChat = "";
        autosaveMinutes = 15;
        afkLogoutMinutes = 15;
        afkLogoutMinutesNewAccounts = 4;
        baseBMValue = 500;
        requireBankPinOnLogin = false;
        defaultBankPinRecoveryDays = 3;
        displayCycleTime = true;
        useInformationCycle = true;
        informationCycleCount = 10;
        gamePort = 43594;
        overrideGamePort = false;
        enableTraceLogging = false;
        production = false;
        test = false;
        displayCycleLag = false;
        refreshQuestTabCycles = 100;
        hitPredictorEnabled = true;
        tournamentsEnabled = true;
        wildernessActivityEnabled = true;
        enableSql = sqlOn;
        linuxOnlyDisplayCycleLag = false;
        ignoreGameLagDetectionMilliseconds = 10000;
        clearQuestInterfaceStrings = false;
        warnSlowPackets = false;
        tournamentMaxParticipants = 150;
        tournamentMinProdParticipants = 6;
        tournamentMinDevParticipants = 2;
        logSuccessfulPackets = false;
        logUnderflowPacketsProduction = false;
        connectionLimit = 4;
        maxAlts = 2;
        pidIntervalTicks = 100;
        enablePidShuffling = true;
        skullTime = 720;
        enableItemSpritesOnNpcDropInterface = true;
        linuxOnlyDisplayCycleTime = false;
        linuxOnlyDisplayTaskLag = false;
        BCryptPasswordRounds = 12;
        BCryptPinRounds = 10;
        logAccuracyChances = false;
        soundsEnabled = false;
        enableDueling = true;
        enableGambling = true;
        maintenanceMode = false;
        enableLoadLastDuelPreset = true;
        venomVsPlayersOn = false;
        venomFromAdminsOn = false;
        fileStore = "";
        definitionsLazy = true;
        discordNotifyId = "";
        enableDiscordLogging = discordLoggingOn;
        publicChatWebHookUrl = "";
        privateChatWebHookUrl = "";
        staffCommandsWebHookUrl = "";
        playerCommandsWebHookUrl = "";
        votesClaimedWebHookUrl = "";
        donationsClaimedWebHookUrl = "";
        tradeFirstScreenDeclinedWebHookUrl = "";
        tradeSecondScreenDeclinedWebHookUrl = "";
        tradeFirstScreenAcceptedWebHookUrl = "";
        tradeSecondScreenAcceptedWebHookUrl = "";
        stakeFirstScreenDeclinedWebHookUrl = "";
        stakeSecondScreenDeclinedWebHookUrl = "";
        stakeFirstScreenAcceptedWebHookUrl = "";
        stakeSecondScreenAcceptedWebHookUrl = "";
        stakeItemsWonWebHookUrl = "";
        stakeItemsLostWebHookUrl = "";
        stakeRulesChangedWebHookUrl = "";
        gambleItemsOfferedWebHookUrl = "";
        gambleItemsWonWebHookUrl = "";
        gambleItemsLostWebHookUrl = "";
        yellItemDropsWebHookUrl = "";
        slayerKeyDropsWebHookUrl = "";
        brimstoneKeyDropsWebHookUrl = "";
        crystalKeyDropsWebHookUrl = "";
        slayerChestWebHookUrl = "";
        brimstoneChestWebHookUrl = "";
        crystalChestWebHookUrl = "";
        collectionChestWebHookUrl = "";
        rewardsClaimedColLogWebHookUrl = "";
        itemsDroppedWebHookUrl = "";
        playerPickupsWebHookUrl = "";
        loginsWebHookUrl = "";
        logoutsWebHookUrl = "";
        logoutDuringTournyWebHookUrl = "";
        itemsBoughtWebHookUrl = "";
        itemsSoldWebHookUrl = "";
        npcDeathWebHookUrl = "";
        playerDeathWebHookUrl = "";
        playersEnteredTournyWebHookUrl = "";
        playersLeftTournyWebHookUrl = "";
        tournyWinnersWebHookUrl = "";
        referralsWebHookUrl = "";
        referralRewardsClaimedWebHookUrl = "";
        itemSalesTPWebHookUrl = "";
        itemsBoughtTPWebHookUrl = "";
        itemsModifiedTPWebHookUrl = "";
        itemsClaimedTPWebHookUrl = "";
        itemsCanceledTPWebHookUrl = "";
        coxRewardsClaimedWebHookUrl = "";
        tobRewardsClaimedWebHookUrl = "";
        cosRewardsClaimedWebHookUrl = "";
        boxesOpenedWebHookUrl = "";
        ticketsOpenedWebHookUrl = "";
        fpkMerkPromoCodeClaimedWebHookUrl = "";
        itemsAddedToCartDispenserWebHookUrl = "";
        itemsDispensedWebHookUrl = "";
        presetsLoadedWebHookUrl = "";
        nifflerLootedWebHookUrl = "";
        lootingBagLootedWebHookUrl = "";
        lootingBagWithdrawnWebHookUrl = "";
        redirectOutStream = false;
        teleToMeInWildOk = false;
        teleToWildyPlayedDisabled = false;
        newAccsPKPTime = 3000;
        pkTelesAfterSetupSet = 50;
        stakingStaffOnly = false;
        edgeDitch10secondPjTimerEnabled = true;
        enableChangeAccountType = true;
        buyTwoGetOneFree = false;
        doubleExperienceEvent = false;
        doubleSlayerRewardPointsEvent = false;
        doublePKPEvent = false;
        doubleVotePointsEvent = false;
        halloween = false;
        christmas = false;
    }

    public final String fileStore;
    public final boolean definitionsLazy;

    /**
     * The flag that determines if the server is sending lower case captcha.
     */
    public boolean lowercaseCaptcha;

    /**
     * The flag that determines if the server is sending a captcha every login.
     */
    public boolean disableCaptchaEveryLogin;

    /**
     * The flag that determines if the server is accepting non-staff logins.
     */
    public boolean staffOnlyLogins;

    /**
     * The current game/client version.
     */
    public final String gameVersion;

    /**
     * The directory of the definition files.
     */
    public final String definitionsDirectory;

    public final String dumpDefinitionsDirectory;

    /**
     * The directory of the clipping files.
     */
    public final String clippingDirectory;

    /**
     * A flag used for determining if admins will see debug statements or not.
     */
    public final boolean debugMode;

    /**
     * A flag used for determining if the quest tab will automatically refresh or not.
     */
    public final boolean autoRefreshQuestTab;

    /**
     * A flag used for how often the quest tab will refresh. Use 1 for every cycle, use 100 for every 100 cycles (every minute)
     */
    public final int refreshQuestTabCycles;

    /**
     * Resource leak detector set to PARANOID (ENABLE_LEAK_DETECTION TRUE) may cause lag (especially if hosting) but checks for any resource leaks. Probably should set ENABLE_LEAK_DETECTION to false if hosting.
     */
    public final boolean enableLeakDetection;

    /**
     * The flag that determines if processing should be parallelized, improving
     * the performance of the server times {@code n} (where
     * {@code n = Runtime.getRuntime().availableProcessors()}) at the cost of
     * substantially more CPU usage.
     */
    public final boolean concurrency;

    /**
     * The maximum amount of iterations for a queue/list that should occur each cycle.
     */
    public final int queuedLoopThreshold;

    /**
     * The maximum amount of messages that can be processed in one sequence.
     * This limit may need to be increased or decreased in the future.
     */
    public final int packetProcessLimit;

    /**
     * The default position, where players will
     * spawn upon logging in for the first time.
     */
    public final Tile defaultTile;

    /**
     * The default position, where players will
     * spawn upon logging in for the first time.
     */
    public final Tile duelTile;

    /**
     * The default clan chat a player will join upon logging in,
     * if they aren't in one already.
     */
    public final String defaultClanChat;

    public final int autosaveMinutes;

    // Idle packet called every 3 minutes, so the value should be a multiple of 3 for the most accurate timing.
    public final int afkLogoutMinutes;

    // Idle packet called every 3 minutes, so the value should be a multiple of 3 for the most accurate timing.
    public final int afkLogoutMinutesNewAccounts;

    //Should we display the Game Engine cycle time in the server log?
    public final boolean displayCycleTime;

    /**
     * See ShopManager for details, 10 is probably a good ratio, 20 or higher might be a better ratio.
     */
    public final int baseBMValue;

    /**
     * Do players need to enter their bank PIN (if set) upon login? If so, set to true.
     */
    public final boolean requireBankPinOnLogin;

    /**
     * The default recovery delay for bank PINs.
     */
    public final int defaultBankPinRecoveryDays;
    /**
     * If this is true, we will only print the cycle information once per informationCycleCount.
     */
    public final boolean useInformationCycle;

    /**
     * The count for which tick we will print cycle information if we use the information cycle.
     */
    public final int informationCycleCount;

    /**
     * The game port that the server listens on and the client connects to.
     */
    public int gamePort;

    /**
     * If we want to override the PVP and ECO game ports, set this to true.
     */
    public final boolean overrideGamePort;

    /**
     * If we want to enable trace logging (the most verbose type of logging) in log4j2, set this to true.
     */
    public final boolean enableTraceLogging;

    /**
     * If we are running in production, set this to true.
     */
    public final boolean production;

    /**
     * If we are running in test, set this to true.
     */
    public final boolean test;

    /**
     * If we want to see warnings and errors about game engine cycle time lag, set this to true.
     * This should be false on slow dev PCs and always true on production.
     */
    public final boolean displayCycleLag;

    /**
     * If we want to enable the hit predictor for all players, set this to true. Make sure to match the client constant for this to not confuse players.
     */
    public final boolean hitPredictorEnabled;

    /**
     * If we want to disable PvP tournaments, set this to false.
     */
    public final boolean tournamentsEnabled;

    /**
     * If we want to disable wilderness activity, set this to false.
     */
    public final boolean wildernessActivityEnabled;

    /**
     * If we want to disable SQL, set this to false.
     */
    public boolean enableSql;

    /**
     * If we want to only display cycle lag messages on Linux, set this to true.
     */
    public final boolean linuxOnlyDisplayCycleLag;

    /**
     * This is the value for how many milliseconds we want to wait for calculating game lag.
     * A reasonable value would be 10000ms which is 10 seconds from when the server is started.
     * Set this to 0 to disable this functionality and instead calculate game lag no matter what.
     */
    public final int ignoreGameLagDetectionMilliseconds;

    /**
     * If we want to clear quest interface strings like for commands, set this to true.
     */
    public final boolean clearQuestInterfaceStrings;

    /**
     * If we want to warn for slow packets (packets > 0ms), set this to true.
     */
    public final boolean warnSlowPackets;

    /**
     * This is the number the max number of participants for the PvP tournaments system.
     */
    public final int tournamentMaxParticipants;

    /**
     * This is the number the min number of participants for the PvP tournaments system in production mode.
     */
    public final int tournamentMinProdParticipants;

    /**
     * This is the number the min number of participants for the PvP tournaments system in development mode.
     */
    public final int tournamentMinDevParticipants;

    /**
     * If we want to log successful packets, set this to true. We don't really need this.
     */
    public final boolean logSuccessfulPackets;

    /**
     * If we want to log packet size mismatch on production, set this to true.
     * We probably don't need this set to true, since packet fragmentation should be expected in production.
     */
    public final boolean logUnderflowPacketsProduction;

    /**
     * a map of ip:count of connections open in netty. not 1:1 with attempting to login because the client
     * might send a js5 or cache request or something else
     */
    public final int connectionLimit;

    /**
     * The amount of connections that are allowed from the same host.
     * This limit may need to be increased or decreased in the future.
     */
    public final int maxAlts;

    /**
     * The number of ticks before PID is shuffled.
     */
    public final int pidIntervalTicks;

    /**
     * If we want to enable PID shuffling, set this to true.
     */
    public final boolean enablePidShuffling;

    /**
     * The default skull timer
     */
    public final int skullTime;

    /**
     * If we want to draw item sprites on the NPC Drop interface, set this to true.
     */
    public final boolean enableItemSpritesOnNpcDropInterface;

    /**
     * If we want to only display cycle time (logging) on Linux, set this to true.
     */
    public final boolean linuxOnlyDisplayCycleTime;

    /**
     * If we want to only display task lag (logging) on Linux, set this to true.
     */
    public final boolean linuxOnlyDisplayTaskLag;

    /**
     * This is the default BCrypt rounds we use for passwords. 12 is probably a good default.
     */
    public final int BCryptPasswordRounds;

    /**
     * This is the default BCrypt rounds we use for bank PINs. 10 is probably a good default.
     */
    public final int BCryptPinRounds;

    /**
     * If we want to enable logging of accuracy chances, set this to true.
     */
    public final boolean logAccuracyChances;

    /**
     * If we want to enable sounds, set this to true.
     */
    public final boolean soundsEnabled;

    /**
     * If we want to enable dueling, set this to true.
     */
    public final boolean enableDueling;

    /**
     * If we want to enable gambling, set this to true.
     */
    public final boolean enableGambling;

    /**
     * If we want to enable maintenance mode at startup, set this to true.
     */
    public final boolean maintenanceMode;

    /**
     * If we want to enable did you know messages, set this to true.
     */
    public final boolean enableLoadLastDuelPreset;

    // Disabled until fully coded
    public final boolean venomVsPlayersOn;

    // During testing phase
    public final boolean venomFromAdminsOn;

    /**
     * This is the Discord ID to notify for any errors.
     */
    public final String discordNotifyId;

    /**
     * If we want logging to our Discord server, set this to true.
     */
    public boolean enableDiscordLogging;

    public final String publicChatWebHookUrl;
    public final String privateChatWebHookUrl;
    public final String staffCommandsWebHookUrl;
    public final String playerCommandsWebHookUrl;
    public final String votesClaimedWebHookUrl;
    public final String donationsClaimedWebHookUrl;
    public final String tradeFirstScreenDeclinedWebHookUrl;
    public final String tradeSecondScreenDeclinedWebHookUrl;
    public final String tradeFirstScreenAcceptedWebHookUrl;
    public final String tradeSecondScreenAcceptedWebHookUrl;
    public final String stakeFirstScreenDeclinedWebHookUrl;
    public final String stakeSecondScreenDeclinedWebHookUrl;
    public final String stakeFirstScreenAcceptedWebHookUrl;
    public final String stakeSecondScreenAcceptedWebHookUrl;
    public final String stakeItemsWonWebHookUrl;
    public final String stakeItemsLostWebHookUrl;
    public final String stakeRulesChangedWebHookUrl;
    public final String gambleItemsOfferedWebHookUrl;
    public final String gambleItemsWonWebHookUrl;
    public final String gambleItemsLostWebHookUrl;
    public final String yellItemDropsWebHookUrl;
    public final String slayerKeyDropsWebHookUrl;
    public final String brimstoneKeyDropsWebHookUrl;
    public final String crystalKeyDropsWebHookUrl;
    public final String slayerChestWebHookUrl;
    public final String brimstoneChestWebHookUrl;
    public final String crystalChestWebHookUrl;
    public final String collectionChestWebHookUrl;
    public final String rewardsClaimedColLogWebHookUrl;
    public final String itemsDroppedWebHookUrl;
    public final String playerPickupsWebHookUrl;
    public final String loginsWebHookUrl;
    public final String logoutsWebHookUrl;
    public final String logoutDuringTournyWebHookUrl;
    public final String itemsBoughtWebHookUrl;
    public final String itemsSoldWebHookUrl;
    public final String npcDeathWebHookUrl;
    public final String playerDeathWebHookUrl;
    public final String playersEnteredTournyWebHookUrl;
    public final String playersLeftTournyWebHookUrl;
    public final String tournyWinnersWebHookUrl;
    public final String referralsWebHookUrl;
    public final String referralRewardsClaimedWebHookUrl;
    public final String itemSalesTPWebHookUrl;
    public final String itemsBoughtTPWebHookUrl;
    public final String itemsModifiedTPWebHookUrl;
    public final String itemsClaimedTPWebHookUrl;
    public final String itemsCanceledTPWebHookUrl;
    public final String coxRewardsClaimedWebHookUrl;
    public final String tobRewardsClaimedWebHookUrl;
    public final String cosRewardsClaimedWebHookUrl;
    public final String boxesOpenedWebHookUrl;
    public final String ticketsOpenedWebHookUrl;
    public final String fpkMerkPromoCodeClaimedWebHookUrl;
    public final String itemsAddedToCartDispenserWebHookUrl;
    public final String itemsDispensedWebHookUrl;
    public final String presetsLoadedWebHookUrl;
    public final String nifflerLootedWebHookUrl;
    public final String lootingBagLootedWebHookUrl;
    public final String lootingBagWithdrawnWebHookUrl;

    public final boolean redirectOutStream;

    // Stops non-admins doing 'teletome' if they're in the wilderness.
    public final boolean teleToMeInWildOk;

    // Every code below here is not by @god_coder and is shit code by @jak
    // Stops non-admins doing 'teleto' if the target is in the wilderness ft. hybrid abuse x2 in 1 hour
    public final boolean teleToWildyPlayedDisabled;

    // Game ticks before new accounts drop pkp (stops farming)
    public final int newAccsPKPTime;

    // How many seconds you have to wait before using teleports (tabs, spellbook, wizard)
    public final int pkTelesAfterSetupSet;

    // If duel arena can only be used when you're a mod.
    public final boolean stakingStaffOnly;

    // If the pj timer is 10 seconds instead of 4.5s at edgeville. Stops pjers.
    public final boolean edgeDitch10secondPjTimerEnabled;

    /**
     *  If we want to enable players changing between PK mode and trained mode, set this to true.
     */
    public final boolean enableChangeAccountType;

    // If this promo is active when purchasing 2 of the same items from the store you get one for free
    public final boolean buyTwoGetOneFree;

    public final boolean doubleExperienceEvent;
    public final boolean doubleSlayerRewardPointsEvent;
    public final boolean doublePKPEvent;
    public final boolean doubleVotePointsEvent;
    public final boolean halloween;
    public final boolean christmas;
}


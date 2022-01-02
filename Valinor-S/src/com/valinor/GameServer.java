package com.valinor;

import com.valinor.db.DatabaseService;
import com.valinor.db.DatabaseServiceBuilder;
import com.valinor.fs.DefinitionRepository;
import com.valinor.game.GameConstants;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.save.PlayerSave;
import com.valinor.test.generic.PlayerProfileVerf;
import com.valinor.util.DiscordWebhook;
import com.valinor.util.flood.Flooder;
import com.google.common.base.Preconditions;
import io.netty.util.ResourceLeakDetector;
import nl.bartpelle.dawnguard.DataStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

import static io.netty.util.ResourceLeakDetector.Level.DISABLED;
import static io.netty.util.ResourceLeakDetector.Level.PARANOID;

/**
 * The starting point of Valinor.
 * Starts the game server.
 *
 * @author Professor Oak
 * @author Lare96
 * @author i_pk_pjers_i
 * @author PVE
 */
public class GameServer {

    /**
     * The logger that will print important information.
     */
    private static final Logger logger;

    /**
     * The flag that determines if the server is currently being updated or not.
     */
    private static volatile boolean isUpdating;

    /**
     * The flag that determines if the server is accepting non-staff logins.
     */
    private static volatile boolean staffOnlyLogins = false;

    /**
     * The flooder used to stress-test the server.
     */
    private static final Flooder flooder = new Flooder();

    public static ServerProperties properties() {
        return ServerProperties.current;
    }

    private static DefinitionRepository definitions;

    public static DefinitionRepository definitions() {
        return definitions;
    }

    /**
     * Filestore instance
     */
    private static DataStore fileStore;

    public static DataStore store() {
        return fileStore;
    }

    static {
        Thread.currentThread().setName(""+GameConstants.SERVER_NAME+"InitializationThread");
        System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        logger = LogManager.getLogger(GameServer.class);
        if (properties().enableDiscordLogging) {
            logger.info("Discord logging has been enabled.");
            publicChatWebHookUrl = new DiscordWebhook(properties().publicChatWebHookUrl);
            privateChatWebHookUrl = new DiscordWebhook(properties().privateChatWebHookUrl);
            staffCommandsWebHookUrl = new DiscordWebhook(properties().staffCommandsWebHookUrl);
            playerCommandsWebHookUrl = new DiscordWebhook(properties().playerCommandsWebHookUrl);
            votesClaimedWebHookUrl = new DiscordWebhook(properties().votesClaimedWebHookUrl);
            donationsClaimedWebHookUrl = new DiscordWebhook(properties().donationsClaimedWebHookUrl);
            tradeFirstScreenDeclinedWebHookUrl = new DiscordWebhook(properties().tradeFirstScreenDeclinedWebHookUrl);
            tradeSecondScreenDeclinedWebHookUrl = new DiscordWebhook(properties().tradeSecondScreenDeclinedWebHookUrl);
            tradeFirstScreenAcceptedWebHookUrl = new DiscordWebhook(properties().tradeFirstScreenAcceptedWebHookUrl);
            tradeSecondScreenAcceptedWebHookUrl = new DiscordWebhook(properties().tradeSecondScreenAcceptedWebHookUrl);
            stakeFirstScreenDeclinedWebHookUrl = new DiscordWebhook(properties().stakeFirstScreenDeclinedWebHookUrl);
            stakeSecondScreenDeclinedWebHookUrl = new DiscordWebhook(properties().stakeSecondScreenDeclinedWebHookUrl);
            stakeFirstScreenAcceptedWebHookUrl = new DiscordWebhook(properties().stakeFirstScreenAcceptedWebHookUrl);
            stakeSecondScreenAcceptedWebHookUrl = new DiscordWebhook(properties().stakeSecondScreenAcceptedWebHookUrl);
            stakeItemsWonWebHookUrl = new DiscordWebhook(properties().stakeItemsWonWebHookUrl);
            stakeItemsLostWebHookUrl = new DiscordWebhook(properties().stakeItemsLostWebHookUrl);
            stakeRulesChangedWebHookUrl = new DiscordWebhook(properties().stakeRulesChangedWebHookUrl);
            gambleItemsOfferedWebHookUrl = new DiscordWebhook(properties().gambleItemsOfferedWebHookUrl);
            gambleItemsWonWebHookUrl = new DiscordWebhook(properties().gambleItemsWonWebHookUrl);
            gambleItemsLostWebHookUrl = new DiscordWebhook(properties().gambleItemsLostWebHookUrl);
            yellItemDropsWebHookUrl = new DiscordWebhook(properties().yellItemDropsWebHookUrl);
            slayerKeyDropsWebHookUrl = new DiscordWebhook(properties().slayerKeyDropsWebHookUrl);
            brimstoneKeyDropsWebHookUrl = new DiscordWebhook(properties().brimstoneKeyDropsWebHookUrl);
            crystalKeyDropsWebHookUrl = new DiscordWebhook(properties().crystalKeyDropsWebHookUrl);
            slayerChestWebHookUrl = new DiscordWebhook(properties().slayerChestWebHookUrl);
            brimstoneChestWebHookUrl = new DiscordWebhook(properties().brimstoneChestWebHookUrl);
            crystalChestWebHookUrl = new DiscordWebhook(properties().crystalChestWebHookUrl);
            collectionChestWebHookUrl = new DiscordWebhook(properties().collectionChestWebHookUrl);
            rewardsClaimedColLogWebHookUrl = new DiscordWebhook(properties().rewardsClaimedColLogWebHookUrl);
            itemsDroppedWebHookUrl = new DiscordWebhook(properties().itemsDroppedWebHookUrl);
            playerPickupsWebHookUrl = new DiscordWebhook(properties().playerPickupsWebHookUrl);
            loginsWebHookUrl = new DiscordWebhook(properties().loginsWebHookUrl);
            logoutsWebHookUrl = new DiscordWebhook(properties().logoutsWebHookUrl);
            logoutDuringTournyWebHookUrl = new DiscordWebhook(properties().logoutDuringTournyWebHookUrl);
            itemsBoughtWebHookUrl = new DiscordWebhook(properties().itemsBoughtWebHookUrl);
            itemsSoldWebHookUrl = new DiscordWebhook(properties().itemsSoldWebHookUrl);
            npcDeathWebHookUrl = new DiscordWebhook(properties().npcDeathWebHookUrl);
            playerDeathWebHookUrl = new DiscordWebhook(properties().playerDeathWebHookUrl);
            playersEnteredTournyWebHookUrl = new DiscordWebhook(properties().playersEnteredTournyWebHookUrl);
            playersLeftTournyWebHookUrl = new DiscordWebhook(properties().playersLeftTournyWebHookUrl);
            tournyWinnersWebHookUrl = new DiscordWebhook(properties().tournyWinnersWebHookUrl);
            referralsWebHookUrl = new DiscordWebhook(properties().referralsWebHookUrl);
            referralRewardsClaimedWebHookUrl = new DiscordWebhook(properties().referralRewardsClaimedWebHookUrl);
            itemSalesTPWebHookUrl = new DiscordWebhook(properties().itemSalesTPWebHookUrl);
            itemsBoughtTPWebHookUrl = new DiscordWebhook(properties().itemsBoughtTPWebHookUrl);
            itemsModifiedTPWebHookUrl = new DiscordWebhook(properties().itemsModifiedTPWebHookUrl);
            itemsClaimedTPWebHookUrl = new DiscordWebhook(properties().itemsClaimedTPWebHookUrl);
            itemsCanceledTPWebHookUrl = new DiscordWebhook(properties().itemsCanceledTPWebHookUrl);
            coxRewardsClaimedWebHookUrl = new DiscordWebhook(properties().coxRewardsClaimedWebHookUrl);
            tobRewardsClaimedWebHookUrl = new DiscordWebhook(properties().tobRewardsClaimedWebHookUrl);
            cosRewardsClaimedWebHookUrl = new DiscordWebhook(properties().cosRewardsClaimedWebHookUrl);
            boxesOpenedWebHookUrl = new DiscordWebhook(properties().boxesOpenedWebHookUrl);
            ticketsOpenedWebHookUrl = new DiscordWebhook(properties().ticketsOpenedWebHookUrl);
            fpkMerkPromoCodeClaimedWebHookUrl = new DiscordWebhook(properties().fpkMerkPromoCodeClaimedWebHookUrl);
            itemsAddedToCartDispenserWebHookUrl = new DiscordWebhook(properties().itemsAddedToCartDispenserWebHookUrl);
            itemsDispensedWebHookUrl = new DiscordWebhook(properties().itemsDispensedWebHookUrl);
            presetsLoadedWebHookUrl = new DiscordWebhook(properties().presetsLoadedWebHookUrl);
            nifflerLootedWebHookUrl = new DiscordWebhook(properties().nifflerLootedWebHookUrl);
            lootingBagLootedWebHookUrl = new DiscordWebhook(properties().lootingBagLootedWebHookUrl);
            lootingBagWithdrawnWebHookUrl = new DiscordWebhook(properties().lootingBagWithdrawnWebHookUrl);
        }
    }

    /**
     * The default constructor, will throw an
     * {@link UnsupportedOperationException} if instantiated.
     *
     * @throws UnsupportedOperationException if this class is instantiated.
     */
    private GameServer() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    /**
     * The server's start time
     */
    public static long startTime;

     /**
     * The server's bound time
     */
    public static long boundTime;

    public static String broadcast = "";

    private static DatabaseService databaseService;

    public static DiscordWebhook publicChatWebHookUrl;
    public static DiscordWebhook privateChatWebHookUrl;
    public static DiscordWebhook staffCommandsWebHookUrl;
    public static DiscordWebhook playerCommandsWebHookUrl;
    public static DiscordWebhook votesClaimedWebHookUrl;
    public static DiscordWebhook donationsClaimedWebHookUrl;
    public static DiscordWebhook tradeFirstScreenDeclinedWebHookUrl;
    public static DiscordWebhook tradeSecondScreenDeclinedWebHookUrl;
    public static DiscordWebhook tradeFirstScreenAcceptedWebHookUrl;
    public static DiscordWebhook tradeSecondScreenAcceptedWebHookUrl;
    public static DiscordWebhook stakeFirstScreenDeclinedWebHookUrl;
    public static DiscordWebhook stakeSecondScreenDeclinedWebHookUrl;
    public static DiscordWebhook stakeFirstScreenAcceptedWebHookUrl;
    public static DiscordWebhook stakeSecondScreenAcceptedWebHookUrl;
    public static DiscordWebhook stakeItemsWonWebHookUrl;
    public static DiscordWebhook stakeItemsLostWebHookUrl;
    public static DiscordWebhook stakeRulesChangedWebHookUrl;
    public static DiscordWebhook gambleItemsOfferedWebHookUrl;
    public static DiscordWebhook gambleItemsWonWebHookUrl;
    public static DiscordWebhook gambleItemsLostWebHookUrl;
    public static DiscordWebhook yellItemDropsWebHookUrl;
    public static DiscordWebhook slayerKeyDropsWebHookUrl;
    public static DiscordWebhook brimstoneKeyDropsWebHookUrl;
    public static DiscordWebhook crystalKeyDropsWebHookUrl;
    public static DiscordWebhook slayerChestWebHookUrl;
    public static DiscordWebhook brimstoneChestWebHookUrl;
    public static DiscordWebhook crystalChestWebHookUrl;
    public static DiscordWebhook collectionChestWebHookUrl;
    public static DiscordWebhook rewardsClaimedColLogWebHookUrl;
    public static DiscordWebhook itemsDroppedWebHookUrl;
    public static DiscordWebhook playerPickupsWebHookUrl;
    public static DiscordWebhook loginsWebHookUrl;
    public static DiscordWebhook logoutsWebHookUrl;
    public static DiscordWebhook logoutDuringTournyWebHookUrl;
    public static DiscordWebhook itemsBoughtWebHookUrl;
    public static DiscordWebhook itemsSoldWebHookUrl;
    public static DiscordWebhook npcDeathWebHookUrl;
    public static DiscordWebhook playerDeathWebHookUrl;
    public static DiscordWebhook playersEnteredTournyWebHookUrl;
    public static DiscordWebhook playersLeftTournyWebHookUrl;
    public static DiscordWebhook tournyWinnersWebHookUrl;
    public static DiscordWebhook referralsWebHookUrl;
    public static DiscordWebhook referralRewardsClaimedWebHookUrl;
    public static DiscordWebhook itemSalesTPWebHookUrl;
    public static DiscordWebhook itemsBoughtTPWebHookUrl;
    public static DiscordWebhook itemsModifiedTPWebHookUrl;
    public static DiscordWebhook itemsClaimedTPWebHookUrl;
    public static DiscordWebhook itemsCanceledTPWebHookUrl;
    public static DiscordWebhook coxRewardsClaimedWebHookUrl;
    public static DiscordWebhook tobRewardsClaimedWebHookUrl;
    public static DiscordWebhook cosRewardsClaimedWebHookUrl;
    public static DiscordWebhook boxesOpenedWebHookUrl;
    public static DiscordWebhook ticketsOpenedWebHookUrl;
    public static DiscordWebhook fpkMerkPromoCodeClaimedWebHookUrl;
    public static DiscordWebhook itemsAddedToCartDispenserWebHookUrl;
    public static DiscordWebhook itemsDispensedWebHookUrl;
    public static DiscordWebhook presetsLoadedWebHookUrl;
    public static DiscordWebhook nifflerLootedWebHookUrl;
    public static DiscordWebhook lootingBagLootedWebHookUrl;
    public static DiscordWebhook lootingBagWithdrawnWebHookUrl;

    /**
     * The main method that will put the server online.
     */
    public static void main(String[] args) {
        try {
            if (properties().redirectOutStream) {
                System.setOut(new StackLogger(System.out, "out.txt", "out"));
                System.setErr(new StackLogger(System.err, "err.txt", "err"));
            }
            //This is the time we start loading the server. We have a separate variable for after we have bound the server.
            startTime = System.currentTimeMillis();
            File store = new File(properties().fileStore);
            if (!store.exists()) {
                throw new FileNotFoundException("Cannot load data store from " + store.getAbsolutePath() + ", aborting.");
            }
            fileStore = new DataStore(properties().fileStore);
            logger.info("Loaded filestore @ (./data/filestore) successfully.");
            definitions = new DefinitionRepository();
            ResourceLeakDetector.setLevel(properties().enableLeakDetection ? PARANOID : DISABLED);
            Preconditions.checkState(args.length == 0, "No runtime arguments needed!");
            //We should only verify player profile integrity with SQL disabled,
            //because the DatabaseService does not exist yet during server startup.
            if (!GameServer.properties().enableSql) {
                PlayerProfileVerf.verifyIntegrity();
            }
            logger.info("Initializing the Bootstrap...");
            Bootstrap bootstrap = new Bootstrap(GameServer.properties().gamePort);
            bootstrap.bind();
            initializeDatabase();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    System.out.println("Official Java Shutdownhook: triggered. Players in world: "+World.getWorld().getPlayers().size());
                    // This is the FINAL THREAD active when application shuts down.
                    // you CANNOT USE old threads (like ExecutorServices) or the TaskManager system.
                    // here you should directly run code that runs on this single thread. Emergencies only.
                    // P.S log4j wont print to console from here on out.
                    for (Player player : World.getWorld().getPlayers()) {
                        if (player == null || !player.isRegistered())
                            continue;
                        // program quit but there are still active players.
                        // A save request never got triggered or program got terminated ungraciously.

                        player.requestLogout();

                        // DIRECT SAVE, assuming the lowPrio executor has stopped/wont run any more save reqs
                        try {
                            new PlayerSave.SaveDetails(player).parseDetails();
                            System.out.printf("jesus rescured %s%n", player);
                        } catch (Exception e) {
                            System.out.println("EMERGENCY SAVE FAIL Player "+player+" ex: "+e);
                            e.printStackTrace();
                        }
                    }
                }
            });
            boundTime = System.currentTimeMillis();
            logger.info("Loaded "+GameConstants.SERVER_NAME+ " on port " + GameServer.properties().gamePort + " version v" + GameServer.properties().gameVersion + ".");
            logger.info("The Bootstrap has been bound, "+GameConstants.SERVER_NAME+ " is now online (it took {}ms).", boundTime - startTime);
        } catch (Throwable t) {
            logger.fatal("An error occurred while loading "+GameConstants.SERVER_NAME+".", t);
            System.exit(1);
        }
    }

    public static boolean isUpdating() {
        return isUpdating;
    }

    public static void setUpdating(boolean isUpdating) {
        GameServer.isUpdating = isUpdating;
    }

    public static Flooder getFlooder() {
        return flooder;
    }

    public static boolean isLinux() {
        String osName = System.getProperty("os.name");
        String osNameMatch = osName.toLowerCase();
        String classPath = System.getProperty("java.class.path");
        return osNameMatch.contains("linux");
    }

    public static DatabaseService getDatabaseService() {
        return databaseService;
    }

    private static void initializeDatabase() {
        if (GameServer.properties().enableSql) {
            try {
                databaseService = new DatabaseServiceBuilder()
                    .dataSource(DatabaseService.create(ServerProperties.localProperties.db1))
                    .build();
                databaseService.init();
            } catch (Throwable t) {
                logger.fatal("There was an error initializing the SQL database service, are you sure you have SQL configured?");
                logger.catching(t);
                System.exit(1);
            }
        } else {
            databaseService = new DatabaseService.DisabledDatabaseService();
        }
    }

    public static boolean isStaffOnlyLogins() {
        return staffOnlyLogins;
    }

    public static void setStaffOnlyLogins(boolean staffOnlyLogins) {
        GameServer.staffOnlyLogins = staffOnlyLogins;
    }
}

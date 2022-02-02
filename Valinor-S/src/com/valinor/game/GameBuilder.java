package com.valinor.game;

import com.valinor.GameServer;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.WildernessActivityManager;
import com.valinor.game.content.clan.ClanRepository;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.content.skill.impl.crafting.Crafting;
import com.valinor.game.content.skill.impl.fletching.Fletching;
import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.tournaments.TournamentManager;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.World;
import com.valinor.game.world.definition.loader.impl.*;
import com.valinor.game.world.region.RegionManager;
import com.valinor.net.packet.interaction.InteractionManager;
import com.valinor.util.BackgroundLoader;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Loads all required necessities and starts processes required
 * for the game to work.
 * 
 * @author Lare96
 */
public class GameBuilder {

    /**
     * The background loader that will load various utilities in the background
     * while the bootstrap is preparing the server.
     */
    private final BackgroundLoader backgroundLoader = new BackgroundLoader();

    /**
     * Initializes this game builder effectively preparing the background
     * startup tasks and game processing.
     *
     * @throws Exception
     *             if any issues occur while starting the network.
     */
    public void initialize() throws Exception {
        //Start background tasks..
        backgroundLoader.init(createBackgroundTasks());

        //Start prioritized tasks...
        RegionManager.init();

        System.gc(); // Some init scripts allocate a ton to parse

        //Start game engine..
        GameEngine.getInstance().start();

        //Make sure the background tasks loaded properly..
        if (!backgroundLoader.awaitCompletion())
            throw new IllegalStateException("Background load did not complete normally!");
    }

    /**
     * Returns a queue containing all the background tasks that will be
     * executed by the background loader. Please note that the loader may use
     * multiple threads to load the utilities concurrently, so utilities that
     * depend on each other <b>must</b> be executed in the same task to ensure
     * thread safety.
     *
     * @return the queue of background tasks.
     */
    public Queue<Runnable> createBackgroundTasks() {
        Queue<Runnable> tasks = new ArrayDeque<>();
        tasks.add(IronmanGroupHandler::loadIronmanGroups);
        tasks.add(ClanRepository::load);
        tasks.add(InteractionManager::init);
        if (GameServer.properties().wildernessActivityEnabled) {
            tasks.add(WildernessActivityManager.getSingleton()::init);
        }

        //Load definitions..
        tasks.add(TradingPost::init);
        tasks.add(new Slayer()::loadMasters);
        tasks.add(Crafting::load);
        tasks.add(Fletching::load);
        tasks.add(new ShopLoader());
        tasks.add(new ObjectSpawnDefinitionLoader());
        tasks.add(new DoorDefinitionLoader());
        if (GameServer.properties().tournamentsEnabled) {
            tasks.add(TournamentManager::initalizeTournaments);
        }
        tasks.add(World.getWorld()::postLoad);
        return tasks;
    }
}

package com.valinor;

import com.valinor.game.GameBuilder;
import com.valinor.game.content.skill.impl.hunter.Impling;
import com.valinor.game.content.skill.impl.hunter.PuroPuro;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.entity.combat.method.impl.npcs.godwars.GwdLogic;
import com.valinor.game.world.items.Item;
import com.valinor.net.HostBlacklist;
import com.valinor.net.NetworkBuilder;

/**
 * The bootstrap that will prepare the game, network, and various utilities.
 * This class effectively enables Eldritch to be put online.
 *
 * @author lare96 <http://github.com/lare96>
 */
public final class Bootstrap {

    /**
     * The port that the {@link NetworkBuilder} will listen for connections on.
     */
    private final int port;

    /**
     * The network builder that will initialize the core components of the
     * network.
     */
    private final NetworkBuilder networkBuilder = new NetworkBuilder();

    /**
     * The game builder that will initialize the core components of the game.
     */
    private final GameBuilder gameBuilder = new GameBuilder();

    /**
     * Creates a new {@link Bootstrap}.
     *
     * @param port
     *            the port that the network handler will listen on.
     */
    protected Bootstrap(int port) {
        this.port = port;
    }

    /**
     * Binds the core of the server together and puts Eldritch online.
     *
     * @throws Exception
     *             if any errors occur while putting the server online.
     */
    public void bind() throws Exception {
        gameBuilder.initialize();
        networkBuilder.initialize(port);
        GwdLogic.onServerStart();
        HostBlacklist.loadBlacklist();
        Item.onServerStart();
        Impling.onServerStartup();

    }
}

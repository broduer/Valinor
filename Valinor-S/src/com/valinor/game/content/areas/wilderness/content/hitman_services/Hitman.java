package com.valinor.game.content.areas.wilderness.content.hitman_services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.valinor.game.GameEngine;
import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.syntax.impl.SetPersonOfInterest;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Color;
import com.valinor.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * This class handles all the hitman activity.
 *
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 08, 2022
 */
public class Hitman {

    /**
     * Make sure we cannot initiate this class.
     */
    private Hitman() throws IllegalAccessException {
        throw new IllegalAccessException("cannot initiate this class.");
    }

    /**
     * The class logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Hitman.class);

    /**
     * A list of people that have a bounty on their head.
     */
    private static List<PersonOfInterest> bountiesList = new ArrayList<>();

    /**
     * The json printing.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    /**
     * The location of the stored bounties.
     */
    private static final String FILE_LOCATION = "./data/saves/hitman/bounties.json";

    /**
     * Load the list on server startup. Uses low priority threading.
     */
    public static void init() {
        try {
            bountiesList = GSON.fromJson(new FileReader(FILE_LOCATION), new TypeToken<List<PersonOfInterest>>() {
            }.getType());
            logger.info("Loaded " + bountiesList.size() + "bounties.");
        } catch (FileNotFoundException e) {
            logger.error("Error loading bounties!", e);
        }
    }

    /**
     * Saves the file on the low priority thread.
     */
    public static void saveBounties() {
        //Let's queue the task to run in the low prio thread so the IO is not on the gamethread.
        GameEngine.getInstance().submitLowPriority(() -> {
            try (FileWriter fileWriter = new FileWriter(FILE_LOCATION)) {
                fileWriter.write(GSON.toJson(bountiesList));
            } catch (final Exception e) {
                logger.error("Error saving bounties!", e);
            }
        });
    }

    /**
     * Ask if the player wants to set a bounty.
     *
     * @param player The player requesting a bounty.
     */
    public static void requestBounty(Player player) {
        player.setEnterSyntax(new SetPersonOfInterest());
        player.getPacketSender().sendEnterInputPrompt("Which person should be hunted down?");
    }

    /**
     * Sets a bounty on a specific player
     *
     * @param player           The player setting a bounty.
     * @param personOfInterest The player that receives a bounty on his head.
     * @param bountyAmount     The amount you will receive when killing this bounty.
     */
    public static void setBounty(Player player, Player personOfInterest, int bountyAmount) {
        PersonOfInterest personToAdd = new PersonOfInterest(personOfInterest.getUsername(), bountyAmount);
        if (personToAdd == null) {
            return;
        }

        //Check if the player is already in the list
        if (findPlayer(personOfInterest).isPresent()) {
            player.message(Color.RED.wrap(personOfInterest.getUsername() + " already has a bounty on his head."));
            return;
        }

        if(player.getHostAddress().equalsIgnoreCase(personOfInterest.getHostAddress())) {
            player.message(Color.RED.wrap("You can't put a bounty on your own address."));
            return;
        }

        if(player.<String>getAttribOr(AttributeKey.MAC_ADDRESS, "invalid").equalsIgnoreCase(personOfInterest.getAttribOr(AttributeKey.MAC_ADDRESS, "invalid"))) {
            player.message(Color.RED.wrap("You can't put a bounty on your own address."));
            return;
        }

        //Remove PKP
        player.takePKP(bountyAmount);

        player.message(Color.PURPLE.wrap("You have set a bounty on " + personOfInterest.getUsername() + " you have paid " + Utils.formatRunescapeStyle(bountyAmount) + " PKP."));

        //Update the list
        bountiesList.add(personToAdd);

        //Save the list
        saveBounties();

        World.getWorld().sendWorldMessage("<img=472><shad=0>[<col=" + Color.YELLOW.getColorValue() + ">Hitman service</col>]: " + Color.BLUE.wrap(player.getUsername()) + " <col=0B610B>has put a bounty of " + Color.PURPLE.wrap(Utils.formatRunescapeStyle(bountyAmount)) + " " + Color.PURPLE.wrap("PKP") + " <col=0B610B>on " + Color.BLUE.wrap(personOfInterest.getUsername()) + "<col=0B610B>!");
    }

    /**
     * Find a player in the list.
     *
     * @param playerToCheck The player to check.
     */
    private static Optional<PersonOfInterest> findPlayer(Player playerToCheck) {
        return bountiesList.stream().filter(p -> p.username.equalsIgnoreCase(playerToCheck.getUsername())).findFirst();
    }

    /**
     * Reward the killer the bounty reward.
     *
     * @param killer The player that killed the target.
     * @param killed The player that had a bounty on their head.
     */
    public static void checkOnKill(Player killer, Player killed) {
        Optional<PersonOfInterest> personOfInterest = findPlayer(killed);
        if (personOfInterest.isPresent()) {
            //#grab the bounty amount
            int bounty = personOfInterest.get().bounty;
            killer.message(Color.PURPLE.wrap("You have killed " + killed.getUsername() + " and claimed the " + Utils.formatRunescapeStyle(bounty) +" bounty."));
            World.getWorld().sendWorldMessage("<img=472><shad=0>[<col=" + Color.YELLOW.getColorValue() + ">Hitman service</col>]: " + Color.BLUE.wrap(killer.getUsername()) + " <col=0B610B>claimed the bounty of " + Color.PURPLE.wrap(Utils.formatRunescapeStyle(bounty)) + " " + Color.PURPLE.wrap("PKP") + " <col=0B610B>from " + Color.BLUE.wrap(killed.getUsername()) + "<col=0B610B>!");
            //#update pkp
            var pkp = killer.<Integer>getAttribOr(AttributeKey.PK_POINTS, 0) + bounty;
            killer.putAttrib(AttributeKey.PK_POINTS, pkp);
            killed.message(Color.BLUE.wrap("The bounty on your head has been removed."));

            //#remove from list
            bountiesList.remove(personOfInterest.get());

            //#update file
            saveBounties();
        }
    }

}

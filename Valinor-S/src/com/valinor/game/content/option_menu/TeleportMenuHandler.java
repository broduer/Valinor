package com.valinor.game.content.option_menu;

import com.valinor.game.world.entity.mob.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles all the Teleport Menu options.
 *
 * @author Lennard
 */
public class TeleportMenuHandler {

    private final Player player;

    /**
     * The client child id of the teleport menu option interface.
     */
    public static final int TELEPORT_MENU_CHILD_ID = 72156;

    /**
     * The client child id of the favorite teleports option interface.
     */
    public static final int FAVORITE_MENU_CHILD_ID = 72206;

    /**
     * Map containing all the available {@link OptionMenu}s for this specific
     * Player.
     */
    private HashMap<Integer, OptionMenu> teleportOptions;

    /**
     * Constructs a new TeleportMenuHandler.
     *
     * @param player The {@link Player} bound to this class.
     */
    public TeleportMenuHandler(Player player) {
        this.player = player;
        teleportOptions = TeleportMenuConstants.getDefaultTeleports(player);
    }

    /**
     * Handles the input of the input field.
     *
     * @param searchingFor The String that is being searched for.
     */
    public void handleTeleportInput(final String searchingFor) {
        final Collection<ClientOptionMenu> results = new ArrayList<>();
        if (searchingFor.length() == 1) {
            load();
            player.getPacketSender().sendString(TELEPORT_MENU_CHILD_ID,"");
            return;
        }
        if (searchingFor.length() == 2) {
            player.getPacketSender().sendString(TELEPORT_MENU_CHILD_ID, "Use at least 3 characters");
            player.getPacketSender().sendOptionMenuInterface(TELEPORT_MENU_CHILD_ID, results);
            player.getPacketSender().setScrollPosition(72155, 0, 0);
            return;
        }
        for (Map.Entry<Integer, OptionMenu> entry : teleportOptions.entrySet()) {
            final int identifier = entry.getKey();
            final OptionMenu menu = entry.getValue();

            if (menu.getOptionName().toLowerCase().contains(searchingFor.toLowerCase())) {
                results.add(new ClientOptionMenu(identifier, menu.getOptionName(), menu.getOptionName()));
                continue;
            }
            if (menu.getTags().length > 0) {
                for (String s : menu.getTags()) {
                    if (s.toLowerCase().contains(searchingFor.toLowerCase())) {
                        results.add(new ClientOptionMenu(identifier, menu.getOptionName(), s));
                        break;
                    }
                }
            }
        }
        player.getPacketSender().sendString(TELEPORT_MENU_CHILD_ID,results.isEmpty() ? "No results found" : "");
        player.getPacketSender().sendOptionMenuInterface(TELEPORT_MENU_CHILD_ID, results);
        player.getPacketSender().setScrollPosition(7155, 0, results.size() * 42 + 4);
    }

    /**
     * Updates the Players favorite teleports.
     */
    public void updateFavorites() {
        final Collection<ClientOptionMenu> results = new ArrayList<>();
        for (int teleportId : player.getFavoriteTeleports()) {
            results.add(new ClientOptionMenu(teleportId, teleportOptions.get(teleportId).getOptionName(), ""));
        }
        player.getPacketSender().sendString(FAVORITE_MENU_CHILD_ID, results.isEmpty() ? "No favorites added!" : "");
        player.getPacketSender().sendOptionMenuInterface(FAVORITE_MENU_CHILD_ID, results);
        player.getPacketSender().setScrollPosition(72205, 0, results.size() * 42 + 4);
    }

    /**
     * Adds a new favorite teleport to the favorite teleport collection.
     *
     * @param teleportId The identifier of the teleport that is being added.
     */
    public void addFavoriteTeleport(final int teleportId) {
        if (!teleportOptions.containsKey(teleportId)) {
            player.message("Invalid teleport id!");
            return;
        }
        if (player.getFavoriteTeleports().contains(teleportId)) {
            player.message("The " + teleportOptions.get(teleportId).getOptionName()
                + " teleport is already in your favorites list!");
            return;
        }
        player.getFavoriteTeleports().add(teleportId);
        player.message("You have added the " + teleportOptions.get(teleportId).getOptionName()
            + " teleport to your favorites list!");
        updateFavorites();
    }

    /**
     * Removes the given teleportId from the favorite teleport collection.
     *
     * @param teleportId The identifier of the teleport that is being removed.
     */
    public void removeFavoriteTeleport(final int teleportId) {
        if (!teleportOptions.containsKey(teleportId)) {
            player.message("Invalid teleport id!");
            return;
        }
        if (player.getFavoriteTeleports().contains(teleportId)) {
            player.getFavoriteTeleports().remove(new Integer(teleportId));
            player.message("The " + teleportOptions.get(teleportId).getOptionName()
                + " teleport has been removed from your favorites list!");
            updateFavorites();
        }
    }

    /**
     * Moves the given teleportId one spot down up the favorites list.
     *
     * @param teleportId The identifier of the teleport that has to move up.
     */
    public void moveUpFavoriteTeleport(final int teleportId) {
        if (!teleportOptions.containsKey(teleportId)) {
            player.message("Invalid teleport id!");
            return;
        }
        if (!player.getFavoriteTeleports().contains(teleportId)) {
            player.message("Can't find favorite teleport.");
            return;
        }
        int previousIndex = 0;
        for (int index = 0; index < player.getFavoriteTeleports().size(); index++) {
            if (player.getFavoriteTeleports().get(index) == null) {
                continue;
            }
            if (player.getFavoriteTeleports().get(index) == teleportId) {
                previousIndex = index;
            }
        }
        if (previousIndex > 0) {
            player.getFavoriteTeleports().remove(previousIndex);
            player.getFavoriteTeleports().add(previousIndex - 1, teleportId);
            updateFavorites();
        }
    }

    /**
     * Moves the given teleportId one spot down on the favorites list.
     *
     * @param teleportId The identifier of the teleport that has to move down.
     */
    public void moveDownFavoriteTeleport(final int teleportId) {
        if (!teleportOptions.containsKey(teleportId)) {
            player.message("Invalid teleport id!");
            return;
        }
        if (!player.getFavoriteTeleports().contains(teleportId)) {
            player.message("Can't find favorite teleport.");
            return;
        }
        int previousIndex = 0;
        for (int index = 0; index < player.getFavoriteTeleports().size(); index++) {
            if (player.getFavoriteTeleports().get(index) == null) {
                continue;
            }
            if (player.getFavoriteTeleports().get(index) == teleportId) {
                previousIndex = index;
            }
        }
        final int nextIndex = previousIndex + 1;
        if (nextIndex >= player.getFavoriteTeleports().size()) {
            return;
        }
        player.getFavoriteTeleports().remove(previousIndex);
        player.getFavoriteTeleports().add(nextIndex, teleportId);
        updateFavorites();
    }

    /**
     * Handles the clicking action when selecting the Option interface client
     * sided.
     *
     * @param identifier The identifier of the Option.
     */
    public void handleTeleportClick(final int identifier) {
        if (teleportOptions.containsKey(identifier)) {
            teleportOptions.get(identifier).getAction().execute();
        }
    }


    public void load() {
        final Collection<ClientOptionMenu> results = new ArrayList<>();
        teleportOptions = TeleportMenuConstants.getDefaultTeleports(player);
        for (Map.Entry<Integer, OptionMenu> entry : teleportOptions.entrySet()) {
            final int identifier = entry.getKey();
            final OptionMenu menu = entry.getValue();
            results.add(new ClientOptionMenu(identifier, menu.getOptionName(), menu.getOptionName()));
        }
        player.getPacketSender().sendOptionMenuInterface(TELEPORT_MENU_CHILD_ID, results);
        player.getPacketSender().sendScrollbarHeight(72155, results.size() * 42 + 4);
    }
}

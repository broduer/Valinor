package com.valinor.game.content.trade;

import com.valinor.game.GameConstants;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.content.tournaments.TournamentManager;
import com.valinor.game.world.InterfaceConstants;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.PlayerStatus;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.ItemContainer;
import com.valinor.game.world.items.container.ItemContainer.StackPolicy;
import com.valinor.game.world.items.container.inventory.Inventory;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;
import com.valinor.util.SecondsTimer;
import com.valinor.util.Utils;
import com.valinor.util.timers.TimerKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.WILDERNESS_KEY;

/**
 * Handles the entire trading system.
 * Should be dupe-free.
 *
 * @author Swiffy
 */
public class Trading {

    private static final Logger logger = LogManager.getLogger(Trading.class);

    //Interface data
    private static final int FIRST_TRADE_INTERFACE = 52000;
    public static final int CONTAINER_INTERFACE_ID = 52015;
    private static final int CONTAINER_INTERFACE_ID_2 = 52016;
    private static final int CONFIRM_TRADE_INTERFACE = 52250;

    //Frames data
    private static final int TRADING_WITH_FRAME = 52002;
    private static final int TRADING_WITH_FRAME_2 = 52259;
    private static final int ROW_1 = 52007;
    private static final int ROW_2 = 52008;
    private static final int ROW_3 = 52009;
    private static final int VALUE_1 = 52255;
    private static final int VALUE_2 = 52257;
    private static final int STATUS_FRAME_1 = 52010;
    private static final int STATUS_FRAME_2 = 52252;
    private static final int ITEM_LIST_1_FRAME = 52260;
    private static final int ITEM_LIST_2_FRAME = 52290;
    private static final int ITEM_VALUE_1_FRAME = 52004;
    private static final int ITEM_VALUE_2_FRAME = 52006;

    //Nonstatic
    private final Player player;
    private final ItemContainer container;
    private Player interact;
    private TradeState state = TradeState.NONE;

    //Delays!!
    private final SecondsTimer button_delay = new SecondsTimer();
    private final SecondsTimer request_delay = new SecondsTimer();

    //The possible states during a trade
    private enum TradeState {
        NONE,
        REQUESTED_TRADE,
        TRADE_SCREEN,
        ACCEPTED_TRADE_SCREEN,
        CONFIRM_SCREEN,
        ACCEPTED_CONFIRM_SCREEN
    }

    //Constructor
    public Trading(Player player) {
        this.player = player;

        //The container which will hold all our offered items.
        this.container = new ItemContainer(28, StackPolicy.STANDARD) {

            public void onRefresh() {
                player.getInterfaceManager().openInventory(FIRST_TRADE_INTERFACE, InterfaceConstants.REMOVE_INVENTORY_ITEM - 1);
                player.getPacketSender().sendItemOnInterface(InterfaceConstants.ADD_INVENTORY_ITEM, container.toArray());
                player.getPacketSender().sendItemOnInterface(InterfaceConstants.REMOVE_INVENTORY_ITEM, player.inventory().toArray());
                player.getPacketSender().sendItemOnInterface(CONTAINER_INTERFACE_ID, player.getTrading().getContainer().toArray());
                player.getPacketSender().sendItemOnInterface(CONTAINER_INTERFACE_ID_2, interact.getTrading().getContainer().toArray());
                interact.getPacketSender().sendItemOnInterface(CONTAINER_INTERFACE_ID_2, player.getTrading().getContainer().toArray());
            }
        };
    }

    public void requestTrade(Player otherPlayer) {
        //Check to see if the player is special teleblocked
        if (player.getTimers().has(TimerKey.SPECIAL_TELEBLOCK)) {
            player.teleblockMessage();
            return;
        }

        if (player.getUsername().equalsIgnoreCase("Box test")) {
            player.message("This account can't trade other players.");
            return;
        }

        if (player.inventory().contains(WILDERNESS_KEY) && WildernessArea.inWilderness(player.tile())) {
            player.message(Color.RED.wrap("You can't send trade requests when holding the wilderness key."));
            return;
        }

        var sameGroup = IronmanGroupHandler.isTradingPermitted(player, otherPlayer);

        if(player.gameMode().isCollectionIron() && !otherPlayer.gameMode().isCollectionIron()) {
            player.message("You can't trade this person, you stand alone.");
            return;
        }

        if(otherPlayer.gameMode().isCollectionIron() && !player.gameMode().isCollectionIron()) {
            player.message("You can't trade this person, they stand alone.");
            return;
        }

        // Ironman? fuck off lol!!
        var playerIsIron = player.gameMode().isIronman() || player.gameMode().isHardcoreIronman() || player.gameMode().isUltimateIronman();
        if (playerIsIron && (otherPlayer == null || !(otherPlayer.getPlayerRights().isDeveloperOrGreater(otherPlayer) || sameGroup))) {
            player.message("You are an Iron Man. You stand alone.");
            return;
        }

        // Ironman? fuck off lol!!
        var otherIsIron = otherPlayer.gameMode().isIronman() || otherPlayer.gameMode().isHardcoreIronman() || otherPlayer.gameMode().isUltimateIronman();
        if (otherIsIron && !(player.getPlayerRights().isDeveloperOrGreater(player) || sameGroup)) {
            player.message(otherPlayer.getUsername() + " is an Iron Man. They stand alone.");
            return;
        }

        if (player.jailed() || otherPlayer.jailed() && !otherPlayer.getPlayerRights().isStaffMember(otherPlayer) && !player.getPlayerRights().isStaffMember(player)) {
            player.message("You cannot trade when jailed.");
            return;
        }

        // Make sure you're not trading yourself.
        if (player == otherPlayer || player.getIndex() == otherPlayer.getIndex()) {
            return;
        }

        if (!TournamentManager.canTrade(player, otherPlayer)) {
            return;
        }

        if (state == TradeState.NONE || state == TradeState.REQUESTED_TRADE) {

            if (player.getDueling().inDuel()) {
                player.message("You cannot trade during a duel!");
                return;
            }

            //Make sure to not allow flooding!
            if (request_delay.active()) {
                int seconds = request_delay.secondsRemaining();
                player.message("You must wait another " + (seconds == 1 ? "second" : "" + seconds + " seconds") + " before sending more trade requests.");
                return;
            }

            //The other players' current trade state.
            final TradeState t_state = otherPlayer.getTrading().getState();

            //Should we initiate the trade or simply send a request?
            boolean initiateTrade = false;

            //Update this instance...
            this.setInteract(otherPlayer);
            this.setState(TradeState.REQUESTED_TRADE);

            //Check if target requested a trade with us...
            if (t_state == TradeState.REQUESTED_TRADE) {
                if (otherPlayer.getTrading().getInteract() != null &&
                    otherPlayer.getTrading().getInteract() == player) {
                    initiateTrade = true;
                }
            }

            //Initiate trade for both players with eachother?
            if (initiateTrade) {
                player.getTrading().initiateTrade();
                otherPlayer.getTrading().initiateTrade();
            } else {
                player.message("You've sent a trade request to " + otherPlayer.getUsername() + ".");
                otherPlayer.getPacketSender().sendMessage(player.getUsername() + ":tradereq:");
            }

            //Set the request delay to 2 seconds at least.
            request_delay.start(2);
        } else {
            player.message("You cannot do that right now.");
        }
    }

    public void initiateTrade() {
        if (player.locked() || player.hp() < 1 || interact.locked() || interact.hp() < 1 || player.dead() || interact.dead())
            return;

        //Update statuses
        player.setStatus(PlayerStatus.TRADING);
        player.getTrading().setState(TradeState.TRADE_SCREEN);
        player.getTrading().container.onRefresh();

        //Update strings on interface
        player.getPacketSender().sendString(TRADING_WITH_FRAME, "Trading with: " + interact.getUsername());
        player.getPacketSender().sendString(STATUS_FRAME_1, "").sendString(STATUS_FRAME_2, "Are you sure you want to make this trade?").sendString(ITEM_VALUE_1_FRAME, "(Value: <col=ffffff>0</col>)").sendString(ITEM_VALUE_2_FRAME, "(Value: <col=ffffff>0</col>)");
        player.getPacketSender().sendString(ROW_1, interact.getUsername() + " has " + interact.inventory().getFreeSlots());
        player.getPacketSender().sendString(ROW_2, "free inventory");
        player.getPacketSender().sendString(ROW_3, "slots.");
        player.getPacketSender().sendString(52005, interact.getUsername() + " offers:");
        interact.getPacketSender().sendString(52005, player.getUsername() + " offers:");

        //Reset container
        container.clear(true);
    }

    /**
     * Aborts a trade, giving the other player a message that the trade has cancelled by this user.
     */
    public void abortTrading() {
        if (state != TradeState.NONE) {

            //Cache the current interact
            final Player partner = interact;

            //Return all items...
            for (Item t : container.toNonNullArray()) {
                player.inventory().addAll(t.clone());
            }

            if(state == TradeState.TRADE_SCREEN) {
                Utils.sendDiscordInfoLog("Player " + player.getUsername() + " with IP " + player.getHostAddress() + " aborted the trade returning items: " + Arrays.toString(container.toNonNullArray()), "trade_1st_declined");
            } else if(state == TradeState.CONFIRM_SCREEN) {
                Utils.sendDiscordInfoLog("Player " + player.getUsername() + " with IP " + player.getHostAddress() + " aborted the trade returning items: " + Arrays.toString(container.toNonNullArray()), "trade_2nd_declined");
            }

            //Refresh inventory
            player.inventory().refresh();

            //Reset all attributes...
            resetAttributes();

            //Send decline message
            player.message("Trade declined.");
            player.getInterfaceManager().close(true, true);

            //Reset trade for other player as well (the cached interact)
            if (partner != null) {
                if (partner.getStatus() == PlayerStatus.TRADING) {
                    if (partner.getTrading().getInteract() != null && partner.getTrading().getInteract() == player) {
                        partner.getInterfaceManager().close(true, true);
                    }
                }
            }
        }
    }

    public void acceptTrade() {
        // Security
        if(interact != null && state != null && (!player.getInterfaceManager().isInterfaceOpen(FIRST_TRADE_INTERFACE) && !interact.getInterfaceManager().isInterfaceOpen(FIRST_TRADE_INTERFACE) && state == TradeState.TRADE_SCREEN)) {
            return;
        }

        // Security
        if(interact != null && state != null && (!player.getInterfaceManager().isInterfaceOpen(CONFIRM_TRADE_INTERFACE) && !interact.getInterfaceManager().isInterfaceOpen(CONFIRM_TRADE_INTERFACE) && state == TradeState.CONFIRM_SCREEN)) {
            return;
        }

        //Validate this trade action..
        if (!validate(player, interact, TradeState.TRADE_SCREEN, TradeState.ACCEPTED_TRADE_SCREEN, TradeState.CONFIRM_SCREEN, TradeState.ACCEPTED_CONFIRM_SCREEN)) {
            return;
        }

        //Check button delay...
        if (button_delay.active()) {
            return;
        }

        //Cache the interact...
        final Player partner = interact;

        //Interact's current trade state.
        final TradeState t_state = partner.getTrading().getState();

        if (player.inventory().isFull()) {
            player.getPacketSender().sendMessage("Trade cannot be accepted, you don't have enough free inventory space.");
            return;
        }

        StringBuilder playerItems = new StringBuilder();
        StringBuilder interactItems = new StringBuilder();

        //Check which action to take..
        if (state == TradeState.TRADE_SCREEN) {

            //Verify that the interact can receive all items first..
            int slotsNeeded = 0;
            for (Item t : container.toNonNullArray()) {
                slotsNeeded += t.stackable() && interact.getInventory().contains(t.getId()) ? 0 : 1;
            }

            int freeSlots = interact.getInventory().getFreeSlots();
            if (slotsNeeded > freeSlots) {
                player.message("Trade cannot be accepted, " + partner.getUsername() + " doesn't have enough free inventory space.");
                return;
            }

            //Both are in the same state. Do the first-stage accept.
            setState(TradeState.ACCEPTED_TRADE_SCREEN);

            //Update status...
            player.getPacketSender().sendString(STATUS_FRAME_1, "Waiting for other player..");
            partner.getPacketSender().sendString(STATUS_FRAME_1, "" + player.getUsername() + " has accepted.");

            //Check if both have accepted..
            if (state == TradeState.ACCEPTED_TRADE_SCREEN &&
                t_state == TradeState.ACCEPTED_TRADE_SCREEN) {

                //Technically here, both have accepted.
                //Go into confirm screen!
                player.getTrading().confirmScreen();
                partner.getTrading().confirmScreen();

                //Log items accepted
                for (Item item : partner.getTrading().getContainer().toNonNullArray()) {
                    playerItems.append(Utils.insertCommasToNumber(String.valueOf(item.getAmount()))).append(" ").append(item.unnote().name()).append(" (id ").append(item.getId()).append("), ");
                }
                for (Item item : player.getTrading().getContainer().toNonNullArray()) {
                    interactItems.append(Utils.insertCommasToNumber(String.valueOf(item.getAmount()))).append(" ").append(item.unnote().name()).append(" (id ").append(item.getId()).append("), ");
                }

                Utils.sendDiscordInfoLog("Player " + player.getUsername() + " with IP "+player.getHostAddress()+" gave " + interactItems + " in a trade to " + partner.getUsername(), "trade_1st_accepted");
                Utils.sendDiscordInfoLog("Player " + partner.getUsername() + " with IP "+player.getHostAddress()+" gave " + playerItems + " in a trade to " + player.getUsername(), "trade_1st_accepted");

            }
        } else if (state == TradeState.CONFIRM_SCREEN) {

            //Both are in the same state. Do the second-stage accept.
            setState(TradeState.ACCEPTED_CONFIRM_SCREEN);

            //Update status...
            player.getPacketSender().sendString(STATUS_FRAME_2, "Waiting for " + partner.getUsername() + "'s confirmation..");
            partner.getPacketSender().sendString(STATUS_FRAME_2, "" + player.getUsername() + " has accepted. Do you wish to do the same?");

            //Check if both have accepted..
            if (state == TradeState.ACCEPTED_CONFIRM_SCREEN &&
                t_state == TradeState.ACCEPTED_CONFIRM_SCREEN) {
                //Give items to both players...
                for (Item item : partner.getTrading().getContainer().toNonNullArray()) {
                    player.getInventory().add(item);
                    playerItems.append(Utils.insertCommasToNumber(String.valueOf(item.getAmount()))).append(" ").append(item.unnote().name()).append(" (id ").append(item.getId()).append("), ");
                }
                for (Item item : player.getTrading().getContainer().toNonNullArray()) {
                    partner.getInventory().add(item);
                    interactItems.append(Utils.insertCommasToNumber(String.valueOf(item.getAmount()))).append(" ").append(item.unnote().name()).append(" (id ").append(item.getId()).append("), ");
                }
                try {
                    Utils.sendDiscordInfoLog("Player " + player.getUsername() + " with IP "+player.getHostAddress()+" gave " + interactItems + " in a trade to " + partner.getUsername(), "trade_2nd_accepted");
                    Utils.sendDiscordInfoLog("Player " + partner.getUsername() + " with IP "+partner.getHostAddress()+" gave " + playerItems + " in a trade to " + player.getUsername(), "trade_2nd_accepted");

                    long plr_value = player.getTrading().getContainer().containerValue();
                    long other_plr_value = interact.getTrading().getContainer().containerValue();
                    long difference;
                    difference = (plr_value > other_plr_value) ? plr_value - other_plr_value : other_plr_value - plr_value;
                    if (difference > 10_000_000) {
                        Utils.sendDiscordInfoLog("Player " + player.getUsername() + " (IP " + player.getHostAddress() + ") and " + partner.getUsername() + " (IP " + partner.getHostAddress() + ") trade value difference of " + Utils.insertCommasToNumber(String.valueOf(difference)) + " coins, possible RWT.", "trade_2nd_accepted");
                    }
                } catch (Exception e) {
                    //The value shouldn't ever really be a string, but just in case, let's catch the exception.
                    logger.catching(e);
                    logger.error("Somehow there was an exception from logging trading between player " + player.getUsername() + " and " + partner.getUsername());
                }

                //Reset attributes for both players...
                player.getTrading().resetAttributes();
                partner.getTrading().resetAttributes();

                //Send interface removal for both players...
                player.getInterfaceManager().close();
                partner.getInterfaceManager().close();

                //Send successful trade message!
                player.message("Trade accepted!");
                partner.getPacketSender().sendMessage("Trade accepted!");
            }
        }
        button_delay.start(1);
    }

    private void confirmScreen() {

        //Update state
        player.getTrading().setState(TradeState.CONFIRM_SCREEN);

        //Send new interface
        player.getInterfaceManager().openInventory(CONFIRM_TRADE_INTERFACE, InterfaceConstants.REMOVE_INVENTORY_ITEM - 1);
        player.getPacketSender().sendItemOnInterface(InterfaceConstants.REMOVE_INVENTORY_ITEM, player.inventory().toArray());
        String plr_value = "" + container.containerValue();
        String other_plr_value = "" + interact.getTrading().getContainer().containerValue();
        player.getPacketSender().sendString(TRADING_WITH_FRAME_2, interact.getUsername());
        interact.getPacketSender().sendString(TRADING_WITH_FRAME_2, player.getUsername());
        player.getPacketSender().sendString(VALUE_1, "(Value: <col=ffffff>" + Utils.insertCommasToNumber(plr_value) + "</col>)");
        player.getPacketSender().sendString(VALUE_2, "(Value: <col=ffffff>" + Utils.insertCommasToNumber(other_plr_value) + "</col>)");
        interact.getPacketSender().sendString(VALUE_1, "(Value: <col=ffffff>" + Utils.insertCommasToNumber(other_plr_value) + "</col>)");
        interact.getPacketSender().sendString(VALUE_2, "(Value: <col=ffffff>" + Utils.insertCommasToNumber(plr_value) + "</col>)");
        //Clear all interface frames to remove text of previously traded items.
        player.getPacketSender().sendString(ITEM_LIST_1_FRAME, "--clearall--"); // custom supported by client to clear all
        //Send new interface frames
        String this_items = listItems(container);
        String interact_item = listItems(interact.getTrading().getContainer());
        player.getPacketSender().sendString(ITEM_LIST_1_FRAME, this_items);
        player.getPacketSender().sendString(ITEM_LIST_2_FRAME, interact_item);
    }

    //Deposit or withdraw an item....
    public void handleItem(int id, int amount, int slot, ItemContainer from, ItemContainer to) {
        // Security
        if (!player.getInterfaceManager().isInterfaceOpen(FIRST_TRADE_INTERFACE) && !interact.getInterfaceManager().isInterfaceOpen(FIRST_TRADE_INTERFACE)) {
            return;
        }

        //Validate this trade action..
        if (!validate(player, interact, TradeState.TRADE_SCREEN, TradeState.ACCEPTED_TRADE_SCREEN)) {
            return;
        }

        boolean illegalItem = false;
        Item tradeItem = new Item(id, amount);

        if (tradeItem.untradeable() && !tradeItem.definition(World.getWorld()).tradeable_special_items) {
            illegalItem = true;
        }

        if(tradeItem.definition(World.getWorld()).pvpSpawnable && player.gameMode().instantPker()) {
            illegalItem = true;
        }

        if(illegalItem) {
            player.message("You cannot trade that item.");
            return;
        }

        //Check if the trade was previously accepted (and now modified)...
        boolean modified = false;
        if (state == TradeState.ACCEPTED_TRADE_SCREEN) {
            state = TradeState.TRADE_SCREEN;
            modified = true;
        }
        if (interact.getTrading().getState() == TradeState.ACCEPTED_TRADE_SCREEN) {
            interact.getTrading().setState(TradeState.TRADE_SCREEN);
            modified = true;
        }
        if (modified) {
            player.getPacketSender().sendString(STATUS_FRAME_1, "<col=ca0d0d>TRADE MODIFIED!");
            interact.getPacketSender().sendString(STATUS_FRAME_1, "<col=ca0d0d>TRADE MODIFIED!");
        }

        //Handle the item switch..
        if (state == TradeState.TRADE_SCREEN && interact.getTrading().getState() == TradeState.TRADE_SCREEN) {
            if (from.getItems()[slot] == null)
                return;
            //Check if the item is in the right place
            if (from.getItems()[slot].getId() == id) {
                //Let's not modify the amount.
                //amount = from.getAmount(id);

                //Make sure we can fit that amount in the trade
                if (from instanceof Inventory) {
                    if (!tradeItem.stackable()) {
                        if (amount > container.getFreeSlots()) {
                            amount = container.getFreeSlots();
                        }
                    }
                }

                if (amount <= 0) {
                    return;
                }

                if (amount > from.count(id)) {
                    amount = from.count(id);
                }

                final Item item = new Item(id, amount);

                //Do the switch!
                if (item.getAmount() == 1) {
                    from.remove(item, slot, true, false);
                } else {
                    from.remove(item, true);
                }
                to.add(item, true);

                //Update value frames for both players
                String plr_value = "" + container.containerValue();
                String other_plr_value = "" + interact.getTrading().getContainer().containerValue();
                player.getPacketSender().sendString(ITEM_VALUE_1_FRAME, "(Value: <col=ffffff>" + Utils.insertCommasToNumber(plr_value) + "</col>)");
                player.getPacketSender().sendString(ITEM_VALUE_2_FRAME, "(Value: <col=ffffff>" + Utils.insertCommasToNumber(other_plr_value) + "</col>)");
                interact.getPacketSender().sendString(ITEM_VALUE_1_FRAME, "(Value: <col=ffffff>" + Utils.insertCommasToNumber(other_plr_value) + "</col>)");
                interact.getPacketSender().sendString(ITEM_VALUE_2_FRAME, "(Value: <col=ffffff>" + Utils.insertCommasToNumber(plr_value) + "</col>)");
                player.getPacketSender().sendString(ROW_1, interact.getUsername() + " has " + interact.inventory().getFreeSlots());
                player.getPacketSender().sendString(ROW_2, "free inventory");
                player.getPacketSender().sendString(ROW_3, "slots.");
                player.getPacketSender().sendString(ROW_1, interact.getUsername() + " has " + interact.inventory().getFreeSlots());
                interact.getPacketSender().sendString(ROW_1, player.getUsername() + " has " + player.inventory().getFreeSlots());
                interact.getPacketSender().sendString(ROW_2, "free inventory");
                interact.getPacketSender().sendString(ROW_3, "slots.");
                container.onRefresh();
            }
        } else {
            player.getInterfaceManager().close();
        }
    }

    public void resetAttributes() {
        //Reset trade attributes
        setInteract(null);
        setState(TradeState.NONE);

        //Reset player status if it's trading.
        if (player.getStatus() == PlayerStatus.TRADING) {
            player.setStatus(PlayerStatus.NONE);
        }

        //Reset container..
        container.clear(true);

        //Send the new empty container to the interface
        //Just to clear the items there.
        player.getPacketSender().sendItemOnInterface(CONTAINER_INTERFACE_ID, container.toArray());
    }

    public static String listItems(ItemContainer items) {
        StringBuilder string = new StringBuilder();
        int item_counter = 0;
        List<Item> list = new ArrayList<>();
        loop1:
        for (Item item : items.toNonNullArray()) {
            //Make sure the item isn't already in the list.
            for (Item item_ : list) {
                if (item_.getId() == item.getId()) {
                    continue loop1;
                }
            }
            list.add(new Item(item.getId(), items.count(item.getId())));
        }
        for (Item item : list) {
            if (item_counter > 0) {
                string.append("<br>");
            }
            string.append(item.unnote().name().replaceAll("_", " "));

            String amt = "" + Utils.format(item.getAmount());
            if (item.getAmount() >= 1000000000) {
                amt = "<col=65280>" + (item.getAmount() / 1000000000) + " billion <col=ffffff>(" + Utils.format(item.getAmount()) + ")";
            } else if (item.getAmount() >= 1000000) {
                amt = "<col=65280>" + (item.getAmount() / 1000000) + " million <col=ffffff>(" + Utils.format(item.getAmount()) + ")";
            } else if (item.getAmount() >= 1000) {
                amt = "<col=65535>" + (item.getAmount() / 1000) + "K <col=ffffff>(" + Utils.format(item.getAmount()) + ")";
            }
            string.append(" x <col=ca0d0d>").append(amt);

            item_counter++;
        }
        if (item_counter == 0) {
            string = new StringBuilder("Absolutely nothing!");
        }
        return string.toString();
    }

    /**
     * Validates a player. Basically checks that all specified params add up.
     */
    private static boolean validate(Player player, Player interact, TradeState... tradeState) {
        //Verify player...
        if (player == null || interact == null) {
            return false;
        }

        //Make sure we have proper status
        if (player.getStatus() != PlayerStatus.TRADING) {
            return false;
        }

        //Make sure we're interacting with each other
        if (interact.getStatus() != PlayerStatus.TRADING) {
            return false;
        }

        if (player.getTrading().getInteract() == null
            || player.getTrading().getInteract() != interact) {
            return false;
        }
        if (interact.getTrading().getInteract() == null
            || interact.getTrading().getInteract() != player) {
            return false;
        }

        //Make sure we have proper trade state.
        boolean found = false;
        for (TradeState duelState : tradeState) {
            if (player.getTrading().getState() == duelState) {
                found = true;
                break;
            }
        }
        if (!found) {
            return false;
        }

        //Do the same for our interact
        found = false;
        for (TradeState duelState : tradeState) {
            if (interact.getTrading().getState() == duelState) {
                found = true;
                break;
            }
        }
        return found;
    }

    public TradeState getState() {
        return state;
    }

    public void setState(TradeState state) {
        this.state = state;
    }

    public SecondsTimer getButtonDelay() {
        return button_delay;
    }

    public Player getInteract() {
        return interact;
    }

    public void setInteract(Player interact) {
        this.interact = interact;
    }

    public ItemContainer getContainer() {
        return container;
    }
}

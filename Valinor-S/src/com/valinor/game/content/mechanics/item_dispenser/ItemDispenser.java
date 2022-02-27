package com.valinor.game.content.mechanics.item_dispenser;

import com.valinor.game.GameConstants;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.mechanics.item_simulator.ItemSimulatorUtility;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.Utils;

import java.util.*;
import java.util.regex.Pattern;

import static com.valinor.game.world.entity.AttributeKey.CART_ITEMS;
import static com.valinor.game.world.entity.AttributeKey.CART_ITEMS_TOTAL_VALUE;
import static com.valinor.util.ObjectIdentifiers.TELESCOPE_25439;

/**
 * The item dispenser is an object which crushes all your items into Valinor coins.
 *
 * @author Patrick van Elderen | February, 14, 2021, 23:12
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class ItemDispenser extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if (object.getId() == TELESCOPE_25439) {
                World.getWorld().shop(15).open(player);
                return true;
            }
        }
        if(option == 2) {
            if (object.getId() == TELESCOPE_25439) {
                checkCart(player);
                return true;
            }
        }
        if(option == 3) {
            if (object.getId() == TELESCOPE_25439) {
                loadValueList(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        if (object.getId() == TELESCOPE_25439) {
            player.faceObj(object);
            addItemToCart(player, item);
            return true;
        }
        return false;
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        if(button == 27206) {
            clearCart(player);
            return true;
        }
        if(button == 27208) {
            dispenseItemsDialogue(player);
            return true;
        }
        return false;
    }

    private static final int AMOUNT_STRING_ID = 27316;

    private void clearStrings(Player player) {
        for (int index = 0; index < 100; index++) {
            player.getPacketSender().sendString(AMOUNT_STRING_ID + index, "");
        }
    }

    public void checkCart(Player player) {
        clearStrings(player);
        player.getInterfaceManager().open(ItemSimulatorUtility.WIDGET_ID);
        player.getPacketSender().sendString(ItemSimulatorUtility.WIDGET_TITLE_ID,"Items to dispense");
        var totalValue = player.<Integer>getAttribOr(CART_ITEMS_TOTAL_VALUE, 0);
        var items = player.<ArrayList<Item>>getAttribOr(CART_ITEMS, new ArrayList<Item>());
        player.getPacketSender().sendString(ItemSimulatorUtility.SUB_TITLE_ID,"Total: "+Utils.formatNumber(totalValue)+" "+GameConstants.SERVER_NAME+" coins");
        player.getPacketSender().sendItemOnInterface(ItemSimulatorUtility.DISPENSER_CONTAINER_ID, items);

        for (int index = 0; index < items.size(); index++) {
            Item item = items.get(index);

            if (item == null) {
                continue;
            }

            if (item != null) {
                Optional<Cart> cart = Cart.get(item.getId());
                if(cart.isPresent()) {
                    int value = item.getAmount() * cart.get().value;
                    player.getPacketSender().sendString(AMOUNT_STRING_ID + index, Utils.formatNumber(value));
                }
            }
        }
    }

    /**
     * Adds items into the cart, which will be dispensed later on.
     * @param src The item to dispense.
     */
    public void addItemToCart(Player player, Item src) {
        Optional<Cart> cart = Cart.get(src.getId());

        if(cart.isEmpty()) {
            player.message("You can't dispense your "+src.unnote().name()+".");
            return;
        }

        int amt = player.inventory().count(src.getId());

        final Item item = src.copy();

        if(player.gameMode().instantPker() && item.definition(World.getWorld()).pvpSpawnable) {
            player.message("As a PvP mode you can't dispense your "+src.unnote().name()+".");
            return;
        }

        if(!player.inventory().contains(item)) {
            return;
        }

        if(amt > 1) {
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Deposit all.", "Nevermind.");
                    setPhase(0);
                }

                @Override
                protected void select(int option) {
                    if(isPhase(0)) {
                        if(option == 1) {
                            if(!player.inventory().contains(item)) {
                                stop();
                                return;
                            }
                            //Change the item amount
                            item.setAmount(amt);
                            store(player, item);
                            stop();
                        } else if ((option == 2)) {
                            stop();
                        }
                    }
                }
            });
            return;
        }

        store(player, item);
    }

    private void store(Player player, Item src) {
        final Item item = src.copy();

        if(!player.inventory().contains(item)) {
            return;
        }

        player.stopActions(true);

        //Do some animation
        player.animate(832);

        //Get the current stored item list
        var items = player.<ArrayList<Item>>getAttribOr(CART_ITEMS, new ArrayList<Item>());

        if (items != null) {
            //Store the items in it's respective attribute
            Optional<Item> any = items.stream().filter(i -> i.matchesId(item.getId())).findAny();
            if (any.isPresent() && ((1L * any.get().getAmount()) + item.getAmount() <= Integer.MAX_VALUE)) {
                any.get().setAmount(any.get().getAmount() + item.getAmount());

                if(Cart.get(any.get().getId()).isPresent()) {
                    var totalItems = item.getAmount();
                    var itemValue = Cart.get(item.getId()).get().value;
                    var total = totalItems * itemValue;
                    var increaseTotalValue = player.<Integer>getAttribOr(CART_ITEMS_TOTAL_VALUE, 0) + total;
                    player.putAttrib(CART_ITEMS_TOTAL_VALUE, increaseTotalValue);
                }
            } else {
                items.add(item);
                if(Cart.get(item.getId()).isPresent()) {
                    var value = Cart.get(item.getId()).get().value;
                    String itemName = item.definition(World.getWorld()).name;
                    if(itemName != null && itemName.contains("pet")) {
                        var bonusPoints = value * .50;
                        player.message(Color.PURPLE.wrap("You will get 50% more coins for exchanging the "+itemName+", extra: "+(int)bonusPoints+"."));
                        value += bonusPoints;
                    }
                    var totalValue = player.<Integer>getAttribOr(CART_ITEMS_TOTAL_VALUE, 0) + value * item.getAmount();
                    player.putAttrib(CART_ITEMS_TOTAL_VALUE, totalValue);
                }
            }

            //Remove the item from inventory
            if (item.getAmount() > 1)
                player.inventory().removeAll(item);
            else
                player.inventory().remove(item, true);

            //Update the attribute
            player.putAttrib(CART_ITEMS, items);

            //Send a message so people know something has been stored
            String itemName = item.unnote().name();
            boolean amOverOne = item.getAmount() > 1;
            String amtString = amOverOne ? "x" + Utils.format(item.getAmount()) + "" : Utils.getAOrAn(item.name());
            player.message(Color.RED.tag() + "You've added " + amtString + " " + itemName + " into the cart.");
            Utils.sendDiscordInfoLog(player.getUsername() + " added " + amtString + " " + itemName + " into the cart.", "added_to_cart_dispenser");
        }
    }

    /**
     * Remove all the items from the cart.
     */
    public void clearCart(Player player) {
        var items = player.<ArrayList<Item>>getAttribOr(CART_ITEMS, new ArrayList<Item>());

        if(items.isEmpty()) {
            player.message(Color.RED.tag()+"There are no items in the cart.");
            return;
        }

        //Do some animation
        player.animate(832);

        //Add items to inventory or send to bank when inventory is full.
        for (final Item item : items) {
            player.inventory().addOrBank(item);
        }

        //Clear the list
        items.clear();
        player.putAttrib(CART_ITEMS_TOTAL_VALUE,0);
        player.getPacketSender().sendItemOnInterface(ItemSimulatorUtility.DISPENSER_CONTAINER_ID, items);
    }

    private void dispense(Player player) {
        if(!player.inventory().hasCapacity(new Item(CustomItemIdentifiers.VALINOR_COINS))) {
            player.message("You have no room for any "+GameConstants.SERVER_NAME+" coins!");
           return;
        }

        player.face(new Tile(3083, 3483));

        //Do some animation
        player.animate(832);

        //Get total value
        var totalCartValue = player.<Integer>getAttribOr(CART_ITEMS_TOTAL_VALUE,0);

        //Add coins to inv
        player.inventory().add(new Item(CustomItemIdentifiers.VALINOR_COINS, totalCartValue),true);

        if(totalCartValue >= 1000) {
            DailyTaskManager.increase(DailyTasks.VALINOR_COINS, player);
        }

        //Clear items after we received the coins not before!
        var items = player.<ArrayList<Item>>getAttribOr(CART_ITEMS, new ArrayList<Item>());
        Utils.sendDiscordInfoLog(player.getUsername() + " dispensed " + Arrays.toString(items.toArray()) + " in the item dispenser.", "items_dispensed");
        items.clear();
        player.putAttrib(CART_ITEMS_TOTAL_VALUE,0);
        player.getPacketSender().sendItemOnInterface(ItemSimulatorUtility.DISPENSER_CONTAINER_ID, items);
        clearStrings(player);
    }

    /**
     * Opens up a list with all the items and their respected values.
     */
    public void loadValueList(Player player) {
        List<String> prices = new ArrayList<>();

        prices.add("<br><col=" + Color.MITHRIL.getColorValue() + "> Item - Value</col><br><br>");
        for (Cart cart : Cart.values()) {
            prices.add(new Item(cart.item).unnote().name()+" - "+ Utils.formatNumber(cart.value)+" "+ GameConstants.SERVER_NAME+" coins<br>");
        }

        player.sendScroll("Item values", Collections.singletonList(prices).toString().replaceAll(Pattern.quote("["), "").replaceAll(Pattern.quote("]"), "").replaceAll(",", ""));
    }

    public void dispenseItemsDialogue(Player p) {
        //First check if we have items
        var items = p.<ArrayList<Item>>getAttribOr(CART_ITEMS, new ArrayList<Item>());
        if(items.isEmpty()) {
            p.message(Color.RED.tag()+"There are no items in the cart.");
            return;
        }

        //We have items in our cart lets continue...
        p.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.STATEMENT,"Are you sure you wish to destroy your items?");
                setPhase(0);
            }

            @Override
            protected void next() {
                if(isPhase(0)) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Yes, i'm sure.", "No, stop the process and return my items.");
                    setPhase(1);
                }
            }

            @Override
            protected void select(int option) {
                if(isPhase(1)) {
                    if(option == 1) {
                        if(items.isEmpty()) {
                            p.message(Color.RED.tag()+"There are no items in the cart.");
                            stop();
                            return;
                        }
                        dispense(p);
                        stop();
                    } else if(option == 2) {
                        if(items.isEmpty()) {
                            stop();
                            return;
                        }
                        clearCart(p);//Return items
                        stop();
                    }
                }
            }
        });
    }
}

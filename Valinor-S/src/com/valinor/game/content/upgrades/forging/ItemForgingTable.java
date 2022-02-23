package com.valinor.game.content.upgrades.forging;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.valinor.game.world.entity.AttributeKey.LAST_ENCHANT_SELECTED;

/**
 * This class represents the item forging table.
 *
 * @author Patrick van Elderen | 16 okt. 2019 : 09:49
 * @see <a href="https://github.com/Patrick9-10-1995">Github profile</a>
 */
public class ItemForgingTable extends Interaction {

    private static final int INTERFACE = 69000;
    private static final int REQUIRED_ITEMS_CONTAINER = 69016;
    private static final int SUCCESS_RATE_STRING = 69008;
    private static final int ENCHANTED_ITEM_REWARD = 69009;
    public static final int START_OF_FORGE_LIST = 69021;
    private static final int ITEM_SCROLL_ID = 69020;

    private void clear(Player player) {
        //Clear out old text
        for (int index = 0; index < 50; index++) {
            player.getPacketSender().sendString(START_OF_FORGE_LIST + index, "");
        }
    }

    public void open(Player player, ItemForgingCategory tier) {
        player.getInterfaceManager().open(INTERFACE);

        //We're viewing the items for tier...
        player.putAttrib(AttributeKey.VIEWING_FORGING_CATEGORY, tier);

        //Clear previous data
        clear(player);

        final List<ItemForgement> items = ItemForgement.sortByTier(tier);
        final int size = items.size();

        player.getPacketSender().sendScrollbarHeight(ITEM_SCROLL_ID, size * 14 + 5);

        for (int index = 0; index < size; index++) {
            final ItemForgement itemForgement = items.get(index);
            player.getPacketSender().sendString(START_OF_FORGE_LIST + index, itemForgement.name);
        }

        //Open first enchantment in the list
        loadInfo(player, ItemForgement.ARMADYL_GODSWORD);
        player.getPacketSender().setClickedText(START_OF_FORGE_LIST,true);
        player.putAttrib(LAST_ENCHANT_SELECTED, 69021);
    }

    private void loadInfo(Player player, ItemForgement itemForgement) {
        //Write the success rate
        String rate = "Success rate: "+itemForgement.successRate+"%";
        player.getPacketSender().sendString(SUCCESS_RATE_STRING, rate);

        //Write the required items
        player.getPacketSender().sendItemOnInterface(REQUIRED_ITEMS_CONTAINER, itemForgement.requiredItem);

        //Write the reward
        player.getPacketSender().sendItemOnInterfaceSlot(ENCHANTED_ITEM_REWARD, itemForgement.enchantedItem, 0);
    }

    private void forge(Player player, ItemForgement itemForgement) {
        Item unnoted = itemForgement.requiredItem.unnote();
        Item noted = itemForgement.requiredItem.note();
        if (!player.inventory().containsAny(unnoted, noted)) {//Check if we actually have the required items to enchant.
            player.message(Color.RED.tag() + "You do not have the required items to attempt this enchantment.");
            player.message("Required item: " + itemForgement.requiredItem.name());
            return;
        }

        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... options) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Attempt to sacrifice items for an enchantment.", "Nevermind.");
                setPhase(0);
            }

            @Override
            public void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        if (!player.inventory().containsAny(unnoted, noted)) {
                            stop();
                            return;
                        }

                        player.message("You attempt the enchantment...");

                        //Calculate the chance for succeeding
                        int chance = itemForgement.successRate;

                        if (player.inventory().contains(unnoted))
                            player.inventory().remove(unnoted);
                        else if (player.inventory().contains(noted))
                            player.inventory().remove(noted);

                        var attempts = player.<Integer>getAttribOr(itemForgement.attempts, 0) + 1;
                        player.putAttrib(itemForgement.attempts, attempts);

                        //Roll
                        if (Utils.percentageChance(chance)) {
                            //Succesfully enchanted

                            //Add enchanted item to inventory or bank
                            player.inventory().addOrBank(itemForgement.enchantedItem);

                            //Send message
                            var totalAttempts = player.<Integer>getAttribOr(itemForgement.attempts, 0);
                            player.message("<col=" + Color.GREEN.getColorValue() + ">You successfully enchanted your item.");
                            World.getWorld().sendWorldMessage("<img=1046>[<col=" + Color.RED.getColorValue() + ">Item forging</col>]: " + Color.HOTPINK.tag() + "" + player.getUsername() + "</col> forged: " + Color.DARK_GREEN.tag() + "" + itemForgement.enchantedItem.name() + "</col>. (Try : " + totalAttempts + ")");
                        } else {
                            //Failed
                            player.message("<col=" + Color.RED.getColorValue() + ">You tried to enchant your item but sadly failed. Try again next time.");
                        }
                        stop();
                    } else if (option == 2) {
                        stop();
                    }
                }
            }
        });
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        if (button == 69006) {
            ItemForgement itemForgement = null;
            button = player.getAttribOr(LAST_ENCHANT_SELECTED, -1); //Override button
            if (player.getAttribOr(AttributeKey.VIEWING_FORGING_CATEGORY, null) == ItemForgingCategory.WEAPON) {
                itemForgement = WEAPON_CATEGORY_BUTTONS.get(button);
            }

            if (player.getAttribOr(AttributeKey.VIEWING_FORGING_CATEGORY, null) == ItemForgingCategory.ARMOUR) {
                itemForgement = ARMOUR_CATEGORY_BUTTONS.get(button);
            }

            if (player.getAttribOr(AttributeKey.VIEWING_FORGING_CATEGORY, null) == ItemForgingCategory.MISC) {
                itemForgement = MISC_CATEGORY_BUTTONS.get(button);
            }

            if (itemForgement != null) {
                forge(player, itemForgement);
            }
            return true;
        }

        if (button == 69010) {
            open(player, ItemForgingCategory.WEAPON);
            loadInfo(player, ItemForgement.ARMADYL_GODSWORD);
            player.getPacketSender().setClickedText(START_OF_FORGE_LIST, true);
            player.getPacketSender().sendString(68004, "Tier I");
            return true;
        }

        if (button == 69011) {
            open(player, ItemForgingCategory.ARMOUR);
            loadInfo(player, ItemForgement.AMULET_OF_FURY);
            player.getPacketSender().setClickedText(START_OF_FORGE_LIST, true);
            player.getPacketSender().sendString(68004, "Tier II");
            return true;
        }

        if (button == 69012) {
            open(player, ItemForgingCategory.MISC);
            loadInfo(player, ItemForgement.RUNE_POUCH);
            player.getPacketSender().setClickedText(START_OF_FORGE_LIST, true);
            player.getPacketSender().sendString(68004, "Tier III");
            return true;
        }

        if (player.getAttribOr(AttributeKey.VIEWING_FORGING_CATEGORY, null) == ItemForgingCategory.WEAPON && WEAPON_CATEGORY_BUTTONS.containsKey(button)) {
            loadInfo(player, WEAPON_CATEGORY_BUTTONS.get(button));
            player.putAttrib(LAST_ENCHANT_SELECTED, button);
            player.getPacketSender().setClickedText(button, true);
            return true;
        }

        if (player.getAttribOr(AttributeKey.VIEWING_FORGING_CATEGORY, null) == ItemForgingCategory.ARMOUR && ARMOUR_CATEGORY_BUTTONS.containsKey(button)) {
            loadInfo(player, ARMOUR_CATEGORY_BUTTONS.get(button));
            player.putAttrib(LAST_ENCHANT_SELECTED, button);
            player.getPacketSender().setClickedText(button, true);
            return true;
        }

        if (player.getAttribOr(AttributeKey.VIEWING_FORGING_CATEGORY, null) == ItemForgingCategory.MISC && MISC_CATEGORY_BUTTONS.containsKey(button)) {
            loadInfo(player, MISC_CATEGORY_BUTTONS.get(button));
            player.putAttrib(LAST_ENCHANT_SELECTED, button);
            player.getPacketSender().setClickedText(button, true);
            return true;
        }
        return false;
    }

    private static final HashMap<Integer, ItemForgement> WEAPON_CATEGORY_BUTTONS = new HashMap<>();
    private static final HashMap<Integer, ItemForgement> ARMOUR_CATEGORY_BUTTONS = new HashMap<>();
    private static final HashMap<Integer, ItemForgement> MISC_CATEGORY_BUTTONS = new HashMap<>();

    //Store all buttons in a hashmap
    static {
        int button;
        button = START_OF_FORGE_LIST;

        for (final ItemForgement itemForgement : ItemForgement.sortByTier(ItemForgingCategory.WEAPON)) {
            WEAPON_CATEGORY_BUTTONS.put(button++, itemForgement);
        }
        button = START_OF_FORGE_LIST;
        for (final ItemForgement itemForgement : ItemForgement.sortByTier(ItemForgingCategory.ARMOUR)) {
            ARMOUR_CATEGORY_BUTTONS.put(button++, itemForgement);
        }
        button = START_OF_FORGE_LIST;
        for (final ItemForgement itemForgement : ItemForgement.sortByTier(ItemForgingCategory.MISC)) {
            MISC_CATEGORY_BUTTONS.put(button++, itemForgement);
        }
    }
}

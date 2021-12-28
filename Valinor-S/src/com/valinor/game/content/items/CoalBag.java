package com.valinor.game.content.items;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 28, 2021
 */
public class CoalBag extends Interaction {

    private int getBagSize(Player player) {
        int capeId = player.getEquipment().getId(EquipSlot.CAPE);
        int maxSize = 27;
        if (capeId == SMITHING_CAPE || capeId == SMITHING_CAPET)
            maxSize += 9;
        return maxSize;
    }

    private void fill(Player player) {
        int maxSize = getBagSize(player);
        if(player.<Integer>getAttribOr(AttributeKey.BAGGED_COAL, 0) >= maxSize) {
            player.itemBox("The coal bag is full.", COAL_BAG);
            return;
        }
        for(Item item : player.getInventory().getItems()) {
            if(item != null && item.getId() == COAL) {
                player.inventory().remove(item);
                var baggedCoal = player.<Integer>getAttribOr(AttributeKey.BAGGED_COAL, 0) + 1;
                player.putAttrib(AttributeKey.BAGGED_COAL, baggedCoal);
                if(baggedCoal >= maxSize) {
                    break;
                }
            }
        }
        check(player);
    }

    private void check(Player player) {
        if(player.<Integer>getAttribOr(AttributeKey.BAGGED_COAL, 0) == 0)
            player.itemBox("The coal bag is empty.", COAL_BAG);
        else if(player.<Integer>getAttribOr(AttributeKey.BAGGED_COAL, 0) == 1)
            player.itemBox("The coal bag contains one piece of coal.", COAL_BAG);
        else
            player.itemBox("The coal bag contains " + player.<Integer>getAttribOr(AttributeKey.BAGGED_COAL, 0) + " pieces of coal.", COAL_BAG);
    }

    private void empty(Player player) {
        if(player.<Integer>getAttribOr(AttributeKey.BAGGED_COAL, 0) == 0) {
            player.itemBox("The coal bag is empty.", COAL_BAG);
            return;
        }
        int freeSlots = player.getInventory().getFreeSlots();
        if(freeSlots == 0) {
            player.message("Not enough space in your inventory.");
            return;
        }
        for(int i = 0; i < player.getInventory().getItems().length; i++) {
            if(player.getInventory().get(i) == null) {
                player.getInventory().set(i, new Item(COAL, 1),true);
                var baggedCoal = player.<Integer>getAttribOr(AttributeKey.BAGGED_COAL, 0);
                baggedCoal--;
                player.putAttrib(AttributeKey.BAGGED_COAL, baggedCoal);
                if (baggedCoal == 0) {
                    break;
                }
            }
        }
        if(player.<Integer>getAttribOr(AttributeKey.BAGGED_COAL, 0) == 0)
            player.itemBox("The coal bag is now empty.", COAL_BAG);
        else if(player.<Integer>getAttribOr(AttributeKey.BAGGED_COAL, 0) == 1)
            player.itemBox("There is one piece of coal left in the bag.", COAL_BAG);
        else
            player.itemBox("There are " + player.<Integer>getAttribOr(AttributeKey.BAGGED_COAL, 0) + " pieces of coal left in the bag.", COAL_BAG);
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == COAL_BAG_12019) {
                fill(player);
                return true;
            }
        }
        if(option == 2) {
            if(item.getId() == COAL_BAG_12019) {
                empty(player);
                return true;
            }
        }
        if(option == 3) {
            if(item.getId() == COAL_BAG_12019) {
                check(player);
                return true;
            }
        }
        return false;
    }
}

package com.valinor.game.content.mechanics.break_items;

import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;

import static com.valinor.util.ItemIdentifiers.COINS_995;
import static com.valinor.util.NpcIdentifiers.PERDU;

/**
 * This class will handle all the enchanted items that break.
 * @author Patrick van Elderen | February, 01, 2021, 14:20
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class BreakItemsOnDeath {

    /**
     * Gets the total cost of repairing a player's broken items.
     *
     * @param player The player with broken items
     * @return The amount of coins it costs to repair your items.
     */
    public static int getRepairCost(Player player) {
        int cost = 0;
        for (BrokenItem item : BrokenItem.values()) {
            final int brokenItems = player.inventory().count(item.brokenItem);
            cost += item.costToRepair * brokenItems;
        }
        //System.out.println("total cost "+cost);
        return cost;
    }

    /**
     * Repairs all broken items for a player.
     *
     * @param player The player repairing items.
     */
    public static void repair(Player player) {
        for (BrokenItem item : BrokenItem.values()) {
            final int count = player.inventory().count(item.brokenItem);
            if (count > 0) {
                var costToRepair = getRepairCost(player);
                boolean canRepair = false;
                int coinsInInventory = player.inventory().count(COINS_995);

                if (coinsInInventory > 0) {
                    if(coinsInInventory >= costToRepair) {
                        canRepair = true;
                        player.inventory().remove(new Item(COINS_995, costToRepair));
                    }
                }

                var coinsInBank = player.getBank().count(COINS_995);
                if(coinsInBank >= costToRepair) {
                    canRepair = true;
                    player.getBank().remove(COINS_995, costToRepair);
                }

                if(canRepair) {
                    player.inventory().remove(item.brokenItem, count);
                    player.inventory().add(item.originalItem, count);
                    DialogueManager.npcChat(player, Expression.DEFAULT, PERDU, "Your items have been repaired.");
                } else {
                    DialogueManager.npcChat(player, Expression.DEFAULT, PERDU, "You could not afford repairing all your items.");
                    break;
                }
            }
        }
    }
}

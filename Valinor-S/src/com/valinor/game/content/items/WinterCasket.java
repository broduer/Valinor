package com.valinor.game.content.items;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.WINTER_CASKET;
import static com.valinor.util.ItemIdentifiers.HEFTY_TAX_BAG;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 09, 2022
 */
public class WinterCasket extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == WINTER_CASKET) {
                open(player);
                return true;
            }
        }
        return false;
    }

    private void open(Player player) {
        if (player.inventory().contains(WINTER_CASKET)) {
            player.inventory().remove(WINTER_CASKET);
            Item reward = reward().copy();
            if (rare) {
                World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a supply crate!");
            }
            player.inventory().addOrDrop(reward);
            rare = false;
        }
    }

    private final List<Item> COMMON_REWARDS = Arrays.asList(

    );

    private final List<Item> UNCOMMON_REWARDS = Arrays.asList(
        new Item(HEFTY_TAX_BAG)
    );

    private final List<Item> RARE_REWARDS = Arrays.asList(

    );

    private boolean rare = false;

    private Item reward() {
        if (World.getWorld().rollDie(50, 1)) {
            rare = true;
            return Utils.randomElement(RARE_REWARDS);
        } else if (World.getWorld().rollDie(20, 1)) {
            return Utils.randomElement(UNCOMMON_REWARDS);
        } else {
            return Utils.randomElement(COMMON_REWARDS);
        }
    }
}

package com.valinor.game.content.items.mystery_box.impl;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.Utils;

import static com.valinor.util.CustomItemIdentifiers.REVENANT_MYSTER_BOX;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since October 15, 2021
 */
public class RevenantMysteryBox extends PacketInteraction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == REVENANT_MYSTER_BOX) {
                open(player);
                return true;
            }
        }
        return false;
    }

    private void open(Player player) {
        if (player.inventory().contains(REVENANT_MYSTER_BOX)) {
            player.inventory().remove(new Item(REVENANT_MYSTER_BOX), true);
            Item reward = rollReward();
            player.inventory().add(reward, true);
            Utils.sendDiscordInfoLog("Player " + player.getUsername() + " received a "+reward.unnote().name()+" from a revenant mystery box.", "box_and_tickets");
        }
    }

    private static final Item[] COMMON = new Item[]{
        new Item(ItemIdentifiers.VIGGORAS_CHAINMACE),
        new Item(ItemIdentifiers.CRAWS_BOW),
        new Item(ItemIdentifiers.THAMMARONS_SCEPTRE),
        new Item(ItemIdentifiers.AMULET_OF_AVARICE),
    };

    public Item rollReward() {
        return Utils.randomElement(COMMON);
    }
}

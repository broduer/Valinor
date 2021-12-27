package com.valinor.game.content.packet_actions.interactions.items;

import com.valinor.game.content.items.RockCake;
import com.valinor.game.content.skill.impl.slayer.content.SlayerRing;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.InteractionManager;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * juni 04, 2020
 */
public class ItemActionThree {

    public static void click(Player player, Item item) {
        int id = item.getId();

        if (InteractionManager.checkItemInteraction(player, item, 3)) {
            return;
        }

        if(player.getRunePouch().quickFill(item.getId())) {
            return;
        }

        if(SlayerRing.onItemOption3(player, item)) {
            return;
        }

        if(RockCake.onItemOption3(player, item)) {
            return;
        }

        switch (id) {
            case LOOTING_BAG, LOOTING_BAG_22586 -> player.getLootingBag().depositWidget();
        }
    }
}

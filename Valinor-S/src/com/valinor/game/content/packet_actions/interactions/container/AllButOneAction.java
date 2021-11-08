package com.valinor.game.content.packet_actions.interactions.container;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;

import static com.valinor.game.world.InterfaceConstants.WITHDRAW_BANK;

/**
 * @author PVE
 * @Since augustus 26, 2020
 */
public class AllButOneAction {

    public static void allButOne(Player player, int slot, int interfaceId, int id) {
        if (interfaceId == WITHDRAW_BANK) {
            Item item = player.getBank().get(slot);
            if (item == null || item.getId() != id || item.getAmount() <= 1) {
                return;
            }
            player.getBank().withdraw(id, slot, item.getAmount() - 1);
        }
    }

}

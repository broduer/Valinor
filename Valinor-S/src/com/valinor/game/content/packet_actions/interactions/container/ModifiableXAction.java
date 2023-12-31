package com.valinor.game.content.packet_actions.interactions.container;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;

import static com.valinor.game.world.InterfaceConstants.WITHDRAW_BANK;

/**
 * @author PVE
 * @Since augustus 26, 2020
 */
public class ModifiableXAction {

    public static void modifiableXAction(Player player, int slot, int interfaceId, int id, int amount) {
        if (interfaceId == WITHDRAW_BANK) {
            final Item item = player.getBank().get(slot);

            if (item == null || item.getId() != id) {
                return;
            }

            player.getBank().withdraw(id, slot, amount);
        }
    }
}

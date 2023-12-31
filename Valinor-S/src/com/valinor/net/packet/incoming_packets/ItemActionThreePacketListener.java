package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.game.content.items.RottenPotato;
import com.valinor.game.content.packet_actions.interactions.items.ItemActionThree;
import com.valinor.game.world.InterfaceConstants;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

import static com.valinor.util.ItemIdentifiers.ROTTEN_POTATO;

/**
 * @author PVE
 * @Since augustus 27, 2020
 */
public class ItemActionThreePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        final int itemId = packet.readShortA();
        final int slot = packet.readLEShortA();
        final int interfaceId = packet.readLEShortA();

        player.debugMessage(String.format("Third item action, itemId: %d slot: %d interfaceId: %d", itemId, slot, interfaceId));

        if (slot < 0 || slot > 27)
            return;
        Item item = player.inventory().get(slot);
        if (item != null && item.getId() == itemId) {

            if(item.getId() == ROTTEN_POTATO) {
                RottenPotato.onItemOption3(player);
                return;
            }

            if (player.locked() || player.dead()) {
                return;
            }

            if (player.busy()) {
                return;
            }

            if (!player.getBankPin().hasEnteredPin() && GameServer.properties().requireBankPinOnLogin) {
                player.getBankPin().openIfNot();
                return;
            }

            if(player.askForAccountPin()) {
                player.sendAccountPinMessage();
                return;
            }

            player.afkTimer.reset();

            player.stopActions(false);
            player.putAttrib(AttributeKey.ITEM_SLOT, slot);
            player.putAttrib(AttributeKey.FROM_ITEM, player.inventory().get(slot));
            player.putAttrib(AttributeKey.ITEM_ID, item.getId());

            if (interfaceId == InterfaceConstants.INVENTORY_INTERFACE) {
                ItemActionThree.click(player, item);
            }
        }
    }
}

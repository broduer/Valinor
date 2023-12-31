package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.game.content.items.RottenPotato;
import com.valinor.game.content.packet_actions.interactions.items.ItemActionTwo;
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
public class ItemActionTwoPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        final int interfaceId = packet.readLEShortA();
        final int slot = packet.readLEShort();
        final int itemId = packet.readShortA();

        player.debugMessage(String.format("Second item action, interface: %d slot: %d itemId: %d", interfaceId, slot, itemId));

        if (slot < 0 || slot > 27) return;

        // Check if we used the item that we think we used.
        Item used = player.inventory().get(slot);
        if (used != null && used.getId() == itemId) {
            if(used.getId() == ROTTEN_POTATO) {
                RottenPotato.onItemOption2(player);
                return;
            }

            // Not possible when locked
            if (player.locked() || player.dead() || !player.inventory().hasAt(slot)) {
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
            player.putAttrib(AttributeKey.ITEM_ID, used.getId());

            if (interfaceId == InterfaceConstants.INVENTORY_INTERFACE) {
                ItemActionTwo.click(player, used);
            }
        }
    }
}

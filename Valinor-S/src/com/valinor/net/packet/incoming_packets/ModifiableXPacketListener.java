package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.game.content.packet_actions.interactions.container.ModifiableXAction;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

/**
 * @author PVE
 * @Since augustus 26, 2020
 */
public class ModifiableXPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        final int slot = packet.readShortA();
        final int interfaceId = packet.readShort();
        final int id = packet.readShortA();
        final int amount = packet.readInt();

        if (player == null || player.dead()) {
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

        player.debugMessage(String.format("modifiableX action, slot: %d interfaceId: %d id %d amount %d", slot, interfaceId, id, amount));

        ModifiableXAction.modifiableXAction(player, slot, interfaceId, id, amount);
    }
}

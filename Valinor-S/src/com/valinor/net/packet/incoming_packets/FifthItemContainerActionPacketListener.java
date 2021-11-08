package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.game.content.packet_actions.interactions.container.FifthContainerAction;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

/**
 * @author PVE
 * @Since augustus 26, 2020
 */
public class FifthItemContainerActionPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {

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

        int interfaceId = packet.readInt();
        int slot = packet.readLEShort();
        int id = packet.readLEShort();
        if (GameServer.properties().debugMode && player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.debugMessage(String.format("fifth action, container: %d slot: %d id %d", interfaceId, slot, id));
        }

        FifthContainerAction.fifthAction(player, interfaceId, slot, id);
    }
}

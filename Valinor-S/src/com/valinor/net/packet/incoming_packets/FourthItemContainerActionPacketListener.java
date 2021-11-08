package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.game.content.packet_actions.interactions.container.FourthContainerAction;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

/**
 * @author PVE
 * @Since augustus 26, 2020
 */
public class FourthItemContainerActionPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int slot = packet.readUnsignedShortA();
        int interfaceId = packet.readInt();
        int id = packet.readUnsignedShortA();

        if (player == null || player.dead()) {
            return;
        }

        player.afkTimer.reset();

        if (!player.getBankPin().hasEnteredPin() && GameServer.properties().requireBankPinOnLogin) {
            player.getBankPin().openIfNot();
            return;
        }

        if(player.askForAccountPin()) {
            player.sendAccountPinMessage();
            return;
        }

        if (GameServer.properties().debugMode && player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.debugMessage(String.format("fourth action, container: %d slot: %d id %d", interfaceId, slot, id));
        }

        FourthContainerAction.fourthAction(player, interfaceId, slot, id);
    }
}

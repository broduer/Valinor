package com.valinor.net.packet.incoming_packets;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

public class WidgetSlot implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int slot = packet.readUnsignedByte();

        if (player.getTrading().getInteract() != null) {
            player.getTrading().getInteract().getPacketSender().sendModified(slot);
        }
    }
}

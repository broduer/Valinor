package com.valinor.net.packet.incoming_packets;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

/**
 * This packet listener is called when a player's region has been loaded.
 * 
 * @author relex lawl
 */
public class FinalizedMapRegionChangePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {

        if (player == null || player.dead()) {
            return;
        }

    }
}

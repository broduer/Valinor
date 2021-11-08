package com.valinor.net.packet.incoming_packets;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

/**
 * @author PVE
 * @Since augustus 27, 2020
 */
public class NpcActionThreePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int index = packet.readShort();
        Npc npc = World.getWorld().getNpcs().get(index);

        NpcActionOnePacketListener.handleNpcClicks(player, npc, 3);
    }
}

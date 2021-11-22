package com.valinor.net.packet.incoming_packets;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;
import com.valinor.net.packet.interaction.PacketInteractionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextClickPacketListener implements PacketListener {

    private static final Logger logger = LogManager.getLogger(TextClickPacketListener.class);

    @Override
    public void handleMessage(Player player, Packet packet) {
        int textFrame = packet.readInt();
        int action = packet.readByte();

        if (player == null || player.dead()) {
            return;
        }
        player.afkTimer.reset();

        player.debugMessage(String.format("frame=%d action=%d", textFrame, action));

        if(player.getPresetManager().handleButton(textFrame, action)) {
            return;
        }

        if (player.getBank().buttonAction(textFrame)) {
            return;
        }

        if (PacketInteractionManager.checkButtonInteraction(player, textFrame)) {
            return;
        }
    }
}

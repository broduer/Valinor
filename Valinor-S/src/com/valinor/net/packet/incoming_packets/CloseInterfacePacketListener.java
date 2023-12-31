package com.valinor.net.packet.incoming_packets;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

public class CloseInterfacePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.dead()) {
            return;
        }
        player.afkTimer.reset();
        player.getInterfaceManager().close();
        //Because the play button is calling this packet due to the Client.
        //We have to hardcode the starter here after all actions are completed.
        //This is the entry point of new players.

        boolean newAccount = player.getAttribOr(AttributeKey.NEW_ACCOUNT, false);
        if (newAccount) {
            //The player can select their appearance here.
            player.getInterfaceManager().open(3559);
        }
    }
}

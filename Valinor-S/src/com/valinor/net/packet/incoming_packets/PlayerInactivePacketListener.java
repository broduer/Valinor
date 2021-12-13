package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

import java.util.concurrent.TimeUnit;

public class PlayerInactivePacketListener implements PacketListener {

    private static final boolean enabled = true;

    @Override
    public void handleMessage(Player player, Packet packet) {
        // ok afk timer now applies to <10min playtime accs, lets make up a verify on login
        if (player.afkTimer.elapsed(GameServer.properties().afkLogoutMinutes, TimeUnit.MINUTES) && !CombatFactory.inCombat(player) && (enabled || player.<Integer>getAttribOr(AttributeKey.GAME_TIME, 0) < 1000)) {
            player.requestLogout();
        }
    }
}

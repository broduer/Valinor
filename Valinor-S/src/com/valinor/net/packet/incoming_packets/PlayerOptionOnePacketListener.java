package com.valinor.net.packet.incoming_packets;

import com.valinor.game.content.seasonal_events.winter.WinterEvent;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.route.routes.TargetRoute;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

import java.lang.ref.WeakReference;

import static com.valinor.util.ItemIdentifiers.SNOWBALL;

/**
 * @author PVE
 * @Since augustus 26, 2020
 */
public class PlayerOptionOnePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        player.stopActions(false);
        int index = packet.readShort() & 0xFFFF;
        if (index > World.getWorld().getPlayers().capacity())
            return;
        Player other = World.getWorld().getPlayers().get(index);
        if (other == null) {
            player.message("Unable to find player.");
        } else {
            player.debugMessage(String.format("Click 1 pid=%d", other.getIndex()));

            if (player.locked() || player.dead()) {
                return;
            }

            //Face the player that we will be interacting with.
            player.setEntityInteraction(other);
            player.putAttrib(AttributeKey.INTERACTION_OPTION, 1);
            player.putAttrib(AttributeKey.TARGET, new WeakReference<Mob>(other));

            if(player.getEquipment().contains(SNOWBALL)) {
                WinterEvent.throwSnow(player, other);
                return;
            }

            TargetRoute.set(player, other, () -> {
                player.runFn(1, () -> {
                    player.face(other.tile());
                    player.setEntityInteraction(null);
                });
                if (player.getMovementQueue().isFollowing(other)) {
                    player.getMovementQueue().resetFollowing();
                    player.setEntityInteraction(null);
                }
                if (player.getController() != null) {
                    player.getController().onPlayerRightClick(player, other, 1);
                }
            });
        }
    }
}

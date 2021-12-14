package com.valinor.game.content.areas.dungeons.gnome_stronghold;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ObjectIdentifiers.*;

public class GnomeEntrance extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (option == 1) {
            if (obj.getId() == CAVE_26709) {
                player.lock();
                player.animate(2796);
                Chain.bound(null).runFn(2, () -> {
                    player.resetAnimation();
                    player.teleport(2429, 9824, 0);
                    player.unlock();
                });
                return true;
            }
            if (obj.getId() == TUNNEL_27257 || obj.getId() == TUNNEL_27258) {
                player.lock();
                player.animate(2796);
                Chain.bound(null).runFn(2, () -> {
                    player.resetAnimation();
                    player.teleport(2430, 3424, 0);
                    player.unlock();
                });
                return true;
            }
        }
        return false;
    }
}

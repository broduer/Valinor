package com.valinor.game.content.areas.dungeons.godwars;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;

public class BossRoomDoors extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (option == 1) {
            // Zamorak
            if (obj.getId() == 26505) {
                if (player.tile().y > 5332) {
                    player.teleport(2925, 5331, 2);
                } else if (player.tile().y == 5331) {
                    player.teleport(2925, 5333, 2);
                }
                return true;
            }

            // Bandos
            if (obj.getId() == 26503) {
                if (player.tile().x < 2863) {
                    player.teleport(2864, 5354, 2);
                } else if (player.tile().x == 2864) {
                    player.teleport(2862, 5354, 2);
                }
                return true;
            }

            // Saradomin
            if (obj.getId() == 26504) {
                if (player.tile().x >= 2909) {
                    player.teleport(2907, 5265, 0);
                } else if (player.tile().x == 2907) {
                    player.teleport(2909, 5265, 0);
                }
                return true;
            }

            // Armadyl
            if (obj.getId() == 26502) {
                if (player.tile().y <= 5294) {
                    player.teleport(2839, 5296, 2);
                } else if (player.tile().y == 5296) {
                    player.teleport(2839, 5294, 2);
                }
                return true;
            }
        }
        return false;
    }
}

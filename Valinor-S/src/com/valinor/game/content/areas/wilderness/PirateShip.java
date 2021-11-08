package com.valinor.game.content.areas.wilderness;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.PacketInteraction;

import static com.valinor.util.ObjectIdentifiers.SHIPS_LADDER;
import static com.valinor.util.ObjectIdentifiers.SHIPS_LADDER_246;

public class PirateShip extends PacketInteraction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (obj.getId() == SHIPS_LADDER) {
            //ship ladder z1 to z2
            if ((obj.tile().x == 3019 && obj.tile().y == 3959) || (obj.tile().x == 3017 && obj.tile().y == 3959)) {
                player.teleport(player.tile().transform(0, 2, 1));
            }
            return true;
        }
        if (obj.getId() == SHIPS_LADDER_246) {
            //down to deck z1
            if ((obj.tile().x == 3019 && obj.tile().y == 3959) || (obj.tile().x == 3017 && obj.tile().y == 3959)) {
                player.teleport(player.tile().transform(0, -2, -1));
            }
            return true;
        }
        return false;
    }
}

package com.valinor.game.content.areas.lumbridge;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.PacketInteraction;

import static com.valinor.util.ObjectIdentifiers.DARK_HOLE;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * april 19, 2020
 */
public class Lumbridge extends PacketInteraction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(option == 1) {
            if(obj.getId() == DARK_HOLE) {
                player.teleport(new Tile(3184, 9549, 0));
                return true;
            }
        }
        return false;
    }
}

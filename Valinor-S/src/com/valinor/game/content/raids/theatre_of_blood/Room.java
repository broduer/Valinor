package com.valinor.game.content.raids.theatre_of_blood;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 07, 2022
 */
public class Room extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        return false;
    }
}

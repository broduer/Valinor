package com.valinor.game.content.areas.karamja;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * april 20, 2020
 */
public class Karamja extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(option == 1) {
            if(obj.getId() == CLIMBING_ROPE_18969) {
                player.message("You climb up the hanging rope...");
                Chain.bound(null).runFn(2, () -> {
                    player.teleport(new Tile(2856, 3167));
                    player.message("You appear on the volcano rim.");
                });
                return true;
            }

            if(obj.getId() == ROCKS_11441) {
                player.teleport(new Tile(2856, 9567));
                player.message("You climb down through the pot hole.");
                return true;
            }

            if(obj.getId() == CAVE_ENTRANCE_11835) {
                player.teleport(new Tile(2480, 5175));
                return true;
            }
        }
        return false;
    }
}

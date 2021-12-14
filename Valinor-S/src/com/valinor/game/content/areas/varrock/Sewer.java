package com.valinor.game.content.areas.varrock;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.MOSSY_KEY;
import static com.valinor.util.ObjectIdentifiers.GATE_32534;
import static com.valinor.util.ObjectIdentifiers.ROCK_PILE_32535;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 29, 2021
 */
public class Sewer extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(object.getId() == GATE_32534) {
            player.faceObj(object);
            player.optionsTitled("Go to Bryphyta's woods?", "Yes", "No", () -> {
                if(!player.inventory().contains(MOSSY_KEY)) {
                    player.message("You need a mossy key to enter this door.");
                    return;
                }
                player.getInventory().remove(MOSSY_KEY, 1);
                player.runFn(1, () -> player.getBryophytaInstance().enterInstance(player));
            });
            return true;
        }
        if(object.getId() == ROCK_PILE_32535) {
            player.optionsTitled("Return to Varrock sewers?", "Yes", "No", () -> {
                player.faceObj(object);
                player.runFn(1, () -> player.animate(839)).then(3, () -> player.teleport(3174, 9897, 0));
            });
            return true;
        }
        return false;
    }
}

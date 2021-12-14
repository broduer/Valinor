package com.valinor.game.content.areas.varrock;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.ObjectIdentifiers;

/**
 * @author Patrick van Elderen | April, 14, 2021, 19:17
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class Crate extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(option == 1) {
            if (obj.getId() == ObjectIdentifiers.CRATE_20885) {
                World.getWorld().shop(28).open(player);
                return true;
            }
        }
        return false;
    }
}

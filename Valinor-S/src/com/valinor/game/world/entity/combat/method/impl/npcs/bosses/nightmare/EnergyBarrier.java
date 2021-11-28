package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.PacketInteraction;

import static com.valinor.util.ObjectIdentifiers.ENERGY_BARRIER_37730;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 27, 2021
 */
public class EnergyBarrier extends PacketInteraction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(object.getId() == ENERGY_BARRIER_37730) {
            player.optionsTitled("Would you like to exit?", "Yes.", "No.", () -> {
                player.teleport(3808, 9749, 1);
            });
            return true;
        }
        return false;
    }
}

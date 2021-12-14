package com.valinor.game.content.areas.burthope;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 02, 2021
 */
public class SkillingIsland extends Interaction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if(npc.id() == KAQEMEEX) {
                World.getWorld().shop(40).open(player);
            }
            if(npc.id() == MASTER_CRAFTER) {
                World.getWorld().shop(50).open(player);
            }
            if(npc.id() == HICKTON) {
                World.getWorld().shop(51).open(player);
            }
        }
        if(option == 2) {
            if(npc.id() == HICKTON) {
                World.getWorld().shop(51).open(player);
            }
        }
        return false;
    }
}

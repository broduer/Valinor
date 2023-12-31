package com.valinor.game.content.areas.dungeons.waterfall;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;

public class WaterfallEntrance extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(option == 1) {
            if(obj.getId() == 2010) {//TODO get id
                player.teleport(new Tile(2575, 9861));
                return true;
            }
        }
        return false;
    }
}

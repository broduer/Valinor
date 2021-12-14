package com.valinor.game.content.areas.dungeons.fremennik_slayer_dungeon;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ObjectIdentifiers.CAVE_ENTRANCE_2123;

public class SlayercaveEntrance extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(option == 1) {
            if(obj.getId() == CAVE_ENTRANCE_2123) {
                Chain.bound(null).runFn(1, () -> player.teleport(new Tile(2808, 10002)));
                return true;
            }
        }
        return false;
    }

}

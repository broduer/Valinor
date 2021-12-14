package com.valinor.game.content.areas.dungeons.asgarnian_ice_dungeon;

import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.TickAndStop;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ObjectIdentifiers.*;

public class IceObjects extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(option == 1) {
            if(obj.getId() == TRAPDOOR_1738) {
                // Trapdoor down
                if (obj.tile().equals(3008, 3150)) {
                    TaskManager.submit(new TickAndStop(1) {
                        @Override
                        public void executeAndStop() {
                            player.teleport(new Tile(3007, 9550));
                        }
                    });
                }
                return true;
            }
            if(obj.getId() == ICY_CAVERN) {
                TaskManager.submit(new TickAndStop(1) {
                    @Override
                    public void executeAndStop() {
                        player.teleport(new Tile(player.tile().x, 9562));
                    }
                });
                return true;
            }
            if(obj.getId() == ICY_CAVERN_10596) {
                TaskManager.submit(new TickAndStop(1) {
                    @Override
                    public void executeAndStop() {
                        player.teleport(new Tile(player.tile().x, 9555));
                    }
                });
                return true;
            }
        }
        return false;
    }
}

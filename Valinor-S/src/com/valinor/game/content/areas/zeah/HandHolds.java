package com.valinor.game.content.areas.zeah;

import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.ForceMovementTask;
import com.valinor.game.task.impl.TickAndStop;
import com.valinor.game.world.entity.mob.Direction;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ObjectIdentifiers.HANDHOLDS;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * april 20, 2020
 */
public class HandHolds extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(option == 1) {
            Tile west_handholds_down = new Tile(1459, 3690);
            Tile wast_handholds_up = new Tile(1455, 3690);

            if (obj.getId() == HANDHOLDS) {
                if (obj.tile().equals(west_handholds_down)) {
                    if (!player.tile().equals(obj.tile().transform(1, 0, 0))) {
                        player.getMovementQueue().walkTo(obj.tile().transform(1, 0, 0));
                    }
                    player.face(new Tile(1462, 3690));
                    TaskManager.submit(new TickAndStop(1) {
                        @Override
                        public void executeAndStop() {
                            player.animate(1148, 20);
                            TaskManager.submit(new ForceMovementTask(player, 1, new ForceMovement(player.tile().clone(), new Tile(-6, 0), 25, 135, Direction.EAST.toInteger())));
                        }
                    });
                    TaskManager.submit(new TickAndStop(5) {
                        @Override
                        public void executeAndStop() {
                            player.teleport(west_handholds_down.x - 4, west_handholds_down.y);
                        }
                    });
                    return true;
                } else if (obj.tile().equals(wast_handholds_up)) {
                    if (!player.tile().equals(obj.tile().transform(-1, 0, 0))) {
                        player.getMovementQueue().walkTo(obj.tile().transform(-1, 0, 0));
                    }
                    player.face(new Tile(1462, 3690));

                    TaskManager.submit(new TickAndStop(1) {
                        @Override
                        public void executeAndStop() {
                            player.animate(1148, 15);
                            TaskManager.submit(new ForceMovementTask(player, 1, new ForceMovement(player.tile().clone(), new Tile(6, 0), 25, 135, Direction.EAST.toInteger())));
                        }
                    });
                    TaskManager.submit(new TickAndStop(5) {
                        @Override
                        public void executeAndStop() {
                            player.teleport(wast_handholds_up.x + 4, wast_handholds_up.y);
                        }
                    });
                    return true;
                }
            }
        }
        return false;
    }
}

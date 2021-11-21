package com.valinor.game.content.skill.impl.hunter;

import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.ForceMovementTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.Direction;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.StepType;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 21, 2021
 */
public class PuroPuro extends PacketInteraction {

    private final String[] PUSH_THROUGH_MESSAGE = {
        "You use your strength to push through the wheat in the most efficient fashion.",
        "You push through the wheat. It's hard work though.",
        "You use your strength to push through the wheat.",
    };

    private void moveThroughWall(Player player, GameObject wall) {
        int playerX = player.getAbsX();
        int playerY = player.getAbsY();
        int wallX = wall.getX();
        int wallY = wall.getY();

        if (playerX == wallX && playerY < wallY)
            TaskManager.submit(new ForceMovementTask(player, 2, new ForceMovement(player.tile().clone(), new Tile(0, 2), 0, 210, Direction.NORTH.toInteger())));
        else if (playerX == wallX && playerY > wallY)
            TaskManager.submit(new ForceMovementTask(player, 2, new ForceMovement(player.tile().clone(), new Tile(0, -2), 0, 210, Direction.SOUTH.toInteger())));
        else if (playerY == wallY && playerX < wallX)
            TaskManager.submit(new ForceMovementTask(player, 2, new ForceMovement(player.tile().clone(), new Tile(2, 0), 0, 210, Direction.EAST.toInteger())));
        else if (playerY == wallY && playerX > wallX)
            TaskManager.submit(new ForceMovementTask(player, 2, new ForceMovement(player.tile().clone(), new Tile(-2, 0), 0, 210, Direction.WEST.toInteger())));

        player.skills().addXp(Skills.STRENGTH, World.getWorld().random(2,4), false);
    }

    private void enter(Player player) {
        Chain.bound(null).runFn(1, () -> {
            player.lock();
            player.stepAbs(2427, 4446, StepType.FORCE_WALK);
        }).then(1, () -> {
            player.animate(6601);
            player.graphic(1118);
        }).then(9, () -> {
            player.teleport(2591, 4319);
            player.unlock();
        });
    }

    private void exit(Player player, GameObject portal) {
        Chain.bound(null).runFn(1, () -> {
            player.lock();
            player.stepAbs(portal.getX(), portal.getY(), StepType.FORCE_WALK);
        }).then(2, () -> {
            player.animate(6601);
            player.graphic(1118);
        }).then(9, () -> {
            player.teleport(2426, 4445);
            player.unlock();
        });
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if(object.getId() == CENTRE_OF_CROP_CIRCLE_24991) {
                enter(player);
                return true;
            }
            if(object.getId() == PORTAL_25014) {
                exit(player, object);
                return true;
            }
        }
        if(option == 4) {
            /*
             * Magical wheat
             */
            int[] magicalWheat = {MAGICAL_WHEAT_25029, MAGICAL_WHEAT, MAGICAL_WHEAT_25019, MAGICAL_WHEAT_25021};
            for (int id : magicalWheat) {
                if(object.getId() == id) {
                    Chain.bound(null).runFn(1, () -> {
                        player.lock();
                        moveThroughWall(player, object);
                        player.animate(6594);
                        player.message(Utils.randomElement(PUSH_THROUGH_MESSAGE));
                    }).then(6, player::unlock);
                    return true;
                }
            }
        }
        return false;
    }

}

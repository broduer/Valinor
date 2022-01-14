package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.ForceMovementTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex.Nex;
import com.valinor.game.world.entity.mob.Direction;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.DumbRoute;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex.NexCombat.NO_ESCAPE_TELEPORTS;
import static com.valinor.util.NpcIdentifiers.NEX;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 14, 2022
 */
public class PForceMoveCmd implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        Npc nex = new Nex(NEX, new Tile(2924, 5202, 0)).spawn();
        Mob target = player;
        if(target.isPlayer()) {

            int vecX = (nex.getAbsX() - Utils.getClosestX(nex, target.tile()));
            int vecY = (nex.getAbsY() - Utils.getClosestY(nex, target.tile()));
            int endX = nex.getAbsX();
            int endY = nex.getAbsY();
            for (int i = 0; i < 4; i++) {
                if (DumbRoute.getDirection(endX, endY, nex.getZ(), target.getSize(), endX + vecX, endY + vecY) != null) { // we can take this step!
                    endX += vecX;
                    endY += vecY;
                } else
                    break; // cant take the step, stop here
            }
            Direction dir;
            if (vecX == -1)
                dir = Direction.EAST;
            else if (vecX == 1)
                dir = Direction.WEST;
            else if (vecY == -1)
                dir = Direction.NORTH;
            else
                dir = Direction.SOUTH;

            int finalEndX = endX;
            int finalEndY = endY;
            Chain.bound(null).runFn(1, () -> {
                int diffX = finalEndX - player.getAbsX();
                int diffY = finalEndY - player.getAbsY();
                //TaskManager.submit(new ForceMovementTask(player, 3, new ForceMovement(player.tile().clone(), new Tile(diffX, diffY), 10, 60, dir.toInteger())));
                final int idx = 1;//Utils.random(NO_ESCAPE_TELEPORTS.length);
                final Tile selectedTile = NO_ESCAPE_TELEPORTS[idx].clone();
                final Tile center = new Tile(2924, 5202, 0);
                Tile dif = new Tile( center.x-selectedTile.x, center.y - selectedTile.y ); // idk if u just changed that mb I did

                TaskManager.submit(new ForceMovementTask(player, 3, new ForceMovement(selectedTile, dif, 50, 60, 1)));
                player.unlock();
                nex.remove();
            });
        }
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isDeveloperOrGreater(player);
    }
}

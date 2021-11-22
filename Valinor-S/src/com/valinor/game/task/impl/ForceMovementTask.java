package com.valinor.game.task.impl;

import com.valinor.game.task.Task;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;

/**
 * A {@link Task} implementation that handles forced movement.
 * An example of forced movement is the Wilderness ditch.
 * @author Professor Oak
 */
public class ForceMovementTask extends Task {

    private final Player player;
    private final Tile end;
    private final Tile start;

    public ForceMovementTask(Player player, int delay, ForceMovement forceM) {
        super("ForceMovementTask", delay, player, (delay == 0));
        this.player = player;
        this.start = forceM.getStart().copy();
        this.end = forceM.getEnd().copy();

        //Reset movement queue
        player.getMovementQueue().clear();

        //Playerupdating
        player.setForceMovement(forceM);
    }

    @Override
    public void execute() {
        int x = start.getX() + end.getX();
        int y = start.getY() + end.getY();
        player.teleport(new Tile(x, y, player.tile().getLevel()));
        //System.out.println("Force movement " + new Tile(x, y, player.tile().getLevel()).toString());
        player.setForceMovement(null);
        stop();
    }
}

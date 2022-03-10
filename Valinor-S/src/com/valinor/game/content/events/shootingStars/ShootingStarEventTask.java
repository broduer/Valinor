package com.valinor.game.content.events.shootingStars;

import com.valinor.game.task.Task;

import static com.valinor.game.content.events.shootingStars.ShootingStars.EVENT_INTERVAL;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 27, 2021
 */
public class ShootingStarEventTask extends Task {

    public ShootingStarEventTask() {
        super("ShootingStarEventTask", EVENT_INTERVAL,true);
    }

    @Override
    public void execute() {
        ShootingStars shootingStars = ShootingStars.getINSTANCE();
        shootingStars.startEvent();
    }
}

package com.valinor.game.content.areas.wilderness.content.bounty_hunter.bounty_tasks;

import com.valinor.game.task.Task;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.concurrent.TimeUnit;

import static com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunterConstants.BOUNTY_HUNTER_TASK_TIME_LEFT;
import static com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunterConstants.BOUNTY_TASK_TIME;

/**
 * The task which handles giving out bounty hunter tasks to players in the wilderness.
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public class BountyTaskEvent extends Task {

    private final Player player;
    boolean messageSent;

    public BountyTaskEvent(Player player) {
        super("BountyTaskEvent", BOUNTY_TASK_TIME, true); // 20 minutes to complete
        this.player = player;
    }

    @Override
    public void execute() {
        BountyHunterTask.setBountyTask(player);
    }

    @Override
    public void onTick() {
        // Stop the task if the player is offline.
        if (!player.isRegistered()) {
            this.stop();
            return;
        }

        // Timer ran out stop task
        if (!player.hasBountyTask()) {
            player.putAttrib(AttributeKey.BOUNTY_TASK_TIMER, -1);
            this.stop();
            return;
        }

        // Decrease the bounty task timer by 1 tick each cycle
        int timer = (Integer) player.getAttribOr(AttributeKey.BOUNTY_TASK_TIMER, -1) - 1;
        player.putAttrib(AttributeKey.BOUNTY_TASK_TIMER, timer);

        //System.out.println("Current BH task timer: "+timer);

        //First get time as seconds
        int time = Utils.getSeconds(timer);

        //System.out.println("BH task time: "+time);

        //Convert to milliseconds
        int timeInMilliseconds = time * 1000;

        //Get minutes and seconds
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds);
        timeInMilliseconds -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds);

        if (!messageSent && time == 60) {
            messageSent = true;//Because it takes 2 ticks to reach a second we need to use a boolean. Otherwise message is sent out twice.
            player.message("Your <col="+ Color.MEDRED.getColorValue()+">Bounty Task</col> will expire in a minute!");
        }

        //Set the string as 20:00 example
        String timeAsString = minutes+":"+seconds;

        //Refresh frame each cycle
        player.getPacketSender().sendString(BOUNTY_HUNTER_TASK_TIME_LEFT, timeAsString);

        //System.out.println("Still ticking... time left: "+time);

        if(time == 0) {
            messageSent = false;
            this.stop();
        }
    }

    @Override
    public void onStop() {
        BountyHunterTask.resetBountyTask(player, false);
    }

}

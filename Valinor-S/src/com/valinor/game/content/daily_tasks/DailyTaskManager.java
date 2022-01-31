package com.valinor.game.content.daily_tasks;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.valinor.game.content.daily_tasks.DailyTaskUtility.*;
import static com.valinor.game.world.entity.AttributeKey.*;

/**
 * @author Patrick van Elderen | June, 15, 2021, 16:15
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class DailyTaskManager extends Interaction {

    public static String timeLeft() {
        LocalDate now = LocalDate.now();
        LocalDate midnight = now.plusDays(1);

        ZonedDateTime aDateTime = ZonedDateTime.now();
        ZonedDateTime otherDateTime = ZonedDateTime.of(midnight.getYear(), midnight.getMonth().getValue(), midnight.getDayOfMonth(), 0, 0, 0, 0, ZoneId.systemDefault());

        long diffInSeconds = ChronoUnit.SECONDS.between(aDateTime, otherDateTime);
        return Utils.convertSecondsToDurationShort(diffInSeconds);
    }

    public static void increase(DailyTasks dailyTask, Player player) {
        //Can only increase when the task isn't already finished.
        if (dailyTask.canIncrease(player)) {
            var completionAmount = dailyTask.completionAmount;
            var increase = player.<Integer>getAttribOr(dailyTask.key, 0) + 1;
            player.putAttrib(dailyTask.key, increase);
            player.message(Color.PURPLE.wrap("Daily task; " + dailyTask.taskName + " Completed: (" + increase + "/" + completionAmount + ")"));

            //We have completed the task
            if (increase == dailyTask.completionAmount) {
                player.putAttrib(dailyTask.completed, true);
                player.message(Color.PURPLE.wrap(dailyTask.taskName + " completed, you may now claim its rewards!"));
            }
        }
    }

    @Override
    public void onLogin(Player player) {
        if (player.<Integer>getAttribOr(LAST_DAILY_RESET, -1) != ZonedDateTime.now().getDayOfMonth()) {
            player.putAttrib(LAST_DAILY_RESET, ZonedDateTime.now().getDayOfMonth());
            for (DailyTasks task : DailyTasks.values()) {
                player.clearAttrib(task.key);
                player.clearAttrib(task.completed);
                player.clearAttrib(task.rewardClaimed);
            }
            player.message(Color.PURPLE.wrap("Your daily tasks have been reset."));
        }
    }

    public static void claimReward(DailyTasks dailyTask, Player player) {
        //Got a be inside the interface to claim
        if (!player.getInterfaceManager().isInterfaceOpen(DAILY_TASK_MANAGER_INTERFACE)) {
            return;
        }

        //Task isn't completed can't claim rewards
        if (!player.<Boolean>getAttribOr(dailyTask.completed, false)) {
            player.message(Color.RED.wrap("You have not completed this daily task yet."));
            return;
        }

        //Reward already claimed
        boolean claimed = player.getAttribOr(dailyTask.rewardClaimed, false);
        if (claimed) {
            player.message("<col=ca0d0d>You've already claimed this daily task. You can complete this task again tomorrow.");
            return;
        }

        var in_tournament = player.inActiveTournament() || player.isInTournamentLobby();
        if (in_tournament) {
            player.message("<col=ca0d0d>You can't claim your reward here.");
            return;
        }

        player.putAttrib(dailyTask.rewardClaimed, true);
        player.inventory().addOrBank(dailyTask.rewards);
        player.message("<col=ca0d0d>You have claimed the reward from task: " + dailyTask.taskName + ".");
    }

    public static void sendInterfaceForTask(final Player player, DailyTasks task) {
        final var completed = player.<Integer>getAttribOr(task.key, 0);
        final var progress = (int) (completed * 100 / (double) task.completionAmount);
        player.getPacketSender().sendString(TITLE_TEXT_ID, "<col=ff9040>" + task.taskName);
        player.getPacketSender().sendString(PROGRESS_BAR_TEXT_ID, progress + "% (" + Utils.format(completed) + " / " + Utils.format(task.completionAmount)+")");
        player.getPacketSender().sendProgressBar(PROGRESS_BAR_CHILD, progress);
        player.getPacketSender().sendString(TIME_FRAME_TEXT_ID, "Activity (Expires: <col=ffff00>"+timeLeft()+")");
        player.getPacketSender().sendString(DESCRIPTION_TEXT_ID, task.taskDescription);
        player.getPacketSender().sendString(COMBAT_TEXT_ID, task.combatRequirement);
        player.getPacketSender().sendString(LOCATION_TEXT_ID, task.location);
        player.getPacketSender().sendItemOnInterface(REWARD_CONTAINER, task.rewards);
        player.getPacketSender().sendString(REWARDS_TEXT, "Fill up the bar for rewards!");
        player.putAttrib(DAILY_TASK_SELECTED, task);
    }

    public static void open(Player player, TaskCategory category) {
        final List<DailyTasks> list = DailyTasks.asList(category);
        var size = list.size();

        for (int index = 0; index < 20; index++) {
            player.getPacketSender().sendString(LIST_START_ID + index, "");
        }

        for (int index = 0; index < size; index++) {
            final DailyTasks task = list.get(index);
            player.getPacketSender().sendString(LIST_START_ID + index, "" + task.taskName);
        }

        player.getPacketSender().sendScrollbarHeight(41430,0);
    }

    public static void openEasyList(Player player) {
        open(player, TaskCategory.EASY);
        sendInterfaceForTask(player, DailyTasks.EXPERIMENTS);
        player.putAttrib(DAILY_TASK_CATEGORY, TaskCategory.EASY);
        player.getPacketSender().sendConfig(1163, 1);
        player.getPacketSender().sendConfig(1164, 0);
        player.getPacketSender().sendConfig(1165, 0);
        player.getPacketSender().setClickedText(LIST_START_ID, true);
        player.getInterfaceManager().open(DAILY_TASK_MANAGER_INTERFACE);
    }

}

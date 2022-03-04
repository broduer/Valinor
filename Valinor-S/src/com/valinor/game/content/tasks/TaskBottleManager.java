package com.valinor.game.content.tasks;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.daily_tasks.TaskCategory;
import com.valinor.game.content.tasks.rewards.TaskReward;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.List;

import static com.valinor.game.world.entity.AttributeKey.*;
import static com.valinor.util.CustomItemIdentifiers.*;

/**
 * @author Patrick van Elderen | April, 08, 2021, 21:55
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class TaskBottleManager {

    private final Player player;

    public TaskBottleManager(Player player) {
        this.player = player;
    }

    public void increase(BottleTasks taskToIncrease) {
        increase(taskToIncrease,1);
    }

    /**
     * Activates, increases the task and also handles completion.
     *
     * @param taskToIncrease The task
     */
    public void increase(BottleTasks taskToIncrease, int increaseBy) {
        // Safety checks
        if (taskToIncrease == null) {
            return;
        }

        BottleTasks task = player.getAttribOr(AttributeKey.BOTTLE_TASK, null);
        if(task == null) return;

        //Can't increase during tourneys
        if(player.inActiveTournament() || player.isInTournamentLobby()) {
            return;
        }

        if(task == taskToIncrease) {

            int before = player.<Integer>getAttribOr(BOTTLE_TASK_AMOUNT, 0);

            var current = player.<Integer>getAttribOr(BOTTLE_TASK_AMOUNT, 0) + increaseBy;
            var completeAmount = player.<Integer>getAttribOr(TASK_COMPLETE_AMOUNT, 0);
            if (current >= completeAmount)
                current = completeAmount;

            player.putAttrib(BOTTLE_TASK_AMOUNT, current);

            int after = player.<Integer>getAttribOr(BOTTLE_TASK_AMOUNT, 0);

            if (after != before) {
                //Task completed
                if (after >= completeAmount) {
                    player.message("You've completed your task, you can now claim your reward!");
                    int tasks_completed = (Integer) player.getAttribOr(TASKS_COMPLETED, 0) + 1;
                    player.putAttrib(TASKS_COMPLETED, tasks_completed);
                    player.message("You have now completed <col=" + Color.BLUE.getColorValue() + ">" + tasks_completed + "</col> tasks.");
                    player.putAttrib(CAN_CLAIM_TASK_REWARD, true);

                    if(task.getTaskCategory() == TaskCategory.SKILLING_TASK) {
                        DailyTaskManager.increase(DailyTasks.SKILLING_TASKS, player);
                    } else if(task.getTaskCategory() == TaskCategory.PVMING_TASK) {
                        DailyTaskManager.increase(DailyTasks.PVM_TASKS, player);
                    }
                }
            }
        }
    }

    public void resetTask() {
        player.putAttrib(BOTTLE_TASK, null);
        player.putAttrib(BOTTLE_TASK_AMOUNT,0);
        player.putAttrib(TASK_COMPLETE_AMOUNT,0);
        player.message(Color.RED.wrap("Your task has been reset."));
    }

    public boolean hasTask() {
        BottleTasks task = player.getAttribOr(BOTTLE_TASK, null);
        return task != null;
    }

    public void giveTask(TaskCategory taskCategory) {
        //Safety, check if a player already has a task.
        if (hasTask()) {
            return;
        }

        //Randomize a task
        BottleTasks randomTask = BottleTasks.randomTask(taskCategory);

        if(randomTask != null) {
            //Save the enum type
            player.putAttrib(BOTTLE_TASK, randomTask);
            player.putAttrib(BOTTLE_TASK_AMOUNT, 0);
            player.putAttrib(TASK_COMPLETE_AMOUNT, randomTask.getTaskAmount());

            String plural = taskCategory == TaskCategory.SKILLING_TASK ? "(skilling)" : taskCategory == TaskCategory.PVMING_TASK ? "(pvming)" : "(pvP)";
            player.message("You've opened the task bottle "+plural+" and got yourself a task...");
            player.message(randomTask.task().replaceAll("<br>"," "));
        }
    }

    public void open() {
        BottleTasks task = player.getAttribOr(BOTTLE_TASK, null);

        player.getInterfaceManager().open(54731);
        player.getPacketSender().sendString(54733, "Task Manager");

        var completed = player.<Integer>getAttribOr(BOTTLE_TASK_AMOUNT, 0);
        var completionAmount = player.<Integer>getAttribOr(TASK_COMPLETE_AMOUNT, 0);
        var progress = (int) (completed * 100 / (double) completionAmount);
        player.getPacketSender().sendString(54762, "(" + progress + "%) (" + completed + "/" + completionAmount + ")");
        player.getPacketSender().sendProgressBar(54760, progress);
        List<Item> rewards = TaskReward.getPossibleRewards();
        player.getPacketSender().sendScrollbarHeight(54758, rewards.size() * 11);
        player.getPacketSender().sendItemOnInterface(54759, rewards);

        for (int i = 54738; i < 54758; i++) {
            player.getPacketSender().sendString(i, "");//Clear old
        }

        StringBuilder stringBuilder = new StringBuilder();

        if (task != null && task.getTaskRequirements() != null) {
            stringBuilder.append("Requirement:<br>");
            for (String s : task.getTaskRequirements()) {
                if(s.isEmpty())
                    s = "- None";
                stringBuilder.append(s).append("<br>");
            }

            stringBuilder.append("<br><br>Task(s)").append("<br>- ").append(task.task());
        }

        player.getPacketSender().sendString(54738, stringBuilder.toString());
    }

    public void claimReward() {
        boolean canClaimReward = player.getAttribOr(CAN_CLAIM_TASK_REWARD, false);
        if (!canClaimReward) {
            final int completed = player.getAttribOr(BOTTLE_TASK_AMOUNT, 0);
            final int completeAmt = player.getAttribOr(TASK_COMPLETE_AMOUNT,0);
            player.message("Your task isn't finished yet, you still have to complete (" + Utils.format(completed) + "/" + completeAmt + ").");
            return;
        }

        BottleTasks bottleTask = player.getAttribOr(AttributeKey.BOTTLE_TASK, null);

        player.putAttrib(CAN_CLAIM_TASK_REWARD,false);
        player.removeAll(bottleTask.getTaskCategory() == TaskCategory.SKILLING_TASK ? new Item(SKILLING_SCROLL) : bottleTask.getTaskCategory() == TaskCategory.PVMING_TASK ? new Item(PVMING_SCROLL) : new Item(PVP_SCROLL));
        player.inventory().addOrDrop(new Item(TASK_BOTTLE_CASKET));
        AchievementsManager.activate(player, Achievements.TASK_MASTER_I, 1);
        AchievementsManager.activate(player, Achievements.TASK_MASTER_II, 1);
        AchievementsManager.activate(player, Achievements.TASK_MASTER_III, 1);
        player.getInterfaceManager().close();

        //Reset old task
        player.putAttrib(BOTTLE_TASK_AMOUNT, 0);
        player.putAttrib(TASK_COMPLETE_AMOUNT, 0);
        player.putAttrib(BOTTLE_TASK, null);
    }
}

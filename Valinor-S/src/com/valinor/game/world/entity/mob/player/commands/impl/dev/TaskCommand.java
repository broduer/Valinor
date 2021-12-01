package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.content.daily_tasks.TaskCategory;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen | November, 15, 2020, 15:36
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class TaskCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (parts.length != 2) {
            player.message("usage ::task pvptask/skillingtask/pvmtask/reset");
            return;
        }
        String cmd = parts[1];
        if(cmd.equalsIgnoreCase("skillingtask")) {
            player.getTaskBottleManager().giveTask(TaskCategory.SKILLING_TASK);
        } else if(cmd.equalsIgnoreCase("pvmtask")) {
            player.getTaskBottleManager().giveTask(TaskCategory.PVMING_TASK);
        } else if(cmd.equalsIgnoreCase("reset")) {
            player.getTaskBottleManager().resetTask();
        } else if(cmd.equalsIgnoreCase("finishtask")) {
            BottleTasks bottleTask = player.getAttribOr(AttributeKey.BOTTLE_TASK, null);
            if(bottleTask == null) {
                player.message("You currently have no task to complete.");
                return;
            }
            int completeAmount = bottleTask.getTaskAmount();
            player.getTaskBottleManager().increase(bottleTask, completeAmount);
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }

}

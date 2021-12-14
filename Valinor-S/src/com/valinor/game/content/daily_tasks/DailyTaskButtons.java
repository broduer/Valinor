package com.valinor.game.content.daily_tasks;

import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;

import java.util.HashMap;

import static com.valinor.game.content.daily_tasks.DailyTaskUtility.*;
import static com.valinor.game.world.entity.AttributeKey.DAILY_TASK_CATEGORY;
import static com.valinor.game.world.entity.AttributeKey.DAILY_TASK_SELECTED;

/**
 * @author Patrick van Elderen | June, 15, 2021, 16:05
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class DailyTaskButtons extends Interaction {

    public static boolean REWARDS_DISABLED = false;

    private static final HashMap<Integer, DailyTasks> EASY_BUTTONS = new HashMap<>();
    private static final HashMap<Integer, DailyTasks> MED_BUTTONS = new HashMap<>();
    private static final HashMap<Integer, DailyTasks> HARD_BUTTONS = new HashMap<>();

    static {
        int button;
        button = 41431;

        for (final DailyTasks task : DailyTasks.asList(TaskCategory.EASY)) {
            EASY_BUTTONS.put(button++, task);
        }
        button = 41431;
        for (final DailyTasks achievement : DailyTasks.asList(TaskCategory.MED)) {
            MED_BUTTONS.put(button++, achievement);
        }
        button = 41431;
        for (final DailyTasks achievement : DailyTasks.asList(TaskCategory.HARD)) {
            HARD_BUTTONS.put(button++, achievement);
        }
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        if (button == 41420) {
            DailyTaskManager.open(player, TaskCategory.EASY);
            DailyTaskManager.sendInterfaceForTask(player, DailyTasks.EXPERIMENTS);
            player.putAttrib(DAILY_TASK_CATEGORY, TaskCategory.EASY);
            player.getPacketSender().sendConfig(1163, 1);
            player.getPacketSender().sendConfig(1164, 0);
            player.getPacketSender().sendConfig(1165, 0);
            player.getPacketSender().setClickedText(LIST_START_ID, true);
            player.getInterfaceManager().open(DAILY_TASK_MANAGER_INTERFACE);
            return true;
        }

        if (button == 41421) {
            DailyTaskManager.open(player, TaskCategory.MED);
            DailyTaskManager.sendInterfaceForTask(player, DailyTasks.BARROWS);
            player.putAttrib(DAILY_TASK_CATEGORY, TaskCategory.MED);
            player.getPacketSender().sendConfig(1163, 0);
            player.getPacketSender().sendConfig(1164, 1);
            player.getPacketSender().sendConfig(1165, 0);
            player.getPacketSender().setClickedText(LIST_START_ID, true);
            player.getInterfaceManager().open(DAILY_TASK_MANAGER_INTERFACE);
            return true;
        }

        if (button == 41422) {
            DailyTaskManager.open(player, TaskCategory.HARD);
            DailyTaskManager.sendInterfaceForTask(player, DailyTasks.CORRUPTED_NECHRYARCH);
            player.putAttrib(DAILY_TASK_CATEGORY, TaskCategory.HARD);
            player.getPacketSender().sendConfig(1163, 0);
            player.getPacketSender().sendConfig(1164, 0);
            player.getPacketSender().sendConfig(1165, 1);
            player.getPacketSender().setClickedText(LIST_START_ID, true);
            player.getInterfaceManager().open(DAILY_TASK_MANAGER_INTERFACE);
            return true;
        }

        if (button == CLAIM_BUTTON_ID) {
            DailyTasks task = player.getAttrib(DAILY_TASK_SELECTED);
            if (task != null) {
                if(!REWARDS_DISABLED) {
                    DailyTaskManager.claimReward(task, player);
                } else {
                    player.message(Color.RED.wrap("You cannot claim the reward at this time, the rewards are disabled until further notice."));
                }
            }
            return true;
        }

        if(button == TELEPORT_BUTTON_ID) {
            DailyTasks task = player.getAttrib(DAILY_TASK_SELECTED);
            if (task != null) {
                if(task == DailyTasks.VOTING) {
                    player.message("This activity has no teleport option.");
                    return true;
                }

                if (!Teleports.canTeleport(player,true, TeleportType.GENERIC)) {
                    return true;
                }

                if(task == DailyTasks.SLAYER) {
                    SlayerCreature.teleport(player);
                    return true;
                }

                Teleports.basicTeleport(player, task.tile);
            }
            return true;
        }

        //Easy task buttons
        if (player.getAttribOr(DAILY_TASK_CATEGORY, null) == TaskCategory.EASY && EASY_BUTTONS.containsKey(button)) {
            DailyTaskManager.sendInterfaceForTask(player, EASY_BUTTONS.get(button));
            player.getPacketSender().setClickedText(button, true);
            return true;
        }

        //Med task buttons
        if (player.getAttribOr(DAILY_TASK_CATEGORY, null) == TaskCategory.MED && MED_BUTTONS.containsKey(button)) {
            DailyTaskManager.sendInterfaceForTask(player, MED_BUTTONS.get(button));
            player.getPacketSender().setClickedText(button, true);
            return true;
        }

        //Hard task buttons
        if (player.getAttribOr(DAILY_TASK_CATEGORY, null) == TaskCategory.HARD && HARD_BUTTONS.containsKey(button)) {
            DailyTaskManager.sendInterfaceForTask(player, HARD_BUTTONS.get(button));
            player.getPacketSender().setClickedText(button, true);
            return true;
        }
        return false;
    }
}

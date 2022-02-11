package com.valinor.game.content.areas.wilderness.content.bounty_hunter;

import com.valinor.game.content.areas.wilderness.content.bounty_hunter.bounty_tasks.BountyHunterTask;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.dialogue.BountyTaskNotificationsD;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.dialogue.SkipTargetD;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.hotspot.Hotspot;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Optional;

import static com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunterConstants.*;
import static com.valinor.game.world.entity.AttributeKey.BOUNTY_HUNTER_TASK;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public class BountyHunterWidget extends Interaction {

    /**
     * Sends the widget for the {@link Player}.
     *
     * @param player The player to update the widget for.
     */
    public static void sendBountyWidget(Player player) {
        Optional<Player> target = BountyHunter.getTargetfor(player);

        boolean bounty_task_notification = player.getAttribOr(AttributeKey.BOUNTY_HUNTER_TASK_NOTIFICATIONS, true);

        int widgetId = bounty_task_notification ? BOUNTY_HUNTER_WIDGET_TASK_BUTTON : BOUNTY_HUNTER_WIDGET_TASK_INFO;

        boolean minimize = player.getAttribOr(AttributeKey.MINIMIZE, false);

        if (minimize) {
            widgetId = 25300;
        }

        //System.out.println(widgetId);

        player.getInterfaceManager().openWalkable(widgetId);

        // If we have a target send the following information.
        if (target.isPresent()) {
            player.getPacketSender().sendString(TARGET_NAME_ID, "<img=506>" + target.get().getUsername() + " (" + target.get().skills().combatLevel() + ")");
            int wild_level_minus_four = WildernessArea.wildernessLevel(target.get().tile()) - 4;
            int wild_level_plus_four = WildernessArea.wildernessLevel(target.get().tile()) + 4;

            //Can't go below level 1 wilderness
            if (wild_level_minus_four < 1) {
                wild_level_minus_four = 1;
            }

            //Can't go above level 58 wilderness
            if (wild_level_plus_four > 58) {
                wild_level_minus_four = 58;
            }

            boolean safe = WildernessArea.inWilderness(target.get().tile());
            player.getPacketSender().sendString(LEVEL_INDICATOR_ID, !safe ? "Safe" : wild_level_minus_four + "-" + wild_level_plus_four);
            player.getPacketSender().sendConfig(855, target.get().<Integer>getAttribOr(AttributeKey.MULTIWAY_AREA, -1) == 1 ? 0 : 1);
        } else {
            player.getPacketSender().sendString(TARGET_NAME_ID, "---");
            player.getPacketSender().sendString(LEVEL_INDICATOR_ID, "---");
            player.getPacketSender().sendString(BOUNTY_HUNTER_TASK_TIME_LEFT, "---");
            player.getPacketSender().sendConfig(855, 1);
        }
    }

    /**
     * The skipping target dialogue.
     *
     * @param player The player skipping his/her target.
     */
    private static void skipTarget(Player player) {
        player.getDialogueManager().start(new SkipTargetD());
    }

    /**
     * Enable and disables bounty task notifications
     *
     * @param player The player toggling the notifications
     */
    private static void bountyTaskNotifications(Player player) {
        player.getDialogueManager().start(new BountyTaskNotificationsD());
    }

    /**
     * Minimizes the bounty hunter widget
     */
    private static void minimize(Player player) {
        player.putAttrib(AttributeKey.MINIMIZE, true);
    }

    /**
     * Reveals the information about the current hotspot and the time remaining before a new hotspot is cast out.
     *
     * @param player The player receiving the info
     */
    private static void hotspotInfo(Player player) {
        String hotspot = Hotspot.ACTIVE.name;
        player.message("<col=" + Color.MEDRED.getColorValue() + ">Bounty Hotspot:</col> The current hotspot is <col=" + Color.MEDRED.getColorValue() + ">" + hotspot + ".");

        int timer = player.getAttribOr(AttributeKey.HOTSPOT_TIMER, -1);
        int convertTicksToSeconds = Utils.getSeconds(timer);
        player.message("It will move in <col=" + Color.MEDRED.getColorValue() + "> " + Utils.convertSecondsToDuration(convertTicksToSeconds, false) + "</col>.");
        player.getPacketSender().sendMessage("You must have a combat level of 50 and carry a Mysterious emblem to be eligible for").sendMessage("hotspot bonuses.");
    }

    /**
     * Reveals the information about the current hotspot and bounty task to the player
     *
     * @param player The player receiving the info
     */
    private static void info(Player player) {
        String hotspot = Hotspot.ACTIVE.name;
        player.message("<col=" + Color.MEDRED.getColorValue() + ">Bounty Hotspot:</col> The current hotspot is <col=" + Color.MEDRED.getColorValue() + ">" + hotspot + ".");
        int timer = player.getAttribOr(AttributeKey.HOTSPOT_TIMER, -1);
        int convertTicksToSeconds = Utils.getSeconds(timer);
        player.message("It will move in <col=" + Color.MEDRED.getColorValue() + "> " + Utils.convertSecondsToDuration(convertTicksToSeconds, false) + "</col>.");

        var bountyHunterTask = player.<BountyHunterTask.BountyTasks>getAttribOr(BOUNTY_HUNTER_TASK,null);
        if (bountyHunterTask != null) {
            player.message("<col=" + Color.MEDRED.getColorValue() + ">Current Bounty Task:</col> " + bountyHunterTask.getTaskDescription() + ".");
        }
    }

    private static void configure(Player player) {
        bountyTaskNotifications(player);
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        switch (button) {
            case 25002 -> {
                minimize(player);
                player.getInterfaceManager().openWalkable(25300);
                return true;
            }
            case 25007 -> {
                hotspotInfo(player);
                return true;
            }
            case 25009 -> {
                bountyTaskNotifications(player);
                return true;
            }
            case 25013 -> {
                skipTarget(player);
                return true;
            }
            case 25103 -> {
                configure(player);
                return true;
            }
            case 25104 -> {
                info(player);
                return true;
            }
            case 25301 -> {
                if (player.getInterfaceManager().getWalkable() == MINIMIZE) {
                    player.putAttrib(AttributeKey.MINIMIZE, false);
                    sendBountyWidget(player);
                }
                return true;
            }
        }
        return false;
    }
}

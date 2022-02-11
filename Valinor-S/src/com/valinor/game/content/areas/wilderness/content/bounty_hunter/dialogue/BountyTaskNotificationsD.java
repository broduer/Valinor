package com.valinor.game.content.areas.wilderness.content.bounty_hunter.dialogue;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.util.Color;

import static com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunterConstants.BOUNTY_HUNTER_WIDGET_TASK_BUTTON;
import static com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunterConstants.BOUNTY_HUNTER_WIDGET_TASK_INFO;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public class BountyTaskNotificationsD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        int skips = player.getAttribOr(AttributeKey.BOUNTY_TASKS_SKIPPED, 0);

        send(DialogueType.OPTION, "Bounty Task Configuration", skips >= 3 ? "Skip current Bounty Task" : "<str>Skip current Bounty Task", "Block Bounty Task notifications", "Cancel.");
        setPhase(0);
    }

    @Override
    public void next() {
        if (getPhase() == 1) {
            stop();
        }
    }

    @Override
    public void select(int option) {
        /*if (isPhase(0)) {
            if (option == 1) {
                int skips = player.getAttribOr(AttributeKey.BOUNTY_TASKS_SKIPPED, 0);

                BountyHunterTask.BountyTasks randomTask = BountyHunterTask.BountyTasks.randomTask();

                if (skips == 3 && !player.getPlayerRights().isDeveloperOrGreater(player)) {
                    player.message("You can only skip a Bounty Task 3 times per day.");
                    return;
                }
                skips++;
                if (skips > 3) //For devs so we don't get confused
                    skips = 3;

                player.putAttrib(AttributeKey.BOUNTY_TASKS_SKIPPED, skips);
                player.setBountyTask(randomTask);
                player.message("Skips used today: <col="+ Color.MEDRED.getColorValue()+">"+skips+"/3");
                String longMessage = player.getBountyTask().getLongdsc();

                if(longMessage.length() > 1) {
                    player.getPacketSender().sendMessage("<col="+Color.MEDRED.getColorValue()+">New Bounty Task:</col> " + player.getBountyTask().getTaskDescription() + ".").sendMessage(player.getBountyTask().getLongdsc()+".");
                } else {
                    player.message("<col="+Color.MEDRED.getColorValue()+">New Bounty Task:</col> " + player.getBountyTask().getTaskDescription() + ".");
                }

                stop();
            } else if (option == 2) {
                boolean bounty_task_notification = player.getAttribOr(AttributeKey.BOUNTY_HUNTER_TASK_NOTIFICATIONS, true);

                if (bounty_task_notification) {
                    player.getInterfaceManager().openWalkable(BOUNTY_HUNTER_WIDGET_TASK_BUTTON);
                    player.putAttrib(AttributeKey.BOUNTY_HUNTER_TASK_NOTIFICATIONS, false);
                    stop();
                } else {
                    player.getInterfaceManager().openWalkable(BOUNTY_HUNTER_WIDGET_TASK_INFO);
                    player.putAttrib(AttributeKey.BOUNTY_HUNTER_TASK_NOTIFICATIONS, true);
                    send(DialogueType.STATEMENT, "You will no longer receive <col=0x7f0000>notifications about Bounty tasks. You", "can <col=0x7f0000>reactivate them again via the <col=0x7f0000>Bounty Hunter head-up display.");
                    setPhase(1);
                }
            } else if (option == 3) {
                stop();
            }
        }*/
    }
}

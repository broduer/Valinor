package com.valinor.game.content.group_ironman;

import com.valinor.game.content.syntax.impl.ChangeGroupLeader;
import com.valinor.game.content.syntax.impl.InviteToGroup;
import com.valinor.game.content.syntax.impl.KickFromGroup;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.util.Color;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 15, 2021
 */
public class DisbandDialogue extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Change group leader", "Leave group", "Kick", "Disband group", "Nevermind");
        setPhase(0);
    }

    @Override
    protected void select(int option) {
        if(isPhase(0)) {
            if(option == 1) {
                stop();
                if(!IronmanGroupHandler.isGroupLeader(player)) {
                    player.message(Color.RED.wrap("You are not the leader of this group."));
                    return;
                }

                player.setEnterSyntax(new ChangeGroupLeader());
                player.getPacketSender().sendEnterInputPrompt("Who would you like to promote to leader?");
            }
            if(option == 2) {
                stop();
                if(IronmanGroupHandler.isGroupLeader(player)) {
                    player.message(Color.RED.wrap("You cannot leave as the leader of the group."));
                    return;
                }
                IronmanGroupHandler.leaveGroup(player);
            }
            if(option == 3) {
                stop();
                if(!IronmanGroupHandler.isGroupLeader(player)) {
                    player.message(Color.RED.wrap("You are not the leader of this group."));
                    return;
                }

                player.setEnterSyntax(new KickFromGroup());
                player.getPacketSender().sendEnterInputPrompt("Who would you like to kick from your group?");
            }
            if(option == 4) {
                stop();
                if(!IronmanGroupHandler.isGroupLeader(player)) {
                    player.message(Color.RED.wrap("You are not the leader of this group."));
                    return;
                }
                IronmanGroupHandler.deleteGroup(player);
                player.messageBox("You have successfully disbanded your group. <br>Form a new one by accepting or sending an invite.");
            }
            if(option == 5) {
                stop();
            }
        }
    }
}

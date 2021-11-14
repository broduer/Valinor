package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Color;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 14, 2021
 */
public class SetGroupName implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, @NotNull String input) {
        if (input.length() == 0) {
            return;
        }
        if (input.length() > 12) {
            player.message("Group name can't be longer than 12 characters long.");
            return;
        }

        Optional<IronmanGroup> team = IronmanGroupHandler.getPlayersGroup(player);
        if(team.isEmpty()) {
            player.messageBox("You aren't in a ironman group.");
            return;
        }
        team.get().setGroupName(player, input);
        player.message("You have set your groups name to "+ Color.RED.wrap(input)+"</col>. You may not change this.");
        player.putAttrib(AttributeKey.GROUP_NAME_SET,true);
    }
}

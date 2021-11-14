package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.Color;

import java.util.Optional;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 13, 2021
 */
public class SetTeamNameCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        String input = parts[0];
        if (input.length() > 12) {
            player.message("Team name must be maximum 12 characters long.");
            return;
        }

        if (player.<Boolean>getAttribOr(AttributeKey.GROUP_NAME_SET,false)) {
            player.message("You have already set a name for your team.");
            return;
        }
        Optional<IronmanGroup> team = IronmanGroupHandler.getPlayersGroup(player);
        if(team.isEmpty()) {
            System.out.println("error.");
            return;
        }
        team.get().setGroupName(player, input);
        player.message("You have set your teams name to "+ Color.RED.wrap(input)+"</col>. You may not change this.");
        player.putAttrib(AttributeKey.GROUP_NAME_SET,true);
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}

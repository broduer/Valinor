package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Color;
import com.valinor.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 15, 2021
 */
public class ChangeGroupLeader implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, @NotNull String username) {
        if (username.length() == 0) {
            return;
        }

        username = Utils.capitalizeFirst(username.toLowerCase());

        Optional<Player> other = World.getWorld().getPlayerByName(username);

        if(other.isPresent()) {
            IronmanGroupHandler.changeGroupLeader(player, other.get());
            player.message(Color.PURPLE.wrap("You've made "+other.get().getUsername()+" the new leader of the group."));
        } else {
            player.message(Color.RED.wrap(username+" seems to be offline, try again later."));
        }
    }
}

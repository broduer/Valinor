package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.group_ironman.IronmanGroup;
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
 * @Since November 14, 2021
 */
public class InviteToGroup implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, @NotNull String username) {
        if (username.length() == 0) {
            return;
        }

        username = Utils.capitalizeFirst(username.toLowerCase());

        Optional<Player> other = World.getWorld().getPlayerByName(username);

        if(other.isPresent()) {
            Optional<IronmanGroup> getTeam = player.getIronmanTeam();
            if (getTeam.isPresent()) {
                if (getTeam.get().canInvite(player, other.get())) {
                    getTeam.get().sendInvitation(player, other.get());
                }
            } else {
                Optional<IronmanGroup> otherTeam = other.get().getIronmanTeam();

                if (otherTeam.isPresent()) {
                    player.message(Color.RED.wrap("The person you're trying to invite is in a team already."));
                    other.get().message(Color.RED.wrap("You've been invited to a clan by " + player.getUsername() + " but you are the owner of a team already."));
                    other.get().message(Color.RED.wrap("If you wish to be in their team, delete your group and have them invite you again!"));
                    return;
                }
                Optional<IronmanGroup> create = IronmanGroupHandler.createIronmanGroup(player);
                if (create.isPresent()) {
                    if (create.get().canInvite(player, other.get())) {
                        create.get().sendInvitation(player, other.get());
                    }
                }
            }
        } else {
            player.message(Color.RED.wrap(username+" seems to be offline, try again later."));
        }
    }
}

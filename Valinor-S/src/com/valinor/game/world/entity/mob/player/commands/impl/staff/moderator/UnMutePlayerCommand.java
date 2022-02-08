package com.valinor.game.world.entity.mob.player.commands.impl.staff.moderator;

import com.valinor.GameServer;
import com.valinor.db.transactions.UnmutePlayerDatabaseTransaction;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.PlayerPunishment;
import com.valinor.util.Utils;

import java.util.Optional;

public class UnMutePlayerCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (command.length() <= 7)
            return;
        String username = Utils.formatText(command.substring(7)); // after "unmute "

        Optional<Player> plr = World.getWorld().getPlayerByName(username);
        if (GameServer.properties().enableSql && GameServer.properties().punishmentsToDatabase) {
            plr.ifPresent(p -> {
                p.muted = false;
            });
            GameServer.getDatabaseService().submit(new UnmutePlayerDatabaseTransaction(username));
            player.message("Player " + username + " was successfully unmuted.");
            Utils.sendDiscordInfoLog(player.getUsername() + " used command: ::unmute "+username, "staff_cmd");
            return;
        }

        if (!GameServer.properties().punishmentsToDatabase) {
            plr.ifPresent(value -> value.putAttrib(AttributeKey.MUTED,false));

            //Remove from regular mute list
            PlayerPunishment.removeMute(username);

            //And from regular the IP mute list
            plr.ifPresent(value -> PlayerPunishment.removeIpMute(value.getHostAddress()));

            player.message("Player " + username + " (" + username + ") was successfully unmuted.");
            Utils.sendDiscordInfoLog(player.getUsername() + " used command: ::unmute "+username, "staff_cmd");
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isModeratorOrGreater(player));
    }

}

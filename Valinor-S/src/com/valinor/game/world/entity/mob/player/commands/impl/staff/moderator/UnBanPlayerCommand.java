package com.valinor.game.world.entity.mob.player.commands.impl.staff.moderator;

import com.valinor.GameServer;
import com.valinor.db.transactions.UnbanPlayerDatabaseTransaction;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.entity.mob.player.save.PlayerSave;
import com.valinor.util.PlayerPunishment;
import com.valinor.util.Utils;

public class UnBanPlayerCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (command.length() <= 6)
            return;
        String username = Utils.formatText(command.substring(6)); // after "unban "

        if (GameServer.properties().enableSql && GameServer.properties().punishmentsToDatabase) {
            GameServer.getDatabaseService().submit(new UnbanPlayerDatabaseTransaction(username));
            player.message("Player " + username + " was successfully unbanned.");
            Utils.sendDiscordInfoLog(player.getUsername() + " used command: ::unban "+username, "staff_cmd");
            return;
        }

        if(!GameServer.properties().punishmentsToDatabase) {
            if (!PlayerSave.playerExists(username)) {
                player.message("Player " + username + " does not exist.");
                return;
            }

            //Remove from regular ban list
            PlayerPunishment.removeBan(username);
            player.message("Player " + username + " was successfully unbanned.");
            Utils.sendDiscordInfoLog("Player " + username + " was unbanned by " + player.getUsername(), "staff_cmd");
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isModeratorOrGreater(player));
    }

}

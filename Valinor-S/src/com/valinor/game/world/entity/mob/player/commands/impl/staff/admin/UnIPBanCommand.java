package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.GameServer;
import com.valinor.game.GameEngine;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.PlayerPunishment;
import com.valinor.util.Utils;

import java.io.IOException;

/**
 * @author Patrick van Elderen | January, 16, 2021, 11:09
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class UnIPBanCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (command.length() <= 8)
            return;
        if(!GameServer.properties().punishmentsToDatabase) {
            String IPToRemove = Utils.formatText(command.substring(8)); // after "unipban "

            if (IPToRemove.isEmpty()) {
                player.message("You must enter a valid username.");
                return;
            }

            GameEngine.getInstance().submitLowPriority(() -> {
                try {
                    GameEngine.getInstance().addSyncTask(() -> {
                        PlayerPunishment.removeIpBan(IPToRemove);
                        player.message("Player " + IPToRemove + " was successfully un IP banned.");
                        Utils.sendDiscordInfoLog("Player " + IPToRemove + " was successfully un IP banned. was unbanned by " + player.getUsername(), "staff_cmd");
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isAdminOrGreater(player));
    }

}

package com.valinor.game.world.entity.mob.player.commands.impl.staff.moderator;

import com.valinor.GameServer;
import com.valinor.game.GameEngine;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.PlayerPunishment;
import com.valinor.util.Utils;

/**
 * @author Patrick van Elderen | February, 04, 2021, 14:07
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class UnIPMuteCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (command.length() <= 8)
            return;

        if(!GameServer.properties().punishmentsToDatabase) {
            String IPToRemove = Utils.formatText(command.substring(9)); // after "unipmute "

            if (IPToRemove.isEmpty()) {
                player.message("You must enter a valid IP.");
                return;
            }
            GameEngine.getInstance().submitLowPriority(() -> {
                try {
                    GameEngine.getInstance().addSyncTask(() -> {
                        PlayerPunishment.removeIpMute(IPToRemove);
                        player.message("The IP: " + IPToRemove + " was successfully removed from the mute list.");
                        Utils.sendDiscordInfoLog("The IP: " + IPToRemove + " was successfully removed from the mute list and was unmuted by " + player.getUsername(), "staff_cmd");
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isModeratorOrGreater(player));
    }

}

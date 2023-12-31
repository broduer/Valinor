package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.GameServer;
import com.valinor.game.GameEngine;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.entity.mob.player.save.PlayerSave;
import com.valinor.util.PlayerPunishment;
import com.valinor.util.Utils;

/**
 * @author Patrick van Elderen | January, 16, 2021, 11:13
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class UnMacBanCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (command.length() <= 9)
            return;
        if(!GameServer.properties().punishmentsToDatabase) {
            String username = Utils.formatText(command.substring(9)); // after "unmacban "

            if (username.isEmpty()) {
                player.message("You must enter a valid username.");
                return;
            }

            Player offlinePlayer = new Player();
            offlinePlayer.setUsername(Utils.formatText(username.substring(0, 1).toUpperCase() + username.substring(1)));

            GameEngine.getInstance().submitLowPriority(() -> {
                try {
                    if (PlayerSave.loadOfflineWithoutPassword(offlinePlayer)) {
                        GameEngine.getInstance().addSyncTask(() -> {
                            if (!PlayerSave.playerExists(offlinePlayer.getUsername())) {
                                player.message("No such player profile..");
                                return;
                            }

                            String MACToRemove = offlinePlayer.getAttribOr(AttributeKey.MAC_ADDRESS, "invalid");
                            PlayerPunishment.removeMacBan(MACToRemove);
                            PlayerPunishment.removeBan(offlinePlayer.getUsername());
                            player.message("Player " + offlinePlayer.getUsername() + " was successfully un MAC banned.");
                            Utils.sendDiscordInfoLog("Player " + offlinePlayer.getUsername() + " was successfully un MAC banned. was unbanned by " + player.getUsername(), "staff_cmd");
                        });
                    } else {
                        player.message("Something went wrong trying to unmacban " + offlinePlayer.getUsername());
                    }
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

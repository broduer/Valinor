package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.Utils;

import java.util.Optional;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 12, 2022
 */
public class FinishAchievementCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (parts.length < 3) {
            player.message("Invalid use of command.");
            player.message("Use: ::finisha username achievement");
            player.message("Example: ::finisha tester BAKER");
            return;
        }
        final String player2 = Utils.formatText(parts[1].replace("_", " "));
        final String achievement = parts[2];
        Optional<Player> plr = World.getWorld().getPlayerByName(player2);
        if (plr.isPresent()) {
            Optional<Achievements> achievToComplete = Achievements.getByName(achievement);
            if(achievToComplete.isPresent()) {
                player.message("You have completed the achievement " + achievement + " for " + player2 + ".");
                AchievementsManager.activate(plr.get(), achievToComplete.get(), achievToComplete.get().getCompleteAmount());
            }
        } else {
            player.message("The player " + player2 + " is not online.");
        }
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isAdminOrGreater(player);
    }
}

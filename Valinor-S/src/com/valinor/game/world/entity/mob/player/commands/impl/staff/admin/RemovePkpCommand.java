package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.Utils;

import java.util.Optional;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.PK_POINTS;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 20, 2022
 */
public class RemovePkpCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (parts.length < 3) {
            player.message("Invalid use of command.");
            player.message("Use: ::removepkp username amount");
            player.message("Example: ::removepkp tester 1");
            return;
        }
        final String player2 = Utils.formatText(parts[1].replace("_", " "));
        int amount = Integer.parseInt(parts[2]);
        Optional<Player> plr = World.getWorld().getPlayerByName(player2);
        if (plr.isPresent()) {
            int currentPoints = plr.get().getAttribOr(AttributeKey.PK_POINTS, 0);
            int newPoints = currentPoints - amount;
            plr.get().putAttrib(AttributeKey.PK_POINTS, newPoints);
            plr.get().getPacketSender().sendString(PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(PK_POINTS.childId).fetchLineData(plr.get()));
            player.message("You have removed "+amount+" pkp from" + player2 + ".");
        } else {
            player.message("The player " + player2 + " is not online.");
        }
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isAdminOrGreater(player);
    }
}

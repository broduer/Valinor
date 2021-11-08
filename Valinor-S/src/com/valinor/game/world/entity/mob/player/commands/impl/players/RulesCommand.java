package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen | January, 20, 2021, 14:21
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class RulesCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendURL("https://ferox-os.com/2021/07/21/ferox-os-rules/");
        player.message("Opening the rules in your web browser...");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}

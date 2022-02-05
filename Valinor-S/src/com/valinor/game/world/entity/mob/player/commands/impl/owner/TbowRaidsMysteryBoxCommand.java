package com.valinor.game.world.entity.mob.player.commands.impl.owner;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.entity.mob.player.rights.MemberRights;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.Optional;

import static com.valinor.game.world.entity.AttributeKey.RAIDS_BOX_GIVES_TBOW;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 05, 2022
 */
public class TbowRaidsMysteryBoxCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (parts.length != 2) {
            player.message("The correct format is '::givetbow name'. Not "+ Arrays.toString(parts));
            player.message("Example '::givetbow test'. ");
            return;
        }

        String name = parts[1].replace("_", " ");

        Optional<Player> other = World.getWorld().getPlayerByName(name);
        other.ifPresent(value -> value.putAttrib(RAIDS_BOX_GIVES_TBOW, true));
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isOwner(player));
    }

}

package com.valinor.game.world.entity.mob.player.commands.impl.member;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;

public class DzoneCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (WildernessArea.inWilderness(player.tile()) && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("You can't use this command in the wilderness.");
            return;
        }
        if (Teleports.canTeleport(player,true, TeleportType.GENERIC)) {
            Teleports.basicTeleport(player, new Tile(2457, 2858));
        }
    }

    @Override
    public boolean canUse(Player player) {
        return player.getMemberRights().isRegularMemberOrGreater(player) || player.getPlayerRights().isStaffMember(player);
    }
}

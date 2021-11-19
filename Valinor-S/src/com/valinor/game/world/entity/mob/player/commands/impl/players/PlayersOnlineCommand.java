package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.Color;

import java.util.List;
import java.util.stream.Collectors;

public class PlayersOnlineCommand implements Command {

    public static void playersOnline(Player player) {
        List<Player> players = World.getWorld().getPlayers().stream().collect(Collectors.toList());
        StringBuilder playersOnline = new StringBuilder("<col=800000>Players:");

        for (Player p : players) {
            if(p == null) continue;

            if(player.getPlayerRights().isAdminOrGreater(player)) {
                playersOnline.append("<br><br> - ").append(p.getUsername()).append(" tile: ").append(p.tile());
            } else {
                playersOnline.append("<br><br> - ").append(p.getUsername());
            }
        }
        player.sendScroll(Color.MAROON.wrap("Players online: "+World.getWorld().getPlayers().size()), playersOnline.toString());
    }

    @Override
    public void execute(Player player, String command, String[] parts) {
       playersOnline(player);
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}

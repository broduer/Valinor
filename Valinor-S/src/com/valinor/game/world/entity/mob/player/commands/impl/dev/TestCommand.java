
package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.action.impl.TeleportAction;
import com.valinor.game.content.option_menu.ClientOptionMenu;
import com.valinor.game.content.option_menu.OptionMenu;
import com.valinor.game.content.option_menu.TeleportMenuConstants;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;

import java.util.*;

import static com.valinor.game.content.option_menu.TeleportMenuHandler.TELEPORT_MENU_CHILD_ID;

public class TestCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        final Collection<ClientOptionMenu> results = new ArrayList<>();
        HashMap<Integer, OptionMenu> teleportOptions = TeleportMenuConstants.getDefaultTeleports(player);
        for (Map.Entry<Integer, OptionMenu> entry : teleportOptions.entrySet()) {
            final int identifier = entry.getKey();
            final OptionMenu menu = entry.getValue();
            results.add(new ClientOptionMenu(identifier, menu.getOptionName(), menu.getOptionName()));
        }
        player.getPacketSender().sendOptionMenuInterface(TELEPORT_MENU_CHILD_ID, results);
        //System.out.println(player.pet().def().name);
        player.message("Test command has been activated.");
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }

}

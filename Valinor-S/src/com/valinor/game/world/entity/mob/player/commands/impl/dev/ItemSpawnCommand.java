package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;
import org.apache.commons.lang3.StringUtils;

public class ItemSpawnCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        int amount = 1;
        if (parts.length < 1 || (!StringUtils.isNumeric(parts[1]) || (parts.length > 2 && !StringUtils.isNumeric(parts[2])))) {
            player.message("Invalid syntax. Please use: ::item [ID] (amount)");
            player.message("Example: ::item 385 or ::item 385 20");
            return;
        }
        if (parts.length > 2) {
            amount = Integer.parseInt(parts[2]);
        }
        int id = Integer.parseInt(parts[1]);

       Item item = new Item(id);

        if(item.getId() > 34_000) {
            player.message("Item id not supported, this item doesn't exist.");
            return;
        }

        if (!player.canSpawn() && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("You can't spawn items here.");
            return;
        }

        if (Item.valid(item)) {
            player.getInventory().add(new Item(id, amount));
            player.message("You have just spawned x"+amount+" "+new Item(Integer.parseInt(parts[1])).unnote().name()+".");
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }
}

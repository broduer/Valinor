package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.entity.mob.player.IronMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.ItemIdentifiers;

/**
 * @author Patrick van Elderen | Zerikoth | PVE
 * @date maart 20, 2020 17:03
 */
public class MagicPotCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(player.ironMode() != IronMode.NONE) {
            player.message("As an ironman you cannot use this command.");
            return;
        }

        if (!player.tile().inSafeZone() && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("You can only use this command at safe zones.");
            return;
        }

        if(WildernessArea.inWilderness(player.tile())) {
            player.message("You can only use this command at safe zones.");
            return;
        }

        Item magicPotion = new Item(ItemIdentifiers.MAGIC_POTION4);
        if (player.inventory().hasCapacityFor(magicPotion)) {
            player.inventory().add(magicPotion);
        } else {
            player.message("Your inventory does not have enough free space to do that.");
        }
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}

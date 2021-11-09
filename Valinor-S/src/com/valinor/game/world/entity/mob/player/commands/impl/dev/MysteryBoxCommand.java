package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.content.items.mystery_box.MboxItem;
import com.valinor.game.content.items.mystery_box.Mbox;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;

import java.util.Optional;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.MYSTERY_BOX;

public class MysteryBoxCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (parts.length != 3) {
            player.message("Invalid use of command.");
            player.message("Use: ::box 100 weapon_box");
            return;
        }

        int amount = Integer.parseInt(parts[1]);
        String box_name = parts[2];

        if (box_name.equals("mbox")) {
            for (int i = 0; i < amount; i++) {
                Optional<Mbox> mBox = Mbox.getMysteryBox(MYSTERY_BOX);
                if (mBox.isPresent()) {
                    player.getMysteryBox().box = mBox.get();
                    MboxItem mboxItem = mBox.get().rollReward().copy();
                    player.getMysteryBox().reward = mboxItem;
                    player.getMysteryBox().broadcast = mboxItem.broadcastItem;
                    player.getMysteryBox().reward();
                }
            }
        }
        player.message("You have opened "+amount+" "+box_name.replaceAll("_", " ")+"'s.");
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }
}

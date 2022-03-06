package com.valinor.game.content.items;

import com.valinor.game.content.syntax.impl.YellTagScrollSyntax;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.YELL_TAG_SCROLL;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 06, 2022
 */
public class YellTagScroll extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == YELL_TAG_SCROLL) {
                player.optionsTitled("Would you like to set a custom yell tag?", "Yes.", "No.", () -> {
                    if(!player.inventory().contains(YELL_TAG_SCROLL)) {
                        return;
                    }

                    player.setEnterSyntax(new YellTagScrollSyntax());
                    player.getPacketSender().sendEnterInputPrompt("What would you like your tag to be?");
                });
                return true;
            }
        }
        return false;
    }
}

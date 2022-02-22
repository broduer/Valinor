package com.valinor.game.content.items;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;

import static com.valinor.game.world.entity.AttributeKey.SWIFT_ABILITY;
import static com.valinor.util.CustomItemIdentifiers.RANGING_SCROLL;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 22, 2022
 */
public class RangingScroll extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == RANGING_SCROLL) {
                if(player.<Boolean>getAttribOr(SWIFT_ABILITY,false)) {
                    player.message(Color.RED.wrap("You have already learnt the ways of the Swift."));
                    return true;
                }
                player.optionsTitled("Are you sure you would like to learn this ability?", "Yes.", "No.", () -> {
                    if(!player.inventory().contains(RANGING_SCROLL))
                        return;
                    player.inventory().remove(RANGING_SCROLL);
                    player.putAttrib(SWIFT_ABILITY,true);
                    player.message("You study the scroll and learn a new ability: "+ Color.DARK_RED.wrap("Swift"));
                    player.graphic(1877);

                });
                return true;
            }
        }
        return false;
    }
}

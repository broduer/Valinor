package com.valinor.game.content.items;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import static com.valinor.util.CustomItemIdentifiers.PKP_TICKET;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 13, 2022
 */
public class PkpTicket extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == PKP_TICKET) {
                if(player.inventory().contains(PKP_TICKET)) {
                    int count = player.inventory().count(PKP_TICKET);
                    player.inventory().remove(PKP_TICKET, count);
                    var pkp = player.<Integer>getAttribOr(AttributeKey.PK_POINTS,0) + count;
                    player.putAttrib(AttributeKey.PK_POINTS, pkp);
                    player.message(Color.BLUE.wrap("You have claimed "+ Utils.formatRunescapeStyle(count)+" PKP."));
                }
                return true;
            }
        }
        return false;
    }
}

package com.valinor.game.content.items.mystery;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.CustomItemIdentifiers.DONATOR_MYSTERY_BOX;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 23, 2021
 */
public class BarrowsMysteryBox extends PacketInteraction {

    private final List<Integer> barrowsItemIds = Arrays.asList(AHRIMS_HOOD, AHRIMS_STAFF, AHRIMS_ROBETOP, AHRIMS_ROBESKIRT, DHAROKS_HELM, DHAROKS_GREATAXE, DHAROKS_PLATEBODY,
        DHAROKS_PLATELEGS, GUTHANS_HELM, GUTHANS_WARSPEAR, GUTHANS_PLATEBODY, GUTHANS_CHAINSKIRT, KARILS_COIF, KARILS_CROSSBOW, KARILS_LEATHERTOP, KARILS_LEATHERSKIRT, TORAGS_HELM,
        TORAGS_HAMMERS, TORAGS_PLATEBODY, TORAGS_PLATELEGS, VERACS_HELM, VERACS_FLAIL, VERACS_BRASSARD, VERACS_PLATESKIRT, AMULET_OF_THE_DAMNED);

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == BARROWS_MYSTERY_BOX) {
                if (!player.inventory().contains(BARROWS_MYSTERY_BOX))
                    return true;
                player.inventory().remove(BARROWS_MYSTERY_BOX);

                int barrowsPieceId = Utils.randomElement(barrowsItemIds);
                Item reward = new Item(barrowsPieceId);
                player.inventory().addOrBank(reward);

                var amt = reward.getAmount();
                player.message("You open the barrows mystery box and found...");
                player.message("x"+amt+" "+reward.unnote().name()+".");
                return true;
            }
        }
        return false;
    }
}

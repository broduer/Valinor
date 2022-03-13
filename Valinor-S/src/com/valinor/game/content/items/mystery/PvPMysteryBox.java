package com.valinor.game.content.items.mystery;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.PVP_MYSTERY_BOX;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 12, 2022
 */
public class PvPMysteryBox extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == PVP_MYSTERY_BOX) {
                if (!player.inventory().contains(PVP_MYSTERY_BOX))
                    return true;
                player.inventory().remove(PVP_MYSTERY_BOX);
                player.inventory().addOrBank(ITEMS);
                var pkp = player.<Integer>getAttribOr(AttributeKey.PK_POINTS,0) + 2500;
                player.putAttrib(AttributeKey.PK_POINTS, pkp);
                player.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(player));

                player.message("You open the pvp mystery box and received some PvP goodies and 2.500 PKP.");
                Utils.sendDiscordInfoLog(player.getUsername() + " with IP " + player.getHostAddress() + " just opened a pvp mystery box.", "boxes_opened");
                return true;
            }
        }
        return false;
    }

    private static final List<Item> ITEMS = Arrays.asList(
        new Item(ABYSSAL_WHIP),
        new Item(DHAROKS_ARMOUR_SET),
        new Item(GRANITE_MAUL_24225),
        new Item(SUPER_COMBAT_POTION4+1, 15),
        new Item(ANGLERFISH+1, 150)
    );
}

package com.valinor.game.content.items;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * Might as well add this just in case we ever go eco, took 2min to convert.
 * Can easily use for PvP too :).
 *
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * april 20, 2020
 */
public class Caskets extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == CASKET) {
                openCasket(player, CASKET);
                return true;
            }
            if (item.getId() == CASKET_7956) {
                openCasket(player, CASKET_7956);
                return true;
            }
        }
        return false;
    }

    private void openCasket(Player player, int id) {
        if (!player.inventory().contains(id))
            return;

        var amount = 0;
        if (id == CASKET) {
            amount = World.getWorld().random(50_000, 250_000);
            var blood_reaper = player.hasPetOut("Blood Reaper pet");
            if(blood_reaper) {
                int extra = amount * 10 / 100;
                amount += extra;
            }
            player.inventory().remove(new Item(CASKET), true);
            player.inventory().add(new Item(COINS_995, amount), true);
            player.message("You open the casket and find " + Utils.formatNumber(amount) + " coins!");
        } else if (id == CASKET_7956) {
            amount = World.getWorld().random(250_000, 500_000);
            var blood_reaper = player.hasPetOut("Blood Reaper pet");
            if(blood_reaper) {
                int extra = amount * 10 / 100;
                amount += extra;
            }
            player.inventory().remove(new Item(CASKET_7956), true);
            player.inventory().add(new Item(BLOOD_MONEY, amount), true);
            player.message("You open the casket and find " + Utils.formatNumber(amount) + " coins!");
        }
    }

}

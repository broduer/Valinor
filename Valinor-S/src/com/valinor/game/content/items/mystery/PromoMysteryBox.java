package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public class PromoMysteryBox extends Interaction {

    private static final int RARE_ROLL = 5;
    private static final int UNCOMMON_ROLL = 3;

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == PROMO_MYSTERY_BOX) {
                if (!player.inventory().contains(PROMO_MYSTERY_BOX))
                    return true;
                player.inventory().remove(PROMO_MYSTERY_BOX);

                Item reward;
                boolean yell = false;
                if (World.getWorld().rollDie(RARE_ROLL, 1)) {
                    reward = Utils.randomElement(RARE_REWARDS);
                    yell = true;
                } else if (World.getWorld().rollDie(UNCOMMON_ROLL, 1)) {
                    reward = Utils.randomElement(UNCOMMON_REWARDS);
                    yell = true;
                } else {
                    reward = Utils.randomElement(COMMON_REWARDS);
                }

                player.inventory().addOrBank(reward);

                var amt = reward.getAmount();
                player.message("You open the promo mystery box and found...");
                player.message("x" + Utils.formatNumber(amt) + " " + reward.unnote().name() + ".");

                if (yell) {
                    boolean moreThanOne = amt > 1;
                    String plural = moreThanOne ? "x "+Utils.formatNumber(amt) : "";
                    World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received "+plural+" " + Utils.getVowelFormat(reward.unnote().name()) + " from a promo mystery box!");
                }
                Utils.sendDiscordInfoLog(player.getUsername() + " with IP " + player.getHostAddress() + " just opened a promo mystery box and received x" + amt + " " + reward.unnote().name() + ".", "boxes_opened");
                return true;
            }
        }
        return false;
    }

    private static final List<Item> RARE_REWARDS = Arrays.asList(
        new Item(FIFTY_TOTAL_DONATED_SCROLL),
        new Item(GIANT_KEY_OF_DROPS),
        new Item(TWISTED_BOW),
        new Item(SCYTHE_OF_VITUR),
        new Item(SANGUINE_ORNAMENT_KIT),
        new Item(DONATOR_TICKET, 10_000),
        new Item(PETS_MYSTERY_BOX,2),
        new Item(RAIDS_MYSTERY_BOX),
        new Item(VALINOR_COINS, 2_500),
        new Item(LIME_PARTYHAT),
        new Item(PURPLE_HWEEN_MASK),
        new Item(LIME_GREEN_HWEEN_MASK),
        new Item(FORTY_DOLLAR_BOND)
    );

    private static final List<Item> UNCOMMON_REWARDS = Arrays.asList(
        new Item(ARCANE_SPIRIT_SHIELD),
        new Item(AVERNIC_DEFENDER),
        new Item(VOLATILE_ORB),
        new Item(ELDRITCH_ORB),
        new Item(HARMONISED_ORB),
        new Item(DONATOR_TICKET, 5_000),
        new Item(PETS_MYSTERY_BOX),
        new Item(ELDER_MAUL),
        new Item(DRAGON_HUNTER_CROSSBOW),
        new Item(DRAGON_HUNTER_LANCE),
        new Item(NIGHTMARE_STAFF)
    );

    private static final List<Item> COMMON_REWARDS = Arrays.asList(
        new Item(DINHS_BULWARK),
        new Item(INQUISITORS_GREAT_HELM),
        new Item(INQUISITORS_HAUBERK),
        new Item(INQUISITORS_PLATESKIRT),
        new Item(INQUISITORS_MACE),
        new Item(INFERNAL_CAPE),
        new Item(DRAGON_CLAWS),
        new Item(ARMADYL_GODSWORD)
    );
}

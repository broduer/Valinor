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
public class YoutubeMysteryBox extends Interaction {
    
    private static final int RARE_ROLL = 30;
    private static final int UNCOMMON_ROLL = 10;

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == YOUTUBE_MYSTERY_BOX) {
                if (!player.inventory().contains(YOUTUBE_MYSTERY_BOX))
                    return true;
                player.inventory().remove(YOUTUBE_MYSTERY_BOX);

                Item reward;
                boolean rare = false;
                if (World.getWorld().rollDie(RARE_ROLL, 1)) {
                    reward = Utils.randomElement(RARE_REWARDS);
                    rare = true;
                } else if (World.getWorld().rollDie(UNCOMMON_ROLL, 1)) {
                    reward = Utils.randomElement(UNCOMMON_REWARDS);
                } else {
                    reward = Utils.randomElement(COMMON_REWARDS);
                }

                player.inventory().addOrBank(reward);

                var amt = reward.getAmount();
                player.message("You open the youtube mystery box and found...");
                player.message("x" + amt + " " + reward.unnote().name() + ".");

                if (rare) {
                    World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a youtube mystery box!");
                }
                Utils.sendDiscordInfoLog(player.getUsername() + " with IP " + player.getHostAddress() + " just opened a youtube mystery box and received x" + amt + " " + reward.unnote().name() + ".", "boxes_opened");
                return true;
            }
        }
        return false;
    }

    private static final List<Item> RARE_REWARDS = Arrays.asList(
        new Item(ABYSSAL_DAGGER),
        new Item(ABYSSAL_DAGGER_P_13271),
        new Item(ZAMORAKIAN_SPEAR),
        new Item(STAFF_OF_THE_DEAD),
        new Item(HEAVY_BALLISTA),
        new Item(ARMADYL_CHESTPLATE),
        new Item(ARMADYL_CHAINSKIRT),
        new Item(BANDOS_CHESTPLATE),
        new Item(BANDOS_TASSETS),
        new Item(GUARDIAN_BOOTS),
        new Item(TOXIC_STAFF_OF_THE_DEAD),
        new Item(TOXIC_BLOWPIPE)
    );

    private static final List<Item> UNCOMMON_REWARDS = Arrays.asList(
        new Item(ARMADYL_HELMET),
        new Item(SARADOMIN_SWORD),
        new Item(DRAGON_CROSSBOW),
        new Item(ABYSSAL_TENTACLE),
        new Item(BANDOS_GODSWORD),
        new Item(SARADOMIN_GODSWORD),
        new Item(ZAMORAK_GODSWORD),
        new Item(COINS_995, 10_000_000),
        new Item(DONATOR_TICKET,250),
        new Item(VOTE_TICKET,5)
    );

    private static final List<Item> COMMON_REWARDS = Arrays.asList(
        new Item(FIGHTER_TORSO),
        new Item(BLESSED_SPIRIT_SHIELD),
        new Item(ROBIN_HOOD_HAT),
        new Item(RANGER_BOOTS),
        new Item(DRAGON_BOOTS),
        new Item(INFINITY_BOOTS),
        new Item(INFINITY_HAT),
        new Item(INFINITY_TOP),
        new Item(INFINITY_BOTTOMS),
        new Item(MAGES_BOOK),
        new Item(DAGONHAI_HAT),
        new Item(DAGONHAI_ROBE_TOP),
        new Item(DAGONHAI_ROBE_BOTTOM),
        new Item(DRAGON_FULL_HELM),
        new Item(DRAGON_PLATEBODY),
        new Item(GUTHANS_ARMOUR_SET),
        new Item(VERACS_ARMOUR_SET),
        new Item(TORAGS_ARMOUR_SET),
        new Item(KARILS_ARMOUR_SET),
        new Item(AHRIMS_ARMOUR_SET),
        new Item(DHAROKS_ARMOUR_SET)
    );
}

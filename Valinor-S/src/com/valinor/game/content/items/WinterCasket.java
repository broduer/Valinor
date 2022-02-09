package com.valinor.game.content.items;

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
 * @Since February 09, 2022
 */
public class WinterCasket extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == WINTER_CASKET) {
                open(player);
                return true;
            }
        }
        return false;
    }

    private void open(Player player) {
        if (player.inventory().contains(WINTER_CASKET)) {
            player.inventory().remove(WINTER_CASKET);
            Item reward = reward().copy();
            if (rare) {
                World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a supply crate!");
            }
            player.inventory().addOrDrop(reward);
            rare = false;
        }
    }

    private final List<Item> COMMON_REWARDS = Arrays.asList(
        new Item(LARRANS_KEY,10),
        new Item(ABYSSAL_WHIP),
        new Item(AMULET_OF_FURY),
        new Item(BONECRUSHER),
        new Item(EXPLORERS_RING_4),
        new Item(BARROWS_MYSTERY_BOX)
    );

    private final List<Item> UNCOMMON_REWARDS = Arrays.asList(
        new Item(BANDOS_GODSWORD_ORNAMENT_KIT),
        new Item(SARADOMIN_GODSWORD_ORNAMENT_KIT),
        new Item(ZAMORAK_GODSWORD_ORNAMENT_KIT),
        new Item(ARMADYL_GODSWORD_ORNAMENT_KIT),
        new Item(TORMENTED_ORNAMENT_KIT),
        new Item(WILDERNESS_KEY,3),
        new Item(DONATOR_MYSTERY_BOX)
    );

    private final List<Item> RARE_REWARDS = Arrays.asList(
        new Item(CRYSTAL_OF_ITHELL),
        new Item(CRYSTAL_OF_IORWERTH),
        new Item(CRYSTAL_OF_TRAHAEARN),
        new Item(CRYSTAL_OF_CADARN),
        new Item(CRYSTAL_OF_CRWYS),
        new Item(CRYSTAL_OF_MEILYR),
        new Item(CRYSTAL_OF_HEFIN),
        new Item(CRYSTAL_OF_AMLODD),
        new Item(PETS_MYSTERY_BOX),
        new Item(CORRUPTING_STONE),
        new Item(CURSED_AMULET_OF_THE_DAMNED),
        new Item(FEROCIOUS_GLOVES),
        new Item(SUPER_MYSTERY_BOX),
        new Item(NIGHTMARE_STAFF),
        new Item(RED_DYE)
    );

    private boolean rare = false;

    private Item reward() {
        if (World.getWorld().rollDie(50, 1)) {
            rare = true;
            return Utils.randomElement(RARE_REWARDS);
        } else if (World.getWorld().rollDie(20, 1)) {
            return Utils.randomElement(UNCOMMON_REWARDS);
        } else {
            return Utils.randomElement(COMMON_REWARDS);
        }
    }
}

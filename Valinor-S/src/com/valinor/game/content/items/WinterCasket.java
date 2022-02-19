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
            Item reward = Utils.randomElement(REWARDS);
            World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a supply crate!");
            player.inventory().addOrDrop(reward);
        }
    }

    private final List<Item> REWARDS = Arrays.asList(
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
        new Item(NIGHTMARE_STAFF),
        new Item(RED_DYE),
        new Item(TORTURE_ORNAMENT_KIT),
        new Item(ANGUISH_ORNAMENT_KIT),
        new Item(OCCULT_ORNAMENT_KIT),
        new Item(CRYSTAL_HELM),
        new Item(CRYSTAL_BODY),
        new Item(CRYSTAL_LEGS),
        new Item(GHRAZI_RAPIER),
        new Item(ARCANE_SIGIL),
        new Item(COINS_995, 200_000_000),
        new Item(VOLATILE_ORB),
        new Item(HARMONISED_ORB),
        new Item(ELDRITCH_ORB),
        new Item(PEGASIAN_BOOTS_OR),
        new Item(ETERNAL_BOOTS_OR),
        new Item(PRIMORDIAL_BOOTS_OR)
    );

}

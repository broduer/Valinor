package com.valinor.game.content.events.haunted_chest;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.PKP_TICKET;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.BANDOS_GODSWORD;
import static com.valinor.util.ObjectIdentifiers.CHEST_25685;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public class HauntedChestInteraction extends Interaction {

    private static final List<Item> RANDOM_ITEMS = Arrays.asList(
        new Item(ABYSSAL_DAGGER),
        new Item(STAFF_OF_THE_DEAD),
        new Item(DRAGONFIRE_WARD),
        new Item(SARADOMIN_GODSWORD),
        new Item(ZAMORAK_GODSWORD),
        new Item(BANDOS_GODSWORD),
        new Item(ARMADYL_HELMET),
        new Item(ARMADYL_CHESTPLATE),
        new Item(ARMADYL_CHAINSKIRT),
        new Item(BANDOS_CHESTPLATE),
        new Item(BANDOS_TASSETS),
        new Item(ZAMORAKIAN_SPEAR),
        new Item(ZAMORAKIAN_HASTA),
        new Item(DRAGON_CLAWS),
        new Item(ARMADYL_GODSWORD),
        new Item(HEAVY_BALLISTA));

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if(object.getId() == CHEST_25685) {
                if (player.skills().level(Skills.THIEVING) < 99) {
                    player.message("You need a thieving level of 99 to search the Haunted chest.");
                    return true;
                }

                if (player.getInventory().getFreeSlots() < 1) {
                    player.message("You need at least one slot free in your inventory.");
                    return true;
                }

                if (player.getClickDelay().elapsed(2000)) {
                    player.getClickDelay().reset();
                    player.animate(881);

                    //Reward random pkp
                    var amount = World.getWorld().random(1, 5);
                    player.inventory().add(new Item(PKP_TICKET, amount));
                    player.message("You've searched the chest and found "+amount+" PKP.");

                    if(World.getWorld().random(10) == 1) {
                        player.hit(player, World.getWorld().random(1, 5));
                        player.forceChat("OUCH!");
                        player.message("You hurt yourself whilst searching through the haunted chest.");
                    }

                    if(World.getWorld().rollDie(350,1)) {
                        Item item = Utils.randomElement(RANDOM_ITEMS);
                        player.inventory().add(item);
                        var plural = amount > 1 ? "x" + amount : "x1";
                        String worldMessage = "<img=452><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Haunted Chest</col>]</shad>:<col=AD800F> " + player.getUsername() + " received " + plural + " <shad=0>" + item.name() + "</shad>!";
                        World.getWorld().sendWorldMessage(worldMessage);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

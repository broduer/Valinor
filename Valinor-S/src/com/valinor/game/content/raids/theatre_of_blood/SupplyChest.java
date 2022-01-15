package com.valinor.game.content.raids.theatre_of_blood;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.ItemIdentifiers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.valinor.game.content.raids.theatre_of_blood.SupplyChest.Supplies.openSupplyChest;
import static com.valinor.game.content.raids.theatre_of_blood.SupplyChest.Supplies.takeSupply;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.CHEST_32758;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 15, 2022
 */
public class SupplyChest extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if(object.getId() == CHEST_32758) {
                openSupplyChest(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleItemContainerActionInteraction(Player player, Item item, int slot, int interfaceId, int type) {
        return takeSupply(player, item.getId(), slot);
    }

    enum Supplies {
        STAMINA_POTION(0, STAMINA_POTION4, 1, 12238),
        PRAYER_POTION(1, PRAYER_POTION4,2,12239),
        SARADOMIN_BREW(2, SARADOMIN_BREW4,3,12240),
        SUPER_RESTORE_POTION(3, SUPER_RESTORE4,3,12241),
        MUSHROOM_POTATO(4, ItemIdentifiers.MUSHROOM_POTATO,1,12242),
        SHARK(5, ItemIdentifiers.SHARK,1,12243),
        SEA_TURTLE(6, ItemIdentifiers.SEA_TURTLE,2,12244),
        MANTA_RAY(7, ItemIdentifiers.MANTA_RAY,2,12245);

        public int index, id, cost, costStringId;

        Supplies(int index, int id, int cost, int costStringId) {
            this.index = index;
            this.id = id;
            this.cost = cost;
            this.costStringId = costStringId;
        }

        private static final Map<Integer, Supplies> supplies = new HashMap<>();

        static {
            for (Supplies s : values()) {
                supplies.put(s.id, s);
            }
        }

        public static Supplies getById(int id) {
            return supplies.get(id);
        }

        public static void openSupplyChest(Player player) {
            final List<Item> supplies = Arrays.asList(new Item(STAMINA_POTION4), new Item(PRAYER_POTION4), new Item(SARADOMIN_BREW4), new Item(SUPER_RESTORE4),
                new Item(ItemIdentifiers.MUSHROOM_POTATO), new Item(ItemIdentifiers.SHARK), new Item(ItemIdentifiers.SEA_TURTLE), new Item(ItemIdentifiers.MANTA_RAY));

            //Load items on interface
            player.getPacketSender().sendItemOnInterface(12236, supplies);

            //Send cost
            for (Supplies supply : values()) {
                player.getPacketSender().sendString(supply.costStringId, Integer.toString(supply.cost));
            }

            //Send current points
            var currentPoints = player.<Integer>getAttribOr(AttributeKey.THEATRE_OF_BLOOD_POINTS,0);
            player.getPacketSender().sendString(12235, Integer.toString(currentPoints));

            //Open interface
            player.getInterfaceManager().open(12230);
        }

        public static boolean takeSupply(Player player, int id, int index) {
            Supplies supply = Supplies.getById(id);
            if(supply != null) {
                //Make sure item id and index both match
                if(supply.id == id && supply.index == index) {
                    var currentPoints = player.<Integer>getAttribOr(AttributeKey.THEATRE_OF_BLOOD_POINTS,0);
                    if(currentPoints > supply.cost) {
                        currentPoints = player.<Integer>getAttribOr(AttributeKey.THEATRE_OF_BLOOD_POINTS,0) - supply.cost;
                        player.putAttrib(AttributeKey.THEATRE_OF_BLOOD_POINTS, currentPoints);

                        //Update points frame
                        player.getPacketSender().sendString(12235, Integer.toString(currentPoints));

                        //Add to inv or drop to ground when no space
                        player.inventory().addOrDrop(new Item(supply.id));
                    } else {
                        player.message(Color.RED.wrap("You do not have enough points."));
                    }
                    return true;
                }
            }
            return false;
        }
    }
}

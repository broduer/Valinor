package com.valinor.game.content.items.water_containers;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;

import java.util.ArrayList;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 28, 2021
 */
public class WaterContainerActions extends Interaction {

    private void fillContainer(Player player, Item item, WaterContainers waterContainer, WaterContainers.WaterSource source) {
        /* Only buckets are able to be filled from a well */
        if (source == WaterContainers.WaterSource.WELL && waterContainer != WaterContainers.BUCKET) {
            player.message("If I drop my " + waterContainer.name + " down there, I don't think I'm likely to get it back.");
            return;
        }

        ArrayList<Item> containers = player.getInventory().collectItems(item.getId());
        containers.forEach(container -> player.inventory().replace(container.getId(), waterContainer.full, true));
        player.animate(832);
        player.sound(2609);
        player.message("You fill the " + waterContainer.name + " from the " + source.name().toLowerCase() + ".");
    }

    private static final int[] FULL_BUCKETS = {1783, 1927, 1929, 4286, 4687, 4693, 7622, 7624, 7626, 9659};

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        if (object.definition().name.equalsIgnoreCase("sink")) {
            player.faceObj(object);
            for (WaterContainers waterContainers : WaterContainers.values()) {
                if(waterContainers.empty == item.getId()) {
                    fillContainer(player, item, waterContainers, WaterContainers.WaterSource.SINK);
                    return true;
                }
            }
        }
        if (object.definition().name.equalsIgnoreCase("fountain")) {
            player.faceObj(object);
            for (WaterContainers waterContainers : WaterContainers.values()) {
                if(waterContainers.empty == item.getId()) {
                    fillContainer(player, item, waterContainers, WaterContainers.WaterSource.FOUNTAIN);
                    return true;
                }
            }
        }
        if (object.definition().name.equalsIgnoreCase("well")) {
            player.faceObj(object);
            for (WaterContainers waterContainers : WaterContainers.values()) {
                if(waterContainers.empty == item.getId()) {
                    fillContainer(player, item, waterContainers, WaterContainers.WaterSource.WELL);
                    return true;
                }
            }
        }
        if (object.definition().name.equalsIgnoreCase("tap")) {
            player.faceObj(object);
            for (WaterContainers waterContainers : WaterContainers.values()) {
                if(waterContainers.empty == item.getId()) {
                    fillContainer(player, item, waterContainers, WaterContainers.WaterSource.TAP);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 2) {
            for (WaterContainers waterContainers : WaterContainers.values()) {
                if (waterContainers.full == item.getId()) {
                    player.inventory().replace(waterContainers.full, waterContainers.empty,true);
                    player.message("You empty the contents of the " + waterContainers.name + " on the floor.");
                    return true;
                }
            }

            for(int id : FULL_BUCKETS) {
                if (id == item.getId()) {
                    player.inventory().replace(id, WaterContainers.BUCKET.empty,true);
                    player.message("You empty the contents of the bucket on the floor.");
                    return true;
                }
            }
        }
        return false;
    }
}

package com.valinor.game.content.skill.impl.smithing;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 25, 2022
 */
public class Anvil extends Interaction {

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        final String objName = object.definition().name;
        final String itemName = item.definition(World.getWorld()).name;
        if (objName != null && objName.equalsIgnoreCase("anvil")) {
            if(itemName != null && itemName.contains("bar")) {
                if (object.tile().equals(2794, 2793)) {
                    player.smartPathTo(object.tile());
                    player.waitUntil(1, () -> !player.getMovementQueue().isMoving(), () -> EquipmentMaking.openInterface(player));
                } else if (object.tile().equals(3343, 9652)) {
                    player.smartPathTo(object.tile());
                    player.waitUntil(1, () -> !player.getMovementQueue().isMoving(), () -> EquipmentMaking.openInterface(player));
                } else
                    EquipmentMaking.openInterface(player);
                return true;
            }
        }
        return false;
    }
}

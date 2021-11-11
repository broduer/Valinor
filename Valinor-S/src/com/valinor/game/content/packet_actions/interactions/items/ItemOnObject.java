package com.valinor.game.content.packet_actions.interactions.items;

import com.valinor.game.content.skill.impl.farming.Farming;
import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.PacketInteractionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.util.Unbox.box;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * mei 05, 2020
 */
public class ItemOnObject {

    private static final Logger logger = LogManager.getLogger(ItemOnObject.class);

    public static void itemOnObject(Player player, Item item, GameObject object) {
        //If the object doesn't exist, we probably shouldn't do anything about it.
        if (object == null) {
            return;
        }

        if (object.definition() == null) {
            logger.error("ObjectDefinition for object {} is null for player " + player.toString() + ".", box(object.getId()));
            return;
        }

        Tile tile = new Tile(object.getX(), object.getY(), player.tile().getZ());

        /*if (player.farming().handleItemOnObjectInteraction(object.getId(), item.getId(), object.getX(), object.getY())) {
            return;
        }*/

        if (PacketInteractionManager.checkItemOnObjectInteraction(player, item, object)) {
            return;
        }

        if (Farming.handleActions(player, FarmingConstants.ITEM_ON_OBJECT_ACTION, tile, item.getId())) {
            return;
        }

        player.message("Nothing interesting happens.");
    }
}

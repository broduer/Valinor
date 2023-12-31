package com.valinor.game.content.packet_actions.interactions.objects;

import com.valinor.game.content.events.shootingStars.ShootingStars;
import com.valinor.game.content.skill.impl.smithing.Bar;
import com.valinor.game.content.skill.impl.smithing.EquipmentMaking;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.InteractionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.valinor.util.ObjectIdentifiers.OPEN_CHEST_3194;
import static org.apache.logging.log4j.util.Unbox.box;

public class ObjectActions {

    private static final Logger logger = LogManager.getLogger(ObjectActions.class);

    /**
     * This method determines all the clicking actions for an npc.
     *
     * @param player      The player
     * @param object      The object
     * @param clickAction The menu action
     */
    public static void handleAction(Player player, GameObject object, int clickAction) {
        //Get object definition
        if (object.definition() == null) {
            logger.error("ObjectDefinition for object {} is null for player " + player.toString() + ".", box(object.getId()));
            return;
        }

        final String name = object.definition().name;
        final boolean bank = object.getId() == OPEN_CHEST_3194 || name.toLowerCase().contains("bank booth") || name.toLowerCase().contains("bank chest") || name.toLowerCase().contains("grand exchange booth");
        if (clickAction == 1) {
            if(name.equalsIgnoreCase("Bank deposit box")) {
                player.getDepositBox().open();
                return;
            }

            if (name.equalsIgnoreCase("anvil")) {
                if (object.tile().equals(2794, 2793)) {
                    player.smartPathTo(object.tile());
                    player.waitUntil(1, () -> !player.getMovementQueue().isMoving(), () -> EquipmentMaking.openInterface(player));
                }
                else if (object.tile().equals(3343, 9652)) {
                    player.smartPathTo(object.tile());
                    player.waitUntil(1, () -> !player.getMovementQueue().isMoving(), () -> EquipmentMaking.openInterface(player));
                }
                else
                    EquipmentMaking.openInterface(player);
                return;
            }

            // Areas
            if (player.getController() != null) {
                if (player.getController().handleObjectClick(player, object, 1)) {
                    return;
                }
            }

            if (bank) {
                player.getBank().open();
                return;
            }

            if (name.equalsIgnoreCase("furnace")) {
                for (Bar bar : Bar.values()) {
                    player.getPacketSender().sendInterfaceModel(bar.getFrame(),150, bar.getBar());
                }
                player.getPacketSender().sendChatboxInterface(2400);
                return;
            }

            if (name.equalsIgnoreCase("anvil")) {
                EquipmentMaking.openInterface(player);
                return;
            }

            if (InteractionManager.checkObjectInteraction(player, object, 1)) {
                return;
            }

            if(ShootingStars.getINSTANCE().handleObjectInteraction(player, object, 1)) {
                return;
            }

            player.getFarming().handleObjectClick(object.getId(), object.tile().x, object.tile().y, 1);
            return;
        }

        if (clickAction == 2) {
            // Areas
            if (player.getController() != null) {
                if (player.getController().handleObjectClick(player, object, 2)) {
                    //System.out.println("Area click object option 2.");
                    return;
                }
            }

            //System.out.println(name);
            if (bank) {
                //System.out.println("object option 2, opening bank.");
                player.getBank().open();
                return;
            }

            if (name.equalsIgnoreCase("furnace")) {
                //System.out.println("object option 2, furnace action.");
                for (Bar bar : Bar.values()) {
                    player.getPacketSender().sendInterfaceModel(bar.getFrame(), 150, bar.getBar());
                }
                player.getPacketSender().sendChatboxInterface(2400);
                return;
            }

            if (InteractionManager.checkObjectInteraction(player, object, 2)) {
                //System.out.println("object option 2, checkObjectInteraction.");
                return;
            }

            if(ShootingStars.getINSTANCE().handleObjectInteraction(player, object, 2)) {
                return;
            }

            player.getFarming().handleObjectClick(object.getId(), object.tile().x, object.tile().y, 2);
            return;
        }

        if (clickAction == 3) {

            if (name.equalsIgnoreCase("Grand Exchange booth")) {
                TradingPost.open(player);
                return;
            }

            if (player.getController() != null) {
                if (player.getController().handleObjectClick(player, object, 3)) {
                    return;
                }
            }

            if (InteractionManager.checkObjectInteraction(player, object, 3)) {
                return;
            }

            player.getFarming().handleObjectClick(object.getId(), object.tile().x, object.tile().y, 3);
            return;
        }

        if (clickAction == 4) {
            if (player.getController() != null) {
                if (player.getController().handleObjectClick(player, object, 4)) {
                    return;
                }
            }

            if (InteractionManager.checkObjectInteraction(player, object, 4)) {
                return;
            }

            return;
        }
    }
}

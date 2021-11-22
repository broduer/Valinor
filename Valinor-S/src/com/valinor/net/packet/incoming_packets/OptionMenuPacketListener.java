package com.valinor.net.packet.incoming_packets;

import com.valinor.game.content.option_menu.TeleportMenuHandler;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

/**
 * Handles selected options for the OptionMenu Interface.
 *
 * @author Lennard
 *
 */
public class OptionMenuPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) throws Exception {
        final int childId = packet.readInt();
        final int identifier = packet.readUnsignedByte();
        final int optionType = packet.readUnsignedByte();

        switch (childId) {

            case TeleportMenuHandler.TELEPORT_MENU_CHILD_ID:
                switch (optionType) {

                    // Teleport
                    case 0 -> player.getTeleportMenuHandler().handleTeleportClick(identifier);

                    // Add favorite
                    case 1 -> player.getTeleportMenuHandler().addFavoriteTeleport(identifier);
                    default -> System.out.println("TeleportMenu Unhandled Option Type!");
                }

                break;

            case TeleportMenuHandler.FAVORITE_MENU_CHILD_ID:
                switch (optionType) {

                    // Teleport
                    case 0 -> player.getTeleportMenuHandler().handleTeleportClick(identifier);

                    // Remove favorite
                    case 1 -> player.getTeleportMenuHandler().removeFavoriteTeleport(identifier);

                    // Move up favorite
                    case 2 -> player.getTeleportMenuHandler().moveUpFavoriteTeleport(identifier);

                    //Move down favorite
                    case 3 -> player.getTeleportMenuHandler().moveDownFavoriteTeleport(identifier);
                    default -> System.out.println("FavoriteMenu Unhandled Option Type!");
                }
                break;

            default:
                System.out.println("OptionMenu[childId: " + childId + " ,identifier: " + identifier + ", optionType: " + optionType + "] ");
                break;
        }
    }
}

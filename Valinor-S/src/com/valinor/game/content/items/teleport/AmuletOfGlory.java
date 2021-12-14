package com.valinor.game.content.items.teleport;

import com.valinor.GameServer;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;

import java.util.stream.IntStream;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | December, 28, 2020, 13:48
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class AmuletOfGlory extends Interaction {

    public static final int[] GLORY = new int[] {AMULET_OF_GLORY, AMULET_OF_GLORY1, AMULET_OF_GLORY2, AMULET_OF_GLORY3, AMULET_OF_GLORY4, AMULET_OF_GLORY5, AMULET_OF_GLORY6, AMULET_OF_ETERNAL_GLORY};

    private void teleport(Player player) {
        Tile tile = GameServer.properties().defaultTile;

        if (Teleports.canTeleport(player,true, TeleportType.ABOVE_20_WILD)) {
            Teleports.basicTeleport(player, tile);
            player.message("You have been teleported to home.");
        }
    }

    @Override
    public boolean handleEquipmentAction(Player player, Item item, int slot) {
        if(IntStream.of(GLORY).anyMatch(glory -> item.getId() == glory) && slot == EquipSlot.AMULET) {
            teleport(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 2) {
            if(IntStream.of(GLORY).anyMatch(glory -> item.getId() == glory)) {
                teleport(player);
                return true;
            }
        }
        return false;
    }
}

package com.valinor.game.content.teleport.tabs;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import java.util.Optional;

/**
 * Teletabs and scrolls are the teleportation methods to travel accross the world.
 *
 * @author Patrick van Elderen | Zerikoth (PVE) | 23 sep. 2019 : 13:42
 * @version 1.0
 * @see <a href="https://github.com/Patrick9-10-1995">Github profile</a>
 */
public class Tablet extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            return breakTablet(player, item.getId());
        }
        return false;
    }

    /**
     * Breaks the tele tab and teleports the player to the location
     *
     * @param player   The player teleporting
     * @param tabletId The tablet thats being broken
     */
    private boolean breakTablet(final Player player, int tabletId) {
        Optional<TabletData> tab = TabletData.getTab(tabletId);

        // Checks if the tab isn't present, if not perform nothing
        if (tab.isEmpty()) {
            return false;
        }

        //Handle present tab..
        if (player.inventory().contains(tab.get().getTablet())) {
            if (player.getClickDelay().elapsed(3000)) {
                if (tab.get().isScroll()) {
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tab.get().getTile(), 3864, new Graphic(1039,100));
                        player.inventory().remove(tab.get().getTablet());
                    }
                } else {
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tab.get().getTile(), 4731, new Graphic(678,0));
                        player.inventory().remove(tab.get().getTablet());
                    }
                }

            }
        }
        return true;
    }

}

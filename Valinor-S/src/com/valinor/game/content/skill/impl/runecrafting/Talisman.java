package com.valinor.game.content.skill.impl.runecrafting;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 30, 2022
 */
public class Talisman extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 2) {
            if(item.getId() == AIR_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2990,3289));
                }
                return true;
            }
            if(item.getId() == MIND_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2984,3520));
                }
                return true;
            }
            if(item.getId() == WATER_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3175,3167));
                }
                return true;
            }
            if(item.getId() == EARTH_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3301,3470));
                }
                return true;
            }
            if(item.getId() == FIRE_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3317,3251));
                }
                return true;
            }
            if(item.getId() == BODY_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3055,3441));
                }
                return true;
            }
            if(item.getId() == COSMIC_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2405,4381));
                }
                return true;
            }
            if(item.getId() == LAW_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2857,3377));
                }
                return true;
            }
            if(item.getId() == NATURE_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2874,3018));
                }
                return true;
            }
            if(item.getId() == CHAOS_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3062,3588));
                }
                return true;
            }
            if(item.getId() == DEATH_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(1863,4635));
                }
                return true;
            }
            if(item.getId() == WRATH_TALISMAN) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2448,2822));
                }
                return true;
            }
        }
        return false;
    }
}

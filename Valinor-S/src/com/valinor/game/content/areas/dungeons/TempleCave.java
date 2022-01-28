package com.valinor.game.content.areas.dungeons;

import com.valinor.game.content.packet_actions.interactions.objects.Ladders;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 27, 2022
 */
public class TempleCave extends Interaction {

    private void forge(Player player) {
        Item shard = player.getInventory().findItem(ZENYTE_SHARD);
        Item onyx = player.getInventory().findItem(ONYX);

        if (shard == null || onyx == null) {
            player.doubleItemBox("You need a <col=000080>cut onyx<col=000000> to fuse with a <col=000080>" +
                "zenyte shard<col=000000> to<br><col=000000>create an uncut zenyte.", ZENYTE_SHARD, ONYX);
            return;
        }

        player.optionsTitled("Are you sure you want to do this?", "Fuse your onyx and zenyte shard?", "Nevermind.", () -> {
            player.inventory().remove(shard, 1);
            player.inventory().remove(onyx, 1);
            player.inventory().add(ZENYTE, 1);
            player.message("You reach into the extremely hot flames and fuse the zenyte and onyx together,");
            player.message("forming an uncut zenyte.");
        });
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if(object.getId() == 4879) {
                player.lock();
                player.animate(827);

                Chain.bound(null).runFn(1, () -> {
                    object.setId(4880);
                    player.unlock();
                });
                return true;
            }

            if(object.getId() == 4880) {
                player.lock();
                player.message("You climb down the trapdoor.");
                Chain.bound(null).runFn(1, () -> {
                    Ladders.ladderDown(player, new Tile(2807, 9201, 0), true);
                    player.unlock();
                });
                return true;
            }

            if(object.getId() == 4881) {
                Chain.bound(null).runFn(1, () -> {
                    player.lock();
                    player.animate(828);
                }).then(1, () -> {
                    player.teleport(2806, 2785);
                    player.unlock();
                });
                return true;
            }
        }

        if(option == 2) {
            if(object.getId() == 4880) {
                player.lock();
                object.setId(4879);
                player.unlock();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        if(object.getId() == 4765 || object.getId() == 4766) {
            if(item.getId() == ZENYTE_SHARD || item.getId() == ONYX) {
                forge(player);
                return true;
            }
        }
        return false;
    }
}

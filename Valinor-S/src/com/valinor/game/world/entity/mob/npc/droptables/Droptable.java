package com.valinor.game.world.entity.mob.npc.droptables;

import com.valinor.game.content.announcements.ServerAnnouncements;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Tile;

import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.game.content.collection_logs.LogType.OTHER;
import static com.valinor.game.world.entity.AttributeKey.DOUBLE_DROP_LAMP_TICKS;
import static com.valinor.util.CustomItemIdentifiers.TREASURE_CASKET;

/**
 * Created by Bart on 10/6/2015.
 */
public interface Droptable {

    void reward(Npc npc, Player killer);

    default void drop(Npc npc, Player player, Item item) {
        drop(npc, npc.tile(), player, item);
    }

    default void drop(Npc npc, Tile tile, Player player, Item item) {
        if (player.nifflerPetOut() && player.nifflerCanStore(npc) && item.getValue() > 0) {
            if(tile.level != player.tile().level) {
                return;
            }
            player.nifflerStore(item);
        } else {
            GroundItemHandler.createGroundItem(new GroundItem(item, tile, player));
        }

        if (World.getWorld().rollDie(50, 1)) {
            drop(npc, new Tile(2262, 3072, player.tile().level), player, new Item(TREASURE_CASKET, 1));
            player.message("<col=0B610B>You have received a treasure casket drop!");
        }

        var doubleDropsLampActive = player.<Integer>getAttribOr(DOUBLE_DROP_LAMP_TICKS, 0) > 0;
        var founderImp = player.pet() != null && player.pet().def().name.equalsIgnoreCase("Founder Imp");
        var canDoubleDrop = doubleDropsLampActive || founderImp;
        if (canDoubleDrop) {
            var rolledDoubleDrop = World.getWorld().rollDie(10, 1);
            if (rolledDoubleDrop) {
                //Drop the item to the ground instead of editing the item instance
                GroundItem doubleDrop = new GroundItem(item, tile, player);

                if (player.nifflerPetOut() && player.nifflerCanStore(npc)) {
                    if(tile.level != player.tile().level) {
                        return;
                    }
                    player.nifflerStore(doubleDrop.getItem());
                } else {
                    GroundItemHandler.createGroundItem(doubleDrop);
                }
                player.message("The double drop effect doubled your drop.");
            }
        }
        ServerAnnouncements.tryBroadcastDrop(player, npc, item);
        BOSSES.log(player, npc.id(), item);
        OTHER.log(player, npc.id(), item);
    }

}

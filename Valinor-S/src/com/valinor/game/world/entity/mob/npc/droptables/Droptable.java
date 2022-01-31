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

        boolean doubleDropsLampActive = (Integer) player.getAttribOr(DOUBLE_DROP_LAMP_TICKS, 0) > 0;
        boolean founderImp = player.pet() != null && player.pet().def().name.equalsIgnoreCase("Founder Imp");
        boolean slayerPerk = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.DOUBLE_DROP_CHANCE) && World.getWorld().rollDie(100, 1);
        if (doubleDropsLampActive || founderImp || slayerPerk) {
            if(World.getWorld().rollDie(10, 1)) {
                item.setAmount(item.getAmount() * 2);
                player.message("Your drop was doubled.");
            }
        }
        ServerAnnouncements.tryBroadcastDrop(player, npc, item);
        BOSSES.log(player, npc.id(), item);
        OTHER.log(player, npc.id(), item);
    }

}

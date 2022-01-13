package com.valinor.game.world.entity.mob.npc.droptables.impl;

import com.valinor.game.content.announcements.ServerAnnouncements;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.NpcDeath;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.valinor.game.content.collection_logs.LogType.BOSSES;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 12, 2022
 */
public class GWDNex implements Droptable {

    private void drop(Mob mob) {
        mob.getCombat().getDamageMap().forEach((key, hits) -> {
            Player player = (Player) key;
            player.message(Color.RED.wrap("You've dealt " + hits.getDamage() + " damage to Nex!"));
            // Only people nearby are rewarded. This is to avoid people 'poking' the boss to do some damage
            // without really risking being there.
            if (mob.tile().isWithinDistance(player.tile(),10) && hits.getDamage() >= 100) {
                if(mob instanceof Npc) {
                    player.message("You received a drop roll from the table for dealing at least 100 damage!");
                    Npc npc = mob.getAsNpc();

                    //Always log kill timers
                    player.getBossTimers().submit(npc.def().name, (int) player.getCombat().getFightTimer().elapsed(TimeUnit.SECONDS), player);

                    //Always increase kill counts
                    player.getBossKillLog().addKill(npc);

                    //Random drop from the table
                    ScalarLootTable table = ScalarLootTable.forNPC(npc.id());
                    if (table != null) {
                        Item reward = table.randomItem(World.getWorld().random());
                        if (reward != null) {

                            // bosses, find npc ID, find item ID
                            BOSSES.log(player, npc.id(), reward);

                            GroundItemHandler.createGroundItem(new GroundItem(reward, npc.tile(), player));
                            ServerAnnouncements.tryBroadcastDrop(player, npc, reward);

                            Utils.sendDiscordInfoLog("Player " + player.getUsername() + " got drop item " + reward, "npcdrops");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void reward(Npc npc, Player killer) {
        drop(npc);
    }
}

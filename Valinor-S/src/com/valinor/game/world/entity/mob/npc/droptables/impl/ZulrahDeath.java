package com.valinor.game.world.entity.mob.npc.droptables.impl;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.NpcDeath;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;

import java.util.Optional;

import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.util.CustomItemIdentifiers.TREASURE_CASKET;
import static com.valinor.util.ItemIdentifiers.ZULRAHS_SCALES;

/**
 * @author Patrick van Elderen | January, 03, 2021, 14:37
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class ZulrahDeath implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var table = ScalarLootTable.forNPC(2042);
        if (table != null) {
            drop(npc, new Tile(2262, 3072, killer.tile().level), killer, new Item(ZULRAHS_SCALES, World.getWorld().random(100, 300)));

            Optional<Pet> pet = NpcDeath.checkForPet(killer, table);
            pet.ifPresent(value -> BOSSES.log(killer, npc.id(), new Item(value.item)));

            if (World.getWorld().rollDie(50, 1)) {
                drop(npc, new Tile(2262, 3072, killer.tile().level), killer, new Item(TREASURE_CASKET, 1));
                killer.message("<col=0B610B>You have received a treasure casket drop!");
            }

            var rolls = 2;
            var reward = table.randomItem(World.getWorld().random());
            for (int i = 0; i < rolls; i++) {
                if (reward != null) {
                    drop(npc, new Tile(2262, 3072, killer.tile().level), killer, reward);
                }
            }
        }
    }
}

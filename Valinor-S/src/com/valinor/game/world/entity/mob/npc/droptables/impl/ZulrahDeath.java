package com.valinor.game.world.entity.mob.npc.droptables.impl;

import com.valinor.game.content.announcements.ServerAnnouncements;
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
import static com.valinor.util.NpcIdentifiers.ZULRAH;

/**
 * @author Patrick van Elderen | January, 03, 2021, 14:37
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class ZulrahDeath implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var table = ScalarLootTable.forNPC(ZULRAH);
        if (table != null) {
            drop(npc, killer.tile(), killer, new Item(12934, 100 + World.getWorld().random(200)));
            drop(npc, killer.tile(), killer, new Item(12938, 1 + World.getWorld().random(1)));

            Optional<Pet> pet = NpcDeath.checkForPet(killer, table);
            pet.ifPresent(value -> BOSSES.log(killer, npc.id(), new Item(value.item)));

            var rolls = 2;
            for (int i = 0; i < rolls; i++) {
                var reward = table.randomItem(World.getWorld().random());
                if (reward != null) {
                    drop(npc, killer.tile(), killer, reward);
                }
            }
        }
    }
}

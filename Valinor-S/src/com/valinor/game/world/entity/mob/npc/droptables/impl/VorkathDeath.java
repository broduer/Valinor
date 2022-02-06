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

import java.util.Optional;

import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.util.ItemIdentifiers.BLUE_DRAGONHIDE;
import static com.valinor.util.ItemIdentifiers.SUPERIOR_DRAGON_BONES;
import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 06, 2022
 */
public class VorkathDeath implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var table = ScalarLootTable.forNPC(VORKATH_8061);
        if (table != null) {
            drop(npc, killer.tile(), killer, new Item(SUPERIOR_DRAGON_BONES, 2));
            drop(npc, killer.tile(), killer, new Item(BLUE_DRAGONHIDE, 2));

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

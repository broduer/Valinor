package com.valinor.game.world.entity.mob.npc.droptables.impl;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Utils;

import static com.valinor.util.NpcIdentifiers.WHIRLPOOL;

/**
 * @author Patrick van Elderen | January, 03, 2021, 14:50
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class CaveKrakenDeath implements Droptable {

    @Override
    public void reward(Npc killed, Player killer) {
        var table = ScalarLootTable.forNPC(493);
        if (table != null) {
            var reward = table.randomItem(World.getWorld().random());
            if (reward != null) {
                drop(killed, killer.tile(), killer, reward);
            }

            table.getGuaranteedDrops().forEach(tableItem -> {
                drop(killed, killer.tile(), killer, new Item(tableItem.id, Utils.random(tableItem.min, tableItem.max)));
            });
        }
        killed.transmog(WHIRLPOOL);
    }
}

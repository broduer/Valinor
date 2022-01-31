package com.valinor.game.world.entity.mob.npc.droptables.impl;

import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 31, 2022
 */
public class SuperiorSlayerDroptable implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var req = SlayerCreature.slayerReq(npc.id());
        var odds = (200.0 - (Math.pow((req + 55), 2.0) / 125.0));

        if (World.getWorld().rollDie((int)odds, 1)) {
            // Odds for the heart are 1/8
            if (World.getWorld().rollDie(8, 1)) {
                drop(npc, killer, new Item(IMBUED_HEART));
            } else {
                // Staves are 7/8 (7/16 each)
                if (World.getWorld().random().nextBoolean()) {
                    drop(npc, killer, new Item(MIST_BATTLESTAFF));
                } else {
                    drop(npc, killer, new Item(DUST_BATTLESTAFF));
                }
            }
        }
    }
}

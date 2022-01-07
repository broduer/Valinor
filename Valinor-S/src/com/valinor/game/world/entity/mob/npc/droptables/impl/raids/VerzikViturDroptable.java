package com.valinor.game.world.entity.mob.npc.droptables.impl.raids;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.player.Player;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 07, 2022
 */
public class VerzikViturDroptable implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var party = killer.raidsParty;

        if (party != null) {
            if (party.getLeader().getRaids() != null)
                party.getLeader().getRaids().complete(party);
        }
    }
}

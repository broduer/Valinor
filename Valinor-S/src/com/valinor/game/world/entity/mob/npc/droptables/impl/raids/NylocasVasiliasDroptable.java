package com.valinor.game.world.entity.mob.npc.droptables.impl.raids;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.player.Player;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 07, 2022
 */
public class NylocasVasiliasDroptable implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var party = killer.raidsParty;

        if (party != null) {
            var currentKills = party.getKills();
            party.setKills(currentKills + 1);
            party.teamMessage("<col=ef20ff>" + npc.def().name + " has been defeated!");

            //Progress to the next stage
            if (party.getKills() == 1) {
                party.setRaidStage(4);
                party.teamMessage("<col=ef20ff>You may now progress to the next room!");
                party.setKills(0);//Reset kills back to 0
            }
        }
    }
}

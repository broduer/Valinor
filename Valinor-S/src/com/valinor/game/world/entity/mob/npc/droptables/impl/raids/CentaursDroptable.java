package com.valinor.game.world.entity.mob.npc.droptables.impl.raids;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.player.Player;

/**
 * @author Patrick van Elderen | May, 10, 2021, 18:36
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class CentaursDroptable implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var party = killer.raidsParty;

        if (party != null) {
            var currentKills = party.getKills();
            party.setKills(currentKills + 1);
            party.teamMessage("<col=ef20ff>" + killer.getUsername() + " has killed a " + npc.def().name + ".");
            //System.out.println(party.getKills());

            //Progress to the next stage
            if (party.getKills() == 8) {
                party.setRaidStage(3);
                party.teamMessage("<col=ef20ff>You may now progress to the next room!");
                party.setKills(0);//Reset kills back to 0
            }
        }
    }
}

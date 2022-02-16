package com.valinor.game.world.entity.mob.npc.droptables.impl.raids;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ObjectIdentifiers.CRYSTAL_30017;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since October 30, 2021
 */
public class TektonDroptable implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var party = killer.raidsParty;

        if (party != null) {
            var currentKills = party.getKills();
            party.setKills(currentKills + 1);
            party.teamMessage("<col=ef20ff>"+npc.def().name+" has been defeated!");

            //Progress to the next stage
            if (party.getKills() == 1) {
                party.setRaidStage(2);
                party.teamMessage("<col=ef20ff>You may now progress to the next room!");
                party.setKills(0);//Reset kills back to 0
                /*party.getObject(CRYSTAL_30017, killer, party).animate(7506);
                Chain.bound(null).runFn(3, () -> {
                    party.removeObject(CRYSTAL_30017, killer, party);
                });*/
            }
        }
    }
}

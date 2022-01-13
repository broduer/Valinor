package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 13, 2022
 */
public class NexMinion extends Npc {

    private boolean hasNoBarrier;

    public NexMinion(int id, Tile tile) {
        super(id, tile);
        cantFollowUnderCombat(true);
        capDamage(0);
    }

    public void breakBarrier() {
        capDamage(-1);
        hasNoBarrier = true;
    }

    @Override
    public void sequence() {
        if (dead() || !hasNoBarrier)
            return;
        super.sequence();
        //TODO check with Jak what this is suppose to be
        /*if (!getCombat().process())
            checkAgressivity();*/
    }

    @Override
    public void die(Hit killHit) {
        ZarosGodwars.moveNextStage();
        super.die(killHit);
    }
}

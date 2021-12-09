package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;
import com.valinor.util.timers.TimerKey;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 05, 2021
 */
public class Parasite extends Npc {

    public Parasite(int id, Tile tile) {
        super(id, tile);
    }

    @Override
    public void sequence() {
        super.sequence();
        if (getCombat().getTarget() != null && !getTimers().has(TimerKey.COMBAT_ATTACK)) {
            getCombat().attack(getCombat().getTarget());
        }
    }
}

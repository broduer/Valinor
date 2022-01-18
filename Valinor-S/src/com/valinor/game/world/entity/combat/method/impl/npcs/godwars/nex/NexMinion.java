package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;

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
        Mob target = null;
        if(getCombatMethod() != null) {
            if (getCombatMethod() instanceof CommonCombatMethod) {
                CommonCombatMethod method = (CommonCombatMethod) getCombatMethod();
                method.set(this, null);
                target = Utils.randomElement(method.getPossibleTargets(this));
            }
        }
        if(target != null)
            getCombat().setTarget(target);
    }

    @Override
    public void die(Hit killHit) {
        ZarosGodwars.moveNextStage();
        super.die(killHit);
    }
}

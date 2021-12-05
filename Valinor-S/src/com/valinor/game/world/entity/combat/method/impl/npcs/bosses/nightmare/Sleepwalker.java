package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.DumbRoute;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 05, 2021
 */
public class Sleepwalker extends Npc {

    public Sleepwalker(int id, Tile tile) {
        super(id, tile);
    }

    private Nightmare nm;

    public Nightmare getNm() {
        return nm;
    }

    public void setNm(Nightmare nm) {
        this.nm = nm;
    }

    //TODO
    /*@Override
    public int hit(Hit... hits) {
        for (Hit h : hits) {
            h.damage = 10;
            h.minDamage = 10;
            h.maxDamage = 10;
        }
        return super.hit(hits);
    }*/

    @Override
    public void sequence() {
        super.sequence();
        if (nm != null) {
            DumbRoute.route(this, nm.getCentrePosition().getX(), nm.getCentrePosition().getY());
            if (tile().distance(nm.getCentrePosition()) < 4) {
                //TODO hit(new Hit().fixedDamage(100));
                nm.setSleepwalkerCount(nm.getSleepwalkerCount() + 1);
            }
        }
    }
}

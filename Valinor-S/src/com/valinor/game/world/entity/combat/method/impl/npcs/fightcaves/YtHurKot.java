package com.valinor.game.world.entity.combat.method.impl.npcs.fightcaves;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen | December, 23, 2020, 14:35
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class YtHurKot extends Npc {

    private TzTokJad tztokJad;

    protected YtHurKot(int id, Tile tile, TzTokJad tztokJad) {
        super(id, tile);
        this.tztokJad = tztokJad;
    }

    @Override
    public void sequence() {
        super.sequence();

        if (tztokJad == null) {
            return;
        }

        if (dead()) {
            return;
        }

        long lastTime = System.currentTimeMillis() - (long) getAttribOr(AttributeKey.LAST_WAS_ATTACKED_TIME, 0L);
        if (lastTime < 6000L) {
            getCombat().reset();//Last attack was 6 seconds ago, reset combat
        }

        if (CombatFactory.lastAttacker(this) != null || getCombat().getTarget() != null) {
            return;
        }

        if (getInteractingEntity() != tztokJad) {
            setEntityInteraction(tztokJad);
        }

        var cycles = 0;
        if (this.tile.isWithinDistance(this, tztokJad,1)) {
            this.face(tztokJad.tile());
            if (cycles % 4 == 0) {
                tztokJad.graphic(444, 250, 5);
                tztokJad.heal(World.getWorld().random(10));
                this.animate(2639);
            }
        } else {
            getRouteFinder().routeAbsolute(tztokJad.tile().x, tztokJad.tile().y);
        }
    }

    @Override
    public void die(Hit killHit) {
        if (tztokJad != null) {
            tztokJad.removeHealer(this);
        }
        tztokJad = null;
        super.die(killHit);
    }


}

package com.valinor.game.content.raids;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;

/**
 * An NPC with raids attributes.
 *
 * @author Patrick van Elderen | May, 10, 2021, 16:16
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class RaidsNpc extends Npc {

    public static final double BONUS_HP_PER_PLAYER = 0.40; // %increased hp for each players beyond the first

    public RaidsNpc(int id, Tile tile, int partySize, boolean scaleDown) {
        super(id, tile);
        this.respawns(false);
        this.combatInfo().aggroradius = 15;
        this.walkRadius(15);
        if(scaleDown) {
            double percentage;
            if(partySize == 5) {
                percentage = 100;
            } else if(partySize == 4) {
                percentage = 87.5;
            } else {
                percentage = 75;
            }
            int newHp = (int) (this.maxHp() * percentage / 100);
            this.combatInfo().stats.hitpoints = newHp;
            this.setHitpoints(newHp);
        } else {
            this.setHitpoints((int) (this.hp() * (1 + (BONUS_HP_PER_PLAYER * (partySize - 1)))));
        }
    }
}

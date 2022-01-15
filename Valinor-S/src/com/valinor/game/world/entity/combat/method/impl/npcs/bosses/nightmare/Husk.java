package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 27, 2021
 */
public class Husk extends Npc {

    private Player player;
    private Nightmare nm;

    public Husk(int id, Tile tile, Player target, Nightmare nm) {
        super(id, tile);
        this.player = target;
        this.nm = nm;
    }

    @Override
    public void sequence() {
        super.sequence();
        if (getCombat().getTarget() == null) {
            getCombat().setTarget(player);
            face(player.tile());
        }
        if (tile().distance(nm.getBase()) > 64) {
            // stop bug where husks are appearing outside instance following the player. no idea how.
            getCombat().reset();
            remove();
        }
    }
}

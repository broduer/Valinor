package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 05, 2021
 */
public class Spore extends GameObject {

    int state = -1;

    public Spore(Tile tile) {
        super(37738, tile, 3, 10, 0);
        spawn();
        check();
    }

    private void check() {
        if (state == 2) {
            remove();
            return;
        } else if (state > -1) {
            state++;
        }
        Chain.bound(null).runFn(1, () -> {
            if (state == -1) {
                for (Player p : World.getWorld().getPlayers()) {
                    if (p.tile().distance(new Tile(getX(), getY(), getZ())) < 2) {
                        p.putAttrib(AttributeKey.NIGHTMARE_SPORE, System.currentTimeMillis() + 30000);
                        animate(definition().anInt2281 + 1);
                        state = 0;
                    }
                }
            }
            check();
        });
    }

}

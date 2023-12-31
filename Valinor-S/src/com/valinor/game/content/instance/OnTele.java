package com.valinor.game.content.instance;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen | June, 28, 2021, 15:02
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
@FunctionalInterface
public interface OnTele {
    void accept(Player player, Tile target);
}

package com.valinor.game.world.entity.mob.npc.droptables.impl;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.player.Player;

import static com.valinor.game.world.entity.AttributeKey.GWD_ZAMORAK_KC;

/**
 * @author Patrick van Elderen | April, 29, 2021, 14:24
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class GWDZamorakMinion implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var current = killer.<Integer>getAttribOr(GWD_ZAMORAK_KC, 0) + 1;
        if (current < 2000) {
            killer.putAttrib(GWD_ZAMORAK_KC, current);
        }
    }
}

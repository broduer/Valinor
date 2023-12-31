package com.valinor.game.world.entity.mob.npc.aggressive;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.AggressionCheck;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since October 19, 2021
 */
public class MenInBlackAgro implements AggressionCheck {

    @Override
    public boolean shouldAgro(Mob mob, Mob victim) {
        return false;
    }
}

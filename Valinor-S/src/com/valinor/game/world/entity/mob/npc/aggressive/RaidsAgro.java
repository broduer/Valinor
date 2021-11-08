package com.valinor.game.world.entity.mob.npc.aggressive;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.AggressionCheck;

public class RaidsAgro implements AggressionCheck {

    @Override
    public boolean shouldAgro(Mob mob, Mob victim) {
        return true;
    }
}

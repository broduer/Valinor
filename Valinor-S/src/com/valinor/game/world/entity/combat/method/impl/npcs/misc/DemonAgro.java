package com.valinor.game.world.entity.combat.method.impl.npcs.misc;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.AggressionCheck;

public class DemonAgro implements AggressionCheck {
    @Override
    public boolean shouldAgro(Mob mob, Mob victim) {
        return true;
    }
}

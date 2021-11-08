package com.valinor.game.world.entity.mob.npc;

import com.valinor.game.world.entity.Mob;

public interface AggressionCheck {

    boolean shouldAgro(Mob mob, Mob victim);

}

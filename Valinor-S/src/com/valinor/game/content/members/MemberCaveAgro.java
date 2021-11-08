package com.valinor.game.content.members;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.AggressionCheck;

public class MemberCaveAgro implements AggressionCheck {

    @Override
    public boolean shouldAgro(Mob mob, Mob victim) {
        return !mob.tile().memberCave();
    }
}

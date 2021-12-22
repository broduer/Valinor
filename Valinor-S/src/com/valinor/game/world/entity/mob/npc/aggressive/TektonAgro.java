package com.valinor.game.world.entity.mob.npc.aggressive;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.AggressionCheck;
import com.valinor.util.NpcIdentifiers;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 21, 2021
 */
public class TektonAgro implements AggressionCheck {

    @Override
    public boolean shouldAgro(Mob mob, Mob victim) {
        return mob.isNpc() && mob.getAsNpc().id() != NpcIdentifiers.TEKTON && mob.getAsNpc().id() != NpcIdentifiers.TEKTON_7545 && !mob.locked();
    }
}

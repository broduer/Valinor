package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.wilderness;

import com.valinor.game.content.mechanics.Poison;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.AggressionCheck;

/**
 * @author Patrick van Elderen | February, 24, 2021, 19:03
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class ScorpiaOffspringAgro implements AggressionCheck {

    @Override
    public boolean shouldAgro(Mob mob, Mob victim) {
        return Poison.poisoned(victim);
    }
}

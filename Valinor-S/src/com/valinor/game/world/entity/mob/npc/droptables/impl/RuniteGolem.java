package com.valinor.game.world.entity.mob.npc.droptables.impl;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.NpcIdentifiers;
import com.valinor.util.Tuple;

/**
 * @author Patrick van Elderen | January, 29, 2021, 09:54
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class RuniteGolem implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var trunk = new Npc(NpcIdentifiers.ROCKS_6601, npc.tile());
        World.getWorld().registerNpc(trunk);
        trunk.putAttrib(AttributeKey.OWNING_PLAYER, new Tuple<>(killer.getIndex(), killer));

        trunk.runUninterruptable(100, () -> {
            if (!trunk.dead()) { // Lives for exactly a minute
                World.getWorld().unregisterNpc(trunk);
            }
        });
    }
}

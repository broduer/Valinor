package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 27, 2021
 */
public class TotemPlugin extends Npc {

    private Nightmare nightmare;

    private boolean chargeable, charged;

    private int hitDelta = -1, spawnId;

    public TotemPlugin(int id, Tile spawn) {
        super(id, spawn);
        spawnId = id;
        spawn(false);
        lock();
        //TODO skipMovementCheck = true;
    }

}

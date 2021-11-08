package com.valinor.game.world.entity.mob.npc.impl;

import com.valinor.game.world.entity.Entity;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;
import com.valinor.util.NpcIdentifiers;

public class UndeadMaxHitDummy extends Npc {

    public UndeadMaxHitDummy(int id, Tile tile) {
        super(NpcIdentifiers.UNDEAD_COMBAT_DUMMY, tile);
    }

    @Override
    public Mob face(Tile positionToFace) {
        return this;
    }

    @Override
    public Mob setEntityInteraction(Entity entity) {
        return this;
    }

    @Override
    public Npc setHitpoints(int hitpoints) {
        return this;
    }

    @Override
    public void sequence() {
        getCombat().process();
    }

}

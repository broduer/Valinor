package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.wilderness.vetion;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.util.Utils;

import static com.valinor.util.NpcIdentifiers.SKELETON_HELLHOUND_6613;

/**
 * @author Patrick van Elderen | Zerikoth | PVE
 * @date maart 19, 2020 16:52
 */
public class VetionMinion extends Npc {

    public VetionMinion(Npc vetion, Mob target) {
        super(SKELETON_HELLHOUND_6613, vetion.tile());
        this.putAttrib(AttributeKey.BOSS_OWNER, vetion);
        this.setTile(vetion.tile().transform(Utils.random(3), -1));
        this.walkRadius(8);
        this.respawns(false);
        this.getCombat().attack(target);
    }
}

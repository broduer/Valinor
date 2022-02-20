package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.hunleff.corrupt;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.CombatMethod;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;

import static com.valinor.game.world.entity.combat.method.impl.npcs.bosses.hunleff.corrupt.CorruptedHunleff.Phase.*;
import static com.valinor.util.NpcIdentifiers.*;

public class CorruptedHunleff extends Npc {

    public CombatAI getCombatAI() {
        return combatAI;
    }

    private final CombatAI combatAI;
    private Phase phase;

    public CorruptedHunleff(int id, Tile tile) {
        super(id, tile);
        this.phase = Phase.forId(id);
        this.combatAI = new CombatAI(this);
        this.setCombatMethod(combatAI);
    }

    enum Phase {
        MELEE(CORRUPTED_HUNLLEF, CombatType.MELEE, new CorruptedHunleffCombatStrategy()),
        RANGED(CORRUPTED_HUNLLEF_9036, CombatType.RANGED, new CorruptedHunleffCombatStrategy()),
        MAGIC(CORRUPTED_HUNLLEF_9037, CombatType.MAGIC, new CorruptedHunleffCombatStrategy());

        public final int npcId;
        public final CombatMethod method;
        public final CombatType type;

        Phase(int npcId, CombatType type, CombatMethod method) {
            this.npcId = npcId;
            this.type = type;
            this.method = method;
        }

        public static Phase forId(int id) {
            for (Phase phase : values()) {
                if (phase.npcId == id)
                    return phase;
            }
            return null;
        }
    }

    public static class CombatAI extends CommonCombatMethod {

        private final CorruptedHunleff corruptedHunleff;
        private CombatMethod currentMethod;
        private int attacksCounter;

        public CombatAI(CorruptedHunleff corruptedHunleff) {
            this.corruptedHunleff = corruptedHunleff;
            currentMethod = corruptedHunleff.phase.method;
        }

        void updatePhase(Phase phase) {
            corruptedHunleff.phase = phase;
            currentMethod = phase.method;
            corruptedHunleff.transmog(phase.npcId);
            corruptedHunleff.getCombat().delayAttack(1);
            target.putAttrib(AttributeKey.HUNLESS_PREVIOUS_STYLE, phase);
        }

        public void attacksDone() {
            attacksCounter += 1;
            //System.out.println("attks "+attacksCounter);
            if (attacksCounter >= 4) {
                Phase old = target.getAttribOr(AttributeKey.HUNLESS_PREVIOUS_STYLE, MELEE);
                //System.out.println("previous phase: "+old);
                if(old == MELEE) {
                    updatePhase(Phase.RANGED);
                } else if(old == RANGED) {
                    updatePhase(MAGIC);
                } else if(old == MAGIC) {
                    updatePhase(MELEE);
                }
                attacksCounter = 0;
            }
        }

        @Override
        public void prepareAttack(Mob mob, Mob target) {
            attacksDone();
            currentMethod.prepareAttack(mob, target);
        }

        @Override
        public int getAttackSpeed(Mob mob) {
            return currentMethod.getAttackSpeed(mob);
        }

        @Override
        public int getAttackDistance(Mob mob) {
            return currentMethod.getAttackDistance(mob);
        }
    }
}

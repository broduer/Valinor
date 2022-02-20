package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.hunleff;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.CombatMethod;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;

import static com.valinor.game.world.entity.combat.method.impl.npcs.bosses.hunleff.CrystallineHunleff.Phase.*;
import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 20, 2022
 */
public class CrystallineHunleff extends Npc {

    public CombatAI getCombatAI() {
        return combatAI;
    }

    private final CombatAI combatAI;
    private Phase phase;

    public CrystallineHunleff(int id, Tile tile) {
        super(id, tile);
        this.phase = Phase.forId(id);
        this.combatAI = new CombatAI(this);
        this.setCombatMethod(combatAI);
    }

    enum Phase {
        MELEE(CRYSTALLINE_HUNLLEF, CombatType.MELEE, new CrystallineHunleffCombatStrategy()),
        RANGED(CRYSTALLINE_HUNLLEF_9022, CombatType.RANGED, new CrystallineHunleffCombatStrategy()),
        MAGIC(CRYSTALLINE_HUNLLEF_9023, CombatType.MAGIC, new CrystallineHunleffCombatStrategy());

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

        private final CrystallineHunleff crystallineHunleff;
        private CombatMethod currentMethod;
        private int attacksCounter;

        public CombatAI(CrystallineHunleff crystallineHunleff) {
            this.crystallineHunleff = crystallineHunleff;
            currentMethod = crystallineHunleff.phase.method;
        }

        void updatePhase(Phase phase) {
            crystallineHunleff.phase = phase;
            currentMethod = phase.method;
            crystallineHunleff.transmog(phase.npcId);
            crystallineHunleff.getCombat().delayAttack(1);
            target.putAttrib(AttributeKey.HUNLESS_PREVIOUS_STYLE, phase);
        }

        public void attacksDone() {
            attacksCounter += 1;
            //System.out.println("attks "+attacksCounter);
            if (attacksCounter >= 4) {
                Phase old = target.getAttribOr(AttributeKey.HUNLESS_PREVIOUS_STYLE, MELEE);
                //System.out.println("previous phase: "+old);
                if(old == MELEE) {
                    updatePhase(RANGED);
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

package com.valinor.game.world.entity.combat.method;

import com.valinor.game.world.entity.Mob;

/**
 * Represents a combat method.
 */
public interface CombatMethod {

    void prepareAttack(Mob mob, Mob target);
    int getAttackSpeed(Mob mob);
    int getAttackDistance(Mob mob);
    default boolean customOnDeath(Mob mob) {
        return false;
    }
    default boolean canMultiAttackInSingleZones() { return false; }
}

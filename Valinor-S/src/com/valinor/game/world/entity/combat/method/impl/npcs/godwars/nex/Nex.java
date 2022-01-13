package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.position.Area;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 12, 2022
 */
public class Nex extends CommonCombatMethod {

    public static final Area NEX_AREA = new Area(2911, 5189, 2939, 5217);

    @Override
    public void prepareAttack(Mob mob, Mob target) {

    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 20;
    }
}

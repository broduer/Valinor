package com.valinor.game.world.entity.combat.method.impl.npcs.slayer;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;

public class CaveKraken extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        new Projectile(mob, target, 162, 32, 65, 30, 30, 0).sendProjectile();
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target,CombatType.MAGIC), 2, CombatType.MAGIC).checkAccuracy().submit();
        target.delayedGraphics(new Graphic(163),2);
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getAsNpc().getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 15;
    }
}

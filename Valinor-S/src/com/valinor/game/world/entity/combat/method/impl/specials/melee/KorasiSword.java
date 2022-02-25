package com.valinor.game.world.entity.combat.method.impl.specials.melee;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.CombatMethod;
import com.valinor.game.world.entity.masks.graphics.Graphic;

/**
 * @author Zerikoth
 * @Since oktober 07, 2020
 */
public class KorasiSword implements CombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.animate(1058);
        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE),1, CombatType.MELEE).graphic(new Graphic(1798, 30, 0)).checkAccuracy();
        hit.submit();
        CombatSpecial.drain(mob, CombatSpecial.KORASI_SWORD.getDrainAmount());
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;
    }
}

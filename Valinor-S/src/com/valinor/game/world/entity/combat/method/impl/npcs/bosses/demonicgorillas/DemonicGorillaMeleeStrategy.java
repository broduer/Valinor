package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.demonicgorillas;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.demonicgorillas.enraged.EnragedDemonicGorilla;

import static com.valinor.util.CustomNpcIdentifiers.ENRAGED_GORILLA_MELEE;

/**
 * @author Patrick van Elderen | March, 13, 2021, 22:10
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class DemonicGorillaMeleeStrategy extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        //mob.forceChat("MELEE!");
        mob.animate(mob.attackAnimation());
        if (mob.isNpc()) {
            if (mob.getAsNpc().id() == 7144)
                target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 1, CombatType.MELEE).checkAccuracy().postDamage(h -> ((DemonicGorilla) mob).getCombatAI().handleAfterHit(h)).submit();
            else if (mob.getAsNpc().id() == ENRAGED_GORILLA_MELEE)
                target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 1, CombatType.MELEE).checkAccuracy().postDamage(h -> ((EnragedDemonicGorilla) mob).getCombatAI().handleAfterHit(h)).submit();
        }
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

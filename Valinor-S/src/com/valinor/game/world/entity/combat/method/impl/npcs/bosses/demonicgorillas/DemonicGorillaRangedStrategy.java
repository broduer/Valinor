package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.demonicgorillas;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.demonicgorillas.enraged.EnragedDemonicGorilla;
import com.valinor.game.world.entity.masks.Projectile;

import static com.valinor.util.CustomNpcIdentifiers.ENRAGED_GORILLA_MELEE;
import static com.valinor.util.CustomNpcIdentifiers.ENRAGED_GORILLA_RANGE;

/**
 * @author Patrick van Elderen | March, 13, 2021, 22:10
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class DemonicGorillaRangedStrategy extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        //mob.forceChat("RANGED!");
        mob.animate(7227);
        var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
        var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
        Projectile projectile = new Projectile(mob, target, 1302, 35, 25 * tileDist, 45, 30, 0);
        projectile.sendProjectile();
        if (mob.isNpc()) {
            if (mob.getAsNpc().id() == 7145)
                target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().postDamage(h -> ((DemonicGorilla)mob).getCombatAI().handleAfterHit(h)).submit();
            else if (mob.getAsNpc().id() == ENRAGED_GORILLA_RANGE)
                target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().postDamage(h -> ((EnragedDemonicGorilla)mob).getCombatAI().handleAfterHit(h)).submit();
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 6;
    }
}

package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.demonicgorillas;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.demonicgorillas.enraged.EnragedDemonicGorilla;
import com.valinor.game.world.entity.masks.Projectile;

import static com.valinor.util.CustomNpcIdentifiers.ENRAGED_GORILLA_MAGIC;
import static com.valinor.util.CustomNpcIdentifiers.ENRAGED_GORILLA_RANGE;

/**
 * @author Patrick van Elderen | March, 13, 2021, 22:10
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class DemonicGorillaMagicStrategy extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        //mob.forceChat("MAGIC!");
        var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
        var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
        Projectile projectile = new Projectile(mob, target, 1304, 40,25 * tileDist, 10, 10, 0);
        projectile.sendProjectile();
        if (mob.isNpc()) {
            if (mob.getAsNpc().id() == 7146)
                target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().postDamage(h -> ((DemonicGorilla)mob).getCombatAI().handleAfterHit(h)).submit();
            else if (mob.getAsNpc().id() == ENRAGED_GORILLA_MAGIC)
                target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().postDamage(h -> ((EnragedDemonicGorilla)mob).getCombatAI().handleAfterHit(h)).submit();
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

package com.valinor.game.world.entity.combat.method.impl.npcs.bosses;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 05, 2022
 */
public class ChaoticNightmare extends CommonCombatMethod {

    private static final int MAGIC_ANIMATION = 8595, RANGE_ANIMATION = 8596, MAGIC_PROJECTILE = 1764, RANGED_PROJECTILE = 1766;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (World.getWorld().random(10) > 5) {
            for (Mob victim : getPossibleTargets(mob)) {
                mob.animate(MAGIC_ANIMATION);
                mob.getCombat().setTarget(victim);
                Chain.bound(null).runFn(1, () -> {
                    Projectile pr = new Projectile(mob, victim, MAGIC_PROJECTILE, 30, 66, 110, 90, 0);
                    pr.sendProjectile();
                });
                final Tile dest = victim.tile();
                int delay;
                if (mob.getCentrePosition().distance(dest) == 1) {
                    delay = 60;
                } else if (mob.getCentrePosition().distance(dest) <= 5) {
                    delay = 80;
                } else if (mob.getCentrePosition().distance(dest) <= 8) {
                    delay = 100;
                } else {
                    delay = 120;
                }
                victim.hit(mob, CombatFactory.calcDamageFromType(mob, victim, CombatType.MAGIC), (delay / 20) - 1, CombatType.MAGIC).checkAccuracy().submit();
            }
        } else {
            for (Mob victim : getPossibleTargets(mob)) {
                int delay;
                mob.animate(RANGE_ANIMATION);
                mob.getCombat().setTarget(victim);
                Chain.bound(null).runFn(1, () -> {
                    Projectile pr = new Projectile(mob, victim, RANGED_PROJECTILE, 30, 66, 110, 90, 0);
                    pr.sendProjectile();
                });
                final Tile dest = victim.tile();
                if (mob.getCentrePosition().distance(dest) == 1) {
                    delay = 60;
                } else if (mob.getCentrePosition().distance(dest) <= 5) {
                    delay = 80;
                } else if (mob.getCentrePosition().distance(dest) <= 8) {
                    delay = 100;
                } else {
                    delay = 120;
                }
                victim.hit(mob, CombatFactory.calcDamageFromType(mob, victim, CombatType.RANGED), (delay / 20) - 1, CombatType.RANGED).checkAccuracy().submit();
            }
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 12;
    }
}

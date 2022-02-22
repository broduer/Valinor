package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.custom;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

import java.util.Arrays;
import java.util.List;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 21, 2022
 */
public class BloodFuryHespori extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (withinDistance(2) && World.getWorld().rollDie(2, 1)) {
            meleeAttack(mob, target);
        } else {
            rangeAttack(mob, target);
        }

        if(World.getWorld().rollDie(5, 1)) {
            groundAttack(mob, target);
        }
    }

    //Melee distance always take damage
    private void meleeAttack(Mob mob, Mob target) {
        mob.animate(8224);
        target.hit(mob, World.getWorld().random(1, Prayers.usingPrayer(target, Prayers.PROTECT_FROM_MELEE) ? 13 : 25));
    }

    private void rangeAttack(Mob mob, Mob target) {
        mob.animate(8221);
        var delay = mob.getProjectileHitDelay(target);
        new Projectile(mob, target, 73, 25, mob.projectileSpeed(target), 100, 21, 0).sendProjectile();
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().submit();
    }

    private void groundAttack(Mob mob, Mob target) {
        mob.animate(8223);

        Tile base = target.tile().copy();
        final List<Tile> plants = Arrays.asList(base, base.transform(2,1), base.transform(1,2), base.transform(2,2));

        Chain.bound(null).cancelWhen(mob::dead).thenCancellable(1, () -> {
            for (Tile tile : plants) {
                World.getWorld().tileGraphic(617, tile, 0,0);
            }
        }).thenCancellable(5, () -> {
            for (Tile tile : plants) {
                if(tile.area(1).contains(target)) {
                    target.graphic(618);
                    target.freeze(5, mob);
                    target.hit(mob, World.getWorld().random(1, Prayers.usingPrayer(target, Prayers.PROTECT_FROM_MAGIC) ? 7 : 25));
                }
            }
        });
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 5;
    }
}

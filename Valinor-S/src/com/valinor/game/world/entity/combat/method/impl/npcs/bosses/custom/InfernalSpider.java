package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.custom;

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
 * @Since February 21, 2022
 */
public class InfernalSpider extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (withinDistance(2) && World.getWorld().rollDie(2, 1)) {
            meleeAttack(mob, target);
        } else {
            magicAttack(mob, target);
        }

        if(World.getWorld().rollDie(5,1)) {
            lavaAttack(mob, target);
        }
    }

    private void meleeAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
    }

    private void magicAttack(Mob mob, Mob target) {
        mob.graphic(1464);
        mob.animate(5322);
        var delay = mob.getProjectileHitDelay(target);
        new Projectile(mob, target, 1465, 25, mob.projectileSpeed(target), 23, 21, 0).sendProjectile();
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
        target.delayedGraphics(1466,50, delay);
    }

    private void lavaAttack(Mob mob, Mob target) {
        Tile tile = target.tile().copy();
        Projectile projectile = new Projectile(mob.tile(), tile, -1, 660, 125, 0, 0, 0, 0);
        projectile.sendProjectile();
        Chain.bound(null).runFn(5, () -> {
            if (mob.dead() || !mob.isRegistered() || target == null)
                return;
            World.getWorld().tileGraphic(659, tile, 0, 0);
            if(tile.area(1).contains(target)) {
                target.hit(mob, World.getWorld().random(1, 12));
            }
        });
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 8;
    }
}

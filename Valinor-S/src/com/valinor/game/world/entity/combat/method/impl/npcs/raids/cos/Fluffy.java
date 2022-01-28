package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cos;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

/**
 * @author Patrick van Elderen | May, 12, 2021, 13:06
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class Fluffy extends CommonCombatMethod {

    private void lighting(Mob mob) {
        //mob.forceChat("LIGHTNING");
        mob.animate(4494);

        for (Mob t : getPossibleTargets(mob, 20, true,false)) {
            final var tile1 = t.tile().copy();
            final var tile2 = t.tile().copy().transform(1, 0);
            final var tile3 = t.tile().copy().transform(1, 1);

            new Projectile(mob.getCentrePosition(), tile1, 1, 731, 125, 40, 25, 0, 0, 16, 96).sendProjectile();
            new Projectile(mob.getCentrePosition(), tile2, 1, 731, 125, 40, 25, 0, 0, 16, 96).sendProjectile();
            new Projectile(mob.getCentrePosition(), tile3, 1, 731, 125, 40, 25, 0, 0, 16, 96).sendProjectile();
            Chain.bound(null).runFn(5, () -> {
                World.getWorld().tileGraphic(83, tile1, 0, 0);
                World.getWorld().tileGraphic(83, tile2, 0, 0);
                World.getWorld().tileGraphic(83, tile3, 0, 0);
                if (t.tile().equals(tile1) || t.tile().equals(tile2) || t.tile().equals(tile3)) {
                    t.hit(mob, World.getWorld().random(1, 23), CombatType.MAGIC).submit();
                }
            });
        }

        mob.getTimers().register(TimerKey.COMBAT_ATTACK, 6);
    }

    private void rangeAttack(Mob mob) {
        //mob.forceChat("RANGE");
        mob.resetFaceTile(); // Stop facing the target
        mob.animate(4492);
        for (Mob t : getPossibleTargets(mob, 20, true,false)) {
            var tileDist = mob.tile().transform(3, 3, 0).distance(target.tile());
            var delay = Math.max(1, (20 + (tileDist * 12)) / 30);

            new Projectile(mob, t, 182, 25, 12 * tileDist, 65, 31, 0, 15, 220).sendProjectile();
            t.hit(mob, CombatFactory.calcDamageFromType(mob, t, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().submit();
            t.delayedGraphics(183, 50, delay);
        }
        mob.face(target.tile()); // Go back to facing the target.
    }

    private void magicAttack(Mob mob) {
        //mob.forceChat("MAGIC");
        mob.resetFaceTile(); // Stop facing the target
        mob.animate(4492);
        for (Mob t : getPossibleTargets(mob, 20, true, false)) {
            var tileDist = mob.tile().transform(3, 3, 0).distance(target.tile());
            var delay = Math.max(1, (20 + (tileDist * 12)) / 30);
            new Projectile(mob, t, 1403, 25, 12 * tileDist, 65, 31, 0, 15, 220).sendProjectile();
            t.hit(mob, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
        }
        mob.face(target.tile()); // Go back to facing the target.
    }

    private void meleeAttack(Mob mob, Mob target) {
        //mob.forceChat("MELEE");
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
        mob.animate(mob.attackAnimation());
    }

    private boolean specialAttack = false;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (!mob.isNpc() || !target.isPlayer())
            return;

        if (World.getWorld().rollDie(20, 1)) { //5% chance the npc sends lightning
            lighting(mob);
            specialAttack = true;
        }

        // Determine if we're going to melee or mage
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            int chance = World.getWorld().random(6);
            if (chance == 1) {
                rangeAttack(mob);
            } else if (chance == 2) {
                magicAttack(mob);
            } else {
                meleeAttack(mob, target);
            }
        } else {
            int chance = World.getWorld().random(3);
            if (chance == 1) {
                rangeAttack(mob);
            } else {
                magicAttack(mob);
            }
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return specialAttack ? 10 : mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 12;
    }
}

package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.hunleff;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.Direction;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 20, 2022
 */
public class CrystallineHunleffCombatStrategy extends CommonCombatMethod {

    private boolean tornadoAttack = false;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.resetFaceTile(); // Stop facing the target

        if (World.getWorld().rollDie(5, 1)) {
            rangeAttack(mob, target);
        } else {
            magicAttack(mob, target);
        }

        if (World.getWorld().rollDie(4, 1)) {
            tornadoAttack = true;
            tornadoAttack(mob, target);
        }

        mob.face(target.tile()); // Go back to facing the target.
    }

    private void rangeAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
        var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
        Projectile projectile = new Projectile(mob, target, 1705, 35, 20 * tileDist, 45, 30, 0);
        projectile.sendProjectile();
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().submit();
    }

    private void magicAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        mob.resetFaceTile(); // Stop facing the target
        //25% chance to disable prayers
        if (World.getWorld().rollDie(4, 1)) {
            prayerDisableAttack(mob, target);
        } else {
            var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
            var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
            Projectile projectile = new Projectile(mob, target, 1713, 35, 20 * tileDist, 45, 30, 0);
            projectile.sendProjectile();
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
            target.delayedGraphics(1709, 100, delay);
        }
    }

    private void prayerDisableAttack(Mob mob, Mob target) {
        var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
        var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
        Projectile projectile = new Projectile(mob, target, 1708, 35, 20 * tileDist, 45, 30, 0);
        projectile.sendProjectile();
        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy();
        hit.submit();
        if (hit.isAccurate()) {
            Prayers.closeAllPrayers(target);
            if (target.isPlayer()) {
                target.message(Color.RED.wrap("Your prayers have been disabled"));
            }
        }
    }

    /**
     * The Hunllef summons multiple tornados that chase the player and deal high damage if they reach the players location, these tornadoes disappear after
     * a brief period. The number of tornados summoned increases as the Hunllef takes damage.
     */
    private void tornadoAttack(Mob mob, Mob t) {
        mob.animate(8418);
        Tile base = mob.tile().copy();

        final List<Tile> crystalSpots = new ArrayList<>(List.of(new Tile(0, 6, 0)));

        if (mob.hp() < 7500) {
            crystalSpots.add(new Tile(3, 6, 0));
        }

        if (mob.hp() < 5000) {
            crystalSpots.add(new Tile(World.getWorld().random(1, 4), World.getWorld().random(1, 4), 0));
        }

        if (mob.hp() < 2500) {
            crystalSpots.add(new Tile(World.getWorld().random(3, 7), World.getWorld().random(2, 6), 0));
        }

        Tile centralCrystalSpot = new Tile(39, 14, 0);
        Tile central = base.transform(centralCrystalSpot.x, centralCrystalSpot.y);
        ArrayList<Tile> spots = new ArrayList<>(crystalSpots);

        int[] ticker = new int[1];
        Chain.bound(null).runFn(2, () -> World.getWorld().tileGraphic(1718, central, 0, 0)).repeatingTask(1, tick -> {
            if (ticker[0] == 10) {
                tick.stop();
                return;
            }
            for (Tile spot : spots) {
                World.getWorld().tileGraphic(1718, base.transform(spot.x, spot.y), 0, 0);
            }
            ArrayList<Tile> newSpots = new ArrayList<>();
            for (Tile spot : new ArrayList<>(spots)) {
                final Tile curSpot = base.transform(spot.x, spot.y);
                if (curSpot.equals(t.tile())) {
                    t.hit(mob, World.getWorld().random(1, 35), SplatType.HITSPLAT);
                } else {
                    final Direction direction = Direction.getDirection(curSpot, t.tile());
                    Tile newSpot = spot.transform(direction.x, direction.y);
                    newSpots.add(newSpot);
                }
            }
            // visual debug
            /*ArrayList<GroundItem> markers = new ArrayList<>(1);
            for (Tile step : newSpots) {
                GroundItem marker = new GroundItem(new Item(ItemIdentifiers.VIAL, 1), new Tile(base.transform(step.x, step.y).x,
                    base.transform(step.x, step.y).y, mob.getZ()), null);
                GroundItemHandler.createGroundItem(marker);
                markers.add(marker);
            }
            Task.runOnceTask(1, c -> {
                markers.forEach(GroundItemHandler::sendRemoveGroundItem);
            });*/
            spots.clear();
            spots.addAll(newSpots);
            ticker[0]++;
        });
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return tornadoAttack ? 8 : mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 10;
    }
}

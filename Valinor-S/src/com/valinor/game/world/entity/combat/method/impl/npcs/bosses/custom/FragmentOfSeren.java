package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.custom;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.Direction;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 21, 2022
 */
public class FragmentOfSeren extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        //In melee distance
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            if (World.getWorld().rollDie(2, 1)) {
                meleeAttack(mob, target);
            } else {
                doRangeOrMagic(mob, target);
            }
        } else {
            doRangeOrMagic(mob, target);
        }
    }

    private void doRangeOrMagic(Mob mob, Mob target) {
        int roll = World.getWorld().random(6);
        if (roll == 6) {
            tornadoAttack(mob, target);
        } else {
            rangeAttack(mob, target);
        }
    }

    private void meleeAttack(Mob mob, Mob target) {
        mob.animate(8377);
        target.hit(mob, World.getWorld().random(1, 35), CombatType.MELEE).checkAccuracy().submit();
    }

    private void rangeAttack(Mob mob, Mob target) {
        mob.animate(8377);
        Projectile projectile = new Projectile(mob, target, 1712, 25, mob.projectileSpeed(target), 30, 30, 0);
        projectile.sendProjectile();

        target.hit(mob, World.getWorld().random(1, 45), CombatType.RANGED).checkAccuracy().submit();
    }

    private void tornadoAttack(Mob mob, Mob target) {
        mob.animate(8379);
        Tile base = mob.tile().copy();

        final List<Tile> crystalSpots = new ArrayList<>(List.of(new Tile(0, 6, 0)));
        crystalSpots.add(new Tile(3, 6, 0));
        crystalSpots.add(new Tile(World.getWorld().random(1, 4), World.getWorld().random(1, 4), 0));

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
                if (curSpot.equals(target.tile())) {
                    target.hit(mob, World.getWorld().random(1, 35), SplatType.HITSPLAT);
                } else {
                    final Direction direction = Direction.getDirection(curSpot, target.tile());
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
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 6;
    }
}

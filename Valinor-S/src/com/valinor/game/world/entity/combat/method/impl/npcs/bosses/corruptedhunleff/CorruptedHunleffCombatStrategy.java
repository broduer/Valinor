package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.corruptedhunleff;

import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
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

public class CorruptedHunleffCombatStrategy extends CommonCombatMethod {

    private Task stompTask;

    private void checkStompTask(Mob mob, Mob target) {
        if (stompTask == null) {
            stompTask = new Task("checkHunleffStompTask", 7) {
                @Override
                public void execute() {
                    if (mob.dead() || !mob.isRegistered() || !mob.getAsNpc().canSeeTarget(mob, target)) {
                        stop();
                        return;
                    }
                    World.getWorld().getPlayers().forEachInRegion(6810, p -> {
                        if (p.boundaryBounds().inside(mob.tile(), mob.getSize())) {
                            stompAttack(mob, target);
                        }
                    });
                }
            }.bind(mob);
            TaskManager.submit(stompTask);
        }
    }

    /**
     * If a player is underneath the Hunllef when it tries to attack the player, the Hunllef will perform a stomp attack which deals very high damage,
     * similar to the stomp attack used by the Corporeal Beast. This is the only attack that does not count towards the 4 attacks.
     */
    public void stompAttack(Mob mob, Mob target) {
        mob.animate(8420);
        target.hit(mob, World.getWorld().random(30, 51), 1, CombatType.MELEE).setAccurate(true).submit();
    }

    private int attacks = 0;
    private boolean tornadoAttack = false;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        checkStompTask(mob, target);
        attacks++;

        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            if(World.getWorld().rollDie(2,1)) {
                mob.animate(8420);
                for (Mob t : getPossibleTargets(mob)) {
                    mob.getAsNpc().combatInfo().maxhit = 50;
                    t.hit(mob, CombatFactory.calcDamageFromType(mob, t, CombatType.MELEE), CombatType.MELEE).setAccurate(true).submit();
                }
            } else {
                //Standard attacks: The Hunllef uses two main standard attacks, a ranged crystal-like attack and a magic based orb attack.
                // The Hunllef will alternate between the two attack styles every 4 attacks.
                if (attacks == 4) {
                    rangeAttack(mob);
                    attacks = 0;
                } else {
                    magicAttack(mob);
                }
            }
        } else {
            //Standard attacks: The Hunllef uses two main standard attacks, a ranged crystal-like attack and a magic based orb attack.
            // The Hunllef will alternate between the two attack styles every 4 attacks.
            if (attacks == 4) {
                rangeAttack(mob);
                attacks = 0;
            } else {
                magicAttack(mob);
            }
        }

        //25% chance to spawn tornados
        if (World.getWorld().rollDie(4, 1)) {
            tornadoAttack = true;
            tornadoAttack(mob);
        }
    }

    private void rangeAttack(Mob mob) {
        for (Mob t : getPossibleTargets(mob)) {
            var tileDist = mob.tile().transform(1, 1, 0).distance(t.tile());
            var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
            Projectile projectile = new Projectile(mob, t, 1705, 35, 20 * tileDist, 45, 30, 0);
            projectile.sendProjectile();
            t.hit(mob, CombatFactory.calcDamageFromType(mob, t, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().submit();
        }
    }

    private void magicAttack(Mob mob) {
        mob.animate(mob.attackAnimation());
        for (Mob t : getPossibleTargets(mob)) {
            //25% chance to disable prayers
            if (World.getWorld().rollDie(4, 1)) {
                prayerDisableAttack(mob, t);
            } else {
                var tileDist = mob.tile().transform(1, 1, 0).distance(t.tile());
                var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
                Projectile projectile = new Projectile(mob, t, 1713, 35, 20 * tileDist, 45, 30, 0);
                projectile.sendProjectile();
                t.hit(mob, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
                t.delayedGraphics(1709, 100, delay);
            }
        }
    }

    /**
     * When the Hunllef is attacking using magic-based attacks, it has a chance to use an attack which turns off prayers. These attacks use a different game
     * sound, hence it is recommended to use sounds during the fight.
     */
    private void prayerDisableAttack(Mob mob, Mob target) {
        var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
        var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
        Projectile projectile = new Projectile(mob, target, 1708, 35, 20 * tileDist, 45, 30, 0);
        projectile.sendProjectile();
        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy();
        hit.submit();
        if(hit.isAccurate()) {
            Prayers.closeAllPrayers(target);
            if(target.isPlayer()) {
                target.message(Color.RED.wrap("Your prayers have been disabled"));
            }
        }
    }

    /**
     * The Hunllef summons multiple tornados that chase the player and deal high damage if they reach the players location, these tornadoes disappear after
     * a brief period. The number of tornados summoned increases as the Hunllef takes damage.
     */
    private void tornadoAttack(Mob mob) {
        mob.animate(8418);
        Tile base = mob.tile().copy();

        final List<Tile> crystalSpots = new ArrayList<>(List.of(new Tile(0, 6, 0)));

        if(mob.hp() < 7500) {
            crystalSpots.add(new Tile(3, 6, 0));
        }

        if(mob.hp() < 5000) {
            crystalSpots.add(new Tile(World.getWorld().random(1,4), World.getWorld().random(1,4), 0));
        }

        if(mob.hp() < 2500) {
            crystalSpots.add(new Tile(World.getWorld().random(3,7), World.getWorld().random(2,6), 0));
        }

        Tile centralCrystalSpot = new Tile(39, 14, 0);
        Tile central = base.transform(centralCrystalSpot.x, centralCrystalSpot.y);
        ArrayList<Tile> spots = new ArrayList<>(crystalSpots);

        for (Mob t : getPossibleTargets(mob)) {
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

package com.valinor.game.world.entity.combat.method.impl.npcs.bosses;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;
import com.valinor.util.chainedwork.Chain;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 05, 2022
 */
public class ChaoticNightmare extends CommonCombatMethod {

    private enum Attacks {
        MELEE, RANGE, MAGIC, SPECIAL_ATTACK
    }

    private Attacks attack = Attacks.MELEE;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target) && World.getWorld().rollDie(2, 1)) {
            if(World.getWorld().rollDie(2,1)) {
                meleeClawAttack(mob, target);//Neither of these hits are protectable
            }
        } else {
            var roll = World.getWorld().random(8);

            switch (roll) {
                case 0, 2, 3 -> magicAttack(mob, target);
                case 1, 4, 5 -> rangeAttack(mob, target);
                case 6, 7, 8 -> specialAttack(mob, target);
            }
        }
    }

    private void meleeClawAttack(Mob mob, Mob target) {
        attack = Attacks.MELEE;
        mob.animate(8594);
        mob.resetFaceTile(); // Stop facing the target
        World.getWorld().getPlayers().forEach(p -> {
            if (mob.isRegistered() && !mob.dead() && p != null && p.tile().inSqRadius(mob.tile(), 12)) {
                int first = World.getWorld().random(1, 18);
                int second = first / 2;
                int third = second / 2;
                int fourth = third / 2;
                p.hit(mob, first, 1);
                p.hit(mob, second, 1);
                p.hit(mob, third, 2);
                p.hit(mob, fourth, 2);
            }
        });
        mob.face(target.tile()); // Go back to facing the target.
    }

    private void magicAttack(Mob mob, Mob target) {
        attack = Attacks.MAGIC;
        mob.animate(8598);
        mob.resetFaceTile(); // Stop facing the target
        Chain.bound(null).runFn(6, () -> {
            World.getWorld().getPlayers().forEach(p -> {
                if (mob.isRegistered() && !mob.dead() && p != null && p.tile().inSqRadius(mob.tile(), 12)) {
                    projectile_bombing(mob, p);
                }
            });
            mob.face(target.tile()); // Go back to facing the target.
        });
    }

    private void projectile_bombing(Mob mob, Mob target) {
        int x = target.tile().x;
        int y = target.tile().y;

        Tile projectile_one = new Tile(x, y);
        int projectile_one_distance = mob.tile().distance(projectile_one);
        int projectile_one_delay = Math.max(1, (20 + projectile_one_distance * 12) / 30);

        Tile projectile_two = new Tile(x + World.getWorld().random(2), y + World.getWorld().random(2));
        int projectile_two_distance = mob.tile().distance(projectile_two);
        int projectile_two_delay = Math.max(1, (20 + projectile_two_distance * 12) / 30);

        Tile projectile_three = new Tile(x + World.getWorld().random(2), y + World.getWorld().random(2));
        int projectile_three_distance = mob.tile().distance(projectile_three);
        int projectile_three_delay = Math.max(1, (20 + projectile_three_distance * 12) / 30);

        new Projectile(mob.tile(), projectile_one, 0, 1665, 24 * projectile_one_distance, projectile_one_delay, 50, 0, 0).sendProjectile();
        new Projectile(mob.tile(), projectile_two, 0, 1665, 24 * projectile_two_distance, projectile_two_delay, 50, 0, 0).sendProjectile();
        new Projectile(mob.tile(), projectile_three, 0, 1665, 24 * projectile_three_distance, projectile_three_delay, 50, 0, 0).sendProjectile();

        World.getWorld().tileGraphic(1717, projectile_one, 1, 24 * projectile_one_distance);
        World.getWorld().tileGraphic(1717, projectile_two, 1, 24 * projectile_two_distance);
        World.getWorld().tileGraphic(1717, projectile_three, 1, 24 * projectile_three_distance);

        Chain.bound(null).name("projectile_one_task").runFn(projectile_one_distance, () -> {
            if (target.tile().inSqRadius(projectile_one, 1))
                target.hit(mob, Math.min(20, World.getWorld().random(35)));
            if (target.tile().inSqRadius(projectile_two, 1))
                target.hit(mob, Math.min(20, World.getWorld().random(35)));
            if (target.tile().inSqRadius(projectile_three, 1))
                target.hit(mob, Math.min(20, World.getWorld().random(35)));
        });

        int explosive_x = projectile_two.x;
        int explosive_z = projectile_two.y;

        Tile ricochet_projectile_one = new Tile(explosive_x + World.getWorld().random(2), explosive_z + World.getWorld().random(2));
        int ricochet_projectile_one_distance = projectile_two.distance(ricochet_projectile_one);
        int ricochet_projectile_one_delay = Math.max(1, (20 + ricochet_projectile_one_distance * 12) / 30);

        Tile ricochet_projectile_two = new Tile(explosive_x + World.getWorld().random(2), explosive_z + World.getWorld().random(3));
        int ricochet_projectile_two_distance = projectile_two.distance(ricochet_projectile_two);
        int ricochet_projectile_two_delay = Math.max(1, (20 + ricochet_projectile_two_distance * 12) / 30);

        Chain.bound(null).name("projectile_two_task").runFn(projectile_two_distance, () -> {
            new Projectile(projectile_two, ricochet_projectile_one, 0, 1378, 50 * ricochet_projectile_one_distance, ricochet_projectile_one_delay, 0, 0, 0).sendProjectile();
            new Projectile(projectile_two, ricochet_projectile_two, 0, 1378, 50 * ricochet_projectile_two_distance, ricochet_projectile_two_delay, 0, 0, 0).sendProjectile();

            World.getWorld().tileGraphic(1718, ricochet_projectile_one, 1, 50 * ricochet_projectile_one_distance);
            World.getWorld().tileGraphic(1718, ricochet_projectile_two, 1, 50 * ricochet_projectile_two_distance);

        }).then(ricochet_projectile_one_delay, () -> {
            if (target.tile().inSqRadius(ricochet_projectile_one, 1))
                target.hit(mob, Math.min(20, World.getWorld().random(35)));
            if (target.tile().inSqRadius(ricochet_projectile_two, 1))
                target.hit(mob, Math.min(20, World.getWorld().random(35)));
        });
    }

    private void rangeAttack(Mob mob, Mob target) {
        attack = Attacks.RANGE;
        mob.animate(8606);
        mob.resetFaceTile(); // Stop facing the target
        World.getWorld().getPlayers().forEach(p -> Chain.bound(null).runFn(2, () -> {
            if (mob.isRegistered() && !mob.dead() && p != null && p.tile().inSqRadius(mob.tile(), 12)) {
                int tileDist = mob.tile().transform(1, 1, 0).distance(p.tile());
                var delay = Math.max(1, (50 + (tileDist * 12)) / 30);

                new Projectile(mob, p, 1380, 12 * tileDist, 120, 100, 43, 0, 14, 5).sendProjectile();

                p.hit(mob, CombatFactory.calcDamageFromType(mob, p, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().submit();

                Chain.bound(null).runFn(3, () -> {
                    p.runOnceTask(5, r -> {
                        final var tile = p.tile().copy();
                        new Projectile(mob.getCentrePosition(), tile, 1, 1637, 125, 40, 100, 0, 0, 16, 96).sendProjectile();
                        World.getWorld().tileGraphic(1638, tile, 0, 0);
                        if (p.tile().equals(tile)) {
                            for (int hits = 0; hits < 4; hits++) {
                                Chain.bound(null).name("the_nightmare_special_ranged_attack_task").runFn(hits * 3, () -> p.hit(mob, World.getWorld().random(6, 8)));
                            }
                        }
                    });
                });
            }
        }));
        mob.face(target.tile()); // Go back to facing the target.
    }

    private void specialAttack(Mob mob, Mob target) {
        attack = Attacks.SPECIAL_ATTACK;
        // Go back to facing the target.
        mob.animate(8601);
        mob.resetFaceTile(); // Stop facing the target
        World.getWorld().getPlayers().forEach(p -> {
            if (mob.isRegistered() && !mob.dead() && p != null && p.tile().inSqRadius(mob.tile(), 12)) {
                Tile tile_one = p.tile();
                Tile tile_two = tile_one.transform(World.getWorld().random(1, 2), World.getWorld().random(1, 2));
                Tile tile_three = tile_one.transform(World.getWorld().random(1, 2), World.getWorld().random(1, 2));
                Tile tile_four = tile_one.transform(World.getWorld().random(1, 2), World.getWorld().random(1, 2));
                Tile tile_five = tile_one.transform(World.getWorld().random(1, 2), World.getWorld().random(1, 2));

                World.getWorld().tileGraphic(1727, tile_one, 100, 0);
                World.getWorld().tileGraphic(1727, tile_two, 100, 0);
                World.getWorld().tileGraphic(1727, tile_three, 100, 0);
                World.getWorld().tileGraphic(1727, tile_four, 100, 0);
                World.getWorld().tileGraphic(1727, tile_five, 100, 0);

                Chain.bound(null).runFn(10, () -> {
                    if (p.tile().inSqRadius(tile_one, 2) || p.tile().inSqRadius(tile_two, 2) || p.tile().inSqRadius(tile_three, 2) || p.tile().inSqRadius(tile_four, 2) || p.tile().inSqRadius(tile_five, 2)) {
                        p.hit(mob, World.getWorld().random(1, 30), 1);
                    }
                });
            }
        });
        mob.face(target.tile()); // Go back to facing the target.
    }
    @Override
    public int getAttackSpeed(Mob mob) {
        return attack.equals(Attacks.MAGIC) ? 12 : mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 12;
    }
}

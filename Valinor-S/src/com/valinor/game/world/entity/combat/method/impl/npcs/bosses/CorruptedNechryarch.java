package com.valinor.game.world.entity.combat.method.impl.npcs.bosses;

import com.google.common.collect.Lists;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.ProjectileRoute;
import com.valinor.util.TickDelay;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Patrick van Elderen | May, 03, 2021, 16:19
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class CorruptedNechryarch extends CommonCombatMethod {

    private final TickDelay acidAttackCooldown = new TickDelay();
    private final List<Tile> acidPools = Lists.newArrayList();

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (!acidAttackCooldown.isDelayed()) {
            acid_attack(mob, target);
        }
        boolean close = target.tile().isWithinDistance(mob.tile(), 2);
        if (close && World.getWorld().rollDie(3))
            melee_attack(mob, target);
        else
            magic_attack(mob, target);
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 12;
    }

    private void acid_attack(Mob mob, Mob target) {
        acidAttackCooldown.delay(50);
        Tile lastAcidPos = mob.tile();

        for (int cycle = 0; cycle < 1; cycle++) {
            // so this start delay needs to increase per target so the attack appears
            // in sequence..
            Chain.bound(null).runFn(2, () -> {
                Tile lockonTile = target.tile();
                var tileDist = mob.tile().transform(3, 3, 0).distance(target.tile());
                var delay = Math.max(1, (20 + (tileDist * 12)) / 30);

                new Projectile(lastAcidPos, lockonTile, -1, 5005, 12 * tileDist, 10, 35, 35, 0, 16, 64).sendProjectile();
                World.getWorld().tileGraphic(5001, lastAcidPos, 0, 0);
                World.getWorld().tileGraphic(5004, lastAcidPos, 0, 0);
                acidPools.add(lastAcidPos);
                Chain.bound(null).runFn(delay, () -> {
                    if (target.tile().equals(lockonTile)) {
                        int damage = World.getWorld().random(1, 30);
                        target.hit(mob, damage);
                        mob.heal(damage);
                    }
                }).then(20, acidPools::clear);
                // after fixed delay of 2s
            });
        }
    }

    private void melee_attack(Mob mob, Mob target) {
        mob.animate(4672);
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
    }

    private void magic_attack(Mob mob, Mob target) {
        mob.animate(7550);
        if (ProjectileRoute.allow(mob, target)) {
            var tileDist = mob.tile().transform(3, 3, 0).distance(target.tile());
            var delay = Math.max(1, (20 + (tileDist * 12)) / 30);
            new Projectile(mob, target, 5000, 30, 12 * tileDist, 120, 43, 0, 16, 64).sendProjectile();
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
            if (World.getWorld().rollDie(10)) {
                Chain.bound(null).runFn(delay + 2, () -> {
                    //after hit effects
                    for (int i = 0; i < 5; i++) {
                        target.hit(mob, 3);
                        target.graphic(5002);
                    }
                });
            }
        }
    }
}

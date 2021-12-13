package com.valinor.game.world.entity.combat.method.impl.npcs.fightcaves;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.player.Player;

/**
 * Handles Jad's combat.
 *
 * @author Professor Oak
 */
public class TztokJadCombatScript extends CommonCombatMethod {

    private static final int MAX_DISTANCE = 10;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        var attackRoll = World.getWorld().random(3);
        var spawnedHealers = mob.<Boolean>getAttribOr(AttributeKey.JAD_SPAWNED_HEALERS, false);

        //Do we spawn the healers?
        if (mob.hp() < 130 && !spawnedHealers) {
            TzTokJad jad = (TzTokJad) mob.getAsNpc();
            if (jad != null && jad.hp() <= jad.maxHp() / 2) {
                jad.spawnHealers(target);
            }
        }

        //Select an attack style based on our random roll..
        switch (attackRoll) {
            case 1 -> {
                rangedAttack(mob, target);
            }
            case 2 -> {
                mageAttack(mob, target);
            }
            default -> {
                if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
                    meleeAttack(mob, target);
                } else {
                    if (World.getWorld().rollDie(2, 1))
                        rangedAttack(mob, target);
                    else
                        mageAttack(mob, target);
                }
            }
        }
    }

    private void meleeAttack(Mob mob, Mob target) {
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
        mob.animate(mob.attackAnimation());
    }

    private void rangedAttack(Mob mob, Mob target) {
        mob.face(target.tile());
        mob.animate(2652);

        target.runFn(3, () -> {
            target.graphic(451);
        }).then(2, () -> {
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED), CombatType.RANGED).checkAccuracy().graphic(new Graphic(157, 80)).submit();
        });
    }

    private void mageAttack(Mob mob, Mob target) {
        var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
        var delay = Math.max(1, (50 + (tileDist * 12)) / 35);

        mob.face(target.tile());
        mob.animate(2656);

        target.runFn(2, () -> {
            mob.graphic(447, 525, 23);
            Projectile projectile1 = new Projectile(mob, target, 448, 35, 8 * tileDist, 130, 36, 0);
            projectile1.sendProjectile();
            Projectile projectile2 = new Projectile(mob, target, 449, 38, 8 * tileDist, 130, 36, 0);
            projectile2.sendProjectile();
            Projectile projectile3 = new Projectile(mob, target, 450, 41, 8 * tileDist, 130, 36, 0);
            projectile3.sendProjectile();

            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().graphic(new Graphic(157, 80)).submit();
        });
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return MAX_DISTANCE;
    }
}

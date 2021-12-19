package com.valinor.game.world.entity.combat.method.impl.npcs.bosses;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.skull.SkullType;
import com.valinor.game.world.entity.combat.skull.Skulling;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import java.security.SecureRandom;

public class Skotizo extends CommonCombatMethod {

    public static void startFight(Player player, GameObject altar) {
        player.lock();
        Chain.bound(null).runFn(1, () -> altar.animate(1477)).then(6, () -> altar.animate(1471));
        Chain.bound(null).runFn(1, () -> {
            player.animate(3865);
            player.graphic(1296);
        }).then(3, () -> {
            player.getSkotizoInstance().enterInstance(player);
        }).then(1, () -> {
            player.animate(-1);
            player.unlock();
        });
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (withinDistance(1) && World.getWorld().rollDie(3, 2))
            meleeAttack(mob, target);
        else
            magicAttack(mob, target);
    }

    /**
     * Handles the melee attack
     */
    private void meleeAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
    }

    /**
     * Handles the magic attack
     */
    private void magicAttack(Mob mob, Mob target) {
        mob.animate(69);
        Projectile projectile = new Projectile(mob, target, 1242, 25, mob.projectileSpeed(target), 120, 31,0);
        projectile.sendProjectile();

        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), mob.getProjectileHitDelay(target), CombatType.MAGIC).checkAccuracy().submit();
        target.delayedGraphics(new Graphic(197,0,0), mob.getProjectileHitDelay(target));
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

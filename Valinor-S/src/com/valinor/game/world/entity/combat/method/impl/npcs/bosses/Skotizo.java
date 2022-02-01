package com.valinor.game.world.entity.combat.method.impl.npcs.bosses;

import com.valinor.game.content.instance.impl.SkotizoInstance;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.NpcIdentifiers.*;

public class Skotizo extends CommonCombatMethod {

    public static void startFight(Player player, GameObject altar) {
        player.lock();
        Chain.bound(null).runFn(1, () -> altar.animate(1477)).then(6, () -> altar.animate(1471));
        Chain.bound(null).runFn(1, () -> {
            player.animate(3865);
            player.graphic(1296);
        }).then(3, () -> {
            SkotizoInstance skotizoInstance = player.getSkotizoInstance();

            if (skotizoInstance == null) {
                player.message("We are unable to allow you in at the moment. Too many players.");
                return;
            }

            player.getSkotizoInstance().init(player);
        }).then(1, () -> {
            player.animate(-1);
            player.unlock();
        });
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if(target.isPlayer()) {
            Player player = target.getAsPlayer();
            //Skotizo special attacks being checked every time he hits
            player.getSkotizoInstance().awakenAltars(player);
            player.getSkotizoInstance().summonMinions(player);
        }
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
        return 20;
    }

    @Override
    public void onDeath(Player player,Npc npc) {
        World.getWorld().getNpcs().forEachInArea(SkotizoInstance.SKOTIZO_AREA, n -> {
            if(n.id() == REANIMATED_DEMON || n.id() == DARK_ANKOU || n.id() == AWAKENED_ALTAR || n.id() == AWAKENED_ALTAR_7290 || n.id() == AWAKENED_ALTAR_7292 || n.id() == AWAKENED_ALTAR_7294) {
                n.remove();//Kill off all npcs that are alive
            }
        });
    }
}

package com.valinor.game.world.entity.combat.method.impl.npcs.dragons;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatConstants;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.game.world.route.StepType;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.concurrent.atomic.AtomicInteger;

import static com.valinor.util.ItemIdentifiers.INSULATED_BOOTS;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * april 29, 2020
 */
public class RuneDragon extends CommonCombatMethod {

    boolean sparkAttack = false;

    @Override
    public void prepareAttack(Mob dragon, Mob target) {
        int rand = Utils.random(5);
        Npc npc = (Npc) dragon;
        if (rand == 1) {
            doDragonBreath(npc, target);
        } else if (rand == 2) {
            doRangedAttack(npc, target);
        } else if (rand == 3) {
            doMagicBlast(npc, target);
        } else if (rand == 4) {
            sparkAttack = true;
            sparkAttack(npc, target);
        } else {
            if (CombatFactory.canReach(dragon, CombatFactory.MELEE_COMBAT, target)) {
                doMelee(npc, target);
            } else {
                int roll = Utils.random(3);
                if (roll == 1) {
                    doDragonBreath(npc, target);
                } else if (roll == 2) {
                    doRangedAttack(npc, target);
                } else if (roll == 3) {
                    doMagicBlast(npc, target);
                }
            }
        }
    }

    private void sparkAttack(Npc npc, Mob target) {
        npc.animate(81);
        for (int i = 0; i < 2; i++) {
            Tile pos = target.tile().relative(World.getWorld().get(-1, 1), World.getWorld().get(-1, 1));
            new Projectile(npc.tile(), pos, 0, 1488, 160, 40,10, 0, 0, 16, 192).sendProjectile();
            Npc spark = new Npc(8032, pos);
            Chain.bound(null).runFn(1, () -> {
                spark.spawn(false);
            }).then(2, () -> {
                AtomicInteger cap = new AtomicInteger(5);
                Chain.bound(null).repeatingTask(1, t -> {
                    if (target == null) {
                        t.stop();
                        return;
                    }
                    if (cap.getAndDecrement() == 0) {
                        spark.remove();
                        // should eventually stop here
                        t.stop();
                        return;
                    }
                    spark.step(World.getWorld().get(-1, 1), World.getWorld().get(-1, 1), StepType.FORCE_WALK);
                    if (target.tile().isWithinDistance(spark.tile(), 1)) {
                        int maxDamage = 8;
                        if (target.getAsPlayer() != null && target.getAsPlayer().getEquipment().contains(INSULATED_BOOTS))
                            maxDamage = 2;
                        target.hit(mob, maxDamage);
                    }
                });
            });
        }
    }

    private void doMelee(Npc npc, Mob target) {
        npc.animate(npc.attackAnimation());
        target.hit(npc, Utils.random(npc.combatInfo().maxhit), CombatType.MELEE).checkAccuracy().submit();
    }

    private void doRangedAttack(Npc npc, Mob target) {
        npc.animate(81);
        new Projectile(npc, target, 1486, 40, npc.projectileSpeed(target), 10, 31, 0, 16, 127).sendProjectile();
        int damage = Utils.random(npc.combatInfo().maxhit);
        //The second attack is a ranged attack that hits through Protect from Missiles, and uses the Life Leech effect from enchanted onyx bolts,
        // where the dragon will heal itself for 100% of the damage dealt to the player.
        if (Utils.rollDie(5, 2)) {
            target.hit(npc, damage, npc.getProjectileHitDelay(target), CombatType.RANGED).submit();
            npc.heal(damage, npc.maxHp());
        } else {
            //Regular ranged attack
            target.hit(npc, damage, npc.getProjectileHitDelay(target), CombatType.RANGED).checkAccuracy().submit();
        }
    }

    private void doMagicBlast(Npc npc, Mob target) {
        npc.animate(81);
        new Projectile(npc, target, 162, 40, npc.projectileSpeed(target), 10, 31, 0, 16, 127).sendProjectile();
        target.hit(npc, Utils.random(npc.combatInfo().maxhit), npc.getProjectileHitDelay(target), CombatType.MAGIC).checkAccuracy().submit();
        target.delayedGraphics(new Graphic(163, 92), npc.getProjectileHitDelay(target));
    }

    private void doDragonBreath(Npc npc, Mob target) {
        npc.animate(81);
        new Projectile(npc, target, 54, 50, npc.projectileSpeed(target), 22, 32, 0, 5, 24).sendProjectile();
        if(target instanceof Player) {
            Player player = (Player) target;
            double max = 50.0;
            int antifire_charges = player.getAttribOr(AttributeKey.ANTIFIRE_POTION, 0);
            boolean memberEffect = player.getMemberRights().isDiamondMemberOrGreater(player);
            boolean hasShield = CombatConstants.hasAntiFireShield(player);
            boolean hasPotion = antifire_charges > 0 || memberEffect;

            if (player.<Boolean>getAttribOr(AttributeKey.SUPER_ANTIFIRE_POTION, false)) {
                player.message("Your super antifire potion protects you completely from the heat of the dragon's breath!");
                max = 0.0;
            }

            //Does our player have an anti-dragon shield?
            if (max > 0 && hasShield) {
                player.message("Your shield absorbs most of the dragon fire!");
                max *= 0.3;
            }

            //Has our player recently consumed an antifire potion?
            if (max > 0 && hasPotion) {
                player.message("Your potion protects you from the heat of the dragon's breath!");
                max *= 0.3;
            }

            //Is our player using protect from magic?
            if (max > 0 && Prayers.usingPrayer(player, Prayers.PROTECT_FROM_MAGIC)) {
                player.message("Your prayer absorbs most of the dragon's breath!");
                max *= 0.6;
            }

            if (hasShield && hasPotion) {
                max = 0.0;
            }

            int hit = Utils.random((int) max);
            player.hit(npc, hit, npc.getProjectileHitDelay(player), CombatType.MAGIC).submit();
            if (max == 65 && hit > 0) {
                player.message("You are badly burned by the dragon fire!");
            }
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return sparkAttack ? 10 : mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 8;
    }
}

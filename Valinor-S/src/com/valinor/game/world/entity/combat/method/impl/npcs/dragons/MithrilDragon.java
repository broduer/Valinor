package com.valinor.game.world.entity.combat.method.impl.npcs.dragons;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatConstants;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Utils;

/**
 * @author Patrick van Elderen | Zerikoth | PVE
 * @date maart 14, 2020 09:50
 */
public class MithrilDragon extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            if (Utils.rollDie(3, 1)) {
                doMelee(mob, target);
            } else {
                breathFire(mob, target);
            }
        } else if (!CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            if (Utils.rollDie(3, 1)) {
                doMagic(mob, target);
            } else {
                doRanged(mob, target);
            }
        }
    }

    private void doMelee(Mob mob, Mob target) {
        mob.animate(80);
        target.hit(mob, Utils.random(28), 1, CombatType.MELEE).checkAccuracy().submit();
    }

    private void doMagic(Mob mob, Mob target) {
        mob.animate(6722);
        new Projectile(mob, target, 136, 40, mob.projectileSpeed(target), 20, 31, 0, 10, 36).sendProjectile();
        target.hit(mob, Utils.random(18), mob.getProjectileHitDelay(target), CombatType.MAGIC).checkAccuracy().submit();
    }

    private void doRanged(Mob mob, Mob target) {
        mob.animate(6722);
        new Projectile(mob, target, 16, 40, mob.projectileSpeed(target), 20, 31, 0, 10, 36).sendProjectile();
        target.hit(mob, Utils.random(22), mob.getProjectileHitDelay(target), CombatType.RANGED).checkAccuracy().submit();
    }

    private void breathFire(Mob mob, Mob target) {
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
            if (max > 0 && (player.getEquipment().hasAt(EquipSlot.SHIELD, 11283) || player.getEquipment().hasAt(EquipSlot.SHIELD, 11284) ||
                player.getEquipment().hasAt(EquipSlot.SHIELD, 1540))) {
                player.message("Your shield absorbs most of the dragon fire!");
                max *= 0.3;
            }

            //Has our player recently consumed an antifire potion?
            if (max > 0 && antifire_charges > 0) {
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
            player.hit(mob, hit, CombatType.MAGIC).submit();
            if (max == 50 && hit > 0) {
                player.message("You are badly burned by the dragon fire!");
            }

            mob.animate(81);
            mob.graphic(1, 100, 0);
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return 4;
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 8;
    }
}

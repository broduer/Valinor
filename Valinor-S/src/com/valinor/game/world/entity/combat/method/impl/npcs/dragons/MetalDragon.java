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

public class MetalDragon extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (!CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            breathFire(mob, target);
        } else {
            basicAttack(mob, target);
        }
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

            new Projectile(mob, target, 54, 50, mob.projectileSpeed(target), 22, 32, 0, 5, 24).sendProjectile();
            mob.animate(81);
            int hit = Utils.random((int) max);
            player.hit(mob, hit, mob.getProjectileHitDelay(player), CombatType.MAGIC).submit();
            if (max == 50 && hit > 0) {
                player.message("You are badly burned by the dragon fire!");
            }
        }
    }

    private void basicAttack(Mob mob, Mob target) {
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
        mob.animate(mob.attackAnimation());
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

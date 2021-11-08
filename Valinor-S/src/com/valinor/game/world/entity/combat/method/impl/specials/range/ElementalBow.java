package com.valinor.game.world.entity.combat.method.impl.specials.range;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import static com.valinor.util.CustomItemIdentifiers.FIRE_ARROWS;
import static com.valinor.util.ItemIdentifiers.ICE_ARROWS;

public class ElementalBow extends CommonCombatMethod {

    private static final int FIRE_ARROW_PROJECTILE = 11;
    private static final int ICE_ARROW_PROJECTILE = 16;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (mob.isPlayer()) {
            Player player = (Player) mob;

            player.animate(EquipmentInfo.attackAnimationFor(player, CustomItemIdentifiers.ELEMENTAL_BOW));

            Hit hit = target.hit(player, CombatFactory.calcDamageFromType(player, target, CombatType.RANGED),2, CombatType.RANGED).checkAccuracy();
            hit.submit();

            player.putAttrib(AttributeKey.ELEMENTAL_BOW_SPECIAL_COOLDOWN, true);

            Chain.bound(null).name("ElementalBowSpecTask").runFn(17, () -> player.putAttrib(AttributeKey.ELEMENTAL_BOW_SPECIAL_COOLDOWN, false));

            //Ice effect
            if (player.getEquipment().hasAt(EquipSlot.AMMO, ICE_ARROWS)) {
                new Projectile(player, target, ICE_ARROW_PROJECTILE, 41, 60, 40, 31, 0, 10, 15).sendProjectile();
                target.freeze(5, player);
                player.forceChat("FREEZE!");

                //A task that loops 5 times
                for (int index = 0; index < 6; index++) {
                    Chain.bound(null).name("ele_bow_freeze_effect").cancelWhen(() -> {
                        return !player.tile().isWithinDistance(target.tile()) || target.dead(); // cancels as expected
                    }).runFn(index * 4, () -> {
                        target.graphic(540);
                        Hit freezeHit = target.hit(player, World.getWorld().random(2, 5),0, CombatType.RANGED).setAccurate(true);
                        freezeHit.submit();
                        // Flag the targ as under attack at this moment to factor in delayed combat styles.
                        target.putAttrib(AttributeKey.LAST_DAMAGER, player);
                        target.putAttrib(AttributeKey.LAST_WAS_ATTACKED_TIME, System.currentTimeMillis());
                        target.getTimers().register(TimerKey.COMBAT_LOGOUT, 16);
                    });
                }
                //Fire effect
            } else if (player.getEquipment().hasAt(EquipSlot.AMMO, FIRE_ARROWS)) {
                new Projectile(player, target, FIRE_ARROW_PROJECTILE, 41, 60, 40, 31, 0, 10, 15).sendProjectile();

                //A task that loops 5 times
                for (int index = 0; index < 6; index++) {
                    // cancels as expected
                    Chain.bound(null).name("ele_bow_fire_effect").cancelWhen(() -> {
                        return !player.tile().isWithinDistance(target.tile()) || target.dead(); // cancels as expected
                    }).runFn(index * 4, () -> {
                        target.graphic(78);
                        Hit fireHit = target.hit(player, World.getWorld().random(2, 5),0, CombatType.RANGED).setAccurate(true);
                        fireHit.submit();
                        // Flag the targ as under attack at this moment to factor in delayed combat styles.
                        target.putAttrib(AttributeKey.LAST_DAMAGER, player);
                        target.putAttrib(AttributeKey.LAST_WAS_ATTACKED_TIME, System.currentTimeMillis());
                        target.getTimers().register(TimerKey.COMBAT_LOGOUT, 16);
                    });
                }
                player.forceChat("BURN!");
            }
            CombatSpecial.drain(player, CombatSpecial.ELEMENTAL_BOW.getDrainAmount());
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return CombatFactory.RANGED_COMBAT.getAttackDistance(mob);
    }
}

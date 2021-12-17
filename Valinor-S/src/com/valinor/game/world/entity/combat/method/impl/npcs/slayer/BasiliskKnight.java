package com.valinor.game.world.entity.combat.method.impl.npcs.slayer;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.player.Skills;

import static com.valinor.util.ItemIdentifiers.MIRROR_SHIELD;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 17, 2021
 */
public class BasiliskKnight extends CommonCombatMethod {

    private static final int[] DRAIN = {Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE, Skills.RANGED, Skills.MAGIC};

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (withinDistance(1)) {
            basicAttack(mob, target);
        } else {
            magicAttack(mob, target);
        }
        if (target.isPlayer() && !target.getAsPlayer().getEquipment().contains(MIRROR_SHIELD)) {
            for (int skill : DRAIN) {
                target.getAsPlayer().skills().alterSkill(skill, -4);
            }
            target.hit(mob, World.getWorld().random(2, 5));
            target.message("<col=ff0000>The basilisk's piercing gaze drains your stats!");
            target.message("<col=ff0000>A mirror shield can protect you from this attack.");
        }
    }

    private void magicAttack(Mob mob, Mob target) {
        Projectile projectile = new Projectile(mob, target, 1662, 25, mob.projectileSpeed(target), 5, 21, 0);
        projectile.sendProjectile();
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), mob.getProjectileHitDelay(target), CombatType.MAGIC).checkAccuracy().submit();
        mob.animate(8500);
    }

    private void basicAttack(Mob mob, Mob target) {
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
        mob.animate(mob.attackAnimation());
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

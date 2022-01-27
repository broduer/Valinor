package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cos.aragog;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Area;

/**
 * @author Patrick van Elderen | April, 07, 2021, 16:35
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class Aragog extends CommonCombatMethod {

    private void webAttack(Mob mob) {
        //mob.forceChat("WEB -> RANGED ATTACK");

        mob.resetFaceTile(); // Stop facing the target
        //Target all raids party members

        for (Mob t : getPossibleTargets(mob, 20, true, false)) {
            var tileDist = mob.tile().transform(3, 3, 0).distance(t.tile());
            new Projectile(mob, t, 1254, 20, 12 * tileDist, 30, 30, 0, 14, 5).sendProjectile();
            var delay = Math.max(1, (20 + (tileDist * 12)) / 30);
            t.hit(mob, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), delay, CombatType.RANGED).checkAccuracy().submit();
        }
        mob.face(target.tile()); // Go back to facing the target.
    }

    private void fireSkullAttack(Mob mob) {
        //mob.forceChat("FIRE SKULL -> MAGIC ATTACK");

        mob.resetFaceTile(); // Stop facing the target
        //Target all raids party members
        for (Mob t : getPossibleTargets(mob, 20, true, false)) {
            var tileDist = mob.tile().transform(3, 3, 0).distance(t.tile());
            var delay = Math.max(1, (20 + (tileDist * 12)) / 30);
            new Projectile(mob, t, 88, 20, 12 * tileDist, 30, 30, 0, 14, 5).sendProjectile();
            t.hit(mob, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
        }
        mob.face(target.tile()); // Go back to facing the target.
    }

    private void meleeAttack(Mob mob, Mob target) {
        //mob.forceChat("MELEE ATTACK");
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
        mob.animate(mob.attackAnimation());
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        // Determine if we're going to melee or mage
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            int chance = World.getWorld().random(6);
            if (chance == 1) {
                fireSkullAttack(mob);
            } else if (chance == 2) {
                webAttack(mob);
            } else {
                meleeAttack(mob, target);
            }
        } else {
            int chance = World.getWorld().random(3);
            if (chance == 1) {
                fireSkullAttack(mob);
            } else {
                webAttack(mob);
            }
            // Do an animation..
            mob.animate(5322);
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 10;
    }
}

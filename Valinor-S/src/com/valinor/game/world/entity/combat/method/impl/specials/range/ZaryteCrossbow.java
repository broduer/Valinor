package com.valinor.game.world.entity.combat.method.impl.specials.range;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.animations.Animation;
import com.valinor.game.world.entity.masks.animations.Priority;
import com.valinor.game.world.entity.mob.player.Player;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 09, 2022
 */
public class ZaryteCrossbow extends CommonCombatMethod {

    private static final Animation ANIMATION = new Animation(9168, Priority.HIGH);

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        final Player player = mob.getAsPlayer();

        player.animate(ANIMATION);

        //Fire projectile
        new Projectile(mob, target, 1995, 50, 70, 44, 35, 0).sendProjectile();

        //Decrement ammo by 1
        CombatFactory.decrementAmmo(player);

        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED),2, CombatType.RANGED).checkAccuracy();
        hit.submit();
        if(hit.getDamage() > 0) {
            player.putAttrib(AttributeKey.ZARYTE_CROSSBOW_SPEC_ACTIVE, true);
        }
        CombatSpecial.drain(mob, CombatSpecial.ARMADYL_CROSSBOW.getDrainAmount());
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 6;
    }
}

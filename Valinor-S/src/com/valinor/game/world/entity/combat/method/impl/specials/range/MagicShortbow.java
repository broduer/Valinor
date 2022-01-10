package com.valinor.game.world.entity.combat.method.impl.specials.range;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.animations.Animation;
import com.valinor.game.world.entity.masks.animations.Priority;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.player.Player;

public class MagicShortbow extends CommonCombatMethod {

    private static final Animation ANIMATION = new Animation(1074, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(250, 100);

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        final Player player = mob.getAsPlayer();

        player.animate(ANIMATION);
        player.performGraphic(GRAPHIC);

        //Send 2 arrow projectiles
        new Projectile(player, target, 249, 20, 36, 40, 36, 0).sendProjectile();
        new Projectile(player, target, 249, 50, 56, 40, 36, 0).sendProjectile();

        //Remove 2 arrows from ammo
        CombatFactory.decrementAmmo(player);
        CombatFactory.decrementAmmo(player);

        Hit hit1 = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED),3, CombatType.RANGED).checkAccuracy();
        hit1.submit();

        Hit hit2 = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED),2, CombatType.RANGED).checkAccuracy();
        hit2.submit();
        CombatSpecial.drain(mob, CombatSpecial.MAGIC_SHORTBOW.getDrainAmount());
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed() + 1;
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 6;
    }
}

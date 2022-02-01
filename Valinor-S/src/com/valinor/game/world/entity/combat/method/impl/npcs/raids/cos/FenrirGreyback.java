package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cos;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;

import static com.valinor.game.world.entity.AttributeKey.CANNOT_ATTACK_OUT_OF_DIST;
import static com.valinor.game.world.entity.AttributeKey.FENRIR_SPECIAL_ACTIVE;

/**
 * @author Patrick van Elderen | May, 13, 2021, 11:55
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class FenrirGreyback extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if(mob.<Boolean>getAttribOr(FENRIR_SPECIAL_ACTIVE, false)) {
            mob.animate(mob.attackAnimation());
            mob.forceChat("DIE, FILTHY MUGGLES!");
            ArrayList<Mob> targets = getPossibleTargets(mob, 64, true, false);
            for (Mob t : targets) {
                if (t == null) {
                    continue;
                }

                //Fenrir is out of distance with possible targets
                if (mob.tile().distance(t.tile()) >= 2) {
                    final var tile = t.tile().copy();
                    Projectile projectile = new Projectile(mob.getCentrePosition(), tile, 1,1637,110, 40, 25, 0,0);
                    projectile.sendProjectile();

                    Chain.bound(null).runFn(4, () -> {
                        World.getWorld().tileGraphic(1905, tile, 0,0);
                        if (t != null && t.tile().equals(tile)) {
                            t.hit(mob, World.getWorld().random(18, 35));
                        }
                    });
                } else {
                    melee(mob, target);
                }
            }
            mob.clearAttrib(FENRIR_SPECIAL_ACTIVE);
        } else {
            melee(mob, target);
        }
    }

    private void melee(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        if(World.getWorld().rollDie(10,2)) {
            mob.forceChat("GRRRRRRRRRRRRRRRR");
            target.hit(mob, World.getWorld().random(8, 12));
        } else {
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return mob.<Boolean>getAttribOr(FENRIR_SPECIAL_ACTIVE, false) ? 12 : 1;
    }
}

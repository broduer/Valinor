package com.valinor.game.world.entity.combat.method.impl.specials.magic;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.AreaConstants;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 11, 2022
 */
public class DawnbringerStaff extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if(mob.isPlayer()) {
            Player player = mob.getAsPlayer();
            if(player.tile().inArea(AreaConstants.VERZIK)) {
                player.graphic(1546, 100,0);
                player.animate(1167);

                if(target.isNpc()) {
                    Projectile projectile = new Projectile(player, target, 1547, 37, 70, 35,10,0);
                    projectile.sendProjectile();
                }

                //The staff's special attack, Pulsate, fires a powerful blast towards Verzik Vitur, inflicting 75-150 hitpoints of damage towards her
                int damage = World.getWorld().random(75, 150);
                Hit hit = target.hit(mob, damage, 3, CombatType.MAGIC).checkAccuracy();
                hit.submit();

                //Drain spec after the attack
                CombatSpecial.drain(mob, CombatSpecial.DAWNBRINGER.getDrainAmount());
            }
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 7;
    }
}

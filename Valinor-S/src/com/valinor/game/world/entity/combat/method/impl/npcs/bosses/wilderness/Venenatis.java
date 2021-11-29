package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.wilderness;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.route.routes.DumbRoute;

/**
 * Handles Venenatis' combat.
 *
 * @author Professor Oak
 */
public class Venenatis extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (withinDistance(2) && World.getWorld().rollDie(3, 1)) { // melee has "halberd" range
            meleeAttack(mob, target);
        } else {
            magicAttack(mob, target);
        }

        if (World.getWorld().rollDie(14, 1)) { // can stack with melee/mage attack
            webAttack(mob, target);
        }

        if (World.getWorld().rollDie(8, 1)) {
            target.hit(mob, 8, SplatType.POISON_HITSPLAT);
        }

        if (World.getWorld().rollDie(20, 1)) {
            drain(mob, target);
        }
    }

    //Melee attack: She simply lunges at the player, dealing up to 50 damage.
    private void meleeAttack(Mob mob, Mob target) {
        mob.getAsNpc().combatInfo().maxhit = 50;
        mob.animate(mob.attackAnimation());
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
    }

    //Magic attack: Launches a magical projectile which hits all players in range, dealing up to 22 damage.
    //This attack has 100% accuracy and ignores single/multicombat area restrictions and players in hiding.
    private void magicAttack(Mob mob, Mob target) {
        mob.getAsNpc().combatInfo().maxhit = 22;
        mob.graphic(164);
        mob.animate(5322);
        for (Player p : mob.getLocalPlayers()) {
            if (DumbRoute.withinDistance(mob, p, 8)) {
                new Projectile(mob, target, 165, 40, mob.projectileSpeed(target), 23, 21, 0).sendProjectile();
                target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), mob.getProjectileHitDelay(target), CombatType.MAGIC).checkAccuracy().submit();
            }
        }
    }

    //Web attack: Her most deadly attack, Venenatis throws a ball of web at the player, dealing up to 50
    //damage. She usually combines this with a melee or magic attack depending on her distance from the
    // target and can easily kill players. Having Protect from Melee active prevents damage from this
    // attack. There is also a rare chance that this attack will instead take the appearance and effect
    // of the Bind-like attack used by chaos druids, lowering the player's Attack by a few levels, and
    // dealing up to approximately 7 damage. Finally, the web attack may fail altogether and simply
    // "splash" on the player, dealing no damage or effects.
    private void webAttack(Mob mob, Mob target) {
        mob.getAsNpc().combatInfo().maxhit = 50;
        mob.animate(5322);
        new Projectile(mob, target, 1254, 75, mob.projectileSpeed(target), 45, 0, 0).sendProjectile();
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), mob.getProjectileHitDelay(target), CombatType.MAGIC).checkAccuracy().submit();
        target.stun(2, true);
        target.message("Venenatis hurls her web at you, sticking you to the ground.");
    }

    //Prayer drain: Venenatis launches a curse-like projectile at the player, draining 15-20 prayer points.
    //This attack can be used quickly without cooldown, as well as between other attacks.
    private void drain(Mob mob, Mob target) {
        target.graphic(172, 92, 0);
        new Projectile(mob, target, 171, 48, mob.projectileSpeed(target), 36, 31, 0).sendProjectile();

        var drain = World.getWorld().random(15, 20);
        target.skills().alterSkill(Skills.PRAYER, -drain);
        target.message("Your prayer was drained!");
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

package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.custom;

import com.valinor.game.content.events.boss_event.WorldBossEvent;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;

/**
 * Npc id = 3359
 * A custom boss which spawns random in the wild by the {@link WorldBossEvent}
 *
 * @author Patrick van Elderen | Zerikoth (PVE) | 06 feb. 2020 : 11:13
 * @see <a href="https://github.com/Patrick9-10-1995">Github profile</a>
 */
public class ZombiesChampion extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if(World.getWorld().rollDie(5, 1)) {
            rangeAttack((Npc) mob, target);
        } else {
            magicAttack((Npc) mob, target);
        }
    }

    private void rangeAttack(Npc npc, Mob target) {
        npc.animate(npc.attackAnimation());
        npc.resetFaceTile(); // Stop facing the target
        World.getWorld().getPlayers().forEach(p -> {
            if(p != null && target.tile().inSqRadius(p.tile(),12)) {
                var delay = mob.getProjectileHitDelay(target);
                new Projectile(npc, p, 298, 32, mob.projectileSpeed(target), 30, 30, 0).sendProjectile();
                p.hit(npc, CombatFactory.calcDamageFromType(npc, p, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().submit();
            }
        });

        npc.face(target.tile()); // Go back to facing the target.
    }

    private void magicAttack(Npc npc, Mob target) {
        npc.animate(npc.attackAnimation());
        npc.resetFaceTile(); // Stop facing the target
        World.getWorld().getPlayers().forEach(p -> {
            if(p != null && target.tile().inSqRadius(p.tile(),12)) {
                new Projectile(npc, p, 448, 32, mob.projectileSpeed(target), 30, 30, 0).sendProjectile();
                var delay = mob.getProjectileHitDelay(target);
                p.hit(npc, CombatFactory.calcDamageFromType(npc, p, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
            }
        });

        npc.face(target.tile()); // Go back to facing the target.
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

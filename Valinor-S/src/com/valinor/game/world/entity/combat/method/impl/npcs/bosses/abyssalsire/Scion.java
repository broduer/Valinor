package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.abyssalsire;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.route.StepType;
import com.valinor.util.TickDelay;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.NpcIdentifiers.SCION;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 30, 2021
 */
public class Scion extends CommonCombatMethod {

    private boolean melee = false;
    private final TickDelay transformDelay = new TickDelay();

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        Npc npc = mob.getAsNpc();
        if (mob.getAsNpc().id() == SCION && !transformDelay.isDelayed()) { // transform
            transformDelay.delay(World.getWorld().get(14, 18));
            Chain.bound(null).runFn(1, () -> {
                npc.lock();
                npc.transmog(SCION);
                npc.animate(7123);
                npc.getCombat().delayAttack(2);
                if (target != null) { // step away
                    int x = -npc.tile().unitVectorX(target.tile());
                    int y = -npc.tile().unitVectorY(target.tile());
                    if (x == 0)
                        x += World.getWorld().rollDie(2, 1) ? -1 : 1;
                    if (y == 0)
                        y += World.getWorld().rollDie(2, 1) ? -1 : 1;
                    melee = false;
                    npc.step(x, y, StepType.NORMAL);
                }
            }).then(2, () -> {
                transformDelay.delay(30);
                npc.unlock();
            });
        }

        if (melee) {
            npc.animate(npc.attackAnimation());
            target.hit(target, CombatFactory.calcDamageFromType(npc, target, CombatType.MELEE),0, CombatType.MELEE);
        } else {
            npc.animate(npc.getAsNpc().id() == SCION ? 7127 : npc.attackAnimation());
            Projectile projectile = new Projectile(npc, target, 628, 35, npc.projectileSpeed(target), 28, 43,0);
            projectile.sendProjectile();
            target.hit(target, CombatFactory.calcDamageFromType(npc, target, CombatType.RANGED),npc.getProjectileHitDelay(target), CombatType.RANGED);
        }
        if (World.getWorld().get() < 0.6) {
            melee = !melee;
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 4;
    }
}

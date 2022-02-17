package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cox;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.items.container.equipment.Equipment;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.RouteDirection;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.NpcIdentifiers.VESPULA;
import static com.valinor.util.NpcIdentifiers.VESPULA_7532;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since October 30, 2021
 */
public class Vespula extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (mob.isNpc()) {
            Npc npc = mob.getAsNpc();
            if (npc.id() != VESPULA || !withinDistance(1) || World.getWorld().random(5) > 3)
                rangeAttack(npc);
            else
                meleeAttack(npc, target);
        }
    }

    private void rangeAttack(Npc npc) {
        npc.animate(mob.attackAnimation());

        npc.resetFaceTile(); // Stop facing the target
        //Target all raids party members
        for (Mob t : getPossibleTargets(npc, 64, true, false)) {
            var tileDist = npc.tile().transform(1, 1, 0).distance(t.tile());
            var delay = Math.max(1, (50 + (tileDist * 12)) / 30);

            if (npc.id() == VESPULA) {
                new Projectile(npc, t, 1486, 20, 12 * tileDist, 70, 43, 0).sendProjectile();
            } else {
                new Projectile(npc, t, 1486, 20, 12 * tileDist, 40, 43, 0).sendProjectile();
            }
            t.hit(npc, CombatFactory.calcDamageFromType(npc, t, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().postDamage(this::handleAfterHit).submit();

            //echo projectile
            RouteDirection echoDir = World.getWorld().random(RouteDirection.values());
            Tile echoTile = t.tile().copy().transform(echoDir.deltaX, echoDir.deltaY, t.tile().level);

            if (npc.id() == VESPULA)
                new Projectile(mob.tile().transform(1, 1, 0), echoTile, 1, 1486, 100, 30, 70, 0, 0).sendProjectile();
            else
                new Projectile(mob.tile().transform(1, 1, 0), echoTile, 1, 1486, 100, 30, 40, 0, 0).sendProjectile();

            Chain.bound(null).runFn(4, () -> {
                if (t.isAt(echoTile)) {
                    t.hit(npc, CombatFactory.calcDamageFromType(npc, t, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().postDamage(this::handleAfterHit).submit();
                }
            });
        }
    }

    private void meleeAttack(Npc npc, Mob target) {
        npc.animate(7454);
        target.hit(npc, CombatFactory.calcDamageFromType(npc, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
    }

    public static void onHit(Npc npc, int damage) {
        if (npc.hp() > npc.maxHp() / 5 && (npc.hp() - damage) <= npc.maxHp() / 5) {
            // land
            npc.transmog(VESPULA_7532);
            npc.heal(npc.maxHp());
            npc.combatInfo(World.getWorld().combatInfo(VESPULA_7532));
            npc.setCombatMethod(World.getWorld().combatInfo(VESPULA_7532).scripts.newCombatInstance());
            npc.animate(7457);
            Chain.bound(null).runFn(50, () -> {
                if (!npc.dead()) {
                    npc.transmog(VESPULA);
                    npc.combatInfo(World.getWorld().combatInfo(VESPULA_7532));
                    npc.setCombatMethod(World.getWorld().combatInfo(VESPULA_7532).scripts.newCombatInstance());
                    npc.animate(7452);
                    npc.heal(npc.maxHp());
                }
            });
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 12;
    }

    public void handleAfterHit(Hit hit) {
        Mob attacker = hit.getAttacker();
        Mob target = hit.getTarget();
        if (World.getWorld().rollDie(5, 1)) {
            if (!Equipment.venomHelm(target)) { // Serp helm stops poison.
                target.hit(attacker, 20, SplatType.POISON_HITSPLAT);
            }
        }
    }
}

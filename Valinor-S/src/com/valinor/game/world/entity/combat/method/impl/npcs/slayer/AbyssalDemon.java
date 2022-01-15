package com.valinor.game.world.entity.combat.method.impl.npcs.slayer;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.ProjectileRoute;
import com.valinor.util.Utils;

import java.util.List;

/**
 * @author PVE
 * @Since augustus 05, 2020
 */
public class AbyssalDemon extends CommonCombatMethod {

    private void teleportAttack(Mob mob, Mob target) {
        Mob entity = Utils.rollDie(2, 1) ? mob : target;
        List<Tile> tiles = target.tile().area(1, pos -> World.getWorld().clipAt(pos) == 0 && !pos.equals(entity.tile()) && !ProjectileRoute.allow(entity, pos));
        Tile destination = Utils.randomElement(tiles);
        if(destination == null) return;
        entity.teleport(destination);
        entity.graphic(409);
        if (entity == target) {
            target.getCombat().reset();
            System.out.println("cmb reset 2121234");
        }
    }

    private void basicAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (Utils.rollDie(4, 1))
            teleportAttack(mob, target);
        else
            basicAttack(mob, target);
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;
    }
}

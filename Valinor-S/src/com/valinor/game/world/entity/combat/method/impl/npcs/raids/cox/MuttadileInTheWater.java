package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cox;

import com.valinor.game.content.raids.party.Party;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Area;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since October 31, 2021
 */
public class MuttadileInTheWater extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        magicAttack((Npc) mob);
    }

    private void magicAttack(Npc npc) {
        for (Mob t : getPossibleTargets(npc, 64, true, false)) {
            var tileDist = npc.tile().transform(1, 1, 0).distance(t.tile());
            var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
            new Projectile(npc, t, 393, 20, 12 * tileDist, 0, 30, 0).sendProjectile();
            t.hit(npc, CombatFactory.calcDamageFromType(npc, t, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 15;
    }
}

package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.wilderness.vetion;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;

import java.util.List;

import static com.valinor.util.NpcIdentifiers.SKELETON_HELLHOUND_6613;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 08, 2022
 */
public class SkeletonHellhound extends CommonCombatMethod {


    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;
    }

    @Override
    public boolean canMultiAttackInSingleZones() {
        return true;
    }

    @Override
    public void onDeath(Player killer, Npc npc) {
        if(npc.id() == SKELETON_HELLHOUND_6613) {
            Npc vetion = npc.getAttribOr(AttributeKey.BOSS_OWNER, null);
            if (vetion != null) {
                //Check for any minions.
                List<Npc> minList = vetion.getAttribOr(AttributeKey.MINION_LIST, null);
                if (minList != null) {
                    minList.remove(npc);
                    // All minions dead? Enable damage on vetion again
                    if (minList.size() == 0) {
                        vetion.putAttrib(AttributeKey.VETION_HELLHOUND_SPAWNED, false);
                    }
                }
            }
        }
    }
}

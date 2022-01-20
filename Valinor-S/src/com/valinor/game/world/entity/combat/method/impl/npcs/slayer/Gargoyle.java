package com.valinor.game.world.entity.combat.method.impl.npcs.slayer;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ItemIdentifiers.ROCK_HAMMER;
import static com.valinor.util.NpcIdentifiers.GARGOYLE;
import static com.valinor.util.NpcIdentifiers.GARGOYLE_413;

/**
 * @author PVE
 * @Since augustus 05, 2020
 */
public class Gargoyle extends CommonCombatMethod {

    private boolean smashed = false;

    @Override
    public void deathRespawn(Npc npc) {
        smashed = false;
        npc.transmog(GARGOYLE);
        super.deathRespawn(npc);
    }

    @Override
    public boolean customOnDeath(Hit hit) {
        if (!smashed) {
            if (hit.getAttacker() != null && hit.getAttacker().getAsPlayer() != null
                && hit.getAttacker().getAsPlayer().getInventory().contains(ROCK_HAMMER)) {
                hit.getTarget().getAsNpc().transmog(GARGOYLE_413);
                hit.getTarget().getAsNpc().animate(1520);
                smash(hit.getAttacker().getAsPlayer(), hit.getTarget().getAsNpc(), false);
            } else
                hit.getTarget().getAsNpc().setHitpoints(1);
            return true;
        }
        return true;
    }

    public void smash(Player player, Npc npc, boolean manual) {
        if (manual && npc.hp() > 9) {
            player.message("The gargoyle is not weak enough to be smashed!");
            return;
        }

        Chain.bound(null).runFn(1, () -> {
            player.getCombat().reset();
            player.animate(1665);
            String plural = player.getEquipment().containsAny(ItemIdentifiers.GRANITE_MAUL, ItemIdentifiers.GRANITE_MAUL_12848, ItemIdentifiers.GRANITE_MAUL_24225) ? "granite maul" : "rock hammer";
            player.message("You smash the Gargoyle with the "+plural+".");
        }).then(1, () -> {
            npc.hidden(true);
            smashed = true;
            npc.hp(0, 0);
            npc.die(null);
        });
    }

    private void basicAttack(Mob mob, Mob target) {
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
        mob.animate(mob.attackAnimation());
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
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

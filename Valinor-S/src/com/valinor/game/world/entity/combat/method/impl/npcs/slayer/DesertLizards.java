package com.valinor.game.world.entity.combat.method.impl.npcs.slayer;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.ItemIdentifiers;

/**
 * @author PVE
 * @Since augustus 05, 2020
 */
public class DesertLizards extends CommonCombatMethod {

    public static void iceCooler(Player player, Npc npc, boolean manual) {
        if (npc.getCombat().getTarget() != player) {
            player.message("That desert lizard is not fighting you.");
            return;
        }

        if (manual && npc.hp() > 4) {
            player.message("The desert lizard is not weak enough to be cooled!");
            return;
        }

        if(!player.inventory().contains(ItemIdentifiers.ICE_COOLER)) {
            player.message("You need at least one ice cooler.");
            return;
        }

        player.animate(2779);
        npc.graphic(85);
        player.inventory().remove(new Item(ItemIdentifiers.ICE_COOLER, 1));
        npc.hp(0, 0);
        npc.die(null);
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

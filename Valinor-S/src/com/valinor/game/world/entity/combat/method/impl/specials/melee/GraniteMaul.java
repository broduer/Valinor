package com.valinor.game.world.entity.combat.method.impl.specials.melee;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.player.EquipSlot;

import static com.valinor.util.CustomItemIdentifiers.RING_OF_VIGOUR;
import static com.valinor.util.ItemIdentifiers.GRANITE_MAUL_12848;

/**
 * Granite maul
 *
 * @author Gabriel Hannason
 */
public class GraniteMaul extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        double vigour = 0;
        if(mob.getAsPlayer().getEquipment().hasAt(EquipSlot.RING, RING_OF_VIGOUR)) {
            vigour += mob.getSpecialAttackPercentage() * 0.1;
        }

        int specPercentage = (int) (mob.getSpecialAttackPercentage() + vigour);

        //Make sure the player has enough special attack
        if (specPercentage < mob.getAsPlayer().getCombatSpecial().getDrainAmount()) {
            mob.message("You do not have enough special attack energy left!");
            mob.setSpecialActivated(false);
            CombatSpecial.updateBar(mob.getAsPlayer());
            return;
        }
        mob.animate(1667);
        mob.graphic(340, 92, 0);
        //TODO mob.world().spawnSound(mob.tile(), 2715, 0, 10)

        int delay = 0;
        if (mob.isPlayer() && target.isPlayer()) {
            int renderIndexOf = World.getWorld().getPlayers().getRenderOrderInternal().indexOf(mob.getIndex());
            int renderIndexOf2 = World.getWorld().getPlayers().getRenderOrderInternal().indexOf(target.getIndex());
            delay = renderIndexOf > renderIndexOf2 ? 1 : 0;
        }

        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), delay, CombatType.MELEE).checkAccuracy();

        if (hit.getDamage() > 49) {
            hit.setDamage(mob.getAsPlayer().getEquipment().hasAt(EquipSlot.WEAPON, GRANITE_MAUL_12848) ? 50 : 49);
        }

        hit.pidded = true; // force instant handling
        hit.submit();
        CombatSpecial.drain(mob, mob.getAsPlayer().getCombatSpecial().getDrainAmount());
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

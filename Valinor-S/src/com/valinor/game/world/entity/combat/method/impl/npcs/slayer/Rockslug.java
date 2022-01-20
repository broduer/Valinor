package com.valinor.game.world.entity.combat.method.impl.npcs.slayer;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.EquipSlot;

import static com.valinor.util.ItemIdentifiers.BAG_OF_SALT;
import static com.valinor.util.ItemIdentifiers.BRINE_SABRE;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 20, 2022
 */
public class Rockslug extends CommonCombatMethod {

    private boolean salted = false;

    @Override
    public void deathRespawn(Npc npc) {
        salted = false;
        super.deathRespawn(npc);
    }

    @Override
    public boolean customOnDeath(Hit hit) {
        if(salted) {
            super.customOnDeath(hit);
        }
        if (hit != null && hit.getAttacker() != null && hit.getAttacker().getAsPlayer() != null && hit.getAttacker().getAsPlayer().getEquipment().hasAt(EquipSlot.WEAPON, BRINE_SABRE)) { // brine sabre bypasses need for salt
            super.customOnDeath(hit);
        } else if (hit != null && hit.getAttacker() != null && hit.getAttacker().getAsPlayer() != null && hit.getAttacker().getAsPlayer().inventory().contains(BAG_OF_SALT, 1)) { // autosalt unlock
            super.customOnDeath(hit);
            hit.getAttacker().getCombat().reset();
            hit.getAttacker().getAsPlayer().getInventory().remove(BAG_OF_SALT, 1);
            hit.getTarget().hp(0, 0);
            hit.getTarget().getAsNpc().die(null);
        } else {
            hit.getTarget().setHitpoints(1);
            hit.getTarget().getCombat().reset();
        }
        return true;
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

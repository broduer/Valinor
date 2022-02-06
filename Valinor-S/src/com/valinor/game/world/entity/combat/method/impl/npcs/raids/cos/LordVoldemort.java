package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cos;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.magic.Autocasting;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.combat.weapon.WeaponInterfaces;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.game.content.items.combine.ElderWand.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since October 25, 2021
 */
public class LordVoldemort extends CommonCombatMethod {

    private int attacks = 0;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        attacks++;
        if(attacks == 10) {
            expelliarmus(((Npc) mob));
            attacks = 0;
        } else {
            var random = Utils.random(7);
            switch (random) {
                case 0, 1 -> crucio(((Npc) mob));
                case 2, 3 -> petrificusTotalus(mob);
                case 4, 5 -> sectumsempra(((Npc) mob));
                default -> avadaKedavra(((Npc) mob));
            }
        }
    }

    private void expelliarmus(Npc npc) {
        //Expelliarmus has a max hit of 35
        npc.combatInfo().maxhit = 35;
        npc.animate(401);
        npc.forceChat("Expelliarmus!");

        npc.resetFaceTile(); // Stop facing the target
        //Target all raids party members
        for (Mob t : getPossibleTargets(mob, 64, true, false)) {
            new Projectile(npc, t, EXPELLIARMUS_PROJECTILE, 32, mob.projectileSpeed(t), 30, 30, 0).sendProjectile();
            var delay = mob.getProjectileHitDelay(t);
            Chain.bound(null).runFn(delay, () -> disarm(t.getAsPlayer()));
            t.hit(npc, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
        }
    }

    private void disarm(Player player) {
        final Item item = player.getEquipment().get(EquipSlot.WEAPON);
        if (item != null && player.inventory().hasCapacity(item)) {
            player.getEquipment().remove(item, EquipSlot.WEAPON, true);
            player.getEquipment().unequip(EquipSlot.WEAPON);
            WeaponInterfaces.updateWeaponInterface(player);
            CombatSpecial.updateBar(player);
            player.setSpecialActivated(false);
            Autocasting.setAutocast(player, null);
            player.looks().resetRender();
            player.inventory().add(item);
        }
    }

    private void petrificusTotalus(Mob mob) {
        //petrificus totalus has a max hit of 32
        mob.getAsNpc().combatInfo().maxhit = 32;
        mob.animate(401);
        mob.forceChat("Petrificus Totalus!");

        mob.resetFaceTile(); // Stop facing the target
        //Target all raids party members
        for (Mob t : getPossibleTargets(mob, 64, true, false)) {
            new Projectile(mob, t, PETRIFICUS_TOTALUS_PROJECTILE, 32, mob.projectileSpeed(t), 30, 30, 0).sendProjectile();
            var delay = mob.getProjectileHitDelay(t);
            t.hit(mob, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
        }
    }

    private void sectumsempra(Npc npc) {
        //sectumsempra totalus has a max hit of 42
        npc.combatInfo().maxhit = 42;
        npc.animate(401);
        npc.forceChat("Sectumsempra!");

        npc.resetFaceTile(); // Stop facing the target
        //Target all raids party members
        for (Mob t : getPossibleTargets(mob, 64, true, false)) {
            new Projectile(npc, t, SECTUMSEMPRA_PROJECTILE, 32, mob.projectileSpeed(t), 30, 30, 0).sendProjectile();
            var delay = mob.getProjectileHitDelay(t);
            t.hit(npc, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
        }
    }

    private void crucio(Npc npc) {
        //sectumsempra totalus has a max hit of 36
        npc.combatInfo().maxhit = 36;
        npc.animate(401);
        npc.forceChat("Crucio!");

        npc.resetFaceTile(); // Stop facing the target
        //Target all raids party members
        for (Mob t : getPossibleTargets(mob, 64, true, false)) {
            new Projectile(npc, t, CRUCIATUS_CURSE_PROJECTILE, 32, mob.projectileSpeed(t), 30, 30, 0).sendProjectile();
            var delay = mob.getProjectileHitDelay(t);
            t.hit(npc, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
        }
    }

    private void avadaKedavra(Npc npc) {
        npc.animate(805);
        npc.forceChat("Avada Kedavra!");

        npc.resetFaceTile(); // Stop facing the target
        //Target all raids party members

        for (Mob t : getPossibleTargets(mob, 64, true, false)) {
            new Projectile(npc, t, AVADA_KEDAVRA_PROJECTILE, 32, mob.projectileSpeed(t), 30, 30, 0).sendProjectile();
            var delay = mob.getProjectileHitDelay(t);
            t.hit(npc, Prayers.usingPrayer(t, Prayers.PROTECT_FROM_MAGIC) ? World.getWorld().random(1, 40) : World.getWorld().random(1, 80), delay);
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
}

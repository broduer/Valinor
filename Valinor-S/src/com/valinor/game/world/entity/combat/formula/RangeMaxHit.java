package com.valinor.game.world.entity.combat.formula;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.combat.weapon.FightStyle;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.equipment.Equipment;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.util.NpcIdentifiers;

import java.util.Arrays;
import java.util.HashSet;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 06, 2021
 */
public class RangeMaxHit {

    private static final HashSet<Integer> TWISTED_BOWS = new HashSet<>(Arrays.asList(TWISTED_BOW, TWISTED_BOW_I, TWISTED_BOW_ORANGE, TWISTED_BOW_PURPLE, SANGUINE_TWISTED_BOW));

    public static int maxHit(Mob mob, Mob target, boolean factorInAmmoRangeStr, boolean includeNpcMax) {
        Player player = (Player) mob;
        EquipmentInfo.Bonuses bonuses = EquipmentInfo.totalBonuses(player, World.getWorld().equipmentInfo(), !factorInAmmoRangeStr);

        double prayerBonus = 1.0;
        // Prayer additions
        if (Prayers.usingPrayer(player, Prayers.SHARP_EYE)) {
            prayerBonus = 1.05;
        } else if (Prayers.usingPrayer(player, Prayers.HAWK_EYE)) {
            prayerBonus = 1.10;
        } else if (Prayers.usingPrayer(player, Prayers.EAGLE_EYE)) {
            prayerBonus = 1.15;
        } else if (Prayers.usingPrayer(player, Prayers.RIGOUR)) {
            prayerBonus = 1.23;
        }

        // The magic shortbow spec ignores bonus like prayers, void etc...
        var usingSpecialAttack = player.isSpecialActivated();
        var isMSB = player.getEquipment().hasAt(EquipSlot.WEAPON, MAGIC_SHORTBOW) || player.getEquipment().hasAt(EquipSlot.WEAPON, MAGIC_SHORTBOW_I);
        var ignoreMSBBonus = isMSB && usingSpecialAttack;
        //Magic short bow ignores prayer bonus on special attacks
        if (ignoreMSBBonus) {
            prayerBonus = 1.0;
        }

        int rangeLevel = player.skills().level(Skills.RANGED);
        double A = (int) Math.floor(rangeLevel * prayerBonus);

        // Accurate mode gives you 3 extra levels in the algorithm.
        if (player.getCombat().getFightType().getStyle().equals(FightStyle.ACCURATE) && !isMSB) {//Magic short bow ignores style bonus
            A += 3;
        }

        A += 8;

        double B = bonuses.rangestr;

        int weapon = player.getEquipment().get(3) == null ? -1 : player.getEquipment().get(3).getId();

        //The base damage
        double D = (0.5 + ((A * (B + 64d)) / 640d));

        //System.out.println("before tbow: "+D);

        if ((TWISTED_BOWS.contains(weapon)) && target != null && target.isNpc()) {
            int magicLevel = 0;

            if (((Npc) target).combatInfo() != null && ((Npc) target).combatInfo().stats != null)
                magicLevel = ((Npc) target).combatInfo().stats.magic;

            double damage = 250D + ((3 * magicLevel - 14D) / 100D) - (Math.pow(3 * magicLevel / 10.0 - 140.0, 2) / 100D);
            damage = Math.min(250, damage);
            D *= Math.min(2D, 1D + damage);

            //Give the sanguine twisted bow a max hit of +10
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, SANGUINE_TWISTED_BOW))
                D += 10;
        }

        //System.out.println("D: "+D);

        // Void effect adds 10%.
        if (FormulaUtils.voidRanger(player)) {
            D *= 1.10;
        }

        // Elite Void effect adds extra 2.5%.
        if (FormulaUtils.wearingEliteVoid(player) || FormulaUtils.voidCustomRanger(player)) {
            D *= 1.125;
        }

        var wearingAnyBlackMask = FormulaUtils.wearingBlackMask(player) || FormulaUtils.wearingBlackMaskImbued(player) || player.getEquipment().wearingSlayerHelm();
        //Special attacks
        if (wearingAnyBlackMask && target != null && target.isNpc() && includeNpcMax) {
            if (target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.15;
            }

            if (Slayer.creatureMatches(player, target.getAsNpc().id())) {
                D *= 1.15;
            }
        }

        //(Note: the black mask bonus is ignored when using a salve amulet)
        if (player.getEquipment().hasAt(EquipSlot.AMULET, SALVE_AMULET) && !wearingAnyBlackMask && target != null && includeNpcMax) {
            if (target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.15;
            }

            if (FormulaUtils.isUndead(target)) {
                D *= 1.15;
            }
        }

        if (player.getEquipment().hasAt(EquipSlot.AMULET, SALVE_AMULETI) && !wearingAnyBlackMask && target != null && includeNpcMax) {
            if (target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.20;
            }

            if (FormulaUtils.isUndead(target)) {
                D *= 1.20;
            }
        }

        //• Salve amulet (e): 1.20
        if (player.getEquipment().hasAt(EquipSlot.AMULET, SALVE_AMULET_E) && !wearingAnyBlackMask && target != null && includeNpcMax) {
            if (target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.20;
            }

            if (FormulaUtils.isUndead(target)) {
                D *= 1.20;
            }
        }

        //• Salve amulet (ei): 1.20 (all styles)
        if (player.getEquipment().hasAt(EquipSlot.AMULET, SALVE_AMULETEI) && !wearingAnyBlackMask && target != null && includeNpcMax) {
            if (target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.20;
            }

            if (FormulaUtils.isUndead(target)) {
                D *= 1.20;
            }
        }

        if (player.getEquipment().hasAt(EquipSlot.WEAPON, GREEN_SLAYER_HELMET_I) && target != null && target.isNpc() && includeNpcMax) {
            D *= 1.10;
        }

        var bowOfFaerdhinen = weapon == BOW_OF_FAERDHINEN || weapon == BOW_OF_FAERDHINEN_C || (weapon >= BOW_OF_FAERDHINEN_C_25884 && weapon <= BOW_OF_FAERDHINEN_C_25896);
        var isCrystalBow = weapon == NEW_CRYSTAL_BOW;
        var crystalArmourBoosted = bowOfFaerdhinen || isCrystalBow;

        if (player.getEquipment().hasAt(EquipSlot.HEAD, CRYSTAL_HELM) && crystalArmourBoosted) {
            D *= 1.025;//2.5% damage boost
        }

        if (player.getEquipment().hasAt(EquipSlot.BODY, CRYSTAL_BODY) && crystalArmourBoosted) {
            D *= 1.075;//7.5% damage boost
        }

        if (player.getEquipment().hasAt(EquipSlot.LEGS, CRYSTAL_LEGS) && crystalArmourBoosted) {
            D *= 1.05;//5.0% damage boost
        }

        //• Dragon hunter crossbow: 1.25
        if ((player.getEquipment().hasAt(EquipSlot.WEAPON, DRAGON_HUNTER_CROSSBOW) || player.getEquipment().hasAt(EquipSlot.WEAPON, DRAGON_HUNTER_CROSSBOW_T)) && target != null && includeNpcMax) {
            if (target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.25;
            }

            if (FormulaUtils.isDragon(target)) {
                D *= 1.25;
            }
        }

        //Craws Bow
        if (FormulaUtils.hasCrawsBow(player) && target != null && target.isNpc() && includeNpcMax) {
            if (target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.50;
            }

            D *= 1.50;
        }

        //System.out.println("After multipliers: "+D);

        double baseSpecialModifier = 1.0;

        //The base modifier
        if (player.isSpecialActivated()) {
            baseSpecialModifier *= player.getCombatSpecial().getspecialMultiplier();
            //System.out.println("base spec mod: "+baseSpecialModifier);

            var ammoId = player.getEquipment().getId(EquipSlot.AMMO);
            var ammoName = ammoId == -1 ? "" : new Item(ammoId).definition(World.getWorld()).name.toLowerCase();
            if (Equipment.darkbow(weapon) && ammoName.contains("dragon arrow")) {
                baseSpecialModifier += 0.20;//• Dark bow (dragon arrows): 1.50
            }

            double totalSpecMod = baseSpecialModifier;
            //System.out.println("total spec mod: "+totalSpecMod);
            D *= totalSpecMod;
        }

        //System.out.println("After special multipliers: "+D);

        //Up this far its the OSRS calculation

        //Here we start custom modifiers

        double petBonus = 1.0;
        double weaponBonus = 1.0;
        double armourBonus = 1.0;
        double slayerPerkBonus = 1.0;

        if (player.hasPetOut("Skeleton hellhound pet")) {
            petBonus *= 1.05;
        }

        if (player.hasPetOut("Olmlet") && target != null && target.isNpc()) {
            if (target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY && includeNpcMax) {
                petBonus *= 1.10;
            }
            if (player.getRaids() != null && player.getRaids().raiding(player))
                petBonus *= 10.10;
        }

        if (player.getEquipment().hasAt(EquipSlot.WEAPON, CRAWS_BOW_C) && target != null && target.isNpc() && includeNpcMax) {
            weaponBonus *= 1.50;
        }

        if (player.getEquipment().hasAt(EquipSlot.HEAD, SARKIS_DARK_COIF) && target != null && target.isNpc() && includeNpcMax) {
            armourBonus *= 1.10;//10% damage boost
        }

        if (player.getEquipment().hasAt(EquipSlot.BODY, SARKIS_DARK_BODY) && target != null && target.isNpc() && includeNpcMax) {
            armourBonus *= 1.20;//20% damage boost
        }

        if (player.getEquipment().hasAt(EquipSlot.LEGS, SARKIS_DARK_LEGS) && target != null && target.isNpc() && includeNpcMax) {
            armourBonus *= 1.15;//15.0% damage boost
        }

        var weakSpot = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.WEAK_SPOT);
        if (weakSpot && target != null && target.isNpc()) {
            if (Slayer.creatureMatches(player, target.getAsNpc().id())) {
                slayerPerkBonus *= 1.10;
            }
        }

        //All custom modifiers combined
        int maxHit = (int) Math.round(D * petBonus * weaponBonus * armourBonus * slayerPerkBonus);

        //System.out.println("Max hit before additions: "+maxHit);

        if (player.hasPetOut("Little Nightmare")) {
            maxHit += 1;
        }

        if (player.hasPetOut("Baby Aragog")) {
            var increaseBy = target != null && target.isNpc() && includeNpcMax ? 5 : 1;
            maxHit += increaseBy;
        }

        if (player.getEquipment().hasAt(EquipSlot.AMULET, NECKLACE_OF_ANGUISH_OR)) {
            maxHit += 1;
        }

        if (player.getEquipment().hasAt(EquipSlot.WEAPON, MAGMA_BLOWPIPE)) {
            maxHit += 3;
        }

        if (player.hasPetOut("Youngllef pet")) {
            maxHit += 1;
        }

        if (player.hasPetOut("Corrupted Youngllef pet")) {
            maxHit += 2;
        }

        //System.out.println("final max hit "+maxHit);

        return maxHit;
    }

}

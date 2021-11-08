package com.valinor.game.world.entity.combat.formula;

import com.valinor.fs.NpcDefinition;
import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.combat.weapon.AttackType;
import com.valinor.game.world.entity.combat.weapon.FightStyle;
import com.valinor.game.world.entity.combat.weapon.WeaponType;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.NpcIdentifiers;

import java.util.ArrayList;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.CustomItemIdentifiers.TOTEMIC_PLATELEGS;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 06, 2021
 */
public class MeleeMaxHit {

    /**
     * The max hit
     * @param player The player performing the hit
     * @param includeNpcMax The npc is a PvP combat dummy
     * @return return the max hit based on the given calculations
     */
    public static int maxHit(Player player, boolean includeNpcMax) {
        //Start by calculating OSRS max hit
        EquipmentInfo.Bonuses bonuses = EquipmentInfo.totalBonuses(player, World.getWorld().equipmentInfo());

        //Prayer goes first
        double prayerBonus = 1;
        if (Prayers.usingPrayer(player, Prayers.BURST_OF_STRENGTH)) {
            prayerBonus = 1.05;
        } else if (Prayers.usingPrayer(player, Prayers.SUPERHUMAN_STRENGTH)) {
            prayerBonus = 1.10;
        } else if (Prayers.usingPrayer(player, Prayers.ULTIMATE_STRENGTH)) {
            prayerBonus = 1.15;
        } else if (Prayers.usingPrayer(player, Prayers.CHIVALRY)) {
            prayerBonus = 1.18;
        } else if (Prayers.usingPrayer(player, Prayers.PIETY)) {
            prayerBonus = 1.23;
        }

        //Get the potential target
        Mob target = player.getCombat().getTarget();

        double A = (int) Math.floor(player.skills().level(Skills.STRENGTH) * prayerBonus);

        //Fight style is now calculated
        WeaponType weaponType = player.getCombat().getWeaponType();
        if(weaponType != null && weaponType != WeaponType.UNARMED) {//Add the stance bonus from the combat options interface.
            if (player.getCombat().getFightType().getStyle().equals(FightStyle.AGGRESSIVE)) {
                A += 3;
            } else if (player.getCombat().getFightType().getStyle().equals(FightStyle.CONTROLLED)) {
                A += 1;
            }
        }

        A += 8;

        double B = bonuses.str;

        //The base damage
        double D = (0.5 + ((A * (B + 64d)) / 640d));

        //System.out.println("Before multipliers: "+D);

        // Void melee: multiply by 1.10. Round down.
        if (FormulaUtils.voidMelee(player) || FormulaUtils.wearingEliteVoid(player) || FormulaUtils.voidCustomMelee(player)) {
            D *= 1.10;
        }

        var wearingAnyBlackMask = FormulaUtils.wearingBlackMask(player) || FormulaUtils.wearingBlackMaskImbued(player) || player.getEquipment().wearingSlayerHelm();
        //Special attacks
        if(wearingAnyBlackMask && target != null && includeNpcMax) {
            if(target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.1667;
            }

            if(Slayer.creatureMatches(player, target.getAsNpc().id())) {
                D *= 1.1667;
            }
        }

        //(Note: the black mask bonus is ignored when using a salve amulet)
        if(player.getEquipment().hasAt(EquipSlot.AMULET, SALVE_AMULET) && !wearingAnyBlackMask && target != null && includeNpcMax) {
            if(target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.16;
            }

            if(FormulaUtils.isUndead(target)) {
                D *= 1.16;
            }
        }

        if(player.getEquipment().hasAt(EquipSlot.AMULET, SALVE_AMULETI) && !wearingAnyBlackMask && target != null && includeNpcMax) {
            if(target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.20;
            }

            if(FormulaUtils.isUndead(target)) {
                D *= 1.20;
            }
        }

        //• Salve amulet (e): 1.20
        if(player.getEquipment().hasAt(EquipSlot.AMULET, SALVE_AMULET_E) && !wearingAnyBlackMask && target != null && includeNpcMax) {
            if(target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.20;
            }

            if(FormulaUtils.isUndead(target)) {
                D *= 1.20;
            }
        }

        //• Salve amulet (ei): 1.20 (all styles)
        if(player.getEquipment().hasAt(EquipSlot.AMULET, SALVE_AMULETEI) && !wearingAnyBlackMask && target != null && includeNpcMax) {
            if(target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.20;
            }

            if(FormulaUtils.isUndead(target)) {
                D *= 1.20;
            }
        }

        if (player.getEquipment().hasAt(EquipSlot.WEAPON, RED_SLAYER_HELMET_I) && target != null && target.isNpc() && includeNpcMax) {
            D *= 1.10;
        }

        if (FormulaUtils.wearingTwistedSlayerHelmetI(player) && target != null && target.isNpc() && includeNpcMax) {
            D *= 1.10;
        }

        //• Arclight: 1.70 (vs. demons)
        if(player.getEquipment().hasAt(EquipSlot.WEAPON, ARCLIGHT) && target != null && includeNpcMax) {
            if(target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.70;
            }

            if(FormulaUtils.isDemon(target)) {
                D *= 1.70;
            }
        }

        //• Darklight: 1.60 (vs. demons)
        if(player.getEquipment().hasAt(EquipSlot.WEAPON, DARKLIGHT) && target != null && includeNpcMax) {
            if(target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.60;
            }

            if(FormulaUtils.isDemon(target)) {
                D *= 1.60;
            }
        }

        //• Leaf-bladed battleaxe: 1.175 (vs. kurask, turoth)
        if(player.getEquipment().hasAt(EquipSlot.WEAPON, DARKLIGHT) && target != null && includeNpcMax) {
            if(target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.175;
            }

            if(target.isNpc() && target.getAsNpc().def() != null && (target.getAsNpc().def().name.equalsIgnoreCase("Kurask") || target.getAsNpc().def().name.equalsIgnoreCase("Turoth"))) {
                D *= 1.175;
            }
        }

        //• Obsidian armour: 1.10
        if (FormulaUtils.obbyArmour(player) && FormulaUtils.hasObbyWeapon(player)) {
            D *= 1.10;
        }

        //• Berserker necklace: 1.20
        if(FormulaUtils.berserkerNecklace(player) && FormulaUtils.hasObbyWeapon(player)) {
            D *= 1.10;
        }

        if (player.getCombat().getFightType().getAttackType() == AttackType.CRUSH) {
            if (player.getEquipment().hasAt(EquipSlot.HEAD, INQUISITORS_GREAT_HELM)) {
                D *= 0.05;//0.5% damage boost
            }

            if (player.getEquipment().hasAt(EquipSlot.BODY, INQUISITORS_HAUBERK)) {
                D *= 0.05;//0.5% damage boost
            }

            if (player.getEquipment().hasAt(EquipSlot.LEGS, INQUISITORS_PLATESKIRT)) {
                D *= 0.05;//0.5% damage boost
            }

            if (player.getEquipment().hasAt(EquipSlot.HEAD, INQUISITORS_GREAT_HELM) || player.getEquipment().hasAt(EquipSlot.BODY, INQUISITORS_HAUBERK) || player.getEquipment().hasAt(EquipSlot.LEGS, INQUISITORS_PLATESKIRT)) {
                D *= 0.01;//1% damage boost for whole set
            }
        }

        //• Dragon hunter lance: 1.20
        if(player.getEquipment().hasAt(EquipSlot.WEAPON, DRAGON_HUNTER_LANCE) && target != null && includeNpcMax) {
            if(target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                D *= 1.20;
            }

            if(FormulaUtils.isDragon(target)) {
                D *= 1.20;
            }
        }

        //• Gadderhammer: 1.25 or 2.0 vs shades
        if(player.getEquipment().hasAt(EquipSlot.WEAPON, GADDERHAMMER)) {
            if(target != null && target.isNpc()) {
                Npc npc = target.getAsNpc();
                NpcDefinition def = npc.def();
                var isShade = def != null && def.name.equalsIgnoreCase("Shade");
                D *= isShade ? 1.25 : 2.00;
            }
        }

        if (FormulaUtils.hasViggorasChainMace(player) && target != null && target.isNpc() && includeNpcMax) {
            D *= 1.50;
        }

        //System.out.println("After multipliers: "+D);

        double baseSpecialModifier = 1.0;
        double extraModifier = 1.0;

        //The base modifier
        if(player.isSpecialActivated()) {
            baseSpecialModifier *= player.getCombatSpecial().getBaseSpecialMultiplier();
            //System.out.println("base spec mod: "+baseSpecialModifier);

            //Some weapons have bonus modifiers, * Armadyl godsword: 1.25
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, ARMADYL_GODSWORD)) {
                extraModifier *= 0.25;
            }

            //Some weapons have bonus modifiers, * Bandos godsword: 1.10
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, BANDOS_GODSWORD)) {
                extraModifier *= 0.10;
            }

            double totalSpecMod = baseSpecialModifier + extraModifier;
            //System.out.println("total spec mod: "+totalSpecMod);
            D *= totalSpecMod;
        }

        //System.out.println("After special multipliers: "+D);

        //• Dharok's set: multiply by: 1+ lost hp/100 * max hp/100
        if (CombatFactory.fullDharoks(player)) {
            double lostHp = player.maxHp() - player.hp();
            System.out.println(lostHp);
            D *= 1 + (lostHp /100 * player.maxHp() / 100);
        }

        //Up this far its the OSRS calculation

        //Here we start custom modifiers

        double petBonus = 1.0;
        double weaponBonus = 1.0;
        double armourBonus = 1.0;
        double slayerPerkBonus = 1.0;

        boolean ancientKingBlackDragonPet = player.hasPetOut("Ancient king black dragon");
        if(ancientKingBlackDragonPet && target != null && target.isNpc() && includeNpcMax) {
            Npc npc = (Npc) target;
            if (npc.def() != null && npc.def().name != null && FormulaUtils.isDragon(npc)) {
                petBonus *= 1.25;
            }
        }

        if (player.hasPetOut("Skeleton hellhound pet") && target != null && target.isNpc() && includeNpcMax) {
            petBonus *= 1.05;
        }

        if(player.hasPetOut("Olmlet") && target != null && target.isNpc() && includeNpcMax) {
            if(target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.COMBAT_DUMMY) {
                petBonus *= 1.10;
            }

            if (player.getRaids() != null && player.getRaids().raiding(player))
                petBonus *= 1.10;
        }

        if(player.hasPetOut("Pet zombies champion") && target != null && target.isNpc() && target.getAsNpc().isWorldBoss() && includeNpcMax) {
            petBonus *= 1.10;
        }

        if (player.getEquipment().hasAt(EquipSlot.WEAPON, VIGGORAS_CHAINMACE_C) && target != null && target.isNpc() && includeNpcMax) {
            weaponBonus *= 1.50;
        }

        if(player.getEquipment().hasAt(EquipSlot.WEAPON, CustomItemIdentifiers.SWORD_OF_GRYFFINDOR) && target != null && target.isNpc() && includeNpcMax) {
            weaponBonus *= 1.25;
        }

        if (player.getEquipment().hasAt(EquipSlot.HEAD, TOTEMIC_HELMET) && target != null && target.isNpc() && includeNpcMax) {
            armourBonus *= 1.10;//10% damage boost
        }

        if (player.getEquipment().hasAt(EquipSlot.BODY, TOTEMIC_PLATEBODY) && target != null && target.isNpc() && includeNpcMax) {
            armourBonus *= 1.20;//20% damage boost
        }

        if (player.getEquipment().hasAt(EquipSlot.LEGS, TOTEMIC_PLATELEGS) && target != null && target.isNpc() && includeNpcMax) {
            armourBonus *= 1.15;//15.0% damage boost
        }

        var weakSpot = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.WEAK_SPOT);
        if(weakSpot && target != null && target.isNpc()) {
            if(Slayer.creatureMatches(player, target.getAsNpc().id())) {
                slayerPerkBonus *= 1.10;
            }
        }

        //All custom modifiers combined
        int maxHit = (int) Math.round(D * petBonus * weaponBonus * armourBonus * slayerPerkBonus);

        List<Integer> increaseMaxHitbyOne = new ArrayList<>(List.of(GRANITE_MAUL_12848, ARMADYL_GODSWORD_OR, BANDOS_GODSWORD_OR, SARADOMIN_GODSWORD_OR, ZAMORAK_GODSWORD_OR, DRAGON_CLAWS_OR));
        if (increaseMaxHitbyOne.stream().anyMatch(w -> player.getEquipment().hasAt(EquipSlot.WEAPON, w))) {
            maxHit += 1;
        }

        if(player.getEquipment().hasAt(EquipSlot.AMULET, AMULET_OF_TORTURE_OR) || player.getEquipment().hasAt(EquipSlot.AMULET, AMULET_OF_FURY_OR) || player.getEquipment().hasAt(EquipSlot.AMULET, BERSERKER_NECKLACE_OR)) {
            maxHit += 1;
        }

        if(player.hasPetOut("Baby Barrelchest") || player.hasPetOut("Ancient barrelchest")) {
            maxHit += 1;
        }

        if(player.hasPetOut("Corrupted nechryarch pet")) {
            maxHit += 1;
        }

        if(player.hasPetOut("Youngllef pet")) {
            maxHit += 1;
        }

        if(player.hasPetOut("Corrupted Youngllef pet")) {
            maxHit += 2;
        }

        //System.out.println("Final max hit = "+maxHit);

        return maxHit;
    }
}

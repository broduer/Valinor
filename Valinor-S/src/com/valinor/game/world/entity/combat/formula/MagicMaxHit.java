package com.valinor.game.world.entity.combat.formula;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.magic.CombatSpell;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.util.NpcIdentifiers;
import com.valinor.util.timers.TimerKey;

import java.util.ArrayList;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 06, 2021
 */
public class MagicMaxHit {

    private static int getTridentMaxDamage(Player player, boolean swamp, boolean sang) {
        EquipmentInfo.Bonuses b = EquipmentInfo.totalBonuses(player, World.getWorld().equipmentInfo());
        int base = 20;
        if (swamp)
            base += 3;
        if(sang)
            base += 4;
        return (int) Math.round((Math.max(base, base + (Math.max(0, player.skills().level(Skills.MAGIC) - 75)) / 3)) * (1 + (b.magestr / 100.0)));
    }

    public static int maxHit(Player player, boolean includeNpcMax) {
        int baseMaxHit = 0;
        CombatSpell spell = player.getCombat().getCastSpell() != null ? player.getCombat().getCastSpell() : player.getCombat().getAutoCastSpell();
        if (spell != null) {
            EquipmentInfo.Bonuses b = EquipmentInfo.totalBonuses(player, World.getWorld().equipmentInfo());
            boolean hasTomeOfFire = player.getEquipment().hasAt(EquipSlot.SHIELD, TOME_OF_FIRE);
            Mob target = player.getCombat().getTarget();
            String spell_name = spell.name();
            int level = player.skills().level(Skills.MAGIC);

            //Find the base maximum damage a spell can deal.
            int spell_maxhit = spell.baseMaxHit();

            //• Slayer dart
            if (spell_name.equals("Magic Dart")) {
                spell_maxhit = (int) (10 + Math.floor(level/10D));
            }

            //• Trident of the seas
            if (spell_name.equals("Trident of the seas")) {
                spell_maxhit = getTridentMaxDamage(player,false,false);
            }

            //• Trident of the swamp
            if (spell_name.equals("Trident of the swamp")) {
                spell_maxhit = getTridentMaxDamage(player,true,false);
            }

            //• Sanguinesti staff
            if (spell_name.equals("Sanguinesti spell") || spell_name.equals("Infernal trident")) {
                spell_maxhit = getTridentMaxDamage(player,false,true);
            }

            //System.out.println("spell_maxhit "+spell_maxhit);

            //• God spells (level 60) in combination with Charge (level 80): the base max hit is 30.
            if (spell_name.equals("Saradomin Strike") || spell_name.equals("Claws of Guthix") || spell_name.equals("Flames of Zamorak")) {
                if (player.getTimers().has(TimerKey.CHARGE_SPELL)) {
                    spell_maxhit = 30;
                }
            }

            if (spell_name.toLowerCase().contains("fire") && hasTomeOfFire) {
                spell_maxhit *= 1.50;
            }

            boolean tridentStaff = spell_name.equals("Trident of the seas") || spell_name.equals("Trident of the swamp") || spell_name.equals("Sanguinesti spell") || spell_name.equals("Infernal trident");
            double multiplier = 1 + ((b.magestr > 0 ? b.magestr : 1.0) / 100);

            if(tridentStaff) { // Tridents have mage str build in the calculation
                multiplier = 1.0;
            }

            var wearingAnyBlackMask = FormulaUtils.wearingBlackMask(player) || FormulaUtils.wearingBlackMaskImbued(player) || player.getEquipment().wearingSlayerHelm();
            //Special attacks
            if(wearingAnyBlackMask && target != null && target.isNpc() && includeNpcMax) {
                Npc npc = target.getAsNpc();
                if(npc.id() == NpcIdentifiers.UNDEAD_COMBAT_DUMMY) {
                    multiplier += 0.15;
                }

                if(Slayer.creatureMatches(player, npc.id())) {
                    multiplier += 0.15;
                }
            }

            //System.out.println("spell_maxhit here "+spell_maxhit);

            if (FormulaUtils.hasThammaronSceptre(player) && target != null && target.isNpc() && includeNpcMax) {
                multiplier += 0.25;
            }

            if (player.getEquipment().hasAt(EquipSlot.WEAPON, THAMMARONS_STAFF_C) && target != null && target.isNpc() && includeNpcMax) {
                multiplier += 0.25;
            }

            if (player.getEquipment().hasAt(EquipSlot.HEAD, RED_SLAYER_HELMET_I)) {
                multiplier += 0.03;
            }

            if (player.getEquipment().hasAt(EquipSlot.HEAD, TZKAL_SLAYER_HELMET_I)) {
                multiplier += 0.03;//3% damage boost
            }

            //System.out.println("spell_maxhit now "+spell_maxhit);

            /**
             * When wearing the clock of invisibility with an elder wand you get a 10% damage boost vs npcs
             */
            boolean elderWand = player.getEquipment().hasAt(EquipSlot.WEAPON, ELDER_WAND);
            boolean cloakOfInvisibility = player.getEquipment().hasAt(EquipSlot.CAPE, CLOAK_OF_INVISIBILITY);
            boolean wearingBoth = elderWand && cloakOfInvisibility;
            if (target != null && target.isNpc() && wearingBoth) {
                multiplier += 0.10;
            }

            boolean inRaids = player.getRaids() != null && player.getRaids().raiding(player);
            if (target != null && cloakOfInvisibility && target.isNpc() && inRaids) {
                multiplier += 0.15;
            }

            // #Custom slayer effects
            var weakSpot = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.WEAK_SPOT);
            if (weakSpot && target != null && target.isNpc()) {
                if (Slayer.creatureMatches(player, target.getAsNpc().id())) {
                    multiplier += 0.10;
                }
            }

            //System.out.println("spell_maxhit wa "+spell_maxhit);

            boolean ancientKingBlackDragonPet = player.hasPetOut("Ancient king black dragon");
            if(ancientKingBlackDragonPet && target != null && target.isNpc() && includeNpcMax) {
                Npc npc = (Npc) target;
                if (npc.def() != null && npc.def().name != null && FormulaUtils.isDragon(npc)) {
                    multiplier += 0.25;
                }
            }

            if(player.hasPetOut("Snowflake")) {
                multiplier += 0.03;
            }

            int weapon = player.getEquipment().get(3) == null ? -1 : player.getEquipment().get(3).getId();
            if (spell_name.equals("Volatile spell") || spell_name.equals("Cursed spell")) {
                int baseLevel = level;
                if (baseLevel > 99)
                    baseLevel = 99;
                double levelTimes = 0.67;
                multiplier -= 0.15;
                spell_maxhit = (int) (baseLevel * levelTimes);
                if(spell_name.equals("Cursed spell"))
                    spell_maxhit += 2;
            }

            //System.out.println("spell_maxhit wonka "+spell_maxhit);

            // #Custom armour multipliers
            if (player.getEquipment().hasAt(EquipSlot.HEAD, DARK_SAGE_HAT) && target != null && target.isNpc() && includeNpcMax) {
                multiplier += 0.05;//5% damage boost
            }

            if (player.getEquipment().hasAt(EquipSlot.BODY, DARK_SAGE_ROBE_TOP) && target != null && target.isNpc() && includeNpcMax) {
                multiplier += 0.10;//10% damage boost
            }

            if (player.getEquipment().hasAt(EquipSlot.LEGS, DARK_SAGE_ROBE_BOTTOM) && target != null && target.isNpc() && includeNpcMax) {
                multiplier += 0.10;//10.0% damage boost
            }

            //System.out.println("spell_maxhit kozt "+spell_maxhit);

            int maxHit = (int) Math.round(spell_maxhit * multiplier);

            //System.out.println("maxHit "+maxHit);

            // #Custom Armour effects
            if (player.getEquipment().hasAt(EquipSlot.AMULET, OCCULT_NECKLACE_OR) || player.getEquipment().hasAt(EquipSlot.HANDS, TORMENTED_BRACELET_OR)) {
                maxHit += 1;
            }

            if (player.getEquipment().hasAt(EquipSlot.RING, MARVOLO_GAUNTS_RING) && target != null && target.isNpc()) {
                maxHit += 5;
            }

            //# Pet effects
            if (player.hasPetOut("Olmlet") && target != null && target.isNpc() && includeNpcMax) {
                var increaseBy = 5;
                if (target.isNpc() && target.getAsNpc().id() == NpcIdentifiers.UNDEAD_COMBAT_DUMMY) {
                    maxHit += increaseBy;
                }
                if (player.getRaids() != null && player.getRaids().raiding(player))
                    maxHit += increaseBy;
            }

            if (player.hasPetOut("Skeleton hellhound pet")) {
                var increaseBy = target != null && target.isNpc() && includeNpcMax ? 2 : 1;
                maxHit += increaseBy;
            }

            if (player.hasPetOut("Baby lava dragon pet")) {
                var increaseBy = target != null && target.isNpc() && includeNpcMax ? 5 : 1;
                maxHit += increaseBy;
            }

            if (player.hasPetOut("Mini necromancer pet")) {
                var increaseBy = target != null && target.isNpc() && includeNpcMax ? 5 : 1;
                maxHit += increaseBy;
            }

            if (player.hasPetOut("Nagini pet")) {
                var increaseBy = target != null && target.isNpc() && includeNpcMax ? 10 : 1;
                maxHit += increaseBy;
            }

            if (player.hasPetOut("Youngllef pet")) {
                maxHit += 1;
            }

            if (player.hasPetOut("Corrupted Youngllef pet")) {
                maxHit += 2;
            }

            if (player.hasPetOut("Little Nightmare")) {
                maxHit += 1;
            }

            //After all modifiers spell max hits
            if (spell_name.equals("Petrificus Totalus") && target != null && target.isPlayer()) {
                maxHit = 40;
            }

            if (spell_name.equals("Cruciatus Curse") && target != null && target.isPlayer()) {
                maxHit = 41;
            }

            if (spell_name.equals("Expelliarmus") && target != null && target.isPlayer()) {
                maxHit = 50;
            }

            if (spell_name.equals("Sectumsempra") && target != null && target.isPlayer()) {
                maxHit = 55;
            }

            if (spell_name.equals("Avada Kedavra") && target != null && target.isPlayer()) {
                maxHit = 82;
            }

            if (spell_name.equals("Sanguinesti spell")) {
                boolean holy_staff = weapon == HOLY_SANGUINESTI_STAFF;
                if (holy_staff) {
                    maxHit += 1;
                }
            }

            if(spell_name.equals("Infernal trident")) {
                maxHit += 2;
            }

            //Ice barrage in PvP is capped at 41
            if (spell_name.equals("Ice barrage") && target.isPlayer()) {
                maxHit = 41;
            }

            //Fire surge in PvP is capped at 49
            if (spell_name.equals("Fire surge") && target.isPlayer()) {
                maxHit = 49;
            }

            boolean isPvPDummy = target.isNpc() && !includeNpcMax;
            if(isPvPDummy || target.isPlayer()) {
                MagicMaxitCaps cap = MagicMaxitCaps.forWeapon(weapon);
                if(player.isSpecialActivated()) {
                    if (cap != null) {
                        if (maxHit > cap.maxHitSpec) {
                            maxHit = cap.maxHitSpec;
                        }
                    }
                }
            }
            return maxHit;
        }
        return baseMaxHit;
    }
}

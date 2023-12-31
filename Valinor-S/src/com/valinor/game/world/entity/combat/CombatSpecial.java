package com.valinor.game.world.entity.combat;

import com.valinor.game.content.duel.DuelRule;
import com.valinor.game.content.packet_actions.GlobalStrings;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.RestoreSpecialAttackTask;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.method.CombatMethod;
import com.valinor.game.world.entity.combat.method.impl.specials.magic.CursedNMS;
import com.valinor.game.world.entity.combat.method.impl.specials.magic.DawnbringerStaff;
import com.valinor.game.world.entity.combat.method.impl.specials.magic.EldritchNMS;
import com.valinor.game.world.entity.combat.method.impl.specials.magic.VolatileNMS;
import com.valinor.game.world.entity.combat.method.impl.specials.melee.*;
import com.valinor.game.world.entity.combat.method.impl.specials.range.*;
import com.valinor.game.world.entity.combat.weapon.WeaponType;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.timers.TimerKey;

import java.util.Arrays;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * Holds constants that hold data for all of the special attacks that can be
 * used.
 *
 * @author lare96
 */
public enum CombatSpecial {

    //Other
    PICKAXE(new int[]{ItemIdentifiers.DRAGON_PICKAXE, DRAGON_PICKAXE_12797, DRAGON_PICKAXE_OR_25376, INFERNAL_PICKAXE, CRYSTAL_PICKAXE, CRYSTAL_PICKAXE_23863}, 100, 1.0, 1.0, null, WeaponType.PICKAXE),

    //Range
    MAGIC_SHORTBOW(new int[]{ItemIdentifiers.MAGIC_SHORTBOW, ItemIdentifiers.MAGIC_SHORTBOW_I, MAGIC_SHORTBOW_TIER_1, MAGIC_SHORTBOW_TIER_2, MAGIC_SHORTBOW_TIER_3, MAGIC_SHORTBOW_TIER_4, MAGIC_SHORTBOW_TIER_5_1, MAGIC_SHORTBOW_TIER_5_2, MAGIC_SHORTBOW_TIER_5_3, MAGIC_SHORTBOW_TIER_5_4, MAGIC_SHORTBOW_TIER_5_5}, 55, 1.0, 1.2, new MagicShortbow(), WeaponType.BOW),
    DARK_BOW(new int[]{ItemIdentifiers.DARK_BOW, ItemIdentifiers.DARK_BOW_12765, ItemIdentifiers.DARK_BOW_12766, ItemIdentifiers.DARK_BOW_12767, ItemIdentifiers.DARK_BOW_12768, ItemIdentifiers.DARK_BOW_20408, DARK_BOW_TIER_5_1, DARK_BOW_TIER_5_2, DARK_BOW_TIER_5_3, DARK_BOW_TIER_5_4, DARK_BOW_TIER_5_5}, 55, 1.30, 1.24, new DarkBow(), WeaponType.BOW),
    ARMADYL_CROSSBOW(new int[]{ItemIdentifiers.ARMADYL_CROSSBOW}, 40, 1.30, 2.0, new ArmadylCrossbow(), WeaponType.CROSSBOW),
    ZARYTE_CROSSBOW(new int[]{ItemIdentifiers.ZARYTE_CROSSBOW}, 75, 1.0, 2.0, new ZaryteCrossbow(), WeaponType.CROSSBOW),
    DRAGON_CROSSBOW(new int[]{ItemIdentifiers.DRAGON_CROSSBOW}, 60, 1.30, 2.0, new DragonCrossbow(), WeaponType.CROSSBOW),
    BALLISTA(new int[]{ItemIdentifiers.LIGHT_BALLISTA, ItemIdentifiers.HEAVY_BALLISTA, ItemIdentifiers.HEAVY_BALLISTA_23630}, 65, 1.25, 1.25, new Ballista(), WeaponType.THROWN),
    DRAGON_THROWNAXE(new int[]{ItemIdentifiers.DRAGON_THROWNAXE, ItemIdentifiers.DRAGON_THROWNAXE_21207}, 25, 1.0, 1.25, new DragonThrownaxe(), WeaponType.THROWN),
    DRAGON_KNIFE(new int[]{ItemIdentifiers.DRAGON_KNIFE, ItemIdentifiers.DRAGON_KNIFEP, ItemIdentifiers.DRAGON_KNIFEP_22808, ItemIdentifiers.DRAGON_KNIFEP_22810}, 25, 1.0, 1.0, new DragonKnife(), WeaponType.THROWN),
    TOXIC_BLOWPIPE(new int[]{ItemIdentifiers.TOXIC_BLOWPIPE, MAGMA_BLOWPIPE}, 50, 1.50, 1.50, new ToxicBlowpipeSpecialAttack(), WeaponType.THROWN),
    MORRIGANS_THROWING_AXE(new int[]{ItemIdentifiers.MORRIGANS_THROWING_AXE}, 50, 1.20, 1.0, new MorrigansThrowingAxe(), WeaponType.THROWN),
    MORRIGANS_JAVALIN(new int[]{ItemIdentifiers.MORRIGANS_JAVELIN_23619, ItemIdentifiers.MORRIGANS_JAVELIN}, 50, 1.0, 1.0, new MorrigansJavelin(), WeaponType.THROWN),
    ELEMENTAL_BOW(new int[]{CustomItemIdentifiers.ELEMENTAL_BOW}, 50, 1.50, 3.00, new ElementalBow(), WeaponType.BOW),

    //Magic
    DAWNBRINGER(new int[]{ItemIdentifiers.DAWNBRINGER}, 35, 5.0, 1.0, new DawnbringerStaff(), WeaponType.THROWN),
    STAFF_OF_THE_DEAD(new int[]{ItemIdentifiers.STAFF_OF_THE_DEAD}, 100, 1.0, 1.0, null, WeaponType.MAGIC_STAFF),
    TOXIC_STAFF_OF_THE_DEAD(new int[]{ItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD, TOXIC_STAFF_OF_THE_DEAD_C}, 100, 1.0, 1.0, null, WeaponType.MAGIC_STAFF),
    STAFF_OF_LIGHT(new int[]{ItemIdentifiers.STAFF_OF_LIGHT, STAFF_OF_LIGHT_TIER_1, STAFF_OF_LIGHT_TIER_2, STAFF_OF_LIGHT_TIER_3, STAFF_OF_LIGHT_TIER_4, STAFF_OF_LIGHT_TIER_5_1, STAFF_OF_LIGHT_TIER_5_2, STAFF_OF_LIGHT_TIER_5_3, STAFF_OF_LIGHT_TIER_5_4, STAFF_OF_LIGHT_TIER_5_5}, 100, 1.0, 1.0, null, WeaponType.MAGIC_STAFF),
    VOLATILE_NIGHTMARE_STAFF(new int[]{ItemIdentifiers.VOLATILE_NIGHTMARE_STAFF}, 55, 1.0, 1.50, new VolatileNMS(), WeaponType.MAGIC_STAFF),
    ELDRITCH_NIGHTMARE_STAFF(new int[]{ItemIdentifiers.ELDRITCH_NIGHTMARE_STAFF}, 75, 1.0, 1.0, new EldritchNMS(), WeaponType.MAGIC_STAFF),
    CURSED_NIGHTMARE_STAFF(new int[]{CustomItemIdentifiers.CURSED_NIGHTMARE_STAFF}, 55, 1.0, 2.50, new CursedNMS(), WeaponType.MAGIC_STAFF),

    //Melee
    ANCIENT_WARRIOR_SWORD(new int[]{CustomItemIdentifiers.ANCIENT_WARRIOR_SWORD, ANCIENT_WARRIOR_SWORD_C}, 100, 1.75, 2.0, new AncientWarriorSword(), WeaponType.LONGSWORD),
    ANCIENT_WARRIOR_AXE(new int[]{CustomItemIdentifiers.ANCIENT_WARRIOR_AXE, ANCIENT_WARRIOR_AXE_C}, 50, 1.50, 3.0, new AncientWarriorAxe(), WeaponType.AXE),
    ANCIENT_WARRIOR_MAUL(new int[]{CustomItemIdentifiers.ANCIENT_WARRIOR_MAUL, ANCIENT_WARRIOR_MAUL_C}, 50, 1.25, 1.50, new AncientWarriorMaul(), WeaponType.HAMMER),

    BLADE_OF_SAELDOR_C(new int[]{ItemIdentifiers.BLADE_OF_SAELDOR_C, BLADE_OF_SAELDOR_C_25870, BLADE_OF_SAELDOR_C_25872, BLADE_OF_SAELDOR_C_25874, BLADE_OF_SAELDOR_C_25876, BLADE_OF_SAELDOR_C_25878, BLADE_OF_SAELDOR_C_25880, BLADE_OF_SAELDOR_C_25882}, 50, 1.20, 2.00, new BladeOfSaeldorC(), WeaponType.LONGSWORD),

    DRAGON_BATTLEAXE(new int[]{ItemIdentifiers.DRAGON_BATTLEAXE}, 100, 1.0, 1.0, null, WeaponType.AXE),
    BARRELCHEST_ANCHOR(new int[]{ItemIdentifiers.BARRELCHEST_ANCHOR}, 50, 1.0, 1.10, new BarrelchestAnchor(), WeaponType.MACE),
    GRANITE_MAUL(new int[]{ItemIdentifiers.GRANITE_MAUL, ItemIdentifiers.GRANITE_MAUL_12848, ItemIdentifiers.GRANITE_MAUL_24225, CustomItemIdentifiers.GRANITE_MAUL_TIER_1, CustomItemIdentifiers.GRANITE_MAUL_TIER_2, CustomItemIdentifiers.GRANITE_MAUL_TIER_3, CustomItemIdentifiers.GRANITE_MAUL_TIER_4, CustomItemIdentifiers.GRANITE_MAUL_TIER_5_1, CustomItemIdentifiers.GRANITE_MAUL_TIER_5_2, CustomItemIdentifiers.GRANITE_MAUL_TIER_5_3, CustomItemIdentifiers.GRANITE_MAUL_TIER_5_4, CustomItemIdentifiers.GRANITE_MAUL_TIER_5_5}, 50, 1.0, 1.10, new GraniteMaul(), WeaponType.HAMMER),
    DRAGON_LONGSWORD(new int[]{ItemIdentifiers.DRAGON_LONGSWORD, DRAGON_LONGSWORD_TIER_1, DRAGON_LONGSWORD_TIER_2, DRAGON_LONGSWORD_TIER_3, DRAGON_LONGSWORD_TIER_4, DRAGON_LONGSWORD_TIER_5_1, DRAGON_LONGSWORD_TIER_5_2, DRAGON_LONGSWORD_TIER_5_3, DRAGON_LONGSWORD_TIER_5_4, DRAGON_LONGSWORD_TIER_5_5}, 25, 1.25, 1.25, new DragonLongsword(), WeaponType.LONGSWORD),
    DRAGON_DAGGER(new int[]{ItemIdentifiers.DRAGON_DAGGER, ItemIdentifiers.DRAGON_DAGGERP, ItemIdentifiers.DRAGON_DAGGERP_5680, ItemIdentifiers.DRAGON_DAGGERP_5698, ItemIdentifiers.DRAGON_DAGGER_20407, DRAGON_DAGGER_TIER_1, DRAGON_DAGGER_TIER_2, DRAGON_DAGGER_TIER_3, DRAGON_DAGGER_TIER_4, DRAGON_DAGGER_TIER_5_1, DRAGON_DAGGER_TIER_5_2, DRAGON_DAGGER_TIER_5_3, DRAGON_DAGGER_TIER_5_4, DRAGON_DAGGER_TIER_5_5}, 25, 1.15, 1.15, new DragonDagger(), WeaponType.DAGGER),
    ABYSSAL_DAGGER(new int[]{ItemIdentifiers.ABYSSAL_DAGGER, ItemIdentifiers.ABYSSAL_DAGGER_P, ItemIdentifiers.ABYSSAL_DAGGER_P_13269, ItemIdentifiers.ABYSSAL_DAGGER_P_13271}, 50, 0.85, 1.25, new AbyssalDagger(), WeaponType.DAGGER),
    ANCIENT_GODSWORD(new int[]{ItemIdentifiers.ANCIENT_GODSWORD}, 50, 1.10, 1.35, new AncientGodsword(), WeaponType.TWOHANDED),
    ARMADYL_GODSWORD(new int[]{ItemIdentifiers.ARMADYL_GODSWORD, ItemIdentifiers.ARMADYL_GODSWORD_OR, 20593, BEGINNER_AGS}, 50, 1.35, 1.35, new ArmadylGodsword(), WeaponType.TWOHANDED),
    BANDOS_GODSWORD(new int[]{ItemIdentifiers.BANDOS_GODSWORD, ItemIdentifiers.BANDOS_GODSWORD_OR}, 50, 1.20, 1.0, new BandosGodsword(), WeaponType.TWOHANDED),
    SARADOMIN_GODSWORD(new int[]{ItemIdentifiers.SARADOMIN_GODSWORD, ItemIdentifiers.SARADOMIN_GODSWORD_OR}, 50, 1.10, 1.1, new SaradominGodsword(), WeaponType.TWOHANDED),
    ZAMORAK_GODSWORD(new int[]{ItemIdentifiers.ZAMORAK_GODSWORD, ItemIdentifiers.ZAMORAK_GODSWORD_OR}, 50, 1.10, 1.1, new ZamorakGodsword(), WeaponType.TWOHANDED),
    DRAGON_SCIMITAR(new int[]{ItemIdentifiers.DRAGON_SCIMITAR, ItemIdentifiers.DRAGON_SCIMITAR_OR, ItemIdentifiers.DRAGON_SCIMITAR_20406, DRAGON_SCIMITAR_TIER_1, DRAGON_SCIMITAR_TIER_2, DRAGON_SCIMITAR_TIER_3, DRAGON_SCIMITAR_TIER_4, DRAGON_SCIMITAR_TIER_5_1, DRAGON_SCIMITAR_TIER_5_2, DRAGON_SCIMITAR_TIER_5_3, DRAGON_SCIMITAR_TIER_5_4, DRAGON_SCIMITAR_TIER_5_5}, 55, 1.0, 1.25, new DragonScimitar(), WeaponType.LONGSWORD),
    KORASI_SWORD(new int[]{CustomItemIdentifiers.KORASI_SWORD}, 65, 1.50, 8.0, new KorasiSword(), WeaponType.LONGSWORD),

    DRAGON_SPEAR(new int[]{ItemIdentifiers.DRAGON_SPEAR, ItemIdentifiers.DRAGON_SPEARP, ItemIdentifiers.DRAGON_SPEARP_5716, ItemIdentifiers.DRAGON_SPEARP_5730}, 25, 1.0, 1.0, new DragonSpear(), WeaponType.SPEAR),
    ZAMORAKIAN_SPEAR(new int[]{ItemIdentifiers.ZAMORAKIAN_SPEAR}, 25, 1.0, 1.0, new ZamorakianSpear(), WeaponType.SPEAR),
    ABYSSAL_TENTACLE(new int[]{ItemIdentifiers.ABYSSAL_TENTACLE}, 50, 1.0, 1.0, new AbyssalTentacle(), WeaponType.WHIP),
    ABYSSAL_WHIP(new int[]{ItemIdentifiers.ABYSSAL_WHIP, ItemIdentifiers.VOLCANIC_ABYSSAL_WHIP, ItemIdentifiers.FROZEN_ABYSSAL_WHIP, ABYSSAL_WHIP_TIER_1, ABYSSAL_WHIP_TIER_2, ABYSSAL_WHIP_TIER_3, ABYSSAL_WHIP_TIER_4, ABYSSAL_WHIP_TIER_5_1, ABYSSAL_WHIP_TIER_5_2, ABYSSAL_WHIP_TIER_5_3, ABYSSAL_WHIP_TIER_5_4, ABYSSAL_WHIP_TIER_5_5}, 50, 1.0, 1.25, new AbyssalWhip(), WeaponType.WHIP),
    BLESSED_SARADOMIN_SWORD(new int[]{ItemIdentifiers.SARADOMINS_BLESSED_SWORD, ItemIdentifiers.SARAS_BLESSED_SWORD_FULL}, 65, 1.25, 1.0, new SaradominBlessedSword(), WeaponType.TWOHANDED),
    SARADOMIN_SWORD(new int[]{ItemIdentifiers.SARADOMIN_SWORD}, 100, 1.0, 1.0, new SaradominSword(), WeaponType.TWOHANDED),
    ANCIENT_MACE(new int[]{ItemIdentifiers.ANCIENT_MACE}, 100, 1.0, 1.0, new AncientMace(), WeaponType.MACE),
    DRAGON_2H_SWORD(new int[]{ItemIdentifiers.DRAGON_2H_SWORD}, 60, 1.0, 1.0, new Dragon2HSword(), WeaponType.TWOHANDED),
    BARRELSCHEST_ANCHOR(new int[]{ItemIdentifiers.BARRELCHEST_ANCHOR}, 50, 1.10, 1.10, new BarrelchestAnchor(), WeaponType.HAMMER),
    DRAGON_MACE(new int[]{ItemIdentifiers.DRAGON_MACE, DRAGON_MACE_TIER_1, DRAGON_MACE_TIER_2, DRAGON_MACE_TIER_3, DRAGON_MACE_TIER_4, DRAGON_MACE_TIER_5_1, DRAGON_MACE_TIER_5_2, DRAGON_MACE_TIER_5_3, DRAGON_MACE_TIER_5_4, DRAGON_MACE_TIER_5_5}, 25, 1.50, 1.25, new DragonMace(), WeaponType.MACE),
    INQUISITORS_MACE(new int[]{ItemIdentifiers.INQUISITORS_MACE}, 25, 1.25, 1.50, new InquisitorsMace(), WeaponType.MACE),
    SHADOW_MACE(new int[]{CustomItemIdentifiers.SHADOW_MACE}, 35, 1.35, 1.75, new ShadowMace(), WeaponType.MACE),
    DRAGON_WARHAMMER(new int[]{ItemIdentifiers.DRAGON_WARHAMMER, ItemIdentifiers.DRAGON_WARHAMMER_20785}, 50, 1.50, 1.50, new DragonWarhammer(), WeaponType.HAMMER),
    STATIUS_WARHAMMER(new int[]{ItemIdentifiers.STATIUSS_WARHAMMER, ItemIdentifiers.STATIUSS_WARHAMMER_23620}, 35, 1.25, 1.50, new StatiusWarhammer(), WeaponType.HAMMER),
    ANCIENT_STATIUS_WARHAMMER(new int[]{CustomItemIdentifiers.ANCIENT_STATIUS_WARHAMMER}, 35, 1.30, 1.50, new AncientStatiusWarhammer(), WeaponType.HAMMER),
    DRAGON_CLAWS(new int[]{ItemIdentifiers.DRAGON_CLAWS, BEGINNER_DRAGON_CLAWS}, 50, 1.0, 3.00, new DragonClaws(), WeaponType.CLAWS),
    DRAGON_CLAWS_OR(new int[]{CustomItemIdentifiers.DRAGON_CLAWS_OR}, 50, 1.0, 3.50, new DragonClaws(), WeaponType.CLAWS),
    DRAGON_HALBERD(new int[]{ItemIdentifiers.DRAGON_HALBERD}, 30, 1.10, 1.35, new DragonHalberd(), WeaponType.HALBERD),
    CRYSTAL_HALBERD(new int[]{13080, 13081, 13082, 13083, 13084, 13085, 13086, 13087, 13088, 13089, 13090, 13091, 13092, 13093, 13094, 13095, 13096, 13097, 13098, 13099, 13100, 13101}, 30, 1.10, 1.35, new CrystalHalberd(), WeaponType.HALBERD),
    ABYSSAL_BLUDGEON(new int[]{ItemIdentifiers.ABYSSAL_BLUDGEON}, 50, 1.0, 1.0, new AbyssalBludgeon(), WeaponType.HAMMER),
    DRAGON_SWORD(new int[]{ItemIdentifiers.DRAGON_SWORD}, 40, 1.25, 1.25, new DragonSword(), WeaponType.SWORD),
    DINHS_BULWARK(new int[]{ItemIdentifiers.DINHS_BULWARK}, 50, 1.0, 2.0, new DinhsBulwark(), WeaponType.DINHS_BULWARK),
    GRANITE_HAMMER(new int[]{ItemIdentifiers.GRANITE_HAMMER}, 60, 1.0, 1.50, new GraniteHammer(), WeaponType.HAMMER),
    VESTA_LONGSWORD(new int[]{ItemIdentifiers.VESTAS_LONGSWORD, ItemIdentifiers.VESTAS_LONGSWORD_23615}, 25, 1.20, 1.50, new VestaLongsword(), WeaponType.LONGSWORD),
    ANCIENT_VESTA_LONGSWORD(new int[]{ItemIdentifiers.VESTAS_LONGSWORD, ANCIENT_VESTAS_LONGSWORD}, 25, 1.25, 1.50, new AncientVestaLongsword(), WeaponType.LONGSWORD),
    VESTA_SPEAR(new int[]{ItemIdentifiers.VESTAS_SPEAR}, 50, 1.20, 1.50, new VestaSpear(), WeaponType.SPEAR);

    /**
     * The weapon ID's that perform this special when activated.
     */
    private final int[] identifiers;

    /**
     * The amount of special energy this attack will drain.
     */
    private int drainAmount;

    /**
     * The strength bonus when performing this special attack.
     */
    private final double baseSpecialMultiplier;

    /**
     * The accuracy bonus when performing this special attack.
     */
    private final double accuracyMultiplier;

    /**
     * The combat type used when performing this special attack.
     */
    private final CombatMethod combatMethod;

    /**
     * The weapon interface used by the identifiers.
     */
    private final WeaponType weaponType;

    /**
     * Create a new {@link CombatSpecial}.
     *
     * @param identifiers        the weapon ID's that perform this special when activated.
     * @param drainAmount        the amount of special energy this attack will drain.
     * @param baseSpecialMultiplier  the strength bonus when performing this special attack.
     * @param accuracyMultiplier the accuracy bonus when performing this special attack.
     * @param combatMethod       the combat type used when performing this special attack.
     * @param weaponType         the weapon interface used by the identifiers.
     */
    CombatSpecial(int[] identifiers, int drainAmount, double baseSpecialMultiplier, double accuracyMultiplier, CombatMethod combatMethod, WeaponType weaponType) {
        this.identifiers = identifiers;
        this.drainAmount = drainAmount;
        this.baseSpecialMultiplier = baseSpecialMultiplier;
        this.accuracyMultiplier = accuracyMultiplier;
        this.combatMethod = combatMethod;
        this.weaponType = weaponType;
    }

    /**
     * Drains the special bar for the argued {@link Mob}.
     *
     * @param mob    the mob who's special bar will be drained.
     * @param amount the amount of energy to drain from the special bar.
     */
    public static void drain(Mob mob, int amount) {
        Mob target = mob.getCombat().getTarget();
        if (target != null) {
            CombatFactory.check_spec_and_tele(mob.getAsPlayer(), target);
        }

        if (mob instanceof Player) {
            Player player = (Player) mob;

            if (player.getEquipment().hasAt(EquipSlot.RING, CustomItemIdentifiers.RING_OF_VIGOUR) || player.pet() != null && player.hasPetOut("Baby Squirt")) {
                amount -= 10;
            }
        }

        mob.desecreaseSpecialAttack(amount);
        mob.setSpecialActivated(false);

        if (!mob.isRecoveringSpecialAttack()) {
            TaskManager.submit(new RestoreSpecialAttackTask(mob));
        }

        if (mob.isPlayer()) {
            Player player = mob.getAsPlayer();
            CombatSpecial.updateBar(player);
        }
    }

    /**
     * Updates the special bar with the amount of special energy the argued
     * {@link Player} has.
     *
     * @param player the player who's special bar will be updated.
     */
    public static void updateBar(Player player) {

        player.getPacketSender().updateSpecialAttackOrb();

        WeaponType weaponType = player.getCombat().getWeaponType();
        if(weaponType == null) {
            return;
        }

        if (weaponType.getSpecialBar() == -1 || weaponType.getSpecialMeter() == -1) {
            return;
        }
        int specialCheck = 10;
        int specialBar = weaponType.getSpecialMeter();
        int specialAmount = player.getSpecialAttackPercentage() / 10;

        // send one packet, not 10. @shadowrs
        player.getPacketSender().sendString(99900, "specbar:" + specialCheck + ":" + specialBar + ":" + specialAmount);

        GlobalStrings.send(weaponType.getSpecialMeter(),
            player.isSpecialActivated() ? ("<col=ffff00> Special Attack: " + player.getSpecialAttackPercentage() + "%") : ("</col> Special Attack: " + player.getSpecialAttackPercentage() + "%"),
            player);
    }

    /**
     * Assigns special bars to the attack style interface if needed.
     *
     * @param player the player to assign the special bar for.
     */
    public static void assign(Player player) {
        //player.debugMessage("Enter CombatSpecial assign");
        if (player.getCombat().getWeaponType().getSpecialBar() == -1) {
            //player.debugMessage("Don't assign spec bar");
            player.setSpecialActivated(false);
            player.setCombatSpecial(null);
            CombatSpecial.updateBar(player);
            return;
        }

        WeaponType weaponType = player.getCombat().getWeaponType();
        //player.debugMessage("Current weapon interface: " + weaponInterface);
        for (CombatSpecial special : CombatSpecial.values()) {
            WeaponType specialAttackBar = special.getWeaponInterface();
            if (weaponType == specialAttackBar) {
                //player.debugMessage("looping combat specials...");
                Item weapon = player.getEquipment().get(EquipSlot.WEAPON);
                if (Arrays.stream(special.getIdentifiers()).anyMatch(id -> weapon != null && weapon.getId() == id)) {
                    //player.debugMessage("set spec bar");
                    player.getPacketSender().sendInterfaceDisplayState(player.getCombat().getWeaponType().getSpecialBar(), false);
                    player.setCombatSpecial(special);
                    return;
                }
            }
        }

        player.getPacketSender().sendInterfaceDisplayState(player.getCombat().getWeaponType().getSpecialBar(), true);
        player.setCombatSpecial(null);
        player.setSpecialActivated(false);
    }

    public static boolean specialAttackButton(Player player, int button) {
        if (player.dead() || player.locked()) {
            return false;
        }
        if (player.getDueling().inDuel() && player.getDueling().getRules()[DuelRule.NO_SPECIAL_ATTACKS.ordinal()]) {
            player.message("Special attacks have been disabled for this duel.");
            return false;
        }

        /*Dragon axe
        special attack anim: 2876 gfx: 479

        Crystal axe
        special attack anim: 2876 gfx: 1689

        Dragon pickaxe
        special attack anim: 7138

        Crystal pickaxe
        special attack anim: 8329
        */

        if (button == 12322) {
            Item weapon = player.getEquipment().get(EquipSlot.WEAPON);

            if (weapon != null) {
                switch (weapon.getId()) {
                    case ItemIdentifiers.DRAGON_PICKAXE, DRAGON_PICKAXE_12797, DRAGON_PICKAXE_OR_25376, INFERNAL_PICKAXE, CRYSTAL_PICKAXE, CRYSTAL_PICKAXE_23863 -> {
                        doDragonPickaxe(player, weapon.getId());
                        return true;
                    }
                    case ItemIdentifiers.STAFF_OF_LIGHT -> {
                        doStaffOfTheDead(player, true);
                        return true;
                    }
                    case ItemIdentifiers.STAFF_OF_THE_DEAD, TOXIC_STAFF_UNCHARGED, ItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD, TOXIC_STAFF_OF_THE_DEAD_C -> {
                        doStaffOfTheDead(player, false);
                        return true;
                    }
                    case ItemIdentifiers.DRAGON_BATTLEAXE -> {
                        doDragonBattleaxe(player);
                        return true;
                    }
                    default -> {
                        CombatSpecial.activate(player);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void doStaffOfTheDead(Player player, boolean staffOfLight) {
        //Toxic & Regular Staff of the Dead
        if (CombatFactory.takeSpecialEnergy(player, 100)) {
            player.animate(staffOfLight ? 7967 : 7083);
            player.graphic(staffOfLight ? 1516 : 1228, 300, 0);
            player.getTimers().addOrSet(TimerKey.SOTD_DAMAGE_REDUCTION, 60);
            player.message("<col=3d5d2b>Spirits of deceased evildoers offer you their protection.");
        }
    }

    private static void doDragonPickaxe(Player player, int weapon) {
        if (CombatFactory.takeSpecialEnergy(player, 100)) {
            player.forceChat("Smashing!");
            player.skills().alterSkill(Skills.MINING, 3);
            player.animate((weapon == 11920) ? 7138 : (weapon == CRYSTAL_PICKAXE_23863) ? 8329 : 3410);
        }
    }

    private static void doDragonBattleaxe(Player player) {
        if (CombatFactory.takeSpecialEnergy(player, 100)) {
            player.forceChat("Raarrrrrgggggghhhhhhhh!");
            var drained = 0;
            int[] array = new int[]{0, 1, 4, 6};
            for (int stat : array) {
                drained += (int) Math.ceil(player.skills().level(stat) / 10.0);
                player.skills().setLevel(stat, (int) (player.skills().level(stat) - Math.floor(player.skills().level(stat) / 10.0)));
            }
            var boost = player.skills().level(Skills.STRENGTH) + 10 + (drained / 4); // you can only ever get to 119 lol
            player.skills().setLevel(Skills.STRENGTH, boost >= 119 ? 120 : boost); // fuck this formula wiki must be wrong
            player.graphic(246);
            player.animate(1056);
        }
    }

    /**
     * Activates a player's special attack.
     */
    public static void activate(Player player) {

        //Make sure the player has a valid special attack
        if (player.getCombatSpecial() == null) {
            return;
        }

        //Duel, disabled special attacks?
        if (player.getDueling().inDuel() && player.getDueling().getRules()[DuelRule.NO_SPECIAL_ATTACKS.ordinal()]) {
            DialogueManager.sendStatement(player, "Special attacks have been disabled in this duel!");
            return;
        }

        //Get the special attack..
        final CombatSpecial spec = player.getCombatSpecial();

        //Check if player has already activated special attack,
        //If that's the case - turn if off.
        if (player.isSpecialActivated()) {
            player.setSpecialActivated(false);
            CombatSpecial.updateBar(player);

            // the button deactivates, but we queue up another gmaul request
            if (spec == CombatSpecial.GRANITE_MAUL) {
                queueGraniteMaulSpecial(player);
            }
        } else {
            //Set special attack activated
            player.setSpecialActivated(true);

            //Update special bar
            CombatSpecial.updateBar(player);

            //Handle instant special attacks here.
            //Example: Granite Maul, Dragon battleaxe...
            if (spec == CombatSpecial.GRANITE_MAUL) {

                double vigour = 0;
                if(player.getEquipment().hasAt(EquipSlot.RING, RING_OF_VIGOUR)) {
                    vigour += player.getSpecialAttackPercentage() * 0.1;
                }

                int specPercentage = (int) (player.getSpecialAttackPercentage() + vigour);

                //Make sure the player has enough special attack
                if (specPercentage < player.getCombatSpecial().getDrainAmount()) {
                    player.message("You do not have enough special attack energy left!");
                    player.setSpecialActivated(false);
                    CombatSpecial.updateBar(player);
                    return;
                }

                // granite maul triggers when traditional combat code is executed
                // in player.process, and NOT here in packet handler code.
                queueGraniteMaulSpecial(player);
            } else if (spec == CombatSpecial.DRAGON_THROWNAXE) {
                double vigour = 0;
                if(player.getEquipment().hasAt(EquipSlot.RING, RING_OF_VIGOUR)) {
                    vigour += player.getSpecialAttackPercentage() * 0.1;
                }

                int specPercentage = (int) (player.getSpecialAttackPercentage() + vigour);

                //Make sure the player has enough special attack
                if (specPercentage < player.getCombatSpecial().getDrainAmount()) {
                    player.message("You do not have enough special attack energy left!");
                    player.setSpecialActivated(false);
                    CombatSpecial.updateBar(player);
                    return;
                }

                //Dragon thrownaxes disregard all combat delays
                player.getTimers().cancel(TimerKey.COMBAT_ATTACK);
            }
        }
    }

    /**
     * runite
     */
    private static void queueGraniteMaulSpecial(Player player) {
        var graniteMaulSpecials = player.<Integer>getAttribOr(AttributeKey.GRANITE_MAUL_SPECIALS, 0);
        player.putAttrib(AttributeKey.GRANITE_MAUL_SPECIALS, graniteMaulSpecials + 1);
        player.putAttrib(AttributeKey.GRANITE_MAUL_TIMEOUT_TICKS, 5);
        if (player.getCombat().lastTarget != null) {
            player.setEntityInteraction(player.getCombat().lastTarget);
            player.getCombat().setTarget(player.getCombat().lastTarget);
        }
    }

    /**
     * Gets the weapon ID's that perform this special when activated.
     *
     * @return the weapon ID's that perform this special when activated.
     */
    public int[] getIdentifiers() {
        return identifiers;
    }

    /**
     * Gets the amount of special energy this attack will drain.
     *
     * @return the amount of special energy this attack will drain.
     */
    public int getDrainAmount() {
        return drainAmount;
    }

    public void setDrainAmount(int drainAmount) {
        this.drainAmount = drainAmount;
    }

    /**
     * Gets the strength bonus when performing this special attack.
     *
     * @return the strength bonus when performing this special attack.
     */
    public double getspecialMultiplier() {
        return baseSpecialMultiplier;
    }

    /**
     * Gets the accuracy bonus when performing this special attack.
     *
     * @return the accuracy bonus when performing this special attack.
     */
    public double getAccuracyMultiplier() {
        return accuracyMultiplier;
    }

    /**
     * Gets the combat type used when performing this special attack.
     *
     * @return the combat type used when performing this special attack.
     */
    public CombatMethod getCombatMethod() {
        return combatMethod;
    }

    /**
     * Gets the weapon interface used by the identifiers.
     *
     * @return the weapon interface used by the identifiers.
     */
    public WeaponType getWeaponInterface() {
        return weaponType;
    }
}

package com.valinor.game.world.entity.combat.magic;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.valinor.GameServer;
import com.valinor.game.content.EffectTimer;
import com.valinor.game.content.duel.DuelRule;
import com.valinor.game.content.mechanics.Poison;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunter;
import com.valinor.game.world.entity.combat.magic.lunar.Humidify;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.timers.TimerKey;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | March, 17, 2021, 14:03
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class MagicClickSpells {

    public enum MagicSpells {

        TELEPORT_TO_TARGET_LUNAR(new Spell() {

            @Override
            public String name() {
                return "Teleport to target";
            }

            @Override
            public int spellId() {
                return 30234;
            }

            @Override
            public int levelRequired() {
                return 85;
            }

            @Override
            public int baseExperience() {
                return 45;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                boolean spellSack = player.inventory().contains(BLIGHTED_TELEPORT_SPELL_SACK);
                if (spellSack) {
                    return List.of(Item.of(BLIGHTED_TELEPORT_SPELL_SACK, 1));
                }
                return List.of(new Item[]{new Item(LAW_RUNE, 1), new Item(DEATH_RUNE, 3), new Item(CHAOS_RUNE, 3)});
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                // Send message and effect timer to client
                if (player != null) {
                    if (!player.locked()) {
                        Optional<Player> target = BountyHunter.getTargetfor(player);
                        if (target.isPresent()) {
                            boolean targetInMulti = target.get().<Integer>getAttribOr(AttributeKey.MULTIWAY_AREA, -1) == 1;
                            if (targetInMulti) {
                                player.confirmDialogue(new String[]{"Are you sure you wish to teleport?", "Your target is inside a multiway area."}, "", "Proceed.", "Nevermind.", () -> {
                                    Teleports.teleportToTarget(player, target.get().tile());
                                    itemsRequired(player).forEach(player.inventory()::remove);
                                    player.getClickDelay().reset();
                                });
                                return;
                            }
                            itemsRequired(player).forEach(player.inventory()::remove);
                            Teleports.teleportToTarget(player, target.get().tile());
                            player.getClickDelay().reset();
                        }
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.<Boolean>getAttribOr(AttributeKey.BOUNTY_HUNTER_TARGET_TELEPORT_UNLOCKED, false)) {
                    player.message("You have not unlocked this spell yet.");
                    return false;
                }

                if (!WildernessArea.inWilderness(player.tile())) {
                    player.message("You must be in the Wilderness to use this spell.");
                    return false;
                }

                if (CombatFactory.inCombat(player)) {
                    player.message("You can't cast this spell during combat.");
                    return false;
                }

                Optional<Player> targetFor = BountyHunter.getTargetfor(player);
                if (targetFor.isPresent()) {
                    if(targetFor.get().tile().insideRiskArea()) {
                        player.message("Your target is currently in the riskzone area, you cannot teleport there.");
                        return false;
                    }

                    if (!WildernessArea.inWilderness(targetFor.get().tile())) {
                        player.message("Your target is currently not in the Wilderness.");
                        return false;
                    }

                    //Prevent players from teleporting to targets that are doing agility obstacles.
                    if (targetFor.get().getMovementQueue().forcedStep()) {
                        player.message("You can't teleport to your target at this time.");
                        return false;
                    }

                    if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        return false;
                    }

                    if (!player.getClickDelay().elapsed(30000)) {
                        player.message("You have just teleported to your target. There is a 30 second cooldown.");
                        return false;
                    }
                } else {
                    player.message("You currently have no target to teleport to!");
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        CHARGE(new Spell() {

            @Override
            public String name() {
                return "Charge";
            }

            @Override
            public int spellId() {
                return 1193;
            }

            @Override
            public int levelRequired() {
                return 80;
            }

            @Override
            public int baseExperience() {
                return 180;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                Item shield = player.getEquipment().get(EquipSlot.SHIELD);
                if (shield != null && shield.getId() == TOME_OF_FIRE) {
                    return List.of(new Item[]{new Item(AIR_RUNE, 3), new Item(BLOOD_RUNE, 3)});
                }
                return List.of(new Item[]{new Item(AIR_RUNE, 3), new Item(FIRE_RUNE, 3), new Item(BLOOD_RUNE, 3)});
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    player.getTimers().register(TimerKey.CHARGE_SPELL, 200);
                    player.message("You feel charged with magic power.");
                    player.animate(811);
                    player.skills().addXp(Skills.MAGIC, this.baseExperience());
                    player.graphic(111, 130, 3);
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (player.getTimers().has(TimerKey.CHARGE_SPELL)) {
                    player.message("You can't recast that yet, your current Charge is too strong.");
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        BONES_TO_BANANAS(new Spell() {

            @Override
            public String name() {
                return "Bones to bananas";
            }

            @Override
            public int spellId() {
                return 1159;
            }

            @Override
            public int levelRequired() {
                return 15;
            }

            @Override
            public int baseExperience() {
                return 25;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(NATURE_RUNE),
                    Item.of(WATER_RUNE, 2),
                    Item.of(EARTH_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    int index = 0;
                    for (Item invItem : player.inventory().getValidItems()) {
                        if (invItem.getId() == BONES) {
                            player.inventory().remove(BONES, 1);
                            player.inventory().add(BANANA, 1);
                            index++;
                        }
                    }
                    player.graphic(141, 100, 0);
                    player.animate(722);
                    player.skills().addXp(Skills.MAGIC, this.baseExperience() * index);
                    player.getClickDelay().reset();
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(500)) {
                    return false;
                }
                if (!player.inventory().contains(BONES)) {
                    player.message("You do not have any bones in your inventory.");
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        ENCHANT_SAPPHIRE(new Spell() {

            @Override
            public String name() {
                return "Lvl-1 Enchant";
            }

            @Override
            public int spellId() {
                return 1155;
            }

            @Override
            public int levelRequired() {
                return 7;
            }

            @Override
            public int baseExperience() {
                return 17;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(WATER_RUNE, 1),
                    Item.of(COSMIC_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {

            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(1800)) {
                    return false;
                }
                return super.canCast(player, target, delete);
            }

        }),

        ENCHANT_EMERALD(new Spell() {

            @Override
            public String name() {
                return "Lvl-2 Enchant";
            }

            @Override
            public int spellId() {
                return 1165;
            }

            @Override
            public int levelRequired() {
                return 27;
            }

            @Override
            public int baseExperience() {
                return 17;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(AIR_RUNE, 3),
                    Item.of(COSMIC_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {

            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(1800)) {
                    return false;
                }
                return super.canCast(player, target, delete);
            }

        }),

        ENCHANT_RUBY_TOPAZ(new Spell() {

            @Override
            public String name() {
                return "Lvl-3 Enchant";
            }

            @Override
            public int spellId() {
                return 1176;
            }

            @Override
            public int levelRequired() {
                return 49;
            }

            @Override
            public int baseExperience() {
                return 17;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(FIRE_RUNE, 5),
                    Item.of(COSMIC_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {

            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(1800)) {
                    return false;
                }
                return super.canCast(player, target, delete);
            }

        }),

        ENCHANT_DIAMOND(new Spell() {

            @Override
            public String name() {
                return "Lvl-4 Enchant";
            }

            @Override
            public int spellId() {
                return 1180;
            }

            @Override
            public int levelRequired() {
                return 57;
            }

            @Override
            public int baseExperience() {
                return 17;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(EARTH_RUNE, 10),
                    Item.of(COSMIC_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {

            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(1800)) {
                    return false;
                }
                return super.canCast(player, target, delete);
            }

        }),

        ENCHANT_DRAGONSTONE(new Spell() {

            @Override
            public String name() {
                return "Lvl-5 Enchant";
            }

            @Override
            public int spellId() {
                return 1187;
            }

            @Override
            public int levelRequired() {
                return 68;
            }

            @Override
            public int baseExperience() {
                return 17;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(WATER_RUNE, 15),
                    Item.of(EARTH_RUNE, 15),
                    Item.of(COSMIC_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {

            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(1800)) {
                    return false;
                }
                return super.canCast(player, target, delete);
            }

        }),

        ENCHANT_ONYX(new Spell() {

            @Override
            public String name() {
                return "Lvl-6 Enchant";
            }

            @Override
            public int spellId() {
                return 6003;
            }

            @Override
            public int levelRequired() {
                return 86;
            }

            @Override
            public int baseExperience() {
                return 17;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(FIRE_RUNE, 20),
                    Item.of(EARTH_RUNE, 20),
                    Item.of(COSMIC_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {

            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(1800)) {
                    return false;
                }
                return super.canCast(player, target, delete);
            }

        }),

        ENCHANT_ZENYTE(new Spell() {

            @Override
            public String name() {
                return "Lvl-7 Enchant";
            }

            @Override
            public int spellId() {
                return 22674;
            }

            @Override
            public int levelRequired() {
                return 93;
            }

            @Override
            public int baseExperience() {
                return 17;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(BLOOD_RUNE, 20),
                    Item.of(SOUL_RUNE, 20),
                    Item.of(COSMIC_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {

            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(1800)) {
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        LOW_ALCHEMY(new Spell() {

            @Override
            public String name() {
                return "Low alchemy";
            }

            @Override
            public int spellId() {
                return 1162;
            }

            @Override
            public int levelRequired() {
                return 21;
            }

            @Override
            public int baseExperience() {
                return 31;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                Item shield = player.getEquipment().get(EquipSlot.SHIELD);
                if (shield != null && shield.getId() == TOME_OF_FIRE) {
                    return List.of(new Item[]{new Item(NATURE_RUNE, 1)});
                }
                return List.of(new Item[]{new Item(FIRE_RUNE, 3), new Item(NATURE_RUNE, 1)});
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    player.getCombat().reset();
                    player.action.clearNonWalkableActions();
                    player.animate(713);
                    player.graphic(113, 100, 15);
                    player.skills().addXp(Skills.MAGIC, this.baseExperience());
                    player.getClickDelay().reset();
                    player.getPacketSender().sendTab(6);
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(1800)) {
                    return false;
                }
                return super.canCast(player, target, delete);
            }

        }),

        TELEKINETIC_GRAB(new Spell() {

            @Override
            public String name() {
                return "Telekinetic grab";
            }

            @Override
            public int spellId() {
                return 1168;
            }

            @Override
            public int levelRequired() {
                return 33;
            }

            @Override
            public int baseExperience() {
                return 43;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(LAW_RUNE),
                    Item.of(AIR_RUNE)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
            }

        }),

        SUPERHEAT_ITEM(new Spell() {

            @Override
            public String name() {
                return "Superheat item";
            }

            @Override
            public int spellId() {
                return 1173;
            }

            @Override
            public int levelRequired() {
                return 43;
            }

            @Override
            public int baseExperience() {
                return 53;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                Item shield = player.getEquipment().get(EquipSlot.SHIELD);
                if (shield != null && player.getEquipment().hasAt(EquipSlot.SHIELD, TOME_OF_FIRE)) {
                    return List.of(new Item[]{new Item(NATURE_RUNE, 1)});
                }
                boolean hasBryophytaStaff = player.getEquipment().hasAt(EquipSlot.WEAPON, BRYOPHYTAS_STAFF);
                if (hasBryophytaStaff) {
                    return List.of(Item.of(FIRE_RUNE, 4));
                }
                return List.of(new Item[]{new Item(FIRE_RUNE, 4), new Item(NATURE_RUNE, 1)});
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
            }

        }),

        HIGH_ALCHEMY(new Spell() {

            @Override
            public String name() {
                return "High alchemy";
            }

            @Override
            public int spellId() {
                return 1178;
            }

            @Override
            public int levelRequired() {
                return 55;
            }

            @Override
            public int baseExperience() {
                return 65;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                Item shield = player.getEquipment().get(EquipSlot.SHIELD);
                if (shield != null && shield.getId() == TOME_OF_FIRE) {
                    return List.of(new Item[]{new Item(NATURE_RUNE, 1)});
                }
                boolean hasBryophytaStaff = player.getEquipment().hasAt(EquipSlot.WEAPON, BRYOPHYTAS_STAFF);
                if (hasBryophytaStaff) {
                    return List.of(Item.of(FIRE_RUNE, 5));
                }
                return List.of(new Item[]{new Item(FIRE_RUNE, 5), new Item(NATURE_RUNE, 1)});
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    player.getCombat().reset();
                    player.action.clearNonWalkableActions();
                    player.animate(713);
                    player.graphic(113, 100, 15);
                    player.skills().addXp(Skills.MAGIC, this.baseExperience());
                    player.getClickDelay().reset();
                    player.getPacketSender().sendTab(6);
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(2400)) {
                    return false;
                }
                return super.canCast(player, target, delete);
            }

        }),

        BONES_TO_PEACHES(new Spell() {

            @Override
            public String name() {
                return "Bones to peaches";
            }

            @Override
            public int spellId() {
                return 15877;
            }

            @Override
            public int levelRequired() {
                return 60;
            }

            @Override
            public int baseExperience() {
                return 65;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(NATURE_RUNE, 2),
                    Item.of(WATER_RUNE, 4),
                    Item.of(EARTH_RUNE, 4)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    int index = 0;
                    for (Item invItem : player.inventory().getValidItems()) {
                        if (invItem.getId() == BONES) {
                            player.inventory().remove(BONES, 1);
                            player.inventory().add(PEACH, 1);
                            index++;
                        }
                    }
                    player.graphic(141, 100, 0);
                    player.animate(722);
                    player.skills().addXp(Skills.MAGIC, this.baseExperience() * index);
                    player.getClickDelay().reset();
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(500)) {
                    return false;
                }
                if (!player.inventory().contains(BONES)) {
                    player.message("You do not have any bones in your inventory.");
                    return false;
                }
                return super.canCast(player, target, delete);
            }

        }),

        BAKE_PIE(new Spell() {

            @Override
            public String name() {
                return "Bake pie";
            }

            @Override
            public int spellId() {
                return 30017;
            }

            @Override
            public int levelRequired() {
                return 65;
            }

            @Override
            public int baseExperience() {
                return 0; // Exp handled in script
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(ASTRAL_RUNE),
                    Item.of(FIRE_RUNE, 5),
                    Item.of(WATER_RUNE, 4)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {

            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.getClickDelay().elapsed(1800)) {
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        CURE_PLANT(new Spell() {

            @Override
            public String name() {
                return "Cure plant";
            }

            @Override
            public int spellId() {
                return 30025;
            }

            @Override
            public int levelRequired() {
                return 66;
            }

            @Override
            public int baseExperience() {
                return 60;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(ASTRAL_RUNE, 1),
                    Item.of(EARTH_RUNE, 8)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }

        }),

        CURE_OTHER(new Spell() {
            @Override
            public String name() {
                return "Cure other";
            }

            @Override
            public int spellId() {
                return 30048;
            }

            @Override
            public int levelRequired() {
                return 68;
            }

            @Override
            public int baseExperience() {
                return 61;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(LAW_RUNE, 1),
                    new Item(ASTRAL_RUNE, 1),
                    new Item(EARTH_RUNE, 10)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    if (castOn.isNpc()) {
                        player.message("You can only use this spell on players.");
                        return;
                    }
                    Player target = castOn.getAsPlayer();
                    if (target.getDueling().inDuel()) {
                        player.message("This player can't be cured right now.");
                        return;
                    }
                    if (target.<Boolean>getAttribOr(AttributeKey.ACCEPT_AID, false)) {
                        player.message("This player is not accepting aid right now.");
                        return;
                    }
                    if (!Poison.poisoned(target)) {
                        player.message("This player is not poisoned.");
                        return;
                    }
                    player.runFn(1, () -> {
                        player.lock();
                        player.animate(4411);
                        player.sound(2886);
                    }).then(4, () -> {
                        target.graphic(738, 95, 0);
                        Poison.cure(target);
                        target.message(player.getUsername() + " has cured your poison.");
                        player.unlock();
                    });
                }
            }
        }),

        HUMIDIFY(new Spell() {
            @Override
            public String name() {
                return "Humidify";
            }

            @Override
            public int spellId() {
                return 30056;
            }

            @Override
            public int levelRequired() {
                return 68;
            }

            @Override
            public int baseExperience() {
                return 68;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(ASTRAL_RUNE, 1),
                    new Item(WATER_RUNE, 3),
                    new Item(FIRE_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Humidify.castHumidify(player);
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        CURE_ME(new Spell() {
            @Override
            public String name() {
                return "Cure me";
            }

            @Override
            public int spellId() {
                return 30091;
            }

            @Override
            public int levelRequired() {
                return 71;
            }

            @Override
            public int baseExperience() {
                return 69;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(ASTRAL_RUNE,2),
                    new Item(COSMIC_RUNE,2),
                    new Item(LAW_RUNE,1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    if (player.getDueling().inDuel()) {
                        player.message("You can't be cured right now.");
                        return;
                    }
                    if (!Poison.poisoned(player)) {
                        player.message("You are not poisoned.");
                        return;
                    }
                    player.runFn(1, () -> {
                        player.lock();
                        player.animate(4411);
                        player.sound(2886);
                    }).then(4, () -> {
                        player.graphic(738, 95, 0);
                        Poison.cure(player);
                        player.unlock();
                    });
                }
            }
        }),

        SUPERGLASS_MAKE(new Spell() {
            @Override
            public String name() {
                return "Superglass Make";
            }

            @Override
            public int spellId() {
                return 30154;
            }

            @Override
            public int levelRequired() {
                return 77;
            }

            @Override
            public int baseExperience() {
                return 0;//Handle by script
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(ASTRAL_RUNE,2),
                    new Item(FIRE_RUNE,6),
                    new Item(AIR_RUNE,10)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
            }
        }),

        VENGEANCE_OTHER(new Spell() {

            @Override
            public String name() {
                return "Vengeance other";
            }

            @Override
            public int spellId() {
                return 30298;
            }

            @Override
            public int levelRequired() {
                return 93;
            }

            @Override
            public int baseExperience() {
                return 108;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    Item.of(ASTRAL_RUNE, 3),
                    Item.of(EARTH_RUNE, 10),
                    Item.of(DEATH_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
            }

            public boolean canCastOn(Player player, Player target) {
                return super.canCastOn(player, target);
            }
        }),

        VENGEANCE(new Spell() {

            @Override
            public String name() {
                return "Vengeance";
            }

            @Override
            public int spellId() {
                return 30306;
            }

            @Override
            public int levelRequired() {
                return 94;
            }

            @Override
            public int baseExperience() {
                return 112;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                boolean spellSack = player.inventory().contains(BLIGHTED_VENGEANCE_SACK);
                if (spellSack) {
                    return List.of(Item.of(BLIGHTED_VENGEANCE_SACK, 1));
                }
                return List.of(
                    Item.of(ASTRAL_RUNE, 4),
                    Item.of(EARTH_RUNE, 10),
                    Item.of(DEATH_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return false;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                // Send message and effect timer to client
                if (player != null) {
                    if (!player.locked()) {
                        if (!player.getTimers().has(TimerKey.VENGEANCE_COOLDOWN)) {
                            player.getTimers().register(TimerKey.VENGEANCE_COOLDOWN, 50);
                            player.putAttrib(AttributeKey.VENGEANCE_ACTIVE, true);
                            itemsRequired(player).forEach(player.inventory()::remove);
                            player.animate(8316);
                            player.graphic(726);
                            player.sound(2907);
                            player.skills().addXp(Skills.MAGIC, 112);
                            player.getPacketSender().sendEffectTimer(30, EffectTimer.VENGEANCE).sendMessage("You now have Vengeance's effect.");
                        } else {
                            player.message("You can only cast vengeance spells every 30 seconds.");
                        }
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                boolean hasVengeance = player.getAttribOr(AttributeKey.VENGEANCE_ACTIVE, false);
                if (player.getDueling().inDuel()) {
                    player.message("You cannot cast vengeance during a duel!");
                    return false;
                }
                if (player.skills().level(Skills.DEFENCE) < 40) {
                    player.message("You need 40 Defence to use Vengence.");
                    return false;
                } else if (player.skills().level(Skills.MAGIC) < 94) {
                    player.message("Your Magic level is not high enough to use this spell.");
                    return false;
                } else if (hasVengeance) {
                    player.message("You already have Vengeance casted.");
                    return false;
                } else if (player.getDueling().inDuel() && player.getDueling().getRules()[DuelRule.NO_MAGIC.ordinal()]) {
                    player.message("Magic is disabled for this duel.");
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        //City teleports
        HOME_NORMAL(new Spell() {
            @Override
            public String name() {
                return "Lumbridge Home Teleport";
            }

            @Override
            public int spellId() {
                return 19210;
            }

            @Override
            public int levelRequired() {
                return 1;
            }

            @Override
            public int baseExperience() {
                return 0;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of();
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return false;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = GameServer.properties().defaultTile;
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                        player.message("You have been teleported to home.");
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (player.getDueling().inDuel()) {
                    player.message("You can't teleport out of a duel.");
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        VARROCK_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Varrock teleport";
            }

            @Override
            public int spellId() {
                return 1164;
            }

            @Override
            public int levelRequired() {
                return 25;
            }

            @Override
            public int baseExperience() {
                return 35;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(AIR_RUNE, 3),
                    new Item(FIRE_RUNE, 1),
                    new Item(LAW_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(3210, 3424, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        /*LUMBRIDGE_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Lumbridge teleport";
            }

            @Override
            public int spellId() {
                return 1167;
            }

            @Override
            public int levelRequired() {
                return 31;
            }

            @Override
            public int baseExperience() {
                return 41;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(AIR_RUNE, 3),
                    new Item(EARTH_RUNE, 1),
                    new Item(LAW_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(3222, 3218, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),*/

        /*FALADOR_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Falador teleport";
            }

            @Override
            public int spellId() {
                return 1170;
            }

            @Override
            public int levelRequired() {
                return 37;
            }

            @Override
            public int baseExperience() {
                return 47;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(AIR_RUNE, 3),
                    new Item(WATER_RUNE, 1),
                    new Item(LAW_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2964, 3378, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),*/

        TELEPORT_TO_HOUSE(new Spell() {
            @Override
            public String name() {
                return "Teleport to house";
            }

            @Override
            public int spellId() {
                return 19208;
            }

            @Override
            public int levelRequired() {
                return 40;
            }

            @Override
            public int baseExperience() {
                return 30;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(AIR_RUNE, 3),
                    new Item(EARTH_RUNE, 1),
                    new Item(LAW_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    //TODO
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (true) {
                    player.message("We do not have construction yet.");
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        /*CAMELOT_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Camelot teleport";
            }

            @Override
            public int spellId() {
                return 1174;
            }

            @Override
            public int levelRequired() {
                return 45;
            }

            @Override
            public int baseExperience() {
                return (int) 55.5;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(AIR_RUNE, 5),
                    new Item(LAW_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2757, 3477, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),*/

        ARDOUGNE_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Ardougne teleport";
            }

            @Override
            public int spellId() {
                return 1540;
            }

            @Override
            public int levelRequired() {
                return 51;
            }

            @Override
            public int baseExperience() {
                return 61;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(WATER_RUNE, 2),
                    new Item(LAW_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2662, 3305, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        WATCHTOWER_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Watchtower teleport";
            }

            @Override
            public int spellId() {
                return 1541;
            }

            @Override
            public int levelRequired() {
                return 58;
            }

            @Override
            public int baseExperience() {
                return 68;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(EARTH_RUNE, 2),
                    new Item(LAW_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2931, 4711, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        TROLLHEIM_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Trollheim teleport";
            }

            @Override
            public int spellId() {
                return 7455;
            }

            @Override
            public int levelRequired() {
                return 61;
            }

            @Override
            public int baseExperience() {
                return 68;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(FIRE_RUNE, 2),
                    new Item(LAW_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2914, 3738, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        APE_ATOLL_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Teleport to Ape Atoll";
            }

            @Override
            public int spellId() {
                return 18470;
            }

            @Override
            public int levelRequired() {
                return 64;
            }

            @Override
            public int baseExperience() {
                return 74;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(FIRE_RUNE, 2),
                    new Item(WATER_RUNE, 2),
                    new Item(LAW_RUNE, 1),
                    new Item(BANANA, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2778, 2786, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        KOUREND_CASTLE_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Kourend Castle Teleport";
            }

            @Override
            public int spellId() {
                return 31674;
            }

            @Override
            public int levelRequired() {
                return 69;
            }

            @Override
            public int baseExperience() {
                return 82;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(FIRE_RUNE, 5),
                    new Item(WATER_RUNE, 4),
                    new Item(LAW_RUNE, 2),
                    new Item(SOUL_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(1543, 3466, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        TELEPORT_TO_TARGET_NORMAL(new Spell() {

            @Override
            public String name() {
                return "Teleport to target";
            }

            @Override
            public int spellId() {
                return 13674;
            }

            @Override
            public int levelRequired() {
                return 85;
            }

            @Override
            public int baseExperience() {
                return 45;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                boolean spellSack = player.inventory().contains(BLIGHTED_TELEPORT_SPELL_SACK);
                if (spellSack) {
                    return List.of(Item.of(BLIGHTED_TELEPORT_SPELL_SACK, 1));
                }
                return List.of(new Item[]{new Item(LAW_RUNE, 1), new Item(DEATH_RUNE, 3), new Item(CHAOS_RUNE, 3)});
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                // Send message and effect timer to client
                if (player != null) {
                    if (!player.locked()) {
                        Optional<Player> target = BountyHunter.getTargetfor(player);
                        if (target.isPresent()) {
                            boolean targetInMulti = target.get().<Integer>getAttribOr(AttributeKey.MULTIWAY_AREA, -1) == 1;
                            if (targetInMulti) {
                                player.confirmDialogue(new String[]{"Are you sure you wish to teleport?", "Your target is inside a multiway area."}, "", "Proceed.", "Nevermind.", () -> {
                                    if (!WildernessArea.inWilderness(target.get().tile())) {
                                        return;
                                    }
                                    Teleports.basicTeleport(player, new Tile(target.get().tile().getX(), target.get().tile().getY() - 1, target.get().tile().level));
                                    itemsRequired(player).forEach(player.inventory()::remove);
                                    player.getClickDelay().reset();
                                });
                                return;
                            }
                            itemsRequired(player).forEach(player.inventory()::remove);
                            Teleports.basicTeleport(player, new Tile(target.get().tile().getX(), target.get().tile().getY() - 1, target.get().tile().level));
                            player.getClickDelay().reset();
                        }
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.<Boolean>getAttribOr(AttributeKey.BOUNTY_HUNTER_TARGET_TELEPORT_UNLOCKED, false)) {
                    player.message("You have not unlocked this spell yet.");
                    return false;
                }

                if (!WildernessArea.inWilderness(player.tile())) {
                    player.message("You must be in the Wilderness to use this spell.");
                    return false;
                }

                if (CombatFactory.inCombat(player)) {
                    player.message("You can't cast this spell during combat.");
                    return false;
                }

                Optional<Player> targetFor = BountyHunter.getTargetfor(player);
                if (targetFor.isPresent()) {
                    if(targetFor.get().tile().insideRiskArea()) {
                        player.message("Your target is currently in the riskzone area, you cannot teleport there.");
                        return false;
                    }

                    if (!WildernessArea.inWilderness(targetFor.get().tile())) {
                        player.message("Your target is currently not in the Wilderness.");
                        return false;
                    }

                    //Prevent players from teleporting to targets that are doing agility obstacles.
                    if (targetFor.get().getMovementQueue().forcedStep()) {
                        player.message("You can't teleport to your target at this time.");
                        return false;
                    }

                    if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        return false;
                    }

                    if (!player.getClickDelay().elapsed(30000)) {
                        player.message("You have just teleported to your target. There is a 30 second cooldown.");
                        return false;
                    }
                } else {
                    player.message("You currently have no target to teleport to!");
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        HOME_ANCIENT(new Spell() {
            @Override
            public String name() {
                return "Edgeville Home Teleport";
            }

            @Override
            public int spellId() {
                return 21741;
            }

            @Override
            public int levelRequired() {
                return 1;
            }

            @Override
            public int baseExperience() {
                return 0;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of();
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return false;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = GameServer.properties().defaultTile;
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                        player.message("You have been teleported to home.");
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (player.getDueling().inDuel()) {
                    player.message("You can't teleport out of a duel.");
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        PADDEWWA_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Paddewwa Teleport";
            }

            @Override
            public int spellId() {
                return 13035;
            }

            @Override
            public int levelRequired() {
                return 54;
            }

            @Override
            public int baseExperience() {
                return 64;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(AIR_RUNE, 1),
                    new Item(FIRE_RUNE, 1),
                    new Item(LAW_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(3098, 9884, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        SENNTISTEN_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Senntisten Teleport";
            }

            @Override
            public int spellId() {
                return 13045;
            }

            @Override
            public int levelRequired() {
                return 60;
            }

            @Override
            public int baseExperience() {
                return 70;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(LAW_RUNE, 2),
                    new Item(SOUL_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(3322, 3336, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        KHARYRLL_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Kharyrll Teleport";
            }

            @Override
            public int spellId() {
                return 13053;
            }

            @Override
            public int levelRequired() {
                return 66;
            }

            @Override
            public int baseExperience() {
                return 76;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(BLOOD_RUNE, 1),
                    new Item(LAW_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(3492, 3471, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        LASSAR_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Lassar Teleport";
            }

            @Override
            public int spellId() {
                return 31674;
            }

            @Override
            public int levelRequired() {
                return 72;
            }

            @Override
            public int baseExperience() {
                return 82;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(WATER_RUNE, 2),
                    new Item(LAW_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(3006, 3471, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        DAREEYAK_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Dareeyak Teleport";
            }

            @Override
            public int spellId() {
                return 13069;
            }

            @Override
            public int levelRequired() {
                return 78;
            }

            @Override
            public int baseExperience() {
                return 88;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(AIR_RUNE, 2),
                    new Item(FIRE_RUNE, 3),
                    new Item(LAW_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2966, 3695, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        CARRALLANGAR_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Carrallangar Teleport";
            }

            @Override
            public int spellId() {
                return 13079;
            }

            @Override
            public int levelRequired() {
                return 84;
            }

            @Override
            public int baseExperience() {
                return 94;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(LAW_RUNE, 2),
                    new Item(SOUL_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(3156, 3666, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        TELEPORT_TO_TARGET_ANCIENT(new Spell() {

            @Override
            public String name() {
                return "Teleport to target";
            }

            @Override
            public int spellId() {
                return 34674;
            }

            @Override
            public int levelRequired() {
                return 85;
            }

            @Override
            public int baseExperience() {
                return 45;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                boolean spellSack = player.inventory().contains(BLIGHTED_TELEPORT_SPELL_SACK);
                if (spellSack) {
                    return List.of(Item.of(BLIGHTED_TELEPORT_SPELL_SACK, 1));
                }
                return List.of(new Item[]{new Item(LAW_RUNE, 1), new Item(DEATH_RUNE, 3), new Item(CHAOS_RUNE, 3)});
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                // Send message and effect timer to client
                if (player != null) {
                    if (!player.locked()) {
                        Optional<Player> target = BountyHunter.getTargetfor(player);
                        if (target.isPresent()) {
                            boolean targetInMulti = target.get().<Integer>getAttribOr(AttributeKey.MULTIWAY_AREA, -1) == 1;
                            if (targetInMulti) {
                                player.confirmDialogue(new String[]{"Are you sure you wish to teleport?", "Your target is inside a multiway area."}, "", "Proceed.", "Nevermind.", () -> {
                                    Teleports.teleportToTarget(player, target.get().tile());
                                    itemsRequired(player).forEach(player.inventory()::remove);
                                    player.getClickDelay().reset();
                                });
                                return;
                            }
                            itemsRequired(player).forEach(player.inventory()::remove);
                            Teleports.teleportToTarget(player, target.get().tile());
                            player.getClickDelay().reset();
                        }
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                if (!player.<Boolean>getAttribOr(AttributeKey.BOUNTY_HUNTER_TARGET_TELEPORT_UNLOCKED, false)) {
                    player.message("You have not unlocked this spell yet.");
                    return false;
                }

                if (!WildernessArea.inWilderness(player.tile())) {
                    player.message("You must be in the Wilderness to use this spell.");
                    return false;
                }

                if (CombatFactory.inCombat(player)) {
                    player.message("You can't cast this spell during combat.");
                    return false;
                }

                Optional<Player> targetFor = BountyHunter.getTargetfor(player);
                if (targetFor.isPresent()) {
                    if(targetFor.get().tile().insideRiskArea()) {
                        player.message("Your target is currently in the riskzone area, you cannot teleport there.");
                        return false;
                    }

                    if (!WildernessArea.inWilderness(targetFor.get().tile())) {
                        player.message("Your target is currently not in the Wilderness.");
                        return false;
                    }

                    //Prevent players from teleporting to targets that are doing agility obstacles.
                    if (targetFor.get().getMovementQueue().forcedStep()) {
                        player.message("You can't teleport to your target at this time.");
                        return false;
                    }

                    if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        return false;
                    }

                    if (!player.getClickDelay().elapsed(30000)) {
                        player.message("You have just teleported to your target. There is a 30 second cooldown.");
                        return false;
                    }
                } else {
                    player.message("You currently have no target to teleport to!");
                    return false;
                }
                return super.canCast(player, target, delete);
            }
        }),

        ANNAKARL_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Annakarl Teleport";
            }

            @Override
            public int spellId() {
                return 13087;
            }

            @Override
            public int levelRequired() {
                return 90;
            }

            @Override
            public int baseExperience() {
                return 100;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(BLOOD_RUNE, 2),
                    new Item(LAW_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(3288, 3886, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        GHORROCK_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Ghorrock Teleport";
            }

            @Override
            public int spellId() {
                return 13095;
            }

            @Override
            public int levelRequired() {
                return 96;
            }

            @Override
            public int baseExperience() {
                return 106;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(WATER_RUNE, 8),
                    new Item(LAW_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2977, 3873, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile);
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        MOONCLAN_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Moonclan Teleport";
            }

            @Override
            public int spellId() {
                return 30064;
            }

            @Override
            public int levelRequired() {
                return 69;
            }

            @Override
            public int baseExperience() {
                return 35;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(ASTRAL_RUNE, 2),
                    new Item(EARTH_RUNE, 2)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2111, 3914, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile, 1816, new Graphic(747, 120, 0));
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        OURIANA_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Ouriana Teleport";
            }

            @Override
            public int spellId() {
                return 30083;
            }

            @Override
            public int levelRequired() {
                return 71;
            }

            @Override
            public int baseExperience() {
                return 35;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(LAW_RUNE, 1),
                    new Item(ASTRAL_RUNE, 2),
                    new Item(EARTH_RUNE, 6)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2466, 3245, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile, 1816, new Graphic(747, 120, 0));
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        /*WATERBIRTH_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Waterbirth Teleport";
            }

            @Override
            public int spellId() {
                return 30106;
            }

            @Override
            public int levelRequired() {
                return 72;
            }

            @Override
            public int baseExperience() {
                return 41;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(LAW_RUNE, 1),
                    new Item(ASTRAL_RUNE, 2),
                    new Item(WATER_RUNE, 1)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2544, 3753, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile, 1816, new Graphic(747, 120, 0));
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),*/

        BARBARIAN_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Barbarian Teleport";
            }

            @Override
            public int spellId() {
                return 30138;
            }

            @Override
            public int levelRequired() {
                return 75;
            }

            @Override
            public int baseExperience() {
                return 48;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(LAW_RUNE, 2),
                    new Item(ASTRAL_RUNE, 2),
                    new Item(FIRE_RUNE, 3)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2541, 3567, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile, 1816, new Graphic(747, 120, 0));
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        KHAZARD_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Khazard Teleport";
            }

            @Override
            public int spellId() {
                return 30162;
            }

            @Override
            public int levelRequired() {
                return 78;
            }

            @Override
            public int baseExperience() {
                return (int) 55.5;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(LAW_RUNE, 2),
                    new Item(ASTRAL_RUNE, 2),
                    new Item(WATER_RUNE, 4)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2636, 3165, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile, 1816, new Graphic(747, 120, 0));
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        FISHING_GUILD_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Fishing Guild Teleport";
            }

            @Override
            public int spellId() {
                return 30226;
            }

            @Override
            public int levelRequired() {
                return 85;
            }

            @Override
            public int baseExperience() {
                return 61;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(LAW_RUNE, 3),
                    new Item(ASTRAL_RUNE, 3),
                    new Item(WATER_RUNE, 10)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2608, 3389, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile, 1816, new Graphic(747, 120, 0));
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        CATHERBY_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Catherby Teleport";
            }

            @Override
            public int spellId() {
                return 30250;
            }

            @Override
            public int levelRequired() {
                return 87;
            }

            @Override
            public int baseExperience() {
                return 68;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(LAW_RUNE, 3),
                    new Item(ASTRAL_RUNE, 3),
                    new Item(WATER_RUNE, 10)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2805, 3455, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile, 1816, new Graphic(747, 120, 0));
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),

        ICE_PLATEAU_TELEPORT(new Spell() {
            @Override
            public String name() {
                return "Ice Plateau Teleport";
            }

            @Override
            public int spellId() {
                return 30266;
            }

            @Override
            public int levelRequired() {
                return 89;
            }

            @Override
            public int baseExperience() {
                return 71;
            }

            @Override
            public List<Item> itemsRequired(Player player) {
                return List.of(
                    new Item(LAW_RUNE, 3),
                    new Item(ASTRAL_RUNE, 3),
                    new Item(WATER_RUNE, 8)
                );
            }

            @Override
            public List<Item> equipmentRequired(Player player) {
                return List.of();
            }

            @Override
            public boolean deleteRunes() {
                return true;
            }

            @Override
            public void startCast(Mob cast, Mob castOn) {
                final Player player = cast.isPlayer() ? (Player) cast : null;

                if (player != null) {
                    Tile tile = new Tile(2971, 3917, 0);
                    if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        Teleports.basicTeleport(player, tile, 1816, new Graphic(747, 120, 0));
                    }
                }
            }

            @Override
            public boolean canCast(Player player, Mob target, boolean delete) {
                return super.canCast(player, target, delete);
            }
        }),
        ;

        public static final ImmutableSet<MagicSpells> VALUES = Sets.immutableEnumSet(EnumSet.allOf(MagicSpells.class));

        MagicSpells(Spell spell) {
            this.spell = checkNotNull(spell, "spell");
        }

        private final Spell spell;

        public static Optional<MagicSpells> find(final int buttonId) {
            return VALUES.stream().filter(spell -> spell.getSpell().spellId() == buttonId).findFirst();
        }

        public Spell getSpell() {
            return spell;
        }
    }

    /**
     * Handles clickable spellbook spells. Spells cast on other entities will be handles via
     * a different packet.
     *
     * @param player The player
     * @param button The button
     */
    public static boolean handleSpell(Player player, int button) {
        final Optional<MagicSpells> magicSpell = MagicSpells.find(button);

        if (magicSpell.isPresent()) {
            final Spell spell = magicSpell.get().getSpell();
            if (!spell.canCast(player, null, spell.deleteRunes())) {
                return false;
            }
            spell.startCast(player, null);
            return true;
        }
        return false;
    }

    public static Optional<MagicSpells> getMagicSpells(int id) {
        return Arrays.stream(MagicSpells.values()).filter(s -> s != null && s.getSpell().spellId() == id).findFirst();
    }

    public static Spell getMagicSpell(int spellId) {
        return getMagicSpells(spellId).map(MagicSpells::getSpell).orElse(null);
    }

    public static void handleSpellOnPlayer(Player player, Player attacked, Spell spell) {
        if (!spell.canCastOn(player, attacked)) {
            return;
        }

        if (!spell.canCast(player, attacked, false)) {
            return;
        }

        spell.deleteRequiredRunes(player, new HashMap<>());
        player.face(attacked.tile());
        spell.startCast(player, attacked);
    }

    /**
     * Handles the spells on objects.
     *
     * @param player
     * @param object
     * @param tile
     * @param spell_id
     * @return
     */
    public static boolean handleSpellOnObject(Player player, GameObject object, Tile tile, int spell_id) {

        Optional<MagicSpells> spell = getMagicSpells(spell_id);

        if (spell.isEmpty()) {
            return false;
        }

        if (!spell.get().getSpell().canCast(player, null, true)) {
            return false;
        }
        return true;
    }

}

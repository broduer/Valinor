package com.valinor.game.content.areas.home;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.areas.edgevile.Mac;
import com.valinor.game.content.areas.edgevile.dialogue.AuburyDialogue;
import com.valinor.game.content.areas.edgevile.dialogue.DrunkenDwarfDialogue;
import com.valinor.game.content.areas.edgevile.dialogue.PerduDialogue;
import com.valinor.game.content.areas.lumbridge.dialogue.Hans;
import com.valinor.game.content.group_ironman.GroupIronmanInterface;
import com.valinor.game.content.mechanics.Poison;
import com.valinor.game.content.mechanics.referrals.ReferralD;
import com.valinor.game.content.mechanics.referrals.Referrals;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.Venom;
import com.valinor.game.world.entity.masks.animations.Animation;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import static com.valinor.util.ItemIdentifiers.COINS_995;
import static com.valinor.util.NpcIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.ALTAR;

/**
 * @author Patrick van Elderen | April, 23, 2021, 10:49
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class HomeArea extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if (option == 1) {
            if (object.getId() == CLOSED_CHEST_2996) {
                player.faceObj(object);
                player.getSlayerKey().open();
                return true;
            }
            if (object.getId() == PORTAL_15477) {
                player.faceObj(object);
                player.message(Color.RED.wrap("Construction is being worked on and not ready to be released yet."));
                return true;
            }
            if (object.getId() == ALTAR) {
                if (player.skills().level(Skills.PRAYER) < player.skills().xpLevel(Skills.PRAYER)) {
                    player.animate(new Animation(645));
                    player.skills().replenishSkill(5, player.skills().xpLevel(5));
                    player.message("You recharge your Prayer points.");
                } else {
                    player.message("You already have full prayer points.");
                }
                return true;
            }
            if (object.getId() == ORNATE_POOL_OF_REJUVENATION) {
                Chain.bound(null).name("RejuvenationPoolTask").runFn(1, () -> player.animate(7305)).then(2, () -> {
                    player.lock();
                    player.message("<col=" + Color.BLUE.getColorValue() + ">You have restored your hitpoints, run energy and prayer.");
                    player.message("<col=" + Color.HOTPINK.getColorValue() + ">You've also been cured of poison and venom.");
                    player.skills().resetStats();
                    int increase = player.getEquipment().hpIncrease();
                    player.hp(Math.max(increase > 0 ? player.skills().level(Skills.HITPOINTS) + increase : player.skills().level(Skills.HITPOINTS), player.skills().xpLevel(Skills.HITPOINTS)), 39); //Set hitpoints to 100%
                    player.skills().replenishSkill(5, player.skills().xpLevel(5)); //Set the players prayer level to fullplayer.putAttrib(AttributeKey.RUN_ENERGY, 100.0);
                    player.setRunningEnergy(100.0, true);
                    Poison.cure(player);
                    Venom.cure(2, player);

                    if (!player.getMemberRights().isSapphireMemberOrGreater(player))
                        player.message(Color.RED.tag() + "When being a member your special attack will also regenerate.");

                    if (player.getMemberRights().isSapphireMemberOrGreater(player)) {
                        if (player.getTimers().has(TimerKey.RECHARGE_SPECIAL_ATTACK)) {
                            player.message("Special attack energy can be restored in " + player.getTimers().asMinutesAndSecondsLeft(TimerKey.RECHARGE_SPECIAL_ATTACK) + ".");
                        } else {
                            player.restoreSpecialAttack(100);
                            player.setSpecialActivated(false);
                            CombatSpecial.updateBar(player);
                            int time = 0;
                            if (player.getMemberRights().isSapphireMemberOrGreater(player))
                                time = 300;//3 minutes
                            if (player.getMemberRights().isEmeraldMemberOrGreater(player))
                                time = 100;//1 minute
                            if (player.getMemberRights().isRubyMemberOrGreater(player))
                                time = 0;//always
                            player.getTimers().register(TimerKey.RECHARGE_SPECIAL_ATTACK, time); //Set the value of the timer.
                            player.message("<col=" + Color.HOTPINK.getColorValue() + ">You have restored your special attack.");
                        }
                    }
                    player.unlock();
                });
                return true;
            }
            if (object.getId() == ALTAR_OF_THE_OCCULT) {
                player.getDialogueManager().start(new MagicalAltarD());
                return true;
            }

            if (object.getId() == ANTIDRAGON_SHIELD) {
                Item item = new Item(ItemIdentifiers.ANTIDRAGON_SHIELD);
                if (player.inventory().hasCapacityFor(item)) {
                    player.animate(536);
                    player.inventory().add(item);
                } else {
                    player.message("You do not have enough space in your inventory.");
                }
                return true;
            }

            if (object.getId() == BRONZE_PICKAXE) {
                Item item = new Item(ItemIdentifiers.BRONZE_PICKAXE);
                if (player.inventory().hasCapacityFor(item)) {
                    player.animate(536);
                    player.inventory().add(item);
                } else {
                    player.message("You do not have enough space in your inventory.");
                }
                return true;
            }

            if (object.getId() == BRONZE_AXE) {
                Item item = new Item(ItemIdentifiers.BRONZE_AXE);
                if (player.inventory().hasCapacityFor(item)) {
                    player.animate(536);
                    player.inventory().add(item);
                } else {
                    player.message("You do not have enough space in your inventory.");
                }
                return true;
            }

            if (object.getId() == MOUNTED_MUSIC_CAPE) {
                Item cape = new Item(ItemIdentifiers.MUSIC_CAPE);
                Item hood = new Item(ItemIdentifiers.MUSIC_HOOD);
                if (player.inventory().hasCapacityFor(cape, hood)) {
                    player.animate(536);
                    player.inventory().addAll(cape, hood);
                } else {
                    player.message("You do not have enough space in your inventory.");
                }
                return true;
            }
            if (object.getId() == MOUNTED_MAX_CAPE_29170) {
                if (Mac.success(player)) {
                    Item cape = new Item(ItemIdentifiers.MAX_CAPE);
                    Item hood = new Item(ItemIdentifiers.MAX_HOOD);
                    if (player.inventory().hasCapacityFor(cape, hood)) {
                        var canAfford = false;
                        int currencyInInventory = player.inventory().count(COINS_995);
                        if (currencyInInventory > 0) {
                            if (currencyInInventory >= 50_000_000) {
                                canAfford = true;
                                player.inventory().remove(new Item(COINS_995, 50_000_000), true);
                            }
                        }

                        if (canAfford) {
                            player.animate(536);
                            player.inventory().addAll(cape, hood);
                        } else {
                            player.message("Sorry, but it appears as if you do not have enough coins to afford this cape.");
                        }
                    } else {
                        player.message("You do not have enough space in your inventory.");
                    }
                }
                return true;
            }
            if (object.getId() == MOUNTED_QUEST_CAPE_29179) {
                Item cape = new Item(ItemIdentifiers.QUEST_POINT_CAPE);
                Item hood = new Item(ItemIdentifiers.QUEST_POINT_HOOD);
                if (player.inventory().hasCapacityFor(cape, hood)) {
                    player.animate(536);
                    player.inventory().addAll(cape, hood);
                } else {
                    player.message("You do not have enough space in your inventory.");
                }
                return true;
            }
            if (object.getId() == MOUNTED_ACHIEVEMENT_DIARY_CAPE_29168) {
                if (AchievementsManager.isCompleted(player, Achievements.COMPLETIONIST)) {
                    Item cape = new Item(ItemIdentifiers.ACHIEVEMENT_DIARY_CAPE);
                    Item hood = new Item(ItemIdentifiers.ACHIEVEMENT_DIARY_HOOD);
                    if (player.inventory().hasCapacityFor(cape, hood)) {
                        player.animate(536);
                        player.inventory().addAll(cape, hood);
                    } else {
                        player.message("You do not have enough space in your inventory.");
                    }
                } else {
                    player.message("You haven't completed all of the achievements yet.");
                }
                return true;
            }
        }
        if (option == 2) {
            if (object.getId() == ANTIDRAGON_SHIELD) {
                Item item = new Item(ItemIdentifiers.ANTIDRAGON_SHIELD);
                EquipmentInfo info = World.getWorld().equipmentInfo();
                int targetSlot = info.slotFor(item.getId());

                if (player.getEquipment().isSlotOccupied(targetSlot)) {
                    player.message(Color.RED.wrap("You are already wearing a shield."));
                    return true;
                }

                player.animate(536);
                player.getEquipment().manualWear(item, true, true);
                return true;
            }
        }
        if (option == 3) {

        }
        return false;
    }

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if (option == 1) {
            if (npc.id() == GRAND_EXCHANGE_CLERK || npc.id() == GRAND_EXCHANGE_CLERK_2149) {
                TradingPost.open(player);
                return true;
            }
            if (npc.id() == IRON_MAN_TUTOR) {
                GroupIronmanInterface.open(player);
                return true;
            }
            if (npc.id() == PERDU) {
                player.getDialogueManager().start(new PerduDialogue());
                return true;
            }
            if (npc.id() == SHURA) {
                if (Referrals.INSTANCE.getCOMMAND_ENABLED()) {
                    player.getDialogueManager().start(new ReferralD());
                } else {
                    player.message("Referrals are currently disabled.");
                }
                return true;
            }
            if (npc.id() == COOK) {
                World.getWorld().shop(13).open(player);
                return true;
            }
            if (npc.id() == DOMMIK) {
                World.getWorld().shop(12).open(player);
                return true;
            }
            if (npc.id() == LOWE) {
                World.getWorld().shop(10).open(player);
                return true;
            }
            if (npc.id() == SHOP_ASSISTANT_2820) {
                World.getWorld().shop(1).open(player);
                return true;
            }
            if (npc.id() == HANS) {
                player.getDialogueManager().start(new Hans());
                return true;
            }
            if (npc.id() == AUBURY) {
                player.getDialogueManager().start(new AuburyDialogue());
                return true;
            }
            if (npc.id() == DRUNKEN_DWARF_2408) {
                player.getDialogueManager().start(new DrunkenDwarfDialogue());
                return true;
            }
        }
        if (option == 2) {
            if (npc.id() == DOMMIK) {
                World.getWorld().shop(12).open(player);
                return true;
            }
            if (npc.id() == LOWE) {
                World.getWorld().shop(10).open(player);
                return true;
            }
            if (npc.id() == AUBURY) {
                if (player.gameMode() == GameMode.NONE) {
                    World.getWorld().shop(11).open(player);
                } else {
                    World.getWorld().shop(23).open(player);
                }
                return true;
            }
            if (npc.id() == VANNAKA) {
                player.getTaskBottleManager().open();
                return true;
            }
            if (npc.id() == HANS) {
                player.getDialogueManager().start(new Hans());
                return true;
            }
        }
        if (option == 3) {
            if (npc.id() == AUBURY) {
                npc.forceChat("Seventhior Distine Molenko!");
                player.graphic(110, 124, 100);
                player.lockNoDamage();
                Chain.bound(player).runFn(3, () -> {
                    player.teleport(new Tile(2911, 4830, 0));
                    player.unlock();
                });
                return true;
            }
        }
        if (option == 4) {

        }
        return false;
    }
}

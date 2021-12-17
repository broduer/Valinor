package com.valinor.game.content.packet_actions.interactions.buttons;

import com.valinor.GameServer;
import com.valinor.game.content.DropsDisplay;
import com.valinor.game.content.achievements.AchievementButtons;
import com.valinor.game.content.bank_pin.BankPin;
import com.valinor.game.content.clan.ClanButtons;
import com.valinor.game.content.collection_logs.CollectionLogButtons;
import com.valinor.game.content.duel.DuelRule;
import com.valinor.game.content.emote.Emotes;
import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.content.interfaces.BonusesInterface;
import com.valinor.game.content.items_kept_on_death.ItemsKeptOnDeath;
import com.valinor.game.content.mechanics.referrals.Referrals;
import com.valinor.game.content.skill.impl.smithing.Smelting;
import com.valinor.game.content.teleport.OrnateJewelleryBox;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.content.tournaments.TournamentManager;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.magic.Autocasting;
import com.valinor.game.world.entity.combat.magic.MagicClickSpells;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.combat.weapon.WeaponInterfaces;
import com.valinor.game.world.entity.dialogue.ItemActionDialogue;
import com.valinor.game.world.entity.mob.npc.pets.PetAI;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.MagicSpellbook;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.equipment.Equipment;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.net.packet.incoming_packets.ButtonClickPacketListener;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.Optional;

import static com.valinor.game.world.entity.combat.magic.Autocasting.ANCIENT_SPELL_AUTOCAST_STAFFS;
import static com.valinor.net.packet.incoming_packets.ButtonClickPacketListener.LOGOUT;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * Handles button actions.
 */
public class Buttons {

    public static final int ADVANCED_OPTIONS_BUTTON = 42524;

    //Auto retal buttons
    private static final int TOGGLE_AUTO_RETALIATE = 22845;
    private static final int TOGGLE_AUTO_RETALIATE_2 = 24115;
    private static final int TOGGLE_AUTO_RETALIATE_3 = 24048;

    private static final int TOGGLE_RUN_ENERGY_ORB = 1050;
    public static final int RUN_BUTTON = 42507;
    private static final int OPEN_PRICE_CHECKER = 27651;
    private static final int OPEN_ITEMS_KEPT_ON_DEATH_SCREEN = 27654;
    private static final int DESTROY_ITEM = 14175;
    private static final int CANCEL_DESTROY_ITEM = 14176;
    private static final int PRICE_CHECKER_WITHDRAW_ALL = 18255;
    private static final int PRICE_CHECKER_DEPOSIT_ALL = 18252;
    private static final int TOGGLE_EXP_LOCK = 476;

    // Trade buttons
    private static final int TRADE_ACCEPT_BUTTON_1 = 52100;
    private static final int TRADE_ACCEPT_BUTTON_2 = 52319;
    private static final int TRADE_DECLINE_BUTTON_1 = 52101;
    private static final int TRADE_DECLINE_BUTTON_2 = 52320;
    private static final int TRADE_SCREEN_CLOSE_BUTTON = 52102;

    // Autocast buttons
    private static final int AUTOCAST_BUTTON_1 = 349;
    private static final int AUTOCAST_BUTTON_2 = 24111;

    // Duel buttons
    private static final int DUEL_ACCEPT_BUTTON_1 = 6674;
    private static final int DUEL_ACCEPT_BUTTON_2 = 6520;

    // Close buttons
    private static final int CLOSE_BUTTON_1 = 18247;
    private static final int CLOSE_BUTTON_2 = 38117;
    private static final int CLOSE_BUTTON_3 = 54002;
    private static final int CLOSE_BUTTON_4 = 54112;
    private static final int CLOSE_BUTTON_5 = 28056;
    private static final int CLOSE_BUTTON_6 = 29175;
    private static final int CLOSE_LOOTING_BAG_OPEN = 26702;
    private static final int CLOSE_LOOTING_BAG_ADD = 26802;
    private static final int CLOSE_LOOTING_BAG_BANK = 26902;

    // Settings tab
    private static final int OPEN_SETTINGS = 42511;
    private static final int CLOSE_SETTINGS = 23020;
    private static final int OPEN_KEYBINDINGS = 42552;

    private static final int[] CLOSE_BUTTONS = {CLOSE_BUTTON_1, CLOSE_BUTTON_2, CLOSE_BUTTON_3, CLOSE_BUTTON_4, CLOSE_BUTTON_5, CLOSE_BUTTON_6};

    /**
     * Handles the button click for a player.
     *
     * @param player The player clicking the button
     * @param button The id of the button being clicked.
     */
    public static void handleButton(Player player, int button) {
        switch (button) {
            // Teleports tab favorite button
            case 72154:
                player.getInterfaceManager().setSidebar(13, 72200);
                break;

            case 72203:
                player.getInterfaceManager().setSidebar(13, 72150);
                break;

            case 15151:
                if (WildernessArea.inWilderness(player.tile())) {
                    return;
                }
                player.getPacketSender().sendInterfaceDisplayState(15150, true);
                player.getBank().open();
                break;

            case TRADE_ACCEPT_BUTTON_1:
            case TRADE_ACCEPT_BUTTON_2:
                player.getTrading().acceptTrade();
                break;

            case TRADE_DECLINE_BUTTON_1:
            case TRADE_DECLINE_BUTTON_2:
            case TRADE_SCREEN_CLOSE_BUTTON:
                player.getTrading().abortTrading();
                break;

            case CLOSE_LOOTING_BAG_OPEN:
            case CLOSE_LOOTING_BAG_ADD:
            case CLOSE_LOOTING_BAG_BANK:
                player.getLootingBag().close();
                break;

            case 14921:
                player.getBankPinSettings().dontKnowPin();
                break;
            case 14922:

            case CANCEL_DESTROY_ITEM:
            case 35002:
                player.getInterfaceManager().close();
                break;

            case TOGGLE_RUN_ENERGY_ORB:
            case RUN_BUTTON:
                if (player.looks().trans() > -1) {
                    player.message("You can't run as an npc.");
                    return;
                }
                double energy = player.getAttribOr(AttributeKey.RUN_ENERGY, 0);
                if (energy > 0 && !player.busy()) {
                    boolean running = player.getAttribOr(AttributeKey.IS_RUNNING, false);
                    player.putAttrib(AttributeKey.IS_RUNNING, !running);
                    player.getPacketSender().sendRunStatus();
                } else {
                    player.message("You cannot do that right now.");
                }
                break;

            case OPEN_SETTINGS: // 42500
                if (!player.busy()) {
                    player.getInterfaceManager().setSidebar(11, 23000);
                } else {
                    player.message("You cannot do that right now.");
                }
                break;

            case OPEN_KEYBINDINGS:
                if (!player.busy()) {
                    player.getInterfaceManager().open(53000);
                } else {
                    player.message("You cannot do that right now.");
                }
                break;

            case OPEN_PRICE_CHECKER:
                if (!player.busy()) {
                    player.getPriceChecker().open();
                } else {
                    player.message("You cannot do that right now.");
                }
                break;

            case PRICE_CHECKER_WITHDRAW_ALL:
                player.getPriceChecker().withdrawAll();
                break;

            case PRICE_CHECKER_DEPOSIT_ALL:
                player.getPriceChecker().depositAll();
                break;

            case OPEN_ITEMS_KEPT_ON_DEATH_SCREEN:
                if (!player.busy()) {
                    ItemsKeptOnDeath.open(player);
                } else {
                    player.message("You cannot do that right now.");
                }
                break;

            case AUTOCAST_BUTTON_1:
                player.putAttrib(AttributeKey.DEFENSIVE_AUTOCAST, false);
                if (player.getSpellbook() == MagicSpellbook.LUNAR) {
                    player.message("You can't autocast lunar magic.");
                    return;
                }

                Item staff = player.getEquipment().get(EquipSlot.WEAPON);
                boolean full_ahrim_effect = CombatFactory.fullAhrims(player) && Equipment.hasAmmyOfDamned(player);
                if (staff != null && ANCIENT_SPELL_AUTOCAST_STAFFS.contains(staff.getId()) && !full_ahrim_effect) {
                    if (player.getSpellbook() == MagicSpellbook.ANCIENT) {
                        //It can autocast offensive standard spells, but cannot autocast Ancient Magicks unlike its other variants.
                        if (player.getEquipment().getWeapon().getId() != HARMONISED_NIGHTMARE_STAFF) {
                            player.getInterfaceManager().setSidebar(0, 1689);
                        } else {
                            player.message("You can only autocast regular offensive spells with this staff.");
                            return;
                        }
                    } else {
                        if (player.getEquipment().getWeapon().getId() != ANCIENT_STAFF) {
                            player.getInterfaceManager().setSidebar(0, 1829);
                        } else {
                            player.message("You can only autocast ancient magicks with that.");
                            return;
                        }
                    }
                } else {
                    if (player.getSpellbook() == MagicSpellbook.NORMAL) {
                        player.getInterfaceManager().setSidebar(0, 1829);
                    } else {
                        player.message("You can only autocast normal magic with that.");
                        return;
                    }
                }
                break;

            case AUTOCAST_BUTTON_2:
                player.putAttrib(AttributeKey.DEFENSIVE_AUTOCAST, true);
                if (player.getSpellbook() == MagicSpellbook.LUNAR) {
                    player.message("You can't autocast lunar spells.");
                    player.getPacketSender().setDefensiveAutocastState(0);
                    return;
                }
                if (player.getEquipment().get(3) != null && player.getEquipment().containsAny(ANCIENT_STAFF, MASTER_WAND, STAFF_OF_THE_DEAD, TOXIC_STAFF_UNCHARGED, TOXIC_STAFF_OF_THE_DEAD, KODAI_WAND, ELDER_WAND, ELDER_WAND_RAIDS, TOXIC_STAFF_OF_THE_DEAD_C)) {
                    player.getInterfaceManager().setSidebar(0, 1689);
                } else {
                    if (player.getSpellbook() != MagicSpellbook.NORMAL) {
                        player.message("You can't autocast ancient magicks with this staff.");
                        player.getPacketSender().setDefensiveAutocastState(0);
                        return;
                    }
                    player.getInterfaceManager().setSidebar(0, 1829);
                }
                break;

            case DUEL_ACCEPT_BUTTON_1:
            case DUEL_ACCEPT_BUTTON_2:
                player.getDueling().acceptDuel();
                break;

            case ADVANCED_OPTIONS_BUTTON:
                player.getInterfaceManager().open(43000);
                break;

            case TOGGLE_AUTO_RETALIATE:
            case TOGGLE_AUTO_RETALIATE_2:
            case TOGGLE_AUTO_RETALIATE_3:
                player.getCombat().setAutoRetaliate(!player.getCombat().autoRetaliate());
                player.getPacketSender().sendConfig(172, player.getCombat().autoRetaliate() ? 1 : 0);
                //System.out.println("hmm auto ret button "+player.getCombat().autoRetaliate());
                break;

            case DESTROY_ITEM:
                int id = player.getDestroyItem();
                Item itemToDestroy = new Item(id);
                if (!player.inventory().contains(itemToDestroy)) {
                    return;
                }

                if(id == PVMING_SCROLL) {
                    player.optionsTitled("Are you sure you wish to destroy this task?", "Yes.", "No.", () -> {
                        if(!player.inventory().contains(PVMING_SCROLL))
                            return;

                        player.inventory().remove(PVMING_SCROLL);
                        player.getTaskBottleManager().resetTask();
                    });
                    return;
                } else if(id == SKILLING_SCROLL) {
                    player.optionsTitled("Are you sure you wish to destroy this task?", "Yes.", "No.", () -> {
                        if(!player.inventory().contains(SKILLING_SCROLL))
                            return;

                        player.inventory().remove(SKILLING_SCROLL);
                        player.getTaskBottleManager().resetTask();
                    });
                    return;
                }

                player.inventory().remove(itemToDestroy, true);
                Utils.sendDiscordInfoLog("Player " + player.getUsername() + " with IP "+player.getHostAddress()+" destroyed item " + itemToDestroy.getAmount() + "x " + itemToDestroy.unnote().name() + " at " + player.tile().toString(), "item_dropped");
                player.getInterfaceManager().close();
                break;

            case TOGGLE_EXP_LOCK:
                boolean locked = player.getAttribOr(AttributeKey.XP_LOCKED, false);
                player.putAttrib(AttributeKey.XP_LOCKED, !locked);

                if (locked) {
                    player.message("Your experience is now <col=ca0d0d>locked.");
                } else {
                    player.message("Your experience is now <col=65280>unlocked.");
                }
                break;

            default:
                if (Arrays.stream(CLOSE_BUTTONS).anyMatch(b -> b == button)) {
                    player.getInterfaceManager().close();
                }

                if (button == LOGOUT) {
                    // Handle this here and not in canLogout() so that x-logging doesn't "break" the
                    // attack timer.
                    if (player.getDueling().inDuel() && player.getDueling().getRules()[DuelRule.NO_FORFEIT.ordinal()]) {
                        player.message("You cannot log out at the moment.");
                        return;
                    }
                    player.putAttrib(AttributeKey.LOGOUT_CLICKED, true);
                    return;
                }

                if (button == 54763) {
                    player.getTaskBottleManager().claimReward();
                    return;
                }

                if (player.getQuickPrayers().handleButton(button)) {
                    return;
                }

                if (TradingPost.handleButtons(player, button))
                    return;

                if (player.getSlayerRewards().handleButtonInteraction(player, button)) {
                    return;
                }
                if (player.getRunePouch().onButton(button)) {
                    return;
                }
                if (player.skills().pressedSkill(button)) {
                    return;
                }
                if (QuestTab.onButton(player, button)) {
                    return;
                }
                if (PetAI.onButton(player, button)) {
                    return;
                }
                if (Smelting.handleButton(player, button)) {
                    return;
                }
                if (BonusesInterface.bonusesButtons(player, button)) {
                    return;
                }
                if (CollectionLogButtons.onButtonClick(player, button)) {
                    return;
                }
                if (OrnateJewelleryBox.teleport(player, button)) {
                    return;
                }
                if (DropsDisplay.clickActions(player, button)) {
                    return;
                }
                if (AchievementButtons.handleButtons(player, button)) {
                    return;
                }
                if (player.getBank().buttonAction(button)) {
                    return;
                }
                Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
                if (group.isPresent() && group.get().getGroupStorage(player).buttonAction(button)) {
                    return;
                }
                if (Referrals.INSTANCE.handleButton(player, button)) {
                    return;
                }
                if (player.chatBoxItemDialogue != null) {
                    if (player.chatBoxItemDialogue.clickButton(button)) {
                        player.chatBoxItemDialogue = null;
                        return;
                    }
                }

                if (ItemActionDialogue.clickButton(player, button)) {
                    return;
                }

                BankPin bankPin = player.getBankPin();
                if (bankPin.isEnteringPin() && bankPin.getPinInterface().enterDigit(button)) {
                    return;
                }
                if (Prayers.togglePrayer(player, button)) {
                    return;
                }
                if (Autocasting.handleLegacyAutocast(player, button)) {
                    return;
                }
                if (Autocasting.toggleAutocast(player, button)) {
                    return;
                }
                if (WeaponInterfaces.changeCombatSettings(player, button)) {
                    return;
                }
                if (MagicClickSpells.handleSpell(player, button)) {
                    return;
                }
                if (player.getPriceChecker().buttonActions(button)) {
                    return;
                }
                if(player.getDepositBox().buttonActions(button)) {
                    return;
                }
                if (Emotes.doEmote(player, button)) {
                    return;
                }
                if (ClanButtons.handle(player, button)) {
                    return;
                }
                if (player.getDueling().checkRule(button)) {
                    DuelRule rule = DuelRule.forButtonId(button);
                    if(rule != null) {
                        Utils.sendDiscordInfoLog("Player " + player.getUsername() + " tried changing the "+Utils.formatEnum(rule.name())+" rule in a stake.", "stake_rules");
                    }
                    return;
                }
                if (player.getPresetManager().handleButton(button, 0)) {
                    return;
                }
                if (TournamentManager.handleWidgetButton(player, button)) {
                    return;
                }
                if (Arrays.stream(CLOSE_BUTTONS).anyMatch(b -> b == button)) {
                    return;
                }
                if (Arrays.stream(ButtonClickPacketListener.ALL).anyMatch(b -> b == button)) {
                    return;
                }
                break;
        }
    }

}

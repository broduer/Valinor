package com.valinor.game.content.account;

import com.valinor.game.GameConstants;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.Flag;
import com.valinor.game.world.entity.mob.player.*;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.timers.TimerKey;

import static com.valinor.game.GameConstants.BANK_ITEMS;
import static com.valinor.game.GameConstants.TAB_AMOUNT;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * The class which represents functionality for selecting your account type.
 *
 * @author Patrick van Elderen | 24 sep. 2021 : 19:56:14
 * @see <a href="https://github.com/PVE95/">Github profile</a>
 */
public class AccountSelection extends Interaction {

    public static void open(Player player) {
        player.getInterfaceManager().open(42400);
        refreshMode(player);
        refreshExpMode(player);
    }

    private static void refreshMode(Player player) {
        switch (player.<Integer>getAttribOr(AttributeKey.GAME_MODE_SELECTED, 42405)) {
            case 42402 -> {
                player.getPacketSender().sendChangeSprite(42402, (byte) 2);
                player.getPacketSender().sendChangeSprite(42403, (byte) 0);
                player.getPacketSender().sendChangeSprite(42423, (byte) 0);
                player.getPacketSender().sendChangeSprite(42405, (byte) 0);
                player.getPacketSender().sendChangeSprite(42406, (byte) 0);
                player.getPacketSender().sendChangeSprite(42431, (byte) 0);
                player.gameMode(GameMode.REGULAR);
            }
            case 42403 -> {
                player.getPacketSender().sendChangeSprite(42402, (byte) 0);
                player.getPacketSender().sendChangeSprite(42403, (byte) 2);
                player.getPacketSender().sendChangeSprite(42423, (byte) 0);
                player.getPacketSender().sendChangeSprite(42405, (byte) 0);
                player.getPacketSender().sendChangeSprite(42406, (byte) 0);
                player.getPacketSender().sendChangeSprite(42431, (byte) 0);
                player.gameMode(GameMode.ULTIMATE);
            }
            case 42423 -> {
                player.getPacketSender().sendChangeSprite(42402, (byte) 0);
                player.getPacketSender().sendChangeSprite(42403, (byte) 0);
                player.getPacketSender().sendChangeSprite(42423, (byte) 2);
                player.getPacketSender().sendChangeSprite(42405, (byte) 0);
                player.getPacketSender().sendChangeSprite(42406, (byte) 0);
                player.getPacketSender().sendChangeSprite(42431, (byte) 0);
                player.gameMode(GameMode.HARDCORE);
            }
            case 42405 -> {
                player.getPacketSender().sendChangeSprite(42402, (byte) 0);
                player.getPacketSender().sendChangeSprite(42403, (byte) 0);
                player.getPacketSender().sendChangeSprite(42423, (byte) 0);
                player.getPacketSender().sendChangeSprite(42405, (byte) 2);
                player.getPacketSender().sendChangeSprite(42406, (byte) 0);
                player.getPacketSender().sendChangeSprite(42431, (byte) 0);
                player.gameMode(GameMode.NONE);
            }
            case 42431 -> {
                player.getPacketSender().sendChangeSprite(42402, (byte) 0);
                player.getPacketSender().sendChangeSprite(42403, (byte) 0);
                player.getPacketSender().sendChangeSprite(42423, (byte) 0);
                player.getPacketSender().sendChangeSprite(42405, (byte) 0);
                player.getPacketSender().sendChangeSprite(42406, (byte) 0);
                player.getPacketSender().sendChangeSprite(42431, (byte) 2);
                player.getPacketSender().sendChangeSprite(42425, (byte) 0);
                player.getPacketSender().sendChangeSprite(42426, (byte) 0);
                player.getPacketSender().sendChangeSprite(42427, (byte) 0);
                player.gameMode(GameMode.INSTANT_PKER);
            }
        }
    }

    private static void refreshExpMode(Player player) {
        switch (player.<Integer>getAttribOr(AttributeKey.EXP_MODE_SELECTED, 42425)) {
            case 42425 -> {
                player.getPacketSender().sendChangeSprite(42425, (byte) 2);
                player.getPacketSender().sendChangeSprite(42426, (byte) 0);
                player.getPacketSender().sendChangeSprite(42427, (byte) 0);
                player.expmode(ExpMode.ROOKIE);
            }
            case 42426 -> {
                player.getPacketSender().sendChangeSprite(42425, (byte) 0);
                player.getPacketSender().sendChangeSprite(42426, (byte) 2);
                player.getPacketSender().sendChangeSprite(42427, (byte) 0);
                player.expmode(ExpMode.CHALLENGER);
            }
            case 42427 -> {
                player.getPacketSender().sendChangeSprite(42425, (byte) 0);
                player.getPacketSender().sendChangeSprite(42426, (byte) 0);
                player.getPacketSender().sendChangeSprite(42427, (byte) 2);
                player.expmode(ExpMode.GLADIATOR);
            }
        }
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        for (AccountType type : AccountType.values()) {
            if (type.getButtonId() == button) {
                if(player.getTimers().has(TimerKey.CLICK_DELAY)) {
                    return true;
                }

                if (player.<Integer>getAttribOr(AttributeKey.GAME_MODE_SELECTED,42405) == button) {
                    player.message("You've already selected this option.");
                } else {
                    if (button == 42402) {
                        player.putAttrib(AttributeKey.GAME_MODE_SELECTED,42402);
                    } else if (button == 42403) {
                        player.putAttrib(AttributeKey.GAME_MODE_SELECTED,42403);
                    } else if (button == 42423) {
                        player.putAttrib(AttributeKey.GAME_MODE_SELECTED,42423);
                    } else if (button == 42405) {
                        player.putAttrib(AttributeKey.GAME_MODE_SELECTED,42405);
                    } else if (button == 42431) {
                        player.putAttrib(AttributeKey.GAME_MODE_SELECTED,42431);
                    }
                    player.getTimers().register(TimerKey.CLICK_DELAY,2);
                    refreshMode(player);
                }
                return true;
            }
        }

        if(button > 42424 && button < 42428) {
            if(player.getTimers().has(TimerKey.CLICK_DELAY)) {
                return true;
            }

            if (player.<Integer>getAttribOr(AttributeKey.GAME_MODE_SELECTED,42405) == 42431) {
                player.message("As an instant pker you have no exp modes.");
                return true;
            }

            if (player.<Integer>getAttribOr(AttributeKey.EXP_MODE_SELECTED,42425) == button) {
                player.message("You've already selected this option.");
            } else {
                if (button == 42425) {
                    player.putAttrib(AttributeKey.EXP_MODE_SELECTED,42425);
                    player.expmode(ExpMode.ROOKIE);
                } else if (button == 42426) {
                    player.putAttrib(AttributeKey.EXP_MODE_SELECTED,42426);
                    player.expmode(ExpMode.CHALLENGER);
                } else {
                    player.putAttrib(AttributeKey.EXP_MODE_SELECTED,42427);
                    player.expmode(ExpMode.GLADIATOR);
                }
                player.getTimers().register(TimerKey.CLICK_DELAY,2);
                refreshExpMode(player);
            }
            return true;
        }

        if(button == 42419) {
            if(player.getTimers().has(TimerKey.CLICK_DELAY)) {
                return true;
            }
            confirm(player);
            player.getTimers().register(TimerKey.CLICK_DELAY,2);
            return true;
        }
        return false;
    }

    private void gearUp(Player player, GameMode type) {
        switch (type) {
            case REGULAR -> {
                player.resetSkills();
                player.getInventory().add(new Item(IRONMAN_HELM, 1), true);
                player.getInventory().add(new Item(IRONMAN_PLATEBODY, 1), true);
                player.getInventory().add(new Item(IRONMAN_PLATELEGS, 1), true);
            }
            case ULTIMATE -> {
                player.resetSkills();
                player.getInventory().add(new Item(ULTIMATE_IRONMAN_HELM, 1), true);
                player.getInventory().add(new Item(ULTIMATE_IRONMAN_PLATEBODY, 1), true);
                player.getInventory().add(new Item(ULTIMATE_IRONMAN_PLATELEGS, 1), true);
            }
            case HARDCORE -> {
                player.getInventory().add(new Item(HARDCORE_IRONMAN_HELM, 1), true);
                player.getInventory().add(new Item(HARDCORE_IRONMAN_PLATEBODY, 1), true);
                player.getInventory().add(new Item(HARDCORE_IRONMAN_PLATELEGS, 1), true);
            }

            case INSTANT_PKER -> {
                /*player.getBank().addAll(BANK_ITEMS);
                System.arraycopy(TAB_AMOUNT, 0, player.getBank().tabAmounts, 0, TAB_AMOUNT.length);
                player.getBank().shift();
                player.message("Your bank has been filled with PvP supplies.");

                for (int skill = 0; skill < 7; skill++) {
                    player.skills().setXp(skill, Skills.levelToXp(99));
                    player.skills().update();
                    player.skills().recalculateCombat();
                }
                player.putAttrib(AttributeKey.COMBAT_MAXED,true);*/
            }
        }

        //Set default spellbook
        player.setSpellbook(MagicSpellbook.NORMAL);
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }

    public boolean confirm(Player player) {
        if (player.getTimers().has(TimerKey.CLICK_DELAY)) {
            return false;
        }

        switch (player.<Integer>getAttribOr(AttributeKey.GAME_MODE_SELECTED, 42405)) {
            case 42402 -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.IRON_MAN);
                }
                gearUp(player, GameMode.REGULAR);
            }
            case 42403 -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.ULTIMATE_IRON_MAN);
                }
                gearUp(player, GameMode.ULTIMATE);
            }
            case 42423 -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.HARDCORE_IRON_MAN);
                }
                gearUp(player, GameMode.HARDCORE);
            }
            case 42431 -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.INSTANT_PKER);
                }
                gearUp(player, GameMode.INSTANT_PKER);
            }
        }
        if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
            player.getPacketSender().sendRights();
        }
        player.unlock();
        player.getInterfaceManager().close();
        player.inventory().addAll(GameConstants.STARTER_ITEMS);
        player.message("You have been given some training equipment.");
        player.putAttrib(AttributeKey.CONTINUE_STARTER_TUTORIAL, true);
        if (player.getAttribOr(AttributeKey.NEW_ACCOUNT, false)) {
            player.getDialogueManager().start(new Tutorial());
        }
        return true;
    }
}

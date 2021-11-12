package com.valinor.game.content.account;

import com.valinor.game.GameConstants;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.Flag;
import com.valinor.game.world.entity.mob.player.*;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.Color;
import com.valinor.util.timers.TimerKey;

import static com.valinor.util.CustomItemIdentifiers.BEGINNER_WEAPON_PACK;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * The class which represents functionality for selecting your account type.
 *
 * @author Patrick van Elderen | 24 sep. 2021 : 19:56:14
 * @see <a href="https://github.com/PVE95/">Github profile</a>
 */
public class AccountSelection extends PacketInteraction {

    public static void open(Player player) {
        player.getInterfaceManager().open(42400);
        refreshOptions(player);
    }

    private static void refreshOptions(Player player) {
        switch (player.<Integer>getAttribOr(AttributeKey.GAME_MODE_SELECTED, 42405)) {
            case 42402 -> {
                player.getPacketSender().sendChangeSprite(42402, (byte) 2);
                player.getPacketSender().sendChangeSprite(42403, (byte) 0);
                player.getPacketSender().sendChangeSprite(42423, (byte) 0);
                player.getPacketSender().sendChangeSprite(42405, (byte) 0);
                player.getPacketSender().sendChangeSprite(42406, (byte) 0);
                player.ironMode(IronMode.REGULAR);
            }
            case 42403 -> {
                player.getPacketSender().sendChangeSprite(42402, (byte) 0);
                player.getPacketSender().sendChangeSprite(42403, (byte) 2);
                player.getPacketSender().sendChangeSprite(42423, (byte) 0);
                player.getPacketSender().sendChangeSprite(42405, (byte) 0);
                player.getPacketSender().sendChangeSprite(42406, (byte) 0);
                player.ironMode(IronMode.HARDCORE);
            }
            case 42423 -> {
                player.getPacketSender().sendChangeSprite(42402, (byte) 0);
                player.getPacketSender().sendChangeSprite(42403, (byte) 0);
                player.getPacketSender().sendChangeSprite(42423, (byte) 2);
                player.getPacketSender().sendChangeSprite(42405, (byte) 0);
                player.getPacketSender().sendChangeSprite(42406, (byte) 0);
                player.ironMode(IronMode.NONE);
            }
            case 42405 -> {
                player.getPacketSender().sendChangeSprite(42402, (byte) 0);
                player.getPacketSender().sendChangeSprite(42403, (byte) 0);
                player.getPacketSender().sendChangeSprite(42423, (byte) 0);
                player.getPacketSender().sendChangeSprite(42405, (byte) 2);
                player.getPacketSender().sendChangeSprite(42406, (byte) 0);
                player.ironMode(IronMode.NONE);
            }
            case 42406 -> {
                player.getPacketSender().sendChangeSprite(42402, (byte) 0);
                player.getPacketSender().sendChangeSprite(42403, (byte) 0);
                player.getPacketSender().sendChangeSprite(42423, (byte) 0);
                player.getPacketSender().sendChangeSprite(42405, (byte) 0);
                player.getPacketSender().sendChangeSprite(42406, (byte) 2);
                player.ironMode(IronMode.REGULAR);
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
                        player.message(Color.RED.wrap("Your levels will be reset if you choose this game mode!"));
                        player.putAttrib(AttributeKey.GAME_MODE_SELECTED,42402);
                    } else if (button == 42403) {
                        player.message(Color.RED.wrap("Your levels will be reset if you choose this game mode!"));
                        player.putAttrib(AttributeKey.GAME_MODE_SELECTED,42403);
                    } else if (button == 42423) {
                        player.putAttrib(AttributeKey.GAME_MODE_SELECTED,42423);
                    } else if (button == 42405) {
                        player.putAttrib(AttributeKey.GAME_MODE_SELECTED,42405);
                    } else if (button == 42406) {
                        player.putAttrib(AttributeKey.GAME_MODE_SELECTED,42406);
                    }
                    player.getTimers().register(TimerKey.CLICK_DELAY,2);
                    refreshOptions(player);
                }
                return true;
            }
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

    private void starter_package(Player player, int type) {
        switch (type) {
            case 0 -> {
                player.resetSkills();
                player.getInventory().add(new Item(IRONMAN_HELM, 1), true);
                player.getInventory().add(new Item(IRONMAN_PLATEBODY, 1), true);
                player.getInventory().add(new Item(IRONMAN_PLATELEGS, 1), true);
                player.getInventory().addAll(GameConstants.STARTER_ITEMS);
                player.message("You have been given some training equipment.");
            }
            case 1 -> {
                player.resetSkills();
                player.getInventory().add(new Item(HARDCORE_IRONMAN_HELM, 1), true);
                player.getInventory().add(new Item(HARDCORE_IRONMAN_PLATEBODY, 1), true);
                player.getInventory().add(new Item(HARDCORE_IRONMAN_PLATELEGS, 1), true);
                player.getInventory().addAll(GameConstants.STARTER_ITEMS);
                player.message("You have been given some training equipment.");
            }
            case 2 -> {
                player.inventory().addOrBank(new Item(BEGINNER_WEAPON_PACK));
                player.getInventory().addAll(GameConstants.STARTER_ITEMS);
                player.message("You have been given some training equipment.");
            }
            case 3 -> {
                player.inventory().addOrBank(new Item(BEGINNER_WEAPON_PACK));
                //Max out combat
                for (int skill = 0; skill < 7; skill++) {
                    player.skills().setXp(skill, Skills.levelToXp(99));
                    player.skills().update();
                    player.skills().recalculateCombat();
                }
            }
            case 4 -> {
                player.getInventory().add(new Item(HARDCORE_IRONMAN_HELM, 1), true);
                player.getInventory().add(new Item(HARDCORE_IRONMAN_PLATEBODY, 1), true);
                player.getInventory().add(new Item(HARDCORE_IRONMAN_PLATELEGS, 1), true);
                player.getInventory().addAll(GameConstants.STARTER_ITEMS);
                player.message("You have been given some training equipment.");
            }
        }

        //Set default spellbook
        player.setSpellbook(MagicSpellbook.NORMAL);
        //Remove tutorial flag.
        player.clearAttrib(AttributeKey.TUTORIAL);
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }

    public boolean confirm(Player player) {
        if (player.getTimers().has(TimerKey.CLICK_DELAY)) {
            return false;
        }

        boolean validButtons = player.<Integer>getAttribOr(AttributeKey.GAME_MODE_SELECTED, 42405) >= 42402 && player.<Integer>getAttribOr(AttributeKey.GAME_MODE_SELECTED, 42405) <= 42406 || player.<Integer>getAttribOr(AttributeKey.GAME_MODE_SELECTED, 42405) == 42423;

        if (!validButtons) {
            player.message("You have yet to select an game mode.");
            return false;
        }

        switch (player.<Integer>getAttribOr(AttributeKey.GAME_MODE_SELECTED, 42405)) {
            case 42402 -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.IRON_MAN);
                }
                starter_package(player, 0);
            }
            case 42403 -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.HARDCORE_IRON_MAN);
                }
                starter_package(player, 1);
            }
            case 42423 -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.PLAYER);
                }
                starter_package(player, 2);
            }
            case 42405 -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.PLAYER);
                }
                starter_package(player, 3);
            }
            case 42406 -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.DARK_LORD);
                }
                starter_package(player, 4);
            }
        }
        if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
            player.getPacketSender().sendRights();
        }
        player.getInterfaceManager().close();
        player.putAttrib(AttributeKey.NEW_ACCOUNT,false);
        player.unlock();
        player.looks().hide(false);
        return true;
    }
}

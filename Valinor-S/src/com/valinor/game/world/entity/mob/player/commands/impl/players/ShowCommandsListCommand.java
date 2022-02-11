package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.GameConstants;
import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class ShowCommandsListCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.message("Opening commands list...");

        List<String> commands = new ArrayList<>();
        commands.add("<br><col=" + Color.MITHRIL.getColorValue() + ">Command [arguments] (optional arguments) - description</col>");
        if(player.gameMode() == GameMode.INSTANT_PKER) {
            commands.add("<br><br><col=" + Color.GREEN.getColorValue() + ">PVP Commands:</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::pots</col> - Spawns a potion set.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::brew</col> - Spawns a saradomin brew.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::restore</col> - Spawns a super restore.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::magicpot</col> - Spawns a magic potion.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::rangepot</col> - Spawns a range potion.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::food</col> - Spawns some sharks.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::barrage</col> - Spawns some barrage runes.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::veng</col> - Spawns some vengeance runes.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::tb</col> - Spawns some teleblock runes.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::runes</col> - Spawns a set of runes.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::fillbank</col> - Refills your bank with the starter items.");
        }
        commands.add("<br><br><col=" + Color.GREEN.getColorValue() + ">Regular Commands:</col>");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::changepassword</col> - Change your password.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::redeem</col> - Redeem all your purchases.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::claimvote</col> - Claim your vote rewards.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::refer</col> - Allows you to fill in your referral.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::yell</col> - Allows you to use the yell channel.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::claimtopvoter</col> - Claims the rewards for being top voter.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::kdr</col> - Display your KDR in chat.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::cp</col> - Teleports you to the Clan Post.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::skull</col> - Gives you a regular skull.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::redskull</col> - Gives you a red skull.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::home</col> - Teleport home.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::leave</col> - Leave the tournament.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::features</col> - Opens the member features.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::vote</col> - Opens the store on the " + GameConstants.SERVER_NAME + " website.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::donate</col> - Opens the store on the " + GameConstants.SERVER_NAME + " website.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::discord</col> - Opens the link to our Discord server.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::rules</col> - Opens the link to our rules.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::staff</col> - Shows you all staff members online.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::players</col> - Displays players online.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::empty</col> - Empties your inventory.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::clearbank</col> - Empties your bank.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::lockexp</col> - Lock and unlock your exp.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::chins</col> - Teleports you to the black chins in wilderness.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::revs</col> - Teleports you to the revs in wilderness.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::mb</col> - Teleports you to the mage bank.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::50s</col> - Teleports you to level 50 wilderness.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::44s</col> - Teleports you to level 44 wilderness.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::graves</col> - Teleports you to the graves in wilderness.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::wests</col> - Teleports you to wests in wilderness.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::easts</col> - Teleports you to easts in wilderness.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::exitraid</col> - Closes your current raid instance.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::toggledyk</col> - Toggle did you know messages.");

        commands.add("<br><br><col=" + Color.GREEN.getColorValue() + ">Member Commands:</col>");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::dzone</col> - Teleports you to the member zone.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::dcave</col> - Teleports you to the member cave.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::unskull</col> - Removes your skull.");
        commands.add("<br><col=" + Color.RED.getColorValue() + ">::pickyellcolour</col> - Picks yell colour.");

        if (player.getPlayerRights().isEventManagerOrGreater(player)) {
            commands.add("<br><br><col=" + Color.GREEN.getColorValue() + ">Event Manager Commands:</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::hpevent [location]</col> - Sets a hp event.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::osrsbroadcast</col> - Sets a broadcast message.");
        }

        if (player.getPlayerRights().isSupportOrGreater(player)) {
            commands.add("<br><br><col=" + Color.GREEN.getColorValue() + ">Support Commands:</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::teleto [username]</col> - Teleports to the specified player.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::mute [username]</col>");
            commands.add("example: <br><col=" + Color.RED.getColorValue() + ">::mute player</col>");
        }

        if (player.getPlayerRights().isModeratorOrGreater(player)) {
            commands.add("<br><br><col=" + Color.GREEN.getColorValue() + ">Moderator Commands:</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::ban [username]</col>");
            commands.add("example: <br><col=" + Color.RED.getColorValue() + ">::ban player</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::unmute [username]</col> - Unmutes the specified player.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::unban [username]</col> - Unbans the specified player.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::teletome [username]</col> - Teleports the specified player to you.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::modzone</col> - Teleports to the mod zone.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::vanish (on or off)</col> - Makes you invisible.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::unvanish (on or off)</col> - Makes you visible.");
        }

        if (player.getPlayerRights().isAdminOrGreater(player)) {
            commands.add("<br><br><col=" + Color.GREEN.getColorValue() + ">Administrator Commands:</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::ipban [username]</col>");
            commands.add("example: <br><col=" + Color.RED.getColorValue() + ">::ipban player</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::kick [username]</col> - Disconnects (kicks) the specified player.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::exit [username]</col> - Closes the client of the specified player.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::resetslayertask [username]</col> - Resets a player's slayer task.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::setslayerstreak [username] [amount]</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::giveslayerpoints [username] [amount]</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::spawnkey</col> - Spawns the wilderness key without timer.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::tele [x] [y] (z)</col> - Teleports to the specified coordinates.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::getip [player]</col> - Displays the IP of the specified player.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::kill (player)</col> - Kills the specified or current player.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::osrsbroadcast</col> - Displays a yellow OSRS message.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::dismissosrsbroadcast</col> - Dismisses the last OSRS message.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::deletepin [username]</col> - Deletes the bank pin for the player.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::copypass [online username] [offline username]</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::changepassother [username]</col>");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::setmember [username] [rights]</col> - rights number 0 to 8.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::promote [username] [rights]</col> - rights number 0 to 5.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::giveitem [username] [id] [amount]</col> - Gives a player an item.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::checkip [IP]</col> - Finds alts based on IP.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::up</col> - Teleports you one height level up.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::down</col> - Teleports you one height level down.");
            commands.add("<br><col=" + Color.RED.getColorValue() + ">::setlevelo</col> - Sets a skill level for other players.");
        }

        player.sendScroll("<col=" + Color.MAROON.getColorValue() + ">Commands List</col>",Collections.singletonList(commands).toString().replaceAll(Pattern.quote("["), "").replaceAll(Pattern.quote("]"), "").replaceAll(",", ""));
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}

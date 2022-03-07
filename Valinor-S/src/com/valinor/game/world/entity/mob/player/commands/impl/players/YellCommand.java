package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.GameServer;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.entity.mob.player.rights.MemberRights;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.util.Color;
import com.valinor.util.Utils;

public class YellCommand implements Command {

    private static int getYellDelay(Player player) {
        int yellTimer = 60;
        MemberRights memberRights = player.getMemberRights();

        switch (memberRights) {
            case SAPPHIRE_MEMBER -> yellTimer = 50;
            case EMERALD_MEMBER -> yellTimer = 40;
            case RUBY_MEMBER -> yellTimer = 30;
            case DIAMOND_MEMBER -> yellTimer = 15;
            case DRAGONSTONE_MEMBER, ONYX_MEMBER, ZENYTE_MEMBER -> yellTimer = 0;
        }
        return player.getPlayerRights().isStaffMember(player) ? 0 : yellTimer;
    }

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (!GameServer.yellEnabled) {
            player.message("The yell channel is currently disabled. Please try again later.");
            return;
        }
        if (player.muted()) {
            player.message("You are muted and cannot yell. Please try again later.");
            return;
        }
        if (player.jailed()) {
            player.message("You are jailed and cannot yell. Please try again later.");
            return;
        }
        int kc = player.getAttribOr(AttributeKey.PLAYER_KILLS, 0);
        if (player.getMemberRights().getRight() < MemberRights.SAPPHIRE_MEMBER.getRight() && player.getPlayerRights() == PlayerRights.PLAYER && kc < 50) {
            player.message("Only Members and players with over 50 kills in the wilderness can yell.");
            return;
        }
        if (player.getYellDelay().active()) {
            int secondsRemaining = player.getYellDelay().secondsRemaining();
            player.message("Please wait " + secondsRemaining + " more seconds before using this yell again...");
            player.message("<col=ca0d0d>Note:<col=0> Abusing yell results in a <col=ca0d0d>permanent<col=0> mute.");
            player.message("<col=255>Note:<col=0> Different types of Membership allow you to yell more often.");
            return;
        }

        String yellMessage = command.substring(5);
        if (yellMessage.length() > 80) {
            yellMessage = yellMessage.substring(0, 79);
        }
        if (Utils.blockedWord(yellMessage)) {
            player.message("<col=ca0d0d>Please refrain from using foul language in the yell chat! Thanks.");
            return;
        }

        sendYell(player, yellMessage);
    }

    public static void sendYell(Player player, String yellMessage) {
        //#Text colour
        String yellColour = player.getAttribOr(AttributeKey.YELL_COLOUR, "006601");

        //#Name colour is based on member rights not player rights
        String nameColour = player.getMemberRights().yellNameColour();

        //#Staff colours can be different
        switch (player.getPlayerRights()) {
            case IRON_MAN -> nameColour = Color.DARKGREY.tag();
            case GOLD_YOUTUBER, SILVER_YOUTUBER, BRONZE_YOUTUBER -> nameColour = Color.GREEN.tag();
            case MODERATOR -> nameColour = Color.WHITE.tag();
            case ADMINISTRATOR, HEAD_ADMIN, HARDCORE_IRON_MAN, EVENT_MANAGER, INSTANT_PKER, OWNER -> nameColour = Color.RED.tag();
            case SUPPORT -> nameColour = Color.SUPPORT_YELL_NAME.tag();
        }

        //# The player icon
        String playerIcon = player.getPlayerRights().getSpriteId() != -1 ? "<img=" + player.getPlayerRights().getSpriteId() + ">" : "";
        String memberIcon = player.getMemberRights().getSpriteId() != -1 ? "<img=" + player.getMemberRights().getSpriteId() + ">" : "";

        //# The username...
        String username = player.getUsername();

        //# The message but formatted proper usages of capitals and such.
        String formatYellMessage = Utils.ucFirst(yellMessage);

        boolean yellColourShad = false;
        if (player.getUsername().equalsIgnoreCase("LOOTATIONS")) {
            yellColour = "01c9f1";
            yellColourShad = true;
        } else if (player.getUsername().equalsIgnoreCase("Bear")) {
            yellColour = "AA336A";
            yellColourShad = true;
        } else if (player.getUsername().equalsIgnoreCase("Skii")) {
            yellColour = "8b0000";
            yellColourShad = true;
        } else if (player.getUsername().equalsIgnoreCase("Dehzyne")) {
            yellColour = "000000";
            yellColourShad = true;
        } else if (player.getUsername().equalsIgnoreCase("Ehwaz")) {
            yellColour = "00ff00";
            yellColourShad = true;
        }

        String tag = player.getAttribOr(AttributeKey.YELL_TAG, "");
        String yellTag = "";
        String yellTagColour = player.getAttribOr(AttributeKey.YELL_TAG_COLOUR, "006601");
        if (tag != null && !tag.isEmpty()) {
            yellTag = "[<shad=0><col=" + yellTagColour + ">" + tag + "</shad></col>]";
        }
        String msg = yellColourShad ? yellTag + "<shad=1>" + nameColour + "[" + playerIcon + "</img>" + memberIcon + "</img>" + username + "]</col></shad>: <shad=0><col=" + yellColour + ">" + formatYellMessage + "</shad>" : yellTag + "<shad=1>" + nameColour + "[" + playerIcon + "</img>" + memberIcon + "</img>" + username + "]</col></shad>: <col=" + yellColour + ">" + formatYellMessage;
        //System.out.println(yellColour);
        World.getWorld().sendWorldMessage(msg);
        int yellDelay = getYellDelay(player);
        if (yellDelay > 0) {
            player.getYellDelay().start(yellDelay);
        }
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}

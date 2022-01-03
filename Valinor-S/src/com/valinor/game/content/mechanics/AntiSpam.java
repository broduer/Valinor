package com.valinor.game.content.mechanics;

import com.valinor.db.DatabaseExtensionsKt;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.impl.kotlin.MiscKotlin;
import com.valinor.game.world.entity.mob.player.commands.impl.staff.moderator.IPMuteCommand;
import com.valinor.util.Utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 01, 2021
 */
public class AntiSpam {

    /**
     * Most commonly used domain name endings.
     */
    private final static String[] flaggedDomainEndings =
        {".com", ".net", ".co.uk", ".io", ".org"};

    /**
     * Websites an advertiser may use to tell players to go on them to type in a specific search term.
     */
    private final static String[] flaggedSearchWebsites =
        {"googl", "youtube", "yt"};

    /**
     * Alterinative synonyms to tell someone to type in something on a website.
     */
    private final static String[] flaggedSearchWords =
        {"search", "type", "look"};

    /**
     * Ip mute a new player for advertising.
     */
    public static boolean isNewPlayerSpamming(Player player, String textSent) {
        if (player.<Integer>getAttribOr(AttributeKey.GAME_TIME,0) < 600) {
            int flagPoint = 0;
            for (int index = 0; index < player.newPlayerChat.size(); index++) {
                String oldText = player.newPlayerChat.get(index).toLowerCase();
                boolean flaggedThisText = false;

                // Give the player +1 flag point player for advertising a website, for example "moparscape.org"
                for (String flaggedDomainText : flaggedDomainEndings) {
                    String flaggedDomainTextWithDot = flaggedDomainText.replace(".", "dot");
                    flaggedDomainTextWithDot = flaggedDomainTextWithDot.replace(".", "dot");

                    String flaggedDomainTextWithDotAndSpace = flaggedDomainText.replace(".", "dot ");
                    if (oldText.contains(flaggedDomainText) || oldText.contains(flaggedDomainTextWithDot) || oldText.contains(flaggedDomainTextWithDotAndSpace)) {
                        flagPoint++;
                        flaggedThisText = true;
                        break;
                    }
                }
                if (flaggedThisText) {
                    continue;
                }

                boolean oldTextHasSearchWebsite = false;
                boolean oldTextHasSearchWordText = false;
                // Give the player +1 flag point player for advertising in a search manor such as "search for autoswitcher on google"
                for (String flaggedSearchWebsiteText : flaggedSearchWebsites) {
                    if (oldText.contains(flaggedSearchWebsiteText)) {
                        oldTextHasSearchWebsite = true;
                        break;
                    }
                }
                for (String flaggedSearchWordText : flaggedSearchWords) {
                    if (oldText.contains(flaggedSearchWordText)) {
                        oldTextHasSearchWordText = true;
                        break;
                    }
                }
                if (oldTextHasSearchWebsite && oldTextHasSearchWordText) {
                    flagPoint++;
                }

            }
            if (flagPoint >= 2) {
                LocalDateTime unmuteDate = LocalDateTime.now().plusYears(Long.parseLong("99"));
                Timestamp unmuteTimestamp = Timestamp.valueOf(unmuteDate);
                DatabaseExtensionsKt.submit(MiscKotlin.INSTANCE.getPlayerDbIdForName(player.getUsername()), id -> {
                    if (id == -1) {
                        player.message("There is no player by the name '"+player.getUsername()+"'");
                    } else {
                        MiscKotlin.INSTANCE.addIPMute(player, player.getUsername(), unmuteTimestamp, "Spamming", IPMuteCommand.feedback(player));
                        Utils.sendDiscordInfoLog(player.getUsername() + " was spamming and got automatically ip muted by the system "+player.getUsername()+" until " + unmuteTimestamp + " for: " + "Spamming", "staff_cmd");
                    }
                });
                return true;
            }
            player.newPlayerChat.add(textSent.toLowerCase());
            if (player.newPlayerChat.size() > 5) {
                player.newPlayerChat.remove(0);
            }
        }
        return false;
    }
}

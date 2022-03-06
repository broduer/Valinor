package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.mechanics.Censor;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.YELL_TAG_SCROLL;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 06, 2022
 */
public class YellTagScrollSyntax implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, @NotNull String input) {
        if(!player.inventory().contains(YELL_TAG_SCROLL)) {
            return;
        }
        List<String> wordsToBlock = Arrays.asList("mod", "moderator", "support", "admin", "administrator");
        boolean illegalWords = wordsToBlock.stream().anyMatch(w -> w.toLowerCase().contains(input));
        boolean illegal = Censor.containsBadWords(input) || Utils.containsMessageFormattingTag(input) || Utils.hasInvalidChars(input) || illegalWords;
        if (input.length() >= 2 && input.length() <= 12 && !illegal) {
            player.putAttrib(AttributeKey.YELL_TAG, input);
            close(player);
            player.setEnterSyntax(new YellTagColourSyntax());
            player.getPacketSender().sendEnterInputPrompt("What colour would you like your tag to be? HTML Color codes: #336eff");
        } else {
            player.message("We were unable to set a yell tag. The tag is either to long or contains illegal words.");
        }
    }
}

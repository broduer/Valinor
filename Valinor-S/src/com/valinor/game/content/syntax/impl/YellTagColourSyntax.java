package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Utils;
import org.jetbrains.annotations.NotNull;

import static com.valinor.util.CustomItemIdentifiers.YELL_TAG_SCROLL;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 06, 2022
 */
public class YellTagColourSyntax implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, @NotNull String input) {
        boolean illegal = Utils.hasInvalidChars(input);
        if (input.length() >= 2 && input.length() <= 12 && !illegal) {
            player.putAttrib(AttributeKey.YELL_TAG_COLOUR, input.replaceAll("#",""));
            String tag = player.getAttribOr(AttributeKey.YELL_TAG, "");
            player.message("Your yell tag is now: [<shad=0><col="+input.replaceAll("#","")+">"+tag+"</shad></col>].");
            player.inventory().remove(YELL_TAG_SCROLL);
        } else {
            player.message("We were unable to set a yell tag. Something went wrong setting the colour.");
        }
    }
}

package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Utils;
import org.jetbrains.annotations.NotNull;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 07, 2022
 */
public class PickYellColour implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, @NotNull String input) {
        boolean illegal = Utils.hasInvalidChars(input);
        if (input.length() >= 2 && input.length() <= 12 && !illegal) {
            player.putAttrib(AttributeKey.YELL_COLOUR, input.replaceAll("#", ""));
            String yellColour = player.getAttribOr(AttributeKey.YELL_COLOUR, "006601");
            player.message("Your yell colour is now: <col="+yellColour+">Yell colour</col>.");
        } else {
            player.message("We were unable to set a yell colour. Something went wrong setting the colour.");
        }
    }
}

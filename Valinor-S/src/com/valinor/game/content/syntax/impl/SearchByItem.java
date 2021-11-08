package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.DropsDisplay;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.mob.player.Player;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * juli 7, 2020
 */
public class SearchByItem implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, String input) {
        DropsDisplay.search(player, input, DropsDisplay.Type.ITEM);
    }

    @Override
    public void handleSyntax(Player player, long input) {

    }
}

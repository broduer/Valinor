package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.mob.player.Player;

public class SearchBank implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, String input) {
        //Bank.search(player, input);
    }

    @Override
    public void handleSyntax(Player player, long input) {

    }

}

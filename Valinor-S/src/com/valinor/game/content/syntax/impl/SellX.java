package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.mob.player.Player;

public class SellX implements EnterSyntax {

    private final int slot, itemId;

    public SellX(int itemId, int slot) {
        this.itemId = itemId;
        this.slot = slot;
    }

    @Override
    public void handleSyntax(Player player, String input) {

    }

    @Override
    public void handleSyntax(Player player, long input) {
       
    }

}

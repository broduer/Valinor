package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.mob.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 02, 2021
 */
public class DepositBoxX implements EnterSyntax {

    private final int item_id;
    private final int slot_id;

    public DepositBoxX(int item_id, int slot_id) {
        this.item_id = item_id;
        this.slot_id = slot_id;
    }

    @Override
    public void handleSyntax(Player player, @NotNull String input) {

    }

    @Override
    public void handleSyntax(Player player, long input) {
        if (item_id < 0 || slot_id < 0 || input <= 0) {
            return;
        }
        if (player.getDepositBox().quantityX) {
            player.getDepositBox().currentQuantityX = (int) input;
        }
        player.getDepositBox().deposit(item_id, (int) input);
    }

}

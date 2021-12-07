package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.mob.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 07, 2021
 */
public class GroupStorageX implements EnterSyntax {

    private final boolean deposit;
    private final int item_id;
    private final int slot_id;

    public GroupStorageX(int item_id, int slot_id, boolean deposit) {
        this.item_id = item_id;
        this.slot_id = slot_id;
        this.deposit = deposit;
    }

    @Override
    public void handleSyntax(Player player, @NotNull String input) {

    }

    @Override
    public void handleSyntax(Player player, long input) {
        if (item_id < 0 || slot_id < 0 || input <= 0) {
            return;
        }
        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
        if (deposit) {
            if(group.isPresent()) {
                group.get().getGroupStorage().deposit(slot_id, (int) input);
            }
        } else {
            if(group.isPresent()) {
                group.get().getGroupStorage().withdraw(item_id, slot_id, (int) input);
            }
        }
    }

}

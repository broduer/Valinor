package com.valinor.game.content.skill.impl.farming.actions;

import com.valinor.game.content.skill.impl.farming.compostbin.CompostBin;
import com.valinor.game.task.impl.PlayerTask;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.incoming_packets.MovementPacketListener;

/**
 * Handles the action of filling the compost bin.
 */
public class CompostBinFillAction extends PlayerTask {

    /**
     * Constructor of compost bin fill action.
     *
     * @param player
     * @param bin
     * @param item_used
     */
    public CompostBinFillAction(Player player, CompostBin bin, int item_used) {
        super(2, player, () -> {

            if (!player.getInventory().contains(item_used) || bin.getInventory().isFull()) {
                player.endCurrentTask();
                return;
            }

            player.getInventory().remove(item_used, 1);
            bin.getInventory().add(item_used, 1);
            player.animate(832);
            player.getFarming().getCompostManager().updateBin(player, bin);

        });

        onEnd(() -> {
            player.animate(65535);
            player.getMovementQueue().reset();
        });

        stopUpon(MovementPacketListener.class);
    }

}

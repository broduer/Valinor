package com.valinor.game.content.skill.impl.farming.actions;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.skill.impl.farming.compostbin.CompostBin;
import com.valinor.game.content.skill.impl.farming.impl.CompostType;
import com.valinor.game.task.impl.PlayerTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.incoming_packets.MovementPacketListener;
import com.valinor.util.ItemIdentifiers;

/**
 * Handles the action of collecting the products from composting.
 *
 * @author Gabriel || Wolfsdarker
 */
public class CompostBinCollectAction extends PlayerTask {

    /**
     * Constructor for compost bin collect action.
     *
     * @param player
     * @param bin
     */
    public CompostBinCollectAction(Player player, CompostBin bin) {
        super(2, player, () -> {

            if (bin.getInventory().isEmpty() || player.getInventory().isFull() ||
                (bin.getCompostType() != CompostType.ROTTEN_TOMATOES && !player.getInventory().contains(ItemIdentifiers.BUCKET))) {
                player.endCurrentTask();
                return;
            }

            player.animate(832);

            bin.getInventory().remove(new Item(bin.getCompostType().getOutcomeItemID()));

            player.getInventory().remove(ItemIdentifiers.BUCKET, 1);
            player.getInventory().add(new Item(bin.getCompostType().getOutcomeItemID()));

            if (bin.getCompostType() == CompostType.ROTTEN_TOMATOES) {
                player.message("You collect a rotten tomato.");
            } else {
                player.message("You fill the bucket with " + World.getWorld().definitions().get(ItemDefinition.class, bin.getCompostType().getOutcomeItemID()).name.toLowerCase() + ".");
            }

            player.getFarming().getCompostManager().updateBin(player, bin);
        });

        onEnd(() -> {
            player.animate(65535);
            player.getMovementQueue().reset();

            if (bin.getInventory().isEmpty()) {
                bin.reset();
            }

        });

        stopUpon(MovementPacketListener.class);
    }
}

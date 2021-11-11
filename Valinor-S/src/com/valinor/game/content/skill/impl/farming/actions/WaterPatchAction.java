package com.valinor.game.content.skill.impl.farming.actions;

import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.content.skill.impl.farming.impl.PatchState;
import com.valinor.game.content.skill.impl.farming.impl.Patches;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.PlayerTask;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.incoming_packets.MovementPacketListener;
import com.valinor.util.ItemIdentifiers;

/**
 * Handles the action of watering the patch.
 *
 * @author Gabriel || Wolfsdarker
 */
public class WaterPatchAction extends PlayerTask {

    /**
     * Constructor for the action.
     *
     * @param player
     * @param state
     * @param item_id
     */
    public WaterPatchAction(Player player, PatchState state, int item_id) {
        super(5, player, () -> {

            state.setWatered(true);

            player.getInventory().remove(item_id, 1);
            player.getInventory().add(item_id == ItemIdentifiers.WATERING_CAN1 ? item_id - 2 : item_id - 1, 1);

            player.getFarming().updatePatches(player);
            player.endCurrentTask();
        });

        onEnd(() -> {
            player.animate(65535);
            player.getMovementQueue().reset();
        });

        stopUpon(MovementPacketListener.class);
    }

    /**
     * Handles the action to water the patch.
     *
     * @param player
     * @param data
     * @param tile
     * @param item_id
     * @return if the patch was found and could be watered
     */
    public static boolean waterPatch(Player player, Patches data, Tile tile, int item_id) {

        PatchState state = player.getFarming().getPatchStates().get(data.name());

        if (state == null) {
            return false;
        }

        if (!FarmingConstants.isWateringCan(item_id)) {
            return false;
        }

        if (!data.getPatchType().isWaterable()) {
            player.message("You can't water this patch.");
            return true;
        }

        if (!state.isUsed()) {
            player.message("You must plant a seed before watering the patch.");
            return false;
        }

        if (state.isWatered()) {
            player.message("This patch is already watered.");
            return true;
        }

        if (FarmingConstants.isFullyGrown(state)) {
            player.message("This patch does not need watering.");
            return true;
        }

        player.face(tile);
        player.animate(FarmingConstants.WATERING_CAN_ANIM);
        TaskManager.submit(new WaterPatchAction(player, state, item_id));
        return true;
    }
}

package com.valinor.game.content.skill.impl.farming.actions;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.content.skill.impl.farming.impl.PatchState;
import com.valinor.game.content.skill.impl.farming.impl.PatchTreatment;
import com.valinor.game.content.skill.impl.farming.impl.Patches;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.PlayerTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.incoming_packets.MovementPacketListener;
import com.valinor.util.ItemIdentifiers;

/**
 * Handles the action to add compost to the patch.
 *
 * @author Gabriel || Wolfsdarker
 */
public class CompostPatchAction extends PlayerTask {

    /**
     * Constructor for the action.
     *
     * @param player
     * @param treatment
     * @param state
     */
    private CompostPatchAction(Player player, PatchTreatment treatment, PatchState state) {
        super(5, player, () -> {

            state.setTreatment(treatment);

            player.getInventory().remove(treatment.getItemId(), 1);
            player.getInventory().add(new Item(ItemIdentifiers.BUCKET));

            player.message("You add " + World.getWorld().definitions().get(ItemDefinition.class, treatment.getItemId()).name.toLowerCase() + " " +
                "to the patch.");
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
     * Handles the action to compost the patch.
     *
     * @param player
     * @param data
     * @param item_id
     * @param tile
     * @return
     */
    public static boolean compostPatch(Player player, Patches data, int item_id, Tile tile) {

        PatchState state = player.getFarming().getPatchStates().get(data.name());

        if (state == null) {
            return false;
        }

        PatchTreatment treatment = PatchTreatment.get(item_id);

        if (treatment == null) {
            return false;
        }

        if (state.getWeedStage() < 3) {
            player.message("You must clear this patch before this.");
            return true;
        }

        if (state.getTreatment() != PatchTreatment.NOT_TREATED) {
            player.message("This patch is already treated.");
            return true;
        }

        if (state.isUsed()) {
            player.message("You can't use the compost after planting the seed.");
            return true;
        }

        player.face(tile);
        player.animate(FarmingConstants.PUTTING_COMPOST);
        TaskManager.submit(new CompostPatchAction(player, treatment, state));
        return true;
    }
}

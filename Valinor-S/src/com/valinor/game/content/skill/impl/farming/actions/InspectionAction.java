package com.valinor.game.content.skill.impl.farming.actions;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.content.skill.impl.farming.impl.DiseaseState;
import com.valinor.game.content.skill.impl.farming.impl.PatchState;
import com.valinor.game.content.skill.impl.farming.impl.PatchTreatment;
import com.valinor.game.task.impl.PlayerTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.incoming_packets.MovementPacketListener;

/**
 * Handles the inspection action.
 *
 * @author Gabriel || Wolfsdarker
 */
public class InspectionAction extends PlayerTask {

    /**
     * Constructor for the action.
     *
     * @param player
     * @param state
     */
    public InspectionAction(Player player, PatchState state) {
        super(1, player, () -> {

            if(state == null) {
                player.endCurrentTask();
                return;
            }

            if (!state.isUsed()) {
                if (state.getWeedStage() < 3) {
                    player.message("The patch needs weeding.");
                } else {
                    player.message("The patch is empty and weeded.");
                }
                player.endCurrentTask();
                return;
            }

            if (state.getTreatment() != PatchTreatment.NOT_TREATED) {
                player.message("The soil has been treated with " + World.getWorld().definitions().get(ItemDefinition.class, state.getTreatment().getItemId())
                    .name.toLowerCase() + ".");
            } else {
                player.message("The soil has not been treated.");
            }

            if (FarmingConstants.isFullyGrown(state)) {
                player.message("The patch is fully grown.");
            } else if (state.getDiseaseState() == DiseaseState.PRESENT) {
                player.message("The patch is diseased and needs attending before it dies.");
            } else if (state.isDead()) {
                player.message("The patch has become infected by disease and died.");
            } else if (state.isWatered()) {
                player.message("The patch is watered and has something growing in it.");
            } else {
                player.message("The patch has something growing in it.");
            }

            player.endCurrentTask();
        });

        onEnd(() -> {
            player.getMovementQueue().reset();
        });

        stopUpon(MovementPacketListener.class);
    }


}

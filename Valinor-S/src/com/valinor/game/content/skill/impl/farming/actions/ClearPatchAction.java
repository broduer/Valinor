package com.valinor.game.content.skill.impl.farming.actions;

import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.content.skill.impl.farming.impl.PatchState;
import com.valinor.game.content.skill.impl.farming.impl.Patches;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.PlayerTask;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;

/**
 * Handles the action to clear the patch.
 *
 * @author Gabriel || Wolfsdarker
 */
public class ClearPatchAction extends PlayerTask {

    /**
     * Constructor for the action.
     *
     * @param player
     * @param state
     */
    public ClearPatchAction(Player player, PatchState state) {
        super(2, player, () -> {

            player.message("You clear the farming patch.");
            state.resetPatch();
            player.getFarming().updatePatches(player);
            player.endCurrentTask();

        });
    }

    /**
     * Handles the action of clearing the patch.
     *
     * @param player
     * @param data
     */
    public static void clearPatch(Player player, Patches data, Tile tile) {

        PatchState state = player.getFarming().getPatchStates().get(data.name());

        if (state == null) {
            return;
        }

        if (state.getWeedStage() < 3) {
            player.message("You must clear the weed with the rake");
            return;
        }

        if (!state.isUsed()) {
            player.message("This patch is already clear.");
            return;
        }

        if (!player.getInventory().contains(FarmingConstants.SPADE)) {
            player.message("You need a spade to clear this patch.");
            return;
        }

        if(!state.isDead()) {
            player.message("You can only clear dead patches.");
            return;
        }

        player.optionsTitled("This will remove all the crops from the patch.", "Yes", "No", () -> {
            player.face(tile);
            player.animate(FarmingConstants.SPADE_ANIM);
            TaskManager.submit(new ClearPatchAction(player, state));
        });
        //TODO "Are you sure you want to do this?"
    }
}

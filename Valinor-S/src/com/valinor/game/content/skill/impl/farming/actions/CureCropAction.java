package com.valinor.game.content.skill.impl.farming.actions;

import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.content.skill.impl.farming.impl.DiseaseState;
import com.valinor.game.content.skill.impl.farming.impl.PatchState;
import com.valinor.game.content.skill.impl.farming.impl.Patches;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.PlayerTask;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.util.ItemIdentifiers;

/**
 * Handles the action to cure the crop.
 *
 * @author Gabriel || Wolfsdarker
 */
public class CureCropAction extends PlayerTask {

    /**
     * Constructor for the action.
     *
     * @param player
     * @param state
     */
    private CureCropAction(Player player, PatchState state) {
        super(3, player, () -> {

            if (player.getInventory().contains(FarmingConstants.ITEM_PLANT_CURE)) {

                state.setDiseaseState(DiseaseState.NOT_PRESENT);

                player.getInventory().remove(FarmingConstants.ITEM_PLANT_CURE);
                player.getInventory().add(new Item(ItemIdentifiers.VIAL));

                player.message("You apply the cure to the patch.");
                player.getFarming().updatePatches(player);
            }

            player.endCurrentTask();
        });
    }

    /**
     * Handles the action to cure the crop.
     *
     * @param player
     * @param data
     * @param tile
     * @param item_id
     * @return
     */
    public static boolean curePlant(Player player, Patches data, Tile tile, int item_id) {

        PatchState state = player.getFarming().getPatchStates().get(data.name());

        if (state == null) {
            return false;
        }

        if (item_id != FarmingConstants.ITEM_PLANT_CURE.getId()) {
            return false;
        }

        if (state.getDiseaseState() != DiseaseState.PRESENT) {
            player.message("This crop is not diseased.");
            return true;
        }

        if (!player.getInventory().contains(FarmingConstants.ITEM_PLANT_CURE)) {
            player.getPacketSender().sendMessage("You need plant cure to cure this patch.");
            return true;
        }

        player.face(tile);
        player.animate(FarmingConstants.CURING_ANIM);
        TaskManager.submit(new CureCropAction(player, state));
        return true;
    }
}

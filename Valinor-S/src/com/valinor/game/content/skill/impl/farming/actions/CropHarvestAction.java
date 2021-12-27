package com.valinor.game.content.skill.impl.farming.actions;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.content.skill.impl.farming.impl.FarmingPatchType;
import com.valinor.game.content.skill.impl.farming.impl.PatchState;
import com.valinor.game.content.skill.impl.farming.impl.Patches;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.PlayerTask;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.incoming_packets.MovementPacketListener;

import static com.valinor.util.ItemIdentifiers.MAGIC_SECATEURS;

/**
 * Handles the action to harvest the crops.
 *
 * @author Gabriel || Wolfsdarker
 */
public class CropHarvestAction extends PlayerTask {

    /**
     * Constructor for the action.
     *
     * @param player
     * @param data
     * @param state
     * @param tile
     */
    private CropHarvestAction(Player player, Patches data, PatchState state, Tile tile) {
        super(4, player, () -> {

            if (data.getPatchType() == FarmingPatchType.ALLOTMENT) {
                if (!player.getInventory().contains(FarmingConstants.SPADE)) {
                    player.endCurrentTask();
                    return;
                }
            }

            if (player.getInventory().isFull()) {
                player.endCurrentTask();
                player.message("You don't have enough space in your inventory to continue this.");
                return;
            }

            if (state.getLivesAmount() <= 0) {
                state.resetPatch();
                player.endCurrentTask();
                player.getFarming().updatePatches(player);
                return;
            }

            player.animate(data.getPatchType().getYieldAnimation());
            player.face(tile);
            int amount = player.getEquipment().hasAt(EquipSlot.WEAPON, MAGIC_SECATEURS) ? 2 : 1;
            player.getInventory().add(new Item(state.getSeed().getProduct(), amount));
            player.skills().addXp(Skills.FARMING, (float)state.getSeed().getExperience() / 10);
            AchievementsManager.activate(player, Achievements.FARMER,1);

            if (FarmingConstants.hasToLoseLife(state, player)) {
                state.setLivesAmount(state.getLivesAmount() - 1);
            }

            player.getFarming().updatePatches(player);
        });

        onEnd(() -> {
            player.animate(65535);
            player.getMovementQueue().reset();
        });

        stopUpon(MovementPacketListener.class);
    }

    /**
     * Handles the action to the crops.
     *
     * @param player
     * @param data
     * @param position
     */
    public static void harvestPatch(Player player, Patches data, Tile position) {

        PatchState state = player.getFarming().getPatchStates().get(data.name());

        if (state.getLivesAmount() <= 0) {
            state.resetPatch();
            player.getFarming().updatePatches(player);
            return;
        }

        if (data.getPatchType() == FarmingPatchType.ALLOTMENT) {
            if (!player.getInventory().contains(FarmingConstants.SPADE)) {
                player.message("You need a spade to do that.");
                return;
            }
        }

        player.message("You begin to harvest the " + data.getPatchType().toString().toLowerCase() + ".");
        TaskManager.submit(new CropHarvestAction(player, data, state, position));
    }
}

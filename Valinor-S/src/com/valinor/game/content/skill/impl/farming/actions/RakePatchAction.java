package com.valinor.game.content.skill.impl.farming.actions;

import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.content.skill.impl.farming.impl.PatchState;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.PlayerTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.incoming_packets.MovementPacketListener;

import static com.valinor.util.ItemIdentifiers.WEEDS;

/**
 * Handles the action of removing weeds from patches.
 *
 * @author Gabriel || Wolfsdarker
 */
public class RakePatchAction extends PlayerTask {

    /**
     * Constructor for the action.
     *
     * @param player
     * @param state
     */
    private RakePatchAction(Player player, PatchState state, Tile tile) {
        super(3, player, () -> {
            player.face(tile);
            player.animate(FarmingConstants.RAKING_ANIM);
            state.setWeedStage(state.getWeedStage() + 1);
            player.getFarming().updatePatches(player);
            player.skills().addXp(Skills.FARMING, 4.0); // Yeah, you get 4 xp... #worth
            player.inventory().add(new Item(WEEDS)); // Free weed :)
            if (state.getWeedStage() >= 3) {
                state.resetLastStageGrowthMoment();
                player.endCurrentTask();
            }
        });

        onEnd(() -> {
            player.animate(65535);
            player.getMovementQueue().reset();
        });

        stopUpon(MovementPacketListener.class);
    }

    /**
     * Returns if the player can rake the patch.
     *
     * @param player
     * @return if it can be done
     */
    public static void rakePatch(Player player, PatchState state, Tile tile) {
        if (!player.getInventory().contains(FarmingConstants.RAKE)) {
            player.message("You need a rake to clear this patch.");
            return;
        }

        // Does this patch need weeding?
        if (state.getWeedStage() > 2) {
            player.message("This patch doesn't need weeding right now.");
            return;
        }

        player.face(tile);
        player.animate(FarmingConstants.RAKING_ANIM);
        TaskManager.submit(new RakePatchAction(player, state, tile));
    }
}

package com.valinor.game.content.skill.impl.farming.actions;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.content.skill.impl.farming.impl.DiseaseState;
import com.valinor.game.content.skill.impl.farming.impl.PatchState;
import com.valinor.game.content.skill.impl.farming.impl.Patches;
import com.valinor.game.content.skill.impl.farming.impl.Seeds;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.PlayerTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.incoming_packets.MovementPacketListener;
import com.valinor.util.Utils;

import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;

/**
 * Handles the action of planting the seed into the patch.
 *
 * @author Gabriel || Wolfsdarker
 */
public class PlantSeedIntoPatchAction extends PlayerTask {

    /**
     * Constructor for the action.
     */
    private PlantSeedIntoPatchAction(Player player, Patches data, PatchState state, Seeds seed_data) {
        super(3, player, () -> {
            player.getInventory().remove(seed_data.getSeedItemId(), 1);
            var aAn = Utils.getAOrAn(new Item(seed_data.getSeedItemId()).name().toLowerCase());
            player.message("You plant "+aAn+" "+World.getWorld().definitions().get(ItemDefinition.class, seed_data.getSeedItemId()).name.toLowerCase()+" in the herb patch.");
            player.skills().addXp(Skills.FARMING, seed_data.getExperience());
            if(seed_data == Seeds.HERB_TORSTOL) {
                player.getTaskBottleManager().increase(BottleTasks.PLANT_TORSTOL_SEED);
            }

            if (World.getWorld().rollDie(75, 1)) {
                GroundItem item = new GroundItem(new Item(TASK_BOTTLE_SKILLING), player.tile(), player);
                GroundItemHandler.createGroundItem(item);
            }

            //Update farming attribs
            state.resetLastStageGrowthMoment();
            state.setStage(seed_data.getMinGrowth());
            state.setDiseaseState(DiseaseState.getImmunity(player, seed_data.getSeedItemId(), data));
            state.setSeed(seed_data);
            player.getFarming().updatePatches(player);//update varbit
            player.endCurrentTask();
        });

        onEnd(() -> {
            player.animate(65535);
            player.getMovementQueue().reset();
        });

        stopUpon(MovementPacketListener.class);
    }

    /**
     * Plants the seed into the patch.
     * @return if it was a valid seed
     */
    public static boolean plantSeed(Player player, Patches data, int seed_id, Tile tile) {

        PatchState state = player.getFarming().getPatchStates().get(data.name());
        Seeds seed = Seeds.get(seed_id);

        // Null seed? Leave.
        if (state == null || seed == null) {
            return false;
        }

        if (data.getPatchType() != seed.getType()) {
            player.message("You can't plant a " + World.getWorld().definitions().get(ItemDefinition.class, seed.getSeedItemId()).name.toLowerCase() + " in this patch.");
            return false;
        }

        if (state.getWeedStage() >= 0 && state.getWeedStage() < 3) {
            player.message("This patch needs weeding first.");
            return false;
        }

        // Is this one already occupied?
        if (state.isUsed()) {
            player.messageBox("You can only plant "+World.getWorld().definitions().get(ItemDefinition.class, seed.getSeedItemId()).name.toLowerCase()+"s in an empty patch.");
            return false;
        }

        if (!player.getInventory().contains(FarmingConstants.SEED_DIBBER)) {
            player.getPacketSender().sendMessage("You need a seed dibber to plant seeds.");
            return true;
        }

        // Are we a real master farmer? :D
        if (seed.getLevelReq() > player.skills().level(Skills.FARMING)) {
            player.message("You must be a Level "+seed.getLevelReq()+" Farmer to plant those.");
            return true;
        }

        // Plant the seeds
        player.face(tile);
        player.animate(FarmingConstants.SEED_DIBBING);
        TaskManager.submit(new PlantSeedIntoPatchAction(player, data, state, seed));
        return true;
    }
}

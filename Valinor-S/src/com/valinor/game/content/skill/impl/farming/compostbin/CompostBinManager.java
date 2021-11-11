package com.valinor.game.content.skill.impl.farming.compostbin;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.content.skill.impl.farming.actions.CompostBinCollectAction;
import com.valinor.game.content.skill.impl.farming.actions.CompostBinFillAction;
import com.valinor.game.content.skill.impl.farming.impl.CompostType;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.util.ItemIdentifiers;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all the player's compost bin.
 */
public class CompostBinManager {

    /**
     * The player's compost bins.
     */
    private List<CompostBin> compost_bins = new ArrayList<>();

    /**
     * Returns the compost bins.
     *
     * @return the bins
     */
    public List<CompostBin> getCompostBins() {
        return compost_bins;
    }

    /**
     * Returns the compost bin by its location.
     *
     * @param tile
     * @return the compost bin.
     */
    public CompostBin get(Tile tile) {
        return compost_bins.stream().filter(bin -> bin.getTile().tile().equals(tile)).findAny().orElse(null);
    }

    /**
     * Attempts to fill the compost bin.
     *
     * @param tile
     * @return if a compost bin and ingredient was found with that position and item
     */
    public boolean fillCompostBin(Player player, int item_used, Tile tile) {

        CompostBin bin = get(tile);

        if (bin == null) {
            return false;
        }

        if ((bin.getCompostType() != null && (bin.getCompostType().getNextState() == null || bin.getInventory().isFull()))) {
            return false;
        }

        if (!CompostType.isValidIngredient(item_used)) {
            player.message("You can't use this item as compost material!");
            return false;
        }

        if (bin.getInventory().isFull()) {
            player.message("This compost bin is already full!");
            return true;
        }

        if (bin.isClosed()) {
            player.message("This compost bin is currently closed!");
            return true;
        }

        player.message("You attempt to fill the compost bin with " + World.getWorld().definitions().get(ItemDefinition.class, item_used).name.toLowerCase() + ".");

        TaskManager.submit(new CompostBinFillAction(player, bin, item_used));
        return true;
    }

    /**
     * Updates the compost bin's object ID.
     *
     * @param bin
     */
    public void updateBin(Player player, CompostBin bin) {

        if (bin == null) {
            player.getPacketSender().sendConfig(FarmingConstants.COMPOST_BIN_CONFIG_ID, 0);
            return;
        }

        if (bin.isClosed()) {
            player.getPacketSender().sendConfig(FarmingConstants.COMPOST_BIN_CONFIG_ID, 70);
            return;
        }

        bin.updateCompostType();

        if (bin.getCompostType() == null) {
            player.getPacketSender().sendConfig(FarmingConstants.COMPOST_BIN_CONFIG_ID, 0);
            return;
        }

        int real_state = bin.getCompostType().getMinConfig() + (bin.getInventory().capacity() - bin.getInventory()
            .getFreeSlots() - 1);

        player.getPacketSender().sendConfig(FarmingConstants.COMPOST_BIN_CONFIG_ID, real_state);
    }

    /**
     * Changes the bin's state.
     *
     * @param tile
     * @return if a bin with that position was found.
     */
    public boolean changeClosedState(Player player, Tile tile) {

        CompostBin bin = get(tile);

        if (bin == null || bin.getCompostType() == null) {
            updateBin(player, bin);
            return false;
        }

        if (bin.canChangeState()) {
            bin.setClosedState(!bin.isClosed());
            if (bin.isClosed() && bin.getCompostType().getNextState() != null) {
                player.message("This bin will produce " + World.getWorld().definitions().get(ItemDefinition.class, bin.getCompostType().getNextState().getOutcomeItemID()).name.toLowerCase() + ".");
            }
        } else {
            player.message("This bin is already full and composting.");
        }

        updateBin(player, bin);

        return true;
    }

    /**
     * Collects the products of composting.
     *
     * @param player
     * @param tile
     * @return if a bin with that position was found.
     */
    public boolean collectProducts(Player player, Tile tile) {

        CompostBin bin = get(tile);

        if (bin == null) {
            return false;
        }

        bin.updateCompostType();

        if (bin.getCompostType() == null || bin.isClosed() || bin.getCompostType().getNextState() !=
            null) {
            return false;
        }

        if (bin.getCompostType() != CompostType.ROTTEN_TOMATOES && !player.getInventory().contains
            (ItemIdentifiers.BUCKET)) {
            player.message("You need a bucket to empty this!");
            return true;
        }

        TaskManager.submit(new CompostBinCollectAction(player, bin));
        return true;
    }

}

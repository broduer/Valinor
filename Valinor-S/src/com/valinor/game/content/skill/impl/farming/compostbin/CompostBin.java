package com.valinor.game.content.skill.impl.farming.compostbin;

import com.valinor.game.content.skill.impl.farming.impl.CompostBinTiles;
import com.valinor.game.content.skill.impl.farming.impl.CompostType;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.impl.CompostContainer;

import static com.valinor.util.Utils.random;

/**
 * Handles the compost bin for farming.
 *
 * @author Gabriel || Wolfsdarker
 */
public class CompostBin {

    /**
     * The compost bin's inventory.
     */
    private final CompostContainer compost_inventory = new CompostContainer();

    /**
     * The time the composting will finish.
     */
    private long composting_end_date = 0L;

    /**
     * If the compost bin is closed.
     */
    private boolean is_closed = false;

    /**
     * The compost bin's position.
     */
    private final CompostBinTiles tile;

    /**
     * The compost type that is being made.
     */
    private CompostType compost_type;

    /**
     * Constructor for compost bins.
     *
     * @param tile
     */
    public CompostBin(CompostBinTiles tile) {
        this.tile = tile;
    }

    /**
     * Returns the compost bin's inventory.
     *
     * @return the inventory
     */
    public CompostContainer getInventory() {
        return compost_inventory;
    }

    /**
     * Returns the time which the composting will end.
     *
     * @return the time
     */
    public long getCompostEndDate() {
        return composting_end_date;
    }

    /**
     * Returns if the compost bin is closed.
     *
     * @return if is closed
     */
    public boolean isClosed() {
        return is_closed;
    }

    /**
     * Returns the compost bin's position.
     *
     * @return the location info
     */
    public CompostBinTiles getTile() {
        return tile;
    }

    /**
     * Returns the compost type that's being processed.
     *
     * @return the type
     */
    public CompostType getCompostType() {
        return compost_type;
    }

    /**
     * Sets if the compost bin is closed or not.
     *
     * @param is_closed
     */
    public void setClosedState(boolean is_closed) {

        this.is_closed = is_closed;

        if (is_closed) {
            this.composting_end_date = System.currentTimeMillis() + (random(15) + 35) * 60_000;
        }

        updateCompostType();
    }

    /**
     * Returns if the bin can change state.
     *
     * @return if its possible
     */
    public boolean canChangeState() {
        return composting_end_date == 0L || System.currentTimeMillis() - composting_end_date >= 0;
    }

    /**
     * Returns if its possible to collect the composting products.
     *
     * @return if its possible
     */
    public boolean canCollectProducts() {
        return System.currentTimeMillis() - composting_end_date >= 0 && composting_end_date > 0;
    }

    /**
     * Updates the bin's compost type.
     */
    public void updateCompostType() {

        if (compost_inventory.isEmpty()) {
            compost_type = null;
            return;
        }

        if (compost_type == null || compost_type.getNextState() != null) {
            compost_type = CompostType.get(compost_inventory);
        }

        if (compost_type.getNextState() == null) {
            return;
        }

        if (canCollectProducts()) {
            compost_type = compost_type.getNextState();
            compost_inventory.clear();
            compost_inventory.add(new Item(compost_type.getOutcomeItemID(), 15));
        }
    }

    /**
     * Resets all the compost bin variables.
     */
    public void reset() {
        this.getInventory().clear();
        this.compost_type = null;
        this.composting_end_date = 0L;
        this.is_closed = false;
    }

}

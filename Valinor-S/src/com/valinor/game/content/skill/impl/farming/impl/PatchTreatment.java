package com.valinor.game.content.skill.impl.farming.impl;

import java.util.Arrays;

/**
 * The treatment types that can be applied to the patches.
 */
public enum PatchTreatment {

    NOT_TREATED(-1, 0),
    COMPOST(6032, 1),
    SUPERCOMPOST(6034, 2),
    ULTRACOMPOST(21483, 3);

    /**
     * The item ID used to treat the patch.
     */
    private int itemId;

    /**
     * The amount of "lives" increased when harvest.
     */
    private int lives_increase;

    PatchTreatment(int itemId, int lives_increase) {
        this.itemId = itemId;
        this.lives_increase = lives_increase;
    }

    /**
     * Returns the item's ID used to treat the patch.
     *
     * @return the item ID
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Returns the amount of "lives" that will be increased when harvest.
     *
     * @return the amount
     */
    public int getLivesIncrease() {
        return lives_increase;
    }

    /**
     * Returns the patch treatment by its required item ID.
     *
     * @param itemId
     * @return the patch treatment
     */
    public static PatchTreatment get(int itemId) {
        return Arrays.stream(values()).filter(t -> t.getItemId() == itemId).findFirst().orElse(null);
    }
}

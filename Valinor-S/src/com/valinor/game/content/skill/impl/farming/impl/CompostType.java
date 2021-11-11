package com.valinor.game.content.skill.impl.farming.impl;

import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.impl.CompostContainer;
import com.valinor.util.ItemIdentifiers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The types of compost that can be made inside the compost bin.
 */
public enum CompostType {

    REGULAR_COMPOST(16, 30, ItemIdentifiers.COMPOST, 4.5, null),

    SUPER_COMPOST(48, 62, ItemIdentifiers.SUPERCOMPOST, 8.5, null),

    ULTRA_COMPOST(0, 0, ItemIdentifiers.ULTRACOMPOST, 12.5, null),

    ROTTEN_TOMATOES(144, 158, ItemIdentifiers.ROTTEN_TOMATO, 8.5, null),

    REGULAR_NOT_COMPOSTED(1, 15, -1, 0.0, CompostType.REGULAR_COMPOST,
        ItemIdentifiers.WEEDS, ItemIdentifiers.GUAM_LEAF, ItemIdentifiers.MARRENTILL, ItemIdentifiers.TARROMIN, ItemIdentifiers.HARRALANDER, ItemIdentifiers.RANARR_WEED,
        ItemIdentifiers.TOADFLAX, ItemIdentifiers.IRIT_LEAF, ItemIdentifiers.AVANTOE, ItemIdentifiers.CADANTINE, ItemIdentifiers.LANTADYME, ItemIdentifiers.TORSTOL,
        ItemIdentifiers.POTATO, ItemIdentifiers.SWEETCORN, ItemIdentifiers.CABBAGE, ItemIdentifiers.ONION, ItemIdentifiers.STRAWBERRY, ItemIdentifiers.REDBERRIES,
        ItemIdentifiers.CADAVA_BERRIES, ItemIdentifiers.DWELLBERRIES),

    SUPER_NOT_COMPOSTED(33, 47, -1, 0.0, CompostType.SUPER_COMPOST,
        ItemIdentifiers.PINEAPPLE, ItemIdentifiers.CALQUAT_FRUIT, ItemIdentifiers.WATERMELON, ItemIdentifiers.OAK_ROOTS, ItemIdentifiers.WILLOW_ROOTS,
        ItemIdentifiers.MAPLE_ROOTS, ItemIdentifiers.YEW_ROOTS, ItemIdentifiers.MAPLE_ROOTS, ItemIdentifiers.SPIRIT_ROOTS, ItemIdentifiers.COCONUT_SHELL,
        ItemIdentifiers.PAPAYA_FRUIT, ItemIdentifiers.JANGERBERRIES, ItemIdentifiers.WHITE_BERRIES, ItemIdentifiers.POISON_IVY_BERRIES, ItemIdentifiers.KWUARM,
        ItemIdentifiers.SNAPDRAGON, ItemIdentifiers.DWARF_WEED, ItemIdentifiers.MUSHROOM),

    REGULAR_TOMATOES(129, 143, -1, 0.0, CompostType.ROTTEN_TOMATOES, ItemIdentifiers.TOMATO);

    /**
     * The minimum amount filled in the compost bin.
     */
    private int min_config;

    /**
     * The maximum amount filled in the compost bin.
     */
    private int max_config;

    /**
     * The item to be collected after the composting process.
     */
    private int outcome_id;

    /**
     * The experience received when collecting the products.
     */
    private double experience;

    /**
     * The product of composting.
     */
    private CompostType next_state;

    /**
     * The ingredients to create the composting.
     */
    private List<Integer> ingredients = new ArrayList<>();

    /**
     * All the ingredients for all compost types.
     */
    private static List<Integer> all_ingredients = new ArrayList<>();

    static {
        for (CompostType type : CompostType.values()) {
            type.getIngredients().stream().forEach(ingredient -> all_ingredients.add(ingredient));
        }
    }

    CompostType(int min_config, int max_config, int outcome_id, double experience, CompostType next_state, int...
        ingredients) {
        this.min_config = min_config;
        this.outcome_id = outcome_id;
        this.max_config = max_config;
        this.next_state = next_state;
        this.experience = experience;
        Arrays.stream(ingredients).forEach(i -> this.ingredients.add(i));
    }

    /**
     * Returns the minimum config state for the compost bin type.
     *
     * @return the config state
     */
    public int getMinConfig() {
        return min_config;
    }

    /**
     * Returns the maximum config state for the compost bin type.
     *
     * @return the config state
     */
    public int getMax_config() {
        return max_config;
    }

    /**
     * Returns the item ID to be collected once the composting process is done.
     *
     * @return the item ID
     */
    public int getOutcomeItemID() {
        return outcome_id;
    }

    /**
     * Returns the experience received when collecting the products.
     *
     * @return the experience
     */
    public double getExperience() {
        return experience;
    }

    /**
     * Returns the next state for the compost bin as a result of composting.
     *
     * @return the next state
     */
    public CompostType getNextState() {
        return next_state;
    }

    /**
     * Returns the array of ItemIdentifiers used to make the compost type.
     *
     * @return the ItemIdentifiers
     */
    public List<Integer> getIngredients() {
        return ingredients;
    }

    /**
     * Checks if the item is valid to be composted.
     *
     * @param item_id
     * @return if is valid
     */
    public static boolean isValidIngredient(int item_id) {
        return all_ingredients.contains(item_id);
    }

    /**
     * Returns the compost type by the bin's inventory.
     *
     * @param inventory
     * @return the type
     */
    public static CompostType get(CompostContainer inventory) {

        CompostType type = CompostType.REGULAR_NOT_COMPOSTED;

        for (CompostType compost : CompostType.values()) {

            for (Item item : inventory.getValidItems()) {
                if (!compost.getIngredients().contains(item.getId())) {
                    type = type == compost ? CompostType.REGULAR_NOT_COMPOSTED : type;
                    break;
                }
                type = compost;
            }
        }
        return type;
    }
}

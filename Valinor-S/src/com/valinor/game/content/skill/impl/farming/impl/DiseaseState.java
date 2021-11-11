package com.valinor.game.content.skill.impl.farming.impl;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.ItemIdentifiers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The states of disease for plants.
 */
public enum DiseaseState {

    NOT_PRESENT,

    PRESENT,

    IMMUNE;


    /**
     * The seeds that are immune to disease when a specific flower is planted near them.
     */
    private static Map<Integer, Integer> flower_immunity = new HashMap<>();

    static {
        flower_immunity.put(ItemIdentifiers.POTATO_SEED, ItemIdentifiers.MARIGOLD_SEED);
        flower_immunity.put(ItemIdentifiers.ONION_SEED, ItemIdentifiers.MARIGOLD_SEED);
        flower_immunity.put(ItemIdentifiers.TOMATO_SEED, ItemIdentifiers.MARIGOLD_SEED);
        flower_immunity.put(ItemIdentifiers.CABBAGE_SEED, ItemIdentifiers.ROSEMARY_SEED);
        flower_immunity.put(ItemIdentifiers.WATERMELON_SEED, ItemIdentifiers.NASTURTIUM_SEED);
    }

    /**
     * Checks if the seed to be planted is immune.
     *
     * @param player
     * @param seed_id
     * @param data
     * @return
     */
    public static DiseaseState getImmunity(Player player, int seed_id, Patches data) {

        Integer value = flower_immunity.get(seed_id);

        if (value == null) {
            return DiseaseState.NOT_PRESENT;
        }

        Patches patch_data = Arrays.stream(Patches.values()).filter(patch -> patch.getPatchType() ==
            FarmingPatchType.FLOWER_PATCH).filter(patch ->
            patch.getAllotmentTile()[0].distance(data.getAllotmentTile()[0]) <= 30).findAny().orElse(null);

        if (patch_data != null) {

            PatchState state = player.getFarming().getPatchStates().get(patch_data.name());

            if (state != null && state.isUsed() && state.getSeed().getSeedItemId() == value.intValue()) {
                return DiseaseState.IMMUNE;
            }
        }

        return DiseaseState.NOT_PRESENT;
    }
}

package com.valinor.game.content.skill.impl.farming.impl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles the gardeners that protect crops for farming skill.
 *
 * @author Gabriel || Wolfsdarker
 */
public enum Gardener {

    ELSTAN(2663, Patches.ALLOTMENT_NORTH_WEST_FALADOR, Patches.ALLOTMENT_SOUTH_EAST_FALADOR),

    LYRA(2666, Patches.ALLOTMENT_NORTH_WEST_CANIFIS, Patches.ALLOTMENT_SOUTH_EAST_CANIFIS),

    KRAGEN(2665, Patches.ALLOTMENT_NORTH_ARDOUGNE, Patches.ALLOTMENT_SOUTH_ARDOUGNE),

    DANTAERA(2664, Patches.ALLOTMENT_NORTH_CAMELOT, Patches.ALLOTMENT_SOUTH_CAMELOT);

    /**
     * The gardener's npc ID.
     */
    private int npc_id;

    /**
     * The list of patches the gardener protect.
     */
    private List<Patches> patches = new ArrayList<>();

    /**
     * List of gardeners avaliable.
     */
    private static final Map<Integer, Gardener> gardener_list = new HashMap<>();

    static {
        Arrays.stream(Gardener.values()).forEach(gardener -> gardener_list.put(gardener.getNpcID(), gardener));
    }

    /**
     * Constructor for gardeners data.
     *
     * @param npc_id
     * @param patches
     */
    Gardener(int npc_id, Patches... patches) {
        this.npc_id = npc_id;
        this.patches.addAll(Arrays.stream(patches).collect(Collectors.toList()));
    }

    /**
     * Returns the gardener's NPC ID.
     *
     * @return the ID
     */
    public int getNpcID() {
        return npc_id;
    }

    /**
     * Returns the patches that can be protected by the gardener.
     *
     * @return the patches
     */
    public List<Patches> getPatches() {
        return patches;
    }

    /**
     * Returns the gardener's info by searching its npc ID.
     *
     * @param npc_id
     * @return the info
     */
    public static Gardener get(int npc_id) {
        return gardener_list.get(npc_id);
    }

    /**
     * Returns the dialogue options for the gardener.
     *
     * @return the options
     */
    public String[] getDialogueOptions() {

        String[] options = new String[patches.size()];

        int index = 0;

        for (Patches patch : patches) {

            String location = patch.name().toLowerCase();

            if (patch.getPatchType() == FarmingPatchType.ALLOTMENT) {
                location = location.substring(location.indexOf("_"), location.lastIndexOf("_")).replaceAll("_", "") + "ern";
            } else {
                location = location.substring(0, location.lastIndexOf("_")).replaceAll("_", " ");
            }

            options[index++] = "The " + location + " " + patch.getPatchType().name().toLowerCase() + ".";
        }

        return options;
    }

}

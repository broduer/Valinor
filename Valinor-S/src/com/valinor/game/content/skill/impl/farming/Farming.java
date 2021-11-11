package com.valinor.game.content.skill.impl.farming;

import com.valinor.game.content.skill.impl.farming.actions.*;
import com.valinor.game.content.skill.impl.farming.compostbin.CompostBin;
import com.valinor.game.content.skill.impl.farming.compostbin.CompostBinManager;
import com.valinor.game.content.skill.impl.farming.impl.*;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.net.BitConfig;
import com.valinor.net.BitConfigBuilder;
import com.valinor.util.ItemIdentifiers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Farming skill main file.
 *
 * @author Gabriel || Wolfsdarker
 */
public class Farming {

    /**
     * The last moment the player logged in or out.
     */
    private long last_log_action;

    /**
     * The manager for all compost bins.
     */
    private final CompostBinManager compost_manager;

    /**
     * The player's crop informations.
     */
    private final Map<String, PatchState> patch_states;

    /**
     * The tools the player has stored inside the leprechaun.
     */
    private final Map<Integer, Integer> tools_stored;

    /**
     * Constructor for the farming instance.
     */
    public Farming() {
        this.compost_manager = new CompostBinManager();
        this.patch_states = new HashMap<>();
        this.tools_stored = new HashMap<>();
    }

    /**
     * Returns the last moment the player logged in or out.
     *
     * @return the moment from epoch day
     */
    public long getLastLogAction() {
        return last_log_action;
    }

    /**
     * Returns the player crop's information.
     *
     * @return the crop information
     */
    public Map<String, PatchState> getPatchStates() {
        return patch_states;
    }

    /**
     * Returns the tools the player has stored in the leprechaun.
     *
     * @return the tools
     */
    public Map<Integer, Integer> getToolStored() {
        return tools_stored;
    }

    /**
     * Returns the compost manager's instance.
     *
     * @return the instance
     */
    public CompostBinManager getCompostManager() {
        return compost_manager;
    }

    /**
     * Sets the last moment the player logged in or out.
     *
     * @param l
     * @return the moment
     */
    public long setLastLogAction(long l) {
        return last_log_action = l;
    }

    /**
     * Resets the last moment the player logged in or out to the current moment.
     *
     * @return the moment
     */
    public long resetLogActionMoment() {
        return last_log_action = System.currentTimeMillis();
    }


    /**
     * Updates all the crop's timers.
     */
    public void updateCropStages(Player player) {
        //System.out.println("Enter updateCropStages");
        patch_states.keySet().forEach(patch_name -> {
            //System.out.println("Enter loop");

            Patches data = Patches.get(patch_name);
            PatchState patch = patch_states.get(patch_name);

            if (data == null || patch == null) {
                System.out.println("data null.");
                return;
            }

            if (patch.isDead()) {
                System.out.println("dead patch.");
                return;
            }

            //System.out.println(System.currentTimeMillis() - last_log_action > (5 * 60_000) && patch.isUsed());
            if (System.currentTimeMillis() - last_log_action > (5 * 60_000) && patch.isUsed()) {
                System.out.println("ticking");
                if (!FarmingConstants.isFullyGrown(patch)) {
                    System.out.println("Not fully grown yet");
                    if (FarmingConstants.inGrowthInterval(patch)) {
                        System.out.println("enter interval");
                        patch.resetLastStageGrowthMoment();
                        if (patch.getProtection() == PatchProtection.NOT_PROTECTED && patch.getDiseaseState() == DiseaseState.NOT_PRESENT &&
                            FarmingConstants.hasToApplyDisease(patch) && patch.getStage() + 1 < patch.getSeed().getMaxGrowth() &&
                            patch.getStage() > patch.getSeed().getMinGrowth() + 1) {
                            patch.setDiseaseState(DiseaseState.PRESENT);
                        } else if (patch.getDiseaseState() == DiseaseState.PRESENT) {
                            patch.setDead(true);
                        } else {
                            System.out.println("Update stage");
                            patch.setStage(patch.getStage() + 1);
                            if (!FarmingConstants.isFullyGrown(patch)) {
                                patch.setWatered(patch.getProtection() != PatchProtection.NOT_PROTECTED);
                            } else {
                                player.message("One or more farming crops have fully grown.");
                            }
                            patch.setLivesAmount(3 + patch.getTreatment().getLivesIncrease());
                        }
                    }
                }
            } else if (!patch.isUsed()) {
                if (patch.getWeedStage() > 0 && (System.currentTimeMillis() - patch.getLastStageChangeMoment() >= 2 * 60_000)) {
                    patch.resetLastStageGrowthMoment();
                    patch.setWeedStage(patch.getWeedStage() - 1);
                }
            }
        });
    }

    /**
     * Updates all the patches for the player.
     */
    public void updatePatches(Player player) {
        //System.out.println("Enter updatePatches");
        final Map<Integer, BitConfigBuilder> configMap = new HashMap<>();
        patch_states.keySet().stream().filter(patch_name -> {
            Patches data = Patches.get(patch_name);

            if (data == null) {
                return false;
            }

            for (Tile tile : data.getAllotmentTile()) {
                if (player.tile().getDistance(tile) <= 56) {
                    //System.out.println("ALLOW "+player.getUsername());
                    return true;
                }
            }
            //System.out.println("FILTER "+player.getUsername());
            return false;
        }).forEach(patch_name -> {
            //System.out.println("Looking for patches");
            Patches data = Patches.get(patch_name);
            PatchState patch = patch_states.get(patch_name);
            BitConfigBuilder config = configMap.getOrDefault(data.getConfigId(), new BitConfigBuilder(data.getConfigId()));

            config.set(patch.getUsedStage(), data.getPatchBitOffset());
            if (patch.isWatered() && !FarmingConstants.isFullyGrown(patch)) {
                if (data.getPatchType() == FarmingPatchType.ALLOTMENT || data.getPatchType() == FarmingPatchType.FLOWER_PATCH) {
                    config.set(1 << data.getPatchType().getStateBitOffset(), data.getPatchBitOffset());
                }
            } else if (patch.isDead()) {
                if (data.getPatchType() == FarmingPatchType.ALLOTMENT || data.getPatchType() == FarmingPatchType.FLOWER_PATCH) {
                    config.set(3 << data.getPatchType().getStateBitOffset(), data.getPatchBitOffset());
                } else if (data.getPatchType() == FarmingPatchType.HERB_PATCH) {
                    config.set(0xAB, data.getPatchBitOffset());
                }
            } else if (patch.getDiseaseState() == DiseaseState.PRESENT) {
                //System.out.println("Patch isn't dead, continue looking");
                if (data.getPatchType() == FarmingPatchType.ALLOTMENT || data.getPatchType() == FarmingPatchType.FLOWER_PATCH) {
                    config.set(2 << data.getPatchType().getStateBitOffset(), data.getPatchBitOffset());
                } else if (data.getPatchType() == FarmingPatchType.HERB_PATCH) {
                    //System.out.println("Found herb patch");
                    config.set(1 << data.getPatchType().getStateBitOffset(), data.getPatchBitOffset());
                }
            }
            configMap.put(data.getConfigId(), config);
        });

        configMap.forEach((key, value) -> {
            final BitConfig config = value.build();
            player.getPacketSender().sendConfigByte(config.getId(), config.getValue());
        });
    }

    /**
     * Handles the player login.
     *
     * @param player
     */
    public static void onLogin(Player player) {
        loadFarming(player);
        player.getFarming().updateCropStages(player);
        player.getFarming().resetLogActionMoment();
        player.getFarming().updatePatches(player);
        TaskManager.submit(new FarmingTask(player));

        for (CompostBin compostBin : player.getFarming().getCompostManager().getCompostBins()) {
            if (player.tile().distance(compostBin.getTile().tile()) <= 56) {
                player.getFarming().getCompostManager().updateBin(player, compostBin);
            }
        }
    }

    /**
     * Returns the player's farming instance after loading (if saved before) the information.
     *
     * @return the instance
     */
    public static void loadFarming(Player player) {

        boolean file_found = FarmingLoading.loadCrops(player.getUsername(), player.getFarming());

        if (!file_found || player.getFarming().getPatchStates().isEmpty()) {
            Arrays.stream(Patches.values()).forEach(data -> player.getFarming().getPatchStates().put(data.name(),
                new PatchState()));
        }

        if (!file_found || player.getFarming().getCompostManager().getCompostBins().isEmpty()) {
            Arrays.stream(CompostBinTiles.values()).forEach(data -> player.getFarming().getCompostManager().getCompostBins()
                .add(new CompostBin(data)));
        }
    }

    /**
     * Handles the region change update.
     *
     * @param player
     */
    public static void onRegionChange(Player player) {
        player.getFarming().updatePatches(player);

        for (CompostBin compostBin : player.getFarming().getCompostManager().getCompostBins()) {
            if (player.tile().distance(compostBin.getTile().tile()) <= 56) {
                player.getFarming().getCompostManager().updateBin(player, compostBin);
            }
        }
    }

    /**
     * Handle the player actions for farming.
     *
     * @param player
     * @param action_type
     * @param tile
     * @param itemId
     * @return
     */
    public static boolean handleActions(Player player, String action_type, Tile tile, int itemId) {

        CompostBinTiles potentialBin = CompostBinTiles.get(tile.getX(), tile.getY());

        final var patchData = potentialBin != null ? null : Patches.get(tile.getX(), tile.getY());
        var patchState = potentialBin == null && patchData != null ? player.getFarming().getPatchStates()
            .get(patchData.name()) : null;

        switch (action_type) {
            case FarmingConstants.ITEM_ON_OBJECT_ACTION -> {
                if (itemId == -1) {
                    return false;
                }
                if (potentialBin != null) {
                    return player.getFarming().getCompostManager().fillCompostBin(player, itemId, potentialBin.tile());
                }
                if (patchData == null) {
                    return false;
                }
                if (PlantSeedIntoPatchAction.plantSeed(player, patchData, itemId, tile)) {
                    return true;
                }
                if (WaterPatchAction.waterPatch(player, patchData, tile, itemId)) {
                    return true;
                }
                if (CompostPatchAction.compostPatch(player, patchData, itemId, tile)) {
                    return true;
                }
                if (CureCropAction.curePlant(player, patchData, tile, itemId)) {
                    return true;
                }
                if (itemId == FarmingConstants.RAKE) {
                    RakePatchAction.rakePatch(player, patchState, tile);
                }
                if (itemId == FarmingConstants.SPADE) {
                    ClearPatchAction.clearPatch(player, patchData, tile);
                }
            }
            case FarmingConstants.FIRST_CLICK_OBJECT -> {
                if (potentialBin != null) {
                    if (!player.getFarming().getCompostManager().collectProducts(player, potentialBin.tile())) {
                        return player.getFarming().getCompostManager().changeClosedState(player, potentialBin.tile());
                    }
                    return true;
                }
                if (patchData == null) {
                    return false;
                }
                if (patchState == null) {
                    return false;
                }
                if (patchState.isUsed() && FarmingConstants.isFullyGrown(patchState)) {
                    CropHarvestAction.harvestPatch(player, patchData, tile);
                } else if (patchState.getDiseaseState() == DiseaseState.PRESENT) {
                    return CureCropAction.curePlant(player, patchData, tile, ItemIdentifiers.PLANT_CURE);
                } else if (patchState.isDead()) {
                    ClearPatchAction.clearPatch(player, patchData, tile);
                } else if (patchState.getWeedStage() < 3) {
                    RakePatchAction.rakePatch(player, patchState, tile);
                } else {
                    TaskManager.submit(new InspectionAction(player, patchState));
                }
                return true;
            }
            case FarmingConstants.SECOND_CLICK_OBJECT -> {
                if (patchData == null) {
                    return false;
                }
                if (patchState == null) {
                    return false;
                }
                TaskManager.submit(new InspectionAction(player, patchState));
                return true;
            }
        }
        return false;
    }

}

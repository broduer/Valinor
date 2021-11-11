package com.valinor.game.content.skill.impl.farming.impl;

/**
 * Handles the state for each patch.
 *
 * @author Gabriel || Wolfsdarker
 */
public class PatchState {

    /**
     * The last moment the plant changed its stage.
     */
    private long last_stage_change_moment;

    /**
     * The plant's grow stage.
     */
    private int stage;

    /**
     * The stage of growth for weeds.
     */
    private int weed_stage;

    /**
     * The amount of lives the patch has before its out of products.
     */
    private int patch_lives;

    /**
     * If the plant is dead.
     */
    private boolean dead;

    /**
     * If the plant is watered.
     */
    private boolean watered;

    /**
     * The plant's disease state.
     */
    private DiseaseState disease_state;

    /**
     * The current treatment applied to the patch.
     */
    private PatchTreatment treatment;

    /**
     * The current protection of the patch.
     */
    private PatchProtection protection;

    /**
     * The seed planted on this patch.
     */
    private int seed_planted;

    /**
     * Constructor for patch states.
     */
    public PatchState() {
        this.seed_planted = -1;
        this.disease_state = DiseaseState.NOT_PRESENT;
        this.treatment = PatchTreatment.NOT_TREATED;
        this.protection = PatchProtection.NOT_PROTECTED;
        this.stage = 0;
    }

    /**
     * Returns the last moment the plant changed stage.
     *
     * @return the moment
     */
    public long getLastStageChangeMoment() {
        return last_stage_change_moment;
    }

    /**
     * Returns the plant's stage.
     *
     * @return the stage
     */
    public int getStage() {
        return stage;
    }

    /**
     * Returns the weed's growth stage.
     *
     * @return the stage
     */
    public int getWeedStage() {
        return weed_stage;
    }

    /**
     * Returns the amount of lives the patch has before it runs out of products.
     *
     * @return the amount
     */
    public int getLivesAmount() {
        return patch_lives;
    }

    /**
     * Returns the stage being used in the patch.
     *
     * @return the stage
     */
    public int getUsedStage() {
        return seed_planted > 0 ? stage : weed_stage;
    }

    /**
     * Returns if the plant is dead.
     *
     * @return if is dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Returns if the plant is watered.
     *
     * @return if is watered
     */
    public boolean isWatered() {
        return watered;
    }

    /**
     * Returns if the patch is being used.
     *
     * @return if is used
     */
    public boolean isUsed() {
        return seed_planted > 0;
    }

    /**
     * Returns the plant's disease state.
     *
     * @return the state
     */
    public DiseaseState getDiseaseState() {
        return disease_state;
    }

    /**
     * Returns the planted seed's data.
     *
     * @return the seed's data
     */
    public Seeds getSeed() {
        return Seeds.get(seed_planted);
    }

    /**
     * Returns the patch's treatment.
     *
     * @return the treatment.
     */
    public PatchTreatment getTreatment() {
        return treatment;
    }

    /**
     * Returns the patch's protection state.
     *
     * @return the protection state
     */
    public PatchProtection getProtection() {
        return protection;
    }

    /**
     * Sets the plant's stage.
     *
     * @param stage
     * @return the stage
     */
    public int setStage(int stage) {
        return this.stage = stage;
    }

    /**
     * Sets the plant's weed stage.
     *
     * @param stage
     * @return the stage
     */
    public int setWeedStage(int stage) {
        return this.weed_stage = stage;
    }

    /**
     * Resets the last stage growth moment to now.
     *
     * @return the moment
     */
    public long resetLastStageGrowthMoment() {
        return this.last_stage_change_moment = System.currentTimeMillis();
    }

    /**
     * Sets the plant's disease state.
     *
     * @param state
     * @return the state
     */
    public DiseaseState setDiseaseState(DiseaseState state) {
        return this.disease_state = state;
    }

    /**
     * Sets if the plant is dead.
     *
     * @param b
     * @return if is dead
     */
    public boolean setDead(boolean b) {
        return this.dead = b;
    }

    /**
     * Sets if the plant is watered.
     *
     * @param b
     * @return if is watered
     */
    public boolean setWatered(boolean b) {
        return this.watered = b;
    }

    /**
     * Sets the patch's live amount.
     *
     * @param amount
     * @return the amount
     */
    public int setLivesAmount(int amount) {
        return this.patch_lives = amount;
    }

    /**
     * Sets the seed that is planted.
     *
     * @param seed
     * @return the seed
     */
    public int setSeed(Seeds seed) {
        return this.seed_planted = seed.getSeedItemId();
    }

    /**
     * Sets the patch's treatment type.
     *
     * @param treatment
     * @return the treatment
     */
    public PatchTreatment setTreatment(PatchTreatment treatment) {
        return this.treatment = treatment;
    }

    /**
     * Sets the patch's protection state.
     *
     * @param protection
     * @return the state
     */
    public PatchProtection setProtection(PatchProtection protection) {
        return this.protection = protection;
    }

    /**
     * Resets the patch when its cleared.
     */
    public void resetPatch() {
        this.weed_stage = 3;
        this.stage = 0;
        this.patch_lives = 0;
        this.seed_planted = -1;
        this.last_stage_change_moment = 0L;
        this.dead = false;
        this.watered = false;
        this.treatment = PatchTreatment.NOT_TREATED;
        this.disease_state = DiseaseState.NOT_PRESENT;
        this.protection = PatchProtection.NOT_PROTECTED;
    }

}

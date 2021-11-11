package com.valinor.game.content.skill.impl.farming.impl;

public enum FarmingPatchType {
    HERB_PATCH(2282, false, true, 6/*7*/),
    ALLOTMENT(831, true, true, 6),
    FLOWER_PATCH(2286, true, true, 6),
    TREE_PATCH(-1, true, true, 6),
    FRUIT_TREE_PATCH(-1, true, true, 0),
    BUSH_PATCH(-1, true, true, 0),
    HOP_PATCH(-1, true, true, 0);

    private int yieldAnimation;
    private boolean waterable;
    private boolean vulnerableToDisease;
    private int stateBitOffset;

    FarmingPatchType(int yieldAnimation, boolean waterable, boolean vulnerableToDisease, int stateBitOffset) {
        this.yieldAnimation = yieldAnimation;
        this.waterable = waterable;
        this.vulnerableToDisease = vulnerableToDisease;
        this.stateBitOffset = stateBitOffset;
    }

    public int getYieldAnimation() {
        return yieldAnimation;
    }

    public boolean isWaterable() {
        return waterable;
    }

    public boolean isVulnerableToDisease() {
        return vulnerableToDisease;
    }

    public int getStateBitOffset() {
        return stateBitOffset;
    }

    @Override
    public String toString() {
        return name().toLowerCase().replaceAll("_", " ");
    }
}

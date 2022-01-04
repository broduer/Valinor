package com.valinor.game.world.entity.mob.player;

/**
 * @author Patrick van Elderen | March, 06, 2021, 14:46
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public enum GameMode {

    INSTANT_PKER("Instant Pker"),
    NONE(""),
    REGULAR("Ironman"),
    ULTIMATE("Ultimate Ironman"),
    HARDCORE("Hardcore Ironman");

    public String name;

    GameMode(String name) {
        this.name = name;
    }

    /**
     * Determines if the {@link #name} is equal to {@link GameMode#INSTANT_PKER}
     * @return {@code true} if the player is of this type, otherwise {@code false}
     */
    public boolean instantPker() {
        return name.equals("Instant Pker");
    }

    /**
     * Determines if the {@link #name} is equal to {@link GameMode#REGULAR} or {@link GameMode#HARDCORE}
     * @return {@code true} if the player is of this type, otherwise {@code false}
     */
    public boolean ironman() {
        return name.equals("Ironman") || name.equals("Hardcore Ironman");
    }

    /**
     * Determines if the {@link #name} is equal to {@link GameMode#REGULAR}
     *
     * @return {@code true} if the player is of this type, otherwise {@code false}
     */
    public boolean isIronman() {
        return name.equals("Ironman");
    }

    /**
     * Determines if the {@link #name} is equal to {@link GameMode#ULTIMATE}
     *
     * @return {@code true} if the player is of this type, otherwise {@code false}
     */
    public boolean isUltimateIronman() {
        return name.equals("Ultimate Ironman");
    }

    /**
     * Determines if the {@link #name} is equal to {@link GameMode#HARDCORE}
     *
     * @return {@code true} if the player is of this type, otherwise {@code false}
     */
    public boolean isHardcoreIronman() {
        return name.equals("Hardcore Ironman");
    }
}

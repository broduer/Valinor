package com.valinor.game.content.gambling;

import com.valinor.game.world.entity.mob.player.Player;

/**
 * The structure of any single game of chance.
 */
public abstract class Gamble {

    /**
     * Players inside the 'gamble'
     */
    public Player playerOne, playerTwo;

    public int gameId;

    public Gamble(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public abstract void gamble();
}

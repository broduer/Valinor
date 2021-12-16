package com.valinor.game.content.gambling;

import com.valinor.game.world.entity.mob.player.Player;

/**
 * The structure of any single game of chance.
 */
public abstract class Gamble {

    /**
     * Players inside the 'gamble'
     */
    public Player host, opponent;

    public int gameId;

    public Gamble(Player host, Player opponent) {
        this.host = host;
        this.opponent = opponent;
    }

    public abstract void gamble();
}

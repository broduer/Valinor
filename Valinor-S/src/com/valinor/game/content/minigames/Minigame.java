package com.valinor.game.content.minigames;

import com.valinor.game.task.Task;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.game.content.minigames.MinigameManager.MinigameType;
import com.valinor.game.content.minigames.MinigameManager.ItemRestriction;
import com.valinor.game.content.minigames.MinigameManager.ItemType;

/**
 * Represents a minigame
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public abstract class Minigame extends Interaction {

    /**
     * Starts the minigame
     *
     * @param player
     *            the player
     */
    public abstract void start(Player player, boolean login);

    /**
     * Gets the task
     *
     * @param player
     *            the player
     * @return the task
     */
    public abstract Task getTask(Player player);

    /**
     * Ends the minigame
     *
     * @param player
     *            the player
     */
    public abstract void end(Player player);

    /**
     * Killing in a minigame
     *
     * @param player
     *            the player
     * @param victim
     *            the victim
     */
    public abstract void killed(Player player, Mob victim);

    /**
     * Checks for requirements
     *
     * @param player
     *            the player
     * @return the requirements
     */
    public abstract boolean hasRequirements(Player player);

    /**
     * The item loss policy
     *
     * @return the policy
     */
    public abstract ItemType getType();

    /**
     * The item restriction policy
     *
     * @return the policy
     */
    public abstract ItemRestriction getRestriction();

    /**
     * Gets the combat type
     *
     * @return the combat type
     */
    public abstract MinigameType getMinigameType();

    /**
     * Returns if the player can teleport out
     *
     * @return if can teleport
     */
    public abstract boolean canTeleportOut();

}

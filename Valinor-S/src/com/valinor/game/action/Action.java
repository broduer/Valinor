package com.valinor.game.action;

import com.valinor.game.action.policy.WalkablePolicy;
import com.valinor.game.task.Task;
import com.valinor.game.world.entity.Mob;

/**
 * Represents an action an entity can execute.
 *
 * @author <a href="http://www.rune-server.org/members/stand+up/">Stand Up</a>
 */
public abstract class Action<T extends Mob> extends Task {

    /** The {@link Mob} associated with this ActionEvent.  */
    private final T mob;

    /**
     * Creates a new {@link Action} randomevent.
     *
     * @param mob     {@link #mob}.
     * @param delay   {@link #delay}.
     * @param instant {@link #insant}.
     */
    public Action(T mob, int delay, boolean instant) {
        super("ActionTask", delay, instant);
        this.mob = mob;
    }

    /**
     * Creates a new {@link Action} randomevent.
     *
     * @param mob   {@link #mob}.
     * @param delay {@link #delay}.
     */
    public Action(T mob, int delay) {
        this(mob, delay,false);
    }

    /**
     * Gets the player.
     *
     * @return The player.
     */
    public T getMob() {
        return mob;
    }

    /**
     * Determines if this action is prioritized. <p>When making an action
     * prioritized, the next action will be ignored if not queued.</p>
     *
     * @return {@code true} if this action is prioritized, {@code false}
     * otherwise.
     */
    public boolean prioritized() {
        return false;
    }

    /**
     * Gets the WalkablePolicy of this action.
     *
     * @return The walkable policy of this action.
     */
    public abstract WalkablePolicy getWalkablePolicy();

    /**
     * Gets the name of this action.
     *
     * @return The name of this action.
     */
    public abstract String getName();

}

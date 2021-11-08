package com.valinor.game.world.entity;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;

/**
 * The enumerated type whose elements represent the different types of
 * node implementations.
 *
 * @author lare96 <http://github.com/lare96>
 */
public enum NodeType {

    /**
     * The element used to represent the {@link Item} implementation.
     */
    ITEM,

    /**
     * The element used to represent the {@link GameObject} implementation.
     */
    OBJECT,

    /**
     * The element used to represent the {@link Player} implementation.
     */
    PLAYER,

    /**
     * The element used to represent the {@link Npc} implementation.
     */
    NPC
}

package com.valinor.game.action.impl;

import com.valinor.game.action.Action;
import com.valinor.game.action.policy.WalkablePolicy;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 21, 2021
 */
public class TeleportAction extends Action<Mob> {

    /**
     * The {@link Tile} that the Entity is being teleported to.
     */
    private final Tile tile;

    public TeleportAction(final Mob mob, final Tile tile) {
        super(mob,0);
        this.tile = tile;
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return null;
    }

    @Override
    public void execute() {
        if (getMob().isPlayer()) {
            final Player player = (Player) getMob();
            if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                Teleports.basicTeleport(player, tile);
            }
        }
    }

    @Override
    public String getName() {
        return "";
    }
}

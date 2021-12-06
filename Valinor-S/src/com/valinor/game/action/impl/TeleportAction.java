package com.valinor.game.action.impl;

import com.valinor.game.action.Action;
import com.valinor.game.action.policy.WalkablePolicy;
import com.valinor.game.content.areas.wilderness.content.boss_event.WildernessBossEvent;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
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

    private final boolean customTile;

    private final String customIdentifier;

    public TeleportAction(final Mob mob, final Tile tile, final boolean customTile, final String customIdentifier) {
        super(mob,0);
        this.tile = tile;
        this.customTile = customTile;
        this.customIdentifier = customIdentifier;
    }

    public TeleportAction(final Mob mob, final Tile tile) {
        super(mob,0);
        this.tile = tile;
        this.customTile = false;
        this.customIdentifier = "";
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return null;
    }

    @Override
    public void execute() {
        if (getMob().isPlayer()) {
            final Player player = (Player) getMob();
            Tile tile = this.tile;
            if(customTile) {
                switch (customIdentifier) {
                    case "World Boss" -> {
                        var worldBossTeleUnlocked = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.WORLD_BOSS_TELEPORT);
                        if (!worldBossTeleUnlocked) {
                            player.message("You do not meet the requirements to use this teleport.");
                            return;
                        }
                        if (WildernessBossEvent.getINSTANCE().getActiveNpc().isPresent() && WildernessBossEvent.currentSpawnPos != null) {
                            tile = WildernessBossEvent.currentSpawnPos;
                        } else {
                            player.message("The world boss recently died and will respawn shortly.");
                        }
                    }
                    case "Revenants" -> {
                        var revsTeleUnlocked = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.REVENANT_TELEPORT);
                        tile = revsTeleUnlocked ? new Tile(3244, 10145, 0) : tile;
                    }
                }
            }

            if(tile == null) {
                player.message("Something went wrong, couldn't find tile to teleport too.");
                return;
            }

            if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                Teleports.basicTeleport(player, tile);
            }
        }
    }

    @Override
    public String getName() {
        return "TeleportAction";
    }
}
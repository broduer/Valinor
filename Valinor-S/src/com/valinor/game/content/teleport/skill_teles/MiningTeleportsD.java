package com.valinor.game.content.teleport.skill_teles;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 02, 2021
 */
public class MiningTeleportsD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Beginner mining", "Intermediate mining", "Amethyst mine", "Nevermind");
        setPhase(0);
    }

    @Override
    protected void select(int option) {
        if (isPhase(0)) {
            if (option == 1) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3181,3371));
                }
            }
            if (option == 2) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3036,9739));
                }
            }
            if (option == 3) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3023,9708));
                }
            }
            if (option == 4) {
                stop();
            }
        }
    }
}

package com.valinor.game.content.teleport.skill_teles;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 03, 2021
 */
public class WoodcuttingTeleportsD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Beginner woodcutting", "Intermediate woodcutting", "Advanced woodcutting", "Nevermind");
        setPhase(0);
    }

    @Override
    protected void select(int option) {
        if (isPhase(0)) {
            if (option == 1) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3136, 3219));
                }
            }
            if (option == 2) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2725,3485));
                }
            }
            if (option == 3) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(1658,3505));
                }
            }
            if (option == 4) {
                stop();
            }
        }
    }
}

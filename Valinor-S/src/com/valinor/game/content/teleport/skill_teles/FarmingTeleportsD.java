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
public class FarmingTeleportsD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Falador herb patch", "Port phasmatys herb patch", "Catherby farm patch", "Ardougne farming patch", "Nevermind");
        setPhase(0);
    }

    @Override
    protected void select(int option) {
        if (isPhase(0)) {
            if (option == 1) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3054,3306));
                }
            }
            if (option == 2) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3602,3527));
                }
            }
            if (option == 3) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2811,3463));
                }
            }
            if (option == 4) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2667,3373));
                }
            }
            if (option == 5) {
                stop();
            }
        }
    }
}

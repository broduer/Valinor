package com.valinor.game.content.teleport.skill_teles;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 02, 2021
 */
public class HunterTeleportsD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Birds", "Chinchompas", "Puro puro", "Nevermind");
        setPhase(0);
    }

    @Override
    protected void select(int option) {
        if (isPhase(0)) {
            if (option == 1) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2609,2898));
                }
            }
            if (option == 2) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Grey chinchompas", "Red chinchompas", "Black chinchompa ("+ Color.RED.wrap("Wilderness!")+")", "Nevermind");
                setPhase(1);
            }
            if (option == 3) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2420,4444));
                }
            }
            if (option == 4) {
                stop();
            }
        } else if (isPhase(0)) {
            if (option == 1) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2338,3582));
                }
            }
            if (option == 2) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2557,2934));
                }
            }
            if (option == 3) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3147, 3712));
                }
            }
            if (option == 4) {
                stop();
            }
        }
    }
}

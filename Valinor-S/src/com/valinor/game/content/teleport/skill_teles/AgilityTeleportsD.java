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
public class AgilityTeleportsD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Agility courses", "Roof-top agility", "Nevermind");
        setPhase(0);
    }

    @Override
    protected void select(int option) {
        if (isPhase(0)) {
            if(option == 1) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Gnome stronghold course", "Barbarian outpost course", "Wilderness course", "Nevermind");
                setPhase(1);
            }
            if(option == 2) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Draynor Village Rooftop Course", "Al Kharid Rooftop Course", "Varrock Rooftop Course", "Canifis Rooftop Course", "More...");
                setPhase(2);
            }
            if(option == 3) {
                stop();
            }
        } else if (isPhase(1)) {
            if(option == 1) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2474,3437));
                }
            }
            if(option == 2) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2552,3561));
                }
            }
            if(option == 3) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2998,3916));
                }
            }
            if(option == 4) {
                stop();
            }
        } else if (isPhase(2)) {
            if(option == 1) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3103,3279));
                }
            }
            if(option == 2) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3273,3197));
                }
            }
            if(option == 3) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3222,3414));
                }
            }
            if(option == 4) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3507,3488));
                }
            }
            if(option == 5) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Falador Rooftop Course", "Seer's Village Rooftop Course", "Rellekka Rooftop Course", "Ardougne Rooftop Course", "Nevermind");
                setPhase(3);
            }
        } else if (isPhase(3)) {
            if(option == 1) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(3036,3340));
                }
            }
            if(option == 2) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2729,3488));
                }
            }
            if(option == 3) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2625,3678));
                }
            }
            if(option == 4) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2673,3297));
                }
            }
            if(option == 5) {
                stop();
            }
        }
    }
}

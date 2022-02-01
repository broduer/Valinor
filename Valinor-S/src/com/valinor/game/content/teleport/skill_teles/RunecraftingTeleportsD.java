package com.valinor.game.content.teleport.skill_teles;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.position.Tile;
import com.valinor.util.timers.TimerKey;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 02, 2021
 */
public class RunecraftingTeleportsD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Essence mine", "Altars", "Nevermind");
        setPhase(0);
    }

    @Override
    protected void select(int option) {
       if(isPhase(0)) {
           if(option == 1) {
               stop();
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(2922,4816));
               }
           }
           if(option == 2) {
               send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Blood altar", "Soul altar", "Nevermind.");
               setPhase(1);
           }
           if(option == 3) {
               stop();
           }
       } else if(isPhase(1)) {
           if(option == 1) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(1732,3828));
               }
           }
           if(option == 2) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(1824,3866));
               }
           }
           if(option == 3) {
              stop();
           }
       }
    }
}

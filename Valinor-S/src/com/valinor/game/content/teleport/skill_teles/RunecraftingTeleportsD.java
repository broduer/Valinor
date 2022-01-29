package com.valinor.game.content.teleport.skill_teles;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;
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
        if(player.getTimers().has(TimerKey.CLICK_DELAY)) {
            return;
        }

        player.getTimers().addOrSet(TimerKey.CLICK_DELAY, 1);
       if(isPhase(0)) {
           if(option == 1) {
               stop();
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(2922,4816));
               }
           }
           if(option == 2) {
               send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Air altar", "Mind altar", "Water altar", "Earth altar", "More...");
               setPhase(1);
           }
           if(option == 3) {
               stop();
           }
       } else if(isPhase(1)) {
           if(option == 1) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(2990,3289));
               }
           }
           if(option == 2) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(2984,3520));
               }
           }
           if(option == 3) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(3175,3167));
               }
           }
           if(option == 4) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(3301,3470));
               }
           }
           if(option == 5) {
               send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Fire altar", "Body altar", "Cosmic altar", "Chaos altar ("+ Color.RED.wrap("Wilderness!")+")", "More...");
               setPhase(2);
           }
       } else if(isPhase(2)) {
           if(option == 1) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(3317,3251));
               }
           }
           if(option == 2) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(3055,3441));
               }
           }
           if(option == 3) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(2405,4381));
               }
           }
           if(option == 4) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(3062,3588));
               }
           }
           if(option == 5) {
               send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Astral altar", "Nature altar", "Law altar", "Death altar", "More...");
               setPhase(3);
           }
       } else if(isPhase(3)) {
           if(option == 1) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(2158,3857));
               }
           }
           if(option == 2) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(2874,3018));
               }
           }
           if(option == 3) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(2857,3377));
               }
           }
           if(option == 4) {
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(1863,4635));
               }
           }
           if(option == 5) {
               send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Blood altar", "Soul altar", "Wrath altar", "Nevermind");
               setPhase(4);
           }
       } else if(isPhase(4)) {
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
               if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                   Teleports.basicTeleport(player, new Tile(2448,2822));
               }
           }
           if(option == 4) {
               stop();
           }
       }
    }
}

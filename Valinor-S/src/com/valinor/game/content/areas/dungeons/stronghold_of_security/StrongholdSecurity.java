package com.valinor.game.content.areas.dungeons.stronghold_of_security;

import com.valinor.game.content.packet_actions.interactions.objects.Ladders;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 23, 2022
 */
public class StrongholdSecurity extends Interaction {

    private void teleportPlayer(Player player, int x, int y) {
        Chain.bound(null).runFn(1, () -> {
            player.lock();
            player.animate(4282);
        }).then(1, () -> {
            player.teleport(x, y);
            player.animate(4283);
        }).then(1, player::unlock);
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            /**
             * Entrance
             */
            if(object.getId() == ENTRANCE_20790) {
                Ladders.ladderDown(player, new Tile(1859, 5243, 0), true);
                DialogueManager.sendStatement(player, "You squeeze through the hole and find a ladder", "a few feet down leading into the Stronghold of Security.");
                return true;
            }
            /**
             * First level
             */
            if(object.getId() == SPIKEY_CHAIN_20782) {
                Ladders.ladderUp(player, new Tile(3081, 3421, 0), true);
                player.message("You climb the ladder to the surface.");
                return true;
            }
            if(object.getId() == LADDER_20784) {
                if(object.tile().equals(1913, 5226, 0)) {
                    Ladders.ladderUp(player, new Tile(3081, 3421, 0), true);
                    player.message("You climb the ladder to the surface.");
                } else if(object.tile().equals(1859, 5244, 0)) {
                    Ladders.ladderUp(player, new Tile(3081, 3421, 0), true);
                    player.message("You climb the ladder to the surface.");
                }
                return true;
            }
            if(object.getId() == PORTAL_20786) {
                player.teleport(1914, 5222, 0);
                player.message("You enter the portal to be whisked through to the treasure room.");
                return true;
            }
            if(object.getId() == LADDER_20785) {
                Ladders.ladderDown(player, new Tile(2042, 5245, 0), true);
                player.message("You climb down the ladder to the next level.");
                return true;
            }
            /**
             * Second level
             */
            if(object.getId() == LADDER_19003) {
                Ladders.ladderUp(player, new Tile(1859, 5243, 0), true);
                player.message("You climb up the ladder to the level above.");
                return true;
            }
            if(object.getId() == ROPE_19001) {
                if(object.tile().equals(2040, 5208, 0)) {
                    Ladders.ladderUp(player, new Tile(1859, 5243, 0), true);
                    player.message("You climb up the ladder to the level above.");
                } else if(object.tile().equals(2031, 5189, 0)) {
                    Ladders.ladderUp(player, new Tile(1859, 5243, 0), true);
                    player.message("You climb up the ladder to the level above.");
                } else if(object.tile().equals(2017, 5210, 0)) {
                    Ladders.ladderUp(player, new Tile(1859, 5243, 0), true);
                    player.message("You climb up the ladder to the level above.");
                } else if(object.tile().equals(2011, 5192, 0)) {
                    Ladders.ladderUp(player, new Tile(1859, 5243, 0), true);
                    player.message("You climb up the ladder to the level above.");
                }
                return true;
            }
            if(object.getId() == PORTAL_19005) {
                player.teleport(2021, 5223, 0);
                player.message("You enter the portal to be whisked through to the treasure room.");
                return true;
            }
            if(object.getId() == LADDER_19004) {
                Ladders.ladderDown(player, new Tile(2123, 5252, 0), true);
                player.message("You climb down the ladder to the next level.");
                return true;
            }
            /**
             * Third level
             */
            if(object.getId() == DRIPPING_VINE) {
                Ladders.ladderUp(player, new Tile(2042, 5245, 0), true);
                player.message("You climb up the ladder to the level above.");
                return true;
            }
            if(object.getId() == GOO_COVERED_VINE) {
                Ladders.ladderUp(player, new Tile(2042, 5245, 0), true);
                player.message("You climb up the ladder to the level above.");
                return true;
            }
            if(object.getId() == PORTAL_23707) {
                player.teleport(2146, 5287, 0);
                player.message("You enter the portal to be whisked through to the treasure room.");
                return true;
            }
            if(object.getId() == DRIPPING_VINE_23706) {
                Ladders.ladderDown(player, new Tile(2358, 5215, 0), true);
                player.message("You climb down the ladder to the next level.");
                return true;
            }
            /**
             * Forth level
             */
            if(object.getId() == BONEY_LADDER) {
                Ladders.ladderUp(player, new Tile(2123, 5252, 0), true);
                player.message("You climb up the ladder to the level above.");
                return true;
            }
            if(object.getId() == PORTAL_23922) {
                player.teleport(2341, 5219, 0);
                player.message("You enter the portal to be whisked through to the treasure room.");
                return true;
            }
            if(object.getId() == FERMENTING_VAT_23932) {
                if(object.tile().equals(2309, 5240, 0)) {
                    Ladders.ladderUp(player, new Tile(2123, 5252, 0), true);
                    player.message("You climb up the ladder to the level above.");
                } else if(object.tile().equals(2350, 5215, 0)) {
                    Ladders.ladderUp(player, new Tile(2123, 5252, 0), true);
                    player.message("You climb up the ladder to the level above.");
                }
                return true;
            }
            /**
             * Double doors
             */
            final int[] DOORS = {GATE_OF_WAR, GATE_OF_WAR_19207, RICKETY_DOOR, RICKETY_DOOR_17100, OOZING_BARRIER, OOZING_BARRIER_23654, PORTAL_OF_DEATH, PORTAL_OF_DEATH_23728};
            for (int door : DOORS) {
                if(object.getId() == door) {
                    boolean atObjX = object.getX() == player.getAbsX();
                    boolean atObjY = object.getY() == player.getAbsY();

                    if (object.getRotation() == 0 && atObjX)
                        teleportPlayer(player, player.getAbsX() - 1, player.getAbsY());
                    else if (object.getRotation() == 1 && atObjY)
                        teleportPlayer(player, object.getX(), object.getY() + 1);
                    else if (object.getRotation() == 2 && atObjX)
                        teleportPlayer(player, object.getX() + 1, object.getY());
                    else if (object.getRotation() == 3 && atObjY)
                        teleportPlayer(player, object.getX(), object.getY() - 1);
                    else
                        teleportPlayer(player, object.getX(), object.getY());
                    return true;
                }
            }
            /**
             * Gifts
             */
            if(object.getId() == 20656 || object.getId() == 19000 || object.getId() == 23731) {
                player.message("You find nothing interesting.");
                return true;
            }
        }
        return false;
    }
}

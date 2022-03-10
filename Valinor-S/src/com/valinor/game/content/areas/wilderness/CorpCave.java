package com.valinor.game.content.areas.wilderness;

import com.valinor.game.content.events.wilderness_key.WildernessKeyPlugin;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.timers.TimerKey;

import static com.valinor.util.ObjectIdentifiers.*;

public class CorpCave extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(option == 1) {
            if(obj.getId() == CAVE_EXIT) {
                // from inside to outside to wildy
                player.teleport(new Tile(3206, 3681));
                return true;
            }

            if(obj.getId() == CAVE) {
                if (obj.tile().equals(3201, 3679)) {
                    if (WildernessKeyPlugin.hasKey(player) && WildernessArea.inWilderness(player.tile())) {
                        player.message("You cannot enter this cave with the Wilderness key.");
                        return true;
                    }
                    //Check to see if the player is teleblocked
                    if (player.getTimers().has(TimerKey.TELEBLOCK) || player.getTimers().has(TimerKey.SPECIAL_TELEBLOCK)) {
                        player.teleblockMessage();
                        return true;
                    }
                    player.teleport(new Tile(2965, 4382, 2));
                }
                return true;
            }


            if(obj.getId() == PASSAGE) {
                // Actual corp entrance
                if (player.tile().x == 2970) {
                    player.teleport(player.tile().transform(4, 0, 0));
                    //TODO corp has a diff walkable interface with DMG string
                    //TODO corp beast damage attrib
                } else if (player.tile().x == 2974) {
                    player.teleport(player.tile().transform(-4, 0, 0));
                    //TODO reset walkable interface and corp dmg
                }
                return true;
            }
        }
        return false;
    }
}

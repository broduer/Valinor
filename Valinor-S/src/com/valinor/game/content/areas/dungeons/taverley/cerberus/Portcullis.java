package com.valinor.game.content.areas.dungeons.taverley.cerberus;

import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ObjectIdentifiers.PORTCULLIS_21772;

public class Portcullis extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(obj.getId() == PORTCULLIS_21772) {
            Tile destination = obj.tile().equals(new Tile(1239, 1225)) ? new Tile(1291, 1253) : //West
                obj.tile().equals(new Tile(1367, 1225)) ? new Tile(1328, 1253) : //East
                    obj.tile().equals(new Tile(1303, 1289)) ? new Tile(1309, 1269) : //North
                        new Tile(0, 0);

            if (option == 1) {
                teleportPlayer(player, destination);
            }
            return true;
        }
        return false;
    }

    private void teleportPlayer(Player player, Tile tile) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, "Do you wish to leave?", "Yes, I'm scared.", "Nah, I'll stay.");
                setPhase(0);
            }
            @Override
            public void select(int option) {
                if (getPhase() == 0) {
                    if(option == 1) {
                        player.teleport(tile);
                    }
                    stop();
                }
            }
        });
    }
}

package com.valinor.game.content.areas.wilderness;

import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.TickAndStop;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ObjectIdentifiers.CAVERN;
import static com.valinor.util.ObjectIdentifiers.CREVICE_26763;

public class Scorpia extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(option == 1) {
            if(obj.getId() == CAVERN) {
                //Cavern
                if (obj.tile().equals(new Tile(3231, 3936))) //South
                    teleportPlayer(player, 3233, 10332);
                else if (obj.tile().equals(new Tile(3231, 3951))) //West
                    teleportPlayer(player, 3232, 10351);
                else if (obj.tile().equals(new Tile(3241, 3949))) //East
                    teleportPlayer(player, 3243, 10351);
                return true;
            }

            if(obj.getId() == CREVICE_26763) {
                if (obj.tile().equals(new Tile(3233, 10331))) //South
                    teleportPlayer(player, 3233, 3938);
                else if (obj.tile().equals(new Tile(3232, 10352))) //West
                    teleportPlayer(player, 3233, 3950);
                else if (obj.tile().equals(new Tile(3243, 10352))) //East
                    teleportPlayer(player, 3242, 3948);
                return true;
            }
        }
        return false;
    }

    private void teleportPlayer(Player player, int x, int y) {
        player.animate(2796);
        TaskManager.submit(new TickAndStop(2) {
            @Override
            public void executeAndStop() {
                player.animate(-1);
                player.teleport(x, y);
            }
        });
    }
}

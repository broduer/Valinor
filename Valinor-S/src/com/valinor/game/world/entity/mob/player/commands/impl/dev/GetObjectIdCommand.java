package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.GameServer;
import com.valinor.fs.ObjectDefinition;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.object.GameObject;
import com.valinor.util.Color;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 13, 2022
 */
public class GetObjectIdCommand implements Command {

    public void execute(Player player, String command, String[] parts) {
        if (parts.length < 2) {
            player.message("Invalid syntax. Please use: ::fo [name]");
            player.message("Example: ::fo ancient barrier");
            return;
        }
        String objectName = command.substring(parts[0].length() + 1);
        if (objectName.length() < 3) {
            player.message("You must give at least 3 letters of input to narrow down the object.");
            return;
        }
        int results = 0;
        player.message("Searching: " + objectName);// used to be ("Searching: " + input)
        for (int j = 0; j < World.getWorld().definitions().total(ObjectDefinition.class); j++) {
            if (results >= 75) {
                player.message("Over 75 results have been found, the maximum number of allowed results. If you cannot");
                player.message("find the object, try and enter more characters to refine the results.");
                return;
            }
            GameObject object = new GameObject(j, null);
            if (object.definition().name != null && !object.definition().name.equalsIgnoreCase("null")) {
                if (object.definition().name.replace("_", " ").toLowerCase().contains(objectName)) {
                    player.message("<col="+ Color.MEDRED.getColorValue()+">" + object.definition().name.replace("_", " ") + " - " + object.getId());
                    results++;
                }
            }
        }
        player.message(results + " results found...");
    }

    @Override
    public boolean canUse(Player player) {
        if(GameServer.properties().test) {
            return true;
        }
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }
}

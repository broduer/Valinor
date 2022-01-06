package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.GameServer;
import com.valinor.fs.ItemDefinition;
import com.valinor.fs.NpcDefinition;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 06, 2022
 */
public class GetNpcIdCommand implements Command {

    public void execute(Player player, String command, String[] parts) {
        if (parts.length < 2) {
            player.message("Invalid syntax. Please use: ::fn [name]");
            player.message("Example: ::fn nex");
            return;
        }
        String npcName = command.substring(parts[0].length() + 1);
        if (npcName.length() < 3) {
            player.message("You must give at least 3 letters of input to narrow down the item.");
            return;
        }
        int results = 0;
        player.message("Searching: " + npcName);// used to be ("Searching: " + input)
        for (int j = 0; j < World.getWorld().definitions().total(NpcDefinition.class); j++) {
            if (results >= 75) {
                player.message("Over 75 results have been found, the maximum number of allowed results. If you cannot");
                player.message("find the item, try and enter more characters to refine the results.");
                return;
            }
            Npc npc = new Npc(j, null);
            if (npc.def().name != null && !npc.def().name.equalsIgnoreCase("null")) {
                if (npc.def().name.replace("_", " ").toLowerCase().contains(npcName)) {
                    player.message("<col="+ Color.MEDRED.getColorValue()+">" + npc.def().name.replace("_", " ") + " - " + npc.id());
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

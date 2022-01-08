package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

import static com.valinor.util.NpcIdentifiers.KALPHITE_QUEEN_6501;

public class SpawnNPCCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        int amt = parts.length > 3 ? Integer.parseInt(parts[3]) : 1;
        for (int i = 0; i < amt; i++) {
            Npc npc = Npc.of(Integer.parseInt(parts[1]), player.tile()).respawns(false);
            World.getWorld().registerNpc(npc);
            npc.combatInfo(World.getWorld().combatInfo(npc.id()));
            player.message("You have just spawned a "+npc.def().name);
            if (parts.length > 2) {
                npc.setHitpoints(Integer.parseInt(parts[2]));
            }
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }

}

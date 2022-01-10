package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.Tile;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 10, 2022
 */
public class ProjectileCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        int projectileId = Integer.parseInt(parts[1]);
        Npc dummy;
        @SuppressWarnings("StreamToLoop") final Optional<Npc> first = player.getLocalNpcs().stream().min(Comparator.comparingInt(c -> c.tile().distance(player.tile())));
        if (first.isEmpty()) {
            dummy = new Npc(100, new Tile(3333, 3333));
        } else {
            dummy = first.get();
        }
        Projectile test = new Projectile(player, dummy, projectileId, 32, 45, 40, 36,0);
        test.sendProjectile();
        if(!dummy.isRegistered()) {
            dummy.spawn();
        }
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isDeveloperOrGreater(player);
    }
}

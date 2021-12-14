package com.valinor.game.content.teleport.royal_seed_pot;

import com.valinor.GameServer;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

public class RoyalSeedPot extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == ItemIdentifiers.ROYAL_SEED_POD) {
                player.stopActions(true);
                if (!Teleports.canTeleport(player, true, TeleportType.ABOVE_20_WILD))
                    return true;
                player.graphic(767);
                player.animate(4544);
                player.lockNoDamage();
                Chain.bound(null).runFn(3, () -> player.looks().transmog(716)).then(1, () -> player.teleport(GameServer.properties().defaultTile)).then(2, () -> player.graphic(769)).then(2, () -> {
                    player.looks().transmog(-1);
                    player.animate(-1);
                    player.getTimers().cancel(TimerKey.FROZEN);
                    player.getTimers().cancel(TimerKey.REFREEZE);
                    player.unlock();
                });
                return true;
            }
        }
        return false;
    }

}

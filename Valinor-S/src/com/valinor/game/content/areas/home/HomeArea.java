package com.valinor.game.content.areas.home;

import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.PacketInteraction;

import static com.valinor.util.NpcIdentifiers.GRAND_EXCHANGE_CLERK;
import static com.valinor.util.NpcIdentifiers.SHOP_ASSISTANT_2820;
import static com.valinor.util.ObjectIdentifiers.TELEPORT_PLATFORM_36062;

/**
 * @author Patrick van Elderen | April, 23, 2021, 10:49
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class HomeArea extends PacketInteraction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if(object.getId() == 31675) {
                player.faceObj(object);
                player.getSlayerKey().open();
                return true;
            }
            if(object.getId() == TELEPORT_PLATFORM_36062) {
                player.getPacketSender().sendString(29078, "World Teleports - Recent");
                player.setCurrentTabIndex(2);
                player.getTeleportInterface().displayRecent();
                player.getInterfaceManager().open(29050);
                return true;
            }
        }
        if(option == 2) {
            
        }
        return false;
    }

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if(npc.id() == GRAND_EXCHANGE_CLERK) {
                TradingPost.open(player);
                return true;
            }
            if(npc.id() == SHOP_ASSISTANT_2820) {
                World.getWorld().shop(1).open(player);
                return true;
            }
        }
        if(option == 2) {

        }
        if(option == 3) {

        }
        if(option == 4) {

        }
        return false;
    }
}

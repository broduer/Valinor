package com.valinor.net.packet.incoming_packets;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.spawn_tab.SpawnTab;
import com.valinor.game.content.syntax.impl.SpawnX;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

/**
 * This packet listener reads a item spawn request
 * from the spawn tab.
 * @author Professor Oak
 */
public class SpawnTabPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        final int item = packet.readInt();
        final boolean spawnX = packet.readByte() == 1;
        final boolean toBank = packet.readByte() == 1;

        if (player == null || player.dead() || player.locked()) {
            return;
        }

        if (player.<Boolean>getAttribOr(AttributeKey.NEW_ACCOUNT,false)) {
            player.message("You have to select your game mode before you can continue.");
            return;
        }

        player.afkTimer.reset();

        ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, item);

        if (def == null) {
            player.message("This item is currently unavailable.");
            return;
        }

        //Check if player busy..
        if (player.busy()) {
            player.message("You cannot do that right now.");
            return;
        }

        if (!player.tile().homeRegion() && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("Spawning items is only allowed at home.");
            return;
        }

        boolean spawnable = def.pvpAllowed;

        if (!spawnable) {
            player.message("This item cannot be spawned.");
            return;
        }

        //Spawn item
        if (!spawnX) {
            SpawnTab.spawn(player, item, 1, toBank);
        } else {
            player.setEnterSyntax(new SpawnX(item, toBank));
            player.getPacketSender().sendEnterAmountPrompt("How many "+def.name+" would you like to spawn?");
        }
    }
}

package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;
import com.valinor.util.Tuple;

import java.lang.ref.WeakReference;

import static com.valinor.game.world.entity.AttributeKey.CONTINUE_STARTER_TUTORIAL;

/**
 * @author PVE
 * @Since augustus 27, 2020
 */
public class AttackNpcPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int index = packet.readShortA();
        if (index < 0 || index > World.getWorld().getNpcs().capacity())
            return;
        final Npc other = World.getWorld().getNpcs().get(index);

        if (other == null) {
            return;
        }

        if (player == null || player.dead()) {
            return;
        }

        player.afkTimer.reset();

        boolean inTutorial = player.<Boolean>getAttribOr(AttributeKey.NEW_ACCOUNT, false) && player.<Boolean>getAttribOr(CONTINUE_STARTER_TUTORIAL, false);
        if (player.busy() || inTutorial) {
            return;
        }

        if (!player.getBankPin().hasEnteredPin() && GameServer.properties().requireBankPinOnLogin) {
            player.getBankPin().openIfNot();
            return;
        }

        if (player.askForAccountPin()) {
            player.sendAccountPinMessage();
            return;
        }

        if (!player.locked() && !player.dead()) {
            player.stopActions(false);

            if (!other.dead()) {
                if (other.combatInfo() == null) {
                    player.message("Without combat attributes this monster is unattackable.");
                    return;
                }

                if (other.cantInteract()) {
                    return;
                }

                // See if it's exclusively owned
                Tuple<Integer, Player> ownerLink = other.getAttribOr(AttributeKey.OWNING_PLAYER, new Tuple<>(-1, null));
                if (ownerLink.first() != null && ownerLink.first() >= 0 && ownerLink.first() != player.getIndex()) {
                    player.message("They don't seem interested in fighting you.");
                    player.getCombat().reset();
                    System.out.println("cmb reset 51088");
                    return;
                }

                other.getMovementQueue().setBlockMovement(true);
                player.putAttrib(AttributeKey.INTERACTION_OPTION, 2);
                player.putAttrib(AttributeKey.TARGET, new WeakReference<Mob>(other));

                player.getCombat().attack(other);
                //CombatFactory.debug(player, "Executed attack in AttackNPC packet", other, true);
                other.getMovementQueue().setBlockMovement(false);
            }
        }
    }

}

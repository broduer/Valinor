package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.route.routes.TargetRoute;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;
import com.valinor.util.Color;

import java.lang.ref.WeakReference;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 14, 2021
 */
public class GroupInviteAccept implements PacketListener {

    @Override
    public void handleMessage(Player invited, Packet packet) throws Exception {
        invited.stopActions(false);
        int index = packet.readUnsignedShort();
        if (index > World.getWorld().getPlayers().capacity() || index < 0) {
            return;
        }
        invited.afkTimer.reset();

        Player requester = World.getWorld().getPlayers().get(index);

        if (invited == null
            || invited.dead()
            || !invited.isRegistered()
            || requester == null || requester.dead()
            || !requester.isRegistered()) {
            return;
        }

        boolean newAccount = invited.getAttribOr(AttributeKey.NEW_ACCOUNT, false);

        if (newAccount) {
            invited.message("You have to select your game mode before you can continue.");
            return;
        }

        if (!invited.getBankPin().hasEnteredPin() && GameServer.properties().requireBankPinOnLogin) {
            invited.getBankPin().openIfNot();
            return;
        }
        if (invited.askForAccountPin()) {
            invited.sendAccountPinMessage();
            return;
        }
        if (invited.dead()) {
            return;
        }
        invited.setEntityInteraction(requester);
        if (!requester.dead()) {
            invited.putAttrib(AttributeKey.TARGET, new WeakReference<Mob>(requester));
            invited.putAttrib(AttributeKey.INTERACTION_OPTION, 4);
            invited.getCombat().reset();
            invited.stopActions(false);
            TargetRoute.set(invited, requester, () -> {
                invited.runFn(1, () -> {
                    invited.face(requester.tile());
                    invited.setEntityInteraction(null);
                });
                if (invited.getMovementQueue().isFollowing(requester)) {
                    invited.getMovementQueue().resetFollowing();
                    invited.setEntityInteraction(null);
                }

                if (invited.getLocalPlayers().contains(requester)) {
                    if (invited.tile().distance(requester.tile()) < 3) {
                        if (IronmanGroupHandler.hasInvitation(invited)) {
                            requester.message(Color.PURPLE.wrap(invited.getUsername()+" has accepted your invitation and joined your group!"));
                            IronmanGroupHandler.acceptInvitation(invited);
                        }
                    }
                }
            });
        }
    }
}

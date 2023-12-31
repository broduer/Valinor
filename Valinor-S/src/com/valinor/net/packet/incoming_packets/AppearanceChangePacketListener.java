package com.valinor.net.packet.incoming_packets;

import com.valinor.game.content.account.Tutorial;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.Flag;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppearanceChangePacketListener implements PacketListener {

    private static final Logger logger = LogManager.getLogger(AppearanceChangePacketListener.class);

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.dead()) {
            return;
        }

        player.afkTimer.reset();

        try {

            final boolean gender = packet.readByte() == 1;
            final int head = packet.readByte();
            final int jaw = packet.readByte();
            final int torso = packet.readByte();
            final int arms = packet.readByte();
            final int hands = packet.readByte();
            final int legs = packet.readByte();
            final int feet = packet.readByte();
            final int hairColor = packet.readByte();
            final int torsoColor = packet.readByte();
            final int legsColor = packet.readByte();
            final int feetColor = packet.readByte();
            final int skinColor = packet.readByte();

            if (skinColor == 10 && !player.getMemberRights().isSapphireMemberOrGreater(player)) {
                player.message("You need to be a Sapphire to use this skin!");
                return;
            }

            if (skinColor == 11 && !player.getMemberRights().isEmeraldMemberOrGreater(player)) {
                player.message("You need to be a Emerald member to use this skin!");
                return;
            }

            if (skinColor == 12 && !player.getMemberRights().isRubyMemberOrGreater(player)) {
                player.message("You need to be a Ruby member to use this skin!");
                return;
            }

            if (skinColor == 13 && !player.getMemberRights().isDiamondMemberOrGreater(player)) {
                player.message("You need to be a Diamond member to use this skin!");
                return;
            }

            if (skinColor == 14 && !player.getMemberRights().isDragonstoneMemberOrGreater(player)) {
                player.message("You need to be a Dragonstone member to use this skin!");
                return;
            }

            if (skinColor == 15 && !player.getMemberRights().isOnyxMemberOrGreater(player)) {
                player.message("You need to be a Onyx member to use this skin!");
                return;
            }

            if (skinColor == 16 && !player.getMemberRights().isZenyteMemberOrGreater(player)) {
                player.message("You need to be a Zenyte member to use this skin!");
                return;
            }

            player.looks().female(gender);
            player.looks().looks(new int[]{head, jaw, torso, arms, hands, legs, feet});
            player.looks().colors(new int[]{hairColor, torsoColor, legsColor, feetColor, skinColor});
            player.getUpdateFlag().flag(Flag.APPEARANCE);
            player.stopActions(true);
            player.getInterfaceManager().close();
            if (player.getAttribOr(AttributeKey.NEW_ACCOUNT, false)) {
                player.getDialogueManager().start(new Tutorial());
            }
        } catch (Exception e) {
            logger.catching(e);
        }
    }

}

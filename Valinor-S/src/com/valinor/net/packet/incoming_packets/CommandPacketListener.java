package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.game.content.clan.ClanManager;
import com.valinor.game.content.duel.Dueling;
import com.valinor.game.content.gambling.GambleState;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.CommandManager;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This packet listener manages commands a player uses by using the command
 * console prompted by using the "`" char.
 *
 * @author Gabriel Hannason
 */
public class CommandPacketListener implements PacketListener {

    private static final Logger logger = LogManager.getLogger(CommandPacketListener.class);

    public static final int OP_CODE = 103;

    @Override
    public void handleMessage(Player player, Packet packet) {
        player.afkTimer.reset();
        String command = packet.readString();
        String[] parts = command.split(" ");
        if (parts.length == 0) // doing ::  with some spaces lol
            return;
        parts[0] = parts[0].toLowerCase();

        if (player.dead()) {
            return;
        }

        boolean newAccount = player.getAttribOr(AttributeKey.NEW_ACCOUNT, false);
        
        if (newAccount) {
            player.message("You have to select your game mode before you can continue.");
            return;
        }
        
        if (!player.getBankPin().hasEnteredPin() && GameServer.properties().requireBankPinOnLogin) {
            player.getBankPin().openIfNot();
            return;
        }

        if(player.askForAccountPin()) {
            player.sendAccountPinMessage();
            return;
        }

        if (player.locked() && !player.getPlayerRights().isDeveloperOrGreater(player)) { // For any player type
            player.message("You cannot do this right now.");
            return;
        }

        if(Dueling.in_duel(player) && !player.getPlayerRights().isStaffMember(player)) { // Only staff can use commands in duel arena
            player.message("You cannot do this right now.");
            return;
        }

        if(player.getGamblingSession().matchActive() || player.getGamblingSession().state() == GambleState.PLACING_BET) {
            player.message("You cannot do this right now.");
            return;
        }

        if (player.jailed() && !player.getPlayerRights().isStaffMember(player)) { // Only staff can use commands in jail
            player.message("You cannot do this right now.");
            return;
        }

        var in_tournament = player.inActiveTournament() || player.isInTournamentLobby();
        if(in_tournament) {
            if((!command.startsWith("yell") && !command.startsWith("/")) && !command.startsWith("exit")) {
                player.message("You cannot do this right now.");
                return;
            }
        }

        if (command.startsWith("/") && command.length() >= 1) {
            ClanManager.message(player, command.substring(1, command.length()));
            return;
        }
        if (command.contains("\r") || command.contains("\n")) {
            return;
        }
        CommandManager.attempt(player, command, parts);
    }
}

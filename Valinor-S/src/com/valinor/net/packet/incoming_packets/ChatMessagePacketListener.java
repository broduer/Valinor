package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.db.transactions.GetMuteStatusDatabaseTransaction;
import com.valinor.game.content.mechanics.AntiSpam;
import com.valinor.game.content.mechanics.Censor;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.masks.chat.ChatMessage;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;
import com.valinor.util.Utils;
import com.valinor.util.flood.Buffer;

/**
 * This packet listener manages the spoken text by a player.
 * 
 * @author Gabriel Hannason
 */
public class ChatMessagePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int size = packet.getSize() - 2;
        int color = packet.readByteS();
        int effect = packet.readByteS();
        byte[] text = packet.readReversedBytesA(size);
        String raw = Utils.textUnpack(text, size);
        String chatMessage = Utils.ucFirst(raw.toLowerCase());

        if (chatMessage.length() <= 0) {
            return;
        }

        if (chatMessage.length() > 80) {
            chatMessage = chatMessage.substring(0, 79);
        }
        
        if (AntiSpam.isNewPlayerSpamming(player, chatMessage)) {
            return;
        }

        boolean newAccount = player.getAttribOr(AttributeKey.NEW_ACCOUNT, false);
        if (newAccount) {
            player.message("You can chat with friends after you have chosen your game mode.");
            return;
        }

        player.afkTimer.reset();
        
        if (!player.getBankPin().hasEnteredPin() && GameServer.properties().requireBankPinOnLogin) {
            player.getBankPin().openIfNot();
            return;
        }

        if(player.askForAccountPin()) {
            player.sendAccountPinMessage();
            return;
        }

        if (chatMessage.toLowerCase().contains("img=") || chatMessage.toLowerCase().contains("col=")) {
            return;
        }

        if (player.isMuted()) {
            player.message("You are muted and cannot chat. Please try again later.");
            return;
        }

        
        if (Utils.blockedWord(chatMessage)) {
            DialogueManager.sendStatement(player, "A word was blocked in your sentence. Please do not repeat it!");
            return;
        }

        if (player.getChatMessageQueue().size() >= 5) {
            player.message("Please do not spam.");
            return;
        }

        player.getChatMessageQueue().add(new ChatMessage(color, effect, text, raw));
        Utils.sendDiscordInfoLog(player.getUsername() + ": " + chatMessage, "chat");
    }

}

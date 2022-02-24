package com.valinor.game.service;

import co.paralleluniverse.strands.Strand;
import com.valinor.game.GameEngine;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.PlayerSession;
import com.valinor.net.codec.PacketDecoder;
import com.valinor.net.codec.PacketEncoder;
import com.valinor.net.login.LoginDetailsMessage;
import com.valinor.net.login.LoginResponses;
import com.valinor.util.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bart on 8/1/2015.
 */
public class LoginWorker implements Runnable {

	private static final Logger logger = LogManager.getLogger(LoginWorker.class);
	
	private final LoginService service;
	
	public LoginWorker(LoginService service) {
		this.service = service;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				processLoginJob();
			} catch (Exception e) {
				logger.error("Error processing login worker job!", e);
			}
		}
	}

	private void processLoginJob() throws Exception {
		LoginRequest request = service.messages().take();

		if (request == null) {
			return;
		} else if (request.delayedUntil() > System.currentTimeMillis()) {
			service.enqueue(request);
			Strand.sleep(30);
			return;
		}

		logger.debug("Attempting to process login request for {}.", request.message.getUsername());

        final Player player = request.player;

        int response = LoginResponses.evaluateAsync(player, request.message);
        if (response != LoginResponses.LOGIN_SUCCESSFUL)
            logger.trace("Login response code for " + player.getUsername() + " is " + response);
        if (response != LoginResponses.LOGIN_SUCCESSFUL) {
            // Load wasn't successful, disconnect with login response.
            sendCodeAndClose(player.getSession().getChannel(), response);
            return;
        }

        // Send the final login response.
        PlayerSession session = player.getSession();
        Channel channel = session.getChannel();
        LoginDetailsMessage message = request.message;
        complete(request, player, channel, message);
	}

    private void sendCodeAndClose(Channel channel, int response) {
        ByteBuf buffer = Unpooled.buffer(Byte.BYTES);
        buffer.writeByte(response);
        channel.writeAndFlush(buffer).addListener(ChannelFutureListener.CLOSE);
    }

    private void initForGame(LoginDetailsMessage message, Channel channel) {
        if (!(channel instanceof EmbeddedChannel)) {
            while (channel.pipeline().last() != null) {
                channel.pipeline().removeLast();
            }

            channel.pipeline().addLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS), new PacketEncoder(message.getEncryptor()), new PacketDecoder(message.getDecryptor()));
        }
    }

    private void complete(LoginRequest request, Player player, Channel channel, LoginDetailsMessage message) {
        GameEngine.getInstance().addSyncTask(() -> {
            int response = LoginResponses.evaluateOnGamethread(player);
            ChannelFuture future = player.getSession().sendOkLogin(response);
            if (response != LoginResponses.LOGIN_SUCCESSFUL) {
                sendCodeAndClose(player.getSession().getChannel(), response);
                logger.trace("Login response 2nd code for " + player.getUsername() + " is " + response);
                // reply is sent either way above, no need here
                return;
            }
            initForGame(message, channel);
            World.getWorld().getPlayers().add(player);
            Utils.sendDiscordInfoLog("```Login successful for player "+request.message.getUsername()+" with IP "+request.message.getHost()+"```", "login");
        });
    }
	
}

package com.valinor.net.channel;

import com.valinor.game.service.LoginWorker;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.NetworkConstants;
import com.valinor.net.PlayerSession;
import com.valinor.net.SessionState;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.SocketException;

/**
 * @author os-scape team
 */
@ChannelHandler.Sharable
public final class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * The logger instance for this class.
     */
    private static final Logger logger = LogManager.getLogger(ServerHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().attr(NetworkConstants.PLAINMSG).setIfAbsent(false);
        super.channelRegistered(ctx);
        logger.trace("A new client has connected: {}", ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);

        LoginHandler.reduceIPConnectedCount(ctx);

        boolean isplain = ctx.channel().attr(NetworkConstants.PLAINMSG).get();
        if (isplain)
            return; // dont print

        logger.trace("A client has disconnected: {}", ctx.channel());

        PlayerSession session = ctx.channel().attr(NetworkConstants.SESSION_KEY).get();

        if (session != null) {
            onUnregisteredIngame(session);
        }
    }

    private void onUnregisteredIngame(PlayerSession session) {
        Player player = session.getPlayer();

        if (player == null) {
            logger.error("channelInactive not possible: "+player);
            return;
        }
        if (player.getUsername() == null || player.getUsername().length() == 0) {
            logger.error("channelInactive wtf: "+player);
            return;
        }
        logger.trace("channelInactive for Player {} state:{}", player, player.getSession().getState());
        if (session.getState() != SessionState.LOGGED_IN) {
            // during login the connection is dropped and sent again a couple times (handshake > update > rsa > login)
            return;
        }
        // trigger logout only when your state is CONNECTED, aka you're ingame
        player.getForcedLogoutTimer().start(60);
        player.putAttrib(AttributeKey.LOGOUT_CLICKED, true);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // oss: read is handled directly in decoder rather than passed to handler
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        try {
            // ignore on traditional socket exception (typically indicated by message starting with read0 but may change..)
            if (cause.getStackTrace().length > 0 && cause.getStackTrace()[0].getMethodName().equals("read0")) {
                return;
            }

            if (cause instanceof SocketException && cause.getStackTrace().length > 0 && cause.getStackTrace()[0].getMethodName().equals("throwConnectionReset")) {
                logger.error("connection reset: "+cause);
                return; // dc
            }

            if (cause instanceof IOException && cause.getStackTrace().length > 0 && cause.getStackTrace()[0].getMethodName().equals("writev0")) {
                logger.error("connection aborted: "+cause);
                return; // dc
            }

            if (cause instanceof ReadTimeoutException) {
                logger.info("Channel disconnected due to read timeout (30s): {}.", ctx.channel());
                ctx.channel().close();
            } else {
                logger.error("An exception has been caused in the pipeline: ", cause);
            }
        } catch (Throwable e) {
            logger.error("Uncaught server exception!", e);
        }

        super.exceptionCaught(ctx, cause);
    }

}

package com.valinor.net.codec;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.region.Buffer;
import com.valinor.net.ByteBufUtils;
import com.valinor.net.NetworkConstants;
import com.valinor.net.PlayerSession;
import com.valinor.net.login.LoginDetailsMessage;
import com.valinor.net.login.LoginResponses;
import com.valinor.net.login.captcha.CaptchaRequirement;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketBuilder;
import com.valinor.net.packet.PacketListener;
import com.valinor.net.security.IsaacRandom;
import com.valinor.util.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import static org.apache.logging.log4j.util.Unbox.box;

/**
 * Attempts to decode a player's login request.
 *
 * @author Professor Oak
 */
public final class LoginDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LogManager.getLogger(LoginDecoder.class);

    /**
     * Generates random numbers via secure cryptography. Generates the session key
     * for packet encryption.
     */
    private static final Random random = new SecureRandom();

    /**
     * The size of the encrypted data.
     */
    private int encryptedLoginBlockSize;

    /**
     * The current login decoder state
     */
    private LoginDecoderState state = LoginDecoderState.LOGIN_REQUEST;

    /**
     * Sends a response code to the client to notify the user logging in.
     *
     * @param ctx The context of the channel handler.
     * @param response The response code to send.
     */
    public static void sendCodeAndClose(ChannelHandlerContext ctx, int response) {
        ByteBuf buffer = Unpooled.buffer(Byte.BYTES);
        buffer.writeByte(response);
        ctx.writeAndFlush(buffer).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        switch (state) {

            case LOGIN_REQUEST:
                decodeRequest(ctx, buffer);
                break;

            case LOGIN_TYPE_AND_SIZE:
                decodeTypeAndSize(ctx, buffer);
                break;

            case LOGIN:
                decodeLogin(ctx, buffer, out);
                break;
        }
    }

    private final Logger log = LogManager.getLogger("timeouts");

    private void decodeRequest(ChannelHandlerContext ctx, ByteBuf buffer) {
        if (!buffer.isReadable()) {
            return;
        }

        int request = buffer.readUnsignedByte();
        if (request != NetworkConstants.LOGIN_REQUEST_OPCODE && request != 69) {
            logger.error("Session rejected for bad login request id: {} for IP: {}", box(request), ctx.channel().remoteAddress());
            sendCodeAndClose(ctx, LoginResponses.LOGIN_BAD_SESSION_ID);
            return;
        }
        if (request == 69) {
            ctx.channel().attr(NetworkConstants.PLAINMSG).setIfAbsent(true);
            int size = buffer.readByte();
            if (buffer.isReadable(size)) {
                byte[] data = new byte[size];
                buffer.readBytes(data);
                final Packet packet = new Packet(69, Unpooled.copiedBuffer(data));
                final String text = packet.readString();
                log.info("host {} report: {}", ctx.channel().remoteAddress(), text);
            }
            ctx.close();
            return;
        }

        // Send information to the client
        ByteBuf buf = Unpooled.buffer(Byte.BYTES + Long.BYTES);
        buf.writeByte(0); // 0 = continue login
        buf.writeLong(random.nextLong()); // This long will be used for
        // encryption later on
        ctx.writeAndFlush(buf);

        state = LoginDecoderState.LOGIN_TYPE_AND_SIZE;
    }

    private void decodeTypeAndSize(ChannelHandlerContext ctx, ByteBuf buffer) {
        if (!buffer.isReadable(2)) {
            return;
        }

        int connectionType = buffer.readUnsignedByte();
        if (connectionType != NetworkConstants.NEW_CONNECTION_OPCODE
            && connectionType != NetworkConstants.RECONNECTION_OPCODE) {
            logger.error("Session rejected for bad connection type id: {}", box(connectionType));
            sendCodeAndClose(ctx, LoginResponses.LOGIN_BAD_SESSION_ID);
            return;
        }

        encryptedLoginBlockSize = buffer.readUnsignedByte();

        state = LoginDecoderState.LOGIN;
    }

    private void decodeLogin(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        if (!buffer.isReadable(encryptedLoginBlockSize)) {
            return;
        }

        // obviously adjust the indentation below:
        int magicId = buffer.readUnsignedByte();
        if (magicId != 0xFF) {
            logger.error(String.format("[host= %s] [magic= %d] was rejected for the wrong magic value.", ctx.channel().remoteAddress(), magicId));
            sendCodeAndClose(ctx, LoginResponses.LOGIN_REJECT_SESSION);
            return;
        }

        int memory = buffer.readByte();
        if (memory != 0 && memory != 1) {
            logger.error("[host={}] was rejected for having the memory setting.", ctx.channel().remoteAddress());
            sendCodeAndClose(ctx, LoginResponses.LOGIN_REJECT_SESSION);
            return;
        }

        /*
         * int[] archiveCrcs = new int[9]; for (int i = 0; i < 9; i++) { archiveCrcs[i]
         * = buffer.readInt(); }
         */

        /**
         * Our RSA components.
         */
        int length = buffer.readUnsignedByte();
        byte[] rsaBytes = new byte[length];
        buffer.readBytes(rsaBytes);

        ByteBuf rsaBuffer = Unpooled.wrappedBuffer(new BigInteger(rsaBytes).modPow(NetworkConstants.RSA_EXPONENT, NetworkConstants.RSA_MODULUS).toByteArray());

        int securityId = rsaBuffer.readByte();
        if (securityId != 10) {
            //logger.error("[host={}] was rejected for having the wrong securityId {}.", ctx.channel().remoteAddress(), securityId);
            sendCodeAndClose(ctx, LoginResponses.LOGIN_REJECT_SESSION);
            return;
        }

        long clientSeed = rsaBuffer.readLong();
        long seedReceived = rsaBuffer.readLong();

        int[] seed = {(int) (clientSeed >> 32), (int) clientSeed, (int) (seedReceived >> 32), (int) seedReceived};
        IsaacRandom decodingRandom = new IsaacRandom(seed);
        for (int i = 0; i < seed.length; i++) {
            seed[i] += 50;
        }

        String uid = ByteBufUtils.readString(rsaBuffer);
        String username = Utils.formatText(ByteBufUtils.readString(rsaBuffer));
        String password = ByteBufUtils.readString(rsaBuffer);
        String mac = ByteBufUtils.readString(rsaBuffer);

        if (username.length() < 1 || username.length() > 12 || password.length() < 3 || password.length() > 20) {
            sendCodeAndClose(ctx, LoginResponses.INVALID_CREDENTIALS_COMBINATION);
            return;
        }

        out.add(new LoginDetailsMessage(ctx, username, password, ByteBufUtils.getHost(ctx.channel()), mac, uid, new IsaacRandom(seed), decodingRandom));
    }

    private enum LoginDecoderState {
        LOGIN_REQUEST, LOGIN_TYPE_AND_SIZE, LOGIN;
    }
}

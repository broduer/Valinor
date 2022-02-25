package com.valinor.net.channel;

import com.valinor.GameServer;
import com.valinor.net.codec.LoginDecoder;
import com.valinor.net.codec.LoginEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;

import java.util.concurrent.Executors;

/**
 * Created by Bart on 8/4/2014.
 * @author @author os-scape team
 */
public class ClientInitializer extends ChannelInitializer<Channel> {

    /**
     * The part of the pipeline that handles exceptions caught, channels being read, in-active
     * channels, and channel triggered events.
     */
    public final ServerHandler handler = new ServerHandler();
    private final LoginHandler LOGIN_HANDLER = new LoginHandler();
    private final GlobalTrafficShapingHandler trafficHandler = new GlobalTrafficShapingHandler(Executors.newSingleThreadScheduledExecutor(), 0, 0, 1000);

    @Override
    protected void initChannel(Channel channel) throws Exception {
        final ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("traffic", trafficHandler);
        pipeline.addLast("decoder", new LoginDecoder());
        pipeline.addLast("encoder", new LoginEncoder());
        pipeline.addLast("login-handler", LOGIN_HANDLER);
        pipeline.addLast("channel-handler", handler);
    }


}

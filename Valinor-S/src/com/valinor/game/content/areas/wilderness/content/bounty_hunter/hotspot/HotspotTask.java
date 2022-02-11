package com.valinor.game.content.areas.wilderness.content.bounty_hunter.hotspot;

import com.valinor.game.task.Task;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;

import static com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunterConstants.HOTSPOT_TIME;

/**
 * This is an endless task which changes the hotspot every 30 minutes.
 *
 *  @author Patrick van Elderen | 30 jan. 2019 : 13:13:57
 *  @see <a href="https://www.rune-server.ee/members/_Patrick_/">Rune-Server profile</a>
 */
public class HotspotTask extends Task {

    private final Player player;

    public HotspotTask(Player player) {
        super("HotspotTask", HOTSPOT_TIME, true);
        this.player = player;
    }

    @Override
    public void execute() {
        if (WildernessArea.inWilderness(player.tile())) {
            Hotspot next = Hotspot.randomHotspot();
            Hotspot.ACTIVE = next;
            player.putAttrib(AttributeKey.HOTSPOT_TIMER, HOTSPOT_TIME);//Set timer
            player.getPacketSender().sendMessage("<col="+ Color.MEDRED.getColorValue()+">Bounty Hotspot:</col> The current hotspot is <col="+Color.MEDRED.getColorValue()+">"+next.name+"</col>.").sendMessage("It will move in <col="+Color.MEDRED.getColorValue()+">30 minutes</col>.");
        }
    }

    @Override
    public void onTick() {
        if (!player.isRegistered() || !WildernessArea.inWilderness(player.tile())) {
            stop();
            return;
        }

        // Timer ran out stop task
        if (!player.hotspotActive()) {
            //System.out.println("executing...");
            execute();//Change location
            return;
        }

        //Remove one tick from the current time
        var timer = player.<Integer>getAttribOr(AttributeKey.HOTSPOT_TIMER, -1) - 1;
        player.putAttrib(AttributeKey.HOTSPOT_TIMER, timer);

        //System.out.println("Still ticking...");
    }
}

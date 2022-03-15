package com.valinor.game.content.events.hp_event;

import com.valinor.GameServer;
import com.valinor.game.content.announcements.ServerAnnouncements;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.valinor.util.CustomItemIdentifiers.DONATOR_MYSTERY_BOX;
import static com.valinor.util.CustomItemIdentifiers.LEGENDARY_MYSTERY_BOX;
import static com.valinor.util.CustomNpcIdentifiers.HP_EVENT;
import static com.valinor.util.ItemIdentifiers.COINS_995;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public class HpEvent {

    private static final HpEvent INSTANCE = new HpEvent();

    public static HpEvent getInstance() {
        return INSTANCE;
    }

    public static boolean ANNOUNCE_5_MIN_TIMER = false;

    private static Tile spawnTile;

    private Optional<Npc> hpEventNpc = Optional.empty();

    public Optional<Npc> getHpEventNpc() {
        return hpEventNpc;
    }

    /**
     * The interval at which server-wide hp events occur. Event runs every three hours
     */
    public static final Duration HP_EVENT_INTERVAL = GameServer.properties().production ? Duration.ofHours(3) : Duration.ofMinutes(7);

    public static LocalDateTime last = LocalDateTime.now();
    public static LocalDateTime next = LocalDateTime.now().plus(HP_EVENT_INTERVAL.toSeconds(), ChronoUnit.SECONDS);

    public static void onServerStart() {
        TaskManager.submit(new HpEventTask());
    }

    public String timeTill(boolean displaySeconds) {
        LocalDateTime now = LocalDateTime.now();
        long difference = now.until(next, ChronoUnit.SECONDS);
        if (difference < 60 && displaySeconds) {
            return difference + " seconds";
        }
        difference = now.until(next, ChronoUnit.MINUTES);
        if (difference <= 2) {
            return 1 + difference + " minutes";
        } else if (difference <= 59) {
            return difference + " minutes";
        } else {
            return (difference / 60) + "h " + difference % 60 + "min";
        }
    }

    public void terminateActiveEvent(boolean force) {
        boolean despawned = false;
        for (Npc n : World.getWorld().getNpcs()) {
            if (n != null && n.id() == HP_EVENT && (n.hp() > 0 || force)) {
                n.stopActions(true);
                World.getWorld().unregisterNpc(n);
                despawned = true;
            }
        }
        ANNOUNCE_5_MIN_TIMER = false;

        if (despawned) {
            spawnTile = null; // reset current pos
        }
    }

    public void startEvent() {
        LocalDateTime now = LocalDateTime.now();
        long difference = last.until(now, ChronoUnit.MINUTES);
        if (difference >= HP_EVENT_INTERVAL.toMinutes()) {
            // First despawn the npc if existing
            terminateActiveEvent(true);

            // Only if it's an actual boss we spawn an NPC.
            last = now;
            next = LocalDateTime.now().plus(HP_EVENT_INTERVAL.toSeconds(), ChronoUnit.SECONDS);
            ANNOUNCE_5_MIN_TIMER = false;

            spawnTile = new Tile(3269, 3867);
            Npc hpEventNpc = new Npc(HP_EVENT, spawnTile).spawn(false);
            hpEventNpc.walkRadius(5);
            hpEventNpc.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN, 5);

            //Assign the npc reference.
            this.hpEventNpc = Optional.of(hpEventNpc);

            // Broadcast it
            World.getWorld().sendWorldMessage("<col=6a1a18><img=1396>The HP event has spawned type ::hpevent to get there!");
            World.getWorld().sendBroadcast("<img=1396>The HP event has spawned type ::hpevent to get there!");
        }
    }
}

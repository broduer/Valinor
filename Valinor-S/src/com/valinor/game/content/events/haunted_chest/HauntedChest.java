package com.valinor.game.content.events.haunted_chest;

import com.valinor.GameServer;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.valinor.util.ObjectIdentifiers.DEADMAN_CHEST_31484;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public class HauntedChest {

    private static final HauntedChest INSTANCE = new HauntedChest();

    public static HauntedChest getInstance() {
        return INSTANCE;
    }

    public static boolean ANNOUNCE_5_MIN_TIMER = false;

    private static final String DESPAWN_MESSAGE = "<col=6a1a18><img=1394>The Haunted chest has despawned!";

    private static final GameObject hauntedChest = new GameObject(DEADMAN_CHEST_31484, new Tile(3183,3668,0), 10, 3);

    /**
     * The interval at which server-wide haunted chests occur. Event runs every two hours
     */
    public static final Duration CHEST_SPAWN_TIMER = GameServer.properties().production ? Duration.ofHours(2) : Duration.ofMinutes(7);

    public static LocalDateTime last = LocalDateTime.now();
    public static LocalDateTime next = LocalDateTime.now().plus(CHEST_SPAWN_TIMER.toSeconds(), ChronoUnit.SECONDS);

    public static void onServerStart() {
        TaskManager.submit(new HauntedChestTask());
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

    public void despawnChest() {
        if(hauntedChest != null) {
            hauntedChest.remove();
        }
        ANNOUNCE_5_MIN_TIMER = false;
    }

    public void startEvent() {
        LocalDateTime now = LocalDateTime.now();
        long difference = last.until(now, ChronoUnit.MINUTES);
        if (difference >= CHEST_SPAWN_TIMER.toMinutes()) {
            // First despawn the object if existing
            despawnChest();

            // Only if it's an actual boss we spawn an NPC.
            last = now;
            next = LocalDateTime.now().plus(CHEST_SPAWN_TIMER.toSeconds(), ChronoUnit.SECONDS);
            ANNOUNCE_5_MIN_TIMER = false;
            hauntedChest.spawnForSetTime(GameServer.properties().production ? 1800 : 300, DESPAWN_MESSAGE);

            // Broadcast it
            World.getWorld().sendWorldMessage("<col=6a1a18><img=1394>The Haunted chest has spawned north east of the graveyard lvl 19 wilderness!");
            World.getWorld().sendBroadcast("<img=1394>The Haunted chest has spawned north east of the graveyard lvl 19 wilderness!");
        }
    }
}

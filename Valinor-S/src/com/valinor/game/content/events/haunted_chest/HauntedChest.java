package com.valinor.game.content.events.haunted_chest;

import com.valinor.GameServer;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.valinor.util.ObjectIdentifiers.CHEST_25685;

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

    private static final GameObject hauntedChest = new GameObject(CHEST_25685, new Tile(3183,3668,0), 10, 3);

    /**
     * The interval at which server-wide haunted chests occur. Event runs every two hours
     */
    public static final int EVENT_INTERVAL = GameServer.properties().production ? 12000 : 700;

    public LocalDateTime last = LocalDateTime.now().minus((long) (EVENT_INTERVAL * 0.6d), ChronoUnit.SECONDS);
    public LocalDateTime next = LocalDateTime.now().plus((long) (EVENT_INTERVAL * 0.6d), ChronoUnit.SECONDS);

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
        // First despawn the object if existing
        despawnChest();

        // Only if it's an actual boss we spawn an NPC.
        last = LocalDateTime.now();
        next = LocalDateTime.now().plus((long) (EVENT_INTERVAL * 0.6d), ChronoUnit.SECONDS);
        ANNOUNCE_5_MIN_TIMER = false;
        hauntedChest.spawn();

        // Broadcast it
        World.getWorld().sendWorldMessage("<col=6a1a18><img=1100>The Haunted chest has spawned north east of the graveyard lvl 19 wilderness!");
        World.getWorld().sendBroadcast("<img=1100>The Haunted chest has spawned north east of the graveyard lvl 19 wilderness!");
    }
}

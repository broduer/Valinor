package com.valinor.game.content.areas.wilderness.content.key;

import com.valinor.GameServer;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.CustomItemIdentifiers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Kaleem on 15/11/2017.
 */
public class WildernessKeyPlugin {

    private static final Logger logger = LogManager.getLogger(WildernessKeyPlugin.class);

    private static final String PICKUP_KEY_MESSAGE = "<col=800000>%s</col> has picked up the Wilderness key %s.";

    private static final String KEY_SPAWN_MESSAGE = "A Wilderness key has spawned near the <col=800000>%s (level %d)</col>!";

    private static final String KEY_DROP_MESSAGE = "<col=800000>%s</col> has died and dropped the Wilderness key near the <col=800000>%s (level %d)</col>!";

    private static final String KEY_ESCAPE_MESSAGE = "<col=800000>%s</col> has escaped with the Wilderness key!";

    private static final String SPRITE = "<img=936> ";

    public static boolean ANNOUNCE_5_MIN_TIMER = false;

    public static boolean ESCAPED = false;

    /**
     * Announces when the player has picked up the key.
     */
    public static void announceKeyPickup(Player player, Tile tile) {
        String currentDescription = null;
        int wildernessLevel = WildernessArea.wildernessLevel(player.tile());

        for (WildernessKeyLocation location : WildernessKeyLocation.values()) {
            if (location.tile().equals(tile)) {
                currentDescription = "near the <col=800000>" + location.getDescription() + "</col> (level <col=800000>" + wildernessLevel + "</col>)";
                break;
            }
        }

        if (currentDescription == null) {
            currentDescription = "around the level <col=800000>" + WildernessArea.wildernessLevel(player.tile()) + "</col>";
        }

        String message = String.format(PICKUP_KEY_MESSAGE, player.getUsername(), currentDescription);
        World.getWorld().sendWorldMessage(SPRITE + message);
    }

    public static void announceKeySpawn(Tile tile) {
        WildernessKeyLocation currentPos = null;
        int wildernessLevel = WildernessArea.wildernessLevel(tile);

        for (WildernessKeyLocation key_pos : WildernessKeyLocation.values()) {
            if (key_pos.tile().equals(tile)) {
                currentPos = key_pos;
                break;
            }
        }

        if (currentPos == null) {
            String message = "A Wilderness key has spawned somewhere in level <col=800000>" + wildernessLevel + "</col> Wilderness!";
            World.getWorld().sendWorldMessage(SPRITE + message);
            return;
        }

        String message = String.format(KEY_SPAWN_MESSAGE, currentPos.getDescription(), wildernessLevel);
        World.getWorld().sendWorldMessage(SPRITE + message);
    }

    public static void announceKeyDrop(Player killed, Tile tile) {
        WildernessKeyLocation currentPos = null;
        int wildernessLevel = WildernessArea.wildernessLevel(tile);

        for (WildernessKeyLocation key_pos : WildernessKeyLocation.values()) {
            if (key_pos.tile().distance(tile) < 5) {
                currentPos = key_pos;
                break;
            }
        }

        if (currentPos == null) {
            String message = killed.getUsername() + " has died and dropped the Wilderness key at <col=800000>" + wildernessLevel + "</col> Wilderness!";
            World.getWorld().sendWorldMessage(SPRITE + message);
            return;
        }

        String message = String.format(KEY_DROP_MESSAGE, killed.getUsername(), currentPos.getDescription(), wildernessLevel);
        World.getWorld().sendWorldMessage(SPRITE + message);
    }

    public static void announceKeyEscape(Player player) {
        if(ESCAPED) {
            return;
        }
        String message = String.format(KEY_ESCAPE_MESSAGE, player.getUsername());
        World.getWorld().sendWorldMessage(SPRITE + message);
        World.getWorld().clearBroadcast();
        ESCAPED = true;
    }

    public static final Duration SPAWN_DURATION = GameServer.properties().production ? Duration.ofHours(2) : Duration.ofMinutes(2);

    public static LocalDateTime last = LocalDateTime.now();
    public static LocalDateTime next = LocalDateTime.now().plus(SPAWN_DURATION.toSeconds(), ChronoUnit.SECONDS);

    private static final Task spawnTask = new Task("WildernessKeyPluginTask",100) {

        @Override
        public void execute() {
            LocalDateTime now = LocalDateTime.now();
            long difference = last.until(now, ChronoUnit.MINUTES);
            if (difference >= SPAWN_DURATION.toMinutes()) {
                logger.trace("A wilderness key is spawning.");
                WildernessKeyLocation location = spawnKey();
                if (location != null) {
                    logger.trace("A wilderness key has spawned at: {} (absolute: {})", location, location.tile());
                }
                ANNOUNCE_5_MIN_TIMER = false;
                ESCAPED = false;
                last = now;
                next = LocalDateTime.now().plus(SPAWN_DURATION.toSeconds(), ChronoUnit.SECONDS);
            }
        }
    };

    public static String timeTill(boolean showSeconds) {
        LocalDateTime now = LocalDateTime.now();
        long difference = now.until(next, ChronoUnit.SECONDS);
        if (difference < 60 && showSeconds) {
            return difference+" seconds";
        }
        difference = now.until(next, ChronoUnit.MINUTES);
        if (difference <= 2) {
            return 1+difference+" minutes";
        } else if (difference <= 59) {
            return difference+" minutes";
        } else {
            return (difference / 60)+"h "+difference % 60+"min";
        }
    }

    public static WildernessKeyLocation spawnKey() {
        if (GroundItemHandler.getGroundItems().stream().anyMatch(groundItem -> groundItem.getItem().getId() == CustomItemIdentifiers.WILDERNESS_KEY)) {
            logger.trace("Another key is already on the floor, new key will not spawn.");
            return null;
        }
        System.out.println("weeee");
        WildernessKeyLocation currentLocation = WildernessKeyLocation.findRandom();
        announceKeySpawn(currentLocation.tile());

        GroundItem key = new GroundItem(Item.of(CustomItemIdentifiers.WILDERNESS_KEY), currentLocation.tile(), null);
        GroundItemHandler.createGroundItem(key);
        key.setTimer(3600);//Wilderness key has a one hour timer.
        return currentLocation;
    }

    public static void onServerStart() {
        TaskManager.submit(spawnTask);
    }

    /**
     * Checks if the player has a key.
     */
    public static boolean hasKey(Player player) {
        return player.inventory().contains(CustomItemIdentifiers.WILDERNESS_KEY);
    }
}

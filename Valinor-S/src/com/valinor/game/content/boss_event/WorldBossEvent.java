package com.valinor.game.content.boss_event;

import com.valinor.GameServer;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.announcements.ServerAnnouncements;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.kill_logs.BossKillLog;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.corruptedhunleff.CorruptedHunleffCombatStrategy;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;
import com.valinor.util.CustomNpcIdentifiers;
import com.valinor.util.NpcIdentifiers;
import com.valinor.util.Utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.util.CustomItemIdentifiers.HWEEN_TOKENS;
import static com.valinor.util.CustomNpcIdentifiers.BRUTAL_LAVA_DRAGON_FLYING;
import static com.valinor.util.CustomNpcIdentifiers.GRIM;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * april 03, 2020
 */
public class WorldBossEvent {

    private static final WorldBossEvent INSTANCE = new WorldBossEvent();

    public static WorldBossEvent getINSTANCE() {
        return INSTANCE;
    }

    public Optional<Npc> getActiveNpc() {
        return activeNpc;
    }

    public WorldBosses getActiveEvent() {
        return activeEvent;
    }

    /**
     * An array of possible boss spawns. Chosen at random when a boss spawns.
     */
    private static final Tile[] POSSIBLE_SPAWNS = {
        new Tile(2968, 3404),//Falador outside gate
        new Tile(3167, 3757),//level 30 wild
        new Tile(3166, 3832),//level 40 wild
        new Tile(3073, 3687),//level 21 wild
        new Tile(3194,3951),//level 54 wild
        new Tile(2963,3819)//level 38 wild
    };

    public static Tile currentSpawnPos;

    /**
     * The interval at which server-wide world boss events occur.
     * Whilst in production mode every hour otherwise every 30 seconds.
     */
    public static final int BOSS_EVENT_INTERVAL = 6000;

    /**
     * The active event being run.
     */
    private WorldBosses activeEvent = WorldBosses.NOTHING;

    /**
     * The rotation of events, executed in sequence.
     */
    private static final WorldBosses[] EVENT_ROTATION = {WorldBosses.BRUTAL_LAVA_DRAGON, WorldBosses.ZOMBIES_CHAMPION, WorldBosses.CORRUPTED_HUNLLEF};

    public static boolean ANNOUNCE_5_MIN_TIMER = false;

    /**
     * The NPC reference for the active event.
     */
    private Optional<Npc> activeNpc = Optional.empty();

    public void bossDeath(Mob mob) {
        mob.getCombat().getDamageMap().forEach((key, hits) -> {
            Player player = (Player) key;
            player.message(Color.RED.wrap("You've dealt " + hits.getDamage() + " damage to the world boss!"));
            // Only people nearby are rewarded. This is to avoid people 'poking' the boss to do some damage
            // without really risking being there.
            if (mob.tile().isWithinDistance(player.tile(),10) && hits.getDamage() >= 1) {
                Npc npc = null;
                if (activeNpc.isPresent()) {
                    npc = activeNpc.get();
                }

                if (npc == null) {
                    return;
                }

                if(npc.id() == ZOMBIES_CHAMPION) {
                    GroundItemHandler.createGroundItem(new GroundItem(new Item(BIG_BONES), npc.tile(), player));
                }

                if(npc.id() == BRUTAL_LAVA_DRAGON_FLYING) {
                    GroundItemHandler.createGroundItem(new GroundItem(new Item(LAVA_DRAGON_BONES), npc.tile(), player));
                }

                //Always drop random coins
                GroundItemHandler.createGroundItem(new GroundItem(new Item(COINS_995, World.getWorld().random(1_000_000, 5_000_000)), npc.tile(), player));

                //Always log kill timers
                player.getBossTimers().submit(npc.def().name, (int) player.getCombat().getFightTimer().elapsed(TimeUnit.SECONDS), player);

                //Always increase kill counts
                player.getBossKillLog().addKill(npc);

                AchievementsManager.activate(player, Achievements.WORLD_BOSS_SMASHER,1);

                DailyTaskManager.increase(DailyTasks.WORLD_BOSS, player);

                //Random drop from the table
                ScalarLootTable table = ScalarLootTable.forNPC(npc.id());
                if (table != null) {
                    Item reward = table.randomItem(World.getWorld().random());
                    if (reward != null) {
                        player.message("You received a drop roll from the table for dealing more then 100 damage!");
                        //System.out.println("Drop roll for "+player.getUsername()+" for killing world boss "+npc.def().name);

                        // bosses, find npc ID, find item ID
                        BOSSES.log(player, npc.id(), reward);

                        //Niffler doesn't loot world boss loot
                        GroundItemHandler.createGroundItem(new GroundItem(reward, npc.tile(), player));
                        ServerAnnouncements.tryBroadcastDrop(player, npc, reward);

                        Utils.sendDiscordInfoLog("Player " + player.getUsername() + " got drop item " + reward.toString(), "npcdrops");
                    }
                }
            }
        });

        //Dissmiss broadcast when boss has been killed.
        World.getWorld().clearBroadcast();
        World.getWorld().sendWorldMessage("<col=6a1a18><img=1081> " + activeEvent.description + " has been killed. It will respawn shortly.");
    }

    public LocalDateTime last = LocalDateTime.now().minus((long) (BOSS_EVENT_INTERVAL * 0.6d), ChronoUnit.SECONDS);
    public LocalDateTime next = LocalDateTime.now().plus((long) (BOSS_EVENT_INTERVAL * 0.6d), ChronoUnit.SECONDS);

    public static void onServerStart() {
        // every 60 mins
        TaskManager.submit(new WorldBossEventTask());
    }

    public String timeTill(boolean displaySeconds) {
        LocalDateTime now = LocalDateTime.now();
        long difference = now.until(next, ChronoUnit.SECONDS);
        if (difference < 60 && displaySeconds) {
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

    boolean nextIsPeriodicEventBoss;
    int lastEvent = 0;

    public void startBossEvent() {
        // First despawn the npc if existing
        terminateActiveEvent(true);

        if(GameServer.properties().halloween || GameServer.properties().winter) {
            if (nextIsPeriodicEventBoss) {
                nextIsPeriodicEventBoss = false;
                activeEvent = GameServer.properties().winter ? WorldBosses.SNOWFLAKE : WorldBosses.GRIM;
            } else {
                if (++lastEvent > EVENT_ROTATION.length - 1) // reset when its at the end
                    lastEvent = 0;
                activeEvent = EVENT_ROTATION[lastEvent];
                nextIsPeriodicEventBoss = true;
            }
        } else {
            if (++lastEvent > EVENT_ROTATION.length - 1) // reset when its at the end
                lastEvent = 0;
            activeEvent = EVENT_ROTATION[lastEvent];
            nextIsPeriodicEventBoss = true;
        }

        // Only if it's an actual boss we spawn an NPC.
        if (activeEvent != WorldBosses.NOTHING) {
            last = LocalDateTime.now();
            next = LocalDateTime.now().plus((long) (BOSS_EVENT_INTERVAL * 0.6d), ChronoUnit.SECONDS);
            // see you can see constructors with ctrl+shift+space
            Tile tile = POSSIBLE_SPAWNS[new SecureRandom().nextInt(POSSIBLE_SPAWNS.length)];
            currentSpawnPos = tile;
            ANNOUNCE_5_MIN_TIMER = false;

            Npc boss = new Npc(activeEvent.npc, tile).spawn(false);
            boss.walkRadius(1);
            boss.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN,1);
            if(activeEvent == WorldBosses.CORRUPTED_HUNLLEF) {
                boss.setCombatMethod(new CorruptedHunleffCombatStrategy());
            }

            //Assign the npc reference.
            this.activeNpc = Optional.of(boss);

            World.getWorld().sendWorldMessage("<col=6a1a18><img=1100> " + activeEvent.description + " has been spotted " + activeEvent.spawnLocation(boss.tile()));
            World.getWorld().sendWorldMessage("<col=6a1a18>It despawns in 60 minutes. Hurry!");

            // Broadcast it
            World.getWorld().sendBroadcast("<img=1100>" + activeEvent.description + " has been spotted " + activeEvent.spawnLocation(boss.tile()));
        }
    }

    public void terminateActiveEvent(boolean force) {
        if (activeEvent != WorldBosses.NOTHING) {
            boolean despawned = false;
            for (Npc n : World.getWorld().getNpcs()) {
                if (n != null && n.id() == activeEvent.npc && (n.hp() > 0 || force)) {
                    n.stopActions(true);
                    World.getWorld().unregisterNpc(n);
                    despawned = true;
                }
            }
            ANNOUNCE_5_MIN_TIMER = false;
            this.activeNpc = Optional.empty();

            if (despawned) {
                currentSpawnPos = null; // reset current pos
                World.getWorld().sendBroadcast(activeEvent.description + " has despawned");
            }
        }
    }

    /**
     * Boss event data. Contains all the types of boss events that can occur - sequentially - across the server.
     * @author Patrick van Elderen | February, 13, 2021, 09:09
     * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
     */
    public enum WorldBosses {

        GRIM(CustomNpcIdentifiers.GRIM, "Grim"),
        SNOWFLAKE(CustomNpcIdentifiers.SNOWFLAKE_BOSS, "Snowflake"),
        BRUTAL_LAVA_DRAGON(CustomNpcIdentifiers.BRUTAL_LAVA_DRAGON_FLYING, "Brutal lava dragon"),
        ZOMBIES_CHAMPION(NpcIdentifiers.ZOMBIES_CHAMPION, "Zombies champion"),
        CORRUPTED_HUNLLEF(NpcIdentifiers.CORRUPTED_HUNLLEF, "Corrupted hunllef"),
        NOTHING(-1, "Nothing"); // Filler

        public final int npc;
        public final String description;

        WorldBosses(int npc, String description) {
            this.npc = npc;
            this.description = description;
        }

        public String spawnLocation(Tile tile) {
            if (tile.equals(new Tile(2968, 3404))) {
                return "north outside of the Falador gate";
            } else if (tile.equals(new Tile(3167, 3757))) {
                return "south east of the black chins hills";
            } else if (tile.equals(new Tile(3166, 3832))) {
                return "near the Lava dragons";
            } else if (tile.equals(new Tile(3073, 3687))) {
                return "outside of the bandit camp";
            } else if (tile.equals(new Tile(3194,3951))) {
                return "next to resource area";
            } else if (tile.equals(new Tile(2963,3819))) {
                return "near lvl 40 wild altar";
            }
            //We shouldn't be getting here
            return "Nothing";
        }
    }
}

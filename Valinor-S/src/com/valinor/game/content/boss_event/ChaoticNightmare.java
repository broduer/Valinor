package com.valinor.game.content.boss_event;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.announcements.ServerAnnouncements;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.valinor.game.content.boss_event.ChaoticNightmareTask.TICKS_TILL_SPAWN;
import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.util.CustomNpcIdentifiers.CHAOTIC_NIGHTMARE;
import static com.valinor.util.ItemIdentifiers.COINS_995;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 03, 2022
 */
public class ChaoticNightmare {

    private static final ChaoticNightmare INSTANCE = new ChaoticNightmare();

    public static ChaoticNightmare getInstance() {
        return INSTANCE;
    }

    private static boolean ANNOUNCE_5_MIN_TIMER = false;

    public static boolean announce5MinTimer() {
        return ANNOUNCE_5_MIN_TIMER;
    }

    public static void setAnnounce5MinTimer(boolean announce5MinTimer) {
        ANNOUNCE_5_MIN_TIMER = announce5MinTimer;
    }

    private static Tile spawnTile;

    public static Tile getSpawnTile() {
        return spawnTile;
    }

    private Optional<Npc> chaoticNightmare = Optional.empty();

    public Optional<Npc> getChaoticNightmare() {
        return chaoticNightmare;
    }

    public LocalDateTime last = LocalDateTime.now().minus((long) (TICKS_TILL_SPAWN * 0.6d), ChronoUnit.SECONDS);
    public LocalDateTime next = LocalDateTime.now().plus((long) (TICKS_TILL_SPAWN * 0.6d), ChronoUnit.SECONDS);

    public void drop(Mob mob) {
        mob.getCombat().getDamageMap().forEach((key, hits) -> {
            Player player = (Player) key;
            if (mob.tile().distance(player.tile()) <= 12 && hits.getDamage() >= 50) {
                Npc npc = null;
                if (chaoticNightmare.isPresent()) {
                    npc = chaoticNightmare.get();
                }

                if (npc == null) {
                    return;
                }

                //Always drop random coins
                GroundItemHandler.createGroundItem(new GroundItem(new Item(COINS_995, World.getWorld().random(10_000_000, 25_000_000)), npc.tile(), player));

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
                        //System.out.println("Drop roll for "+player.getUsername()+" for killing world boss "+npc.def().name);

                        // bosses, find npc ID, find item ID
                        BOSSES.log(player, npc.id(), reward);

                        //Niffler doesn't loot world boss loot
                        GroundItemHandler.createGroundItem(new GroundItem(reward, npc.tile(), player));
                        ServerAnnouncements.tryBroadcastDrop(player, npc, reward);

                        Utils.sendDiscordInfoLog("Player " + player.getUsername() + " got drop item " + reward, "npcdrops");
                    }
                }
            }
        });

        //Dismiss broadcast when boss has been killed.
        World.getWorld().clearBroadcast();
        World.getWorld().sendWorldMessage("<img=452><shad=0><col=6a1a18> Chaotic nightmare has been killed. It will respawn in 2 hours.");
    }

    public static void onServerStart() {
        // every 60 mins
        TaskManager.submit(new ChaoticNightmareTask());
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

    public void terminateActiveEvent(boolean force) {
        boolean despawned = false;
        for (Npc n : World.getWorld().getNpcs()) {
            if (n != null && n.id() == CHAOTIC_NIGHTMARE && (n.hp() > 0 || force)) {
                n.stopActions(true);
                World.getWorld().unregisterNpc(n);
                despawned = true;
            }
        }
        ANNOUNCE_5_MIN_TIMER = false;

        if (despawned) {
            spawnTile = null; // reset current pos
            World.getWorld().sendBroadcast("The Chaotic nightmare has despawned.");
        }
    }

    public void startBossEvent() {
        // First despawn the npc if existing
        terminateActiveEvent(true);

        // Only if it's an actual boss we spawn an NPC.
        last = LocalDateTime.now();
        next = LocalDateTime.now().plus((long) (TICKS_TILL_SPAWN * 0.6d), ChronoUnit.SECONDS);
        spawnTile = new Tile(3236, 3641);
        ANNOUNCE_5_MIN_TIMER = false;

        Npc boss = new Npc(CHAOTIC_NIGHTMARE, spawnTile).spawn(false);
        boss.walkRadius(1);
        boss.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN, 1);

        //Assign the npc reference.
        chaoticNightmare = Optional.of(boss);

        World.getWorld().sendWorldMessage("<col=6a1a18><img=1100>Chaotic nightmare has been spotted near the chaos altar type ::chaos to get there!");

        // Broadcast it
        World.getWorld().sendBroadcast("<img=1100>Chaotic nightmare has been spotted near the chaos altar type ::chaos to get there!");
    }
}

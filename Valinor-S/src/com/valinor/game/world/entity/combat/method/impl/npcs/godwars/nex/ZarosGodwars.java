package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.announcements.ServerAnnouncements;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.MapObjects;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.util.NpcIdentifiers.*;

/**
 * An utility class for the Zaros part of GWD
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 13, 2022
 */
public class ZarosGodwars {

    /**
     * A list of players inside the nex area
     */
    private static final List<Player> players = Collections.synchronizedList(new ArrayList<>());

    private static Nex nex;
    private static NexMinion fumus;
    public static NexMinion umbra;
    public static NexMinion cruor;
    public static NexMinion glacies;

    public static Optional<GameObject> ancientBarrierPurple = MapObjects.get(42967, new Tile(2909, 5202, 0));
    public static GameObject redBarrierPurple = null;

    public static boolean NEX_EVENT_ACTIVE = false;

    public static int getPlayersCount() {
        return players.size();
    }

    public static void breakFumusBarrier() {
        if (fumus == null) return;
        fumus.breakBarrier();
    }

    public static void breakUmbraBarrier() {
        if (umbra == null) return;
        umbra.breakBarrier();
    }

    public static void breakCruorBarrier() {
        if (cruor == null) return;
        cruor.breakBarrier();
    }

    public static void breakGlaciesBarrier() {
        if (glacies == null) return;
        glacies.breakBarrier();
    }

    public static void addPlayer(Player player) {
        if (players.contains(player)) {
            return;
        }
        players.add(player);

        if (!NEX_EVENT_ACTIVE) {
            startEvent();
        }
    }

    public static void removeFromList(Player player) {
        players.remove(player);
        cancel();
    }

    public static void clear() {
        if (nex != null) {
            nex.killBloodReavers();
            nex.remove();
            nex = null;
        }
        if (fumus != null) {
            fumus.remove();
            fumus = null;
        }
        if (umbra != null) {
            umbra.remove();
            umbra = null;
        }
        if (cruor != null) {
            cruor.remove();
            cruor = null;
        }
        if (glacies != null) {
            glacies.remove();
            glacies = null;
        }
    }

    private static void cancel() {
        //Replace red barrier with purple
        if (redBarrierPurple != null && ancientBarrierPurple.isPresent()) {
            ObjectManager.replaceWith(redBarrierPurple, ancientBarrierPurple.get());
        }
        if (getPlayersCount() == 0) {
            clear();
            NEX_EVENT_ACTIVE = false;
        }
    }

    public static ArrayList<Mob> getPossibleTargets() {
        ArrayList<Mob> possibleTarget = new ArrayList<>(players.size());
        for (Player player : players) {
            if (player == null || player.dead() || !player.isRegistered())
                continue;
            possibleTarget.add(player);
        }
        return possibleTarget;
    }

    public static void moveNextStage() {
        if (nex == null) return;
        nex.moveNextStage();
    }

    private static final boolean TESTING = false;

    public static void startEvent() {
        if (getPlayersCount() >= 1) {
            if (nex == null) {
                NEX_EVENT_ACTIVE = true;
                Nex nex = new Nex(NEX, new Tile(2924, 5202, 0));
                ZarosGodwars.nex = nex;
                Chain.bound(null).cancelWhen(() -> getPlayersCount() == 0 || ZarosGodwars.nex == null || nex == null).thenCancellable(TESTING ? 5 : 20, () -> {
                    nex.spawn(false);
                }).thenCancellable(1, () -> {
                    nex.forceChat("AT LAST!");
                    nex.animate(9182);
                }).thenCancellable(3, () -> {
                    Npc fumus = new NexMinion(FUMUS, new Tile(2913, 5215, 0)).spawn(false);
                    ZarosGodwars.fumus = (NexMinion) fumus;
                    fumus.face(nex.tile());
                    nex.face(fumus.tile());
                    nex.forceChat("Fumus!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.fumus, nex, 2010, 30, 80, 18, 18, 0);
                    projectile.sendProjectile();
                }).thenCancellable(3, () -> {
                    Npc umbra = new NexMinion(UMBRA, new Tile(2937, 5215, 0)).spawn(false);
                    ZarosGodwars.umbra = (NexMinion) umbra;
                    umbra.face(nex.tile());
                    nex.face(umbra.tile());
                    nex.forceChat("Umbra!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.umbra, nex, 2010, 30, 80, 18, 18, 0);
                    projectile.sendProjectile();
                }).thenCancellable(3, () -> {
                    Npc cruor = new NexMinion(CRUOR, new Tile(2937, 5191, 0)).spawn(false);
                    ZarosGodwars.cruor = (NexMinion) cruor;
                    cruor.face(nex.tile());
                    nex.face(cruor.tile());
                    nex.forceChat("Cruor!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.cruor, nex, 2010, 30, 80, 18, 18, 0);
                    projectile.sendProjectile();
                }).thenCancellable(3, () -> {
                    Npc glacies = new NexMinion(GLACIES, new Tile(2913, 5191, 0)).spawn(false);
                    ZarosGodwars.glacies = (NexMinion) glacies;
                    glacies.face(nex.tile());
                    nex.face(glacies.tile());
                    nex.forceChat("Glacies!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.glacies, nex, 2010, 30, 80, 18, 18, 0);
                    projectile.sendProjectile();
                }).thenCancellable(3, () -> {
                    nex.forceChat("Fill my soul with smoke!");
                    Projectile projectile = new Projectile(ZarosGodwars.glacies, nex, 2010, 30, 80, 18, 18, 0);
                    projectile.sendProjectile();
                }).thenCancellable(2, () -> {
                    nex.cantInteract(false);
                    nex.getCombat().setTarget(Utils.randomElement(getPossibleTargets()));

                    //Replace purple barrier with red
                    if (ancientBarrierPurple.isPresent()) {
                        redBarrierPurple = new GameObject(42941, ancientBarrierPurple.get().tile(), ancientBarrierPurple.get().getType(), ancientBarrierPurple.get().getRotation());
                        ObjectManager.replaceWith(ancientBarrierPurple.get(), redBarrierPurple);
                    }
                });
            }
        }
    }

    public static void drop(Mob mob) {
        /*var list = mob.getCombat().getDamageMap().entrySet().stream().sorted(Comparator.comparingInt(e -> e.getValue().getDamage())).collect(Collectors.toList());
        list.stream().limit(3).forEach(e -> {
            var key = e.getKey();
            Player player = (Player) key;
            if (mob.tile().isWithinDistance(player.tile(),12)) {
                if(mob instanceof Npc) {*/
        mob.getCombat().getDamageMap().forEach((key, hits) -> {
            Player player = (Player) key;
            if (mob.tile().isWithinDistance(player.tile(), 12) && hits.getDamage() >= 100) {
                if (mob instanceof Npc) {
                    Npc npc = mob.getAsNpc();

                    //Always log kill timers
                    player.getBossTimers().submit(npc.def().name, (int) player.getCombat().getFightTimer().elapsed(TimeUnit.SECONDS), player);

                    //Always increase kill counts
                    player.getBossKillLog().addKill(npc);

                    AchievementsManager.activate(player, Achievements.ZAROS_TEMPLE, 1);
                    DailyTaskManager.increase(DailyTasks.NEX, player);

                    //Random drop from the table
                    ScalarLootTable table = ScalarLootTable.forNPC(npc.id());
                    if (table != null) {
                        Item reward = table.randomItem(World.getWorld().random());
                        if (reward != null) {

                            // bosses, find npc ID, find item ID
                            BOSSES.log(player, npc.id(), reward);

                            GroundItemHandler.createGroundItem(new GroundItem(reward, npc.tile(), player));
                            ServerAnnouncements.tryBroadcastDrop(player, npc, reward);

                            Utils.sendDiscordInfoLog("Player " + player.getUsername() + " got drop item " + reward, "npcdrops");
                        }
                    }
                }
            }
        });
    }
}

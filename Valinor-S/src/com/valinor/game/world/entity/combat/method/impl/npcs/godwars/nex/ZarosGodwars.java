package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.content.announcements.ServerAnnouncements;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.bountyhunter.emblem.BountyHunterEmblem;
import com.valinor.game.world.entity.combat.hit.HitDamageCache;
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
import com.valinor.util.Color;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 13, 2022
 */
public class ZarosGodwars {

    private static final List<Player> playersOn = Collections.synchronizedList(new ArrayList<>());

    public static Nex nex;
    public static NexMinion fumus;
    public static NexMinion umbra;
    public static NexMinion cruor;
    public static NexMinion glacies;

    public static Optional<GameObject> ancientBarrierPurple = MapObjects.get(42967, new Tile(2909, 5202, 0));
    public static GameObject redBarrierPurple = null;

    public static int getPlayersCount() {
        return playersOn.size();
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
        if (playersOn.contains(player)) {
            return;
        }
        playersOn.add(player);
        startEvent();
    }

    public static void removePlayer(Player player) {
        playersOn.remove(player);
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
        if(redBarrierPurple != null && ancientBarrierPurple.isPresent()) {
            ObjectManager.replaceWith(redBarrierPurple, ancientBarrierPurple.get());
        }
        if (getPlayersCount() == 0)
            clear();
    }

    public static ArrayList<Mob> getPossibleTargets() {
        ArrayList<Mob> possibleTarget = new ArrayList<>(playersOn.size());
        for (Player player : playersOn) {
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

    public static void end() {
        clear();
        startEvent();
    }

    private static final boolean TESTING = true;

    public static void startEvent() {
        if (getPlayersCount() >= 1) {
            if (nex == null) {
                Nex nex = new Nex(NEX, new Tile(2924, 5202, 0));
                ZarosGodwars.nex = nex;
                Chain.bound(null).cancelWhen(() -> { // ah cancel applies here only to the first chain, so you want it to take to next
                    return getPlayersCount() == 0 || nex == null; // cancels as expected
                }).thenCancellable(TESTING ? 5 : 20, () -> {
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
                    Projectile projectile = new Projectile(ZarosGodwars.fumus, nex, 2010, 30,80,18, 18,0);
                    projectile.sendProjectile();
                }).thenCancellable(3, () -> {
                    Npc umbra = new NexMinion(UMBRA, new Tile(2937, 5215, 0)).spawn(false);
                    ZarosGodwars.umbra = (NexMinion) umbra;
                    umbra.face(nex.tile());
                    nex.face(umbra.tile());
                    nex.forceChat("Umbra!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.umbra, nex, 2010, 30,80,18, 18,0);
                    projectile.sendProjectile();
                }).thenCancellable(3, () -> {
                    Npc cruor = new NexMinion(CRUOR, new Tile(2937, 5191, 0)).spawn(false);
                    ZarosGodwars.cruor = (NexMinion) cruor;
                    cruor.face(nex.tile());
                    nex.face(cruor.tile());
                    nex.forceChat("Cruor!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.cruor, nex, 2010, 30,80,18, 18,0);
                    projectile.sendProjectile();
                }).thenCancellable(3, () -> {
                    Npc glacies = new NexMinion(GLACIES, new Tile(2913, 5191, 0)).spawn(false);
                    ZarosGodwars.glacies = (NexMinion) glacies;
                    glacies.face(nex.tile());
                    nex.face(glacies.tile());
                    nex.forceChat("Glacies!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.glacies, nex, 2010, 30,80,18, 18,0);
                    projectile.sendProjectile();
                }).thenCancellable(3, () -> {
                    nex.forceChat("Fill my soul with smoke!");
                    Projectile projectile = new Projectile(ZarosGodwars.glacies, nex, 2010, 30,80,18, 18,0);
                    projectile.sendProjectile();
                }).thenCancellable(2, () -> {
                    nex.cantInteract(false);
                    nex.getCombat().setTarget(Utils.randomElement(getPossibleTargets()));

                    //Replace purple barrier with red
                    if(ancientBarrierPurple.isPresent()) {
                        redBarrierPurple = new GameObject(42941, ancientBarrierPurple.get().tile(), ancientBarrierPurple.get().getType(), ancientBarrierPurple.get().getRotation());
                        ObjectManager.replaceWith(ancientBarrierPurple.get(), redBarrierPurple);
                    }
                });
            }
        }
    }

    /**
     * A comparartor that sorts player damages by the most damage done, descending.
     */
    private static final Comparator<Map.Entry<Mob, HitDamageCache>> BEST_DAMAGE_COMPARATOR = Comparator.comparingInt(e -> e.getValue().getDamage());

    public static void drop(Mob mob) {
        var list = mob.getCombat().getDamageMap().entrySet().stream().sorted(BEST_DAMAGE_COMPARATOR);
        list.limit(2).collect(Collectors.toList()).forEach(e -> {
            var key = e.getKey();
            var hits = e.getValue();
            Player player = (Player) key;
            if (mob.tile().isWithinDistance(player.tile(),12) && hits.getDamage() >= 300) {
                if(mob instanceof Npc) {
                    Npc npc = mob.getAsNpc();

                    //Always log kill timers
                    player.getBossTimers().submit(npc.def().name, (int) player.getCombat().getFightTimer().elapsed(TimeUnit.SECONDS), player);

                    //Always increase kill counts
                    player.getBossKillLog().addKill(npc);

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

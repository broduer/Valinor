package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            // System.out.println("ERROR DOUBLE ENTRY!");
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
        if (getPlayersCount() == 0) clear();
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
    }

    public static void startEvent() {
        if (getPlayersCount() >= 1) {
            if (nex == null) {
                Nex nex = new Nex(NEX, new Tile(2924, 5202, 0));
                ZarosGodwars.nex = nex;
                Chain.bound(null).cancelWhen(() -> {
                    return nex == null; // cancels as expected
                }).runFn(20, () -> {
                    nex.spawn(false);
                }).then(1, () -> {
                    nex.forceChat("AT LAST!");
                    nex.animate(9182);
                }).then(3, () -> {
                    Npc fumus = new NexMinion(FUMUS, new Tile(2913, 5215, 0)).spawn(false);
                    ZarosGodwars.fumus = (NexMinion) fumus;
                    fumus.spawnDirection(Utils.getFaceDirection(1, -1));
                    nex.face(new Tile(fumus.getCoordFaceX(fumus.getSize()), fumus.getCoordFaceY(fumus.getSize()), 0));
                    nex.forceChat("Fumus!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.fumus, nex, 2010, 30,60,18, 18,0);
                    projectile.sendProjectile();
                }).then(3, () -> {
                    Npc umbra = new NexMinion(UMBRA, new Tile(2937, 5215, 0)).spawn(false);
                    ZarosGodwars.umbra = (NexMinion) umbra;
                    ZarosGodwars.umbra.spawnDirection(Utils.getFaceDirection(-1, -1));
                    nex.face(new Tile(ZarosGodwars.umbra.getCoordFaceX(ZarosGodwars.umbra.getSize()), ZarosGodwars.umbra.getCoordFaceY(ZarosGodwars.umbra.getSize()), 0));
                    nex.forceChat("Umbra!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.umbra, nex, 2010, 30,60,18, 18,0);
                    projectile.sendProjectile();
                }).then(3, () -> {
                    Npc cruor = new NexMinion(CRUOR, new Tile(2937, 5191, 0)).spawn(false);
                    ZarosGodwars.cruor = (NexMinion) cruor;
                    ZarosGodwars.cruor.spawnDirection(Utils.getFaceDirection(-1, 1));
                    nex.face(new Tile(ZarosGodwars.cruor.getCoordFaceX(ZarosGodwars.cruor.getSize()), ZarosGodwars.cruor.getCoordFaceY(ZarosGodwars.cruor.getSize()), 0));
                    nex.forceChat("Cruor!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.cruor, nex, 2010, 30,60,18, 18,0);
                    projectile.sendProjectile();
                }).then(3, () -> {
                    Npc glacies = new NexMinion(GLACIES, new Tile(2913, 5191, 0)).spawn(false);
                    ZarosGodwars.glacies = (NexMinion) glacies;
                    ZarosGodwars.glacies.face(new Tile(ZarosGodwars.glacies.getCoordFaceX(ZarosGodwars.glacies.getSize()), ZarosGodwars.glacies.getCoordFaceY(ZarosGodwars.glacies.getSize()), 0));
                    ZarosGodwars.glacies.spawnDirection(Utils.getFaceDirection(1, 1));
                    nex.face(new Tile(ZarosGodwars.glacies.getCoordFaceX(ZarosGodwars.glacies.getSize()), ZarosGodwars.glacies.getCoordFaceY(ZarosGodwars.glacies.getSize()), 0));
                    nex.forceChat("Glacies!");
                    nex.animate(9189);
                    Projectile projectile = new Projectile(ZarosGodwars.glacies, nex, 2010, 30,60,18, 18,0);
                    projectile.sendProjectile();
                }).then(3, () -> {
                    nex.forceChat("Fill my soul with smoke!");
                    Projectile projectile = new Projectile(ZarosGodwars.glacies, nex, 2010, 30,60,18, 18,0);
                    projectile.sendProjectile();
                }).then(2, () -> {
                    nex.cantInteract(false);
                    nex.getCombat().setTarget(Utils.randomElement(getPossibleTargets()));
                });
            }
        }
    }
}

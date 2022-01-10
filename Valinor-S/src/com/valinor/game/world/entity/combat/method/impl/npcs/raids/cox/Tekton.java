package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cox;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.RouteDirection;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.List;

import static com.valinor.util.NpcIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.GIANT_ANVIL;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * april 11, 2020
 */
public class Tekton extends CommonCombatMethod {

   /* private final List<Tile> bubbles = new ArrayList<>(20);
    private boolean forcedSmith = false;
    private boolean init = false;

    private void init(Npc npc) {
        int baseX = ((npc.spawnTile().getX() >> 3) & (~3)) << 3; // base X of the chamber
        int baseY = ((npc.spawnTile().getY() >> 3) & (~3)) << 3;
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                bubbles.add(new Tile(baseX + x, baseY + y, npc.tile().level));
            }
        }
    }

    private Player findRandomTarget(Player player) {
        ArrayList<Player> targets = new ArrayList<>();
        if (player.raidsParty != null) {
            for (Player p : player.raidsParty.getMembers()) {
                if (p != null && p.getRaids() != null && p.getRaids().raiding(p) && p.tile().inArea(new Area(3307, 5288, 3319, 5301, p.raidsParty.getHeight()))) {
                    targets.add(p);
                }
            }
        }
        return World.getWorld().get(targets);
    }*/

    private static boolean instanceFinished(Mob mob) {
        if (mob instanceof Npc) {
            Npc npc = (Npc) mob;
            if (npc.dead() || !npc.isRegistered()) {
                return true;
            }
        }
        return false;
    }

    private static void doMeleePhaseInner(Mob mob, Mob target) {
        mob.setEntityInteraction(null);
        mob.animate(7493);
        Tile p1 = target.tile().copy();
        mob.face(p1);
        Chain.bound(null).cancelWhen(() -> instanceFinished(mob)).runFn(1, () -> {
            mob.face(p1);
        }).then(4, () -> {
            if (p1.area(1).contains(target)) {
                target.hit(mob, Utils.random(41), 0, CombatType.MELEE).checkAccuracy().submit();
            }
        }).then(2, () -> {
            mob.setEntityInteraction(target);
        }).then(2, () -> {
            mob.setEntityInteraction(null);
        });
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        mob.getMovement().setBlockMovement(true); // Lock movement when we found a target
        doMeleePhaseInner(mob, target);
        /*if (!init) {
            init(((Npc) mob));
            init = true;
        }
        if (target.isPlayer()) {
            Player player = target.getAsPlayer();
            ArrayList<Player> targets = new ArrayList<>();
            if (player.raidsParty != null) {
                for (Player p : player.raidsParty.getMembers()) {
                    if (p != null && p.getRaids() != null && p.getRaids().raiding(p) && p.tile().inArea(new Area(3307, 5288, 3319, 5301, p.raidsParty.getHeight()))) {
                        Tile pos = Utils.getClosestTile(mob, p);
                        int xDist = Math.abs(pos.getX() - p.getAbsX());
                        int yDist = Math.abs(pos.getY() - p.getAbsY());
                        int dist = Math.max(xDist, yDist);
                        if (dist <= 4) {
                            targets.add(p);
                        }
                    }
                }
            }

            Player facePlayer = World.getWorld().get(targets);
            RouteDirection side;
            Tile pos = Utils.getClosestTile(mob, facePlayer);
            int xDiff = pos.getX() - facePlayer.getAbsX();
            int yDiff = pos.getY() - facePlayer.getAbsY();
            if (xDiff > 0)
                side = RouteDirection.WEST;
            else if (xDiff < 0)
                side = RouteDirection.EAST;
            else if (yDiff > 0)
                side = RouteDirection.SOUTH;
            else
                side = RouteDirection.NORTH;
            mob.face(side);
            mob.animate(7483);
            Chain.bound(null).runFn(1, () -> {
                if (mob.dead())
                    return;
                for (Player p : targets) {
                    Tile src = Utils.getClosestTile(mob, p);
                    if ((side.deltaX != 0 && p.getAbsX() - src.getX() == side.deltaX) ||
                        (side.deltaY != 0 && p.getAbsY() - src.getY() == side.deltaY)) {
                        int maxDamage = 40;
                        if (Prayers.usingPrayer(p, Prayers.PROTECT_FROM_MELEE))
                            maxDamage = 20;

                        p.hit(mob, World.getWorld().random(10, maxDamage));
                    }
                }
            });
        }*/
    }

    /*private void smith(Npc npc, Mob target) {
        Tile walkToTile = new Tile(3309, 5295);
        Chain.bound(null).cancelWhen(() -> {
            return npc.dead(); // cancels as expected
        }).runFn(1, () -> {
            npc.lock();
            npc.getLocalPlayers().forEach(plr -> plr.getCombat().reset());
            npc.animate(7479);
            npc.transmog(TEKTON_7541);
            npc.resetFaceTile();
        }).then(2, () -> {
            npc.getRouteFinder().routeAbsolute(walkToTile.x, walkToTile.y);
        }).waitUntil(1, () -> {
            npc.getRouteFinder().routeAbsolute(walkToTile.x, walkToTile.y);
            return npc.tile().equals(walkToTile.x, walkToTile.y);
        }, () -> {
            npc.face(walkToTile);
        }).then(1, () -> {
            npc.transmog(TEKTON_7545);
            npc.animate(7475);
            for (int i = 0; i < 6; i++) {
                shootLava(npc, target);
                Chain.bound(null).runFn(3, () -> {
                    npc.heal(Math.max((int) (1200 * 0.005), 1));
                });
            }
            npc.animate(7474);
        }).then(2, () -> {
            npc.transmog(TEKTON_7541);
            Player p = findRandomTarget(target.getAsPlayer());
            npc.face(p.tile());
            npc.getRouteFinder().routeEntity(p);
            Chain.bound(null).waitUntil(1, () -> {
                npc.getRouteFinder().routeEntity(p);
                return npc.tile().equals(p.tile());
            }, () -> {
                npc.animate(7478);
                npc.transmog(TEKTON_7542);
            }).then(1, () -> {
                npc.getCombat().setTarget(p);
                npc.resetFaceTile();
                npc.unlock();
            });
        });
    }

    private void shootLava(Mob mob, Mob target) {
        if (target.isPlayer()) {
            Player player = target.getAsPlayer();
            if (player.raidsParty != null) {
                for (Player p : player.raidsParty.getMembers()) {
                    if (p != null && p.getRaids() != null && p.getRaids().raiding(p) && p.tile().inArea(new Area(3307, 5288, 3319, 5301, p.raidsParty.getHeight()))) {
                        for (int i = 0; i < 2; i++) {
                            Tile source = World.getWorld().get(bubbles);
                            Tile tile = p.tile().copy();
                            Chain.bound(null).runFn(1, () -> {
                                Projectile projectile = new Projectile(source, tile, 1, 660, 150, 0, 0, 0, 0);
                                projectile.sendProjectile();
                                World.getWorld().tileGraphic(659, tile, 0, 2);
                            }).then((2 * 25) / 600, () -> {
                                if (mob.dead() || !mob.isRegistered() || target == null)
                                    return;
                                int distance = Utils.getDistance(p.tile(), tile);
                                if (distance <= 1)
                                    p.hit(mob, World.getWorld().random(1, distance == 1 ? 12 : 20));
                            });
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onHit(Mob mob, Mob target, Hit hit) {
        if (mob.isNpc() && mob.getAsNpc().def() != null && mob.getAsNpc().def().name.toLowerCase().contains("tekton")) {
            if (World.getWorld().rollDie(10,1) && !forcedSmith) {
                System.out.println("here");
                forcedSmith = true;
                smith(mob.getAsNpc(), target);
            }
        }
    }*/

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;
    }
}

package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cox;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.RouteDirection;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.List;

import static com.valinor.util.NpcIdentifiers.TEKTON_7541;
import static com.valinor.util.NpcIdentifiers.TEKTON_7545;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * april 11, 2020
 */
public class Tekton extends CommonCombatMethod {

    private final List<Tile> bubbles = new ArrayList<>(20);

    @Override
    public void init(Mob mob) {
        if (mob.isNpc()) {
            Npc npc = mob.getAsNpc();
            int baseX = ((npc.spawnTile().getX() >> 3) & (~3)) << 3; // base X of the chamber
            int baseY = ((npc.spawnTile().getY() >> 3) & (~3)) << 3;
            for (int x = 0; x < 32; x++) {
                for (int y = 0; y < 32; y++) {
                    bubbles.add(new Tile(baseX + x, baseY + y, npc.tile().level));
                }
            }
        }
    }

    private boolean forcedSmith = false;

    private Player findRandomTarget(Mob mob) {
        ArrayList<Mob> targets = new ArrayList<>();
        for (Mob target : getPossibleTargets(mob)) {
            if (target != null) {
                targets.add(target);
            }
        }
        return World.getWorld().get(targets).getAsPlayer();
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (target.isPlayer()) {
            ArrayList<Mob> targets = new ArrayList<>();
            for (Mob t : getPossibleTargets(mob, 4, true, false)) {
                Tile pos = Utils.getClosestTile(mob, t);
                int xDist = Math.abs(pos.getX() - t.getAbsX());
                int yDist = Math.abs(pos.getY() - t.getAbsY());
                int dist = Math.max(xDist, yDist);
                if (dist <= 4) {
                    targets.add(t);
                }
            }

            Mob p = World.getWorld().get(targets);
            RouteDirection side;
            Tile pos = Utils.getClosestTile(mob, p);
            int xDiff = pos.getX() - p.getAbsX();
            int yDiff = pos.getY() - p.getAbsY();
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
                for (Mob t : targets) {
                    Tile src = Utils.getClosestTile(mob, t);
                    if ((side.deltaX != 0 && t.getAbsX() - src.getX() == side.deltaX) ||
                        (side.deltaY != 0 && t.getAbsY() - src.getY() == side.deltaY)) {
                        int maxDamage = 40;
                        if (Prayers.usingPrayer(t, Prayers.PROTECT_FROM_MELEE))
                            maxDamage = 20;

                        t.hit(mob, World.getWorld().random(10, maxDamage));
                    }
                }
            });
        }
    }

    private void resetCombatForPlayers(Mob mob) {
        for (Mob t : getPossibleTargets(mob)) {
            t.getCombat().reset();
        }
    }

    private void smith(Mob mob, Mob target) {
        if (mob.isNpc()) {
            Npc npc = mob.getAsNpc();
            Tile walkToTile = new Tile(3309, 5295, mob.tile().level);
            // cancels as expected
            Chain.bound(null).cancelWhen(npc::dead).runFn(1, () -> {
                npc.lock();
                resetCombatForPlayers(npc);
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
                npc.getCombat().setTarget(p);
                npc.resetFaceTile();
                npc.unlock();
            });
        }
    }

    private void shootLava(Mob mob, Mob target) {
        for (Mob t : getPossibleTargets(mob)) {
            for (int i = 0; i < 2; i++) {
                Tile source = World.getWorld().get(bubbles);
                Tile tile = t.tile().copy();
                Chain.bound(null).runFn(1, () -> {
                    Projectile projectile = new Projectile(source, tile, 1, 660, 150, 0, 0, 0, 0);
                    projectile.sendProjectile();
                    World.getWorld().tileGraphic(659, tile, 0, 2);
                }).then(5, () -> {
                    if (mob.dead() || !mob.isRegistered() || target == null)
                        return;
                    int distance = Utils.getDistance(t.tile(), tile);
                    if (distance <= 1)
                        t.hit(mob, World.getWorld().random(1, distance == 1 ? 12 : 20));
                });
            }
        }
    }

    @Override
    public void onHit(Mob mob, Mob target, Hit hit) {
        if (mob.isNpc() && mob.getAsNpc().def() != null && mob.getAsNpc().def().name.toLowerCase().contains("tekton")) {
            if (mob.hp() < mob.maxHp() / 2 && !forcedSmith && World.getWorld().rollDie(1, 1)) {//15
                forcedSmith = true;
                smith(mob, target);
            }
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;
    }
}

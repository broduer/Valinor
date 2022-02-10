package com.valinor.game.world.entity.combat.method.impl.npcs.raids.tob;

import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.valinor.util.NpcIdentifiers.XARPUS_8340;
import static com.valinor.util.NpcIdentifiers.XARPUS_8341;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 07, 2022
 */
public class Xarpus extends CommonCombatMethod {

    //courners as locations, check if player is within 6 tiles of it

    //exhumed spawn 25 times
    //exhumed close gfx 1549
    //attack anim = 8059
    //take off ground = 8061

    private int phase = 0; //0 = healing, 1 = poison splats, 2 = death stare;
    private Task healingTask;
    private Task staringTask;
    private final List<PoisonPool> poison = new ArrayList<>();
    private int exumedCount = 0;
    private final Area ARENA = new Area(3163, 4380, 3177, 4394);
    private Tile STARING_QUAD = QUAD[World.getWorld().random(0,QUAD.length - 1)];
    private int oldestPool = 0;

    private static final Tile[] QUAD = {
        new Tile(3163, 4380),
        new Tile(3163, 4380),
        new Tile(3177, 4394),
        new Tile(3177, 4380)
    };

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        List<Mob> targets = getPossibleTargets(mob);
        if (phase == 1) { //poison pools		
            mob.animate(8059);
            final Tile tile = target.tile();
            Task task = new Task("XarpusPrepareAttackTask", 1) {

                Mob next = target;
                int tick = 0;

                @Override
                public void execute() {
                    tick++;
                    if (tick == 1) {
                        Projectile projectile = new Projectile(mob, target, 1555, 0, 120, 50, 25,0);
                        projectile.sendProjectile();

                    } else if (tick == 4) {
                        boolean exists = poison.stream().anyMatch(pool -> pool.getTile().equals(tile));
                        if (!exists) {
                            if (poison.size() > 25) {
                                poison.set(oldestPool, new PoisonPool(World.getWorld().random(1654, 1661), tile));
                                if (oldestPool > 25) {
                                    oldestPool = 0;
                                }
                            } else
                                poison.add(new PoisonPool(World.getWorld().random(1654, 1661), tile));
                        }
                        World.getWorld().tileGraphic(1556, tile,0,0);
                    } else if (tick == 5) {
                        int tries = 0;
                        boolean moreThenOneTarget = targets.size() > 1;
                        while (next == target && tries++ < 50 && moreThenOneTarget) {
                            next = World.getWorld().randomTypeOfList(targets);
                        }

                        if(moreThenOneTarget) {
                            Projectile projectile = new Projectile(tile, next, 1555, 0, 120, 50, 25, 0);
                            projectile.sendProjectile();
                        }
                    } else if (tick == 9) {
                        final Tile nextTile = next.tile();
                        boolean exists = poison.stream().anyMatch(pool -> pool.getTile().equals(nextTile));
                        if (!exists) {
                            if (poison.size() > 25) {
                                poison.set(oldestPool, new PoisonPool(World.getWorld().random(1654, 1661), nextTile));
                                oldestPool++;
                                if (oldestPool > 25) {
                                    oldestPool = 0;
                                }
                            } else
                                poison.add(new PoisonPool(World.getWorld().random(1654, 1661), nextTile));
                        }
                        World.getWorld().tileGraphic(1556, nextTile,0,0);
                        stop();
                    }
                }
            };
            TaskManager.submit(task);
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 8;
    }

    @Override
    public void onDeath(Player player, Npc npc) {
        npc.transmog(XARPUS_8341);
    }

    @Override
    public void process(Mob mob, Mob target) {
        super.process(mob, target);
        List<Mob> targets = getPossibleTargets(mob);
        Optional<Mob> u = targets.stream().filter(t -> t.tile().isWithinDistance(mob.tile(), 6)).findAny();
        if (phase == 0 && exumedCount < 10 && u.isPresent()) {
            mob.resetFaceTile();
            mob.setEntityInteraction(null);
            if (healingTask == null) {
                if (!targets.isEmpty()) {
                    healingTask = new Task("XarpusProcessHealingTask", 1) {

                        private Tile exumed;
                        int tick = 0;

                        @Override
                        public void execute() {
                            tick++;
                            if (tick == 1) {
                                int x = World.getWorld().random(ARENA.swX, ARENA.neX), y = World.getWorld().random(ARENA.swY, ARENA.neY);
                                exumed = Tile.of(x, y).transform(0,0, mob.tile().level);
                                exumedCount++;
                            } else if (tick > 1 && tick <= 16) {
                                boolean plugged = false;
                                for (Mob t : targets) {
                                    if (t.tile().equals(exumed)) {
                                        plugged = true;
                                    }
                                }
                                if (targets.get(0).isPlayer()) {
                                    World.getWorld().tileGraphic(1549, exumed, 0, 0);
                                }
                                if (!plugged && exumed != null) {
                                    new Projectile(exumed, mob.getCentrePosition(), mob.getIndex(), 1550, 87, 40, 11, 55, 0).sendProjectile();
                                    mob.hit_(mob,9, 4, SplatType.NPC_HEALING_HITSPLAT);
                                }
                            } else if (tick >= 17) {
                                if (exumedCount >= 10) {
                                    mob.getAsNpc().canAttack(true);
                                    mob.getAsNpc().transmog(XARPUS_8340);
                                    mob.animate(8061);
                                    mob.getCombat().setTarget(World.getWorld().randomTypeOfList(targets));
                                    phase = 1;
                                }
                                stop();
                            }
                        }
                    };
                    TaskManager.submit(healingTask);
                }
            } else {
                if (!healingTask.isRunning()) {
                    healingTask = null;
                }
            }
        }
        if (phase == 2) {
            if (staringTask == null) {
                staringTask = new Task("XarpusProcessStaringTask", 1) {
                    int tick = 0;

                    @Override
                    public void execute() {
                        tick++;
                        if (tick == 1) {
                            STARING_QUAD = getRandomFocus().transform(0, 0, mob.tile().level);
                            mob.face(STARING_QUAD.getX(), STARING_QUAD.getY());
                        } else if (tick >= 12) {
                            stop();
                        }
                    }

                };
                TaskManager.submit(staringTask);
            } else {
                if (!staringTask.isRunning()) {
                    staringTask = null;
                }
            }
        }
        if (phase == 1 || phase == 2) {
            for (PoisonPool pool : poison) {
                if (pool == null)
                    continue;
                targets.stream().filter(p -> !p.getAsPlayer().dead()).findFirst().ifPresent(creator -> World.getWorld().tileGraphic(pool.getGfx(), pool.getTile(), 0, 0));
                for (Mob t : targets) {
                    if (t == null || t.getAsPlayer().dead())
                        continue;
                    if (t.tile().equals(pool.getTile())) {
                        t.hit_(mob, World.getWorld().random(6, 8), 1, SplatType.POISON_HITSPLAT);
                    }
                }
            }
        }
        if (phase == 3) {
            poison.clear();
        }
    }

    @Override
    public void onHit(Mob mob, Mob target, Hit hit) {
        double health = ((double) mob.hp() / mob.maxHp()) * 100;
        if (health <= 22.5D && phase != 2) {
            mob.getAsNpc().canAttack(false);
            phase = 2;
        }
        if (phase == 2) {
            if (target.tile().isWithinDistance(STARING_QUAD, 7)) {
                System.out.println("huh2");
                new Projectile(mob, target, 1555, 0, 120, 50, 25, 0).sendProjectile();
                target.graphic(1556);
                target.hit_(mob,World.getWorld().random(6, 8), 3, SplatType.POISON_HITSPLAT);
            }
        }
    }

    /**
     * Gets the next random focus point, excludes last one.
     */
    public Tile getRandomFocus() {
        Tile random = QUAD[World.getWorld().random(0, QUAD.length - 1)];
        if (random.equals(STARING_QUAD)) {
            return getRandomFocus();
        } else {
            return random;
        }
    }

    public static class PoisonPool {

        public PoisonPool(int gfx, Tile tile) {
            this.gfx = gfx;
            this.tile = tile;
        }

        public final int gfx;

        public int getGfx() {
            return gfx;
        }

        public final Tile tile;

        public Tile getTile() {
            return tile;
        }
    }
}

package com.valinor.game.world.entity.combat.method.impl.npcs.raids.tob;

import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.AreaConstants;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.DumbRoute;
import com.valinor.game.world.route.routes.ProjectileRoute;
import com.valinor.util.timers.TimerKey;

import java.util.List;

/**
 *
 * @author ReverendDread
 * Apr 20, 2019
 */
public class PestilentBloat extends CommonCombatMethod {

    private static final Tile[] corners = {
        new Tile(3288, 4440), //bottom left
        new Tile(3288, 4451), //top left
        new Tile(3299, 4451), //top right
        new Tile(3299, 4440) //bottom right
    };

    private static final int SLEEPING = 8082; //sleep animation
    private int corner = 0; //corner flag
    private int stepsTillStop = World.getWorld().random(40, 80); //steps till bloat stops walking and then sleeps

    public int getStepsTillStop() {
        return stepsTillStop;
    }

    public void setStepsTillStop(int stepsTillStop) {
        this.stepsTillStop = stepsTillStop;
    }

    private boolean damageReduction = true; //damage reduction

    private boolean stopped; //if bloat has stopped walking

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    private boolean sleeping;

    public boolean isSleeping() {
        return sleeping;
    }

    public void setSleeping(boolean sleeping) {
        this.sleeping = sleeping;
    }

    private List<Mob> targets;
    private static final int MIN_FLESH = 1570, MAX_FLESH = 1573;
    private static final Area ARENA = new Area(3288, 4440, 3303, 4455);
    private boolean flesh;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        //Process handles this combat script
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;
    }

    @Override
    public double getDamageReduction() {
        return damageReduction ? 0 : 1;
    }

    @Override
    public void process(Mob mob, Mob target) {
        super.process(mob, target);
        if(mob.dead()) {
            return;
        }
        mob.getAsNpc().noRetaliation(true);
        mob.getTimers().cancel(TimerKey.FROZEN);
        targets = getPossibleTargets();
        if (corner >= corners.length) {
            corner = 0;
        }

        Tile cornerTile = corners[corner].transform(0,0, mob.tile().level);

        if (mob.tile().equals(cornerTile) && stopped) {
            corner++;
        }
        if (!sleeping && !isStopped()) {
            DumbRoute.route(mob, cornerTile.getX(), cornerTile.getY());
            mob.getCombat().setTarget(null);
            if (stepsTillStop-- <= 0) {
                setStopped(true);
            }
            if (mob.tile().equals(cornerTile) && !isSleeping()) {
                corner++;
            }
        } else {
            if (isStopped() && getStepsTillStop() <= 0 && !isSleeping()) {
                mob.animate(SLEEPING);
                damageReduction = false;
                shutdown(mob);
            }
        }
        if (!isSleeping()) {
            checkForLineOfSight(mob);
        }
        boolean present = targets.stream().anyMatch(t -> t.tile().inArea(AreaConstants.BLOAT_ARENA));
        if (present && !flesh && !isSleeping()) {
            fleshFall(mob, targets.get(0));
        }
    }

    /**
     * Handles line of sight attacks
     */
    private void checkForLineOfSight(Mob mob) {
        for (Mob target : targets) {
            if (!ProjectileRoute.allow(mob, target.tile())) {
                continue;
            }
            if (target.isPlayer()) {
                new Projectile(mob, target, 1569, 0, 100, 45, 28, 0).sendProjectile();
                target.hit(mob, World.getWorld().random(1, 23), 3,null).setAccurate(false).graphic(new Graphic(1569)).submit();
            }
        }
    }

    /**
     * Handles falling flesh
     */
    private void fleshFall(Mob mob, Mob target) {
        flesh = true;
        Task task = new Task("PestilentBloatFleshFallTask", 1) {

            int cycle = 0;
            final List<Tile> tiles = ARENA.getRandomLocations(World.getWorld().random(10, 20), mob.getZ());

            @Override
            public void execute() {
                if(mob.dead()) {
                    stop();
                    return;
                }
                if (cycle == 0) {
                    for (Tile loc : tiles) {
                        if (target.isPlayer()) {
                            World.getWorld().tileGraphic(World.getWorld().random(MIN_FLESH, MAX_FLESH), loc,0,0);
                        }
                    }
                } else if (cycle == 4) {
                    for (Mob target : targets) {
                        for (Tile loc : tiles) {
                            if (target.tile().equals(loc)) {
                                target.hit(mob, World.getWorld().random(20, 30));
                                target.stun(5);
                            }
                        }
                    }
                } else if (cycle >= 8) {
                    flesh = false;
                    stop();
                }
                cycle++;
            }

        };
        TaskManager.submit(task);
    }

    /**
     * Handles the sleeping bit
     */
    private void shutdown(Mob mob) {
        setSleeping(true);
        Task task = new Task("PestilentBloatShutdownTask", 1) {

            int duration = 0;

            @Override
            public void execute() {
                if (duration >= 29) {
                    setStopped(false);
                    setStepsTillStop(World.getWorld().random(40, 80));
                    setSleeping(false);
                    damageReduction = true;
                    stop();
                }
                duration++;
            }

        };
        TaskManager.submit(task);
    }
}

package com.valinor.game.world.entity.combat.method.impl.npcs.raids.tob;

import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.position.Area;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 07, 2022
 */
public class Sotetseg extends CommonCombatMethod {

    private static final Graphic RED_BALL_SPLASH = new Graphic(1605);
    private static final Area ARENA = new Area(3273, 4308, 3287, 4334);
    private static final int ATTACK = 8139;

    private int attacks = 0;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        attacks++;
        if (attacks == 10) { //big spec
            attacks = 0;
            mob.animate(ATTACK - 1);
            new Projectile(mob, target, 1604, 1, 350, 35, 30, 0).sendProjectile();
            Task task = new Task("SotetsegBigSpecTask", 1) {

                int cycle = 0;
                int inRange = 0;
                final List<Mob> targets = getPossibleTargets(mob);
                final int damage = targets.size() > 2 ? World.getWorld().random(135, 150) : World.getWorld().random(55, 70);

                @Override
                public void execute() {
                    if (cycle >= 12) {
                        if (mob.dead()) {
                            stop();
                            return;
                        }
                        for (Mob t : targets) {
                            if (t.isPlayer() && !t.getAsPlayer().dead() && t.tile().isWithinDistance(target.tile(), 1)) {
                                inRange++;
                            }
                        }
                        for (Mob t : targets) {
                            if (t.isPlayer() && !t.getAsPlayer().dead() && t.tile().isWithinDistance(target.tile(), 1)) {
                                target.hit(mob, damage / inRange, 0);
                                t.getAsPlayer().graphic(RED_BALL_SPLASH.id(), RED_BALL_SPLASH.height(), 0);
                            }
                        }
                        stop();
                    }
                    cycle++;
                }

            };
            TaskManager.submit(task);
        } else {
            mob.animate(ATTACK);
            //Normal attack
            Task task = new Task("SotetsegNormalAttackTask", 1) {
                int cycle = 0;
                final List<Mob> chainable = getPossibleTargets(mob);

                @Override
                public void execute() {
                    if (mob.dead()) {
                        stop();
                        return;
                    }
                    if (cycle == 0) {
                        new Projectile(mob, target, 1606, 1, 175, 35, 30, 0).sendProjectile();
                        if (target.isPlayer()) {
                            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), 6, CombatType.MAGIC).checkAccuracy().submit();
                        }
                    } else if (cycle == 5 && chainable.size() > 1) {
                        Mob chain = World.getWorld().randomTypeOfList(chainable
                            .stream()
                            .filter(player -> player.tile().inArea(ARENA))
                            .filter(player -> player != target)
                            .collect(Collectors.toList()));
                        if (chain != null) {
                            if (chain.isPlayer() && target.isPlayer() && !chain.getAsPlayer().dead()) {
                                new Projectile(mob, target, 1607, 0, 200, 35, 30, 0).sendProjectile();
                                if (target.isPlayer()) {
                                    target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED), 4, CombatType.RANGED).checkAccuracy().submit();
                                }
                            }
                        }
                        stop();
                    }
                    cycle++;
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
        return 12;
    }
}

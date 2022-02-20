package com.valinor.game.world.entity.combat.method.impl.npcs.raids.tob;

import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.timers.TimerKey;

import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 07, 2022
 */
public class NylocasVasilias extends CommonCombatMethod {

    private Form form = new Form(NYLOCAS_VASILIAS_8355, CombatType.MELEE, CombatType.MAGIC);
    private static final int MELEE_ANIM = 8004, MAGIC_ANIM = 7989, RANGED_ANIM = 7999;
    private int attacks = 0;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        int damage = CombatFactory.calcDamageFromType(mob, target, form.getStyle());
        var tileDist = mob.tile().transform(1, 1, 0).distance(target.tile());
        var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
        switch (mob.getAsNpc().id()) {
            case NYLOCAS_VASILIAS_8356:
                if (target.isPlayer()) {
                    mob.animate(MAGIC_ANIM);
                    new Projectile(mob, target, 1580, 20, 12 * tileDist, 25, 30, 0).sendProjectile();
                    target.hit(mob, damage, delay, CombatType.MAGIC).checkAccuracy().submit();
                }
                break;
            case NYLOCAS_VASILIAS_8355:
                if (target.isPlayer()) {
                    mob.animate(MELEE_ANIM);
                    target.hit(mob, damage, 2, CombatType.MELEE).checkAccuracy().submit();
                }
                break;
            case NYLOCAS_VASILIAS_8357:
                if (target.isPlayer()) {
                    mob.animate(RANGED_ANIM);
                    new Projectile(mob, target, 1560, 20, 12 * tileDist, 25, 30, 0).sendProjectile();
                    target.hit(mob, damage, delay, CombatType.RANGED).checkAccuracy().submit();
                }
                break;
            default:
                break;
        }
        attacks--;
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return form.getStyle() == CombatType.MELEE ? 2 : 24;
    }

    @Override
    public void process(Mob mob, Mob target) {
        if (canTransform()) {
            attacks = 3;
            Task task = new Task("NylocasVasiliasTask", 2) {

                @Override
                public void execute() {
                    form = getNextForm();
                    mob.getAsNpc().transmog(form.getId());
                    mob.getAsNpc().completelyLockedFromMoving(form.id != NYLOCAS_VASILIAS_8355);
                    mob.getCombat().delayAttack(3);
                    stop();
                }
            };
            TaskManager.submit(task);
        }
        mob.getTimers().cancel(TimerKey.FROZEN);
    }

    /**
     * Checks if vasilia can tranform again.
     */
    private boolean canTransform() {
        return attacks <= 0;
    }

    private Form getNextForm() {
        Form next = form;
        while (next.getId() == form.getId()) {
            next = switch (World.getWorld().random(0, 2)) {
                case 1 -> new Form(NYLOCAS_VASILIAS_8356, CombatType.MAGIC, CombatType.RANGED);
                case 2 -> new Form(NYLOCAS_VASILIAS_8357, CombatType.RANGED, CombatType.MELEE);
                default -> new Form(NYLOCAS_VASILIAS_8355, CombatType.MELEE, CombatType.MAGIC);
            };
        }
        return next;
    }

    protected static class Form {

        Form(int id, CombatType style, CombatType weakness) {
            this.id = id;
            this.style = style;
            this.weakness = weakness;
        }

        private final int id;

        public int getId() {
            return id;
        }

        private final CombatType style;

        public CombatType getStyle() {
            return style;
        }

        private final CombatType weakness;

        public CombatType getWeakness() {
            return weakness;
        }
    }
}

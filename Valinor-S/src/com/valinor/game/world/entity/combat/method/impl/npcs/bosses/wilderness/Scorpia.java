package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.wilderness;

import com.valinor.fs.NpcDefinition;
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
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.NpcIdentifiers.SCORPIA;
import static com.valinor.util.NpcIdentifiers.SCORPIAS_GUARDIAN;

public class Scorpia extends CommonCombatMethod {

    static {
        World.getWorld().definitions().get(NpcDefinition.class, SCORPIA).ignoreOccupiedTiles = true; // so she doesnt get stuck on her babies
        World.getWorld().definitions().get(NpcDefinition.class, SCORPIAS_GUARDIAN).ignoreOccupiedTiles = true;
    }

    private boolean spawnedGuardians = false;
    private final Npc[] guardians = new Npc[2];

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
            mob.animate(mob.attackAnimation());
            if (World.getWorld().rollDie(6, 1))
                target.hit(mob, 20, SplatType.POISON_HITSPLAT);
        }
    }

    private void spawnGuardians(Mob mob, Mob target) {
        if (!spawnedGuardians && mob.hp() < 100) {
            spawnedGuardians = true;
            Area bounds = new Area(mob.getAbsX(), mob.getAbsY(), mob.getAbsX() + mob.getSize() - 1, mob.getAbsY() + mob.getSize() - 1, 0);
            for (int i = 0; i < guardians.length; i++) {
                guardians[i] = new Npc(SCORPIAS_GUARDIAN, new Tile(bounds.randomX(), bounds.randomY(), bounds.level)).spawn(false);
                Npc guardian = guardians[i];
                guardian.face(mob.tile());
                guardian.graphic(144, 20, 0);
                Chain.bound(null).name("ScorpiaGuardiansTask").repeatingTask(4, t -> {

                    if ((guardian != null && guardian.dead()) || target.dead() || !mob.getCombat().inCombat()) {
                        t.stop();
                        return;
                    }

                    var ticksNoHeal = 0;
                    var distanceTo = guardian.tile().distanceTo(mob.tile());
                    if(distanceTo > 5) {
                        ticksNoHeal++;
                    }

                    if(ticksNoHeal == 25) {
                        t.stop();
                        return;
                    }

                    guardian.getMovement().follow(mob);
                    guardian.animate(guardian.attackAnimation());
                    new Projectile(guardian, mob, 109, 30, 100, 43, 31, 0).sendProjectile();
                    mob.heal(World.getWorld().random(1, 10));
                });
            }
        }
    }

    @Override
    public void onHit(Mob mob, Mob target, Hit hit) {
        if (mob.isNpc() && mob.getAsNpc().id() == SCORPIA) {
            spawnGuardians(mob, target);
        }
    }

    @Override
    public void onDeath(Player player, Npc npc) {
        spawnedGuardians = false;
        for (Npc guardian : guardians) {
            if (guardian != null && !guardian.dead())
                guardian.hit(guardian, guardian.hp());
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

package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.wilderness.vetion;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import java.util.ArrayList;
import java.util.List;

public class Vetion extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (mob.hp() <= 125 && !mob.hasAttrib(AttributeKey.VETION_HELLHOUND_SPAWNED)) {
            spawnHellhounds((Npc) mob, target);
        }

        //If the 5 minute timer has expired we revert vetion back to his original form.
        if (!mob.getTimers().has(TimerKey.VETION_REBORN_TIMER) && ((Npc) mob).transmog() == 6612) {
            ((Npc) mob).transmog(6611);
        }

        if (Utils.rollDie(20, 1)) { // 5% chance the target sends his bitch ass lightning
            doMagic(mob, target);
        } else if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target) && World.getWorld().rollDie(2,1)) {
            mob.animate(5499);
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
        } else {
            doMagic(mob, target);
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return 6;
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 6;
    }

    private void doMagic(Mob mob, Mob target) {
        mob.animate(mob.attackAnimation());
        Tile lightning_one = target.tile();
        Tile lightning_two = lightning_one.transform(1, 0);
        Tile lightning_three = lightning_one.transform(1, 1);
        int tileDist = mob.tile().distance(target.tile());
        int delay = Math.max(1, (30 + tileDist * 12) / 30);

        Chain.bound(null).runFn(2, () -> {
            new Projectile(new Tile(mob.tile().x + -1, mob.tile().y + 1), lightning_one, 0, 280, 10 * tileDist, delay, 70, 45, 0).sendProjectile();
            new Projectile(new Tile(mob.tile().x + -1, mob.tile().y + 1), lightning_two, 0, 280, 10 * tileDist, delay, 70, 45, 0).sendProjectile();
            new Projectile(new Tile(mob.tile().x + -1, mob.tile().y + 1), lightning_three, 0, 280, 10 * tileDist, delay, 70, 45, 0).sendProjectile();

            World.getWorld().tileGraphic(281, lightning_one,0, 10 * tileDist);
            World.getWorld().tileGraphic(281, lightning_one, 0, 10 * tileDist);
            World.getWorld().tileGraphic(281, lightning_two, 0, 10 * tileDist);
            World.getWorld().tileGraphic(281, lightning_three, 0, 10 * tileDist);
        }).then(3, () -> {
            if (target != null && (target.tile() == (lightning_one) || target.tile() == (lightning_one.transform(1, 0)) || target.tile() == (lightning_one.transform(1, 1)))) {
                target.hit(mob, World.getWorld().random(30));
            }
        });
        mob.getCombat().delayAttack(6);
    }

    private void spawnHellhounds(Npc vetion, Mob target) {
        vetion.forceChat("Kill my pets!");
        List<Npc> minions = new ArrayList<>();
        for (int index = 0; index < 2; index++) {
            VetionMinion minion = new VetionMinion(vetion, target);
            minions.add(minion);
            World.getWorld().registerNpc(minion);
        }

        vetion.putAttrib(AttributeKey.VETION_HELLHOUND_SPAWNED, true);
        vetion.putAttrib(AttributeKey.MINION_LIST, minions);
    }

    @Override
    public boolean customOnDeath(Hit hit) {
        if (hit.getTarget().isNpc()) {
            Npc purpleVetion = (Npc) hit.getTarget();
            if ((purpleVetion.hp() == 0 || purpleVetion.dead()) && !purpleVetion.<Boolean>getAttribOr(AttributeKey.VETION_REBORN_ACTIVE, false)) {
                purpleVetion.heal(255); // Heal vetion
                purpleVetion.transmog(6612); //Transform into orange vetion
                purpleVetion.setTile(purpleVetion.tile());//Update tile
                purpleVetion.forceChat("Do it again!!");
                purpleVetion.getTimers().register(TimerKey.VETION_REBORN_TIMER, 500);
                purpleVetion.putAttrib(AttributeKey.VETION_REBORN_ACTIVE, true);
                return purpleVetion.transmog() != 6612;
            }
        }
        return false;
    }
}

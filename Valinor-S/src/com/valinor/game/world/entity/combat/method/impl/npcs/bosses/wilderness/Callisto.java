package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.wilderness;

import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.ForceMovementTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.Direction;
import com.valinor.game.world.entity.mob.FaceDirection;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.DumbRoute;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

/**
 * Handles Callisto's combat.
 *
 * @author PVE, Oak did a shitty job. Parts taken from OSS.
 */
public class Callisto extends CommonCombatMethod {

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;//Should be one because melee bear
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        Npc npc = (Npc) mob;
        // At all times, callisto can initiate the heal.
        if (World.getWorld().rollDie(12, 1)) {
            prepareHeal(npc);
        }

        // Determine if we do a special hit, or a regular hit.
        if (CombatFactory.canReach(npc, CombatFactory.MELEE_COMBAT, target) && Utils.percentageChance(80)) {
            target.hit(npc, CombatFactory.calcDamageFromType(npc, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().submit();
            npc.animate(npc.attackAnimation());
        } else if (Utils.percentageChance(80)) {
            fury(npc, target);
        } else {
            roar(npc, target);
        }
    }

    /**
     * Callisto unleashes a shockwave against his target. When this happens, a game message will appear saying that he has used the ability against you,
     * just like Vet'ion's earthquake and Venenatis' web attack. This attack has no cooldown and can hit up to 60 in one attack. Callisto will use this ability much more often
     * the further you are from him. The projectile of this attack is similar to Wind Wave.
     */
    private void fury(Npc npc, Mob target) {
        npc.animate(4925);
        new Projectile(npc, target, 395, 40, 60, 31, 43, 0).sendProjectile();

        Chain.bound(null).name("CallistoFuryTask").runFn(2, () -> {
            target.hit(npc, Utils.random(60), CombatType.MELEE).checkAccuracy().submit();
            ((Player)target).message("Callisto's fury sends an almighty shockwave through you.");
            target.stun(4);
            target.graphic(245, 124, 0);
        });
    }

    /**
     * A blast will appear under Callisto. Even though the game states that Callisto will prepare to heal himself based on the damage dealt to him,
     * he actually heals himself during this time for a small amount.
     */
    private void prepareHeal(Npc npc) {
        npc.graphic(157);
        npc.putAttrib(AttributeKey.CALLISTO_DMG_HEAL, true);
        npc.heal(Utils.random(3, 10));
    }

    /**
     * A small white projectile flies from the player straight to Callisto. When this happens, you will be knocked back several spaces from your current location,
     * and a game message will appear stating "Callisto's roar knocks you backwards.", dealing 3 damage.
     */
    private void roar(Npc npc, Mob target) {
        npc.putAttrib(AttributeKey.CALLISTO_ROAR, true);
        npc.animate(npc.attackAnimation());
        int vecX = (target.getAbsX() - Utils.getClosestX(mob, target.tile()));
        if (vecX != 0)
            vecX /= Math.abs(vecX); // determines X component for knockback
        int vecY = (target.getAbsY() - Utils.getClosestY(mob, target.tile()));
        if (vecY != 0)
            vecY /= Math.abs(vecY); // determines Y component for knockback
        int endX = target.getAbsX();
        int endY = target.getAbsY();
        for (int i = 0; i < 4; i++) {
            if (DumbRoute.getDirection(endX, endY, npc.getZ(), target.getSize(), endX + vecX, endY + vecY) != null) { // we can take this step!
                endX += vecX;
                endY += vecY;
            } else
                break; // cant take the step, stop here
        }
        Direction dir;
        if (vecX == -1)
            dir = Direction.EAST;
        else if (vecX == 1)
            dir = Direction.WEST;
        else if (vecY == -1)
            dir = Direction.NORTH;
        else
            dir = Direction.SOUTH;

        if (endX != target.getAbsX() || endY != target.getAbsY()) { // only do movement if we can take at least one step
            if(target.isPlayer()) {
                int finalEndX = endX;
                int finalEndY = endY;
                Chain.bound(null).runFn(1, () -> {
                    final Player p = target.getAsPlayer();
                    p.lock();
                    p.animate(1157);
                    p.graphic(245, 5, 124);
                    p.hit(mob, 3);
                    p.stun(2, true);
                    int diffX = finalEndX - p.getAbsX();
                    int diffY = finalEndY - p.getAbsY();
                    TaskManager.submit(new ForceMovementTask(p, 3, new ForceMovement(p.tile().clone(), new Tile(diffX, diffY), 10, 60, dir.toInteger())));
                   /* target.getMovement().teleport(finalEndX, finalEndY, npc.getHeight());
                    target.getMovement().force(diffX, diffY, 0, 0, 10, 60, dir);*/
                    p.message("Callisto's roar throws you backwards.");
                    p.unlock();
                });
            }
        }
        npc.clearAttrib(AttributeKey.CALLISTO_ROAR);
    }

}

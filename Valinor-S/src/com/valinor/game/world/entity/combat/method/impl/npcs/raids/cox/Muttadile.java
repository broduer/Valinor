package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cox;

import com.valinor.game.content.raids.party.Party;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;

import static com.valinor.game.world.entity.AttributeKey.MUTTADILE_EATING_STATE;
import static com.valinor.game.world.entity.AttributeKey.MUTTADILE_HEAL_COUNT;
import static com.valinor.util.NpcIdentifiers.MUTTADILE_7562;
import static com.valinor.util.NpcIdentifiers.MUTTADILE_7563;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since October 29, 2021
 */
public class Muttadile extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (mob.isNpc()) {
            Npc npc = mob.getAsNpc();
            Player player = target.getAsPlayer();

            int maxHp = npc.maxHp();
            int currentHp = npc.hp();
            final int hpPercentage = (currentHp * 100 / maxHp);
            //As the muttadiles' health reaches about 40%, they will go to the nearby meat tree and start eating it to replenish health, up to three times each.
            if ((hpPercentage < 40 || npc.<Boolean>getAttribOr(MUTTADILE_EATING_STATE,false)) && npc.<Integer>getAttribOr(MUTTADILE_HEAL_COUNT,0) < 3) {
                // when <40 trigger, then when state is true, still healing
                npc.setEntityInteraction(null);
                npc.getMovement().interpolate(new Tile(3303, 5321, npc.tile().level));
                npc.putAttrib(MUTTADILE_EATING_STATE, true);

                Party party = player.raidsParty;
                if (party == null) return;

                GameObject meatTree = party.getMeatTree();
                if (npc.tile().isWithinDistance(meatTree.tile(), 2)) {
                    npc.noRetaliation(true);
                    npc.face(meatTree.tile());
                    npc.animate(npc.attackAnimation());
                    npc.heal(World.getWorld().random(125, 200));

                    int newHpPercentage = (currentHp * 100 / maxHp);
                    if(newHpPercentage > 70) {
                        var healCount = npc.<Integer>getAttribOr(MUTTADILE_HEAL_COUNT,0) + 1;
                        npc.putAttrib(MUTTADILE_HEAL_COUNT, healCount);
                        npc.putAttrib(MUTTADILE_EATING_STATE, false);
                    }
                }
            } else {
                //The small muttadile attacks with both Melee and Ranged.
                if (npc.id() == MUTTADILE_7562) {//Small
                    if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
                        meleeAttack(npc, target);
                    } else {
                        rangeAttack(npc);
                    }
                    //Apart from having higher stats and using all three forms of combat, this larger muttadile will also perform a shockwave attack if anyone is within melee range,
                    // dealing massive damage to everyone in melee range, which can hit as high as 118.
                } else if (npc.id() == MUTTADILE_7563) {//Big
                    if (CombatFactory.canReach(mob, CombatFactory.MELEE_COMBAT, target)) {
                        if (World.getWorld().rollDie(2, 1)) {
                            shockwaveAttack(npc, target);
                        } else {
                            meleeAttack(npc, target);
                        }
                    } else if (World.getWorld().rollDie(2, 1)) {
                        rangeAttack(npc);
                    } else {
                        magicAttack(npc);
                    }
                }
            }
        }
    }

    private void meleeAttack(Npc npc, Mob target) {
        npc.combatInfo().maxhit = 78;
        npc.animate(npc.attackAnimation());
        target.hit(npc, CombatFactory.calcDamageFromType(npc, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
    }

    private void rangeAttack(Npc npc) {
        for (Mob t : getPossibleTargets(npc, 64, true,false)) {
            npc.combatInfo().maxhit = 35;
            npc.animate(npc.attackAnimation());
            var tileDist = npc.tile().transform(1, 1, 0).distance(t.tile());
            var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
            new Projectile(npc, t, 1291, 20, 12 * tileDist, npc.id() == MUTTADILE_7562 ? 15 : 35, 30, 0).sendProjectile();
            t.hit(npc, CombatFactory.calcDamageFromType(npc, t, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().submit();
        }
    }

    private void magicAttack(Npc npc) {
        for (Mob t : getPossibleTargets(npc, 64, true, false)) {
            npc.combatInfo().maxhit = 45;
            npc.animate(npc.attackAnimation());
            var tileDist = npc.tile().transform(1, 1, 0).distance(t.tile());
            var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
            new Projectile(npc, t, 393, 20, 12 * tileDist, 35, 30, 0).sendProjectile();
            t.hit(npc, CombatFactory.calcDamageFromType(npc, t, CombatType.MAGIC), delay, CombatType.MAGIC).checkAccuracy().submit();
        }
    }

    private void shockwaveAttack(Npc npc, Mob target) {
        npc.combatInfo().maxhit = 118;
        npc.animate(7424);
        target.hit(npc, CombatFactory.calcDamageFromType(npc, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().submit();
        target.message(Color.RED.wrap("You have been hit by the Muttadiles stomp attack!"));
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 6;
    }
}

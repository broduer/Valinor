package com.valinor.game.world.entity.combat.method.impl.npcs.bosses;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 29, 2021
 */
public class Bryophyta extends CommonCombatMethod {

    boolean spawned = false;
    boolean spawnedSecond = false;
    private static final Area BRYOPHYTA_WOODS = new Area(3205, 9923, 3231, 9947);

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (!target.tile().inArea(BRYOPHYTA_WOODS)) {
            mob.getCombat().reset();
            return;
        }
        if (withinDistance(1)) {
            meleeAttack(mob, target);
        } else  {
            mageAttack(mob, target);
        }
    }

    private void meleeAttack(Mob mob, Mob target) {
        mob.face(target.tile());
        mob.animate(mob.attackAnimation());
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), CombatType.MELEE).checkAccuracy().postDamage(this::handleAfterHit).submit();
    }

    private void mageAttack(Mob mob, Mob target) {
        mob.face(target.tile());
        mob.animate(7173);
        new Projectile(mob, target, 124, 35, 100, 20, 31, 0).sendProjectile();
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC),3, CombatType.MAGIC).checkAccuracy().postDamage(this::handleAfterHit).submit();
        target.delayedGraphics(new Graphic(125),3);
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 16;
    }

    public void handleAfterHit(Hit hit) {
        if(hit.getDamage() > 0) {
            hit.getAttacker().heal(10);
        }
    }

    @Override
    public void onHit(Mob mob, Mob target, Hit hit) {
        if(mob.isNpc() && mob.getAsNpc().id() == BRYOPHYTA) {
            if (mob.getAsNpc().hp() <= 85) {
                spawnFirstMinions(target);
            }
            if (mob.getAsNpc().hp() <= 45) {
                spawnSecondMinions(target);
            }
        }
    }

    public void spawnFirstMinions(Mob target) {
        final Mob e = target;
        List<Tile> tiles = e.tile().area(3, tile -> !tile.equals(e.tile()) && tile.clip() == 0);
        if (!spawned) {
            Npc melee1 = new Npc(GROWTHLING, Utils.randomElement(tiles)).spawn(false);
            melee1.graphic(580);
            Npc melee2 = new Npc(GROWTHLING, Utils.randomElement(tiles)).spawn(false);
            melee2.graphic(580);
            Npc melee3 = new Npc(GROWTHLING, Utils.randomElement(tiles)).spawn(false);
            melee3.graphic(580);
            Player p = findPlayerInRoom();
            melee1.getCombat().attack(p);
            melee2.getCombat().attack(p);
            melee3.getCombat().attack(p);
            spawned = true;
        }
    }

    private void spawnSecondMinions(Mob target) {
        final Mob e = target;
        List<Tile> tiles = e.tile().area(3, tile -> !tile.equals(e.tile()) && tile.clip() == 0);
        if (!spawnedSecond) {
            Npc melee1 = new Npc(GROWTHLING, Utils.randomElement(tiles)).spawn(false);
            melee1.graphic(580);
            Npc melee2 = new Npc(GROWTHLING, Utils.randomElement(tiles)).spawn(false);
            melee2.graphic(580);
            Npc melee3 = new Npc(GROWTHLING, Utils.randomElement(tiles)).spawn(false);
            melee3.graphic(580);
            Player p = findPlayerInRoom();
            melee1.getCombat().attack(p);
            melee2.getCombat().attack(p);
            melee3.getCombat().attack(p);
            spawnedSecond = true;
        }
    }

    private Player findPlayerInRoom() {
        ArrayList<Player> targets = new ArrayList<>();
        World.getWorld().getPlayers().forEachInArea(BRYOPHYTA_WOODS, p -> {
            if (p != null) {
                targets.add(p);
            }
        });
        return Utils.randomElement(targets);
    }

    @Override
    public void onDeath(Player player,Npc npc) {
        World.getWorld().getNpcs().forEachInArea(BRYOPHYTA_WOODS, n -> {
            if (n.id() == GROWTHLING) {
                n.hit(n, n.hp());
            }
        });
    }
}

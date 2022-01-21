package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.sarachnis;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 26, 2021
 */
public class Sarachnis extends CommonCombatMethod {

    private static final Area SARACHNIS_AREA = new Area(1830, 9891, 1856, 9911, 0);
    private static final List<Tile> WALK_TO_TILES = Arrays.asList(new Tile(1834, 9894), new Tile(1846, 9894), new Tile(1846, 9905), new Tile(1834, 9906));

    private static int attacks;
    boolean spawned = false;
    boolean spawnedSecond = false;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (attacks == 4) {
            move(mob);
        } else if (withinDistance(1)) {
            meleeAttack(mob, target);
        } else {
            rangedAttack(mob, target);
        }
        attacks++;
    }

    public void move(Mob mob) {
        Chain.bound(null).runFn(1, () -> {
            mob.forceChat("Hsss");
            World.getWorld().getPlayers().forEachInArea(SARACHNIS_AREA, plr -> {
                plr.freeze(6, mob);
                Tile tile = plr.tile().copy();
                GameObject web = new GameObject(34895, tile, 10, 0);
                ObjectManager.spawnTempObject(web,8);
            });
        }).then(1, () -> {
            mob.getCombat().reset();
            mob.lock();
            walk(mob);
        }).waitUntil(1, () -> {
                //System.out.println("eval "+mob.tile()+" vs "+mob.<Tile>getAttrib(AttributeKey.SARACHNIS_RANDOM_MOVE_TILE));
                mob.smartPathTo(mob.<Tile>getAttrib(AttributeKey.SARACHNIS_RANDOM_MOVE_TILE));
             return mob.tile().equals(mob.<Tile>getAttrib(AttributeKey.SARACHNIS_RANDOM_MOVE_TILE));
            }
            , () -> {
            Player p = findPlayerInRoom();
            mob.face(p.tile());
            mob.getCombat().attack(p);
            mob.resetFaceTile();
            attacks = 0;
            mob.unlock();
        });
    }

    public void meleeAttack(Mob mob, Mob target) {
        mob.face(target.tile());
        mob.animate(8147);
        target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 0, CombatType.MELEE).checkAccuracy().postDamage(this::handleAfterHit).submit();
    }

    public void rangedAttack(Mob mob, Mob target) {
        mob.face(target.tile());
        mob.animate(4410);
        World.getWorld().getPlayers().forEachInArea(SARACHNIS_AREA, plr -> {
            var tileDist = mob.tile().transform(1, 1, 0).distance(plr.tile());
            var delay = Math.max(1, (50 + (tileDist * 12)) / 30);
            Projectile projectile = new Projectile(mob, plr, 1686, 35, 20 * tileDist, 31, 35, 0);
            projectile.sendProjectile();
            target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().postDamage(this::handleAfterHit).submit();
        });
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 16;
    }

    @Override
    public void onHit(Mob mob, Mob target, Hit hit) {
        if(mob.isNpc() && mob.getAsNpc().id() == SARACHNIS) {
            if (mob.getAsNpc().hp() <= 266) {
                spawnFirstMinions(mob.getAsNpc(), target);
            }
            if (mob.getAsNpc().hp() <= 133) {
                spawnSecondMinions(mob.getAsNpc(), target);
            }
        }
    }

    @Override
    public void onDeath(Player player, Npc npc) {
        World.getWorld().getNpcs().forEachInArea(SARACHNIS_AREA, n -> {
            if(n.id() == SPAWN_OF_SARACHNIS || n.id() == SPAWN_OF_SARACHNIS_8715) {
                n.hit(n, n.hp());
            }
        });
    }

    public void handleAfterHit(Hit hit) {
        if(hit.getAttacker().isNpc()) {
            Npc npc = hit.getAttacker().getAsNpc();
            if (hit.getDamage() > 0)
                npc.heal(10);
        }
    }

    public void spawnFirstMinions(Mob mob, Mob target) {
        final Mob e = target;
        List<Tile> tiles = e.tile().area(3, tile -> !tile.equals(e.tile()) && tile.clip() == 0);
        if (!spawned) {
            Npc mage = new Npc(SPAWN_OF_SARACHNIS_8715, Utils.randomElement(tiles)).spawn(false);
            Npc melee = new Npc(SPAWN_OF_SARACHNIS, Utils.randomElement(tiles)).spawn(false);
            Player p = findPlayerInRoom();
            mage.face(p.tile());
            melee.face(p.tile());
            mob.getCombat().attack(p);
            spawned = true;
        }
    }

    private void spawnSecondMinions(Mob mob, Mob target) {
        final Mob e = target;
        List<Tile> tiles = e.tile().area(3, tile -> !tile.equals(e.tile()) && tile.clip() == 0);
        if (!spawnedSecond) {
            Npc mage = new Npc(SPAWN_OF_SARACHNIS_8715, Utils.randomElement(tiles)).spawn(false);
            Npc melee = new Npc(SPAWN_OF_SARACHNIS, Utils.randomElement(tiles)).spawn(false);
            Player p = findPlayerInRoom();
            mage.face(p.tile());
            melee.face(p.tile());
            mob.getCombat().attack(p);
            spawnedSecond = true;
        }
    }

    private void walk(Mob mob) {
        Tile currentPos = mob.tile().copy();
        Tile newPos = Utils.randomElement(WALK_TO_TILES);
        if (currentPos != newPos) {
            mob.putAttrib(AttributeKey.SARACHNIS_RANDOM_MOVE_TILE, newPos);
            mob.getMovement().walkTo(newPos);
        }
    }

    private Player findPlayerInRoom() {
        ArrayList<Player> targets = new ArrayList<>();
        World.getWorld().getPlayers().forEachInArea(SARACHNIS_AREA, p -> {
            if (p != null) {
                targets.add(p);
            }
        });
        return Utils.randomElement(targets);
    }
}

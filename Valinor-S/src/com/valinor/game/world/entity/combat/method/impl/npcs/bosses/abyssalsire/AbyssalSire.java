package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.abyssalsire;

import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.valinor.util.NpcIdentifiers.SPAWN;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 22, 2021
 */
public class AbyssalSire extends CommonCombatMethod {

    //animations
    private static final int SIRE_PHASE_1_SPAWN = 4530;
    private static final int SIRE_PHASE_1_POISON = 4531;
    private static final int SIRE_PHASE_2_MELEE = 5366;
    private static final int SIRE_PHASE_2_STRONG_MELEE = 5369;
    private static final int SIRE_PHASE_2_STRONGER_MELEE = 5755;

    //gfx
    private static final int POISON_EFFECT = 1275;

    private final List<Npc> spawns = new LinkedList<>();
    private int attempts = 0;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        if (++attempts == 5) {
            teleportPlayer(mob, target);
            attempts = 0;
        }
        if (World.getWorld().rollDie(6, 4))
            meleeAttack(mob, target);
        else if (World.getWorld().rollDie(2, 1))
            poisonPool(mob, target, target.tile().copy());
        else
            projectileSpawn(mob, target);
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 12;
    }

    private Player findRandomPlayer(Mob mob) {
        ArrayList<Player> targets = new ArrayList<>();
        World.getWorld().getPlayers().forEachInRegion(mob.tile().region(), p -> {
            if (p != null) {
                targets.add(p);
            }
        });
        return Utils.randomElement(targets);
    }

    private void teleportPlayer(Mob mob, Mob target) {
        if (target == null) target = findRandomPlayer(mob);
        final Player player = target.getAsPlayer();
        Teleports.basicTeleport(player, new Tile(mob.getAbsX() + 2, mob.getAbsY() - 1, mob.getZ()), 1816, new Graphic(342));
    }

    private void meleeAttack(Mob mob, Mob target) { // phase 2 ONLY
        double roll = World.getWorld().get();
        int minDamage, maxDamage;
        if (roll < 0.25) { // double back swipe
            minDamage = 8;
            maxDamage = 26;
            mob.animate(SIRE_PHASE_2_STRONGER_MELEE);
        } else if (roll < 0.65) { // single back swipe
            minDamage = 4;
            maxDamage = 12;
            mob.animate(SIRE_PHASE_2_STRONG_MELEE);
        } else { // arm swipe
            minDamage = 2;
            maxDamage = 6;
            mob.animate(SIRE_PHASE_2_MELEE);
        }
        if (target.getAsPlayer() != null) {
            if (!Prayers.usingPrayer(target.getAsPlayer(), Prayers.PROTECT_FROM_MELEE)) {
                minDamage *= 2;
                maxDamage *= 2;
            }
        }
        target.hit(mob, World.getWorld().random(minDamage, maxDamage), 0, CombatType.MELEE).submit();
    }

    private void projectileSpawn(Mob mob, Mob target) {
        spawns.removeIf(n -> mob.dead());
        if (spawns.size() >= 14)
            return;
        mob.animate(SIRE_PHASE_1_SPAWN);
        Tile dest = World.getWorld().get(target.tile().area(2, tile -> tile.clip() == 0));
        Projectile projectile = new Projectile(mob.tile(), dest, -1, 1274, 100, 50, 70, 0, 0);
        projectile.sendProjectile();
        final Mob entity = target;
        Chain.bound(null).runFn(3, () -> {
            if (mob.dead())
                return;
            spawn(entity, target, dest);
        });
    }

    private void spawn(Mob mob, Mob target, Tile dest) {
        spawns.removeIf(n -> mob.dead());
        if (spawns.size() >= 14)
            return;
        if (target == null) {
            return;
        }
        Npc spawn = new Npc(SPAWN, dest);
        spawn.spawn(false);
        spawn.getCombat().setTarget(target);
        spawn.getCombat().attack(target);
        spawns.add(spawn);
    }

    private void poisonPool(Mob mob, Mob target, Tile tile) {
        mob.animate(SIRE_PHASE_1_POISON);
        // phase 3 and 4 don't do an animation on spawning a pool
        Chain.bound(null).runFn(1, () -> World.getWorld().tileGraphic(POISON_EFFECT, tile, 0, 0)).then(2, () -> World.getWorld().getPlayers().forEachInRegion(target.tile().region(), p -> {
            int distance = Utils.getDistance(p.tile(), tile);
            if (distance > 1)
                return;
            int maxDamage = 30;
            if ((int) p.getAttribOr(AttributeKey.POISON_TICKS, 0) != 0)
                maxDamage *= 0.7;
            maxDamage /= distance + 1;
            p.hit(mob, World.getWorld().random(1, maxDamage), SplatType.POISON_HITSPLAT);
            if (World.getWorld().rollDie(5, 1))
                p.poison(8);
        }));
    }

    @Override
    public void onDeath(Player player,Npc npc) {
        for(Npc n : spawns) {
            if(n.isRegistered() || !n.dead()) {
                n.remove(player);
            }
        }
    }
}

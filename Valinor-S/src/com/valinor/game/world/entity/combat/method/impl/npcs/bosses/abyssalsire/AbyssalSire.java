package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.abyssalsire;

import com.valinor.fs.NpcDefinition;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.RouteDirection;
import com.valinor.util.TickDelay;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 22, 2021
 */
public class AbyssalSire extends CommonCombatMethod {

    //animations
    private static final int TENTACLE_WAKE_UP_ANIM = 7108;
    private static final int TENTACLE_ATTACK_ANIM = 7109;
    private static final int TENTACLE_WAKE_UP_ANIM_2 = 7111;
    private static final int TENTACLE_SLEEP_ANIM = 7112;
    private static final int TENTACLE_WAKE_UP_ANIM_3 = 7114;

    private static final int SIRE_WAKE_ANIM = 4528;
    private static final int SIRE_PHASE_1_SPAWN = 4530;
    private static final int SIRE_PHASE_1_POISON = 4531;
    private static final int SIRE_PHASE_1_TRANSITION = 4532;

    private static final int SIRE_PHASE_2_MELEE = 5366;
    private static final int SIRE_PHASE_2_POISON = 5367;
    private static final int SIRE_PHASE_2_SPAWN = 7095;
    private static final int SIRE_PHASE_2_STRONG_MELEE = 5369;
    private static final int SIRE_PHASE_2_STRONGER_MELEE = 5755;
    private static final int SIRE_PHASE_2_TRANSITION = 7096;

    private static final int SIRE_PHASE_3_EXPLODE = 7098;

    //gfx
    private static final int POISON_EFFECT = 1275;

    //npc ids
    private static final int STUNNED_TENTACLE = 5911;
    private static final int ACTIVE_TENTACLE = 5912;
    private static final int SPAWN = 5916;

    private List<Npc> tentacles = new ArrayList<>(6);
    private List<Npc> respiratorySystemNPCs = new ArrayList<>(4);
    private List<GameObject> respiratorySystemObjects = new ArrayList<>(4);
    private List<Npc> spawns = new LinkedList<>();
    private Npc nwTentacle, wTentacle, swTentacle, neTentacle, eTentacle, seTentacle;

    private int phase = 0;
    private int activatedBy = -1;
    private TickDelay resetDelay = new TickDelay();
    private int attempts = 0;

    static {
        for (int id : Arrays.asList(5886, 5887, 5888, 5889, 5890, 5891, 5908, 5916, 5917, 5918))
            World.getWorld().definitions().get(NpcDefinition.class, id).ignoreOccupiedTiles = true;
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        switch (phase) {
            case 1:
                mob.face(RouteDirection.SOUTH);
                if (World.getWorld().rollDie(3, 1)) { // doesn't actually attack every turn
                    if (World.getWorld().rollDie(4, 1))
                        projectileSpawn(mob, target);
                    else
                        poisonPool(mob, target, target.tile().copy());
                }
                break;
            case 2:
                if (!withinDistance(1)) {
                    if (mob.getMovement().isAtDestination()) {
                        if (++attempts == 5) {
                            teleportPlayer(mob, target);
                            attempts = 0;
                        }
                    }
                    return;
                } else {
                    if (World.getWorld().rollDie(6, 4))
                        meleeAttack(mob, target);
                    else if (World.getWorld().rollDie(2, 1))
                        poisonPool(mob, target, target.tile().copy());
                    else
                        projectileSpawn(mob, target);
                }
                break;
            case 3:
                mob.resetFaceTile();
                mob.face(RouteDirection.SOUTH);
                poisonPool(mob, target, target.tile().copy());
                if (World.getWorld().rollDie(3, 1))
                    spawn(mob, target);
                break;
            case 4:
                mob.resetFaceTile();
                mob.face(RouteDirection.SOUTH);
                spawn(mob, target);
                if (World.getWorld().rollDie(2, 1)) // anotha one
                    spawn(mob, target);
                break;
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return 12;
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return mob.getBaseAttackSpeed();
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
        if (phase != 2)
            throw new IllegalStateException();
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
        target.hit(mob, World.getWorld().random(minDamage, maxDamage));
    }

    private void projectileSpawn(Mob mob, Mob target) {
        spawns.removeIf(n -> mob.dead());
        if (spawns.size() >= 14)
            return;
        if (phase == 1)
            mob.animate(SIRE_PHASE_1_SPAWN);
        else
            mob.animate(SIRE_PHASE_2_SPAWN); // phases 3 and 4 don't do projectile-based spawns!
        Tile dest = World.getWorld().get(target.tile().area(2, tile -> tile.clip() == 0));
        Projectile projectile = new Projectile(mob.tile(), dest, -1,1274,100, 50, 70,0,0);
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

    private void spawn(Mob mob, Mob target) {
        spawn(mob, target, World.getWorld().get(target.tile().area(2, pos -> pos.clip() == 0)));
    }

    private void poisonPool(Mob mob, Mob target, Tile tile) {
        if (phase == 1)
            mob.animate(SIRE_PHASE_1_POISON);
        else if (phase == 2)
            mob.animate(SIRE_PHASE_2_POISON);
        // phase 3 and 4 don't do an animation on spawning a pool
        Chain.bound(null).runFn(1, () -> {
            World.getWorld().tileGraphic(POISON_EFFECT, tile, 0, 0);
        }).then(2, () -> {
            for (int i = 0; i < 4; i++) {
                World.getWorld().getPlayers().forEachInRegion(target.tile().region(), p -> {
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
                });
            }
        });
    }
}

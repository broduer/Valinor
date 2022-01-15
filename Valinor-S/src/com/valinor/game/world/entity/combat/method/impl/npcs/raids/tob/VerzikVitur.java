package com.valinor.game.world.entity.combat.method.impl.npcs.raids.tob;

import com.valinor.fs.NpcDefinition;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.DumbRoute;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import java.util.List;
import java.util.stream.Stream;

import static com.valinor.util.ItemIdentifiers.DAWNBRINGER;
import static com.valinor.util.NpcIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.TREASURE_ROOM;
import static com.valinor.util.ObjectIdentifiers.VERZIKS_THRONE_32737;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 07, 2022
 */
public class VerzikVitur extends CommonCombatMethod {

    private static final Tile CENTRE = new Tile(3167, 4311);
    private static final int OUT_OF_CHAIR = 8111;
    private static final int CHAIR_ATTACK = 8109;
    private static final Tile SPIDER_SPAWN = new Tile(3171, 4315);
    private static final Area ARENA = new Area(3154, 4303, 3182, 4322);
    private int bombCount = 0;
    private int electricCount = 0;

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        set(mob, target);
        List<Mob> targets = getPossibleTargets(mob);
        final Tile tile = target.tile();
        if (mob.getAsNpc().id() == VERZIK_VITUR_8370) {
            mob.animate(CHAIR_ATTACK);
            for (Mob t : targets) {
                if (t == null || t.getAsPlayer().dead() || !t.tile().isWithinDistance(mob.tile(), 32) || !t.tile().inArea(ARENA)) {
                    continue;
                }
                final Tile t_tile = t.tile();
                Projectile projectile = new Projectile(mob.tile().center(mob.getSize()).transform(-1,0), target.tile(), -1,1580, 220, 0, 100, 0,0);
                int dmg = Prayers.usingPrayer(t, Prayers.PROTECT_FROM_MAGIC) ? World.getWorld().random(1, 60) : World.getWorld().random(1, 137);
                handleDodgableAttack(mob, t, projectile, null, dmg, 7, new Task("VerzikViturPrepareAttackTask1", 1) {
                    int count = 0;
                    @Override
                    public void execute() {
                        count++;
                        if (count == 8) {
                            World.getWorld().tileGraphic(1582, t_tile,0,0);
                            Stream<Mob> ts = targets.stream().filter(n -> !n.equals(t) && n.tile().withinDistanceIgnoreHeight(t_tile, 1));
                            int dmg = Prayers.usingPrayer(t, Prayers.PROTECT_FROM_MAGIC) ? World.getWorld().random(1, 60) : World.getWorld().random(1, 137);
                            ts.forEach(t -> t.hit(mob, dmg, 0, CombatType.MAGIC).setAccurate(false).checkAccuracy().submit());
                            stop();
                        }
                    }
                });
            }
        }
        if (mob.getAsNpc().id() == VERZIK_VITUR_8372) {
            mob.animate(8114);
            if (bombCount < 4) {
                for (Mob t : targets) {
                    if (t == null || t.getAsPlayer().dead() || !t.tile().isWithinDistance(mob.tile(), 32) || !t.tile().inArea(ARENA)) {
                        continue;
                    }
                    final Tile t_tile = t.tile();
                    Projectile projectile = new Projectile(mob, t, 1583, 0, 130, 100, 0,0);
                    handleDodgableAttack(mob, t, projectile, null, World.getWorld().random(1, 60),5, new Task("VerzikViturPrepareAttackTask2", 1) {
                        int count = 0;
                        @Override
                        public void execute() {
                            count++;
                            if (count == 5) {
                                World.getWorld().tileGraphic(1584, t_tile,0,0);
                                Stream<Mob> ts = targets.stream().filter(n -> !n.equals(t) && n.tile().withinDistanceIgnoreHeight(t_tile, 1));
                                ts.forEach(t -> t.hit(mob, World.getWorld().random(1, 60), 0, CombatType.MAGIC).setAccurate(false).checkAccuracy().submit());
                                stop();
                            }
                        }
                    });
                }
                bombCount++;
            } else {
                if (electricCount < 2) {
                    for (Mob t : targets) {
                        if (t == null || t.getAsPlayer().dead() || !t.tile().isWithinDistance(mob.tile(), 32) || !t.tile().inArea(ARENA)) {
                            continue;
                        }
                        mob.hit(t, target.hp() < 2 ? 1 : 20, 3, CombatType.MAGIC).submit();
                    }
                    electricCount++;
                    bombCount = 0;
                } else {
                    Projectile projectile = new Projectile(mob, target, 1586, 0, 130, 100, 0,0);
                    handleDodgableAttack(mob, target, projectile, null, World.getWorld().random(1, 60), 5, new Task("VerzikViturPrepareAttackTask3", 1) {
                        int count = 0;
                        Npc healer;
                        Npc bomber;

                        @Override
                        public void execute() {
                            count++;
                            if (count == 5 && healer == null && bomber == null) {
                                healer = new Npc(NYLOCAS_ATHANATOS, tile).spawn(false);
                                healer.animate(8079);
                                healer.face(mob.tile());
                                bomber = new Npc(NYLOCAS_MATOMENOS_8385, SPIDER_SPAWN).spawn(false);
                                bomber.getCombat().setTarget(target);
                                bomber.animate(8098);
                            }
                            if (count >= 5 && healer != null) {
                                if (healer.hp() < healer.maxHp()) {
                                    healer.hit(healer, healer.hp());
                                }
                            }
                            if (count >= 15) {
                                if (bomber != null)
                                    bomber.hit(healer, healer.hp());
                                if (healer != null)
                                    healer.hit(healer, healer.hp());
                                stop();
                            }
                            if ((count % 2 == 0) && healer != null && !healer.dead()) {
                                Projectile projectile = new Projectile(mob, target, 1578, 0, 100, 50, 25,0);
                                projectile.sendProjectile();
                                mob.hit(mob, 6, SplatType.NPC_HEALING_HITSPLAT);
                            }
                        }
                    });
                    electricCount = 0;
                }
            }
        }
        if (mob.getAsNpc().id() == VERZIK_VITUR_8374) {
            int random = World.getWorld().random(0, 2);
            if (random == 0) {
                mob.animate(8123);
                mob.hit(target, World.getWorld().random(1, 40), 2, CombatType.MELEE).submit();
            } else if (random == 1) {
                mob.animate(8124);
                for (Mob t : targets) {
                    if (t == null || t.getAsPlayer().dead() || !t.tile().inArea(ARENA)) {
                        continue;
                    }
                    Projectile projectile = new Projectile(mob, target, 1580, 0, 220, 100, 0,0);
                    projectile.sendProjectile();
                    t.hit(mob, World.getWorld().random(1, 40), 2, CombatType.MAGIC).checkAccuracy().submit();
                }
            } else if (random == 2) {
                mob.animate(8125);
                for (Mob t : targets) {
                    if (t == null || t.getAsPlayer().dead() || !t.tile().inArea(ARENA)) {
                        continue;
                    }
                    Projectile projectile = new Projectile(mob, target, 1560, 0, 100, 25, 30,0);
                    projectile.sendProjectile();
                    t.hit(mob, World.getWorld().random(1, 40), 2, CombatType.RANGED).checkAccuracy().submit();
                }
            }
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return mob.getAsNpc().id() == VERZIK_VITUR_8374 ? 4 : 32;
    }

    @Override
    public void process(Mob mob, Mob target) {
        if (mob.getAsNpc().id() == VERZIK_VITUR_8371 || mob.getAsNpc().id() == VERZIK_VITUR_8372) {
            if (!mob.tile().equals(CENTRE.transform(0,0, mob.tile().level))) {
                DumbRoute.route(mob, CENTRE.getX(), CENTRE.getY());
            } else {
                mob.getAsNpc().transmog(VERZIK_VITUR_8372);
                mob.getAsNpc().def(World.getWorld().definitions().get(NpcDefinition.class, VERZIK_VITUR_8372));
            }
        }
       mob.getTimers().cancel(TimerKey.FROZEN);
    }

    @Override
    public void onHit(Mob attacker, Mob verzik, Hit hit) {
        if(verzik.isNpc()) {
            if (verzik.getAsNpc().id() == VERZIK_VITUR_8370) {
                hit.setSplatType(SplatType.VERZIK_SHIELD_HITSPLAT);
            }
            if (verzik.getAsNpc().id() == VERZIK_VITUR_8371 || verzik.getAsNpc().id() == VERZIK_VITUR_8375) {
                hit.setDamage(0);
                hit.setSplatType(SplatType.BLOCK_HITSPLAT);
            }
        }
    }

    @Override
    public boolean customOnDeath(Mob mob) {
        List<Mob> targets = getPossibleTargets(mob);
        target = Utils.randomElement(targets);
        return transform(mob, ((Player) target));
    }

    private boolean transform(Mob mob, Player player) {
        var party = player.raidsParty;
        if (party == null) {
            return false;
        }
        if (mob.getAsNpc().id() == VERZIK_VITUR_8370) {
            List<Mob> targets = getPossibleTargets(mob);
            mob.getAsNpc().canAttack(false);
            mob.animate(OUT_OF_CHAIR);
            mob.getCombat().reset();
            mob.resetFaceTile();
            targets.forEach(t -> {
                t.getAsPlayer().removeAll(new Item(DAWNBRINGER));
                t.getAsPlayer().getCombat().reset();
            });
            Task task = new Task("VerzikViturTransformTask", 4) {

                @Override
                public void execute() {
                    mob.getAsNpc().transmog(VERZIK_VITUR_8371);
                    mob.getAsNpc().def(World.getWorld().definitions().get(NpcDefinition.class, VERZIK_VITUR_8371));
                    mob.heal(mob.maxHp());
                    GameObject gameObject = new GameObject(VERZIKS_THRONE_32737, new Tile(3167, 4324, mob.tile().level), 10,0);
                    gameObject.spawn();
                    mob.getAsNpc().animate(-1);
                    mob.getAsNpc().canAttack(true);
                    stop();
                }
            };
            TaskManager.submit(task);
            return true;
        } else if (mob.getAsNpc().id() == VERZIK_VITUR_8372) {
            mob.getAsNpc().canAttack(false);
            mob.animate(8119);
            mob.getAsNpc().transmog(VERZIK_VITUR_8374);
            mob.getAsNpc().def(World.getWorld().definitions().get(NpcDefinition.class, VERZIK_VITUR_8374));
            mob.heal(mob.maxHp());
            Task task = new Task("VerzikViturTransformTask2", 4) {

                @Override
                public void execute() {
                    mob.animate(-1);
                    mob.forceChat("Behold my true nature!");
                    mob.getAsNpc().canAttack(true);
                    stop();
                }
            };
            TaskManager.submit(task);
            return true;
        } else if (mob.getAsNpc().id() == VERZIK_VITUR_8374) {
            mob.getAsNpc().canAttack(false);
            mob.getAsNpc().transmog(VERZIK_VITUR_8375);
            Chain.bound(null).runFn(5, () -> World.getWorld().unregisterNpc(mob.getAsNpc()));
            GameObject gameObject = new GameObject(TREASURE_ROOM, new Tile(3167, 4324, mob.tile().level), 10,0);
            gameObject.spawn();

            List<Player> players = party.getMembers();
            players.forEach(p -> {
                p.message("You have defeated Verzik Vitur!");
                p.getCombat().reset();
            });

            if (party != null) {
                if (party.getLeader().getRaids() != null) {
                    party.getLeader().getRaids().complete(party);
                }
            }
            return true;
        }
        return false;
    }
}

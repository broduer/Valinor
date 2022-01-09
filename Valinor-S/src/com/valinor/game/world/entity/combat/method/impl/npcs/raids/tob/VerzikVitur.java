package com.valinor.game.world.entity.combat.method.impl.npcs.raids.tob;

import com.valinor.game.content.raids.theatre_of_blood.TheatreOfBloodRewards;
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
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.DumbRoute;
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
        List<Mob> targets = getPossibleTargets();
        final Tile tile = target.tile();
        if (mob.getAsNpc().id() == VERZIK_VITUR_8370) {
            mob.animate(CHAIR_ATTACK);
            for (Mob t : targets) {
                if (t == null || t.getAsPlayer().dead() || !t.tile().isWithinDistance(mob.tile(), 32) || !t.tile().inArea(ARENA)) {
                    continue;
                }
                final Tile t_tile = t.tile();
                Projectile projectile = new Projectile(mob, target, 1580, 0, 220, 100, 0,0);
                handleDodgableAttack(mob, t, CombatType.MAGIC, projectile, null, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), new Task("VerzikViturPrepareAttackTask1", 1) {
                    int count = 0;
                    @Override
                    public void execute() {
                        count++;
                        if (count == 8) {
                            World.getWorld().tileGraphic(1582, t_tile,0,0);
                            Stream<Mob> ts = targets.stream().filter(n -> !n.equals(t) && n.tile().withinDistanceIgnoreHeight(t_tile, 1));
                            ts.forEach(t -> target.hit(t, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), 0, CombatType.MAGIC).setAccurate(false).checkAccuracy().submit());
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
                    handleDodgableAttack(mob, t, CombatType.MAGIC, projectile, null, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), new Task("VerzikViturPrepareAttackTask2", 1) {
                        int count = 0;
                        @Override
                        public void execute() {
                            count++;
                            if (count == 5) {
                                World.getWorld().tileGraphic(1584, t_tile,0,0);
                                Stream<Mob> ts = targets.stream().filter(n -> !n.equals(t) && n.tile().withinDistanceIgnoreHeight(t_tile, 1));
                                ts.forEach(t -> target.hit(t, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), 0, CombatType.MAGIC).setAccurate(false).checkAccuracy().submit());
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
                    handleDodgableAttack(mob, target, CombatType.MAGIC, projectile, null, CombatFactory.calcDamageFromType(mob, target, CombatType.MAGIC), new Task("VerzikViturPrepareAttackTask3", 1) {
                        int count = 0;
                        Npc healer;
                        Npc bomber;

                        @Override
                        public void execute() {
                            count++;
                            if (count == 5 && healer == null && bomber == null) {
                                healer = new Npc(8384, tile).spawn(false);
                                //healer.attackTimer = Integer.MAX_VALUE;
                                healer.animate(8079);
                                healer.face(mob.tile());
                                bomber = new Npc(8385, SPIDER_SPAWN).spawn(false);
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
                mob.hit(target, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE), 2, CombatType.MELEE).submit();
            } else if (random == 1) {
                mob.animate(8124);
                for (Mob t : targets) {
                    if (t == null || t.getAsPlayer().dead() || !t.tile().inArea(ARENA)) {
                        continue;
                    }
                    Projectile projectile = new Projectile(mob, target, 1580, 0, 220, 100, 0,0);
                    projectile.sendProjectile();
                    target.hit(t, CombatFactory.calcDamageFromType(mob, t, CombatType.MAGIC), 2, CombatType.MAGIC).checkAccuracy().submit();
                }
            } else if (random == 2) {
                mob.animate(8125);
                for (Mob t : targets) {
                    if (t == null || t.getAsPlayer().dead() || !t.tile().inArea(ARENA)) {
                        continue;
                    }
                    Projectile projectile = new Projectile(mob, target, 1560, 0, 100, 25, 30,0);
                    projectile.sendProjectile();
                    target.hit(t, CombatFactory.calcDamageFromType(mob, t, CombatType.RANGED), 2, CombatType.RANGED).checkAccuracy().submit();
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
            if (!mob.tile().equalsIgnoreHeight(CENTRE)) {
                DumbRoute.route(mob, CENTRE.getX(), CENTRE.getY());
            } else {
                mob.getAsNpc().transmog(VERZIK_VITUR_8372);
            }
        }
       mob.getTimers().cancel(TimerKey.FROZEN);
    }

    @Override
    public void onHit(Mob mob, Mob target, Hit hit) {
        if (mob.getAsNpc().id() == 8370) {
            hit.setSplatType(SplatType.BLOCK_HITSPLAT);
            //damage.setHitmark(Hitmark.DAWNBRINGER);
        }
        if (mob.getAsNpc().id() == VERZIK_VITUR_8371 || mob.getAsNpc().id() == VERZIK_VITUR_8375) {
            hit.setDamage(0);
            hit.setSplatType(SplatType.BLOCK_HITSPLAT);
        }
        if (mob.hp() - hit.getDamage() <= 0) { //would of died
            if (transform(mob, target.getAsPlayer())) {
                mob.setHitpoints(mob.maxHp());
            }
        }
    }

    private boolean transform(Mob mob, Player player) {
        var party = player.raidsParty;
        if (party == null) {
            return false;
        }
        if (mob.getAsNpc().id() == 8370) {
            List<Mob> targets = getPossibleTargets();
            mob.getAsNpc().canAttack(false);
            mob.animate(OUT_OF_CHAIR);
            mob.getCombat().reset();
            mob.resetFaceTile();
            targets.forEach(t -> {
                t.getAsPlayer().inventory().remove(DAWNBRINGER, 28);
                if (t.getAsPlayer().getEquipment().hasAt(EquipSlot.WEAPON, DAWNBRINGER)) {
                    t.getAsPlayer().getEquipment().remove(new Item(DAWNBRINGER), EquipSlot.WEAPON, true);
                }
                t.getAsPlayer().getCombat().reset();
            });
            Task task = new Task("VerzikViturTransformTask", 4) {

                @Override
                public void execute() {
                    mob.getAsNpc().transmog(VERZIK_VITUR_8371);
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
            GameObject gameObject = new GameObject(TREASURE_ROOM, new Tile(3167, 4324, mob.tile().level), 10,0);
            gameObject.spawn();

            List<Player> players = party.getMembers();
            players.forEach(p -> {
                p.message("You have defeated Verzik Vitur!");
                /*
                 * pet chance
                 */
                if (World.getWorld().rollDie(650, 1)) {
                    TheatreOfBloodRewards.unlockLilZik(player);
                }
                p.getCombat().reset();
            });
            return true;
        }
        return false;
    }
}

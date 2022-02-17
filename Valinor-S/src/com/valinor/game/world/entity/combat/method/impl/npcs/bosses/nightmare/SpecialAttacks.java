package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 28, 2021
 */
public enum SpecialAttacks {

    GRASPING_CLAWS(8598, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
//			nm.forceText("Grasping claws " + System.currentTimeMillis());
            ArrayList<Tile> tiles = new ArrayList<Tile>();
            for (int x = 23; x < 41; x++) {
                l:
                for (int y = 6; y < 24; y++) {
                    Tile riftTile = nm.getBase().transform(x, y);
                    for (Npc n : World.getWorld().getNpcs()) {
                        if (n == null) continue;
                        if (n.getCentrePosition().distance(riftTile) < (n.getSize() > 1 ? 2 : 1)) {
                            continue l;
                        }
                    }
                    if (World.getWorld().random(10) < 3) {
                        tiles.add(riftTile);
                    }
                }
            }

            //final List<GroundItem> groundItems = new ArrayList<>();
            //groundItems.addAll(GroundItemHandler.getGroundItems());
            //groundItems.forEach(GroundItemHandler::sendRemoveGroundItem);
            for (Tile tile : tiles) {
                World.getWorld().tileGraphic(1767, tile, 0, 0);
                //GroundItemHandler.createGroundItem(new GroundItem(new Item(VIAL_OF_WATER), tile, null));
            }
            Chain.bound(null).runFn(2, () -> {
                World.getWorld().getPlayers().forEachInRegion(nm.tile().region(), p -> {
                    if (tiles.contains(p.tile())) {
                        p.hit(nm, World.getWorld().random(50));
                    }
                });
            });
        }

    },

    SLEEPWALKERS(-1, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
            World.getWorld().getPlayers().forEachInRegion(nm.tile().region(), player -> {
                player.message("<col=ff0000>The Nightmare begins to charge up a devastating attack.");
            });
            Chain.bound(null).runFn(1, () -> {
                int count = Math.min(nm.playersInRegion(), 24);
                ArrayList<Tile> spots = nm.getSleepwalkerTiles();
                Collections.shuffle(spots);
                for (int i = 0; i < count; i++) {
                    int id = 9446 + World.getWorld().random(5);
                    Sleepwalker sw = new Sleepwalker(id, new Tile(spots.get(i).getX(), spots.get(i).getY(), spots.get(i).getZ()));
                    sw.spawn(false);
                    sw.setNm(nm);
                }
            }).then(2, () -> {
                nm.animate(8604);
            }).then(1, () -> {
                World.getWorld().getPlayers().forEachInRegion(nm.tile().region(), player -> {
                    player.graphic(1782);
                });
            }).then(8, () -> {
                World.getWorld().getPlayers().forEachInRegion(nm.tile().region(), player -> {
                    player.hit(nm, nm.getSleepwalkerCount() * 5);
                });
                //nm.transmog(9425 + nm.getStage());
                nm.setSleepwalkerCount(0);
            });
        }
    },

    HUSKS(8599, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
            //If there are more than 4 husks spawned, we do not continue
            if(nm.husksSpawned.size() > 4) {
                return;
            }
            int size = nm.playersInRegion() > 1 ? nm.playersInRegion() / 2 : nm.playersInRegion();
            ArrayList<Mob> targets = nm.getCombatMethod().getPossibleTargets(nm, 64, true, false);
            Collections.shuffle(targets);
            for (int i = 0; i < size; i++) {
                Player target = (Player) targets.get(i);
                int rX = ((target.tile().region() >> 8) << 6), rY = ((target.tile().region() & 0xFF) << 6);
                Tile[] pos = null;
                if (target.tile().getX() - rX >= 23 || (target.tile().getX() - rX <= 41) || target.tile().getY() - rY >= 6 || (target.tile().getY() - rY <= 24)) {
                    if (target.tile().getY() - rY >= 6 || (target.tile().getY() - rY <= 24)) {
                        pos = new Tile[]{target.tile().transform(-1, 0), target.tile().transform(1, 0)};
                    } else {
                        pos = new Tile[]{target.tile().transform(0, 1), target.tile().transform(0, -1)};
                    }
                } else {
                    if (World.getWorld().random(10) > 5) {
                        pos = new Tile[]{target.tile().transform(-1, 0), target.tile().transform(1, 0)};
                    } else {
                        pos = new Tile[]{target.tile().transform(0, 1), target.tile().transform(0, -1)};
                    }
                }
                if (pos != null) {
                    Husk husk = new Husk(9466, pos[0], target, nm);
                    husk.spawn(false);
                    nm.husksSpawned.add(husk);
                    Husk husk2 = new Husk(9467, pos[1], target, nm);
                    husk2.spawn(false);
                    nm.husksSpawned.add(husk2);
                }
            }
        }
    },

    FLOWER_POWER(8601, Nightmare.CENTER) {
        @Override
        public void run(Nightmare nm) {
            if (nm.getFlowerRotary() != -1) {
                return;
            }
            int rand = World.getWorld().random(FlowerRotary.values().length - 1);
            World.getWorld().getPlayers().forEachInRegion(nm.tile().region(), p -> {
                p.message("<col=ff0000>The Nightmare splits the area into segments!");
            });
            nm.setFlowerRotary(rand);
            FlowerRotary pattern = FlowerRotary.values()[rand];
            Tile center = nm.getBase().transform(32, 15);
            ArrayList<GameObject> flowers = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                GameObject lightFlower = new GameObject(37743, center.transform(0, pattern.getLight()[1] * i), 10, 0);
                GameObject lightFlower2 = new GameObject(37743, center.transform(pattern.getLight()[0] * i, 0), 10, 0);
                GameObject darkFlower = new GameObject(37740, center.transform(pattern.getDark()[0] * i, 0), 10, 0);
                GameObject darkFlower2 = new GameObject(37740, center.transform(0, pattern.getDark()[1] * i), 10, 0);
                flowers.add(lightFlower);
                flowers.add(lightFlower2);
                flowers.add(darkFlower);
                flowers.add(darkFlower2);
                lightFlower.spawn();
                lightFlower2.spawn();
                darkFlower.spawn();
                darkFlower2.spawn();
            }
            Chain.bound(null).runFn(1, () -> {
                for (GameObject flower : flowers) {
                    flower.animate(flower.definition().anInt2281 + 1);
                }
            }).then(1, () -> {
                for (GameObject flower : flowers) {
                    flower.setId(flower.getId() + 1);
                }
            }).then(6, () -> {
                for (GameObject flower : flowers) {
                    flower.animate(flower.definition().anInt2281 + 1);
                }
            }).then(1, () -> {
                for (GameObject flower : flowers) {
                    flower.setId(flower.getId() + 1);
                }
            }).then(10, () -> {
                for (GameObject flower : flowers) {
                    flower.animate(flower.definition().anInt2281 + 1);
                }
                World.getWorld().getPlayers().forEachInRegion(nm.tile().region(), p -> {
                    if (!pattern.safe(center, p.tile())) {
                        p.message("You failed to make it back to the safe area.");
                        p.hit(nm, 50);
                    }
                });
            }).then(1, () -> {
                for (GameObject flower : flowers) {
                    flower.remove();
                }
                nm.setFlowerRotary(-1);
            });
        }
    },

    CURSE(8600, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
            Chain.bound(null).runFn(2, () -> {
                World.getWorld().getPlayers().forEachInRegion(nm.tile().region(), p -> {
                    if (Prayers.usingPrayer(p, Prayers.PROTECT_FROM_MISSILES)) {
                        Prayers.deactivatePrayer(p, Prayers.PROTECT_FROM_MISSILES);
                        Prayers.deactivatePrayer(p, Prayers.PROTECT_FROM_MELEE);
                    } else if (Prayers.usingPrayer(p, Prayers.PROTECT_FROM_MELEE)) {
                        Prayers.deactivatePrayer(p, Prayers.PROTECT_FROM_MELEE);
                        Prayers.deactivatePrayer(p, Prayers.PROTECT_FROM_MAGIC);
                    } else if (Prayers.usingPrayer(p, Prayers.PROTECT_FROM_MAGIC)) {
                        Prayers.deactivatePrayer(p, Prayers.PROTECT_FROM_MAGIC);
                        Prayers.deactivatePrayer(p, Prayers.PROTECT_FROM_MISSILES);
                    }
                    p.putAttrib(AttributeKey.NIGHTMARE_CURSE, System.currentTimeMillis() + 30000);
                    p.message("<col=ff0000>The Nightmare has cursed you, shuffling your prayers!");
                });
            });
        }

    },

    PARASITES(8606, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
            for (Mob victim : nm.getCombatMethod().getPossibleTargets(nm)) {
                Player p = (Player) victim;
                if (World.getWorld().random(6) == 3) continue;
                Projectile pr = new Projectile(nm, victim, 1770, 30, 66, 110, 90, 0);
                pr.sendProjectile();
                p.message("<col=ff0000>The Nightmare has impregnated you with a deadly parasite!");
                p.putAttrib(AttributeKey.NIGHTMARE_BABY_DADY, true);
            }
            final List<Parasite> parasites = new ArrayList<Parasite>();
            Chain.bound(null).runFn(28, () -> {
                for (Mob victim : nm.getCombatMethod().getPossibleTargets(nm)) {
                    if (victim.<Boolean>getAttribOr(AttributeKey.NIGHTMARE_BABY_DADY, false)) {
                        Player p = (Player) victim;
                        p.message("<col=ff0000>The parasite bursts out of you, fully grown!");
                        p.graphic(1779);
                    }
                }
            }).then(2, () -> {
                for (Mob victim : nm.getCombatMethod().getPossibleTargets(nm)) {
                    if (victim.<Boolean>getAttribOr(AttributeKey.NIGHTMARE_BABY_DADY, false)) {
                        victim.graphic(1765);
                        Parasite parasite = (Parasite) new Parasite(World.getWorld().random(5) == 3 ? 9469 : 9468, victim.tile().copy()).spawn(false);
                        parasite.getCombat().setTarget(victim);
                        parasites.add(parasite);
                        victim.clearAttrib(AttributeKey.NIGHTMARE_BABY_DADY);
                    }
                }
            }).then(3, () -> {
                for (Parasite parasite : parasites) {
                    parasite.unlock();
                    //parasite.getCombat().setTarget(nm);
                    parasite.face(nm.tile());
                }
            });
        }
    },

    SURGE(8597, Nightmare.EDGE) {
        @Override
        public void run(Nightmare nm) {
//			ForceMovement forceMovement = ForceMovement.create(0, 10, 0, 18, 33, 60, WalkingQueue.NORTH);
//			forceMovement.setDestination(nm.tile().transform(0, 18, 0));
//			forceMovement.setDelay(3); //this needs to be set as a param
//			World.getWorld().submit(new Tickable(1) {
//
//				@Override
//				public void execute() {
//					nm.playAnimation(Animation.create(8597));
//					nm.setForceWalk(forceMovement, true);
//					nm.getUpdateFlags().flag(UpdateFlag.FORCE_MOVEMENT);
//					nm.face(forceMovement.getDestination());
//					this.stop();
//				}
//			});
        }

    },

    SPORES(8599, Nightmare.NO_TELEPORT) {
        @Override
        public void run(Nightmare nm) {
            World.getWorld().getPlayers().forEachInRegion(nm.tile().region(), player -> {
                player.message("<col=ff0000>The Nightmare summons some infectious spores!");
            });
            int[][] spores = {{10, 7}, {15, 5}, {18, 10}, {15, 10}, {10, 7}, {5, 10}, {9, 10}, {6, 10}, {10, 13}, {14, 10}, {10, 12}};
            for (int[] ints : spores) {
                Spore spore = new Spore(nm.getBase().transform(ints[0], ints[1]));
                //System.out.println(spore.tile());
                Chain.bound(null).runFn(1, () -> {
                    spore.animate(spore.definition().anInt2281 + 1);
                }).then(3, () -> {
                    spore.setId(37739);
                });
                spore.spawn();
            }
        }

    };

    int animation;

    int teleportOption;

    SpecialAttacks(int animation, int teleportOption) {
        this.animation = animation;
        this.teleportOption = teleportOption;
    }

    public void run(Nightmare nm) {

    }

}

package com.valinor.game.content.instance.impl;

import com.valinor.game.content.instance.InstancedArea;
import com.valinor.game.content.instance.InstancedAreaManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.NpcIdentifiers;

import java.util.*;

import static com.valinor.util.NpcIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.ALTAR_28924;
import static com.valinor.util.ObjectIdentifiers.AWAKENED_ALTAR;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 19, 2021
 */
public class SkotizoInstance {

    /**
     * The Skotizo instance
     */
    private InstancedArea instance;

    /**
     * get the instance
     *
     * @return the instance
     */
    public InstancedArea getInstance() {
        return instance;
    }

    public SkotizoInstance() {

    }

    public List<Npc> npcList = new ArrayList<>();
    public List<GameObject> objectList = new ArrayList<>();
    public Npc skotizo;
    public GameObject southAltarEmpty;
    public GameObject northAltarEmpty;
    public GameObject westAltarEmpty;
    public GameObject eastAltarEmpty;

    public static final Tile ENTRANCE_POINT = new Tile(1695, 9878);
    public static final Area SKOTIZO_AREA = new Area(1678, 9870, 1714, 9905);

    public boolean northAltar, southAltar, eastAltar, westAltar, ankouSpawned, demonsSpawned = false;

    public Map<Integer, String> altarMap = Collections.synchronizedMap(new HashMap<>());

    private final String[] altarMapDirection = {"NORTH", "SOUTH", "WEST", "EAST"};

    public int altarCount = 0;

    /**
     * Constructs the content by creating an event
     */
    public void init(Player player) {
        instance = InstancedAreaManager.getSingleton().createInstancedArea(player, SKOTIZO_AREA);
        if (player != null && instance != null) {
            npcList.clear();
            player.teleport(ENTRANCE_POINT.transform(0, 0, instance.getzLevel()));

            //Create a Skotizo instance
            skotizo = new Npc(SKOTIZO, new Tile(1688, 9880, instance.getzLevel())).spawn(false);
            skotizo.getCombat().setTarget(player);
            skotizo.getCombat().attack(player);
            npcList.add(skotizo);

            player.getPacketSender().sendChangeSprite(29232, (byte) 0).sendChangeSprite(29233, (byte) 0).sendChangeSprite(29234, (byte) 0).sendChangeSprite(29235, (byte) 0);

            southAltarEmpty = new GameObject(ALTAR_28924, new Tile(1696, 9871, instance.getzLevel()), 10, 0);
            objectList.add(southAltarEmpty);
            northAltarEmpty = new GameObject(ALTAR_28924, new Tile(1694, 9904, instance.getzLevel()), 10, 2);
            objectList.add(northAltarEmpty);
            westAltarEmpty = new GameObject(ALTAR_28924, new Tile(1678, 9888, instance.getzLevel()), 10, 1);
            objectList.add(westAltarEmpty);
            eastAltarEmpty = new GameObject(ALTAR_28924, new Tile(1714, 9888, instance.getzLevel()), 10, 3);
            objectList.add(eastAltarEmpty);
        }
    }

    private int getAltar() {
        return (World.getWorld().random(3) + 1);
    }

    public int damageReducctionEffect(Player target, int damage) {
        if (target.getSkotizoInstance().altarCount == 1) {
            damage = (int)(damage * .75);
        } else if (target.getSkotizoInstance().altarCount == 2) {
            damage = (int)(damage * .50);
        } else if (target.getSkotizoInstance().altarCount == 3) {
            damage = (int)(damage * .25);
        } else if (target.getSkotizoInstance().altarCount == 4) {
            damage = 0;
        }
        return damage;
    }

    public void summonMinions(Player player) {
        int random = World.getWorld().random(10);
         if (random == 2 || random == 3) {
            if (skotizo.hp() < 225 && !demonsSpawned) {
                skotizo.forceChat("Gar mulno ful taglo!");
                Npc reanimatedDemon1 = new Npc(REANIMATED_DEMON, new Tile(player.getX() + 1, player.getY(), instance.getzLevel())).spawn(false);
                Npc reanimatedDemon2 = new Npc(REANIMATED_DEMON, new Tile(player.getX() - 1, player.getY(), instance.getzLevel())).spawn(false);
                Npc reanimatedDemon3 = new Npc(REANIMATED_DEMON, new Tile(player.getX(), player.getY() + 1, instance.getzLevel())).spawn(false);
                reanimatedDemon1.getCombat().attack(player);
                reanimatedDemon2.getCombat().attack(player);
                reanimatedDemon3.getCombat().attack(player);
                npcList.add(reanimatedDemon1);
                npcList.add(reanimatedDemon2);
                npcList.add(reanimatedDemon3);
                demonsSpawned = true;
            }
        } else if (random == 4 && World.getWorld().random(5) == 0) {
            if (skotizo.hp() < 150 && !ankouSpawned) {
                Npc darkAnkou = new Npc(DARK_ANKOU, new Tile(player.getX(), player.getY() - 1, instance.getzLevel())).spawn(false);
                darkAnkou.getCombat().attack(player);
                npcList.add(darkAnkou);
                ankouSpawned = true;
            }
        }
    }

    public void awakenAltars(Player player) {
        if(skotizo.dead()) {
            return;
        }

        int random = World.getWorld().random(11);

        if (random == 1) {
            try {
                int altarNumber = getAltar();
                boolean unique = false;

                while (!unique) {
                    if ((altarMap.get(1) != null && altarMap.get(1).equals("NORTH"))
                            && (altarMap.get(2) != null && altarMap.get(2).equals("SOUTH"))
                            && (altarMap.get(3) != null && altarMap.get(3).equals("WEST"))
                            && (altarMap.get(4) != null && altarMap.get(4).equals("EAST"))) {
                        break;
                    }
                    String altar = altarMap.get(altarNumber);
                    if (altar == null) {
                        altarMap.put(altarNumber, altarMapDirection[altarNumber - 1]);
                        unique = true;
                        switch (altarMapDirection[altarNumber - 1]) {
                            case "NORTH" -> {
                                player.message("<col=ff7000>The north altar has just awakened!");
                                player.getPacketSender().sendChangeSprite(29232, (byte) 1);
                                ObjectManager.removeObj(northAltarEmpty);// Remove North - Empty Altar
                                objectList.remove(northAltarEmpty);
                                GameObject altarNorth = new GameObject(AWAKENED_ALTAR, new Tile(1694, 9904, instance.getzLevel()), 10, 2);// North - Awakened Altar
                                ObjectManager.addObj(altarNorth);
                                Npc awakenedAltar = new Npc(NpcIdentifiers.AWAKENED_ALTAR, new Tile(1694, 9904, instance.getzLevel())).spawn(false);
                                npcList.add(awakenedAltar);
                                altarCount++;
                                northAltar = true;
                            }
                            case "SOUTH" -> {
                                player.message("<col=ff7000>The south altar has just awakened!");
                                player.getPacketSender().sendChangeSprite(29233, (byte) 1);
                                ObjectManager.removeObj(southAltarEmpty);// Remove South - Empty Altar
                                objectList.remove(southAltarEmpty);
                                GameObject altarSouth = new GameObject(AWAKENED_ALTAR, new Tile(1696, 9871, instance.getzLevel()), 10, 2);// South - Awakened Altar
                                ObjectManager.addObj(altarSouth);
                                objectList.add(altarSouth);
                                Npc awakenedAltar = new Npc(NpcIdentifiers.AWAKENED_ALTAR_7290, new Tile(1696, 9871, instance.getzLevel())).spawn(false);
                                npcList.add(awakenedAltar);
                                altarCount++;
                                southAltar = true;
                            }
                            case "WEST" -> {
                                player.message("<col=ff7000>The west altar has just awakened!");
                                player.getPacketSender().sendChangeSprite(29234, (byte) 1);
                                ObjectManager.removeObj(westAltarEmpty);// Remove West - Empty Altar
                                objectList.remove(westAltarEmpty);
                                GameObject altarWest = new GameObject(AWAKENED_ALTAR, new Tile(1678, 9888, instance.getzLevel()), 10, 2);// West - Awakened Altar
                                ObjectManager.addObj(altarWest);
                                objectList.add(altarWest);
                                Npc awakenedAltar = new Npc(NpcIdentifiers.AWAKENED_ALTAR_7292, new Tile(1678, 9888, instance.getzLevel())).spawn(false);
                                npcList.add(awakenedAltar);
                                altarCount++;
                                westAltar = true;
                            }
                            case "EAST" -> {
                                player.message("<col=ff7000>The east altar has just awakened!");
                                player.getPacketSender().sendChangeSprite(29235, (byte) 1);
                                ObjectManager.removeObj(eastAltarEmpty);// Remove East - Empty Altar
                                objectList.remove(eastAltarEmpty);
                                GameObject altarEast = new GameObject(AWAKENED_ALTAR, new Tile(1714, 9888, instance.getzLevel()), 10, 2);// East - Awakened Altar
                                ObjectManager.addObj(altarEast);
                                objectList.add(altarEast);
                                Npc awakenedAltar = new Npc(NpcIdentifiers.AWAKENED_ALTAR_7294, new Tile(1714, 9888, instance.getzLevel())).spawn(false);
                                npcList.add(awakenedAltar);
                                altarCount++;
                                eastAltar = true;
                            }
                        }
                    } else {
                        altarNumber = getAltar();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clear(Player player) {
        for (Npc npc : npcList) {
            World.getWorld().unregisterNpc(npc);
        }
        npcList.clear();

        for(GameObject obj : objectList) {
            ObjectManager.removeObj(obj);
        }
        objectList.clear();

        altarMap.clear();

        player.getPacketSender().sendChangeSprite(29232, (byte) 0).sendChangeSprite(29233, (byte) 0).sendChangeSprite(29234, (byte) 0).sendChangeSprite(29235, (byte) 0);

        for (GroundItem gi : GroundItemHandler.getGroundItems()) {
            if (!gi.getTile().inArea(SKOTIZO_AREA))
                continue;

            GroundItemHandler.sendRemoveGroundItem(gi);
        }
    }

}

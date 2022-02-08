package com.valinor.game.content.skill.impl.farming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.common.base.Preconditions;
import com.valinor.fs.ItemDefinition;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

public class Farming {

    public static final int HARVEST_ANIMATION = 2275;

    /**
     * Placeholder for Tree harvesting
     */
    public static final int HARVEST_CHOPPING = -2;

    private static final int[] farmingOutfit = { 13646, 13642, 13640, 13644 };

    private final Player player;
    private final Plant[] plants = new Plant[50];
    private final GrassyPatch[] patches = new GrassyPatch[50];

    public Farming(Player player) {
        this.player = player;

        for (int i = 0; i < patches.length; i++)
            if (patches[i] == null)
                patches[i] = new GrassyPatch();
    }

    public static int getFarmingPieces(Player player) {
        int pieces = 0;
        for (int aFarmingOutfit : farmingOutfit) {
            if (player.getEquipment().contains(aFarmingOutfit)) {
                pieces++;
            }
        }
        return pieces;
    }

    public void handleLogin() {
        doConfig();
        Chain.bound(null).name("farming_task").repeatingTask(1, t -> {
            if(player == null || !player.isRegistered()) {
                t.stop();
                return;
            }
            sequence();
        });
    }

    public void handleObjectClick(int objectId, int x, int y, int option) {
        player.getFarming().click(player, x, y, option);
    }

    public void handleItemOnObject(int itemId, int objectId, int objectX, int objectY) {
        if (plant(itemId, objectX, objectY))
            return;
        if (useItemOnPlant(itemId, objectX, objectY))
            return;
    }

    public void regionChanged() {
        player.getFarming().doConfig();
    }

    public void sequence() {
        for (Plant i : plants) {
            if (i != null) {
                i.process(player);
            }
        }
        for (int i = 0; i < patches.length; i++) {
            if (i >= FarmingPatches.values().length)
                break;
            if ((patches[i] != null) && (!inhabited(FarmingPatches.values()[i].bottomLeft.getX(), FarmingPatches.values()[i].bottomLeft.getY()))) {
                patches[i].process(player, i);
            }
        }
    }

    public int config(FarmingPatches patch) {
        if (inhabited(patch.bottomLeft.getX(), patch.bottomLeft.getY())) {
            for (Plant plant : plants) {
                if (plant != null && plant.getPatch() == patch) {
                    return plant.getConfig();
                }
            }
        }

        return patches[patch.ordinal()].stage;
    }

    public void doConfig() {
        FarmingPatches[] patches = {
            FarmingPatches.FALADOR_HERB,
            FarmingPatches.CATHERBY_HERB,
            FarmingPatches.ARDOUGNE_HERB,
            FarmingPatches.PHAS_HERB
        };

        FarmingPatches closest = null;
        int lowest = 0;
        for (FarmingPatches patch : patches) {
            int dist = (int) Utils.distance(player.tile().x, player.tile().y, patch.bottomLeft.getX(), patch.bottomLeft.getY());
            if (closest == null || dist < lowest) {
                closest = patch;
                lowest = dist;
            }
        }

        int config = 0;

        switch (closest) {
            case FALADOR_HERB:
                config = (config(FarmingPatches.FALADOR_HERB) << 24)
                    + (config(FarmingPatches.FALADOR_FLOWER) << 16)
                    + (config(FarmingPatches.FALADOR_ALLOTMENT_SOUTH) << 8)
                    + (config(FarmingPatches.FALADOR_ALLOTMENT_NORTH));
                break;
            case CATHERBY_HERB:
                /**
                 * //		529(<<0) = Fruit Tree
                 * //		529(<<24) = Herb
                 * //		529(<<16) = Flower
                 * //		529(<<8) = South Allotment
                 * //		529(<<0) = North Allotment
                 * //		511(<<24) = Compost Bin
                 */
                config = (config(FarmingPatches.CATHERBY_HERB) << 24)
                    + (config(FarmingPatches.CATHERBY_FLOWER) << 16)
                    + (config(FarmingPatches.CATHERBY_ALLOTMENT_SOUTH) << 8)
                    + (config(FarmingPatches.CATHERBY_ALLOTMENT_NORTH));
                break;
            case ARDOUGNE_HERB:
                config = (config(FarmingPatches.ARDOUGNE_HERB) << 24)
                    + (config(FarmingPatches.ARDOUGNE_FLOWER) << 16)
                    + (config(FarmingPatches.ARDOUGNE_ALLOTMENT_SOUTH) << 8)
                    + (config(FarmingPatches.ARDOUGNE_ALLOTMENT_NORTH));
                break;
            case PHAS_HERB:
                config = (config(FarmingPatches.PHAS_HERB) << 24)
                    + (config(FarmingPatches.PHAS_FLOWER) << 16)
                    + (config(FarmingPatches.PHAS_ALLOTMENT_EAST) << 8)
                    + (config(FarmingPatches.PHAS_ALLOTMENT_WEST));
                break;
        }
        player.getPacketSender().sendConfigByte(529, config);
    }

    public void clear() {
        for (int i = 0; i < plants.length; i++) {
            plants[i] = null;
        }

        for (int i = 0; i < patches.length; i++) {
            patches[i] = new GrassyPatch();
        }
    }

    public void insert(Plant patch) {
        for (int i = 0; i < plants.length; i++)
            if (plants[i] == null) {
                plants[i] = patch;
                break;
            }
    }

    public boolean inhabited(int x, int y) {
        for (int i = 0; i < plants.length; i++) {
            if (plants[i] != null) {
                FarmingPatches patch = plants[i].getPatch();
                if ((x >= patch.bottomLeft.getX()) && (y >= patch.bottomLeft.getY()) && (x <= patch.topLeft.getX()) && (y <= patch.topLeft.getY())) {
                    if (isPatchException(x, y, patch)) {
                        continue;
                    }
                    return true;
                }
            }
        }

        return false;
    }

    public int getGrassyPatch(int x, int y) {
        for (int i = 0; i < FarmingPatches.values().length; i++) {
            FarmingPatches patch = FarmingPatches.values()[i];
            if (x >= patch.bottomLeft.getX() && y >= patch.bottomLeft.getY() && x <= patch.topLeft.getX() && y <= patch.topLeft.getY()) {
                if (!isPatchException(x, y, patch)) {
                    if (inhabited(x, y) || patches[i] == null)
                        break;
                    return i;
                }
            }
        }

        return -1;
    }

    public Plant getPlantedPatch(int x, int y) {
        for (int i = 0; i < FarmingPatches.values().length; i++) {
            FarmingPatches patch = FarmingPatches.values()[i];
            if (x >= patch.bottomLeft.getX() && y >= patch.bottomLeft.getY() && x <= patch.topLeft.getX() && y <= patch.topLeft.getY()) {
                if (!isPatchException(x, y, patch)) {
                    for (Plant plant : plants) {
                        if (plant != null && plant.patch == patch.ordinal()) {
                            return plant;
                        }
                    }
                }
            }
        }

        return null;
    }

    private boolean isPatchException(int x, int y, FarmingPatches patch) {
        if(x == 3054 && y == 3307 && patch != FarmingPatches.FALADOR_FLOWER)
            return true;
        if (x == 3601 && y == 3525 && patch != FarmingPatches.PHAS_FLOWER)
            return true;
        return false;
    }

    public boolean click(Player player, int x, int y, int option) {
        int grass = getGrassyPatch(x, y);
        if (grass != -1) {
            if (option == 1) {
                patches[grass].click(player, option, grass);
            }
            return true;
        } else {
            Plant plant = getPlantedPatch(x, y);

            if (plant != null) {
                plant.click(player, option);
                return true;
            }
        }

        return false;
    }

    public void remove(Plant plant) {
        for (int i = 0; i < plants.length; i++) {
            if ((plants[i] != null) && (plants[i] == plant)) {
                patches[plants[i].getPatch().ordinal()].setTime();
                plants[i] = null;
                doConfig();
                return;
            }
        }
    }

    public boolean useItemOnPlant(int item, int x, int y) {
        if (item == ItemIdentifiers.RAKE) {
            int patch = getGrassyPatch(x, y);
            if (patch != -1) {
                patches[patch].rake(player, patch);
                return true;
            }
        }

        Plant plant = getPlantedPatch(x, y);
        if (plant != null) {
            plant.useItemOnPlant(player, item);
            return true;
        }

        return false;
    }

    public boolean plant(int seed, int x, int y) {
        if (!Plants.isSeed(seed)) {
            return false;
        }

        for (FarmingPatches patch : FarmingPatches.values()) {
            if ((x >= patch.bottomLeft.getX()) && (y >= patch.bottomLeft.getY()) && (x <= patch.topLeft.getX()) && (y <= patch.topLeft.getY())) {
                if (isPatchException(x, y, patch)) {
                    continue;
                }
                if (!patches[patch.ordinal()].isRaked()) {
                    player.message("This patch needs to be raked before anything can grow in it.");
                    return true;
                }

                for (Plants plant : Plants.values()) {
                    if (plant.seed == seed) {
                        if (player.skills().level(Skills.FARMING) >= plant.level) {
                            if (inhabited(x, y)) {
                                player.message("There are already seeds planted here.");
                                return true;
                            }

                            if (patch.seedType != plant.type) {
                                player.message("You can't plant this type of seed here.");
                                return true;
                            }

                            if (player.inventory().contains(patch.planter)) {
                                player.animate(2291);
                                player.message("You bury the seed in the dirt.");
                                player.inventory().remove(seed, 1);
                                Plant planted = new Plant(patch.ordinal(), plant.ordinal());
                                planted.setTime();
                                insert(planted);
                                if (player.inventory().contains(25025)) { // Magic watering can (doesn't exist in osrs)
                                    planted.watered = -1;
                                    planted.magicCan = true;
                                    player.message("Your magic watering can waters and fertilizes the soil.");
                                }
                                doConfig();
                                player.skills().addXp(Skills.FARMING, (int)plant.plantExperience, true);
                            } else {
                                String name = World.getWorld().definitions().get(ItemDefinition.class, patch.planter).name;
                                player.message("You need " + Utils.getAOrAn(name) + " " +name+ " to plant seeds.");
                            }

                        } else {
                            player.message("You need a Farming level of " + plant.level + " to plant this.");
                        }

                        return true;
                    }
                }

                return false;
            }
        }

        return false;
    }

    private String getDirectory() {
        return "./data/saves/farming/";
    }

    private String getFile() {
        return getDirectory() + player.getUsername() + ".txt";
    }

    public void save() {
        try {
            if (!new File(getDirectory()).exists()) {
                Preconditions.checkState(new File(getDirectory()).mkdirs());
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()));
            for (int i = 0; i < patches.length; i++) {
                if (i >= FarmingPatches.values().length)
                    break;
                if (patches[i] != null) {
                    writer.write("[PATCH]");
                    writer.newLine();
                    writer.write("patch: "+i);
                    writer.newLine();
                    writer.write("stage: "+patches[i].stage);
                    writer.newLine();
                    writer.write("time: "+patches[i].time);
                    writer.newLine();
                    writer.write("END PATCH");
                    writer.newLine();
                    writer.newLine();
                }
            }
            for (int i = 0; i < plants.length; i++) {
                if (plants[i] != null) {
                    writer.write("[PLANT]");
                    writer.newLine();
                    writer.write("patch: "+plants[i].patch);
                    writer.newLine();
                    writer.write("plant: "+plants[i].plant);
                    writer.newLine();
                    writer.write("stage: "+plants[i].stage);
                    writer.newLine();
                    writer.write("watered: "+plants[i].watered);
                    writer.newLine();
                    writer.write("harvested: "+plants[i].harvested);
                    writer.newLine();
                    writer.write("magicCan: "+plants[i].magicCan);
                    writer.newLine();
                    writer.write("time: "+plants[i].time);
                    writer.newLine();
                    writer.write("END PLANT");
                    writer.newLine();
                    writer.newLine();
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (!new File(getFile()).exists())
                return;
            BufferedReader r = new BufferedReader(new FileReader(getFile()));
            int stage = -1, patch = -1, plant = -1, watered = -1, harvested = -1;
            boolean magicCan = false;
            long time = -1;
            while(true) {
                String line = r.readLine();
                if(line == null) {
                    break;
                } else {
                    line = line.trim();
                }
                if(line.startsWith("patch"))
                    patch = Integer.valueOf(line.substring(line.indexOf(":")+2));
                else if(line.startsWith("stage"))
                    stage = Integer.valueOf(line.substring(line.indexOf(":")+2));
                else if(line.startsWith("plant"))
                    plant = Integer.valueOf(line.substring(line.indexOf(":")+2));
                else if(line.startsWith("watered"))
                    watered = Integer.valueOf(line.substring(line.indexOf(":")+2));
                else if(line.startsWith("harvested"))
                    harvested = Integer.valueOf(line.substring(line.indexOf(":")+2));
                else if(line.startsWith("magicCan"))
                    magicCan = Boolean.valueOf(line.substring(line.indexOf(":")+2));
                else if(line.startsWith("time"))
                    time = Long.valueOf(line.substring(line.indexOf(":")+2));
                else if(line.equals("END PATCH") && patch >= 0) {
                    patches[patch].stage = (byte)stage;
                    patches[patch].time = time;
                    patch = -1;
                }
                else if(line.equals("END PLANT") && patch >= 0) {
                    plants[patch] = new Plant(patch, plant);
                    plants[patch].watered = (byte) watered;
                    plants[patch].stage = (byte) stage;
                    plants[patch].harvested = (byte) harvested;
                    plants[patch].time = time;
                    plants[patch].magicCan = magicCan;
                    patch = -1;
                }
            }
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//Catherby
//		529(<<0) = Fruit Tree
//		529(<<24) = Herb
//		529(<<16) = Flower
//		529(<<8) = South Allotment
//		529(<<0) = North Allotment
//		511(<<24) = Compost Bin
//		Falador
//		529(<<0) = Tree
//		529(<<24) = Herb
//		529(<<16) = Flower
//		529(<<8) = South Allotment
//		529(<<0) = North Allotment
//		511(<<0) = Compost Bin
//		Ardougne
//		529(<<24) = Herb
//		529(<<16) = Flower
//		529(<<8) = South Allotment
//		529(<<0) = North Allotment
//		529(<<24) = Compost Bin
//		529(<<0) = Bush
//		Port Phasmatys
//		529(<<24) = Herb
//		529(<<16) = Flower
//		529(<<8) = South Allotment
//		529(<<0) = North Allotment
//		511(<<16) = Compost Bin
//		Lumbridge
//		529(<<0) = Tree
//		Taverley
//		529(<<0) = Tree
//		Varrock
//		529(<<0) = Tree
//		529(<<0) = Bush
//		Gnome Stronghold
//		529(<<0) = Tree
//		529(<<8) = Fruit Tree
//		Tree Gnome Village
//		529(<<0) = Fruit Tree
//		Brimhaven
//		529(<<0) = Fruit Tree
//		Rimmington
//		529(<<0) = Bush
//		Etceteria
//		529(<<0) = Bush
//		Al Kharid
//		529(<<0) = Cactus

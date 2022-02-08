package com.valinor.game.content.skill.impl.farming;

import com.google.common.collect.Lists;
import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.List;

public class Plant {

    public int patch;
    public int plant;
    public long time = 0;
    public byte stage = 0;
    public byte disease = -1;
    public byte watered = 0;
    public boolean magicCan = false;

    private final boolean dead = false;

    public byte harvested = 0;
    boolean harvesting = false;

    public Plant(int patchId, int plantId) {
        patch = patchId;
        plant = plantId;
    }

    public void water(Player player, int item) {
        if (item == 5332) {
            return;
        }

        if (getPatch().seedType == SeedType.HERB) {
            player.message("This patch doesn't need watering.");
            return;
        }

        if (isWatered()) {
            player.message("Your plants have already been watered.");
            return;
        }

        if (item == 5331) {
            player.message("Your watering can is empty.");
            return;
        }

        player.message("You water the plant.");
//		if (item != 25025) {
//			player.getItems().deleteItem(item, 1);
//			player.getItems().addItem(item > 5333 ? (item - 1) : (item - 2), 1);
//		}
        player.animate(2293);
        watered = -1;
        doConfig(player);
    }

    public void setTime() {
        time = System.currentTimeMillis();
    }

    public void click(Player player, int option) {
        Plants plant = Plants.values()[this.plant];
        if (option == 1) {
            if (stage == plant.stages) {
                harvest(player);
            } else if (plant.type == SeedType.HERB) {
                statusMessage(player, plant);
            }
        } else if ((option == 2)) {
            statusMessage(player, plant);
        }
    }

    private void statusMessage(Player player, Plants plant) {
        if (dead) {
            player.message("Oh dear, your plants have died!");
        } else if (isDiseased()) {
            player.message("Your plants are diseased!");
        } else if (stage == plant.stages) {
            player.message("Your plants are healthy and ready to harvest.");
        } else {
            int stagesLeft = plant.stages - stage;
            String s = "Your plants are healthy";
            if(!isWatered() && getPatch().seedType != SeedType.HERB)
                s += " but need some water to survive.";
            else {
                s += " and are currently growing (about " + (stagesLeft * (plant.getMinutes() / plant.stages)) + " minutes remain).";
            }
            player.message(s);
        }
    }

    public void harvest(final Player player) {
        if(harvesting)
            return;
        List<Integer> harvestItemNeeded = Lists.newArrayList();
        harvestItemNeeded.add(getPatch().harvestItem);
        if (getPatch().harvestItem == ItemIdentifiers.SECATEURS) {
            harvestItemNeeded.add(ItemIdentifiers.MAGIC_SECATEURS);
            harvestItemNeeded.add(ItemIdentifiers.MAGIC_SECATEURS);
        }

        if (harvestItemNeeded.stream().anyMatch(item -> player.inventory().contains(item) || player.getEquipment().contains(item))) {
            final Plant instance = this;
            Chain.bound(null).repeatingTask(3, t -> {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.message("Your inventory is full.");
                    t.stop();
                    return;
                }
                player.animate(2282);
                Item add;
                int id = Plants.values()[plant].harvest;
                ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, id);
                add = def.noted() ? new Item(def.notelink, 1) : new Item(id, 1);
                player.inventory().add(add.getId(), add.getAmount());

                int petRate = player.<Boolean>getAttribOr(AttributeKey.PETS_SCROLL,false) ? (int) (10000 * .75) : 10000;
                if (World.getWorld().random(petRate) == 2) {
                    UnlockFarmingPet.unlockTangleroot(player);
                }

                def = World.getWorld().definitions().get(ItemDefinition.class, Plants.values()[plant].harvest);
                String name = def.name;
                if (name.endsWith("s"))
                    name = name.substring(0, name.length() - 1);
                player.message("You harvest " + Utils.getAOrAn(name) + " " + name + ".");
                player.skills().addXp(Skills.FARMING,(int) Plants.values()[plant].harvestExperience * (1 + (Farming.getFarmingPieces(player) * 0.12)), true);

                harvested++;

                int min = 7;
                if (magicCan)
                    min += 7;
                if (player.getEquipment().contains(ItemIdentifiers.MAGIC_SECATEURS))
                    min += 4;

                AchievementsManager.activate(player, Achievements.FARMER,1);
                if (id == 225) {
                    player.getInventory().addOrDrop(new Item(225, 2));
                    player.getFarming().remove(instance);
                    t.stop();
                    return;
                }
                if (getPatch().seedType == SeedType.FLOWER || harvested >= min && World.getWorld().random(4) <= 1) {
                    player.getFarming().remove(instance);
                    t.stop();
                }
            });
        } else {
            ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, FarmingPatches.values()[patch].harvestItem);
            String name = def.name;
            player.message("You need " + Utils.getAOrAn(name) + " " + name + " to harvest these plants.");
        }
    }

    public boolean useItemOnPlant(final Player player, int item) {
        if (item == 952) {
            player.animate(830);
            player.getFarming().remove(this);
            Chain.bound(null).runFn(2, () -> {
                player.message("You remove your plants from the plot.");
                player.animate(65535);
            });
            return true;
        }
        if (item == 6036) {
            if (dead) {
                player.message("Your plant is dead!");
            } else if (isDiseased()) {
                player.message("You cure the plant.");
                player.animate(2288);
                player.inventory().remove(6036, 1);
                disease = -1;
                doConfig(player);
            } else {
                player.message("Your plant does not need this.");
            }
            return true;
        }
        if ((item >= 5331) && (item <= 5340) || item == 25025) {
            water(player, item);
            return true;
        }
        return false;
    }

    public void process(Player player) {
        if (dead || stage >= Plants.values()[plant].stages) {
            return;
        }

        long elapsed = (System.currentTimeMillis() - time) / 60_000;

        Plants plant = Plants.values()[this.plant];
        int grow = plant.getMinutes() / plant.stages;
        if (grow == 0)
            grow = 1;

        if (elapsed >= grow) {
            for (int i = 0; i < elapsed / grow; i++) {
                if(isWatered() || getPatch().seedType == SeedType.HERB) {
                    stage++;
                    player.getFarming().doConfig();
                    if (stage >= plant.stages) {
                        player.message(Color.BLUE.wrap("A seed you planted has finished growing!"));
                        return;
                    }
                }

            }
            setTime();
        }
    }

    public void doConfig(Player player) {
        player.getFarming().doConfig();
    }

    public int getConfig() {
        Plants plants = Plants.values()[plant];
        return (plants.healthy + stage + (isWatered() && stage == 0 ? 64 : 0));
    }

    public FarmingPatches getPatch() {
        return FarmingPatches.values()[patch];
    }

    public boolean isDiseased() {
        return disease > -1;
    }

    public boolean isWatered() {
        return watered == -1;
    }
}

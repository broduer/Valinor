package com.valinor.game.content.skill.impl.thieving;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.items.ItemSet;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.items.loot.LootItem;
import com.valinor.game.world.items.loot.LootTable;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.CustomItemIdentifiers.DOUBLE_DROPS_LAMP;
import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;
import static com.valinor.util.ItemIdentifiers.COINS_995;

/**
 * @author Patrick van Elderen | April, 21, 2021, 11:44
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class Stalls extends Interaction {

    public enum Stall {

        VEGETABLE_STALL(2, 2, 10.0, 52000, "vegetable stall",
            new int[][]{
                {4706, 634},
                {4708, 634},
            },
            new LootTable().addTable(1,
                new LootItem(1957, 1, 1),                    //Onion
                new LootItem(1965, 1, 1),                    //Cabbage
                new LootItem(1942, 1, 1),                    //Potato
                new LootItem(1982, 1, 1),                    //Tomato
                new LootItem(1550, 1, 1),                    //Garlic
                new LootItem(COINS_995, 400, 500, 12) //Coins
            )),
        BAKERS_STALL(1, 2, 16.0, 49000, "baker's stall",
            new int[][]{
                {6163, 6984},
                {11730, 634}
            },
            new LootTable().addTable(1,
                new LootItem(1891, 1, 3),                    //Cake
                new LootItem(2309, 1, 1),                    //Bread
                new LootItem(1901, 1, 1),                    //Chocolate slice
                new LootItem(COINS_995, 3000, 4000, 3) //Coins
            )),
        CRAFTING_STALL(5, 7, 16.0, 49000, "crafting stall",
            new int[][]{
                {4874, 4797},
                {6166, 6984},
            },
            new LootTable().addTable(1,
                new LootItem(1755, 1, 1),                    //Chisel
                new LootItem(1592, 1, 1),                    //Ring mould
                new LootItem(1597, 1, 1),                    //Necklace mould
                new LootItem(COINS_995, 3500, 4500, 3) //Coins
            )),
        MONKEY_FOOD_STALL(5, 7, 16.0, 49000, "food stall",
            new int[][]{
                {4875, 4797},
            },
            new LootTable().addTable(1,
                new LootItem(1963, 1, 1),                    //Banana
                new LootItem(COINS_995, 400, 800, 2) //Coins
            )),
        MONKEY_GENERAL_STALL(5, 7, 16.0, 49000, "general stall",
            new int[][]{
                {4876, 4797},
            },
            new LootTable().addTable(1,
                new LootItem(1931, 1, 1),                    //Pot
                new LootItem(2347, 1, 1),                    //Hammer
                new LootItem(560, 1, 1),                     //Tinderbox
                new LootItem(COINS_995, 400, 800, 4) //Coins
            )),
        TEA_STALL(5, 5, 16.0, 42000, "tea stall",
            new int[][]{
                {635, 634},
                {6574, 6573},
                {20350, 20349}
            },
            new LootTable().addTable(1,
                new LootItem(712, 1, 2),                     //Cup of tea
                new LootItem(COINS_995, 400, 800, 3) //Coins
            )),
        SILK_STALL(20, 2, 24.0, 42000, "silk stall",
            new int[][]{
                {6165, 6984},
                {11729, 634},
            },
            new LootTable().addTable(1,
                new LootItem(950, 1, 1),                     //Silk
                new LootItem(COINS_995, 6000, 8000, 2) //Coins
            )),
        WINE_STALL(22, 3, 27.0, 41000, "wine stall",
            new int[][]{
                {14011, 634},
            },
            new LootTable().addTable(1,
                new LootItem(1937, 1, 2),                     //Jug of water
                new LootItem(1993, 1, 1),                     //Jug of wine
                new LootItem(1988, 5,15, 4),                     //Grapes
                new LootItem(3732, 1, 1),                     //Empty jug
                new LootItem(7919, 1, 1),                     //Bottle of wine
                new LootItem(COINS_995, 600, 1200, 4) //Coins
            )),
        SEED_STALL(27, 3, 10.0, 40000, "seed stall",
            new int[][]{
                {7053, 634},
            },
            new LootTable().addTable(1,
                new LootItem(5318, 3,9, 8),                     //Potato seed
                new LootItem(5096, 2, 8),                     //Marigold seed
                new LootItem(5319, 3, 6, 8),                     //Onion seed
                new LootItem(5324, 3, 6, 6),                     //Cabbage seed
                new LootItem(5322, 3, 6, 3),                     //Tomato seed
                new LootItem(5320, 3, 6, 3),                     //Sweetcorn seed
                new LootItem(5097, 1, 3),                     //Rosemary seed
                new LootItem(5098, 1, 3),                     //Nasturtium seed
                new LootItem(5321, 3, 6, 1),                     //Watermelon seed
                new LootItem(5323, 3, 6, 1),                     //Strawberry seed
                new LootItem(COINS_995, 2000, 3000, 60) //Coins
            )),
        FUR_STALL(35, 5, 36.0, 22000, "fur stall",
            new int[][]{
                {4278, 634},
                {11732, 634},
            },
            new LootTable().addTable(1,
                new LootItem(958, 1, 1),                       //Grey wolf fur
                new LootItem(COINS_995, 2000, 4000, 5) //Coins
            )),
        FISH_STALL(42, 5, 42.0, 21000, "fish stall",
            new int[][]{
                {4277, 634},
                {4705, 634},
                {4707, 634},
            },
            new LootTable().addTable(1,
                new LootItem(332, 1,3, 4),                      //Raw salmon
                new LootItem(360, 1,3, 3),                      //Raw tuna
                new LootItem(378, 1, 2,2),                      //Raw lobster
                new LootItem(COINS_995, 2200, 4400, 12) //Coins
            )),
        CROSSBOW_STALL(49, 5, 52.0, 20000, "crossbow stall",
            new int[][]{
                {17031, 6984},
            },
            new LootTable().addTable(1,
                new LootItem(877, 3, 2),                        //Bronze bolts
                new LootItem(9420, 1, 2),                       //Bronze limbs
                new LootItem(9440, 1, 2),                       //Wooden stock
                new LootItem(9431, 1, 2),                       //Runite stock
                new LootItem(COINS_995, 2500,5000, 14) //Coins
            )),
        SILVER_STALL(50, 5, 54.0, 19000, "silver stall",
            new int[][]{
                {6164, 6984},
                {11734, 634},
            },
            new LootTable().addTable(1,
                new LootItem(442, 1, 1),                       //Silver ore
                new LootItem(COINS_995, 8000, 10000, 2)               //Coins
            )),
        SPICE_STALL(65, 5, 81.0, 13000, "spice stall",
            new int[][]{
                {6572, 6573},
                {11733, 634},
                {20348, 20349},
            },
            new LootTable().addTable(1,
                new LootItem(2007, 1, 2),                      //Spice
                new LootItem(COINS_995, 2000, 7000, 4)  //Coins
            )),
        MAGIC_STALL(65, 7, 100, 12000, "magic stall",
            new int[][]{
                {4877, 4797},
            },
            new LootTable().addTable(1,
                new LootItem(556, 50, 5),                      //Air rune
                new LootItem(557, 50, 5),                      //Earth rune
                new LootItem(554, 50, 5),                      //Fire rune
                new LootItem(555, 50, 5),                      //Water rune
                new LootItem(563, 25, 1),                      //Law rune
                new LootItem(COINS_995, 2000, 5000, 10) //Coins
            )),
        SCIMITAR_STALL(65, 7, 100.0, 1000, "scimitar stall",
            new int[][]{
                {4878, 4797},
            },
            new LootTable().addTable(1,
                new LootItem(1323, 1, 30),                      //Iron scimitar
                new LootItem(1333, 1, 4),                      //Rune scimitar
                new LootItem(4587, 1, 1),                      //Dragon scimitar
                new LootItem(COINS_995, 4000, 6000, 70) //Coins
            )),
        GEM_STALL(75, 10, 160.0, 8500, "gem stall",
            new int[][]{
                {6162, 6984},
                {11731, 634},
            },
            new LootTable().addTable(1,
                new LootItem(1623, 1, 10),                      //Uncut sapphire
                new LootItem(1621, 1, 8),                      //Uncut emerald
                new LootItem(1619, 1, 6),                      //Uncut ruby
                new LootItem(1617, 1, 4),                      //Uncut diamond
                new LootItem(1631, 1, 1),                      //Uncut dragonstone
                new LootItem(COINS_995, 12000, 15000, 10) //Coins
            )),
        MOR_GEM_STALL(75, 10, 160.0, 8500, "gem stall",
            new int[][]{
                {30280, 30278}
            },
            new LootTable().addTable(1,
                new LootItem(1623, 1, 5),                      //Uncut sapphire
                new LootItem(1608, 1, 5),                      //Sapphire
                new LootItem(1621, 1, 4),                      //Uncut emerald
                new LootItem(1606, 1, 4),                      //Emerald
                new LootItem(1619, 1, 3),                      //Uncut ruby
                new LootItem(1617, 1, 2),                      //Uncut diamond
                new LootItem(1602, 1, 2),                      //Diamond
                new LootItem(1631, 1, 1),                      //Uncut dragonstone
                new LootItem(1616, 1, 1),                      //Dragonstone
                new LootItem(COINS_995, 2000, 3000, 3) //Coins
            )),
        ORE_STALL(82, 10, 210.0, 8500, "ore stall",
            new int[][]{
                {30279, 30278}
            },
            new LootTable().addTable(1,
                new LootItem(COINS_995, 3000, 5000, 3),
                new LootItem(454, 2, 8),  //Coal
                new LootItem(445, 1, 4),  //Gold ore
                new LootItem(448, 1, 3),  //Mithril ore
                new LootItem(450, 1, 2),  //Adamant ore
                new LootItem(452, 1, 1)   //Runite ore
            ));

        public final int levelReq, respawnTime, petOdds;
        public final int[][] objIDs;
        public final double experience;
        public final String name;
        public final LootTable lootTable;

        Stall(int levelReq, int respawnTime, double experience, int petOdds, String name, int[][] objIDs, LootTable lootTable) {
            this.levelReq = levelReq;
            this.respawnTime = respawnTime * 1000 / 600;
            this.experience = experience;
            this.petOdds = petOdds;
            this.name = name;
            this.objIDs = objIDs;
            this.lootTable = lootTable;
        }
    }

    private void attempt(Player player, Stall stall, GameObject object, int replacementID) {
        player.faceObj(object);
        if (!player.skills().check(Skills.THIEVING, stall.levelReq, "steal from the " + stall.name))
            return;

        if (player.inventory().isFull()) {
            player.sound(2277);
            DialogueManager.sendStatement(player, "Your inventory is too full to hold any more.");
            return;
        }

        player.message("You attempt to steal from the " + stall.name + "...");
        player.lock();
        player.animate(832);

        Chain.bound(player).runFn(1, () -> {
            replaceStall(stall, object, replacementID);
            AchievementsManager.activate(player, Achievements.MASTER_THIEF, 1);
            DailyTaskManager.increase(DailyTasks.THIEVING, player);
            if(stall == Stall.GEM_STALL) {
                player.getTaskBottleManager().increase(BottleTasks.STEAL_FROM_GEM_STALL);
            }
            Item loot = stall.lootTable.rollItem();
            if(ItemSet.wearingRogueOutfit(player))
                loot.setAmount(loot.getAmount() * 2);
            player.getInventory().add(loot);

            if (World.getWorld().rollDie(200, 1)) {
                GroundItem item = new GroundItem(new Item(TASK_BOTTLE_SKILLING), player.tile(), player);
                GroundItemHandler.createGroundItem(item);
            }

            var doubleDropLampsUnlock = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.DOUBLE_DROP_LAMPS);
            if (World.getWorld().rollDie(200, 1) && doubleDropLampsUnlock) {
                player.inventory().addOrDrop(new Item(DOUBLE_DROPS_LAMP));
                player.message(Color.RED.wrap("Double drops lamp slayer perk activated."));
            }

            var coinsThievingUnlock = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.MORE_COINS_THIEVING);
            if (coinsThievingUnlock) {
                if(loot.getId() == COINS_995) {
                    loot.setAmount(loot.getAmount() + World.getWorld().random(2500, 5500));
                    player.message(Color.RED.wrap("Thieving slayer perk activated."));
                }
            }

            // Woo! A pet!
            var odds = (int) (stall.petOdds * player.getMemberRights().petRateMultiplier());
            if (World.getWorld().rollDie(odds, 1)) {
                ThievingPet.unlockRaccoon(player);
            }
            player.skills().addXp(Skills.THIEVING, stall.experience, true);
            player.unlock();
        });
    }

    private void replaceStall(Stall stall, GameObject object, int replacementID) {
        var replacement = new GameObject(replacementID, object.tile(), object.getType(), object.getRotation());
        ObjectManager.replace(object, replacement, stall.respawnTime);
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if (option == 1 || option == 2) {
            for (Stall stall : Stall.values()) {
                for (int[] ids : stall.objIDs) {
                    if (object.getId() == ids[0]) {
                        attempt(player, stall, object, ids[1]);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

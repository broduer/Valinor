package com.valinor.game.world.entity.mob.npc.droptables;

import com.valinor.GameServer;
import com.valinor.game.content.announcements.ServerAnnouncements;
import com.valinor.game.content.skill.impl.prayer.Bone;
import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.content.treasure.TreasureRewardCaskets;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.combat.skull.SkullType;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.valinor.game.content.collection_logs.CollectionLog.COS_RAIDS_KEY;
import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.game.content.collection_logs.LogType.OTHER;
import static com.valinor.game.world.entity.AttributeKey.DOUBLE_DROP_LAMP_TICKS;
import static com.valinor.game.world.entity.mob.npc.NpcDeath.notification;
import static com.valinor.util.CustomItemIdentifiers.HWEEN_TOKENS;
import static com.valinor.util.CustomItemIdentifiers.XMAS_TOKENS;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 25, 2021
 */
public class ItemDrops {

    private static final Logger npcDropLogs = LogManager.getLogger("NpcDropLogs");
    private static final Level NPC_DROPS;

    static {
        NPC_DROPS = Level.getLevel("NPC_DROPS");
    }

    private static final Area CORPOREAL_BEAST_AREA = new Area(2944, 4352, 3007, 4415);
    private static final List<Integer> BONES = Arrays.asList(ItemIdentifiers.BONES, BURNT_BONES, BAT_BONES, BIG_BONES, BABYDRAGON_BONES, DRAGON_BONES, JOGRE_BONES, ZOGRE_BONES, OURG_BONES, WYVERN_BONES, DAGANNOTH_BONES, LONG_BONE, CURVED_BONE, LAVA_DRAGON_BONES, SUPERIOR_DRAGON_BONES, WYRM_BONES, DRAKE_BONES, HYDRA_BONES);

    public static void dropAlwaysItems(Player player, Npc npc) {
        ScalarLootTable table = ScalarLootTable.forNPC(npc.id());
        if (table == null)
            return;

        var inWilderness = WildernessArea.inWilderness(player.tile());
        var dropUnderPlayer = npc.id() == NpcIdentifiers.KRAKEN || npc.id() == NpcIdentifiers.CAVE_KRAKEN || npc.id() >= NpcIdentifiers.ZULRAH && npc.id() <= NpcIdentifiers.ZULRAH_2044 || npc.id() >= NpcIdentifiers.VORKATH_8059 && npc.id() <= NpcIdentifiers.VORKATH_8061;
        Tile tile = dropUnderPlayer ? player.tile() : npc.tile();

        table.getGuaranteedDrops().forEach(tableItem -> {
            if (player.inventory().contains(BONECRUSHER)) {
                int itemId = tableItem.convert().getId();
                for (int bone : BONES) {
                    if (itemId == bone) {
                        Bone bones = Bone.get(itemId);
                        if (bones != null)
                            player.skills().addXp(Skills.PRAYER, bones.xp);
                    }
                }
            } else {
                if (tableItem.min > 0) {
                    // not fixed-amount drop, amount has a min/max amount randomly given
                    Item dropped = new Item(tableItem.id, Utils.random(tableItem.min, tableItem.max));
                    int itemId = dropped.getId();
                    var eventCurrency = itemId == HWEEN_TOKENS || itemId == XMAS_TOKENS;

                    if (eventCurrency) {
                        if (inWilderness) {
                            //50% extra tokens in wilderness
                            int extraTokens = dropped.getAmount() * 50 / 100;
                            dropped.setAmount(dropped.copy().getAmount() + extraTokens);

                            //Doubled if the player is skulled
                            if (player.getSkullType() != SkullType.NO_SKULL) {
                                dropped.setAmount(dropped.copy().getAmount() * 2);
                            }
                        }
                    }

                    var noteDragonBones = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.NOTED_DRAGON_BONES);
                    var isDragonBone = itemId == DRAGON_BONES || itemId == LAVA_DRAGON_BONES;
                    if (isDragonBone && noteDragonBones) {
                        dropped = dropped.note();
                    }

                    //Niffler
                    if (player.nifflerPetOut() && player.nifflerCanStore()) {
                        player.nifflerStore(dropped);
                    } else {
                        GroundItemHandler.createGroundItem(new GroundItem(dropped, tile, player));
                    }
                } else {
                    Item item = tableItem.convert();
                    int itemId = tableItem.convert().getId();

                    // fixed amount items
                    var noteDragonBones = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.NOTED_DRAGON_BONES);
                    var isDragonBone = itemId == DRAGON_BONES || itemId == LAVA_DRAGON_BONES;
                    if (isDragonBone && noteDragonBones) {
                        item = item.note();
                    }

                    if (player.nifflerPetOut() && player.nifflerCanStore() && tableItem.convert().getValue() > 0) {
                        player.nifflerStore(item);
                    } else {
                        GroundItemHandler.createGroundItem(new GroundItem(item, tile, player));
                    }
                }
            }
        });
    }

    public static void rollTheDropTable(Player player, Npc npc) {
        ScalarLootTable table = ScalarLootTable.forNPC(npc.id());
        if (table == null)
            return;

        var dropUnderPlayer = npc.id() == NpcIdentifiers.KRAKEN || npc.id() == NpcIdentifiers.CAVE_KRAKEN || npc.id() >= NpcIdentifiers.ZULRAH && npc.id() <= NpcIdentifiers.ZULRAH_2044 || npc.id() >= NpcIdentifiers.VORKATH_8059 && npc.id() <= NpcIdentifiers.VORKATH_8061;
        Tile tile = dropUnderPlayer ? player.tile() : npc.tile();

        //Amount of drop rolls
        int dropRolls = npc.combatInfo().droprolls;

        var doubleDropChance = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.DOUBLE_DROP_CHANCE);
        var isBoss = npc.combatInfo() != null && npc.combatInfo().boss;
        if (doubleDropChance && isBoss && World.getWorld().rollDie(100, 1)) {
            dropRolls += 1;
            player.message(Color.PURPLE.wrap("The Double drops perk grants you a second drop!"));
        }

        for (int i = 0; i < dropRolls; i++) {
            Item reward = table.randomItem(World.getWorld().random());
            if (reward != null) {
                var doubleDropsLampActive = player.<Integer>getAttribOr(DOUBLE_DROP_LAMP_TICKS, 0) > 0;
                var founderImp = player.pet() != null && player.pet().def().name.equalsIgnoreCase("Founder Imp");
                var canDoubleDrop = doubleDropsLampActive || founderImp;
                if (canDoubleDrop) {
                    var rolledDoubleDrop = World.getWorld().rollDie(10, 1);
                    if (rolledDoubleDrop) {
                        //Drop the item to the ground instead of editing the item instance
                        GroundItem doubleDrop = new GroundItem(reward, tile, player);

                        if (player.nifflerPetOut() && player.nifflerCanStore()) {
                            player.nifflerStore(doubleDrop.getItem());
                        } else {
                            GroundItemHandler.createGroundItem(doubleDrop);
                        }
                        player.message("The double drop effect doubled your drop.");
                    }
                }

                // bosses, find npc ID, find item ID
                BOSSES.log(player, npc.id(), reward);
                BOSSES.log(player, COS_RAIDS_KEY, reward);
                OTHER.log(player, npc.id(), reward);

                if (player.nifflerPetOut() && player.nifflerCanStore() && reward.getValue() > 0) {
                    player.nifflerStore(reward);
                } else {
                    GroundItemHandler.createGroundItem(new GroundItem(reward, tile, player));
                }

                ServerAnnouncements.tryBroadcastDrop(player, npc, reward);
                npcDropLogs.log(NPC_DROPS, "Player " + player.getUsername() + " got drop item " + reward.unnote().name());
                Utils.sendDiscordInfoLog("Player " + player.getUsername() + " got drop item " + reward.unnote().name(), "npcdrops");

                // Corp beast drops are displayed to surrounding players.
                if (npc.id() == 319) {
                    World.getWorld().getPlayers().forEachInArea(CORPOREAL_BEAST_AREA, p -> {
                        String amtString = reward.unnote().getAmount() == 1 ? reward.unnote().name() : "" + reward.getAmount() + " x " + reward.unnote().getAmount() + ".";
                        p.message("<col=0B610B>" + player.getUsername() + " received a drop: " + amtString);
                    });
                }
            }
        }
    }

    public static void dropCoins(Player player, Npc npc) {
        if (!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.MONEY_FROM_KILLING_BOSSES)) {
            return;
        }

        if (!Slayer.creatureMatches(player, npc.id())) {
            return;
        }

        if(npc.combatInfo() != null && npc.combatInfo().boss) {
            Item coins = new Item(COINS_995, World.getWorld().random(100_000, 500_000));
            GroundItem groundItem = new GroundItem(coins, player.tile(), player);
            GroundItemHandler.createGroundItem(groundItem);
            notification(player, coins);
            player.message("<col=0B610B>You have received a "+Utils.formatRunescapeStyle(coins.getAmount())+" coins drop!");
        }
    }

    public static void supplyCrateDrops(Player player, Npc npc) {
        if (!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.SUPPLY_DROP)) {
            return;
        }

        if (!Slayer.creatureMatches(player, npc.id())) {
            return;
        }

        int roll = 100;
        if (World.getWorld().rollDie(roll, 1)) {
            Item supplyCrate = new Item(SUPPLY_CRATE);
            GroundItem groundItem = new GroundItem(supplyCrate, player.tile(), player);
            GroundItemHandler.createGroundItem(groundItem);
            notification(player, supplyCrate);
            player.message("<col=0B610B>You have received a supply crate drop!");
        }
    }

    public static void treasure(Player killer, Npc npc) {
        if (!killer.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.TREASURE_HUNT)) {
            return;
        }

        if (!Slayer.creatureMatches(killer, npc.id())) {
            return;
        }

        int treasureCasketChance;
        if (killer.getMemberRights().isZenyteMemberOrGreater(killer))
            treasureCasketChance = 95;
        else if (killer.getMemberRights().isOnyxMemberOrGreater(killer))
            treasureCasketChance = 100;
        else if (killer.getMemberRights().isDragonstoneMemberOrGreater(killer))
            treasureCasketChance = 105;
        else if (killer.getMemberRights().isDiamondMemberOrGreater(killer))
            treasureCasketChance = 110;
        else if (killer.getMemberRights().isRubyMemberOrGreater(killer))
            treasureCasketChance = 115;
        else if (killer.getMemberRights().isEmeraldMemberOrGreater(killer))
            treasureCasketChance = 120;
        else if (killer.getMemberRights().isSapphireMemberOrGreater(killer))
            treasureCasketChance = 125;
        else
            treasureCasketChance = 128;

        var reduction = treasureCasketChance * killer.masterCasketMemberBonus() / 100;
        treasureCasketChance -= reduction;

        if (World.getWorld().rollDie(killer.getPlayerRights().isDeveloperOrGreater(killer) && !GameServer.properties().production ? 1 : treasureCasketChance, 1)) {
            Item clueItem = new Item(TreasureRewardCaskets.MASTER_CASKET);
            GroundItem groundItem = new GroundItem(clueItem, killer.tile(), killer);
            GroundItemHandler.createGroundItem(groundItem);
            notification(killer, clueItem);
            killer.message("<col=0B610B>You have received a treasure casket drop!");
        }

        Item smallCasket = new Item(ItemIdentifiers.CASKET_7956);
        Item bigChest = new Item(CustomItemIdentifiers.BIG_CHEST);
        int combat = killer.skills().combatLevel();

        int chance;
        int regularOdds = 100;

        if (combat <= 10)
            chance = 1;
        else if (combat <= 20)
            chance = 2;
        else if (combat <= 80)
            chance = 3;
        else if (combat <= 120)
            chance = 4;
        else
            chance = 5;

        //If the player is in the wilderness, they have an increased chance at a casket drop
        if (World.getWorld().random(regularOdds - 15) < chance) {
            if (npc.combatInfo() != null && npc.combatInfo().boss && Utils.random(3) == 2) {
                killer.message("<col=0B610B>You have received a Big chest drop!");
                GroundItem groundItem = new GroundItem(bigChest, killer.tile(), killer);
                GroundItemHandler.createGroundItem(groundItem);
                notification(killer, bigChest);
            } else {
                killer.message("<col=0B610B>You have received a small casket drop!");
                GroundItem groundItem = new GroundItem(smallCasket, killer.tile(), killer);
                GroundItemHandler.createGroundItem(groundItem);
                notification(killer, smallCasket);
            }
        }
    }
}

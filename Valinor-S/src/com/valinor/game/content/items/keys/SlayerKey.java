package com.valinor.game.content.items.keys;

import com.valinor.GameServer;
import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.game.content.collection_logs.LogType.KEYS;
import static com.valinor.game.world.entity.AttributeKey.*;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.SUPER_ANTIFIRE_POTION4;

/**
 * This class represents the slayer key. The slayer key is a random drop during a slayer task.
 * The key has various rewards including armour, supplies and weaponry.
 * @author Patrick van Elderen | May, 18, 2021, 14:44
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class SlayerKey {

    private static final int RARE_ROLL = 100;
    private static final int UNCOMMON_ROLL = 25;

    private final Player player;

    public SlayerKey(Player player) {
        this.player = player;
    }

    public void drop(Npc npc) {
        var task_id = player.<Integer>getAttribOr(SLAYER_TASK_ID,0);
        var task = SlayerCreature.lookup(task_id);
        var roll = player.getPlayerRights().isDeveloperOrGreater(player) && !GameServer.properties().production ? 1 : 20;
        boolean hasTask = player.slayerTaskAmount() > 0;
        if (task != null && Slayer.creatureMatches(player, npc.id()) && hasTask) {
            if(World.getWorld().rollDie(roll,1)) {
                var keysReceived = player.<Integer>getAttribOr(SLAYER_KEYS_RECEIVED,0) + 1;
                player.putAttrib(SLAYER_KEYS_RECEIVED, keysReceived);
                player.getPacketSender().sendString(QuestTab.InfoTab.SLAYER_KEYS_RECEIVED.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.SLAYER_KEYS_RECEIVED.childId).fetchLineData(player));

                player.inventory().addOrDrop(new Item(SLAYER_KEY));
                player.message(Color.PURPLE.wrap("A slayer key appeared, you have now collected a total of "+keysReceived+ " slayer keys."));
                Utils.sendDiscordInfoLog("Player " + player.getUsername() + " has received a slayer key drop.", "slayer_key_drop");
            }
        }
    }

    public void open() {
        if(!player.inventory().contains(SLAYER_KEY)) {
            player.message("This chest wont budge, I need some sort of Slayer key.");
            return;
        }

        player.message("You unlock the chest with your key.");
        player.sound(51);
        player.animate(536);
        player.inventory().remove(SLAYER_KEY,1);

        int roll = Utils.percentageChance(player.extraItemRollChance()) ? 2 : 1;
        for (int i = 0; i < roll; i++) {
            Item reward = reward();
            KEYS.log(player, SLAYER_KEY, reward);
            player.inventory().addOrBank(reward);
            Utils.sendDiscordInfoLog("Player " + player.getUsername() + " opened the slayer chest and received " + Utils.getAOrAn(reward.unnote().name()) +" "+reward.unnote().name()+".", "slayer_chest");

            var sendWorldMessage = rare;
            var amount = reward.getAmount();
            var plural = amount > 1 ? "x" + amount : "x1";
            if (sendWorldMessage && !player.getUsername().equalsIgnoreCase("Box test")) {
                String worldMessage = "<img=505><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Slayer Key</col>]</shad>:<col=AD800F> " + player.getUsername() + " received " + plural + " <shad=0>" + reward.name() + "</shad>!";
                World.getWorld().sendWorldMessage(worldMessage);
                rare = false;
            }
        }

        var keysUsed = player.<Integer>getAttribOr(SLAYER_KEYS_OPENED,0) + 1;
        player.putAttrib(SLAYER_KEYS_OPENED, keysUsed);
        player.message(Color.PURPLE.wrap("You have now opened "+Utils.formatNumber(keysUsed)+" slayer keys!"));
    }

    private boolean rare = false;
    private Item reward() {
        if (Utils.rollDie(RARE_ROLL, 1)) {
            rare = true;
            return Utils.randomElement(RARE);
        } else if (Utils.rollDie(UNCOMMON_ROLL, 1)) {
            return Utils.randomElement(UNCOMMON);
        } else {
            return Utils.randomElement(COMMON);
        }
    }

    private static final List<Item> COMMON = Arrays.asList(
        new Item(DIVINE_SUPER_COMBAT_POTION4 + 1, 10),
        new Item(STAMINA_POTION4 + 1, 10),
        new Item(ANTIVENOM4 + 1, 10),
        new Item(SUPER_ANTIFIRE_POTION4 + 1, 10),
        new Item(GRIMY_DWARF_WEED + 1, 25),
        new Item(GRIMY_TORSTOL + 1, 25),
        new Item(GRIMY_RANARR_WEED + 1, 25),
        new Item(GRIMY_SNAPDRAGON + 1, 25),
        new Item(GREEN_DRAGONHIDE + 1, 25),
        new Item(BLUE_DRAGONHIDE + 1, 25),
        new Item(RED_DRAGONHIDE + 1, 25),
        new Item(BLACK_DRAGONHIDE + 1, 25),
        new Item(DRAGON_BONES + 1, 25),
        new Item(BABYDRAGON_BONES + 1, 25),
        new Item(UNCUT_DRAGONSTONE + 1, 25),
        new Item(ADAMANT_ARROW, 200 + World.getWorld().random(400)),
        new Item(RUNE_ARROW, 200 + World.getWorld().random(300))
    );

    private static final List<Item> UNCOMMON = Arrays.asList(
        new Item(DRAGON_DART, 100 + World.getWorld().random(400)),
        new Item(DRAGON_ARROW, 100 + World.getWorld().random(400)),
        new Item(DRAGON_PLATELEGS, 1),
        new Item(DRAGON_PLATESKIRT, 1),
        new Item(DRAGON_BOOTS, 1),
        new Item(DRAGON_AXE, 1),
        new Item(DRAGON_CHAINBODY_3140, 1)
    );

    private static final List<Item> RARE = Arrays.asList(
        new Item(DRAGONSTONE_FULL_HELM, 1),
        new Item(DRAGONSTONE_PLATEBODY, 1),
        new Item(DRAGONSTONE_PLATELEGS, 1),
        new Item(DRAGONSTONE_GAUNTLETS, 1),
        new Item(DRAGONSTONE_BOOTS, 1),
        new Item(UNCUT_ONYX, 1)
    );
}

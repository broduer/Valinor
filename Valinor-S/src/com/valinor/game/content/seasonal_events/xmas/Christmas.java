package com.valinor.game.content.seasonal_events.xmas;

import com.valinor.game.content.seasonal_events.halloween.Halloween;
import com.valinor.game.content.seasonal_events.rewards.UnlockEventRewards;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.valinor.game.content.seasonal_events.rewards.UnlockEventRewards.UNLOCKED_ITEM_SLOT;
import static com.valinor.util.CustomItemIdentifiers.XMAS_TOKENS;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 30, 2021
 */
public class Christmas extends PacketInteraction {

    private static final Logger logger = LogManager.getLogger(Halloween.class);

    public static void loadNpcs() {
        Npc santa = new Npc(11112, new Tile(3096, 3486));
        World.getWorld().registerNpc(santa);

        loadSnowman();
        logger.info("Loaded halloween npcs.");
    }

    private static void loadSnowman() {
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        if (button == 73307) {
            player.optionsTitled("Exchange your 5,000 X'mas tokens for a reward?", "Yes", "No", () -> {
                if (!player.inventory().contains(new Item(XMAS_TOKENS, 10_000))) {
                    return;
                }

                var unlockedAllRewards = player.getEventRewards().rewardsUnlocked().values().stream().allMatch(r -> r);
                if (unlockedAllRewards) {
                    player.message(Color.RED.wrap("You have already unlocked all rewards."));
                    return;
                }

                Item reward = player.getEventRewards().generateReward();
                if (reward == null) {
                    return;
                }

                if (player.inventory().contains(new Item(XMAS_TOKENS, 10_000))) {
                    player.getEventRewards().refreshItems();
                    player.getPacketSender().sendItemOnInterfaceSlot(UNLOCKED_ITEM_SLOT, reward.copy(),0);
                    player.getEventRewards().rollForReward(XMAS_TOKENS, 10_000, reward.copy(), "X'mas");
                } else {
                    player.message(Color.RED.wrap("You do not have enough X'mas tokens to roll for a reward."));
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if (option == 1) {
            if (object.getId() == 2654) {
                if (UnlockEventRewards.CHRISTMAS) {
                    player.getEventRewards().open("X'mas");
                }
                return true;
            }
        }
        return false;
    }
}

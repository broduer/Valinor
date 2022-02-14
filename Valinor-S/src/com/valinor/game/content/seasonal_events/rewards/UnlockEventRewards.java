package com.valinor.game.content.seasonal_events.rewards;

import com.valinor.GameServer;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.valinor.game.world.entity.AttributeKey.HWEEN_EVENT_TOKENS_SPENT;
import static com.valinor.game.world.entity.AttributeKey.WINTER_EVENT_TOKENS_SPENT;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since October 14, 2021
 */
public class UnlockEventRewards {

    private final Player player;

    public UnlockEventRewards(Player player) {
        this.player = player;
    }

    private HashMap<EventRewards, Boolean> eventRewardsUnlocked = new HashMap<>();

    public HashMap<EventRewards, Boolean> rewardsUnlocked() {
        return eventRewardsUnlocked;
    }

    public void setEventRewardsUnlocked(HashMap<EventRewards, Boolean> eventRewardsUnlocked) {
        this.eventRewardsUnlocked = eventRewardsUnlocked;
    }

    public boolean lookForMissing() {
        var missing = false;
        for (EventRewards value : EventRewards.values()) {
            // if any of the values are missing from unlocked, they're not unlocked
            if (!player.getEventRewards().rewardsUnlocked().getOrDefault(value, false)) {
                missing = true;
                break;
            }
        }
        return missing;
    }

    public Item generateReward() {
        var randomReward = World.getWorld().random(210);

        HashMap<EventRewards, Boolean> eventRewards = player.getEventRewards().rewardsUnlocked();
        for (EventRewards reward : EventRewards.values()) {
            var unlocked = eventRewards.size() != 0 && eventRewards.getOrDefault(reward, false);
            if (unlocked) {
                continue;
            }
            if (reward.chance <= randomReward) {
                player.getEventRewards().rewardsUnlocked().put(reward, true);
                return reward.reward;
            }
        }
        //Some reason this line errors randomly and when the last in the enum is still open it always errors
        // well it will npe if they are all unlocked there is still 1 free tho
        final EventRewards r = Arrays.stream(EventRewards.values()).filter(value -> !eventRewards.getOrDefault(value, false)).findAny().get();
        player.getEventRewards().rewardsUnlocked().put(r, true);
        return r.reward;
    }

    public void reset(String event) {
        var unlockedAllRewards = player.getEventRewards().rewardsUnlocked().values().stream().allMatch(r -> r);

        //We've unlock all rewards we can go ahead and reset them as requested.
        if (unlockedAllRewards) {
            player.optionsTitled("Would you like to reset all the rewards?", "Yes", "No", () -> {
                player.getEventRewards().rewardsUnlocked().clear();
                if(event.equalsIgnoreCase("Winter")) {
                    player.putAttrib(WINTER_EVENT_TOKENS_SPENT,0);
                } else if(event.equalsIgnoreCase("Halloween")) {
                    player.putAttrib(HWEEN_EVENT_TOKENS_SPENT,0);
                }
                player.inventory().addOrBank(COMPLETED_EVENT_REWARD);
                //Let the world know this play has finished the rewards and goes for them again
                World.getWorld().sendWorldMessage("<img=452><shad=0>" + Color.MEDRED.wrap("[" + event + "]:") + "</shad> " + Color.PURPLE.wrap(player.getUsername()) + " has just reset the " + Color.PURPLE.wrap(event) + " Event Rewards!");
                player.getInterfaceManager().close();
            });
        } else {
            player.message(Color.RED.wrap("You haven't unlocked all the possible rewards from this event yet."));
        }
    }

    private final List<Integer> items_to_shout = Arrays.asList(
        DONATOR_MYSTERY_BOX, DRACONIC_VISAGE, FIVE_DOLLAR_BOND, KEY_OF_DROPS, MYSTERY_TICKET, CHRISTMAS_CRACKER, PURPLE_HWEEN_MASK, CYAN_PARTYHAT);

    public void rollForReward(int tokens, int amount, Item reward, String event) {
        player.inventory().remove(tokens, amount);
        player.inventory().addOrBank(reward);

        int tokensSpent = 0;
        if(event.equalsIgnoreCase("Halloween")) {
            tokensSpent = player.<Integer>getAttribOr(HWEEN_EVENT_TOKENS_SPENT,0) + amount;
            player.putAttrib(HWEEN_EVENT_TOKENS_SPENT, tokensSpent);
        } else if(event.equalsIgnoreCase("Winter")) {
            tokensSpent = player.<Integer>getAttribOr(WINTER_EVENT_TOKENS_SPENT,0) + amount;
            player.putAttrib(WINTER_EVENT_TOKENS_SPENT, tokensSpent);
        }

        int max = event.equalsIgnoreCase("Halloween") ? 220_000 : 440_000;
        if(tokensSpent > max)
            tokensSpent = max;

        final var progress = (int) (tokensSpent * 100 / (double) max);

        player.getPacketSender().sendProgressBar(73313, progress);
        player.getPacketSender().sendString(73314, "Tokens spent: " + Utils.formatNumber(tokensSpent));

        boolean shout = items_to_shout.stream().anyMatch(item -> reward.getId() == item);

        if (shout) {
            //The user box test doesn't yell.
            if(player.getUsername().equalsIgnoreCase("Box test")) {
                return;
            }
            World.getWorld().sendWorldMessage("<img=452><shad=0>" + Color.MEDRED.wrap("[News]:") + "</shad> " + Color.PURPLE.wrap(player.getUsername()) + " received " + Color.HOTPINK.wrap("x" + reward.getAmount()) + " " + Color.HOTPINK.wrap(reward.unnote().name()) + " from the Event!");
        } else {
            int rewardAmount = reward.getAmount();
            String plural = rewardAmount > 1 ? "x" + rewardAmount : "x1";
            player.message(Color.BLUE.tag() + "Congratulations, you have received " + plural + " " + reward.unnote().name() + "!");
        }
    }

    private static final int INTERFACE_ID = 73300;
    private static final int COMPLETED_EVENT_REWARD_SLOT = 73315;
    private static final int TOKEN_REQUIREMENT_SLOT = 73316;
    public static final int UNLOCKED_ITEM_SLOT = 73317;
    private static final int UNLOCKABLE_ITEMS_CONTAINER = 73318;

    private static final Item COMPLETED_EVENT_REWARD = new Item(WINTER_CASKET,1);

    private static final Item TOKEN_REQUIREMENT = GameServer.properties().halloween ? new Item(HWEEN_TOKENS, 5000) : new Item(WINTER_TOKENS, 10_000);

    private static final ArrayList<Item> items = new ArrayList<>();

    static {
        //Fill the map with all potential rewards
        for (EventRewards reward : EventRewards.values()) {
            items.add(reward.reward);
        }
    }

    public void open(String event) {
        if(event.equalsIgnoreCase("Halloween")) {
            player.getPacketSender().sendString(73304, "Halloween event 2021");
        } else if(event.equalsIgnoreCase("Winter")) {
            player.getPacketSender().sendString(73304, "Winter event 2022");
        }

        setAllItemsTrans();

        //Refresh them
        refreshItems();

        player.getInterfaceManager().open(INTERFACE_ID);

        player.getPacketSender().sendItemOnInterface(UNLOCKABLE_ITEMS_CONTAINER, items);

        int tokensSpent = 0;
        if(event.equalsIgnoreCase("Halloween")) {
            tokensSpent = player.<Integer>getAttribOr(HWEEN_EVENT_TOKENS_SPENT,0);
            player.putAttrib(HWEEN_EVENT_TOKENS_SPENT, tokensSpent);
        } else if(event.equalsIgnoreCase("Winter")) {
            tokensSpent = player.<Integer>getAttribOr(WINTER_EVENT_TOKENS_SPENT,0);
            player.putAttrib(WINTER_EVENT_TOKENS_SPENT, tokensSpent);
        }

        final var progress = (int) (tokensSpent * 100 / (double) 220_000);
        player.getPacketSender().sendProgressBar(73313, progress);
        player.getPacketSender().sendString(73314, "Tokens spent: " +Utils.formatNumber(tokensSpent));

        //Write the event reward item
        player.getPacketSender().sendItemOnInterfaceSlot(COMPLETED_EVENT_REWARD_SLOT, COMPLETED_EVENT_REWARD, 0);

        //Write the event token requirement
        player.getPacketSender().sendItemOnInterfaceSlot(TOKEN_REQUIREMENT_SLOT, TOKEN_REQUIREMENT, 0);

        //Clear the rolled item slot
        player.getPacketSender().sendItemOnInterfaceSlot(UNLOCKED_ITEM_SLOT, null, 0);

        //Winter event has no reset button
        if(event.equalsIgnoreCase("Winter")) {
            player.getPacketSender().sendInterfaceDisplayState(73310, true);
        }
    }

    private void setAllItemsTrans() {
        for (Item item : items) {
            item.setAmount(0);
        }
    }

    public void refreshItems() {
        for (EventRewards reward : EventRewards.values()) {
            HashMap<EventRewards, Boolean> rewards = player.getEventRewards().rewardsUnlocked();

            //List empty, we haven't unlocked anything
            if(rewards != null && rewards.isEmpty()) {
                continue;
            }

            var unlocked = rewards != null && rewards.get(reward) != null &&
                rewards.size() != 0 &&
                rewards.get(reward);
            if (unlocked) {
                reward.reward.setAmount(1);
                player.getPacketSender().sendItemOnInterface(UNLOCKABLE_ITEMS_CONTAINER, items);
            }
        }
    }
}

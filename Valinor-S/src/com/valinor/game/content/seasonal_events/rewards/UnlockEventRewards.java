package com.valinor.game.content.seasonal_events.rewards;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.valinor.game.world.entity.AttributeKey.HWEEN_EVENT_TOKENS_SPENT;
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

    public Item generateReward() {
        var randomReward = World.getWorld().random(210);

        for (EventRewards reward : EventRewards.values()) {
            //System.out.println(reward.name()+" -> "+player.<Boolean>getAttribOr(reward.key,false));
            var unlocked = player.getEventRewards().rewardsUnlocked().get(reward);
            if (unlocked) {
                continue;
            }
            if (reward.chance <= randomReward) {
                player.getEventRewards().rewardsUnlocked().put(reward, true);
                return reward.reward;
            }
        }
        final EventRewards eventRewards = Arrays.stream(EventRewards.values()).filter(r -> !player.getEventRewards().rewardsUnlocked().get(r)).findAny().get();
        player.getEventRewards().rewardsUnlocked().put(eventRewards, true);
        return eventRewards.reward;
    }

    public void reset(String event) {
        var unlockedAllRewards = player.getEventRewards().rewardsUnlocked().values().stream().allMatch(r -> r);

        //We've unlock all rewards we can go ahead and reset them as requested.
        if (unlockedAllRewards) {
            player.optionsTitled("Would you like to reset all the rewards?", "Yes", "No", () -> {
                player.getEventRewards().rewardsUnlocked().clear();
                player.putAttrib(HWEEN_EVENT_TOKENS_SPENT,0);
                player.inventory().addOrBank(COMPLETED_EVENT_REWARD);
                //Let the world know this play has finished the rewards and goes for them again
                World.getWorld().sendWorldMessage("<img=505><shad=0>" + Color.MEDRED.wrap("[" + event + "]:") + "</shad> " + Color.PURPLE.wrap(player.getUsername()) + " has just reset the " + Color.PURPLE.wrap(event) + " Event Rewards!");
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
        if(event.equalsIgnoreCase("H'ween")) {
            tokensSpent = player.<Integer>getAttribOr(HWEEN_EVENT_TOKENS_SPENT,0) + amount;
            player.putAttrib(HWEEN_EVENT_TOKENS_SPENT, tokensSpent);
        } else if(event.equalsIgnoreCase("X'mas")) {
            tokensSpent = player.<Integer>getAttribOr(HWEEN_EVENT_TOKENS_SPENT,0) + amount;
            player.putAttrib(HWEEN_EVENT_TOKENS_SPENT, tokensSpent);
        }

        int totalToSpent = event.equalsIgnoreCase("H'ween") ? 220_000 : 220_000;

        final var progress = (int) (tokensSpent * 100 / (double) totalToSpent);

        player.getPacketSender().sendProgressBar(73313, progress);
        player.getPacketSender().sendString(73314, "Tokens spent: " +tokensSpent);

        boolean shout = items_to_shout.stream().anyMatch(item -> reward.getId() == item);

        if (shout) {
            //The user box test doesn't yell.
            if(player.getUsername().equalsIgnoreCase("Box test")) {
                return;
            }
            World.getWorld().sendWorldMessage("<img=505><shad=0>" + Color.MEDRED.wrap("[News]:") + "</shad> " + Color.PURPLE.wrap(player.getUsername()) + " received " + Color.HOTPINK.wrap("x" + reward.getAmount()) + " " + Color.HOTPINK.wrap(reward.unnote().name()) + " from the Event!");
        } else {
            int rewardAmount = reward.getAmount();
            String plural = rewardAmount > 1 ? "x" + rewardAmount : "x1";
            player.message(Color.BLUE.tag() + "Congratulations, you have received " + plural + " " + reward.unnote().name() + "!");
        }
    }

    public static final boolean HALLOWEEN = false;
    public static final boolean CHRISTMAS = true;

    private static final int INTERFACE_ID = 73300;
    private static final int COMPLETED_EVENT_REWARD_SLOT = 73315;
    private static final int TOKEN_REQUIREMENT_SLOT = 73316;
    public static final int UNLOCKED_ITEM_SLOT = 73317;
    private static final int UNLOCKABLE_ITEMS_CONTAINER = 73318;

    private static final Item COMPLETED_EVENT_REWARD = HALLOWEEN ? new Item(BLOOD_MONEY, 1) : new Item(MYSTERY_TICKET,2);

    private static final Item TOKEN_REQUIREMENT = HALLOWEEN ? new Item(HWEEN_TOKENS, 5000) : new Item(XMAS_TOKENS, 10_000);

    private static final ArrayList<Item> items = new ArrayList<>();

    static {
        //Fill the map with all potential rewards
        for (EventRewards reward : EventRewards.values()) {
            items.add(reward.reward);
        }
    }

    public void open(String event) {
        if(event.equalsIgnoreCase("H'ween")) {
            player.getPacketSender().sendString(73304, "Halloween event 2021");
        } else if(event.equalsIgnoreCase("X'mas")) {
            player.getPacketSender().sendString(73304, "Christmas event 2021");
        }

        setAllItemsTrans();

        //Refresh them
        refreshItems();

        player.getInterfaceManager().open(INTERFACE_ID);

        player.getPacketSender().sendItemOnInterface(UNLOCKABLE_ITEMS_CONTAINER, items);

        final int tokensSpent = player.<Integer>getAttribOr(HWEEN_EVENT_TOKENS_SPENT,0);
        player.putAttrib(HWEEN_EVENT_TOKENS_SPENT, tokensSpent);
        final var progress = (int) (tokensSpent * 100 / (double) 220_000);
        player.getPacketSender().sendProgressBar(73313, progress);
        player.getPacketSender().sendString(73314, "Tokens spent: " +tokensSpent);

        //Write the event reward item
        player.getPacketSender().sendItemOnInterfaceSlot(COMPLETED_EVENT_REWARD_SLOT, COMPLETED_EVENT_REWARD, 0);

        //Write the event token requirement
        player.getPacketSender().sendItemOnInterfaceSlot(TOKEN_REQUIREMENT_SLOT, TOKEN_REQUIREMENT, 0);

        //Clear the rolled item slot
        player.getPacketSender().sendItemOnInterfaceSlot(UNLOCKED_ITEM_SLOT, null, 0);

        //X'mas event has no reset button
        if(event.equalsIgnoreCase("X'mas")) {
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
            //List empty, we haven't unlocked anything
            if(player.getEventRewards().rewardsUnlocked().isEmpty()) {
                continue;
            }

            var unlocked = player.getEventRewards().rewardsUnlocked().get(reward);
            if (unlocked) {
                reward.reward.setAmount(1);
                player.getPacketSender().sendItemOnInterface(UNLOCKABLE_ITEMS_CONTAINER, items);
            }
        }
    }
}

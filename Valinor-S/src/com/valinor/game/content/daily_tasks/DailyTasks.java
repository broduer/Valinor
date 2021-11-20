package com.valinor.game.content.daily_tasks;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.valinor.game.world.entity.AttributeKey.*;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | June, 15, 2021, 16:06
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public enum DailyTasks {

    DAILY_RAIDS("Daily Raiding", "Complete 5 raids.", 5, DAILY_RAIDS_DAILY_TASK_COMPLETION_AMOUNT, DAILY_RAIDS_DAILY_TASK_COMPLETED, DAILY_RAIDS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVM, new Item(DONATOR_MYSTERY_BOX, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    WORLD_BOSS("Daily World boss", "Kill 3 world bosses.", 3, WORLD_BOSS_DAILY_TASK_COMPLETION_AMOUNT, WORLD_BOSS_DAILY_TASK_COMPLETED, WORLD_BOSS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVM, new Item(COINS_995, 10_000_000)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    REVENANTS("Daily Revenants", "Kill 100 revenants", 100, DAILY_REVENANTS_TASK_COMPLETION_AMOUNT, DAILY_REVENANTS_TASK_COMPLETED, DAILY_REVENANTS_TASK_REWARD_CLAIMED, TaskCategory.PVM, new Item(ANCIENT_RELIC, 1), new Item(ANCIENT_EFFIGY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    BATTLE_MAGE("Daily Battle mage", "Kill 100 battle mages.", 100, BATTLE_MAGE_DAILY_TASK_COMPLETION_AMOUNT, BATTLE_MAGE_DAILY_TASK_COMPLETED, BATTLE_MAGE_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVM, new Item(STAFF_OF_THE_DEAD, 1), new Item(OCCULT_NECKLACE, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    WILDERNESS_BOSS("Daily Wildy Boss", "Kill 50 wilderness bosses.<br>The following bosses count:<br>Callisto, Venenatis, Scorpia,<br>Chaos Ele, Barrelchest,<br>and Vet'ion.", 50, WILDERNESS_BOSS_DAILY_TASK_COMPLETION_AMOUNT, WILDERNESS_BOSS_DAILY_TASK_COMPLETED, WILDERNESS_BOSS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVM, new Item(WEAPON_MYSTERY_BOX, 1), new Item(ARMOUR_MYSTERY_BOX, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    ZULRAH("Daily Zulrah", "Kill 25 Zulrah.", 25, ZULRAH_DAILY_TASK_COMPLETION_AMOUNT, ZULRAH_DAILY_TASK_COMPLETED, ZULRAH_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVM, new Item(BLOOD_MONEY, 20000)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    SLAYER("Daily Slayer", "Complete 15 wilderness tasks.", 15, SLAYER_DAILY_TASK_COMPLETION_AMOUNT, SLAYER_DAILY_TASK_COMPLETED, SLAYER_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVM, new Item(SLAYER_KEY, 5)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    CORRUPTED_NECHRYARCHS("Daily Nechryarch", "Kill 10 Corrupted Nechryarchs.", 10, CORRUPTED_NECHRYARCHS_DAILY_TASK_COMPLETION_AMOUNT, CORRUPTED_NECHRYARCHS_DAILY_TASK_COMPLETED, CORRUPTED_NECHRYARCHS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVM, new Item(WEAPON_MYSTERY_BOX, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    VORKATH("Daily Vorkath", "Kill 10 Vorkaths.", 10, VORKATH_DAILY_TASK_COMPLETION_AMOUNT, VORKATH_DAILY_TASK_COMPLETED, VORKATH_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVM, new Item(BLOOD_MONEY, 30000)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    CORPOREAL_BEAST("Daily Corp", "Kill 10 Corporeal Beasts.", 10, CORPOREAL_BEAST_DAILY_TASK_COMPLETION_AMOUNT, CORPOREAL_BEAST_DAILY_TASK_COMPLETED, CORPOREAL_BEAST_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVM,new Item(DONATOR_MYSTERY_BOX), new Item(BLESSED_SPIRIT_SHIELD, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    WILDY_AGLITY("Wildy Runner", "Complete 25 laps of wilderness<br>agility.", 25, WILDY_RUNNER_DAILY_TASK_COMPLETION_AMOUNT, WILDY_RUNNER_DAILY_TASK_COMPLETED, WILDY_RUNNER_DAILY_TASK_REWARD_CLAIMED, TaskCategory.OTHER, new Item(BLOOD_MONEY, 25000)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    };

    public final String taskName;
    public final String taskDescription;
    public final int completionAmount;
    public final AttributeKey key;
    public final AttributeKey completed;
    public final AttributeKey rewardClaimed;
    public final TaskCategory category;
    public final Item[] rewards;

    DailyTasks(String taskName, String taskDescription, int completionAmount, AttributeKey key, AttributeKey completed, AttributeKey rewardClaimed, TaskCategory category, Item... rewards) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.completionAmount = completionAmount;
        this.key = key;
        this.completed = completed;
        this.rewardClaimed = rewardClaimed;
        this.category = category;
        this.rewards = rewards;
    }

    public static List<DailyTasks> asList(TaskCategory category) {
        return Arrays.stream(values()).filter(a -> a.category == category).sorted(Comparator.comparing(Enum::name)).collect(Collectors.toList());
    }

    public abstract boolean canIncrease(Player player);
}

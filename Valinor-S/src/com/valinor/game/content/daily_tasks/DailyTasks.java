package com.valinor.game.content.daily_tasks;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;

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

    ROCK_CRABS("Rock crabs", "Kill 20 rock crabs", "Any!", "Safe", new Tile(2706, 3713), 20, ROCK_CRABS_DAILY_TASK_COMPLETION_AMOUNT, ROCK_CRABS_DAILY_TASK_COMPLETED, ROCK_CRABS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(XMAS_TOKENS, 100), new Item(COINS_995, 1_000_000), new Item(DOUBLE_DROPS_SCROLL, 15), new Item(CRYSTAL_KEY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    EXPERIMENTS("Experiments", "Kill 20 experiments", "25+", "Safe", new Tile(3556, 9944), 20, EXPERIMENTS_DAILY_TASK_COMPLETION_AMOUNT, EXPERIMENTS_DAILY_TASK_COMPLETED, EXPERIMENTS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(XMAS_TOKENS, 100), new Item(COINS_995, 1_000_000), new Item(DOUBLE_DROPS_SCROLL, 15), new Item(CRYSTAL_KEY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    THIEVING("Thieving", "Steal from 100 stalls or pick-pockets", "Any!", "Safe", new Tile(3084,3475), 100, THIEVING_DAILY_TASK_COMPLETION_AMOUNT, THIEVING_DAILY_TASK_COMPLETED, THIEVING_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(XMAS_TOKENS, 100), new Item(COINS_995, 1_000_000), new Item(DOUBLE_DROPS_SCROLL, 15)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    VOTING("Voting", "Vote for us on 3 top list sites", "Any!", "Safe", new Tile(3100, 3505), 1, VOTING_DAILY_TASK_COMPLETION_AMOUNT, VOTING_DAILY_TASK_COMPLETED, VOTING_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, /*new Item(XMAS_TOKENS, 500),*/ new Item(VOTE_TICKET, 1), new Item(COINS_995, 1_000_000), new Item(POINTS_MYSTERY_BOX, 1), new Item(CRYSTAL_KEY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    SKILLING_TASKS("Skilling tasks", "Complete 3 skilling tasks", "Any!", "Safe", new Tile(3083,3488), 3, SKILLING_DAILY_TASK_COMPLETION_AMOUNT, SKILLING_DAILY_TASK_COMPLETED, SKILLING_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, /*new Item(XMAS_TOKENS, 500),*/ new Item(VOTE_TICKET, 1), new Item(COINS_995, 1_000_000), new Item(POINTS_MYSTERY_BOX, 1), new Item(CRYSTAL_KEY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    SLAYER("Slayer", "Complete 5 slayer tasks","25+", "Safe", null, 5, SLAYER_DAILY_TASK_COMPLETION_AMOUNT, SLAYER_DAILY_TASK_COMPLETED, SLAYER_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(XMAS_TOKENS, 100), new Item(COINS_995, 1_000_000), new Item(DOUBLE_DROPS_SCROLL, 15), new Item(POINTS_MYSTERY_CHEST, 1), new Item(SLAYER_TELEPORT_SCROLL, 5)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    PVM_TASKS("PvMing tasks", "Complete 3 pvming tasks", "75+", "Safe", new Tile(3083,3488), 3, PVMING_DAILY_TASK_COMPLETION_AMOUNT, PVMING_DAILY_TASK_COMPLETED, PVMING_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, /*new Item(XMAS_TOKENS, 500),*/ new Item(VOTE_TICKET, 1), new Item(COINS_995, 1_000_000), new Item(POINTS_MYSTERY_BOX, 1), new Item(CRYSTAL_KEY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    BARROWS("Barrows", "Complete 3 barrows runs", "70+", "Safe", new Tile(3565, 3306), 3, BARROWS_TASK_COMPLETION_AMOUNT, BARROWS_DAILY_TASK_COMPLETED, BARROWS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(XMAS_TOKENS, 250), new Item(COINS_995, 1_500_000), new Item(BARROWS_MYSTERY_BOX)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    GREEN_DRAGONS("Green dragons", "Kill 30 green dragons", "70+", "Dangerous", new Tile(3343, 3664), 30, GREEN_DRAGONS_DAILY_TASK_COMPLETION_AMOUNT, GREEN_DRAGONS_DAILY_TASK_COMPLETED, GREEN_DRAGONS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(XMAS_TOKENS, 250), new Item(COINS_995, 1_000_000), new Item(DRAGON_BONES+1,150), new Item(POINTS_MYSTERY_BOX+1, 3)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    IMPLING("Impling catching", "Catch 35 implings", "Any!", "Safe", new Tile(2592,4317), 35, IMPLING_DAILY_TASK_COMPLETION_AMOUNT, IMPLING_DAILY_TASK_COMPLETED, IMPLING_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(XMAS_TOKENS, 250), new Item(COINS_995, 1_000_000), new Item(DRAGON_BONES+1,150), new Item(POINTS_MYSTERY_BOX+1, 3)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    RAIDING("Raids", "Complete 5 raids of any type", "126+", "Safe", new Tile(1245, 3561), 5, RAIDS_DAILY_TASK_COMPLETION_AMOUNT, RAIDS_DAILY_TASK_COMPLETED, RAIDS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.HARD, new Item(XMAS_TOKENS, 2500), new Item(COINS_995, 2_500_000), new Item(POINTS_MYSTERY_BOX+1,5), new Item(POINTS_MYSTERY_CHEST)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    ZULRAH("Zulrah", "Kill 15 Zulrah", "90+", "Safe", new Tile(2204, 3056), 15, ZULRAH_DAILY_TASK_COMPLETION_AMOUNT, ZULRAH_DAILY_TASK_COMPLETED, ZULRAH_DAILY_TASK_REWARD_CLAIMED, TaskCategory.HARD, /*new Item(XMAS_TOKENS, 1000),*/ new Item(COINS_995, 1_500_000), new Item(POINTS_MYSTERY_BOX+1,3)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    CORRUPTED_NECHRYARCH("Corrupted Nechryarch", "Kill 15 Corrupted Nechryarch", "90+", "Safe", new Tile(1885, 3865), 15, CORRUPTED_NECHRYARCH_DAILY_TASK_COMPLETION_AMOUNT, CORRUPTED_NECHRYARCH_DAILY_TASK_COMPLETED, CORRUPTED_NECHRYARCH_DAILY_TASK_REWARD_CLAIMED, TaskCategory.HARD, /*new Item(XMAS_TOKENS, 1000),*/ new Item(COINS_995, 1_500_000), new Item(DONATOR_MYSTERY_BOX)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    VALINOR_COINS("Valinor coins", "Exchange items for 1000 Valinor coins", "Any!", "Safe", new Tile(3109, 3500), 1, VALINOR_COINS_DAILY_TASK_COMPLETION_AMOUNT, VALINOR_COINS_DAILY_TASK_COMPLETED, VALINOR_COINS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.HARD, /*new Item(XMAS_TOKENS, 500),*/ new Item(DONATOR_MYSTERY_BOX,1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    ;

    public final String taskName;
    public final String taskDescription;
    public final String combatRequirement;
    public final String location;
    public final Tile tile;
    public final int completionAmount;
    public final AttributeKey key;
    public final AttributeKey completed;
    public final AttributeKey rewardClaimed;
    public final TaskCategory category;
    public final Item[] rewards;

    DailyTasks(String taskName, String taskDescription, String combatRequirement, String location, Tile tile, int completionAmount, AttributeKey key, AttributeKey completed, AttributeKey rewardClaimed, TaskCategory category, Item... rewards) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.combatRequirement = combatRequirement;
        this.location = location;
        this.tile = tile;
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

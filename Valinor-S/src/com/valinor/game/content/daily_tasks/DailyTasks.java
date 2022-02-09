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

    ROCK_CRABS("Rock crabs", "Kill 20 rock crabs", "Any!", "Safe", new Tile(2706, 3713), 20, ROCK_CRABS_DAILY_TASK_COMPLETION_AMOUNT, ROCK_CRABS_DAILY_TASK_COMPLETED, ROCK_CRABS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(WINTER_TOKENS, 200), new Item(COINS_995, 1_000_000), new Item(DOUBLE_DROPS_SCROLL, 15), new Item(CRYSTAL_KEY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    EXPERIMENTS("Experiments", "Kill 20 experiments", "25+", "Safe", new Tile(3556, 9944), 20, EXPERIMENTS_DAILY_TASK_COMPLETION_AMOUNT, EXPERIMENTS_DAILY_TASK_COMPLETED, EXPERIMENTS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(WINTER_TOKENS, 200), new Item(COINS_995, 1_000_000), new Item(DOUBLE_DROPS_SCROLL, 15), new Item(CRYSTAL_KEY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    THIEVING("Thieving", "Steal from 100 stalls or pick-pockets", "Any!", "Safe", new Tile(3084,3475), 100, THIEVING_DAILY_TASK_COMPLETION_AMOUNT, THIEVING_DAILY_TASK_COMPLETED, THIEVING_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(WINTER_TOKENS, 200), new Item(COINS_995, 1_000_000), new Item(DOUBLE_DROPS_SCROLL, 15)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    VOTING("Voting", "Vote for us on 3 top list sites", "Any!", "Safe", new Tile(3100, 3505), 1, VOTING_DAILY_TASK_COMPLETION_AMOUNT, VOTING_DAILY_TASK_COMPLETED, VOTING_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(WINTER_TOKENS, 700), new Item(VOTE_TICKET, 1), new Item(COINS_995, 1_000_000), new Item(POINTS_MYSTERY_BOX, 1), new Item(CRYSTAL_KEY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    SKILLING_TASKS("Skilling tasks", "Complete 3 skilling tasks", "Any!", "Safe", new Tile(3083,3488), 3, SKILLING_DAILY_TASK_COMPLETION_AMOUNT, SKILLING_DAILY_TASK_COMPLETED, SKILLING_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(WINTER_TOKENS, 200), new Item(VOTE_TICKET, 1), new Item(COINS_995, 1_000_000), new Item(POINTS_MYSTERY_BOX, 1), new Item(CRYSTAL_KEY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    SLAYER("Slayer", "Complete 5 slayer tasks","25+", "Safe", null, 5, SLAYER_DAILY_TASK_COMPLETION_AMOUNT, SLAYER_DAILY_TASK_COMPLETED, SLAYER_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(WINTER_TOKENS, 400), new Item(COINS_995, 1_000_000), new Item(DOUBLE_DROPS_SCROLL, 15), new Item(POINTS_MYSTERY_CHEST, 1), new Item(SLAYER_TELEPORT_SCROLL, 5)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    PVM_TASKS("PvMing tasks", "Complete 3 pvming tasks", "75+", "Safe", new Tile(3083,3488), 3, PVMING_DAILY_TASK_COMPLETION_AMOUNT, PVMING_DAILY_TASK_COMPLETED, PVMING_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(WINTER_TOKENS, 400), new Item(VOTE_TICKET, 1), new Item(COINS_995, 1_000_000), new Item(POINTS_MYSTERY_BOX, 1), new Item(CRYSTAL_KEY, 1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    BARROWS("Barrows", "Complete 3 barrows runs", "70+", "Safe", new Tile(3565, 3306), 3, BARROWS_TASK_COMPLETION_AMOUNT, BARROWS_DAILY_TASK_COMPLETED, BARROWS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(WINTER_TOKENS, 200), new Item(COINS_995, 1_500_000), new Item(BARROWS_MYSTERY_BOX)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    GREEN_DRAGONS("Green dragons", "Kill 30 green dragons", "70+", "Dangerous", new Tile(3343, 3664), 30, GREEN_DRAGONS_DAILY_TASK_COMPLETION_AMOUNT, GREEN_DRAGONS_DAILY_TASK_COMPLETED, GREEN_DRAGONS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(WINTER_TOKENS, 200), new Item(COINS_995, 1_000_000), new Item(DRAGON_BONES+1,150), new Item(POINTS_MYSTERY_BOX+1, 3)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    IMPLING("Impling catching", "Catch 35 implings", "Any!", "Safe", new Tile(2592,4317), 35, IMPLING_DAILY_TASK_COMPLETION_AMOUNT, IMPLING_DAILY_TASK_COMPLETED, IMPLING_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(WINTER_TOKENS, 200), new Item(COINS_995, 1_000_000), new Item(DRAGON_BONES+1,150), new Item(POINTS_MYSTERY_BOX+1, 3)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    RAIDING("Raids", "Complete 5 raids of any type", "126+", "Safe", new Tile(1245, 3561), 5, RAIDS_DAILY_TASK_COMPLETION_AMOUNT, RAIDS_DAILY_TASK_COMPLETED, RAIDS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.HARD, new Item(WINTER_TOKENS, 400), new Item(COINS_995, 2_500_000), new Item(POINTS_MYSTERY_BOX+1,5), new Item(POINTS_MYSTERY_CHEST)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    ZULRAH("Zulrah", "Kill 15 Zulrah", "90+", "Safe", new Tile(2204, 3056), 15, ZULRAH_DAILY_TASK_COMPLETION_AMOUNT, ZULRAH_DAILY_TASK_COMPLETED, ZULRAH_DAILY_TASK_REWARD_CLAIMED, TaskCategory.HARD, new Item(WINTER_TOKENS, 550), new Item(COINS_995, 1_500_000), new Item(POINTS_MYSTERY_BOX+1,3)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    CORRUPTED_NECHRYARCH("Nechryarch", "Kill 15 Corrupted Nechryarch", "90+", "Safe", new Tile(1885, 3865), 15, CORRUPTED_NECHRYARCH_DAILY_TASK_COMPLETION_AMOUNT, CORRUPTED_NECHRYARCH_DAILY_TASK_COMPLETED, CORRUPTED_NECHRYARCH_DAILY_TASK_REWARD_CLAIMED, TaskCategory.HARD, new Item(WINTER_TOKENS, 500), new Item(COINS_995, 1_500_000), new Item(DONATOR_MYSTERY_BOX)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    VALINOR_COINS("Valinor coins", "Exchange items for 1.000 Valinor coins", "Any!", "Safe", new Tile(3109, 3500), 1, VALINOR_COINS_DAILY_TASK_COMPLETION_AMOUNT, VALINOR_COINS_DAILY_TASK_COMPLETED, VALINOR_COINS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.HARD, new Item(WINTER_TOKENS, 2500), new Item(DONATOR_MYSTERY_BOX,1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    TOURNY("PvP Tournament", "Participate in 1 pvp tournament", "Any!", "Safe", new Tile(3081, 3505), 1, TOURNY_DAILY_TASK_COMPLETION_AMOUNT, TOURNY_DAILY_TASK_COMPLETED, TOURNY_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(WINTER_TOKENS, 200), new Item(DONATOR_MYSTERY_BOX,1), new Item(DOUBLE_DROPS_SCROLL,15), new Item(POINTS_MYSTERY_BOX,2)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    STARDUST("Stardust", "Mine 1.000 stardust", "Any!", "Safe", null, 1000, STARDUST_DAILY_TASK_COMPLETION_AMOUNT, STARDUST_DAILY_TASK_COMPLETED, STARDUST_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(WINTER_TOKENS, 200), new Item(DONATOR_MYSTERY_BOX,1), new Item(DOUBLE_DROPS_SCROLL,5), new Item(COINS_995,1_000_000), new Item(POINTS_MYSTERY_BOX,1), new Item(CRYSTAL_KEY,1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    WORLD_BOSS("World boss", "Kill 5 world boss of any kind", "80+", "Safe", null, 5, WORLD_BOSS_DAILY_TASK_COMPLETION_AMOUNT, WORLD_BOSS_DAILY_TASK_COMPLETED, WORLD_BOSS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.EASY, new Item(WINTER_TOKENS, 250), new Item(DOUBLE_DROPS_SCROLL,10), new Item(COINS_995,2_500_000), new Item(POINTS_MYSTERY_BOX,2), new Item(CRYSTAL_KEY,2)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    REVENANTS("Revenants", "Kill 150 revenants", "100+", "Dangerous!", new Tile(3127, 3832), 150, REVENANTS_DAILY_TASK_COMPLETION_AMOUNT, REVENANTS_DAILY_TASK_COMPLETED, REVENANTS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(WINTER_TOKENS, 650), new Item(DOUBLE_DROPS_SCROLL,15), new Item(COINS_995,3_000_000), new Item(POINTS_MYSTERY_BOX,2), new Item(CRYSTAL_KEY,1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    KRAKENS("Krakens", "Kill 30 Krakens", "80+", "Safe", new Tile(2280, 10016), 30, KRAKEN_DAILY_TASK_COMPLETION_AMOUNT, KRAKEN_DAILY_TASK_COMPLETED, KRAKEN_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(WINTER_TOKENS, 450), new Item(DOUBLE_DROPS_SCROLL,5), new Item(COINS_995,1_500_000), new Item(POINTS_MYSTERY_BOX,1), new Item(CRYSTAL_KEY,1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    BATTLE_MAGES("Battle Mages", "Kill 100 battle mages", "100+", "Dangerous", new Tile(2539, 4716), 100, BATTLE_MAGE_DAILY_TASK_COMPLETION_AMOUNT, BATTLE_MAGE_DAILY_TASK_COMPLETED, BATTLE_MAGE_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(WINTER_TOKENS, 450), new Item(DOUBLE_DROPS_SCROLL,40), new Item(COINS_995,4_000_000), new Item(POINTS_MYSTERY_BOX,2), new Item(CRYSTAL_KEY,2)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    NEX("Nex", "Kill Nex 30 times", "115+", "Safe", new Tile(2904, 5203), 30, NEX_DAILY_TASK_COMPLETION_AMOUNT, NEX_DAILY_TASK_COMPLETED, NEX_DAILY_TASK_REWARD_CLAIMED, TaskCategory.HARD, new Item(WINTER_TOKENS, 1000), new Item(DOUBLE_DROPS_SCROLL,30), new Item(COINS_995,3_000_000), new Item(POINTS_MYSTERY_BOX,3), new Item(CRYSTAL_KEY,3)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    DEMONIC_GORILLAS("Demonic gorilla", "Kill 100 Demonic Gorillas", "100+", "Safe", new Tile(2128, 5647), 100, DEMONIC_GORILLA_DAILY_TASK_COMPLETION_AMOUNT, DEMONIC_GORILLA_DAILY_TASK_COMPLETED, DEMONIC_GORILLA_DAILY_TASK_REWARD_CLAIMED, TaskCategory.MED, new Item(WINTER_TOKENS, 750), new Item(DOUBLE_DROPS_SCROLL,15), new Item(COINS_995,3_000_000), new Item(POINTS_MYSTERY_BOX,2), new Item(CRYSTAL_KEY,2)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    WIN_PVP_TOURNY("Win Tourny", "Win 1 PvP tournament", "Any!", "Safe", new Tile(3081, 3505), 1, WIN_PVP_TOURNAMENT_DAILY_TASK_COMPLETION_AMOUNT, WIN_PVP_TOURNAMENT_DAILY_TASK_COMPLETED, WIN_PVP_TOURNAMENT_DAILY_TASK_REWARD_CLAIMED, TaskCategory.HARD, new Item(WINTER_TOKENS, 650), new Item(DOUBLE_DROPS_SCROLL,50), new Item(POINTS_MYSTERY_BOX,2), new Item(CRYSTAL_KEY,2), new Item(COINS_995,5_000_000)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    WILDY_KEY_ESCAPE("Wildy key escape", "Escape with the wilderness key 2 times", "120+", "Dangerous", null, 2, WILDY_KEY_ESCAPE_DAILY_TASK_COMPLETION_AMOUNT, WILDY_KEY_ESCAPE_DAILY_TASK_COMPLETED, WILDY_KEY_ESCAPE_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVP, new Item(WINTER_TOKENS, 1000), new Item(DONATOR_MYSTERY_BOX,1), new Item(WILDERNESS_KEY,1), new Item(COINS_995,10_000_000), new Item(POINTS_MYSTERY_CHEST,2), new Item(VOTE_TICKET,3)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    KILL_PLAYERS_REV_CAVE("Rev cave", "Kill 7 players inside the revenants cave", "100+", "Dangerous", new Tile(3130, 3828), 7, KILL_PLAYERS_REV_CAVE_DAILY_TASK_COMPLETION_AMOUNT, KILL_PLAYERS_REV_CAVE_DAILY_TASK_COMPLETED, KILL_PLAYERS_REV_CAVE_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVP, new Item(WINTER_TOKENS, 750), new Item(MORRIGANS_JAVELIN,100), new Item(MORRIGANS_THROWING_AXE,100), new Item(COINS_995,2_500_000), new Item(POINTS_MYSTERY_CHEST,2), new Item(DONATOR_MYSTERY_BOX,1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    DEEP_WILD("Deep wild", "Kill 10 players above level 30 wilderness", "100+", "Dangerous", null, 10, DEEP_WILD_DAILY_TASK_COMPLETION_AMOUNT, DEEP_WILD_DAILY_TASK_COMPLETED, DEEP_WILD_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVP, new Item(WINTER_TOKENS, 1000), new Item(COINS_995,2_000_000), new Item(DOUBLE_DROPS_SCROLL,20), new Item(DONATOR_MYSTERY_BOX,1)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    KILLSTREAK("Killstreak", "Achieve a kill streak of 10", "100+", "Dangerous", null, 1, KILLSTREAK_DAILY_TASK_COMPLETION_AMOUNT, KILLSTREAK_DAILY_TASK_COMPLETED, KILLSTREAK_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVP, new Item(WINTER_TOKENS, 1000), new Item(DONATOR_MYSTERY_BOX,2), new Item(COINS_995,3_000_000), new Item(POINTS_MYSTERY_CHEST,2)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    EDGEVILE_KILLS("Edgevile", "Kill 10 players at edgeville", "70+", "Dangerous", new Tile(3095, 3516), 10, EDGEVILE_KILLS_DAILY_TASK_COMPLETION_AMOUNT, EDGEVILE_KILLS_DAILY_TASK_COMPLETED, EDGEVILE_KILLS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVP, new Item(WINTER_TOKENS, 650), new Item(DONATOR_MYSTERY_BOX,1), new Item(COINS_995,3_500_000), new Item(DOUBLE_DROPS_SCROLL,30), new Item(POINTS_MYSTERY_CHEST,2), new Item(VOTE_TICKET,2)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    MAGE_BANK_KILLS("Mage bank", "kill 5 players at the mage bank", "100+", "Dangerous", new Tile(2539, 4716), 5, MAGE_BANK_KILLS_DAILY_TASK_COMPLETION_AMOUNT, MAGE_BANK_KILLS_DAILY_TASK_COMPLETED, MAGE_BANK_KILLS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVP, new Item(WINTER_TOKENS, 650), new Item(DONATOR_MYSTERY_BOX,1), new Item(COINS_995,3_000_000)) {
        @Override
        public boolean canIncrease(Player player) {
            return !player.<Boolean>getAttribOr(completed, false);
        }
    },

    MEMBER_CAVE_KILLS("Member cave", "Kill 5 players inside member cave", "100+", "Dangerous", null, 5, MEMBER_CAVE_KILLS_DAILY_TASK_COMPLETION_AMOUNT, MEMBER_CAVE_KILLS_DAILY_TASK_COMPLETED, MEMBER_CAVE_KILLS_DAILY_TASK_REWARD_CLAIMED, TaskCategory.PVP, new Item(WINTER_TOKENS, 650), new Item(DONATOR_MYSTERY_BOX,1), new Item(COINS_995,2_000_000), new Item(DOUBLE_DROPS_SCROLL,10)) {
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

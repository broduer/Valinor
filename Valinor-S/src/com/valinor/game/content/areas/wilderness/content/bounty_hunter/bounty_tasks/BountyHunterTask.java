package com.valinor.game.content.areas.wilderness.content.bounty_hunter.bounty_tasks;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import static com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunterConstants.BOUNTY_HUNTER_TASK_TIME_LEFT;
import static com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunterConstants.BOUNTY_TASK_TIME;
import static com.valinor.game.world.entity.AttributeKey.BOUNTY_HUNTER_TASK;
import static com.valinor.game.world.entity.AttributeKey.BOUNTY_HUNTER_TASK_COMPLETION_AMOUNT;
import static com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers.PROTECT_ITEM;

/**
 * This class solely serves for Bounty Hunter Task information and completion.
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public class BountyHunterTask {

    /**
     * A more smooth way to check if the player meets the task requirements upon kill.
     * @param <T>
     */
    public interface BountyInterface<T> {

        void meetsRequirement(final T player);

    }

    public enum BountyTasks implements BountyInterface<Player> {
        NO_OVERHEAD_PRAYERS("Kill your target without the use of overhead Prayers", 1) {
            @Override
            public void meetsRequirement(Player player) {
                if (!Prayers.overheadPrayerActivated(player)) {
                    BountyHunterTask.activate(player, BountyHunterTask.BountyTasks.NO_OVERHEAD_PRAYERS, 1);
                }
            }
        },
        KILL_WITHOUT_PROTECT_ITEM("Kill your target without using Protect Item", 1) {
            @Override
            public void meetsRequirement(Player player) {
                if (!Prayers.usingPrayer(player, PROTECT_ITEM)) {
                    BountyHunterTask.activate(player, BountyTasks.KILL_WITHOUT_PROTECT_ITEM, 1);
                }
            }
        },
        KILL_WITHOUT_BODY_EQUIPPED("Kill your target with no body armour equipped", 1) {
            @Override
            public void meetsRequirement(Player player) {
                if (!player.getEquipment().hasChest()) {
                    BountyHunterTask.activate(player, BountyTasks.KILL_WITHOUT_BODY_EQUIPPED, 1);
                }
            }
        },
        KILL_WITHOUT_LEGS_EQUIPPED("Kill your target with no leg armour equipped", 1) {
            @Override
            public void meetsRequirement(Player player) {
                if (!player.getEquipment().hasLegs()) {
                    BountyHunterTask.activate(player, BountyTasks.KILL_WITHOUT_LEGS_EQUIPPED, 1);
                }
            }
        },
        KILL_WITHOUT_RING_OR_NECKLACE_EQUIPPED("Kill your target with no ring or neck armour equipped", 1) {
            @Override
            public void meetsRequirement(Player player) {
                if (!player.getEquipment().hasRing() || !player.getEquipment().hasAmulet()) {
                    BountyHunterTask.activate(player, BountyTasks.KILL_WITHOUT_RING_OR_NECKLACE_EQUIPPED, 1);
                }
            }
        };

        private final String taskDescription;
        private final int completionAmount;

        BountyTasks(String taskDescription, int completionAmount) {
            this.taskDescription = taskDescription;
            this.completionAmount = completionAmount;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public int getCompletionAmount() {
            return completionAmount;
        }

        /**
         * An array of all possible tasks
         */
        private static final BountyTasks[] bountyTasks = BountyTasks.values();

        /**
         * Picks a random bounty task
         */
        public static BountyTasks randomTask() {
            return bountyTasks[Utils.RANDOM.nextInt(bountyTasks.length)];
        }

        /**
         * Checks if the killer meets the requirements to complete his Bounty Task.
         * @param killer The killer to check
         */
        public static void checkOnKill(Player killer) {
            //Look for current task
            var bountyHunterTask = killer.<BountyTasks>getAttribOr(BOUNTY_HUNTER_TASK,null);

            if (bountyHunterTask != null) {
                bountyHunterTask.meetsRequirement(killer);
            }
        }
    }

    public static void resetBountyTask(Player player, boolean completed) {
        if (!completed) {
            player.message("<col="+ Color.MEDRED.getColorValue()+">You failed to complete your Bounty Task.");
        }
        player.getPacketSender().sendString(BOUNTY_HUNTER_TASK_TIME_LEFT, "---");
        player.clearAttrib(BOUNTY_HUNTER_TASK);
        player.clearAttrib(BOUNTY_HUNTER_TASK_COMPLETION_AMOUNT);
        player.putAttrib(AttributeKey.BOUNTY_TASK_TIMER, -1);
        player.putAttrib(AttributeKey.FAILED_BOUNTY_HUNTER_TASK, false);
    }

    /**
     * Sets a random bounty hunter task
     * @param player the player receiving the random task
     */
    public static void setBountyTask(Player player) {
        //Player already has a task
        if (player.hasBountyTask() && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            return;
        }

        //Player has to be inside the wilderness
        if (!WildernessArea.inWilderness(player.tile()) && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            return;
        }

        //Randomize a task
        BountyTasks randomTask = BountyHunterTask.BountyTasks.randomTask();

        //Save the enum type
        player.putAttrib(BOUNTY_HUNTER_TASK, randomTask);

        //Set the timer
        player.putAttrib(AttributeKey.BOUNTY_TASK_TIMER, BOUNTY_TASK_TIME);

        //System.out.println("timer set: " + player.getBountyTaskTimer());

        var bountyHunterTask = player.<BountyTasks>getAttribOr(BOUNTY_HUNTER_TASK,null);
        player.message("<col=" + Color.MEDRED.getColorValue() + ">Current Task:</col> " + bountyHunterTask.getTaskDescription() + ".");
    }

    /**
     * Activates the bounty task and handles completion.
     *
     * @param player   The player which has the task assigned
     * @param bountyTasks     The bounty task
     * @param increaseBy The increase amount should always be increased by one.
     */
    private static void activate(Player player, BountyTasks bountyTasks, int increaseBy) {
        // Safety checks
        if (bountyTasks == null) {
            return;
        }

        // Can't go over required completion amount
        var bountyHunterTask = player.<BountyTasks>getAttribOr(BOUNTY_HUNTER_TASK,null);
        if(bountyHunterTask == null) {
            return;
        }

        var completionAmount = player.<Integer>getAttribOr(BOUNTY_HUNTER_TASK_COMPLETION_AMOUNT,0);
        if (completionAmount >= bountyTasks.getCompletionAmount()) {
            return;
        }

        //Increase completion amount
        completionAmount += increaseBy;
        player.putAttrib(BOUNTY_HUNTER_TASK_COMPLETION_AMOUNT, completionAmount);

        //Task completed
        if (completionAmount >= bountyTasks.getCompletionAmount()) {
            player.message("<col=" + Color.MEDRED.getColorValue() + ">Bounty Task complete:</col> " + bountyHunterTask.getTaskDescription() + ".");
            resetBountyTask(player, true);

            int bountyTasksCompleted = (Integer) player.getAttribOr(AttributeKey.BOUNTY_TASKS_COMPLETED, 0) + 1;
            player.putAttrib(AttributeKey.BOUNTY_TASKS_COMPLETED, bountyTasksCompleted);

            var pkp = player.<Integer>getAttribOr(AttributeKey.PK_POINTS,0) + 250;
            player.putAttrib(AttributeKey.PK_POINTS, pkp);
            player.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(player));
            player.message("You were awarded with 250 extra PKP for completing your bounty task! You now have a total of "+ Utils.formatNumber(pkp)+" PKP!");

        }
    }

}

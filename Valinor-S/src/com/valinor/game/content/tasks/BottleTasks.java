package com.valinor.game.content.tasks;

import com.valinor.game.content.daily_tasks.TaskCategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patrick van Elderen | April, 08, 2021, 21:53
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public enum BottleTasks {

    //#Skilling tasks
    BONES_ON_ALTAR(125,"Pledge 125 bones on any altar.", TaskCategory.SKILLING_TASK),
    CRAFT_DEATH_RUNES(1000,"Craft 1000 death runes.", TaskCategory.SKILLING_TASK,"- Level 65 Runecrafting"),
    WILDERNESS_COURSE(35,"Complete 35 laps at the Wilderness agility course.", TaskCategory.SKILLING_TASK,"- Level 50 Agility"),
    MAKE_SUPER_COMBAT_POTIONS(100,"Make 100 super combat potions.", TaskCategory.SKILLING_TASK,"- Level 90 Herblore"),
    STEAL_FROM_GEM_STALL(150,"Steal from the gem stall 150 times.", TaskCategory.SKILLING_TASK,"- Level 75 Thieving"),
    CRAFT_DRAGONSTONES(200,"Craft 200 uncut dragonstone's.", TaskCategory.SKILLING_TASK,"- Level 55 Crafting"),
    MAGIC_BOW(200,"Make 200 magic bows.", TaskCategory.SKILLING_TASK,"- Level 80 Fletching"),
    COMPLETE_SLAYER_TASKS(10,"Complete 10 slayer tasks.", TaskCategory.SKILLING_TASK),
    BLACK_CHINCHOMPAS(100,"Catch 100 black chinchompas.", TaskCategory.SKILLING_TASK,"- Level 73 Hunter"),
    MINE_RUNITE_ORE(100,"Mine 100 Runite ores.", TaskCategory.SKILLING_TASK,"- Level 85 Mining"),
    MAKE_ADAMANT_PLATEBODY(100,"Create 100 Adamant platebodies.", TaskCategory.SKILLING_TASK,"- Level 88 Smithing"),
    CATCH_SHARKS(100,"Catch 100 sharks.", TaskCategory.SKILLING_TASK,"- Level 76 Fishing"),
    COOK_SHARKS(200,"Cook 200 sharks.", TaskCategory.SKILLING_TASK,"- Level 80 Cooking"),
    BURN_MAGIC_LOGS(100,"Burn 100 magic logs.", TaskCategory.SKILLING_TASK,"- Level 75 Firemaking"),
    CUT_MAGIC_TREES(100,"Cut down 100 Magic trees.", TaskCategory.SKILLING_TASK,"- Level 75 Woodcutting"),
    CUT_YEW_TREES(100,"Cut down 100 Yew trees.", TaskCategory.SKILLING_TASK,"- Level 60 Woodcutting"),
    PLANT_TORSTOL_SEED(5,"Plant 5 Torstol seeds.", TaskCategory.SKILLING_TASK,"- Level 85 Farming"),

    //#PVM tasks
    REVENANTS(250, "Kill 250 Revenants.", TaskCategory.PVMING_TASK),
    DRAGONS(200,"Kill 200 Dragons.", TaskCategory.PVMING_TASK, "- None"),
    CALLISTO(50,"Kill Callisto 50 times.", TaskCategory.PVMING_TASK),
    CERBERUS(40,"Kill Cerberus 40  times.", TaskCategory.PVMING_TASK),
    CHAOS_FANATIC(75,"Kill Chaos Fanatic 75 times.", TaskCategory.PVMING_TASK),
    CORPOREAL_BEAST(35,"Kill the Corporal Beast 35 times.", TaskCategory.PVMING_TASK),
    CRAZY_ARCHAEOLOGIST(75,"Kill the Crazy Archaeologist 75 times.", TaskCategory.PVMING_TASK),
    DEMONIC_GORILLA(50,"Kill 50 Demonic Gorillas.", TaskCategory.PVMING_TASK),
    KING_BLACK_DRAGON(75,"Kill the King Black Dragon 75 times.", TaskCategory.PVMING_TASK),
    KRAKEN(100,"Kill 100 Kraken.", TaskCategory.PVMING_TASK),
    LIZARDMAN_SHAMAN(100,"Kill 100 Lizardman Shamans.", TaskCategory.PVMING_TASK),
    THERMONUCLEAR_SMOKE_DEVIL(100,"Kill 100 Thermonuclear smoke devils.", TaskCategory.PVMING_TASK),
    VENENATIS(40,"Kill Venenatis 40 times.", TaskCategory.PVMING_TASK),
    VETION(40,"Kill Vet'ion 40 times.", TaskCategory.PVMING_TASK),
    SCORPIA(40,"Kill Scorpia 40 times.", TaskCategory.PVMING_TASK),
    CHAOS_ELEMENTAL(40,"Kill 40 Chaos Elementals.", TaskCategory.PVMING_TASK),
    ZULRAH(60,"Kill Zulrah 60 times.", TaskCategory.PVMING_TASK),
    VORKATH(25,"Kill Vorkath 25 times.", TaskCategory.PVMING_TASK),
    WORLD_BOSS(5,"Kill any world boss 5 times.", TaskCategory.PVMING_TASK),
    KALPHITE_QUEEN(50,"Kill the Kalphite Queen 50 times.", TaskCategory.PVMING_TASK),
    DAGANNOTH_KINGS(100,"Kill 100 Dagannoth Kings.", TaskCategory.PVMING_TASK),
    GIANT_MOLE(150,"Kill 150 Giant Moles.", TaskCategory.PVMING_TASK),
    ALCHEMICAL_HYDRA(20,"Kill 20 Alchemical Hydras.", TaskCategory.PVMING_TASK),
    ;

    private final int taskAmount;
    private final String task;
    private final TaskCategory taskCategory;
    private final String[] taskRequirements;

    public int getTaskAmount() {
        return taskAmount;
    }

    public String task() {
        return task;
    }

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public String[] getTaskRequirements() {
        return taskRequirements;
    }

    BottleTasks(int taskAmount, String task, TaskCategory taskCategory, String... requirements) {
        this.taskAmount = taskAmount;
        this.task = task;
        this.taskCategory = taskCategory;
        this.taskRequirements = requirements;
    }

    /**
     * Picks a random task from the Tasks enum.
     */
    public static BottleTasks randomTask(TaskCategory taskCategory) {
        List<BottleTasks> tasks;
        if(taskCategory == TaskCategory.SKILLING_TASK) {
            tasks = Arrays.stream(BottleTasks.values()).filter(task -> task.taskCategory != TaskCategory.PVMING_TASK).collect(Collectors.toList());
        } else {
            tasks = Arrays.stream(BottleTasks.values()).filter(task -> task.taskCategory != TaskCategory.SKILLING_TASK).collect(Collectors.toList());
        }
        Collections.shuffle(tasks);
        return tasks.get(0);
    }
}

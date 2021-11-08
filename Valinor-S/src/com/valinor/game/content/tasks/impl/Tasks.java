package com.valinor.game.content.tasks.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patrick van Elderen | April, 08, 2021, 21:53
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public enum Tasks {

    //#Default
    NONE(0,"", ""),

    //#Skilling tasks
    BONES_ON_ALTAR(125,"Pledge 100 bones on any altar.","- None"),
    CRAFT_DEATH_RUNES(1000,"Craft 1000 death runes.","- Level 65 Runecrafting"),
    WILDERNESS_COURSE(35,"Complete 35 laps at the Wilderness agility course.","- Level 50 Agility"),
    MAKE_SUPER_COMBAT_POTIONS(100,"Make 100 super combat potions.","- Level 90 Herblore"),
    STEAL_FROM_SCIMITAR_STALL(150,"Steal from the scimitar stall 150 times.","- Level 65 Thieving"),
    CRAFT_DRAGONSTONES(200,"Craft 200 uncut dragonstone's.","- Level 55 Crafting"),
    MAGIC_SHORTBOW(200,"Make 200 magic bows.","- Level 80 Fletching"),
    COMPLETE_SLAYER_TASKS(10,"Complete 10 slayer tasks.","- None"),
    BLACK_CHINCHOMPAS(100,"Catch 100 black chinchompas.","- Level 73 Hunter"),
    MINE_RUNITE_ORE(100,"Mine 100 Runite ores.","- Level 85 Mining"),
    MAKE_ADAMANT_PLATEBODY(100,"Create 100 Adamant platebodies.","- Level 88 Smithing"),
    CATCH_SHARKS(100,"Catch 100 sharks.","- Level 76 Fishing"),
    COOK_SHARKS(200,"Cook 200 sharks.","- Level 80 Cooking"),
    BURN_MAGIC_LOGS(100,"Burn 100 magic logs.","- Level 75 Firemaking"),
    CUT_MAGIC_TREES(100,"Cut down 100 Magic trees.","- Level 75 Woodcutting"),
    CUT_YEW_TREES(100,"Cut down 100 Yew trees.","- Level 60 Woodcutting"),
    PLANT_TORSTOL_SEED(25,"Plant 25 Torstol seeds.","- Level 85 Farming"),

    //#PVM tasks
    REVENANTS(250, "Kill 250 Revenants.","- None"),
    DRAGONS(200,"Kill 200 Dragons.", "- None"),
    CALLISTO(50,"Kill Callisto 50 times.","- None"),
    CERBERUS(40,"Kill Cerberus 40  times.","- None"),
    CHAOS_FANATIC(75,"Kill Chaos Fanatic 75 times.","- None"),
    CORPOREAL_BEAST(35,"Kill the Corporal Beast 35 times.","- None"),
    CRAZY_ARCHAEOLOGIST(75,"Kill the Crazy Archaeologist 75 times.","- None"),
    DEMONIC_GORILLA(50,"Kill 50 Demonic Gorillas.","- None"),
    KING_BLACK_DRAGON(75,"Kill the King Black Dragon 75 times.","- None"),
    KRAKEN(100,"Kill 100 Kraken.","- None"),
    LIZARDMAN_SHAMAN(100,"Kill 100 Lizardman Shamans.","- None"),
    THERMONUCLEAR_SMOKE_DEVIL(100,"Kill 100 Thermonuclear smoke devils.","- None"),
    VENENATIS(40,"Kill Venenatis 40 times.","- None"),
    VETION(40,"Kill Vet'ion 40 times.","- None"),
    SCORPIA(40,"Kill Scorpia 40 times.","- None"),
    CHAOS_ELEMENTAL(40,"Kill 40 Chaos Elementals.","- None"),
    ZULRAH(60,"Kill Zulrah 60 times.","- None"),
    VORKATH(25,"Kill Vorkath 25 times.","- None"),
    WORLD_BOSS(10,"Kill any world boss 10 times.","- None"),
    KALPHITE_QUEEN(50,"Kill the Kalphite Queen 50 times.","- None"),
    DAGANNOTH_KINGS(100,"Kill 100 Dagannoth Kings.","- None"),
    GIANT_MOLE(150,"Kill 150 Giant Moles.","- None"),
    ALCHEMICAL_HYDRA(35,"Kill 35 Alchemical Hydras.","- None"),
    ;

    private final int taskAmount;
    private final String task;
    private final String[] taskRequirements;

    public int getTaskAmount() {
        return taskAmount;
    }

    public String task() {
        return task;
    }

    public String[] getTaskRequirements() {
        return taskRequirements;
    }

    Tasks(int taskAmount, String task, String... requirements) {
        this.taskAmount = taskAmount;
        this.task = task;
        this.taskRequirements = requirements;
    }

    /**
     * Picks a random Skilling task from the Tasks enum.
     */
    public static Tasks randomTask() {
        List<Tasks> tasks = Arrays.stream(Tasks.values()).filter(task -> task != NONE).collect(Collectors.toList());
        Collections.shuffle(tasks);
        return tasks.get(0);
    }
}

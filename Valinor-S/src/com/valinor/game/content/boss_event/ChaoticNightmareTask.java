package com.valinor.game.content.boss_event;

import com.valinor.game.task.Task;


/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 03, 2022
 */
public class ChaoticNightmareTask extends Task {

    private static final boolean TESTING = false;
    public static final int TICKS_TILL_SPAWN = TESTING ? 100 : 12000;

    public ChaoticNightmareTask() {
        super("ChaoticNightmareTask", TICKS_TILL_SPAWN,false);
    }

    @Override
    public void execute() {
        ChaoticNightmare chaoticNightmare = ChaoticNightmare.getInstance();
        chaoticNightmare.startBossEvent();
    }
}

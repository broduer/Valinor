package com.valinor.game.content.events.chaotic_nightmare;

import com.valinor.game.task.Task;

import static com.valinor.game.content.events.chaotic_nightmare.ChaoticNightmare.EVENT_INTERVAL;


/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 03, 2022
 */
public class ChaoticNightmareTask extends Task {

    public ChaoticNightmareTask() {
        super("ChaoticNightmareTask", 100);
    }

    @Override
    public void execute() {
        ChaoticNightmare chaoticNightmare = ChaoticNightmare.getInstance();
        chaoticNightmare.startBossEvent();
    }
}

package com.valinor.game.content.boss_event;

import com.valinor.game.task.Task;

import static com.valinor.game.content.boss_event.WorldBossEvent.BOSS_EVENT_INTERVAL;

/**
 * @author Patrick van Elderen | February, 13, 2021, 09:11
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class WorldBossEventTask extends Task {

    public WorldBossEventTask() {
        super("WorldBossEventTask", BOSS_EVENT_INTERVAL,false);
    }

    @Override
    public void execute() {
        WorldBossEvent worldBossEvent = WorldBossEvent.getINSTANCE();
        worldBossEvent.startBossEvent();
    }
}

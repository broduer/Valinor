package com.valinor.game.content.events.boss_event;

import com.valinor.game.task.Task;

/**
 * @author Patrick van Elderen | February, 13, 2021, 09:11
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class WorldBossEventTask extends Task {

    public WorldBossEventTask() {
        super("WorldBossEventTask",100);
    }

    @Override
    public void execute() {
        WorldBossEvent worldBossEvent = WorldBossEvent.getINSTANCE();
        worldBossEvent.startBossEvent();
    }
}

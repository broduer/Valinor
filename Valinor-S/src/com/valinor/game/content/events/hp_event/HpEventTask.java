package com.valinor.game.content.events.hp_event;

import com.valinor.game.task.Task;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public class HpEventTask extends Task {

    public HpEventTask() {
        super("HpEventTask", 100);
    }

    @Override
    public void execute() {
        HpEvent hpEvent = HpEvent.getInstance();
        hpEvent.startEvent();
    }
}

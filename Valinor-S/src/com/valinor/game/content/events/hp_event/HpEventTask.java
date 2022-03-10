package com.valinor.game.content.events.hp_event;

import com.valinor.game.task.Task;

import static com.valinor.game.content.events.hp_event.HpEvent.EVENT_INTERVAL;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public class HpEventTask extends Task {

    public HpEventTask() {
        super("HpEventTask", EVENT_INTERVAL,true);
    }

    @Override
    public void execute() {
        HpEvent hpEvent = HpEvent.getInstance();
        hpEvent.startEvent();
    }
}

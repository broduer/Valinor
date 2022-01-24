package com.valinor.game.content.skill.impl.hunter;

import com.valinor.game.task.Task;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 24, 2022
 */
public class ClearTrapsTask extends Task {

    public ClearTrapsTask() {
        super("ClearTrapsTask", 1);
    }

    @Override
    public void execute() {
        Hunter.exec();
    }
}

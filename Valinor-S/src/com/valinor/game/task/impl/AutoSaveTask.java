package com.valinor.game.task.impl;

import com.valinor.GameServer;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;

/**
 * @author lare96 <http://github.com/lare96>
 */
public class AutoSaveTask extends Task {

    private final Player player;

    private AutoSaveTask(Player player) {
        super("AutoSaveTask", GameServer.properties().autosaveMinutes * 100);
        this.player = player;
    }

    @Override
    protected void execute() {
        if (!player.isRegistered()) {
            stop();
            return;
        }
        World.getWorld().ls.savePlayerAsync(player);
    }

    public static void start(Player player) {
        if (player.getAttribOr(AttributeKey.PLAYER_AUTO_SAVE_TASK_RUNNING, false))
            return;

        player.putAttrib(AttributeKey.PLAYER_AUTO_SAVE_TASK_RUNNING,true);
        TaskManager.submit(new AutoSaveTask(player));
    }
}

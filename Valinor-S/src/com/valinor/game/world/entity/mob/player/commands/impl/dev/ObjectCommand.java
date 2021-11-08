package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;

public class ObjectCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        ObjectManager.addObj(new GameObject(Integer.parseInt(parts[1]), player.tile().copy()));
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }

}

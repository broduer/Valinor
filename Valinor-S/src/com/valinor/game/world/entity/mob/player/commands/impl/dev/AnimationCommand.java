package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.world.entity.masks.animations.Animation;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

public class AnimationCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        int anim = Integer.parseInt(parts[1]);
        player.animate(new Animation(anim));
    }

    @Override
    public boolean canUse(Player player) {

        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }

}

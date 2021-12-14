package com.valinor.game.content.gambling;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;

public class GambleInterface extends Interaction {

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        return player.getGamblingSession() != null && player.getGamblingSession().handleButton(button);
    }
}

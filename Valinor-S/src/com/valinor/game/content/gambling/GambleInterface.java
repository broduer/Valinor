package com.valinor.game.content.gambling;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.PacketInteraction;

public class GambleInterface extends PacketInteraction {

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        return player.getGamblingSession() != null && player.getGamblingSession().handleButton(button);
    }
}

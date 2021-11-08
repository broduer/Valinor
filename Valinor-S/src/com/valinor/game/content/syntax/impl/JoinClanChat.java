package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.clan.ClanManager;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.mob.player.Player;

public class JoinClanChat implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, String input) {
        ClanManager.join(player, input);
    }

    @Override
    public void handleSyntax(Player player, long input) {

    }
}

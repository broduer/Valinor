package com.valinor.game.world.entity.mob.player.commands.impl.member;

import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * mei 21, 2020
 */
public class HealCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        boolean extremeMember = player.getMemberRights().isEliteMemberOrGreater(player);
        if(!extremeMember && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("You need to be at least a elite member to use this command.");
            return;
        }
        if(((!player.tile().inArea(Tile.EDGEVILE_HOME_AREA) || WildernessArea.inWilderness(player.tile()))) && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("<col="+ Color.RED.getColorValue()+">You can only restore your health at home.");
            return;
        }
        if (CombatFactory.inCombat(player) && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("You cannot use this command during combat.");
            return;
        }
        player.heal();
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}

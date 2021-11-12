package com.valinor.game.world.entity.mob.player.commands.impl.member;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.master.impl.SlayerMasterDialogue;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.areas.impl.WildernessArea;

/**
 * @author Patrick van Elderen | June, 10, 2021, 22:27
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class NewTaskCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(!player.getMemberRights().isDragonstoneMemberOrGreater(player)) {
            player.message("You have to be at least an legendary member to use this command.");
            return;
        }

        if(WildernessArea.inWilderness(player.tile())) {
            player.message("You can't use that command here.");
            return;
        }

        Slayer.cancelTask(player,true);
        player.getDialogueManager().start(new SlayerMasterDialogue());
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}

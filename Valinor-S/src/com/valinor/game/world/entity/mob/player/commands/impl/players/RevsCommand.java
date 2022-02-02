package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen | January, 11, 2021, 18:08
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class RevsCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Level 17 entrance.", "Level 39 entrance.");
                setPhase(1);
            }

            @Override
            protected void select(int option) {
                if(isPhase(1)) {
                    if (option == 1) {
                        Tile tile = new Tile(3075, 3651, 0);
                        if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                            stop();
                            return;
                        }
                        Teleports.basicTeleport(player,tile);
                        player.message("You have been teleported to the revenants cave.");
                        stop();
                    } else if (option == 2) {
                        Tile tile = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.REVENANT_TELEPORT) ? new Tile(3244, 10145, 0) : new Tile(3130, 3828);;
                        if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                            stop();
                            return;
                        }
                        Teleports.basicTeleport(player, tile);
                        player.message("You have been teleported to the revenants cave.");
                        stop();
                    }
                }
            }
        });
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}

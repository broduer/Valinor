package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.game.world.InterfaceConstants;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;
import com.valinor.util.Utils;

import java.util.Optional;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 16, 2022
 */
public class CheckInventoryCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (command.length() <= 9)
            return;
        String username = Utils.formatText(command.substring(9)); // after "checkinv "

        Optional<Player> other = World.getWorld().getPlayerByName(username);

        if(other.isPresent()) {
            player.lock();
            otherInventory(player, other.get());
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Quit viewing", "");
                    setPhase(0);
                }

                @Override
                protected void select(int option) {
                    if(isPhase(0)) {
                        player.unlock();
                        player.inventory().refresh();
                        stop();
                    }
                }
            });
        } else {
            player.message(username+" does not exist or is offline.");
        }
    }

    private void otherInventory(Player player, Player other) {
        //Safety
        if(other == player || other == null || player == null)
            return;
        Item[] inventory = other.inventory().toArray();
        player.getPacketSender().sendItemOnInterface(InterfaceConstants.INVENTORY_INTERFACE, inventory);
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isAdminOrGreater(player));
    }
}

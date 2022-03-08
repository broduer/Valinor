package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.areas.wilderness.content.hitman_services.Hitman;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Color;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.valinor.util.CustomItemIdentifiers.PKP_TICKET;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 08, 2022
 */
public class SetPersonOfInterest implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, @NotNull String input) {
        Optional<Player> personOfInterest = World.getWorld().getPlayerByName(input);

        if (personOfInterest.isEmpty()) {
            player.message(Color.RED.wrap(input + " is not online, there for you cannot set a bounty."));
            close(player);
            return;
        }

        //Save the target
        Player target = personOfInterest.get();

        if (target == player) {
            player.message(Color.RED.wrap("You can't place a bounty on yourself."));
            close(player);
            return;
        }

        final int[] bountyAmount = {0};
        player.setEnterSyntax(new EnterSyntax() {
            @Override
            public void handleSyntax(Player player, long input) {
                int amountToSet = (int) input;

                if (amountToSet < 10_000) {
                    player.message(Color.RED.wrap("You have to set a minimum bounty of 10K PKP."));
                    close(player);
                    return;
                }

                var canSetBounty = false;
                int pkpInInventory = player.inventory().count(PKP_TICKET);
                if (pkpInInventory >= amountToSet) {
                    canSetBounty = true;
                }

                var pkPoints = player.<Integer>getAttribOr(AttributeKey.PK_POINTS, 0);
                if (pkPoints >= amountToSet) {
                    canSetBounty = true;
                }

                //Player has enough pkp, proceed.
                if (canSetBounty) {
                    bountyAmount[0] = amountToSet;
                    Hitman.setBounty(player, target, bountyAmount[0]);
                } else {
                    player.message("You do not have enough PKP to set a bounty.");
                }
            }
        });
        player.getPacketSender().sendEnterAmountPrompt("Enter your bounty.");
    }
}

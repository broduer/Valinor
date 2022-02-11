package com.valinor.game.content.areas.wilderness.content.bounty_hunter.dialogue;

import com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunter;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.TargetPair;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.util.Color;

import java.util.Optional;


/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public class SkipTargetD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.STATEMENT, "<col="+ Color.MEDRED.getColorValue()+">Warning:</col> Skipping too many targets in a short period of time can", "cause you to incur a target restriction penalty. You should not use", "this too frequently. You have abandoned your target once recently.");
        setPhase(0);
    }

    @Override
    public void next() {
        if (isPhase(0)) {
            send(DialogueType.OPTION,"Do you want to skip your target?", "Yes - there is no penalty.", "No.");
            setPhase(1);
        }
    }

    @Override
    public void select(int option) {
        if (isPhase(1)) {
            if (option == 1) {
                final Optional<TargetPair> pair = BountyHunter.getPairFor(player);
                if (pair.isPresent()) {
                    BountyHunter.unassign(player);
                    player.message("<col="+Color.MEDRED.getColorValue()+">You have abandoned your target. This skip was penalty free.");
                } else {
                    player.message("You have no target to skip.");
                }
                stop();
            } else if (option == 2) {
                stop();
            }
        }
    }
}

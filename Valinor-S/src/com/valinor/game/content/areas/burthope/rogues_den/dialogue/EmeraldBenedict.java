package com.valinor.game.content.areas.burthope.rogues_den.dialogue;

import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;

import static com.valinor.util.NpcIdentifiers.EMERALD_BENEDICT;

/**
 * @author Patrick van Elderen | March, 26, 2021, 09:35
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class EmeraldBenedict extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.NPC_STATEMENT, EMERALD_BENEDICT, Expression.EVIL, "Got anything you don't want to lose?");
        setPhase(0);
    }

    @Override
    protected void next() {
        if (isPhase(0)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Yes actually, can you help?", "Yes, but can you show me my PIN settings?", "Yes, but I'd like to collect items now.", "Yes thanks, and I'll keep hold of it too.");
            setPhase(1);
        } else if (isPhase(2)) {
            stop();
        }
    }

    @Override
    protected void select(int option) {
        if (isPhase(1)) {
            if (option == 1) {
                stop();
                player.getBank().open();
            } else if (option == 2) {
                player.getBankPinSettings().open(EMERALD_BENEDICT);
            } else if (option == 3) {
                send(DialogueType.NPC_STATEMENT, EMERALD_BENEDICT, Expression.ON_ONE_HAND, "We don't have grand exchange support.");
                setPhase(2);
            } else if (option == 4) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_FOUR, "Yes thanks, and I'll keep hold of it too.");
                setPhase(2);
            }
        }
    }
}

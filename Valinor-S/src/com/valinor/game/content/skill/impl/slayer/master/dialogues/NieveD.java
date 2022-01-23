package com.valinor.game.content.skill.impl.slayer.master.dialogues;

import com.valinor.game.content.skill.impl.slayer.master.impl.Nieve;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;

import static com.valinor.util.NpcIdentifiers.NIEVE;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 18, 2021
 */
public class NieveD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.NPC_STATEMENT, NIEVE, Expression.NODDING_ONE, "'Ello, and what are you after then?");
        setPhase(0);
    }

    @Override
    protected void next() {
        if(isPhase(0)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "I need another assignment.", "Have you any rewards for me, or anything to trade?", "Let's talk about the difficulty of my assignments.", "Er... Nothing...");
            setPhase(1);
        } else if (isPhase(2)) {
            Nieve.giveTask(player);
        } else if (isPhase(5)) {
            send(DialogueType.NPC_STATEMENT, NIEVE, Expression.NODDING_FIVE, "I have quite a few rewards you can earn, and a wide", "variety of Slayer equipment for sale.");
            setPhase(6);
        } else if (isPhase(6)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Look at rewards.", "Look at shop.");
            setPhase(7);
        } else if (isPhase(8)) {
            stop();
        }
    }

    @Override
    protected void select(int option) {
        if (isPhase(1)) {
            if (option == 1) {
                send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "I need another assignment.");
                setPhase(2);
            } else if (option == 2) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Have you any rewards for me, or anything to trade?");
                setPhase(5);
            } else if (option == 3) {
                stop();//TODO OSS doesn't have this option need to YT
            } else if (option == 4) {
                send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "Er... Nothing...");
                setPhase(8);
            }
        } else if (isPhase(7)) {
            if (option == 1) {
                stop();
                player.getSlayerRewards().open();
            } else if(option == 2) {
                stop();
                World.getWorld().shop(14).open(player);
            }
        }
    }
}

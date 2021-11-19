package com.valinor.game.content.skill.impl.slayer.master.dialogues;

import com.valinor.game.content.skill.impl.slayer.master.impl.Mazchna;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;

import static com.valinor.util.NpcIdentifiers.MAZCHNA;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 18, 2021
 */
public class MazchnaD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.NPC_STATEMENT, MAZCHNA, Expression.NODDING_ONE, "'Ello, and what are you after then?");
        setPhase(0);
    }

    @Override
    protected void next() {
        if(isPhase(0)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "I need another assignment.", "Have you any rewards for me, or anything to trade?", "Let's talk about the difficulty of my assignments.", "Er... Nothing...");
            setPhase(1);
        } else if (isPhase(2)) {
            // Tell the player if we can go Vannaka-mode
            if (player.skills().combatLevel() >= 40) {
                send(DialogueType.NPC_STATEMENT, MAZCHNA, Expression.ANXIOUS, "You're actually very strong, are you sure you don't", "want Vannaka under Edgeville to assign you a task?");
                setPhase(9);
            } else {
                Mazchna.giveTask(player);
            }
        } else if (isPhase(5)) {
            send(DialogueType.NPC_STATEMENT, MAZCHNA, Expression.NODDING_FIVE, "I have quite a few rewards you can earn, and a wide", "variety of Slayer equipment for sale.");
            setPhase(6);
        } else if (isPhase(6)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Look at rewards.", "Look at shop.");
            setPhase(7);
        } else if (isPhase(8)) {
            stop();
        } else if (isPhase(9)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "No that's okay, I'll take a task from you.", "Oh okay then, I'll go talk to Vannaka.");
            setPhase(10);
        } else if (isPhase(11)) {
            stop();
            Mazchna.giveTask(player);
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
        } else if (isPhase(10)) {
            if (option == 1) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_THREE, "No that's okay, I'll take a task from you.");
                setPhase(11);
            } else if(option == 2) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_THREE, "Oh okay then, I'll go talk to Vannaka.");
                setPhase(8);
            }
        }
    }
}

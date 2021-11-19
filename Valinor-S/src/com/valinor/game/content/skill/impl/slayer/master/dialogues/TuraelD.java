package com.valinor.game.content.skill.impl.slayer.master.dialogues;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.master.impl.Turael;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;

import static com.valinor.util.ItemIdentifiers.ENCHANTED_GEM;
import static com.valinor.util.NpcIdentifiers.TURAEL;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 18, 2021
 */
public class TuraelD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.NPC_STATEMENT, TURAEL, Expression.NODDING_ONE, "'Ello, and what are you after then?");
        setPhase(0);
    }

    @Override
    protected void next() {
        if (isPhase(0)) {
            boolean talked = (int) player.getAttribOr(AttributeKey.SLAYER_TASK_AMT, 0) > 0 || player.skills().xp()[Skills.SLAYER] > 0;
            if (talked) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "I need another assignment.", "Have you any rewards for me, or anything to trade?", "Let's talk about the difficulty of my assignments.", "Er... Nothing...");
                setPhase(21);
            } else {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Who are you?", "Have you any rewards for me, or anything to trade?", "Er... Nothing...");
                setPhase(1);
            }
        } else if (isPhase(2)) {
            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.NODDING_THREE, "I'm one of the elite Slayer Masters.");
            setPhase(3);
        } else if (isPhase(3)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "What's a slayer?", "Never heard of you...");
            setPhase(4);
        } else if (isPhase(5)) {
            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.SHAKING_HEAD_TWO, "Oh dear, what do they teach you in school?");
            setPhase(6);
        } else if (isPhase(6)) {
            send(DialogueType.PLAYER_STATEMENT, Expression.SLIGHTLY_SAD, "Well... er...");
            setPhase(7);
        } else if (isPhase(7)) {
            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.SHAKING_HEAD_THREE, "I suppose I'll have to educate you then. A slayer is", "someone who is trained to fight specific creatures. They", "know these creatures' every weakness and strength. As", "you can guess it makes killing them a lot easier.");
            setPhase(8);
        } else if (isPhase(8)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Wow, can you teach me?", "Sounds useless to me.");
            setPhase(9);
        } else if (isPhase(10)) {
            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.EVIL, "Hmmm well I'm not so sure...");
            setPhase(11);
        } else if (isPhase(11)) {
            send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_FOUR, "Pleeeaasssse!");
            setPhase(12);
        } else if (isPhase(12)) {
            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.NODDING_FIVE, "Oh okay then, you twisted my arm. You'll have to train", "against specific groups of creatures.");
            setPhase(13);
        } else if (isPhase(13)) {
            send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Okay, what's first?");
            setPhase(14);
        } else if (isPhase(14)) {
            player.getInventory().add(new Item(ENCHANTED_GEM), true);
            Turael.assignTask(player);

            SlayerCreature task = SlayerCreature.lookup(player.slayerTaskId());
            int num = player.slayerTaskAmount();

            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.ANXIOUS, "We'll start you off hunting "+ Slayer.taskName(task.uid)+", you'll need to kill "+num+"", "of them.");
            setPhase(15);
        } else if (isPhase(15)) {
            stop();
        } else if (isPhase(16)) {
            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.ANGRY, "Suit yourself.");
            setPhase(15);
        } else if (isPhase(17)) {
            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.BAD, "That's because my foe never lives to tell of me. We", "slayers are a dangerous bunch.");
            setPhase(15);
        } else if (isPhase(18)) {
            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.NODDING_FIVE, "I have quite a few rewards you can earn, and a wide", "variety of Slayer equipment for sale.");
            setPhase(19);
        } else if (isPhase(19)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Look at rewards.", "Look at shop.");
            setPhase(20);
        } else if (isPhase(22)) {
            // Tell the player if we can go Mazchna-mode
            if (player.skills().combatLevel() >= 20) {
                send(DialogueType.NPC_STATEMENT, TURAEL, Expression.ANXIOUS, "You're actually very strong, are you sure you don't", "want Mazchna in Morytania to assign you a task?");
                setPhase(23);
            } else {
                setPhase(24);
            }
        } else if (isPhase(23)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "No that's okay, I'll take a task from you.", "Oh okay then, I'll go talk to Mazchna.");
            setPhase(25);
        } else if(isPhase(24)) {
            // Time to check our task state. Can we hand out?
            int numleft = player.slayerTaskAmount();
            if (numleft > 0) {
                send(DialogueType.NPC_STATEMENT, TURAEL, Expression.H, "You're still hunting " + Slayer.taskName(player.slayerTaskId()) + "; you have " + numleft + " to go. Come", "back when you've finished your task.");
                setPhase(15);
                return;
            }

            // Give them a task.
            Turael.assignTask(player);

            SlayerCreature task = SlayerCreature.lookup(player.slayerTaskId());
            int num = player.slayerTaskAmount();
            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.ANXIOUS, "Excellent, you're doing great. Your new task is to kill "+num+" "+Slayer.taskName(task.uid)+".");
            setPhase(26);
        } else if(isPhase(26)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Got any tips for me?", "Okay, great!");
            setPhase(27);
        } else if(isPhase(28)) {
            SlayerCreature task = SlayerCreature.lookup(player.slayerTaskId());
            send(DialogueType.NPC_STATEMENT, TURAEL, Expression.HAPPY, Slayer.tipFor(task));
            setPhase(29);
        } else if(isPhase(29)) {
            send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Great, thanks!");
            setPhase(15);
        }
    }

    @Override
    protected void select(int option) {
        if (isPhase(1)) {
            if (option == 1) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Who are you?");
                setPhase(2);
            } else if(option == 2) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Have you any rewards for me, or anything to trade?");
                setPhase(18);
            } else if(option == 3) {
                send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "Er... Nothing...");
                setPhase(15);
            }
        } else if (isPhase(4)) {
            if (option == 1) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "What's a slayer?");
                setPhase(5);
            } else if(option == 2) {
                send(DialogueType.PLAYER_STATEMENT, Expression.SHAKING_HEAD_TWO, "Never heard of you...");
                setPhase(17);
            }
        } else if (isPhase(9)) {
            if (option == 1) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_THREE, "Wow, can you teach me?");
                setPhase(10);
            } else if(option == 2) {
                send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "Sounds useless to me.");
                setPhase(16);
            }
        } else if (isPhase(20)) {
            if (option == 1) {
                stop();
                player.getSlayerRewards().open();
            } else if(option == 2) {
                stop();
                World.getWorld().shop(14).open(player);
            }
        } else if (isPhase(21)) {
            if (option == 1) {
                send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "I need another assignment.");
                setPhase(22);
            } else if (option == 2) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Have you any rewards for me, or anything to trade?");
                setPhase(18);
            } else if (option == 3) {
                stop();//TODO OSS doesn't have this option need to YT
            } else if (option == 4) {
                send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "Er... Nothing...");
                setPhase(15);
            }
        } else if (isPhase(25)) {
            if(option == 1) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_THREE, "No that's okay, I'll take a task from you.");
                setPhase(24);
            } else if (option == 2) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_THREE, "Oh okay then, I'll go talk to Mazchna.");
                setPhase(15);
            }
        } else if (isPhase(27)) {
            if(option == 1) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Got any tips for me?");
                setPhase(28);
            } else if(option == 2) {
                send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "Okay, great!");
                setPhase(15);
            }
        }
    }
}

package com.valinor.game.content.account;

import com.valinor.GameServer;
import com.valinor.game.GameConstants;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.position.Tile;
import com.valinor.util.NpcIdentifiers;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 12, 2021
 */
public class Tutorial extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        if(player.getAttribOr(AttributeKey.CONTINUE_STARTER_TUTORIAL,false)) {
            send(DialogueType.NPC_STATEMENT, NpcIdentifiers.GIELINOR_GUIDE, Expression.CALM_TALK, "Would you like a quick starter tutorial?");
            setPhase(2);
        } else {
            send(DialogueType.NPC_STATEMENT, NpcIdentifiers.GIELINOR_GUIDE, Expression.HAPPY, "Welcome to " + GameConstants.SERVER_NAME + "!", "Let's start off by picking your game mode...");
            setPhase(1);
        }
    }

    @Override
    protected void next() {
        //System.out.println("phase "+getPhase());
        if (isPhase(1)) {
            stop();
            GameModeInterface.open(player, GameModeInterface.AccountType.REGULAR_ACCOUNT);
            //AccountSelection.open(player);
        } else if (isPhase(2)) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Yes.", "No.");
            setPhase(2);
        } else if (isPhase(3)) {
            send(DialogueType.NPC_STATEMENT, NpcIdentifiers.GIELINOR_GUIDE, Expression.CALM_TALK, "which has a 20% chance of doubling your drop", "for 60 minutes.");
            setPhase(4);
        } else if (isPhase(4)) {
            player.teleport(new Tile(3100, 3504));
            send(DialogueType.NPC_STATEMENT, NpcIdentifiers.GIELINOR_GUIDE, Expression.CALM_TALK, "You can also save up your vote points and spend them here.");
            setPhase(5);
        } else if (isPhase(5)) {
            player.teleport(new Tile(3104, 3506));
            send(DialogueType.NPC_STATEMENT, NpcIdentifiers.GIELINOR_GUIDE, Expression.CALM_TALK, "You can find the slayer masters here.", "If you would like a full guide for slayer ::slayerguide.", "We offer various perks to make your game experience better.");
            setPhase(6);
        } else if (isPhase(6)) {
            DailyTaskManager.openEasyList(player);
            send(DialogueType.NPC_STATEMENT, NpcIdentifiers.GIELINOR_GUIDE, Expression.CALM_TALK, "You can jump straight into money making", "with the daily activities, we offer various quick rewards.", "You can find these activities in the quest tab or magic book.");
            setPhase(7);
        } else if(isPhase(7)) {
            player.teleport(new Tile(3246, 10169));
            send(DialogueType.NPC_STATEMENT, NpcIdentifiers.GIELINOR_GUIDE, Expression.CALM_TALK, "And the revenants can be found here deep in the wilderness.", "You can use the teleporting tab or a quick access", "command for both entrances. ::revs offers to teleport you", "to the level 17 or level 39 entrance.");
            setPhase(8);
        } else if (isPhase(8)) {
            player.teleport(GameServer.properties().defaultTile);
            send(DialogueType.NPC_STATEMENT, NpcIdentifiers.GIELINOR_GUIDE, Expression.CALM_TALK, "Enjoy your stay here at " + GameConstants.SERVER_NAME + "!");
            setPhase(9);
        } else if (isPhase(9)) {
            stop();
            player.putAttrib(AttributeKey.NEW_ACCOUNT, false);
            player.unlock();
            player.looks().hide(false);
            player.inventory().addAll(GameConstants.STARTER_ITEMS.clone());
            player.inventory().refresh();//Force an inventory refresh
            player.message("You have been given some training equipment.");
        }
    }

    @Override
    protected void select(int option) {
        if (getPhase() == 2) {
            if (option == 1) {
                player.teleport(new Tile(3092, 3495));
                send(DialogueType.NPC_STATEMENT, NpcIdentifiers.GIELINOR_GUIDE, Expression.CALM_TALK, "To start off you should ::vote for starter money.", "Every first week of the month you get double points.", "You can sell the vote tickets in the trading post. You also", "get a double drops lamp.");
                setPhase(3);
            } else if (option == 2) {
                player.putAttrib(AttributeKey.NEW_ACCOUNT, false);
                player.looks().hide(false);
                player.inventory().addAll(GameConstants.STARTER_ITEMS.clone());
                player.inventory().refresh();//Force an inventory refresh
                player.message("You have been given some training equipment.");
                stop();
            }
        }
    }
}

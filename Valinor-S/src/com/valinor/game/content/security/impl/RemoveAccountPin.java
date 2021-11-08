package com.valinor.game.content.security.impl;

import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Color;

import static com.valinor.game.world.entity.AttributeKey.ACCOUNT_PIN;
import static com.valinor.game.world.entity.AttributeKey.ASK_FOR_ACCOUNT_PIN;
import static com.valinor.util.NpcIdentifiers.SECURITY_GUARD;

/**
 * @author Patrick van Elderen | May, 01, 2021, 15:56
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class RemoveAccountPin implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, long input) {
        String pinToString = Integer.toString((int) input);
        if(pinToString.equalsIgnoreCase(Integer.toString(player.<Integer>getAttribOr(ACCOUNT_PIN,0)))) {
            player.putAttrib(ACCOUNT_PIN,0);
            player.putAttrib(ASK_FOR_ACCOUNT_PIN,false);
            player.message(Color.GREEN.wrap("Your account pin has been removed."));
        } else {
            close(player);
            DialogueManager.npcChat(player, Expression.ANNOYED, SECURITY_GUARD,"Your account pin did not match!");
        }
    }
}

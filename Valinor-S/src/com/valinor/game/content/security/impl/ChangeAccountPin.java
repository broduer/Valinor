package com.valinor.game.content.security.impl;

import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.game.world.entity.AttributeKey.ACCOUNT_PIN;
import static com.valinor.util.NpcIdentifiers.SECURITY_GUARD;

/**
 * @author Patrick van Elderen | May, 01, 2021, 15:52
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class ChangeAccountPin implements EnterSyntax {

    @Override
    public void handleSyntax(Player player, long input) {
        String pinToString = Integer.toString((int) input);

        if(pinToString.equalsIgnoreCase(Integer.toString(player.<Integer>getAttribOr(ACCOUNT_PIN,0)))) {
            Chain.bound(player).runFn(2, () -> {
                player.setEnterSyntax(new SetupAccountPin());
                player.getPacketSender().sendEnterAmountPrompt("Fill in your account pin. (Exactly 5 digits)");
            });
        } else {
            DialogueManager.npcChat(player, Expression.ANNOYED, SECURITY_GUARD,"Your account pin did not match!");
        }
    }
}

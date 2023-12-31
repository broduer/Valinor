package com.valinor.game.task.impl;

import com.valinor.game.content.security.impl.EnterAccountPin;
import com.valinor.game.task.Task;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;

import static com.valinor.game.world.entity.AttributeKey.ACCOUNT_PIN_ATTEMPTS_LEFT;
import static com.valinor.game.world.entity.AttributeKey.ACCOUNT_PIN_FREEZE_TICKS;

/**
 * @author Patrick van Elderen | May, 01, 2021, 15:34
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class AccountPinFrozenTask extends Task {

    private final Player player;

    public AccountPinFrozenTask(Player player) {
        super("AccountPinFrozenTask",1, player,false);
        this.player = player;
    }

    @Override
    public void execute() {
        int ticksLeft = player.getAttribOr(ACCOUNT_PIN_FREEZE_TICKS, 0);

        if(ticksLeft > 0) {
            ticksLeft--;

            player.putAttrib(ACCOUNT_PIN_FREEZE_TICKS, ticksLeft);

            if(ticksLeft == 0) {
                player.putAttrib(ACCOUNT_PIN_ATTEMPTS_LEFT,5);
                player.setEnterSyntax(new EnterAccountPin());
                player.getPacketSender().sendEnterAmountPrompt("Enter your account pin, attempts left: "+player.<Integer>getAttribOr(AttributeKey.ACCOUNT_PIN_ATTEMPTS_LEFT,5)+"/5");
                stop();
            }
        }
    }
}

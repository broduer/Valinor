package com.valinor.game.content.skill.impl.farmingOld;

import com.valinor.game.task.Task;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;

import static com.valinor.util.ItemIdentifiers.BUCKET;

/**
 * @author Patrick van Elderen | March, 29, 2021, 09:35
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class FarmingCompostTask extends Task {

    private final Player attachment;

    public FarmingCompostTask(Player attachment, int ticks) {
        super("FarmingCompostTask", ticks);
        this.attachment = attachment;
        this.bind(attachment);
    }

    @Override
    public void execute() {
        if (attachment == null || !attachment.isRegistered()) {
            this.stop();
            return;
        }
        if (!attachment.inventory().contains(BUCKET)) {
            DialogueManager.sendStatement(attachment,"You have run out of buckets to fill.");
            this.stop();
            return;
        }
        attachment.animate(2283);
        attachment.inventory().remove(new Item(BUCKET),true);
        attachment.inventory().add(new Item(Constants.COMPOST),true);
    }

    @Override
    public void stop() {
        super.stop();
        if (attachment == null || !attachment.isRegistered()) {
            return;
        }
        attachment.resetAnimation();
    }

}

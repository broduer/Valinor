package com.valinor.game.content.areas.home;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 30, 2021
 */

import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.MagicSpellbook;

/**
 * A dialogue made for easy access to magic books.
 */
public class MagicalAltarD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Normal Spellbook", "Ancient Spellbook.", "Lunar Spellbook.", "Cancel");
        setPhase(1);
    }

    @Override
    public void select(int option) {
        if (isPhase(1)) {
            switch (option) {
                case 1 -> MagicSpellbook.changeSpellbook(player, MagicSpellbook.NORMAL, true);
                case 2 -> MagicSpellbook.changeSpellbook(player, MagicSpellbook.ANCIENT, true);
                case 3 -> MagicSpellbook.changeSpellbook(player, MagicSpellbook.LUNAR, true);
            }
            stop();
        }
    }
}

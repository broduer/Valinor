package com.valinor.game.content.areas.wilderness.content.upgrade_station;

import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;

/**
 * @author Zerikoth
 * @Since oktober 08, 2020
 */
public class WeaponUpgraderD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Open upgrade station.", "Nevermind.");
        setPhase(0);
    }

    @Override
    protected void select(int option) {
        if(isPhase(0)) {
            if(option ==1) {
                player.getWeaponUpgrade().open();
            } else if(option == 2) {
                stop();
            }
        }
    }
}

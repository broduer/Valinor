package com.valinor.game.content.syntax.impl;

import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Utils;
import org.jetbrains.annotations.NotNull;


public class ChangePresetName implements EnterSyntax {

    private final int presetIndex;

    public ChangePresetName(final int presetIndex) {
        this.presetIndex = presetIndex;
    }

    @Override
    public void handleSyntax(Player player, @NotNull String input) {
        input = Utils.formatText(input);

        if (!Utils.isValidName(input)) {
            player.message("Invalid name for preset. Please enter characters only.");
            player.setCurrentPreset(null);
            player.getPresetManager().open();
            return;
        }

        if (player.getPresets()[presetIndex] != null) {

            player.getPresets()[presetIndex].setName(input);
            player.message("The presets name has been updated.");

            player.getPresetManager().open();
        }
    }

    @Override
    public void handleSyntax(Player player, long input) {
    }

}

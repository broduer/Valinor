package com.valinor.game.content.consumables.potions.impl;

import com.valinor.game.content.EffectTimer;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

/**
 * @author Patrick van Elderen | November, 28, 2020, 13:19
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class DivineSuperCombatPotion extends Interaction {

    @Override
    public void onLogin(Player me) {
        setTimer(me);
    }

    public static void setTimer(Player player) {
        player.putAttrib(AttributeKey.DIVINE_SUPER_COMBAT_POTION_TASK_RUNNING, true);
        TaskManager.submit(new Task("DivineSuperCombatPotionTask", 1, false) {

            @Override
            public void execute() {
                int ticks = player.<Integer>getAttribOr(AttributeKey.DIVINE_SUPER_COMBAT_POTION_TICKS, 0);
                boolean potionEffectActive = player.getAttribOr(AttributeKey.DIVINE_SUPER_COMBAT_POTION_EFFECT_ACTIVE, false);

                if (!player.isRegistered() || player.dead() || ticks == 0) {
                    stop();
                    player.clearAttrib(AttributeKey.DIVINE_SUPER_COMBAT_POTION_TASK_RUNNING);
                    return;
                }

                if (potionEffectActive) {
                    player.getPacketSender().sendEffectTimer((int) Utils.ticksToSeconds(ticks), EffectTimer.DIVINE_SUPER_COMBAT_POTION);
                    ticks--;
                    player.putAttrib(AttributeKey.DIVINE_SUPER_COMBAT_POTION_TICKS, ticks--);
                    if (ticks == 0) {
                        player.putAttrib(AttributeKey.DIVINE_SUPER_COMBAT_POTION_TASK_RUNNING, false);
                        player.putAttrib(AttributeKey.DIVINE_SUPER_COMBAT_POTION_EFFECT_ACTIVE, false);
                        player.putAttrib(AttributeKey.DIVINE_SUPER_COMBAT_POTION_TICKS, 0);
                        player.message(Color.RED.tag() + "Your divine super attack potion has expired.");
                        stop();
                    }
                }
            }
        });
    }
}

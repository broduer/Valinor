package com.valinor.game.content.areas.edgevile;

import com.valinor.game.content.areas.edgevile.dialogue.WildernessStatBoardDialogue;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.Utils;

/**
 * @author Patrick van Elderen | December, 09, 2020, 14:11
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class WildernessStatisticsBoard extends PacketInteraction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if(object.getId() == 26756) {
                int kills = player.getAttribOr(AttributeKey.PLAYER_KILLS, 0);
                int deaths = player.getAttribOr(AttributeKey.BOT_KILLS, 0);
                int targetKills = player.getAttribOr(AttributeKey.TARGET_KILLS, 0);
                player.message("You have " + targetKills + " target " + Utils.pluralOrNot("kill", kills) + ", " + kills + " player " + Utils.pluralOrNot("kill", kills) + ", and " + deaths + " " + Utils.pluralOrNot("death", deaths) + ". Your KD ratio is " + player.getKillDeathRatio() + ".");
                return true;
            }
        } else if(option == 2) {
            if(object.getId() == 26756) {
                player.getDialogueManager().start(new WildernessStatBoardDialogue());
                return true;
            }
        }
        return false;
    }
}

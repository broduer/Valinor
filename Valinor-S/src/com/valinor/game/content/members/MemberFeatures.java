package com.valinor.game.content.members;

import com.valinor.game.GameConstants;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;

import java.util.Calendar;

import static com.valinor.util.CustomItemIdentifiers.KEY_OF_DROPS;

public class MemberFeatures extends Interaction {

    /**
     * Every first day of a new month sponsor players receive a free legendary mystery box and key of drops.
     * @param player The sponsor player
     */
    public void checkForMonthlySponsorRewards(Player player) {
        if(player.getMemberRights().isZenyteMemberOrGreater(player)) {
            if (player.<Boolean>getAttribOr(AttributeKey.RECEIVED_MONTHLY_SPONSOR_REWARDS, false)) {
                return;
            }

            if (World.getWorld().getCalendar().get(Calendar.DAY_OF_MONTH) == 1) {
                player.putAttrib(AttributeKey.RECEIVED_MONTHLY_SPONSOR_REWARDS, true);
                player.message(Color.PURPLE.wrap("You have received your monthly sponsor rewards. Thank you for supporting " + GameConstants.SERVER_NAME + "!"));
            }
        }
    }

    @Override
    public void onLogin(Player player) {
        checkForMonthlySponsorRewards(player);
    }
}

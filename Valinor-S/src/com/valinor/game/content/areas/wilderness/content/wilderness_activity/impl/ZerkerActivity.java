package com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl;

import com.valinor.game.content.areas.wilderness.content.wilderness_activity.WildernessActivity;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.WildernessLocations;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;

import java.util.concurrent.TimeUnit;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 30, 2022
 */
public class ZerkerActivity extends WildernessActivity {

    private WildernessLocations wildernessLocation;

    @Override
    public String description() {
        return "Zerker Pking";
    }

    @Override
    public String location() {
        return "Edgeville";
    }

    @Override
    public String announcement() {
        return "Zerker Pking is the new wilderness activity for one hour!";
    }

    @Override
    public void onCreate() {
        wildernessLocation = WildernessLocations.EDGEVILLE;
    }

    @Override
    public void process() {
    }

    @Override
    public void onFinish() {
        wildernessLocation = null;
    }

    @Override
    public long activityTime() {
        return TimeUnit.MINUTES.toMillis(60);
    }

    @Override
    public boolean canReward(Player player) {
        int combatLevel = player.skills().combatLevel();
        int defenceLevel = player.skills().level(Skills.DEFENCE);
        boolean isZerker = defenceLevel == 45 && combatLevel >= 95;
        return wildernessLocation.isInArea(player) && isZerker;
    }
}

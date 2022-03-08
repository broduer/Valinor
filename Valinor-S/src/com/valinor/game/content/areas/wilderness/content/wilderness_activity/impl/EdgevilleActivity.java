package com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl;

import com.valinor.game.content.areas.wilderness.content.wilderness_activity.WildernessActivity;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.WildernessLocations;
import com.valinor.game.world.entity.mob.player.Player;

import java.util.concurrent.TimeUnit;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 30, 2022
 */
public class EdgevilleActivity extends WildernessActivity {

    private WildernessLocations wildernessLocation;

    @Override
    public String description() {
        return "Edgeville Pking";
    }

    @Override
    public String location() {
        return "Edgeville";
    }

    @Override
    public String announcement() {
        return "Edgeville Pking is the new wilderness activity for one hour!";
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
        return wildernessLocation.isInArea(player);
    }
}

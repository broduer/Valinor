package com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl;

import com.valinor.game.content.areas.wilderness.content.wilderness_activity.WildernessActivity;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.WildernessLocations;
import com.valinor.game.world.entity.mob.player.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 30, 2022
 */
public class WildernessHotspot extends WildernessActivity {

    private WildernessLocations wildernessLocation;

    @Override
    public String description() {
        return "Mysterious Wilderness";
    }

    @Override
    public String location() {
        return wildernessLocation.location();
    }

    @Override
    public String announcement() {
        return "<img=506>" + wildernessLocation.location() +" is the new Wilderness Hotspot for the next hour!</col>";
    }

    /**
     * This method saves all the {@link WildernessLocations} in a list.
     * After that it filters the list, so we can't get "Edgevile" as location.
     *
     * returns a location that isn't Edgevile.
     */
    @Override
    public void onCreate() {
        List<WildernessLocations> wildernessLocations = Arrays.stream(WildernessLocations.values()).filter(locations -> locations != WildernessLocations.EDGEVILLE).collect(Collectors.toList());
        Collections.shuffle(wildernessLocations);
        wildernessLocation = wildernessLocations.get(0);
        wildernessLocations.clear();
    }

    @Override
    public void process() {

    }

    /**
     * When the activity has been finished clear the location.
     */
    @Override
    public void onFinish() {
        wildernessLocation = null;
    }

    /**
     * Each and every activity lasts for 60 minutes.
     * @return The time duration in this case 1 hour.
     */
    @Override
    public long activityTime() {
        return TimeUnit.MINUTES.toMillis(60);
    }

    /**
     * This method checks if the player meets the requirements in order to be awarded with a hotspot casket.
     *
     * @param player The player
     * @return true if we can receive a reward false otherwise.
     */
    @Override
    public boolean canReward(Player player) {
        return wildernessLocation.isInArea(player);
    }
}

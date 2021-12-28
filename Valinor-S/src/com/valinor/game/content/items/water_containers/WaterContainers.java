package com.valinor.game.content.items.water_containers;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;

import java.util.ArrayList;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 17, 2021
 */
public enum WaterContainers {

    /**
     * Misc
     */
    VIAL(229, 227),
    BUCKET(1925, 1929),
    BOWL(1923, 1921),
    JUG(1935, 1937),
    KETTLE(7688, 7690),

    /**
     * Watering cans
     */
    WATERING_CAN_EMPTY(5331, 5340),
    WATERING_CAN_ONE(5333, 5340),
    WATERING_CAN_TWO(5334, 5340),
    WATERING_CAN_THREE(5335, 5340),
    WATERING_CAN_FOUR(5336, 5340),
    WATERING_CAN_FIVE(5337, 5340),
    WATERING_CAN_SIX(5338, 5340),
    WATERING_CAN_SEVEN(5331, 5340);

    public final int empty, full;
    public final String name;

    WaterContainers(int empty, int full) {
        this.empty = empty;
        this.full = full;
        this.name = World.getWorld().definitions().get(ItemDefinition.class, empty).name.toLowerCase();
    }

    public enum WaterSource {
        SINK, WATERPUMP, FOUNTAIN, WELL, TAP
    }
}

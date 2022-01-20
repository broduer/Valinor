package com.valinor.game.content.instance;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Area;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that manages all {@link InstancedArea} objects created.
 *
 * @author Jason MacKeigan
 * @date Jan 28, 2015, 1:07:55 PM
 */
public class InstancedAreaManager {

    /**
     * A single instance of this class for global usage
     */
    private static final InstancedAreaManager SINGLETON = new InstancedAreaManager();

    /**
     * A mapping of all {#InstancedArea} objects that are being operated on
     * and are active.
     */
    private final Map<Integer, InstancedArea> active = new HashMap<>();

    /**
     * A private empty {@link InstancedAreaManager} constructor exists to ensure that no other instance of this class can be created from outside this class.
     */
    private InstancedAreaManager() {
    }

    /**
     * Creates a new {@link InstancedArea} object with the given params
     * @param zLevel
     *             the zLevel of the new instance
     * @param instance
     *             the instance that will be added
     * @return
     *             null if no zLevel can be found for this area, otherwise the new
     *             {@link InstancedArea} object will be returned.
     */
    public void add(int zLevel, InstancedArea instance) {
        active.put(zLevel, instance);
    }

    public InstancedArea ofZ(int z) {
        return active.get(z);
    }

    /**
     * Determines if the {@link InstancedArea} parameter exists within
     * the mapping of active {@link InstancedArea} objects and can be
     * disposed of.
     *
     * @param area    the instanced area
     * @return        true if the area exists in the mapping with the same height level
     *                 and the same reference
     */
    public boolean disposeOf(InstancedArea area) {
        if (area == null)
            return false;
        int z = area.getzLevel();
        if (!active.containsKey(z)) {
            return false;
        }
        InstancedArea found = active.get(z);
        if (!found.equals(area)) {
            return false;
        }
        active.remove(z);
        return true;
    }

    /**
     * Creates a new {@link InstancedArea} object with the given params
     * @param player    the player for this instanced area
     * @param area    the boundary of the area
     * @return    null if no height can be found for this area, otherwise the new
     * {@link InstancedArea} object will be returned.
     */
    public InstancedArea createInstancedArea(Player player, Area area) {
        int z = anyZ();
        if (z == -1) {
            return null;
        }
        InstancedArea singleInstancedArea = new InstancedArea(player, area, z);
        active.put(z, singleInstancedArea);
        return singleInstancedArea;
    }

    public int anyZ() {
        int attempts = 0;
        while (attempts < 100) {
            int z = World.getWorld().random(1024)*4;
            if (!active.containsKey(z)) {
                return z;
            }
            attempts++;
        }
        return -1;
    }

    /**
     * Retrieves the single instance of this class
     * @return    the single instance
     */
    public static InstancedAreaManager getSingleton() {
        return SINGLETON;
    }

}

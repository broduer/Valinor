package com.valinor.game.content.instance;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;

public class InstancedArea {

    /**
     * The owner of the instance
     */
    protected Player player;

    /**
     * The boundary or location for this instanced area
     */
    protected Area area;

    /**
     * The height of this area
     */
    protected int zLevel;

    /**
     * Creates a new area with a boundary
     * @param area    the boundary or location
     * @param zLevel    the height of the area
     */
    public InstancedArea(Player player, Area area, int zLevel) {
        this.area = area;
        this.zLevel = zLevel;
        this.player = player;
    }

    /**
     * When an instanced area is disposed it should be common to clean up
     * that instanced area so that it can be re-used by others.
     * <p>
     * This function is called when the {@link InstancedAreaManager#disposeOf(InstancedArea)} function
     * is referenced.
     * </p>
     */
    public void onDispose() {

    }

    /**
     * Determines the height of this area
     * @return    the height
     */
    public int getzLevel() {
        return zLevel;
    }

    /**
     * The boundary or location of this instanced area
     * @return    the boundary
     */
    public Area getArea() {
        return area;
    }

    /**
     * The instance owner
     * @return the owner of this instance
     */
    public Player getPlayer() {
        return player;
    }

    private OnTele onTeleport;

    public void setOnTeleport(OnTele c) {
        this.onTeleport = c;
    }

    public void onTeleport(Player player, Tile tile) {
         if (onTeleport != null)
             onTeleport.accept(player, tile);
    }
}

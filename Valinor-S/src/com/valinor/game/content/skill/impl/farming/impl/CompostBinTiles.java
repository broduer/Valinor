package com.valinor.game.content.skill.impl.farming.impl;

import com.valinor.game.world.position.Tile;

import java.util.Arrays;

/**
 * The compost bin's location and information.
 */
public enum CompostBinTiles {

    NORTH_ARDOUGNE(7839, new Tile(2661, 3375)),

    FALADOR(7836, new Tile(3056, 3312)),

    CATHERBY(7837, new Tile(2804, 3464)),

    PHASMATYS(7838, new Tile(3610, 3522));

    /***
     * The bin's object ID.
     */
    private int object_id;

    /**
     * The coordinates for the object.
     */
    private Tile tile;

    CompostBinTiles(int object_id, Tile tile) {
        this.object_id = object_id;
        this.tile = tile;
    }

    /**
     * Returns the bin's object ID.
     *
     * @return the ID
     */
    public int getObjectID() {
        return object_id;
    }

    /**
     * Returns the compost bin's position on map.
     *
     * @return the tile
     */
    public Tile tile() {
        return tile;
    }

    /**
     * Returns the compost bin's impl by its position on map.
     *
     * @param tile
     * @return the impl
     */
    public static CompostBinTiles get(Tile tile) {
        return get(tile.getX(), tile.getY());
    }

    /**
     * Returns the compost bin's impl by its position on map.
     *
     * @param x
     * @param y
     * @return the impl
     */
    public static CompostBinTiles get(int x, int y) {
        return Arrays.stream(CompostBinTiles.values()).filter((bin) -> (bin.tile().getX() == x && bin
            .tile().getY() == y)).findAny().orElse(null);
    }

    /**
     * Returns the compost bin's impl by its object ID.
     *
     * @param ID
     * @return the impl
     */
    public static CompostBinTiles get(int ID) {
        return Arrays.stream(CompostBinTiles.values()).filter((bin) -> (bin.getObjectID() == ID)).findAny()
            .orElse(null);
    }
}

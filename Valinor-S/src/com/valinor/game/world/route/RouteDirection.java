package com.valinor.game.world.route;

import com.valinor.game.world.position.Tile;

public enum RouteDirection {

    /**
     * DO NOT REORDER
     */

    NORTH_WEST(-1, 1, 768,0),     //0
    NORTH(0, 1, 1024,1),          //1
    NORTH_EAST(1, 1, 1280,2),     //2
    WEST(-1, 0, 512,3),           //3
    EAST(1, 0, 1536,4),           //4
    SOUTH_WEST(-1, -1, 256,5),    //5
    SOUTH(0, -1, 0,6),            //6
    SOUTH_EAST(1, -1, 1792,7);    //7

    public final int deltaX, deltaY;
    public final int clientValue;
    public final int faceValue;

    RouteDirection(int deltaX, int deltaY, int clientValue, int faceValue) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.clientValue = clientValue;
        this.faceValue = faceValue;
    }

    public static RouteDirection get(String cardinal) {
        switch(cardinal.toUpperCase()) {
            case "N":   return RouteDirection.NORTH;
            case "NW":  return RouteDirection.NORTH_WEST;
            case "NE":  return RouteDirection.NORTH_EAST;
            case "W":   return RouteDirection.WEST;
            case "E":   return RouteDirection.EAST;
            case "SW":  return RouteDirection.SOUTH_WEST;
            case "SE":  return RouteDirection.SOUTH_EAST;
            default:    return RouteDirection.SOUTH;
        }
    }

    public static RouteDirection getFromObjectDirection(int direction) {
        switch (direction) {
            case 0:
                return SOUTH;
            case 1:
                return WEST;
            case 2:
                return NORTH;
            case 3:
                return EAST;
            default:
                return null;
        }
    }

    public static RouteDirection fromDoorDirection(int direction) {
        switch (direction) {
            case 0:
                return WEST;
            case 1:
                return NORTH;
            case 2:
                return EAST;
            case 3:
                return SOUTH;
            default:
                return null;
        }
    }

    public static RouteDirection getDirection(Tile src, Tile dest) {
        int deltaX = dest.getX() - src.getX();
        int deltaY = dest.getY() - src.getY();
        return getDirection(deltaX, deltaY);
    }

    public static RouteDirection getDirection(int deltaX, int deltaY) {
        if (deltaX != 0)//normalize
            deltaX /= Math.abs(deltaX);
        if (deltaY != 0)
            deltaY /= Math.abs(deltaY);
        for (RouteDirection d: RouteDirection.values()) {
            if (d.deltaX == deltaX && d.deltaY == deltaY)
                return d;
        }
        return null;
    }

}

package com.valinor.scene.object;

import com.valinor.entity.Renderable;

public final class Wall {

    public int plane;
    public int world_x;
    public int world_y;
    public int wall_orientation;
    public int corner_orientation;
    public Renderable wall;
    public Renderable corner;
    public long uid;
    public byte mask;

}

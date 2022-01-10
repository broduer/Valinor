package com.valinor.game.world.entity.combat.ranged;

public enum ThrownaxeDrawback {

    /**
     * Throwing axes
     */
    BRONZE_THROWING_AXE(800, 43, 36),
    IRON_THROWING_AXE(801, 42, 35),
    STEEL_THROWING_AXE(802, 44, 37),
    MITHRIL_THROWING_AXE(803, 45, 38),
    ADAMANT_THROWING_AXE(804, 46, 39),
    RUNE_THROWING_AXE(805, 48, 41),
    DRAGON_THROWING_AXE(20849, 1320, 1319),
    MORRIGANS_THROWING_AXE(22634, 1624, 1623),

    /**
     * Thrown (misc)
     */
    OBBY_RING(6522, -1, 442),
    MORRIGANS_JAVELIN(22636, 1619, 1620)
    ;

    public int arrow, gfx, projectile;

    ThrownaxeDrawback(int arrow, int gfx, int projectile) {
        this.arrow = arrow;
        this.gfx = gfx;
        this.projectile = projectile;
    }

    public static ThrownaxeDrawback find(int ammo) {
        for (ThrownaxeDrawback thrownaxeDrawback : ThrownaxeDrawback.values()) {
            if(thrownaxeDrawback.arrow == ammo) {
                return thrownaxeDrawback;
            }
        }
        return null;
    }
}

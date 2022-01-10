package com.valinor.game.world.entity.combat.ranged;

public enum DartDrawback {

    BRONZE_DART(806, 232, 226),
    IRON_DART(807, 233, 227),
    STEEL_DART(808, 234, 228),
    BLACK_DART(3093, 273, 34),
    MITHRIL_DART(809, 235, 229),
    ADAMANT_DART(810, 236, 230),
    RUNITE_DART(811, 237, 231),
    AMETHYST_DART(25849, 1936, 1937),
    //p
    BRONZE_DART_P1(812, 232, 226),
    IRON_DART_P1(813, 233, 227),
    STEEL_DART_P1(814, 234, 228),
    BLACK_DART_P1(3094, 273, 34),
    MITHRIL_DART_P1(815, 235, 229),
    ADAMANT_DART_P1(816, 236, 230),
    RUNITE_DART_P1(817, 237, 231),
    AMETHYST_DART_P1(25851, 1936, 1937),
    //p+
    BRONZE_DART_P2(5628, 232, 226),
    IRON_DART_P2(5629, 233, 227),
    STEEL_DART_P2(5630, 234, 228),
    BLACK_DART_P2(5631, 273, 34),
    MITHRIL_DART_P2(5632, 235, 229),
    ADAMANT_DART_P2(5633, 236, 230),
    RUNITE_DART_P2(5634, 237, 231),
    AMETHYST_DART_P2(25855, 1936, 1937),
    //p++
    BRONZE_DART_P3(5635, 232, 226),
    IRON_DART_P3(5636, 233, 227),
    STEEL_DART_P3(5637, 234, 228),
    BLACK_DART_P3(5638, 273, 34),
    MITHRIL_DART_P3(5639, 235, 229),
    ADAMANT_DART_P3(5640, 236, 230),
    RUNITE_DART_P3(5641, 237, 231),
    AMETHYST_DART_P3(25857, 1936, 1937),

    //dragon
    DRAGON_DART(11230, 1123, 1122),
    DRAGON_DART_P1(11231, 1123, 1122),
    DRAGON_DART_P2(11233, 1123, 1122),
    DRAGON_DART_P3(11234, 1123, 1122);

    public int dart, gfx, projectile;

    DartDrawback(int dart, int gfx, int projectile) {
        this.dart = dart;
        this.gfx = gfx;
        this.projectile = projectile;
    }

    public static DartDrawback find(int ammo) {
        for (DartDrawback dartDrawback : DartDrawback.values()) {
            if(dartDrawback.dart == ammo) {
                return dartDrawback;
            }
        }
        return null;
    }
}

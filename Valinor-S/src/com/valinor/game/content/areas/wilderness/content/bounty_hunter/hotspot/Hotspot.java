package com.valinor.game.content.areas.wilderness.content.bounty_hunter.hotspot;

import com.valinor.game.world.position.Area;

import java.util.Random;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public class Hotspot {

    public static final Hotspot[] HOTSPOTS = {
        new Hotspot("Edgeville", new Area(2993, 3523, 3124, 3597, 0)),
        new Hotspot("Mage Bank", new Area(3070, 3905, 3132, 3974, 0)),
        new Hotspot("Deserted Keep", new Area(3144, 3911, 3177, 3968, 0)),
        new Hotspot("East Dragons", new Area(3316, 3625, 3415, 3735, 0)),
        new Hotspot("West Dragons", new Area(2949, 3567, 3015, 3653, 0)),
        new Hotspot("Level 44 Obelisk", new Area(2966, 3834, 3024, 3888, 0)),
        new Hotspot("Level 50 Obelisk", new Area(3282, 3904, 3329, 3952, 0)),
        new Hotspot("Graveyard", new Area(3132, 3644, 3195, 3696, 0)),
        new Hotspot("Demonic Ruins", new Area(3261, 3856, 3346, 3903, 0)),
    };

    public static Hotspot ACTIVE = randomHotspot();

    public static Hotspot randomHotspot() {
        Random generator = new Random();
        int randomIndex = generator.nextInt(HOTSPOTS.length);
        return HOTSPOTS[randomIndex];
    }

    /**
     * Separator
     */

    public final String name;

    public final Area area;

    public Hotspot(String name, Area area) {
        this.name = name;
        this.area = area;
    }
}

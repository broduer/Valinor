package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.widget.Widget;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 19, 2021
 */
public class SkotizoWidget extends Widget {

    public static void unpack() {
        Widget tab = addInterface(29230);
        tab.totalChildren(5);
        addSprite(29231, 1932);
        tab.child(0, 29231, 36, 141);
        addSprites(29232, 1936, 1933);
        tab.child(1, 29232, 56, 128);
        addSprites(29233, 1936, 1933);
        tab.child(2, 29233, 56, 193);
        addSprites(29234, 1936, 1933);
        tab.child(3, 29234, 23, 160);
        addSprites(29235, 1936, 1933);
        tab.child(4, 29235, 88, 160);
    }
}

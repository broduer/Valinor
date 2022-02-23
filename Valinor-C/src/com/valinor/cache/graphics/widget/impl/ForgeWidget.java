package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 23, 2022
 */
public class ForgeWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        Widget mode = addInterface(69000);
        addSprite(69001, 1855);
        closeButton(69002, 107,108,false);
        addText(69003, "<col=09FF00>Item forging", font, 2, 0xff9933, true, true);
        addText(69005, "Items required:", font, 2, 0xff9933, true, true);

        configHoverButton(69006, "Upgrade", 1856, 1857, 1857, 1857, false, 69006);
        addText(69007, "Forge", font, 2, 0xff9933, true, true);

        addText(69008, "Success rate: 45%", font, 2, 0xff9933, true, true);

        itemGroup(69009, 1, 1, 5, 3);

        configHoverButton(69010, "View weaponry upgrades",1858, 1859, 1859, 1859, false, 69011, 69012);
        configHoverButton(69011, "View armor upgrades", 1858, 1859, 1859, 1859, false, 69010, 69012);
        configHoverButton(69012, "View pet upgrades",1858, 1859, 1859, 1859, false, 69010, 69011);

        addText(69013, "Weaponry", font, 0, 0xff9933, true, true);
        addText(69014, "Armour", font, 0, 0xff9933, true, true);
        addText(69015, "Misc", font, 0, 0xff9933, true, true);
        addContainer(69016, TYPE_CONTAINER, 5, 2, 5, 5, 0, false, true, true);

        mode.totalChildren(16);
        mode.child(0, 69001, 60, 47);
        mode.child(1, 69002, 446, 54);

        mode.child(2, 69003, 285, 56);
        mode.child(3, 69005, 330, 88);

        mode.child(4, 69006, 313, 255);
        mode.child(5, 69007, 370, 262);

        mode.child(6, 69008, 340, 189);

        mode.child(7, 69009, 358, 212);

        mode.child(8, 69010, 69, 85);
        mode.child(9, 69011, 133, 85);
        mode.child(10, 69012, 196, 85);

        mode.child(11, 69013, 100, 91);
        mode.child(12, 69014, 163, 91);
        mode.child(13, 69015, 227, 91);
        mode.child(14, 69016, 280, 110);
        mode.child(15, 69020, 70, 110);

        Widget nameScroll = addTabInterface(69020);
        int totalItems = 50;
        nameScroll.width = 180;
        nameScroll.height = 175;
        nameScroll.scrollMax = nameScroll.height + 1;

        nameScroll.totalChildren(totalItems);
        int y = 0;
        for(int index = 0; index < totalItems; index++) {
            addClickableText(69021 + index, "Abyssal whip", "Select", font, 0, 0xff9933, false, true, 165);
            nameScroll.child(index, 69021 + index, 2, y + 6);
            textClicked(69021 + index, 1137, 1, 2);
            y += 14;
        }
        nameScroll.scrollMax = y;
    }
}

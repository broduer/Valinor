package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 23, 2022
 */
public class AccountSelectionWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        Widget widget = addTabInterface(22800);
        addSprite(22801, 1367);
        hoverButton(22802, "Confirm and Continue", 1767, 1768);
        addText(22803, "Account selection", font, 2, 0xF7AA25, true, true);
        addText(22804, "Perks:", font, 2, 0xF7AA25, true, true);
        addText(22805, "Lorem ipsum dolor sit amet, consectetur<br>adipiscing elit, sed do eiusmod tempor<br>incididunt ut labore et dolore magna aliqua.", font, 1, 0xF7AA25, true, true);
        addText(22806, "Description:", font, 2, 0xF7AA25, true, true);
        addText(22807, "Lorem ipsum dolor sit amet, consectetur<br>adipiscing elit, sed do eiusmod tempor<br>incididunt ut labore et dolore magna aliqua.", font, 2, 0xF7AA25, true, true);
        addText(22808, "<u=ffff00>Game modes:", font, 2, 0xFD851A, false, true);

        Widget scrollInterface = addTabInterface(22810);
        scrollInterface.scrollPosition = 0;
        scrollInterface.contentType = 0;
        scrollInterface.width = 138;
        scrollInterface.height = 255;
        scrollInterface.scrollMax = 250;
        int x = 5, y = 5;
        int amountOfLines = 6;
        scrollInterface.totalChildren(amountOfLines);
        for (int index = 0; index < amountOfLines; index++) {
            addText(22811 + index, "Instant pker", font, 1, 0xF7AA25, false, false, 0xffffff, "Select", 150);
            textClicked(22811 + index, 1137, 1, 0);
            scrollInterface.child(index, 22811 + index, x, y);
            y += 18;
        }

        addClickableSprites(22820, "Toggle", 490, 491, 547);
        addClickableSprites(22821, "Toggle", 490, 491, 547);
        addClickableSprites(22822, "Toggle", 490, 491, 547);
        addText(22823, "Rookie", font, 0, 0xFD851A, false, true);
        addText(22824, "Challenger", font, 0, 0xFD851A, false, true);
        addText(22825, "Gladiator", font, 0, 0xFD851A, false, true);
        addText(22826, "<u=ffff00>Exp modes:", font, 2, 0xFD851A, false, true);

        widget.totalChildren(16);
        widget.child(0, 22801, 26, 15);
        widget.child(1, 22802, 460, 20);
        widget.child(2, 22803, 271, 24);
        widget.child(3, 22804, 175, 52);
        widget.child(4, 22805, 315, 85);
        widget.child(5, 22806, 195, 200);
        widget.child(6, 22807, 315, 231);
        widget.child(7, 22810, 27, 67);
        widget.child(8, 22820, 35, 225);
        widget.child(9, 22821, 35, 245);
        widget.child(10, 22822, 35, 265);
        widget.child(11, 22823, 55, 228);
        widget.child(12, 22824, 55, 248);
        widget.child(13, 22825, 55, 268);
        widget.child(14, 22826, 35, 205);
        widget.child(15, 22808, 35, 50);
    }
}

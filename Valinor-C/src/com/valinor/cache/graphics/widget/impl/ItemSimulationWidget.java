package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

/**
 * @author Patrick van Elderen | February, 14, 2021, 23:05
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class ItemSimulationWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        final Widget widget = addTabInterface(27200);
        Widget scroll = addInterface(27300);
        setChildren(101, scroll);

        addContainer(27201, 8, 10, 20, 17, false);
        setBounds(27201, 10, 5, 0, scroll);
        scroll.height = 276;
        scroll.width = 425;
        scroll.scrollMax = 500;

        for (int index = 0; index < 100; index++) {
            addText(27316 + index, "", font, 0, 0xffffff, true, true);
            int x = index % 8;
            int y = index / 8;

            x = (36 + 17) * x;
            y = (32 + 21) * y + 39;

            setBounds(27316 + index, x + 22, y, 1 + index, scroll);
        }

        addText(27202, "Drop Simulator", 0xFF981F, true, true, 52, font, 2);
        addText(27203, "Showing ...", 0xFF981F, true, true, 52, font, 1);

        addSpriteLoader(27204, 538);

        closeButton(27205, 142, 143, false);

        hoverButton(27206, "Clear", 1776, 1777);
        addText(27207, "Clear", font, 2, 0xB27300, false, true);

        hoverButton(27208, "Exchange", 1776, 1777);
        addText(27209, "Exchange", font, 2, 0xB27300, false, true);

        setChildren(9, widget);
        setBounds(27204, 25, 2, 0, widget);
        setBounds(27300, 38, 48, 1, widget);
        setBounds(27202, 259, 13, 2, widget);
        setBounds(27203, 259, 26, 3, widget);
        setBounds(27205, 455, 14, 4, widget);
        setBounds(27206, 35, 10, 5, widget);
        setBounds(27207, 53, 16, 6, widget);
        setBounds(27208, 110, 10, 7, widget);
        setBounds(27209, 114, 16, 8, widget);
    }
}

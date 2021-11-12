package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

/**
 * The shop interface widget, moved from Widget class to its new structure.
 * This shop widget can display text, and has slightly different positioning.
 * @author Professor Oak
 */
public class ShopWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        shop_with_close_button(font);
    }

    private static void shop_with_close_button(AdvancedFont[] font) {
        Widget widget = cache[3824];
        int[] childrenId = new int[widget.children.length + 2];
        int[] childrenX = new int[widget.children.length + 2];
        int[] childrenY = new int[widget.children.length + 2];
        for (int i = 0; i < widget.children.length; i++) {
            childrenId[i] = widget.children[i];
            childrenX[i] = widget.child_x[i];
            childrenY[i] = widget.child_y[i];
        }
        closeButton(28056, 107, 108, true);
        addText(3902, "", 16750623, false, true, 52, font, 1);
        setChildren(93, widget);
        for (int i = 0; i < widget.children.length; i++) {
            setBounds(childrenId[i], childrenX[i], childrenY[i], i, widget);
        }
        setBounds(28056, 472, 27, 92, widget);
    }
}

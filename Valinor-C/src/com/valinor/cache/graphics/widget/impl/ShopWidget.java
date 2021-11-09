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
        shop_with_scrollbar(font);
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

    private static void shop_with_scrollbar(AdvancedFont[] font) {
        //Set up the shop inventory
        Widget shopInventory = cache[3900];
        //1000 items is way too much, changed to 100
        shopInventory.inventoryItemId = new int[100];
        shopInventory.inventoryAmounts = new int[100];
        shopInventory.itemOpacity = new int[100];
        shopInventory.drawInfinity = true;
        shopInventory.x = 25;
        shopInventory.width = 8;
        shopInventory.height = 200;
        shopInventory.inventoryMarginX = 10;
        shopInventory.inventoryMarginY = 18;

        //The scroll, add the shop inventory to it.
        Widget scroll = addTabInterface(32995);
        scroll.totalChildren(101);
        setBounds(3900, 0, 0, 0, scroll);
        scroll.height = 230;
        scroll.width = 445;
        scroll.scrollMax = 230;


        //Comment out the default text, in case we ever need to hide it in the future
        //interfaceCache[3903].defaultText = "";

        for (int index = 0; index < 100; index++) {
            addText(32996 + index, "", font, 0, 0xffffff, true, true);
            int x = index % 8;
            int y = index / 8;

            x = (32 + 19) * x + 15;
            y = (32 + 25) * y + 37;

            scroll.child(1 + index, 32996 + index, x + 22, y);
        }


        //Position the item container in the actual shop interface
        setBounds(32995, 33, 60, 75, cache[3824]);
    }
}

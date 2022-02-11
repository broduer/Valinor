package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public class BountyHunterWidget extends Widget {

    private final static int WILDERNESS_SKULL_ICON = 197;
    public static final int EXPAND_WIDGET = 25300;
    public static final int BOUNTY_HUNTER_WIDGET = 25000;
    public static final int BOUNTY_HUNTER_WIDGET_SUB_COMPONENT = 25100;

    public static void unpack(AdvancedFont[] font) {
        widget_with_task_button(font);
        target_with_task_info(font);
        expand();
    }

    private static void expand() {
        Widget widget = addTabInterface(EXPAND_WIDGET);

        addHoverButton(EXPAND_WIDGET + 1, 1033, 20, 22, "Expand", -1, EXPAND_WIDGET + 2, OPTION_OK);
        addHoveredButton(EXPAND_WIDGET + 2, 1034, 20, 22, EXPAND_WIDGET + 3);

        widget.totalChildren(3);
        widget.child(0,EXPAND_WIDGET + 1,487,55);
        widget.child(1,EXPAND_WIDGET + 2,487,55);
        //Skull icon
        //Be careful with positioning the skull icon especially the y position,
        //we have hardcoded checks for position for this interface and
        //moving the skull icon breaks the checks
        widget.child(2, WILDERNESS_SKULL_ICON, -30, 2);
    }

    private static void target_with_task_info(AdvancedFont[] font) {
        Widget widget = addTabInterface(BOUNTY_HUNTER_WIDGET_SUB_COMPONENT);

        addText(BOUNTY_HUNTER_WIDGET_SUB_COMPONENT + 1, "Bounty Task:", font, 0, 0xa0a0a0);
        addText(BOUNTY_HUNTER_WIDGET_SUB_COMPONENT + 2, "17:49", font, 0, 0xff981f);
        addButton(BOUNTY_HUNTER_WIDGET_SUB_COMPONENT + 3, 938, "Configure");
        addButton(BOUNTY_HUNTER_WIDGET_SUB_COMPONENT + 4, 939, "Info");
        widget.totalChildren(16);
        widget.child(0, Widget.cache[BOUNTY_HUNTER_WIDGET].children[0], 319, 43);
        widget.child(1, Widget.cache[BOUNTY_HUNTER_WIDGET].children[1], 323, 47);
        widget.child(2, Widget.cache[BOUNTY_HUNTER_WIDGET].children[2], 323, 47);
        widget.child(3, Widget.cache[BOUNTY_HUNTER_WIDGET].children[3], 322, 58);
        widget.child(4, Widget.cache[BOUNTY_HUNTER_WIDGET].children[4], 337, 58);
        widget.child(5, Widget.cache[BOUNTY_HUNTER_WIDGET].children[5], 322, 72);
        widget.child(6, Widget.cache[BOUNTY_HUNTER_WIDGET].children[6], 340, 64);
        widget.child(7, Widget.cache[BOUNTY_HUNTER_WIDGET].children[9], 373, 44);
        widget.child(8, Widget.cache[BOUNTY_HUNTER_WIDGET].children[10], 390, 45);

        //Skull icon
        //Be careful with positioning the skull icon especially the y position,
        //we have hardcoded checks for position for this interface and
        //moving the skull icon breaks the checks
        widget.child(9, WILDERNESS_SKULL_ICON, -30, 2);
        widget.child(10, BOUNTY_HUNTER_WIDGET_SUB_COMPONENT + 1, 425, 58);
        widget.child(11, BOUNTY_HUNTER_WIDGET_SUB_COMPONENT + 2, 445, 72);
        widget.child(12, BOUNTY_HUNTER_WIDGET_SUB_COMPONENT + 3, 485, 71);
        widget.child(13, BOUNTY_HUNTER_WIDGET_SUB_COMPONENT + 4, 410, 73);
        widget.child(14, Widget.cache[BOUNTY_HUNTER_WIDGET].children[11], 486, 41);
        widget.child(15, Widget.cache[BOUNTY_HUNTER_WIDGET].children[12], 486, 41);
    }

    private static void widget_with_task_button(AdvancedFont[] font) {
        Widget widget = addTabInterface(BOUNTY_HUNTER_WIDGET);

        addTransparentSprite(BOUNTY_HUNTER_WIDGET + 1, 933, 150);
        addHoverButton(BOUNTY_HUNTER_WIDGET + 2, 940, 12, 12, "Minimize", -1, BOUNTY_HUNTER_WIDGET + 3, 1);
        addHoveredButton(BOUNTY_HUNTER_WIDGET + 3, 941, 12, 12, BOUNTY_HUNTER_WIDGET + 4);
        addSprite(BOUNTY_HUNTER_WIDGET + 5, 936);
        addText(BOUNTY_HUNTER_WIDGET + 6, "1-4", font, 0, 0xffffff);
        addButton(BOUNTY_HUNTER_WIDGET + 7, 937, "Hotspot Info");
        addConfigSprite(BOUNTY_HUNTER_WIDGET + 8, 935, 934, 1, 855);
        addHoverButton(BOUNTY_HUNTER_WIDGET + 9, 943, 100, 28, "Bounty Tasks", -1, BOUNTY_HUNTER_WIDGET + 10, OPTION_OK);
        addHoveredButton(BOUNTY_HUNTER_WIDGET + 10, 944, 100, 28, BOUNTY_HUNTER_WIDGET + 11);
        addText(BOUNTY_HUNTER_WIDGET + 12, "<img=506>Extreme (122)", font, 0, 0xff981f);
        addHoverButton(BOUNTY_HUNTER_WIDGET + 13, 945, 16, 15, "Skip target", -1, BOUNTY_HUNTER_WIDGET + 14, OPTION_OK);
        addHoveredButton(BOUNTY_HUNTER_WIDGET + 14, 946, 16, 15, BOUNTY_HUNTER_WIDGET + 15);
        widget.totalChildren(13);
        widget.child(0, BOUNTY_HUNTER_WIDGET + 1, 319, 43);
        widget.child(1, BOUNTY_HUNTER_WIDGET + 2, 323, 47);
        widget.child(2, BOUNTY_HUNTER_WIDGET + 3, 323, 47);
        widget.child(3, BOUNTY_HUNTER_WIDGET + 5, 322, 58);
        widget.child(4, BOUNTY_HUNTER_WIDGET + 6, 337, 58);
        widget.child(5, BOUNTY_HUNTER_WIDGET + 7, 322, 72);
        widget.child(6, BOUNTY_HUNTER_WIDGET + 8, 340, 64);
        widget.child(7, BOUNTY_HUNTER_WIDGET + 9, 404, 57);
        widget.child(8, BOUNTY_HUNTER_WIDGET + 10, 404, 57);
        widget.child(9, BOUNTY_HUNTER_WIDGET + 12, 380, 45);
        //Skull icon
        //Be careful with positioning the skull icon especially the y position,
        //we have hardcoded checks for position for this interface and
        //moving the skull icon breaks the checks
        widget.child(10, WILDERNESS_SKULL_ICON, -30, 2);
        widget.child(11, BOUNTY_HUNTER_WIDGET + 13, 486, 41);
        widget.child(12, BOUNTY_HUNTER_WIDGET + 14, 486, 41);
    }
}

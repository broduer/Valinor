package com.valinor.cache.graphics.widget.impl;

import com.valinor.ClientConstants;
import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

/**
 * @author Zerikoth
 * @Since October 12, 2020
 */
public class QuestTabSidebarWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        questTab(font);
        panelTab(font);
    }

    private static void questTab(AdvancedFont[] font) {
        Widget widget = Widget.addInterface(53400);

        addSprite(53401, 1883);
        addSprite(53402, 1885);
        addSprite(53403, 1884);
        addText(53404, "Information", font, 2, ClientConstants.ORANGE, true);
        hoverButton(53405, "Information", 1882, 1887);
        hoverButton(53406, "Panel", 1882, 1887);
        addSprite(53407, 1881);
        addSprite(53408, 1879);

        Widget scrollTab1 = Widget.addInterface(53415);
        int scrollChildren = 50;
        scrollTab1.totalChildren(scrollChildren);
        int y = 5;
        int interfaceId = 53416;
        for (int index = 0; index < scrollChildren; index++) {
            addText(interfaceId, "", font, 0, 0xffb000, false, true);
            scrollTab1.child(index, interfaceId, 0, y);
            interfaceId++;
            y += 13;
        }
        scrollTab1.width = 148;
        scrollTab1.height = 170;
        scrollTab1.scrollMax = 550;

        widget.totalChildren(9);
        widget.child(0, 53401, 4, 40);
        widget.child(1, 53402, 11, 75);
        widget.child(2, 53403, 11, 47);
        widget.child(3, 53404, 95, 50);
        widget.child(4, 53405, 4, 6);
        widget.child(5, 53406, 49, 6);
        widget.child(6, 53407, 16, 14);
        widget.child(7, 53408, 63, 14);
        widget.child(8, 53415, 14, 76);
    }

    private static void panelTab(AdvancedFont[] font) {
        Widget widget = Widget.addInterface(53500);

        addSprite(53501, 1883);
        addSprite(53502, 1885);
        addSprite(53503, 1884);
        addText(53504, "Panel", font, 2, ClientConstants.ORANGE, true);
        hoverButton(53505, "Information", 1882, 1887);
        hoverButton(53506, "Panel", 1882, 1887);
        addSprite(53507, 1881);
        addSprite(53508, 1879);

        // Scroll content
        Widget scrollTab1 = Widget.addInterface(53515);
        int interfaceId = 53516;

        String[] buttonNames = {"Achievements", "Titles", "Collection log", "Npc drops", "Presets", "Daily tasks", "Guide"};
        int[][] iconData = {
            { 1894, 12, 6 }, // Achievements
            { 1899, 12, 6 }, // Titles
            { 1398, 13, 9 }, // Profile
            { 1897, 11, 5 }, // Npc drops
            { 1898, 14, 9 }, // Presets
            { 1895, 10, 7 }, // Pets
            { 1900, 12, 6 }, // Guide
        };
        // @formatter:on
        int scrollChildren = buttonNames.length;
        scrollTab1.totalChildren(scrollChildren * 4);
        int y = 1;
        int panelYIncrease = 28;
        int scrollChild = 0;
        for (int index = 0; index < scrollChildren; index++) {
            // First panel button
            Widget.addHoverButtonLatest(interfaceId, interfaceId + 1, interfaceId + 2, 1888, 1889, 167, 29, "Open");
            scrollTab1.child(scrollChild, interfaceId, 0, y);
            scrollChild++;
            scrollTab1.child(scrollChild, interfaceId + 1, 0, y);
            scrollChild++;
            interfaceId += 3;

            // First panel icon
            Widget.addSprite(interfaceId, iconData[index][0]);
            scrollTab1.child(scrollChild, interfaceId, iconData[index][1], y + iconData[index][2]);
            scrollChild++;
            interfaceId++;

            // First panel text
            Widget.addText(interfaceId, buttonNames[index], font, 2,
                ClientConstants.ORANGE, true);
            scrollTab1.child(scrollChild, interfaceId, 92, y + 7);
            scrollChild++;
            interfaceId++;

            y += panelYIncrease;
        }
        scrollTab1.width = 151;
        scrollTab1.height = 173;
        scrollTab1.scrollMax = (scrollChildren * panelYIncrease) + 2;

        widget.totalChildren(9);
        widget.child(0, 53501, 4, 40);
        widget.child(1, 53502, 11, 75);
        widget.child(2, 53503, 11, 47);
        widget.child(3, 53504, 95, 50);
        widget.child(4, 53505, 4, 6);
        widget.child(5, 53506, 49, 6);
        widget.child(6, 53507, 16, 14);
        widget.child(7, 53508, 63, 14);
        widget.child(8, 53515, 11, 74);
    }

}

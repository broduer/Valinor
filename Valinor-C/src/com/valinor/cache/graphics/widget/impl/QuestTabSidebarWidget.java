package com.valinor.cache.graphics.widget.impl;

import com.valinor.Client;
import com.valinor.ClientConstants;
import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

/**
 * @author Zerikoth
 * @Since oktober 12, 2020
 */
public class QuestTabSidebarWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        questTabInformationTab(font);
        questTabPanelTab(font);
        questTabActivitiesTab(font);
    }

    private static void questTabInformationTab(AdvancedFont[] font) {
        int interfaceId = 72980;
        int child = 0;
        Widget interfaces = Widget.addInterface(interfaceId);
        interfaceId++;
        Widget.setChildren(17, interfaces);
        int xOffset = 0;
        int yOffset = 1;

        // Background
        Widget.addSpriteComplete(interfaceId, child, 1883, 4 + xOffset, 39 + yOffset, interfaces,
            true);
        interfaceId++;
        child++;

        // Mini background
        Widget.addSpriteComplete(interfaceId, child, 1885, 11 + xOffset, 74 + yOffset, interfaces,
            true);
        interfaceId++;
        child++;

        // Information text background
        Widget.addSpriteComplete(interfaceId, child, 1884, 11 + xOffset, 46 + yOffset, interfaces,
            true);
        interfaceId++;
        child++;

        // Information text
        Widget.addText(interfaceId, "Information", font, 2, ClientConstants.ORANGE,
            true);
        Widget.setBounds(interfaceId, 95 + xOffset, 49 + yOffset, child, interfaces);
        interfaceId++;
        child++;

        int buttonSpacing = 0;
        int buttonSpacingIncrease = 45;
        // Information button
        Widget.addHoverButtonComplete(interfaceId, child, 4 + xOffset, 5 + yOffset, 1882, 1887, 46,
            35, "Information", interfaces, true);
        Widget.setSpriteClicked(interfaceId, 1886);
        Client.setInterfaceClicked(72980, interfaceId, true, true); // Set information
        // tab to
        // default
        // clicked.
        interfaceId += 3;
        child += 2;
        buttonSpacing += buttonSpacingIncrease;

        // Information button icon
        Widget.addSpriteComplete(interfaceId, child, 1881, 16 + xOffset, 13 + yOffset, interfaces,
            true);
        interfaceId++;
        child++;

        // Panel button
        Widget.addHoverButtonComplete(interfaceId, child, 4 + xOffset + buttonSpacing,
            5 + yOffset, 1882, 1887, 46, 35, "Panel", interfaces, true);
        Widget.setSpriteClicked(interfaceId, 1886);
        interfaceId += 3;
        child += 2;
        buttonSpacing += buttonSpacingIncrease;

        // Panel button icon
        Widget.addSpriteComplete(interfaceId, child, 1879, 63 + xOffset, 13 + yOffset, interfaces,
            true);
        interfaceId++;
        child++;

        // Activity button
        Widget.addHoverButtonComplete(interfaceId, child, 4 + xOffset + buttonSpacing,
            5 + yOffset, 1882, 1887, 46, 35, "Activities", interfaces, true);
        Widget.setSpriteClicked(interfaceId, 1886);
        interfaceId += 3;
        child += 2;
        buttonSpacing += buttonSpacingIncrease;

        // Activity button icon
        Widget.addSpriteComplete(interfaceId, child, 1880, 107 + xOffset, 12 + yOffset,
            interfaces, true);
        interfaceId++;
        child++;

        // Scroll content
        Widget scrollTab1 = Widget.addInterface(interfaceId);
        interfaces.child(child, interfaceId, 14, 76); // Scroll interface.
        interfaceId++;
        child++;
        int scrollChildren = 35;
        scrollTab1.totalChildren(scrollChildren);
        int y = 5;
        for (int index = 0; index < scrollChildren; index++) {
            if (index == 0 || index == 4 || index == 9) {
                Widget.addText(interfaceId, "", font, 0, 0xffb000, false, true);
            } else {
                if (index <= 9) {
                    Widget.addText(interfaceId, "", font, 0, 0xffb000, false, true);
                } else {
                    Widget.addText(interfaceId, "", font, 0, 0xffb000, false, true,
                        0xffffff, "Quick chat", 226);
                }
            }
            scrollTab1.child(index, interfaceId, 0, y);
            interfaceId++;
            y += 13;
        }
        scrollTab1.width = 148;
        scrollTab1.height = 170;
        scrollTab1.scrollMax = 380;
    }

    private static void questTabPanelTab(AdvancedFont[] font) {
        int interfaceId = 72980;
        int child = 0;
        Widget interfaces = Widget.addInterface(73089); // Must
        // change
        // id
        // to
        // a
        // different
        // one
        // for
        // duplicate
        // interfaces
        interfaceId++;
        Widget.setChildren(17, interfaces);
        int xOffset = 0;
        int yOffset = 1;

        // Background
        Widget.addSpriteComplete(interfaceId, child, 1883, 4 + xOffset, 39 + yOffset, interfaces,
            false);
        interfaceId++;
        child++;

        // Mini background
        Widget.addSpriteComplete(interfaceId, child, 1885, 11 + xOffset, 74 + yOffset, interfaces,
            false);
        interfaceId++;
        child++;

        // Information text background
        Widget.addSpriteComplete(interfaceId, child, 1884, 11 + xOffset, 46 + yOffset, interfaces,
            false);
        interfaceId++;
        child++;

        // Information text
        Widget.addText(interfaceId, "Information", font, 2, ClientConstants.ORANGE,
            true);
        Widget.setBounds(interfaceId, 95 + xOffset, 49 + yOffset, child, interfaces);
        interfaceId++;
        child++;

        int buttonSpacing = 0;
        int buttonSpacingIncrease = 45;
        // Information button
        Widget.addHoverButtonComplete(interfaceId, child, 4 + xOffset, 5 + yOffset, 1882, 1887, 46,
            35, "Information", interfaces, false);
        interfaceId += 3;
        child += 2;
        buttonSpacing += buttonSpacingIncrease;

        // Information button icon
        Widget.addSpriteComplete(interfaceId, child, 1881, 16 + xOffset, 13 + yOffset, interfaces,
            false);
        interfaceId++;
        child++;

        // Panel button
        Widget.addHoverButtonComplete(interfaceId, child, 4 + xOffset + buttonSpacing,
            5 + yOffset, 1882, 1887, 46, 35, "Panel", interfaces, false);
        interfaceId += 3;
        child += 2;
        buttonSpacing += buttonSpacingIncrease;

        // Panel button icon
        Widget.addSpriteComplete(interfaceId, child, 1879, 63 + xOffset, 13 + yOffset, interfaces,
            false);
        interfaceId++;
        child++;

        // Activity button
        Widget.addHoverButtonComplete(interfaceId, child, 4 + xOffset + buttonSpacing,
            5 + yOffset, 1882, 1887, 46, 35, "Activities", interfaces, false);
        interfaceId += 3;
        child += 2;
        buttonSpacing += buttonSpacingIncrease;

        // Activity button icon
        Widget.addSpriteComplete(interfaceId, child, 1880, 107 + xOffset, 12 + yOffset,
            interfaces, false);
        interfaceId++;
        child++;

        // Start of duplicate interface changes.
        interfaceId += 110;

        // Scroll content
        Widget scrollTab1 = Widget.addInterface(interfaceId);
        interfaces.child(child, interfaceId, 11, 74); // Scroll interface.
        interfaceId++;
        child++;

        String[] buttonNames =
            {"Achievements", "Titles", "Players", "Npc drops", "Presets", "Daily tasks", "Guide"};
        int[][] iconData = {
            // @formatter:off
            {
                // Achievements
                1894, 12, 6
            }, {
            // Titles
            1899, 12, 6 },
            {
                // Profile
                1896, 13, 9 },
            {
                // Npc drops
                1897, 11, 5 },
            {
                // Presets
                1898, 14, 9 },
            {
                // Pets
                1895, 10, 7 },
            {
                // Guide
                1900, 12, 6 }, };
        // @formatter:on
        int scrollChildren = buttonNames.length;
        scrollTab1.totalChildren(scrollChildren * 4);
        int y = 1;
        int panelYIncrease = 28;
        int scrollChild = 0;
        for (int index = 0; index < scrollChildren; index++) {
            // First panel button
            Widget.addHoverButtonLatest(interfaceId, interfaceId + 1, interfaceId + 2, 1888, 1889,
                167, 29, "Open");
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

    }

    private static void questTabActivitiesTab(AdvancedFont[] fon) {
        int interfaceId = 72980;
        int child = 0;
        Widget interfaces = Widget.addInterface(73180); // Must
        // change
        // id
        // to
        // a
        // different
        // one
        // for
        // duplicate
        // interfaces
        interfaceId++;
        Widget.setChildren(24, interfaces);
        int xOffset = 0;
        int yOffset = 1;

        // Background
        Widget.addSpriteComplete(interfaceId, child, 1883, 4 + xOffset, 39 + yOffset, interfaces,
            false);
        interfaceId++;
        child++;

        // Mini background
        Widget.addSpriteComplete(interfaceId, child, 1885, 11 + xOffset, 74 + yOffset, interfaces,
            false);
        interfaceId++;
        child++;

        // Information text background
        Widget.addSpriteComplete(interfaceId, child, 1884, 11 + xOffset, 46 + yOffset, interfaces,
            false);
        interfaceId++;
        child++;

        // Information text
        Widget.addText(interfaceId, "Information", fon, 2, ClientConstants.ORANGE,
            true);
        Widget.setBounds(interfaceId, 95 + xOffset, 49 + yOffset, child, interfaces);
        interfaceId++;
        child++;

        int buttonSpacing = 0;
        int buttonSpacingIncrease = 45;
        // Information button
        Widget.addHoverButtonComplete(interfaceId, child, 4 + xOffset, 5 + yOffset, 1882, 1887, 46,
            35, "Information", interfaces, false);
        interfaceId += 3;
        child += 2;
        buttonSpacing += buttonSpacingIncrease;

        // Information button icon
        Widget.addSpriteComplete(interfaceId, child, 1881, 16 + xOffset, 13 + yOffset, interfaces,
            false);
        interfaceId++;
        child++;

        // Panel button
        Widget.addHoverButtonComplete(interfaceId, child, 4 + xOffset + buttonSpacing,
            5 + yOffset, 1882, 1887, 46, 35, "Panel", interfaces, false);
        interfaceId += 3;
        child += 2;
        buttonSpacing += buttonSpacingIncrease;

        // Panel button icon
        Widget.addSpriteComplete(interfaceId, child, 1879, 63 + xOffset, 13 + yOffset, interfaces,
            false);
        interfaceId++;
        child++;

        // Activity button
        Widget.addHoverButtonComplete(interfaceId, child, 4 + xOffset + buttonSpacing,
            5 + yOffset, 1882, 1887, 46, 35, "Activities", interfaces, false);
        interfaceId += 3;
        child += 2;
        buttonSpacing += buttonSpacingIncrease;

        // Activity button icon
        Widget.addSpriteComplete(interfaceId, child, 1880, 107 + xOffset, 12 + yOffset,
            interfaces, false);
        interfaceId++;
        child++;

        // Start of duplicate interface changes.
        interfaceId += 201;

        // Mini background different
        Widget.addSpriteComplete(interfaceId, child, 1891, 11 + xOffset, 74 + yOffset, interfaces,
            true);
        interfaceId++;
        child++;

        // Call players button
        Widget.addHoverButtonComplete(interfaceId, child, 10 + xOffset, 74 + yOffset, 1892, 1890,
            84, 26, "Quest", interfaces, true);
        interfaceId += 3;
        child += 2;

        // Call players text
        Widget.addText(interfaceId, "Call players", fon, 1, ClientConstants.ORANGE,
            true);
        Widget.setBounds(interfaceId, 51 + xOffset, 79 + yOffset, child, interfaces);
        interfaceId++;
        child++;

        // World events
        Widget.addHoverButtonComplete(interfaceId, child, 94 + xOffset, 74 + yOffset, 1892, 1890,
            84, 26, "Quest", interfaces, true);
        interfaceId += 3;
        child += 2;

        // World event text
        Widget.addText(interfaceId, "World event", fon, 1, ClientConstants.ORANGE,
            true);
        Widget.setBounds(interfaceId, 134 + xOffset, 79 + yOffset, child, interfaces);
        interfaceId++;
        child++;

        // Scroll content
        Widget scrollTab1 = Widget.addInterface(interfaceId);
        interfaces.child(child, interfaceId, 11, 101); // Scroll interface.
        interfaceId++;
        child++;

        int scrollChildren = 30;
        scrollTab1.totalChildren(scrollChildren * 2);
        int y = 0;
        int scrollChild = 0;
        String[] activitiesTitle = {"World event", "Activities"};
        int[] activitiesTitleIndex = {0, 3};
        int panelYIncrease = 16;
        for (int index = 0; index < scrollChildren; index++) {
            panelYIncrease = 14;
            int spriteId = 1901;
            int xExtra = 5;
            String text = "";
            boolean hover = true;
            for (int i = 0; i < activitiesTitleIndex.length; i++) {
                if (index == activitiesTitleIndex[i]) {
                    text = activitiesTitle[i];
                    spriteId = 1902;
                    xExtra = 0;
                    hover = false;
                    panelYIncrease = 17;
                    break;
                }
                if (index > 0 && index == activitiesTitleIndex[i] - 1) {
                    panelYIncrease = 18;
                }
            }
            // Title sprite background
            Widget.addSprite(interfaceId, spriteId);
            scrollTab1.child(scrollChild, interfaceId, 2, y + 0);
            scrollChild++;
            interfaceId++;

            // Text
            if (hover) {
                Widget.addText(interfaceId, "", fon, 0, 0xffb000, false, true,
                    0xffffff, "Teleport", 226);
            } else {
                Widget.addText(interfaceId, text, 0xffb000, false, true, -1, fon, 0);
            }
            scrollTab1.child(scrollChild, interfaceId, 4 + xExtra, y + 4);
            scrollChild++;
            interfaceId++;

            y += panelYIncrease;
        }
        scrollTab1.width = 151;
        scrollTab1.height = 146;
        scrollTab1.scrollMax = 185;

    }
}

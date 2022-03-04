package com.valinor.cache.graphics.widget.impl;

import com.valinor.Client;
import com.valinor.cache.graphics.SimpleImage;
import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;
import com.valinor.cache.graphics.widget.option_menu.OptionMenuInterface;
import com.valinor.util.ConfigUtility;

/**
 * This class represents the world's teleporting manager.
 *
 * @author Zerikoth | 27 okt. 2020 : 16:12
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>}
 */
public class TeleportWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        teleportInterface(font);
        teleportFavoriteInterface(font);
        teleport(font);
    }

    public static void teleport(AdvancedFont[] font) {
        Widget widget = addInterface(65000);

        addSprite(65001, 1283);
        closeButton(65002, 24, 25, false);
        addHoverButton(65003,1284, 100, 32, "Teleport", -1, 65004, 1);
        addHoveredButton(65004,1285, 100, 32, 65005);
        addText(65006, "Teleport", font, 2, 16751360, true, true);
        addText(65007, "Bosses", font, 2, 16751360, true, true);
        addText(65008, "King black dragon", font, 0, 16751360, true, true);
        addText(65009, "Description", font, 2, 16751360, true, true);
        addText(65010, "Best Possible Drops", font, 2, 16751360, true, true);
        addText(65011, "Teleportation Network", font, 2, 16751360, true, true);

        Widget scroll = addTabInterface(65012);
        scroll.width = 298;
        scroll.height = 47;
        scroll.scrollMax = 400;
        addContainer(65014, TYPE_CONTAINER, 7, 5, 10, 10, 0, false, false, true);
        scroll.totalChildren(1);
        scroll.child(0, 65014, 1, 5);

        addNPCWidget(65015);

        addText(65017, "- Max hit:", font, 2, 16777215, true, true);
        addConfigSprite(65018, 508, -1, 1, ConfigUtility.TELEPORT_INTERFACE_MAX_HIT);
        addText(65019, "55", font, 0, 16777215, true, true);

        addText(65020, "- Respawn:", font, 2, 16777215, true, true);
        addConfigSprite(65021, 469, -1, 1, ConfigUtility.TELEPORT_INTERFACE_RESPAWN);
        addText(65022, "55 seconds", font, 0, 16777215, true, true);

        addText(65023, "- Multi:", font, 2, 16777215, true, true);
        addConfigSprite(65024, 1286, -1, 1, ConfigUtility.TELEPORT_INTERFACE_MULTI_AREA);

        addText(65025, "- Slayer req:", font, 2, 16777215, true, true);
        addConfigSprite(65026, 100, -1, 1, ConfigUtility.TELEPORT_INTERFACE_SLAYER);
        addText(65027, "1", font, 0, 16777215, true, true);

        addText(65028, "- Killcount:", font, 2, 16777215, true, true);
        addConfigSprite(65029, 460, -1, 1, ConfigUtility.TELEPORT_INTERFACE_KILLS);
        addText(65030, "32", font, 0, 16777215, true, true);

        int x = 5;
        int y = 20;
        setChildren(27, widget);
        widget.child(0, 65001, x, y);
        widget.child(1, 65002, 475 + x, 7 + y);
        widget.child(2, 65003, 152 + x, 173 + y);
        widget.child(3, 65004, 152 + x, 173 + y);
        widget.child(4, 65006, 202 + x, 181 + y);
        widget.child(5, 65007, 65 + x, 33 + y);
        widget.child(6, 65008, 315 + x, 38 + y);
        widget.child(7, 65009, 350 + x, 65 + y);
        widget.child(8, 65010, 247 + x, 217 + y);
        widget.child(9, 65011, 265 + x, 7 + y);
        widget.child(10, 65012, 156 + x, 236 + y);
        widget.child(11, 65015, 145, 70);
        widget.child(12, 65017, 320 + x, 87 + y);
        widget.child(13, 65018, 360 + x, 85 + y);
        widget.child(14, 65019, 372 + x, 90 + y);
        widget.child(15, 65020, 324 + x, 109 + y);
        widget.child(16, 65021, 365 + x, 111 + y);
        widget.child(17, 65022, 408 + x, 113 + y);
        widget.child(18, 65023, 312 + x, 131 + y);
        widget.child(19, 65024, 340 + x, 130 + y);
        widget.child(20, 65025, 330 + x, 150 + y);
        widget.child(21, 65026, 375 + x, 150 + y);
        widget.child(22, 65027, 400 + x, 153 + y);
        widget.child(23, 65028, 324 + x, 170 + y);
        widget.child(24, 65029, 365 + x, 173 + y);
        widget.child(25, 65030, 385 + x, 174 + y);

        widget.child(26, 65031, x, 53 + y);

        Widget teleportScroll = addInterface(65031);
        teleportScroll.scrollMax = 550;
        teleportScroll.width = 110;
        teleportScroll.height = 239;
        teleportScroll.hoverType = 87;
        int scrollY = 0;
        final int CHILD_LENGTH = 30 * 2;
        int child = 0;
        teleportScroll.totalChildren(CHILD_LENGTH);
        for (int index = 65035; index < 65035 + CHILD_LENGTH; index+= 2) {
            addSprite(index,1422);
            addClickableText(index + 1, "King black dragon", "Select", font, 0, 16750623, false, false, 125,15);
            teleportScroll.child(child++, index, 8, scrollY);
            teleportScroll.child(child++, index + 1, 10, scrollY + 5);
            scrollY+=18;
        }
    }

    private static void teleportInterface(AdvancedFont[] font) {
        final Widget widget = addTabInterface(72150);

        setChildren(5, widget);

        addSprite(72151, 1906);
        setBounds(72151, 10, 55, 0, widget);

        addInputField(72152, 15, 0xE68A00, "Search", 170, 23, false, true);
        setBounds(72152, 10, 30, 1, widget);

        addText(72153, "Teleportation Menu", 0xFF981F, true, true, 52, font, 2);
        setBounds(72153, 90, 10, 2, widget);

        addButton(72154, 1908, "Favorites");
        setBounds(72154, 163, 8, 3, widget);

        // List
        setBounds(72155, 10, 56, 4, widget);

        Widget list = addInterface(72155);

        setChildren(1, list);

        new OptionMenuInterface(72156, 153, 200, 100, new SimpleImage[] {Client.spriteCache.get(1907) }, "");
        setBounds(72156, 1, 1, 0, list);

        list.height = 198;
        list.width = 154;
        list.scrollMax = list.height;
    }

    private static void teleportFavoriteInterface(AdvancedFont[] font) {
        final Widget widget = addTabInterface(72200);

        setChildren(4, widget);

        addSprite(72201, 1906);
        setBounds(72201, 10, 55, 0, widget);

        addText(72202, "Favorite Teleports", 0xFF981F, true, true, 52, font, 2);
        setBounds(72202, 90, 10, 1, widget);

        closeButton(72203, 107, 108,true);
        setBounds(72203, 163, 8, 2, widget);

        // List
        setBounds(72205, 10, 56, 3, widget);

        Widget list = addInterface(72205);

        setChildren(1, list);

        new OptionMenuInterface(72206, 153, 200, 100,
            new SimpleImage[] { Client.spriteCache.get(1909),
                Client.spriteCache.get(1910),
                Client.spriteCache.get(1911) },
            "use atleast 3 characters.");
        setBounds(72206, 1, 1, 0, list);

        list.height = 198;
        list.width = 154;
        list.scrollMax = list.height;
    }
}

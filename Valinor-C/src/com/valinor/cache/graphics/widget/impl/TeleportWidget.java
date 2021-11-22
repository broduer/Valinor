package com.valinor.cache.graphics.widget.impl;

import com.valinor.Client;
import com.valinor.cache.graphics.SimpleImage;
import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;
import com.valinor.cache.graphics.widget.option_menu.OptionMenuInterface;

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

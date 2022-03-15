package com.valinor.cache.graphics.widget.impl;

import com.valinor.ClientConstants;
import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

import static com.valinor.util.ConfigUtility.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 14, 2022
 */
public class LeaderboardWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        final Widget widget = addInterface(75000);

        addSprite(75001, 1941);
        closeButton(75002, 24, 25, false);
        addText(75003, ClientConstants.CLIENT_NAME+" Player Hiscores", font, 2, 0xF7AA25, true, true);
        addText(75004, "Todays hiscores ends in 7h 30m", font, 2, 0xF7AA25, true, true);
        addText(75005, "Type", font, 2, 0xF7AA25, true, true);
        addText(75006, "Player", font, 2, 0xF7AA25, true, true);
        addText(75007, "Wins", font, 2, 0xF7AA25, true, true);
        addText(75008, "Reward", font, 2, 0xF7AA25, true, true);
        addConfigButton(75009, 75000, 1947, 1948, "Player hiscores", PLAYER_HISCORES, 0, OPTION_OK);
        addConfigButton(75010, 75000, 1946, 1947, "Clan hiscores", CLAN_HISCORES, 0, OPTION_OK);
        addConfigButton(75011, 75000, 1949, 1950, "Today", TODAY_HISCORES, 1, OPTION_OK);
        addConfigButton(75012, 75000, 1952, 1953, "This week", THIS_WEEK_HISCORES, 0, OPTION_OK);
        addConfigButton(75013, 75000, 1949, 1950, "All times", ALL_TIME_HISCORES, 0, OPTION_OK);
        addText(75014, "Today", font, 2, 0xF7AA25, true, true);
        addText(75015, "This week", font, 2, 0xF7AA25, true, true);
        addText(75016, "All time", font, 2, 0xF7AA25, true, true);

        Widget types = addTabInterface(75017);

        int y = 2;
        final int total = 6 * 2;
        int child = 0;
        types.totalChildren(total);
        int index = 0;
        for (int i = 75018; i < 75018 + total; i += 2) {
            index++;
            addConfigButton(i, 75017, 1943, 1942, "Deep Wildy Kills",LEADERBOARD_SIDEBAR + index, 0, OPTION_OK);
            addText(i + 1, "Deep Wildy Kills", font, 2, 0xF7AA25, false, true);

            types.child(child++, i, 0, y);
            types.child(child++, i + 1, 7, y + 8);
            y += 32;
        }

        Widget text = addTabInterface(75037);

        int textY = 4;
        final int textTotal = 10 * 4;
        int textChild = 0;
        text.totalChildren(textTotal);
        for (int i = 75038; i < 75038 + textTotal; i += 4) {
            addText(i, "1.", font, 1, 0xF7AA25, false, true);
            addText(i + 1, "Patrick", font, 2, 0xF7AA25, false, true);
            addText(i + 2, "10.000", font, 2, 0xF7AA25, false, true);
            addText(i + 3, "100K PKP", font, 2, 0xF7AA25, false, true);

            text.child(textChild++, i, 0, textY);
            text.child(textChild++, i + 1, 20, textY);
            text.child(textChild++, i + 2, 160, textY);
            text.child(textChild++, i + 3, 260, textY);
            textY += 19;
        }

        widget.totalChildren(18);
        widget.child(0, 75001, 15,5);
        widget.child(1, 75002, 470,14);
        widget.child(2, 75003, 255,15);
        widget.child(3, 75004, 265,300);
        widget.child(4, 75005, 75,77);
        widget.child(5, 75006, 175,77);
        widget.child(6, 75007, 325,77);
        widget.child(7, 75008, 440,77);
        widget.child(8, 75009, 405,40);
        widget.child(9, 75010, 450,40);
        widget.child(10, 75011, 20,40);
        widget.child(11, 75012, 100,40);
        widget.child(12, 75013, 180,40);
        widget.child(13, 75014, 70,47);
        widget.child(14, 75015, 148,47);
        widget.child(15, 75016, 230,47);
        widget.child(16, 75017, 21,94);
        widget.child(17, 75037, 145,97);
    }
}

package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

import static com.valinor.util.ConfigUtility.*;

/**
 * @author Patrick van Elderen | January, 17, 2021, 14:42
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class GambleWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        firstScreen(font);
        secondScreen(font);
    }

    private static void firstScreen(AdvancedFont[] font) {
        Widget widget = addTabInterface(16200);
        addSprite(16201, 1428);
        closeButton(16202, 24, 25, true);
        addText(16203, "Gambling with Patrick", font, 2, 0xF7AA25, true, true);
        addText(16204, "Flower poker", font, 1, 0xF7AA25, true, true);
        addConfigButton(16205, 16200, 491, 547, "Select", GAMBLE_FLOWER_POKER, 0, OPTION_OK);
        /*addText(16206, "55x2", font, 1, 0xF7AA25, true, true);
        addConfigButton(16207, 16200, 491, 547, "Select", GAMBLE_55X2_WE_HOST, 0, OPTION_OK);
        addText(16208, "Blackjack", font, 1, 0xF7AA25, true, true);
        addConfigButton(16209, 16200, 491, 547, "Select", GAMBLE_55X2_OPPONENT_HOST, 0, OPTION_OK);
        addText(16210, "Dice duel", font, 1, 0xF7AA25, true, true);
        addConfigButton(16211, 16200, 491, 547, "Select", GAMBLE_DICE_DUEL, 0, OPTION_OK);*/
        hoverButton(16212, "Accept", 1433, 1432);
        hoverButton(16215, "Decline", 1431, 1430);
        addText(16218, "Select game mode...", font, 2, 0xF7AA25, true, true);
        addText(16219, "Tutorial", font, 2, 0xF7AA25, true, true);
        addText(16222, "Lorem ipsum dolor sit amet, consectetur<br>adipiscing elit, sed do eiusmod tempor<br>incididunt ut labore et dolore magna aliqua.", font, 2, 0xF7AA25, false, true);
        addText(16223, "<col=ca0d0d>GAMBLE MODIFIED!", font, 1, 0xFFFFFF, true, true);

        Widget containerOne = addTabInterface(16228);
        containerOne.width = 135;
        containerOne.height = 130;
        containerOne.scrollMax = 275;
        addText(16229, "You (100,000 bm)", font, 1, 0xFFFFFF, false, true);
        addContainer(16230, TYPE_CONTAINER,4,7,2,5,0,false,true,true);
        cache[16230].actions = new String[]{
            "Remove",
            "Remove-5",
            "Remove-10",
            "Remove-All",
            "Remove-X"
        };
        containerOne.totalChildren(2);
        containerOne.child(0, 16229, 1, 1);
        containerOne.child(1, 16230, 1, 15);

        Widget scroll = addTabInterface(16231);
        scroll.width = 136;
        scroll.height = 130;
        scroll.scrollMax = 275;
        addText(16232, "Other (100,000 bm)", font, 1, 0xFFFFFF, false, true);
        addContainer(16233, TYPE_CONTAINER,4,7,2,5,0,false,true,true);
        cache[16233].actions = new String[]{
            "Remove",
            "Remove-5",
            "Remove-10",
            "Remove-All",
            "Remove-X"
        };
        scroll.totalChildren(2);
        scroll.child(0, 16232, 1, 1);
        scroll.child(1, 16233, 1, 15);

        widget.totalChildren(13);
        widget.child(0, 16201, 12,10);
        widget.child(1, 16202, 475,20);
        widget.child(2, 16203, 255,20);
        widget.child(3, 16204, 77,211);
        widget.child(4, 16205, 20,211);
        /*widget.child(5, 16206, 55,234);
        widget.child(6, 16207, 20,234);
        widget.child(7, 16208, 68,256);
        widget.child(8, 16209, 20,255);
        widget.child(9, 16210, 68,278);
        widget.child(10, 16211, 20,277);*/
        widget.child(5, 16212, 210,60);
        widget.child(6, 16215, 210,110);
        widget.child(7, 16218, 90,185);
        widget.child(8, 16219, 230,185);
        widget.child(9, 16222, 200,215);
        widget.child(10, 16223, 257,149);
        widget.child(11, 16228, 20,45);
        widget.child(12, 16231, 340,45);
    }

    private static void secondScreen(AdvancedFont[] font) {
        Widget widget = addTabInterface(15350);
        addSprite(15351, 1429);
        addText(15352, "Gambling with: Patrick89123 - Are you sure you wish to gamble?", font, 2, 0xF7AA25, false, true);
        hoverButton(15353, "Decline", 1431, 1430);
        hoverButton(15356, "Decline", 1431, 1430);
        addText(15359, "Other player has accepted!", font, 1, 0xFFFFFF, false, true);
        addText(15360, "Game Mode:<br>Flower Poker", font, 2, 0xF7AA25, false, true);
        addText(15361, "Your items:<br>(100K pkp)", font, 1, 0xF7AA25, false, true);
        addText(15362, "Patrick89123's items:<br>(100K pkp)", font, 1, 0xF7AA25, false, true);

        Widget firstItemContainer = addTabInterface(15365);
        firstItemContainer.contentType = 0;
        firstItemContainer.width = 130;
        firstItemContainer.height = 150;
        firstItemContainer.scrollMax = 500;
        int x = 65, y = 5;
        int amountOfLines = 28;
        firstItemContainer.totalChildren(amountOfLines);
        for (int index = 0; index < amountOfLines; index++) {
            addText(15366 + index, "100x Elysian spirit shield", font, 0, 0xFFFFFF, true, false);
            firstItemContainer.child(index, 15366 + index, x, y);
            y += 18;
        }

        Widget secondItemContainer = addTabInterface(15425);
        secondItemContainer.contentType = 0;
        secondItemContainer.width = 130;
        secondItemContainer.height = 150;
        secondItemContainer.scrollMax = 500;
        secondItemContainer.totalChildren(amountOfLines);
        int x1 = 65, y1 = 5;
        for (int index = 0; index < amountOfLines; index++) {
            addText(15426 + index, "1000x Scythe of vitur", font, 0, 0xFFFFFF, true, false);
            secondItemContainer.child(index, 15426 + index, x1, y1);
            y1 += 18;
        }

        widget.totalChildren(10);
        widget.child(0, 15351, 12,50);
        widget.child(1, 15352, 48,59);
        widget.child(2, 15353, 210,100);
        widget.child(3, 15356, 210,160);
        widget.child(4, 15359, 180,202);
        widget.child(5, 15360, 215,238);
        widget.child(6, 15361, 60,90);
        widget.child(7, 15362, 365,90);
        widget.child(8,15365, 20,125);
        widget.child(9,15425, 350,125);
    }
}

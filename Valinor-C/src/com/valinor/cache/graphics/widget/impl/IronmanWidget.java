package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

/**
 * The class which represents functionality for the ironman interface.
 * @author Patrick van Elderen | March, 06, 2021, 14:37
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class IronmanWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        ironman_widget_new(font);
        ironmanLeaderboard(font);
    }

    public static void ironmanLeaderboard(AdvancedFont[] font) {
        Widget widget = addInterface(67000);
        addSprite(67001, 1781);
        closeButton(67002, 24, 25,false);
        addText(67005, "<img=1770> Group Ironman Leaderboards", font, 2, 0xff981f, false, true);
        addText(67006, "Top 10 Groups", font, 2, 0xff981f, false, true);
        addText(67007, "Recent Groups", font, 2, 0xff981f, false, true);
        addText(67008, "Team:", font, 2, 0xff981f, false, true);
        addText(67009, "Status:", font, 2, 0xff981f, false, true);
        addText(67010, "Avg. Combat:", font, 2, 0xff981f, false, true);
        addText(67011, "Avg. Total And EXP:", font, 2, 0xff981f, false, true);
        addButton(67012, 1786,"Invite");
        addButton(67013, 1786,"Set name");
        addButton(67014, 1786,"Delete group");
        addText(67015, "Invite", font, 2, 0xff981f, false, true);
        addText(67016, "Set name", font, 2, 0xff981f, false, true);
        addText(67017, "Disband", font, 2, 0xff981f, false, true);
        addButton(67018, 1786,"Create");
        addText(67019, "Create", font, 2, 0xff981f, false, true);
        setChildren(19, widget);
        setBounds(67001, 20, 24, 0, widget);
        setBounds(67002, 479, 31, 1, widget);
        setBounds(67005, 150, 30, 2, widget);
        setBounds(67006, 50, 55, 3, widget);
        setBounds(67007, 50, 217, 4, widget);
        setBounds(67008, 40, 77, 5, widget);
        setBounds(67009, 163, 77, 6, widget);
        setBounds(67010, 240, 77, 7, widget);
        setBounds(67011, 350, 77, 8, widget);
        setBounds(67012, 365, 214, 9, widget);
        setBounds(67013, 365, 241, 10, widget);
        setBounds(67014, 365, 268, 11, widget);
        setBounds(67015, 390, 218, 12, widget);
        setBounds(67016, 379, 245, 13, widget);
        setBounds(67017, 388, 273, 14, widget);
        setBounds(67125, 35, 94, 15, widget);
        setBounds(67225, 35, 235, 16, widget);
        setBounds(67018, 365, 295, 17, widget);
        setBounds(67019, 388, 299, 18, widget);

        //Top 10
        Widget main = addTabInterface(67125);
        main.scrollPosition = 0;
        main.contentType = 0;
        main.width = 435;
        main.height = 117;
        main.scrollMax = 302;

        int y = 2;
        final int CHILD_LENGTH = 10 * 6;
        int child = 0;
        main.totalChildren(CHILD_LENGTH);
        int section = 0;
        for (int i = 67130; i < 67130 + CHILD_LENGTH; i += 6) {
            section++;
            addSprite(i, section % 2 == 0 ? 1784 : 1785);
            addText(i + 1, "Grootte bois040", font, 1, 16750623);
            addText(i + 2, "Offline", font, 1, 16750623);
            addText(i + 3, "Level: 126", font, 1, 16750623);
            addText(i + 4, "Total Level: 1202", font, 1, 16750623);
            addText(i + 5, "Total Exp: 200M", font, 1, 16750623);

            main.child(child++, i, 0, y);
            main.child(child++, i + 1, 3, y + 6);
            main.child(child++, i + 2, 130, y + 6);
            main.child(child++, i + 3, 215, y + 6);
            main.child(child++, i + 4, 328, y);
            main.child(child++, i + 5, 328, y + 14);
            y += 30;
        }

        //Recent
        Widget recent = addTabInterface(67225);
        recent.scrollPosition = 0;
        recent.contentType = 0;
        recent.width = 278;
        recent.height = 59;
        recent.scrollMax = 182;

        int recent_y = 2;
        final int RECENT_CHILD_LENGTH = 6 * 3;
        int recent_child = 0;
        recent.totalChildren(RECENT_CHILD_LENGTH);
        int recent_section = 0;
        for (int i = 67230; i < 67230 + RECENT_CHILD_LENGTH; i += 3) {
            recent_section++;
            addSprite(i, recent_section % 2 == 0 ? 1782 : 1783);
            addText(i + 1, "Group Name", font, 2, 0xff0000);
            addText(i + 2, "Henkie, Patrick, Malefique, Wezel", font, 0, 16750623);

            recent.child(recent_child++, i, 0, recent_y);
            recent.child(recent_child++, i + 1, 3, recent_y + 6);
            recent.child(recent_child++, i + 2, 130, recent_y + 2);
            recent_y += 30;
        }
    }

    private static void ironman_widget_new(AdvancedFont[] font) {
        Widget widget = addInterface(42400);
        addSprite(42401, 1766);
        addClickableSprites(42402, "Toggle", 490, 491, 547);
        addClickableSprites(42403, "Toggle", 490, 491, 547);
        addClickableSprites(42423, "Toggle", 490, 491, 547);
        addClickableSprites(42405, "Toggle", 490, 491, 547);
        addClickableSprites(42425, "Toggle", 490, 491, 547);
        addClickableSprites(42426, "Toggle", 490, 491, 547);
        addClickableSprites(42427, "Toggle", 490, 491, 547);
        addText(42428, "Rookie: X75 experience, no drop rate bonus.", font, 0, 0xFD851A, false, true);
        addText(42429, "Challenger: X30 experience, 5% drop rate bonus.", font, 0, 0xFD851A, false, true);
        addText(42430, "Gladiator: X7.5 experience, 10% drop rate bonus.", font, 0, 0xFD851A, false, true);
        addText(42407, "An Iron Man can't receive items or assistance from other players.<br>" + "They cannot trade, stake, receive PVP loot or pickup dropped items.", font, 0, 0xFD851A, false, true);
        addText(42408, "In addition, an Ultimate Iron Man cannot use banks.", font, 0, 0xFD851A, false, true);
        addText(42409, "Account Selection", font, 2, 0xFD851A, false, true);
        addText(42424, "A hardcore ironman account loses its status upon death.",font, 0, 0xFD851A, false, true);
        addText(42412, "Standard Iron Man", font, 0, 0xFFFFFF, false, true);
        addText(42413, "Ultimate Iron Man", font, 0, 0xFFFFFF, false, true);
        addText(42422, "Hardcore Iron Man", font, 0, 0xFFFFFF, false, true);
        addText(42410, "No Iron man restrictions will be applied to this account.", font, 0, 0xFD851A, false, true);
        addText(42415, "Regular account", font, 0, 0xFFFFFF, false, true);

        addText(42417, "Select your game mode", font, 1, 0xFFFFFF, false, true);
        addText(42418, "Select your experience mode", font, 1, 0xFFFFFF, false, true);
        addHoverButton(42419,1767, 23, 23, "Confirm and Continue", 0, 42420, 1);
        addHoveredButton(42420,1768, 23, 23, 42421);

        setChildren(24, widget);
        setBounds(42401, 15, 28, 0, widget);
        setBounds(42402, 30, 88, 1, widget);
        setBounds(42403, 30, 123, 2, widget);
        setBounds(42423, 30, 150, 3, widget);
        setBounds(42405, 30, 178, 4, widget);
        setBounds(42407, 50, 99, 5, widget);
        setBounds(42408, 50, 135, 6, widget);
        setBounds(42424, 50, 163, 7, widget);
        setBounds(42410, 50, 191, 8, widget);
        setBounds(42412, 50, 89, 9, widget);
        setBounds(42413, 50, 125, 10, widget);
        setBounds(42422, 50, 153, 11, widget);
        setBounds(42415, 50, 180, 12, widget);
        setBounds(42417, 150, 69, 13, widget);
        setBounds(42418, 125, 210, 14, widget);
        setBounds(42419, 465, 34, 15, widget);
        setBounds(42420, 465, 34, 16, widget);
        setBounds(42409, 200, 35, 17, widget);
        setBounds(42425, 110, 235, 18, widget);
        setBounds(42426, 110, 256, 19, widget);
        setBounds(42427, 110, 278, 20, widget);
        setBounds(42428, 130, 239, 21, widget);
        setBounds(42429, 130, 259, 22, widget);
        setBounds(42430, 130, 281, 23, widget);
    }
}

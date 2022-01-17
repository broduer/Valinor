package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.DrawLine;
import com.valinor.cache.graphics.widget.Widget;

import static com.valinor.ClientConstants.PVP_GAME_MODE_ENABLED;

/**
 * The class which represents functionality for the ironman interface.
 * @author Patrick van Elderen | March, 06, 2021, 14:37
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class IronmanWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        ironman_widget_new(font);
        ironmanLeaderboard(font);
        ironmanStorage(font);
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
            addText(i + 2, "Henkie, Patrick, Malefique, Wezel, Rakka rakka", font, 0, 16750623);

            recent.child(recent_child++, i, 0, recent_y);
            recent.child(recent_child++, i + 1, 3, recent_y + 6);
            recent.child(recent_child++, i + 2, 130, recent_y + 2);
            recent_y += 30;
        }
    }

    public static void ironmanStorage(AdvancedFont[] font) {
        Widget widget = addInterface(67500);
        addSprite(67501, 1921);
        closeButton(67502, 107, 108,false);
        addText(67503, "Group Storage", font, 2, 0xff981f, false, true);
        addText(67504, "0", font, 0, 0xff981f, false, true);
        new DrawLine(67505, 12, 0xFE9624, 255, DrawLine.LineType.HORIZONTAL);
        addText(67506, "80", font, 0, 0xff981f, false, true);
        addButton(67507, 1922, "Back to bank");
        addSprite(67508, 1924);
        addText(67509, "1,234,567", font, 0, 0xff9933, false, true);
        adjustableConfig(67510, "Shuffle items up", 1923, 180, 728, 727);
        adjustableConfig(67511, "Search", 729, 180, 728, 727);
        adjustableConfig(67512, "Deposit inventory", 730, 180, 728, 727);
        addConfigButton(67513, 67500, 122, 121, "1", 1455, 1, 5);
        addConfigButton(67514, 67500, 122, 121,"5", 1456, 0, 5);
        addConfigButton(67515, 67500, 122, 121,"10", 1457, 0, 5);
        addConfigButton(67516, 67500, 122, 121,"X", 1458, 0, 5);
        addConfigButton(67517, 67500, 122, 121,"All", 1459, 0, 5);
        addText(67518, "1", font, 1, 0xff981f, false, true);
        addText(67519, "5", font, 1, 0xff981f, false, true);
        addText(67520, "10", font, 1, 0xff981f, false, true);
        addText(67521, "X", font, 1, 0xff981f, false, true);
        addText(67522, "All", font, 1, 0xff981f, false, true);
        addText(67523, "Quantity:", font, 1, 0xff981f, false, true);
        addText(67524, "Rearrange mode:", font, 1, 0xff981f, false, true);
        addText(67525, "Withdraw as:", font, 1, 0xff981f, false, true);
        addConfigButton(67526, 67500, 1926, 1925, "Swap", 1460, 0, 5);
        addConfigButton(67527, 67500, 1926, 1925,"Insert", 1461, 0, 5);
        addText(67528, "Swap", font, 1, 0xff981f, false, true);
        addText(67529, "Insert", font, 1, 0xff981f, false, true);
        addConfigButton(67530, 67500, 1928, 1927, "Item", 1462, 0, 5);
        addConfigButton(67531, 67500, 1928, 1927,"Note", 1463, 0, 5);
        addText(67532, "Item", font, 1, 0xff981f, false, true);
        addText(67533, "Note", font, 1, 0xff981f, false, true);
        addText(67534, "Save your changes", font, 0, 0xffffff, false, true);
        addButton(67535, 1929,"Save");
        addHoverText(67536, "Save", "Save", font, 2, 0xff981f, false, true, 67, 0xffffff);
        addHoverText(67537, "Back to the bank", "Back to the bank", font, 1, 0xff981f, false, true, 67, 0xffffff);

        Widget container = addTabInterface(67540);
        container.width = 390;
        container.height = 245;
        container.scrollMax = 300;
        addContainer(67541, TYPE_CONTAINER,8,10,15,20,0,false,true,true, "Remove", "Remove-5", "Remove-10", "Remove-All", "Remove-X");
        container.totalChildren(1);
        container.child(0, 67541, 0, -1);

        setChildren(37, widget);
        setBounds(67501, 12, 1, 0, widget);
        setBounds(67502, 473, 8, 1, widget);
        setBounds(67503, 200, 10, 2, widget);
        setBounds(67504, 25, 6, 3, widget);
        setBounds(67505, 23, 16, 4, widget);
        setBounds(67506, 22, 17, 5, widget);
        setBounds(67507, 50, 7, 6, widget);
        setBounds(67508, 395, 10, 7, widget);
        setBounds(67509, 415, 12, 8, widget);
        setBounds(67510, 25, 125, 9, widget);
        setBounds(67511, 25, 175, 10, widget);
        setBounds(67512, 25, 225, 11, widget);
        setBounds(67513, 345, 297, 12, widget);
        setBounds(67514, 375, 297, 13, widget);
        setBounds(67515, 405, 297, 14, widget);
        setBounds(67516, 435, 297, 15, widget);
        setBounds(67517, 465, 297, 16, widget);
        setBounds(67518, 353, 302, 17, widget);
        setBounds(67519, 383, 302, 18, widget);
        setBounds(67520, 410, 302, 19, widget);
        setBounds(67521, 443, 302, 20, widget);
        setBounds(67522, 470, 302, 21, widget);
        setBounds(67523, 390, 282, 22, widget);
        setBounds(67524, 28, 281, 23, widget);
        setBounds(67525, 140, 282, 24, widget);
        setBounds(67526, 23, 297, 25, widget);
        setBounds(67527, 78, 297, 26, widget);
        setBounds(67528, 32, 301, 27, widget);
        setBounds(67529, 87, 301, 28, widget);
        setBounds(67530, 133, 297, 29, widget);
        setBounds(67531, 178, 297, 30, widget);
        setBounds(67532, 141, 301, 31, widget);
        setBounds(67533, 184, 301, 32, widget);
        setBounds(67534, 233, 282, 33, widget);
        setBounds(67535, 232, 297, 34, widget);
        setBounds(67536, 265, 301, 35, widget);
        setBounds(67540, 72, 40, 36, widget);
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
        if(PVP_GAME_MODE_ENABLED) {
            addClickableSprites(42431, "Toggle", 490, 491, 547);
        }
        addText(42428, "Rookie: X150 experience, no drop rate bonus.", font, 0, 0xFD851A, false, true);
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
        if(PVP_GAME_MODE_ENABLED) {
            addText(42432, "PvP account", font, 0, 0xFFFFFF, false, true);
            addText(42433, "PvP ready account.", font, 0, 0xFD851A, false, true);
        }

        addText(42417, "Select your game mode", font, 1, 0xFFFFFF, false, true);
        addText(42418, "Select your experience mode", font, 1, 0xFFFFFF, false, true);
        hoverButton(42419, "Confirm and Continue", 1767, 1768);

        setChildren(PVP_GAME_MODE_ENABLED ? 26 : 23, widget);
        setBounds(42401, 15, 28, 0, widget);
        setBounds(42402, 30, 85, 1, widget);
        setBounds(42403, 30, 114, 2, widget);
        setBounds(42423, 30, 138, 3, widget);
        setBounds(42405, 30, 159, 4, widget);
        setBounds(42407, 50, 96, 5, widget);
        setBounds(42408, 50, 128, 6, widget);
        setBounds(42424, 50, 150, 7, widget);
        setBounds(42410, 50, 172, 8, widget);
        setBounds(42412, 50, 87, 9, widget);
        setBounds(42413, 50, 117, 10, widget);
        setBounds(42422, 50, 139, 11, widget);
        setBounds(42415, 50, 160, 12, widget);
        setBounds(42417, 150, 69, 13, widget);
        setBounds(42418, 125, 210, 14, widget);
        setBounds(42419, 465, 34, 15, widget);
        setBounds(42409, 200, 35, 16, widget);
        setBounds(42425, 110, 235, 17, widget);
        setBounds(42426, 110, 256, 18, widget);
        setBounds(42427, 110, 278, 19, widget);
        setBounds(42428, 130, 239, 20, widget);
        setBounds(42429, 130, 259, 21, widget);
        setBounds(42430, 130, 281, 22, widget);
        if(PVP_GAME_MODE_ENABLED) {
            setBounds(42431, 30, 182, 23, widget);
            setBounds(42432, 50, 182, 24, widget);
            setBounds(42433, 50, 193, 25, widget);
        }
    }
}

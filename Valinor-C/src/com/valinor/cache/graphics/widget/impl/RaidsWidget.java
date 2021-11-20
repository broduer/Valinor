package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

import static com.valinor.util.ConfigUtility.*;

/**
 * @author Patrick van Elderen | April, 19, 2021, 16:12
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class RaidsWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        points(font);
        reward();
        party(font);
    }

    private static void party(AdvancedFont[] font) {
        Widget widget = addInterface(12100);
        addSprite(12101,1787);
        closeButton(12102,24,25,false);
        addText(12103, "Raiding party setup - Patrick's party", font,2,0xff981f,false,true);
        addText(12104, "Possible Drops", font,2,0xff981f,false,true);
        addText(12105, "Raids type", font,2,0xff981f,false,true);
        addText(12106, "Party Members", font,2,0xff981f,false,true);
        addText(12108, "Patrick", font,1,0xff981f,false,true);
        addText(12109, "Malefique", font,1,0xff981f,false,true);
        addText(12110, "Matthew", font,1,0xff981f,false,true);
        addText(12111, "Swapcity", font,1,0xff981f,false,true);
        addText(12112, "Tink Tink", font,1,0xff981f,false,true);
        addText(12113, "Chambers of Xeric", font,1,0xff981f,false,true);
        addText(12114, "Theatre of Blood", font,1,0xff981f,false,true);
        addConfigButton(12115, COS_CONFIG_ID, 1790, 1791, 115, 12, "Select", 0, 5, COS_CONFIG_ID, false);
        addConfigButton(12116, TOB_CONFIG_ID, 1790, 1791, 107, 12, "Select", 0, 5, TOB_CONFIG_ID, false);
        addText(12117, "Patrick12345", font,1,0xff981f,true,true);
        addText(12118, "Malefique123", font,1,0xff981f,true,true);
        addText(12119, "Matthew12345", font,1,0xff981f,true,true);
        addText(12120, "Swapcity123", font,1,0xff981f,true,true);
        addText(12121, "Tink Tink123", font,1,0xff981f,true,true);
        hoverButton(12125, "Kick", 1792, 1793);
        hoverButton(12128, "Kick", 1792, 1793);
        hoverButton(12131, "Kick", 1792, 1793);
        hoverButton(12134, "Kick", 1792, 1793);
        addContainer(12137, TYPE_CONTAINER, 6, 4, 10, 2, 0, false, true, true);
        addText(12138, "Chamber of Secrets", font,1,0xff981f,false,true);
        addConfigButton(12139, HP_CONFIG_ID, 1790, 1791, 107, 12, "Select", 0, 5, HP_CONFIG_ID, false);
        hoverButton(12140, "Invite", 1788, 1789);
        hoverButton(12143, "Start Party", 1788, 1789);
        hoverButton(12146, "Leave Party", 1788, 1789);
        addText(12148, "Invite", font,2,0xff981f,true,true);
        addText(12149, "Start Raid", font,2,0xff981f,true,true);
        addText(12150, "Leave Party", font,2,0xff981f,true,true);

        int index = 0;
        widget.totalChildren(29);
        setBounds(12101, 0, 2, index++, widget);
        setBounds(12102, 490, 10, index++, widget);
        setBounds(12103, 149, 9, index++, widget);
        setBounds(12104, 260, 47,index++, widget);
        setBounds(12105, 47, 47, index++, widget);
        setBounds(12106, 155, 182, index++, widget);
        setBounds(12113, 45, 82, index++, widget);
        setBounds(12114, 45, 109, index++, widget);
        setBounds(12115, 32, 84, index++, widget);
        setBounds(12116, 32, 111, index++, widget);
        setBounds(12117, 267, 212, index++, widget);
        setBounds(12118, 168, 248, index++, widget);
        setBounds(12119, 267, 248, index++, widget);
        setBounds(12120, 365, 246, index++, widget);
        setBounds(12121, 265, 280, index++, widget);
        setBounds(12125, 198, 241, index++, widget);
        setBounds(12128, 298, 241, index++, widget);
        setBounds(12131, 398, 241, index++, widget);
        setBounds(12134, 299, 275, index++, widget);
        setBounds(12137, 238, 65, index++, widget);
        setBounds(12138, 45, 134, index++, widget);
        setBounds(12139, 32, 137, index++, widget);
        setBounds(12140, 13, 178, index++, widget);
        setBounds(12143, 13, 228, index++, widget);
        setBounds(12145, 13, 278, index++, widget);
        setBounds(12146, 13, 278, index++, widget);
        setBounds(12148, 63, 184, index++, widget);
        setBounds(12149, 63, 234, index++, widget);
        setBounds(12150, 60, 283, index++, widget);
    }

    private static void reward() {
        Widget widget = addInterface(12020);
        addSprite(12021,1780);
        addItem(12022,true);
        addItem(12023,true);
        addItem(12024,false);
        closeButton(12025,107,108,false);

        widget.totalChildren(5);
        setBounds(12021, 140, 75, 0, widget);
        setBounds(12022, 275, 115, 1, widget);
        setBounds(12023, 275, 155, 2, widget);
        setBounds(12024, 275, 200, 3, widget);
        setBounds(12025, 372, 82, 4, widget);
    }

    private static void points(AdvancedFont[] font) {
        Widget points_widget = addInterface(12000);
        points_widget.totalChildren(5);

        addOutlinedColorBox(12001, 0x534a40, 100, 27, 200);
        addText(12002, "Total:", font,1,0xff981f,false,true);
        addText(12003, "84,306", font,1,0xffffff,false,true);
        addText(12004, "Iron DVS:", font,1,0xff981f,false,true);
        addText(12005, "12,724", font,1,0xffffff,false,true);
        setBounds(12001, 15, 9, 0, points_widget);
        setBounds(12002, 15, 9, 1, points_widget);
        setBounds(12003, 74, 9, 2, points_widget);
        setBounds(12004, 15, 20, 3, points_widget);
        setBounds(12005, 74, 20, 4, points_widget);
    }
}

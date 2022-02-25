package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;
import com.valinor.model.content.prayer.PrayerSystem;

public class PrayerSidebarWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        quickPrayers(font);

        addPrayer(28001, "Activate <col=ffb000>Preserve", 31, 32, 150, 1, -1, 151, 152, 1, 708, 28003);

        addPrayer(28004, "Activate <col=ffb000>Rigour", 31, 32, 150, 1, -5, 153, 154, 1, 710, 28006);

        addPrayer(28007, "Activate <col=ffb000>Augury", 31, 32, 150, 1, -3, 155, 156, 1, 712, 28009);

        addPrayerHover(28003, "Level 55<br>Preserve<br>Boosted stats last 20% longer.", -135, -60);

        addPrayerHover(28006, "Level 74<br>Rigour<br>Increases your Ranged attack<br>by 20% and damage by 23%,<br>and your defence by 25%", -70, -100);

        addPrayerHover(28009, "Level 77<br>Augury<br>Increases your Magic attack<br>by 25% and your defence by <br>25%", -110, -100);

        PrayerSystem.prayerPlacement();
        curseTabInterface(font);
        quickCursePrayers(font);
    }

    private static void quickPrayers(AdvancedFont[] font) {
        int frame = 0;
        Widget tab = addTabInterface(17200);

        addTransparentSprite(17235, 131, 50);
        addSpriteLoader(17201, 132);
        addText(17231, "Select your quick prayers below.", font, 0, 0xFF981F, false, true);

        int child = 17202;
        int config = 620;
        for (int i = 0; i < 29; i++) {
            addConfigButton(child++, 17200, 133, 134, "Select", config++, 0, 1);
        }

        addHoverButton(17232, 135, 190, 24, "Confirm Selection", -1, 17233, 1);
        addHoveredButton(17233, 136, 190, 24, 17234);

        setChildren(64, tab);
        setBounds(5632, 5, 8 + 20, frame++, tab);
        setBounds(5633, 44, 8 + 20, frame++, tab);
        setBounds(5634, 79, 11 + 20, frame++, tab);
        setBounds(19813, 116, 10 + 20, frame++, tab);
        setBounds(19815, 153, 9 + 20, frame++, tab);
        setBounds(5635, 5, 48 + 20, frame++, tab);
        setBounds(5636, 44, 47 + 20, frame++, tab);
        setBounds(5637, 79, 49 + 20, frame++, tab);
        setBounds(5638, 116, 50 + 20, frame++, tab);
        setBounds(5639, 154, 50 + 20, frame++, tab);
        setBounds(5640, 4, 84 + 20, frame++, tab);
        setBounds(19817, 44, 87 + 20, frame++, tab);
        setBounds(19820, 81, 85 + 20, frame++, tab);
        setBounds(5641, 117, 85 + 20, frame++, tab);
        setBounds(5642, 156, 87 + 20, frame++, tab);
        setBounds(5643, 5, 125 + 20, frame++, tab);
        setBounds(5644, 43, 124 + 20, frame++, tab);
        setBounds(13984, 83, 124 + 20, frame++, tab);
        setBounds(5645, 115, 121 + 20, frame++, tab);
        setBounds(19822, 154, 124 + 20, frame++, tab);
        setBounds(19824, 5, 160 + 20, frame++, tab);
        setBounds(5649, 41, 158 + 20, frame++, tab);
        setBounds(5647, 79, 163 + 20, frame++, tab);
        setBounds(5648, 116, 158 + 20, frame++, tab);

        //Preserve
        setBounds(28002, 157, 160 + 20, frame++, tab);

        //Chivarly
        setBounds(19826, 10, 208, frame++, tab);

        //Piety
        setBounds(19828, 45, 207 + 13, frame++, tab);

        //Rigour
        setBounds(28005, 85, 210, frame++, tab);

        //Augury
        setBounds(28008, 124, 210, frame++, tab);

        setBounds(17235, 0, 25, frame++, tab);// Faded backing
        setBounds(17201, 0, 22, frame++, tab);// Split
        setBounds(17201, 0, 237, frame++, tab);// Split

        setBounds(17202, 5 - 3, 8 + 17, frame++, tab);
        setBounds(17203, 44 - 3, 8 + 17, frame++, tab);
        setBounds(17204, 79 - 3, 8 + 17, frame++, tab);
        setBounds(17205, 116 - 3, 8 + 17, frame++, tab);
        setBounds(17206, 153 - 3, 8 + 17, frame++, tab);
        setBounds(17207, 5 - 3, 48 + 17, frame++, tab);
        setBounds(17208, 44 - 3, 48 + 17, frame++, tab);
        setBounds(17209, 79 - 3, 48 + 17, frame++, tab);
        setBounds(17210, 116 - 3, 48 + 17, frame++, tab);
        setBounds(17211, 153 - 3, 48 + 17, frame++, tab);
        setBounds(17212, 5 - 3, 85 + 17, frame++, tab);
        setBounds(17213, 44 - 3, 85 + 17, frame++, tab);
        setBounds(17214, 79 - 3, 85 + 17, frame++, tab);
        setBounds(17215, 116 - 3, 85 + 17, frame++, tab);
        setBounds(17216, 153 - 3, 85 + 17, frame++, tab);
        setBounds(17217, 5 - 3, 124 + 17, frame++, tab);
        setBounds(17218, 44 - 3, 124 + 17, frame++, tab);
        setBounds(17219, 79 - 3, 124 + 17, frame++, tab);
        setBounds(17220, 116 - 3, 124 + 17, frame++, tab);
        setBounds(17221, 153 - 3, 124 + 17, frame++, tab);
        setBounds(17222, 5 - 3, 160 + 17, frame++, tab);
        setBounds(17223, 44 - 3, 160 + 17, frame++, tab);
        setBounds(17224, 79 - 3, 160 + 17, frame++, tab);
        setBounds(17225, 116 - 3, 160 + 17, frame++, tab);
        setBounds(17226, 153 - 3, 160 + 17, frame++, tab);

        setBounds(17227, 1, 207 + 4, frame++, tab); //Chivalry toggle button
        setBounds(17228, 41, 207 + 4, frame++, tab); //Piety toggle button
        setBounds(17229, 77, 207 + 4, frame++, tab); //Rigour toggle button
        setBounds(17230, 116, 207 + 4, frame++, tab); //Augury toggle button

        setBounds(17231, 5, 5, frame++, tab);// text
        setBounds(17232, 0, 237, frame++, tab);// confirm
        setBounds(17233, 0, 237, frame++, tab);// Confirm hover
    }

    private static void quickCursePrayers(AdvancedFont[] font) {
        int frame = 0;
        Widget tab = addTabInterface(18200);

        addTransparentSprite(18235, 131, 50);
        addSpriteLoader(18201, 132);
        addText(18231, "Select your quick prayers below.", font, 0, 0xFF981F, false, true);

        int child = 18202;
        int config = 720;
        for (int i = 0; i < 20; i++) {
            addConfigButton(child++, 18200, 133, 134, "Select", config++, 0, 1);
        }

        addHoverButton(18232, 135, 190, 24, "Confirm Selection", -1, 18233, 1);
        addHoveredButton(18233, 136, 190, 24, 18234);

        setChildren(46, tab);
        setBounds(32504, 5, 8 + 20, frame++, tab);
        setBounds(32506, 44, 8 + 20, frame++, tab);
        setBounds(32508, 79, 11 + 20, frame++, tab);
        setBounds(32510, 116, 10 + 20, frame++, tab);
        setBounds(32512, 153, 9 + 20, frame++, tab);
        setBounds(32514, 5, 48 + 20, frame++, tab);
        setBounds(32516, 44, 47 + 20, frame++, tab);
        setBounds(32518, 79, 49 + 20, frame++, tab);
        setBounds(32520, 116, 50 + 20, frame++, tab);
        setBounds(32522, 154, 50 + 20, frame++, tab);
        setBounds(32524, 4, 84 + 20, frame++, tab);
        setBounds(32526, 44, 87 + 20, frame++, tab);
        setBounds(32528, 81, 85 + 20, frame++, tab);
        setBounds(32530, 117, 85 + 20, frame++, tab);
        setBounds(32532, 156, 87 + 20, frame++, tab);
        setBounds(32534, 5, 125 + 20, frame++, tab);
        setBounds(32536, 43, 124 + 20, frame++, tab);
        setBounds(32538, 83, 124 + 20, frame++, tab);
        setBounds(32540, 115, 121 + 20, frame++, tab);
        setBounds(32542, 154, 124 + 20, frame++, tab);

        setBounds(18235, 0, 25, frame++, tab);// Faded backing
        setBounds(18201, 0, 22, frame++, tab);// Split
        setBounds(18201, 0, 237, frame++, tab);// Split

        setBounds(18202, 5 - 3, 8 + 17, frame++, tab);
        setBounds(18203, 44 - 3, 8 + 17, frame++, tab);
        setBounds(18204, 79 - 3, 8 + 17, frame++, tab);
        setBounds(18205, 116 - 3, 8 + 17, frame++, tab);
        setBounds(18206, 153 - 3, 8 + 17, frame++, tab);
        setBounds(18207, 5 - 3, 48 + 17, frame++, tab);
        setBounds(18208, 44 - 3, 48 + 17, frame++, tab);
        setBounds(18209, 79 - 3, 48 + 17, frame++, tab);
        setBounds(18210, 116 - 3, 48 + 17, frame++, tab);
        setBounds(18211, 153 - 3, 48 + 17, frame++, tab);
        setBounds(18212, 5 - 3, 85 + 17, frame++, tab);
        setBounds(18213, 44 - 3, 85 + 17, frame++, tab);
        setBounds(18214, 79 - 3, 85 + 17, frame++, tab);
        setBounds(18215, 116 - 3, 85 + 17, frame++, tab);
        setBounds(18216, 153 - 3, 85 + 17, frame++, tab);
        setBounds(18217, 5 - 3, 124 + 17, frame++, tab);
        setBounds(18218, 44 - 3, 124 + 17, frame++, tab);
        setBounds(18219, 79 - 3, 124 + 17, frame++, tab);
        setBounds(18220, 116 - 3, 124 + 17, frame++, tab);
        setBounds(18221, 153 - 3, 124 + 17, frame++, tab);

        setBounds(18231, 5, 5, frame++, tab);// text
        setBounds(18232, 0, 237, frame++, tab);// confirm
        setBounds(18233, 0, 237, frame++, tab);// Confirm hover
    }

    private static void curseTabInterface(AdvancedFont[] font) {
        Widget statAdjustmentsWidget = addInterface(32600);

        addSpriteLoader(688, 1194);
        addText(687, "99/99", 0xff981f, false, false, -1, font, 1);
        addText(690, "+0%", 0xff981f, false, false, -1, font, 0);
        addText(691, "+0%", 0xff981f, false, false, -1, font, 0);
        addText(692, "+0%", 0xff981f, false, false, -1, font, 0);
        addText(693, "0%", 0xff981f, false, false, -1, font, 0);
        addText(694, "0%", 0xff981f, false, false, -1, font, 0);

        int child = 0;
        setChildren(6, statAdjustmentsWidget);
        setBounds(688, 0, 170, child, statAdjustmentsWidget);
        child++;
        setBounds(690, 4, 201, child, statAdjustmentsWidget);
        child++;
        setBounds(691, 43, 201, child, statAdjustmentsWidget);
        child++;
        setBounds(692, 79, 201, child, statAdjustmentsWidget);
        child++;
        setBounds(693, 125, 201, child, statAdjustmentsWidget);
        child++;
        setBounds(694, 165, 201, child, statAdjustmentsWidget);
        child++;

        Widget widget = addTabInterface(32500);
        int index = 0;

        addSpriteLoader(689, 1195);
        addCustomClickableText(695, "Show stat adjustments.", "Show", font, 0, 0xFFCC00, true, false, 190, 10);
        addSpriteLoader(32502, 1196);
        addPrayer(32503, 0, 610, 49, 1197, 1217, "Protect Item", 32582);
        addPrayer(32505, 0, 611, 49, 1198, 1218, "Sap Warrior", 32544);
        addPrayer(32507, 0, 612, 51, 1199, 1219, "Sap Ranger", 32546);
        addPrayer(32509, 0, 613, 53, 1200, 1220, "Sap Mage", 32548);
        addPrayer(32511, 0, 614, 55, 1201, 1221, "Sap Spirit", 32550);
        addPrayer(32513, 0, 615, 58, 1202, 1222, "Berserker", 32552);
        addPrayer(32515, 0, 616, 61, 1203, 1223, "Deflect Summoning", 32554);
        addPrayer(32517, 0, 617, 64, 1204, 1224, "Deflect Magic", 32556);
        addPrayer(32519, 0, 618, 67, 1205, 1225, "Deflect Missiles", 32558);
        addPrayer(32521, 0, 619, 70, 1206, 1226, "Deflect Melee", 32560);
        addPrayer(32523, 0, 620, 73, 1207, 1227, "Leech Attack", 32562);
        addPrayer(32525, 0, 621, 75, 1208, 1228, "Leech Ranged", 32564);
        addPrayer(32527, 0, 622, 77, 1209, 1229, "Leech Magic", 32566);
        addPrayer(32529, 0, 623, 79, 1210, 1230, "Leech Defence", 32568);
        addPrayer(32531, 0, 624, 81, 1211, 1231, "Leech Strength", 32570);
        addPrayer(32533, 0, 625, 83, 1212, 1232, "Leech Energy", 32572);
        addPrayer(32535, 0, 626, 85, 1213, 1233, "Leech Special Attack", 32574);
        addPrayer(32537, 0, 627, 88, 1214, 1234, "Wrath", 32576);
        addPrayer(32539, 0, 628, 91, 1215, 1235, "Soul Split", 32578);
        addPrayer(32541, 0, 629, 94, 1216, 1236, "Turmoil", 32580);
        addTooltip(32582, "Level 50<br>Protect Item<br>Keep 1 extra item if you die");
        addTooltip(32544,
            "Level 50<br>Sap Warrior<br>Drains 10% of enemy Attack,<br>Strength and Defence,<br>increasing to 20% over time");
        addTooltip(32546,
            "Level 52<br>Sap Ranger<br>Drains 10% of enemy Ranged<br>and Defence, increasing to 20%<br>over time");
        addTooltip(32548, "Level 54<br>Sap Mage<br>Drains 10% of enemy Magic<br>and Defence, increasing to 20%<br>over time");
        addTooltip(32550, "Level 56<br>Sap Spirit<br>Drains enenmy special attack<br>energy");
        addTooltip(32552, "Level 59<br>Berserker<br>Boosted stats last 15% longer");
        addTooltip(32554,
            "Level 62<br>Deflect Summoning<br>Reduces damage dealt from<br>Summoning scrolls, prevents the<br>use of a familiar's special<br>attack, and can deflect some of<br>damage back to the attacker");
        addTooltip(32556,
            "Level 65<br>Deflect Magic<br>Protects against magical attacks<br>and can deflect some of the<br>damage back to the attacker");
        addTooltip(32558,
            "Level 68<br>Deflect Missiles<br>Protects against ranged attacks<br>and can deflect some of the<br>damage back to the attacker");
        addTooltip(32560,
            "Level 71<br>Deflect Melee<br>Protects against melee attacks<br>and can deflect some of the<br>damage back to the attacker");
        addTooltip(32562,
            "Level 74<br>Leech Attack<br>Boosts Attack by 5%, increasing<br>to 10% over time, while draining<br>enemy Attack by 10%, increasing<br>to 25% over time");
        addTooltip(32564,
            "Level 76<br>Leech Ranged<br>Boosts Ranged by 5%, increasing<br>to 10% over time, while draining<br>enemy Ranged by 10%,<br>increasing to 25% over<br>time");
        addTooltip(32566,
            "Level 78<br>Leech Magic<br>Boosts Magic by 5%, increasing<br>to 10% over time, while draining<br>enemy Magic by 10%, increasing<br>to 25% over time");
        addTooltip(32568,
            "Level 80<br>Leech Defence<br>Boosts Defence by 5%, increasing<br>to 10% over time, while draining<br> enemy Defence by10%,<br>increasing to 25% over<br>time");
        addTooltip(32570,
            "Level 82<br>Leech Strength<br>Boosts Strength by 5%, increasing<br>to 10% over time, while draining<br>enemy Strength by 10%, increasing<br> to 25% over time");
        addTooltip(32572, "Level 84<br>Leech Energy<br>Drains enemy run energy, while<br>increasing your own");
        addTooltip(32574,
            "Level 86<br>Leech Special Attack<br>Drains enemy special attack<br>energy, while increasing your<br>own");
        addTooltip(32576, "Level 89<br>Wrath<br>Inflicts damage to nearby<br>targets if you die");
        addTooltip(32578,
            "Level 92<br>Soul Split<br>1/4 of damage dealt is also removed<br>from opponent's Prayer and<br>added to your Hitpoints");
        addTooltip(32580,
            "Level 95<br>Turmoil<br>Increases Attack and Defence<br>by 15%, plus 15% of enemy's<br>level, and Strength by 23% plus<br>10% of enemy's level");
        setChildren(65, widget);
        setBounds(689, 0, 217, index, widget);
        index++;
        setBounds(695, 0, 222, index, widget);
        index++;
        /* curse start */
        setBounds(687, 85, 241, index, widget);
        index++;
        setBounds(32502, 65, 241, index, widget);
        index++;
        setBounds(32503, 2, 5, index, widget);
        index++;
        setBounds(32504, 8, 8, index, widget);
        index++;
        setBounds(32505, 40, 5, index, widget);
        index++;
        setBounds(32506, 47, 12, index, widget);
        index++;
        setBounds(32507, 76, 5, index, widget);
        index++;
        setBounds(32508, 82, 11, index, widget);
        index++;
        setBounds(32509, 113, 5, index, widget);
        index++;
        setBounds(32510, 116, 8, index, widget);
        index++;
        setBounds(32511, 150, 5, index, widget);
        index++;
        setBounds(32512, 155, 10, index, widget);
        index++;
        setBounds(32513, 2, 45, index, widget);
        index++;
        setBounds(32514, 9, 48, index, widget);
        index++;
        setBounds(32515, 39, 45, index, widget);
        index++;
        setBounds(32516, 42, 47, index, widget);
        index++;
        setBounds(32517, 76, 45, index, widget);
        index++;
        setBounds(32518, 79, 48, index, widget);
        index++;
        setBounds(32519, 113, 45, index, widget);
        index++;
        setBounds(32520, 116, 48, index, widget);
        index++;
        setBounds(32521, 151, 45, index, widget);
        index++;
        setBounds(32522, 154, 48, index, widget);
        index++;
        setBounds(32523, 2, 82, index, widget);
        index++;
        setBounds(32524, 6, 86, index, widget);
        index++;
        setBounds(32525, 40, 82, index, widget);
        index++;
        setBounds(32526, 42, 86, index, widget);
        index++;
        setBounds(32527, 77, 82, index, widget);
        index++;
        setBounds(32528, 79, 86, index, widget);
        index++;
        setBounds(32529, 114, 83, index, widget);
        index++;
        setBounds(32530, 119, 87, index, widget);
        index++;
        setBounds(32531, 153, 83, index, widget);
        index++;
        setBounds(32532, 156, 86, index, widget);
        index++;
        setBounds(32533, 2, 120, index, widget);
        index++;
        setBounds(32534, 7, 125, index, widget);
        index++;
        setBounds(32535, 40, 120, index, widget);
        index++;
        setBounds(32536, 45, 124, index, widget);
        index++;
        setBounds(32537, 78, 120, index, widget);
        index++;
        setBounds(32538, 86, 124, index, widget);
        index++;
        setBounds(32539, 114, 120, index, widget);
        index++;
        setBounds(32540, 120, 125, index, widget);
        index++;
        setBounds(32541, 151, 120, index, widget);
        index++;
        setBounds(32542, 153, 127, index, widget);
        index++;
        setBounds(32582, 10, 40, index, widget);
        index++;
        setBounds(32544, 20, 40, index, widget);
        index++;
        setBounds(32546, 20, 40, index, widget);
        index++;
        setBounds(32548, 20, 40, index, widget);
        index++;
        setBounds(32550, 20, 40, index, widget);
        index++;
        setBounds(32552, 10, 80, index, widget);
        index++;
        setBounds(32554, 10, 80, index, widget);
        index++;
        setBounds(32556, 10, 80, index, widget);
        index++;
        setBounds(32558, 10, 80, index, widget);
        index++;
        setBounds(32560, 10, 80, index, widget);
        index++;
        setBounds(32562, 10, 120, index, widget);
        index++;
        setBounds(32564, 10, 120, index, widget);
        index++;
        setBounds(32566, 10, 120, index, widget);
        index++;
        setBounds(32568, 5, 120, index, widget);
        index++;
        setBounds(32570, 5, 120, index, widget);
        index++;
        setBounds(32572, 10, 160, index, widget);
        index++;
        setBounds(32574, 10, 160, index, widget);
        index++;
        setBounds(32576, 10, 160, index, widget);
        index++;
        setBounds(32578, 10, 160, index, widget);
        index++;
        setBounds(32580, 10, 160, index, widget);
        index++;
        setBounds(32600, 0, 0, index, widget);
        index++;
    }

}

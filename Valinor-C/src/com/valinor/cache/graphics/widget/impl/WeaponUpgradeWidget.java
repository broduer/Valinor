package com.valinor.cache.graphics.widget.impl;

import com.valinor.cache.graphics.font.AdvancedFont;
import com.valinor.cache.graphics.widget.Widget;

/**
 * @author Patrick van Elderen | December, 15, 2020, 10:16
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class WeaponUpgradeWidget extends Widget {

    public static void unpack(AdvancedFont[] font) {
        background(font);
        components(font);
    }

    private static void background(final AdvancedFont[] font) {
        Widget widget = addInterface(73000);
        addSprite(73001, 1413);
        closeButton(73002, 142, 143, false);
        addText(73003, "Weapon Upgrading Service", font, 2, 16777215, true, false);
        widget.scrollMax = 0;

        widget.totalChildren(4);
        widget.child(0, 73001, 50, 50);//Background
        widget.child(1, 73002, 433, 56);//Close button
        widget.child(2, 73003, 250, 58);//Title
        widget.child(3, 73008, 7, 88);//components
    }

    private static void components(final AdvancedFont[] font) {
        Widget component = addInterface(73008);
        addSprite(73009, 1414);
        addHoverText(73010, "Upgrade to Tier 1 Abyssal whip", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(73011, "Upgrade to Tier 1 Dark bow", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(73012, "Upgrade to Tier 1 Granite maul", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(73013, "Upgrade to Tier 1 Dragon dagger", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(73014, "Upgrade to Tier 1 Staff of light", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(74010, "Upgrade to Tier 1 Dragon scimitar", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(74011, "Upgrade to Tier 1 Dragon mace", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(74012, "Upgrade to Tier 1 Dragon longsword", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(74013, "Upgrade to Tier 1 Magic shortbow", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(74014, "Upgrade to Tier 1 Rune crossbow", "Upgrade", font, 1, 65280, false, true, 200);

        addSprite(74115, 1416);
        addText(74116, "+84 Slash", font,1,16777215);
        addSprite(74117, 1415);
        addText(74118, "+84 Melee strength", font,1,16777215);

        addSprite(74119, 1417);
        addText(74120, "+84 Range", font,1,16777215);
        addSprite(74121, 1417);
        addText(74122, "+84 Ranged strength", font,1,16777215);

        addSprite(74123, 1416);
        addText(74124, "+84 Crush", font,1,16777215);
        addSprite(74125, 1415);
        addText(74126, "+84 Melee strength", font,1,16777215);

        addSprite(74127, 1416);
        addText(74128, "+42 Stab, +27 Slash, -4 Crush", font,1,16777215);
        addSprite(74129, 1415);
        addText(74130, "+84 Melee strength", font,1,16777215);

        addSprite(74131, 1418);
        addText(74132, "+42 Magic", font,1,16777215);
        addSprite(74133, 1418);
        addText(74134, "Magic damage +10%", font,1,16777215);

        addSprite(74135, 1416);
        addText(74136, "+42 Stab, +27 Slash, -4 Crush", font,1,16777215);
        addSprite(74137, 1415);
        addText(74138, "+84 Melee strength", font,1,16777215);

        addSprite(74139, 1416);
        addText(74140, "+42 Stab, +27 Slash, -4 Crush", font,1,16777215);
        addSprite(74141, 1415);
        addText(74142, "+84 Melee strength", font,1,16777215);

        addSprite(74143, 1416);
        addText(74144, "+42 Stab, +27 Slash, -4 Crush", font,1,16777215);
        addSprite(74145, 1415);
        addText(74146, "+84 Melee strength", font,1,16777215);

        addSprite(74147, 1417);
        addText(74148, "+84 Range", font,1,16777215);
        addSprite(74149, 1417);
        addText(74150, "+84 Ranged strength", font,1,16777215);

        addSprite(74151, 1417);
        addText(74152, "+84 Range", font,1,16777215);
        addSprite(74153, 1417);
        addText(74154, "+84 Ranged strength", font,1,16777215);

        addItemUpgradeStation(73030);
        Widget itemSelectionOne = Widget.cache[73030];
        itemSelectionOne.inventoryMarginY = 75;

        itemSelectionOne.inventoryItemId[0] = 15442;
        itemSelectionOne.inventoryAmounts[0] = 1;

        itemSelectionOne.inventoryItemId[6] = 12766;
        itemSelectionOne.inventoryAmounts[6] = 1;

        itemSelectionOne.inventoryItemId[12] = 16201;
        itemSelectionOne.inventoryAmounts[12] = 1;

        itemSelectionOne.inventoryItemId[18] = 16210;
        itemSelectionOne.inventoryAmounts[18] = 1;

        itemSelectionOne.inventoryItemId[24] = 16269;
        itemSelectionOne.inventoryAmounts[24] = 1;

        addItemUpgradeStation(74030);
        Widget itemSelectionTwo = Widget.cache[74030];
        itemSelectionTwo.inventoryMarginY = 75;

        itemSelectionTwo.inventoryItemId[0] = 16237;
        itemSelectionTwo.inventoryAmounts[0] = 1;

        itemSelectionTwo.inventoryItemId[6] = 16255;
        itemSelectionTwo.inventoryAmounts[6] = 1;

        itemSelectionTwo.inventoryItemId[12] = 16246;
        itemSelectionTwo.inventoryAmounts[12] = 1;

        itemSelectionTwo.inventoryItemId[18] = 16219;
        itemSelectionTwo.inventoryAmounts[18] = 1;

        itemSelectionTwo.inventoryItemId[24] = 16228;
        itemSelectionTwo.inventoryAmounts[24] = 1;

        component.parent = 73008;
        component.id = 73008;
        component.optionType = 0;
        component.contentType = 0;
        component.width = 430;
        component.height = 220;
        component.opacity = 0;
        component.hoverType = 87;
        component.scrollMax = 1100;
        component.totalChildren(66);

        component.child(0, 73009, 50, 0);
        component.child(1, 73009, 50, 106);
        component.child(2, 73009, 50, 213);
        component.child(3, 73009, 50, 320);
        component.child(4, 73009, 50, 427);
        component.child(5, 73010, 130, 10);
        component.child(6, 73011, 130, 116);
        component.child(7, 73012, 130, 222);
        component.child(8, 73013, 130, 328);
        component.child(9, 73014, 130, 434);
        component.child(10, 73030, 73, 20);
        component.child(11, 73009, 50, 534);
        component.child(12, 73009, 50, 641);
        component.child(13, 73009, 50, 748);
        component.child(14, 73009, 50, 855);
        component.child(15, 73009, 50, 962);
        component.child(16, 74010, 130, 545);
        component.child(17, 74011, 130, 651);
        component.child(18, 74012, 130, 757);
        component.child(19, 74013, 130, 864);
        component.child(20, 74014, 130, 971);
        component.child(21, 74030, 73, 555);
        component.child(22, 74020, 150, 515);
        component.child(23, 74021, 150, 535);
        component.child(24, 74022, 150, 611);
        component.child(25, 74023, 150, 631);
        component.child(26, 74024, 150, 707);
        component.child(27, 74025, 150, 727);
        component.child(28, 74026, 150, 803);
        component.child(29, 74027, 150, 823);
        component.child(30, 74028, 150, 899);
        component.child(31, 74029, 150, 919);
        component.child(32, 74115, 130, 55);
        component.child(33, 74116, 150, 55);
        component.child(34, 74117, 130, 73);
        component.child(35, 74118, 150, 78);
        component.child(36, 74119, 130, 160);
        component.child(37, 74120, 150, 160);
        //component.child(38, 74121, 130, 180);
        //component.child(39, 74122, 153, 185);
        component.child(38, 74123, 130, 267);
        component.child(39, 74124, 150, 267);
        component.child(40, 74125, 130, 287);
        component.child(41, 74126, 150, 292);
        component.child(42, 74127, 130, 375);
        component.child(43, 74128, 150, 375);
        component.child(44, 74129, 130, 395);
        component.child(45, 74130, 150, 400);
        component.child(46, 74131, 130, 480);
        component.child(47, 74132, 150, 480);
        component.child(48, 74133, 130, 505);
        component.child(49, 74134, 150, 505);
        component.child(50, 74135, 130, 588);
        component.child(51, 74136, 150, 588);
        component.child(52, 74137, 130, 608);
        component.child(53, 74138, 150, 613);
        component.child(54, 74139, 130, 695);
        component.child(55, 74140, 150, 695);
        component.child(56, 74141, 130, 715);
        component.child(57, 74142, 150, 720);
        component.child(58, 74143, 130, 803);
        component.child(59, 74144, 150, 803);
        component.child(60, 74145, 130, 823);
        component.child(61, 74146, 150, 828);
        component.child(62, 74147, 130, 910);
        component.child(63, 74148, 150, 910);
        //component.child(66, 74149, 130, 928);
        //component.child(67, 74150, 155, 933);
        component.child(64, 74151, 130, 1015);
        component.child(65, 74152, 150, 1015);
        //component.child(70, 74153, 130, 1035);
        //component.child(71, 74154, 155, 1040);
    }
}

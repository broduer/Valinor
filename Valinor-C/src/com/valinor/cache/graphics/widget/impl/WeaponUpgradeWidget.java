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
        Widget widget = addInterface(71000);
        addSprite(71001, 1413);
        closeButton(71002, 142, 143, false);
        addText(71003, "Weapon Upgrading Service", font, 2, 16777215, true, false);
        widget.scrollMax = 0;

        widget.totalChildren(4);
        widget.child(0, 71001, 50, 50);//Background
        widget.child(1, 71002, 433, 56);//Close button
        widget.child(2, 71003, 250, 58);//Title
        widget.child(3, 71008, 7, 88);//components
    }

    private static void components(final AdvancedFont[] font) {
        Widget component = addInterface(71008);
        addSprite(71009, 1414);
        addHoverText(71010, "Upgrade to Tier 1 Abyssal whip", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(71011, "Upgrade to Tier 1 Dark bow", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(71012, "Upgrade to Tier 1 Granite maul", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(71013, "Upgrade to Tier 1 Dragon dagger", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(71014, "Upgrade to Tier 1 Staff of light", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(72010, "Upgrade to Tier 1 Dragon scimitar", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(72011, "Upgrade to Tier 1 Dragon mace", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(72012, "Upgrade to Tier 1 Dragon longsword", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(72013, "Upgrade to Tier 1 Magic shortbow", "Upgrade", font, 1, 65280, false, true, 200);
        addHoverText(72014, "Upgrade to Tier 1 Rune crossbow", "Upgrade", font, 1, 65280, false, true, 200);

        addSprite(72115, 1416);
        addText(72116, "+84 Slash", font,1,16777215);
        addSprite(72117, 1415);
        addText(72118, "+84 Melee strength", font,1,16777215);

        addSprite(72119, 1417);
        addText(72120, "+84 Range", font,1,16777215);
        addSprite(72121, 1417);
        addText(72122, "+84 Ranged strength", font,1,16777215);

        addSprite(72123, 1416);
        addText(72124, "+84 Crush", font,1,16777215);
        addSprite(72125, 1415);
        addText(72126, "+84 Melee strength", font,1,16777215);

        addSprite(72127, 1416);
        addText(72128, "+42 Stab, +27 Slash, -4 Crush", font,1,16777215);
        addSprite(72129, 1415);
        addText(72130, "+84 Melee strength", font,1,16777215);

        addSprite(72131, 1418);
        addText(72132, "+42 Magic", font,1,16777215);
        addSprite(72133, 1418);
        addText(72134, "Magic damage +10%", font,1,16777215);

        addSprite(72135, 1416);
        addText(72136, "+42 Stab, +27 Slash, -4 Crush", font,1,16777215);
        addSprite(72137, 1415);
        addText(72138, "+84 Melee strength", font,1,16777215);

        addSprite(72139, 1416);
        addText(72140, "+42 Stab, +27 Slash, -4 Crush", font,1,16777215);
        addSprite(72141, 1415);
        addText(72142, "+84 Melee strength", font,1,16777215);

        addSprite(72143, 1416);
        addText(72144, "+42 Stab, +27 Slash, -4 Crush", font,1,16777215);
        addSprite(72145, 1415);
        addText(72146, "+84 Melee strength", font,1,16777215);

        addSprite(72147, 1417);
        addText(72148, "+84 Range", font,1,16777215);
        addSprite(72149, 1417);
        addText(72150, "+84 Ranged strength", font,1,16777215);

        addSprite(72151, 1417);
        addText(72152, "+84 Range", font,1,16777215);
        addSprite(72153, 1417);
        addText(72154, "+84 Ranged strength", font,1,16777215);

        addItemUpgradeStation(71030);
        Widget itemSelectionOne = Widget.cache[71030];
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

        addItemUpgradeStation(72030);
        Widget itemSelectionTwo = Widget.cache[72030];
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

        component.parent = 71008;
        component.id = 71008;
        component.optionType = 0;
        component.contentType = 0;
        component.width = 430;
        component.height = 220;
        component.opacity = 0;
        component.hoverType = 87;
        component.scrollMax = 1100;
        component.totalChildren(66);

        component.child(0, 71009, 50, 0);
        component.child(1, 71009, 50, 106);
        component.child(2, 71009, 50, 213);
        component.child(3, 71009, 50, 320);
        component.child(4, 71009, 50, 427);
        component.child(5, 71010, 130, 10);
        component.child(6, 71011, 130, 116);
        component.child(7, 71012, 130, 222);
        component.child(8, 71013, 130, 328);
        component.child(9, 71014, 130, 434);
        component.child(10, 71030, 73, 20);
        component.child(11, 71009, 50, 534);
        component.child(12, 71009, 50, 641);
        component.child(13, 71009, 50, 748);
        component.child(14, 71009, 50, 855);
        component.child(15, 71009, 50, 962);
        component.child(16, 72010, 130, 545);
        component.child(17, 72011, 130, 651);
        component.child(18, 72012, 130, 757);
        component.child(19, 72013, 130, 864);
        component.child(20, 72014, 130, 971);
        component.child(21, 72030, 73, 555);
        component.child(22, 72020, 150, 515);
        component.child(23, 72021, 150, 535);
        component.child(24, 72022, 150, 611);
        component.child(25, 72023, 150, 631);
        component.child(26, 72024, 150, 707);
        component.child(27, 72025, 150, 727);
        component.child(28, 72026, 150, 803);
        component.child(29, 72027, 150, 823);
        component.child(30, 72028, 150, 899);
        component.child(31, 72029, 150, 919);
        component.child(32, 72115, 130, 55);
        component.child(33, 72116, 150, 55);
        component.child(34, 72117, 130, 73);
        component.child(35, 72118, 150, 78);
        component.child(36, 72119, 130, 160);
        component.child(37, 72120, 150, 160);
        //component.child(38, 72121, 130, 180);
        //component.child(39, 72122, 153, 185);
        component.child(38, 72123, 130, 267);
        component.child(39, 72124, 150, 267);
        component.child(40, 72125, 130, 287);
        component.child(41, 72126, 150, 292);
        component.child(42, 72127, 130, 375);
        component.child(43, 72128, 150, 375);
        component.child(44, 72129, 130, 395);
        component.child(45, 72130, 150, 400);
        component.child(46, 72131, 130, 480);
        component.child(47, 72132, 150, 480);
        component.child(48, 72133, 130, 505);
        component.child(49, 72134, 150, 505);
        component.child(50, 72135, 130, 588);
        component.child(51, 72136, 150, 588);
        component.child(52, 72137, 130, 608);
        component.child(53, 72138, 150, 613);
        component.child(54, 72139, 130, 695);
        component.child(55, 72140, 150, 695);
        component.child(56, 72141, 130, 715);
        component.child(57, 72142, 150, 720);
        component.child(58, 72143, 130, 803);
        component.child(59, 72144, 150, 803);
        component.child(60, 72145, 130, 823);
        component.child(61, 72146, 150, 828);
        component.child(62, 72147, 130, 910);
        component.child(63, 72148, 150, 910);
        //component.child(66, 72149, 130, 928);
        //component.child(67, 72150, 155, 933);
        component.child(64, 72151, 130, 1015);
        component.child(65, 72152, 150, 1015);
        //component.child(70, 72153, 130, 1035);
        //component.child(71, 72154, 155, 1040);
    }
}

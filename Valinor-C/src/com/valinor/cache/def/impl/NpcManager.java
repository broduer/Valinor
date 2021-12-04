package com.valinor.cache.def.impl;

import com.valinor.ClientConstants;
import com.valinor.cache.def.NpcDefinition;

import static com.valinor.util.NpcIdentifiers.*;

public class NpcManager {

    public static void unpack(int id) {
        NpcDefinition definition = NpcDefinition.get(id);
        boolean impling = false;

        switch (id) {

            case 11112:
                definition.name = "Santa";
                definition.models = new int[]{235, 189, 299, 4226, 4218, 162, 4924, 4925, 4926};
                definition.actions = new String[]{"Talk-to", null, null, null, null};
                definition.additionalModels = new int[]{7380, 69, 53};//Beard, santa hat, head
                definition.description = "Also known as Father christmas.";
                definition.walkingAnimation = 819;
                definition.rotate180Animation = 819;
                definition.rotate90LeftAnimation = 819;
                definition.rotate90RightAnimation = 819;
                definition.standingAnimation = 808;
                definition.recolorFrom = new int[]{6798, 8078, 8741, 25238, 6587, 5400};
                definition.recolorTo = new int[]{86, 10351, 933, 933, 10351, 0};
                break;

            case 3443:
                definition.name = "Lord voldemort";
                definition.combatLevel = 1433;
                definition.actions = new String[] {null, "Attack", null, null, null, null};
                break;

            case 1922:
                definition.actions = new String[] {"Talk-to", null, "Lost-items", null, null, null};
                break;

            case 7632:
                definition.name = "Men in black";
                definition.combatLevel = 80;
                definition.actions = new String[] {null, "Attack", null, null, null, null};
                break;

            case 317:
                definition.actions = new String[]{"Talk-to", null, "Boss-points", null, null};
                break;

            case 2149:
                definition.actions = new String[]{"Exchange", null, null, null, null};
                break;

            // Shura
            case 9413:
                definition.name = "<col=00ACFF>Referral Manager</col>";
                definition.actions = new String[]{"Talk-to", null, null, null, null};
                definition.combatLevel = 0;
                definition.models = new int[] {39207};
                definition.additionalModels = new int[] {39485};
                definition.standingAnimation = 8551;
                definition.drawMapDot = true;
                definition.walkingAnimation = 8552;
                break;

            case 15016:
            case 15021:
            case 15019:
            case SHOP_ASSISTANT_2820:
            case GRUM_2889:
                definition.isClickable = true;
                break;
            case THORODIN_5526:
                definition.name = "Boss slayer master";
                definition.actions = new String[]{"Talk-to", null, "Slayer-Equipment", "Slayer-Rewards", null};
                break;
            case TRAIBORN:
            case GUNNJORN:
                definition.isClickable = true;
                definition.actions = new String[] {"Weapons", null, "Armour", "Ironman", null};
                break;
            case TWIGGY_OKORN:
                definition.actions = new String[]{"Talk-to", null, "Claim-cape", null, null};
                break;

            case FANCY_DAN:
                definition.name = "Vote Manager";
                definition.actions[0] = "Trade";
                definition.actions[2] = "Cast-votes";
                break;

            case WISE_OLD_MAN:
                definition.name = "Credit Manager";
                definition.actions[0] = "Talk-to";
                definition.actions[2] = "Open-Shop";
                definition.actions[3] = "Claim-purchases";
                break;

            case SECURITY_GUARD:
                definition.name = "Security Advisor";
                definition.actions[0] = "Check Pin Settings";
                break;

            case SIGMUND_THE_MERCHANT:
                definition.actions[0] = "Buy-items";
                definition.actions[2] = "Sell-items";
                definition.actions[3] = "Sets";
                definition.actions[4] = null;
                break;

            case MAKEOVER_MAGE_1307:
                definition.actions[0] = "Change-looks";
                definition.actions[2] = "Title-unlocks";
                definition.actions[3] = null;
                definition.actions[4] = null;
                break;

            case GRAND_EXCHANGE_CLERK:
                definition.actions[0] = "Exchange";
                definition.actions[2] = null;
                definition.actions[3] = null;
                definition.actions[4] = null;
                break;

            case FRANK:
                definition.name = "Shop";
                definition.actions[0] = "Untradeable";
                break;

            case RADIGAD_PONFIT:
                definition.name = "Ranged Shop";
                definition.actions = new String[] {"Weapons", null, "Armour", "Ironman", null};
                break;

            case CLAUS_THE_CHEF:
                definition.name = "Shop";
                definition.actions[0] = "Consumable";
                break;

            case SPICE_SELLER_4579:
                definition.name = "Shop";
                definition.actions[0] = "Misc";
                break;

            case LISA:
                definition.name = "Tournament Manager";
                definition.actions = new String[]{"Sign-up", null, "Quick-join", "Quick-spectate", null, null, null};
                break;

            case VANNAKA:
                definition.name = "Task master";
                definition.actions = new String[]{"Talk-to", null, "Progress", null, null};
                break;

            case COMBAT_DUMMY:
                definition.actions = new String[]{null, "Attack", null, null, null};
                definition.hasRenderPriority = true;
                break;
            case ROCK_CRAB:
            case ROCK_CRAB_102:
                definition.hasRenderPriority = true;
                break;
            case 6481:
                definition.actions = new String[]{"Talk-to", null, "Skillcape", null, null, null, null};
                break;
            case 1635:
            case 1636:
            case 1637:
            case 1638:
            case 1639:
            case 1640:
            case 1641:
            case 1642:
            case 1643:
            case 1644:
            case 7233:
                impling = true;
                break;

            // tournament guy
            case 8146:
                definition.actions = new String[]{"Tournament", null, "Quick-join", "Quick-spectate", null, null, null};
                break;
            // emblem trader
            case 308:
                definition.actions = new String[]{"Talk-to", null, "Trade", null, null, null, null};
                break;

            // Sigbert the Adventurer
            case 3254:
                definition.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 5979:
                definition.name = "Weapon Upgrader";
                break;

            case 306:
                definition.name = ClientConstants.CLIENT_NAME + " Guide";
                break;
            // Zombies Champion
            case 3359:
                definition.combatLevel = 785;
                break;
            //Vorkath
            case 319:
            case 8061:
                definition.largeHpBar = true;
                break;
        }

        if (definition != null && impling) {
            definition.isClickable = true;
        }
    }
}

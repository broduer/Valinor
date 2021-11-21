package com.valinor.cache.def.impl;

import com.valinor.ClientConstants;
import com.valinor.cache.def.NpcDefinition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.valinor.util.NpcIdentifiers.*;

public class NpcManager {

    public static void unpack(int id) {
        NpcDefinition definition = NpcDefinition.get(id);
        boolean impling = false;

        switch (id) {

            case 3443:
                definition.name = "Lord voldemort";
                definition.cmb_level = 1433;
                definition.actions = new String[] {null, "Attack", null, null, null, null};
                break;

            case 1922:
                definition.actions = new String[] {"Talk-to", null, "Lost-items", null, null, null};
                break;

            case 7632:
                definition.name = "Men in black";
                definition.cmb_level = 80;
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
                definition.cmb_level = 0;
                definition.model_id = new int[] {39207};
                definition.additionalModels = new int[] {39485};
                definition.standingAnimation = 8551;
                definition.renderOnMinimap = true;
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
                definition.render_priority = true;
                break;
            case ROCK_CRAB:
            case ROCK_CRAB_102:
                definition.render_priority = true;
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
            case 1654:
            case 7233:
            case 7302:
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
                definition.cmb_level = 785;
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

package com.valinor.game.content.areas.wilderness.content.wilderness_activity;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.WILDY_ACTIVITY_CASKET;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 30, 2022
 */
public class WildyActivityCasket extends Interaction {

    private static final List<Item> COMMON = Arrays.asList(
        new Item(DRAGON_CROSSBOW),
        new Item(DRAGON_HUNTER_CROSSBOW),
        new Item(BANDOS_GODSWORD),
        new Item(SARADOMIN_GODSWORD),
        new Item(ZAMORAK_GODSWORD),
        new Item(LIGHT_BALLISTA),
        new Item(STAFF_OF_THE_DEAD),
        new Item(SUPER_COMBAT_POTION4+1, 50),
        new Item(ANGLERFISH+1,100),
        new Item(ANTIVENOM4+1,100),
        new Item(ETERNAL_BOOTS),
        new Item(PEGASIAN_BOOTS),
        new Item(PRIMORDIAL_BOOTS),
        new Item(STAFF_OF_THE_DEAD),
        new Item(DRAGONFIRE_SHIELD),
        new Item(ARMADYL_HELMET),
        new Item(ARMADYL_CHESTPLATE),
        new Item(ARMADYL_CHAINSKIRT),
        new Item(BANDOS_CHESTPLATE),
        new Item(BANDOS_TASSETS),
        new Item(DRAGONFIRE_WARD),
        new Item(SERPENTINE_HELM),
        new Item(ANCIENT_WYVERN_SHIELD),
        new Item(SPECTRAL_SPIRIT_SHIELD),
        new Item(ARMADYL_CROSSBOW),
        new Item(DRAGON_HUNTER_CROSSBOW)
    );

    private static final List<Item> UNCOMMON = Arrays.asList(
        new Item(ARMADYL_GODSWORD),
        new Item(HEAVY_BALLISTA),
        new Item(DRAGON_CLAWS),
        new Item(SERPENTINE_HELM),
        new Item(TOXIC_STAFF_OF_THE_DEAD),
        new Item(ARCANE_SPIRIT_SHIELD),
        new Item(DINHS_BULWARK),
        new Item(ARCANE_SPIRIT_SHIELD),
        new Item(NEITIZNOT_FACEGUARD),
        new Item(MORRIGANS_COIF),
        new Item(MORRIGANS_LEATHER_BODY),
        new Item(MORRIGANS_LEATHER_CHAPS),
        new Item(ZURIELS_HOOD),
        new Item(ZURIELS_ROBE_TOP),
        new Item(ZURIELS_ROBE_BOTTOM),
        new Item(VESTAS_SPEAR),
        new Item(VESTAS_LONGSWORD),
        new Item(TOXIC_BLOWPIPE),
        new Item(DRAGON_WARHAMMER)
    );

    private static final List<Item> RARE = Arrays.asList(
        new Item(GHRAZI_RAPIER),
        new Item(ELDER_MAUL),
        new Item(SANGUINESTI_STAFF),
        new Item(ELYSIAN_SPIRIT_SHIELD),
        new Item(STATIUSS_FULL_HELM),
        new Item(STATIUSS_PLATEBODY),
        new Item(STATIUSS_PLATELEGS),
        new Item(VESTAS_CHAINBODY),
        new Item(VESTAS_PLATESKIRT),
        new Item(ANCESTRAL_HAT),
        new Item(ANCESTRAL_ROBE_TOP),
        new Item(ANCESTRAL_ROBE_BOTTOM),
        new Item(NIGHTMARE_STAFF),
        new Item(INQUISITORS_GREAT_HELM),
        new Item(INQUISITORS_HAUBERK),
        new Item(INQUISITORS_PLATESKIRT)
    );

    private void open(Player player) {
        if(!player.inventory().contains(WILDY_ACTIVITY_CASKET))
            return;

        player.inventory().remove(new Item(WILDY_ACTIVITY_CASKET), true);

        List<Item> items;
        if (World.getWorld().rollDie(20, 1)) {
            items = RARE;
        } else if (World.getWorld().rollDie(10, 1)) {
            items = UNCOMMON;
        } else {
            items = COMMON;
        }

        Item item = Utils.randomElement(items);
        player.inventory().addOrDrop(item);

        boolean amOverOne = item.getAmount() > 1;
        String amtString = amOverOne ? "" + Utils.format(item.getAmount()) + "x" : Utils.getAOrAn(item.name());

        player.message("You open the casket and find...");
        player.message("And find... "+Color.HOTPINK.tag()+""+amtString+" "+item.unnote().name()+"</col>.");
        if (items == RARE) {
            //The user box test doesn't yell.
            if(player.getUsername().equalsIgnoreCase("Box test")) {
                return;
            }
            String msg = "<img=1081><shad=0>" + player.getUsername() + " has received "+Color.HOTPINK.tag()+"" + amtString + " " + item.name() + "</col> from a Activity casket!";
            World.getWorld().sendWorldMessage(msg);
        }
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == WILDY_ACTIVITY_CASKET) {
                open(player);
                return true;
            }
        }
        return false;
    }
}

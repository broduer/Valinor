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
        new Item(BANDOS_GODSWORD),
        new Item(LIGHT_BALLISTA),
        new Item(STAFF_OF_THE_DEAD),
        new Item(SUPER_COMBAT_POTION4+1, 50),
        new Item(ANGLERFISH+1,100),
        new Item(ANTIVENOM4+1,50),
        new Item(ANTIVENOM4_12913+1,25),
        new Item(DIVINE_SUPER_COMBAT_POTION4+1,35),
        new Item(BASTION_POTION4+1,35),
        new Item(BATTLEMAGE_POTION4+1,35),
        new Item(STAMINA_POTION4+1,35),
        new Item(OPAL_DRAGON_BOLTS_E,100),
        new Item(JADE_DRAGON_BOLTS_E,100),
        new Item(PEARL_DRAGON_BOLTS_E,100),
        new Item(TOPAZ_DRAGON_BOLTS_E,100),
        new Item(SAPPHIRE_DRAGON_BOLTS_E,100),
        new Item(EMERALD_DRAGON_BOLTS_E,100),
        new Item(RUBY_DRAGON_BOLTS_E,100),
        new Item(DIAMOND_DRAGON_BOLTS_E,100),
        new Item(DRAGONSTONE_DRAGON_BOLTS_E,100),
        new Item(ONYX_DRAGON_BOLTS_E,100),
        new Item(WRATH_RUNE,100),
        new Item(DHAROKS_ARMOUR_SET,1),
        new Item(AHRIMS_ARMOUR_SET,1),
        new Item(KARILS_ARMOUR_SET,1),
        new Item(ABYSSAL_WHIP,1),
        new Item(AMULET_OF_FURY,1),
        new Item(DRAGON_BOOTS,1),
        new Item(FIGHTER_TORSO,1),
        new Item(FIGHTER_HAT,1),
        new Item(FIGHTER_TORSO,1),
        new Item(BERSERKER_RING,1),
        new Item(WARRIOR_RING,1),
        new Item(SEERS_RING,1),
        new Item(ARCHERS_RING,1),
        new Item(RUNE_POUCH,1),
        new Item(ABYSSAL_TENTACLE,1),
        new Item(OCCULT_NECKLACE,1)
    );

    private static final List<Item> UNCOMMON = Arrays.asList(
        new Item(ARMADYL_CROSSBOW),
        new Item(PRIMORDIAL_BOOTS),
        new Item(ETERNAL_BOOTS),
        new Item(PEGASIAN_BOOTS),
        new Item(ARMADYL_HELMET),
        new Item(ARMADYL_CHESTPLATE),
        new Item(ARMADYL_CHAINSKIRT),
        new Item(BANDOS_CHESTPLATE),
        new Item(BANDOS_TASSETS),
        new Item(DRAGONFIRE_WARD),
        new Item(SERPENTINE_HELM),
        new Item(ARMADYL_GODSWORD),
        new Item(HEAVY_BALLISTA),
        new Item(DRAGON_CLAWS),
        new Item(TOXIC_STAFF_OF_THE_DEAD),
        new Item(DINHS_BULWARK),
        new Item(NEITIZNOT_FACEGUARD)
    );

    private static final List<Item> RARE = Arrays.asList(
        new Item(STATIUSS_WARHAMMER),
        new Item(VESTAS_SPEAR),
        new Item(VESTAS_LONGSWORD),
        new Item(STATIUSS_FULL_HELM),
        new Item(STATIUSS_PLATEBODY),
        new Item(STATIUSS_PLATELEGS),
        new Item(VESTAS_CHAINBODY),
        new Item(VESTAS_PLATESKIRT),
        new Item(MORRIGANS_COIF),
        new Item(MORRIGANS_LEATHER_BODY),
        new Item(MORRIGANS_LEATHER_CHAPS),
        new Item(ZURIELS_STAFF),
        new Item(ZURIELS_HOOD),
        new Item(ZURIELS_ROBE_TOP),
        new Item(ZURIELS_ROBE_BOTTOM)
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
            String msg = "<img=452><shad=0>" + player.getUsername() + " has received "+Color.HOTPINK.tag()+"" + amtString + " " + item.name() + "</col> from a Activity casket!";
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

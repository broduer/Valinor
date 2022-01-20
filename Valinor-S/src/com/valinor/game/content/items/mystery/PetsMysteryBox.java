package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.Utils;

import static com.valinor.game.content.collection_logs.LogType.MYSTERY_BOX;
import static com.valinor.util.CustomItemIdentifiers.PETS_MYSTERY_BOX;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 20, 2022
 */
public class PetsMysteryBox extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == PETS_MYSTERY_BOX) {
                open(player);
                return true;
            }
        }
        return false;
    }

    public static Item rollReward() {
        if (World.getWorld().rollDie(5, 1)) {
            return Utils.randomElement(RARE);
        } else if (World.getWorld().rollDie(3, 1)) {
            return Utils.randomElement(UNCOMMON);
        } else {
            return Utils.randomElement(COMMON);
        }
    }

    public static void open(Player player) {
        if(player.inventory().contains(PETS_MYSTERY_BOX)) {
            player.inventory().remove(new Item(PETS_MYSTERY_BOX),true);
            Item reward = rollReward();
            Utils.sendDiscordInfoLog("Player " + player.getUsername() + " received a "+reward.unnote().name()+" from a mystery box.", "box_and_tickets");
            MYSTERY_BOX.log(player, PETS_MYSTERY_BOX, reward);
            player.inventory().addOrBank(reward);
            int count = player.<Integer>getAttribOr(AttributeKey.PETS_MYSTERY_BOXES_OPENED,0) + 1;
            player.putAttrib(AttributeKey.PETS_MYSTERY_BOXES_OPENED, count);
            //The user box test doesn't yell.
            if(player.getUsername().equalsIgnoreCase("Box test")) {
                return;
            }
            String worldMessage = "<img=505><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Pets mystery box</col>]</shad>:<col=AD800F> " + player.getUsername() + " received a <shad=0>" + reward.name() + "</shad>!";
            World.getWorld().sendWorldMessage(worldMessage);
        }
    }

    private static final Item[] RARE = new Item[] {
        new Item(CustomItemIdentifiers.JAWA_PET),
        new Item(CustomItemIdentifiers.ZRIAWK),
        new Item(CustomItemIdentifiers.FAWKES),
        new Item(CustomItemIdentifiers.BABY_ARAGOG),
        new Item(CustomItemIdentifiers.BLOOD_REAPER),
        new Item(ItemIdentifiers.YOUNGLLEF),
        new Item(CustomItemIdentifiers.MINI_NECROMANCER),
        new Item(CustomItemIdentifiers.ARTIO_PET),
        new Item(CustomItemIdentifiers.ANCIENT_BARRELCHEST_PET),
        new Item(CustomItemIdentifiers.PET_CORRUPTED_NECHRYARCH)
    };

    private static final Item[] UNCOMMON = new Item[] {
        new Item(CustomItemIdentifiers.GRIM_REAPER_PET),
        new Item(CustomItemIdentifiers.NIFFLER),
        new Item(CustomItemIdentifiers.WAMPA_PET),
        new Item(CustomItemIdentifiers.KERBEROS_PET),
        new Item(CustomItemIdentifiers.SKORPIOS_PET),
        new Item(CustomItemIdentifiers.ARACHNE_PET),
        new Item(CustomItemIdentifiers.DEMENTOR_PET),
        new Item(CustomItemIdentifiers.FENRIR_GREYBACK_JR),
        new Item(CustomItemIdentifiers.FLUFFY_JR),
        new Item(CustomItemIdentifiers.ANCIENT_KING_BLACK_DRAGON_PET),
        new Item(CustomItemIdentifiers.ANCIENT_CHAOS_ELEMENTAL_PET),
        new Item(CustomItemIdentifiers.FOUNDER_IMP),
        new Item(CustomItemIdentifiers.BABY_LAVA_DRAGON),
        new Item(CustomItemIdentifiers.JALTOK_JAD)
    };

    private static final Item[] COMMON = new Item[] {
        new Item(CustomItemIdentifiers.RING_OF_ELYSIAN),
        new Item(CustomItemIdentifiers.GENIE_PET),
        new Item(CustomItemIdentifiers.DHAROK_PET),
        new Item(CustomItemIdentifiers.ZOMBIES_CHAMPION_PET),
        new Item(CustomItemIdentifiers.BABY_ABYSSAL_DEMON),
        new Item(CustomItemIdentifiers.BABY_DARK_BEAST_EGG),
        new Item(CustomItemIdentifiers.BABY_SQUIRT),
        new Item(CustomItemIdentifiers.CENTAUR_FEMALE),
        new Item(CustomItemIdentifiers.CENTAUR_MALE)
    };
}

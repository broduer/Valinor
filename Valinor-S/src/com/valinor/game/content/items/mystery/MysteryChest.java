package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.game.content.collection_logs.LogType.MYSTERY_BOX;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | March, 21, 2021, 23:49
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class MysteryChest extends Interaction {

    //1/10
    private final List<Item> RARE_REWARDS = Arrays.asList(
        new Item(ELYSIAN_SPIRIT_SHIELD), new Item(TWISTED_BOW), new Item(SCYTHE_OF_VITUR), new Item(DARK_BANDOS_CHESTPLATE), new Item(DARK_BANDOS_TASSETS), new Item(BLADE_OF_SAELDOR_C_25882),
        new Item(ANCESTRAL_ROBE_BOTTOM_I), new Item(ANCESTRAL_ROBE_TOP_I), new Item(SALAZAR_SLYTHERINS_LOCKET));

    //1/5
    private final List<Item> UNCOMMON_REWARDS = Arrays.asList(
        new Item(CORRUPTED_BOOTS), new Item(RING_OF_TRINITY), new Item(ANCIENT_WARRIOR_AXE_C), new Item(ANCIENT_WARRIOR_MAUL_C), new Item(ANCIENT_WARRIOR_SWORD_C),
        new Item(DARK_ARMADYL_CHESTPLATE), new Item(TOM_RIDDLE_DIARY), new Item(CLOAK_OF_INVISIBILITY), new Item(DARK_ARMADYL_CHAINSKIRT));

    //rolls under 5
    private final List<Item> COMMON_REWARDS = Arrays.asList(
        new Item(BOW_OF_FAERDHINEN), new Item(DARK_ARMADYL_HELMET), new Item(ELDER_WAND_HANDLE), new Item(ELDER_WAND_STICK), new Item(SWORD_OF_GRYFFINDOR), new Item(TALONHAWK_CROSSBOW), new Item(ANCESTRAL_HAT_I), new Item(LAVA_DHIDE_BODY),
        new Item(LAVA_DHIDE_CHAPS), new Item(LAVA_DHIDE_COIF));


    private void rollForReward(Player player) {
        Item reward;
        //Roll for a random item
        reward = rewardRoll();
        MYSTERY_BOX.log(player, MYSTERY_CHEST, reward);
        player.inventory().addOrBank(reward);
        String chest = "Mystery chest";
        var opened = player.<Integer>getAttribOr(AttributeKey.MYSTERY_CHESTS_OPENED, 0) + 1;
        player.putAttrib(AttributeKey.MYSTERY_CHESTS_OPENED, opened);
        if (reward != null) {
            Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a mystery chest and received "+reward.unnote().name()+".", "boxes_opened");
            //The user box test doesn't yell.
            if (player.getUsername().equalsIgnoreCase("Box test")) {
                return;
            }
            String worldMessage = "<img=505><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">" + chest + "</col>]</shad>:<col=AD800F> " + player.getUsername() + " received a <shad=0>" + reward.name() + "</shad>!";
            World.getWorld().sendWorldMessage(worldMessage);
        }
    }

    private static final int RARE_ROLL = 10;
    private static final int UNCOMMON_ROLL = 5;

    private Item rewardRoll() {
        if (Utils.rollDie(RARE_ROLL, 1)) {
            return Utils.randomElement(RARE_REWARDS);
        } else if (Utils.rollDie(UNCOMMON_ROLL, 1)) {
            return Utils.randomElement(UNCOMMON_REWARDS);
        } else {
            return Utils.randomElement(COMMON_REWARDS);
        }
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == MYSTERY_CHEST) {
                //Safety make sure people aren't spoofing using cheat clients
                if (player.inventory().contains(MYSTERY_CHEST)) {
                    player.inventory().remove(new Item(MYSTERY_CHEST), true);
                    rollForReward(player);
                }
            }
            if(item.getId() == HWEEN_MYSTERY_CHEST) {
                //Safety make sure people aren't spoofing using cheat clients
                if (player.inventory().contains(HWEEN_MYSTERY_CHEST)) {
                    player.inventory().remove(new Item(HWEEN_MYSTERY_CHEST), true);
                    rollForReward(player);
                }
                return true;
            }
        }
        return false;
    }
}

package com.valinor.game.content.areas.wilderness.content.key;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.skull.SkullType;
import com.valinor.game.world.entity.combat.skull.Skulling;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import static com.valinor.game.content.collection_logs.LogType.KEYS;
import static com.valinor.util.CustomItemIdentifiers.RUNE_POUCH_I;
import static com.valinor.util.CustomItemIdentifiers.WILDERNESS_KEY;

import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.DEADMAN_CHEST;
import static com.valinor.util.ObjectIdentifiers.DEADMAN_CHEST_27290;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 30, 2022
 */
public class WildernessKeyChest extends Interaction {

    private static final Item[] EXTREMELY_RARE = new Item[]{
        new Item(VESTAS_LONGSWORD), new Item(VESTAS_SPEAR), new Item(VESTAS_CHAINBODY), new Item(VESTAS_PLATESKIRT), new Item(STATIUSS_WARHAMMER), new Item(STATIUSS_PLATEBODY), new Item(STATIUSS_PLATELEGS), new Item(STATIUSS_FULL_HELM), new Item(MORRIGANS_COIF), new Item(MORRIGANS_LEATHER_BODY), new Item(MORRIGANS_LEATHER_CHAPS), new Item(ZURIELS_STAFF), new Item(ZURIELS_HOOD), new Item(ZURIELS_ROBE_TOP), new Item(ZURIELS_ROBE_BOTTOM)
    };

    private static final Item[] RARE = new Item[]{
        new Item(AMULET_OF_TORTURE), new Item(NECKLACE_OF_ANGUISH), new Item(HEAVY_BALLISTA), new Item(LIGHT_BALLISTA), new Item(NEITIZNOT_FACEGUARD), new Item(PRIMORDIAL_BOOTS), new Item(PEGASIAN_BOOTS), new Item(ETERNAL_BOOTS), new Item(IMBUED_HEART), new Item(DRAGON_CLAWS), new Item(ARMADYL_GODSWORD),
        new Item(STAFF_OF_THE_DEAD), new Item(STAFF_OF_LIGHT), new Item(TOXIC_STAFF_OF_THE_DEAD),
    };

    private static final Item[] UNCOMMON = new Item[]{
        new Item(MORRIGANS_JAVELIN, 25), new Item(MORRIGANS_THROWING_AXE,25), new Item(ABYSSAL_TENTACLE), new Item(TARGET_TELEPORT_SCROLL), new Item(AMULET_OF_FURY), new Item(OCCULT_NECKLACE), new Item(TOME_OF_FIRE), new Item(DRAGON_BOOTS), new Item(BERSERKER_RING_I), new Item(SEERS_RING_I), new Item(ARCHERS_RING_I), new Item(WARRIOR_RING_I), new Item(WARD_UPGRADE_KIT), new Item(RUNE_POUCH_I)
    };

    private static final Item[] COMMON = new Item[]{
        new Item(DARK_BOW), new Item(ABYSSAL_WHIP), new Item(DHAROKS_ARMOUR_SET), new Item(KARILS_ARMOUR_SET), new Item(AHRIMS_ARMOUR_SET), new Item(INFINITY_BOOTS), new Item(MAGES_BOOK), new Item(DIVINE_SUPER_COMBAT_POTION4+1, 25), new Item(DIVINE_BASTION_POTION4+1, 25), new Item(DIVINE_BATTLEMAGE_POTION4+1, 25), new Item(DRAGONSTONE_DRAGON_BOLTS_E, 100), new Item(RUBY_DRAGON_BOLTS_E, 100), new Item(DIAMOND_DRAGON_BOLTS_E, 100), new Item(PEARL_DRAGON_BOLTS_E, 100), new Item(ONYX_DRAGON_BOLTS_E, 100), new Item(ANGLERFISH+1, 100), new Item(COOKED_KARAMBWAN+1, 100),
        new Item(STAMINA_POTION4+1, 35), new Item(WRATH_RUNE, 100)
    };

    public Item rollReward() {
        var roll = World.getWorld().random(100);
        //Reward rarity to base the server message on
        if (roll >= 95 && roll <= 100) {
            return Utils.randomElement(EXTREMELY_RARE);
        } else if (roll >= 75 && roll <= 94) {
            return Utils.randomElement(RARE);
        } else if (roll >= 35 && roll <= 74) {
            return Utils.randomElement(UNCOMMON);
        } else {
            return Utils.randomElement(COMMON);
        }
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        //Only perform actions if the object is a deadman chest and the option is 1.
        if (option == 1 && object.getId() == DEADMAN_CHEST || object.getId() == DEADMAN_CHEST_27290) {
            //Do stuff here
            player.faceObj(object);

            if (!player.inventory().contains(WILDERNESS_KEY)) {
                player.message("You need a wilderness key to open this chest.");
                return true;
            }

            if(player.tile().region() == 9370 && !player.getMemberRights().isDiamondMemberOrGreater(player)) {
                player.message("Only diamond members can loot this chest.");
                return true;
            }

            //Important to lock the player during the action
            player.lock();

            Item rewardOne = rollReward();
            Item rewardTwo = rollReward();

            player.runFn(1, () -> {
                //Generate reward
                if(object.getId() == DEADMAN_CHEST_27290 && object.tile().equals(2337, 9904,1)) {
                    player.confirmDialogue(new String[]{"Are you sure you wish to open the chest?", "You will be " + Color.RED.wrap("red") + " skulled and teleblocked if you proceed."}, "", "proceed to open the chest.", "Nevermind.", () -> {
                        if(!player.inventory().contains(WILDERNESS_KEY)) {
                            return;
                        }
                        open(player, rewardOne, rewardTwo,true);
                    });
                    return;
                }

                open(player, rewardOne,null,false);
            });
            return true;
        }
        return false;
    }

    private void open(Player player, Item rewardOne, Item rewardTwo, boolean wilderness) {
        player.message("You unlock the chest with your key.");
        player.sound(51);
        player.animate(536);
        player.inventory().remove(new Item(WILDERNESS_KEY));

        if(wilderness) {
            player.teleblock(250,true);
            Skulling.assignSkullState(player, SkullType.RED_SKULL);
        }

        int keysUsed = player.<Integer>getAttribOr(AttributeKey.WILDY_KEYS_OPENED, 0) + 1;
        player.putAttrib(AttributeKey.WILDY_KEYS_OPENED, keysUsed);

        if(rewardOne != null) {
            boolean amOverOne = rewardOne.getAmount() > 1;
            String amtString = amOverOne ? "x" + Utils.format(rewardOne.getAmount()) + "" : Utils.getAOrAn(rewardOne.name());
            String openedAt = wilderness ? "inside the member zone" : "at home";

            if(!player.getUsername().equalsIgnoreCase("Box test")) {
                String msg = "<img=506><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Wildy key</col>]</shad>: " + "<col=AD800F>" + player.getUsername() + " received " + amtString + " " + rewardOne.name() + " " + openedAt + "! Keys opened ("+keysUsed+")";
                World.getWorld().sendWorldMessage(msg);
            }

            //Check if item exists in collection log items
            KEYS.log(player, WILDERNESS_KEY, rewardOne);

            if(wilderness) {
                player.inventory().addOrDrop(rewardOne);
            } else {
                player.inventory().addOrBank(rewardOne);
            }
        }

        if(rewardTwo != null) {
            boolean amOverOne = rewardTwo.getAmount() > 1;
            String amtString = amOverOne ? "x " + Utils.format(rewardTwo.getAmount()) + "" : Utils.getAOrAn(rewardTwo.name());
            String openedAt = wilderness ? "inside the member zone" : "at home";

            //The user box test doesn't yell.
            if(!player.getUsername().equalsIgnoreCase("Box test")) {
                String msg = "<img=506><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Wildy key</col>]</shad>: " + "<col=AD800F>" + player.getUsername() + " has received " + amtString + " " + rewardTwo.name() + " " + openedAt + "!";
                World.getWorld().sendWorldMessage(msg);
            }

            //Check if item exists in collection log items
            KEYS.log(player, WILDERNESS_KEY, rewardTwo);

            if(wilderness) {
                player.inventory().addOrDrop(rewardTwo);
            } else {
                player.inventory().addOrBank(rewardTwo);
            }
        }

        //And unlock the player
        player.unlock();

        AchievementsManager.activate(player, Achievements.WILDY_KEY_I, 1);
        AchievementsManager.activate(player, Achievements.WILDY_KEY_II, 1);
        AchievementsManager.activate(player, Achievements.WILDY_KEY_III, 1);
    }
}

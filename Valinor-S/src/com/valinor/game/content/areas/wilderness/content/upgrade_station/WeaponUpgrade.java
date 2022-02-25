package com.valinor.game.content.areas.wilderness.content.upgrade_station;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.packet_actions.interactions.items.ItemOnItem;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import static com.valinor.game.world.entity.AttributeKey.WEAPON_UPGRADES;
import static com.valinor.util.CustomItemIdentifiers.PKP_TICKET;

/**
 * This class manages the upgrading process.
 *
 * @author Zerikoth
 * @Since oktober 07, 2020
 */
public class WeaponUpgrade {

    private final Player player;

    public WeaponUpgrade(Player player) {
        this.player = player;
    }

    public void open() {
        player.getInterfaceManager().open(71000);

        for (Upgrades upgrade : Upgrades.values()) {

            // to navigate tiers i had to use the enum name since theyre all int he same enum, a future improvement
            // might be to use class instead of enum so you can group it all like upgrade(whip, t1-5) uphgrade(dds, t1-5) anyway
            int tier = switch (upgrade.name().substring(upgrade.name().indexOf("TIER_") + 5).trim()) {
                case "ONE" -> 1;
                case "TWO" -> 2;
                case "THREE" -> 3;
                case "FOUR" -> 4;
                case "FIVE" -> 5;
                default -> throw new IllegalStateException("Unexpected value: " + upgrade.name().substring(upgrade.name().indexOf("TIER_") + 5).trim());
            };
            int myTier = player.getAttribOr(upgrade.tierKey, 0);
            if (myTier > tier)
                continue;
            if (tier > 1 + myTier)
                continue;

            int currentKills = player.getAttribOr(upgrade.upgradeKey, 0);

            final String locked = Color.RED.tag() + "[LOCKED] Requires " + upgrade.requiredKillsToUpgrade + " " + upgrade.name + " Kills";
            final String unlocked = Color.GREEN.tag() + "Upgrade to a " + upgrade.tier + " " + upgrade.name;

            // send locked / unlocked string with # of kills for next tier to unlock
            player.getPacketSender().sendString(upgrade.upgradeTextId, currentKills < upgrade.requiredKillsToUpgrade ? locked : unlocked);

            //Send item based on current tier
            player.getPacketSender().sendItemOnInterfaceSlot(upgrade.containerId, upgrade.upgradeItemId, 1, upgrade.containerSlot);

            EquipmentInfo.Bonuses item = World.getWorld().equipmentInfo().bonuses(upgrade.upgradeItemId);
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (int stat_index = 0; stat_index < item.bonuses().length; stat_index++) {
                if (item.bonuses()[stat_index] != 0)
                    stringBuilder.append(item.bonuses()[stat_index] < 0 ? "" : "+").append(item.bonuses()[stat_index]).append(" ").append(item.bonusesStr()[stat_index]).append(", ");
            }
            for (int stat_index = 0; stat_index < item.morebonuses().length; stat_index++) {
                if (item.morebonuses()[stat_index] != 0)
                    sb2.append(item.morebonuses()[stat_index] < 0 ? "" : "+").append(item.morebonuses()[stat_index]).append(" ").append(item.morebonusesStr()[stat_index]).append(", ");
            }
            int baseId = 72116;
            if (upgrade.name().startsWith("ABYSSAL_WHIP")) {
                baseId = 72116;
            } else if (upgrade.name().startsWith("DARK_BOW")) {
                baseId += 4;
            } else if (upgrade.name().startsWith("GRANITE_MAUL")) {
                baseId += 8;
            } else if (upgrade.name().startsWith("DRAGON_DAGGER")) {
                baseId += 12;
            } else if (upgrade.name().startsWith("STAFF_OF_LIGHT")) {
                baseId += 16;
            } else if (upgrade.name().startsWith("DRAGON_SCIMITAR")) {
                baseId += 20;
            } else if (upgrade.name().startsWith("DRAGON_MACE")) {
                baseId += 24;
            } else if (upgrade.name().startsWith("DRAGON_LONGSWORD")) {
                baseId += 28;
            } else if (upgrade.name().startsWith("MAGIC_SHORTBOW")) {
                baseId += 32;
            } else if (upgrade.name().startsWith("RUNE_CROSSBOW")) {
                baseId += 36;
            }
            String s = stringBuilder.toString();
            if (s.endsWith(", "))
                s = s.substring(0, s.length() - 2);
            player.getPacketSender().sendString(baseId, s);
            String s1 = sb2.toString();
            if (s1.endsWith(", "))
                s1 = s1.substring(0, s1.length() - 2);
            player.getPacketSender().sendString(baseId + 2, s1);
        }
    }

    public boolean upgrade(int button) {
        for (Upgrades upgrade : Upgrades.values()) {
            //Check if button is valid
            if (button == upgrade.upgradeTextId) {
                int tier = switch (upgrade.name().substring(upgrade.name().indexOf("TIER_") + 5).trim()) {
                    case "ONE" -> 1;
                    case "TWO" -> 2;
                    case "THREE" -> 3;
                    case "FOUR" -> 4;
                    case "FIVE" -> 5;
                    default -> throw new IllegalStateException("Unexpected value: " + upgrade.name().substring(upgrade.name().indexOf("TIER_") + 5).trim());
                };

                //Check if player has the correct item in their inventory to upgrade.
                int currentKills = player.getAttribOr(upgrade.upgradeKey, 0);

                int myTier = player.getAttribOr(upgrade.tierKey, 0);
                if (myTier >= tier)
                    continue;
                if (tier > 1 + myTier)
                    continue;

                if (currentKills < upgrade.requiredKillsToUpgrade) {
                    player.message(Color.RED.tag() + "" + upgrade.name + " Requires " + upgrade.requiredKillsToUpgrade + " Kills, you currently have "+currentKills+" kills.");
                    // dont skip tier 1, just inform them req not met.
                    return true;
                }

                var requiredItem = new Item(upgrade.itemToUpgrade).name();
                var canUpgrade = false;
                var cost = 20_000;
                var slot = ItemOnItem.slotOf(player, PKP_TICKET);
                int pkpInInventory = player.inventory().count(PKP_TICKET);
                if (pkpInInventory > 0) {
                    if (pkpInInventory >= cost) {
                        canUpgrade = true;
                        player.inventory().remove(new Item(PKP_TICKET, cost), slot, true);
                    }
                }

                var pkPoints = player.<Long>getAttribOr(AttributeKey.PK_POINTS, 0L);
                if (pkPoints >= cost) {
                    canUpgrade = true;
                    player.putAttrib(AttributeKey.PK_POINTS, pkPoints - cost);
                    player.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(player));
                }

                //Player has enough pkp, proceed.
                if (canUpgrade) {
                    player.inventory().add(new Item(upgrade.upgradeItemId));
                    int upgrades = (Integer) player.getAttribOr(WEAPON_UPGRADES, 0) + 1;
                    player.putAttrib(WEAPON_UPGRADES, upgrades);

                    if (upgrades >= 1) {
                        AchievementsManager.activate(player, Achievements.AMELIORATION_I, 1);
                    }

                    if (upgrades >= 3) {
                        AchievementsManager.activate(player, Achievements.AMELIORATION_II, 1);
                    }

                    if (upgrade.upgradeItemId == 15445) {
                        AchievementsManager.activate(player, Achievements.AMELIORATION_III, 1);
                    }
                    player.putAttrib(upgrade.tierKey, tier);

                    World.getWorld().sendWorldMessage("<img=1081><shad=0><col=" + Color.YELLOW.getColorValue() + ">[News]:</shad></col> <col=" + Color.HOTPINK.getColorValue() + ">" + player.getUsername() + "</col> Upgraded his<col=" + Color.HOTPINK.getColorValue() + "> "+requiredItem+"</col> to a <col=" + Color.HOTPINK.getColorValue() + ">"+new Item(upgrade.upgradeItemId).name()+"</col>!");
                    open();
                } else {
                    player.message("You need "+ Utils.formatNumber(cost)+" PKP to upgrade your "+requiredItem+".");
                }
                return true;
            }
        }
        return false;
    }

    public boolean reclaim(int interfaceId, int slot, int id) {
        //Make sure the player isn't trying to claim from somewhere else
        if(interfaceId != 71030 && interfaceId != 72030) {
            return false;
        }

        for (Upgrades upgrade : Upgrades.values()) {
            Item upgradedVersion = new Item(id);//This is wrong :D welld one

            if(upgrade.upgradeItemId == id && upgrade.containerSlot == slot) {
                //Check if we can reclaim, we should match the appropriate kills requirement.
                int currentKills = player.getAttribOr(upgrade.upgradeKey, 0);

                // first, find the right tier
                if (currentKills < upgrade.requiredKillsToUpgrade) {
                    int myUnlockedTier = upgrade.getTier();
                    //System.out.println("reducing "+myUnlockedTier);
                    // reduce the tier until we find which one we have unlocked
                    while (myUnlockedTier-- > 1) {
                        upgrade = Upgrades.values()[upgrade.ordinal() - 1];
                        if (currentKills >= upgrade.requiredKillsToUpgrade) {
                            //System.out.println("real is "+upgrade);
                            upgradedVersion = new Item(upgrade.upgradeItemId);
                            break;
                        }
                    }
                }

                // do the check on the right tier
                if (currentKills < upgrade.requiredKillsToUpgrade) {
                    player.message(Color.RED.tag() + "Unable to reclaim " + upgradedVersion.name() + " requires " + upgrade.requiredKillsToUpgrade + " Kills.");
                    return true;
                }

                //Can't own multiple of the same tier upgrade
                boolean already_owns = player.inventory().contains(upgradedVersion) || player.getEquipment().contains(upgradedVersion) || player.getBank().contains(upgradedVersion);

                if(already_owns) {
                    player.message(Color.RED.tag()+"You can't own more than one "+upgradedVersion.name()+".");
                    return true;
                }

                //If we have enough space give item otherwise block
                if(player.inventory().hasCapacity(upgradedVersion)) {
                    player.inventory().add(upgradedVersion,true);
                } else {
                    player.message(Color.RED.tag()+"You need at least one free inventory slot.");
                }
                return true;
            }
        }
        return false;
    }
}

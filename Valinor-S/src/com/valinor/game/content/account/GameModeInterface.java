package com.valinor.game.content.account;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.Flag;
import com.valinor.game.world.entity.mob.player.*;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;
import com.valinor.util.timers.TimerKey;

import static com.valinor.game.GameConstants.BANK_ITEMS;
import static com.valinor.game.GameConstants.TAB_AMOUNT;
import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.EXP_MODE;
import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.GAME_MODE;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 24, 2022
 */
public class GameModeInterface extends Interaction {

    public enum AccountType {
        REGULAR_ACCOUNT(22811, -1, GameMode.NONE, new String[]{"Rookie: X50/X150 combat experience no drop rate bonus.", "<br>Challenger: X30 experience, 5% drop rate bonus.", "<br>Gladiator: X7.5 experience, 10% drop rate bonus."}, new String[]{"No Iron man restrictions will be applied" + "<br>to this account."}),
        PVP_ACCOUNT(22812, 936, GameMode.INSTANT_PKER, new String[]{"PvP accounts can use various pre made" + "<br>presets. Commands to spawn food and potions," + "<br>Can wear a max cape for getting 250 kills" + "<br>and +30 highest killstreak."}, new String[]{"PvP ready account, pre made bank and access" + "<br>to quick gear and consumables spawns."}),
        IRONMAN(22813, 502, GameMode.REGULAR, new String[]{}, new String[]{"An Iron Man can't receive items or assistance" + "<br> from other players. They cannot trade," + "<br>stake, receive PVP loot or pickup dropped items."}),
        HARDCORE_IRONMAN(22814, 504, GameMode.HARDCORE, new String[]{"Rookie: X50/X150 combat experience no drop rate bonus.", "<br>Challenger: X30 experience, 5% drop rate bonus.", "<br>Gladiator: X7.5 experience, 10% drop rate bonus."}, new String[]{"A hardcore ironman account loses its status" + "<br>upon death."}),
        ULTIMATE_IRONMAN(22815, 503, GameMode.ULTIMATE, new String[]{"Rookie: X50/X150 combat experience no drop rate bonus.", "<br>Challenger: X30 experience, 5% drop rate bonus.", "<br>Gladiator: X7.5 experience, 10% drop rate bonus."}, new String[]{"In addition, an Ultimate Iron Man cannot use" + "<br>banks."}),
        COLLECTION_IRONMAN(22816, 470, GameMode.COLLECTION_IRON, new String[]{"Rookie: X50/X150 combat experience no drop rate bonus.", "<br>Challenger: X30 experience, 5% drop rate bonus.", "<br>Gladiator: X7.5 experience, 10% drop rate bonus."}, new String[]{"A Collection ironman account cannot use items"+"<br>they have not unlocked. In the collection log." + "<br>They are allowed to trade, other Collection" + "<br>ironman."});

        public final int button, spriteId;
        public final GameMode gameMode;
        public final String[] perks, description;

        AccountType(int button, int spriteId, GameMode gameMode, String[] perks, String[] description) {
            this.button = button;
            this.spriteId = spriteId;
            this.gameMode = gameMode;
            this.perks = perks;
            this.description = description;
        }

        private String crown() {
            return spriteId == -1 ? "    " : "<img=" + spriteId + ">";
        }

    }

    private static void clear(Player player) {
        player.getPacketSender().sendString(22805, "").sendString(22807, "");
    }

    private static void loadList(Player player) {
        for (AccountType type : AccountType.values()) {
            player.getPacketSender().sendString(type.button, type.crown() + Utils.formatEnum(type.name()));
        }
    }

    private static void info(Player player, AccountType type) {
        clear(player);

        StringBuilder perksBuilder = new StringBuilder();

        if (type.perks != null) {
            for (String s : type.perks) {
                perksBuilder.append(s).append("<br>");
            }
        }

        //Perks
        player.getPacketSender().sendString(22805, perksBuilder.toString());

        StringBuilder descriptionBuilder = new StringBuilder();

        if (type.description != null) {
            for (String s : type.description) {
                descriptionBuilder.append(s).append("<br>");
            }
        }

        //Description
        player.getPacketSender().sendString(22807, descriptionBuilder.toString());

        //Set selected game mode sprite
        player.getPacketSender().setClickedText(type.button, true);

        //Change game mode
        player.gameMode(type.gameMode);

        //Current selected game mode
        player.putAttrib(AttributeKey.CURRENT_GAME_MODE_SELECTED, type.gameMode);
    }

    public static void open(Player player, AccountType type) {
        loadList(player);
        info(player, type);

        if(type != AccountType.PVP_ACCOUNT) {
            //Default exp mode
            player.putAttrib(AttributeKey.CURRENT_EXP_MODE_SELECTED, ExpMode.ROOKIE);
            player.getPacketSender().sendChangeSprite(22820, (byte) 2).sendChangeSprite(22821, (byte) 0).sendChangeSprite(22822, (byte) 0);
        } else {
            player.getPacketSender().sendChangeSprite(22820, (byte) 0).sendChangeSprite(22821, (byte) 0).sendChangeSprite(22822, (byte) 0);
        }
        player.getInterfaceManager().open(22800);
    }

    private void gearUp(Player player, GameMode type) {
        switch (type) {
            case REGULAR -> {
                player.resetSkills();
                player.getInventory().add(new Item(IRONMAN_HELM, 1), true);
                player.getInventory().add(new Item(IRONMAN_PLATEBODY, 1), true);
                player.getInventory().add(new Item(IRONMAN_PLATELEGS, 1), true);
            }
            case ULTIMATE -> {
                player.resetSkills();
                player.getInventory().add(new Item(ULTIMATE_IRONMAN_HELM, 1), true);
                player.getInventory().add(new Item(ULTIMATE_IRONMAN_PLATEBODY, 1), true);
                player.getInventory().add(new Item(ULTIMATE_IRONMAN_PLATELEGS, 1), true);
            }
            case HARDCORE -> {
                player.getInventory().add(new Item(HARDCORE_IRONMAN_HELM, 1), true);
                player.getInventory().add(new Item(HARDCORE_IRONMAN_PLATEBODY, 1), true);
                player.getInventory().add(new Item(HARDCORE_IRONMAN_PLATELEGS, 1), true);
            }

            case INSTANT_PKER -> {
                player.getBank().addAll(BANK_ITEMS);
                System.arraycopy(TAB_AMOUNT, 0, player.getBank().tabAmounts, 0, TAB_AMOUNT.length);
                player.getBank().shift();
                player.message("Your bank has been filled with PvP supplies.");

                for (int skill = 0; skill < 7; skill++) {
                    player.skills().setXp(skill, Skills.levelToXp(99));
                    player.skills().update();
                    player.skills().recalculateCombat();
                }
                player.putAttrib(AttributeKey.COMBAT_MAXED,true);
            }
        }

        //Set default spellbook
        player.setSpellbook(MagicSpellbook.NORMAL);
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }

    public boolean confirm(Player player) {
        if (player.getTimers().has(TimerKey.CLICK_DELAY)) {
            return false;
        }

        player.getTimers().register(TimerKey.CLICK_DELAY,2);

        switch (player.<GameMode>getAttribOr(AttributeKey.CURRENT_GAME_MODE_SELECTED, GameMode.NONE)) {
            case REGULAR, COLLECTION_IRON -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.IRON_MAN);
                }
                gearUp(player, GameMode.REGULAR);
            }
            case ULTIMATE -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.ULTIMATE_IRON_MAN);
                }
                gearUp(player, GameMode.ULTIMATE);
            }
            case HARDCORE -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.HARDCORE_IRON_MAN);
                }
                gearUp(player, GameMode.HARDCORE);
            }
            case INSTANT_PKER -> {
                if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.INSTANT_PKER);
                }
                gearUp(player, GameMode.INSTANT_PKER);
            }
        }
        if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
            player.getPacketSender().sendRights();
        }
        player.unlock();
        player.getInterfaceManager().close();
        player.putAttrib(AttributeKey.CONTINUE_STARTER_TUTORIAL, true);
        player.getPacketSender().sendString(GAME_MODE.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.GAME_MODE.childId).fetchLineData(player));
        player.getPacketSender().sendString(EXP_MODE.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.EXP_MODE.childId).fetchLineData(player));
        if (player.getAttribOr(AttributeKey.NEW_ACCOUNT, false)) {
            player.getDialogueManager().start(new Tutorial());
        }
        return true;
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        if (button == 22811) {
            open(player, AccountType.REGULAR_ACCOUNT);
            return true;
        }
        if (button == 22812) {
            open(player, AccountType.PVP_ACCOUNT);
            return true;
        }
        if (button == 22813) {
            open(player, AccountType.IRONMAN);
            return true;
        }
        if (button == 22814) {
            open(player, AccountType.HARDCORE_IRONMAN);
            return true;
        }
        if (button == 22815) {
            open(player, AccountType.ULTIMATE_IRONMAN);
            return true;
        }
        if (button == 22816) {
            open(player, AccountType.COLLECTION_IRONMAN);
            return true;
        }
        if (button == 22820 || button == 22821 || button == 22822) {
            if (player.<GameMode>getAttribOr(AttributeKey.CURRENT_GAME_MODE_SELECTED,null) == GameMode.INSTANT_PKER) {
                player.message("As an instant pker you have no exp modes.");
                return true;
            }

            if(button == 22820) {
                player.getPacketSender().sendChangeSprite(22820, (byte) 2);
                player.getPacketSender().sendChangeSprite(22821, (byte) 0);
                player.getPacketSender().sendChangeSprite(22822, (byte) 0);
                player.expmode(ExpMode.ROOKIE);
                player.putAttrib(AttributeKey.CURRENT_EXP_MODE_SELECTED, ExpMode.ROOKIE);
            } else if (button == 22821) {
                player.getPacketSender().sendChangeSprite(22821, (byte) 2);
                player.getPacketSender().sendChangeSprite(22820, (byte) 0);
                player.getPacketSender().sendChangeSprite(22822, (byte) 0);
                player.expmode(ExpMode.CHALLENGER);
                player.putAttrib(AttributeKey.CURRENT_EXP_MODE_SELECTED, ExpMode.CHALLENGER);
            } else if (button == 22822) {
                player.getPacketSender().sendChangeSprite(22822, (byte) 2);
                player.getPacketSender().sendChangeSprite(22820, (byte) 0);
                player.getPacketSender().sendChangeSprite(22821, (byte) 0);
                player.expmode(ExpMode.GLADIATOR);
                player.putAttrib(AttributeKey.CURRENT_EXP_MODE_SELECTED, ExpMode.GLADIATOR);
            }
            return true;
        }

        if (button == 22802) {
            confirm(player);
            return true;
        }

        return false;
    }
}

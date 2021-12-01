package com.valinor.game.content.packet_actions.interactions.items;

import com.valinor.game.content.EffectTimer;
import com.valinor.game.content.collection_logs.LogType;
import com.valinor.game.content.consumables.FoodConsumable;
import com.valinor.game.content.consumables.potions.Potions;
import com.valinor.game.content.duel.DuelRule;
import com.valinor.game.content.items.MithrilSeeds;
import com.valinor.game.content.items.RockCake;
import com.valinor.game.content.items.tools.ItemPacks;
import com.valinor.game.content.skill.impl.herblore.Cleaning;
import com.valinor.game.content.skill.impl.hunter.Hunter;
import com.valinor.game.content.skill.impl.hunter.HunterItemPacks;
import com.valinor.game.content.skill.impl.hunter.trap.impl.Birds;
import com.valinor.game.content.skill.impl.hunter.trap.impl.Chinchompas;
import com.valinor.game.content.skill.impl.slayer.content.ImbuedHeart;
import com.valinor.game.content.skill.impl.woodcutting.BirdNest;
import com.valinor.game.content.tasks.rewards.TaskReward;
import com.valinor.game.content.treasure.TreasureRewardCaskets;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.bountyhunter.dialogue.TeleportToTargetScrollD;
import com.valinor.game.world.entity.mob.player.MagicSpellbook;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.net.packet.interaction.PacketInteractionManager;
import com.valinor.util.Color;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.Utils;
import com.valinor.util.timers.TimerKey;

import static com.valinor.game.world.entity.AttributeKey.VIEWING_RUNE_POUCH_I;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

public class ItemActionOne {

    public static void click(Player player, Item item) {
        int id = item.getId();
        if (PacketInteractionManager.checkItemInteraction(player, item, 1)) {
            return;
        }
        if (TreasureRewardCaskets.openCasket(player, item)) {
            return;
        }

        if(Potions.onItemOption1(player, item)) {
            return;
        }

        if(BirdNest.onItemOption1(player, item)) {
            return;
        }

        if (FoodConsumable.onItemOption1(player, item)) {
            return;
        }

        if(MithrilSeeds.onItemOption1(player, item)) {
            return;
        }

        if(RockCake.onItemOption1(player, item)) {
            return;
        }

        if(ItemPacks.open(player, item)) {
            return;
        }

        if(Cleaning.onItemOption1(player, item)) {
            return;
        }

        if(HunterItemPacks.onItemOption1(player, item)) {
            return;
        }

        if(id == TASK_BOTTLE_CASKET) {
            player.inventory().remove(new Item(TASK_BOTTLE_CASKET));
            TaskReward.reward(player);
            return;
        }

        if(id == TARGET_TELEPORT_SCROLL) {
            boolean alreadyClaimed = player.getAttribOr(AttributeKey.BOUNTY_HUNTER_TARGET_TELEPORT_UNLOCKED, false);
            if (alreadyClaimed) {
                player.message("You already know this spell.");
                return;
            }
            if (player.inventory().contains(TARGET_TELEPORT_SCROLL)) {
                player.getDialogueManager().start(new TeleportToTargetScrollD());
            }
            return;
        }

        if (id == ItemIdentifiers.COLLECTION_LOG) {
            player.getCollectionLog().open(LogType.BOSSES);
            return;
        }

        if(id == ItemIdentifiers.IMBUED_HEART) {
            ImbuedHeart.activate(player);
            return;
        }

        if(id == VOTE_TICKET) {
            if(!player.inventory().contains(VOTE_TICKET)) {
                return;
            }

            if(WildernessArea.inWilderness(player.tile())) {
                player.message("You cannot exchange vote points in the wilderness.");
                return;
            }

            int amount = player.inventory().count(VOTE_TICKET);
            int current = player.getAttribOr(AttributeKey.VOTE_POINTS, 0);

            player.putAttrib(AttributeKey.VOTE_POINTS, current + amount);
            player.getPacketSender().sendString(QuestTab.InfoTab.VOTE_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.VOTE_POINTS.childId).fetchLineData(player));
            player.inventory().remove(new Item(VOTE_TICKET, amount), true);
            player.message("You exchange " + Color.BLUE.tag() + "" + Utils.formatNumber(amount) + " vote points</col>.");
            return;
        }

        if (id == VENGEANCE_SKULL) {
            if(player.getSpellbook() != MagicSpellbook.LUNAR) {
                player.message("You can only use the vengeance cast on the lunars spellbook.");
                return;
            }
            boolean hasVengeance = player.getAttribOr(AttributeKey.VENGEANCE_ACTIVE, false);
            if (player.skills().level(Skills.DEFENCE) < 40) {
                player.message("You need 40 Defence to use Vengence.");
            } else if (player.skills().level(Skills.MAGIC) < 94) {
                player.message("Your Magic level is not high enough to use this spell.");
            } else if (hasVengeance) {
                player.message("You already have Vengeance casted.");
            } else if (player.getDueling().inDuel() && player.getDueling().getRules()[DuelRule.NO_MAGIC.ordinal()]) {
                player.message("Magic is disabled for this duel.");
            } else if (!player.getTimers().has(TimerKey.VENGEANCE_COOLDOWN)) {
                if (!player.inventory().contains(964)) {
                    return;
                }
                player.getTimers().register(TimerKey.VENGEANCE_COOLDOWN, 50);
                player.putAttrib(AttributeKey.VENGEANCE_ACTIVE, true);
                player.animate(8316);
                player.graphic(726);
                player.sound(2907);
                player.getPacketSender().sendEffectTimer(30, EffectTimer.VENGEANCE).sendMessage("You now have Vengeance's effect.");
            } else {
                player.message("You can only cast vengeance spells every 30 seconds.");
            }
            return;
        }

        if (id == 10006) {
            Hunter.lay(player, new Birds(player));
            return;
        }

        if (id == 10008) {
            Hunter.lay(player, new Chinchompas(player));
            return;
        }

        /* Looting bag. */
        if (id == 11941 || id == 22586) {
            player.getLootingBag().openAndCloseBag(id);
            return;
        }
        if (id == RUNE_POUCH) {
            player.getRunePouch().open(RUNE_POUCH);
            player.putAttrib(VIEWING_RUNE_POUCH_I,false);
            return;
        }

        if (id == RUNE_POUCH_I) {
            player.getRunePouch().open(RUNE_POUCH_I);
            player.putAttrib(VIEWING_RUNE_POUCH_I,true);
            return;
        }
    }
}

package com.valinor.game.content.areas.home;

import com.valinor.game.content.areas.edgevile.dialogue.AuburyDialogue;
import com.valinor.game.content.areas.edgevile.dialogue.DrunkenDwarfDialogue;
import com.valinor.game.content.areas.edgevile.dialogue.PerduDialogue;
import com.valinor.game.content.areas.lumbridge.dialogue.Hans;
import com.valinor.game.content.group_ironman.GroupIronmanInterface;
import com.valinor.game.content.mechanics.Poison;
import com.valinor.game.content.mechanics.referrals.ReferralD;
import com.valinor.game.content.mechanics.referrals.Referrals;
import com.valinor.game.content.tasks.TaskMasterD;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.Venom;
import com.valinor.game.world.entity.masks.animations.Animation;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.MagicSpellbook;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.Color;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import static com.valinor.util.NpcIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.ALTAR;

/**
 * @author Patrick van Elderen | April, 23, 2021, 10:49
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class HomeArea extends PacketInteraction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if(object.getId() == 31675) {
                player.faceObj(object);
                player.getSlayerKey().open();
                return true;
            }
            if(object.getId() == TELEPORT_PLATFORM_36062) {
                player.getPacketSender().sendString(29078, "World Teleports - Recent");
                player.setCurrentTabIndex(2);
                player.getTeleportInterface().displayRecent();
                player.getInterfaceManager().open(29050);
                return true;
            }
            if (object.getId() == ALTAR) {
                if (player.skills().level(Skills.PRAYER) < player.skills().xpLevel(Skills.PRAYER)) {
                    player.animate(new Animation(645));
                    player.skills().replenishSkill(5, player.skills().xpLevel(5));
                    player.message("You recharge your Prayer points.");
                } else {
                    player.message("You already have full prayer points.");
                }
                return true;
            }
            if (object.getId() == ORNATE_REJUVENATION_POOL) {
                Chain.bound(null).name("RejuvenationPoolTask").runFn(1, () -> player.animate(7305)).then(2, () -> {
                    player.lock();
                    player.message("<col=" + Color.BLUE.getColorValue() + ">You have restored your hitpoints, run energy and prayer.");
                    player.message("<col=" + Color.HOTPINK.getColorValue() + ">You've also been cured of poison and venom.");
                    player.skills().resetStats();
                    int increase = player.getEquipment().hpIncrease();
                    player.hp(Math.max(increase > 0 ? player.skills().level(Skills.HITPOINTS) + increase : player.skills().level(Skills.HITPOINTS), player.skills().xpLevel(Skills.HITPOINTS)), 39); //Set hitpoints to 100%
                    player.skills().replenishSkill(5, player.skills().xpLevel(5)); //Set the players prayer level to fullplayer.putAttrib(AttributeKey.RUN_ENERGY, 100.0);
                    player.setRunningEnergy(100.0, true);
                    Poison.cure(player);
                    Venom.cure(2, player);

                    if (player.tile().region() != 13386) {
                        player.message(Color.RED.tag() + "When being a member your special attack will also regenerate.");
                        if (player.getMemberRights().isSapphireMemberOrGreater(player)) {
                            if (player.getTimers().has(TimerKey.RECHARGE_SPECIAL_ATTACK)) {
                                player.message("Special attack energy can be restored in " + player.getTimers().asMinutesAndSecondsLeft(TimerKey.RECHARGE_SPECIAL_ATTACK) + ".");
                            } else {
                                player.restoreSpecialAttack(100);
                                player.setSpecialActivated(false);
                                CombatSpecial.updateBar(player);
                                int time = 0;
                                if (player.getMemberRights().isSapphireMemberOrGreater(player))
                                    time = 300;//3 minutes
                                if (player.getMemberRights().isEmeraldMemberOrGreater(player))
                                    time = 100;//1 minute
                                if (player.getMemberRights().isRubyMemberOrGreater(player))
                                    time = 0;//always
                                player.getTimers().register(TimerKey.RECHARGE_SPECIAL_ATTACK, time); //Set the value of the timer.
                                player.message("<col=" + Color.HOTPINK.getColorValue() + ">You have restored your special attack.");
                            }
                        }
                    } else {
                        player.restoreSpecialAttack(100);
                        player.setSpecialActivated(false);
                        CombatSpecial.updateBar(player);
                        player.message("<col=" + Color.HOTPINK.getColorValue() + ">You have restored your special attack.");
                    }
                    player.unlock();
                });
                return true;
            }
            if (object.getId() == ALTAR_OF_THE_OCCULT) {
                player.animate(645);
                player.message("Your mind fills with knowledge.");
                MagicSpellbook.changeSpellbook(player, MagicSpellbook.NORMAL, true);
                return true;
            }
        }
        if(option == 2) {
            if (object.getId() == ALTAR_OF_THE_OCCULT) {
                player.animate(645);
                player.message("Your mind fills with knowledge.");
                MagicSpellbook.changeSpellbook(player, MagicSpellbook.ANCIENT, true);
                return true;
            }
        }
        if(option == 3) {
            if (object.getId() == ALTAR_OF_THE_OCCULT) {
                player.animate(645);
                player.message("Your mind fills with knowledge.");
                MagicSpellbook.changeSpellbook(player, MagicSpellbook.LUNAR, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if (npc.id() == GRAND_EXCHANGE_CLERK || npc.id() == GRAND_EXCHANGE_CLERK_2149) {
                TradingPost.open(player);
                return true;
            }
            if(npc.id() == IRON_MAN_TUTOR) {
                GroupIronmanInterface.open(player);
                return true;
            }
            if(npc.id() == PERDU) {
                player.getDialogueManager().start(new PerduDialogue());
                return true;
            }
            if (npc.id() == SHURA) {
                if(Referrals.INSTANCE.getCOMMAND_ENABLED()) {
                    player.getDialogueManager().start(new ReferralD());
                } else {
                    player.message("Referrals are currently disabled.");
                }
                return true;
            }
            if(npc.id() == SHOP_ASSISTANT_2820) {
                World.getWorld().shop(1).open(player);
                return true;
            }
            if (npc.id() == HANS) {
                player.getDialogueManager().start(new Hans());
                return true;
            }
            if (npc.id() == AUBURY) {
                player.getDialogueManager().start(new AuburyDialogue());
                return true;
            }
            if (npc.id() == VANNAKA) {
                player.getDialogueManager().start(new TaskMasterD());
                return true;
            }
            if (npc.id() == DRUNKEN_DWARF_2408) {
                player.getDialogueManager().start(new DrunkenDwarfDialogue());
                return true;
            }
        }
        if(option == 2) {
            if (npc.id() == VANNAKA) {
                player.getTaskMasterManager().open();
                return true;
            }
            if (npc.id() == HANS) {
                player.getDialogueManager().start(new Hans());
                return true;
            }
        }
        if(option == 3) {
            if (npc.id() == AUBURY) {
                npc.forceChat("Seventhior Distine Molenko!");
                player.graphic(110, 124, 100);
                player.lockNoDamage();
                Chain.bound(player).runFn(3, () -> {
                    player.teleport(new Tile(2911, 4830, 0));
                    player.unlock();
                });
                return true;
            }
        }
        if(option == 4) {

        }
        return false;
    }
}

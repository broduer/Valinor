package com.valinor.game.content.areas.edgevile;

import com.valinor.GameServer;
import com.valinor.game.content.areas.edgevile.dialogue.*;
import com.valinor.game.content.areas.lumbridge.dialogue.Hans;
import com.valinor.game.content.areas.wilderness.dialogue.ArtifactTraderDialogue;
import com.valinor.game.content.group_ironman.GroupIronmanInterface;
import com.valinor.game.content.mechanics.MagicalAltarDialogue;
import com.valinor.game.content.mechanics.Poison;
import com.valinor.game.content.mechanics.referrals.ReferralD;
import com.valinor.game.content.mechanics.referrals.Referrals;
import com.valinor.game.content.tasks.TaskMasterD;
import com.valinor.game.content.teleport.OrnateJewelleryBox;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.ForceMovementTask;
import com.valinor.game.task.impl.TickableTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.Venom;
import com.valinor.game.world.entity.masks.animations.Animation;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.MagicSpellbook;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.Color;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import static com.valinor.util.NpcIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.ALTAR;
import static com.valinor.util.ObjectIdentifiers.MINE_CART;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen | Zerikoth | PVE
 * @date februari 21, 2020 17:06
 */
public class Edgevile extends PacketInteraction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if (option == 1) {
            if (npc.id() == EMBLEM_TRADER) {
                player.getDialogueManager().start(new ArtifactTraderDialogue());
                return true;
            }
        } else if (option == 2) {
            if (npc.id() == EMBLEM_TRADER) {
                World.getWorld().shop(17).open(player);
                return true;
            }
            if (npc.id() == AUBURY) {
                World.getWorld().shop(23).open(player);
                return true;
            }
        } else if (option == 3) {

        } else if (option == 4) {
        }
        return false;
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (option == 1) {
            if (obj.getId() == DWARVEN_MACHINERY) {
                player.getItemDispenser().dispenseItemsDialogue();
                return true;
            }

            if (obj.getId() == MINE_CART) {
                player.getItemDispenser().checkCart();
                return true;
            }

            if (obj.getId() == CHAOS_ALTAR_411) {
                if (player.skills().level(Skills.PRAYER) < player.skills().xpLevel(Skills.PRAYER)) {
                    player.animate(new Animation(645));
                    player.skills().replenishSkill(5, player.skills().xpLevel(5));
                    player.message("You recharge your Prayer points.");
                } else {
                    player.message("You already have full prayer points.");
                }
                return true;
            }

            if (obj.getId() == LEVER_26761) {
                player.faceObj(obj);

                //Check to see if the player is teleblocked
                if (player.getTimers().has(TimerKey.TELEBLOCK)) {
                    player.teleblockMessage();
                    return true;
                }

                player.lockNoDamage();
                GameObject spawned = new GameObject(88, obj.tile(), obj.getType(), obj.getRotation());
                player.runFn(1, () -> {
                    player.animate(2140);
                    player.message("You pull the lever...");
                }).then(1, () -> {
                    ObjectManager.addObj(spawned);
                    ObjectManager.replace(obj, spawned, 5);
                }).then(1, () -> {
                    player.animate(714);
                    player.graphic(111, 110, 0);
                }).then(4, () -> {
                    player.teleport(3154, 3924);
                    player.animate(-1);
                    player.unlock();
                    player.message("...And teleport into the wilderness.");
                });
                return true;
            }

            if (obj.getId() == TRAPDOOR_1579) {
                GameObject open = new GameObject(1581, obj.tile(), obj.getType(), obj.getRotation());
                ObjectManager.replaceWith(obj, open);
                return true;
            }

            if (obj.getId() == WILDERNESS_DITCH) {
                player.getMovementQueue().clear();
                player.lockDelayDamage();
                if (player.getForceMovement() == null && player.getClickDelay().elapsed(2000)) {
                    final Tile crossDitch = new Tile(0, player.tile().getY() < 3522 ? 3 : -3);
                    TaskManager.submit(new ForceMovementTask(player, 3, new ForceMovement(player.tile().copy(), crossDitch, 0, 70, crossDitch.getY() == 3 ? 0 : 2)));
                    player.animate(6132);
                    player.getClickDelay().reset();
                    TickableTask.runOnceTask(3, c -> player.unlock());
                }
                return true;
            }

            if (obj.getId() == DOOR_1536) {
                player.message("You feel like it wouldn't be wise to do that...");
                return true;
            }
        } else if (option == 2) {

            if (obj.getId() == MINE_CART) {
                player.getItemDispenser().loadValueList();
                return true;
            }
            if (obj.getId() == DWARVEN_MACHINERY) {
                World.getWorld().shop(15).open(player);
                return true;
            }

            if (obj.getId() == ORNATE_JEWELLERY_BOX) {
                OrnateJewelleryBox.open(player);
                return true;
            }

            if (obj.getId() == TRAPDOOR_1581) {
                GameObject open = new GameObject(1581, obj.tile(), obj.getType(), obj.getRotation());
                GameObject close = new GameObject(1579, obj.tile(), obj.getType(), obj.getRotation());
                ObjectManager.replaceWith(open, close);
                return true;
            }
        } else if (option == 3) {
            if (obj.getId() == 637) {
                player.getItemDispenser().clearCart();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        if (object.getId() == 637) {
            player.getItemDispenser().addItemToCart(item);
            return true;
        }
        return false;
    }

}

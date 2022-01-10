package com.valinor.game.content.areas.varrock;

import com.valinor.game.content.packet_actions.interactions.objects.Ladders;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.movement.MovementQueue;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.object.doors.Door;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.StepType;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.game.world.object.doors.Doors.CACHE;

/**
 * @author Patrick van Elderen | April, 14, 2021, 19:20
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class CookingGuild extends Interaction {

    private final static int CHEF = 2658;
    private final static int CHEFS_HAT = 1949;
    private final static int GOLDEN_CHEFS_HAT = 20205;
    public final static int GUILD_DOOR = 24958;
    private final static int FIRST_FLOOR_STAIRS = 2608;
    private final static int SECOND_FLOOR_STAIRS = 2609;
    private final static int THIRD_FLOOR_STAIRS = 2610;

    private void door(Player player, GameObject obj) {
        //Requirement checks to enter the cooking guild.
            if (player.skills().level(Skills.COOKING) < 32) {
                DialogueManager.npcChat(player, Expression.DULL, CHEF, "Sorry. Only the finest chefs are allowed in here. Get", "your cooking level up to 32 and come back wearing a", "chef's hat.");
            } else if (!player.getEquipment().containsAny(CHEFS_HAT, GOLDEN_CHEFS_HAT)) {
                DialogueManager.npcChat(player, Expression.ANXIOUS, CHEF, "You can't come in here unless you're wearing a chef's", "hat, or something like that.");
            } else {
                GameObject opened = new GameObject(24959, new Tile(3143, 3444, 0), obj.getType(), 2);
                Chain.bound(null).waitUntil(1, () -> {
                    player.lock();
                    player.stepAbs(obj.getX(), player.getAbsY(), StepType.FORCE_WALK);
                    return player.tile().equals(obj.getX(), player.getAbsY());
                }, () -> {
                    opened.spawn();
                    obj.skipClipping(true).remove();
                    player.step(0, player.getAbsY() <= 3443 ? 1 : -1, StepType.FORCE_WALK);
                }).then(2, () -> {
                    obj.restore().skipClipping(false);
                    opened.remove();
                    player.unlock();
                });
            }
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int interaction) {
        if (interaction == 1) {
            // This door ID is used elsewhere too!
            if (obj.getId() == GUILD_DOOR) {
                if (obj.tile().equals(3143, 3443)) {
                    door(player, obj);
                } else {
                    Door door = CACHE.stream().filter(d -> d.id() == obj.getId()).findAny().orElse(null);
                    if (door == null)
                        return true;
                    door.open(obj, player,true);
                }
                return true;
            }
            //Staircase inside the cooking guild
            if (obj.getId() == FIRST_FLOOR_STAIRS) {
                if (obj.tile().equals(3144, 3447, 0)) {
                    Ladders.ladderUp(player, new Tile(player.tile().x, player.tile().y, player.tile().level + 1), true);
                }
                return true;
            }

            if (obj.getId() == SECOND_FLOOR_STAIRS) {
                Ladders.ladderUp(player, new Tile(3144, 3446, player.tile().level + 1), true);
                return true;
            }

            if (obj.getId() == THIRD_FLOOR_STAIRS) {
                if (obj.tile().equals(3144, 3447, 2)) {
                    Ladders.ladderDown(player, new Tile(3144, 3449, player.tile().level - 1), true);
                }
                return true;
            }
        } else if (interaction == 2) {
            if (obj.getId() == SECOND_FLOOR_STAIRS) {
                Ladders.ladderUp(player, new Tile(3144, 3446, player.tile().level + 1), true);
                return true;
            }
        } else if (interaction == 3) {
            if (obj.getId() == SECOND_FLOOR_STAIRS) {
                Ladders.ladderDown(player, new Tile(3144, 3449, player.tile().level - 1), true);
                return true;
            }
        }
        return false;
    }
}

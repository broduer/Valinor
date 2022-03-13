package com.valinor.game.content.areas.burthope.warriors_guild;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.items.skillcape.CapeOfCompletion;
import com.valinor.game.content.packet_actions.interactions.objects.Ladders;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.game.content.areas.burthope.warriors_guild.CyclopsRoom.insideBasementCyclopsRoom;
import static com.valinor.game.content.areas.burthope.warriors_guild.CyclopsRoom.insideTopFloorCyclopsRoom;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen | March, 26, 2021, 09:45
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class Basement extends Interaction {

    @Override
    public boolean onLogout(Player player) {
        if(insideTopFloorCyclopsRoom(player.tile())) {
            player.teleport(2843, 3540,2);
            return true;
        } else if(insideBasementCyclopsRoom(player.tile())) {
            player.teleport(2909, 9969,0);
            return true;
        }
        return false;
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(obj.getId() == DOOR_10043) {
            if(!(player.inventory().containsAny(RUNE_DEFENDER, DRAGON_DEFENDER, DRAGON_DEFENDER_T)) && !player.getEquipment().containsAny(RUNE_DEFENDER, DRAGON_DEFENDER, DRAGON_DEFENDER_T)) {
                DialogueManager.sendStatement(player, "You need at least a rune defender to enter this area.");
                return true;
            }

            if ((!player.inventory().contains(new Item(WARRIOR_GUILD_TOKEN, 100)) && !CapeOfCompletion.ATTACK.operating(player)) && player.getX() < 2912) {
                DialogueManager.sendStatement(player, "You need at least 100 warrior guild tokens to enter this area.");
                return true;
            }

            set_item(player, DRAGON_DEFENDER);

            int defid = player.getAttribOr(AttributeKey.WARRIORS_GUILD_CYCLOPS_ROOM_DEFENDER, BRONZE_DEFENDER);
            ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, defid);

            player.message("<col=804080>Cyclops' are currently dropping " + def.name.toLowerCase() + "s.");

            player.teleport(player.getX() < 2912 ? 2912 : 2911, 9968);
            if (!CapeOfCompletion.ATTACK.operating(player)) {
                CyclopsRoom.handle_time_spent(player, true);
            }
            return true;
        }

        if (obj.getId() == LADDER_10042) {
            Ladders.ladderDown(player, new Tile(2907, 9968), true);
            return true;
        }
        if (obj.getId() == LADDER_9742) {
            Ladders.ladderUp(player, new Tile(2834, 3542), true);
            return true;
        }
        return false;
    }

    private static void set_item(Player player, int item) {
        player.putAttrib(AttributeKey.WARRIORS_GUILD_CYCLOPS_ROOM_DEFENDER, item);
    }
}

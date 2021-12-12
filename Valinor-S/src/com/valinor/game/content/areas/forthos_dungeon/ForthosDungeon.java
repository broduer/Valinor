package com.valinor.game.content.areas.forthos_dungeon;

import com.valinor.game.content.packet_actions.interactions.objects.Ladders;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.StepType;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ItemIdentifiers.WILDERNESS_SWORD_4;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 27, 2021
 */
public class ForthosDungeon extends PacketInteraction {

    private static final Area SARACHNIS = new Area(1829, 9888, 1851, 9911, 0);
    public static int KNIFE = 946;

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if (option == 1) {
            if (object.getId() == STAIRCASE_34865) {
                Ladders.ladderDown(player, new Tile(1800, 9967, 0), true);
                return true;
            }
            if (object.getId() == STAIRCASE_34864) {
                Ladders.ladderUp(player, new Tile(1670, 3569, 0), true);
                return true;
            }

            if (object.getId() == WEB_34898) {
                enterSarachnisLair(player, object);
                return true;
            }

            if (object.getId() == THICK_WEB) {
                if (player.getAbsY() == 9912) {
                    player.optionsTitled("<col=FF0000>You are about to enter a dangerous area", "Enter.", "Do not enter.", () -> enterSarachnisLair(player, object));
                    return true;
                }
            }

            if (object.getId() == TEMPLE_DOOR_34843) {
                player.lock();
                Chain.bound(null).runFn(1, () -> {
                    if (player.getAbsX() == 1824) {
                        player.stepAbs(player.getAbsX() + 1, player.getAbsY(), StepType.FORCE_WALK);
                    } else {
                        player.stepAbs(player.getAbsX() - 1, player.getAbsY(), StepType.FORCE_WALK);
                    }
                    player.unlock();
                });
                return true;
            }
        }
        if (option == 2) {
            if (object.getId() == THICK_WEB) {
                enterSarachnisLair(player, object);
                return true;
            }

            if (object.getId() == WEB_34898) {
                int count = 0;
                for (Player p : World.getWorld().getPlayers()) {
                    if (p != null && p.tile().inArea(SARACHNIS))
                        count++;
                }
                if (count <= 0)
                    player.message("You listen and hear no adventurers inside the crypt.");
                else
                    player.message("You listen and hear " + count + " adventurer" + (count > 1 ? "s" : "") + " inside the crypt.");
                return true;
            }
        }
        return false;
    }

    private void enterSarachnisLair(Player player, GameObject obj) {
        player.faceObj(obj);
        int weapon = player.getEquipment().hasWeapon() ? player.getEquipment().getWeapon().getId() : -1;
        String wepName = weapon == -1 ? "" : new Item(weapon).name().toLowerCase();
        boolean hasSharpEdge = wepName.contains("scythe") || wepName.contains("sword") || wepName.contains("dagger") || wepName.contains("axe") || wepName.contains("whip") || wepName.contains("scimitar") || wepName.contains("of light") || wepName.contains("dead") || wepName.contains("tent") || wepName.contains("claw");
        int KNIFE = 946;
        if (player.inventory().contains(KNIFE)) {
            player.animate(911);
            slashWeb(player, obj);
        } else if (hasSharpEdge) {
            player.animate(player.attackAnimation());
            slashWeb(player, obj);
        } else {
            player.message("Only a sharp blade can cut through this sticky web.");
        }
    }

    private void slashWeb(Player player, GameObject web) {
        player.lock();
        player.message("You slash the web apart.");
        Chain.bound(null).name("SlashWebTask").runFn(1, () -> {
            ObjectManager.removeObj(web);
            ObjectManager.addObj(new GameObject(734, web.tile(), web.getType(), web.getRotation()));
        }).then(50, () -> {
            ObjectManager.removeObj(new GameObject(734, web.tile(), web.getType(), web.getRotation()));
            ObjectManager.addObj(web);
        });
        player.unlock();
    }
}

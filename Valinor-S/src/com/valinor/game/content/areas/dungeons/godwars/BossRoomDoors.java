package com.valinor.game.content.areas.dungeons.godwars;

import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex.ZarosGodwars;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.timers.TimerKey;

import static com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex.NexCombat.NEX_AREA;

public class BossRoomDoors extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (option == 1) {
            // Zamorak
            if (obj.getId() == 26505) {
                if (player.tile().y > 5332) {
                    player.teleport(2925, 5331, 2);
                } else if (player.tile().y == 5331) {
                    player.teleport(2925, 5333, 2);
                }
                return true;
            }

            // Bandos
            if (obj.getId() == 26503) {
                if (player.tile().x < 2863) {
                    player.teleport(2864, 5354, 2);
                } else if (player.tile().x == 2864) {
                    player.teleport(2862, 5354, 2);
                }
                return true;
            }

            // Saradomin
            if (obj.getId() == 26504) {
                if (player.tile().x >= 2909) {
                    player.teleport(2907, 5265, 0);
                } else if (player.tile().x == 2907) {
                    player.teleport(2909, 5265, 0);
                }
                return true;
            }

            // Armadyl
            if (obj.getId() == 26502) {
                if (player.tile().y <= 5294) {
                    player.teleport(2839, 5296, 2);
                } else if (player.tile().y == 5296) {
                    player.teleport(2839, 5294, 2);
                }
                return true;
            }

            //Nex
            if(obj.getId() == 42967) {
                if(player.getX() <= 2908) {
                    player.teleport(new Tile(2910, 5203, 0));
                    ZarosGodwars.addPlayer(player);
                }
                return true;
            }
        }

        if(option == 3) {
            if(obj.getId() == 42967) {

                int count = 0;
                for (Player p : World.getWorld().getPlayers()) {
                    if (p != null && p.tile().inArea(NEX_AREA))
                        count++;
                }

                if (count == 0) {
                    DialogueManager.sendStatement(player, "You peek inside the barrier and see no adventurers inside.");
                } else {
                    DialogueManager.sendStatement(player, "You peek inside the barrier and see " + count + " adventurers inside.");
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onLogout(Player player) {
        if(player.tile().inArea(NEX_AREA)) {
            player.teleport(2904, 5203,0);
            return true;
        }
        return false;
    }
}

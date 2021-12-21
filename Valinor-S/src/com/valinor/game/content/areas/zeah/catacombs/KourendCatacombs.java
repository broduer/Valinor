package com.valinor.game.content.areas.zeah.catacombs;

import com.valinor.game.content.packet_actions.interactions.objects.Ladders;
import com.valinor.game.content.skill.impl.prayer.Bone;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.Skotizo;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen | Zerikoth | PVE
 * @date februari 29, 2020 22:03
 */
public class KourendCatacombs extends Interaction {

    private static final Area CATACOMBS_BOUNDS = new Area(1578, 9960, 1760, 10110, 0);

    @Override
    public boolean onLogout(Player player) {
        if(player.tile().region() == 6810) {
            player.teleport(1665, 10048, 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (option == 1) {
            if (obj.getId() == VINE_LADDER) {
                Ladders.ladderUp(player, new Tile(1639, 3673), true);
                return true;
            }

            if (obj.getId() == VINE_28895) {
                Ladders.ladderUp(player, new Tile(1562, 3791), true);
                return true;
            }

            if (obj.getId() == STATUE_27785) {
                Ladders.ladderDown(player, new Tile(1665, 10050), true);
                return true;
            }

            if (obj.getId() == ALTAR_28900) {
                startSkotizoFight(player, obj);
                return true;
            }

            if (obj.getId() == 28921) {
                Ladders.ladderDown(player, new Tile(1617, 10101), true);
                return true;
            }

            if (obj.getId() == VINE_28896) {
                Ladders.ladderUp(player, new Tile(1469, 3653), true);
                return true;
            }

            if (obj.getId() == 28919) {
                Ladders.ladderDown(player, new Tile(1650, 9987), true);
                return true;
            }

            if (obj.getId() == VINE_28897) {
                Ladders.ladderUp(player, new Tile(1667, 3565), true);
                return true;
            }

            if (obj.getId() == 28918) {
                Ladders.ladderDown(player, new Tile(1725, 9993), true);
                return true;
            }

            if (obj.getId() == VINE_28898) {
                Ladders.ladderUp(player, new Tile(1696, 3864), true);
                return true;
            }

            if (obj.getId() == 28920) {
                Ladders.ladderDown(player, new Tile(1719, 10101), true);
                return true;
            }

            if (obj.getId() == STONE_28893) {
                if (!player.skills().check(Skills.AGILITY, 34, "use this shortcut"))
                    return true;
                if (player.tile().equals(1613, 10069)) {
                    Chain.bound(player).name("Shortcut1Task").runFn(2, () -> {
                        player.lock();
                        player.faceObj(obj);
                        player.animate(741);
                    }).then(1, () -> player.teleport(player.tile().x, player.tile().y - 1)).then(1, () -> player.animate(741)).then(1, () -> player.teleport(player.tile().x, player.tile().y - 1)).then(1, () -> player.animate(741)).then(1, () -> player.teleport(player.tile().x, player.tile().y - 1)).then(1, () -> {
                        player.face(1612, 10066);
                        player.animate(741);
                    }).then(1, () -> player.teleport(player.tile().x - 1, player.tile().y)).then(1, () -> player.animate(741)).then(1, () -> player.teleport(player.tile().x - 1, player.tile().y)).then(1, () -> {
                        player.face(1611, 10065);
                        player.animate(741);
                    }).then(1, () -> player.teleport(player.tile().x, player.tile().y - 1)).then(1, () -> player.animate(741)).then(1, () -> player.teleport(player.tile().x, player.tile().y - 1)).then(1, () -> {
                        player.face(1610, 10064);
                        player.animate(741);
                    }).then(1, () -> player.teleport(player.tile().x - 1, player.tile().y)).then(1, () -> {
                        player.face(1610, 10063);
                        player.animate(741);
                    }).then(1, () -> player.teleport(player.tile().x, player.tile().y - 1)).then(1, () -> player.animate(741)).then(1, () -> {
                        player.teleport(player.tile().x, player.tile().y - 2);
                        player.unlock();
                    });
                } else if (player.tile().equals(1610, 10062)) {
                    Chain.bound(player).name("Shortcut2Task").runFn(2, () -> {
                        player.lock();
                        player.faceObj(obj);
                        player.animate(741);
                    }).then(1, () -> player.teleport(player.tile().x, player.tile().y + 1)).then(1, () -> player.animate(741)).then(1, () -> player.teleport(player.tile().x, player.tile().y + 1)).then(1, () -> {
                        player.face(new Tile(1611, 10064));
                        player.animate(741);
                    }).then(1, () -> player.teleport(player.tile().x + 1, player.tile().y)).then(1, () -> {
                        player.face(new Tile(1611, 10065));
                        player.animate(741);
                    }).then(1, () -> player.teleport(player.tile().x, player.tile().y + 1)).then(1, () -> player.animate(741)).then(1, () -> player.teleport(player.tile().x, player.tile().y + 1)).then(1, () -> {
                        player.face(new Tile(1612, 10066));
                        player.animate(741);
                    }).then(1, () -> player.teleport(player.tile().x + 1, player.tile().y)).then(1, () -> player.animate(741)).then(1, () -> player.teleport(player.tile().x + 1, player.tile().y)).then(1, () -> {
                        player.face(new Tile(1613, 10067));
                        player.animate(741);
                    }).then(1, () -> player.teleport(player.tile().x, player.tile().y + 1)).then(1, () -> player.animate(741)).then(1, () -> player.teleport(player.tile().x, player.tile().y + 1)).then(1, () -> player.animate(741)).then(1, () -> {
                        player.teleport(player.tile().x, player.tile().y + 2);
                        player.unlock();
                    });
                }
                return true;
            }

            if (obj.getId() == CRACK_28892 && obj.tile().equals(1706, 10077)) {
                squeezeThroughCrack(player, obj, new Tile(1716, 10056, 0), 34);
                return true;
            }

            if (obj.getId() == CRACK_28892 && obj.tile().equals(1716, 10057)) {
                squeezeThroughCrack(player, obj, new Tile(1706, 10078, 0), 34);
                return true;
            }

            if (obj.getId() == CRACK_28892 && obj.tile().equals(1646, 10001)) {
                squeezeThroughCrack(player, obj, new Tile(1648, 10009, 0), 25);
                return true;
            }

            if (obj.getId() == CRACK_28892 && obj.tile().equals(1648, 10008)) {
                squeezeThroughCrack(player, obj, new Tile(1646, 10000, 0), 25);
                return true;
            }
        }
        return false;
    }

    private static void squeezeThroughCrack(Player player, GameObject crack, Tile destination, int levelReq) {
        if (!player.skills().check(Skills.AGILITY, levelReq, "use this shortcut"))
            return;
        player.lock();
        Chain.bound(null).runFn(1, () -> {
            player.faceObj(crack);
            player.animate(746);
        }).then(1, () -> {
            player.teleport(destination);
            player.animate(748);
        }).then(1, player::unlock);
    }

    private static void startSkotizoFight(Player player, GameObject gameObject) {
        if (!player.getInventory().contains(DARK_TOTEM, 1)) {
            player.itemBox("A completed dark totem is required to activate the altar.", DARK_TOTEM);
            return;
        }

        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.STATEMENT, ""+ Color.RED.wrap("WARNING:")+" You are about to enter Skotizo's lair.", "Your dark totem will be consumed.");
                setPhase(0);
            }

            @Override
            protected void next() {
                if(isPhase(0)) {
                    stop();
                    player.optionsTitled("Are you sure you want to continue?", "Yes.", "No.", () -> {
                        if (!player.getInventory().contains(DARK_TOTEM, 1)) {
                            return;
                        }
                        player.getInventory().remove(DARK_TOTEM, 1);
                        Skotizo.startFight(player, gameObject);
                    });
                }
            }
        });
    }

    public static int getNextTotemPiece(Player player) {
        int base = 0, middle = 0, top = 0;
        for (Item item : player.inventory().getItems()) {
            if (item == null)
                continue;
            if (item.getId() == DARK_TOTEM_BASE)
                base += item.getAmount();
            else if (item.getId() == DARK_TOTEM_MIDDLE)
                middle += item.getAmount();
            else if (item.getId() == DARK_TOTEM_TOP)
                top += item.getAmount();
        }
        for (Item item : player.getBank().getItems()) {
            if (item == null)
                continue;
            if (item.getId() == DARK_TOTEM_BASE)
                base += item.getAmount();
            else if (item.getId() == DARK_TOTEM_MIDDLE)
                middle += item.getAmount();
            else if (item.getId() == DARK_TOTEM_TOP)
                top += item.getAmount();
        }

        int lowest = Math.min(base, Math.min(middle, top));
        if (lowest == base)
            return DARK_TOTEM_BASE;
        else if (lowest == middle)
            return DARK_TOTEM_MIDDLE;
        else return DARK_TOTEM_TOP;
    }

    public static void drop(Player pKiller, Npc npc, Tile tile) {
        if (!npc.tile().inArea(CATACOMBS_BOUNDS))
            return;
        if (rollTotemDrop(npc)) {
            int nextPiece = getNextTotemPiece(pKiller);
            if (nextPiece > 0) {
                GroundItem item = new GroundItem(new Item(nextPiece,1), tile, pKiller);
                GroundItemHandler.createGroundItem(item);
            }
        }
        if (rollShardDrop(npc)) {
            GroundItem item = new GroundItem(new Item(19677, 1), tile, pKiller);
            GroundItemHandler.createGroundItem(item);
        }
    }

    private static boolean rollTotemDrop(Npc npc) {
        double chance = 1d / (200 - Math.min(200, npc.maxHp()));
        return World.getWorld().get() <= chance;
    }

    private static boolean rollShardDrop(Npc npc) {
        double chance = 1d / (2d/3 * (200 - Math.min(200, npc.maxHp())));
        return World.getWorld().get() <= chance;
    }

    public static void buriedBone(Player player, Bone bone) {
        if (!player.tile().inArea(CATACOMBS_BOUNDS))
            return;
        if (bone.xp < 15)
            player.skills().alterSkill(Skills.PRAYER,1);
        else if (bone.xp < 72)
            player.skills().alterSkill(Skills.PRAYER,2);
        else
            player.skills().alterSkill(Skills.PRAYER,4);
    }
}

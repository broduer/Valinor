package com.valinor.game.content.members;

import com.valinor.GameServer;
import com.valinor.game.content.clan.Clan;
import com.valinor.game.content.clan.ClanRepository;
import com.valinor.game.content.instance.InstancedAreaManager;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.NpcIdentifiers;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import java.util.Arrays;

import static com.valinor.util.CustomNpcIdentifiers.*;
import static com.valinor.util.NpcIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * mei 19, 2020
 */
public class MemberZone extends Interaction {

    public static boolean canAttack(Mob attacker, Mob target) {
        if (attacker.isPlayer() && target.isNpc()) {
            Player player = (Player) attacker;
            Npc npc = (Npc) target;

            var ruby_member = player.getMemberRights().isRubyMemberOrGreater(player);
            var diamondMember = player.getMemberRights().isDiamondMemberOrGreater(player);
            var dragonstoneMember = player.getMemberRights().isDragonstoneMemberOrGreater(player);
            var onyxMember = player.getMemberRights().isOnyxMemberOrGreater(player);

            //Make sure we're in the member zone
            if (player.tile().memberCave()) {
                if ((npc.id() == ANCIENT_REVENANT_DARK_BEAST || npc.id() == ANCIENT_REVENANT_ORK || npc.id() == ANCIENT_REVENANT_CYCLOPS || npc.id() == ANCIENT_REVENANT_DRAGON || npc.id() == ANCIENT_REVENANT_KNIGHT) && !ruby_member) {
                    player.getCombat().reset();
                    player.message(Color.RED.wrap("You need to be at least a ruby member to attack ancient revenants."));
                    return false;
                }

                if ((npc.id() == ANCIENT_BARRELCHEST || npc.id() == ANCIENT_CHAOS_ELEMENTAL || npc.id() == ANCIENT_KING_BLACK_DRAGON) && !diamondMember) {
                    player.getCombat().reset();
                    player.message(Color.RED.wrap("You need to be at least a diamond member to attack ancient bosses."));
                    return false;
                }

                if ((npc.id() == CRYSTALLINE_HUNLLEF || npc.id() == CRYSTALLINE_HUNLLEF_9022 || npc.id() == CRYSTALLINE_HUNLLEF_9023) && !onyxMember) {
                    player.getCombat().reset();
                    player.message(Color.RED.wrap("You need to be at least a onyx member to attack the Crystalline Hunleff."));
                    return false;
                }

                if (npc.id() == BLOOD_FURY_HESPORI && !dragonstoneMember) {
                    player.getCombat().reset();
                    player.message(Color.RED.wrap("You need to be at least a dragonstone member to attack the Bloodfury hespori."));
                    return false;
                }

                if (npc.id() == NpcIdentifiers.FRAGMENT_OF_SEREN && !diamondMember) {
                    player.getCombat().reset();
                    player.message(Color.RED.wrap("You need to be at least a diamond member to attack the Fragment of seren."));
                    return false;
                }

                if (npc.id() == INFERNAL_SPIDER && !dragonstoneMember) {
                    player.getCombat().reset();
                    player.message(Color.RED.wrap("You need to be at least a dragonstone member to attack the Infernal spider."));
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (option == 1) {
            if (obj.getId() == PORTAL_33037 && obj.tile().equals(3057,2918)) {
                Tile tile = new Tile(3038, 2956);
                if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    return true;
                }
                Teleports.basicTeleport(player, tile);
                return true;
            } else if (obj.getId() == PORTAL_33037 && obj.tile().equals(3033,2954)) {
                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... parameters) {
                        send(DialogueType.OPTION, "Which member cave would you like to enter?", "Safe cave. (Diamond member+)", "Wilderness cave.", "Neither.");
                        setPhase(0);
                    }

                    @Override
                    protected void select(int option) {
                        if(isPhase(0)) {
                            if(option == 1) {
                                if (!player.getMemberRights().isDiamondMemberOrGreater(player)) {
                                    player.message(Color.RED.wrap("You need to be at least an Diamond member to enter the safe member cave."));
                                    stop();
                                    return;
                                }
                                Tile tile = new Tile(3056, 2922);
                                if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                    stop();
                                    return;
                                }
                                Teleports.basicTeleport(player, tile);
                                stop();
                            }
                            if(option == 2) {
                                stop();
                                Tile tile = new Tile(2335, 9795);

                                player.optionsTitled("Dangerous teleport! Would you like to proceed?", "Yes.", "No.", () -> {

                                    if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                        return;
                                    }

                                    player.getDialogueManager().start(new Dialogue() {
                                        @Override
                                        protected void start(Object... parameters) {
                                            send(DialogueType.STATEMENT, "This teleport will send you to a dangerous area.", "Do you wish to continue?");
                                            setPhase(1);
                                        }

                                        @Override
                                        protected void next() {
                                            if (isPhase(1)) {
                                                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Yes.", "No.");
                                                setPhase(2);
                                            }
                                        }

                                        @Override
                                        protected void select(int option) {
                                            if (option == 1) {
                                                if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                                    stop();
                                                    return;
                                                }
                                                Teleports.basicTeleport(player, tile);
                                            } else if (option == 2) {
                                                stop();
                                            }
                                        }
                                    });
                                });
                            }
                        }
                    }
                });
            }
            if (obj.getId() == CAVE_ENTRANCE_31606 || obj.getId() == PORTAL_OF_LEGENDS) {
                if (!player.getMemberRights().isSapphireMemberOrGreater(player)) {
                    player.message(Color.RED.wrap("You need to be at least an Member to enter the member zone."));
                    return true;
                }

                Tile tile = new Tile(2335, 9795);

                player.optionsTitled("Dangerous teleport! Would you like to proceed?", "Yes.", "No.", () -> {

                    if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        return;
                    }

                    player.getDialogueManager().start(new Dialogue() {
                        @Override
                        protected void start(Object... parameters) {
                            send(DialogueType.STATEMENT, "This teleport will send you to a dangerous area.", "Do you wish to continue?");
                            setPhase(1);
                        }

                        @Override
                        protected void next() {
                            if (isPhase(1)) {
                                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Yes.", "No.");
                                setPhase(2);
                            }
                        }

                        @Override
                        protected void select(int option) {
                            if (option == 1) {
                                if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                    stop();
                                    return;
                                }
                                Teleports.basicTeleport(player, tile);
                            } else if (option == 2) {
                                stop();
                            }
                        }
                    });
                });
                return true;
            }
            if (obj.getId() == EXIT_30844) {
                //Check to see if the player is teleblocked
                if (player.getTimers().has(TimerKey.TELEBLOCK)) {
                    player.teleblockMessage();
                    return true;
                }

                if (player.getMemberRights().isSapphireMemberOrGreater(player)) {
                    Teleports.basicTeleport(player, new Tile(2457, 2858));
                } else {
                    Teleports.basicTeleport(player, GameServer.properties().defaultTile);
                }
                return true;
            }
            if (obj.getId() == PORTAL_OF_HEROES) {
                Tile tile = new Tile(3299, 3918);

                if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    return true;
                }

                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... parameters) {
                        send(DialogueType.STATEMENT, "This teleport will send you to a dangerous area.", "Do you wish to continue?");
                        setPhase(1);
                    }

                    @Override
                    protected void next() {
                        if (isPhase(1)) {
                            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Yes.", "No.");
                            setPhase(2);
                        }
                    }

                    @Override
                    protected void select(int option) {
                        if (option == 1) {
                            if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                stop();
                                return;
                            }
                            Teleports.basicTeleport(player, tile);
                            player.message("You have been teleported to level 50 wilderness.");
                        } else if (option == 2) {
                            stop();
                        }
                    }
                });
                return true;
            }
            if (obj.getId() == PORTAL_OF_CHAMPIONS) {
                Tile tile = new Tile(3287, 3884);

                if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    return true;
                }

                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... parameters) {
                        send(DialogueType.STATEMENT, "This teleport will send you to a dangerous area.", "Do you wish to continue?");
                        setPhase(1);
                    }

                    @Override
                    protected void next() {
                        if (isPhase(1)) {
                            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Yes.", "No.");
                            setPhase(2);
                        }
                    }

                    @Override
                    protected void select(int option) {
                        if (option == 1) {
                            if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                stop();
                                return;
                            }
                            Teleports.basicTeleport(player, tile);
                            player.message("You have been teleported to level 50 wilderness.");
                        } else if (option == 2) {
                            stop();
                        }
                    }
                });
                return true;
            }
            if (obj.getId() == PORTAL_34752) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, GameServer.properties().defaultTile);
                }
                return true;
            }
            if (obj.getId() == STAIRS_31627) {
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    if (player.getClanChat() != null) {
                        Clan clan = ClanRepository.get(player.getClanChat());

                        if (clan != null) {
                            if (clan.meetingRoom == null) {
                                clan.meetingRoom = InstancedAreaManager.getSingleton().createInstancedArea(player, new Area(1, 2, 3, 4));
                                Npc pvpDummy = new Npc(NpcIdentifiers.COMBAT_DUMMY, new Tile(2454, 2846, 2 + clan.meetingRoom.getzLevel()));
                                pvpDummy.spawnDirection(1);
                                Npc slayerDummy = new Npc(NpcIdentifiers.UNDEAD_COMBAT_DUMMY, new Tile(2454, 2848, 2 + clan.meetingRoom.getzLevel()));
                                slayerDummy.spawnDirection(6);
                                clan.dummys = Arrays.asList(pvpDummy, slayerDummy);
                                World.getWorld().registerNpc(clan.dummys.get(0));
                                World.getWorld().registerNpc(clan.dummys.get(1));
                            }

                            Teleports.basicTeleport(player, new Tile(2452, 2847, 2 + clan.meetingRoom.getzLevel()));
                            player.message("You teleport to the " + player.getClanChat() + " clan outpost.");
                        }
                    }
                }
                return true;
            }
            if (obj.getId() == STAIRS_31610) {
                if (!player.getMemberRights().isSapphireMemberOrGreater(player)) {
                    player.message(Color.RED.wrap("You need to be at least an Member to enter the member zone."));
                    return true;
                }
                if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    Teleports.basicTeleport(player, new Tile(2457, 2858));
                }
                return true;
            }
            if (obj.getId() == PILLAR_31561) {
                player.smartPathTo(obj.tile());
                // lazy wait until we stop moving
                player.waitUntil(1, () -> !player.getMovementQueue().isMoving(), () -> {
                    if (obj.tile().equals(2356, 9841)) {
                        if (player.skills().level(Skills.AGILITY) < 91) {
                            player.message("You need an agility level of at least 91 to jump this pillar.");
                        } else {
                            player.faceObj(obj);
                            if (player.tile().equals(2356, 9839)) {
                                Chain.bound(null).runFn(1, () -> {
                                    player.animate(741, 15);
                                }).then(2, () -> {
                                    player.teleport(new Tile(2356, 9841));
                                }).then(2, () -> {
                                    player.animate(741, 15);
                                }).then(2, () -> {
                                    player.teleport(new Tile(2356, 9843));
                                });
                            } else {
                                Chain.bound(null).runFn(1, () -> {
                                    player.animate(741, 15);
                                }).then(2, () -> {
                                    player.teleport(new Tile(2356, 9841));
                                }).then(2, () -> {
                                    player.animate(741, 15);
                                }).then(2, () -> {
                                    player.teleport(new Tile(2356, 9839));
                                });
                            }
                        }
                    }
                });
                return true;
            }

            if (obj.getId() == ROW_BOAT) {
                if (!obj.tile().equals(2323, 9801)) {
                    player.message("I can't travel with this row boat.");
                    return true;
                }
                if (!player.getMemberRights().isEmeraldMemberOrGreater(player)) {
                    player.message(Color.RED.wrap("You need to be at least a super member to travel with this boat."));
                    return true;
                }
                player.teleport(new Tile(2312, 9904));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if(npc.id() == CHIEF_FARMER) {
                World.getWorld().shop(54).open(player);
                return true;
            }
        }
        return false;
    }
}

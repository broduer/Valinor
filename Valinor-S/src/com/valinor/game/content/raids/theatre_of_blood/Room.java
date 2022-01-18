package com.valinor.game.content.raids.theatre_of_blood;

import com.valinor.game.content.raids.party.Party;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.ObjectIdentifiers;

import static com.valinor.game.world.position.AreaConstants.*;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.NpcIdentifiers.VERZIK_VITUR_8369;
import static com.valinor.util.NpcIdentifiers.VERZIK_VITUR_8370;
import static com.valinor.util.ObjectIdentifiers.TREASURE_ROOM;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 07, 2022
 */
public class Room extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            Party party = player.raidsParty;
            if (party == null) {
                return false;
            }

            if (object.getId() == BARRIER_32755) {
                if(!party.canEnterBossRoom(player)) {
                    player.message("You have died and can't re-enter this fight!");
                    return true;
                }

                if (player.tile().inArea(MAIDEN)) {
                    if (player.getX() >= 3186) {
                        player.teleport(3184, player.getY(), party.getHeight());
                    } else if (player.getX() <= 3184 && party.getRaidStage() == 2) {
                        player.teleport(3186, player.getY(), party.getHeight());
                    }
                }
                if (player.tile().inArea(BLOAT)) {
                    if (player.getX() <= 3286) {
                        player.teleport(3288, player.getY(), party.getHeight());
                    } else if (player.getX() >= 3288 && player.getX() <= 3291 && party.getRaidStage() == 3) {
                        player.teleport(3286, player.getY(), party.getHeight());
                    } else if (player.getX() >= 3300 && player.getX() <= 3303 && party.getRaidStage() == 3) {
                        player.teleport(3305, player.getY(), party.getHeight());
                    } else if (player.getX() >= 3305) {
                        player.teleport(3303, player.getY(), party.getHeight());
                    }
                }
                if (player.tile().inArea(NYLOCAS)) {
                    if (player.getY() >= 4256) {
                        player.teleport(player.getX(), 4254, party.getHeight());
                    } else if (player.getY() <= 4254 && party.getRaidStage() == 4) {
                        player.teleport(player.getX(), 4256, party.getHeight());
                    }
                }
                if (player.tile().inArea(SOTETSEG)) {
                    if (player.getY() <= 4306) {
                        player.teleport(player.getX(), 4308, party.getHeight());
                    } else if (player.getY() >= 4308 && party.getRaidStage() == 5) {
                        player.teleport(player.getX(), 4306, party.getHeight());
                    }
                }
                if (player.tile().inArea(XARPUS)) {
                    if (player.getY() <= 4378) {
                        player.teleport(player.getX(), 4380, party.getHeight() + 1);
                    } else if (player.getY() >= 4380 && player.getY() <= 4385 && party.getRaidStage() == 6) {
                        player.teleport(player.getX(), 4378, party.getHeight() + 1);
                    } else if (player.getY() <= 4394 && player.getY() >= 4389 && party.getRaidStage() == 6) {
                        player.teleport(player.getX(), 4396, party.getHeight() + 1);
                    } else if (player.getY() >= 4396) {
                        player.teleport(player.getX(), 4394, party.getHeight() + 1);
                    }
                }
                return true;
            }

            if(object.getId() == FORMIDABLE_PASSAGE) {
                if (player.tile().inArea(MAIDEN)) {
                    if (party.getRaidStage() == 2) {
                        player.teleport(3322, 4448);
                    } else {
                        player.message("You must defeat Maiden before progressing!");
                    }
                }
                if (player.tile().inArea(BLOAT)) {
                    if (party.getRaidStage() == 3) {
                        player.teleport(3296, 4283);
                    } else {
                        player.message("You must defeat Bloat before progressing!");
                    }
                }
                if (player.tile().inArea(NYLOCAS)) {
                    if (party.getRaidStage() == 4) {
                        player.teleport(3291, 4328);
                    } else {
                        player.message("You must defeat Nylocas before progressing!");
                    }
                }
                if (player.tile().inArea(SOTETSEG)) {
                    if (party.getRaidStage() == 5) {
                        player.teleport(3170, 4375, party.getHeight() + 1);
                    } else {
                        player.message("You must defeat Sotetseg before progressing!");
                    }
                }
                return true;
            }
            if(object.getId() == DOOR_32751) {
                if (player.tile().inArea(XARPUS)) {
                    if (party.getRaidStage() == 6) {
                        player.teleport(3168, 4303, party.getHeight());
                    } else {
                        player.message("You must defeat Xarpus before progressing!");
                    }
                }
                return true;
            }

            if(object.getId() == SKELETON_32741) {
                boolean alreadyHas = player.getEquipment().contains(DAWNBRINGER) || player.inventory().contains(DAWNBRINGER);
                if(alreadyHas) {
                    DialogueManager.sendStatement(player, "You already have a dawnbringer.");
                    return true;
                }
                if (player.inventory().isFull()) {
                    DialogueManager.sendStatement(player, "Your inventory is too full to do this.");
                    return true;
                }
                player.inventory().add(new Item(DAWNBRINGER));
                player.message("You have found the Dawnbringer!");
                return true;
            }

            if(object.getId() == TREASURE_ROOM) {
                player.teleport(3237, 4307, party.getHeight());
                return true;
            }

            if(object.getId() == STAIRS_32995) {
                player.message("You cannot go back!");
                return true;
            }

            if(object.getId() == CHEST_32758) {
                //TODO c.getShops().openShop(135);
                return true;
            }

            if(object.getId() == ObjectIdentifiers.TELEPORT_CRYSTAL) {
                if (player.getRaids() != null) {
                    player.getRaids().exit(player);
                }
                unlockCape(player);
                return true;
            }

            if(object.getId() == MONUMENTAL_CHEST) {
                if (player.getRaidRewards().isEmpty()) {
                    player.message(Color.RED.wrap("You have already looted the chest, or your points are below 10,000."));
                    return true;
                }
                TheatreOfBloodRewards.displayRewards(player);
                TheatreOfBloodRewards.withdrawReward(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if(npc.id() == VERZIK_VITUR_8369) {
                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... parameters) {
                        send(DialogueType.NPC_STATEMENT, VERZIK_VITUR_8369, Expression.CALM_TALK, "Now that was quite the show! I haven't been that", "entertained in a long time.");
                        setPhase(0);
                    }

                    @Override
                    protected void next() {
                        if(isPhase(0)) {
                            npc.transmog(VERZIK_VITUR_8370);
                            stop();
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }

    private void unlockCape(Player player) {
        var finished = player.<Integer>getAttribOr(AttributeKey.THEATRE_OF_BLOOD_RUNS_COMPLETED, 0);
        if (finished == 10) {
            player.inventory().addOrBank(new Item(SINHAZA_SHROUD_TIER_1));
            player.message("You have been awarded a Sinhaza Shroud for completing 10 rounds!");
        }
        if (finished == 25) {
            player.inventory().addOrBank(new Item(SINHAZA_SHROUD_TIER_2));
            player.message("You have been awarded a Sinhaza Shroud for completing 25 rounds!");
        }
        if (finished == 50) {
            player.inventory().addOrBank(new Item(SINHAZA_SHROUD_TIER_3));
            player.message("You have been awarded a Sinhaza Shroud for completing 50 rounds!");
        }
        if (finished == 100) {
            player.inventory().addOrBank(new Item(SINHAZA_SHROUD_TIER_4));
            player.message("You have been awarded a Sinhaza Shroud for completing 100 rounds!");
        }
        if (finished == 250) {
            player.inventory().addOrBank(new Item(SINHAZA_SHROUD_TIER_5));
            player.message("You have been awarded a Sinhaza Shroud for completing 250 rounds!");
        }
    }
}

package com.valinor.game.content.teleport.world_teleport_manager;

import com.valinor.game.content.boss_event.WorldBossEvent;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;

import java.util.HashMap;
import java.util.List;

/**
 * This class represents the world teleport interface.
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 04, 2022
 */
public class WorldTeleportNetwork {

    private final Player player;

    public WorldTeleportNetwork(Player player) {
        this.player = player;
    }

    private static final int INTERFACE_ID = 65000;
    private static final int TELEPORT_NAME_ID = 65008;
    private static final int BEST_POSSIBLE_DROPS_CONTAINER_ID = 65014;
    private static final int MODEL_VIEWER_ID = 65015;
    private static final int MAX_HIT_ID = 65019;
    private static final int RESPAWN_TIMEFRAME_ID = 65022;
    private static final int MULTI_AREA_ID = 325;
    private static final int SLAYER_REQ_ID = 65027;
    private static final int KILLCOUNT_ID = 65030;
    private static final int SCROLLBAR_ID = 65031;
    private static final int TELEPORT_NAME_START_ID = 65036;
    private static final int MAX_TELEPORT_LOCATIONS = 30;

    public void open(Category category) {
        player.getInterfaceManager().open(INTERFACE_ID);

        final List<TeleportLocations> list = TeleportLocations.filterByCategory(category);

        //Set a scrollbar if we have more then 12 teleport locations.
        player.getPacketSender().sendScrollbarHeight(SCROLLBAR_ID, list.size() > 12 ? list.size() * 18 + 5 : 0);

        //Clear old strings first
        for (int index = TELEPORT_NAME_START_ID; index < TELEPORT_NAME_START_ID + MAX_TELEPORT_LOCATIONS; index+= 2) {
            player.getPacketSender().sendString(index, "");
        }

        //Write the teleport locations
        for (int index = 0; index < list.size(); index++) {
            TeleportLocations teleport = list.get(index);
            //System.out.println(teleport.name());
            player.getPacketSender().sendString(TELEPORT_NAME_START_ID + (index * 2), Utils.formatEnum(teleport.name()));
        }
    }

    private void clearDescription(Player player) {
        //Clear text, so we can fill in our own description
        for (int index = 65017; index <= 65030; index++) {
            player.getPacketSender().sendString(index, "");
        }

        //Clear all icons
        for (int config = 325; config <= 329; config++) {
            player.getPacketSender().sendConfig(config, 0);
        }
    }

    private void fillDescription(Player player) {
        //Activate icons
        for (int config = 325; config <= 329; config++) {
            player.getPacketSender().sendConfig(config, 1);
        }

        //Write strings
        player.getPacketSender().sendString(65017, "- Max hit:");
        player.getPacketSender().sendString(65020, "- Respawn:");
        player.getPacketSender().sendString(65023, "- Multi:");
        player.getPacketSender().sendString(65025, "- Slayer req:");
        player.getPacketSender().sendString(65028, "- Killcount:");
    }

    public void sendTeleport(TeleportLocations teleport) {
        final int npc = teleport.id();
        //Update the teleport name
        player.getPacketSender().sendString(TELEPORT_NAME_ID, Utils.formatEnum(teleport.name()));

        //Send the npc models to the interface
        player.getPacketSender().sendNpcModel(MODEL_VIEWER_ID, npc, teleport.zoom());

        switch(teleport.category()) {
            case PVM -> player.getPacketSender().sendString(65007, "PvM");

            case TRAINING -> player.getPacketSender().sendString(65007, "Training");

            case PKING -> player.getPacketSender().sendString(65007, "Pking");
        }

        if(teleport.loadConfigs()) {
            fillDescription(player);

            final int maxHit = World.getWorld().combatInfo(npc).maxhit;
            final int respawnTime = World.getWorld().combatInfo(npc).respawntime;
            final boolean inMultiArea = teleport.multiArea();
            final int slayerRequirement = World.getWorld().combatInfo(npc).slayerlvl;

            int skotizo = player.getAttribOr(AttributeKey.SKOTIZOS_KILLED, 0);
            int tekton = player.getAttribOr(AttributeKey.SKOTIZOS_KILLED, 0);
            int zc = player.getAttribOr(AttributeKey.SKOTIZOS_KILLED, 0);
            final int worldBossKillCount = skotizo + tekton + zc;
            final int killcount = player.getAttribOr(teleport.killcountKey(), 0);

            //Update the description box
            player.getPacketSender().sendString(MAX_HIT_ID, "" + maxHit);
            player.getPacketSender().sendString(RESPAWN_TIMEFRAME_ID, "" + Utils.getSeconds(respawnTime) + " seconds");
            player.getPacketSender().sendConfig(MULTI_AREA_ID, inMultiArea ? 1 : 0);
            player.getPacketSender().sendString(SLAYER_REQ_ID, "" + slayerRequirement);
            int kc = teleport == TeleportLocations.WORLD_BOSS ? worldBossKillCount : killcount;
            player.getPacketSender().sendString(KILLCOUNT_ID, "" + kc);
        } else {
            clearDescription(player);

            StringBuilder stringBuilder = new StringBuilder();

            if (teleport.description() != null) {
                for (String s : teleport.description()) {
                    stringBuilder.append(s).append("<br>");
                }
            }

            player.getPacketSender().sendString(65019, stringBuilder.toString());
        }

        //Send the possible drops to the interface
        player.getPacketSender().sendItemOnInterface(BEST_POSSIBLE_DROPS_CONTAINER_ID, teleport.bestPossibleDrops());
    }

    /**
     * Stores the boss teleport buttons.
     */
    private final HashMap<Integer, TeleportLocations> BOSSES = new HashMap<>();
    private final HashMap<Integer, TeleportLocations> PVM_AND_TRAINING = new HashMap<>();
    private final HashMap<Integer, TeleportLocations> PKING = new HashMap<>();

    {
        int button;
        button = 65034;//Start index

        //Populate the boss buttons map
        for (final TeleportLocations teleport : TeleportLocations.filterByCategory(Category.PVM)) {
            BOSSES.put(button+=2, teleport);
        }
        button = 65034;
        //Populate the pvm and training buttons map
        for (final TeleportLocations teleport : TeleportLocations.filterByCategory(Category.TRAINING)) {
            PVM_AND_TRAINING.put(button+=2, teleport);
        }
        button = 65034;
        //Populate the pking buttons map
        for (final TeleportLocations teleport : TeleportLocations.filterByCategory(Category.PKING)) {
            PKING.put(button+=2, teleport);
        }
    }

    public boolean onButton(int buttonId) {
        int category_open = player.getAttribOr(AttributeKey.CATEGORY_OPEN,0);
        int teleportButton = player.getAttribOr(AttributeKey.CURRENT_SELECTED_TELEPORT,65036);
        if(category_open == 0) {
            if (BOSSES.containsKey(buttonId)) {
                sendTeleport(BOSSES.get(buttonId));
                player.putAttrib(AttributeKey.CURRENT_SELECTED_TELEPORT, buttonId);
                return true;
            }
        } else if(category_open == 1) {
            if (PVM_AND_TRAINING.containsKey(buttonId)) {
                sendTeleport(PVM_AND_TRAINING.get(buttonId));
                player.putAttrib(AttributeKey.CURRENT_SELECTED_TELEPORT, buttonId);
                return true;
            }
        } else if(category_open == 2) {
            if (PKING.containsKey(buttonId)) {
                sendTeleport(PKING.get(buttonId));
                player.putAttrib(AttributeKey.CURRENT_SELECTED_TELEPORT, buttonId);
                return true;
            }
        }

        if (buttonId == 30083 || buttonId == 1170 || buttonId == 13053) {
            open(Category.PVM);
            player.putAttrib(AttributeKey.CATEGORY_OPEN, 0);
            //Opens the first teleport in the list
            sendTeleport(TeleportLocations.ADAMANT_DRAGON);
            return true;
        }

        if (buttonId == 30075 || buttonId == 1167 || buttonId == 13045) {
            open(Category.TRAINING);
            player.putAttrib(AttributeKey.CATEGORY_OPEN, 1);
            //Opens the first teleport in the list
            sendTeleport(TeleportLocations.AREA_FOR_SKILLING);
            return true;
        }

        if (buttonId == 30106 || buttonId == 1174 || buttonId == 13061) {
            open(Category.PKING);
            player.putAttrib(AttributeKey.CATEGORY_OPEN, 2);
            //Opens the first teleport in the list
            sendTeleport(TeleportLocations.BANDIT_CAMP);
            return true;
        }

        //The teleport button
        if (buttonId == 65003) {
            TeleportLocations teleport = category_open == 0 ? BOSSES.get(teleportButton) : category_open == 1 ? PVM_AND_TRAINING.get(teleportButton) : PKING.get(teleportButton);

            //We have a teleport selected
            if (teleport != null) {
                //Check for warning

                Tile tile = teleport.tile();

                /*List<TeleportLocations> locations = List.of(TeleportLocations.BANDIT_CAMP, TeleportLocations.CHAOS_TEMPLE, TeleportLocations.EAST_DRAGONS, TeleportLocations.WEST_DRAGONS, TeleportLocations.GRAVEYARD, TeleportLocations.MAGE_BANK, TeleportLocations.THE_GATE);

                boolean isPkTeleport = locations.stream().anyMatch(teleport::equals);
                if (isPkTeleport && !Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                    return true;
                }*/

                if(teleport == TeleportLocations.GODWARS_DUNGEON) {
                    player.getDialogueManager().start(new Dialogue() {
                        @Override
                        protected void start(Object... parameters) {
                            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Commander Zilyana", "General Graardor.", "Kree'Arra.", "K'ril Tsutsaroth", "Nevermind.");
                            setPhase(1);
                        }

                        @Override
                        protected void select(int option) {
                            if (option == 1) {
                                if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                    stop();
                                    return;
                                }
                                Teleports.basicTeleport(player, new Tile(2911, 5267, 0));
                            } else if (option == 2) {
                                if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                    stop();
                                    return;
                                }
                                Teleports.basicTeleport(player, new Tile(2860, 5354, 2));
                            } else if (option == 3) {
                                if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                    stop();
                                    return;
                                }
                                Teleports.basicTeleport(player, new Tile(2841, 5291, 2));
                            } else if (option == 4) {
                                if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                    stop();
                                    return;
                                }
                                Teleports.basicTeleport(player, new Tile(2925, 5336, 2));
                            } else if (option == 5) {
                                stop();
                            }
                        }
                    });
                    return true;
                }

                if(teleport == TeleportLocations.WORLD_BOSS) {
                    if(!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.WORLD_BOSS_TELEPORT)) {
                        player.message("You do not meet the requirements to use this teleport.");
                        return true;
                    }

                    if (WorldBossEvent.getINSTANCE().getActiveNpc().isPresent() && WorldBossEvent.currentSpawnPos != null) {
                        tile = WorldBossEvent.currentSpawnPos;
                    } else {
                        player.message("The world boss recently died and will respawn shortly.");
                        return true;
                    }
                }

                if(teleport == TeleportLocations.REVENANT_CAVES) {
                    if(player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.REVENANT_TELEPORT)) {
                        tile = new Tile(3244,10145,0);
                    }
                }

                if (!teleport.warning()) {
                    if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                        return false;
                    }
                    Teleports.basicTeleport(player, tile);
                } else {
                    Tile finalTile = tile;
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
                                Teleports.basicTeleport(player, finalTile);
                            } else if (option == 2) {
                                stop();
                            }
                        }
                    });
                }
            }
            return true;
        }
        return false;
    }
}

package com.valinor.game.world;

import com.valinor.game.content.seasonal_events.halloween.Halloween;
import com.valinor.game.content.seasonal_events.winter.WinterEvent;
import com.valinor.game.world.items.Item;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.valinor.GameServer;
import com.valinor.fs.DefinitionRepository;
import com.valinor.fs.NpcDefinition;
import com.valinor.game.GameConstants;
import com.valinor.game.GameEngine;
import com.valinor.game.TimesCycle;
import com.valinor.game.content.areas.burthope.warriors_guild.dialogue.Shanomi;
import com.valinor.game.content.minigames.MinigameManager;
import com.valinor.game.content.skill.impl.fishing.Fishing;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.NodeType;
import com.valinor.game.world.entity.combat.method.impl.npcs.slayer.kraken.KrakenBoss;
import com.valinor.game.world.entity.combat.skull.Skulling;
import com.valinor.game.world.entity.masks.updating.NPCUpdating;
import com.valinor.game.world.entity.masks.updating.PlayerUpdating;
import com.valinor.game.world.entity.mob.MobList;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.NpcCombatInfo;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.PlayerPerformanceTracker;
import com.valinor.game.world.entity.mob.sync.GameSyncExecutor;
import com.valinor.game.world.entity.mob.sync.GameSyncTask;
import com.valinor.game.world.items.ItemWeight;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.game.world.items.container.shop.Shop;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.OwnedObject;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.region.Flags;
import com.valinor.game.world.region.Region;
import com.valinor.game.world.region.RegionManager;
import com.valinor.util.*;
import kotlin.ranges.IntRange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.valinor.util.NpcIdentifiers.SHANOMI;

/**
 * Represents the world, processing it and its mobs.
 *
 * @author Professor Oak
 * @author lare96
 */
public class World {

    public static boolean SYNCMODE1 = false;
    public final Tile HOME = Tile.of(2028, 3577, 0);
    public final Tile EDGEHOME = Tile.of(3085, 3492, 0);

    private static final Logger logger = LogManager.getLogger(World.class);

    public World() {
        definitionRepository = GameServer.definitions();
        examineRepository = new ExamineRepository(definitionRepository);
    }

    /**
     * World instance.
     */
    private static final World world = new World();

    /**
     * Gets the world instance.
     *
     * @return The world instance.
     */
    public static World getWorld() {
        return world;
    }

    public Map<Integer, Shop> shops = new HashMap<>();

    public Shop shop(int id) {
        return shops.get(id);
    }

    public static long LAST_FLUSH;

    /**
     * The collection of active {@link Player}s.
     */
    private final MobList<Player> players = new MobList<>(GameConstants.PLAYERS_LIMIT);

    /**
     * The collection of active {@link Npc}s. Be careful when adding NPCs directly to the list without using the queue, try not to bypass the queue.
     */
    private final MobList<Npc> npcs = new MobList<>(GameConstants.NPCS_LIMIT);

    /**
     * The collection of active {@link GameObject}s..
     */
    private final List<GameObject> spawnedObjs = new LinkedList<>();
    private final List<GameObject> removedObjs = new LinkedList<>();

    /**
     * The manager for game synchronization.
     */
    private static final GameSyncExecutor executor = new GameSyncExecutor();

    /**
     * The sections of the World sequence, used for logging successful completion of the World sequence.
     */
    public boolean[] section = new boolean[11];

    private final Calendar calendar = new GregorianCalendar();

    public Calendar getCalendar() {
        return calendar;
    }

    private boolean applyWeekendDoubleExperience() {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return (day == 5 || day == 6 || day == 7) && GameServer.properties().doubleExperienceEvent;
    }

    private boolean applyWeekendDoubleSlayerRewardPoints() {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return (day == 5 || day == 6 || day == 7) && GameServer.properties().doubleSlayerRewardPointsEvent;
    }

    private boolean applyWeekendDoublePKP() {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return (day == 5 || day == 6 || day == 7) && GameServer.properties().doublePKPEvent;
    }

    public boolean doubleVotePoints() {
        return isFirstWeekofMonth() && GameServer.properties().doubleVotePointsEvent;
    }

    private boolean isFirstWeekofMonth() {
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth <= 7;
    }

    /**
     * Timing
     */
    public long currentTick() {
        return cycleCount();
    }

    public long getEnd(long ticks) {
        return currentTick() + ticks;
    }

    public boolean isPast(long end) {
        return currentTick() >= end;
    }

    public int xpMultiplier = applyWeekendDoubleExperience() || GameServer.properties().doubleExperienceEvent ? 2 : 1;
    public int slayerRewardPointsMultiplier = applyWeekendDoubleSlayerRewardPoints() || GameServer.properties().doubleSlayerRewardPointsEvent ? 2 : 1;
    public int pkpMultiplier = applyWeekendDoublePKP() || GameServer.properties().doublePKPEvent ? 2 : 1;

    private int elapsedTicks;

    private int lastPidUpdateTick;

    private long lastMinuteScan;

    private final DefinitionRepository definitionRepository;

    public DefinitionRepository definitions() {
        return definitionRepository;
    }

    private final ExamineRepository examineRepository;

    public ExamineRepository examineRepository() {
        return examineRepository;
    }

    protected final Map<String, OwnedObject> ownedObjects = Maps.newConcurrentMap();

    public <T> T randomTypeOfList(List<T> list) {
        if(list == null || list.size() == 0)
            return null;
        return list.get(new SecureRandom().nextInt(list.size()));
    }

    public static class WorldPerfTracker {
        public long skulls, tasks, login, logout, objects, packets, players,
            npcs, gpi, flush, reset, games;

        // track totals for all 2048 players this cycle
        public PlayerPerformanceTracker allPlayers = new PlayerPerformanceTracker();

        public void reset() {
            skulls = tasks = login = logout = objects = packets = players = npcs =
                gpi = flush = reset = games = 0;
        }

        static final DecimalFormat df = new DecimalFormat("#.##");

        @Override
        public String toString() {
            return breakdown();
        }

        public String breakdown() {
            if (!TimesCycle.BENCHMARKING_ENABLED) return "-";
            StringBuilder sb2 = new StringBuilder();
            if ((int) (1. * skulls / 1_000_000.) > 0)
                sb2.append(String.format("skulls:%s ms, ", df.format(1. * skulls / 1_000_000.)));
            if ((int) (1. * tasks / 1_000_000.) > 0)
                sb2.append(String.format("tasks:%s ms, ", df.format(1. * tasks / 1_000_000.)));
            if ((int) (1. * login / 1_000_000.) > 0)
                sb2.append(String.format("login:%s ms, ", df.format(1. * login / 1_000_000.)));
            if ((int) (1. * objects / 1_000_000.) > 0)
                sb2.append(String.format("objects:%s ms, ", df.format(1. * objects / 1_000_000.)));
            if ((int) (1. * packets / 1_000_000.) > 0)
                sb2.append(String.format("packets:%s ms, ", df.format(1. * packets / 1_000_000.)));
            if ((int) (1. * players / 1_000_000.) > 0)
                sb2.append(String.format("players:%s ms, ", df.format(1. * players / 1_000_000.)));
            if ((int) (1. * npcs / 1_000_000.) > 0)
                sb2.append(String.format("npcs:%s ms, ", df.format(1. * npcs / 1_000_000.)));
            if ((int) (1. * gpi / 1_000_000.) > 0)
                sb2.append(String.format("gpi:%s ms, ", df.format(1. * gpi / 1_000_000.)));
            if ((int) (1. * flush / 1_000_000.) > 0)
                sb2.append(String.format("flush:%s ms, ", df.format(1. * flush / 1_000_000.)));
            if ((int) (1. * reset / 1_000_000.) > 0)
                sb2.append(String.format("reset:%s ms, ", df.format(1. * reset / 1_000_000.)));
            if ((int) (1. * games / 1_000_000.) > 0)
                sb2.append(String.format("games:%s ms, ", df.format(1. * games / 1_000_000.)));

            if (sb2.toString().length() > 0)
                return sb2 + allPlayers.breakdown();
            return "nil";
        }
    }

    List<Integer> playerRenderOrder;

    List<Integer> npcRenderOrder;

    public WorldPerfTracker benchmark = new WorldPerfTracker();

    Runnable skull = () -> {

        //Temporary minute check until a better system is created.
        if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - lastMinuteScan) >= 60) {
            players.forEach(p -> {
                if (Skulling.skulled(p)) {
                    Skulling.decrementSkullCycle(p);
                }
            });

            lastMinuteScan = System.currentTimeMillis();
        }
    }, tasks = () -> {

        //Process all active {@link Task}s..
        TaskManager.sequence();
    }, objs = () -> {

        for (OwnedObject object : ownedObjects.values()) {
            try {
                object.tick();
            } catch (Throwable t) {
                logger.catching(t);
            }
        }
    }, packets = () -> {
        executor.sync(new GameSyncTask(NodeType.PLAYER, false, playerRenderOrder) {
            @Override
            public void execute(int index) {
                Player player = players.get(index);
                try {
                    // Process incoming packets...
                    player.getSession().handleQueuedPackets();
                    player.syncContainers();
                    player.getSession().flush();
                } catch (Exception e) {
                    logger.catching(e);
                    player.requestLogout();
                }
            }
        });
    }, playerProcess = () -> {
        executor.sync(new GameSyncTask(NodeType.PLAYER, false, playerRenderOrder) {
            @Override
            public void execute(int index) {
                Player player = players.get(index);
                try {
                    player.processed = true;
                    player.sequence();
                    player.syncContainers();
                } catch (Exception e) {
                    logger.catching(e);
                    player.requestLogout();
                }
            }
        });
    }, npcProcess = () -> {
        NpcPerformance.resetWorldTime();
        executor.sync(new GameSyncTask(NodeType.NPC, false, npcRenderOrder) {
            @Override
            public void execute(int index) {
                Npc npc = npcs.get(index);
                if (RegionManager.getRegion(npc.getX(), npc.getY()) == null) {
                    //System.err.println("region "+npc.getPosition().region()+" missing @ "+npc.getPosition());
                    return;
                }
                try {
                    if (npc != null) {
                        npc.processed = true;
                        npc.sequence();
                        npc.inViewport(false); //Assume viewport is false, we set it in NPC Updating below.
                    }
                } catch (Exception e) {
                    logger.error("Error processing logic for NPC: {}. cb={}", npc, npc.getCombat());
                    logger.catching(e);
                    World.getWorld().getNpcs().remove(npc);
                }
            }
        });
    }, gpi = () -> {
        executor.sync(new GameSyncTask(NodeType.PLAYER, playerRenderOrder) {
            @Override
            public void execute(int index) {
                Player player = players.get(index);

                if (World.SYNCMODE1) {
                    synchronized (player) {
                        try {
                            PlayerUpdating.update(player);
                            NPCUpdating.update(player);
                            if (GameServer.broadcast != null) {
                                player.getPacketSender().sendBroadcast(GameServer.broadcast);
                            }
                        } catch (Exception e) {
                            logger.catching(e);
                            player.requestLogout();
                        }
                    }
                } else {
                    try {
                        PlayerUpdating.update(player);
                        NPCUpdating.update(player);
                        if (GameServer.broadcast != null) {
                            player.getPacketSender().sendBroadcast(GameServer.broadcast);
                        }
                    } catch (Exception e) {
                        logger.catching(e);
                        player.requestLogout();
                    }
                }
            }
        });
    }, flush = () -> {
        executor.sync(new GameSyncTask(NodeType.PLAYER, false, playerRenderOrder) {
            @Override
            public void execute(int index) {
                Player player = players.get(index);
                try {
                    player.resetUpdating();
                    player.clearAttrib(AttributeKey.CACHED_PROJECTILE_STATE);
                    player.setCachedUpdateBlock(null);
                    player.getSession().flush();
                    player.perf.reset();
                    player.processed = false;
                } catch (Exception e) {
                    logger.catching(e);
                    player.requestLogout();
                }
            }
        });
    }, reset = () -> {
        executor.sync(new GameSyncTask(NodeType.NPC, false, npcRenderOrder) {
            @Override
            public void execute(int index) {
                Npc npc = npcs.get(index);
                try {
                    npc.resetUpdating();
                    npc.clearAttrib(AttributeKey.CACHED_PROJECTILE_STATE);
                    npc.processed = false;
                } catch (Exception e) {
                    logger.catching(e);
                    World.getWorld().getNpcs().remove(npc);
                }
            }
        });
    }, games = () -> {

        MinigameManager.onTick();
    };

    /**
     * Processes the world.
     */
    public void sequence() {
        Arrays.fill(section, false);

        long startTime = System.currentTimeMillis();

        Mob.time(t -> benchmark.skulls += t.toNanos(), skull);
        Mob.time(t -> benchmark.tasks += t.toNanos(), tasks);
        Mob.time(t -> benchmark.objects += t.toNanos(), objs);

        //Handle synchronization tasks.
        if (GameServer.properties().enablePidShuffling && (lastPidUpdateTick == 0 || elapsedTicks - lastPidUpdateTick >= GameServer.properties().pidIntervalTicks)) {
            //logger.info("Shuffling PID");
            //Misc.sendDiscordInfoLog("Shuffling PID");
            lastPidUpdateTick = elapsedTicks;
            players.shuffleRenderOrder();
        }

        playerRenderOrder = players.getRenderOrder();
        npcRenderOrder = npcs.getRenderOrder();

        Mob.time(t -> GameEngine.profile.wp.player_process += t.toMillis(), () -> {
            Mob.time(t -> benchmark.packets += t.toNanos(), packets);
            Mob.time(t -> benchmark.players += t.toNanos(), playerProcess);
        });

        Mob.time(t -> {
            benchmark.npcs += t.toNanos();
            GameEngine.profile.wp.npc_process = t.toMillis();
        }, npcProcess);

        Mob.time(t -> {
            benchmark.gpi += t.toNanos();
            GameEngine.profile.wp.player_npc_updating = t.toMillis();
        }, gpi);

        Mob.time(t -> benchmark.flush += t.toNanos(), flush);
        Mob.time(t -> benchmark.reset += t.toNanos(), reset);
        Mob.time(t -> benchmark.games += t.toNanos(), games);

        GameEngine.profile.world = System.currentTimeMillis() - startTime;
        elapsedTicks++;
    }

    /**
     * Gets a player by their username, using {@link String#equalsIgnoreCase(String)}. case-sensitive usercase Does Not enforce uniqueness.
     * <br> by using ignoreCase we can be sure we get a player even if they have a capitalized name like HITEST.
     *
     * @param username The username of the player.
     * @return The player with the matching username.
     */
    public Optional<Player> getPlayerByName(String username) {
        return players.search(p -> p != null && p.getUsername().equalsIgnoreCase(username));
    }

    public void clearBroadcast() {
        sendBroadcast("");
    }

    public void sendBroadcast(String broadcast) {
        sendBroadcast(broadcast, false, "no_link");
    }

    public void sendBroadcast(String broadcast, boolean hasUrl, String link) {
        if (hasUrl)
            World.getWorld().sendWorldMessage("osrsbroadcast##" + broadcast + "%%" + link);
        else
            World.getWorld().sendWorldMessage("osrsbroadcast##" + broadcast + "%%" + "no_link");
    }

    /**
     * Broadcasts a message to all players in the game.
     *
     * @param message The message to broadcast.
     */
    public void sendWorldMessage(String message) {
        players.forEach(p -> p.getPacketSender().sendMessage(message));
    }

    /**
     * Broadcasts a message to all staff-members in the game.
     *
     * @param message The message to broadcast.
     */
    public void sendStaffMessage(String message) {
        players.stream().filter(p -> !Objects.isNull(p) && p.getPlayerRights().isStaffMember(p)).forEach(p -> p.getPacketSender().sendMessage(message));
    }

    public MobList<Player> getPlayers() {
        return players;
    }

    public int getRegularPlayers() {
        int regular_players = 0;

        for (Player player : players) {
            if (player != null && !player.getPlayerRights().isStaffMember(player)) {
                regular_players++;
            }
        }

        return regular_players;
    }

    public MobList<Npc> getNpcs() {
        return npcs;
    }

    public List<GameObject> getSpawnedObjs() {
        return spawnedObjs;
    }

    public List<GameObject> getRemovedObjs() {
        return removedObjs;
    }

    public com.valinor.game.service.LoginService ls = new com.valinor.game.service.LoginService();

    public int cycleCount() {
        return elapsedTicks;
    }

    public boolean registerNpc(Npc npc) {
        npcs.add(npc);
        Tile.occupy(npc);
        npc.spawnStack = new Throwable().getStackTrace()[1].toString();
        return true;
    }

    public void unregisterNpc(Npc npc) {
        npcs.remove(npc);
    }

    private EquipmentInfo equipmentInfo;

    public EquipmentInfo equipmentInfo() {
        return equipmentInfo;
    }

    public void loadEquipmentInfo() {
        equipmentInfo = new EquipmentInfo(
            new File("data/list/equipment_info.json"),
            new File("data/list/renderpairs.txt"),
            new File("data/list/bonuses.json"),
            new File("data/list/weapon_types.txt"),
            new File("data/list/weapon_speeds.txt"));
    }

    public NpcCombatInfo combatInfo(int id) {
        return id > combatInfo.length - 1 ? null : combatInfo[id];
    }

    private NpcCombatInfo[] combatInfo;

    public void loadNpcCombatInfo() {
        combatInfo = new NpcCombatInfo[definitionRepository.total(NpcDefinition.class)];

        int total = 0;
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new GsonPropertyValidator()).create();
        File defs = new File("data/combat/npc");
        for (File def : defs.listFiles()) {
            if (def.getName().endsWith(".json")) {
                NpcCombatInfo[] s = null;
                try {
                    s = gson.fromJson(new FileReader(def), NpcCombatInfo[].class);
                } catch (JsonParseException e) {
                    throw new RuntimeException("Failed to parse npc combat def: " + def.getAbsolutePath() + " (" + e.getMessage() + ")");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (s == null)
                    continue;

                int entryIndex = 0;
                for (NpcCombatInfo cbInfo : s) {
                    if (cbInfo == null)
                        continue;

                    if (cbInfo.ids == null) {
                        logger.error("Failed to parse entry {} in NpcCombatInfo {} due to missing field 'ids'", entryIndex, def.getName());
                        continue;
                    }

                    // Store original stats to restore after respawning.
                    cbInfo.originalStats = cbInfo.stats.clone();
                    cbInfo.originalBonuses = cbInfo.bonuses.clone();

                    // Resolve scripts
                    if (cbInfo.scripts != null) {
                        cbInfo.scripts.resolve();
                    }

                    // Insert the combat info reference into the array at the index respective to the concerning npc ids
                    for (int i : cbInfo.ids) {
                        combatInfo[i] = cbInfo;
                    }

                    entryIndex++;
                }
                total += s.length;
            }
        }
        logger.info("Loaded {} NPC combat info sheets.", total);
    }

    public void loadItemSpawns(File dir) {
        int total = 0;
        Gson gson = JGson.get();
        for (File spawn : dir.listFiles()) {
            if (spawn.getName().endsWith(".json")) {
                try {
                    ItemSpawn[] s = gson.fromJson(new FileReader(spawn), ItemSpawn[].class);
                    for (ItemSpawn sp : s) {
                        if (sp == null)
                            continue;

                        Tile spawnTile = new Tile(sp.x, sp.y, sp.level);
                        GroundItem item = new GroundItem(new Item(sp.id, sp.amount), spawnTile, null);
                        item.respawns(true).respawnTimer(sp.delay);
                        GroundItemHandler.createGroundItem(item);
                    }
                    total += s.length;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (spawn.isDirectory()) {
                loadItemSpawns(spawn);
            }
        }
        logger.info("Loaded {} item spawns.", total);
    }

    public void loadDrops() {
        ScalarLootTable.loadAll(new File("data/combat/drops"));
        System.out.println(ScalarLootTable.registered.size() + " loaded drops");
    }

    public static void loadNpcSpawns(File dir) {
        long start = System.currentTimeMillis();
        Gson gson = new Gson();

        for (File spawn : dir.listFiles()) {
            if (spawn.getName().endsWith(".json")) {
                try {
                    NpcSpawn[] s = gson.fromJson(new FileReader(spawn), NpcSpawn[].class);

                    for (NpcSpawn sp : s) {
                        if (sp == null)
                            continue;

                        Tile spawnTile = new Tile(sp.x, sp.y, sp.z);
                        Npc npc = Npc.of(sp.id, spawnTile);
                        npc.spawnDirection(sp.dir());
                        npc.walkRadius(sp.walkRange);
                        npc.ancientSpawn(sp.ancientSpawn);

                        if (npc.id() == SHANOMI) {
                            Shanomi.shoutMessage(npc);
                        }

                        // successfully added to game world
                        KrakenBoss.onNpcSpawn(npc);

                        if (npc.id() == NpcIdentifiers.VENENATIS_6610) {
                            npc.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN, 30);
                        }

                        // Set the max return to spawnpoint distance for gwd room npcs
                        if (npc.def().gwdRoomNpc) {
                            npc.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN, 40);
                        }

                        // successfully added to game world
                        World.getWorld().registerNpc(npc);
                    }
                } catch (JsonParseException e) {
                    throw new RuntimeException("Failed to parse npc spawn: " + spawn.getAbsolutePath() + " (" + e + ")");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (spawn.isDirectory()) {
                loadNpcSpawns(spawn);
            }
        }
        long elapsed = System.currentTimeMillis() - start;
        logger.info("  Loaded definitions for ./data/map/npcs. It took {}ms.", elapsed);
    }

    public void postLoad() {
        try {
            loadEquipmentInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            loadNpcCombatInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //Seasonal spawns
            if(GameServer.properties().halloween) {
                Halloween.loadNpcs();
            }
            if(GameServer.properties().winter) {
                WinterEvent.loadNpcs();
                WinterEvent.loadObjects();
            }
            loadNpcSpawns(new File("data/map/npcs"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Fishing.respawnAllSpots(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logger.info("Loaded {} NPC spawns.", npcs.size());

        try {
            loadItemSpawns(new File("data/map/items"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ItemWeight.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            loadDrops();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int meleeClip(int absx, int absy, int z) {
        return clipInMap(absx, absy, z, false);
    }

    public int projectileClip(int absx, int absy, int z) {
        return clipInMap(absx, absy, z, true);
    }

    private int clipInMap(int x, int y, int z, boolean projectile) {
        return projectile ? RegionManager.getClippingProj(x, y, z) : RegionManager.getClipping(x, y, z);
    }

    /*
     * checks clip
     */
    public boolean canMoveNPC(int plane, int x, int y, int size) {
        for (int tileX = x; tileX < x + size; tileX++)
            for (int tileY = y; tileY < y + size; tileY++)
                if (getMask(plane, tileX, tileY) != 0)
                    return false;
        return true;
    }

    /*
     * checks clip
     */
    public boolean isNotCliped(int plane, int x, int y, int size) {
        for (int tileX = x; tileX < x + size; tileX++)
            for (int tileY = y; tileY < y + size; tileY++)
                if ((getMask(plane, tileX, tileY) & 2097152) != 0)
                    return false;
        return true;
    }

    public int getMask(int plane, int x, int y) {
        Tile tile = new Tile(x, y, plane);
        int regionId = tile.region();
        Region region = RegionManager.getRegion(regionId);
        if (region == null)
            return -1;
        int clip = region.getClip(x, y, tile.getLevel());
        //("gm %s,%s,%s = %s aka %s%n", x, y, tile.level, clip, clipstr(clip));
        return clip;
        // int baseLocalX = x - ((regionId >> 8) * 64);
        // int baseLocalY = y - ((regionId & 0xff) * 64);
    }

    public static String clipstr(final int clip) {
        StringBuilder sb = new StringBuilder();
        Flags.getMASKS().forEach((s, integer) -> {
            if ((clip & integer) != 0) {
                sb.append(s).append(",");
            }
        });
        return sb.toString().length() == 0 ? "none" : sb.toString();
    }

    public static String clipstrMethods(Tile tile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RegionManager.blockedEast(tile) ? "E, " : "");
        stringBuilder.append(RegionManager.blockedNorth(tile) ? "N, " : "");
        stringBuilder.append(RegionManager.blockedSouth(tile) ? "S, " : "");
        stringBuilder.append(RegionManager.blockedWest(tile) ? "W, " : "");
        stringBuilder.append(RegionManager.blockedNorthEast(tile) ? "NE, " : "");
        stringBuilder.append(RegionManager.blockedNorthWest(tile) ? "NW, " : "");
        stringBuilder.append(RegionManager.blockedSouthEast(tile) ? "SE, " : "");
        stringBuilder.append(RegionManager.blockedSouthWest(tile) ? "SW, " : "");
        return "blocked in dirs: " + stringBuilder.toString();
    }

    public Tile randomTileAround(Tile base, int radius) {
        int[][] clip = clipSquare(base.transform(-radius, -radius, 0), radius * 2 + 1);

        for (int i = 0; i < 100; i++) {
            int x = Utils.RANDOM_GEN.get().nextInt(radius * 2 + 1), z = Utils.RANDOM_GEN.get().nextInt(radius * 2 + 1);
            if (clip[x][z] == 0) {
                return base.transform(x - radius, z - radius, 0);
            }
        }

        return base;
    }

    public int[][] clipAround(Tile base, int radius) {
        Tile src = base.transform(-radius, -radius, 0);
        return clipSquare(src, radius * 2 + 1);
    }

    public int[][] clipSquare(Tile base, int size) {
        int[][] clipping = new int[size][size];

        Region active = RegionManager.getRegion(base.region());
        int activeId = base.region();

        for (int x = base.x; x < base.x + size; x++) {
            for (int y = base.y; y < base.y + size; y++) {
                int reg = Tile.coordsToRegion(x, y);
                if (reg != activeId) {
                    activeId = reg;
                    active = RegionManager.getRegion(activeId);
                }

                if (active != null && active.clips[base.level % 4] != null)
                    clipping[x - base.x][y - base.y] = active.clips[base.level % 4][x & 63][y & 63];
            }
        }

        return clipping;
    }

    public int clipAt(int x, int z, int level) {
        return clipAt(new Tile(x, z, level));
    }

    public int clipAt(Tile tile) {
        Region active = RegionManager.getRegion(tile.region());
        return active == null ? 0 : active.clips == null ? 0 : active.clips[tile.level % 4] == null ? 0 : active.clips[tile.level % 4][tile.x & 63][tile.y & 63];
    }

    public int floorAt(Tile tile) {
        Region active = RegionManager.getRegion(tile.region());
        return active == null ? 0 : active.heightMap[tile.level % 4][tile.x & 63][tile.y & 63];
    }

    public void tileGraphic(int id, Tile tile, int height, int delay) {
        players.forEachInRegion(tile.region(), player -> {
            if(player.tile().level == tile.level) {
                player.getPacketSender().sendTileGraphic(id, tile, height, delay);
            }
        });
    }

    private final Random random = new SecureRandom();

    public Random random() {
        return random;
    }

    /**
     * @param i Maximum - INCLUSIVE!
     * @return Integer between 1 - MAX
     */
    public int random(int i) {
        if (i < 1) {
            return 0;
        }

        return random.nextInt(i + 1);
    }

    public int random(final int min, final int max) {
        final int n = Math.abs(max - min);
        return Math.min(min, max) + (n == 0 ? 0 : random.nextInt(n + 1));
    }

    public double get() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public int get(int maxRange) {
        return (int) (get() * (maxRange + 1D));
    }

    public int get(int minRange, int maxRange) {
        return minRange + get(maxRange - minRange);
    }

    public int get(int... values) {
        return values[get(values.length - 1)];
    }

    public <T> T get(T[] values) {
        return values[get(values.length - 1)];
    }

    public <T> T get(List<T> list) {
        return list.get(get(list.size() - 1));
    }

    public <T> T get(List<T> list, Object exclude) {
        T result;
        while ((result = get(list)) == exclude) {}
        return result;
    }

    public double randomDouble() {
        return random.nextDouble();
    }

    public <T> T random(T[] i) {
        return i[random.nextInt(i.length)];
    }

    public int random(int[] i) {
        return i[random.nextInt(i.length)];
    }

    public int random(IntRange range) {
        return random.nextInt(range.getEndInclusive() - range.getStart() + 1) + range.getStart();
    }

    public boolean rollDie(int dieSides, int chance) {
        return random(dieSides) < chance;
    }

    public boolean rollDie(int maxRoll) {
        return rollDie(maxRoll, 1);
    }

    public Optional<Player> getPlayerByUid(int userId) {
        return players.filter(plr -> plr.getIndex() == userId).findFirst();
    }

    public Player getPlayer(int userId, boolean onlineReq) {
        if (onlineReq) {
            for (Player player : players) {
                if (player != null && player.getIndex() == userId)
                    return player;
            }
        } else {
            for (Player player : players.mobs) {
                if (player != null && player.getIndex() == userId)
                    return player;
            }
        }
        return null;
    }

    public Map<String, OwnedObject> getOwnedObjects() {
        return ownedObjects;
    }

    public void registerOwnedObject(OwnedObject object) {
        ownedObjects.put(object.getOwnerUID() + ":" + object.getIdentifier(), object);
    }

    public OwnedObject getOwnedObject(Player owner, String identifier) {
        return ownedObjects.get(owner.getIndex() + ":" + identifier);
    }

    public void deregisterOwnedObject(OwnedObject object) {
        ownedObjects.remove(object.getOwnerUID() + ":" + object.getIdentifier());
    }
}

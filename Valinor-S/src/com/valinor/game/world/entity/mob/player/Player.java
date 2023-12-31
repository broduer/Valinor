package com.valinor.game.world.entity.mob.player;

import com.valinor.GameServer;
import com.valinor.db.transactions.*;
import com.valinor.game.GameConstants;
import com.valinor.game.GameEngine;
import com.valinor.game.content.EffectTimer;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.areas.wilderness.content.RiskManagement;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.bounty_tasks.BountyHunterTask;
import com.valinor.game.content.areas.wilderness.content.upgrade_station.WeaponUpgrade;
import com.valinor.game.content.events.haunted_chest.HauntedChest;
import com.valinor.game.content.events.hp_event.HpEvent;
import com.valinor.game.content.events.wilderness_key.WildernessKeyPlugin;
import com.valinor.game.content.events.chaotic_nightmare.ChaoticNightmare;
import com.valinor.game.content.events.boss_event.WorldBossEvent;
import com.valinor.game.content.bank_pin.BankPin;
import com.valinor.game.content.bank_pin.BankPinSettings;
import com.valinor.game.content.clan.Clan;
import com.valinor.game.content.clan.ClanManager;
import com.valinor.game.content.collection_logs.CollectionLog;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.duel.Dueling;
import com.valinor.game.content.gambling.GambleState;
import com.valinor.game.content.gambling.GamblingSession;
import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.content.instance.InstancedAreaManager;
import com.valinor.game.content.instance.impl.*;
import com.valinor.game.content.kill_logs.BossKillLog;
import com.valinor.game.content.kill_logs.SlayerKillLog;
import com.valinor.game.content.mechanics.*;
import com.valinor.game.content.mechanics.referrals.Referrals;
import com.valinor.game.content.minigames.Minigame;
import com.valinor.game.content.minigames.MinigameManager;
import com.valinor.game.content.minigames.impl.fight_caves.FightCavesMinigame;
import com.valinor.game.content.option_menu.TeleportMenuHandler;
import com.valinor.game.content.packet_actions.GlobalStrings;
import com.valinor.game.content.presets.PresetManager;
import com.valinor.game.content.presets.Presetable;
import com.valinor.game.content.raids.Raids;
import com.valinor.game.content.raids.party.Party;
import com.valinor.game.content.seasonal_events.rewards.UnlockEventRewards;
import com.valinor.game.content.security.impl.EnterAccountPin;
import com.valinor.game.content.skill.Skillable;
import com.valinor.game.content.skill.impl.farming.Farming;
import com.valinor.game.content.skill.impl.hunter.Hunter;
import com.valinor.game.content.items.keys.SlayerKey;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.content.skill.impl.slayer.SlayerRewards;
import com.valinor.game.content.sound.CombatSounds;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.content.tasks.TaskBottleManager;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.content.teleport.world_teleport_manager.WorldTeleportNetwork;
import com.valinor.game.content.title.AvailableTitle;
import com.valinor.game.content.title.TitleCategory;
import com.valinor.game.content.title.TitleColour;
import com.valinor.game.content.title.req.impl.other.TitleUnlockRequirement;
import com.valinor.game.content.tournaments.Tournament;
import com.valinor.game.content.tournaments.TournamentManager;
import com.valinor.game.content.tournaments.TournamentUtils;
import com.valinor.game.content.trade.Trading;
import com.valinor.game.content.tradingpost.TradingPostListing;
import com.valinor.game.content.upgrades.forging.ItemForgingTable;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.AccountPinFrozenTask;
import com.valinor.game.task.impl.DoubleExpTask;
import com.valinor.game.task.impl.DropRateLampTask;
import com.valinor.game.task.impl.RestoreSpecialAttackTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.*;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.Venom;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunter;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.content.instance.impl.NightmareInstance;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.vorkath.VorkathState;
import com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex.ZarosGodwars;
import com.valinor.game.world.entity.combat.prayer.QuickPrayers;
import com.valinor.game.world.entity.combat.prayer.default_prayer.DefaultPrayerData;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.combat.skull.SkullType;
import com.valinor.game.world.entity.combat.skull.Skulling;
import com.valinor.game.world.entity.combat.weapon.WeaponInterfaces;
import com.valinor.game.world.entity.dialogue.ChatBoxItemDialogue;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.masks.chat.ChatMessage;
import com.valinor.game.world.entity.mob.Flag;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.pets.PetAI;
import com.valinor.game.world.entity.mob.npc.pets.insurance.PetInsurance;
import com.valinor.game.world.entity.mob.player.commands.impl.staff.admin.UpdateServerCommand;
import com.valinor.game.world.entity.mob.player.relations.PlayerRelations;
import com.valinor.game.world.entity.mob.player.rights.MemberRights;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.game.world.entity.mob.player.save.PlayerSave;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.ItemContainer;
import com.valinor.game.world.items.container.bank.Bank;
import com.valinor.game.world.items.container.bank.DepositBox;
import com.valinor.game.world.items.container.equipment.Equipment;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.game.world.items.container.inventory.Inventory;
import com.valinor.game.world.items.container.looting_bag.LootingBag;
import com.valinor.game.world.items.container.price_checker.PriceChecker;
import com.valinor.game.world.items.container.rune_pouch.RunePouch;
import com.valinor.game.world.object.OwnedObject;
import com.valinor.game.world.object.dwarf_cannon.DwarfCannon;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.ControllerManager;
import com.valinor.game.world.position.areas.impl.TournamentArea;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.game.world.route.routes.TargetRoute;
import com.valinor.net.PlayerSession;
import com.valinor.net.SessionState;
import com.valinor.net.channel.ServerHandler;
import com.valinor.net.packet.PacketBuilder;
import com.valinor.net.packet.PacketSender;
import com.valinor.net.packet.incoming_packets.PickupItemPacketListener;
import com.valinor.net.packet.interaction.InteractionManager;
import com.valinor.net.packet.outgoing.UnnecessaryPacketDropper;
import com.valinor.util.*;
import com.valinor.util.timers.TimerKey;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import org.apache.commons.compress.utils.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.valinor.game.content.areas.wilderness.content.EloRating.DEFAULT_ELO_RATING;
import static com.valinor.game.content.daily_tasks.DailyTaskUtility.DAILY_TASK_MANAGER_INTERFACE;
import static com.valinor.game.content.daily_tasks.DailyTaskUtility.TIME_FRAME_TEXT_ID;
import static com.valinor.game.world.entity.AttributeKey.*;
import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.*;
import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.WILDERNESS_KEY;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

public class Player extends Mob {

    private final WorldTeleportNetwork worldTeleportNetwork = new WorldTeleportNetwork(this);

    public WorldTeleportNetwork getWorldTeleportNetwork() {
        return worldTeleportNetwork;
    }

    private final RiskManagement riskManagement = new RiskManagement(this);

    public RiskManagement getRisk() {
        return riskManagement;
    }

    private final WeaponUpgrade weaponUpgrade = new WeaponUpgrade(this);

    public WeaponUpgrade getWeaponUpgrade() {
        return weaponUpgrade;
    }

    private final ItemForgingTable itemForgingTable = new ItemForgingTable();

    public ItemForgingTable getItemForgingTable() {
        return itemForgingTable;
    }

    private Task bountyHunterTask;

    public void setBountyHunterTask(Task task) {
        stopBountyHunterTask();
        this.bountyHunterTask = task;
        if (task != null) {
            TaskManager.submit(task);
        }
    }

    public void stopBountyHunterTask() {
        if (bountyHunterTask != null && bountyHunterTask.isRunning()) {
            bountyHunterTask.stop();
        }
    }

    public boolean hotspotActive() {
        int hotspotTimer = this.getAttribOr(AttributeKey.HOTSPOT_TIMER, -1);
        return hotspotTimer > 0;
    }

    public boolean hasBountyTask() {
        int bountyTaskTimer = this.getAttribOr(AttributeKey.BOUNTY_TASK_TIMER, -1);
        var bountyTask = this.<BountyHunterTask.BountyTasks>getAttribOr(BOUNTY_HUNTER_TASK, null);
        return bountyTaskTimer > 0 && bountyTask != null;
    }

    private final Farming farming = new Farming(this);
    public Farming getFarming() {
        return farming;
    }

    public TickDelay snowballCooldown = new TickDelay();
    public boolean muted;

    public void removeAll(Item item) {
        int inventoryCount = inventory.count(item.getId());
        for (int i = 0; i < inventoryCount; i++) {
            inventory.remove(item, true);
        }

        //Equipment can only have one item in a slot
        equipment.remove(item, true);

        int bankCount = bank.count(item.getId());
        for (int i = 0; i < bankCount; i++) {
            bank.removeFromBank(item);
        }

        if (item.getId() == DAWNBRINGER) {
            message("The weapon falls apart in your hand as Verzik's shield is destroyed.");
        }
    }

    private final TeleportMenuHandler teleportMenuHandler = new TeleportMenuHandler(this);

    public TeleportMenuHandler getTeleportMenuHandler() {
        return teleportMenuHandler;
    }


    private ArrayList<Integer> favoriteTeleports = new ArrayList<Integer>();

    public ArrayList<Integer> getFavoriteTeleports() {
        return favoriteTeleports;
    }

    public void setFavoriteTeleports(ArrayList<Integer> favoriteTeleports) {
        this.favoriteTeleports = favoriteTeleports;
    }

    public Optional<IronmanGroup> getIronmanGroup() {
        return IronmanGroupHandler.getPlayersGroup(this);
    }

    private Raids raids;

    public Raids getRaids() {
        return raids;
    }

    public void setRaids(Raids raids) {
        this.raids = raids;
    }

    public void heal() {
        graphic(436, 48, 0);
        message("<col=" + Color.BLUE.getColorValue() + ">You have restored your hitpoints, run energy and prayer.");
        message("<col=" + Color.HOTPINK.getColorValue() + ">You've also been cured of poison and venom.");
        skills().resetStats();
        int increase = getEquipment().hpIncrease();
        hp(Math.max(increase > 0 ? skills().level(Skills.HITPOINTS) + increase : skills().level(Skills.HITPOINTS), skills().xpLevel(Skills.HITPOINTS)), 39); //Set hitpoints to 100%
        skills().replenishSkill(5, skills().xpLevel(5)); //Set the players prayer level to fullputAttrib(AttributeKey.RUN_ENERGY, 100.0);
        setRunningEnergy(100.0, true);
        Poison.cure(this);
        Venom.cure(2, this);

        if (getTimers().has(TimerKey.RECHARGE_SPECIAL_ATTACK)) {
            message("Special attack energy can be restored in " + getTimers().asMinutesAndSecondsLeft(TimerKey.RECHARGE_SPECIAL_ATTACK) + ".");
        } else {
            restoreSpecialAttack(100);
            setSpecialActivated(false);
            CombatSpecial.updateBar(this);
            int time = 100;
            if (getMemberRights().isSapphireMemberOrGreater(this))
                time = 75;//45 seconds
            if (getMemberRights().isEmeraldMemberOrGreater(this))
                time = 50;//30 seconds
            if (getMemberRights().isRubyMemberOrGreater(this))
                time = 0;//always
            getTimers().register(TimerKey.RECHARGE_SPECIAL_ATTACK, time); //Set the value of the timer.
            message("<col=" + Color.HOTPINK.getColorValue() + ">You have restored your special attack.");
        }
    }

    public static class TextData {

        public final String text;
        public final int id;

        public TextData(String text, int id) {
            this.text = text;
            this.id = id;
        }
    }

    private final UnlockEventRewards unlockEventRewards = new UnlockEventRewards(this);

    public UnlockEventRewards getEventRewards() {
        return unlockEventRewards;
    }

    private final Map<Integer, TinterfaceText> interfaceText = new HashMap<>();

    public static class TinterfaceText {
        public int id;
        public String currentState;

        public TinterfaceText(String s, int id) {
            this.currentState = s;
            this.id = id;
        }
    }

    public boolean checkPacket126Update(String text, int id) {
        if (interfaceText.containsKey(id)) {
            TinterfaceText t = interfaceText.get(id);
            if (text.equals(t.currentState)) {
                return false;
            }
        }
        interfaceText.put(id, new TinterfaceText(text, id));
        return true;
    }

    private final UnnecessaryPacketDropper packetDropper = new UnnecessaryPacketDropper();

    public UnnecessaryPacketDropper getPacketDropper() {
        return packetDropper;
    }

    public int extraItemRollChance() {
        return switch (getMemberRights()) {
            case NONE, SAPPHIRE_MEMBER, EMERALD_MEMBER -> 0;
            case RUBY_MEMBER -> 1;
            case DIAMOND_MEMBER -> 3;
            case DRAGONSTONE_MEMBER -> 5;
            case ONYX_MEMBER -> 8;
            case ZENYTE_MEMBER -> 10;
        };
    }

    public int memberAncientRevBonus() {
        var extraPercentageChance = 0;
        if (getMemberRights().isZenyteMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 95;
        else if (getMemberRights().isOnyxMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 80;
        else if (getMemberRights().isDragonstoneMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 70;
        else if (getMemberRights().isDiamondMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 60;
        else if (getMemberRights().isRubyMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 55;
        else if (getMemberRights().isEmeraldMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 50;
        else if (getMemberRights().isSapphireMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 45;

        return extraPercentageChance;
    }

    public int treasureCasketMemberBonus() {
        var extraPercentageChance = 0;
        if (getMemberRights().isZenyteMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 25;
        else if (getMemberRights().isOnyxMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 15;
        else if (getMemberRights().isDragonstoneMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 10;
        else if (getMemberRights().isDiamondMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 7;
        else if (getMemberRights().isRubyMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 4;
        else if (getMemberRights().isEmeraldMemberOrGreater(this) && tile().memberCave())
            extraPercentageChance = 2;

        return extraPercentageChance;
    }

    public int dropRateBonus() {
        var percent = switch (getMemberRights()) {
            case NONE -> 0;
            case SAPPHIRE_MEMBER -> 1;
            case EMERALD_MEMBER -> 2;
            case RUBY_MEMBER -> 4;
            case DIAMOND_MEMBER -> 6;
            case DRAGONSTONE_MEMBER -> 8;
            case ONYX_MEMBER -> 10;
            case ZENYTE_MEMBER -> 14;
        };

        if (mode == ExpMode.CHALLENGER)
            percent += 5;

        if (mode == ExpMode.GLADIATOR)
            percent += 10;

        if (equipment.hasAt(EquipSlot.RING, RING_OF_WEALTH))
            percent += 5;

        if (equipment.hasAt(EquipSlot.RING, RING_OF_WEALTH_I) || equipment.hasAt(EquipSlot.RING, RING_OF_TRINITY))
            percent += 7.5;

        if(equipment.wearingMaxCape())
            percent += 5.0;

        var dropRateBoostUnlock = slayerRewards.getUnlocks().containsKey(SlayerConstants.DROP_RATE_BOOST);
        if (dropRateBoostUnlock)
            percent += 3;

        if (hasPetOut("Zriawk"))
            percent += 15;

        if (equipment.hasAt(EquipSlot.HEAD, PURPLE_SLAYER_HELMET_I)) {
            percent += 3;
        }

        if (equipment.hasAt(EquipSlot.HEAD, TZKAL_SLAYER_HELMET_I)) {
            percent += 3;
        }

        //Drop rate percentage boost can't go over cap%
        if(percent > 50 && !memberRights.isOnyxMemberOrGreater(this)) {
            percent = 50;
        }

        return percent;
    }


    public void healPlayer() {
        hp(Math.max(skills().level(Skills.HITPOINTS), skills().xpLevel(Skills.HITPOINTS)), 20); //Set hitpoints to 100%
        skills().replenishSkill(5, skills().xpLevel(5)); //Set the players prayer level to full
        skills().replenishStatsToNorm();
        setRunningEnergy(100.0, true);
        Poison.cure(this);
        Venom.cure(2, this);
    }

    private final SlayerKey slayerKey = new SlayerKey(this);

    public SlayerKey getSlayerKey() {
        return slayerKey;
    }

    private final ItemContainer raidRewards = new ItemContainer(3, ItemContainer.StackPolicy.ALWAYS);

    public ItemContainer getRaidRewards() {
        return raidRewards;
    }

    public boolean hasAccountPin() {
        var pin = this.<Integer>getAttribOr(ACCOUNT_PIN, 0);
        var pinAsString = pin.toString();
        return pinAsString.length() == 5;
    }

    public boolean askForAccountPin() {
        return this.<Boolean>getAttribOr(ASK_FOR_ACCOUNT_PIN, false);
    }

    public void sendAccountPinMessage() {
        int ticks = this.<Integer>getAttribOr(ACCOUNT_PIN_FREEZE_TICKS, 0);
        int convertTicksToSeconds = Utils.getSeconds(ticks);

        if (this.<Integer>getAttribOr(ACCOUNT_PIN_FREEZE_TICKS, 0) > 0) {
            DialogueManager.sendStatement(this, "Try again in " + Utils.convertSecondsToDuration(convertTicksToSeconds, false) + ".");
            return;
        }
        this.interfaceManager.closeDialogue();
        this.setEnterSyntax(new EnterAccountPin());
        this.getPacketSender().sendEnterAmountPrompt("Enter your account pin, attempts left: " + this.<Integer>getAttribOr(AttributeKey.ACCOUNT_PIN_ATTEMPTS_LEFT, 5) + "/5");
    }

    public Party raidsParty;

    private int multi_cannon_stage;

    public int getMultiCannonStage() {
        return multi_cannon_stage;
    }

    public void setMultiCannonStage(int stage) {
        this.multi_cannon_stage = stage;
    }

    public List<TradingPostListing> tempList;

    public List<TradingPostListing> tradePostHistory = Lists.newArrayList();

    public int tradingPostListedItemId, tradingPostListedAmount;

    public String lastTradingPostUserSearch, lastTradingPostItemSearch;

    public boolean jailed() {
        return (int) getAttribOr(AttributeKey.JAILED, 0) == 1;
    }

    /**
     * If the player has the tool store open.
     */
    private boolean tool_store_open;

    /**
     * Returns if the player has tool store open.
     *
     * @return if is open
     */
    public boolean isToolStoreOpen() {
        return tool_store_open;
    }

    /**
     * Sets if the player has tool store open.
     *
     * @param b
     */
    public void setToolStoreOpen(boolean b) {
        this.tool_store_open = b;
    }

    private Task currentTask;

    public Task getCurrentTask() {
        return currentTask;
    }

    public void endCurrentTask() {
        if (currentTask != null) {
            currentTask.stop();
        }
        currentTask = null;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public Triggers getTriggers() {
        return triggers;
    }

    private final transient Triggers triggers = new Triggers(this);

    private final BossTimers bossTimers = new BossTimers();

    public BossTimers getBossTimers() {
        return bossTimers;
    }

    public boolean ownsAny(int... ids) {
        return this.inventory.containsAny(ids) || this.equipment.containsAny(ids) || this.bank.containsAny(ids);
    }

    private GameMode gameMode = GameMode.NONE;

    public GameMode gameMode() {
        return gameMode;
    }

    public void gameMode(GameMode mode) {
        gameMode = mode;
    }

    private final GamblingSession gamblingSession = new GamblingSession(this);

    public GamblingSession getGamblingSession() {
        return gamblingSession;
    }

    private static final Logger logger = LogManager.getLogger(Player.class);

    private VorkathState state = VorkathState.SLEEPING;

    public VorkathState getVorkathState() {
        return state;
    }

    public void setVorkathState(VorkathState state) {
        this.state = state;
    }

    private final PetInsurance petInsurance = new PetInsurance(this);

    public PetInsurance getPetInsurance() {
        return petInsurance;
    }

    private ArrayList<Integer> unlockedPets = new ArrayList<>();

    public ArrayList<Integer> getUnlockedPets() {
        return unlockedPets;
    }

    public void setUnlockedPets(ArrayList<Integer> unlockedPets) {
        if (unlockedPets == null)
            return;
        this.unlockedPets = unlockedPets;
    }

    public boolean isPetUnlocked(int id) {
        return unlockedPets.contains(id);
    }

    public void addUnlockedPet(int id) {
        if (this.unlockedPets.contains(id)) {
            return;
        }
        unlockedPets.add(id);
    }

    private ArrayList<Integer> insuredPets = new ArrayList<>();

    public ArrayList<Integer> getInsuredPets() {
        return insuredPets;
    }

    public void setInsuredPets(ArrayList<Integer> insuredPets) {
        // lets not set the array to null, list should always exist. If the player doesn't have pets when logging in, insuredPets is null in the PlayerSave class.
        if (insuredPets == null)
            return;
        this.insuredPets = insuredPets;
    }

    public boolean isInsured(int id) {
        return insuredPets.contains(id);
    }

    public void addInsuredPet(int id) {
        insuredPets.add(id);
    }

    private Minigame minigame;

    /**
     * Sets the minigame
     *
     * @return the minigame
     */
    public Minigame getMinigame() {
        return minigame;
    }

    /**
     * Sets the minigame
     *
     * @param minigame the minigame
     */
    public void setMinigame(Minigame minigame) {
        this.minigame = minigame;
    }

    private final MinigameManager minigameManager = new MinigameManager();

    /**
     * Sets the minigameManager
     *
     * @return the minigameManager
     */
    public MinigameManager getMinigameManager() {
        return minigameManager;
    }

    // Slayer

    public void slayerWidgetActions(int buttonId, String name, int config, int type) {
        this.putAttrib(SLAYER_WIDGET_BUTTON_ID, buttonId);
        this.putAttrib(SLAYER_WIDGET_NAME, name);
        this.putAttrib(SLAYER_WIDGET_CONFIG, config);
        this.putAttrib(SLAYER_WIDGET_TYPE, type);
    }

    private final SlayerRewards slayerRewards = new SlayerRewards(this);

    public SlayerRewards getSlayerRewards() {
        return slayerRewards;
    }

    private final List<TitleUnlockRequirement.UnlockableTitle> unlockedTitles = new ArrayList<>();

    public List<TitleUnlockRequirement.UnlockableTitle> getUnlockedTitles() {
        return unlockedTitles;
    }

    private TitleCategory currentCategory = TitleCategory.PKING;
    private AvailableTitle currentSelectedTitle;
    private TitleColour currentSelectedColour;

    public TitleColour getCurrentSelectedColour() {
        return currentSelectedColour;
    }

    public void setCurrentSelectedColour(TitleColour currentSelectedColour) {
        this.currentSelectedColour = currentSelectedColour;
    }

    public AvailableTitle getCurrentSelectedTitle() {
        return currentSelectedTitle;
    }

    public void setCurrentSelectedTitle(AvailableTitle currentSelectedTitle) {

        this.currentSelectedTitle = currentSelectedTitle;
    }

    public TitleCategory getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(TitleCategory currentCategory) {
        this.currentCategory = currentCategory;
    }

    private Optional<Skillable> skillable = Optional.empty();

    public Optional<Skillable> getSkillable() {
        return skillable;
    }

    public void setSkillable(Optional<Skillable> skillable) {
        this.skillable = skillable;
    }

    public int slayerTaskAmount() {
        return this.getAttribOr(AttributeKey.SLAYER_TASK_AMT, 0);
    }

    public int slayerTaskId() {
        return this.getAttribOr(AttributeKey.SLAYER_TASK_ID, 0);
    }

    private final TaskBottleManager taskBottleManager = new TaskBottleManager(this);

    public TaskBottleManager getTaskBottleManager() {
        return taskBottleManager;
    }

    private Varps varps;

    public Varps varps() {
        return varps;
    }

    /**
     * Our achieved skill levels
     */
    private Skills skills;

    public Skills skills() {
        return skills;
    }

    public void skills(Skills skills) {
        this.skills = skills;
    }

    /**
     * Our looks (clothes, colours, gender)
     */
    private final Looks looks;

    public Looks looks() {
        return looks;
    }

    private final CollectionLog collectionLog = new CollectionLog(this);

    public CollectionLog getCollectionLog() {
        return collectionLog;
    }

    private Clan clan;
    private String clanChat;
    private String savedClan;
    private String clanPromote;

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public String getSavedClan() {
        return savedClan;
    }

    public void setSavedClan(String savedClan) {
        this.savedClan = savedClan;
    }

    public String getClanPromote() {
        return clanPromote;
    }

    public void setClanPromote(String clanPromote) {
        this.clanPromote = clanPromote;
    }

    public String getClanChat() {
        return clanChat;
    }

    public void setClanChat(String clanChat) {
        this.clanChat = clanChat;
    }

    public ChatBoxItemDialogue chatBoxItemDialogue;

    //This task keeps looping until the player action has been completed.
    public Task loopTask;

    /**
     * Their skull icon identification
     */
    private SkullType skullType = SkullType.NO_SKULL;

    public SkullType getSkullType() {
        return skullType;
    }

    public void setSkullType(SkullType skullType) {
        this.skullType = skullType;
    }

    private boolean[] savedDuelConfig = new boolean[22]; // 22 rules

    public boolean[] getSavedDuelConfig() {
        return savedDuelConfig;
    }

    public void setSavedDuelConfig(boolean[] savedDuelConfig) {
        this.savedDuelConfig = savedDuelConfig;
    }

    public void setSavedDuelConfig(int index, boolean value) {
        this.savedDuelConfig[index] = value;
    }

    // Obtain the ItemContainer with our reward
    public ItemContainer clueScrollReward() {
        ItemContainer offer = getAttribOr(AttributeKey.CLUE_SCROLL_REWARD, null);
        if (offer != null)
            return offer;

        //This contain has a maximum size of 8
        ItemContainer container = new ItemContainer(8, ItemContainer.StackPolicy.ALWAYS);
        putAttrib(AttributeKey.CLUE_SCROLL_REWARD, container);
        return container;
    }

    public long lastGpi;
    public Map<Integer, Integer> commonStringsCache;

    private final InterfaceManager interfaceManager = new InterfaceManager(this);

    public InterfaceManager getInterfaceManager() {
        return interfaceManager;
    }

    public final RuntimeException initializationSource;

    /**
     * Creates this player.
     *
     * @param playerIO
     */
    public Player(PlayerSession playerIO) {
        super(NodeType.PLAYER, GameServer.properties().defaultTile);
        initializationSource = new RuntimeException("player created");
        this.session = playerIO;
        this.looks = new Looks(this);
        this.skills = new Skills(this);
        this.varps = new Varps(this);
    }

    public Player() {
        super(NodeType.PLAYER, GameServer.properties().defaultTile);
        initializationSource = new RuntimeException("player created");
        this.looks = new Looks(this);
        this.skills = new Skills(this);
        this.varps = new Varps(this);
    }

    public void teleblockMessage() {
        if (!getTimers().has(TimerKey.SPECIAL_TELEBLOCK))
            return;

        long special_timer = getTimers().left(TimerKey.SPECIAL_TELEBLOCK) * 600L;

        message(String.format("A teleport block has been cast on you. It should wear off in %d minutes, %d seconds.",
            TimeUnit.MILLISECONDS.toMinutes(special_timer),
            TimeUnit.MILLISECONDS.toSeconds(special_timer) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(special_timer))
        ));

        if (!getTimers().has(TimerKey.TELEBLOCK))
            return;

        long millis = getTimers().left(TimerKey.TELEBLOCK) * 600L;

        message(String.format("A teleport block has been cast on you. It should wear off in %d minutes, %d seconds.",
            TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        ));
    }

    public boolean canSpawn() {
        if ((!this.tile().homeRegion() || WildernessArea.inWilderness(this.tile())) && !getPlayerRights().isDeveloperOrGreater(this)) {
            this.message("You can only spawn items at home.");
            return false;
        }

        if (this.busy() && !getPlayerRights().isDeveloperOrGreater(this)) {
            this.message("You can't spawn items at this time.");
            return false;
        }

        if (this.inActiveTournament() && !getPlayerRights().isDeveloperOrGreater(this)) {
            this.message("You can't spawn items inside the tournament.");
            return false;
        }

        if (CombatFactory.inCombat(this) && !getPlayerRights().isDeveloperOrGreater(this)) {
            //Silent no message
            return false;
        }
        return true;
    }

    @Override
    public int yLength() {
        return 1;
    }

    @Override
    public int xLength() {
        return 1;
    }

    @Override
    public Tile getCentrePosition() {
        return tile();
    }

    @Override
    public int getProjectileLockonIndex() {
        return -getIndex() - 1;
    }

    private KrakenInstance krakenInstance;

    public KrakenInstance getKrakenInstance() {
        if (krakenInstance == null)
            krakenInstance = new KrakenInstance();
        return krakenInstance;
    }

    private ZulrahInstance zulrahInstance;

    public ZulrahInstance getZulrahInstance() {
        if (zulrahInstance == null)
            zulrahInstance = new ZulrahInstance();
        return zulrahInstance;
    }

    private VorkathInstance vorkathInstance;

    public VorkathInstance getVorkathInstance() {
        if (vorkathInstance == null)
            vorkathInstance = new VorkathInstance();
        return vorkathInstance;
    }

    private AlchemicalHydraInstance alchemicalHydraInstance;

    public AlchemicalHydraInstance getAlchemicalHydraInstance() {
        if (alchemicalHydraInstance == null)
            alchemicalHydraInstance = new AlchemicalHydraInstance();
        return alchemicalHydraInstance;
    }

    private BryophytaInstance bryophytaInstance;

    public BryophytaInstance getBryophytaInstance() {
        if (bryophytaInstance == null)
            bryophytaInstance = new BryophytaInstance();
        return bryophytaInstance;
    }

    private SkotizoInstance skotizoInstance;

    public NightmareInstance getNightmareInstance() {
        return nightmareInstance;
    }

    public void setNightmareInstance(NightmareInstance nightmareInstance) {
        this.nightmareInstance = nightmareInstance;
    }

    private NightmareInstance nightmareInstance;

    public SkotizoInstance getSkotizoInstance() {
        if (skotizoInstance == null)
            skotizoInstance = new SkotizoInstance();
        return skotizoInstance;
    }

    /**
     * Actions that should be done when this mob is added to the world.
     */
    @Override
    public void onAdd() {
        World.getWorld().ls.ONLINE.add(getMobName().toUpperCase());
        // Update session state
        session.setState(SessionState.LOGGED_IN);

        // This has to be the first packet!
        setNeedsPlacement(true);
        packetSender.sendMapRegion().sendDetails().sendRights().sendTabs();

        Tile.occupy(this);

        //Actions done for the player on login
        onLogin();
    }

    /**
     * Actions that should be done when this mob is removed from the world.
     */
    @Override
    public void onRemove() {
        // onlogout moved to logout service
    }

    @Override
    public Hit manipulateHit(Hit hit) {
        Mob attacker = hit.getAttacker();

        if (attacker.isNpc()) {
            Npc npc = attacker.getAsNpc();
            if (npc.id() == NpcIdentifiers.TZTOKJAD) {
                if (Prayers.usingPrayer(this, Prayers.getProtectingPrayer(hit.getCombatType()))) {
                    hit.setDamage(0);
                }
            }
        }

        return hit;
    }

    @Override
    public void die(Hit killHit) {
        stopActions(true);
        Death.death(this, killHit);
    }

    @Override
    public int hp() {
        return skills.level(Skills.HITPOINTS);
    }

    @Override
    public int maxHp() {
        return skills.xpLevel(Skills.HITPOINTS);
    }

    @Override
    public void hp(int hp, int exceed) {
        skills.setLevel(Skills.HITPOINTS, Math.max(0, Math.min(Math.max(hp(), maxHp() + exceed), hp)));//max(0, 114)  -> 114= min(99+16, 119)  -> 99+16 needs to equal min(hp() so brew doesnt reset!, newval)
        //but then max(0, 99) -> 99= min(99, 105) -> the 99 would be broke by min (99 already not brewed yet)
    }

    @Override
    public Mob setHitpoints(int hitpoints) {
        if (invulnerable) {
            if (skills.level(Skills.HITPOINTS) > hitpoints) {
                return this;
            }
        }
        skills.setLevel(Skills.HITPOINTS, hitpoints);
        skills.makeDirty(Skills.HITPOINTS);//Force refresh
        return this;
    }

    @Override
    public int attackAnimation() {
        this.sound(CombatSounds.weapon_attack_sounds(this), 10);
        return EquipmentInfo.attackAnimationFor(this);
    }

    @Override
    public int getBlockAnim() {
        return EquipmentInfo.blockAnimationFor(this);
    }

    @Override
    public int getBaseAttackSpeed() {

        // Gets attack speed for player's weapon
        // If player is using magic, attack speed is
        // Calculated in the MagicCombatMethod class.
        int speed;
        Item weapon = this.getEquipment().get(EquipSlot.WEAPON);
        if (weapon == null) {
            speed = 4; //Default is 4
        } else {
            speed = World.getWorld().equipmentInfo().weaponSpeed(weapon.getId());
        }

        if (getCombat().getTarget() instanceof Npc && (getEquipment().contains(ItemIdentifiers.TOXIC_BLOWPIPE) || getEquipment().contains(MAGMA_BLOWPIPE))) {
            speed--;
        }

        if (getCombat().getTarget() instanceof Npc && (getEquipment().hasAt(EquipSlot.WEAPON, TALONHAWK_CROSSBOW))) {
            speed--;
        }

        if (getCombat().getFightType().toString().toLowerCase().contains("rapid")) {
            speed--;
        }

        if(getCombat().getTarget() instanceof Player && getEquipment().contains(SWORD_OF_GRYFFINDOR)) {
            speed = 6;
        }

        return speed;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }
        Player p = (Player) o;
        if (p.username == null || username == null)
            return false;
        return p.getUsername().equals(username);
    }

    @Override
    public int hashCode() {
        assert false : "Hashcode not designed";
        return 0;
    }

    @Override
    public String toString() {
        return getPlayerRights().getName() + ": " + username + ", " + hostAddress + ", [" + getX() + ", " + getY() + ", " + getZ() + "], " + (WildernessArea.inWilderness(tile()) ? "in wild" : "not in wild");
    }

    @Override
    public int getSize() {
        return 1;
    }

    public PlayerPerformanceTracker perf = new PlayerPerformanceTracker();

    private void fireLogout() {
        if (username == null || this.<Boolean>getAttribOr(IS_BOT, false))
            return;
        // proactive checking of DC
        if (this.<Boolean>getAttribOr(LOGOUT_CLICKED, false) || !active()) {
            clearAttrib(LOGOUT_CLICKED);
            if (!canLogout()) {
                return;
            }
            requestLogout();
        }
    }

    private boolean active() {
        return session.getChannel() != null && session.getChannel().isActive();
    }

    private String captureState() {
        StringBuilder sb = new StringBuilder();
        sb.append(username + " state: ");
        sb.append(String.format("ded %s, lock %s, moving %s", dead(), lockState(), getMovementQueue().isMoving()));
        sb.append(" inv: " + Arrays.toString(inventory.getValidItems().stream().map(i -> i.toShortString()).toArray()));
        sb.append(" equipment: " + Arrays.toString(equipment.getValidItems().stream().map(i -> i.toShortString()).toArray()));
        return sb.toString();
    }

    private boolean divinePotionEffectActive() {
        List<AttributeKey> attribList = new ArrayList<>(List.of(DIVINE_BASTION_POTION_EFFECT_ACTIVE, DIVINE_BATTLEMAGE_POTION_EFFECT_ACTIVE, DIVINE_MAGIC_POTION_EFFECT_ACTIVE, DIVINE_RANGING_POTION_EFFECT_ACTIVE, DIVINE_SUPER_ATTACK_POTION_EFFECT_ACTIVE, DIVINE_SUPER_COMBAT_POTION_EFFECT_ACTIVE, DIVINE_SUPER_DEFENCE_POTION_EFFECT_ACTIVE, DIVINE_SUPER_STRENGTH_POTION_EFFECT_ACTIVE));
        return attribList.stream().anyMatch(key -> this.getAttribOr(key, false));
    }

    private void postcycle_dirty() {
        // Sync skills if dirty
        skills.syncDirty();
    }

    /**
     * Saves this player on the underlying thread (probably the game thread).
     */
    public void synchronousSave() {
        if (session.getState() == SessionState.LOGGED_IN || session.getState() == SessionState.LOGGING_OUT) {
            PlayerSave.save(this);
        }
    }

    /**
     * Can the player logout?
     *
     * @return Yes if they can logout, false otherwise.
     */
    public boolean canLogout() {
        boolean logCooldown = this.getAttribOr(AttributeKey.ALLOWED_TO_LOGOUT, true);

        // wait for the gambling session is finished
        if(gamblingSession.gambleState == GambleState.IN_PROGRESS)
            return false;

        // wait for forcemovement to finish, dont save players half on an agility obstacle they cant get out of
        if (getForceMovement() != null && getMovementQueue().forcedStep())
            return false;
        // dont save dead/tping players. login with 0hp = POSSIBLE DUPES
        if (dead() || isNeedsPlacement())
            return false;
        // extremely important only force logout via update AFTER isdead() check
        // otherwise dupes can occur.
        if (UpdateServerCommand.time < 1 || getForcedLogoutTimer().expiredAfterBeingRun()) {
            return true;
        }
        if (!logCooldown) {
            message("You must wait a few seconds before logging out.");
            return false;
        }

        if (getTimers().has(TimerKey.COMBAT_LOGOUT)) {
            message("You can't log out until 10 seconds after the end of combat.");
            return false;
        }

        if (inventory.contains(CustomItemIdentifiers.WILDERNESS_KEY) && WildernessArea.inWilderness(tile)) {
            message("You cannot logout holding the wilderness key.");
            return false;
        }

        if (locked() && getLock() != LockType.FULL_LOGOUT_OK) {
            return false;
        }
        return true;
    }

    /**
     * Sends the logout packet to the client.
     * <br>do NOT rely on netty {@link ServerHandler} events to kick off logouts.
     * it's mad unreliable as various methods can be triggered.
     * <br>Instead, submit the logout request to OUR service which we have full control over.
     */
    public void requestLogout() {
        stopActions(true);
        getSession().setState(SessionState.REQUESTED_LOG_OUT);
        logoutLock();

        try {
            // If we're logged in and the channel is active, begin with sending a logout message and closing the channel.
            // We use writeAndFlush here because otherwise the message won't be flushed cos of the next unregister() call.
            if (session.getChannel() != null && session.getChannel().isActive()) {
                // logoutpacket
                try {
                    session.getChannel().writeAndFlush(new PacketBuilder(109).toPacket()).addListener(ChannelFutureListener.CLOSE);
                } catch (Exception e) {
                    // Silenced
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception during logout => Channel closing for Player '{}'", getMobName(), e);
        }
        // remove from minigames etc, dont care about sending info to client since it'll logout anyway
        onLogout();

        GameEngine.getInstance().addSyncTask(() -> {
            // shadowrs warning: calling remove() when iterating over players() higher in the callstack (example in players.each.process()) = should trigger a ConcurrentModificationException .. but a deadlock occurs with no trace in log.
            // calling this in a sync task solves this
            World.getWorld().getPlayers().remove(this);
            this.onRemove();
            submitSave(new SaveAttempt());
        });
    }

    public static class SaveAttempt {
        int attempts;
    }

    private void submitSave(SaveAttempt saveAttempt) {
        GameEngine.getInstance().submitLowPriority(() -> {
            if (!World.getWorld().ls.ONLINE.contains(getMobName().toUpperCase())) {
                //logger.info("ignore save for {}", getMobName().toUpperCase());
                return;
            }
            final boolean success = World.getWorld().ls.savePlayerFile(this);
            if (!success) {
                try {
                    Thread.sleep(50); // dont try to save straight away
                } catch (InterruptedException e) {
                    logger.error(e);
                }
                saveAttempt.attempts++;
                submitSave(saveAttempt);
            } else {
                World.getWorld().ls.ONLINE.remove(getMobName().toUpperCase());
            }
        });
    }

    private final Map<String, Runnable> onLogoutListeners = new HashMap<>();

    public Map<String, Runnable> getOnLogoutListeners() {
        return onLogoutListeners;
    }

    public void runExceptionally(Runnable r) {
        try {
            r.run();
        } catch (Throwable e) {
            logger.error("Exception during logout => Player '{}'", getUsername(), e);
        }
    }

    /**
     * Handles the actual logging out from the game.
     */
    public void onLogout() {
        // Notify us
        //logger.info("Deregistering player - [username, host] : [{}, {}]", getUsername(), getHostAddress());
        Utils.sendDiscordInfoLog("```Deregistering player - " + getUsername() + " with IP " + getHostAddress() + "```", "logout");

        // Update session state
        getSession().setState(SessionState.LOGGING_OUT);

        clearAttrib(AttributeKey.PLAYER_AUTO_SAVE_TASK_RUNNING);

        // the point of wrapping each line in code is so that as many as possible things
        // can run successfully without stopping the ones after.
        runExceptionally(() -> stopActions(true));
        runExceptionally(() -> onLogoutListeners.values().forEach(Runnable::run));

        runExceptionally(() -> InteractionManager.onLogout(this));

        runExceptionally(() -> {
            //If we're in certain gamble states and we logout, return items.
            if (gamblingSession.state() != GambleState.NONE && !gamblingSession.matchActive()) {
                gamblingSession.abortGambling();
            }

            // If we're in a gamble, make sure to give us a loss for logging out.
            if (gamblingSession.matchActive()) {
                gamblingSession.end();
            }
        });

        runExceptionally(() -> {
            Party.onLogout(this);
        });

        runExceptionally(() -> {
            var minigame = this.getMinigame();
            if (minigame != null) {
                minigame.end(this);
            }
        });

        /*
         * Clear instances
         */
        runExceptionally(() -> {
            clearInstance();
            ZarosGodwars.removeFromList(this);
            var raids = getRaids();
            if(raids != null) {
                raids.exit(this,true);
            }
        });

        runExceptionally(() -> {
            // If we're in a duel, make sure to give us a loss for logging out.
            if (getDueling().inDuel()) {
                getDueling().onDeath();
            }
        });
        runExceptionally(() -> {
            // Leave area
            if (getController() != null) {
                getController().leave(this);
            }
        });

        runExceptionally(() -> {
            OwnedObject cannon = World.getWorld().getOwnedObject(this, DwarfCannon.IDENTIFIER);
            if (cannon != null) {
                this.putAttrib(AttributeKey.LOST_CANNON, true);
                cannon.destroy();
            }
            getRelations().onLogout();
            BountyHunter.unassign(this);
            PetAI.despawnOnLogout(this);
            getInterfaceManager().close();
            TaskManager.cancelTasks(this);
            looks().hide(true);
            Hunter.abandon(this, null, true);
            if (WildernessArea.inWilderness(this.tile())) {
                if (this.inventory().contains(CustomItemIdentifiers.WILDERNESS_KEY)) {
                    this.inventory().remove(CustomItemIdentifiers.WILDERNESS_KEY, Integer.MAX_VALUE);
                    World.getWorld().clearBroadcast();
                    PickupItemPacketListener.respawn(Item.of(CustomItemIdentifiers.WILDERNESS_KEY), tile, 3);
                    WildernessKeyPlugin.announceKeySpawn(tile);
                }
            }
            if (getClan() != null) {
                ClanManager.leave(this, true);
            }
        });

        runExceptionally(() -> {
            //Leave tourny on logout
            if (controller instanceof TournamentArea)
                Utils.sendDiscordInfoLog("Player " + this.getUsername() + " with IP " + this.getHostAddress() + " logged out during a tournament.", "logout_tourny");
            TournamentManager.leaveTourny(this, true);
        });

        //Technically this is the last logout, but we'll use it as the last login so the last login doesn't get "overwritten" for the welcome screen when the player logs in.
        setLastLogin(new Timestamp(new Date().getTime()));

        if (GameServer.properties().enableSql) {
            boolean developer = getPlayerRights().isDeveloperOrGreater(this);
            boolean owner = getPlayerRights().isOwner(this);
            boolean pvpGameMode = gameMode == GameMode.INSTANT_PKER;
            boolean cannotEnterSkillsDatabase = developer || owner || pvpGameMode;

            if (!cannotEnterSkillsDatabase) {
                GameServer.getDatabaseService().submit(new UpdatePlayerSkillsDatabaseTransaction(this));
            }

            GameServer.getDatabaseService().submit(new UpdatePlayerInfoDatabaseTransaction(getAttribOr(DATABASE_PLAYER_ID, -1), getHostAddress() == null ? "invalid" : getHostAddress(), getAttribOr(MAC_ADDRESS, "invalid"), getAttribOr(GAME_TIME, 0), expmode().toName(), gameMode().name));
            GameServer.getDatabaseService().submit(new UpdateKillsDatabaseTransaction(getAttribOr(AttributeKey.PLAYER_KILLS, 0), username));
            GameServer.getDatabaseService().submit(new UpdateDeathsDatabaseTransaction(getAttribOr(AttributeKey.PLAYER_DEATHS, 0), username));
            GameServer.getDatabaseService().submit(new UpdateKdrDatabaseTransaction(Double.parseDouble(getKillDeathRatio()), username));
            GameServer.getDatabaseService().submit(new UpdateKillstreakDatabaseTransaction(getAttribOr(AttributeKey.KILLSTREAK_RECORD, 0), username));
            GameServer.getDatabaseService().submit(new InsertPlayerIPDatabaseTransaction(this));
        }
    }

    /**
     * Called by the world's login queue!
     */
    public void onLogin() {
        long startTime = System.currentTimeMillis();
        putAttrib(AttributeKey.LOGGED_IN_AT_TIME, startTime);
        // Attempt to register the player..
        //logger.info("Registering player - [username, host] : [{}, {}]", getUsername(), getHostAddress());

        skills.update();
        inventory.refresh();
        equipment.refresh();

        // Send simple player options
        packetSender.sendInteractionOption("Follow", 3, false).sendInteractionOption("Trade with", 4, false);
        if (equipment.get(3) != null && equipment.get(3).getId() == SNOWBALL) // Snowball
            packetSender.sendInteractionOption("Pelt", 1, true);

        // Trigger a scripting event
        InteractionManager.onLogin(this);

        //Update player looks
        getUpdateFlag().flag(Flag.APPEARANCE);

        // Sync varps
        varps.syncNonzero();
        double energy = this.getAttribOr(RUN_ENERGY, 0.0);
        packetSender.sendConfig(709, Prayers.canUse(this, DefaultPrayerData.PRESERVE, false) ? 1 : 0).sendConfig(711, Prayers.canUse(this, DefaultPrayerData.RIGOUR, false) ? 1 : 0).sendConfig(713, Prayers.canUse(this, DefaultPrayerData.AUGURY, false) ? 1 : 0).sendConfig(172, this.getCombat().autoRetaliate() ? 1 : 0).updateSpecialAttackOrb().sendRunStatus().sendRunEnergy((int) energy);
        Prayers.closeAllPrayers(this);

        // Send simple player options
        relations.setPrivateMessageId(1);
        // Send friends and ignored players lists...
        relations.onLogin();
        farming.handleLogin();
        getMovement().clear();
        setHeadHint(-1);
        teleportMenuHandler.load();
        teleportMenuHandler.updateFavorites();

        // Force fix any remaining bugged accounts
        if (this.<Integer>getAttribOr(MULTIWAY_AREA, -1) == 1 && !MultiwayCombat.includes(this.tile())) {
            putAttrib(MULTIWAY_AREA, 0);
        }

        if (this.<Boolean>getAttribOr(ASK_FOR_ACCOUNT_PIN, false)) {
            askForAccountPin();
        }

        moveFromRegionOnLogin();

        boolean newAccount = this.getAttribOr(NEW_ACCOUNT, false);

        if (!newAccount && getBankPin().hasPin() && !getBankPin().hasEnteredPin() && GameServer.properties().requireBankPinOnLogin) {
            getBankPin().enterPin();
        }

        if (newAccount) {
            //Join new players to help channel.
            ClanManager.join(this, "help");
            interfaceManager.open(3559);
            setNewPassword("");
            setRunningEnergy(100.0, true);
            message("Welcome to " + GameConstants.SERVER_NAME + ".");
        } else {
            message("Welcome back to " + GameConstants.SERVER_NAME + ".");
        }

        if (clanChat != null && !clanChat.isEmpty()) {
            ClanManager.join(this, clanChat);
        }

        QuestTab.refreshInfoTab(this);

        //Update info
        restartTasks();

        auditTabs();

        if (Referrals.INSTANCE.getCOMMAND_ENABLED()) {
            Referrals.INSTANCE.fetchDbId(this, true);
            Referrals.INSTANCE.onLoginReferals(this);
        }

        if (getPlayerRights().isStaffMember(this) && !username.equalsIgnoreCase("Patrick")) {
            World.getWorld().sendWorldMessage("[Staff]: <img=" + getPlayerRights().getSpriteId() + ">" + Color.staffColor(this, getPlayerRights().getName()) + "</img> " + Color.BLUE.wrap(username) + " has logged in.");
        }

        long endTime = System.currentTimeMillis() - startTime;
        GameEngine.profile.login = System.currentTimeMillis() - startTime;
        //logger.info("it took " + endTime + "ms for processing player login.");
        //System.out.println("it took " + endTime + "ms for processing player login.");

        if (GameServer.properties().enableSql) {
            GameEngine.getInstance().submitLowPriority(() -> {
                try {
                    //Here we use execute instead of submit, since we want this to be executed synchronously and not asynchronously, since we want to wait for the response of the query before continuing execution in this LoginResponses class.
                    muted = GameServer.getDatabaseService().execute(new GetMuteStatusDatabaseTransaction(username));
                } catch (Exception e) {
                    logger.catching(e);
                }
            });
        }
    }

    private void moveFromRegionOnLogin() {
        if (tile().region() == 10536) {
            teleport(new Tile(2657, 2639, 0));
        }

        if (jailed() && tile().region() != 13103) { // Safety since it was possible to escape.
            Teleports.basicTeleport(this, new Tile(3290, 3017));
        }

        if (tile().region() == 9551) {
            //restart the wave on login
            heal(maxHp());
            int wave = getAttribOr(AttributeKey.FIGHT_CAVES_WAVE, 1);
            MinigameManager.playMinigame(this, new FightCavesMinigame(wave));
        }

        //Move player out Zulrah area on login
        if (tile().region() == 9008) {
            teleport(2201, 3057, 0);
        }
    }

    private static final Set<String> veteranGiftClaimedIP = new HashSet<>();
    private static final Set<String> veteranGiftClaimedMAC = new HashSet<>();

    private static final Set<String> playtimeGiftClaimedIP = new HashSet<>();
    private static final Set<String> playtimeGiftClaimedMAC = new HashSet<>();

    public static void init() {
        veteranGiftClaimed("./data/saves/veteranGiftsClaimed.txt");
        playtimeGiftClaimed("./data/saves/playtimeGiftsClaimed.txt");
    }

    public static void playtimeGiftClaimed(String directory) {
        try {
            try (BufferedReader in = new BufferedReader(new FileReader(directory))) {
                String data;
                while ((data = in.readLine()) != null) {
                    playtimeGiftClaimedIP.add(data);
                    playtimeGiftClaimedMAC.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void veteranGiftClaimed(String directory) {
        try {
            try (BufferedReader in = new BufferedReader(new FileReader(directory))) {
                String data;
                while ((data = in.readLine()) != null) {
                    veteranGiftClaimedIP.add(data);
                    veteranGiftClaimedMAC.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void restartTasks() {
        decreaseStats.start(60);
        increaseStats.start(60);

        if (this.getSpecialAttackPercentage() < 100) {
            TaskManager.submit(new RestoreSpecialAttackTask(this));
        }

        int staminaPotionTicks = this.getAttribOr(AttributeKey.STAMINA_POTION_TICKS, 0);
        if (staminaPotionTicks > 0) {
            int seconds = (int) Utils.ticksToSeconds(staminaPotionTicks);
            packetSender.sendEffectTimer(seconds, EffectTimer.STAMINA);
        }

        int specialTeleblockTicks = this.getTimers().left(TimerKey.SPECIAL_TELEBLOCK);
        if (specialTeleblockTicks > 0) {
            teleblock(specialTeleblockTicks, true);
        }

        int deathTeleportTimer = this.getTimers().left(TimerKey.DEATH_TELEPORT_TIMER);
        if (deathTeleportTimer > 0) {
            getTimers().extendOrRegister(TimerKey.DEATH_TELEPORT_TIMER, deathTeleportTimer);
        }

        int dropRateLampTicks = this.getAttribOr(AttributeKey.DOUBLE_DROP_LAMP_TICKS, 0);
        if (dropRateLampTicks > 0) {
            int ticksToMinutes = dropRateLampTicks / 100;
            message(Color.BLUE.tag() + "Bonus double drops active for " + ticksToMinutes + " more minutes.");
            packetSender.sendEffectTimer((int) Utils.ticksToSeconds(dropRateLampTicks), EffectTimer.DROP_LAMP);
            TaskManager.submit(new DropRateLampTask(this));
        }

        int doubleExpTicks = this.getAttribOr(AttributeKey.DOUBLE_EXP_TICKS, 0);
        if (doubleExpTicks > 0) {
            int ticksToMinutes = doubleExpTicks / 100;
            message(Color.BLUE.tag() + "Bonus double exp active for " + ticksToMinutes + " more minutes.");
            packetSender.sendEffectTimer((int) Utils.ticksToSeconds(doubleExpTicks), EffectTimer.DOUBLE_EXP);
            TaskManager.submit(new DoubleExpTask(this));
        }

        int accountPinFrozenTicks = this.getAttribOr(AttributeKey.ACCOUNT_PIN_FREEZE_TICKS, 0);
        if (accountPinFrozenTicks > 0) {
            TaskManager.submit(new AccountPinFrozenTask(this));
        }
    }

    public void auditTabs() {
        try {
            if (IntStream.of(this.getBank().tabAmounts).sum() != this.getBank().capacity() - bank.getFreeSlots()) {
                if (getPlayerRights().isDeveloperOrGreater(this)) {
                    message("<col=ca0d0d>Bank tabAmounts does not equal used slots. ::fixtabs will reset all tabs");
                    if (bank.size() < 15) {
                        // on dev accs just reset the whole thing to instantly fix (we dont care about loss of tab order)
                        bank.tabAmounts = new int[10];
                        bank.tabAmounts[0] = bank.size();
                    }
                }
                int tab = 0;
                int tabStartPos = 0;
                for (int tabAmount : bank.tabAmounts) {
                    if (tabAmount == 0) break; // tab not used
                    for (int i = tabStartPos; i < tabStartPos + tabAmount; i++) {
                        Item item = bank.getItems()[i];
                        if (item == null) {
                            logger.error("found null slot in middle of bank: player {} slot {} in tab {} tabsize {}",
                                getMobName(), i, tab, tabAmount);
                            Item[] proximity = new Item[10];
                            int k = 0;
                            for (int j = Math.max(0, i - 5); j < i + 5; j++) {
                                if (k >= proximity.length || j >= bank.getItems().length)
                                    break;
                                proximity[k++] = bank.getItems()[j];
                            }
                            logger.error("closest items: " + Arrays.toString(Arrays.stream(proximity).map(i2 -> i2 == null ? "?" : i2.name()).toArray()));
                            // in this case, tabsize -=1 and shuffle everything.
                            if (i == (tabStartPos + tabAmount) - 1) {
                                // NULL is the last item in a tab. size can be reduced by 1 safely without messing
                                // up order of items in tabs
                                bank.tabAmounts[tab] -= 1; // reduce to fix
                                logger.error("tabfix 1 for {}", getMobName());
                            } else {
                                // null items appears Not at the end of the tab. dodgy stuff.
                                // yoink items removing nulls
                                bank.shift();
                                // now reduce size safely
                                bank.tabAmounts[tab] -= 1; // reduce to fix
                                logger.error("tabfix 2 for {}", getMobName());
                            }
                        }
                    }
                    tabStartPos = tabStartPos + tabAmount;
                    tab++;
                }
                if (tab >= bank.tabAmounts.length)
                    tab--; // dont throw AIOOB ex, use lower tab
                // start at the first available free slot, aka after all bank tabs finish
                tab--;
                int hiddenItems = 0;
                for (int i = tabStartPos; i < bank.capacity(); i++) {
                    if (bank.getItems()[i] != null) {
                        logger.error("Player {} tab {} size was {} but item {} exists after this caret, increasing tabsize to fix",
                            getMobName(), tab, bank.tabAmounts[tab], bank.getItems()[i]);
                        hiddenItems++;
                    }
                }
                if (hiddenItems > 0) {
                    logger.error("tabfix 3 for {} had {} hidden items", getMobName(), hiddenItems);
                    // put it into the last tab
                    bank.tabAmounts[tab] += hiddenItems;
                }
                logger.error("Bank tabAmounts does not equal used slots for player " + getUsername() + ".");
                //Utils.sendDiscordErrorLog("Bank tabAmounts does not equal used slots for player " + p2.getUsername() + ".");
            }
        } catch (Exception e) {
            // doesnt matter if this fails
            logger.error("banktab fix yeeted", e);
        }
    }

    /**
     * Resets the player's skills to default.
     */
    public void resetSkills() {
        getBank().depositeEquipment();
        getBank().depositInventory();
        for (int skillId = 0; skillId < Skills.SKILL_COUNT; skillId++) {
            skills.setXp(skillId, Skills.levelToXp(1));
        }
        skills.setXp(3, Skills.levelToXp(10));
        skills.update();
    }

    public void resetContainers() {
        getBank().clear(false);
        getBank().tabAmounts = new int[10];
        inventory().clear(false);
        getEquipment().clear(false);
        getRunePouch().clear(false);
        getLootingBag().clear(false);
        setTile(GameServer.properties().defaultTile.copy().add(Utils.getRandom(2), Utils.getRandom(2)));
        setSpellbook(MagicSpellbook.NORMAL);
        setMemberRights(MemberRights.NONE);
        putAttrib(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D);
        //Cancel all timers
        getTimers().cancel(TimerKey.FROZEN); //Remove frozen timer key
        getTimers().cancel(TimerKey.REFREEZE);
        getTimers().cancel(TimerKey.STUNNED); //Remove stunned timer key
        getTimers().cancel(TimerKey.TELEBLOCK); //Remove teleblock timer key
        getTimers().cancel(TimerKey.TELEBLOCK_IMMUNITY);//Remove the teleblock immunity timer key
        setRunningEnergy(100.0, true);//Set energy to 100%
        setSpecialAttackPercentage(100);
        setSpecialActivated(false);//Disable special attack
        getMovementQueue().setBlockMovement(false).clear();
    }

    /**
     * Resets the player's attributes to default.
     */
    public void resetAttributes() {
        animate(-1);
        resetFaceTile();// Reset entity facing
        skills.resetStats();//Reset all players stats
        Poison.cure(this); //Cure the player from any poisons
        Venom.cure(2, this, false);
        //Cancel all timers
        putAttrib(AttributeKey.MAGEBANK_MAGIC_ONLY, false); // Let our players use melee again! : )
        clearAttrib(AttributeKey.VENOM_TICKS);
        clearAttrib(VENOMED_BY);
        getTimers().cancel(TimerKey.CHARGE_SPELL); //Removes the spell charge timer from the player
        getTimers().cancel(TimerKey.FROZEN); //Remove frozen timer key
        getTimers().cancel(TimerKey.REFREEZE);
        getTimers().cancel(TimerKey.STUNNED); //Remove stunned timer key
        getTimers().cancel(TimerKey.TELEBLOCK); //Remove teleblock timer key
        getTimers().cancel(TimerKey.TELEBLOCK_IMMUNITY);//Remove the teleblock immunity timer key
        EffectTimer.clearTimers(this);

        setRunningEnergy(100.0, true);
        setSpecialAttackPercentage(100);
        setSpecialActivated(false);//Disable special attack
        CombatSpecial.updateBar(this);
        Prayers.closeAllPrayers(this);//Disable all prayers

        //Update weapon interface
        WeaponInterfaces.updateWeaponInterface(this);
        getMovementQueue().setBlockMovement(false).clear();
    }

    /**
     * Checks if a player is busy.
     *
     * @return
     */
    public boolean busy() {
        return !interfaceManager.isMainClear() || dead() || isNeedsPlacement() || getStatus() != PlayerStatus.NONE;
    }

    /*
     * Fields
     */
    private String username;
    private String password;
    private String newPassword;
    private String hostAddress;
    private Long longUsername;
    private final PacketSender packetSender = new PacketSender(this);
    private final PlayerRelations relations = new PlayerRelations(this);
    private final QuickPrayers quickPrayers = new QuickPrayers(this);
    private PlayerSession session;
    private PlayerInteractingOption playerInteractingOption = PlayerInteractingOption.NONE;
    private PlayerRights rights = PlayerRights.PLAYER;
    private MemberRights memberRights = MemberRights.NONE;
    private PlayerStatus status = PlayerStatus.NONE;
    private String clanChatName = GameServer.properties().defaultClanChat;
    public final Stopwatch last_trap_layed = new Stopwatch();
    private boolean allowRegionChangePacket;
    private boolean usingQuestTab = false;
    private int presetIndex = 0;
    private int interactingNpcId = 0;
    private final RunePouch runePouch = new RunePouch(this);
    private final Inventory inventory = new Inventory(this);
    private final Equipment equipment = new Equipment(this);
    private final PriceChecker priceChecker = new PriceChecker(this);
    private final Stopwatch clickDelay = new Stopwatch();
    private EnterSyntax enterSyntax;
    private MagicSpellbook spellbook = MagicSpellbook.NORMAL;
    private MagicSpellbook previousSpellbook = MagicSpellbook.NORMAL;
    private final SecondsTimer yellDelay = new SecondsTimer();
    public final SecondsTimer increaseStats = new SecondsTimer();
    public final SecondsTimer decreaseStats = new SecondsTimer();

    private int destroyItem = -1;
    private boolean queuedAppearanceUpdate; // Updates appearance on next tick
    private int regionHeight;

    private int duelWins = 0;
    private int duelLosses = 0;

    public int getPlayerQuestTabCycleCount() {
        return playerQuestTabCycleCount;
    }

    public void setPlayerQuestTabCycleCount(int playerQuestTabCycleCount) {
        this.playerQuestTabCycleCount = playerQuestTabCycleCount;
    }

    public boolean isInTournamentLobby() {
        return this.inTournamentLobby;
    }

    public boolean isTournamentSpectating() {
        return this.tournamentSpectating;
    }

    public Tournament getParticipatingTournament() {
        return this.participatingTournament;
    }

    public Player getTournamentOpponent() {
        return this.tournamentOpponent;
    }

    public void setInTournamentLobby(boolean inTournamentLobby) {
        this.inTournamentLobby = inTournamentLobby;
    }

    public void setTournamentSpectating(boolean tournamentSpectating) {
        this.tournamentSpectating = tournamentSpectating;
    }

    public void setParticipatingTournament(Tournament participatingTournament) {
        this.participatingTournament = participatingTournament;
    }

    public void setTournamentOpponent(Player tournamentOpponent) {
        this.tournamentOpponent = tournamentOpponent;
    }

    public void syncContainers() {
        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(this);
        if (group.isPresent()) {
            if (group.get().getGroupStorage(this).dirty) {
                group.get().getGroupStorage(this).sync();
                group.get().getGroupStorage(this).dirty = false;
            }
        }
        if (getBank().dirty) {
            getBank().sync();
            getBank().dirty = false;
        }
        if (inventory().dirty) {
            inventory().sync();
            inventory().dirty = false;
        }
        if (getEquipment().dirty) {
            getEquipment().sync();
            getEquipment().dirty = false;
        }
        if (getPriceChecker().dirty) {
            getPriceChecker().sync();
            getPriceChecker().dirty = false;
        }
        if (getRunePouch().dirty) {
            getRunePouch().sync();
            getRunePouch().dirty = false;
        }
        if (getLootingBag().dirty) {
            getLootingBag().sync();
            getLootingBag().dirty = false;
        }
        if (getDepositBox().dirty) {
            getDepositBox().sync();
            getDepositBox().dirty = false;
        }
    }

    // Combat
    private final SecondsTimer aggressionTolerance = new SecondsTimer();
    private CombatSpecial combatSpecial;

    private final RunEnergy runEnergy = new RunEnergy(this);

    public RunEnergy runEnergy() {
        return runEnergy;
    }

    public double getEnergyDeprecation() {
        double weight = Math.max(0, Math.min(54, getWeight())); // Capped at 54kg - where stamina affect no longer works.. for a QoL. Stamina always helpful!
        return (0.67) + weight / 100.0;
    }

    public double getRecoveryRate() {
        return (8.0 + (skills.level(Skills.AGILITY) / 6.0)) / 100;
    }

    public void setRunningEnergy(double runningEnergy, boolean send) {
        if (runningEnergy > 100) {
            runningEnergy = 100;
        } else if (runningEnergy < 0) {
            runningEnergy = 0;
        }

        if (runningEnergy < 1.0) {
            putAttrib(AttributeKey.IS_RUNNING, false);
            getPacketSender().sendRunStatus();
        }

        putAttrib(AttributeKey.RUN_ENERGY, runningEnergy);

        int re = (int) runningEnergy;
        if (send) {
            GlobalStrings.RUN_ENERGY.send(this, 100);
            getPacketSender().sendRunEnergy(re);
        }
    }

    // Delay for restoring special attack
    private final SecondsTimer specialAttackRestore = new SecondsTimer();

    // Bounty hunter
    private final SecondsTimer targetSearchTimer = new SecondsTimer();
    private final List<String> recentKills = new ArrayList<>(); // Contains ip addresses of recent kills
    private final Queue<ChatMessage> chatMessageQueue = new ConcurrentLinkedQueue<>();
    private ChatMessage currentChatMessage;

    // Logout
    private final SecondsTimer forcedLogoutTimer = new SecondsTimer();
    private final BankPin bankPin = new BankPin(this);
    private final BankPinSettings bankPinSettings = new BankPinSettings(this);

    // Banking
    private String searchSyntax = "";

    // Trading
    private final Trading trading = new Trading(this);
    private final Dueling dueling = new Dueling(this);

    // Presets
    private Presetable currentPreset;
    private Presetable[] presets = new Presetable[20];

    /**
     * The cached player update block for updating.
     */
    private volatile ByteBuf cachedUpdateBlock;

    private int playerQuestTabCycleCount;

    public int getInteractingNpcId() {
        return interactingNpcId;
    }

    public void setInteractingNpcId(int interactingNpcId) {
        this.interactingNpcId = interactingNpcId;
    }

    public int getPresetIndex() {
        return presetIndex;
    }

    public void setPresetIndex(int presetIndex) {
        this.presetIndex = presetIndex;
    }

    public PlayerSession getSession() {
        return session;
    }

    public String getUsername() {
        return username;
    }

    public Player setUsername(String username) {
        this.username = username;
        return this;
    }

    public Long getLongUsername() {
        return longUsername;
    }

    public Player setLongUsername(Long longUsername) {
        this.longUsername = longUsername;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Player setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Return the password that has been changed by a command.
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Set the password that has been changed by a command.
     */
    public Player setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public Player setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
        return this;
    }

    public HashMap<String, Integer> getHostAddressMap() {
        return hostAddressMap;
    }

    public void setHostAddressMap(HashMap<String, Integer> hostAddressMap) {
        this.hostAddressMap = hostAddressMap;
    }

    private HashMap<String, Integer> hostAddressMap = new HashMap<>();

    public PlayerRights getPlayerRights() {
        return rights;
    }

    public Player setPlayerRights(PlayerRights rights) {
        this.rights = rights;
        return this;
    }

    public MemberRights getMemberRights() {
        return memberRights;
    }

    public Player setMemberRights(MemberRights memberRights) {
        this.memberRights = memberRights;
        return this;
    }

    public PacketSender getPacketSender() {
        return packetSender;
    }

    public SecondsTimer getForcedLogoutTimer() {
        return forcedLogoutTimer;
    }

    public PlayerRelations getRelations() {
        return relations;
    }

    public int tabSlot = 0;

    /**
     * The dialogue manager instance
     */
    private final DialogueManager dialogueManager = new DialogueManager(this);

    /**
     * Gets the dialogue manager
     *
     * @return
     */
    public DialogueManager getDialogueManager() {
        return dialogueManager;
    }

    public void setAllowRegionChangePacket(boolean allowRegionChangePacket) {
        this.allowRegionChangePacket = allowRegionChangePacket;
    }

    public boolean isAllowRegionChangePacket() {
        return allowRegionChangePacket;
    }

    public PlayerInteractingOption getPlayerInteractingOption() {
        return playerInteractingOption;
    }

    public Player setPlayerInteractingOption(PlayerInteractingOption playerInteractingOption) {
        this.playerInteractingOption = playerInteractingOption;
        return this;
    }

    public RunePouch getRunePouch() {
        return runePouch;
    }

    public Inventory inventory() {
        return inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * Weight of the player
     */
    private double weight;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public CombatSpecial getCombatSpecial() {
        return combatSpecial;
    }

    public void setCombatSpecial(CombatSpecial combatSpecial) {
        this.combatSpecial = combatSpecial;
    }

    public MagicSpellbook getSpellbook() {
        return spellbook;
    }

    public MagicSpellbook getPreviousSpellbook() {
        return previousSpellbook;
    }

    public void setSpellbook(MagicSpellbook spellbook) {
        this.spellbook = spellbook;
    }

    public void setPreviousSpellbook(MagicSpellbook previousSpellbook) {
        this.previousSpellbook = previousSpellbook;
    }

    public void setDestroyItem(int destroyItem) {
        this.destroyItem = destroyItem;
    }

    public int getDestroyItem() {
        return destroyItem;
    }

    public Stopwatch getClickDelay() {
        return clickDelay;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public Player setStatus(PlayerStatus status) {
        this.status = status;
        return this;
    }

    public EnterSyntax getEnterSyntax() {
        return enterSyntax;
    }

    public void setEnterSyntax(EnterSyntax enterSyntax) {
        this.enterSyntax = enterSyntax;
    }

    private final PresetManager presetManager = new PresetManager(this);

    public final PresetManager getPresetManager() {
        return presetManager;
    }

    private final LootingBag lootingBag = new LootingBag(this);

    public final LootingBag getLootingBag() {
        return lootingBag;
    }

    private final DepositBox depositBox = new DepositBox(this);

    public final DepositBox getDepositBox() {
        return depositBox;
    }

    private final Bank bank = new Bank(this);

    public final Bank getBank() {
        return bank;
    }

    public PriceChecker getPriceChecker() {
        return priceChecker;
    }

    public Trading getTrading() {
        return trading;
    }

    public Presetable[] getPresets() {
        return presets;
    }

    public void setPresets(Presetable[] sets) {
        this.presets = sets;
    }

    public Presetable getCurrentPreset() {
        return currentPreset;
    }

    public void setCurrentPreset(Presetable currentPreset) {
        this.currentPreset = currentPreset;
    }

    private Object[] lastPreset;

    public Object[] getLastPreset() {
        return lastPreset;
    }

    public void setLastPreset(final Object[] lastPresetData) {
        this.lastPreset = lastPresetData;
    }

    public Queue<ChatMessage> getChatMessageQueue() {
        return chatMessageQueue;
    }

    public ChatMessage getCurrentChatMessage() {
        return currentChatMessage;
    }

    public void setCurrentChatMessage(ChatMessage currentChatMessage) {
        this.currentChatMessage = currentChatMessage;
    }

    public QuickPrayers getQuickPrayers() {
        return quickPrayers;
    }

    public SecondsTimer getYellDelay() {
        return yellDelay;
    }

    public String getKillDeathRatio() {
        double kc = 0;
        int kills = this.getAttribOr(AttributeKey.PLAYER_KILLS, 0);
        int deaths = this.getAttribOr(AttributeKey.PLAYER_DEATHS, 0);
        if (deaths == 0) {
            kc = kills;
        } else {
            kc = ((double) kills / deaths);
        }
        return String.valueOf(Math.round(kc * 100) / 100.0);
    }

    public List<String> getRecentKills() {
        return recentKills;
    }

    public SecondsTimer getTargetSearchTimer() {
        return targetSearchTimer;
    }

    public SecondsTimer getSpecialAttackRestore() {
        return specialAttackRestore;
    }

    public boolean queuedAppearanceUpdate() {
        return queuedAppearanceUpdate;
    }

    public void setQueuedAppearanceUpdate(boolean updateAppearance) {
        this.queuedAppearanceUpdate = updateAppearance;
    }

    public Dueling getDueling() {
        return dueling;
    }

    public void setCachedUpdateBlock(ByteBuf cachedUpdateBlock) {
        this.cachedUpdateBlock = cachedUpdateBlock;
    }

    public int getRegionHeight() {
        return regionHeight;
    }

    public void setRegionHeight(int regionHeight) {
        this.regionHeight = regionHeight;
    }

    private ExpMode mode = ExpMode.ROOKIE;

    public ExpMode expmode() {
        return mode;
    }

    public void expmode(ExpMode mode) {
        this.mode = mode;
    }

    public void message(String message) {
        if (message == null)
            return;
        getPacketSender().sendMessage(message);
    }

    public void message(String format, Object... params) {
        if (format == null)
            return;
        String message = params.length > 0 ? String.format(format, (Object[]) params) : format;
        getPacketSender().sendMessage(message);
    }

    public void sendScroll(String title, String... lines) {

        for (int counter = 21408; counter < 21609; counter++) {
            packetSender.sendString(counter, "");
        }

        int childId = 21408;

        packetSender.sendString(21403, "<col=" + Color.MAROON.getColorValue() + ">" + title + "</col>");

        for (String s : lines)
            packetSender.sendString(childId++, s);

        interfaceManager.open(21400);
    }

    public void debug(String format, Object... params) {
        if (rights.isDeveloperOrGreater(this)) {
            if (getAttribOr(AttributeKey.DEBUG_MESSAGES, false)) {
                getPacketSender().sendMessage(params.length > 0 ? String.format(format, (Object[]) params) : format);
            }
        }
    }

    public void debugMessage(String message) {
        boolean debugMessagesEnabled = getAttribOr(AttributeKey.DEBUG_MESSAGES, true);
        //Removed debug mode check, let's check it per player so we can use it any time on live.
        if (getPlayerRights().isDeveloperOrGreater(this) && debugMessagesEnabled) {
            getPacketSender().sendMessage("[Debug] " + message);
        }
    }

    /**
     * We need this because our movementQueue isn't properly setup. So we need to toggle off running.
     */
    public void agilityWalk(boolean reset) {
        if (reset) {
            this.putAttrib(AttributeKey.IS_RUNNING, true);
        } else {
            this.putAttrib(AttributeKey.IS_RUNNING, false);
        }
        this.getPacketSender().sendRunStatus();
    }

    public void sound(int id) {
        if (GameServer.properties().soundsEnabled)
            packetSender.sendSound(id, 0, 0, 80);
    }

    public void sound(int id, int delay) {
        if (GameServer.properties().soundsEnabled)
            packetSender.sendSound(id, 0, delay, 8);
    }

    private Task distancedTask;
    public final Stopwatch afkTimer = new Stopwatch();

    public void setDistancedTask(Task task) {
        stopDistancedTask();
        this.distancedTask = task;
        if (task != null) {
            TaskManager.submit(task);
        }
    }

    public void stopDistancedTask() {
        if (distancedTask != null && distancedTask.isRunning()) {
            distancedTask.stop();
        }
    }

    // Time the account was last logged in
    private Timestamp lastLogin = new Timestamp(new Date().getTime());

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp timestamp) {
        lastLogin = timestamp;
    }

    // Time the account was created
    private Timestamp creationDate;

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp timestamp) {
        creationDate = timestamp;
    }

    // IP the account was created with
    private String creationIp;

    public String getCreationIp() {
        return creationIp;
    }

    public void setCreationIp(String creationIp) {
        this.creationIp = creationIp;
    }

    private boolean invulnerable;

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public int getDuelWins() {
        return duelWins;
    }

    public void setDuelWins(int duelWins) {
        this.duelWins = duelWins;
    }

    public int getDuelLosses() {
        return duelLosses;
    }

    public void setDuelLosses(int duelLosses) {
        this.duelLosses = duelLosses;
    }

    public void setUsingQuestTab(boolean usingQuestTab) {
        this.usingQuestTab = usingQuestTab;
    }

    public BankPin getBankPin() {
        return bankPin;
    }

    public BankPinSettings getBankPinSettings() {
        return bankPinSettings;
    }

    boolean inTournamentLobby, tournamentSpectating;

    Tournament participatingTournament;

    public boolean inActiveTournament() {
        return participatingTournament != null;
    }

    Player tournamentOpponent;

    private final HashMap<Achievements, Integer> achievements = new HashMap<>(Achievements.values().length) {
        @Serial
        private static final long serialVersionUID = 1842952445111093360L;

        {
            for (final Achievements achievement : Achievements.values()) {
                put(achievement, 0);
            }
        }
    };

    public HashMap<Achievements, Integer> achievements() {
        return achievements;
    }

    public int achievementsCompleted() {
        int completed = 0;
        for (final Achievements achievement : this.achievements().keySet()) {
            if (achievement != null && this.achievements().get(achievement) == achievement.getCompleteAmount()) {
                completed++;
            }
        }
        return completed;
    }

    public boolean completedAllAchievements() {
        return achievementsCompleted() >= Achievements.getTotal() - 1;
    }

    private final BossKillLog bossKillLog = new BossKillLog(this);

    /**
     * Returns the single instance of the {@link BossKillLog} class for this player.
     *
     * @return the tracker class
     */
    public BossKillLog getBossKillLog() {
        return bossKillLog;
    }

    private final SlayerKillLog slayerKillLog = new SlayerKillLog(this);

    /**
     * Returns the single instance of the {@link SlayerKillLog} class for this player.
     *
     * @return the tracker class
     */
    public SlayerKillLog getSlayerKillLog() {
        return slayerKillLog;
    }

    @Override
    public void autoRetaliate(Mob attacker) {
        if (dead() || hp() < 1 || !getMovementQueue().empty()) {
            return;
        }
        super.autoRetaliate(attacker);
    }

    @Override
    public void takehitSound(Hit hit) {
        if (hit == null)
            return;
        int blocksound = CombatSounds.block_sound(this, hit.getDamage());
        int dmgsound = CombatSounds.damage_sound();

        //sound(blocksound);
        if (hit.getDamage() > 0)
            sound(dmgsound, 20);

        if (hit.getAttacker() != null && hit.getAttacker() instanceof Player) {
            Player from = (Player) hit.getAttacker();
            from.sound(blocksound);
            if (hit.getDamage() > 0)
                from.sound(dmgsound, 20);
        }
    }

    /**
     * Clears instances during the following actions:
     * Teleportation, death and logout.
     */
    public void clearInstance() {
        if (krakenInstance != null && krakenInstance.getInstance() != null) {
            InstancedAreaManager.getSingleton().disposeOf(krakenInstance.getInstance());
            putAttrib(AttributeKey.TENTACLES_DISTURBED, 0);
        }

        if (zulrahInstance != null && zulrahInstance.getInstance() != null) {
            zulrahInstance.clear(this);
            InstancedAreaManager.getSingleton().disposeOf(zulrahInstance.getInstance());
        }

        if (vorkathInstance != null && vorkathInstance.getInstance() != null) {
            vorkathInstance.clear(this);
            InstancedAreaManager.getSingleton().disposeOf(vorkathInstance.getInstance());
        }

        if (bryophytaInstance != null && bryophytaInstance.getInstance() != null) {
            bryophytaInstance.clear(this);
            this.getPacketSender().sendEffectTimer(0, EffectTimer.MONSTER_RESPAWN);
            InstancedAreaManager.getSingleton().disposeOf(bryophytaInstance.getInstance());
        }

        if (skotizoInstance != null && skotizoInstance.getInstance() != null) {
            skotizoInstance.clear(this);
            InstancedAreaManager.getSingleton().disposeOf(skotizoInstance.getInstance());
        }
    }

    @Override
    public void stopActions(boolean cancelMoving) {
        super.stopActions(cancelMoving);

        if (cancelMoving)
            getMovementQueue().clear();

        if (interfaceManager.getMain() > 0) {
            if (interfaceManager.isInterfaceOpen(16200)) {
                gamblingSession.abortGambling();
            }
            interfaceManager.close();
        }

        // all your typical interrupts here
        skills().stopSkillable();

        getMovementQueue().resetFollowing();
    }

    public boolean hasPetOut(String name) {
        //System.out.println(pet().def().name);
        return pet() != null && pet().def().name != null && pet().def().name.contains(name);
    }

    public Npc pet() {
        Npc pet = this.getAttribOr(AttributeKey.ACTIVE_PET, null);

        // Only consider the pet valid if it's non-null and isn't finished.
        if (pet != null && !pet.finished()) {
            return pet;
        }

        return null;
    }

    public int nifflerPouchSize() {
        return this.<ArrayList<Item>>getAttribOr(AttributeKey.NIFFLER_ITEMS_STORED, new ArrayList<Item>()).size();
    }

    public boolean nifflerCanStore(Mob target) {
        if (target != null && target.isPlayer()) {
            Player playerTarg = (Player) target;
            var playerIsIron = gameMode.isIronman() || gameMode.isHardcoreIronman();
            var targIsIron = playerTarg.gameMode.isIronman() || playerTarg.gameMode.isHardcoreIronman();
            if (playerIsIron && !targIsIron) {
                message(Color.RED.wrap(playerTarg.getUsername() + " is not a ironman, your Niffler cannot loot their items."));
                return false;
            }
        }

        if (nifflerPouchSize() >= 28) {
            message(Color.RED.tag() + "Your Niffler cannot hold anymore items in his belly.");
            return false;
        }
        return true;
    }

    public boolean nifflerPetOut() {
        return this.hasPetOut("Niffler");
    }

    public void nifflerStore(Item src) {
        final Item item = src.copy(); // yeah lets not fuck with amounts of existing instances yeah? haha nice! this was the presets dupe cause too.
        //Check if we have a pet out.
        if (this.pet() != null) {
            //Check if the pet is a Niffler
            if (this.hasPetOut("Niffler")) {
                if (item.getId() == CustomItemIdentifiers.WILDERNESS_KEY) {
                    message(Color.RED.tag() + "The Niffler couldn't store the Wilderness key.");
                    return;
                }

                //Get the current stored item list
                var currentList = this.<ArrayList<Item>>getAttribOr(AttributeKey.NIFFLER_ITEMS_STORED, new ArrayList<Item>());

                if (currentList != null) {
                    //Store the items in it's respective attribute
                    Optional<Item> any = currentList.stream().filter(i -> i.matchesId(item.getId())).findAny();
                    if (any.isPresent() && ((1L * any.get().getAmount()) + item.getAmount() <= Integer.MAX_VALUE)) {
                        var newAmt = any.get().getAmount() + item.getAmount();
                        any.get().setAmount(newAmt);
                    } else {
                        currentList.add(item);
                    }

                    //Update the attribute
                    this.putAttrib(NIFFLER_ITEMS_STORED, currentList);

                    //Send a message so people know something has been stored
                    String itemName = item.unnote().name();
                    boolean amOverOne = item.getAmount() > 1;
                    String amtString = amOverOne ? "x" + Utils.format(item.getAmount()) + "" : Utils.getAOrAn(item.name());
                    this.message(Color.RED.tag() + "The niffler collected " + amtString + " " + itemName + ".");
                    Utils.sendDiscordInfoLog("Player " + getUsername() + "'s IP " + hostAddress + " niffler collected a: " + amtString + " " + itemName, "niffler_looted");
                }
            }
        }
    }

    public boolean muted() {
        return PlayerPunishment.IPmuted(hostAddress) || PlayerPunishment.muted(username) || this.<Boolean>getAttribOr(MUTED, false) || muted;
    }

    // Main item used
    public Item itemUsed() {
        return this.<Item>getAttrib(AttributeKey.FROM_ITEM);
    }

    // Other item used
    public int itemOnSlot() {
        return this.<Integer>getAttrib(AttributeKey.ALT_ITEM_SLOT);
    }

    // Main item used
    public int itemUsedSlot() {
        return this.<Integer>getAttrib(AttributeKey.ITEM_SLOT);
    }

    public void takePKP(int amount) {
        //PKP as item goes over points
        int pkpInInventory = inventory.count(CustomItemIdentifiers.PKP_TICKET);
        if (pkpInInventory > 0) {
            if(pkpInInventory >= amount) {
                inventory.remove(CustomItemIdentifiers.PKP_TICKET, amount);
                return;
            }
        }
        var pkPoints = this.<Integer>getAttribOr(AttributeKey.PK_POINTS, 0);
        if (pkPoints >= amount) {
            putAttrib(AttributeKey.PK_POINTS, pkPoints - amount);
            packetSender.sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(this));
        }
    }

    public void itemDialogue(String message, int item) {
        this.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.ITEM_STATEMENT, new Item(item), "", message);
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    stop();
                }
            }
        });
    }

    public void confirmDialogue(Object[] params, String title, String optionOne, String optionTwo, Runnable runnable) {
        this.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.STATEMENT, params);
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    send(DialogueType.OPTION, title.isEmpty() ? DEFAULT_OPTION_TITLE : title, optionOne, optionTwo);
                    setPhase(1);
                }
            }

            @Override
            protected void select(int option) {
                if (isPhase(1)) {
                    stop();
                    if (option == 1) {
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                }
            }
        });
    }

    public void itemBox(String message, int id) {
        this.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.ITEM_STATEMENT, new Item(id), "", message);
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    stop();
                }
            }
        });
    }

    public void doubleItemBox(String message, int id, int id2) {
        this.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.DOUBLE_ITEM_STATEMENT, new Item(id), new Item(id2), message);
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    stop();
                }
            }
        });
    }

    public void itemBox(String message, int id, int amount) {
        this.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.ITEM_STATEMENT, new Item(id, amount), "", message);
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    stop();
                }
            }
        });
    }

    public void messageBox(String message) {
        this.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.STATEMENT, message);
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    stop();
                }
            }
        });
    }

    public void optionsTitled(String title, String opt1, String opt2, Runnable runnable) {
        this.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, title, opt1, opt2);
                setPhase(0);
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        if (runnable != null) {
                            stop();
                            runnable.run();
                        }
                    }
                    if (option == 2) {
                        stop();
                    }
                }
            }
        });
    }

    public void doubleItemBox(String message, Item first, Item second) {
        this.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.DOUBLE_ITEM_STATEMENT, first, second, message);
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    stop();
                }
            }
        });
    }

    public static int warnTimeMs = 20;

    public void sequence() {
        try {

            Runnable total = () -> {
                time(t -> {
                    perf.logout += t.toNanos();
                    World.getWorld().benchmark.allPlayers.logout += t.toNanos();
                }, logR);
                time(t -> {
                    perf.qtStuffs += t.toNanos();
                    World.getWorld().benchmark.allPlayers.qtStuffs += t.toNanos();
                }, qtStuff);
                time(t -> {
                    perf.timers += t.toNanos();
                    World.getWorld().benchmark.allPlayers.timers += t.toNanos();
                }, timers);
                time(t -> {
                    perf.actions += t.toNanos();
                    World.getWorld().benchmark.allPlayers.actions += t.toNanos();
                }, actions);
                time(t -> {
                    perf.tasks += t.toNanos();
                    World.getWorld().benchmark.allPlayers.tasks += t.toNanos();
                }, tasks);
                time(t -> {
                    perf.bmove += t.toNanos();
                    World.getWorld().benchmark.allPlayers.bmove += t.toNanos();
                }, beforemove);
                time(t -> {
                    perf.move += t.toNanos();
                    World.getWorld().benchmark.allPlayers.move += t.toNanos();
                }, movement);
                time(t -> {
                    perf.regions += t.toNanos();
                    World.getWorld().benchmark.allPlayers.regions += t.toNanos();
                }, regions);
                time(t -> {
                    perf.controllers += t.toNanos();
                    World.getWorld().benchmark.allPlayers.controllers += t.toNanos();
                }, controllers);
                time(t -> {
                    perf.cbBountyFlush += t.toNanos();
                    World.getWorld().benchmark.allPlayers.cbBountyFlush += t.toNanos();
                }, cbBountyFlush);
                time(t -> {
                    perf.end += t.toNanos();
                    World.getWorld().benchmark.allPlayers.end += t.toNanos();
                }, end);
            };
            time(t -> {
                perf.total += t.toNanos();
                World.getWorld().benchmark.allPlayers.total += t.toNanos();
            }, total);

            if ((int) (1. * perf.total / 1_000_000.) > warnTimeMs) {
                logger.trace("Player {} sequence took {}ms : {}", getMobName(), (int) (1. * perf.total / 1_000_000.), perf.toString());
            }

        } catch (Exception e) {
            logger.error("Error processing logic for Player: {}.", this);
            logger.error(captureState());
            logger.catching(e);
        }
    }

    Runnable logR = this::fireLogout,
        qtStuff = () -> {
            setPlayerQuestTabCycleCount(getPlayerQuestTabCycleCount() + 1);
            var gametime = (Integer) getAttribOr(GAME_TIME, 0) + 1;
            putAttrib(GAME_TIME, gametime);// Increment ticks we've played for

            //Always update this line, only if interface is open.
            if (interfaceManager.isInterfaceOpen(TournamentUtils.TOURNAMENT_INTERFACE)) {
                getPacketSender().sendString(TournamentUtils.TOURNAMENT_TIME_LEFT_FRAME, TournamentManager.tournamentInfo());
            }

            if (interfaceManager.isInterfaceOpen(DAILY_TASK_MANAGER_INTERFACE)) {
                getPacketSender().sendString(TIME_FRAME_TEXT_ID, "Activity (Expires: <col=ffff00>" + DailyTaskManager.timeLeft() + ")");
            }

            var staminaTicks = this.<Integer>getAttribOr(STAMINA_POTION_TICKS, 0);
            if (staminaTicks > 0) {
                staminaTicks--;
                this.putAttrib(STAMINA_POTION_TICKS, staminaTicks);
                if (staminaTicks == 50) {
                    message("<col=8f4808>Your stamina potion is about to expire.");
                    sound(3120);
                } else if (staminaTicks == 0) {
                    message("<col=8f4808>Your stamina potion has expired.");
                    sound(2672);
                    packetSender.sendStamina(false).sendEffectTimer(0, EffectTimer.STAMINA);
                }
            }

            // Refresh the quest tab every minute (every 100 ticks)
            if (GameServer.properties().autoRefreshQuestTab && getPlayerQuestTabCycleCount() == GameServer.properties().refreshQuestTabCycles) {
                setPlayerQuestTabCycleCount(0);

                //We only have to update the uptime here, every other line is automatically updated.
                this.getPacketSender().sendString(TIME.childId, QuestTab.InfoTab.INFO_TAB.get(TIME.childId).fetchLineData(this));
                this.getPacketSender().sendString(UPTIME.childId, QuestTab.InfoTab.INFO_TAB.get(UPTIME.childId).fetchLineData(this));
                this.getPacketSender().sendString(WILDERNESS_ACTIVITY.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.WILDERNESS_ACTIVITY.childId).fetchLineData(this));
                this.getPacketSender().sendString(WILDERNESS_ACTIVITY_LOCATION.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.WILDERNESS_ACTIVITY_LOCATION.childId).fetchLineData(this));

                LocalDateTime now = LocalDateTime.now();
                long minutesTillWildyBoss = now.until(WorldBossEvent.next, ChronoUnit.MINUTES);
                long minutesTillWildyKey = now.until(WildernessKeyPlugin.next, ChronoUnit.MINUTES);
                long minutesTillChaoticNightmare = now.until(ChaoticNightmare.next, ChronoUnit.MINUTES);
                long minutesTillHPEvent = now.until(HpEvent.next, ChronoUnit.MINUTES);
                long minutesTillHauntedChestEvent = now.until(HauntedChest.next, ChronoUnit.MINUTES);

                if (minutesTillWildyBoss == 5) {
                    if (!WorldBossEvent.ANNOUNCE_5_MIN_TIMER) {
                        WorldBossEvent.ANNOUNCE_5_MIN_TIMER = true;
                        World.getWorld().sendWorldMessage("<col=6a1a18><img=1100>The world boss will spawn in 5 minutes, gear up!");
                    }
                }

                if (minutesTillWildyKey == 5) {
                    if (!WildernessKeyPlugin.ANNOUNCE_5_MIN_TIMER) {
                        WildernessKeyPlugin.ANNOUNCE_5_MIN_TIMER = true;
                        World.getWorld().sendWorldMessage("<col=800000><img=1939>The wilderness key will spawn in 5 minutes, gear up!");
                    }
                }

                if (minutesTillChaoticNightmare == 5) {
                    if (!ChaoticNightmare.ANNOUNCE_5_MIN_TIMER) {
                        ChaoticNightmare.ANNOUNCE_5_MIN_TIMER = true;
                        World.getWorld().sendWorldMessage("<col=800000><img=1405>The Chaotic nightmare will spawn in 5 minutes, gear up!");
                    }
                }

                if (minutesTillHPEvent == 5) {
                    if (!HpEvent.ANNOUNCE_5_MIN_TIMER) {
                        HpEvent.ANNOUNCE_5_MIN_TIMER = true;
                        World.getWorld().sendWorldMessage("<col=800000><img=1396>The HP Event will spawn in 5 minutes, gear up!");
                    }
                }

                if (minutesTillHauntedChestEvent == 5) {
                    if (!HauntedChest.ANNOUNCE_5_MIN_TIMER) {
                        HauntedChest.ANNOUNCE_5_MIN_TIMER = true;
                        World.getWorld().sendWorldMessage("<col=800000><img=1394>The Haunted chest will spawn in 5 minutes!");
                    }
                }

                //Update this timer frames every minute.
                this.getPacketSender().sendString(WORLD_BOSS_SPAWN.childId, QuestTab.InfoTab.INFO_TAB.get(WORLD_BOSS_SPAWN.childId).fetchLineData(this));
                this.getPacketSender().sendString(SHOOTING_STAR_SPAWN.childId, QuestTab.InfoTab.INFO_TAB.get(SHOOTING_STAR_SPAWN.childId).fetchLineData(this));
                this.getPacketSender().sendString(WILDERNESS_KEY.childId, QuestTab.InfoTab.INFO_TAB.get(WILDERNESS_KEY.childId).fetchLineData(this));
                this.getPacketSender().sendString(CHAOTIC_NIGHTMARE.childId, QuestTab.InfoTab.INFO_TAB.get(CHAOTIC_NIGHTMARE.childId).fetchLineData(this));
                this.getPacketSender().sendString(HP_EVENT.childId, QuestTab.InfoTab.INFO_TAB.get(HP_EVENT.childId).fetchLineData(this));
                this.getPacketSender().sendString(HAUNTED_CHEST.childId, QuestTab.InfoTab.INFO_TAB.get(HAUNTED_CHEST.childId).fetchLineData(this));
            }
        }, timers = () -> {
        getTimers().cycle(this);
    }, actions = () -> {
        action.sequence();
    }, tasks = () -> {
        TaskManager.sequenceForMob(this);
    }, beforemove = () -> {
        getCombat().preAttack(); // must be beforeMovement
        TargetRoute.beforeMovement(this); // must be after preAttack
    }, movement = () -> {

        getMovementQueue().process(); // must be between before+after movement
        TargetRoute.afterMovement(this); // must be afterMove
    }, regions = () -> {

        int lastregion = getAttribOr(AttributeKey.LAST_REGION, -1);
        // Chunk enter and leave triggers
        int lastChunk = getAttribOr(AttributeKey.LAST_CHUNK, -1);

        if (lastregion != tile.region() || lastChunk != tile.chunk()) {
            MultiwayCombat.refresh(this, lastregion, lastChunk);
        }

        // Update last region and chunk ids
        putAttrib(AttributeKey.LAST_REGION, tile.region());
        putAttrib(AttributeKey.LAST_CHUNK, tile.chunk());
    }, controllers = () -> {
        Prayers.drainPrayer(this);

        //Section 8 Process areas..
        ControllerManager.process(this);
    }, cbBountyFlush = () -> {
        getCombat().process();

        //Section 8 Process Bounty Hunter
        BountyHunter.sequence(this);
    }, end = () -> {
        if (queuedAppearanceUpdate()) {
            getUpdateFlag().flag(Flag.APPEARANCE);
            setQueuedAppearanceUpdate(false);
        }

        //Section 12 Sync containers, if dirty
        postcycle_dirty();

        //Section 13 Send queued chat messages
        if (!getChatMessageQueue().isEmpty()) {
            setCurrentChatMessage(getChatMessageQueue().poll());
            getUpdateFlag().flag(Flag.CHAT);
        } else {
            setCurrentChatMessage(null);
        }

        //Section 14 Decrease boosted stats Increase lowered stats. Don't decrease stats whilst the divine potion effect is active.
        if ((!increaseStats.active() || (decreaseStats.secondsElapsed() >= (Prayers.usingPrayer(this, Prayers.PRESERVE) ? 90 : 60))) && !divinePotionEffectActive()) {
            skills.replenishStats();

            // Reset timers
            if (!increaseStats.active()) {
                increaseStats.start(60);
            }
            if (decreaseStats.secondsElapsed() >= (Prayers.usingPrayer(this, Prayers.PRESERVE) ? 90 : 60)) {
                decreaseStats.start((Prayers.usingPrayer(this, Prayers.PRESERVE) ? 90 : 60));
            }
        }
    };

    public void resetDefault() {
        //place player at edge
        setTile(GameServer.properties().defaultTile.copy());

        //Save player save to re-index
        PlayerSave.save(this);
    }

    /**
     * Resets the player's entire account to default.
     */
    public void completelyResetAccount() {
        //Clear all attributes
        clearAttribs();

        //Reset the account status to brand new
        putAttrib(AttributeKey.NEW_ACCOUNT, true);
        setRunningEnergy(100.0, true);//Set energy to 100%
        putAttrib(IS_RUNNING, false);
        getHostAddressMap().clear();
        putAttrib(COMBAT_MAXED, false);
        Skulling.unskull(this);
        getUnlockedPets().clear();
        getInsuredPets().clear();
        getUnlockedTitles().clear();
        getRelations().getFriendList().clear();
        getRelations().getIgnoreList().clear();
        putAttrib(AttributeKey.ELO_RATING, DEFAULT_ELO_RATING);
        getRecentKills().clear();

        setTile(GameServer.properties().defaultTile.copy());

        //Reset skills
        for (int skill = 0; skill < Skills.SKILL_COUNT; skill++) {
            skills().setLevel(skill, 1, true);
            skills.setXp(skill, Skills.levelToXp(1), true);
            if (skill == Skills.HITPOINTS) {
                skills().setLevel(Skills.HITPOINTS, 10, true);
                skills.setXp(Skills.HITPOINTS, Skills.levelToXp(10), true);
            }
            skills.update(true);
        }

        //Clear slayer blocks
        getSlayerRewards().getBlocked().clear();

        //Clear slayer unlocks
        getSlayerRewards().getUnlocks().clear();

        //Clear slayer extends
        getSlayerRewards().getExtendable().clear();

        //Clear the collection log
        getCollectionLog().collectionLog.clear();

        //Clear boss timers
        getBossTimers().getTimes().clear();

        //Clear bank
        getBank().clear(false);
        getBank().tabAmounts = new int[10];
        getBank().placeHolderAmount = 0;

        //Clear inventory
        inventory().clear(false);

        //Clear equipment
        getEquipment().clear(false);

        //Clear rune pouch
        getRunePouch().clear(false);

        //Clear looting bag
        getLootingBag().clear(false);

        //Clear the niffler
        putAttrib(NIFFLER_ITEMS_STORED, new ArrayList<Item>());

        //Clear all achievements
        achievements().clear();

        //Clear presets
        Arrays.fill(getPresets(), null);

        //Reset spellbook and prayer book
        setSpellbook(MagicSpellbook.NORMAL);

        //Reset member ranks
        setMemberRights(MemberRights.NONE);

        //Make sure these points have been reset
        putAttrib(AttributeKey.VOTE_POINTS, 0);
        putAttrib(SLAYER_REWARD_POINTS, 0);
        putAttrib(REFERRER_USERNAME, "");

        //Put back special attack
        setSpecialAttackPercentage(100);
        setSpecialActivated(false);//Disable special attack

        //No idea why this is here
        getMovementQueue().setBlockMovement(false).clear();

        PlayerSave.save(this);
    }
}

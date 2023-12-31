package com.valinor.game.world.entity.mob.player;

import com.valinor.GameServer;
import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.areas.edgeville.Mac;
import com.valinor.game.content.skill.Skillable;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.content.syntax.impl.SetLevel;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunter;
import com.valinor.game.world.entity.combat.prayer.default_prayer.DefaultPrayerData;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.Flag;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.lang.ref.WeakReference;
import java.util.*;

import static com.valinor.game.world.entity.AttributeKey.DOUBLE_EXP_TICKS;
import static com.valinor.game.world.position.AreaConstants.RESOURCE_AREA;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * Created by Bart Pelle on 8/23/2014.
 */
public class Skills {

    public static final int SKILL_COUNT = 23;
    private static final int[] XP_TABLE = new int[100];
    public static boolean USE_EXPERIMENTAL_PERFORMANCE = true;
    private double[] xps = new double[SKILL_COUNT];
    private int[] levels = new int[SKILL_COUNT];
    private final Player player;
    private int combat;
    private final boolean[] dirty = new boolean[SKILL_COUNT];

    public final HashMap<Integer, Double> skillsBeforeTourny = new HashMap<>();

    public Skills(Player player) {
        this.player = player;

        Arrays.fill(levels, 1);

        /* Hitpoints differs :) */
        xps[3] = levelToXp(10);
        levels[3] = 10;
    }

    public void update() {
        update(false);
    }

    public void update(boolean ignore) {
        if (!ignore) {
            for (int skill = 0; skill < SKILL_COUNT; skill++) {
                //Send the skill
                player.getPacketSender().updateSkill(skill, levels[skill], (int) xps[skill]);
            }

            updatePrayerText();
            recalculateCombat();

            //Update prayer orb
            player.getPacketSender().sendString(4012, "" + levels[PRAYER] + "");
            player.getPacketSender().sendString(4013, "" + xpToLevel((int) xps[PRAYER]) + "");

            //Update hp orb
            player.getPacketSender().sendString(4016, "" + levels[HITPOINTS] + "");
            player.getPacketSender().sendString(4017, "" + xpToLevel((int) xps[HITPOINTS]) + "");

            //Send total level
            player.getPacketSender().sendString(10121, "" + totalLevel());
        }
    }

    public void updatePrayerText() {
        int currentLevel = levels[PRAYER];
        int maxLevel = xpToLevel((int) xps[PRAYER]);
        player.getPacketSender().sendString(687, Color.ORANGE.tag() + currentLevel + "/" + maxLevel);
    }

    public void syncDirty() {
        for (int skill = 0; skill < SKILL_COUNT; skill++) {
            if (dirty[skill]) {
                player.getPacketSender().updateSkill(skill, levels[skill], (int) xps[skill]);
                dirty[skill] = false;
            }
        }
    }

    public void makeDirty(int skill) {
        this.makeDirty(skill, false);
    }

    public void makeDirty(int skill, boolean ignore) {
        if (USE_EXPERIMENTAL_PERFORMANCE) {
            dirty[skill] = true;
        } else {
            player.getPacketSender().updateSkill(skill, levels[skill], (int) xps[skill]);
        }

        //Update prayer orb
        player.getPacketSender().sendString(4012, "" + levels[PRAYER] + "");
        player.getPacketSender().sendString(4013, "" + xpToLevel((int) xps[PRAYER]) + "");

        //Update hp orb
        player.getPacketSender().sendString(4016, "" + levels[HITPOINTS] + "");
        player.getPacketSender().sendString(4017, "" + xpToLevel((int) xps[HITPOINTS]) + "");

        if (!ignore) {
            player.skills().updatePrayerText();
        }
    }

    /**
     * Returns the current level a stat is at, could be 50/99 for HP.
     * <br>Use XP to get the real level.
     *
     * @param skill
     * @return
     */
    public int level(int skill) {
        return levels[skill];
    }

    /**
     * Gets the level which your XP qualifies you for
     */
    public int xpLevel(int skill) {
        return xpToLevel((int) xps[skill]);
    }

    public int[] levels() {
        return levels;
    }

    public void setAllLevels(int[] levels) {
        this.levels = levels;
    }

    public double[] xp() {
        return xps;
    }

    public void setAllXps(double[] xps) {
        this.xps = xps;
    }

    public void setXp(int skill, double amt) {
        this.setXp(skill, amt, false);
    }

    public void setXp(int skill, double amt, boolean ignore) {
        xps[skill] = Math.min(200000000, amt);
        int newLevel = xpToLevel((int) xps[skill]);
        levels[skill] = newLevel;

        if (!ignore) {
            recalculateCombat();
            makeDirty(skill);
        }
    }

    public void setLevel(int skill, int lvtemp) {
        this.setLevel(skill, lvtemp, false);
    }

    public void setLevel(int skill, int lvtemp, boolean ignore) {
        levels[skill] = lvtemp;
        if (!ignore) {
            makeDirty(skill);
        }
    }

    public boolean addXp(int skill, double amt) {
        return addXp(skill, amt, true, true);
    }

    public boolean addXp(int skill, double amt, boolean multiplied) {
        return addXp(skill, amt, multiplied, true);
    }

    public boolean addXp(int skill, double amt, boolean multiplied, boolean counter) {
        Mob target = ((WeakReference<Mob>) player.getAttribOr(AttributeKey.TARGET, new WeakReference<>(null))).get();
        // Active target and facing. Can't tell if combat script is running.
        boolean pvp = target != null && target.isPlayer() && target.getIndex() + 32768 == (int) player.getAttribOr(AttributeKey.LAST_FACE_ENTITY_IDX, 0);
        boolean combatxp = skill == ATTACK || skill == STRENGTH || skill == DEFENCE || skill == RANGED || skill == MAGIC || skill == HITPOINTS;
        boolean xplocked = player.getAttribOr(AttributeKey.XP_LOCKED, false);

        if (combatxp && xplocked) { // don't get combat exp when locked.
            return false;
        }

        // Don't add the experience if player is in a tournament.
        if (player.inActiveTournament() || player.isInTournamentLobby())
            return false;

        if (target != null && target.isNpc() && combatxp) { // Don't add exp if the target is hidden or locked.
            Npc npc = (Npc) target;
            if (npc.hidden() || (npc.locked() && !npc.isDamageOkLocked()))
                return false;
        }

        boolean geniePet = player.hasPetOut("Genie pet");

        if (multiplied) {
            if (combatxp) {
                amt *= player.expmode().getCombatExp();
            } else {
                amt *= player.expmode().getExpMultiplier();
            }
        }

        var expBoostUnlock = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.EXP_BOOSTER);
        if(expBoostUnlock) {
            switch (player.expmode()) {
                case ROOKIE -> amt *= 1.10;
                case GLADIATOR -> amt *= 1.5;
                case CHALLENGER -> amt *= 1.3;
            }
        }

        if(player.tile().memberZone()) {
            switch (player.getMemberRights()) {
                case SAPPHIRE_MEMBER -> amt *= 1.10;
                case EMERALD_MEMBER -> amt *= 1.25;
                case RUBY_MEMBER -> amt *= 1.40;
                case DIAMOND_MEMBER -> amt *= 1.60;
                case DRAGONSTONE_MEMBER -> amt *= 1.75;
                case ONYX_MEMBER -> amt *= 1.95;
                case ZENYTE_MEMBER -> amt *= 2.0;
            }
        }

        var ringOfCharosA = player.getEquipment().hasAt(EquipSlot.RING, RING_OF_CHAROSA);

        if(!combatxp && player.getEquipment().hasAt(EquipSlot.RING, EXPLORERS_RING_4)) {
            amt *= 1.25;
        }

        if(combatxp && player.getEquipment().hasAt(EquipSlot.RING, RADAS_BLESSING_4)) {
            amt *= 1.25;
        }

        if(combatxp) {
            amt *= 2.0;
        }

        if(player.tile().inArea(RESOURCE_AREA)) {
            amt *= 1.75;
        }

        if(ringOfCharosA) {
            amt *= 2.0;
        }

        var double_exp_ticks = player.<Integer>getAttribOr(DOUBLE_EXP_TICKS, 0) > 0;

        var doubleExp = geniePet || World.getWorld().xpMultiplier > 1 || double_exp_ticks;

        //Genie pet gives x2 exp
        amt *= doubleExp ? 2.0 : 1.0;

        player.getPacketSender().sendExpDrop(skill, (int) amt, counter);

        int oldLevel = xpToLevel((int) xps[skill]);
        xps[skill] = Math.min(200000000, xps[skill] + amt);
        int newLevel = xpToLevel((int) xps[skill]);

        if (newLevel > oldLevel) {
            if (levels[skill] < newLevel)
                levels[skill] += newLevel - oldLevel;
            player.graphic(199, 124, 0);
        }

        makeDirty(skill);

        //Send total level
        player.getPacketSender().sendString(10121, "" + totalLevel());

        if (oldLevel != newLevel) {
            int levels = newLevel - oldLevel;
            if (levels == 1) {
                player.message("Congratulations, you just advanced %s %s level.", SKILL_INDEFINITES[skill], SKILL_NAMES[skill]);
            } else {
                player.message("Congratulations, you just advanced %d %s levels.", levels, SKILL_NAMES[skill]);
            }

            if (newLevel == 99) {
                player.graphic(1388, 124, 0);
                player.message(Color.ORANGE_RED.tag() + "Congratulations on achieving level 99 in " + SKILL_NAMES[skill] + "!");
                player.message(Color.ORANGE_RED.tag() + "You may now purchase a skillcape from Mac who can be found at home.");
                World.getWorld().sendWorldMessage("<img=452><shad=0><col=" + Color.HOTPINK.getColorValue() + ">" + player.getUsername() + "</col> has just achieved level 99 in " + Color.BLUE.tag() + "" + SKILL_NAMES[skill] + "</col> on a " + Color.BLUE.tag() + " " + Utils.gameModeToString(player) + " exp mode: "+player.expmode().toName()+"</col>!");
            }

            if (totalLevel() >= Mac.TOTAL_LEVEL_FOR_MAXED) {
                World.getWorld().sendWorldMessage("<img=452><shad=0><col=" + Color.HOTPINK.getColorValue() + ">" + player.getUsername() + "</col> has just maxed out on a " + Color.BLUE.tag() + " " + Utils.gameModeToString(player) + "</col>!");
            }

            recalculateCombat();

            if (player.skills().combatLevel() >= 126) {
                player.putAttrib(AttributeKey.COMBAT_MAXED, true);
            }

            //Only unlockable for trained accounts.
            if (totalLevel() >= 750) {
                AchievementsManager.activate(player, Achievements.SKILLER_I, 1);
            }
            if (totalLevel() >= 1000) {
                AchievementsManager.activate(player, Achievements.SKILLER_II, 1);
            }
            if (totalLevel() >= 1500) {
                AchievementsManager.activate(player, Achievements.SKILLER_III, 1);
            }
            if (totalLevel() >= Mac.TOTAL_LEVEL_FOR_MAXED) {
                AchievementsManager.activate(player, Achievements.SKILLER_IV, 1);
            }

            //Send custom interfaces 377 doesn't have these
            if (skill == FARMING) {
                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... options) {
                        send(DialogueType.ITEM_STATEMENT, new Item(5340), "", "Congratulations! You've just advanced Farming level!", "You have reached level " + newLevel + "!");
                        setPhase(0);
                    }
                });
            } else if (skill == CONSTRUCTION) {

            } else if (skill == HUNTER) {
                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... options) {
                        send(DialogueType.ITEM_STATEMENT, new Item(9951), "", "Congratulations! You've just advanced Hunter level!", "You have reached level " + newLevel + "!");
                        setPhase(0);
                    }
                });
            } else {
                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... parameters) {
                        player.getPacketSender().sendString(LEVEL_UP[skill][1], "<col=128>Congratulations, you just advanced a " + SKILL_NAMES[skill] + " level!");
                        player.getPacketSender().sendString(LEVEL_UP[skill][2], "Your " + SKILL_NAMES[skill] + " level is now " + newLevel + ".");
                        player.getPacketSender().sendChatboxInterface(LEVEL_UP[skill][0]);
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
        }

        /*if (xps[skill] == 200_000_000) {
            player.graphic(1388, 124, 0);
            player.message(Color.ORANGE_RED.tag() + "Congratulations on achieving 200m experience in " + SKILL_NAMES[skill] + "!");
            World.getWorld().sendWorldMessage("<img=452><shad=0><col=" + Color.HOTPINK.getColorValue() + ">" + player.getUsername() + "</col> has just achieved 200M exp in " + Color.BLUE.tag() + "" + SKILL_NAMES[skill] + "</col> on a " + Color.BLUE.tag() + " " + Utils.gameModeToString(player) + "</col>!");
        }*/

        update();//Force an update
        return oldLevel != newLevel;
    }

    private static final int[][] LEVEL_UP = {
        {6247, 6248, 6249},
        {6253, 6254, 6255},
        {6206, 6207, 6208},
        {6216, 6217, 6218},
        {4443, 5453, 6114},
        {6242, 6243, 6244},
        {6211, 6212, 6213},
        {6226, 6227, 6228},
        {4272, 4273, 4274},
        {6231, 6232, 6233},
        {6258, 6259, 6260},
        {4282, 4283, 4284},
        {6263, 6264, 6265},
        {6221, 6222, 6223},
        {4416, 4417, 4438},
        {6237, 6238, 6239},
        {4277, 4278, 4279},
        {4261, 4263, 4264},
        {12122, 12123, 12124},
        {8267, 4268, 4269}, //farming
        {4267, 4268, 4269}, //rc
        {8267, 4268, 4269}, //construction
        {8267, 4268, 4269}}; //hunter

    /**
     * Checks if the player is maxed in all combat skills.
     */
    public boolean isCombatMaxed() {
        int maxCount = 7;
        int count = 0;
        for (int index = 0; index < maxCount; index++) {
            if (player.skills().level(index) >= 99) {
                count++;
            }
        }
        return count == maxCount;
    }

    public void update(int skill) {
        makeDirty(skill);
    }

    /**
     * @param skill
     * @param change
     */
    public void alterSkill(int skill, int change) {
        levels[skill] += change;
        if (change > 0 && levels[skill] > xpLevel(skill) + change) { // Cap at realLvl (99) + boost (20) = 118
            levels[skill] = xpLevel(skill) + change;
        }
        if (levels[skill] < 1) { // Min 1
            levels[skill] = 1;
        }
        update(skill);
    }

    public void hpEventLevel(int increaseBy) {
        levels[Skills.HITPOINTS] = increaseBy;
        update(Skills.HITPOINTS);
    }

    public void overloadPlusBoost(int skill) {
        boolean fluffyPet = player.hasPetOut("Fluffy Jr");
        int boost = fluffyPet ? 16 : 6;
        int boostedLevel = (int) ((player.skills().xpLevel(skill) * 0.16) + boost);
        levels[skill] += boostedLevel;
        if (boostedLevel > 0 && levels[skill] > xpLevel(skill) + boostedLevel) { // Cap at realLvl (99) + boost (20) = 118
            levels[skill] = xpLevel(skill) + boostedLevel;
        }
        update(skill);
    }

    public void replenishSkill(int skill, int change) {
        if (levels[skill] < xpLevel(skill)) // Current level under real level
            levels[skill] = Math.min(xpLevel(skill), level(skill) + change);//cap replenish at 99
        update(skill);
    }

    public void replenishStats() {
        if (player.dead() || player.hp() < 1)
            return;

        for (int i = 0; i < SKILL_COUNT; i++) {
            if (levels[i] < xpLevel(i)) {
                levels[i]++;
                update(i);
            } else if (levels[i] > xpLevel(i)) {
                levels[i]--;
                update(i);
            }
        }
    }

    public void replenishStatsToNorm() {
        for (int i = 0; i < SKILL_COUNT; i++) {
            if (levels[i] < xpLevel(i)) {
                levels[i] = xpLevel(i);
                update(i);
            }
        }
    }

    public void resetStats() {
        for (int i = 0; i < SKILL_COUNT; i++) {
            levels[i] = xpLevel(i);
        }
        update();
    }

    public void recalculateCombat() {
        int old = combat;
        double defence = xpLevel(Skills.DEFENCE);
        double attack = xpLevel(Skills.ATTACK);
        double strength = xpLevel(Skills.STRENGTH);
        double prayer = xpLevel(Skills.PRAYER);
        double ranged = xpLevel(Skills.RANGED);
        double magic = xpLevel(Skills.MAGIC);
        double hp = xpLevel(Skills.HITPOINTS);

        int baseMelee = (int) Math.floor(0.25 * (defence + hp + Math.floor(prayer / 2d)) + 0.325 * (attack + strength));
        int baseRanged = (int) Math.floor(0.25 * (defence + hp + Math.floor(prayer / 2d)) + 0.325 * (Math.floor(ranged / 2) + ranged));
        int baseMage = (int) Math.floor(0.25 * (defence + hp + Math.floor(prayer / 2d)) + 0.325 * (Math.floor(magic / 2) + magic));
        combat = Math.max(Math.max(baseMelee, baseMage), baseRanged);

        // If our combat changed, we need to update our looks as that contains our cb level too.
        if (combat != old && player.looks() != null) {
            player.getUpdateFlag().flag(Flag.APPEARANCE);
            player.getPacketSender().sendString(19000, "Combat level: " + combat).sendString(5858, "Combat level: " + combat);
        }
    }

    public long getTotalExperience() {
        return Arrays.stream(xps).mapToLong(e -> (long) e).sum();
    }


    public int combatLevel() {
        return combat;
    }

    public int totalLevel() {
        int total = 0;

        for (int i = 0; i < xps.length; i++) {
            total += xpLevel(i);
        }

        //Max total level is 2277 in osrs however this calculates slightly over.
        if (total > 2277) {
            total = 2277;
        }

        return total;
    }

    /**
     * Converts given XP to the equivalent skill level.
     *
     * @param xp
     * @return
     */
    public static int xpToLevel(double xp) {
        // Most-frequently used variants.
        if (xp >= 13_034_431)//13,034,431 exp is level 99
            return 99;
        if (xp < 83)//83 exp is level 2
            return 1;

        int lvl = 1;
        for (; lvl < 100; lvl++) {
            if (xp < XP_TABLE[lvl])
                break;
        }

        return Math.min(lvl, 99);
    }

    /**
     * Converts skill level to EXP
     *
     * @param level skill id
     * @return XP equivalent to given skill level
     */
    public static int levelToXp(int level) {
        return XP_TABLE[Math.min(99, Math.max(0, level - 1))];
    }

    static {
        // Calculate XP table
        for (int lv = 1, points = 0; lv < 100; lv++) {
            points += Math.floor(lv + 300 * Math.pow(2, lv / 7D));
            XP_TABLE[lv] = points / 4;
        }
    }

    public static final int ATTACK = 0;
    public static final int DEFENCE = 1;
    public static final int STRENGTH = 2;
    public static final int HITPOINTS = 3;
    public static final int RANGED = 4;
    public static final int PRAYER = 5;
    public static final int MAGIC = 6;
    public static final int COOKING = 7;
    public static final int WOODCUTTING = 8;
    public static final int FLETCHING = 9;
    public static final int FISHING = 10;
    public static final int FIREMAKING = 11;
    public static final int CRAFTING = 12;
    public static final int SMITHING = 13;
    public static final int MINING = 14;
    public static final int HERBLORE = 15;
    public static final int AGILITY = 16;
    public static final int THIEVING = 17;
    public static final int SLAYER = 18;
    public static final int FARMING = 19;
    public static final int RUNECRAFTING = 20;
    public static final int HUNTER = 21;
    public static final int CONSTRUCTION = 22;
    // 23 ... sailing meme skill or unreleased w.e

    public static final String[] SKILL_NAMES = {
        "Attack", "Defence", "Strength", "Hitpoints", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching",
        "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer",
        "Farming", "Runecrafting", "Hunter", "Construction"
    };

    public static final String[] SKILL_INDEFINITES = {
        "an", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "an", "a", "a", "a", "a", "a", "a"
    };

    public double totalXp() {
        double xp = 0.0D;
        for (int i = 0; i < SKILL_COUNT; i++) // 23 kills yo .. skill.length is 24!
            xp += xp()[i];
        return xp;
    }

    private boolean attackLevelBoosted() {
        return level(Skills.ATTACK) > xpLevel(Skills.ATTACK);
    }

    private boolean defenceLevelBoosted() {
        return level(Skills.DEFENCE) > xpLevel(Skills.DEFENCE);
    }

    private boolean strengthLevelBoosted() {
        return level(Skills.STRENGTH) > xpLevel(Skills.STRENGTH);
    }

    private boolean rangeLevelBoosted() {
        return level(Skills.RANGED) > xpLevel(Skills.RANGED);
    }

    private boolean magicLevelBoosted() {
        return level(Skills.MAGIC) > xpLevel(Skills.MAGIC);
    }

    public boolean combatStatsBoosted() {
        return attackLevelBoosted() || defenceLevelBoosted() || strengthLevelBoosted() || rangeLevelBoosted() || magicLevelBoosted();
    }

    /**
     * Checks if the button that was clicked is used for setting a skill to a
     * desired level.
     *
     * @param button The button that was clicked.
     * @return True if a skill should be set, false otherwise.
     */
    public boolean pressedSkill(int button) {
        Skill skill = Skill.fromButton(button);

        if (skill != null) {
            boolean isCombatMaxed = player.getAttribOr(AttributeKey.COMBAT_MAXED, false);
            if (!player.getPlayerRights().isDeveloperOrGreater(player) && (!isCombatMaxed && skill.canSetLevel())) {
                player.message("You can set your levels after you've maxed out your combat.");
                return false;
            }

            if (GameServer.properties().debugMode && player.getPlayerRights().isDeveloperOrGreater(player)) {
                player.message("Clicked skill: " + skill.toString());
            }

            if (!skill.canSetLevel()) {
                if(player.getPlayerRights().isDeveloperOrGreater(player)) {
                    player.getDialogueManager().start(new Dialogue() {
                        @Override
                        protected void start(Object... parameters) {
                            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Set level", "Teleport");
                            setPhase(0);
                        }

                        @Override
                        protected void select(int option) {
                            if(isPhase(0)) {
                                if(option == 1) {
                                    player.getInterfaceManager().close();
                                    player.setEnterSyntax(new SetLevel(skill.getId()));
                                    player.getPacketSender().sendEnterAmountPrompt("Please enter your desired " + skill.getName() + " level below.");
                                }
                                if(option == 2) {
                                    stop();
                                    //Skills with no dialogues but a specific teleport.
                                    if(skill.getDialogue() == null) {
                                        if(skill == Skill.SLAYER) {
                                            if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                                Teleports.basicTeleport(player, new Tile(3081,3503));
                                            }
                                            return;
                                        }

                                        if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                            Teleports.basicTeleport(player, new Tile(2791,3537));
                                        }
                                        return;
                                    }
                                    player.getDialogueManager().start(skill.getDialogue());
                                }
                            }
                        }
                    });
                    return true;
                } else {
                    //Skills with no dialogues but a specific teleport.
                    if(skill.getDialogue() == null) {
                        if(skill == Skill.SLAYER) {
                            if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                                Teleports.basicTeleport(player, new Tile(3081,3503));
                            }
                            return true;
                        }

                        if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                            Teleports.basicTeleport(player, new Tile(2791,3537));
                        }
                        return true;
                    }
                    player.getDialogueManager().start(skill.getDialogue());
                    return true;
                }
            }
            if (isCombatMaxed || player.getPlayerRights().isDeveloperOrGreater(player)) {
                if (CombatFactory.inCombat(player) && !player.getPlayerRights().isDeveloperOrGreater(player)) {
                    player.message("You can't change your levels during combat.");
                    return true;
                }
                player.getInterfaceManager().close();
                player.setEnterSyntax(new SetLevel(skill.getId()));
                player.getPacketSender().sendEnterAmountPrompt("Please enter your desired " + skill.getName() + " level below.");
                return true;
            }
            return true;
        }
        return false;
    }

    /**
     * Sets a skill to the desired level.
     *
     * @param skill
     * @param level
     */
    public void clickSkillToChangeLevel(int skill, int level) {
        //Make sure they are in a safe area
        if ((!player.tile().inArea(Tile.EDGEVILLE_HOME_AREA) || WildernessArea.inWilderness(player.tile())) && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("You can only set levels at home.");
            return;
        }

        //make sure they aren't wearing any items which arent allowed to be worn at that level.
        for (Item item : player.getEquipment().getItems()) {
            if (item == null) {
                continue;
            }

            final Map<Integer, Integer> requiredLevels = World.getWorld().equipmentInfo().requirementsFor(item.getId());

            if (requiredLevels != null) {
                final Integer requiredLevel = requiredLevels.get(skill);
                if (requiredLevel != null) {
                    player.message("<col=FF0000>You don't have the level requirements to wear: %s.", World.getWorld().definitions().get(ItemDefinition.class, item.getId()).name);
                    return;
                }
            }
        }

        if (skill == HITPOINTS) {
            if (level < 10) {
                player.message("Hitpoints must be set to at least level 10.");
                return;
            }
        }

        //Set skill level
        player.skills().setXp(skill, Skills.levelToXp(level));
        player.skills().update();
        player.skills().recalculateCombat();

        if (skill == PRAYER) {
            player.getPacketSender().sendConfig(709, Prayers.canUse(player, DefaultPrayerData.PRESERVE, false) ? 1 : 0);
            player.getPacketSender().sendConfig(711, Prayers.canUse(player, DefaultPrayerData.RIGOUR, false) ? 1 : 0);
            player.getPacketSender().sendConfig(713, Prayers.canUse(player, DefaultPrayerData.AUGURY, false) ? 1 : 0);
        }

        //Update weapon tab to send combat level etc.
        player.clearAttrib(AttributeKey.VENGEANCE_ACTIVE);
        Prayers.closeAllPrayers(player);
        BountyHunter.unassign(player);
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }

    /**
     * Starts the {@link Skillable} skill.
     *
     * @param skill
     */
    public void startSkillable(Skillable skill) {
        //Stop previous skills..
        stopSkillable();

        //Close interfaces..
        player.getInterfaceManager().close();

        //Check if we have the requirements to start this skill..
        if (!skill.hasRequirements(player)) {
            return;
        }

        //Start the skill..
        player.setSkillable(Optional.of(skill));
        skill.start(player);
    }

    /**
     * Stops the player's current skill, if they have one active.
     */
    public void stopSkillable() {
        //Stop any previous skill..
        player.getSkillable().ifPresent(e -> e.cancel(player));
        player.setSkillable(Optional.empty());
    }

    /**
     * Gets the max level for said skill.
     *
     * @param skill The skill to get max level for.
     * @return The skill's maximum level.
     */
    public int getMaxLevel(int skill) {
        return xpLevel(skill);
    }

    /**
     * Gets the max level for said skill.
     *
     * @param skill The skill to get max level for.
     * @return The skill's maximum level.
     */
    public int getMaxLevel(Skill skill) {
        return xpLevel(skill.getId());
    }

    public boolean check(int skill, int lvlReq) {
        return level(skill) >= lvlReq;
    }

    /**
     * Checks the fixed (not boosted) level.
     */
    public boolean checkFixed(int skill, int levelReq, String action) {
        if (xpLevel(skill) < levelReq) {
            player.message("You need " + Skills.SKILL_NAMES[skill] + " level of " + levelReq + " or higher to " + action + ".");
            return false;
        }
        return true;
    }

    public boolean check(int skill, int levelReq, String action) {
        if (!check(skill, levelReq)) {
            player.message("You need " + Skills.SKILL_NAMES[skill] + " level of " + levelReq + " or higher to " + action + ".");
            return false;
        }
        return true;
    }

    public boolean check(int skill, int lvlReq, int itemId, String action) {
        if (!check(skill, lvlReq)) {
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.ITEM_STATEMENT, new Item(itemId), "", "You need " + Skills.SKILL_NAMES[skill] + " level of " + lvlReq + " or higher to " + action + ".");
                    setPhase(0);
                }

                @Override
                protected void next() {
                    if (isPhase(0)) {
                        stop();
                    }
                }
            });
            return false;
        }
        return true;
    }

    public boolean check(int skill, int lvlReq, int itemId1, int itemId2, String action) {
        if (!check(skill, lvlReq)) {
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.DOUBLE_ITEM_STATEMENT, new Item(itemId1), new Item(itemId2), "You need " + Skills.SKILL_NAMES[skill] + " level of " + lvlReq + " or higher to " + action + ".");
                    setPhase(0);
                }

                @Override
                protected void next() {
                    if (isPhase(0)) {
                        stop();
                    }
                }
            });
            return false;
        }
        return true;
    }
}

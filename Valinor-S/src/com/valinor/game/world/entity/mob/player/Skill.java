package com.valinor.game.world.entity.mob.player;

import com.valinor.game.content.teleport.skill_teles.*;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.util.Utils;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The enumerated type whose elements represent data for the skills.
 *
 * @author lare96 <http://github.com/lare96>
 */
public enum Skill {

    ATTACK(0,10001,null),
    DEFENCE(1,10007,null),
    STRENGTH(2,10004,null),
    HITPOINTS(3,10002,null),
    RANGED(4,10010,null),
    PRAYER(5,10013,null),
    MAGIC(6,10016,null),
    COOKING(7,10012, new CookingTeleportsD()),
    WOODCUTTING(8,10018, new WoodcuttingTeleportsD()),
    FLETCHING(9,10017,null),
    FISHING(10,10009, new FishingTeleportsD()),
    FIREMAKING(11,10015,new FiremakingTeleportsD()),
    CRAFTING(12,10014,null),
    SMITHING(13,10006, new SmithingTeleportsD()),
    MINING(14,10003, new MiningTeleportsD()),
    HERBLORE(15,10008,null),
    AGILITY(16,10005, new AgilityTeleportsD()),
    THIEVING(17,10011, new ThievingTeleportsD()),
    SLAYER(18,10020,null),
    FARMING(19,10021, new FarmingTeleportsD()),
    RUNECRAFTING(20,10019, new RunecraftingTeleportsD()),
    HUNTER(21,10023, new HunterTeleportsD()),
    CONSTRUCTION(22,10022,null);

    public static final Set<Skill> ALL = EnumSet.allOf(Skill.class);
    private static final Map<Integer, Skill> BUTTON_TO_SKILL = ALL.stream().collect(Collectors.toMap(Skill::getButton, Function.identity()));
    private static final Map<Integer, Skill> ID_TO_SKILL = ALL.stream().collect(Collectors.toMap(Skill::getId, Function.identity()));

    /**
     * The {@link ImmutableSet} which represents the skills that a player can set to a desired level.
     */
    public static final ImmutableSet<Skill> ALLOWED_TO_SET_LEVELS = Sets.immutableEnumSet(ATTACK, DEFENCE, STRENGTH, HITPOINTS, RANGED, PRAYER, MAGIC);

    /** An array of skill names. */
    public static final String[] SKILL_NAMES = new String[] { /* 00 */ "Attack", /* 01 */ "Defence",
        /* 02 */ "Strength", /* 03 */ "Hitpoints", /* 04 */ "Ranged", /* 05 */ "Prayer", /* 06 */ "Magic",
        /* 07 */ "Cooking", /* 08 */ "Woodcutting", /* 09 */ "Fletching", /* 10 */ "Fishing", /* 11 */ "Firemaking",
        /* 12 */ "Crafting", /* 13 */ "Smithing", /* 14 */ "Mining", /* 15 */ "Herblore", /* 16 */ "Agility",
        /* 17 */ "Thieving", /* 18 */ "Slayer", /* 19 */ "Farming", /* 20 */ "Runecrafting",
        /* 21 */ "Hunter", /* 22 */ "Construction", };

    public static final String[] SKILL_INDEFINITES = {
        "an", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "an", "a", "a", "a", "a", "a", "a"
    };

    public static String getName(int skillId) {
        return SKILL_NAMES[skillId];
    }

    /**
     * Checks if a skill can be manually set to a level by a player.
     * @return        true if the player can set their level in this skill, false otherwise.
     */
    public boolean canSetLevel() {
        return ALLOWED_TO_SET_LEVELS.contains(this);
    }

    /**
     * Gets a skill for its button id.
     * @param button        The button id.
     * @return                The skill with the matching button.
     */
    public static Skill fromButton(int button) {
        return BUTTON_TO_SKILL.get(button);
    }

    public static Skill fromId(int id) {
        return ID_TO_SKILL.get(id);
    }

    public boolean isCombatSkill() {
        return id <= 6;
    }

    Skill(int id, int button, Dialogue dialogue) {
        this.id = id;
        this.button = button;
        this.dialogue = dialogue;
    }

    /** The identification for this skill in the skills array. */
    private final int id;

    /**
     * Gets the identification for this skill in the skills array.
     *
     * @return the identification for this skill.
     */
    public final int getId() {
        return id;
    }

    /**
     * The {@link Skill}'s button in the skills tab
     * interface.
     */
    private final int button;

    /**
     * Gets the {@link Skill}'s button id.
     * @return        The button for this skill.
     */
    public int getButton() {
        return button;
    }

    private final Dialogue dialogue;

    public Dialogue getDialogue() {
        return dialogue;
    }

    /**
     * Gets the {@link Skill}'s name.
     * @return    The {@link Skill}'s name in a suitable format.
     */
    public String getName() {
        return Utils.formatText(toString().toLowerCase());
    }

}

package com.valinor.game.content.skill.impl.slayer.slayer_reward_interface;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.CustomItemIdentifiers;

import java.util.HashMap;
import java.util.Map;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | December, 21, 2020, 13:19
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public enum SlayerUnlockable {

    BONUS_SLAYER_POINTS(63406, new Item(SLAYER_TOME), 750, "Bonus slayer points", "Automatically adds +15 bonus points when" + "<br>completing any slayer task. <col=ca0d0d>(750 points)</col>"),

    MONEY_FROM_KILLING_BOSSES(63407, new Item(COINS_995, 5000), 100, "Coins from killing bosses", "Generate coins whilst killing bosses.<col=ca0d0d>(100 <br><col=ca0d0d>points)"),

    DOUBLE_DROP_CHANCE(63408, new Item(DOUBLE_DROPS_LAMP), 400, "Double drops", "Have a chance at rolling double drops when" + "<br>killing bosses. 1/100 chance <col=ca0d0d>400 points)"),

    KILL_BLOW(63409, new Item(ARMADYL_GODSWORD), 750, "Kill blow", "Have a chance at dealing a finishing blow" + "<br>on any monster.<col=ca0d0d>(750 points)"),

    BIGGER_AND_BADDER(63410, new Item(1451), 75, "Bigger and Badder", "Increase the risk against certain slayer" + "<br>monsters with the chance of a superior " + "<br>version spawning whilst on a slayer task.<br><col=ca0d0d>(75 points)"),

    REVENANT_TELEPORT(63411, new Item(REVENANT_CAVE_TELEPORT), 300, "Revenants cave teleport", "Learn to teleport directly to the revenants." + "<br><col=ca0d0d>(300 points)"),

    LARRANS_LUCK(63412, new Item(LARRANS_KEY, 10), 250, "Larrans luck", "Extra chance of receiving Larrans keys" + "<br>during your slayer tasks. +15%." + "<br><col=ca0d0d>(250 points)"),

    NO_SLAYER_REQ(63413, new Item(SLAYERS_RESPITE), 400, "No slayer requirement", "You can kill any monster without the slayer<br>level requirement. <col=ca0d0d>(400 points)"),

    MORE_COINS_THIEVING(63414, new Item(COINS_995, 5000), 100, "Thieving boost", "Gives 10% more coins whilst<br>thieving. <col=ca0d0d>(100 points)"),

    DROP_RATE_BOOST(63415, new Item(SPELL_SCROLL), 1250, "Drop rate boost", "Boosts your drop rate by 3%.<br><col=ca0d0d>(1250 points)"),

    SUPPLY_DROP(63416, new Item(SUPPLY_CRATE), 500, "Supply drop", "Drops supplies during slayer tasks.<br><col=ca0d0d>(500 points)"),

    DOUBLE_DROP_LAMPS(63417, new Item(DOUBLE_DROPS_LAMP), 150, "Double drops lamp", "Ability to receive double drops lamps<br>whilst thieving. <col=ca0d0d>(150 points)"),

    LIKE_A_BOSS(63418, new Item(3064), 100, "Like a boss", "Slayer master will be able to assign<br>World boss monsters as your task. <col=ca0d0d>(100 <br><col=ca0d0d>points)"),

    KEY_OF_DROPS(63419, new Item(CustomItemIdentifiers.KEY_OF_DROPS), 250, "Key of drops", "Random chance of receiving the key of<br>drops. During your slayer task" + "<br>Drop rate = 1/1000 <col=ca0d0d> (250 points)"),

    NOTED_DRAGON_BONES(63420, new Item(LAVA_DRAGON_BONES + 1), 250, "Noted dragon bones", "Dragons drop dragon bones in banknote" + "<br>when killing dragons on task." + "<br><col=ca0d0d>(250 points)"),

    EXP_BOOSTER(63421, new Item(TOME_OF_EXPERIENCE), 650, "Experience booster", "Boosts your exp based on your game mode.<br>10% for rookie, 5% for gladiator, 3%<br>for challenger<col=ca0d0d>(650 points)"),

    WORLD_BOSS_TELEPORT(63422, new Item(SKOTOS), 250, "World Boss Teleport", "Ability to teleport directly to the world<br>boss. <col=ca0d0d>(250 points)"),

    RUNAWAY(63423, new Item(BOOTS_OF_LIGHTNESS), 400, "Runaway", "Infinite run energy outside of the<br>wilderness. <col=ca0d0d>(400 points)"),

    TREASURE_HUNT(63424, new Item(CASKET), 300, "Treasure hunt", "Learn how to find treasures whilst pvming.<br><col=ca0d0d>(300 points)"),

    TELEPORT_TO_TASK(63425, new Item(TELEPORT_CARD), 100, "Task teleportation", "Unlock the ability to teleport straight to tasks. <col=ca0d0d>(100 <br><col=ca0d0d>points)"),

    BIGGEST_AND_BADDEST(63426, new Item(SUPERIOR_DRAGON_BONES), 500, "Biggest and baddest", "Better chance to get a superior boss," + "<br>while on slayer task <col=ca0d0d>(500 points)"),

    ELECTION_DAY(63427, new Item(VOTE_TICKET), 750, "Election day", "10% Chance for double vote points when" + "<br>voting <col=ca0d0d>(750 points)"),

    MALEVOLENT_MASQUERADE(63428, new Item(SLAYER_HELMET), 400, "Malevolent masquerade", "Learn to combine the protective Slayer<br>headgear and Slayer gem into one<br>universal helmet, with level 55 Crafting.<br><col=ca0d0d>(400 points)"),

    WEAK_SPOT(63429, new Item(BANDOS_GODSWORD), 650, "Weak spot", "10% Increased damage to the task monster." + "<br><col=ca0d0d>(650 points)"),

    DIGGING_FOR_TREASURE(63430, new Item(TREASURE_CASKET), 200, "Digging for treasure", "One extra loot when" + "<br>opening master caskets.<br><col=ca0d0d>(200 points)");

    private final int buttonId;
    private final Item item;
    private final int rewardPoints;
    private final String name;
    private final String description;

    SlayerUnlockable(int buttonId, Item item, int rewardPoints, String name, String description) {
        this.buttonId = buttonId;
        this.item = item;
        this.rewardPoints = rewardPoints;
        this.name = name;
        this.description = description;
    }

    public int getButtonId() {
        return buttonId;
    }

    public Item getItem() {
        return item;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * A map of unlockable buttons.
     */
    private static final Map<Integer, SlayerUnlockable> unlockable = new HashMap<>();

    public static SlayerUnlockable byButton(int id) {
        return unlockable.get(id);
    }

    static {
        for (SlayerUnlockable unlockButtons : values())
            unlockable.put(unlockButtons.getButtonId(), unlockButtons);
    }

    public static void updateInterface(Player player) {
        for (SlayerUnlockable slayerUnlockable : SlayerUnlockable.values()) {
            player.getPacketSender().sendItemOnInterface(63431 + slayerUnlockable.ordinal(), slayerUnlockable.getItem());
            player.getPacketSender().sendString(63456 + slayerUnlockable.ordinal(), slayerUnlockable.getName());
            player.getPacketSender().sendString(63481 + slayerUnlockable.ordinal(), slayerUnlockable.getDescription());
        }
    }
}

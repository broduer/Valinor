package com.valinor.game.content.achievements;

import com.valinor.game.GameConstants;
import com.valinor.game.world.items.Item;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | April, 14, 2021, 16:26
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public enum Achievements {

    /*
     * ROMAN NUMBERS:
     * I
     * II
     * III
     * IV
     * V
     * VI
     * VII
     * VIII
     * IX
     * X
     */
    //Misc
    COMPLETIONIST("Completionist", "Complete all Achievements besides this one.", 1, Difficulty.HARD, new Item(TEN_DOLLAR_BOND), new Item(COINS_995, 100_000_000), new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX,5)),
    VOTE_FOR_US_I("Vote I", "Claim at least 100 vote points.<br>Type ::vote to support "+GameConstants.SERVER_NAME,100, Difficulty.EASY, new Item(VOTE_TICKET, 25), new Item(FIVE_DOLLAR_BOND)),
    VOTE_FOR_US_II("Vote II", "Claim at least 250 vote points.<br>Type ::vote to support "+GameConstants.SERVER_NAME,250, Difficulty.MED, new Item(VOTE_TICKET, 50), new Item(FIVE_DOLLAR_BOND)),
    VOTE_FOR_US_III("Vote III", "Claim at least 500 vote points.<br>Type ::vote to support "+GameConstants.SERVER_NAME,500, Difficulty.HARD, new Item(VOTE_TICKET, 75), new Item(TEN_DOLLAR_BOND)),
    WHATS_IN_THE_BOX_I("Mystery box I", "Open the mystery box 10 times.", 10, Difficulty.EASY, AchievementUtility.DEFAULT_REWARD, new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX)),
    WHATS_IN_THE_BOX_II("Mystery box II", "Open the mystery box 50 times.", 50, Difficulty.MED, new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX,2)),
    WHATS_IN_THE_BOX_III("Mystery box III", "Open the mystery box 100 times.", 100, Difficulty.HARD, new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX,5)),
    CRYSTAL_LOOTER_I("Crystal looter I", "Open the crystal chest 10 times.", 10, Difficulty.EASY, new Item(CRYSTAL_KEY,2)),
    CRYSTAL_LOOTER_II("Crystal looter II", "Open the crystal chest 50 times.", 50, Difficulty.MED, new Item(CRYSTAL_KEY,5)),
    CRYSTAL_LOOTER_III("Crystal looter III", "Open the crystal chest 100 times.", 100, Difficulty.HARD, new Item(CRYSTAL_KEY,10)),
    LARRANS_LOOTER_I("Larran's looter I", "Open the Larran's chest 10 times.", 10, Difficulty.EASY, new Item(LARRANS_KEY,2)),
    LARRANS_LOOTER_II("Larran's looter II", "Open the Larran's chest 50 times.", 50, Difficulty.MED, new Item(LARRANS_KEY,5)),
    LARRANS_LOOTER_III("Larran's looter III", "Open the Larran's chest 100 times.", 100, Difficulty.HARD, new Item(LARRANS_KEY,10)),

    //Pvm
    YAK_HUNTER("Yak hunter", "Kill 50 Yaks.", 50, Difficulty.EASY, new Item(DIVINE_SUPER_COMBAT_POTION4+1,25)),
    ROCK_CRAB_HUNTER("Rock crab hunter", "Kill 50 Rock crabs.", 50, Difficulty.EASY, new Item(DIVINE_SUPER_COMBAT_POTION4+1,25)),
    SAND_CRAB_HUNTER("Sand crab hunter", "Kill 50 Sand crabs.", 50, Difficulty.EASY, new Item(DIVINE_SUPER_COMBAT_POTION4+1,25)),
    EXPERIMENTS_HUNTER("Experiments hunter", "Kill 50 Experiments.", 50, Difficulty.EASY, new Item(DIVINE_SUPER_COMBAT_POTION4+1,25)),
    DRAGON_SLAYER_I("Dragon slayer I", "Kill 250 dragons.", 250, Difficulty.EASY, new Item(DRAGON_BONES+1,100)),
    DRAGON_SLAYER_II("Dragon slayer II", "Kill 50 black dragons.", 50, Difficulty.MED, new Item(KBD_HEADS)),
    DRAGON_SLAYER_III("Dragon slayer III", "Kill 100 King black dragons.", 100, Difficulty.HARD, new Item(ANCIENT_WYVERN_SHIELD)),
    DRAGON_SLAYER_IV("Dragon slayer IV", "Kill 100 adamant or rune dragons.", 100, Difficulty.HARD, new Item(DRAGONFIRE_WARD)),
    FLUFFY_I("Fluffy I", "Kill Cerberus 15 times.", 15, Difficulty.EASY, new Item(COINS_995,5_000_000)),
    FLUFFY_II("Fluffy II", "Kill Cerberus 50 times.", 50, Difficulty.MED, new Item(COINS_995,10_000_000)),
    BUG_EXTERMINATOR_I("Bug exterminator I", "Kill the Kalphite Queen 25 times.", 25, Difficulty.EASY, new Item(BLOOD_MONEY,15_000)),
    BUG_EXTERMINATOR_II("Bug exterminator II", "Kill the Kalphite Queen 100 times.", 100, Difficulty.MED, new Item(WEAPON_MYSTERY_BOX)),
    ULTIMATE_CHAOS_I("Ultimate chaos I", "Kill 20 Chaos elementals.", 20, Difficulty.EASY, new Item(COINS_995, 10_000_000)),
    ULTIMATE_CHAOS_II("Ultimate chaos II", "Kill 100 Chaos elementals.", 100, Difficulty.MED, new Item(COINS_995, 30_000_000)),
    ULTIMATE_CHAOS_III("Ultimate chaos III", "Kill 500 Chaos elementals.", 500, Difficulty.HARD, new Item(LEGENDARY_MYSTERY_BOX)),
    HOLEY_MOLEY_I("Holey moley I", "Kill the Giant mole 10 times.", 10, Difficulty.MED, new Item(BLOOD_MONEY, 5_000)),
    HOLEY_MOLEY_II("Holey moley II", "Kill the Giant mole 50 times.", 50, Difficulty.MED, new Item(BLOOD_MONEY, 25_000)),
    HOLEY_MOLEY_III("Holey moley III", "Kill the Giant mole 100 times.", 100, Difficulty.MED, new Item(BLOOD_MONEY, 50_000)),
    LORD_OF_THE_RINGS_I("Lord of the rings I", "Kill 100 dagannoth kings.", 100, Difficulty.MED, new Item(ARCHERS_RING_I), new Item(BERSERKER_RING_I), new Item(SEERS_RING_I), new Item(WARRIOR_RING_I)),
    LORD_OF_THE_RINGS_II("Lord of the rings II", "Kill 250 dagannoth kings.", 250, Difficulty.MED, new Item(RING_OF_SUFFERING_I)),
    SQUIDWARD_I("Squidward I", "Kill 25 Krakens.", 25, Difficulty.EASY, new Item(COINS_995, 5_000_000)),
    SQUIDWARD_II("Squidward II", "Kill 100 Krakens", 100, Difficulty.MED, new Item(COINS_995, 15_000_000)),
    SQUIDWARD_III("Squidward III", "Kill 250 Krakens", 250, Difficulty.HARD, new Item(TRIDENT_OF_THE_SEAS)),
    DR_CURT_CONNORS_I("Dr. Curt Connors I", "Kill 10 Lizardman shaman.", 10, Difficulty.EASY, new Item(COINS_995, 5_000_000)),
    DR_CURT_CONNORS_II("Dr. Curt Connors II", "Kill 100 Lizardman shaman.", 100, Difficulty.MED, new Item(COINS_995, 25_000_000)),
    DR_CURT_CONNORS_III("Dr. Curt Connors III", "Kill 300 Lizardman shaman.", 300, Difficulty.HARD, new Item(COINS_995, 100_000_000)),
    TSJERNOBYL_I("Tsjernobyl I", "Kill 25 Thermonuclear smoke devil.", 25, Difficulty.EASY, new Item(COINS_995,5_000_000)),
    TSJERNOBYL_II("Tsjernobyl II", "Kill 150 Thermonuclear smoke devil.", 150, Difficulty.MED, new Item(COINS_995, 35_000_000)),
    TSJERNOBYL_III("Tsjernobyl III", "Kill 500 Thermonuclear smoke devil.", 500, Difficulty.HARD, new Item(SMOKE_BATTLESTAFF)),
    VETION_I("Vet'ion I", "Kill 25 Vet'ions.", 25, Difficulty.EASY, new Item(COINS_995, 10_000_000)),
    VETION_II("Vet'ion II", "Kill 75 Vet'ions.", 75, Difficulty.MED, new Item(COINS_995, 35_000_000)),
    VETION_III("Vet'ion I", "Kill 150 Vet'ions.", 150, Difficulty.HARD, new Item(RING_OF_THE_GODS_I)),
    BABY_ARAGOG_I("Baby Aragog I", "Kill Venenatis 25 times.", 25, Difficulty.EASY, new Item(TREASONOUS_RING_I)),
    BABY_ARAGOG_II("Baby Aragog II", "Kill Venenatis 100 times.", 100, Difficulty.MED, new Item(DONATOR_MYSTERY_BOX), new Item(BLOOD_MONEY, 25_000)),
    BABY_ARAGOG_III("Baby Aragog III", "Kill Venenatis 350 times.", 350, Difficulty.HARD, new Item(LEGENDARY_MYSTERY_BOX)),
    BARK_SCORPION_I("Bark scorpion I", "Kill scorpia 25 times.", 25, Difficulty.EASY, new Item(COINS_995,10_000_000)),
    BARK_SCORPION_II("Bark scorpion II", "Kill scorpia 75 times.", 75, Difficulty.MED, new Item(COINS_995,25_000_000)),
    BARK_SCORPION_III("Bark scorpion III", "Kill scorpia 150 times.", 150, Difficulty.HARD, new Item(COINS_995,50_000_000)),
    BEAR_GRYLLS_I("Bear Grylls I", "Kill 25 Callisto.", 25, Difficulty.EASY, new Item(TYRANNICAL_RING_I)),
    BEAR_GRYLLS_II("Bear Grylls II", "Kill 50 Callisto.", 50, Difficulty.MED, new Item(WEAPON_MYSTERY_BOX), new Item(BLOOD_MONEY, 12_500)),
    BEAR_GRYLLS_III("Bear Grylls III", "Kill 100 Callisto.", 100, Difficulty.HARD, new Item(DONATOR_MYSTERY_BOX)),
    SNAKE_CHARMER_I("Snake charmer I", "Kill 10 Zulrah.", 10, Difficulty.EASY, new Item(BLOOD_MONEY, 15_000)),
    SNAKE_CHARMER_II("Snake charmer II", "Kill 50 Zulrah.", 50, Difficulty.MED, new Item(SERPENTINE_HELM)),
    SNAKE_CHARMER_III("Snake charmer III", "Kill 250 Zulrah.", 250, Difficulty.HARD, new Item(TOXIC_BLOWPIPE)),
    VORKY_I("Vorky I", "Kill 50 Vorkaths.", 50, Difficulty.HARD, new Item(VORKATHS_HEAD_21907)),
    VORKY_II("Vorky II", "Kill 100 Vorkaths.", 100, Difficulty.HARD, new Item(SUPERIOR_DRAGON_BONES+1, 100), new Item(DONATOR_MYSTERY_BOX,3)),
    VORKY_III("Vorky III", "Kill 250 Vorkaths.", 250, Difficulty.HARD, new Item(KEY_OF_DROPS)),
    REVENANT_HUNTER_I("Revenant hunter I", "Kill 250 revenants.", 250, Difficulty.EASY, new Item(ANCIENT_EMBLEM,2), new Item(ItemIdentifiers.ANCIENT_STATUETTE,2)),
    REVENANT_HUNTER_II("Revenant hunter II", "Kill 500 revenants.", 500, Difficulty.MED, new Item(AMULET_OF_AVARICE)),
    REVENANT_HUNTER_III("Revenant hunter III", "Kill 2500 revenants.", 2500, Difficulty.HARD, new Item(ItemIdentifiers.ANCIENT_STATUETTE,2)),
    REVENANT_HUNTER_IV("Revenant hunter IV", "Kill 10000 revenants.", 10000, Difficulty.HARD, new Item(CRAWS_BOW), new Item(VIGGORAS_CHAINMACE), new Item(THAMMARONS_SCEPTRE)),
    GODWAR("Godwar", "Kill 500 Godwars Dungeon Bosses", 500, Difficulty.HARD, new Item(ARMADYL_GODSWORD + 1,2), new Item(BANDOS_GODSWORD + 1,2), new Item(SARADOMIN_GODSWORD + 1,2), new Item(ZAMORAK_GODSWORD + 1,2)),
    CORPOREAL_CRITTER("Corporeal Critter", "Kill 100 Corporeal beasts.", 100, Difficulty.HARD, new Item(SPECTRAL_SPIRIT_SHIELD)),
    MAGE_ARENA_I("Mage arena I", "Kill 100 battle mages at the mage arena.", 100, Difficulty.EASY, new Item(ItemIdentifiers.STAFF_OF_LIGHT)),
    MAGE_ARENA_II("Mage arena II", "Kill 250 battle mages at the mage arena.", 250, Difficulty.MED, new Item(ItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD)),
    MAGE_ARENA_III("Mage arena III", "Kill 500 battle mages at the mage arena.", 500, Difficulty.HARD, new Item(ItemIdentifiers.ZURIELS_STAFF)),
    MAGE_ARENA_IV("Mage arena IV", "Kill 1.000 battle mages at the mage arena.", 1000, Difficulty.HARD, new Item(ItemIdentifiers.ZURIELS_HOOD), new Item(ItemIdentifiers.ZURIELS_ROBE_TOP), new Item(ItemIdentifiers.ZURIELS_ROBE_BOTTOM)),

    SKILLER_I("Skiller I", "Earn a total level of 750 on a trained account.", 1, Difficulty.EASY, new Item(ItemIdentifiers.ANTIQUE_LAMP, 1), new Item(ItemIdentifiers.MYSTERY_BOX, 1)),
    SKILLER_II("Skiller II", "Earn a total level of 1000 on a trained account.", 1, Difficulty.MED, new Item(ItemIdentifiers.ANTIQUE_LAMP, 1), new Item(ARMOUR_MYSTERY_BOX, 2)),
    SKILLER_III("Skiller III", "Earn a total level of 1500 on a trained account.", 1, Difficulty.HARD, new Item(ItemIdentifiers.ANTIQUE_LAMP, 2), new Item(WEAPON_MYSTERY_BOX, 2)),
    SKILLER_IV("Skiller IV", "Earn level 99 in all skills on a trained account<br>with the exception of construction.", 1, Difficulty.HARD, new Item(DONATOR_MYSTERY_BOX, 5)),

    TASK_MASTER_I("Task master I", "Complete 10 tasks.", 10, Difficulty.EASY, new Item(ItemIdentifiers.TWISTED_ANCESTRAL_COLOUR_KIT)),
    TASK_MASTER_II("Task master II", "Complete 25 tasks.", 25, Difficulty.MED, new Item(CustomItemIdentifiers.DONATOR_MYSTERY_BOX)),
    TASK_MASTER_III("Task master III", "Complete 50 tasks.", 50, Difficulty.HARD, new Item(ItemIdentifiers.VESTAS_LONGSWORD)),

    //Minigames
    BARROWS_I("Barrows I", "Complete 10 barrows runs.", 10, Difficulty.EASY, new Item(TORAGS_ARMOUR_SET)),
    BARROWS_II("Barrows II", "Complete 30 barrows runs.", 30, Difficulty.MED, new Item(ItemIdentifiers.DHAROKS_ARMOUR_SET, 1)),
    BARROWS_III("Barrows III", "Complete 50 barrows runs.", 50, Difficulty.HARD, new Item(KARILS_ARMOUR_SET, 1)),
    BARROWS_IV("Barrows IV", "Complete 75 barrows runs.", 75, Difficulty.HARD, new Item(AHRIMS_ARMOUR_SET, 1)),
    BARROWS_V("Barrows V", "Complete 100 barrows runs.", 100, Difficulty.HARD, new Item(GUTHANS_ARMOUR_SET, 1)),
    FIGHT_CAVES_I("Fight caves I", "Defeat TzTok-Jad.", 1, Difficulty.EASY, new Item(COINS_995, 5_000_000)),
    FIGHT_CAVES_II("Fight caves II", "Defeat TzTok-Jad 50 times.", 50, Difficulty.MED, new Item(COINS_995, 50_000_000)),
    FIGHT_CAVES_III("Fight caves III", "Defeat TzTok-Jad 150 times.", 150, Difficulty.HARD, new Item(COINS_995,150_000_000)),

    //Skilling


    /* Thieving */
    THIEF_I("Thief I", "Steal 150 times from the Crafting stall.", 150, Difficulty.EASY, new Item(COINS_995, 10_000_000)),
    THIEF_II("Thief II", "Steal 350 times from the General stall.", 350, Difficulty.EASY, new Item(COINS_995, 15_000_000)),
    THIEF_III("Thief III", "Steal 500 times from the Magic stall.", 500, Difficulty.MED, new Item(COINS_995, 50_000_000)),
    THIEF_IV("Thief IV", "Steal 750 times from the Scimitar stall.", 750, Difficulty.MED, new Item(COINS_995,  75_000_000)),
    MASTER_THIEF("Master thief", "Steal times supplies from the any stall.", 5000, Difficulty.HARD, new Item(COINS_995, 100_000_000)),

    ;

    public static List<Achievements> asList(Difficulty difficulty) {
        return Arrays.stream(values()).filter(a -> a.difficulty == difficulty).sorted((a, b) -> a.name().compareTo(b.name())).collect(Collectors.toList());
    }

    private final String name;
    private final String description;
    private final int completeAmount;
    private final String rewardString;
    private final Difficulty difficulty;
    private final Item[] reward;

    Achievements(String name, String description, int completeAmount, Difficulty difficulty, Item... reward) {
        this.name = name;
        this.description = description;
        this.completeAmount = completeAmount;
        this.rewardString = "";
        this.difficulty = difficulty;
        this.reward = reward;
    }

    Achievements(String name, String description, int completeAmount, String rewardString, Difficulty difficulty, Item... reward) {
        this.name = name;
        this.description = description;
        this.completeAmount = completeAmount;
        this.rewardString = rewardString;
        this.difficulty = difficulty;
        this.reward = reward;
    }

    public int getCompleteAmount() {
        return completeAmount;
    }

    public String getDescription() {
        return description;
    }

    public Item[] getReward() {
        return reward;
    }

    public String getName() {
        return name;
    }

    public String otherRewardString() {
        return rewardString;
    }

    public static int getTotal() {
        return values().length;
    }
}

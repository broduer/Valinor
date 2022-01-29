package com.valinor.game.world.items;

import com.google.gson.annotations.Expose;
import com.valinor.fs.DefinitionRepository;
import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.areas.wilderness.content.revenant_caves.AncientArtifacts;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.util.ItemIdentifiers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import static com.valinor.game.content.mechanics.break_items.BreakItemsOnDeath.*;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.HOLY_SANGUINESTI_STAFF;

/**
 * Represents an item.
 *
 * @author Professor Oak
 */

public class Item implements Cloneable {

    private static final Logger logger = LogManager.getLogger(Item.class);

    public static final Comparator<Item> ITEM_VALUE_COMPARATOR = Comparator.comparingInt(Item::getValue);

    /**
     * Returns an {@code Item} instance of the type {@code id}, and a stack size of {@code amount}.
     *
     * @throws IllegalArgumentException if {@code id} is invalid, or {@code amount} is {@code < 1}.
     */
    public static Item of(int id, int amount) {
        ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, id);
        if (def == null) {
            throw new IllegalArgumentException("undefined item: " + id);
        }
        if (amount < 1) {
            throw new IllegalArgumentException("amount zero or negative: " + amount);
        }
        return new Item(id, amount);
    }

    /**
     * Returns an {@code Item} instance of the type {@code id}, and a stack size of {@code 1}.
     *
     * @throws IllegalArgumentException if {@code id} is invalid.
     */
    public static Item of(int id) {
        ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, id);
        if (def == null) {
            throw new IllegalArgumentException("undefined item: " + id);
        }
        return new Item(id, 1);
    }

    /**
     * The item id.
     */
    @Expose
    private int id;

    /**
     * Amount of the item.
     */
    @Expose
    private int amount;

    public static void onServerStart() {
        Mob.accumulateRuntimeTo(() -> {
            checkDefs();
        }, t -> {
            System.out.println("itemdef special fields took " + t.toMillis() + " ms");
        });

    }

    private static void checkDefs() {
        for (int i : AUTO_KEPT_LIST) {
            ItemDefinition definition = World.getWorld().definitions().get(ItemDefinition.class, i);
            definition.autoKeptOnDeath = true;
        }
        for (AncientArtifacts value : AncientArtifacts.values()) {
            ItemDefinition definition = World.getWorld().definitions().get(ItemDefinition.class, value.getItemId());
            definition.changes = true;
        }
        World.getWorld().definitions().get(ItemDefinition.class, SARADOMINS_BLESSED_SWORD).changes = true;
        World.getWorld().definitions().get(ItemDefinition.class, SARAS_BLESSED_SWORD_FULL).changes = true;
        for (int i = 0; i < World.getWorld().definitions().total(ItemDefinition.class); i++) {
            if (Item.isCrystal(i)) {
                World.getWorld().definitions().get(ItemDefinition.class, SARAS_BLESSED_SWORD_FULL).isCrystal = true;
            }
        }
        int[] tradeable_special_items = {
            SALVE_AMULET, SALVE_AMULETI, VENGEANCE_SKULL, FEROCIOUS_GLOVES, RING_OF_MANHUNTING, AVERNIC_DEFENDER, INFERNAL_CAPE, TWISTED_SLAYER_HELMET_I,
            RING_OF_TRINITY, RING_OF_PRECISION, RING_OF_SORCERY,
            DRAGON_DAGGERP_24949, ELITE_VOID_TOP_24943, ELITE_VOID_ROBE_24942, VOID_MELEE_HELM_24941, VOID_MAGE_HELM_24940, VOID_RANGER_HELM_24939,
            VOID_KNIGHT_GLOVES_24938, GRANITE_MAUL_24944, PARTYHAT__SPECS_24945, ABYSSAL_TENTACLE_24948, FREMENNIK_KILT_24946, SPIKED_MANACLES_24947, FAWKES_32937,
        };
        for (int i : tradeable_special_items) {
            World.getWorld().definitions().get(ItemDefinition.class, i).tradeable_special_items = true;
        }
    }

    /**
     * Gets the item's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the amount of the item.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * An Item object constructor.
     *
     * @param id     Item id.
     * @param amount Item amount.
     */
    public Item(int id, int amount) {
        this.id = id;
        this.amount = Math.max(amount, 0);
    }

    /**
     * An Item object constructor.
     *
     * @param item   the Item.
     * @param amount Item amount.
     */
    public Item(Item item, int amount) {
        id = item.id;
        this.amount = Math.max(amount, 0);
    }

    /**
     * An Item object constructor.
     *
     * @param id Item id.
     */
    public Item(int id) {
        this(id, 1);
    }

    public Item(Item item) {
        id = ((short) item.getId());
        amount = item.getAmount();
    }

    /**
     * Sets the identification of this item.
     *
     * @param id the new identification of this item.
     */
    public final void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the quantity of this item.
     *
     * @param amount the new quantity of this item.
     */
    public final void setAmount(int amount) {
        if (amount < 0)
            amount = 0;
        this.amount = amount;
    }

    /**
     * Checks if this item is valid or not.
     *
     * @return
     */
    public boolean isValid() {
        return id > 0 && amount >= 1;
    }

    /**
     * Determines if {@code item} is valid. In other words, determines if
     * {@code item} is not {@code null} and the {@link Item#id}.
     *
     * @param item the item to determine if valid.
     * @return {@code true} if the item is valid, {@code false} otherwise.
     */
    public static boolean valid(Item item) {
        return item != null && item.id > 0;
    }

    /**
     * Copying the item by making a new item with same values.
     */
    public Item copy() {
        Item item = new Item(id, amount);
        return item;
    }

    /**
     * Increment the amount by the specified amount.
     */
    public void incrementAmountBy(int amount) {
        if ((this.amount + amount) > Integer.MAX_VALUE) {
            this.amount = Integer.MAX_VALUE;
        } else {
            this.amount += amount;
        }
    }

    /**
     * Decrement the amount by the specified amount.
     */
    public void decrementAmountBy(int amount) {
        if ((this.amount - amount) < 1) {
            this.amount = 0;
        } else {
            this.amount -= amount;
        }
    }

    /**
     * Creates a new item with {@code newId} and the same amount as this instance.
     * The returned {@code Item} <strong>does not</strong> hold any references to
     * this one unless {@code id == newId}. It will throw an exception on an invalid
     * id.
     *
     * @param newId The new id to set.
     * @return The newly id set {@code Item}.
     */
    public Item createWithId(int newId) {
        if (id == newId) {
            return this;
        }
        return new Item(newId, amount);
    }

    /**
     * Creates a new item with {@code newAmount} and the same identifier as this
     * instance. The returned {@code Item} <strong>does not</strong> hold any
     * references to this one unless {@code amount == newAmount}. It will throw an
     * exception on overflows and negative values.
     *
     * @param newAmount The new amount to set.
     * @return The newly amount set {@code Item}.
     */
    public Item createWithAmount(int newAmount) {
        if (amount == newAmount) {
            return this;
        }
        return new Item(id, newAmount);
    }

    /**
     * Creates a new item with {@code amount + addAmount} and the same identifier.
     * The returned {@code Item} <strong>does not</strong> hold any references to
     * this one. It will also have a maximum amount of {@code
     * Integer.MAX_VALUE}.
     *
     * @param addAmount The amount to deposit.
     * @return The newly incremented {@code Item}.
     */
    public Item createAndIncrement(int addAmount) {
        if (addAmount < 0) { // Same effect as decrementing.
            return createAndDecrement(Math.abs(addAmount));
        }

        int newAmount = amount + addAmount;

        if (newAmount < amount) { // An overflow.
            newAmount = Integer.MAX_VALUE;
        }

        Item item = clone();
        item.setAmount(newAmount);
        return item;
    }

    /**
     * Creates a new item with {@code amount - removeAmount} and the same
     * identifier. The returned {@code Item} <strong>does not</strong> hold any
     * references to this one. It will also have a minimum amount of {@code 1}.
     *
     * @param removeAmount The amount to withdraw.
     * @return The newly incremented {@code Item}.
     */
    public Item createAndDecrement(int removeAmount) {
        if (removeAmount < 0) { // Same effect as incrementing.
            return createAndIncrement(-removeAmount);
        }

        int newAmount = amount - removeAmount;

        // Value too low, or an overflow.
        if (newAmount < 1 || newAmount > amount) {
            newAmount = 1;
        }

        Item clone = clone();
        clone.setAmount(newAmount);
        return clone;
    }

    /**
     * Increments the amount by {@code 1}.
     */
    public final void incrementAmount() {
        incrementAmountBy(1);
    }

    public boolean isTwoHanded() {
        EquipmentInfo info = World.getWorld().equipmentInfo();
        return info.typeFor(this.getId()) == 5;// If type is 5 it is a two-handed weapon
    }

    /**
     * Resolve this item's definition in the world's repository for definitions. No definition returns <code>null</code>.
     *
     * @param world The world to use to resolve the definition for this item.
     * @return The item's definitions, or <code>null</code> if that didn't work out.
     */
    public ItemDefinition definition(World world) {
        return world.definitions().get(ItemDefinition.class, id);
    }

    public ItemDefinition definition(DefinitionRepository repo) {
        return repo.get(ItemDefinition.class, id);
    }

    public Item unnote() {
        return unnote(World.getWorld().definitions());
    }

    public Item unnote(DefinitionRepository repo) {
        ItemDefinition def = definition(repo);
        if (def != null && def.noteModel > 0) {
            return new Item(def.notelink, amount); // Properties check not required: properties do not stick to notes.
        }

        return this;
    }

    public Item note() {
        ItemDefinition def = definition(World.getWorld());
        if (def == null || def.noteModel > 0 || def.notelink < 1) {
            return this;
        }

        return new Item(def.notelink, amount);
    }

    public boolean noted() {
        return noted(World.getWorld().definitions());
    }

    public boolean noted(DefinitionRepository repo) {
        return unnote(repo) != this;
    }

    public boolean noteable() {
        return id != note().getId();
    }

    public boolean stackable() {
        ItemDefinition def = definition(World.getWorld());
        //Item not in OSRS cache could be custom, lets flag as false
        if (def == null) {
            return false;
        }
        return def.stackable();
    }

    public boolean rawtradable() {
        ItemDefinition def = definition(World.getWorld());
        return (def == null || id == ItemIdentifiers.PLATINUM_TOKEN || id == COINS_995 || def.grandexchange || def.noteModel > 0 || def.notelink > 0);
    }

    public String name() {
        ItemDefinition def = definition(World.getWorld());
        if (def == null) {
            return "Unknown name";
        }
        return def.name;
    }

    // These untradable items will be send to the inventory or bank on death.
    public static final int[] AUTO_KEPT_LIST = new int[]{
        COAL_BAG, IMCANDO_HAMMER, GOLDSMITH_GAUNTLETS, COOKING_GAUNTLETS, MAGIC_SECATEURS, EXPLORERS_RING_4, RADAS_BLESSING_4, RING_OF_CHAROSA, ARDOUGNE_CLOAK_4,
        BONECRUSHER, FIGHTER_HAT, SKILLING_SCROLL, TASK_BOTTLE_SKILLING, TASK_BOTTLE_PVMING, TASK_BOTTLE_CASKET, PVMING_SCROLL,
        MAX_CAPE, FIRE_MAX_CAPE, SARADOMIN_MAX_CAPE, ZAMORAK_MAX_CAPE, GUTHIX_MAX_CAPE, ACCUMULATOR_MAX_CAPE, MAX_CAPE_13342, ARDOUGNE_MAX_CAPE, INFERNAL_MAX_CAPE_21285, IMBUED_SARADOMIN_MAX_CAPE, IMBUED_ZAMORAK_MAX_CAPE, IMBUED_GUTHIX_MAX_CAPE, ASSEMBLER_MAX_CAPE, MYTHICAL_MAX_CAPE,
        SLAYER_HELMET, SLAYER_HELMET_I, BLACK_SLAYER_HELMET, BLACK_SLAYER_HELMET_I, GREEN_SLAYER_HELMET, GREEN_SLAYER_HELMET_I, RED_SLAYER_HELMET,
        RED_SLAYER_HELMET_I, PURPLE_SLAYER_HELMET, PURPLE_SLAYER_HELMET_I, TURQUOISE_SLAYER_HELMET, TURQUOISE_SLAYER_HELMET_I, HYDRA_SLAYER_HELMET,
        HYDRA_SLAYER_HELMET_I, TWISTED_SLAYER_HELMET, TWISTED_SLAYER_HELMET_I, SLAYER_HELMET_I_25177, BLACK_SLAYER_HELMET_I_25179, GREEN_SLAYER_HELMET_I_25181,
        RED_SLAYER_HELMET_I_25183, PURPLE_SLAYER_HELMET_I_25185, TURQUOISE_SLAYER_HELMET_I_25187, HYDRA_SLAYER_HELMET_I_25189, TWISTED_SLAYER_HELMET_I_25191,
        TZTOK_SLAYER_HELMET, TZTOK_SLAYER_HELMET_I, TZTOK_SLAYER_HELMET_I_25902, VAMPYRIC_SLAYER_HELMET, VAMPYRIC_SLAYER_HELMET_I, VAMPYRIC_SLAYER_HELMET_I_25908,
        TZKAL_SLAYER_HELMET, TZKAL_SLAYER_HELMET_I, TZKAL_SLAYER_HELMET_I_25914,
        SALVE_AMULET,
        SALVE_AMULETI,
        VENGEANCE_SKULL,
        INFERNAL_CAPE,
        RING_OF_WEALTH_I,
        AVAS_ASSEMBLER,
        AVAS_ACCUMULATOR,
        BRONZE_DEFENDER,
        IRON_DEFENDER,
        BLACK_DEFENDER,
        MITHRIL_DEFENDER,
        ADAMANT_DEFENDER,
        RUNE_DEFENDER,
        DRAGON_DEFENDER,
        AVERNIC_DEFENDER,
        IMBUED_SARADOMIN_CAPE,
        IMBUED_GUTHIX_CAPE,
        IMBUED_ZAMORAK_CAPE,
        VOID_MAGE_HELM,
        VOID_RANGER_HELM,
        VOID_MELEE_HELM,
        VOID_KNIGHT_TOP,
        VOID_KNIGHT_ROBE,
        VOID_KNIGHT_GLOVES,
        ELITE_VOID_TOP,
        ELITE_VOID_ROBE,
        FIRE_CAPE,
        FIGHTER_TORSO,
        ELDER_WAND_RAIDS,
        KILLERS_KNIFE_21059,
        BEGINNER_WEAPON_PACK,
        BEGINNER_DRAGON_CLAWS,
        BEGINNER_AGS,
        BEGINNER_CHAINMACE,
        BEGINNER_CRAWS_BOW,
        VETERAN_HWEEN_MASK,
        VETERAN_PARTYHAT,
        VETERAN_SANTA_HAT,
        BLOOD_FIREBIRD,
        RING_OF_VIGOUR,
        MAGMA_BLOWPIPE,
        SARADOMIN_CAPE,
        ZAMORAK_CAPE,
        GUTHIX_CAPE,
        MYTHICAL_CAPE_22114,
        ARDOUGNE_CLOAK_4,
        MITHRIL_GLOVES,
        ADAMANT_GLOVES,
        BARROWS_GLOVES,
        HEALER_HAT,
        RUNNER_HAT,
        RANGER_HAT,
        RUNNER_BOOTS,
        PENANCE_SKIRT,
        ARDOUGNE_CLOAK_4,
        FEROCIOUS_GLOVES,
        ROYAL_SEED_POD,
        AVAS_ASSEMBLER,
        VENGEANCE_SKULL,
        PET_KREE_ARRA_WHITE,
        PET_ZILYANA_WHITE,
        PET_GENERAL_GRAARDOR_BLACK,
        PET_KRIL_TSUTSAROTH_BLACK,
        RUNE_POUCH_I,
        DOUBLE_DROPS_LAMP,
        RING_OF_MANHUNTING,
        VALINOR_COINS,
        ATTACKER_ICON,
        COLLECTOR_ICON,
        DEFENDER_ICON,
        HEALER_ICON,
        MAGIC_SHORTBOW_I,
    };

    // Variants of crystal bow, shield, halberd, (i)
    // Note this does NOT INCLUDE 4212 ('new' bow) or 4224 ('new' shield)
    // Those are un-used crystal items, which drop as themselves.. they can be noted!
    // The halberd is a quest item and has no noted form.
    public static boolean isCrystal(int id) {
        return (id >= 4214 && id <= 4223) || (id >= 4225 && id <= 4234)
            || (id >= 11748 && id <= 11758)
            || (id >= 11759 && id <= 11769)
            || (id >= 13080 && id <= 13101);
    }

    public boolean skillcape() {
        return id >= 9747 && id <= 9814;
    }

    /**
     * Gets the value for this item.
     *
     * @return the value of this item.
     */
    public int getValue() {
        return TradingPost.TRADING_POST_VALUE_ENABLED ? TradingPost.getProtectionPrice(id) : this.unnote().definition(World.getWorld()).cost;
    }

    /**
     * Checks if this item is valid or not.
     *
     * @return
     */
    public boolean validate() {
        return id >= 0 && amount >= 0;
    }

    public boolean matchesId(int id) {
        return this.id == id;
    }

    @Override
    public Item clone() {
        return new Item(id, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item))
            return false;
        Item item = (Item) o;
        return item.getId() == this.getId()
            && item.getAmount() == this.getAmount();
    }

    public String toLongString() {
        return "Item{" +
            "id=" + id +
            ", name=" + name() +
            ", amount=" + amount +
            ", value=" + getValue() +
            ", noted=" + noted() + "}";
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", name=" + name() +
            ", amount=" + amount +
            ", noted=" + noted() +
            '}';
    }

    public String toShortString() {
        return name() + (getAmount() == 1 ? "" : " x" + getAmount()) + " id=" + getId() + "";
    }

    public String toShortValueString() {
        return name() + (getAmount() == 1 ? "" : " x" + getAmount()) +
            (getValue() == 0 ? "" :
                getAmount() == 1 ? " " + getValue() + "ea" :
                    " " + (long) getValue() * amount + "@" + getValue() + "ea");
    }

    public boolean equalIds(Item other) {
        return other != null && id == other.id;
    }

    public int getSellValue() {
        return (int) (getValue() * 0.85);
    }
}

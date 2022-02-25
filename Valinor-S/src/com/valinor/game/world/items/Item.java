package com.valinor.game.world.items;

import com.google.gson.annotations.Expose;
import com.valinor.fs.DefinitionRepository;
import com.valinor.fs.ItemDefinition;
import com.valinor.game.GameConstants;
import com.valinor.game.content.areas.wilderness.content.revenant_caves.AncientArtifacts;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.util.ItemIdentifiers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
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
        Mob.accumulateRuntimeTo(Item::checkDefs, t -> System.out.println("item def special fields took " + t.toMillis() + " ms"));
    }

    private static void checkDefs() {
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
            FEROCIOUS_GLOVES, AVERNIC_DEFENDER, INFERNAL_CAPE, RING_OF_TRINITY, RING_OF_PRECISION, RING_OF_SORCERY,
            RING_OF_MANHUNTING, CORRUPTED_RANGER_GAUNTLETS, FAWKES_32937, TASK_BOTTLE_SKILLING, TASK_BOTTLE_PVMING,
            TASK_BOTTLE_CASKET, RING_OF_VIGOUR,
        };

        for (int i : tradeable_special_items) {
            World.getWorld().definitions().get(ItemDefinition.class, i).tradeable_special_items = true;
        }

        for(Item item : GameConstants.BANK_ITEMS) {
            ItemDefinition unnoted = World.getWorld().definitions().get(ItemDefinition.class, item.unnote().getId());
            unnoted.pvpSpawnable = true;
            ItemDefinition noted = World.getWorld().definitions().get(ItemDefinition.class, item.note().getId());
            noted.pvpSpawnable = true;
        }

        int[] upgraded_weapons = {
            ABYSSAL_WHIP_TIER_1, ABYSSAL_WHIP_TIER_2, ABYSSAL_WHIP_TIER_3, ABYSSAL_WHIP_TIER_4, ABYSSAL_WHIP_TIER_5_1, ABYSSAL_WHIP_TIER_5_2, ABYSSAL_WHIP_TIER_5_3, ABYSSAL_WHIP_TIER_5_4, ABYSSAL_WHIP_TIER_5_5,
            DRAGON_DAGGER_TIER_1, DRAGON_DAGGER_TIER_2, DRAGON_DAGGER_TIER_3, DRAGON_DAGGER_TIER_4, DRAGON_DAGGER_TIER_5_1, DRAGON_DAGGER_TIER_5_2, DRAGON_DAGGER_TIER_5_3, DRAGON_DAGGER_TIER_5_4, DRAGON_DAGGER_TIER_5_5,
            MAGIC_SHORTBOW_TIER_1, MAGIC_SHORTBOW_TIER_2, MAGIC_SHORTBOW_TIER_3, MAGIC_SHORTBOW_TIER_4, MAGIC_SHORTBOW_TIER_5_1, MAGIC_SHORTBOW_TIER_5_2, MAGIC_SHORTBOW_TIER_5_3, MAGIC_SHORTBOW_TIER_5_4, MAGIC_SHORTBOW_TIER_5_5,
            RUNE_CROSSBOW_TIER_1, RUNE_CROSSBOW_TIER_2, RUNE_CROSSBOW_TIER_3, RUNE_CROSSBOW_TIER_4, RUNE_CROSSBOW_TIER_5_1, RUNE_CROSSBOW_TIER_5_2, RUNE_CROSSBOW_TIER_5_3, RUNE_CROSSBOW_TIER_5_4, RUNE_CROSSBOW_TIER_5_5,
            DRAGON_SCIMITAR_TIER_1, DRAGON_SCIMITAR_TIER_2, DRAGON_SCIMITAR_TIER_3, DRAGON_SCIMITAR_TIER_4, DRAGON_SCIMITAR_TIER_5_1, DRAGON_SCIMITAR_TIER_5_2, DRAGON_SCIMITAR_TIER_5_3, DRAGON_SCIMITAR_TIER_5_4, DRAGON_SCIMITAR_TIER_5_5,
            DRAGON_LONGSWORD_TIER_1, DRAGON_LONGSWORD_TIER_2, DRAGON_LONGSWORD_TIER_3, DRAGON_LONGSWORD_TIER_4, DRAGON_LONGSWORD_TIER_5_1, DRAGON_LONGSWORD_TIER_5_2, DRAGON_LONGSWORD_TIER_5_3, DRAGON_LONGSWORD_TIER_5_4, DRAGON_LONGSWORD_TIER_5_5,
            DRAGON_MACE_TIER_1, DRAGON_MACE_TIER_2, DRAGON_MACE_TIER_3, DRAGON_MACE_TIER_4, DRAGON_MACE_TIER_5_1, DRAGON_MACE_TIER_5_2, DRAGON_MACE_TIER_5_3, DRAGON_MACE_TIER_5_4, DRAGON_MACE_TIER_5_5,
            GRANITE_MAUL_TIER_1, GRANITE_MAUL_TIER_2, GRANITE_MAUL_TIER_3, GRANITE_MAUL_TIER_4, GRANITE_MAUL_TIER_5_1, GRANITE_MAUL_TIER_5_2, GRANITE_MAUL_TIER_5_3, GRANITE_MAUL_TIER_5_4, GRANITE_MAUL_TIER_5_5,
            STAFF_OF_LIGHT_TIER_1, STAFF_OF_LIGHT_TIER_2, STAFF_OF_LIGHT_TIER_3, STAFF_OF_LIGHT_TIER_4, STAFF_OF_LIGHT_TIER_5_1, STAFF_OF_LIGHT_TIER_5_2, STAFF_OF_LIGHT_TIER_5_3, STAFF_OF_LIGHT_TIER_5_4, STAFF_OF_LIGHT_TIER_5_5,
            DARK_BOW_12765, DARK_BOW_12766, DARK_BOW_12767, DARK_BOW_12768, DARK_BOW_TIER_5_1, DARK_BOW_TIER_5_2, DARK_BOW_TIER_5_3, DARK_BOW_TIER_5_4, DARK_BOW_TIER_5_5,
        };

        for (int w : upgraded_weapons) {
            World.getWorld().definitions().get(ItemDefinition.class, w).isUpgradedWeapon = true;
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

    public boolean untradeable() {
        return !rawtradable();
    }

    public String name() {
        ItemDefinition def = definition(World.getWorld());
        if (def == null) {
            return "Unknown name";
        }
        return def.name;
    }

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
        //Perhaps custom items, without a value
        if(this.unnote().definition(World.getWorld()).protectionValue == null) {
            return 150;
        }
        return this.unnote().definition(World.getWorld()).protectionValue.price;
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

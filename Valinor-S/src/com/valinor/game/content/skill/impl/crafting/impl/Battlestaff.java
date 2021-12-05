package com.valinor.game.content.skill.impl.crafting.impl;

import com.valinor.game.content.skill.impl.crafting.Craftable;
import com.valinor.game.content.skill.impl.crafting.CraftableItem;
import com.valinor.game.world.items.Item;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 05, 2021
 */
public enum Battlestaff implements Craftable {

    WATER(new Item(BATTLESTAFF), new Item(WATER_ORB), new CraftableItem(new Item(WATER_BATTLESTAFF), new Item(WATER_ORB),54,100.0)),
    EARTH(new Item(BATTLESTAFF), new Item(EARTH_ORB), new CraftableItem(new Item(EARTH_BATTLESTAFF), new Item(EARTH_ORB),58,112.5)),
    FIRE(new Item(BATTLESTAFF), new Item(FIRE_ORB), new CraftableItem(new Item(FIRE_BATTLESTAFF), new Item(FIRE_ORB),62,125.0)),
    AIR(new Item(BATTLESTAFF), new Item(AIR_ORB), new CraftableItem(new Item(AIR_BATTLESTAFF), new Item(AIR_ORB),66,137.5));

    private final Item use;
    private final Item with;
    private final CraftableItem[] items;

    Battlestaff(Item use, Item with, CraftableItem... items) {
        this.use = use;
        this.with = with;
        this.items = items;
    }

    @Override
    public String getName() {
        return "Battlestaff";
    }

    @Override
    public int getAnimation() {
        return -1;
    }

    @Override
    public Item getUse() {
        return use;
    }

    @Override
    public Item getWith() {
        return with;
    }

    @Override
    public CraftableItem[] getCraftableItems() {
        return items;
    }

    @Override
    public Item[] getIngredients(int index) {
        return new Item[] {use, with };
    }

    @Override
    public String getProductionMessage() {
        return null;
    }

}

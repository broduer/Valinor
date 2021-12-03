package com.valinor.game.content.skill.impl.crafting.impl;

import com.valinor.game.content.skill.impl.crafting.Craftable;
import com.valinor.game.content.skill.impl.crafting.CraftableItem;
import com.valinor.game.world.items.Item;
import com.valinor.util.ItemIdentifiers;

import static com.valinor.util.ItemIdentifiers.CHISEL;

public enum Amethyst implements Craftable {

    AMETHYST(new Item(CHISEL), new Item(ItemIdentifiers.AMETHYST), new CraftableItem(new Item(ItemIdentifiers.AMETHYST_BOLT_TIPS, 15), new Item(ItemIdentifiers.AMETHYST), 83, 60.0), new CraftableItem(new Item(ItemIdentifiers.AMETHYST_ARROWTIPS, 15), new Item(ItemIdentifiers.AMETHYST), 85, 60.0) ,new CraftableItem(new Item(ItemIdentifiers.AMETHYST_JAVELIN_HEADS, 5), new Item(ItemIdentifiers.AMETHYST), 87, 60.0));

    private final Item use;
    private final Item with;
    private final CraftableItem[] items;

    Amethyst(Item use, Item with, CraftableItem... items) {
        this.use = use;
        this.with = with;
        this.items = items;
    }

    @Override
    public String getName() {
        return "Amethyst";
    }

    @Override
    public int getAnimation() {
        return 6295;
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
        return new Item[] { with };
    }

    @Override
    public String getProductionMessage() {
        return "You cut the Amethyst.";
    }

}

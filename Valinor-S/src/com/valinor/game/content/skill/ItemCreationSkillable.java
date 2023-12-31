package com.valinor.game.content.skill;

import com.valinor.game.content.items.ItemSet;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.masks.animations.AnimationLoop;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.RequiredItem;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.util.Utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * An implementation of {@link DefaultSkillable}.
 *
 * This sub class handles the creation of an item.
 * It's used by many skills such as Fletching.
 *
 * @author Professor Oak
 */
public class ItemCreationSkillable extends DefaultSkillable {

    /**
     * A {@link List} containing all the {@link RequiredItem}s.
     */
    private final List<RequiredItem> requiredItems;

    /**
     * The item we're making.
     */
    private final Item product;

    /**
     * The amount to make.
     */
    private int amount;

    /**
     * The {@link AnimationLoop} the player will perform whilst
     * performing this skillable.
     */
    private final Optional<AnimationLoop> animLoop;

    /**
     * The level required to make this item.
     */
    private final int requiredLevel;

    /**
     * The experience a player will receive
     * in the said skill for making
     * this item.
     */
    private final double experience;

    /**
     * The skill to reward the player experience in.
     */
    private final int skill;

    public ItemCreationSkillable(List<RequiredItem> requiredItems, Item product, int amount, Optional<AnimationLoop> animLoop, int requiredLevel, double experience, int skill) {
        this.requiredItems = requiredItems;
        this.product = product;
        this.amount = amount;
        this.animLoop = animLoop;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.skill = skill;
    }

    @Override
    public void startAnimationLoop(Player player) {
        if (animLoop.isEmpty()) {
            return;
        }
        Task animLoopTask = new Task("ItemCreationAnimationTask", animLoop.get().getLoopDelay(), player, true) {
            @Override
            public void execute() {
                player.animate(animLoop.get().getAnim());
            }
        };
        TaskManager.submit(animLoopTask);
        getTasks().add(animLoopTask);
    }

    @Override
    public int cyclesRequired(Player player) {
        return 2;
    }

    @Override
    public void onCycle(Player player) {
    }

    @Override
    public void finishedCycle(Player player) {
        //Decrement amount to make and stop if we hit 0.
        //Random discord code audit, this is incorrect: https://i.imgur.com/t5A5WiM.png
        //if (amount-- <= 0) {
        if (--amount <= 0) {
            cancel(player);
        }

        //remove items required..
        filterRequiredItems(RequiredItem::isDelete).forEach(r -> {
            player.inventory().remove(r.getItem());
            if(r.getReplaceWith() != null) {
                //When worn, 10% chance of smelting 2 of any bar at once when using the Edgeville furnace.
                var doubleRoll = Utils.rollPercent((int) ItemSet.varrockDiaryArmour(player));
                var isOre = r.getItem().name().toLowerCase().contains("ore") || r.getItem().name().toLowerCase().contains("coal");
                if(doubleRoll && isOre) {
                    r.getReplaceWith().setAmount(2);
                }
                if(player.getEquipment().hasAt(EquipSlot.RING, RING_OF_CHAROSA))
                    r.getReplaceWith().setAmount(r.getReplaceWith().getAmount() + 1);
                player.inventory().add(r.getReplaceWith());
            }
        });

        //Add product..
        player.inventory().add(product);

        if(product.name().equalsIgnoreCase("Adamant platebody")) {
            player.getTaskBottleManager().increase(BottleTasks.MAKE_ADAMANT_PLATEBODY);
        }

        //Add exp..
        player.skills().addXp(skill, player.getEquipment().hasAt(EquipSlot.HANDS, GOLDSMITH_GAUNTLETS) ? experience * 2.5 : experience);

        if (World.getWorld().rollDie(100, 1)) {
            GroundItem item = new GroundItem(new Item(TASK_BOTTLE_SKILLING), player.tile(), player);
            GroundItemHandler.createGroundItem(item);
        }
    }

    @Override
    public boolean hasRequirements(Player player) {
        //Validate amount..
        if (amount <= 0) {
            return false;
        }

        //Check if we have required stringing level..
        if (player.skills().levels()[skill] < requiredLevel) {
            player.message("You need a "+Skills.SKILL_NAMES[skill]+" level of at least "+ requiredLevel +" to do this.");
            return false;
        }

        //Validate required items..
        //Check if we have the required ores..
        boolean hasItems = true;
        boolean hasHammer = player.getEquipment().contains(IMCANDO_HAMMER) || player.inventory().contains(IMCANDO_HAMMER);
        for (RequiredItem item : requiredItems) {
            if (!player.inventory().contains(item.getItem())) {
                String prefix = item.getItem().getAmount() > 1 ? Integer.toString(item.getItem().getAmount()) : "some";
                if(skill == Skills.SMITHING && !hasHammer) {
                    player.message("You "+(!hasItems ? "also need" : "need")+" "+prefix+" "+item.getItem().unnote().name()+".");
                }
                hasItems = hasHammer;
            }
        }
        if (!hasItems) {
            return false;
        }

        return super.hasRequirements(player);
    }

    @Override
    public boolean loopRequirements() {
        return true;
    }

    @Override
    public boolean allowFullInventory() {
        return true;
    }

    public void decrementAmount() {
        amount--;
    }

    public int getAmount() {
        return amount;
    }

    public List<RequiredItem> filterRequiredItems(Predicate<RequiredItem> criteria) {
        return requiredItems.stream().filter(criteria).collect(Collectors.toList());
    }

    public List<RequiredItem> getRequiredItems() {
        return requiredItems;
    }
}

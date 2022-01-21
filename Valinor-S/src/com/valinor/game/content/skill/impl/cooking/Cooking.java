package com.valinor.game.content.skill.impl.cooking;

import com.valinor.game.action.Action;
import com.valinor.game.action.policy.WalkablePolicy;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.ChatBoxItemDialogue;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;
import static com.valinor.util.ItemIdentifiers.COOKING_GAUNTLETS;
import static com.valinor.util.ItemIdentifiers.RING_OF_CHAROSA;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * Created by Situations on 11/20/2015.
 */
public class Cooking extends Interaction {

    private int cookingChance(Player player, Cookable type, GameObject obj) {
        int points = obj.getId() == CLAY_OVEN_21302 ? 63 : 60;
        int diff = player.skills().levels()[Skills.COOKING] - type.lvl;
        return Math.min(100, points + diff);
    }

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item with) {
        if (JugOfWine.onItemOnItem(player, use, with)) {
            return true;
        }

        if(Pizza.makePizza(player, use, with)) {
            return true;
        }

        if(Potato.makePotato(player, use, with)) {
            return true;
        }

        if(Toppings.makeTopping(player, use, with)) {
            return true;
        }

        //Bread dough
        if ((use.getId() == ItemIdentifiers.POT_OF_FLOUR && with.getId() == ItemIdentifiers.BUCKET_OF_WATER) || (use.getId() == ItemIdentifiers.BUCKET_OF_WATER && with.getId() == ItemIdentifiers.POT_OF_FLOUR)) {
            player.inventory().remove(new Item(ItemIdentifiers.POT_OF_FLOUR));
            player.inventory().remove(new Item(ItemIdentifiers.BUCKET_OF_WATER));
            player.inventory().add(new Item(ItemIdentifiers.BREAD_DOUGH));
            return true;
        } else if ((use.getId() == ItemIdentifiers.POT_OF_FLOUR && with.getId() == ItemIdentifiers.BOWL_OF_WATER) || (use.getId() == ItemIdentifiers.BOWL_OF_WATER && with.getId() == ItemIdentifiers.POT_OF_FLOUR)) {
            player.inventory().remove(new Item(ItemIdentifiers.POT_OF_FLOUR));
            player.inventory().remove(new Item(ItemIdentifiers.BOWL_OF_WATER));
            player.inventory().add(new Item(ItemIdentifiers.BREAD_DOUGH));
            return true;
        } else if ((use.getId() == ItemIdentifiers.POT_OF_FLOUR && with.getId() == ItemIdentifiers.JUG_OF_WATER) || (use.getId() == ItemIdentifiers.JUG_OF_WATER && with.getId() == ItemIdentifiers.POT_OF_FLOUR)) {
            player.inventory().remove(new Item(ItemIdentifiers.POT_OF_FLOUR));
            player.inventory().remove(new Item(ItemIdentifiers.JUG_OF_WATER));
            player.inventory().add(new Item(ItemIdentifiers.BREAD_DOUGH));
            return true;
        }
        //Uncooked stew
        if ((use.getId() == ItemIdentifiers.BOWL_OF_WATER && with.getId() == ItemIdentifiers.POTATO) || (use.getId() == ItemIdentifiers.POTATO && with.getId() == ItemIdentifiers.BOWL_OF_WATER) ||
            (use.getId() == ItemIdentifiers.BOWL_OF_WATER && with.getId() == ItemIdentifiers.COOKED_MEAT) || (use.getId() == ItemIdentifiers.COOKED_MEAT && with.getId() == ItemIdentifiers.BOWL_OF_WATER) ||
            (use.getId() == ItemIdentifiers.COOKED_MEAT && with.getId() == ItemIdentifiers.POTATO) || (use.getId() == ItemIdentifiers.POTATO && with.getId() == ItemIdentifiers.COOKED_MEAT)) {
            player.inventory().remove(new Item(ItemIdentifiers.POTATO));
            player.inventory().remove(new Item(ItemIdentifiers.COOKED_MEAT));
            player.inventory().remove(new Item(ItemIdentifiers.BOWL_OF_WATER));
            player.inventory().add(new Item(ItemIdentifiers.UNCOOKED_STEW));
            return true;
        } else if ((use.getId() == ItemIdentifiers.BOWL_OF_WATER && with.getId() == ItemIdentifiers.POTATO) || (use.getId() == ItemIdentifiers.POTATO && with.getId() == ItemIdentifiers.BOWL_OF_WATER) ||
            (use.getId() == ItemIdentifiers.BOWL_OF_WATER && with.getId() == ItemIdentifiers.COOKED_CHICKEN) || (use.getId() == ItemIdentifiers.COOKED_CHICKEN && with.getId() == ItemIdentifiers.BOWL_OF_WATER) ||
            (use.getId() == ItemIdentifiers.COOKED_CHICKEN && with.getId() == ItemIdentifiers.POTATO) || (use.getId() == ItemIdentifiers.POTATO && with.getId() == ItemIdentifiers.COOKED_CHICKEN)) {
            player.inventory().remove(new Item(ItemIdentifiers.POTATO));
            player.inventory().remove(new Item(ItemIdentifiers.COOKED_CHICKEN));
            player.inventory().remove(new Item(ItemIdentifiers.BOWL_OF_WATER));
            player.inventory().add(new Item(ItemIdentifiers.UNCOOKED_STEW));
            return true;
        }
        //Curry
        if ((use.getId() == ItemIdentifiers.SPICE && with.getId() == ItemIdentifiers.UNCOOKED_STEW) || (use.getId() == ItemIdentifiers.UNCOOKED_STEW && with.getId() == ItemIdentifiers.SPICE)) {
            player.inventory().remove(new Item(ItemIdentifiers.SPICE));
            player.inventory().remove(new Item(ItemIdentifiers.UNCOOKED_STEW));
            player.inventory().add(new Item(ItemIdentifiers.CURRY));
            return true;
        } else if ((use.getId() == ItemIdentifiers.CURRY_LEAF && with.getId() == ItemIdentifiers.UNCOOKED_STEW) || (use.getId() == ItemIdentifiers.UNCOOKED_STEW && with.getId() == ItemIdentifiers.CURRY_LEAF)) {
            if(!player.inventory().contains(new Item(ItemIdentifiers.CURRY_LEAF, 3))) {
                player.message("You need atleast 3 curry leaves.");
                return true;
            }
            player.inventory().remove(new Item(ItemIdentifiers.CURRY_LEAF, 3));
            player.inventory().remove(new Item(ItemIdentifiers.UNCOOKED_STEW));
            player.inventory().add(new Item(ItemIdentifiers.CURRY));
            return true;
        }
        return false;
    }

    private static final List<String> COOKING_OBJECTS = Arrays.asList("clay oven", "cooking range", "range", "fire", "fireplace", "stove", "sulphur vent", "cooking pot", "bonfire", "uncooking pot");

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        if(object.getId() == RANGE_7183 && object.tile().equals(3094, 3462)) {
            player.smartPathTo(new Tile(3093, 3463));
            player.waitForTile(new Tile(3093, 3463), () -> {
                cook(player);
            });
            return true;
        }
        if (COOKING_OBJECTS.stream().anyMatch(co -> object.definition().name.toLowerCase().contains(co))) {
            cook(player);
            return true;
        }
        return false;
    }

    private void cook(Player player) {
        int id = player.getAttribOr(AttributeKey.ITEM_ID, -1);
        GameObject obj = player.getAttribOr(AttributeKey.INTERACTION_OBJECT, null);
        Cookable food = Cookable.get(id);
        player.faceObj(obj);

        if (food != null) {
            //Check to see if the player has the level required to cook the food
            if (player.skills().levels()[Skills.COOKING] < food.lvl) {
                DialogueManager.sendStatement(player, "You need a cooking level of " + food.lvl + " to cook " + food.itemname + ".");
            } else {
                startCooking(player, food, obj);
            }
        }
    }

    private final static int SINEW = 9436;

    private void startCooking(Player player, Cookable food, GameObject obj) {
        //If we're using raw beef or bear meat we..
        if (food.raw == 2132 || food.raw == 2136) {
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Dry the meat into sinew.", "Cook the meat");
                    setPhase(0);
                }

                @Override
                protected void select(int option) {
                    if (isPhase(0)) {
                        if (option == 1) {
                            stop();
                            player.action.execute(cook(player, food, obj, 1), true);
                        } else if (option == 2) {
                            stop();
                            player.action.execute(cook(player, food, obj, 1), true);
                        }
                    }
                }
            });
            return;
        }
        // Either 1 if we only have one, else ask.
        int amt = player.inventory().count(food.raw);
        if (amt == 1) {
            player.action.execute(cook(player, food, obj, 1), true);
        } else {
            ChatBoxItemDialogue.sendInterface(player, 1746, 175, food.raw);
            player.chatBoxItemDialogue = new ChatBoxItemDialogue(player) {
                @Override
                public void firstOption(Player player) {
                    player.action.execute(cook(player, food, obj, 1), true);
                }

                @Override
                public void secondOption(Player player) {
                    player.action.execute(cook(player, food, obj, 5), true);
                }

                @Override
                public void thirdOption(Player player) {
                    player.setEnterSyntax(new EnterSyntax() {
                        @Override
                        public void handleSyntax(Player player, long input) {
                            player.action.execute(cook(player, food, obj, (int) input), true);
                        }
                    });
                    player.getPacketSender().sendEnterAmountPrompt("How many would you like to cook?");
                }

                @Override
                public void fourthOption(Player player) {
                    player.action.execute(cook(player, food, obj,player.inventory().count(food.raw)), true);
                }
            };
        }
    }

    private Action<Player> cook(Player player, Cookable food, GameObject obj, int amount) {
        return new Action<>(player, 3) {
            int ticks = 0;

            @Override
            public void execute() {
                if (!player.inventory().remove(new Item(food.raw), true)) {
                    player.message("You don't have any more " + food.plural + " to cook.");
                    stop();
                    return;
                }

                if (obj.getId() == 26185 || obj.getId() == 29300) {
                    player.animate(897);
                } else {
                    player.animate(896);
                }

                int chance = cookingChance(player, food, obj);

                // Cooking skillcape stops burning all food.
                if (player.getEquipment().containsAny(9801, 9802, 10658)
                    || player.getEquipment().wearingMaxCape()
                    || player.getEquipment().hasAt(EquipSlot.HANDS, COOKING_GAUNTLETS)
                    || World.getWorld().rollDie(100, chance)
                    || food == Cookable.SEAWEED) {

                    int amount = player.getEquipment().hasAt(EquipSlot.RING, RING_OF_CHAROSA) ? 2 : 1;
                    player.inventory().add(new Item(food.cooked, amount), true);

                    String msg = switch (food) {
                        case RAW_LOBSTER -> "You roast a lobster.";
                        case PIE_MEAT -> "You successfully bake a tasty meat pie.";
                        case REDBERRY_PIE -> "You successfully bake a delicious redberry pie.";
                        case SEAWEED -> "You burn the seaweed to soda ash.";
                        default -> "You successfully cook " + food.itemname + ".";
                    };

                    if(food == Cookable.RAW_SHARK) {
                        player.getTaskBottleManager().increase(BottleTasks.COOK_SHARKS);
                    }

                    if(food == Cookable.BREAD || food == Cookable.CAKE) {
                        AchievementsManager.activate(player, Achievements.BAKER,1);
                    } else {
                        AchievementsManager.activate(player, Achievements.COOK,1);
                    }

                    if (World.getWorld().rollDie(75, 1)) {
                        GroundItem item = new GroundItem(new Item(TASK_BOTTLE_SKILLING), player.tile(), player);
                        GroundItemHandler.createGroundItem(item);
                    }

                    player.message(msg);
                    player.skills().addXp(Skills.COOKING, food.exp);
                    //Caskets Money, money, money..
                    if (Utils.rollDie(20, 1)) {
                        player.inventory().addOrDrop(new Item(7956, 1));
                        player.message("Hmm strange, a casket appears on the cooking range. You took it before it burnt.");
                    }
                } else {
                    player.inventory().add(new Item(food.burnt), true);
                    player.message("You accidentally burn " + food.itemname.replaceFirst("an ", "the ").replaceFirst("a ", "the ") + ".");
                }

                if (++ticks == amount) {
                    stop();
                }
            }

            @Override
            public String getName() {
                return "Super combat potion";
            }

            @Override
            public boolean prioritized() {
                return false;
            }

            @Override
            public WalkablePolicy getWalkablePolicy() {
                return WalkablePolicy.NON_WALKABLE;
            }
        };
    }
}

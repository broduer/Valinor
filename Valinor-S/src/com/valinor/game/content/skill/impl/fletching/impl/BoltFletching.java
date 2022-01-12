package com.valinor.game.content.skill.impl.fletching.impl;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.action.Action;
import com.valinor.game.action.policy.WalkablePolicy;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.ChatBoxItemDialogue;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 12, 2022
 */
public class BoltFletching extends Interaction {

    public enum Bolt {

        /**
         * Gem-tipped bolts
         */
        OPAL(11, 1.6, 877, 45, 879),
        JADE(26, 2.4, 9139, 9187, 9335),
        PEARL(41, 3.0, 9140, 46, 880),
        RED_TOPAZ(48, 4.0, 9141, 9188, 9336),
        SAPPHIRE(56, 4.0, 9142, 9189, 9337),
        EMERALD(55, 4.0, 9142, 9190, 9338),
        RUBY(63, 6.3, 9143, 9191, 9339),
        DIAMOND(65, 7.0, 9143, 9192, 9340),
        DRAGONSTONE(71, 8.2, 9144, 9193, 9341),
        ONYX(73, 10.0, 9144, 9194, 9342),

        /**
         * Dragon gem-tipped bolts
         */
        OPAL_DRAGON(84, 12.0, 21905, 45, 21955),
        JADE_DRAGON(84, 12.0, 21905, 9187, 21957),
        PEARL_DRAGON(84, 12.0, 21905, 46, 21959),
        RED_TOPAZ_DRAGON(84, 12.0, 21905, 9188, 21961),
        SAPPHIRE_DRAGON(84, 12.0, 21905, 9189, 21963),
        EMERALD_DRAGON(84, 12.0, 21905, 9190, 21965),
        RUBY_DRAGON(84, 12.0, 21905, 9191, 21967),
        DIAMOND_DRAGON(84, 12.0, 21905, 9192, 21969),
        DRAGONSTONE_DRAGON(84, 12.0, 21905, 9193, 21971),
        ONYX_DRAGON(84, 12.0, 21905, 9194, 21973),

        /**
         * Other
         */
        BROAD(55, 3.0, 11875, 21338, 21316),
        AMETHYST(76, 10.6, 11875, 21338, 21316);

        public final int levelRequirement;
        public final double experience;
        public final int id;
        public final int tip;
        public final int tipped;
        public final String tippedName;

        Bolt(int levelRequirement, double experience, int id, int tip, int tipped) {
            this.levelRequirement = levelRequirement;
            this.experience = experience;
            this.id = id;
            this.tip = tip;
            this.tipped = tipped;
            this.tippedName = World.getWorld().definitions().get(ItemDefinition.class, tipped).name;
        }

        private void make(Player player, Item boltItem, Item tipItem) {
            player.inventory().remove(boltItem.getId(), 10);
            player.inventory().remove(tipItem.getId(), 10);
            player.inventory().add(tipped, 10);
            player.skills().addXp(Skills.FLETCHING, experience * 10, true);
            boolean broad = boltItem.getId() == BROAD.id;
            if (broad)
                player.message("You attach feathers to the broad bolts.");
            else
                player.message("You fletch the bolts");
        }
    }

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        for (Bolt bolt : Bolt.values()) {
            if ((use.getId() == bolt.id || usedWith.getId() == bolt.id) && (use.getId() == bolt.tip || usedWith.getId() == bolt.tip)) {
                Item boltItem = player.getInventory().findItem(bolt.id);
                if (boltItem == null)
                    return false;
                Item tipItem = player.getInventory().findItem(bolt.tip);
                if (tipItem == null)
                    return false;

                if (!player.skills().check(Skills.FLETCHING, bolt.levelRequirement, bolt.tipped, "make " + bolt.tippedName))
                    return false;

                ChatBoxItemDialogue.sendInterface(player, 1746, 170, bolt.tipped);
                player.chatBoxItemDialogue = new ChatBoxItemDialogue(player) {
                    @Override
                    public void firstOption(Player player) {
                        player.action.execute(makeBolts(player, bolt, use, usedWith, 1), true);
                    }

                    @Override
                    public void secondOption(Player player) {
                        player.action.execute(makeBolts(player, bolt, use, usedWith, 5), true);
                    }

                    @Override
                    public void thirdOption(Player player) {
                        player.setEnterSyntax(new EnterSyntax() {
                            @Override
                            public void handleSyntax(Player player, long input) {
                                player.action.execute(makeBolts(player, bolt, use, usedWith, (int) input), true);
                            }
                        });
                        player.getPacketSender().sendEnterAmountPrompt("How many would you like to make?");
                    }

                    @Override
                    public void fourthOption(Player player) {
                        player.action.execute(makeBolts(player, bolt, use, usedWith, player.inventory().count(bolt.tip)), true);
                    }
                };
                return true;
            }
        }
        return false;
    }

    private static Action<Player> makeBolts(Player player, Bolt bolt, Item use, Item with, int amount) {
        return new Action<>(player, 1) {
            int ticks = 0;

            @Override
            public void execute() {
                if (!player.inventory().contains(new Item(bolt.id)) || !player.inventory().contains(bolt.tip)) {
                    stop();
                    return;
                }

                bolt.make(player, use, with);

                if (++ticks == amount) {
                    stop();
                }
            }

            @Override
            public String getName() {
                return "Bolts";
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

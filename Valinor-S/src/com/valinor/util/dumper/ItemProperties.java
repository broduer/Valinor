package com.valinor.util.dumper;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 12, 2022
 */
public class ItemProperties {

    public int id;
    public String name;
    public boolean members;
    public boolean tradable;
    public boolean tradable_on_ge;
    public boolean stackable;
    public boolean noted;
    public boolean noteable;
    public int linked_id;
    public boolean placeholder;
    public boolean equipable;
    public boolean equipable_by_player;
    public boolean equipable_weapon;
    public int cost;
    public int lowalch;
    public int highalch;
    public double weight;
    public int buy_limit;
    public boolean quest_item;
    public String release_date;
    public String examine;
    public String url;
    public EquipmentProperties equipment;
    public Weapon weapon;

    public static class EquipmentProperties {
        public int attack_stab;
        public int attack_slash;
        public int attack_crush;
        public int attack_magic;
        public int attack_ranged;
        public int defence_stab;
        public int defence_slash;
        public int defence_crush;
        public int defence_magic;
        public int defence_ranged;
        public int melee_strength;
        public int ranged_strength;
        public int magic_damage;
        public int prayer;
        public String slot;
        public Requirements requirements;

        public static class Requirements {
            public int attack;
            public int defence;
            public int strength;
            public int hitpoints;
            public int ranged;
            public int prayer;
            public int magic;
        }
    }

    public static class Weapon {
        public int attack_speed;
        public String weapon_type;
    }
}

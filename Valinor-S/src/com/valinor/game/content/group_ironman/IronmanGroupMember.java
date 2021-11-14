package com.valinor.game.content.group_ironman;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;

import java.util.Optional;

/**
 * Represents an ironman group member
 *
 * @author optimum on 14/05/2020
 */
public class IronmanGroupMember {

    private String username;
    private int combatLevel;
    private long totalXp;
    private int totalLevel;

    /**
     * updates setting with a player object
     * @param player - the player
     */
    public void update(Player player) {
        this.username = player.getUsername();
        this.combatLevel = player.skills().combatLevel();
        this.totalXp = player.skills().getTotalExperience();
        this.totalLevel = player.skills().totalLevel();
    }

    public IronmanGroupMember() {
    }

    public IronmanGroupMember(Player player) {
        update(player);
    }

    public String getUsername() {
        return username;
    }

    public Optional<Player> getPlayer() {
        return World.getWorld().getPlayerByName(this.username);
    }

    public IronmanGroupMember setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getCombatLevel() {
        return combatLevel;
    }

    public IronmanGroupMember setCombatLevel(int combatLevel) {
        this.combatLevel = combatLevel;
        return this;
    }

    public long getTotalXp() {
        return totalXp;
    }

    public IronmanGroupMember setTotalXp(long totalXp) {
        this.totalXp = totalXp;
        return this;
    }

    public int getTotalLevel() {
        return totalLevel;
    }

    public IronmanGroupMember setTotalLevel(int totalLevel) {
        this.totalLevel = totalLevel;
        return this;
    }
}

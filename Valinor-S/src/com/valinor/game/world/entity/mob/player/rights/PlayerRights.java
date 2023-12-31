package com.valinor.game.world.entity.mob.player.rights;

import com.valinor.GameServer;
import com.valinor.game.world.entity.mob.player.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * The rights of a player determines their authority. Every right can be viewed
 * with a name and a value. The value is used to separate each right from one
 * another.
 * 
 * @author Jason
 * @author Patrick van Elderen | zondag 14 juli 2019 (CEST) : 13:25
 * @see <a href="https://github.com/Patrick9-10-1995">Github profile</a>
 *
 */
public enum PlayerRights {

    PLAYER("Player", -1, -1, 0),

    SUPPORT("Support Team", 505, 20, 2),

    MODERATOR("Moderator", 494, 1, 3),

    ADMINISTRATOR("Administrator", 495, 2, 4),

    OWNER("Owner", 496, 3, 6),

    DEVELOPER("Developer", 497, 4, 5),

    BRONZE_YOUTUBER("Bronze Youtuber", 1087, 12, 1),

    SILVER_YOUTUBER("Silver Youtuber", 1088, 13, 1),

    GOLD_YOUTUBER("Gold Youtuber", 1089, 14, 1),

    IRON_MAN("Iron Man", 502, 15, 0),

    ULTIMATE_IRON_MAN("Ultimate Iron Man", 503, 16, 0),

    HARDCORE_IRON_MAN("Hardcore Iron Man", 504, 17, 0),

    GROUP_IRON_MAN("Group Iron Man", 1917, 18, 0),

    GROUP_HARDCORE_IRONMAN("Group Hardcore Iron Man", 1918, 19, 0),

    SECURITY_MODERATOR("Security Mod", 1861, 21, 3),

    EVENT_MANAGER("Event Manager", 468, 22, 0),

    INSTANT_PKER("Instant Pker", 506, 23, 0),

    HEAD_ADMIN("Head Admin", 1940, 24, 4),

    COLLECTION_IRONMAN("Collection ironman", 1398, 25, 0),

    ;

    private final String name;

    private final int spriteId;

    private final int clientImageId;

    /**
     * The value of the right. The higher the value, the more permissions the player has.
     */
    private final int rightValue;

    PlayerRights(String name, int spriteId, int clientImageId, int rightValue) {
        this.name = name;
        this.spriteId = spriteId;
        this.clientImageId = clientImageId;
        this.rightValue = rightValue;
    }

    @Override
    public String toString() {
        return "PlayerRights{" + "name='" + name + '\'' + '}';
    }

    /**
     * A {@link Set} of all {@link PlayerRights} elements that cannot be directly
     * modified.
     */
    private static final Set<PlayerRights> RIGHTS = Collections.unmodifiableSet(EnumSet.allOf(PlayerRights.class));

    /**
     * Returns a {@link PlayerRights} object for the value.
     *
     * @param value the right level
     * @return the rights object
     */
    public static PlayerRights get(int value) {
        return RIGHTS.stream().filter(element -> element.rightValue == value).findFirst().orElse(PLAYER);
    }

    public final String getName() {
        return name;
    }

    public final int getSpriteId() {
        return spriteId;
    }

    public final int getClientImageId() {
        return clientImageId;
    }

    public final int getRightValue() {
        return rightValue;
    }

    public boolean isSupportOrGreater(Player player) {
        return getRightValue() >= SUPPORT.getRightValue() || isOwner(player);
    }

    public boolean isModeratorOrGreater(Player player) {
        return getRightValue() >= MODERATOR.getRightValue() || isOwner(player);
    }

    public boolean isEventManagerOrGreater(Player player) {
        return getRightValue() >= EVENT_MANAGER.getRightValue() || isOwner(player);
    }

    public boolean isAdminOrGreater(Player player) {
        return getRightValue() >= ADMINISTRATOR.getRightValue() || isOwner(player);
    }

    /** Checks if the player is a developer or greater. */
    public boolean isDeveloperOrGreater(Player player) {
        return getRightValue() >= DEVELOPER.getRightValue() || isOwner(player);
    }

    public boolean isServerSupport() {
        return getRightValue() == SUPPORT.getRightValue();
    }

    public boolean isModerator() {
        return getRightValue() == MODERATOR.getRightValue();
    }

    public boolean isAdmin(Player player) {
        return getRightValue() == ADMINISTRATOR.getRightValue() || isOwner(player);
    }

    public boolean isOwner(Player player) {
        return getRightValue() >= OWNER.getRightValue() || Arrays.stream(getOwners()).anyMatch(username -> username.equalsIgnoreCase(player.getUsername()));
    }

    public String[] getOwners() {
        if(!GameServer.properties().production) {
            return new String[]{"patrick", "matthew", "test"};
        } else {
            return new String[]{"matthew"};
        }
    }

    public final boolean greater(PlayerRights other) {
        return getRightValue() > other.getRightValue();
    }

    public final boolean less(PlayerRights other) {
        return getRightValue() < other.getRightValue();
    }

    public boolean isStaffMember(Player player) {
        return isServerSupport() || isModerator() || isAdminOrGreater(player);
    }

    public boolean isStaffMemberOrYoutuber(Player player) {
        return isServerSupport() || isModerator() || isAdminOrGreater(player) || isYoutuber(player);
    }

    public boolean isYoutuber(Player player) {
        return player.getPlayerRights().equals(BRONZE_YOUTUBER) || player.getPlayerRights().equals(SILVER_YOUTUBER) || player.getPlayerRights().equals(GOLD_YOUTUBER);
    }

    /** Gets the crown display. */
    public static String getCrown(Player player) {
        return player.getPlayerRights().equals(PLAYER) ? "" : "<img=" + (player.getPlayerRights().getSpriteId()) + ">";
    }
}

package com.valinor.util;

import java.util.HashMap;
import java.util.Map;

public enum PlayerRights {
    //Warning: the rights in here must match the server-side Rights enum.
    PLAYER(null),
    MODERATOR(ChatCrown.MOD_CROWN),
    ADMINISTRATOR(ChatCrown.ADMIN_CROWN),
    OWNER(ChatCrown.OWNER_CROWN),
    DEVELOPER(ChatCrown.DEVELOPER),
    BRONZE_YOUTUBER(ChatCrown.BRONZE_YOUTUBER),
    SILVER_YOUTUBER(ChatCrown.SILVER_YOUTUBER),
    GOLD_YOUTUBER(ChatCrown.GOLD_YOUTUBER),
    IRON_MAN(ChatCrown.IRON_MAN),
    ULTIMATE_IRONMAN(ChatCrown.ULTIMATE_IRONMAN),
    HARDCORE_IRONMAN(ChatCrown.HARDCORE_IRONMAN),
    GROUP_IRONMAN(ChatCrown.GROUP_IRONMAN),
    GROUP_HARDCORE_IRONMAN(ChatCrown.GROUP_HARDCORE_IRONMAN),
    SUPPORT(ChatCrown.SUPPORT),
    SECURITY_MOD(ChatCrown.SECURITY_MOD),
    EVENT_MANAGER(ChatCrown.EVENT_MANAGER),
    INSTANT_PKER(ChatCrown.INSTANT_PKER),
    HEAD_ADMIN(ChatCrown.HEAD_ADMIN),
    ;

    private final ChatCrown crown;

    PlayerRights(ChatCrown crown) {
        this.crown = crown;
    }

    public ChatCrown getCrown() {
        return crown;
    }

    private static final Map<Integer, PlayerRights> rights = new HashMap<>();
    static {
        for (PlayerRights r : PlayerRights.values()) {
            rights.put(r.ordinal(), r);
        }
    }

    public static PlayerRights get(int ordinal) {
        return rights.getOrDefault(ordinal, PlayerRights.PLAYER);
    }
}

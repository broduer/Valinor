package com.valinor.util;

import java.util.HashMap;
import java.util.Map;

public enum MemberRights {

    NONE(null),
    SAPPHIRE_MEMBER(ChatCrown.SAPPHIRE_MEMBER),
    EMERALD_MEMBER(ChatCrown.EMERALD_MEMBER),
    RUBY_MEMBER(ChatCrown.RUBY_MEMBER),
    DIAMOND_MEMBER(ChatCrown.DIAMOND_MEMBER),
    DRAGONSTONE_MEMBER(ChatCrown.DRAGONSTONE_MEMBER),
    ONYX_MEMBER(ChatCrown.ONYX_MEMBER),
    ZENYTE_MEMBER(ChatCrown.ZENYTE_MEMBER),
    ;

    private final ChatCrown crown;

    MemberRights(ChatCrown crown) {
        this.crown = crown;
    }

    public ChatCrown getCrown() {
        return crown;
    }

    private static final Map<Integer, MemberRights> rights = new HashMap<>();
    static {
        for (MemberRights r : MemberRights.values()) {
            rights.put(r.ordinal(), r);
        }
    }

    public static MemberRights get(int ordinal) {
        return rights.getOrDefault(ordinal, MemberRights.NONE);
    }
}

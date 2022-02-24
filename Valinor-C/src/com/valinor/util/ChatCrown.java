package com.valinor.util;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public enum ChatCrown {

    SUPPORT("<img=20</img>", 505),
    MOD_CROWN("<img=1</img>", 494),
    ADMIN_CROWN("<img=2</img>", 495),
    OWNER_CROWN("<img=3</img>", 496),
    DEVELOPER("<img=4</img>", 497),

    SAPPHIRE_MEMBER("<img=5</img>", 1871),
    EMERALD_MEMBER("<img=6</img>", 1875),
    RUBY_MEMBER("<img=7</img>", 1872),
    DIAMOND_MEMBER("<img=8</img>", 1874),
    DRAGONSTONE_MEMBER("<img=9</img>", 1876),
    ONYX_MEMBER("<img=10</img>", 1877),
    ZENYTE_MEMBER("<img=11</img>", 1873),
    BRONZE_YOUTUBER("<img=12</img>", 1087),
    SILVER_YOUTUBER("<img=13</img>", 1088),
    GOLD_YOUTUBER("<img=14</img>", 1089),
    IRON_MAN("<img=15</img>", 502),
    ULTIMATE_IRONMAN("<img=16</img>", 503),
    HARDCORE_IRONMAN("<img=17</img>", 504),
    GROUP_IRONMAN("<img=18</img>", 1917),
    GROUP_HARDCORE_IRONMAN("<img=19</img>", 1918),
    SECURITY_MOD("<img=21</img>", 1861),
    EVENT_MANAGER("<img=22</img>", 468),
    INSTANT_PKER("<img=23</img>", 506),
    HEAD_ADMIN("<img=24</img>", 1940),
    COLLECTION_IRONMAN("<img=25</img>", 470),
    ;

    private final String identifier;
    private final int spriteId;

    ChatCrown(String identifier, int spriteId) {
        this.identifier = identifier;
        this.spriteId = spriteId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getSpriteId() {
        return spriteId;
    }

    private static final Set<ChatCrown> STAFF = EnumSet.of(SUPPORT, MOD_CROWN, ADMIN_CROWN, OWNER_CROWN, DEVELOPER, SECURITY_MOD);

    public boolean isStaff() {
        return STAFF.contains(this);
    }

    public static List<ChatCrown> get(int rights, int donatorRights) {
        List<ChatCrown> crowns = new ArrayList<>();

        PlayerRights playerRights = PlayerRights.get(rights);
        if (playerRights != PlayerRights.PLAYER && playerRights.getCrown() != null) {
            crowns.add(playerRights.getCrown());
        }

        MemberRights donorRights = MemberRights.get(donatorRights);
        if (donorRights != MemberRights.NONE && donorRights.getCrown() != null) {
            crowns.add(donorRights.getCrown());
        }

        return crowns;
    }
    
}

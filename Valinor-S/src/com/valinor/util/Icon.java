package com.valinor.util;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 06, 2021
 */
public enum Icon {
    RED_INFO_BADGE(83),
    YELLOW_INFO_BADGE(84),
    MYSTERY_BOX(33),
    BLUE_INFO_BADGE(85),
    GREEN_INFO_BADGE(86),
    WILDERNESS(29),
    ANNOUNCEMENT(15),
    HCIM_DEATH(504),
    DONATOR(142)
    ;

    public final int imgId;

    Icon(int imgId) {
        this.imgId = imgId;
    }


    public String tag() {
        return "<img=" + imgId + ">";
    }

}

package com.valinor.game.content.group_ironman.sorts;

import com.valinor.game.content.group_ironman.IronmanGroup;

import java.util.Comparator;

/**
 * Ironman default sort by getAverageCombatLevel
 *
 * @author optimum on 14/05/2020
 */
public class IronmanGroupAverageSort implements Comparator<IronmanGroup> {

    @Override
    public int compare(IronmanGroup o1, IronmanGroup o2) {
        return Integer.compare(o2.getAverageTotalLevel(), o1.getAverageTotalLevel());
    }

    public static final IronmanGroupAverageSort AVERAGE_SORT = new IronmanGroupAverageSort();
}

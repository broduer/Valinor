package com.valinor.game.content.group_ironman.sorts;

import com.valinor.game.content.group_ironman.IronmanGroup;

import java.util.Comparator;

/**
 * Ironman date sorting
 * @author optimum on 14/05/2020
 */
public class IronmanGroupDateSort implements Comparator<IronmanGroup> {

    @Override
    public int compare(IronmanGroup o1, IronmanGroup o2) {
        return o1.getDateStated().compareTo(o2.getDateStated());
    }

    public static final IronmanGroupDateSort DATE_SORT = new IronmanGroupDateSort();
}

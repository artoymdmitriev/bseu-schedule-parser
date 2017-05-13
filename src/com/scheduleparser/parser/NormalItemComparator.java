package com.scheduleparser.parser;

import java.util.Comparator;

/**
 * Created by Artoym on 13.05.2017.
 */
public class NormalItemComparator implements Comparator<NormalItem> {
    @Override
    public int compare(NormalItem o1, NormalItem o2) {
            int n;
            n = new Integer(o1.getNumberOfWeek()).compareTo(new Integer(o2.getNumberOfWeek()));
            if (n != 0) return n;
            n = new Integer(o1.getNumberOfWeek()).compareTo(new Integer(o2.getNumberOfWeek()));
            if (n != 0) return n;

            return 0;
    }
}

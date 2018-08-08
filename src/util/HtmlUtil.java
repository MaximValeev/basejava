package util;

import model.Place;

public class HtmlUtil {
    public static String formatDates(Place.WorkPosition workPosition) {
        return DateUtil.format(workPosition.getStartDate()) + "-" + DateUtil.format(workPosition.getEndDate());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}

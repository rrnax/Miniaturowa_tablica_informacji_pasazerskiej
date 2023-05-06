package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.TimeTable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class TimeComparator implements Comparator<WarsawTimeTable> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public int compare(WarsawTimeTable o1, WarsawTimeTable o2) {
        LocalTime time1 = LocalTime.parse(o1.getEstimatedTime(), formatter);
        LocalTime time2 = LocalTime.parse(o2.getEstimatedTime(), formatter);
        return time1.compareTo(time2);
    }
}

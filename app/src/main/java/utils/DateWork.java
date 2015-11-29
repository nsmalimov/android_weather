package utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateWork {
    public static String todayDate()
    {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String answer = Integer.toString(year) + "_" +
                        Integer.toString(month) + "_" +
                        Integer.toString(day);
        return answer;
    }

    public static ArrayList<String> getDateRange() {

        int howMuchDay = Parametres.howMuchDaysNeed;
        DateTime startDate = DateTime.parse(todayDate().replace("_", "-"));

        List<DateTime> ret = new ArrayList<DateTime>();
        DateTime tmp = startDate;
        ret.add(tmp);

        for (int i = 0; i < howMuchDay-1; i++) {
            tmp = tmp.plusDays(1);
            ret.add(tmp);
        }

        ArrayList<String> datesArray = new ArrayList<String>();

        for (DateTime d: ret)
        {
            datesArray.add(d.toString().split("T")[0].replace("-", "_"));

        }

        return datesArray;
    }
}

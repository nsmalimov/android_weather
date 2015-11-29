package utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class DateWork {
    public static String todayDate()
    {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String yearStr = Integer.toString(year);
        String monthStr = Integer.toString(month);
        String dayStr = Integer.toString(day);

        if (month < 10)
            monthStr = "0" + monthStr;
        if (day < 10)
            dayStr = "0" + dayStr;

        String answer = yearStr + "_" + monthStr + "_" + dayStr;

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
            datesArray.add("date_" + d.toString().split("T")[0].replace("-", "_"));

        }

        return datesArray;
    }
}

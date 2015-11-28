package database;

import android.content.Context;

import java.util.ArrayList;

import utils.DateWork;


public class SqLiteWork {
    public static ArrayList<DBHelper.SingleDB> tablesInst = new ArrayList<DBHelper.SingleDB>();
    public static Context context;
    public static void createUpdateBase(Context contextGet)
    {
        context = contextGet;
        ArrayList<String> needTime = DateWork.getDateRange();

        DBHelper d = new DBHelper();
        d.tableName = "workerTable";
        d.needCreate = false;

        DBHelper.SingleDB singleD = d.new SingleDB(context);

        ArrayList<String> allTables = singleD.getAllTables();
        deleteOldTables(singleD, needTime.get(0), allTables);

        ArrayList<String> needDates = DateWork.getDateRange();

        createNeedTables(d, allTables, needDates);
    }

    public static void updateData()
    {
        ArrayList<String> cities = new ArrayList<String>();
    }

    public static void deleteOldTables(DBHelper.SingleDB mydb, String todayDate, ArrayList<String> allTables)
    {
        for (String s: allTables)
        {
            String[] sSplit = s.split("-");
            String[] todayDateSplit = todayDate.split("-");
            //year month day
            if (Integer.parseInt(sSplit[0]) < Integer.parseInt(todayDateSplit[0]) ||
                    Integer.parseInt(sSplit[1]) < Integer.parseInt(todayDateSplit[1]) ||
                    (Integer.parseInt(sSplit[2]) < Integer.parseInt(todayDateSplit[2]) &&
                            Integer.parseInt(sSplit[1]) == Integer.parseInt(todayDateSplit[1])))
            {
                mydb.deleteTable(s);
            }
        }
    }

    public static void createNeedTables(DBHelper d, ArrayList<String> allTables, ArrayList<String> needDates)
    {
        for (String s: needDates)
        {
            if (!allTables.contains(s))
            {
                d.tableName = s;
                d.needCreate = true;
                tablesInst.add(d.new SingleDB(context));
            }
            else
            {
                d.tableName = s;
                d.needCreate = false;
                tablesInst.add(d.new SingleDB(context));
            }
        }
    }
}

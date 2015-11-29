package database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import MainWorkers.GetWeatherData;
import utils.DateWork;
import utils.Parametres;
import utils.Weather;


public class SqLiteWork {
    public static HashMap<String, DBHelper.SingleDB> tablesInst = new HashMap<String, DBHelper.SingleDB>();
    public static Context context;
    public static void createUpdateBase(Context contextGet) throws Exception
    {
        context = contextGet;
        context.deleteDatabase("WeatherDB.db");
        //ArrayList<String> needTime = DateWork.getDateRange();

        //DBHelper d = new DBHelper();
        //d.tableName = "workerTable";
        //d.needCreate = false;

        //DBHelper.SingleDB singleD = d.new SingleDB(context);

        //ArrayList<String> allTables = singleD.getAllTables();
        //deleteOldTables(singleD, needTime.get(0), allTables);

        ArrayList<String> needDates = DateWork.getDateRange();

        createNeedTables(needDates);
    }

    public static void updateData() throws Exception
    {
        ArrayList<String> cities = Parametres.cities;

        for (String s: cities)
        {
            Log.i("Data", s);

            HashMap<String, Weather> mapCities = GetWeatherData.getAllDataNextDays(s);

            //date weather by city

            //iterate by dates
            for (HashMap.Entry<String, Weather> entry : mapCities.entrySet())
            {
                Weather wth = entry.getValue(); //weather

                //Log.i("Instanc", tablesInst.get(entry.getKey()).toString());
                //Log.i("Instanc1", tablesInst.entrySet().toString());

                tablesInst.get(entry.getKey()).insertWeatherData(s, wth.temp, wth.pressure,
                        wth.humidity, wth.clouds, wth.wind);
            }
        }
    }

    public static void deleteOldTables(DBHelper.SingleDB mydb, String todayDate, ArrayList<String> allTables)
    {
        for (String s: allTables)
        {
            //String[] sSplit = s.split("-");
            //String[] todayDateSplit = todayDate.split("-");
            //year month day
            //if (Integer.parseInt(sSplit[0]) < Integer.parseInt(todayDateSplit[0]) ||
            //        Integer.parseInt(sSplit[1]) < Integer.parseInt(todayDateSplit[1]) ||
            //        (Integer.parseInt(sSplit[2]) < Integer.parseInt(todayDateSplit[2]) &&
            //                Integer.parseInt(sSplit[1]) == Integer.parseInt(todayDateSplit[1])))
            //{
            mydb.deleteTable(s);
            //}
        }
    }

    public static void createNeedTables(ArrayList<String> needDates) throws Exception
    {
        ArrayList<String> cities = Parametres.cities;

        for (String s: needDates)
        {
                DBHelper db = new DBHelper();
                db.tableName = s;

                //Users users = new Users();
            DBHelper.SingleDB query = db.new SingleDB(context);
            query.getReadableDatabase();

            for (String s1: cities) {
                //Log.i("Data", s1);

                HashMap<String, Weather> mapCities = GetWeatherData.getAllDataNextDays(s1);

                //date weather by city

                //iterate by dates
                for (HashMap.Entry<String, Weather> entry : mapCities.entrySet()) {
                    Weather wth = entry.getValue(); //weather

                    //Log.i("Instanc", tablesInst.get(entry.getKey()).toString());
                    //Log.i("Instanc1", tablesInst.entrySet().toString());

                    //query.insertWeatherData(s, wth.temp, wth.pressure,
                    //        wth.humidity, wth.clouds, wth.wind);

                    tablesInst.put(db.tableName, db.new SingleDB(context));
                    tablesInst.get(db.tableName).getWritableDatabase();
                    tablesInst.get(db.tableName).insertWeatherData(s1, wth.temp, wth.pressure,
                                    wth.humidity, wth.clouds, wth.wind);

                }
            }
            return;

        }
    }

    public static String getTemperature(String date, String cityName)
    {
        return tablesInst.get("date_" + date).getTemp(cityName);
    }
}

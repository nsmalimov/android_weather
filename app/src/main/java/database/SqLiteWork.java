package database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import workers.GetWeatherData;
import utils.DateWork;
import utils.Parametres;
import utils.Weather;


public class SqLiteWork {
    public static Context context;
    public static DBHelper.SingleDB db;

    public static void createUpdateBase(Context contextGet) throws Exception {
        context = contextGet;
        context.deleteDatabase("WeatherDB.db");
        ArrayList<String> needDates = DateWork.getDateRange();
        createUpdateTables(needDates);
    }

    public static void createUpdateTables(ArrayList<String> needDates) throws Exception {
        ArrayList<String> cities = Parametres.cities;

        db = new DBHelper(needDates).new SingleDB(context);

        HashMap<String, Weather> mapCities = null;

        Log.i("City", cities.toString());
        Log.i("Dates", needDates.toString());

        for (String cityName : cities) {

            Log.i("DDD", needDates.toString());
            try {
                mapCities = GetWeatherData.getAllDataNextDays(cityName);
            } catch (Exception e) {
                Log.i("SSS", e.toString());
            }


            for (HashMap.Entry<String, Weather> entry : mapCities.entrySet()) {
                Weather wth = entry.getValue(); //weather
                db.insertWeatherData(entry.getKey(), cityName, wth.temp, wth.pressure,
                        wth.humidity, wth.clouds, wth.wind);
            }
        }
    }

    public static String getTemperature(String date, String cityName) {
        try {
            return db.getTemp("date_" + date, cityName);
        } catch (Exception e) {
            return "no data";
        }
    }

    public static HashMap<String, String> getAllData(String date, String cityName) {
        try {
            return db.getAllData("date_" + date, cityName);
        } catch (Exception e) {
            return null;
        }
    }
}

package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper {

    public ArrayList<String> tableNameArray;

    public DBHelper(ArrayList<String> tableNameArray) {
        this.tableNameArray = tableNameArray;
    }

    public class SingleDB extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "WeatherDB2.db";

        public SingleDB(Context context) {
            super(context, DATABASE_NAME, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            for (String s : tableNameArray) {
                db.execSQL(
                        "create table " + s + " " +
                                "(id integer primary key, city text,temp text,pressure text, humidity text," +
                                "clouds text,wind text)"
                );
                Log.i("Table", s);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
        }

        public void insertWeatherData(String tableName, String city, String temp, String pressure,
                                      String humidity, String clouds, String wind) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("city", city);
            contentValues.put("temp", temp);
            contentValues.put("pressure", pressure);
            contentValues.put("humidity", humidity);
            contentValues.put("clouds", clouds);
            contentValues.put("wind", wind);

            Log.i("DATA", tableName + " " + city + " " + temp + " " + wind);

            db.insert(tableName, null, contentValues);
        }

        public String getTemp(String tableName, String cityName) {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT temp FROM " + tableName + " WHERE city='" + cityName + "'";
            Cursor c = db.rawQuery(selectQuery, null);
            String temperature = "No";
            if (c.moveToFirst()) {
                temperature = c.getString(c.getColumnIndex("temp"));
            }
            c.close();
            return temperature;
        }

        public HashMap<String, String> getAllData(String tableName, String cityName) {
            HashMap<String, String> dataMap = new HashMap<String, String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + tableName + " WHERE city='" + cityName + "'";
            Cursor res = db.rawQuery(selectQuery, null);
            res.moveToFirst();

            while (!res.isAfterLast()) {
                dataMap.put("city", res.getString(res.getColumnIndex("city")));
                dataMap.put("temp", res.getString(res.getColumnIndex("temp")));
                dataMap.put("pressure", res.getString(res.getColumnIndex("pressure")));
                dataMap.put("humidity", res.getString(res.getColumnIndex("humidity")));
                dataMap.put("clouds", res.getString(res.getColumnIndex("clouds")));
                dataMap.put("wind", res.getString(res.getColumnIndex("wind")));
                res.moveToNext();
            }
            return dataMap;
        }
    }
}
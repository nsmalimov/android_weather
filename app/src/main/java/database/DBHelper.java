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

    public String tableName = "";
    public boolean needCreate = false;

    public class SingleDB extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "WeatherDB.db";

        public String weatherTableName = tableName;

        public SingleDB(Context context) {
            super(context, DATABASE_NAME, null, 2);
            context.deleteDatabase(DATABASE_NAME);
            //super(context, DATABASE_NAME, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            //if (needCreate) {

                db.execSQL(
                        "create table " + weatherTableName + " " +
                                "(id integer primary key, city text,temp text,pressure text, humidity text," +
                                "clouds text,wind text)"
                );
            //Log.i("Table", weatherTableName);
            //}
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + weatherTableName);

            onCreate(db);
        }

        public void deleteTable(String tableName) {
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }

        public void insertWeatherData(String city, String temp, String pressure,
                                     String humidity, String clouds, String wind) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("city", city);
            contentValues.put("temp", temp);
            contentValues.put("pressure", pressure);
            contentValues.put("humidity", humidity);
            contentValues.put("clouds", clouds);
            contentValues.put("wind", wind);
            db.insert(weatherTableName, null, contentValues);
            //return true;
        }

        public String getTemp(String cityName) {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT temp FROM " + weatherTableName + " WHERE city='" + cityName + "'";
            Cursor c = db.rawQuery(selectQuery, null);
            String temp = "No";
            if (c.moveToFirst()) {
                temp = c.getString(c.getColumnIndex("temp"));
            }
            c.close();
            return temp;
        }

        public HashMap<String, String> getAllData(String cityName) {
            HashMap<String, String> dataMap = new HashMap<String, String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + weatherTableName + " WHERE city='" + cityName + "'";
            Cursor res = db.rawQuery(selectQuery, null);
            res.moveToFirst();

            while (!res.isAfterLast()) {
                dataMap.put("city", res.getString(res.getColumnIndex("city")));
                dataMap.put("temp", res.getString(res.getColumnIndex("temp")));
                dataMap.put("pressure", res.getString(res.getColumnIndex("pressure")));
                dataMap.put("humidity", res.getString(res.getColumnIndex("humidity")));
                dataMap.put("clouds", res.getString(res.getColumnIndex("clouds")));
                dataMap.put("wind", res.getString(res.getColumnIndex("wind")));
                ;
                res.moveToNext();
            }
            return dataMap;
        }

        public ArrayList<String> getAllTables() {
            ArrayList<String> arrTblNames = new ArrayList<String>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

            if (c.moveToFirst()) {
                while (!c.isAfterLast()) {
                    String tableName = c.getString(c.getColumnIndex("name"));
                    String[] tableNameSplit = tableName.split("-");

                    if (tableNameSplit.length == 3) {
                        arrTblNames.add(tableName);
                    }
                    c.moveToNext();
                }
            }

            return arrTblNames;
        }
    }
}
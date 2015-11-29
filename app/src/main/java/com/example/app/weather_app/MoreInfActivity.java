package com.example.app.weather_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

import database.DBHelper;
import database.SqLiteWork;
import utils.Parametres;

public class MoreInfActivity extends AppCompatActivity {

    TextView myTextViewTemp;
    TextView myTextViewPressure;
    TextView myTextViewHumidity;
    TextView myTextViewClouds;
    TextView myTextViewWind;

    TextView myTextViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_inf);

        myTextViewTemp = (TextView) findViewById(R.id.textTemp);
        myTextViewPressure = (TextView) findViewById(R.id.textPressure);
        myTextViewHumidity = (TextView) findViewById(R.id.textHumidity);
        myTextViewClouds = (TextView) findViewById(R.id.textClouds);
        myTextViewWind = (TextView) findViewById(R.id.textWind);

        myTextViewDate = (TextView) findViewById(R.id.textViewDate);

        HashMap<String, String> hm = SqLiteWork.getAllData(Parametres.needDate, Parametres.cityName);

        if (hm == null)
        {
            return;
        }

        //TODO вызов temp
        String temp = myTextViewTemp.getText() + " " + SqLiteWork.getTemperature(Parametres.needDate, Parametres.cityName) + " °C";
        String pressure = myTextViewPressure.getText() + " " + hm.get("pressure") + " мм. рт. ст.";
        String humidity = myTextViewHumidity.getText() + " " + hm.get("humidity") + " %";
        String clouds = myTextViewClouds.getText() + " " + hm.get("clouds") + " %";
        String wind = myTextViewWind.getText() + " " + hm.get("wind") + " м/с";

        myTextViewTemp.setText(temp);
        myTextViewPressure.setText(pressure);
        myTextViewHumidity.setText(humidity);
        myTextViewClouds.setText(clouds);
        myTextViewWind.setText(wind);

        myTextViewDate.setText(Parametres.needDate);
    }
}

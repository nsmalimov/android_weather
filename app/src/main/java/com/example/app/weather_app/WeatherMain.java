package com.example.app.weather_app;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database.SqLiteWork;
import utils.Parametres;

public class WeatherMain extends Activity implements View.OnClickListener{

    TextView myTextViewCity;
    TextView myTextViewTemp;

    Button buttonMoreInf;
    Button buttonPredictWeather;

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        myTextViewCity = (TextView) findViewById(R.id.textCityName);
        myTextViewTemp = (TextView) findViewById(R.id.textTemperature);

        myTextViewCity.setText(Parametres.cityName);

        String temp = SqLiteWork.getTemperature(Parametres.todayDate, Parametres.cityName) + "°C";
        myTextViewTemp.setText(temp);

        buttonMoreInf = (Button) findViewById(R.id.moreInfBut);
        buttonMoreInf.setOnClickListener(this);

        buttonPredictWeather = (Button) findViewById(R.id.predictBut);
        buttonPredictWeather.setOnClickListener(this);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moreInfBut:
                Intent intent = new Intent(this, MoreInfActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            case R.id.predictBut:
                calendarView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

}

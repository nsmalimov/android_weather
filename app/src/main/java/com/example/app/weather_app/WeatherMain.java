package com.example.app.weather_app;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        myTextViewCity = (TextView) findViewById(R.id.textCityName);
        myTextViewTemp = (TextView) findViewById(R.id.textTemperature);

        myTextViewCity.setText(Parametres.cityName);
        myTextViewTemp.setText(SqLiteWork.getTemperature(Parametres.todayDate, Parametres.cityName) + "°C");
    }

    @Override
    public void onClick(View v) {

    }

}

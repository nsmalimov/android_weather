package com.example.app.weather_app;

import android.app.Activity;
import android.content.Context;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import database.SqLiteWork;
import utils.Parametres;

public class WeatherMain extends Activity implements View.OnClickListener{

    TextView myTextViewCity;
    TextView myTextViewTemp;

    private static MaterialDialog dateDialog;

    Button buttonMoreInf;
    Button buttonPredictWeather;

    Context context;

    private static MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        context = this;

        setDatePicker();

        myTextViewCity = (TextView) findViewById(R.id.textCityName);
        myTextViewTemp = (TextView) findViewById(R.id.textTemperature);

        myTextViewCity.setText(Parametres.cityName);

        String temp = SqLiteWork.getTemperature(Parametres.todayDate, Parametres.cityName) + "°C";
        if (temp.equals("no data"))
            temp = "no data";

        myTextViewTemp.setText(temp);

        buttonMoreInf = (Button) findViewById(R.id.moreInfBut);
        buttonMoreInf.setOnClickListener(this);

        buttonPredictWeather = (Button) findViewById(R.id.predictBut);
        buttonPredictWeather.setOnClickListener(this);
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
                onDatePickerClick(v);
                break;
            default:
                break;
        }
    }

    private void setDatePicker() {
        boolean wrapInScrollView = false;
        dateDialog = new MaterialDialog.Builder(this)
                .title("Выбор даты")
                .customView(R.layout.date_dialog, wrapInScrollView)
                .positiveText("Ok")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        CalendarDay calendarDay = calendarView.getSelectedDate();

                        if (calendarDay != null)
                            parseCalendarDay(calendarDay);
                            Intent intent = new Intent(context, MoreInfActivity.class);
                            startActivity(intent);
                    }
                })
                .build();

        calendarView = (MaterialCalendarView) dateDialog.findViewById(R.id.calendarView);

        Calendar currentDate = Calendar.getInstance();

        calendarView.setSelectedDate(currentDate);

        calendarView.setMinimumDate(currentDate);
        CalendarDay currentCalendarDaySome = new CalendarDay(currentDate);
        currentDate.add(Calendar.DATE, 4);

        calendarView.setMaximumDate(currentDate);

        parseCalendarDay(currentCalendarDaySome);
    }

    private void parseCalendarDay(CalendarDay calendarDay) {

        int day = calendarDay.getDay();
        int month = calendarDay.getMonth();
        int year = calendarDay.getYear();

        String yearStr = Integer.toString(year);
        String monthStr = Integer.toString(month + 1);
        String dayStr = Integer.toString(day);

        if (month < 10)
            monthStr = "0" + monthStr;
        if (day < 10)
            dayStr = "0" + dayStr;


        String answer = yearStr + "_" + monthStr + "_" + dayStr;

        Parametres.needDate = answer;
    }


    public void onDatePickerClick(View v) {
        dateDialog.show();
    }
}

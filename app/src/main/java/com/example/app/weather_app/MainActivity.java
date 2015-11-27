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
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.Parametres;

public class MainActivity extends Activity implements View.OnClickListener {

    Button buttonAddCity;
    ListView lvMain;

    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    EditText mEdit;

    String[] cities = {"Санкт-Петербург", "Москва"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMain = (ListView) findViewById(R.id.lvCity);

        buttonAddCity = (Button) findViewById(R.id.addCityBut);

        buttonAddCity.setOnClickListener(this);

        listItems = new ArrayList<String>(Arrays.asList(cities));
        mEdit = (EditText)findViewById(R.id.cityEditText);

        // создаем адаптер
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String checkedCity = adapter.getItem(position);
                Parametres.cityName = checkedCity;
                Log.i("Checked city", checkedCity);

                Intent intent = new Intent(MainActivity.this, WeatherMain.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        listItems.add(mEdit.getText().toString());
        adapter.notifyDataSetChanged();
        mEdit.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}

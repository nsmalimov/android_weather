package com.example.app.weather_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.ProgressBar;

import java.util.ArrayList;

import utils.DateWork;
import utils.Parametres;
import MainWorkers.MainBackGround;

public class MainActivity extends Activity implements View.OnClickListener {

    final String SAVED_TEXT = "";

    Button buttonAddCity;
    ListView lvMain;
    ProgressBar progressBar;

    ArrayAdapter<String> adapter;
    EditText mEdit;

    SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMain = (ListView) findViewById(R.id.lvCity);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        buttonAddCity = (Button) findViewById(R.id.addCityBut);

        buttonAddCity.setOnClickListener(this);

        Parametres.cities = loadCitiesApp();
        mEdit = (EditText)findViewById(R.id.cityEditText);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Parametres.cities);

        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Parametres.isInternet = isOnline();

                String checkedCity = adapter.getItem(position);
                Parametres.cityName = checkedCity;
                Parametres.todayDate = DateWork.todayDate();

                Log.i("Checked city", checkedCity);

                if (Parametres.isInternet) {
                    new MainBackGround(MainActivity.this, progressBar).execute();
                    Log.i("Internet", "yes");
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, WeatherMain.class);
                    startActivity(intent);
                    Log.i("Internet", "no");
                }
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
        Parametres.cities.add(mEdit.getText().toString());
        adapter.notifyDataSetChanged();

        saveCitiesApp(Parametres.cities);
        Parametres.needUpdate = true;

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

    public ArrayList<String> loadCitiesApp()
    {
        ArrayList<String> citiesArray = new ArrayList<String>();

        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");

        if (!savedText.equals("")) {
            String[] cities = savedText.split(",");

            for (String s : cities) {
                citiesArray.add(s);
            }
        }

        if (!citiesArray.contains("Санкт-Петербург"))
            citiesArray.add("Санкт-Петербург");
        if (!citiesArray.contains("Москва"))
            citiesArray.add("Москва");

        return citiesArray;
    }

    public void saveCitiesApp(ArrayList<String> cities)
    {
        StringBuilder toSaveString = new StringBuilder();

        for (String e: cities)
        {
            toSaveString.append(e);
            toSaveString.append(",");
        }

        toSaveString.deleteCharAt(toSaveString.length() - 1);

        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, toSaveString.toString());
        ed.apply();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}

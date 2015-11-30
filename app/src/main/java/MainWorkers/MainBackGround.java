package MainWorkers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.app.weather_app.R;
import com.example.app.weather_app.WeatherMain;

import database.SqLiteWork;
import utils.Parametres;

public class MainBackGround extends AsyncTask<Void, Integer, Void> {

    ProgressBar progressBar;

    Context context;
    //constructor
    public MainBackGround(Context context, ProgressBar progressBar) {
        this.context = context.getApplicationContext();
        this.progressBar = progressBar;
    }

    //befor main start work
    @Override
    protected void onPreExecute() {

        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    //start main work
    @Override
    protected Void doInBackground(Void... params)  {
        try {
            Log.v("Start", Boolean.toString(Parametres.needUpdate));
                SqLiteWork.createUpdateBase(context);
        }
        catch (Exception e)
        {
            Log.i("Error Async", e.toString());
        }

        return null;
    }

    //while main task work
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    //end of work
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        Intent intent = new Intent(context, WeatherMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        progressBar.setVisibility(View.INVISIBLE);
        context.startActivity(intent);
    }
}

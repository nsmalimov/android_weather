package MainWorkers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.app.weather_app.WeatherMain;

public class MainBackGround extends AsyncTask<Void, Integer, Void> {

    int progress_status;

    Context context;
    public MainBackGround(Context context) {
        this.context = context.getApplicationContext();
    }


    @Override
    protected void onPreExecute() {
        // обновляем пользовательский интерфейс сразу после выполнения задачи
        super.onPreExecute();

        progress_status = 0;
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        context.startActivity(new Intent(context, WeatherMain.class));

    }
}

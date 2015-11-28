package MainWorkers;

import android.provider.SyncStateContract;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import utils.Constants;
import utils.Weather;

import com.google.gson.*;

import java.util.HashMap;

public class GetWeatherData {
    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static HashMap<String, Weather> getAllDataNextDays(String city) throws Exception{

        HashMap<String, Weather> hm = new HashMap<String, Weather>();
        //get for 5 days
        String json = readUrl(Constants.apiWeatherUrl + "data/2.5/forecast?q=" + city +
                "&mode=json&appid=" + Constants.apiKey);

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        JsonArray ob = jsonObject.get("list").getAsJsonArray();

        for (JsonElement ob1: ob)
        {
            Weather wth = new Weather();

            JsonObject jsonObj = ob1.getAsJsonObject();

            String timeStr = jsonObj.get("dt_txt").toString();

            timeStr = timeStr.split(" ")[0].replace("\"", "");

            if (!hm.containsKey(timeStr)) {
                JsonElement jsonObjNew = jsonObj.get("main");
                wth.temp = jsonObjNew.getAsJsonObject().get("temp").toString();
                wth.temp = Integer.toString((int)((Double.parseDouble(wth.temp) - 274.15)));
                wth.pressure = jsonObjNew.getAsJsonObject().get("pressure").toString();
                wth.humidity = jsonObjNew.getAsJsonObject().get("humidity").toString();
                wth.clouds = jsonObj.get("clouds").getAsJsonObject().get("all").toString();
                wth.wind = jsonObj.get("wind").getAsJsonObject().get("speed").toString();

                hm.put(timeStr, wth);
            }
        }

        return hm;
    }

}


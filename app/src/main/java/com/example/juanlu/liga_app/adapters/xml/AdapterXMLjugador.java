package com.example.juanlu.liga_app.adapters.xml;
import com.example.juanlu.liga_app.Jugador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by juanlu on 15/02/16.
 */
public class AdapterXMLjugador implements Callable<Jugador> {

    private String id;
    private URL url;
    public String ruta = null;

    public AdapterXMLjugador(String id) {
        this.id = id;
        ruta = "http://www.resultados-futbol.com/scripts/api/api.php?tz=Europe/Madrid&format=json&req=player&key=07970ab46cab577a2aa761be7a21098f&id="+id;
    }

    @Override
    public Jugador call() throws Exception {
        String age, country, weight, height, minutes_played = null, assists = null, goals = null, player_id;
        Jugador jugador = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(ruta.replaceAll("\\n", ""));
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject parenObject = new JSONObject(finalJson);
            player_id = parenObject.getString("player_id");
            country = parenObject.getString("country");
            age = parenObject.getString("age");
            weight = parenObject.getString("weight");
            height = parenObject.getString("height");
            String statistics_resume = parenObject.getString("statistics_resume");
            statistics_resume = "{\"statistics_resume\":" + statistics_resume.substring(0, statistics_resume.indexOf("}") + 1) + "]}";
            JSONObject statisticsObject = new JSONObject(statistics_resume);
            JSONArray parentArray = statisticsObject.getJSONArray("statistics_resume");
            for (int idx = 0; idx < parentArray.length(); idx++) {
                JSONObject finalObject = parentArray.getJSONObject(idx);
                minutes_played = finalObject.getString("minutes_played");
                assists = finalObject.getString("assists");
                goals = finalObject.getString("goals");
            }
            jugador = new Jugador(player_id, age, goals, assists, minutes_played, weight, height, country);
        } catch(java.io.FileNotFoundException e){
            return jugador = new Jugador("-", "-" , "-", "-", "-", "-", "-", "-");
        } catch (MalformedURLException e) {
           return jugador = new Jugador("-", "-" , "-", "-", "-", "-", "-", "-");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jugador;
    }
}

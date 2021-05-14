package com.example.juanlu.liga_app.adapters.json;

import android.os.AsyncTask;
import android.widget.Button;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanlu on 17/12/15.
 */
public class AdapterJSONclasificacion {

    static List <String> idEquipo = new ArrayList<>();
    static List <String> posicion = new ArrayList<>();
    static List <String> partidos_jugados = new ArrayList<>();
    static List <String> equipo = new ArrayList<>();
    static List <String> puntos = new ArrayList<>();
    static List <String> wins = new ArrayList<>();
    static List <String> draws = new ArrayList<>();
    static List <String> losses = new ArrayList<>();
    static List <String> gf = new ArrayList<>();
    static List <String> gc = new ArrayList<>();
    static List <String> avg = new ArrayList<>();
    private Button button;

    public AdapterJSONclasificacion( Button button ){
        this.button = button;
        limpiarlistas();
        idEquipo.add("id");
        posicion.add("pos");
        partidos_jugados.add("PJ");
        equipo.add("Eq");
        puntos.add("P");
        wins.add("V");
        draws.add("E");
        losses.add("Per");
        gf.add("gf");
        gc.add("gc");
        avg.add("avg");
        new JSONTask().execute("http://www.resultados-futbol.com/scripts/api/api.php?tz=Europe/Madrid&format=json&req=tables&key=259bcb52ae9c48daae34c0cbef30f244&league=1&group=1");
    }

    public void limpiarlistas(){
        idEquipo.clear();
        posicion.clear();
        partidos_jugados.clear();
        equipo.clear();
        puntos.clear();
        wins.clear();
        draws.clear();
        losses.clear();
        gf.clear();
        gc.clear();
        avg.clear();
    }
    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parenObject = new JSONObject(finalJson);
                JSONArray parentArray = parenObject.getJSONArray("table");

                int posicionEquipo = 1;
                StringBuffer finalBufferedData = new StringBuffer();
                for (int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    idEquipo.add(finalObject.getString("id"));
                    posicion.add(new String(posicionEquipo+""));
                    equipo.add(finalObject.getString("team"));
                    puntos.add(finalObject.getString("points"));
                    wins.add(finalObject.getString("wins"));
                    int win = Integer.parseInt(finalObject.getString("wins"));
                    draws.add(finalObject.getString("draws"));
                    int draw = Integer.parseInt(finalObject.getString("draws"));
                    losses.add(finalObject.getString("losses"));
                    int losse = Integer.parseInt(finalObject.getString("losses"));
                    partidos_jugados.add(win+draw+losse+"");
                    gf.add(finalObject.getString("gf"));
                    gc.add(finalObject.getString("ga"));
                    avg.add(finalObject.getString("avg"));

                    posicionEquipo++;
                }

                return finalBufferedData.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            button.setEnabled(true);
        }
    }
    public List getidEquipo() {
        return this.idEquipo;
    }
    public List getPosicion() {
        return this.posicion;
    }
    public List getPartidosJugados() { return this.partidos_jugados;}
    public List getEquipo() {
        return this.equipo;
    }
    public List getPuntos() {
        return this.puntos;
    }
    public List getWins() {
        return this.wins;
    }
    public List getDraws() {
        return this.draws;
    }
    public List getLosses() {
        return this.losses;
    }
    public List getGF() {
        return this.gf;
    }
    public List getGC() {
        return this.gc;
    }
    public List getAVG() {
        return this.avg;
    }
}

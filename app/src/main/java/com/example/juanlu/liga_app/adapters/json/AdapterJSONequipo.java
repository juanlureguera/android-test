package com.example.juanlu.liga_app.adapters.json;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import com.example.juanlu.liga_app.Equipo;
import com.example.juanlu.liga_app.activities.DetallesEquipo;

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

/**
 * Created by juanlu on 18/02/16.
 */
public class AdapterJSONequipo {

    public View v;
    private String idEquipo;
    private boolean valido = true;
    private Context context;
    private String posicion;

    public AdapterJSONequipo(String row, View v, String posicion) {
        this.posicion = posicion;
        this.idEquipo = row;
        this.v = v;
        new JSONTask().execute("http://www.resultados-futbol.com/scripts/api/api.php?tz=Europe/Madrid&format=json&req=team&key=07970ab46cab577a2aa761be7a21098f&id="+idEquipo);
    }

    public AdapterJSONequipo(String row, Context context, String posicion) {
        this.posicion = posicion;
        this.idEquipo = row;
        this.context = context;
        valido = false;
        new JSONTask().execute("http://www.resultados-futbol.com/scripts/api/api.php?tz=Europe/Madrid&format=json&req=team&key=07970ab46cab577a2aa761be7a21098f&id="+idEquipo);
    }

    public class JSONTask extends AsyncTask<String, String, Equipo> {

        private Equipo eq = null;
        private URL url;

        @Override
        protected Equipo doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                url = new URL(params[0]);
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
                String parte1 = finalJson.substring(0, finalJson.indexOf(":") + 1) + "[";
                String parte2 = finalJson.substring(finalJson.indexOf(":") + 1, finalJson.indexOf("}") + 1) + "}]}";

                finalJson = parte1 + parte2;
                JSONObject parenObject = new JSONObject(finalJson);
                JSONArray parentArray = parenObject.getJSONArray("team");

                for (int i = 0; i < parentArray.length(); i++) {
                    //nombre, pais, estadio, nombreCorto, entrenador,web, fundacion, ciudad, direccion, presidente, nombreCompleto, historial, color1, color2, escudo, imgestadio
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    String nombre = finalObject.getString("nameShow");
                    String estadio = finalObject.getString("stadium");
                    String nombreCorto = finalObject.getString("short_name");
                    String entrenador = finalObject.getString("managerNow");
                    String web = finalObject.getString("website");
                    String fundacion = finalObject.getString("yearFoundation");
                    String ciudad = finalObject.getString("city");
                    String direccion = finalObject.getString("address");
                    String presidente = finalObject.getString("chairman");
                    String nombreCompleto = finalObject.getString("fullName");
                    String historial = finalObject.getString("historical");
                    String escudo = finalObject.getString("shield");
                    String estadio_img = finalObject.getString("img_stadium");
                    String category = finalObject.getString("category");
                    JSONObject paren = new JSONObject(category);
                    String team_id = paren.getString("team_id");

                    eq = new Equipo(posicion, nombre, estadio, nombreCorto, entrenador,web, fundacion, ciudad, direccion, presidente, nombreCompleto, historial, escudo, estadio_img, team_id);
                }
            } catch(java.io.FileNotFoundException e){
                return eq = new Equipo("-","-", "-", "-", "-","-", "-", "-", "-", "-", "-", "-", "-", "-", "-");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onPostExecute(Equipo result) {
            super.onPostExecute(result);
            if( valido ){
                Intent act = new Intent(v.getContext(), DetallesEquipo.class);
                act.putExtra("ClaseEquipo", eq);
                v.getContext().startActivity(act);
            }
            else{
                Intent act = new Intent(context, DetallesEquipo.class);
                act.putExtra("ClaseEquipo", eq);
                context.startActivity(act);
            }
        }
    }
}

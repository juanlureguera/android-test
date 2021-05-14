package com.example.juanlu.liga_app.adapters.xml;

import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.widget.Button;
import android.widget.TextView;

import com.example.juanlu.liga_app.activities.DetallesEquipo;
import com.example.juanlu.liga_app.Jugador;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by juanlu on 15/02/16.
 */
public class AdapterXMLjugadores {

    List<Jugador> jugadores = new ArrayList<>();
    List<Jugador> jugadores_detalles = new ArrayList<>();
    private String team_id;
    private String ruta;
    private URL url;
    private Button button;


    public AdapterXMLjugadores(String team_id, Button button) {
        this.team_id = team_id;
        this.button = button;
        ruta = "http://www.resultados-futbol.com/scripts/api/api.php?tz=Europe/Madrid&format=xml&req=team_players&key=07970ab46cab577a2aa761be7a21098f&team="+team_id;
        new JSONTask().execute(ruta);
    }

    public class JSONTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            Jugador jugador = null;
            HttpURLConnection connection = null;

            try {
                String dorsal = null, nombre = null, role = null, id = null;
                url = new URL(ruta);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream stream = connection.getInputStream();

                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document xmlDocument = documentBuilder.parse(stream);
                //Obtener el elemento raíz del documento
                Element raiz = xmlDocument.getDocumentElement();
                NodeList listaJugadores = raiz.getElementsByTagName("player");
                for(int idx=0; idx<listaJugadores.getLength(); idx++) {
                    Node player = listaJugadores.item(idx);
                    NodeList datosJugador = player.getChildNodes();
                    for (int idy = 0; idy < datosJugador.getLength(); idy++) {
                        Node dato = datosJugador.item(idy);
                        if (dato.getNodeType() == Node.ELEMENT_NODE) {
                            if (dato.getNodeName().equals("id")) {
                                id = dato.getTextContent().toString();
                            }
                            if (dato.getNodeName().equals("nick")) {
                                nombre = dato.getTextContent().toString();
                            }
                            if (dato.getNodeName().equals("role")) {
                                role = dato.getTextContent().toString();
                            }
                            if (dato.getNodeName().equals("squadNumber")) {
                                dorsal = dato.getTextContent().toString();
                            }
                        }
                    }
                    jugadores.add(jugador = new Jugador(id, dorsal, nombre, role));
                }
                List<AdapterXMLjugador> lista_tareas = new ArrayList<>();
                ExecutorService executor = (ExecutorService) Executors.newFixedThreadPool(7);
                for(Jugador j : jugadores){
                    String ids = j.getId().toString();
                    AdapterXMLjugador tarea = new AdapterXMLjugador( ids );
                    lista_tareas.add(tarea);
                }
                List <Future<Jugador>> listaResultados = null;
                try {
                    listaResultados = executor.invokeAll(lista_tareas);

                } catch (InterruptedException ex) {
                    Logger.getLogger(DetallesEquipo.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Termina el Executor
                executor.shutdown();
                for (int i = 0; i < listaResultados.size(); i++) {
                    Future <Jugador> resultado = listaResultados.get( i );

                    try {
                        jugadores_detalles.add(resultado.get());
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(DetallesEquipo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            button.setEnabled(true);
        }
    }
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public List<Jugador> getJugadores_detalles() {
        return jugadores_detalles;
    }
}

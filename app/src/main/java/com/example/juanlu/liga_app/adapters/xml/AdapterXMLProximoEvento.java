package com.example.juanlu.liga_app.adapters.xml;

import android.os.AsyncTask;
import android.widget.Button;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by juanlu on 15/02/16.
 */
public class AdapterXMLProximoEvento {
    static String [ ] fecha_partido = new String [ 2 ];
    private URL url;
    public List<String> listalocal = new ArrayList<>();
    public List <String> listavisitor = new ArrayList<>();
    public List <String> listaround = new ArrayList<>();
    public List <String> listacompeticion = new ArrayList<>();
    public List <String> listaEscudoL = new ArrayList<>();
    public List <String> listaEscudoV = new ArrayList<>();
    public List <String> listaDate = new ArrayList<>();

    private Button button;

    public AdapterXMLProximoEvento( Button button ){
        this.button = button;
        Calendar now = Calendar.getInstance();


        int iYear = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH)+1; // Note: zero based!
        int iday = now.get(Calendar.DAY_OF_MONTH);

        // Create a calendar object and set year and month
        Calendar mycal = new GregorianCalendar(iYear, month, iday);
        // Get the number of days in that month
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        recorreDias(iday, iYear, month, daysInMonth);
        if( fecha_partido [ 0 ] == null ){
            if( month == 12 ){
                month = 1;
                iYear = iYear+1;
            }
            else{
                month = month+1;
            }
            mycal = new GregorianCalendar(iYear, month, iday);

            daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
            recorreDias(1, iYear, month, daysInMonth);
            fecha_partido = recorreDias( iday, iYear, month, daysInMonth );
        }
        for(String f : fecha_partido){
            new JSONTask().execute("http://www.resultados-futbol.com/scripts/api/api.php?tz=Europe/Madrid&format=xml&req=tv_matches&key=07970ab46cab577a2aa761be7a21098f&media=&date="+f);
        }

    }

    public class JSONTask extends AsyncTask<String, String, String> {
        String local = null, visitor = null, round = null, competicion = null, escudoL = null, escudoV = null, date = null;
        HttpURLConnection connection = null;
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream stream = connection.getInputStream();

                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document xmlDocument = documentBuilder.parse(stream);
                //Obtener el elemento raíz del documento
                Element raiz = xmlDocument.getDocumentElement();
                NodeList listaJugadores = raiz.getElementsByTagName("matches");
                for(int idx=0; idx<listaJugadores.getLength(); idx++) {
                    Node player = listaJugadores.item(idx);
                    NodeList datosPartido = player.getChildNodes();
                    for (int idy = 0; idy < datosPartido.getLength(); idy++) {
                        Node dato = datosPartido.item(idy);
                        if (dato.getNodeName().equals("round")) {
                            round = dato.getTextContent().toString();
                        }
                        if (dato.getNodeName().equals("local")) {
                            local = dato.getTextContent().toString();
                        }
                        if (dato.getNodeName().equals("visitor")) {
                            visitor = dato.getTextContent().toString();
                        }
                        if(dato.getNodeName().equals("competition_name")){
                            competicion = dato.getTextContent().toString();
                        }
                        if(dato.getNodeName().equals("local_shield")){
                            escudoL = dato.getTextContent().toString();
                        }
                        if(dato.getNodeName().equals("visitor_shield")){
                            escudoV = dato.getTextContent().toString();
                        }
                        if(dato.getNodeName().equals("date")){
                            date = dato.getTextContent().toString();
                        }

                    }
                    if(competicion.trim().equals("Liga BBVA")){
                        listaround.add(round);
                        listalocal.add(local);
                        listavisitor.add(visitor);
                        listaEscudoL.add(escudoL);
                        listaEscudoV.add(escudoV);
                        listaDate.add(date);
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
    public static String [ ] recorreDias(int iday, int iYear, int month, int daysInMonth){
        for( int idx = iday; idx < daysInMonth; idx ++){
            String dia = getDiaSemana(iYear + "-" + month + "-" + idx);
            String diac = null;
            String mes = null;
            if( dia.equals("Sabado") ){
                if( idx < 10 ){
                    diac = "0"+idx;
                }
                else{
                    diac = ""+idx;
                }
                if( month < 10){
                    mes = "0"+month;
                }
                else{
                    mes = ""+month;
                }
                fecha_partido [ 0 ] = iYear+"-"+mes+"-"+diac;
                int dia2 = Integer.parseInt(diac)+1;
                String dia3 = null;
                if( dia2 < 10){
                    dia3 = "0"+(dia2);
                }
                else{
                    dia3 = ""+dia2;
                }
                if( dia2 > daysInMonth && month < 12){
                    fecha_partido [ 1 ] = iYear+"-"+0+(mes+1)+"-01";
                }
                else if( dia2 > daysInMonth && month >= 12 ){
                    fecha_partido [ 1 ] = (iYear+1)+"-01-01";
                }
                else{
                    fecha_partido [ 1 ] = iYear+"-"+mes+"-"+dia3;
                }
                break;
            }
        }
        return fecha_partido;
    }
    public static String getDiaSemana(String fecha) {
        String Valor_dia = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);
        switch (diaSemana) {
            case 1:
                Valor_dia = "Domingo";
                break;
            case 2:
                Valor_dia = "Lunes";
                break;
            case 3:
                Valor_dia = "Martes";
                break;
            case 4:
                Valor_dia = "Miercoles";
                break;
            case 5:
                Valor_dia = "Jueves";
                break;
            case 6:
                Valor_dia = "Viernes";
                break;
            case 7:
                Valor_dia = "Sabado";
                break;
            default:
                break;
        }
        return Valor_dia;
    }
    public List<String> getListaround() {
        return listaround;
    }
    public List<String> getListavisitor() {
        return listavisitor;
    }
    public List<String> getListalocal() {
        return listalocal;
    }
    public List<String> getListacompeticion() {
        return listacompeticion;
    }
    public List<String> getListaEscudoV() {
        return listaEscudoV;
    }
    public List<String> getListaEscudoL() {
        return listaEscudoL;
    }

    public List<String> getListaDate() {
        return listaDate;
    }
}
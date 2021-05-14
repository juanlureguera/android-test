package com.example.juanlu.liga_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.juanlu.liga_app.R;
import com.example.juanlu.liga_app.adapters.json.AdapterJSONclasificacion;
import com.example.juanlu.liga_app.adapters.xml.AdapterXMLProximoEvento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements Serializable {

    static AdapterJSONclasificacion adj = null;
    static AdapterXMLProximoEvento pe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button buttonCl = (Button) findViewById(R.id.btnTabla);
        Button buttonPe = (Button) findViewById(R.id.buttonPP);
        adj = new AdapterJSONclasificacion( buttonCl );
        pe = new AdapterXMLProximoEvento( buttonPe );

    }
    public void ir_a_clasificacion (View v) {
        Intent act = new Intent(this, Clasificacion.class);
        act.putStringArrayListExtra("idEquipo", (ArrayList<String>) adj.getidEquipo());
        act.putStringArrayListExtra("Posicion", (ArrayList<String>) adj.getPosicion());
        act.putStringArrayListExtra("Partidos_jugados", (ArrayList<String>) adj.getPartidosJugados());
        act.putStringArrayListExtra("Equipos", (ArrayList<String>) adj.getEquipo());
        act.putStringArrayListExtra("Puntos", (ArrayList<String>) adj.getPuntos());
        act.putStringArrayListExtra("Victorias", (ArrayList<String>) adj.getWins());
        act.putStringArrayListExtra("Empates", (ArrayList<String>) adj.getDraws());
        act.putStringArrayListExtra("Derrotas", (ArrayList<String>) adj.getLosses());
        act.putStringArrayListExtra("GF", (ArrayList<String>) adj.getGF());
        act.putStringArrayListExtra("GC", (ArrayList<String>) adj.getGC());
        act.putStringArrayListExtra("AVG", (ArrayList<String>) adj.getAVG());
        startActivity(act);
    }
    public void ir_a_proximos_eventos (View v) {
        Intent act = new Intent(this, ProximoEvento.class);
        act.putStringArrayListExtra("local", (ArrayList<String>) pe.getListalocal());
        act.putStringArrayListExtra("visitor", (ArrayList<String>) pe.getListavisitor());
        act.putStringArrayListExtra("round", (ArrayList<String>) pe.getListaround());
        act.putStringArrayListExtra("competicion", (ArrayList<String>) pe.getListacompeticion());
        act.putStringArrayListExtra("escudoL", (ArrayList<String>) pe.getListaEscudoL());
        act.putStringArrayListExtra("escudoV", (ArrayList<String>) pe.getListaEscudoV());
        act.putStringArrayListExtra("date", (ArrayList<String>) pe.getListaDate());
        startActivity(act);
    }
    public void ir_a_busqueda (View v) {
        Intent act = new Intent(this, BusquedaEquipo.class);
        act.putStringArrayListExtra("Posicion", (ArrayList<String>) adj.getPosicion());
        act.putStringArrayListExtra("idEquipo", (ArrayList<String>) adj.getidEquipo());
        act.putStringArrayListExtra("Equipos", (ArrayList<String>) adj.getEquipo());
        startActivity(act);
    }
}

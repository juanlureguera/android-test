package com.example.juanlu.liga_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.juanlu.liga_app.R;
import com.example.juanlu.liga_app.adapters.image.ImageWithURL;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ProximoEvento extends AppCompatActivity {

    private List<String> listalocal = new ArrayList<>();
    private List <String> listavisitor = new ArrayList<>();
    private List <String> listaround = new ArrayList<>();
    private List <String> listacompeticion = new ArrayList<>();
    private List <String> listaEscudoL = new ArrayList<>();
    private List <String> listaEscudoV = new ArrayList<>();
    private List <String> listaDate = new ArrayList<>();

    public String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximo_evento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.fecha = getIntent().getStringExtra("fecha");
        this.listalocal = (List) getIntent().getStringArrayListExtra("local");
        this.listavisitor =  (List) getIntent().getStringArrayListExtra("visitor");
        this.listaround = (List) getIntent().getStringArrayListExtra("round");
        this.listacompeticion = (List) getIntent().getStringArrayListExtra("competicion");
        this.listaEscudoL = (List) getIntent().getStringArrayListExtra("escudoL");
        this.listaEscudoV = (List) getIntent().getStringArrayListExtra("escudoV");
        this.listaDate = (List) getIntent().getStringArrayListExtra("date");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Jornada nº"+listaround.get(0).replaceAll("\\n", ""));


        ListView lista = (ListView)findViewById(R.id.listView);
        AdapterLista adapterLista = new AdapterLista( this );
        lista.setAdapter( adapterLista );
    }
    class AdapterLista extends ArrayAdapter<String>{
        Activity context;

        public AdapterLista( Activity context ){
            super(context, R.layout.lista_partidos, listalocal);
            this.context = context;
        }
        public View getView(int posicion, View view, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.lista_partidos, null);

            TextView partido = (TextView) item.findViewById(R.id.partido);
            partido.setText(listalocal.get(posicion).replaceAll("\\n", "") + " (L)  vs  (V) " + listavisitor.get(posicion).replaceAll("\\n", "") + "\n");

            TextView txtfecha = (TextView) item.findViewById(R.id.fecha);
            txtfecha.setText(listaDate.get(posicion));

            return item;
        }
    }
}
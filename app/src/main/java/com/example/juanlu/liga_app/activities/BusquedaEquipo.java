package com.example.juanlu.liga_app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.juanlu.liga_app.R;
import com.example.juanlu.liga_app.adapters.json.AdapterJSONequipo;

import java.util.ArrayList;
import java.util.List;

public class BusquedaEquipo extends AppCompatActivity {

    private List<String> idEquipo = new ArrayList<>();
    private List <String> equipo = new ArrayList<>();
    private List <String> equipo_minus = new ArrayList<>();
    private List <String> posicion = new ArrayList<>();
    private Button button;
    private EditText text;
    private TextView textNo;
    private String nombre_equipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_equipo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.buttonBuscar);
        button.setEnabled(false);
        text = (EditText) findViewById(R.id.textBuscar);
        textNo = (TextView) findViewById(R.id.textNo);

        this.posicion = (List) getIntent().getStringArrayListExtra("Posicion");
        this.idEquipo = (List) getIntent().getStringArrayListExtra("idEquipo");
        this.equipo =  (List) getIntent().getStringArrayListExtra("Equipos");
        for( String eq : equipo ){
            equipo_minus.add(eq.toLowerCase());
        }

        text.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    button.setEnabled(true);
                }
                else if ( s.length() == 0 ){
                    button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                textNo.setText("");
                nombre_equipo = arg0.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                button.setEnabled(false);
            }
        });
    }
    public void ir_a_equipo (View v) {
        int pos = 0;
        System.out.println(nombre_equipo);
        if( equipo_minus.contains(nombre_equipo.toLowerCase())) {
            for( int idx = 0; idx < equipo_minus.size(); idx ++){
                if( equipo_minus.get(idx).equals(nombre_equipo.toLowerCase())){
                    pos = idx;
                }
            }
            Context context = this;
            AdapterJSONequipo ad = new AdapterJSONequipo(idEquipo.get( pos ), context, posicion.get( pos ));
        }
        else{
            textNo.setText("No existe equipo introducido.");
        }
    }
}

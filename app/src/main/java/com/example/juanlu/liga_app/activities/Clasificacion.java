package com.example.juanlu.liga_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.juanlu.liga_app.adapters.json.AdapterJSONequipo;
import com.example.juanlu.liga_app.R;

import java.util.ArrayList;
import java.util.List;

public class Clasificacion extends AppCompatActivity {

    private List <String> idEquipo = new ArrayList<>();
    private List <String> posicion = new ArrayList<>();
    private List <String> partidos_jugados = new ArrayList<>();
    private List <String> equipo = new ArrayList<>();
    private List <String> puntos = new ArrayList<>();
    private List <String> wins = new ArrayList<>();
    private List <String> draws = new ArrayList<>();
    private List <String> losses = new ArrayList<>();
    private List <String> gf = new ArrayList<>();
    private List <String> gc = new ArrayList<>();
    private List <String> avg = new ArrayList<>();

    private static TableLayout tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabla = (TableLayout) findViewById(R.id.tablaL);

        this.idEquipo = (List) getIntent().getStringArrayListExtra("idEquipo");
        this.posicion =  (List) getIntent().getStringArrayListExtra("Posicion");
        this.partidos_jugados = (List) getIntent().getStringArrayListExtra("Partidos_jugados");
        this.equipo =  (List) getIntent().getStringArrayListExtra("Equipos");
        this.puntos =  (List) getIntent().getStringArrayListExtra("Puntos");
        this.wins =  (List) getIntent().getStringArrayListExtra("Victorias");
        this.draws =  (List) getIntent().getStringArrayListExtra("Empates");
        this.losses =  (List) getIntent().getStringArrayListExtra("Derrotas");
        this.gf =  (List) getIntent().getStringArrayListExtra("GF");
        this.gc =  (List) getIntent().getStringArrayListExtra("GC");
        this.avg =  (List) getIntent().getStringArrayListExtra("AVG");

        createTable(equipo.size(), 10);
    }
    public void createTable(int rows, int cols){
        for (int i = 0; i < rows; i++) {
            final TableRow row = new TableRow(this);
            row.setClickable(true);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView nombreEquipo = (TextView) row.getChildAt(1);
                    String row = nombreEquipo.getText().toString();

                    if (!row.equals("Eq")) {
                        int pos = calculaPosicion( row );
                        AdapterJSONequipo ad = new AdapterJSONequipo(idEquipo.get( pos ), v, posicion.get( pos ));
                    }
                }
            });
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < cols; j++) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setPadding(30, 7, 4, 2);
                if( j==0 ) {
                    tv.setText(posicion.get(i));
                }
                else if( j==1 ){
                    tv.setText(equipo.get(i));
                }
                else if( j==2 ){
                    tv.setText(partidos_jugados.get(i));
                }
                else if( j==3 ){
                    tv.setText(puntos.get(i));
                }
                else if( j==4 ){
                    tv.setText(wins.get(i));
                }
                else if( j==5 ){
                    tv.setText(draws.get(i));
                }
                else if( j==6 ){
                    tv.setText(losses.get(i));
                }
                else if( j==7 ){
                    tv.setText(gf.get(i));
                }
                else if( j==8 ){
                    tv.setText(gc.get(i));
                }
                else if( j==9 ){
                    tv.setText(avg.get(i));
                }
                row.addView(tv);
                //tv.setBackgroundResource(R.drawable.borde);
            }
            tabla.addView(row);
        }
    }
    public int calculaPosicion( String row ){
        int pos = 0;
        for( int idx = 0; idx < equipo.size(); idx ++){
            if(equipo.get(idx).equals(row)){
                pos = idx;
                break;
            }
        }
        return pos;
    }
}
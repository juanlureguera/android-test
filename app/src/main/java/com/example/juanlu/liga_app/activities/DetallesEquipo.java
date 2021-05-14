package com.example.juanlu.liga_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.juanlu.liga_app.Equipo;
import com.example.juanlu.liga_app.Jugador;
import com.example.juanlu.liga_app.R;
import com.example.juanlu.liga_app.adapters.xml.AdapterXMLjugadores;
import com.example.juanlu.liga_app.adapters.image.ImageWithURL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DetallesEquipo extends AppCompatActivity {

    private List<Jugador> jugadores = new ArrayList<>();
    private List<Jugador> jugadores_detalles = new ArrayList<>();
    private ImageView imageView;
    private String imageHttpAddress;
    private Equipo e;
    private AdapterXMLjugadores adapter = null;
    private TableLayout tableJugadores;
    private boolean visible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        e = (Equipo) getIntent().getExtras().getSerializable("ClaseEquipo");

        Button button = (Button) findViewById(R.id.button);

        tableJugadores = (TableLayout) findViewById(R.id.tableJugadores);
        //Imagen
        loadIMG();
        //Texto
        loadText();
        //invoco al hilo
        adapter = new AdapterXMLjugadores(e.getTeam_id(), button);

        this.jugadores = adapter.getJugadores();
        this.jugadores_detalles = adapter.getJugadores_detalles();
    }
    public void ver_jugadores(View v) throws ExecutionException, InterruptedException {
        if(jugadores_detalles.size() < jugadores.size()){
            visible = false;
        }
        clearTable();
        createTable(jugadores.size()+1,9);
    }
    private void clearTable(){
        int count = tableJugadores.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = tableJugadores.getChildAt(i);
            if (child instanceof TableRow) tableJugadores.removeAllViews();
        }
    }
    private void loadIMG(){
        imageView = (ImageView) findViewById(R.id.imageEscudo);
        imageHttpAddress = e.getEscudo();

        String pathToFile = imageHttpAddress;
        ImageWithURL downloadTask = new ImageWithURL(imageView);
        downloadTask.execute(pathToFile);
    }
    private void loadText(){

        TextView nombreEquipo = (TextView)findViewById(R.id.nombreEquipo);
            nombreEquipo.setText(e.getNombreCompleto()+" ("+e.getNombreCorto()+") \nPosición en ligan: "+e.getPosicion()+"º\n\n\tFundación: "+e.getFundacion()+"\n\n\tEstadio: "+e.getEstadio());
        TextView entrenador = (TextView)findViewById(R.id.entrenador);
            entrenador.setText("Entrenador:\n " + e.getEntrenador());
    }
    private void createTable(int rows, int cols){
        int contador = 0;
        for (int i = 0; i < rows; i++) {
            final TableRow row = new TableRow(this);
            row.setClickable(true);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < cols; j++) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setPadding(30, 7, 4, 2);
                if( j==0 ) {
                    if(contador==0){
                        tv.setText("Dor");
                    }
                    else{
                        tv.setText(jugadores.get(i-1).getDorsal());
                    }
                }
                else if( j==1 ){
                    if(contador==0){
                        tv.setText("Nom");
                    }
                    else{
                        tv.setText(jugadores.get(i-1).getNombre());
                    }
                }
                else if( j==2 ){
                    if(contador==0){
                        tv.setText("Edad");
                    }
                    else if (contador >0){
                        tv.setText("\n"+jugadores_detalles.get(i-1).getEdad());
                    }
                }
                else if( j==3 ){
                    if(contador==0){
                        tv.setText("G");
                    }
                    else if (contador >0){
                        tv.setText("\n"+jugadores_detalles.get(i-1).getGol());
                    }
                }
                else if( j==4 ){
                    if(contador==0){
                        tv.setText("A");
                    }
                    else if (contador >0){
                        tv.setText("\n"+jugadores_detalles.get(i-1).getAsistencia());
                    }
                }
                else if( j==5 ){
                    if(contador==0){
                        tv.setText("Min");
                    }
                    else if (contador >0){
                        tv.setText("\n"+jugadores_detalles.get(i-1).getnMinutos());
                    }
                }
                else if( j==6 ){
                    if(contador==0){
                        tv.setText("Nac");
                    }
                    else if (contador >0){
                        tv.setText("\n"+jugadores_detalles.get(i-1).getNacionalidad());
                    }
                }
                else if( j==7 ){
                    if(contador==0){
                        tv.setText("H");
                    }
                    else if (contador >0){
                        tv.setText("\n"+jugadores_detalles.get(i-1).getAltura());
                    }
                }
                else if( j==8 ){
                    if(contador==0){
                        tv.setText("W");
                    }
                    else if (contador >0){
                        tv.setText("\n"+jugadores_detalles.get(i-1).getPeso());
                    }
                }
                row.addView(tv);
            }
            tableJugadores.addView(row);
            contador++;
        }
    }
}

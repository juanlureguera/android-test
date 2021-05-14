package com.example.juanlu.liga_app;

import java.io.Serializable;

/**
 * Created by alumno on 12/02/16.
 */
public class Equipo implements Serializable {
    //nombre, estadio, nombreCorto, entrenador,web, fundacion, ciudad, direccion, presidente, nombreCompleto, historial,  escudo, imgestadio
    public String nombre;  public String pais;
    public String estadio; public String nombreCorto;
    public String entrenador;  public String web;
    public String fundacion;   public String ciudad;
    public String direccion;   public String presidente;
    public String nombreCompleto;  public String historial;
    public String escudo;  public String imgestadio;
    public String team_id; public String posicion;

    public Equipo(String posicion, String nombre, String estadio, String nombreCorto, String entrenador, String web, String fundacion, String ciudad, String direccion, String presidente, String nombreCompleto, String historial, String escudo, String imgestadio, String team_id) {
        this.nombre = nombre; this.posicion = posicion;
        this.estadio = estadio; this.nombreCorto = nombreCorto;
        this.entrenador = entrenador;   this.web = web;
        this.fundacion = fundacion; this.ciudad = ciudad;
        this.direccion = direccion; this.presidente = presidente;
        this.nombreCompleto = nombreCompleto;   this.historial = historial;
        this.escudo = escudo;   this.imgestadio = imgestadio;this.team_id = team_id;
    }

    public String getPosicion() {
        return posicion;
    }

    public String getNombre() { return nombre; }


    public String getEstadio() {
        return estadio;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public String getWeb() {
        return web;
    }

    public String getFundacion() {
        return fundacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getHistorial() {return historial;}

    public String getEscudo() { return escudo; }

    public String getImgestadio() {
        return imgestadio;
    }

    public String getTeam_id() { return team_id; }
}

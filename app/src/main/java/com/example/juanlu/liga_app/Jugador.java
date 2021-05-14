package com.example.juanlu.liga_app;

/**
 * Created by juanlu on 15/02/16.
 */
public class Jugador {
    private String dorsal;
    private String nombre;
    private String role;
    private String id;
    private String gol;
    private String asistencia;
    private String nMinutos;
    private String peso;
    private String altura;
    private String nacionalidad;
    private String edad;

    public Jugador(String id, String dorsal, String nombre, String role) {
        this.id = id;
        this.dorsal = dorsal;
        this.nombre = nombre;
        this.role = role;
    }

    public Jugador(String id, String edad, String gol, String asistencia, String nMinutos, String peso, String altura, String nacionalidad) {
        this.id = id;
        this.edad = edad;
        this.gol = gol;
        this.asistencia = asistencia;
        this.peso = peso;
        this.nMinutos = nMinutos;
        this.altura = altura;
        this.nacionalidad = nacionalidad;
    }

    public String getEdad() {
        return edad;
    }

    public String getGol() {
        return gol;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public String getnMinutos() {
        return nMinutos;
    }

    public String getPeso() {
        return peso;
    }

    public String getAltura() {
        return altura;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getId() {
        return id;
    }

    public String getDorsal() {
        return dorsal;
    }

    public String getNombre() {
        return nombre;
    }

}

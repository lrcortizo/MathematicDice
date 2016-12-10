package com.example.luisr.dadomatematico.core;

import java.io.Serializable;

/**
 * Created by luisr on 10/12/2016.
 */
@SuppressWarnings("serial")
public class Partida implements Serializable {
    private Dado dado6;
    private int objetivo;
    private String jugador1;
    private String jugador2;
    private String resultado1;
    private String resultado2;

    public Partida(String j1, String j2){
        jugador1 = j1;
        jugador2 = j2;
    }

    public void setDados(Dado d, int ob){
        dado6 = d;
        objetivo = ob;
    }

    public void setResultado1(String r){
        resultado1 = r;
    }
    public void setResultado2(String r){
        resultado2 = r;
    }

    public String getResultado1() {
        return resultado1;
    }

    public Dado getDado6() {
        return dado6;
    }

    public int getObjetivo() {
        return objetivo;
    }

    public String getJugador1() {
        return jugador1;
    }

    public String getJugador2() {
        return jugador2;
    }

    public String getResultado2() {
        return resultado2;
    }
}

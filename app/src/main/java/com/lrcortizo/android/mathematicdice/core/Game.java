package com.lrcortizo.android.mathematicdice.core;

import java.io.Serializable;

public class Game implements Serializable {
    private Dice dice6;
    private int objetivo;
    private String jugador1;
    private String jugador2;
    private String resultado1;
    private String resultado2;

    public Game(String j1, String j2){
        jugador1 = j1;
        jugador2 = j2;
    }

    public void setDados(Dice d, int ob){
        dice6 = d;
        objetivo = ob;
    }

    public void setResultado1(String r){
        resultado1 = r;
    }

    public void setResultado2(String r){
        resultado2 = r;
    }

    public String ganador(){
        String toRet = "";
        double r1 = Double.parseDouble(resultado1);
        double r2 = Double.parseDouble(resultado2);
        if(resultado1==null && resultado2==null){
            toRet = "Empate";
        }else if(resultado1==null){
            toRet = jugador2;
        }else if(resultado2==null){
            toRet = jugador1;
        }else if(r1==r2){
            toRet = "Empate";
        } else if(r1<objetivo && r2<objetivo){
            if(r1>r2){
                toRet = jugador1;
            } else{
                toRet = jugador2;
            }
        } else if(r1>objetivo && r2>objetivo){
            if(r1<r2){
                toRet = jugador1;
            } else{
                toRet = jugador2;
            }
        } else if(r1>objetivo && r2<objetivo){
            if((r1-objetivo)==(objetivo-r2)){
                toRet = "Empate";
            } else if((r1-objetivo)<(objetivo-r2)){
                toRet = jugador1;
            } else if((r1-objetivo)>(objetivo-r2)){
                toRet = jugador2;
            }
        } else if(r1<objetivo && r2>objetivo){
            if((r2-objetivo)==(objetivo-r1)){
                toRet = "Empate";
            } else if((r2-objetivo)<(objetivo-r1)){
                toRet = jugador2;
            } else if((r2-objetivo)>(objetivo-r1)){
                toRet = jugador1;
            }
        }
        return toRet;
    }

    public String getResultado1() {
        return resultado1;
    }

    public String getResultado2() {
        return resultado2;
    }

    public Dice getDado6() {
        return dice6;
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


}

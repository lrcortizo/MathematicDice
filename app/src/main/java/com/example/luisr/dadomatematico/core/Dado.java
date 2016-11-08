package com.example.luisr.dadomatematico.core;
import java.math.*;

/**
 * Created by luisr on 08/11/2016.
 */

public class Dado {
    private int numCaras;

    public Dado(int n){
        numCaras=n;
    }

    public String lanzarDado(){
        int toret=0;
        toret = (int)(Math.random()*numCaras+1);
        StringBuilder sb = new StringBuilder();
        sb.append(toret);
        return sb.toString();
    }
}

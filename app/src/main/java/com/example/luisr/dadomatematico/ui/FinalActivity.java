package com.example.luisr.dadomatematico.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.luisr.dadomatematico.R;
import com.example.luisr.dadomatematico.core.App;
import com.example.luisr.dadomatematico.core.Partida;
import com.example.luisr.dadomatematico.core.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        final Partida partida = (Partida)getIntent().getExtras().getSerializable("partida");
        final TextView tvObjetivo = (TextView) findViewById(R.id.tvObjetivo);
        final TextView tvResultado1 = (TextView) findViewById(R.id.tvResultado1);
        final TextView tvResultado2 = (TextView) findViewById(R.id.tvResultado2);
        final TextView tvGanador = (TextView) findViewById(R.id.tvGanador);
        final Button btFin = (Button) findViewById(R.id.btFin);
        tvObjetivo.setText("Objetivo: "+partida.getObjetivo());
        tvResultado1.setText("Resultado de "+partida.getJugador1()+": "+partida.getResultado1());
        tvResultado2.setText("Resultado de "+partida.getJugador2()+": "+partida.getResultado2());
        tvGanador.setText(partida.ganador());
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        SQLite sqlDb = ( (App) this.getApplication() ).getDb();
        SQLiteDatabase db = sqlDb.getWritableDatabase();
        db.execSQL( "INSERT OR IGNORE INTO historial(fecha, resultado) VALUES( ?, ? )",
                new String[]{ hourdateFormat.toString(), partida.ganador() } );
        btFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Activity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Activity);
            }
        });
    }
}

package com.lrcortizo.android.mathematicdice.ui;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lrcortizo.android.mathematic.dice.R;
import com.lrcortizo.android.mathematicdice.core.Game;
import com.lrcortizo.android.mathematicdice.core.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;


public class FinalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        //-------------------------------WIDGETS AND TEXT FIELDS------------------
        final Game game = (Game)getIntent().getExtras().getSerializable("partida");
        final TextView tvObjetivo = (TextView) findViewById(R.id.tvObjetivo);
        final TextView tvResultado1 = (TextView) findViewById(R.id.tvResultado1);
        final TextView tvResultado2 = (TextView) findViewById(R.id.tvResultado2);
        final TextView tvGanador = (TextView) findViewById(R.id.tvGanador);
        final Button btFin = (Button) findViewById(R.id.btFin);
        tvObjetivo.setText(this.getString(R.string.objetivo)+" "+ game.getObjetivo());
        tvResultado1.setText(this.getString(R.string.resultado)+" "+ game.getJugador1()+": "+ game.getResultado1());
        tvResultado2.setText(this.getString(R.string.resultado)+" "+ game.getJugador2()+": "+ game.getResultado2());
        //-------------------------------MOSTRAR GANADOR---------------------
        if (game.ganador() == "Empate") {
            tvGanador.setText(this.getString(R.string.empate)+" "+ game.getJugador1()+" "+this.getString(R.string.y)+" "+ game.getJugador2());
        }else if(game.ganador() == game.getJugador1()){
            tvGanador.setText(game.getJugador1()+" "+this.getString(R.string.ganar)+" "+ game.getJugador2());
        }else{
            tvGanador.setText(game.getJugador2()+" "+this.getString(R.string.ganar)+" "+ game.getJugador1());
        }
        //------------------------------OBTENER FECHA Y HORA-----------------------
        Date d = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        String fecha = hourdateFormat.format(d);
        //-----------------------------ACTUALIZAR BASE DE DATOS----------------------------
        Queue<String> cola = new LinkedList<>();
        SQLite sqlDb = new SQLite(this.getApplicationContext());
        SQLiteDatabase db = sqlDb.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT fecha FROM historial", null );
        if ( cursor.moveToFirst() ) {
            do {
                String date = cursor.getString( 0 );
                cola.add(date);
            } while ( cursor.moveToNext() );
            cursor.close();
        }
        if(cola.size()==10){
            db = sqlDb.getWritableDatabase();
            db.execSQL( "DELETE FROM historial WHERE fecha = ?", new String[]{ cola.remove() } );
        }
        db = sqlDb.getWritableDatabase();
        db.execSQL( "INSERT OR IGNORE INTO historial(fecha, resultado) VALUES( ?, ? )",
                new String[]{ fecha, game.ganador() } );
        //------------------------------------------BUTTON LISTENER-----------------------------
        btFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Activity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Activity);
                finish();
            }
        });
    }
}

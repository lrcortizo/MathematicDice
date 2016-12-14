package com.example.luisr.dadomatematico.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Queue;


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
        Calendar calendar = new GregorianCalendar();
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR));
        sb.append(" "+calendar.get(Calendar.HOUR_OF_DAY) + ":" + (calendar.get(Calendar.MINUTE)+1) + ":" + calendar.get(Calendar.SECOND));
        String fecha = sb.toString();
        Queue<String> cola = new LinkedList<String>();
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
                new String[]{ fecha, partida.ganador() } );
        btFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Activity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Activity);
            }
        });
    }
}

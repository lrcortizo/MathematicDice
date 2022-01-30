package com.lrcortizo.android.mathematicdice.ui;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lrcortizo.android.mathematicdice.R;
import com.lrcortizo.android.mathematicdice.core.Game;
import com.lrcortizo.android.mathematicdice.core.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;


public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        //-------------------------------WIDGETS AND TEXT FIELDS------------------
        final Game game = (Game) getIntent().getExtras().getSerializable("game");
        final TextView tvTarget = (TextView) findViewById(R.id.tvTarget);
        final TextView tvResult1 = (TextView) findViewById(R.id.tvResult1);
        final TextView tvResult2 = (TextView) findViewById(R.id.tvResult2);
        final TextView tvWinner = (TextView) findViewById(R.id.tvWinner);
        final Button btFin = (Button) findViewById(R.id.btFin);
        tvTarget.setText(String.format("%s %d", this.getString(R.string.objetivo), game.getTarget()));
        tvResult1.setText(String.format("%s %s: %s", this.getString(R.string.result),
                game.getPlayer1(), game.getResult1()));
        tvResult2.setText(String.format("%s %s: %s", this.getString(R.string.result),
                game.getPlayer2(), game.getResult2()));

        //-------------------------------SHOW WINNER---------------------
        if (game.winner().equals("Draw")) {
            tvWinner.setText(String.format("%s %s %s %s", this.getString(R.string.empate),
                    game.getPlayer1(), this.getString(R.string.y), game.getPlayer2()));
        } else if (game.winner().equals(game.getPlayer1())) {
            tvWinner.setText(String.format("%s %s %s", game.getPlayer1(),
                    this.getString(R.string.ganar), game.getPlayer2()));
        } else {
            tvWinner.setText(String.format("%s %s %s", game.getPlayer2(),
                    this.getString(R.string.ganar), game.getPlayer1()));
        }

        //------------------------------GET DATE AND HOUR-----------------------
        final DateFormat dateTimeFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        final String dateString = dateTimeFormat.format(new Date());

        //-----------------------------REFRESH DATABASE----------------------------
        Queue<String> queue = new LinkedList<>();
        final SQLite sqlDb = new SQLite(this.getApplicationContext());
        SQLiteDatabase db = sqlDb.getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT fecha FROM historial", null);
        if (cursor.moveToFirst()) {
            do {
                String dateCursor = cursor.getString(0);
                queue.add(dateCursor);
            } while (cursor.moveToNext());
            cursor.close();
        }
        if (queue.size() == 10) {
            db = sqlDb.getWritableDatabase();
            db.execSQL("DELETE FROM historial WHERE fecha = ?", new String[]{queue.remove()});
        }
        db = sqlDb.getWritableDatabase();
        db.execSQL("INSERT OR IGNORE INTO historial(fecha, resultado) VALUES( ?, ? )",
                new String[]{dateString, game.winner()});

        //------------------------------------------BUTTON LISTENER-----------------------------
        btFin.setOnClickListener(v -> {
            Intent Activity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Activity);
            finish();
        });
    }
}

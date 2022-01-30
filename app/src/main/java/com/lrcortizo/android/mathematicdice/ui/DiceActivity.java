package com.lrcortizo.android.mathematicdice.ui;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.*;

import com.lrcortizo.android.mathematicdice.R;
import com.lrcortizo.android.mathematicdice.core.Dice;
import com.lrcortizo.android.mathematicdice.core.Game;

public class DiceActivity extends AppCompatActivity {

    private boolean label6 = false;

    private boolean label12 = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        //-------------------------------WIDGETS AND TEXT FIELDS------------------
        final Game game = (Game) getIntent().getExtras().getSerializable("game");
        final Button btPlay = (Button) this.findViewById(R.id.btPlay);
        final Button bt6 = (Button) this.findViewById(R.id.button6);
        final Button bt12 = (Button) this.findViewById(R.id.button12);
        final TextView tvDices6 = (TextView) this.findViewById(R.id.tvDices6);
        final TextView tvDices12 = (TextView) this.findViewById(R.id.tvDices12);
        final TextView tvDice = (TextView) this.findViewById(R.id.tvDice);

        //------------------------------THROW DICES--------------------------
        final Dice dice6 = new Dice(6);
        final Dice dice12 = new Dice(12);
        dice6.throwDice();
        dice12.throwDice();

        //------------------------------------------BUTTON LISTENERS-----------------------------
        bt6.setOnClickListener(v -> {
            tvDices6.setText(String.format("%s %s, %s, %s, %s, %s, %s",
                    DiceActivity.this.getString(R.string.dice6), dice6.getDiceThrows().get(0),
                    dice6.getDiceThrows().get(1), dice6.getDiceThrows().get(2),
                    dice6.getDiceThrows().get(3), dice6.getDiceThrows().get(4),
                    dice6.getDiceThrows().get(5)));
            label6 = true;
        });
        bt12.setOnClickListener(v -> {
            tvDices12.setText(String.format("%s %s, %s",
                    DiceActivity.this.getString(R.string.dice12), dice12.getDiceThrows().get(0),
                    dice12.getDiceThrows().get(1)));
            tvDice.setText(String.format("%s %d", DiceActivity.this.getString(R.string.objetivo),
                    Integer.parseInt(dice12.getDiceThrows().get(0)) * Integer.parseInt(dice12.getDiceThrows().get(1))));
            label12 = true;
        });
        btPlay.setOnClickListener(v -> {
            //-----------CHECK DICES THROWN---------------
            if (label6 && label12) {
                game.setDices(dice6, Integer.parseInt(dice12.getDiceThrows().get(0)) * Integer.parseInt(dice12.getDiceThrows().get(1)));
                Intent intent = new Intent(v.getContext(), Turn1Activity.class);
                intent.putExtra("game", game);
                startActivityForResult(intent, 0);
                finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(DiceActivity.this);
                builder.setTitle(DiceActivity.this.getString(R.string.error));
                builder.setMessage(DiceActivity.this.getString(R.string.errordice));
                builder.create().show();

            }
        });
    }

    //---------------------------OPTIONS MENU--------------------------------
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.opHelp:
                help();
                return true;
            case R.id.opSalir:
                Intent Activity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Activity);
                finish();
                return true;
        }
        return false;
    }

    public void help() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getString(R.string.help));
        builder.setMessage(this.getString(R.string.helpdados));
        builder.create().show();
    }
}

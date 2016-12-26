package com.example.luisr.dadomatematico.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luisr.dadomatematico.R;
import com.example.luisr.dadomatematico.core.Partida;


public class PlayersActivity extends AppCompatActivity {
    private Partida partida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        final EditText etJugador1 = (EditText) this.findViewById(R.id.etJugador1);
        final EditText etJugador2 = (EditText) this.findViewById(R.id.etJugador2);
        final Button btStart = (Button) this.findViewById( R.id.btStart );
        final String error = this.getString(R.string.error);
        final String error1 = this.getString(R.string.errorplayers1);
        final String error2 = this.getString(R.string.errorplayers2);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etJugador1.getText().toString().equals(etJugador2.getText().toString())){
                    AlertDialog.Builder builder = new AlertDialog.Builder( PlayersActivity.this );
                    builder.setTitle( error );
                    builder.setMessage(error1);
                    builder.create().show();
                } else if (!etJugador1.getText().toString().isEmpty() && !etJugador2.getText().toString().isEmpty()
                                && !(etJugador1.getText().toString().equals(etJugador2.getText().toString()))) {
                    partida = new Partida(etJugador1.getText().toString(), etJugador2.getText().toString());
                    Intent intent = new Intent(v.getContext(), DiceActivity.class);
                    intent.putExtra("partida", partida);
                    startActivityForResult(intent, 0);
                    finish();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder( PlayersActivity.this );
                    builder.setTitle( error );
                    builder.setMessage( error2 );
                    builder.create().show();
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        boolean toret = false;
        switch (menuItem.getItemId()){
            case R.id.opHelp:
                help();
                toret = true;
                break;
            case R.id.opSalir:
                Intent Activity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Activity);
                finish();
                toret = true;
                break;
        }
        return toret;
    }

    public void help(){
        final TextView t = new TextView(this);
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle(this.getString(R.string.help));
        builder.setMessage(this.getString(R.string.helpplayers));
        builder.create().show();

    }

}


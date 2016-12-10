package com.example.luisr.dadomatematico.ui;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.*;

import com.example.luisr.dadomatematico.R;
import com.example.luisr.dadomatematico.core.Dado;
import com.example.luisr.dadomatematico.core.Partida;

public class DiceActivity extends AppCompatActivity {
    private boolean label6 = false;
    private boolean label12 = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        final Partida partida = (Partida)getIntent().getExtras().getSerializable("partida");
        final Button btPlay =(Button) this.findViewById(R.id.btPlay);
        final Button bt6 = (Button) this.findViewById( R.id.button6 );
        final Button bt12 = (Button) this.findViewById( R.id.button12 );
        final TextView tvDados6 = (TextView) this.findViewById(R.id.tvDados6);
        final TextView tvDados12 = (TextView) this.findViewById(R.id.tvDados12);
        final TextView tvDice = (TextView) this.findViewById(R.id.tvDice);
        final Dado dado6 = new Dado(6);
        final Dado dado12 = new Dado(12);
        dado6.lanzarDado();
        dado12.lanzarDado();
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDados6.setText("Reultado del lanzamiento de los dados de 6 caras: "+dado6.getTirada()[0]+", "+dado6.getTirada()[1]+", "
                        +dado6.getTirada()[2]+", "+dado6.getTirada()[3]+", "+dado6.getTirada()[4]+", "+dado6.getTirada()[5]);
                label6=true;
            }
        });
        bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDados12.setText("Reultado del lanzamiento de los dados de 12 caras: "+dado12.getTirada()[0]+", "+dado12.getTirada()[1]);
                tvDice.setText("La cifra objetivo es: "+(Integer.parseInt(dado12.getTirada()[0])*Integer.parseInt(dado12.getTirada()[1])));
                label12=true;
            }
        });
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(label6 && label12) {
                    partida.setDados(dado6, Integer.parseInt(dado12.getTirada()[0])*Integer.parseInt(dado12.getTirada()[1]));
                    Intent intent = new Intent(v.getContext(), Turno1Activity.class);
                    intent.putExtra("partida", partida);
                    startActivityForResult(intent, 0);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder( DiceActivity.this );
                    builder.setTitle( "Error" );
                    builder.setMessage( "Lanza los dados antes de continuar" );
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
                toret = true;
                break;
        }
        return toret;
    }

    public void help(){
        final TextView t = new TextView(this);
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle("Help");
        builder.setMessage( "Pulsa los botones para lanzar los dados" );
        builder.create().show();

    }
}

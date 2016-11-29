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
import java.lang.*;

import com.example.luisr.dadomatematico.R;
import com.example.luisr.dadomatematico.core.Dado;

public class DiceActivity extends AppCompatActivity {
    public static String[] lanzar6;
    public static String[] lanzar12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        final String nombre1 = (String)getIntent().getExtras().getString("nombre1");
        final String nombre2 = (String)getIntent().getExtras().getString("nombre2");
        final Button btPlay =(Button) this.findViewById(R.id.btPlay);
        final Button bt6 = (Button) this.findViewById( R.id.button6 );
        final Button bt12 = (Button) this.findViewById( R.id.button12 );
        final TextView tvDados6 = (TextView) this.findViewById(R.id.tvDados6);
        final TextView tvDados12 = (TextView) this.findViewById(R.id.tvDados12);
        final Dado dado6 = new Dado(6);
        final Dado dado12 = new Dado(12);
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<6;i++){
                    lanzar6[i] = dado6.lanzarDado();
                    tvDados6.setText("Reultado del lanzamiento de los dados de 6 caras:"+lanzar6[0]+", "+lanzar6[1]+", "+lanzar6[2]+", "
                            +lanzar6[3]+", "+lanzar6[4]+", "+lanzar6[5]);
                }
            }
        });
        bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<2;i++) {
                    lanzar12[i] = dado12.lanzarDado();
                    tvDados12.setText("Reultado del lanzamiento de los dados de 12 caras:"+lanzar6[0]+", "+lanzar6[1]);
                }
            }
        });
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Turno1Activity.class);
                intent.putExtra("dados6", lanzar6);
                intent.putExtra("dados12", lanzar12);
                intent.putExtra("nombre1", nombre1);
                intent.putExtra("nombre2", nombre2);
                startActivityForResult(intent, 0);
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
        builder.setMessage( "Mensaje ayuda" );
        builder.create().show();

    }
}

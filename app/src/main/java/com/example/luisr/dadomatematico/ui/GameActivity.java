package com.example.luisr.dadomatematico.ui;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisr.dadomatematico.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageView myAnimation = (ImageView)findViewById(R.id.image_dado);
        final AnimationDrawable myAnimationDrawable = (AnimationDrawable)myAnimation.getDrawable();

        myAnimation.post(
                new Runnable(){

                    @Override
                    public void run() {
                        myAnimationDrawable.start();
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

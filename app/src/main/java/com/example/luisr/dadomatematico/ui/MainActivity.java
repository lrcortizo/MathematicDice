package com.example.luisr.dadomatematico.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.luisr.dadomatematico.R;
import com.example.luisr.dadomatematico.core.Dado;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        final Button btGame = (Button) this.findViewById( R.id.btGame );
        final Button btScore = (Button) this.findViewById( R.id.btScore );
        final Button btOptions = (Button) this.findViewById( R.id.btOptions );
        final Button btExit = (Button) this.findViewById( R.id.btExit );
        final Button b = (Button) this.findViewById( R.id.button );
        /*final EditText ed = (EditText) this.findViewById(R.id.editText);
        final Dado dado = new Dado(6);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = dado.lanzarDado();
                ed.setText(a);
            }
        });*/

//-----------------------------------BUTTON LISTENERS---------------------------------------------
        btGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), GameActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        btScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ScoreActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        btOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), OptionsActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }

}
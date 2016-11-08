package com.example.luisr.dadomatematico.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.luisr.dadomatematico.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        Button btGame = (Button) this.findViewById( R.id.btGame );
        Button btScore = (Button) this.findViewById( R.id.btScore );
        Button btOptions = (Button) this.findViewById( R.id.btOptions );
        Button btExit = (Button) this.findViewById( R.id.btExit );
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
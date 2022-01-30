package com.lrcortizo.android.mathematicdice.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lrcortizo.android.mathematicdice.R;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        //-------------------------------WIDGETS AND TEXT FIELDS-------------------
        final TextView tvInstructions = (TextView) this.findViewById(R.id.tvInstructions);
        tvInstructions.setText(this.getString(R.string.instrucciones));
    }
}

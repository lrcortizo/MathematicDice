package com.example.luisr.dadomatematico.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luisr.dadomatematico.R;

import org.mozilla.javascript.*;

public class Turno1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turno1);
        final String nombre1 = getIntent().getExtras().getString("nombre1");
        final String nombre2 = getIntent().getExtras().getString("nombre2");
        final String [] dados6 = getIntent().getExtras().getStringArray("dados6");
        final int objetivo = getIntent().getExtras().getInt("objetivo");
        final TextView tvObjetivo = (TextView) this.findViewById(R.id.tvObjetivo);
        final TextView tvCifras = (TextView) this.findViewById(R.id.tvCifras);
        tvObjetivo.setText("El objetivo es:"+objetivo);
        tvCifras.setText("Los numeros a utilizar son: "+dados6[0]+", "+dados6[1]+", "+dados6[2]+", "
                +dados6[3]+", "+dados6[4]+", "+dados6[5]);
        final EditText etExpresion = (EditText) this.findViewById(R.id.etExpresion);
        final Button btTurno = (Button) this.findViewById(R.id.btTurno);
        final TextView tvTemporizador = (TextView) this.findViewById(R.id.tvTemporizador);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTemporizador.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvTemporizador.setText("done!");
            }
        }.start();

        btTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean label = true;
                String resultado = "";
                for(int i=1;i<etExpresion.getText().toString().length()+1;i++){
                    if (!(etExpresion.getText().toString().substring((i-1),i).equals("+")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals("*")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals("-")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals("/")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals("(")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(")")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(dados6[0])) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(dados6[1])) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(dados6[2])) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(dados6[3])) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(dados6[4])) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(dados6[5])))
                        {
                            label=false;
                    }
                }
                if(etExpresion.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder( Turno1Activity.this );
                    builder.setTitle( "Error" );
                    builder.setMessage( "Introduce un resultado" );
                    builder.create().show();
                }else if(!label || etExpresion.getText().toString().length()==1){
                    AlertDialog.Builder builder = new AlertDialog.Builder( Turno1Activity.this );
                    builder.setTitle( "Error" );
                    builder.setMessage( "Formato incorrecto" );
                    builder.create().show();
                }else{
                    try {
                        resultado = calc(etExpresion.getText().toString());
                    }catch (Exception e){
                        AlertDialog.Builder builder = new AlertDialog.Builder( Turno1Activity.this );
                        builder.setTitle( "Error" );
                        builder.setMessage( "Formato incorrecto" );
                        builder.create().show();
                    }
                    Intent intent = new Intent(v.getContext(), Turno2Activity.class);
                    intent.putExtra("dados6", dados6);
                    intent.putExtra("objetivo", objetivo);
                    intent.putExtra("nombre1", nombre1);
                    intent.putExtra("nombre2", nombre2);
                    intent.putExtra("resultado", resultado);
                    startActivityForResult(intent, 0);
                }
            }

        });
    }

    public static String calc(String expresion){
        //ScriptEngineManager engineManager = new ScriptEngineManager();
        //ScriptEngine engine = engineManager.getEngineByName("js");
        //Interpreter interpreter = new Interpreter();
       /* String[] varArray = new String[mapaVariables.size()];
        int count = 0;

        for (String key: mapaVariables.keySet()) {
            varArray[count] = key + " = " + mapaVariables.get(key);
            count++;
        }*/

        Object[] params = new Object[] { "javaScriptParam" };

        // Every Rhino VM begins with the enter()
        // This Context is not Android's Context
        Context rhino = Context.enter();

        // Turn off optimization to make Rhino Android compatible
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();

            // Note the forth argument is 1, which means the JavaScript source has
            // been compressed to only one line using something like YUI
           /* for (String s : varArray) {
                rhino.evaluateString(scope, s, "JavaScript", 1, null).toString();
            }*/

            String expr = expresion.replaceAll("NOT", "!");
            expr = expr.replaceAll("OR", "|");
            expr = expr.replaceAll("AND", "&");

            String toRet = rhino.evaluateString(scope, expr, "JavaScript", 1, null).toString().replaceAll(".0","");
            toRet = toRet.replaceAll("true","1");
            toRet = toRet.replaceAll("false","0");

            return toRet;


        } finally {
            Context.exit();
        }

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
        builder.setMessage( "Escribe una expresion matematica para llegar al objetivo" );
        builder.create().show();

    }
}

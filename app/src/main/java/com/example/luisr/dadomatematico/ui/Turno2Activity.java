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
import com.example.luisr.dadomatematico.core.Partida;

import org.mozilla.javascript.*;

public class Turno2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turno2);
        final Partida partida = (Partida)getIntent().getExtras().getSerializable("partida");
        final TextView tvObjetivo = (TextView) this.findViewById(R.id.tvObjetivo);
        final TextView tvCifras = (TextView) this.findViewById(R.id.tvCifras);
        tvObjetivo.setText("El objetivo es:"+partida.getObjetivo());
        tvCifras.setText("Los numeros a utilizar son: "+partida.getDado6().getTirada()[0]+", "+partida.getDado6().getTirada()[1]+", "
                +partida.getDado6().getTirada()[2]+", "+partida.getDado6().getTirada()[3]+", "
                +partida.getDado6().getTirada()[4]+", "+partida.getDado6().getTirada()[5]);
        final EditText etExpresion = (EditText) this.findViewById(R.id.etExpresion);
        final Button btTerminar = (Button) this.findViewById(R.id.btTerminar);
        final TextView tvTemporizador = (TextView) this.findViewById(R.id.tvTemporizador);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTemporizador.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvTemporizador.setText("done!");
            }
        }.start();

        btTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean label = true;
                String resultado2 = "";
                for(int i=1;i<etExpresion.getText().toString().length()+1;i++){
                    if (!(etExpresion.getText().toString().substring((i-1),i).equals("+")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals("*")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals("-")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals("/")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals("(")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(")")) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(partida.getDado6().getTirada()[0])) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(partida.getDado6().getTirada()[1])) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(partida.getDado6().getTirada()[2])) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(partida.getDado6().getTirada()[3])) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(partida.getDado6().getTirada()[4])) &&
                        !(etExpresion.getText().toString().substring((i-1),i).equals(partida.getDado6().getTirada()[5])))
                    {
                        label=false;
                    }
                }
                if(etExpresion.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder( Turno2Activity.this );
                    builder.setTitle( "Error" );
                    builder.setMessage( "Introduce un resultado" );
                    builder.create().show();
                }else if(!label || etExpresion.getText().toString().length()==1){
                    AlertDialog.Builder builder = new AlertDialog.Builder( Turno2Activity.this );
                    builder.setTitle( "Error" );
                    builder.setMessage( "Formato incorrecto" );
                    builder.create().show();
                }else{
                    try {
                        resultado2 = calc(etExpresion.getText().toString());
                    }catch (Exception e){
                        AlertDialog.Builder builder = new AlertDialog.Builder( Turno2Activity.this );
                        builder.setTitle( "Error" );
                        builder.setMessage( "Formato incorrecto" );
                        builder.create().show();
                    }
                    if(!resultado2.equals("")) {
                        partida.setResultado2(resultado2);
                        Intent intent = new Intent(v.getContext(), FinalActivity.class);
                        intent.putExtra("partida", partida);
                        startActivityForResult(intent, 0);
                    }
                }
            }
        });
    }

    public static String calc(String expresion){

        Object[] params = new Object[] { "javaScriptParam" };

        Context rhino = Context.enter();

        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();

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

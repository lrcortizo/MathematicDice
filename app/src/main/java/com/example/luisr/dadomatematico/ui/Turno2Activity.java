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
    private String help = this.getString(R.string.help);
    private String helpmessage = this.getString(R.string.helpturno);
    private String textobjetivo = this.getString(R.string.helpturno);
    private String textnumeros = this.getString(R.string.helpturno);
    private String error = this.getString(R.string.error);
    private String error1 = this.getString(R.string.errorturno1);
    private String error2 = this.getString(R.string.errorturno2);
    private String error3 = this.getString(R.string.errorturno3);
    private String tiempo = this.getString(R.string.tiempo);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turno2);
        final Partida partida = (Partida)getIntent().getExtras().getSerializable("partida");
        final TextView tvObjetivo = (TextView) this.findViewById(R.id.tvObjetivo2);
        final TextView tvCifras = (TextView) this.findViewById(R.id.tvCifras2);
        tvObjetivo.setText("El objetivo es:"+partida.getObjetivo());
        tvCifras.setText("Los numeros a utilizar son: "+partida.getDado6().getTirada()[0]+", "+partida.getDado6().getTirada()[1]+", "
                +partida.getDado6().getTirada()[2]+", "+partida.getDado6().getTirada()[3]+", "
                +partida.getDado6().getTirada()[4]+", "+partida.getDado6().getTirada()[5]);
        final EditText etExpresion = (EditText) this.findViewById(R.id.etExpresion2);
        final Button btTerminar = (Button) this.findViewById(R.id.btTerminar);
        final TextView tvTemporizador = (TextView) this.findViewById(R.id.tvTemporizador2);
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTemporizador.setText(tiempo+" " + millisUntilFinished / 1000);
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
                    builder.setTitle( error );
                    builder.setMessage( error1 );
                    builder.create().show();
                }else if(!label || etExpresion.getText().toString().length()==1){
                    AlertDialog.Builder builder = new AlertDialog.Builder( Turno2Activity.this );
                    builder.setTitle( error );
                    builder.setMessage( error2 );
                    builder.create().show();
                }else{
                    try {
                        resultado2 = calc(etExpresion.getText().toString(), partida);
                    }catch (Exception e){
                        AlertDialog.Builder builder = new AlertDialog.Builder( Turno2Activity.this );
                        builder.setTitle( error );
                        builder.setMessage( e.getMessage());
                        builder.create().show();
                    }
                    if(!resultado2.equals("")) {
                        partida.setResultado2(resultado2);
                        Intent intent = new Intent(v.getContext(), FinalActivity.class);
                        intent.putExtra("partida", partida);
                        startActivityForResult(intent, 0);
                        finish();
                    }
                }
            }
        });
    }

    public String calc(String expresion, Partida partida) throws Exception{

        Context rhino = Context.enter();

        rhino.setOptimizationLevel(-1);
        for(int i=1;i<expresion.length()+1;i++){
            if (!(expresion.substring((i-1),i).equals("+")) &&
                    !(expresion.substring((i-1),i).equals("*")) &&
                    !(expresion.substring((i-1),i).equals("-")) &&
                    !(expresion.substring((i-1),i).equals("/")) &&
                    !(expresion.substring((i-1),i).equals("(")) &&
                    !(expresion.substring((i-1),i).equals(")")) &&
                    !(expresion.substring((i-1),i).equals(partida.getDado6().getTirada()[0])) &&
                    !(expresion.substring((i-1),i).equals(partida.getDado6().getTirada()[1])) &&
                    !(expresion.substring((i-1),i).equals(partida.getDado6().getTirada()[2])) &&
                    !(expresion.substring((i-1),i).equals(partida.getDado6().getTirada()[3])) &&
                    !(expresion.substring((i-1),i).equals(partida.getDado6().getTirada()[4])) &&
                    !(expresion.substring((i-1),i).equals(partida.getDado6().getTirada()[5])))
            {
                throw new Exception(error3);
            }
        }
            Scriptable scope = rhino.initStandardObjects();

            String toRet = rhino.evaluateString(scope, expresion, "JavaScript", 1, null).toString();

            return toRet;
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
        builder.setTitle(help);
        builder.setMessage( helpmessage );
        builder.create().show();
    }
}

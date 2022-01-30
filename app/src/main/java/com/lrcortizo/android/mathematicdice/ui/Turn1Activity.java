package com.lrcortizo.android.mathematicdice.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lrcortizo.android.mathematic.dice.R;
import com.lrcortizo.android.mathematicdice.core.Game;

import org.mozilla.javascript.*;

public class Turn1Activity extends AppCompatActivity {
    private boolean label = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turno1);
        //-------------------------------WIDGETS AND TEXT FIELDS------------------
        final Game game = (Game)getIntent().getExtras().getSerializable("partida");
        final TextView tvObjetivo = (TextView) this.findViewById(R.id.tvObjetivo1);
        final TextView tvCifras = (TextView) this.findViewById(R.id.tvCifras1);
        final EditText etExpresion = (EditText) this.findViewById(R.id.etExpresion1);
        final Button btTurno = (Button) this.findViewById(R.id.btTurno);
        final TextView tvTemporizador = (TextView) this.findViewById(R.id.tvTemporizador1);
        tvObjetivo.setText(this.getString(R.string.objetivo)+" "+ game.getObjetivo());
        tvCifras.setText(this.getString(R.string.numeros)+" "+ game.getDado6().getTirada()[0]+", "+ game.getDado6().getTirada()[1]+", "
                + game.getDado6().getTirada()[2]+", "+ game.getDado6().getTirada()[3]+", "
                + game.getDado6().getTirada()[4]+", "+ game.getDado6().getTirada()[5]);
        //-------------------------------------TEMPORIZADOR------------------------------------
        final CountDownTimer temporizador = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTemporizador.setText(Turn1Activity.this.getString(R.string.tiempo)+" " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if(label==true) {
                    game.setResultado1(null);
                    Intent intent = new Intent(Turn1Activity.this, Turn2Activity.class);
                    intent.putExtra("partida", game);
                    startActivityForResult(intent, 0);
                    finish();
                }
            }
        };
        temporizador.start();
        //------------------------------------------BUTTON LISTENER--------------------
        btTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultado = "";
                //---------------COMPROBACION CAMPO VACÍO Y CARACTERES VÁLIDOS-----------------
                if(etExpresion.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder( Turn1Activity.this );
                    builder.setTitle( Turn1Activity.this.getString(R.string.error) );
                    builder.setMessage( Turn1Activity.this.getString(R.string.errorturno1) );
                    builder.create().show();
                }else if(etExpresion.getText().toString().length()==1){
                    AlertDialog.Builder builder = new AlertDialog.Builder( Turn1Activity.this );
                    builder.setTitle( Turn1Activity.this.getString(R.string.error) );
                    builder.setMessage( Turn1Activity.this.getString(R.string.errorturno2) );
                    builder.create().show();
                }else{
                    try {
                        //---------EVALUAR EXPRESION MATEMATICA----------------
                        resultado = calc(etExpresion.getText().toString(), game);
                    }catch (Exception e){
                        AlertDialog.Builder builder = new AlertDialog.Builder( Turn1Activity.this );
                        builder.setTitle( Turn1Activity.this.getString(R.string.error) );
                        builder.setMessage( e.getMessage() );
                        builder.create().show();
                    }
                    if(!resultado.equals("")) {
                        game.setResultado1(resultado);
                        Intent intent = new Intent(v.getContext(), Turn2Activity.class);
                        intent.putExtra("partida", game);
                        startActivityForResult(intent, 0);
                        finish();
                        temporizador.cancel();
                    }
                }
            }

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        label = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        label = true;
    }
//-------------------------EVALUATE MATH EXPRESION----------------------
    public String calc(String expresion, Game game) throws Exception{
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        boolean label0=false;
        boolean label1=false;
        boolean label2=false;
        boolean label3=false;
        boolean label4=false;
        boolean label5=false;
        //----------------COMPROBACIÓN CARACTERES PERMITIDOS-----------------------------
        for(int i=1;i<expresion.length()+1;i++){
            if (!(expresion.substring((i-1),i).equals("+")) &&
                    !(expresion.substring((i-1),i).equals("*")) &&
                    !(expresion.substring((i-1),i).equals("-")) &&
                    !(expresion.substring((i-1),i).equals("/")) &&
                    !(expresion.substring((i-1),i).equals("(")) &&
                    !(expresion.substring((i-1),i).equals(")")) &&
                    !(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[0])) &&
                    !(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[1])) &&
                    !(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[2])) &&
                    !(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[3])) &&
                    !(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[4])) &&
                    !(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[5])))
            {
                    throw new Exception(this.getString(R.string.errorturno3));

            } else if(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[0]) && label0==false){
                label0=true;
            } else if(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[1]) && label1==false){
                label1=true;
            } else if(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[2]) && label2==false){
                label2=true;
            } else if(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[3]) && label3==false){
                label3=true;
            } else if(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[4]) && label4==false){
                label4=true;
            } else if(expresion.substring((i-1),i).equals(game.getDado6().getTirada()[5]) && label5==false){
                label5=true;
            } else if(!(expresion.substring((i-1),i).equals("+")) &&
                    !(expresion.substring((i-1),i).equals("*")) &&
                    !(expresion.substring((i-1),i).equals("-")) &&
                    !(expresion.substring((i-1),i).equals("/")) &&
                    !(expresion.substring((i-1),i).equals("(")) &&
                    !(expresion.substring((i-1),i).equals(")"))){
                throw new Exception(this.getString(R.string.errorturno3));
            }
        }

        Scriptable scope = rhino.initStandardObjects();


        String toRet = rhino.evaluateString(scope, expresion, "JavaScript", 1, null).toString();

        return toRet;


    }
    //---------------------------------OPTIONS MENU-----------------------------------
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
        builder.setMessage( this.getString(R.string.helpturno) );
        builder.create().show();

    }
}

package com.example.luisr.dadomatematico.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luisr.dadomatematico.R;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private ArrayAdapter<String> playerListAdapter;
    private ArrayList<String> playerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ListView lvPlayerList = (ListView) this.findViewById( R.id.idPlayerList );

        this.playerList = new ArrayList<String>();
        this.playerListAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_selectable_list_item,
                this.playerList
        );
        lvPlayerList.setAdapter(this.playerListAdapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.players_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        boolean toret = false;
        switch (menuItem.getItemId()){
            case R.id.opAñadirJugador:
                añadirJugador();
                toret = true;
                break;
            case R.id.opHelp:
                help();
                toret = true;
                break;
        }
        return toret;
    }

    public void añadirJugador(){
        final EditText edText = new EditText( this );

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle("Añadir Jugador");
        builder.setMessage( "Nombre" );
        builder.setView( edText );
        builder.setPositiveButton( "+", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String text = edText.getText().toString();

                GameActivity.this.playerListAdapter.add( text );
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    public void help(){
        final TextView t = new TextView(this);
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle("Help");
        builder.setMessage( "Mensaje ayuda" );
        builder.create().show();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo cmi){
        super.onCreateContextMenu(menu,view,cmi);
        if(view.getId()==R.id.idPlayerList){
            this.getMenuInflater().inflate(R.menu.players_contextual_menu, menu);
            menu.setHeaderTitle(R.string.app_name);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem){
        boolean toret = false;

        int pos= ((AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo()).position;

        switch(menuItem.getItemId()){
            case R.id.context_op_elimina:
                this.elimina(pos);
                toret=true;
                break;
        }
        return toret;
    }

    private void elimina(int pos)
    {
        if(pos>=0){
            GameActivity.this.playerList.remove(pos);
            GameActivity.this.playerListAdapter.notifyDataSetChanged();
        }
    }
}


package com.anew.devl.cardealershipmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.anew.devl.cardealershipmanager.fipeclient.MarcaSelectActivity;
import com.anew.devl.cardealershipmanager.others.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startBuscaFipe();
            }
        });


        ImageButton btnGarage = (ImageButton) findViewById(R.id.imageButton);
        btnGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), GaragemActivity.class);
                startActivity(in);

            }
        });
    }

    private void startBuscaFipe() {
        if (Utils.isOnline(getBaseContext())) {
            Intent in = new Intent(getBaseContext(), MarcaSelectActivity.class);
            startActivity(in);
        } else {
            Toast.makeText(getApplicationContext(), "Não há conexão com a Internet", Toast.LENGTH_LONG).show();
        }
    }

}

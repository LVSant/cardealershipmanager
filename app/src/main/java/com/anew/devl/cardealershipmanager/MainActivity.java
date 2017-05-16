package com.anew.devl.cardealershipmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.anew.devl.cardealershipmanager.fipeclient.FipeSelectActivity;
import com.anew.devl.cardealershipmanager.others.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
            Intent in = new Intent(getBaseContext(), FipeSelectActivity.class);
            in.putExtra(FipeSelectActivity.PASSO_BUSCA, "1");
            startActivity(in);
        } else {
            Toast.makeText(getApplicationContext(), "Não há conexão com a Internet", Toast.LENGTH_LONG).show();
        }
    }

}

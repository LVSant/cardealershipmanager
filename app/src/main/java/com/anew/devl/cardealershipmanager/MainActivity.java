package com.anew.devl.cardealershipmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.anew.devl.cardealershipmanager.fipeclient.FipeSelectCarActivity;
import com.anew.devl.cardealershipmanager.fipeclient.FipeSelectMotoActivity;
import com.anew.devl.cardealershipmanager.others.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imgCar = (ImageView) findViewById(R.id.imgviewCar);
        imgCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBuscaFipe(true);
            }
        });

        ImageView imgMoto = (ImageView) findViewById(R.id.imgviewMoto);
        imgMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBuscaFipe(false);
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

    private void startBuscaFipe(boolean isCar) {
        if (Utils.isOnline(getBaseContext())) {
            Intent in;
            if (isCar) {
                in = new Intent(getBaseContext(), FipeSelectCarActivity.class);
            } else {
                in = new Intent(getBaseContext(), FipeSelectMotoActivity.class);
            }
            in.putExtra(FipeSelectMotoActivity.PASSO_BUSCA, "1");
            startActivity(in);
        } else {
            Toast.makeText(getApplicationContext(), "Não há conexão com a Internet", Toast.LENGTH_LONG).show();
        }
    }

}

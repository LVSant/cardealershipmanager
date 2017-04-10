package com.anew.devl.cardealershipmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.anew.devl.cardealershipmanager.POJO.Veiculo;
import com.anew.devl.cardealershipmanager.fipeclient.adapter.VeiculoGaragemAdapter;
import com.anew.devl.cardealershipmanager.others.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class GaragemActivity extends AppCompatActivity {
    VeiculoGaragemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garagem);


        populateList();
    }

    private void populateList() {
        List<Veiculo> veiculos = new ArrayList<>();
        DBHelper helper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                DBHelper.VeiculoDBHelper._ID,
                DBHelper.VeiculoDBHelper.COLUMN_NAME_NAME,
                DBHelper.VeiculoDBHelper.COLUMN_NAME_MARCA,
                DBHelper.VeiculoDBHelper.COLUMN_NAME_PRECO,
                DBHelper.VeiculoDBHelper.COLUMN_NAME_COMBUSTIVEL,
                DBHelper.VeiculoDBHelper.COLUMN_NAME_ADICIONADO
        };

        //String selection =  DBHelper.VeiculoDBHelper;
        String sortOrder =
                DBHelper.VeiculoDBHelper.COLUMN_NAME_ADICIONADO + " DESC";

        Cursor c = db.query(
                DBHelper.VeiculoDBHelper.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        if (c.moveToFirst()) {
            do {
                //id, name, marca, preco, adicionado
                long id = c.getLong(c.getColumnIndexOrThrow("_id"));
                String name = c.getString(c.getColumnIndexOrThrow("name"));
                String marca = c.getString(c.getColumnIndexOrThrow("marca"));
                String combustivel = c.getString(c.getColumnIndexOrThrow("combustivel"));
                Double preco = c.getDouble(c.getColumnIndexOrThrow("preco"));
                String adicionado = c.getString(c.getColumnIndexOrThrow("adicionado"));


                veiculos.add(new Veiculo(id, name, marca, combustivel, preco, adicionado));


            } while (c.moveToNext());
        }

        this.adapter = new VeiculoGaragemAdapter(getApplicationContext(), veiculos);

        ListView listView = (ListView) findViewById(R.id.listviewgaragem);
        listView.setAdapter(adapter);


    }

    public void btnExcluir(View view) {
        DBHelper helper = new DBHelper(getApplicationContext());
        helper.onDropAll(helper.getWritableDatabase());
    }

    public void btnReadGaragem(View view) {
        populateList();
    }

    public void onBack(View view) {
        this.finish();
    }
}

package com.anew.devl.cardealershipmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.anew.devl.cardealershipmanager.POJO.Veiculo;
import com.anew.devl.cardealershipmanager.fipeclient.adapter.VeiculoGaragemAdapter;
import com.anew.devl.cardealershipmanager.others.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class GaragemActivity extends AppCompatActivity {
    VeiculoGaragemAdapter adapter;
    ArrayList<Long> idsExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garagem);

        init();

        populateList();
    }

    private void init() {

        idsExcluir = new ArrayList<>();

        final Button bex = (Button) findViewById(R.id.btnExcluir);
        bex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnExcluir();
            }
        });

        final ListView listview = (ListView) findViewById(R.id.listviewgaragem);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setItemsCanFocus(false);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //View v = listview.getChildAt(i);
                CheckedTextView checkExcluir = (CheckedTextView) view.findViewById(R.id.checkExcluir);

                if (checkExcluir.isChecked()) {

                    Veiculo item = (Veiculo) adapterView.getItemAtPosition(i);
                    idsExcluir.remove(item.getId());


                    checkExcluir.setChecked(false);


                } else {


                    Veiculo item = (Veiculo) adapterView.getItemAtPosition(i);
                    idsExcluir.add(item.getId());


                    checkExcluir.setCheckMarkDrawable(R.mipmap.ic_delete);
                    checkExcluir.setChecked(true);
                }
            }
        });
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
        String sortByAdd =
                DBHelper.VeiculoDBHelper.COLUMN_NAME_ADICIONADO + " DESC";

        Cursor c = db.query(
                DBHelper.VeiculoDBHelper.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortByAdd                                 // The sort order
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

    public void btnExcluir() {
        if (idsExcluir != null && !idsExcluir.isEmpty()) {
            DBHelper helper = new DBHelper(getBaseContext());
            helper.dropIds(helper.getWritableDatabase(), idsExcluir);

            //atualiza listview
            btnReadGaragem(null);

            //avisa da exclusão
            Toast.makeText(getBaseContext(),
                    "Veículo(s) deletados com sucesso!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void btnReadGaragem(View view) {
        populateList();
    }

    public void onBack(View view) {
        this.finish();
    }
}

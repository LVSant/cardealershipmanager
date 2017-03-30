package com.anew.devl.cardealershipmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.anew.devl.cardealershipmanager.POJO.Marca;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MarcaSelectActivity extends AppCompatActivity {
    final static String MARCA_ID = "cardealershipmanager.idmarca";
    final static String MARCA_NAME = "cardealershipmanager.namemarca";
    MarcaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca_select);

        setTitle("Busca Marcas FIPE");

        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Intent intent = new Intent(getBaseContext(), ModeloSelectActivity.class);

                long marcaSelecionadaID = adapter.getItemId(position);

                intent.putExtra(MARCA_ID, marcaSelecionadaID);
                intent.putExtra(MARCA_NAME, adapter.getItem(position).getName());
                startActivity(intent);
            }
        });


    }

    public void btnReadMarcas(View view) {
        new readMarcas().execute("http://fipeapi.appspot.com/api/1/carros/marcas.json");

    }

    private class readMarcas extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return new HttpHandler().makeServiceCall(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                //That's why it cames as an object
                //JSONObject jsonObject = new JSONObject(result);
                JSONArray marcasItem = new JSONArray(result);
                populateList(marcasItem);
            } catch (Exception e) {
                Log.d("readMarcasEX", e.toString());
            }
        }
    }

    private void populateList(JSONArray marcasItem) {

        ArrayList<Marca> marcas = new ArrayList<>();

        try {
            for (int i = 0; i < marcasItem.length(); i++) {
                JSONObject jsonMarca = marcasItem.optJSONObject(i);

                String name = (String) jsonMarca.get("fipe_name");
                Integer id = (Integer) jsonMarca.get("id");
                String key = (String) jsonMarca.get("key");

                Marca marca = new Marca(name, id, key);

                marcas.add(marca);
            }
        } catch (JSONException jsonex) {
            ToatException(jsonex.toString());
        }


        this.adapter = new MarcaAdapter(this, marcas);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

    }

    private void ToatException(String ex) {
        Toast.makeText(MarcaSelectActivity.this, "Formato JSON Inválido"
                + ex, Toast.LENGTH_LONG).show();
        Log.e("ERROR", ex);
    }

}


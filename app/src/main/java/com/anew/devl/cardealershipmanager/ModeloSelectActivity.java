package com.anew.devl.cardealershipmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.anew.devl.cardealershipmanager.POJO.Modelo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class ModeloSelectActivity extends AppCompatActivity {
    long idMarca = 0;

    ModeloAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelo_select);

        Intent in = getIntent();
        idMarca = in.getLongExtra(MarcaSelectActivity.MARCA_ID, 0l);
        String nameMarca = in.getStringExtra(MarcaSelectActivity.MARCA_NAME);

        if(nameMarca != null && !nameMarca.isEmpty()) {
            TextView texthelper = (TextView) findViewById(R.id.textHelper2);
            texthelper.setText("Fabricante: " + nameMarca);
        }
        ListView listview = (ListView) findViewById(R.id.listviewmodelo);


//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(getBaseContext(), ModeloSelect.class);
//                String message = "abc";
//                long marcaSelecionadaID = adapter.getItemId(position);
//
//                intent.putExtra(MARCA_ID, marcaSelecionadaID);
//                startActivity(intent);
//            }
//        });

        setTitle("Busca Modelos");

    }

    public void btnReadModelos(View view) {
        new readMarcas().execute("http://fipeapi.appspot.com/api/1/carros/veiculos/" + idMarca + ".json");

    }

    private void populateList(JSONArray marcasItem) {

        ArrayList<Modelo> marcas = new ArrayList<>();

        try {
            for (int i = 0; i < marcasItem.length(); i++) {
                JSONObject jsonMarca = marcasItem.optJSONObject(i);

                String name = (String) jsonMarca.get("fipe_name");
                Long id = Long.parseLong(jsonMarca.get("id").toString());


                Modelo modelo = new Modelo(name, id, idMarca);

                marcas.add(modelo);
            }
        } catch (JSONException jsonex) {
            ToatException(jsonex.toString());
        }


        this.adapter = new ModeloAdapter(this, marcas);

        ListView listView = (ListView) findViewById(R.id.listviewmodelo);
        listView.setAdapter(adapter);

    }

    private void ToatException(String ex) {
        Toast.makeText(ModeloSelectActivity.this, "Formato JSON Inválido"
                + ex, Toast.LENGTH_LONG).show();
        Log.e("ERROR", ex);
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
                Log.d("readMModelo", e.toString());
            }
        }
    }

}


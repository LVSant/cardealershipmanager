package com.anew.devl.cardealershipmanager.fipeclient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anew.devl.cardealershipmanager.others.HttpHandler;
import com.anew.devl.cardealershipmanager.POJO.Modelo;
import com.anew.devl.cardealershipmanager.R;
import com.anew.devl.cardealershipmanager.others.Utils;
import com.anew.devl.cardealershipmanager.fipeclient.adapter.ModeloAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ModeloSelectActivity extends AppCompatActivity {
    final static String MODELO_ID = "cardealershipmanager.modeloid";
    final static String MODELO_NAME = "cardealershipmanager.modeloname";
    long idMarca;
    ModeloAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelo_select);
        setTitle("Busca Modelos");

        Intent in = getIntent();
        idMarca = in.getLongExtra(MarcaSelectActivity.MARCA_ID, 0l);
        String nameMarca = in.getStringExtra(MarcaSelectActivity.MARCA_NAME);


        if (nameMarca != null && !nameMarca.isEmpty()) {
            TextView texthelper = (TextView) findViewById(R.id.textHelper2);
            texthelper.setText("Fabricante: " + nameMarca);
        }


        btnReadModelos(null);

        configureOnClickModeloItem(nameMarca, idMarca);
    }

    public void btnReadModelos(View view) {
        if (Utils.isOnline(getBaseContext())) {

            new readMarcas().execute("http://fipeapi.appspot.com/api/1/carros/veiculos/" + idMarca + ".json");
        } else {
            Toast.makeText(getApplicationContext(),
                    "Não há conexão com a Internet", Toast.LENGTH_LONG).show();
        }
    }

    private void configureOnClickModeloItem(final String nameMarca, final long idMarca) {
        ListView listview = (ListView) findViewById(R.id.listviewmodelo);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), AnoVeiculoSelectActivity.class);

                String idModelo= adapter.getItem(position).getKey();
                String modelo= adapter.getItem(position).getName();

                Log.d("VARS", "MarcaID " + idMarca+ " ModeloID " + idModelo);

                intent.putExtra(MODELO_ID, idModelo);
                intent.putExtra(MODELO_NAME, modelo);
                intent.putExtra(MarcaSelectActivity.MARCA_NAME, nameMarca);
                intent.putExtra(MarcaSelectActivity.MARCA_ID, idMarca);
                startActivity(intent);
            }
        });

    }

    private void populateList(JSONArray modelosItems) {

        ArrayList<Modelo> marcas = new ArrayList<>();

        try {
            for (int i = 0; i < modelosItems.length(); i++) {
                JSONObject jsonModelo = modelosItems.optJSONObject(i);

                String name = (String) jsonModelo.get("fipe_name");

                Object idjson = (Object) jsonModelo.get("id");
                String id;
                if(idjson instanceof String){
                     id = idjson.toString();
                }else{
                    id = Integer.parseInt(idjson.toString())+"";

                }
                Modelo modelo = new Modelo(name, idMarca, id);


                marcas.add(modelo);
            }
        } catch (JSONException jsonex) {
            ToatException(jsonex);
        }


        this.adapter = new ModeloAdapter(this, marcas);

        ListView listView = (ListView) findViewById(R.id.listviewmodelo);
        listView.setAdapter(adapter);

    }

    private void ToatException(Exception ex) {
        Toast.makeText(ModeloSelectActivity.this, "Formato JSON Inválido"
                + ex, Toast.LENGTH_LONG).show();
        Log.d("ERROR", ex.getLocalizedMessage());
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
                Log.d("readMModelo",e.getLocalizedMessage());
                e.printStackTrace(System.err);
            }
        }
    }

    public void onBack(View view){
        this.finish();
    }

}


package com.anew.devl.cardealershipmanager.fipeclient;

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

import com.anew.devl.cardealershipmanager.POJO.VeiculoAno;
import com.anew.devl.cardealershipmanager.R;
import com.anew.devl.cardealershipmanager.fipeclient.adapter.AnoVeiculoAdapter;
import com.anew.devl.cardealershipmanager.others.HttpHandler;
import com.anew.devl.cardealershipmanager.others.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AnoVeiculoSelectActivity extends AppCompatActivity {

    long marcaId;
    String  modeloId;
    AnoVeiculoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anoveiculo_select);

        setTitle("Busca Modelos FIPE");
        init();
        configureOnClickVeiculo();
        btnReadAnoVeiculo(null);
    }

    private void init() {


        Intent in = getIntent();

        marcaId = in.getLongExtra(MarcaSelectActivity.MARCA_ID, 0L);

        modeloId = in.getStringExtra(ModeloSelectActivity.MODELO_ID);
        String modeloName = in.getStringExtra(ModeloSelectActivity.MODELO_NAME);
        String marcaName = in.getStringExtra(MarcaSelectActivity.MARCA_NAME);

        Log.d("VARS", "MarcaID " + this.marcaId + " ModeloID " + this.modeloId);

        if (in != null) {
            TextView helper = (TextView) findViewById(R.id.textHelper3);
            helper.setText(marcaName + " " + modeloName);
        }


    }

    public void btnReadAnoVeiculo(View view) {
        if (Utils.isOnline(getBaseContext())) {
            new readMarcas().execute("http://fipeapi.appspot.com/api/1/carros/veiculo/" + this.marcaId + "/" + this.modeloId + ".json");
        } else {
            Toast.makeText(getApplicationContext(),
                    "Não há conexão com a Internet", Toast.LENGTH_LONG).show();
        }
    }

    private void configureOnClickVeiculo() {
        ListView listview = (ListView) findViewById(R.id.listviewanoveiculo);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Intent intent = new Intent(getBaseContext(), ModeloSelectActivity.class);
//                long marcaSelecionadaID = adapter.getItemId(position);
//
//                intent.putExtra(MARCA_ID, marcaSelecionadaID);
//                intent.putExtra(MARCA_NAME, adapter.getItem(position).getName());
//                startActivity(intent);
            }
        });

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
                Log.d("readVeiculosEX", e.toString());
            }
        }
    }

    private void populateList(JSONArray veiculoAnosJSonArray) {

        ArrayList<VeiculoAno> veiculoAnos = new ArrayList<>();

        try {
            for (int i = 0; i < veiculoAnosJSonArray.length(); i++) {
                JSONObject jsonVeiculoAno = veiculoAnosJSonArray.optJSONObject(i);

                String name = (String) jsonVeiculoAno.get("name");
                String id = (String) jsonVeiculoAno.get("id");
                String key = (String) jsonVeiculoAno.get("key");
                String marca = (String) jsonVeiculoAno.get("fipe_marca");
                String veiculo = (String) jsonVeiculoAno.get("veiculo");

                VeiculoAno vAno = new VeiculoAno(name, marca, key, id, veiculo);

                veiculoAnos.add(vAno);
            }
        } catch (JSONException jsonex) {
            ToatException(jsonex.toString());
        }


        this.adapter = new AnoVeiculoAdapter(this, veiculoAnos);

        ListView listView = (ListView) findViewById(R.id.listviewanoveiculo);
        listView.setAdapter(adapter);

    }

    private void ToatException(String ex) {
        Toast.makeText(getApplicationContext(), "Formato JSON Inválido"
                + ex, Toast.LENGTH_LONG).show();
        Log.e("ERROR", ex);
    }

}


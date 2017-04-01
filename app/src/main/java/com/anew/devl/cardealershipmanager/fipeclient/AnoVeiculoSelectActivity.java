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

import static com.anew.devl.cardealershipmanager.fipeclient.MarcaSelectActivity.MARCA_ID;
import static com.anew.devl.cardealershipmanager.fipeclient.MarcaSelectActivity.MARCA_NAME;
import static com.anew.devl.cardealershipmanager.fipeclient.ModeloSelectActivity.MODELO_ID;


public class AnoVeiculoSelectActivity extends AppCompatActivity {

    long marcaId;
    String modeloId;
    String marcaName;
    AnoVeiculoAdapter adapter;

    static final String ANO_ID = "cardealershipmanager.anoid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anoveiculo_select);

        setTitle("Ano VeiculoDBHelper FIPE");
        init();
        configureOnClickVeiculo();
        btnReadAnoVeiculo(null);
    }

    private void init() {

        Intent in = getIntent();

        marcaId = in.getLongExtra(MARCA_ID, 0L);

        modeloId = in.getStringExtra(MODELO_ID);
        String modeloName = in.getStringExtra(ModeloSelectActivity.MODELO_NAME);
        this.marcaName = in.getStringExtra(MARCA_NAME);

        if (in != null) {
            TextView helper = (TextView) findViewById(R.id.textHelper3);
            String helperText = "Selecionado: " + marcaName + " " + modeloName.split(" ")[0] +
                    " " + modeloName.split(" ")[1];
            helper.setText(helperText);
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

                Intent intent = new Intent(getBaseContext(), VeiculoShowActivity.class);

                intent.putExtra(MARCA_ID, marcaId);
                intent.putExtra(MARCA_NAME, marcaName);
                intent.putExtra(MODELO_ID, modeloId);
                intent.putExtra(ANO_ID, adapter.getItem(position).getKey());
                startActivity(intent);
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
                toasException(e.toString());
            }
        }
    }

    private void populateList(JSONArray veiculoAnosJSonArray) {

        ArrayList<VeiculoAno> veiculoAnos = new ArrayList<>();

        try {
            for (int i = 0; i < veiculoAnosJSonArray.length(); i++) {
                JSONObject jsonVeiculoAno = veiculoAnosJSonArray.optJSONObject(i);

                String name = (String) jsonVeiculoAno.get("name");
                //String id = (String) jsonVeiculoAno.get("id");
                String key = (String) jsonVeiculoAno.get("key");
                String marca = (String) jsonVeiculoAno.get("fipe_marca");
                String veiculo = (String) jsonVeiculoAno.get("veiculo");

                VeiculoAno vAno = new VeiculoAno(name, marca, key, 0l, veiculo);

                veiculoAnos.add(vAno);
            }
        } catch (JSONException jsonex) {
            toasException(jsonex.toString());
        }


        this.adapter = new AnoVeiculoAdapter(this, veiculoAnos);

        ListView listView = (ListView) findViewById(R.id.listviewanoveiculo);
        listView.setAdapter(adapter);

    }

    private void toasException(String ex) {
        Toast.makeText(getApplicationContext(), "Formato JSON Inválido"
                + ex, Toast.LENGTH_LONG).show();
        Log.e("ERROR", ex);
    }

    public void onBack(View view) {
        this.finish();
    }

}


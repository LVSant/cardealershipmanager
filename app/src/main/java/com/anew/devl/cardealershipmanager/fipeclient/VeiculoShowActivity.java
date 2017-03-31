package com.anew.devl.cardealershipmanager.fipeclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anew.devl.cardealershipmanager.MainActivity;
import com.anew.devl.cardealershipmanager.POJO.Veiculo;
import com.anew.devl.cardealershipmanager.R;
import com.anew.devl.cardealershipmanager.others.HttpHandler;
import com.anew.devl.cardealershipmanager.others.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.anew.devl.cardealershipmanager.fipeclient.AnoVeiculoSelectActivity.ANO_ID;
import static com.anew.devl.cardealershipmanager.fipeclient.MarcaSelectActivity.MARCA_ID;
import static com.anew.devl.cardealershipmanager.fipeclient.MarcaSelectActivity.MARCA_NAME;
import static com.anew.devl.cardealershipmanager.fipeclient.ModeloSelectActivity.MODELO_ID;


public class VeiculoShowActivity extends AppCompatActivity {


    long marcaId;
    String modeloId;
    String anoId;
    String marcaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo_show);

        setTitle("Visualizar Veiculo");
        init();
        brnReadVeiculo(null);

    }

    private void init() {
        Intent in = getIntent();

        this.marcaId = in.getLongExtra(MARCA_ID, 0L);
        this.marcaName = in.getStringExtra(MARCA_NAME);
        this.modeloId = in.getStringExtra(MODELO_ID);
        this.anoId = in.getStringExtra(ANO_ID);
    }

    public void brnReadVeiculo(View view) {
        if (Utils.isOnline(getBaseContext())) {
            new readModelo().execute("http://fipeapi.appspot.com/api/1/carros/veiculo/" + marcaId + "/" + modeloId + "/" + anoId + ".json");
        } else {
            Toast.makeText(getApplicationContext(),
                    "Não há conexão com a Internet", Toast.LENGTH_LONG).show();
        }
    }

    private class readModelo extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return new HttpHandler().makeServiceCall(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                //That's why it cames as an object
                //JSONObject jsonObject = new JSONObject(result);
                JSONObject item = new JSONObject(result);
                populateList(item);
            } catch (Exception e) {
                toastException(e.toString());
            }
        }
    }


    private void populateList(JSONObject jsonVeiculo) {
        try {
            String name = (String) jsonVeiculo.get("name");
            String combustivel = (String) jsonVeiculo.get("combustivel");
            String referencia = (String) jsonVeiculo.get("referencia");
            String fipe_codigo = (String) jsonVeiculo.get("fipe_codigo");
            String preco = (String) jsonVeiculo.get("preco");

            Veiculo instVeiculo = new Veiculo(name, combustivel, referencia, fipe_codigo, preco);

            TextView textName = (TextView) findViewById(R.id.textName);
            TextView textCombustivel = (TextView) findViewById(R.id.textCombustivel);
            TextView textReferencia = (TextView) findViewById(R.id.textReferencia);
            TextView textCodFipe = (TextView) findViewById(R.id.textCodFipe);
            TextView textPreco = (TextView) findViewById(R.id.textPreco);
            TextView textMarca = (TextView) findViewById(R.id.textMarca);

            textName.setText(instVeiculo.getName());
            textCombustivel.setText(instVeiculo.getCombustivel());
            textReferencia.setText(instVeiculo.getReferencia());
            textCodFipe.setText(instVeiculo.getFipe_codigo());
            textPreco.setText(instVeiculo.getPreco());
            textMarca.setText(marcaName);


        } catch (JSONException jsonex) {
            toastException(jsonex.toString());
        }
    }


    private void toastException(String ex) {
        Toast.makeText(getApplicationContext(), "Formato JSON Inválido"
                + ex, Toast.LENGTH_LONG).show();
        Log.e("ERROR", ex);
    }

    public void onCancelar(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

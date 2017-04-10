package com.anew.devl.cardealershipmanager.fipeclient;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anew.devl.cardealershipmanager.MainActivity;
import com.anew.devl.cardealershipmanager.POJO.VeiculoShowPojo;
import com.anew.devl.cardealershipmanager.R;
import com.anew.devl.cardealershipmanager.others.DBHelper;
import com.anew.devl.cardealershipmanager.others.HttpHandler;
import com.anew.devl.cardealershipmanager.others.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.anew.devl.cardealershipmanager.fipeclient.FipeSelectActivity.ANO_ID;
import static com.anew.devl.cardealershipmanager.fipeclient.FipeSelectActivity.MARCA_ID;
import static com.anew.devl.cardealershipmanager.fipeclient.FipeSelectActivity.MARCA_NAME;
import static com.anew.devl.cardealershipmanager.fipeclient.FipeSelectActivity.MODELO_ID;


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

            VeiculoShowPojo instVeiculo = new VeiculoShowPojo(name, combustivel, referencia, fipe_codigo, preco);

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

    private void toastCadastrado() {
        Toast.makeText(getBaseContext(), "Veículo cadastrado com sucesso!", Toast.LENGTH_LONG).show();
    }

    public void onCadastrar(View view) {

        DBHelper helper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        //values
        TextView textName = (TextView) findViewById(R.id.textName);
        TextView textPreco = (TextView) findViewById(R.id.textPreco);
        TextView textCombustivel = (TextView) findViewById(R.id.textCombustivel);
        TextView textMarca = (TextView) findViewById(R.id.textMarca);

        //handling the String to double thing
        double preco = DBHelper.formatPrecoToSQLiteDouble(textPreco.getText().toString());

        //handling the "SQL Date Format" thing
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String adicionado = df.format(new Date());

        Log.w("DATE VALUE", adicionado);

        ContentValues values = new ContentValues();
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_NAME, textName.getText().toString());
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_MARCA, textMarca.getText().toString());
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_PRECO, preco);
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_ADICIONADO, adicionado);
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_COMBUSTIVEL, textCombustivel.getText().toString());


        //insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBHelper.VeiculoDBHelper.TABLE_NAME, null, values);

        if (newRowId >= 1) {
            toastCadastrado();
            onCancelar(null);
        }

    }
}


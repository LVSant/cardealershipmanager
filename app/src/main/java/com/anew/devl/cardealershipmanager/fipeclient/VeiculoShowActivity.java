package com.anew.devl.cardealershipmanager.fipeclient;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anew.devl.cardealershipmanager.MainActivity;
import com.anew.devl.cardealershipmanager.POJO.Veiculo;
import com.anew.devl.cardealershipmanager.R;
import com.anew.devl.cardealershipmanager.others.DBHelper;
import com.anew.devl.cardealershipmanager.others.HttpHandler;
import com.anew.devl.cardealershipmanager.others.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.anew.devl.cardealershipmanager.fipeclient.FipeSelectCarActivity.ANO_ID;
import static com.anew.devl.cardealershipmanager.fipeclient.FipeSelectCarActivity.MARCA_ID;
import static com.anew.devl.cardealershipmanager.fipeclient.FipeSelectCarActivity.MODELO_ID;
import static com.anew.devl.cardealershipmanager.fipeclient.FipeSelectMotoActivity.IS_CAR;


public class VeiculoShowActivity extends AppCompatActivity {

    private long marcaId;
    private String modeloId;
    private String anoId;
    private Veiculo newVehicle;
    private boolean isCar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

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

        this.isCar = in.getBooleanExtra(IS_CAR, true);
        this.marcaId = in.getLongExtra(MARCA_ID, 0L);
        this.modeloId = in.getStringExtra(MODELO_ID);
        this.anoId = in.getStringExtra(ANO_ID);
    }

    public void brnReadVeiculo(View view) {
        if (Utils.isOnline(getBaseContext())) {
            if (isCar)
                new readModelo().execute("http://fipeapi.appspot.com/api/1/carros/veiculo/" + marcaId + "/" + modeloId + "/" + anoId + ".json");

            if (!isCar)
                new readModelo().execute("http://fipeapi.appspot.com/api/1/motos/veiculo/" + marcaId + "/" + modeloId + "/" + anoId + ".json");
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
            String marca = (String) jsonVeiculo.get("marca");
            String anoModelo = (String) jsonVeiculo.get("ano_modelo");

            //Creates a new vehicle, already handling the PrecoToSQLiteDouble thing
            Veiculo veiculo = new Veiculo(name, marca, combustivel, DBHelper.formatPrecoToSQLiteDouble(preco),
                    referencia, fipe_codigo, anoModelo, isCar);
            newVehicle = veiculo;

            TextView textName = (TextView) findViewById(R.id.textName);
            TextView textCombustivel = (TextView) findViewById(R.id.textCombustivel);
            TextView textReferencia = (TextView) findViewById(R.id.textReferencia);
            TextView textCodFipe = (TextView) findViewById(R.id.textCodFipe);
            TextView textPreco = (TextView) findViewById(R.id.textPreco);
            TextView textMarca = (TextView) findViewById(R.id.textMarca);
            TextView textAnoModelo = (TextView) findViewById(R.id.textAnoModelo);
            TextView textVeiculo = (TextView) findViewById(R.id.textVeiculo);

            NumberFormat nf = NumberFormat.getCurrencyInstance();

            textName.setText(veiculo.getName());
            textCombustivel.setText(veiculo.getCombustivel());
            textReferencia.setText(veiculo.getAdicionado());
            textCodFipe.setText(veiculo.getFipe_codigo());
            textPreco.setText("R" + nf.format(veiculo.getPreco()));
            textMarca.setText(veiculo.getMarca());
            textAnoModelo.setText(veiculo.getAnoModelo());
            textVeiculo.setText(veiculo.getIsCar() ? "Carros e Utilitários Pequenos" : "Motos e Motonetas");


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

        //handling the "SQL Date Format" thing
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String adicionado = df.format(new Date());

        ContentValues values = new ContentValues();
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_NAME, newVehicle.getName());
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_MARCA, newVehicle.getMarca());
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_PRECO, newVehicle.getPreco());
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_ADICIONADO, adicionado);
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_COMBUSTIVEL, newVehicle.getCombustivel());
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_FIPE_CODIGO, newVehicle.getFipe_codigo());
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_ANO_MODELO, newVehicle.getAnoModelo());
        values.put(DBHelper.VeiculoDBHelper.COLUMN_NAME_IS_CAR, newVehicle.getIsCar() ? 1 : 0);


        //insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBHelper.VeiculoDBHelper.TABLE_NAME, null, values);

        if (newRowId >= 1) {
            toastCadastrado();
            onCancelar(null);
        }

    }
}

